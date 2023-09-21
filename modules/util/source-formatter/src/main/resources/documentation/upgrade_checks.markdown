# Upgrade Checks

Check | File Extensions | Description
----- | --------------- | -----------
[GradleUpgradeReleaseDXPCheck](check/gradle_upgrade_release_dxp_check.markdown#gradleupgradereleasedxpcheck) | .gradle | Remove and replaced dependencies in `build.gradle` that are already in `release.dxp.api` with `released.dxp.api` dependency. |
JSONUpgradeLiferayThemePackageJSONCheck | .ipynb, .json or .npmbridgerc | Upgrade the `package.json` of a Liferay Theme to make it compatible with Liferay 7.4 |
JSPUpgradeRemovedTagsCheck | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds removed tags when upgrading. |
JavaUpgradeCommerceShippingOptionCheck | .java | Replace and reorder parameters in the CommerceShippingOption instance. |
JavaUpgradeFetchCPDefinitionByCProductExternalReferenceCodeCheck | .java | Reorder parameters in the fetchCPDefinitionByCProductExternalReferenceCode method |
JavaUpgradeModelPermissionsCheck | .java | Replace setGroupPermissions and setGuestPermissions by new implementation |
JavaUpgradeOnAfterUpdateParameterCheck | .java | Add new parameter in method onAfterUpdate for classes extending the BaseModelListener |
[PropertiesUpgradeLiferayPluginPackageFileCheck](check/properties_upgrade_liferay_plugin_package_file_check.markdown#propertiesupgradeliferaypluginpackagefilecheck) | .eslintignore, .prettierignore or .properties | Performs several upgrade checks in `liferay-plugin-package.properties` file. |
PropertiesUpgradeLiferayPluginPackageLiferayVersionsCheck | .eslintignore, .prettierignore or .properties | Validates and upgrades the version in `liferay-plugin-package.properties` file. |
UpgradeBNDIncludeResourceCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Checks if the property value `-includeresource` or `Include-Resource` exists and removes it |
UpgradeDLUtilCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replace the getGroupIds method of class `DLUtil` by getCurrentAndAncestorSiteGroupIds of class `PortalUtil`. |
UpgradeDeprecatedAPICheck | .java | Finds calls to deprecated classes, constructors, fields or methods after an upgrade |
UpgradeGetClassNamesMethodCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Run code migration of method from 'getClassNames' to 'getSearchClassNames' |
UpgradeGetImagePreviewURLMethodCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replaces the references of the method 'DLUtil.getImagePreviewURL' with the method 'getImagePreviewURL' of 'DLURLHelper' class |
UpgradeGetPortletGroupIdMethodCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Run code migration of the method 'getPortletGroupId' to 'getScopeGroupId' |
UpgradeGradleIncludeResourceCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replaces with `compileInclude` the configuration attribute for dependencies in `build.gradle` that are listed at `Include-Resource` property at `bnd.bnd` associated file. |
UpgradeJSPFieldSetGroupCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Run code to remove 'fieldset-group' tag |
UpgradeJavaAddAddressMethodCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Fill the new parameter of the method 'addAddress' of AddressLocalService, AddressLocalServiceUtil, AddressService and AddressServiceUtil |
UpgradeJavaAddCategoryParameterCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Fill the new parameter of the method 'addCategory' of 'AssetCategoryLocalService' and 'AssetCategoryLocalServiceUtil' classes |
UpgradeJavaAddFDSTableSchemaFieldCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replace method addFDSTableSchemaFieldCheck by add |
UpgradeJavaAddFileEntryParameterCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Fill the new parameter of the method 'addFileEntry' of 'DLAppLocalServiceUtil' and 'DLAppLocalService' |
UpgradeJavaAddFolderParameterCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Fill the new parameter of the method `addFolder` of `JournalFolderService`, `JournalFolderLocalService`, and `JournalFolderLocalServiceUtil` classes |
UpgradeJavaAssetEntryAssetCategoriesCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replaces methods referring to class `AssetEntryAssetCategory` in class `AssetCategoryLocalService` with equivalent methods in class `AssetEntryAssetCategoryRelLocalService`. |
UpgradeJavaBasePanelAppExtendedClassesCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replace the setPortlet method with getPortlet |
UpgradeJavaCaptchaUtilCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replaces the usages of `CaptchaUti.serveImage(resourceRequest, resourceResponse)` with `CaptchaUti.serveImage(httpServletRequest, httpServletResponse)` |
UpgradeJavaCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Performs upgrade checks for `java` files |
UpgradeJavaCommerceCountryCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replaces the old methods of class `CommerceCountry` with the new equivalents in the `Country` class. |
UpgradeJavaCommerceCountryServiceCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replaces the old methods of class `CommerceCountryService` with the new equivalents in the `CountryService` class. |
UpgradeJavaCommerceRegionCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replaces the old methods of class `CommerceRegion` with the new equivalents in the `Region` class. |
UpgradeJavaCookieKeysCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | CookieKeys class was replaced by CookiesManagerUtil and CookieConstants |
UpgradeJavaCookieUtilCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replace CookieUtilCheck.get by CookiesManagerUtil.getCookieValue and reorder parameters |
UpgradeJavaDLFolderMethodCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Fill the new parameter of the method addFolder of DLFolderLocalService, DLFolderLocalServiceUtil, DLFolderService and DLFolderServiceUtil |
UpgradeJavaExtractTextMethodCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replaces the references of the method `HtmlUtil.extractText(` with the method `extractText(` of `HtmlParser` class |
UpgradeJavaFDSActionProviderCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Reorder parameters in the getDropdownItems method of the FDSDataProvider interface |
UpgradeJavaFDSDataProviderCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Reorder parameters in the getItems and getItemsCount methods of the FDSDataProvider interface |
UpgradeJavaFacetedSearcherCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replaces the references of the `Indexer indexer = FacetedSearcher.getInstance();` declaration and `indexer.search` method call. |
UpgradeJavaGetFileMethodCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Run code migration of method from 'getFile' to 'getFileAsStream', and include a method 'FileUtil.createTempFile' |
UpgradeJavaGetLayoutDisplayPageObjectProviderCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replace parameter type long by ItemInfoReference in the getLayoutDisplayPageObjectProvider method |
UpgradeJavaGetLayoutDisplayPageProviderCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replace getLayoutDisplayPageProvider by getLayoutDisplayPageProviderByClassName |
UpgradeJavaIndexerCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replace Indexer by Indexer<?> |
UpgradeJavaLanguageUtilCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replace `ListUtil.fromArray` by `new ArrayList' when the parameter is to 'LanguageUtil.getAvailableLocales' |
UpgradeJavaLayoutServicesCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Fill the new parameters of the method `addLayout` and `updateLayout` of `LayoutServiceUtil`, `LayoutService`, `LayoutLocalService` and `LayoutLocalServiceUtil` classes |
UpgradeJavaMultiVMPoolUtilCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replaces the references of the MultiVMPoolUtil class and also its methods usages. |
UpgradeJavaPhoneLocalServiceUtilCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Fill the new parameter `ServiceContext` of the method `addPhone` of class `PhoneLocalServiceUtil`. |
UpgradeJavaPortletIdMethodCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replace the 'document.get(Field.PORTLET_ID)' by the new interface 'PortletProviderUtil.getPortletId' |
UpgradeJavaPortletSharedSearchSettingsCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replaces the Optional return type of the methods `getParameterValues` and `getPortletPreferences` of `PortletSharedSearchSettings` class |
UpgradeJavaSchedulerEntryImplConstructorCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replace constructors that use the empty constructor of the SchedulerEntryImpl class. |
UpgradeJavaSearchVocabulariesMethodCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Fill in the new parameters of the method `searchVocabularies` of 'AssetVocabularyService' and 'AssetVocabularyLocalService' |
UpgradeJavaServiceReferenceAnnotationCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Run code migration to replace '@ServiceReference' by '@Reference' |
UpgradeJavaServiceTrackerListCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replace the number of generic type arguments in ServiceTrackerList |
UpgradeJavaUpdateCommerceAddressCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replace parameter in updateCommerceAddress method by other parameters list |
UpgradeJavaUpdateFileEntryMethodCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Fill in the new parameters of the method 'updateFileEntry' of 'DLAppLocalService' and 'DLAppLocalServiceUtil' |
UpgradeJavaUserLocalServiceUtilCheck | .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm | Replace parameters of addUser and updateStatus methods in UserServices |
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