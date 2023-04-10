# Checks for .bnd, .gradle or .java

Check | Category | Description
----- | -------- | -----------
UpgradeBNDIncludeResourceCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Checks if the property value `-includeresource` or `Include-Resource` exists and removes it |
UpgradeGradleIncludeResourceCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replaces with `compileInclude` the configuration attribute for dependencies in `build.gradle` that are listed at `Include-Resource` property at `bnd.bnd` associated file. |
UpgradeJavaCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Performs upgrade checks for `java` files |
UpgradeVelocityCommentMigrationCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of comments from a `velocity` file to a `fremarker` file with the syntax replacements |