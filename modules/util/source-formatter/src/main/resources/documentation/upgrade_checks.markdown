# Upgrade Checks

Check | File Extensions | Description
----- | --------------- | -----------
[GradleUpgradeReleaseDXPCheck](check/gradle_upgrade_release_dxp_check.markdown#gradleupgradereleasedxpcheck) | .gradle | Remove and replaced dependencies in `build.gradle` that are already in `release.dxp.api` with `released.dxp.api` dependency. |
JSPUpgradeRemovedTagsCheck | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds removed tags when upgrading. |
JavaUpgradeModelPermissionsCheck | .java | Replace setGroupPermissions and setGuestPermissions by new implementation |
JavaUpgradeOnAfterUpdateParameterCheck | .java | Add new parameter in method onAfterUpdate for classes extending the BaseModelListener |
JavaUpgradeSchedulerEntryImplConstructorCheck | .java | Replace constructors that use the empty constructor of the SchedulerEntryImpl class. |
JavaUpgradeServiceTrackerListCheck | .java | Replace the number of generic type arguments in ServiceTrackerList |
[PropertiesUpgradeLiferayPluginPackageFileCheck](check/properties_upgrade_liferay_plugin_package_file_check.markdown#propertiesupgradeliferaypluginpackagefilecheck) | .eslintignore, .prettierignore or .properties | Performs several upgrade checks in `liferay-plugin-package.properties` file. |
PropertiesUpgradeLiferayPluginPackageLiferayVersionsCheck | .eslintignore, .prettierignore or .properties | Validates and upgrades the version in `liferay-plugin-package.properties` file. |
UpgradeBNDIncludeResourceCheck | .bnd, .ftl, .gradle, .java, .jsp, .jspf or .vm | Checks if the property value `-includeresource` or `Include-Resource` exists and removes it |
UpgradeDeprecatedAPICheck | .java | Finds calls to deprecated classes, constructors, fields or methods after an upgrade |
UpgradeGetImagePreviewURLMethodCheck | .bnd, .ftl, .gradle, .java, .jsp, .jspf or .vm | Replaces the references of the method 'DLUtil.getImagePreviewURL' with the method 'getImagePreviewURL' of 'DLURLHelper' class |
UpgradeGetPortletGroupIdMethodCheck | .bnd, .ftl, .gradle, .java, .jsp, .jspf or .vm | Run code migration of the method 'getPortletGroupId' to 'getScopeGroupId' |
UpgradeGradleIncludeResourceCheck | .bnd, .ftl, .gradle, .java, .jsp, .jspf or .vm | Replaces with `compileInclude` the configuration attribute for dependencies in `build.gradle` that are listed at `Include-Resource` property at `bnd.bnd` associated file. |
UpgradeJavaAddFDSTableSchemaFieldCheck | .bnd, .ftl, .gradle, .java, .jsp, .jspf or .vm | Replace method addFDSTableSchemaFieldCheck by add |
UpgradeJavaAddFolderParameterCheck | .bnd, .ftl, .gradle, .java, .jsp, .jspf or .vm | Fill the new parameter of the method `addFolder` of `JournalFolderService`, `JournalFolderLocalService`, and `JournalFolderLocalServiceUtil` classes |
UpgradeJavaAssetEntryAssetCategoriesCheck | .bnd, .ftl, .gradle, .java, .jsp, .jspf or .vm | Replaces methods referring to class `AssetEntryAssetCategory` in class `AssetCategoryLocalService` with equivalent methods in class `AssetEntryAssetCategoryRelLocalService`. |
UpgradeJavaCheck | .bnd, .ftl, .gradle, .java, .jsp, .jspf or .vm | Performs upgrade checks for `java` files |
UpgradeJavaCommerceCountryCheck | .bnd, .ftl, .gradle, .java, .jsp, .jspf or .vm | Replaces the old methods of class `CommerceCountry` with the new equivalents in the `Country` class. |
UpgradeJavaExtractTextMethodCheck | .bnd, .ftl, .gradle, .java, .jsp, .jspf or .vm | Replaces the references of the method `HtmlUtil.extractText(` with the method `extractText(` of `HtmlParser` class |
UpgradeJavaFDSActionProviderCheck | .bnd, .ftl, .gradle, .java, .jsp, .jspf or .vm | Reorder parameters in the getDropdownItems method of the FDSDataProvider interface |
UpgradeJavaFDSDataProviderCheck | .bnd, .ftl, .gradle, .java, .jsp, .jspf or .vm | Reorder parameters in the getItems and getItemsCount methods of the FDSDataProvider interface |
UpgradeJavaGetFileMethodCheck | .bnd, .ftl, .gradle, .java, .jsp, .jspf or .vm | Run code migration of method from 'getFile' to 'getFileAsStream', and include a method 'FileUtil.createTempFile' |
UpgradeJavaMultiVMPoolUtilCheck | .bnd, .ftl, .gradle, .java, .jsp, .jspf or .vm | Replaces the references of the MultiVMPoolUtil class and also its methods usages. |
UpgradeJavaPortletSharedSearchSettingsCheck | .bnd, .ftl, .gradle, .java, .jsp, .jspf or .vm | Replaces the Optional return type of the methods `getParameterValues` and `getPortletPreferences` of `PortletSharedSearchSettings` class |
UpgradeJavaServiceReferenceAnnotationCheck | .bnd, .ftl, .gradle, .java, .jsp, .jspf or .vm | Run code migration to replace '@ServiceReference' by '@Reference' |
UpgradeRejectedExecutionHandlerCheck | .bnd, .ftl, .gradle, .java, .jsp, .jspf or .vm | Replace Liferay's RejectedExecutionHandler with Java's RejectedExecutionHandler |
UpgradeRemovedAPICheck | .java | Finds cases where calls are made to removed API after an upgrade. |
UpgradeSetResultsSetTotalMethodCheck | .bnd, .ftl, .gradle, .java, .jsp, .jspf or .vm | Run code migration of method searchContainer.setResults to the searchContainer.setResultsAndTotal and delete searchContainer.setTotal |
UpgradeVelocityCommentMigrationCheck | .bnd, .ftl, .gradle, .java, .jsp, .jspf or .vm | Run code migration of comments from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityFileImportMigrationCheck | .bnd, .ftl, .gradle, .java, .jsp, .jspf or .vm | Run code migration of file import from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityForeachMigrationCheck | .bnd, .ftl, .gradle, .java, .jsp, .jspf or .vm | Run code migration of references to Foreach statement from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityIfStatementsMigrationCheck | .bnd, .ftl, .gradle, .java, .jsp, .jspf or .vm | Run code migration of references to If statements from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityLiferayTaglibReferenceMigrationCheck | .bnd, .ftl, .gradle, .java, .jsp, .jspf or .vm | Run code migration of references to specific Liferay taglib from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityMacroDeclarationMigrationCheck | .bnd, .ftl, .gradle, .java, .jsp, .jspf or .vm | Run code migration of references to Macro statement from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityMacroReferenceMigrationCheck | .bnd, .ftl, .gradle, .java, .jsp, .jspf or .vm | Run code migration of references to a custom Macro statement from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityVariableReferenceMigrationCheck | .bnd, .ftl, .gradle, .java, .jsp, .jspf or .vm | Run code migration of references to variables from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityVariableSetMigrationCheck | .bnd, .ftl, .gradle, .java, .jsp, .jspf or .vm | Run code migration of set variables from a Velocity file to a Freemarker file with the syntax replacements |
XMLUpgradeDTDVersionCheck | .action, .function, .jelly, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks and upgrades the DTD version in `*.xml` file. |
XMLUpgradeRemovedDefinitionsCheck | .action, .function, .jelly, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Finds removed XML definitions when upgrading. |