# Talend Components for Liferay

This project contains components for working with Liferay DXP from Talend
Studio.

Open Talend 6.5.1 (Components API v0.19.9).

## Prerequisites

* JDK 1.8+
* Apache Maven 3.3+
* Open Talend 6.5.1
	* Components API v0.19.9

Download Talend Open Studio 6.5.1: https://www.talend.com/download/talend-open-studio/

## Build

Run Maven `install` task in the components folder:

```sh
$ cd components-liferay
$ mvn clean install
```

This gives you a definition OSGi bundle of the component in
`talend-definition/target/com.liferay.talend.definition-0.1.0-SNAPSHOT.jar`

## Registering components in Talend Studio

Detailed steps of adding new components are described in the Talend Wiki:
* https://github.com/Talend/components/wiki/8.-Testing-the-component-in-Talend-Studio
* [Building the component in the Studio](https://help.talend.com/reader/99uNhyKAYtzK~Gc29xeUSQ/xjeUGCLFdPIkR46ha78wxA)

Here is a brief summary:

1. From the root folder of the project, `/components-liferay/` in this example,
	execute `mvn clean install` to build the component.

2. Let's assume that Studio has been extracted like this:

	```sh
	$ cd $HOME/tmp
	... download distribution archive ...
	$ unzip TOS_ESB-20180116_1512-V6.5.1.zip
	$ STUDIO_ROOT=$HOME/tmp/TOS_ESB-20180116_1512-V6.5.1
	```

3. Now copy the component definition bundle into `$STUDIO_ROOT/plugins`

	```sh
	$ cp [COMPONENTS-LIFERAY]/talend-definition/target/com.liferay.talend.definition-0.1.0-SNAPSHOT.jar \
		 $STUDIO_ROOT/plugins
	```

4. Edit `$STUDIO_ROOT/configuration/config.ini` as it described in wiki page above.
	The diff should look like this:

	```diff
	--- config.ini
	+++ config.ini
	@@ -5,7 +5,7 @@
	 eclipse.product=org.talend.rcp.branding.tos.product
	 #The following osgi.framework key is required for the p2 update feature not to override the osgi.bundles values.
	 osgi.framework=file\:plugins/org.eclipse.osgi_3.10.100.v20150521-1310.jar
	-osgi.bundles=org.eclipse.equinox.common@2:start,org.eclipse.update.configurator@3:start,org.eclipse.equinox.ds@2:start,org.eclipse.core.runtime@start,org.talend.maven.resolver@start,org.ops4j.pax.url.mvn@start,org.talend.components.api.service.osgi@start
	+osgi.bundles=org.eclipse.equinox.common@2:start,org.eclipse.update.configurator@3:start,org.eclipse.equinox.ds@2:start,org.eclipse.core.runtime@start,org.talend.maven.resolver@start,org.ops4j.pax.url.mvn@start,org.talend.components.api.service.osgi@start,com.liferay.talend.definition-0.1.0-SNAPSHOT.jar@start
	 osgi.bundles.defaultStartLevel=4
	 osgi.bundlefile.limit=200
	 osgi.framework.extensions=org.talend.osgi.lib.loader
	```

5. In the `configuration` folder, remove any folders which start their names
with `org.eclipse`.

6. Copy the `com.liferay.talend.runtime` folder from your local
`$USER_HOME/.m2/repository/com/liferay/` to
`$STUDIO_ROOT/configuration/.m2/repository/com/liferay/`

	Tip: you can alternatively add `maven.repository=global` to the
	studio_installation/configuration/config.ini file to make the Studio use
	your local .m2 repository.

* Now start the Studio, and you should be able to see new components on palette
under `Business/Liferay` category.

7. There is a bug in Talend Open Studio 6.5.1 which requires you to manually add
	the component dependency and runtime artifacts to the job's classpath in
	order to be able to run the job with a custom component.

	See bug report: https://community.talend.com/t5/Design-and-Development/Component-definition-is-not-added-to-the-job-s-classpath-in/m-p/49285/highlight/true#M15736