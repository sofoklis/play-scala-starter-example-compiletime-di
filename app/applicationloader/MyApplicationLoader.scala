package applicationloader

import play.api.ApplicationLoader.Context
import play.filters.HttpFiltersComponents
import router.Routes
import services.AtomicCounter

class MyApplicationLoader extends play.api.ApplicationLoader {
  def load(context: Context) = {
    new MyComponents(context).application
  }
}

class MyComponents(context: Context)
  extends play.api.BuiltInComponentsFromContext(context)
    with HttpFiltersComponents with controllers.AssetsComponents {

  val counter = new AtomicCounter()
  val prefix: String = "/"

  lazy val homeController = new mycontrollers.HomeController(controllerComponents, assets, assetsFinder)
  lazy val countController = new mycontrollers.CountController(controllerComponents, counter)
  lazy val asyncController = new mycontrollers.AsyncController(controllerComponents, actorSystem)



  lazy val router = new Routes(httpErrorHandler, homeController, countController, asyncController, assets, prefix)

}