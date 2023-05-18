# Checks for .bnd, .gradle, .java or .vm

Check | Category | Description
----- | -------- | -----------
UpgradeBNDIncludeResourceCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Checks if the property value `-includeresource` or `Include-Resource` exists and removes it |
UpgradeGradleIncludeResourceCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replaces with `compileInclude` the configuration attribute for dependencies in `build.gradle` that are listed at `Include-Resource` property at `bnd.bnd` associated file. |
UpgradeJavaAddFolderParameterCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Fill the new parameter of the method `addFolder` of `JournalFolderService`, `JournalFolderLocalService`, and `JournalFolderLocalServiceUtil` classes |
UpgradeJavaCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Performs upgrade checks for `java` files |
UpgradeVelocityCommentMigrationCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of comments from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityFileImportMigrationCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of file import from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityForeachMigrationCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of references to Foreach statement from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityIfStatementsMigrationCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of references to If statements from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityLiferayTaglibReferenceMigrationCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of references to specific Liferay taglib from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityMacroDeclarationMigrationCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of references to Macro statement from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityMacroReferenceMigrationCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of references to a custom Macro statement from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityVariableReferenceMigrationCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of references to variables from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityVariableSetMigrationCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of set variables from a Velocity file to a Freemarker file with the syntax replacements |