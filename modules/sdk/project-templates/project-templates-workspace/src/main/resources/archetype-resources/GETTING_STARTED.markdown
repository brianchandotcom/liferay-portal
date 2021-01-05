#set($h1 = '#')
#set($h2 = '##')
#set($h3 = '###')
#set($h4 = '####')
$h1 Getting Started with Liferay Workspace

Complete documentation for Liferay Workspace can be found
[here](https://learn.liferay.com/dxp/7.x/en/developing-applications/tooling/liferay-workspace.html).

$h2 Folder Structure
```
my-project
├── configs
│ ├── common
│ ├── dev
│ ├── docker
│ ├── local
│ ├── prod
│ └── uat
├── modules
│ ├── apis
│ ├── services
│ ├── java widgets
│ ├── js widgets
│ ├── java ee widgets (spring mvc, jsf, etc)
│ └── java themes
└── themes
    └── js themes
```

$h2 Running Liferay DXP locally
```
my-project $ blade gw initBundle
my-project $ blade gw deploy
my-project $ blade server run
```
$h2 Running Liferay DXP in Docker
```
my-project $ blade gw createDockerContainer
my-project $ blade gw startDockerContainer
```

$h2 Creating a Liferay DXP distribution

$h3 Creating a tar
```
my-project $ blade gw distBundleTar
```

$h3 Creating a zip
```
my-project $ blade gw distBundleZip
```

$h3 Creating a docker image
```
my-project $ blade gw buildDockerImage
```

$h2 Create a Liferay DXP module

$h3 Creating a new service
```
my-project $ blade create -t service-builder my-service
```

$h3 Creating a javascript theme
```
my-project $ blade create -t js-theme my-theme
```

$h3 Creating a Java Widget
```
my-project $ blade create -t mvc-portlet "my-java-widget"
```

$h3 Create a JS Widget
```
my-project $ blade create -t js-widget "my-js-widget"
```

$h2 Gradle Properties

Set the following in `gradle.properties` to change the default settings.

$h4 liferay.workspace.product
Set the `liferay.workspace.product` to set the `app.server.tomcat.version`,
`liferay.workspace.bundle.url`, `liferay.workspace.docker.image.liferay`, and
`liferay.workspace.target.platform.version` that matches your Liferay Product
Version. To override each of these settings, set them individually.

$h4 app.server.tomcat.version
Set this property to override the default setting provided by
`liferay.workspace.product`. Set the `app.server.tomcat.version` to match what
is contained inside the Liferay bundle. Both the TestIntegrationPlugin and
LiferayExtPlugin rely on this version to match the bundled Tomcat version. If
your configured bundle url points to a bundle with a different Tomcat version,
set the property below to match that Tomcat version. If you did not set
`liferay.workspace.product`, the default value is `9.0.36`.

$h4 liferay.workspace.bundle.url
Set this property to override the default setting provided by
`liferay.workspace.product`. Set the URL pointing to the bundle Zip to
download. If you did not set `liferay.workspace.product`, the default value is
`https://releases-cdn.liferay.com/portal/7.3.5-ga6/liferay-ce-portal-tomcat-7.3.5-ga6-20200924034643403.tar.gz`.

$h4 liferay.workspace.docker.image.liferay
Set this property to override the default setting provided by
`liferay.workspace.product`. Set the Liferay Portal Docker image to create
your container from. If you did not set `liferay.workspace.product`, the
default value is `liferay/portal:7.3.5-ga6`.

$h4 liferay.workspace.target.platform.version
Set this property to override the default setting provided by
`liferay.workspace.product`. Set the Liferay Portal or DXP version to
develop and test against. By setting this property, it enables the target
platform features such as dependency management and OSGi resolve tasks. Use the
version that matches the Liferay Portal or DXP bundle version in this workspace.

For a list of all available target platform versions, see
https://bit.ly/2IkAwwW for Liferay Portal and https://bit.ly/2GIyfZF for
Liferay DXP. If you did not set `liferay.workspace.product`, the default value
is `7.3.5`.

$h4 liferay.workspace.bundle.cache.dir
Set the directory where the downloaded bundle Zip files are stored. The default
value is the `.liferay/bundles` folder inside the user home directory. The
default value is `~/.liferay/bundles`.

$h4 liferay.workspace.default.repository.enabled
Set this to true to configure Liferay CDN as the default repository in the root
project. The default value is `true`.

$h4 liferay.workspace.environment
Set the environment with the settings appropriate for current development. The
`configs` folder is used to hold different environments in the same workspace.
You can organize environment settings and generate an environment installation
with those settings. There are five environments: common, dev, docker, local,
prod, and uat. The default value is `local`.

$h4 liferay.workspace.ext.dir
Set the folder that contains all Ext OSGi modules and Ext plugins. The default
value is `ext`.

$h4 liferay.workspace.home.dir
Set the folder that contains the Liferay bundle downloaded from the
`liferay.workspace.bundle.url` property. The default value is `bundles`.

$h4 liferay.workspace.modules.default.repository.enabled
Set this to true to configure Liferay CDN as the default repository for
module/OSGi projects. The default value is `true`.

$h4 liferay.workspace.modules.dir
Set the folder that contains all module projects. Set to `*` to search all
subdirectories. The default value is `modules`.

$h4 liferay.workspace.modules.jsp.precompile.enabled
Set this to true to compile the JSP files in OSGi modules and have them added
to the distributable Zip/Tar. The default value is `false`.

$h4 liferay.workspace.node.package.manager
Set this property to `yarn` to build Node.js-style projects using Yarn. The
default value is `npm`.

$h4 liferay.workspace.plugins.sdk.dir
Set the folder that contains the Plugins SDK environment. The default value is
`plugins-sdk`.

$h4 liferay.workspace.themes.dir
Set the folder that contains Node.js-style theme projects. The default value is
`themes`.

$h4 liferay.workspace.themes.java.build
Set this to true to build the theme projects using the Liferay Portal Tools
Theme Builder. The default value is `false`.

$h4 microsoft.translator.subscription.key
Set the subscription key for Microsoft Translation integration. This is service
is used to provide automatic translations for `buildLang`.

$h4 target.platform.index.sources
Set this to true if you have enabled the Target Platform plugin (i.e. you have
set the above property) and you want to apply the TargetPlatformIDE plugin to
the root workspace project. This will cause all of the BOM artifacts jars and
their Java sources to be indexed by your IDE. Setting this property to true can
slow down your IDE's project synchronization.