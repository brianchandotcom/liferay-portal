# WSDD Builder Gradle Plugin

The WSDD Builder Gradle plugin allows you to run the Liferay WSDD Builder tool
in order to generate the [Apache Axis](http://axis.apache.org/axis/) Web Service
Deployment Descriptor (WSDD) files from a [Service Builder](https://dev.liferay.com/develop/tutorials/-/knowledge_base/7-0/what-is-service-builder)
`service.xml` file.

## Usage

To use the plugin, include it in your build script:

```gradle
buildscript {
	dependencies {
		classpath group: "com.liferay", name: "com.liferay.gradle.plugins.wsdd.builder", version: "1.0.8"
	}

	repositories {
		maven {
			url "https://cdn.lfrs.sl/repository.liferay.com/nexus/content/groups/public"
		}
	}
}

apply plugin: "com.liferay.wsdd.builder"
```

The WSDD Builder plugin automatically applies the [`java`](https://docs.gradle.org/current/userguide/java_plugin.html)
plugin.

The plugin automatically resolves the Liferay WSDD Builder library as a
dependency, therefore, you have to configure a repository hosting the library
and its transitive dependencies. One repository that hosts them all is the
Liferay CDN:

```gradle
repositories {
	maven {
		url "https://cdn.lfrs.sl/repository.liferay.com/nexus/content/groups/public"
	}
}
```

## Tasks

The plugin adds one task to your project:

Name | Depends On | Type | Description
---- | ---------- | ---- | -----------
`buildWSDD` | [`compileJava`](https://docs.gradle.org/current/userguide/java_plugin.html#sec:compile) | [`BuildWSDDTask`](#buildwsddtask) | Runs Liferay WSDD Builder.

By default, the `buildWSDD` task uses the `${project.projectDir}/service.xml`
file as input. Then it generates `${project.projectDir}/server-config.wsdd` and
the `*_deploy.wsdd` and `*_undeploy.wsdd` files in the first [`resources`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.SourceSet.html#org.gradle.api.tasks.SourceSet:resources)
directory of the `main` [source set](https://docs.gradle.org/current/userguide/java_plugin.html#N1503E)
(by default: `src/main/resources`).

Instead, if the [`war`](https://docs.gradle.org/current/userguide/war_plugin.html)
plugin is applied, the task uses `${project.webAppDir}/WEB-INF/service.xml` as
input, and generates `${project.webAppDir}/WEB-INF/server-config.wsdd`. The
`*_deploy.wsdd` and `*_undeploy.wsdd` files are still generated in the first
`resources` directory of the `main` source set.

Liferay WSDD Build Service requires an additional classpath (configured with the
`buildWSDD.builderClasspath` property) in order to correctly generate the WSDD
files. The `buildWSDD` task uses the following default value, which creates an
implicit dependency to the `compileJava` task:

```gradle
tasks.compileJava.outputs.files + sourceSets.main.compileClasspath + sourceSets.main.runtimeClasspath
```

### BuildWSDDTask

Tasks of type `BuildWSDDTask` extend [`JavaExec`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html),
so all its properties and methods, like [`args`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:args(java.lang.Iterable))
and [`maxHeapSize`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:maxHeapSize)
are available. They also have the following properties set by default:

Property Name | Default Value
------------- | -------------
[`args`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:args) | WSDD Builder command line arguments
[`classpath`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:classpath) | [`project.configurations.wsddBuilder`](#liferay-wsdd-builder-dependency)
[`main`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:main) | `"com.liferay.portal.tools.wsdd.builder.WSDDBuilder"`

#### Task Properties

Property Name | Type | Default Value | Description
------------- | ---- | ------------- | -----------
`builderClasspath` | `String` | `null` | The classpath used by Liferay WSDD Builder to generate the WSDD files.
`inputFile` | `File` | `null` | The `service.xml` from which to generate the WSDD files.
`outputDir` | `File` | `null` | The directory where the `*_deploy.wsdd` and `*_undeploy.wsdd` files should be generated.
`serverConfigFile` | `File` | `${project.projectDir}/server-config.wsdd` | The destination of the generated `server-config.wsdd` file.
`serviceNamespace` | `String` | `"Plugin"` | The WSDD Service namespace.

The properties of type `File` supports any type that can be resolved by [`project.file`](https://docs.gradle.org/current/dsl/org.gradle.api.Project.html#org.gradle.api.Project:file(java.lang.Object)).
Moreover, it is possible to use Closures and Callables as values for the
`String` properties, in order to defer their evaluation at the moment of the
task execution.

## Additional Configuration

There are additional configurations related to the WSDD Builder, which can aid
in your usage of the plugin.

### Liferay WSDD Builder Dependency

By default, the plugin creates a configuration called `wsddBuilder` and adds
a dependency to the latest released version of Liferay WSDD Builder. It is
possible to override this setting and use a specific version of the tool by
manually adding a dependency to the `wsddBuilder` configuration:

```gradle
dependencies {
	wsddBuilder group: "com.liferay", name: "com.liferay.portal.tools.wsdd.builder", version: "1.0.4"
}
```