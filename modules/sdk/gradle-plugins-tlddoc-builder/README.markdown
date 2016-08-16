# TLDDoc Builder Gradle Plugin

The TLDDoc Builder Gradle plugin allows you to run the
[Tag Library Documentation Generator](http://web.archive.org/web/20070624180825/https://taglibrarydoc.dev.java.net/)
tool in order to generate documentation for the JSP Tag Library Descriptor (TLD)
files in your project.

## Usage

To use the plugin, include it in your build script:

```gradle
buildscript {
	dependencies {
		classpath group: "com.liferay", name: "com.liferay.gradle.plugins.tlddoc.builder", version: "1.0.3"
	}

	repositories {
		maven {
			url "https://cdn.lfrs.sl/repository.liferay.com/nexus/content/groups/public"
		}
	}
}

apply plugin: "com.liferay.tlddoc.builder"
```

Since the plugin automatically resolves the Tag Library Documentation Generator
library as a dependency, you have to configure a repository that hosts the
library and its transitive dependencies. The Liferay CDN repository hosts them
all:

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
`copyTLDDocResources` | \- | [`Copy`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.Copy.html) | Copies Tag Library documentation resources from `src/main/tlddoc` to the [destination directory](#destinationdir) of the `tlddoc` task.
`tlddoc` | `copyTLDDocResources`, `validateTLD` | [`TLDDocTask`](#tlddoctask) | Generates the Tag Library documentation.
`validateTLD` | \- | [`ValidateSchemaTask`](#validateschematask) | Validates the TLD files in this project.

The `tlddoc` task is automatically configured with sensible defaults,
depending on whether the [`java`](https://docs.gradle.org/current/userguide/java_plugin.html)
plugin is applied:

Property Name | Default Value with the `java` plugin
------------- | -------------
[`destinationDir`](#destinationdir) | `${project.docsDir}/tlddoc`
[`includes`](#includes) | `["**/*.tld"]`
[`source`](#source) | `project.sourceSets.main.resources.srcDirs`

If the `java`plugin is applied, the `validateTLD` is similarly configured with
the following sensible defaults:

Property Name | Default Value with the `java` plugin
------------- | -------------
[`includes`](#includes) | `["**/*.tld"]`
[`source`](#source) | `project.sourceSets.main.resources.srcDirs`

By default, `tlddoc` task generates the documentation for all the TLD files that
are found in the resources directories of the `main` sourceset. The
documentation files are saved in `build/docs/tlddoc` and include the files
copied from `src/main/tlddoc`.

Thanks to the `copyTLDDocResources` task, it is possible to add references to
images and other resoures directly in the TLD files. For example, if the project
includes an image called `breadcrumb.png` in the `src/main/tlddoc/images`
directory, you can reference it in a TLD file contained in the
`src/main/resources` directory:

```xml
<description>Hello World <![CDATA[<img src="../images/breadcrumb.png"]]></description>
```

### TLDDocTask

Tasks of type `TLDDocTask` extend [`JavaExec`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html),
so all its properties and methods, such as [`args`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:args(java.tlddoc.Iterable))
and [`maxHeapSize`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:maxHeapSize),
are available. They also have the following properties set by default:

Property Name | Default Value
------------- | -------------
[`args`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:args) | Tag Library Documentation Generator command line arguments
[`classpath`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:classpath) | [`project.configurations.tlddoc`](#tag-library-documentation-generator-dependency)
[`main`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:main) | `"com.sun.tlddoc.TLDDoc"`
[`maxHeapSize`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:maxHeapSize) | `"256m"`

The `TLDDocTask` class is also very similar to [`SourceTask`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.SourceTask.html),
which means it provides a `source` property and allows to specify include and
exclude patterns.

#### Task Properties

Property Name | Type | Default Value | Description
------------- | ---- | ------------- | -----------
<a name="destinationdir"></a>`destinationDir` | `File` | `null` | The directory where the Tag Library documentation files are saved.
`excludes` | `Set<String>` | `[]` | The TLD file patterns to exclude.
<a name="includes"></a>`includes` | `Set<String>` | `[]` | The TLD file patterns to include.
<a name="source"></a>`source` | [`FileTree`](https://docs.gradle.org/current/javadoc/org/gradle/api/file/FileTree.html) | `[]` | The TLD files to generate documentation for, after the include and exclude patterns have been applied.
`xsltDir` | `File` | `null` | The directory which contains the custom XSLT stylesheets used by the Tag Library Documentation Generator to produce the final documentation files. It sets the `-xslt` argument.

The properties of type `File` support any type that can be resolved by
[`project.file`](https://docs.gradle.org/current/dsl/org.gradle.api.Project.html#org.gradle.api.Project:file(java.tlddoc.Object)).

#### Task Methods

The methods available for `TLDDocTask` are exactly the same as the one defined
in the [`SourceTask`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.SourceTask.html)
class.

### ValidateSchemaTask

Tasks of type `ValidateSchemaTask` extend [`SourceTask`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.SourceTask.html),
so all its properties and methods, such as [`include`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.SourceTask.html#org.gradle.api.tasks.SourceTask:include(java.lang.Iterable))
and [`exclude`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.SourceTask.html#org.gradle.api.tasks.SourceTask:exclude(java.lang.Iterable)),
are available.

Tasks of this type invoke the [`schemavalidate`](http://ant.apache.org/manual/Tasks/schemavalidate.html)
Ant task in order to validate XML files described by an XML schema.

#### Task Properties

Property Name | Type | Default Value | Description
------------- | ---- | ------------- | -----------
`dtdDisabled` | `boolean` | `false` | Whether to disable DTD support.
`fullChecking` | `boolean` | `true` | Whether to enable full schema checking.
`lenient` | `boolean` | `false` | Whether to only check if the XML document is well-formed.

## Additional Configuration

There are additional configurations that can help you use the TLDDoc Builder.

### Tag Library Documentation Generator Dependency

By default, the plugin creates a configuration called `tlddoc` and adds a
dependency to the 1.3 version of the Tag Library Documentation Generator. It is
possible to override this setting and use a specific version of the tool by
manually adding a dependency to the `tlddoc` configuration:

```gradle
dependencies {
	tlddoc group: "taglibrarydoc", name: "tlddoc", version: "1.3"
}
```
