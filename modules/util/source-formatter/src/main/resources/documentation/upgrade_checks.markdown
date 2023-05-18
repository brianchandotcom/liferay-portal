# Upgrade Checks

Check | File Extensions | Description
----- | --------------- | -----------
[GradleUpgradeReleaseDXPCheck](check/gradle_upgrade_release_dxp_check.markdown#gradleupgradereleasedxpcheck) | .gradle | Remove and replaced dependencies in `build.gradle` that are already in `release.dxp.api` with `released.dxp.api` dependency. |
JSPUpgradeRemovedTagsCheck | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds removed tags when upgrading. |
[PropertiesUpgradeLiferayPluginPackageFileCheck](check/properties_upgrade_liferay_plugin_package_file_check.markdown#propertiesupgradeliferaypluginpackagefilecheck) | .eslintignore, .prettierignore or .properties | Performs several upgrade checks in `liferay-plugin-package.properties` file. |
PropertiesUpgradeLiferayPluginPackageLiferayVersionsCheck | .eslintignore, .prettierignore or .properties | Validates and upgrades the version in `liferay-plugin-package.properties` file. |
UpgradeBNDIncludeResourceCheck | .bnd, .gradle, .java or .vm | Checks if the property value `-includeresource` or `Include-Resource` exists and removes it |
UpgradeDeprecatedAPICheck | .java | Finds calls to deprecated classes, constructors, fields or methods after an upgrade |
UpgradeGradleIncludeResourceCheck | .bnd, .gradle, .java or .vm | Replaces with `compileInclude` the configuration attribute for dependencies in `build.gradle` that are listed at `Include-Resource` property at `bnd.bnd` associated file. |
UpgradeJavaAddFolderParameterCheck | .bnd, .gradle, .java or .vm | Fill the new parameter of the method `addFolder` of `JournalFolderService`, `JournalFolderLocalService`, and `JournalFolderLocalServiceUtil` classes |
UpgradeJavaCheck | .bnd, .gradle, .java or .vm | Performs upgrade checks for `java` files |
UpgradeRemovedAPICheck | .java | Finds cases where calls are made to removed API after an upgrade. |
UpgradeVelocityCommentMigrationCheck | .bnd, .gradle, .java or .vm | Run code migration of comments from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityFileImportMigrationCheck | .bnd, .gradle, .java or .vm | Run code migration of file import from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityForeachMigrationCheck | .bnd, .gradle, .java or .vm | Run code migration of references to Foreach statement from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityIfStatementsMigrationCheck | .bnd, .gradle, .java or .vm | Run code migration of references to If statements from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityLiferayTaglibReferenceMigrationCheck | .bnd, .gradle, .java or .vm | Run code migration of references to specific Liferay taglib from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityMacroDeclarationMigrationCheck | .bnd, .gradle, .java or .vm | Run code migration of references to Macro statement from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityMacroReferenceMigrationCheck | .bnd, .gradle, .java or .vm | Run code migration of references to a custom Macro statement from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityVariableReferenceMigrationCheck | .bnd, .gradle, .java or .vm | Run code migration of references to variables from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityVariableSetMigrationCheck | .bnd, .gradle, .java or .vm | Run code migration of set variables from a Velocity file to a Freemarker file with the syntax replacements |
XMLUpgradeDTDVersionCheck | .action, .function, .jelly, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks and upgrades the DTD version in `*.xml` file. |
XMLUpgradeRemovedDefinitionsCheck | .action, .function, .jelly, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Finds removed XML definitions when upgrading. |