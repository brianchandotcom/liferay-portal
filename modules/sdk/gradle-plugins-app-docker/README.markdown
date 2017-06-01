# App Docker Gradle Plugin

The App Docker Gradle plugin lets you build a single Docker image for an
application that spans different subprojects, each one representing a different
component of the same application. The Docker image can then be pushed to a
registry.

The plugin has been successfully tested with Gradle 2.5 up to 3.3.

## Usage

To use the plugin, include it in the build script of the root project:

```gradle
buildscript {
	dependencies {
		classpath group: "com.liferay", name: "com.liferay.gradle.plugins.app.docker", version: "1.0.0"
	}

	repositories {
		maven {
			url "https://cdn.lfrs.sl/repository.liferay.com/nexus/content/groups/public"
		}
	}
}

apply plugin: "com.liferay.app.docker"
```

The App Docker plugin automatically applies the
[`com.bmuschko.docker-remote-api`](https://github.com/bmuschko/gradle-docker-plugin)
plugin.

## Project Extension

The App Docker plugin exposes the following properties through the
extension named `appDocker`:

Property Name | Type | Default Value | Description
------------- | ---- | ------------- | -----------
`imageName` | `String` | `project.name` | The name of the Docker image of the app (e.g., `foo` in the image named `liferay/foo`).
`imageTags` | `List<String>` | `[`committer date of the latest Git commit`, ` hash of the latest Git commit`]` | The list of tags for the Docker image of the app to push to the registry.
`imageUser` | `String` | `project.group` | The user repository of the Docker image of the app (e.g., `liferay` in the image named `liferay/foo`).
`inputDir` | `File` | `"${project.projectDir}/${project.name}-docker"` | The directory that contains the `Dockerfile` and other resources to copy into the context path used to build the Docker image of the app.
`subprojects` | `Set<Project>` | `project.subprojects` | The subprojects to include in the Docker image of the app.

The same extension exposes the following methods:

Method | Description
------ | -----------
`AppDockerExtension imageTags(Iterable<?> imageTags)` | Adds tags for the Docker image of the app to push to the registry.
`AppDockerExtension imageTags(Object... imageTags)` | Adds tags for the Docker image of the app to push to the registry.
`AppDockerExtension onlyIf(Closure<Boolean> onlyIfClosure)` | Includes a subproject in the Docker image if the given closure returns `true`. The closure is evaluated at the end of the subproject configuration phase and is passed a single parameter: the subproject. If the closure returns `false`, the subproject is not included in the Docker image.
`AppDockerExtension onlyIf(Spec<Project> onlyIfSpec)` | Includes a subproject in the Docker image if the given spec is satisfied. The spec is evaluated at the end of the subproject configuration phase. If the spec is not satisfied, the subproject is not included in the Docker image.
`AppDockerExtension subprojects(Iterable<Project> subprojects)` | Include additional projects in the Docker image of the app.
`AppDockerExtension subprojects(Project... subprojects)` | Include additional projects in the Docker image of the app.

## Tasks

The plugin adds a series of tasks to your project:

Name | Depends On | Type | Description
---- | ---------- | ---- | -----------
[`buildAppDockerImage`](#task-buildappdockerimage) | `prepareAppDockerImageInputDir` | [`DockerBuildImage`](http://bmuschko.github.io/gradle-docker-plugin/docs/groovydoc/com/bmuschko/gradle/docker/tasks/image/DockerBuildImage.html) | Builds the Docker image of the app.
[`prepareAppDockerImageInputDir`](#task-prepareappdockerimageinputdir) | \- | [`Sync`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.Sync.html) | Copies all the subproject artifacts and other resources to a temporary directory that will be used to build the Docker image of the app.
[`pushAppDockerImage`](#task-pushappdockerimage) | `[buildAppDockerImage, pushAppDockerImage_tag1, pushAppDockerImage_tag2, `...`]` | [`DockerPushImage`](http://bmuschko.github.io/gradle-docker-plugin/docs/groovydoc/com/bmuschko/gradle/docker/tasks/image/DockerPushImage.html) | Pushes the Docker image of the app to the registry.
[`pushAppDockerImage_${tag}`](#tasks-pushappdockerimage_tag) | `tagAppDockerImage_${tag}` | [`DockerPushImage`](http://bmuschko.github.io/gradle-docker-plugin/docs/groovydoc/com/bmuschko/gradle/docker/tasks/image/DockerPushImage.html) | Pushes the Docker image `${tag}` to the registry.
[`tagAppDockerImage_${tag}`](tasks-tagappdockerimage_tag) | `buildAppDockerImage` | [`DockerTagImage`](http://bmuschko.github.io/gradle-docker-plugin/docs/groovydoc/com/bmuschko/gradle/docker/tasks/image/DockerTagImage.html) | Creates the tag `${tag}` which refers to the Docker image of the app.

### Task `buildAppDockerImage`

The `buildAppDockerImage` task is automatically configured with sensible
defaults:

Property Name | Default Value
------------- | -------------
[`inputDir`](http://bmuschko.github.io/gradle-docker-plugin/docs/groovydoc/com/bmuschko/gradle/docker/tasks/image/DockerBuildImage.html#inputDir) | `project.tasks.prepareAppDockerImageInputDir.destinationDir`
[`tag`](http://bmuschko.github.io/gradle-docker-plugin/docs/groovydoc/com/bmuschko/gradle/docker/tasks/image/DockerBuildImage.html#tag) | `"${appDocker.imageUser}/${appDocker.imageName}"`, or `"${appDocker.imageName}"` if `appDocker.imageUser` is not set.

### Task `prepareAppDockerImageInputDir`

The `prepareAppDockerImageInputDir` task is automatically configured with
sensible defaults:

Property Name | Default Value
------------- | -------------
[`destinationDir`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.Sync.html#org.gradle.api.tasks.Sync:destinationDir) | `"${project.buildDir}/docker"`
[`from`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.Sync.html#org.gradle.api.tasks.Sync:from%28java.lang.Object,%20groovy.lang.Closure%29) | `appDocker.inputDir`, `appDocker.subprojects*.allArtifacts.files` (only the subprojects that respect the `appDocker.onlyIf` conditions).

### Task `pushAppDockerImage`

The `pushAppDockerImage` task is automatically configured with sensible
defaults:

Property Name | Default Value
------------- | -------------
[`imageName`](http://bmuschko.github.io/gradle-docker-plugin/docs/groovydoc/com/bmuschko/gradle/docker/tasks/image/DockerPushImage.html#imageName) | `"${appDocker.imageUser}/${appDocker.imageName}"`, or `"${appDocker.imageName}"` if `appDocker.imageUser` is not set.

### Tasks `pushAppDockerImage_${tag}`

For each entry `imageTag` in the `appDocker.imageTags` collection, one task
`pushAppDockerImage_${appDocker.imageUser}/${appDocker.imageName}:${imageTag}`
of this type is created at the end of the project evaluation. Each one of these
task is automatically configured with sensible defaults:

Property Name | Default Value
------------- | -------------
[`imageName`](http://bmuschko.github.io/gradle-docker-plugin/docs/groovydoc/com/bmuschko/gradle/docker/tasks/image/DockerPushImage.html#imageName) | `project.tasks.tagAppDockerImage_${tag}.repository`
[`tag`](http://bmuschko.github.io/gradle-docker-plugin/docs/groovydoc/com/bmuschko/gradle/docker/tasks/image/DockerPushImage.html#tag) | `project.tasks.tagAppDockerImage_${tag}.tag`

### Tasks `tagAppDockerImage_${tag}`

For each entry `imageTag` in the `appDocker.imageTags` collection, one task
`tagAppDockerImage_${appDocker.imageUser}/${appDocker.imageName}:${imageTag}` of
this type is created at the end of the project evaluation. Each one of these
task is automatically configured with sensible defaults:

Property Name | Default Value
------------- | -------------
[`imageId`](http://bmuschko.github.io/gradle-docker-plugin/docs/groovydoc/com/bmuschko/gradle/docker/tasks/image/DockerExistingImage.html#imageId) | `project.tasks.buildAppDockerImage.imageId`
[`repository`](http://bmuschko.github.io/gradle-docker-plugin/docs/groovydoc/com/bmuschko/gradle/docker/tasks/image/DockerTagImage.html#repository) | `"${appDocker.imageUser}/${appDocker.imageName}"`, or `"${appDocker.imageName}"` if `appDocker.imageUser` is not set.
[`tag`](http://bmuschko.github.io/gradle-docker-plugin/docs/groovydoc/com/bmuschko/gradle/docker/tasks/image/DockerTagImage.html#tag) | `imageTag`