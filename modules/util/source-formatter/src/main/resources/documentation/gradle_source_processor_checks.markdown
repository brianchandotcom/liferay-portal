# Checks for .gradle

Check | Category | Description
----- | -------- | -----------
GradleBlockOrderCheck | [Styling](styling_checks.markdown#styling-checks) | Sorts logic in gradle build files. |
GradleBodyCheck | [Styling](styling_checks.markdown#styling-checks) | Applies rules to enforce consistency in the body of gradle build files. |
[GradleDependenciesCheck](check/gradle_dependencies_check.markdown#gradledependenciescheck) | [Performance](performance_checks.markdown#performance-checks) | Checks that modules are not depending on other modules. |
[GradleDependencyArtifactsCheck](check/gradle_dependency_artifacts_check.markdown#gradledependencyartifactscheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that value `default` is not used for attribute `version`. |
GradleDependencyConfigurationCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Validates the scope of dependencies in build gradle files. |
GradleDependencyVersionCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks the version for dependencies in gradle build files. |
GradleExportedPackageDependenciesCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Validates dependencies in gradle build files. |
GradleImportsCheck | [Styling](styling_checks.markdown#styling-checks) | Sorts and groups imports in `.gradle` files. |
GradleIndentationCheck | [Styling](styling_checks.markdown#styling-checks) | Finds incorrect indentation in gradle build files. |
GradleJavaVersionCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks values of properties `sourceCompatibility` and `targetCompatibility` in gradle build files. |
GradleMissingJarManifestTaskCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds missing `jarManifest` task when using `jarPatched` task in gradle build files. |
GradlePropertiesCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Validates property values in gradle build files. |
GradleProvidedDependenciesCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Validates the scope of dependencies in build gradle files. |
[GradleRequiredDependenciesCheck](check/gradle_required_dependencies_check.markdown#gradlerequireddependenciescheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Validates the dependencies in `/required-dependencies/required-dependencies/build.gradle`. |
GradleStylingCheck | [Styling](styling_checks.markdown#styling-checks) | Applies rules to enforce consistency in code style. |
[GradleTaskCreationCheck](check/gradle_task_creation_check.markdown#gradletaskcreationcheck) | [Styling](styling_checks.markdown#styling-checks) | Checks that a task is declared on a separate line before the closure. |
GradleTestDependencyVersionCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks the version for dependencies in gradle build files. |
GradleTestUtilDeployDirCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks for incorrect use of `deployDir`. |
[GradleUpgradeReleaseDXPCheck](check/gradle_upgrade_release_dxp_check.markdown#gradleupgradereleasedxpcheck) | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Remove and replaced dependencies in `build.gradle` that are already in `release.dxp.api` with `released.dxp.api` dependency. |
WhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | Finds missing and unnecessary whitespace. |