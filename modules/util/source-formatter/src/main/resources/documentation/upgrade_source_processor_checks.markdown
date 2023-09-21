# Checks for .bnd, .ftl, .gradle, .java, .json, .jsp, .jspf, .scss or .vm

Check | Category | Description
----- | -------- | -----------
UpgradeBNDIncludeResourceCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Checks if the property value `-includeresource` or `Include-Resource` exists and removes it |
UpgradeDLUtilCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replace the getGroupIds method of class `DLUtil` by getCurrentAndAncestorSiteGroupIds of class `PortalUtil`. |
UpgradeGetClassNamesMethodCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of method from 'getClassNames' to 'getSearchClassNames' |
UpgradeGetImagePreviewURLMethodCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replaces the references of the method 'DLUtil.getImagePreviewURL' with the method 'getImagePreviewURL' of 'DLURLHelper' class |
UpgradeGetPortletGroupIdMethodCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of the method 'getPortletGroupId' to 'getScopeGroupId' |
UpgradeGradleIncludeResourceCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replaces with `compileInclude` the configuration attribute for dependencies in `build.gradle` that are listed at `Include-Resource` property at `bnd.bnd` associated file. |
UpgradeJSPFieldSetGroupCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code to remove 'fieldset-group' tag |
UpgradeJavaAddAddressMethodCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Fill the new parameter of the method 'addAddress' of AddressLocalService, AddressLocalServiceUtil, AddressService and AddressServiceUtil |
UpgradeJavaAddCategoryParameterCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Fill the new parameter of the method 'addCategory' of 'AssetCategoryLocalService' and 'AssetCategoryLocalServiceUtil' classes |
UpgradeJavaAddFDSTableSchemaFieldCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replace method addFDSTableSchemaFieldCheck by add |
UpgradeJavaAddFileEntryParameterCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Fill the new parameter of the method 'addFileEntry' of 'DLAppLocalServiceUtil' and 'DLAppLocalService' |
UpgradeJavaAddFolderParameterCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Fill the new parameter of the method `addFolder` of `JournalFolderService`, `JournalFolderLocalService`, and `JournalFolderLocalServiceUtil` classes |
UpgradeJavaAssetEntryAssetCategoriesCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replaces methods referring to class `AssetEntryAssetCategory` in class `AssetCategoryLocalService` with equivalent methods in class `AssetEntryAssetCategoryRelLocalService`. |
UpgradeJavaBasePanelAppExtendedClassesCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replace the setPortlet method with getPortlet |
UpgradeJavaCaptchaUtilCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replaces the usages of `CaptchaUti.serveImage(resourceRequest, resourceResponse)` with `CaptchaUti.serveImage(httpServletRequest, httpServletResponse)` |
UpgradeJavaCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Performs upgrade checks for `java` files |
UpgradeJavaCommerceCountryCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replaces the old methods of class `CommerceCountry` with the new equivalents in the `Country` class. |
UpgradeJavaCommerceCountryServiceCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replaces the old methods of class `CommerceCountryService` with the new equivalents in the `CountryService` class. |
UpgradeJavaCommerceRegionCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replaces the old methods of class `CommerceRegion` with the new equivalents in the `Region` class. |
UpgradeJavaCookieKeysCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | CookieKeys class was replaced by CookiesManagerUtil and CookieConstants |
UpgradeJavaCookieUtilCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replace CookieUtilCheck.get by CookiesManagerUtil.getCookieValue and reorder parameters |
UpgradeJavaDLFolderMethodCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Fill the new parameter of the method addFolder of DLFolderLocalService, DLFolderLocalServiceUtil, DLFolderService and DLFolderServiceUtil |
UpgradeJavaExtractTextMethodCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replaces the references of the method `HtmlUtil.extractText(` with the method `extractText(` of `HtmlParser` class |
UpgradeJavaFDSActionProviderCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Reorder parameters in the getDropdownItems method of the FDSDataProvider interface |
UpgradeJavaFDSDataProviderCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Reorder parameters in the getItems and getItemsCount methods of the FDSDataProvider interface |
UpgradeJavaFacetedSearcherCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replaces the references of the `Indexer indexer = FacetedSearcher.getInstance();` declaration and `indexer.search` method call. |
UpgradeJavaGetFileMethodCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of method from 'getFile' to 'getFileAsStream', and include a method 'FileUtil.createTempFile' |
UpgradeJavaGetLayoutDisplayPageObjectProviderCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replace parameter type long by ItemInfoReference in the getLayoutDisplayPageObjectProvider method |
UpgradeJavaGetLayoutDisplayPageProviderCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replace getLayoutDisplayPageProvider by getLayoutDisplayPageProviderByClassName |
UpgradeJavaIndexerCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replace Indexer by Indexer<?> |
UpgradeJavaLanguageUtilCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replace `ListUtil.fromArray` by `new ArrayList' when the parameter is to 'LanguageUtil.getAvailableLocales' |
UpgradeJavaLayoutServicesCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Fill the new parameters of the method `addLayout` and `updateLayout` of `LayoutServiceUtil`, `LayoutService`, `LayoutLocalService` and `LayoutLocalServiceUtil` classes |
UpgradeJavaMultiVMPoolUtilCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replaces the references of the MultiVMPoolUtil class and also its methods usages. |
UpgradeJavaPhoneLocalServiceUtilCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Fill the new parameter `ServiceContext` of the method `addPhone` of class `PhoneLocalServiceUtil`. |
UpgradeJavaPortletIdMethodCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replace the 'document.get(Field.PORTLET_ID)' by the new interface 'PortletProviderUtil.getPortletId' |
UpgradeJavaPortletSharedSearchSettingsCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replaces the Optional return type of the methods `getParameterValues` and `getPortletPreferences` of `PortletSharedSearchSettings` class |
UpgradeJavaSchedulerEntryImplConstructorCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replace constructors that use the empty constructor of the SchedulerEntryImpl class. |
UpgradeJavaSearchVocabulariesMethodCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Fill in the new parameters of the method `searchVocabularies` of 'AssetVocabularyService' and 'AssetVocabularyLocalService' |
UpgradeJavaServiceReferenceAnnotationCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration to replace '@ServiceReference' by '@Reference' |
UpgradeJavaServiceTrackerListCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replace the number of generic type arguments in ServiceTrackerList |
UpgradeJavaUpdateCommerceAddressCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replace parameter in updateCommerceAddress method by other parameters list |
UpgradeJavaUpdateFileEntryMethodCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Fill in the new parameters of the method 'updateFileEntry' of 'DLAppLocalService' and 'DLAppLocalServiceUtil' |
UpgradeJavaUserLocalServiceUtilCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replace parameters of addUser and updateStatus methods in UserServices |
UpgradePortletFTLCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Include the CSS classes 'cadmin' and include for impression of 'right cadmin' in 'portlet.ftl' file |
UpgradeRejectedExecutionHandlerCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replace Liferay's RejectedExecutionHandler with Java's RejectedExecutionHandler |
UpgradeSCSSImportsCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replace compat/mixins by clay/cadmin-variables |
UpgradeSCSSMixinsCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replace outdated mixins (e.g. media-query, respond-to, etc.) |
UpgradeSCSSNodeSassPatternsCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of Dart Sass deprecated patterns (e.g., the division operation using the '/' character, the interpolation syntax, etc.) |
UpgradeSetResultsSetTotalMethodCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of method searchContainer.setResults to the searchContainer.setResultsAndTotal and delete searchContainer.setTotal |
UpgradeVelocityCommentMigrationCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of comments from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityFileImportMigrationCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of file import from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityForeachMigrationCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of references to Foreach statement from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityIfStatementsMigrationCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of references to If statements from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityLiferayTaglibReferenceMigrationCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of references to specific Liferay taglib from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityMacroDeclarationMigrationCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of references to Macro statement from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityMacroReferenceMigrationCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of references to a custom Macro statement from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityVariableReferenceMigrationCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of references to variables from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityVariableSetMigrationCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of set variables from a Velocity file to a Freemarker file with the syntax replacements |