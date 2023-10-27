# Upgrade Checks

Check | File Extensions | Description
----- | --------------- | -----------
[GradleUpgradeReleaseDXPCheck](check/gradle_upgrade_release_dxp_check.markdown#gradleupgradereleasedxpcheck) | .gradle | Remove and replaced dependencies in `build.gradle` that are already in `release.dxp.api` with `released.dxp.api` dependency. |
JSONUpgradeLiferayThemePackageJSONCheck | .ipynb, .json or .npmbridgerc | Upgrade the `package.json` of a Liferay Theme to make it compatible with Liferay 7.4 |
JSPUpgradeRemovedTagsCheck | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds removed tags when upgrading. |
JavaUpgradeCommerceShippingOptionCheck | .java | Replace and reorder parameters in the CommerceShippingOption instance. |
JavaUpgradeModelPermissionsCheck | .java | Replace setGroupPermissions and setGuestPermissions by new implementation |
[PropertiesUpgradeLiferayPluginPackageFileCheck](check/properties_upgrade_liferay_plugin_package_file_check.markdown#propertiesupgradeliferaypluginpackagefilecheck) | .eslintignore, .prettierignore or .properties | Performs several upgrade checks in `liferay-plugin-package.properties` file. |
PropertiesUpgradeLiferayPluginPackageLiferayVersionsCheck | .eslintignore, .prettierignore or .properties | Validates and upgrades the version in `liferay-plugin-package.properties` file. |
UpgradeBNDIncludeResourceCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Checks if the property value `-includeresource` or `Include-Resource` exists and removes it |
UpgradeCatchAllCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Performs replacements on Liferay's outdated code |
UpgradeDLUtilCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replace the getGroupIds method of class `DLUtil` by getCurrentAndAncestorSiteGroupIds of class `PortalUtil`. |
UpgradeDeprecatedAPICheck | .java | Finds calls to deprecated classes, constructors, fields or methods after an upgrade |
UpgradeGetImagePreviewURLMethodCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replaces the references of the method 'DLUtil.getImagePreviewURL' with the method 'getImagePreviewURL' of 'DLURLHelper' class |
UpgradeGetPortletGroupIdMethodCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Run code migration of the method 'getPortletGroupId' to 'getScopeGroupId' |
UpgradeGradleIncludeResourceCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replaces with `compileInclude` the configuration attribute for dependencies in `build.gradle` that are listed at `Include-Resource` property at `bnd.bnd` associated file. |
UpgradeJSPFieldSetGroupCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Run code to remove 'fieldset-group' tag |
UpgradeJavaAccountPortletKeysCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replace constants of CommerceAccountPortletKeys by constants of AccountPortletKeys |
UpgradeJavaAssetEntryAssetCategoriesCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replaces methods referring to class `AssetEntryAssetCategory` in class `AssetCategoryLocalService` with equivalent methods in class `AssetEntryAssetCategoryRelLocalService`. |
UpgradeJavaBaseModelListenerCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Add parameter in the onAfterUpdate and onBeforeUpdate methods of the BaseModelListener class |
UpgradeJavaBasePanelAppExtendedClassesCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replace the setPortlet method with getPortlet |
UpgradeJavaCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Performs upgrade checks for `java` files |
UpgradeJavaCommerceOrderItemServicesCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Fill the new parameters of the method `addCommerceOrderItem` and `deleteCommerceOrderItems` of `CommerceOrderItemLocalService`, `CommerceOrderItemLocalServiceUtil`, `CommerceOrderItemService` and `CommerceOrderItemServiceUtil` classes |
UpgradeJavaCommerceOrderValidatorCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replace the parameter Int for BigDecimal of method validate of 'CommerceOrderValidator' interface |
UpgradeJavaCookieKeysCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | CookieKeys class was replaced by CookiesManagerUtil and CookieConstants |
UpgradeJavaCookieUtilCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replace CookieUtilCheck.get by CookiesManagerUtil.getCookieValue and reorder parameters |
UpgradeJavaFDSActionProviderCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Reorder parameters in the getDropdownItems method of the FDSDataProvider interface |
UpgradeJavaFDSDataProviderCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Reorder parameters in the getItems and getItemsCount methods of the FDSDataProvider interface |
UpgradeJavaFacetedSearcherCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replaces the references of the `Indexer indexer = FacetedSearcher.getInstance();` declaration and `indexer.search` method call. |
UpgradeJavaFetchAssetCategoryByExternalReferenceCodeCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Adds a message about the breaking change made on method `fetchAssetCategoryByExternalReferenceCode` of `AssetCategoryLocalService` and `AssetCategoryLocalServiceUtil` classes. |
UpgradeJavaGetFDSTableSchemaParameterCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Fill the new parameter of the method 'getFDSTableSchema' of 'FDSTableSchema' |
UpgradeJavaGetFileMethodCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Run code migration of method from 'getFile' to 'getFileAsStream', and include a method 'FileUtil.createTempFile' |
UpgradeJavaGetLayoutDisplayPageObjectProviderCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replace parameter type long by ItemInfoReference in the getLayoutDisplayPageObjectProvider method |
UpgradeJavaGetLayoutDisplayPageProviderCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replace getLayoutDisplayPageProvider by getLayoutDisplayPageProviderByClassName |
UpgradeJavaIndexerCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replace Indexer by Indexer<?> |
UpgradeJavaLanguageUtilCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replace `ListUtil.fromArray` by `new ArrayList' when the parameter is to 'LanguageUtil.getAvailableLocales' |
UpgradeJavaMultiVMPoolUtilCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replaces the references of the MultiVMPoolUtil class and also its methods usages. |
UpgradeJavaPortletIdMethodCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replace the 'document.get(Field.PORTLET_ID)' by the new interface 'PortletProviderUtil.getPortletId' |
UpgradeJavaPortletSharedSearchSettingsCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replaces the Optional return type of the methods `getParameterValues` and `getPortletPreferences` of `PortletSharedSearchSettings` class |
UpgradeJavaSchedulerEntryImplConstructorCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replace constructors that use the empty constructor of the SchedulerEntryImpl class. |
UpgradeJavaServiceReferenceAnnotationCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Run code migration to replace '@ServiceReference' by '@Reference' |
UpgradeJavaServiceTrackerListCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replace the number of generic type arguments in ServiceTrackerList |
UpgradeJavaUpdateCommerceAddressCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replace parameter in updateCommerceAddress method by other parameters list |
UpgradeJavaUpdateFileEntryMethodCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Fill in the new parameters of the method 'updateFileEntry' of 'DLAppLocalService' and 'DLAppLocalServiceUtil' |
UpgradeJavaUserLocalServiceUtilCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replace parameters of addUser and updateStatus methods in UserServices |
UpgradePortletDisplayCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replaces 'portletDisplay.getPortletInstanceConfiguration' with 'ConfigurationProviderUtil.getPortletInstanceConfiguration' |
UpgradePortletFTLCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Include the CSS classes 'cadmin' and include for impression of 'right cadmin' in 'portlet.ftl' file |
UpgradeRejectedExecutionHandlerCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replace Liferay's RejectedExecutionHandler with Java's RejectedExecutionHandler |
UpgradeRemovedAPICheck | .java | Finds cases where calls are made to removed API after an upgrade. |
UpgradeSCSSImportsCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replace compat/mixins by clay/cadmin-variables |
UpgradeSCSSMixinsCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replace outdated mixins (e.g. media-query, respond-to, etc.) |
UpgradeSCSSNodeSassPatternsCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Run code migration of Dart Sass deprecated patterns (e.g., the division operation using the '/' character, the interpolation syntax, etc.) |
UpgradeSetResultsSetTotalMethodCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Run code migration of method searchContainer.setResults to the searchContainer.setResultsAndTotal and delete searchContainer.setTotal |
UpgradeVelocityCommentMigrationCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Run code migration of comments from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityFileImportMigrationCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Run code migration of file import from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityForeachMigrationCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Run code migration of references to Foreach statement from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityIfStatementsMigrationCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Run code migration of references to If statements from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityLiferayTaglibReferenceMigrationCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Run code migration of references to specific Liferay taglib from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityMacroDeclarationMigrationCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Run code migration of references to Macro statement from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityMacroReferenceMigrationCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Run code migration of references to a custom Macro statement from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityVariableReferenceMigrationCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Run code migration of references to variables from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityVariableSetMigrationCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Run code migration of set variables from a Velocity file to a Freemarker file with the syntax replacements |
XMLUpgradeCompatibilityVersionCheck | .action, .function, .jelly, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks and upgrades the compatibility version in `*.xml` file. |
XMLUpgradeDTDVersionCheck | .action, .function, .jelly, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks and upgrades the DTD version in `*.xml` file. |
XMLUpgradeRemovedDefinitionsCheck | .action, .function, .jelly, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Finds removed XML definitions when upgrading. |