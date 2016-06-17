# XSD Builder Gradle Plugin

The XSD Builder Gradle plugin allows you to generate [Apache XMLBeans](https://xmlbeans.apache.org/)
bindings from XML Schema (XSD) files.

## Usage

To use the plugin, include it in your build script:

```gradle
buildscript {
	dependencies {
		classpath group: "com.liferay", name: "com.liferay.gradle.plugins.xsd.builder", version: "1.0.5"
	}

	repositories {
		maven {
			url "https://cdn.lfrs.sl/repository.liferay.com/nexus/content/groups/public"
		}
	}
}

apply plugin: "com.liferay.xsd.builder"
```

The XSD Builder plugin automatically applies the [`java`](https://docs.gradle.org/current/userguide/java_plugin.html)
plugin.

The plugin automatically resolves the Apache XMLBeans library as a dependency,
therefore, you have to configure a repository hosting the library and its
transitive dependencies. One repository that hosts them all is the Liferay CDN:

```gradle
repositories {
	maven {
		url "https://cdn.lfrs.sl/repository.liferay.com/nexus/content/groups/public"
	}
}
```

## Tasks

The plugin adds three tasks to your project:

Name | Depends On | Type | Description
---- | ---------- | ---- | -----------
`buildXSD` | `buildXSDCompile` | [`BuildXSDTask`](#buildxsdtask) | Generates XMLBeans bindings and compiles them in a JAR file.
`buildXSDGenerate` | `cleanBuildXSDGenerate` | [`JavaExec`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html) | Invokes the [XMLBeans Schema Compiler](https://xmlbeans.apache.org/docs/2.6.0/guide/tools.html#scomp) in order to generate Java types from XML Schema.
`buildXSDCompile` | `buildXSDGenerate`, `cleanBuildXSDCompile` | [`JavaCompile`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.compile.JavaCompile.html) | Compiles the generated Java types.

By default, the `buildXSD` task looks for XSD files in the
`${project.projectDir}/xsd` directory, and saves the generated JAR file as
`${project.projectDir}/lib/${project.archivesBaseName}-xbean.jar`.

Instead, if the [`war`](https://docs.gradle.org/current/userguide/war_plugin.html)
plugin is applied, the task looks for XSD files in the
`${project.webAppDir}/WEB-INF/xsd` directory, and saves the generated JAR file
as `${project.webAppDir}/WEB-INF/lib/${project.archivesBaseName}-xbean.jar`.

### BuildXSDTask

Tasks of type `BuildXSDTask` extend [`Zip`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.bundling.Zip.html).
They also have the following properties set by default:

Property Name | Default Value
------------- | -------------
[`appendix`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.bundling.Zip.html#org.gradle.api.tasks.bundling.Zip:appendix) | `"xbean"`
[`extension`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.bundling.Zip.html#org.gradle.api.tasks.bundling.Zip:extension) | `"jar"`
[`version`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.bundling.Zip.html#org.gradle.api.tasks.bundling.Zip:version) | `null`

For each task of type `BuildXSDTask`, the following helper tasks are created:

- `${buildXSDTask.name}Compile`
- `${buildXSDTask.name}Generate`

#### Task Properties

Property Name | Type | Default Value | Description
------------- | ---- | ------------- | -----------
`inputDir` | `File` | `null` | The directory containing the XSD files.

The properties of type `File` supports any type that can be resolved by [`project.file`](https://docs.gradle.org/current/dsl/org.gradle.api.Project.html#org.gradle.api.Project:file(java.lang.Object)).

## Additional Configuration

There are additional configurations related to the XSD Builder, which can aid in
your usage of the plugin.

### Apache XMLBeans Dependency

By default, the plugin creates a configuration called `xsdBuilder` and adds
a dependency to the 2.5.0 version of Apache XMLBeans. It is possible to override
this setting and use a specific version of the library by manually adding a
dependency to the `xsdBuilder` configuration:

```gradle
dependencies {
	xsdBuilder group: "org.apache.xmlbeans", name: "xmlbeans", version: "2.6.0"
}
```