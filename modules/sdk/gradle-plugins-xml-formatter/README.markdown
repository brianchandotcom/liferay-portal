# XML Formatter Gradle Plugin

The XML Formatter Gradle plugin allows you to format project XML files using the
Liferay XML Formatter tool.

## Usage

To use the plugin, include it in your build script:

```gradle
buildscript {
	dependencies {
		classpath group: "com.liferay", name: "com.liferay.gradle.plugins.xml.formatter", version: "1.0.5"
	}

	repositories {
		maven {
			url "https://cdn.lfrs.sl/repository.liferay.com/nexus/content/groups/public"
		}
	}
}

apply plugin: "com.liferay.xml.formatter"
```

The plugin automatically resolves the Liferay XML Formatter library as a
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
`formatXML` | \- | [`FormatXMLTask`](#formatxmltask) | Runs Liferay XML Formatter to format the project files.

If the [`java`](https://docs.gradle.org/current/userguide/java_plugin.html)
plugin is applied, the task formats the XML files contained in the [`resources`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.SourceSet.html#org.gradle.api.tasks.SourceSet:resources)
directories of the `main` [source set](https://docs.gradle.org/current/userguide/java_plugin.html#N1503E) (by default: `src/main/resources/**/*.xml`).

### FormatXMLTask

Tasks of type `FormatXMLTask` extend [`SourceTask`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.SourceTask.html),
so all its properties and methods, like [`include`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.SourceTask.html#org.gradle.api.tasks.SourceTask:include(java.lang.Iterable))
and [`exclude`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.SourceTask.html#org.gradle.api.tasks.SourceTask:exclude(java.lang.Iterable))
are available.

#### Task Properties

Property Name | Type | Default Value | Description
------------- | ---- | ------------- | -----------
`classpath` | [`FileCollection`](https://docs.gradle.org/current/javadoc/org/gradle/api/file/FileCollection.html) | [`project.configurations.xmlFormatter`](#liferay-xml-formatter-dependency) | The classpath for executing the main class.
`mainClassName` | `String` | `"com.liferay.xml.formatter.XMLFormatter"` | The fully qualified name of the XML Formatter Main class.
`stripComments` | `boolean` | `false` | Whether to remove all the comments from the XML files.

## Additional Configuration

There are additional configurations related to the XML Formatter, which can aid
in your usage of the plugin.

### Liferay XML Formatter Dependency

By default, the plugin creates a configuration called `xmlFormatter` and adds
a dependency to the latest released version of Liferay XML Formatter. It is
possible to override this setting and use a specific version of the tool by
manually adding a dependency to the `xmlFormatter` configuration:

```gradle
dependencies {
	xmlFormatter group: "com.liferay", name: "com.liferay.xml.formatter", version: "1.0.0"
}
```