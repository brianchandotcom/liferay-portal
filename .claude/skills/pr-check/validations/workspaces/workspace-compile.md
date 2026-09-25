# Workspace Compile

## Trigger

Always.

## Match

`.`

## Command

```bash
(cd "${BUILD_ROOT}" && ./gradlew --continue assemble testIntegrationClasses)
```

`assemble` compiles every module and builds its jar, runs the JavaScript build, and builds every client extension, which runs `validateClientExtensions` and `validateClientExtensionIds`. `testIntegrationClasses` compiles the integration test sources without running them, so a change that breaks an integration test's call site fails here rather than in CI's `workspaces-integration` batch. Neither pulls in a unit test, an integration test run, or the product bundle.

`--continue` keeps one broken module from hiding the rest, since Gradle otherwise stops at the first failed task.

Do not add heap flags. A workspace that needs more heap sets `org.gradle.jvmargs` in its own `gradle.properties`, and a flag here would override it.

Report FAIL when a failing task is in something the diff changed, quoting the failing task line and the error. When the failing task is in code the diff did not touch, the workspace is already broken, so report **NOT VERIFIED** for it and name the failing task and the commit that last changed its source. Report **NOT VERIFIED** as well when the run could not proceed, such as a dependency that failed to download.

## Notes

The first run in a workspace resolves the workspace plugin and the product dependencies from the network and is far slower than later runs.

## Time Estimate

~1-3 min warm, longer on the first run in a workspace.