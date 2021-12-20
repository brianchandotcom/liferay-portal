# Talend Components for Liferay

This project contains components for working with Liferay DXP from Talend
Studio.

Open Talend 7.1.1

## Prerequisites

* JDK 1.8+
* Apache Maven 3.3+
* Open Talend 7.1.1
	* Components API v0.25.3

Download Talend Open Studio: https://www.talend.com/products/talend-open-studio/
* Direct link: https://download-mirror2.talend.com/esb/release/V7.1.1/TOS_ESB-20181026_1147-V7.1.1.zip
* All releases link: https://www.talend.com/products/data-integration-manuals-release-notes/

## Talend Open Studio Settings

### Eclipse `TOS_*.ini` Settings

Adjust memory settings if necessary in `TOS_HOME/studio/TOS_*.ini` file by
providing `-X` jvm arguments after line with `-vmargs`. If necessary other jvm
arguments may be configured here as well. Here is configuration sample:

```sh
-vm
/Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/MacOS/libjli.dylib
-vmargs
-Xms512m
-Xmx1536m
-Dfile.encoding=UTF-8
-Dosgi.requiredJavaVersion=1.8
-XX:+UseG1GC
-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005
-Dos.version=10.14
-Declipse.log.level=ALL
```
Mac OSX users must make sure they set `-Dos.version` to current OSX version.

### config.ini Settings

After successful installation one should make sure that
`TOS_HOME/studio/configuration/config.ini` does not set
`maven.repository=global`.
Delete the line or comment that setting `# maven.repository=global`.

### Maven Central Repository Setting

Update `TOS_HOME/studio/configuration/maven_user_settings.xml` file to include
workaround for recent Maven deprecation of insecure repository connections. To
make sure m2m plugin can reach repository with secure connection include
repository mirror location:

```
<?xml version="1.0" encoding="UTF-8"?>
<settings>
  <localRepository>DO_NOT_CHANGE_THIS</localRepository>
  <mirrors>
    <mirror>
      <id>secure-central-mirror</id>
      <name>Secure Central Mirror Repository</name>
      <url>https://repo.maven.apache.org/maven2</url>
      <mirrorOf>central</mirrorOf>
    </mirror>
  </mirrors>
</settings>
```

## Build

Run Maven `clean install` tasks in the `modules/etl/talend` folder:

```sh
$ cd modules/etl/talend
$ mvn clean install
```

This gives you a definition OSGi bundle of the component in
`talend-definition/target/com.liferay.talend.definition-x.y.z-SNAPSHOT.jar`
and additional:
`talend-common/target/com.liferay.talend.common-x.y.z-SNAPSHOT.jar`
`talend-runtime/target/com.liferay.talend.runtime-x.y.z-SNAPSHOT.jar`

Current unpublished version is `0.4.0-SNAPSHOT`.

Maven install task will succeed only if all tests pass. JAR files will be
published to current user's local maven repository `USER_HOME/.m2`:
* `USER_HOME/.m2/repository/com/liferay/com.liferay.talend`
* `USER_HOME/.m2/repository/com/liferay/com.liferay.common`
* `USER_HOME/.m2/repository/com/liferay/com.liferay.talend.definition`
* `USER_HOME/.m2/repository/com/liferay/com.liferay.talend.runtime`

If you get an error of a missing artifact, this could be the result of a failed test.
Since the installation will not finish successfully without the missing artifact, you may need to do the following:

Navigate to each of the talend subfolders and run Maven clean install task with the skip test flag
```sh
$ cd modules/etl/talend/..
$ mvn clean install -DskipTests
```

Once all artifacts are in place, you can run Maven clean install in the parent folder

## Registering components in Talend Studio

