# Poshi Runner Gradle Plugin

The Poshi Runner Gradle plugin lets you use [Poshi](https://github.com/liferay/liferay-portal/tree/master/modules/test/poshi)
to write and run functional tests.

## Usage

To use the plugin, include it in your build script:

```gradle
buildscript {
	dependencies {
		classpath group: "com.liferay", name: "com.liferay.gradle.plugins.poshi.runner", version: "3.0.25"
	}

	repositories {
		maven {
			url "https://repository-cdn.liferay.com/nexus/content/groups/public"
		}
	}
}

apply plugin: "com.liferay.poshi.runner"
```

## Project Extension

The Poshi Runner Gradle plugin exposes the following properties through the
extension named `poshiRunner`:

Property Name | Type | Default Value | Description
------------- | ---- | ------------- | -----------
`baseDir` | `File` | `src/testFunctional` | The base directory of hte project that will store the Poshi files. The plugin will set `test.base.dir.name` to `baseDir` in system properties.
`openCVVersion` | `String` | `2.4.9-0.9` | The version of OpenCV to use (Sikuli dependency).
`poshiPropertiesFile` | `File` | `poshi.properties` | The properties file that will override and update test configurations.
`testNames` | Set<String> | | The test names to be passed along to Poshi. The plugin will set `test.name` to `testNames` in system properties.
`version` | `String` | `"1.0.311"` | The version of [`com.liferay.poshi.runner`](https://github.com/liferay/liferay-portal/tree/master/modules/test/poshi/poshi-runner) to use.

The properties of type `File` support any type that can be resolved by
[`project.file`](https://docs.gradle.org/current/dsl/org.gradle.api.Project.html#org.gradle.api.Project:file(java.css.Object)).
Moreover, it is possible to use Closures and Callables as values for `String`,
to defer evaluation until execution.

## Tasks

The plugin adds a series of tasks to your project:

Name | Depends On | Type | Description
---- | ---------- | ---- | -----------
`executePQLQuery` | \- | [`JavaExec`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html) | Invokes the Poshi class/method: [`com.liferay.poshi.runner.PoshiRunnerCommandExecutor.executePQLQuery`](https://github.com/liferay/liferay-portal/blob/master/modules/test/poshi/poshi-runner/src/main/java/com/liferay/poshi/runner/PoshiRunnerCommandExecutor.java). Executes a PQL query.
`evaluatePoshiConsole` | \- | [`JavaExec`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html) | Invokes the Poshi class: [`com.liferay.poshi.runner.PoshiRunnerConsoleEvaluator`](https://github.com/liferay/liferay-portal/blob/master/modules/test/poshi/poshi-runner/src/main/java/com/liferay/poshi/runner/PoshiRunnerConsoleEvaluator.java). Evaluates console output errors.
`runPoshi` | \- | [`Test`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.testing.Test.html) | Executes a Poshi test.
`validatePoshi` | \- | [`JavaExec`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html) | Invokes the Poshi class: [`com.liferay.poshi.core.PoshiValidation`](https://github.com/liferay/liferay-portal/blob/master/modules/test/poshi/poshi-core/src/main/java/com/liferay/poshi/core/PoshiValidation.java). Validates the Poshi file syntax
`writePoshiProperties` | \- | [`JavaExec`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html) | Generates properties using meta data from Poshi files in the project.