# Soy Gradle Plugin

The Soy Gradle plugin lets you compile [Closure Templates](https://developers.google.com/closure/templates/)
into JavaScript functions.

## Usage

To use the plugin, include it in your build script:

```gradle
buildscript {
	dependencies {
		classpath group: "com.liferay", name: "com.liferay.gradle.plugins.soy", version: "1.0.4"
	}

	repositories {
		maven {
			url "https://cdn.lfrs.sl/repository.liferay.com/nexus/content/groups/public"
		}
	}
}

apply plugin: "com.liferay.soy"
```

Since the plugin automatically resolves the Soy library as a dependency, you
have to configure a repository that hosts the library and its transitive
dependencies. The Liferay CDN repository hosts them all:

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
`buildSoy` | \- | [`BuildSoyTask`](#buildsoytask) | Compiles Closure Templates into JavaScript functions.

The `buildSoy` task is automatically configured with sensible defaults,
depending on whether the [`java`](https://docs.gradle.org/current/userguide/java_plugin.html)
plugin is applied:

Property Name | Default Value
------------- | -------------
[`includes`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.SourceTask.html#org.gradle.api.tasks.SourceTask:includes) | `["**/*.soy"]`
[`source`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.SourceTask.html#org.gradle.api.tasks.SourceTask:source) | <p>**If the `java` plugin is applied:** The first `resources` directory of the `main` source set (by default, `src/main/resources`).</p><p>**Otherwise:** `[]`</p>

### BuildSoyTask

Tasks of type `BuildSoyTask` extend [`SourceTask`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.SourceTask.html),
so all its properties and methods, such as [`include`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.SourceTask.html#org.gradle.api.tasks.SourceTask:include(java.lang.Iterable))
and [`exclude`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.SourceTask.html#org.gradle.api.tasks.SourceTask:exclude(java.lang.Iterable)),
are available.

#### Task Properties

Property Name | Type | Default Value | Description
------------- | ---- | ------------- | -----------
`classpath` | [`FileCollection`](https://docs.gradle.org/current/javadoc/org/gradle/api/file/FileCollection.html) | [`project.configurations.soy`](#soy-dependency) | The classpath for executing the main class `com.google.template.soy.SoyToJsSrcCompiler`.

## Additional Configuration

There are additional configurations that can help you use the Soy library.

### Soy Dependency

By default, the plugin creates a configuration called `soy` and adds a
dependency to the `2015-04-10` version of the Soy library. It is possible to
override this setting and use a specific version of the tool by manually adding
a dependency to the `soy` configuration:

```gradle
dependencies {
	soy group: "com.google.template", name: "soy", version: "2015-04-10"
}
```