Detailed steps of adding new components are described in the Talend Wiki:
* https://github.com/Talend/components/wiki/8.-Testing-the-component-in-Talend-Studio
* [Building the component in the Studio](https://help.talend.com/reader/99uNhyKAYtzK~Gc29xeUSQ/xjeUGCLFdPIkR46ha78wxA)

Here is a brief summary:

1. From the root folder of the project, `liferay-portal/modules/etl/talend/` in this example,
    execute `mvn clean install` to build the component.

2. Let's assume that Studio has been extracted like this:

    ```sh
    $ cd $HOME/tmp
    ... download distribution archive ...
    $ unzip TOS_ESB-Version.zip
    $ STUDIO_ROOT=$HOME/tmp/TOS_ESB-Version
    ```

3. Now copy the component definition bundle latest version into
`$STUDIO_ROOT/plugins`

    ```sh
    $ cp [liferay-portal/modules/etl/talend]/talend-definition/target/com.liferay.talend.definition-x.y.z-SNAPSHOT.jar \
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
    +osgi.bundles=org.eclipse.equinox.common@2:start,org.eclipse.update.configurator@3:start,org.eclipse.equinox.ds@2:start,org.eclipse.core.runtime@start,org.talend.maven.resolver@start,org.ops4j.pax.url.mvn@start,org.talend.components.api.service.osgi@start,com.liferay.talend.definition-x.y.z-SNAPSHOT.jar@start
     osgi.bundles.defaultStartLevel=4
     osgi.bundlefile.limit=200
     osgi.framework.extensions=org.talend.osgi.lib.loader

5. Copy the `com.liferay.talend`, `com.liferay.talend.common` and
`com.liferay.talend.runtime` folders from your local

    `$USER_HOME/.m2/repository/com/liferay/` to

    `$STUDIO_ROOT/configuration/.m2/repository/com/liferay/`

    Now start the Studio, and you should be able to see new components on palette
    under `Business/Liferay` category.

6. There is a bug in Talend Open Studio which requires you to manually add
    the component dependency and runtime artifacts to the job's classpath in
    order to be able to run the job with a custom component.

    See bug report: https://community.talend.com/t5/Design-and-Development/Component-definition-is-not-added-to-the-job-s-classpath-in/m-p/49285/highlight/true#M15736

    Alternative workaround:
    ```
    1. Right mouse click on `.Java` project or if you use TOS 7.0.1+ the project
        called `LOCAL_PROJECT_$ActualJobName$` in `Navigator` view
    2. Maven -> Update Project
    3. Uncheck "Offline" and run Update
    ```

## Reloading components in Talend Studio after codebase changed

If component codebase is changed and user wants latest version, user should
reload generated JARs to Talend Studio. It is similar to initial component
registration:

1. Shutdown Talend Studio

2. In the `configuration` folder, remove any folders which start their names
    with `org.eclipse`.

3. From the root folder of the project, `liferay-portal/modules/etl/talend/`, execute
    `mvn clean install` to rebuild and publish the components to Maven repo.

4. Now copy the new version of component definition bundle into `$STUDIO_ROOT/plugins`

	```sh
	$ cp [liferay-portal/modules/etl/talend]/talend-definition/target/com.liferay.talend.definition-x.y.z-SNAPSHOT.jar \
		 $STUDIO_ROOT/plugins
	```
    TIP: **Please make sure you remove old jar version.**

5. Copy the `com.liferay.talend`, `com.liferay.talend.common` and
`com.liferay.talend.runtime` folders from your local
`$USER_HOME/.m2/repository/com/liferay/` to
`$STUDIO_ROOT/configuration/.m2/repository/com/liferay/`

6. Start Talend Studio

7. Update existing Talend jobs to use latest component version
 This has to be done manually. In Talend Studio workspace open job that uses Liferay components.
 Remove components. Pick back components from Palette view and configure them as before.

 Tip: Make sure you are aware of all previous configurations otherwise your updated job may not
 keep the same functionality as before.

OSX and Linux users may use these 2 scripts to automatize process:

1. install component: https://gist.github.com/igorbeslic/1bc7aeb76445e8d72908157ff40a466a

2. redeploy component: https://gist.github.com/igorbeslic/6ddccd61c7fbc20c3fd65b303c46caa7

## Running Talend studio in OS with multiple java versions

Regarding to JVM requirements from prerequisites section, user may end up in problems in OS where
multiple versions of Java present. Talend Studio doesn't rely on `JAVA_HOME`environment variable
but on system launcher Java version so it will pick the latest Java version installed in OS. For
XOS users OS can be easily forced to use particular Java version by renaming
`[JAVA_INSTALL_DIR]/Contents/Info.plist` to `Info.plist.disabled`.

Use `/usr/libexec/java_home -V` command to locate and disable unwanted JREs.

	Tip: User can still use newer JREs by manipulating `JAVA_HOME` environment variable.

## Basic guidelines for Liferay Component Configuration
All I/O components require Open API Module Path setting. Path must always start
with `/headless-` as it is pattern for all headless modules generated by REST
Builder and it requires proper version sub path.

Valid path examples:
``
/headless-delivery-api
/headless-commerce-admin-catalog/v1.0
/headless-commerce-delivery-catalog/v1.0
``

All components support re-using Liferay Connection Component. It saves user from
repetitive connection configuration steps required by all I/O components.

### List of components

* Connection Component

  Liferay Host URL should be set to root domain like `"http://localhost:8080"`
  or `"https://liferay.com"`

* Liferay Input Component

  Submits GET requests to configured endpoint, receives data, converts it to
  schema described by Open API Spec and passes it further to Talend flow.  

* Liferay Output Component

  Receives data, converts it to schema described by Open API Spec schema and
  submits POST/PUT requests with data to configured endpoint.

* Liferay Batch File

  Formats received records into proper format described by Open API Spec schema
  and aggregates it into JSON line batch file.

* Liferay Batch Output File

  Submits batch file to `headless-batch-engine` endpoint. This component may
  refer to Liferay Batch File component if that component produces batch file