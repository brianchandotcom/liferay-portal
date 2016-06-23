# TLD Formatter Gradle Plugin

The TLD Formatter Gradle plugin allows you to format project TLD files using the
Liferay TLD Formatter tool.

## Usage

To use the plugin, include it in your build script:

```gradle
buildscript {
	dependencies {
		classpath group: "com.liferay", name: "com.liferay.gradle.plugins.tld.formatter", version: "1.0.4"
	}

	repositories {
		maven {
			url "https://cdn.lfrs.sl/repository.liferay.com/nexus/content/groups/public"
		}
	}
}

apply plugin: "com.liferay.tld.formatter"
```

The plugin automatically resolves the Liferay TLD Formatter library as a
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
`formatTLD` | \- | [`FormatTLDTask`](#formattldtask) | Runs Liferay TLD Formatter to format files.

### FormatTLDTask

Tasks of type `FormatTLDTask` extend [`JavaExec`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html),
so all its properties and methods, like [`args`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:args(java.lang.Iterable))
and [`maxHeapSize`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:maxHeapSize)
are available. They also have the following properties set by default:

Property Name | Default Value
------------- | -------------
[`args`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:args) | TLD Formatter command line arguments
[`classpath`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:classpath) | [`project.configurations.tldFormatter`](#liferay-tld-formatter-dependency)
[`main`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:main) | `"com.liferay.tld.formatter.TLDFormatter"`

#### Task Properties

Property Name | Type | Default Value | Description
------------- | ---- | ------------- | -----------
`plugin` | `boolean` | `true` | Whether to format all the TLD files contained in the [`workingDir`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:workingDir) directory. Otherwise, the file `liferay-portlet-ext.tld`, if found, will be ignored.

## Additional Configuration

There are additional configurations related to the TLD Formatter, which can aid
in your usage of the plugin.

### Liferay TLD Formatter Dependency

By default, the plugin creates a configuration called `tldFormatter` and adds
a dependency to the latest released version of Liferay TLD Formatter. It is
possible to override this setting and use a specific version of the tool by
manually adding a dependency to the `tldFormatter` configuration:

```gradle
dependencies {
	tldFormatter group: "com.liferay", name: "com.liferay.tld.formatter", version: "1.0.0"
}
```