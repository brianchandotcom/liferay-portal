# Bug Prevention Checks

Check | File Extensions | Description
----- | --------------- | -----------
[AnonymousClassCheck](checks/anonymous_class_check.markdown#anonymousclasscheck) | .java | Checks for serialization issue when using anonymous class. |
ArquillianCheck | .java | Checks for correct use of `com.liferay.arquillian.extension.junit.bridge.junit.Arquillian`. |
[AvoidStarImportCheck](https://checkstyle.sourceforge.io/config_imports.html#AvoidStarImport) | .java | Checks that there are no import statements that use the * notation. |
[BNDBundleActivatorCheck](checks/bnd_bundle_activator_check.markdown#bndbundleactivatorcheck) | .bnd | Validates property value for `Bundle-Activator`. |
[BNDBundleCheck](checks/bnd_bundle_check.markdown#bndbundlecheck) | .bnd | Validates `Liferay-Releng-*` properties. |
[BNDBundleInformationCheck](checks/bnd_bundle_information_check.markdown#bndbundleinformationcheck) | .bnd | Validates property values for `Bundle-Version`, `Bundle-Name` and `Bundle-SymbolicName`. |
[BNDDefinitionKeysCheck](checks/bnd_definition_keys_check.markdown#bnddefinitionkeyscheck) | .bnd | Validates definition keys in `.bnd` files. |
[BNDDirectoryNameCheck](checks/bnd_directory_name_check.markdown#bnddirectorynamecheck) | .bnd | Checks if the directory names of the submodules match the parent module name. |
[BNDExportsCheck](checks/bnd_exports_check.markdown#bndexportscheck) | .bnd | Checks that modules not ending with `-api`, `-client`, `-spi`, `-tablig`, `-test-util` do not export packages. |
[BNDIncludeResourceCheck](checks/bnd_include_resource_check.markdown#bndincluderesourcecheck) | .bnd | Checks for unnesecarry including of `test-classes/integration`. |
[BNDLiferayEnterpriseAppCheck](checks/bnd_liferay_enterprise_app_check.markdown#bndliferayenterpriseappcheck) | .bnd | Checks for correct use of property `Liferay-Enterprise-App`. |
[BNDLiferayRelengBundleCheck](checks/bnd_liferay_releng_bundle_check.markdown#bndliferayrelengbundlecheck) | .bnd | Checks if `.lfrbuild-release-src` file exists for DXP module with `Liferay-Releng-Bundle: true` |
[BNDLiferayRelengCategoryCheck](checks/bnd_liferay_releng_category_check.markdown#bndliferayrelengcategorycheck) | .bnd | Validates `Liferay-Releng-Category` properties |
[BNDMultipleAppBNDsCheck](checks/bnd_multiple_app_bnds_check.markdown#bndmultipleappbndscheck) | .bnd | Checks for duplicate `app.bnd` (when both `/apps/` and `/apps/dxp/` contain the same module). |
[BNDRangeCheck](checks/bnd_range_check.markdown#bndrangecheck) | .bnd | Checks for use or range expressions. |
[BNDSchemaVersionCheck](checks/bnd_schema_version_check.markdown#bndschemaversioncheck) | .bnd | Checks for incorrect use of property `Liferay-Require-SchemaVersion`. |
[BNDWebContextPathCheck](checks/bnd_web_context_path_check.markdown#bndwebcontextpathcheck) | .bnd | Checks if the property value for `Web-ContextPath` matches the module directory. |
CDNCheck | | Checks the URL in `artifact.properties` files. |
CQLKeywordCheck | .cql | Checks that Cassandra keywords are upper case. |
[CodeownersFileLocationCheck](checks/codeowners_file_location_check.markdown#codeownersfilelocationcheck) | CODEOWNERS | Checks that `CODEOWNERS` files are located in `.github` directory. |
CompatClassImportsCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that classes are imported from `compat` modules, when possible. |
DeprecatedUsageCheck | .java | Finds calls to deprecated classes or methods. |
FactoryCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | Finds cases where `*Factory` should be used when creating new instances of an object. |
FilterStringWhitespaceCheck | .java | Finds missing and unnecessary whitespace in the value of the filter string in `ServiceTrackerFactory.open` or `WaiterUtil.waitForFilter`. |
[GenericTypeCheck](checks/generic_type_check.markdown#generictypecheck) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that generics are always specified to provide compile-time checking and removing the risk of `ClassCastException` during runtime. |
[GradleDependencyArtifactsCheck](checks/gradle_dependency_artifacts_check.markdown#gradledependencyartifactscheck) | .gradle | Checks that value `default` is not used for attribute `version`. |
GradleDependencyConfigurationCheck | .gradle | Validates the scope of dependencies in build gradle files. |
GradleDependencyVersionCheck | .gradle | Checks the version for dependencies in gradle build files. |
GradleExportedPackageDependenciesCheck | .gradle | Validates dependencies in gradle build files. |
GradleJavaVersionCheck | .gradle | Checks values of properties `sourceCompatibility` and `targetCompatibility` in gradle build files. |
GradlePropertiesCheck | .gradle | Validates property values in gradle build files. |
GradleProvidedDependenciesCheck | .gradle | Validates the scope of dependencies in build gradle files. |
[GradleRequiredDependenciesCheck](checks/gradle_required_dependencies_check.markdown#gradlerequireddependenciescheck) | .gradle | Validates the dependencies in `/required-dependencies/required-dependencies/build.gradle`. |
GradleTestDependencyVersionCheck | .gradle | Checks the version for dependencies in gradle build files. |
[IncorrectFileLocationCheck](checks/incorrect_file_location_check.markdown#incorrectfilelocationcheck) | | Checks that `/src/*/java/` only contains `.java` files. |
[JSLodashDependencyCheck](checks/js_lodash_dependency_check.markdown#jslodashdependencycheck) | .js or .jsx | Finds incorrect use of `AUI._`. |
[JSONDeprecatedPackagesCheck](checks/json_deprecated_packages_check.markdown#jsondeprecatedpackagescheck) | .ipynb, .json or .npmbridgerc | Finds incorrect use of deprecated packages in `package.json` files. |
JSONPackageJSONBNDVersionCheck | .ipynb, .json or .npmbridgerc | Checks the version for dependencies in `package.json` files. |
JSONPackageJSONCheck | .ipynb, .json or .npmbridgerc | Checks content of `package.json` files. |
JSONPackageJSONDependencyVersionCheck | .ipynb, .json or .npmbridgerc | Checks the version for dependencies in `package.json` files. |
[JSONValidationCheck](checks/json_validation_check.markdown#jsonvalidationcheck) | .ipynb, .json or .npmbridgerc | Validates content of `.json` files. |
[JSPArrowFunctionCheck](checks/jsp_arrow_function_check.markdown#jsparrowfunctioncheck) | .jsp, .jspf, .tag, .tpl or .vm | Checks that there are no array functions. |
[JSPIllegalSyntaxCheck](checks/jsp_illegal_syntax_check.markdown#jspillegalsyntaxcheck) | .jsp, .jspf, .tag, .tpl or .vm | Finds incorrect use of `System.out.print`, `console.log` or `debugger.*` in `.jsp` files. |
[JSPIncludeCheck](checks/jsp_include_check.markdown#jspincludecheck) | .jsp, .jspf, .tag, .tpl or .vm | Validates values of `include` in `.jsp` files. |
JSPLanguageKeysCheck | .jsp, .jspf, .tag, .tpl or .vm | Finds missing language keys in `Language.properties`. |
JSPLanguageUtilCheck | .jsp, .jspf, .tag, .tpl or .vm | Finds cases where Locale is passed to `LanguageUtil.get` instead of `HttpServletRequest`. |
JSPLogFileNameCheck | .jsp, .jspf, .tag, .tpl or .vm | Validates the value that is passed to `LogFactoryUtil.getLog` in `.jsp`. |
JSPLogParametersCheck | .jsp, .jspf, .tag, .tpl or .vm | Validates the values of parameters passed to `_log.*` calls. |
[JSPMethodCallsCheck](checks/jsp_method_calls_check.markdown#jspmethodcallscheck) | .jsp, .jspf, .tag, .tpl or .vm | Checks that type `LiferayPortletResponse` is used to call `getNamespace()`. |
[JSPMissingTaglibsCheck](checks/jsp_missing_taglibs_check.markdown#jspmissingtaglibscheck) | .jsp, .jspf, .tag, .tpl or .vm | Checks for missing taglibs. |
[JSPSendRedirectCheck](checks/jsp_send_redirect_check.markdown#jspsendredirectcheck) | .jsp, .jspf, .tag, .tpl or .vm | Checks that there are no calls to `HttpServletResponse.sendRedirect` from `jsp` files. |
JSPSessionKeysCheck | .jsp, .jspf, .tag, .tpl or .vm | Checks that messages send to `SessionsErrors` or `SessionMessages` follow naming conventions. |
JSPTagAttributesCheck | .jsp, .jspf, .tag, .tpl or .vm | Performs several checks on tag attributes. |
[JavaAPISignatureCheck](checks/java_api_signature_check.markdown#javaapisignaturecheck) | .java | Checks that types `HttpServletRequest`, `HttpServletResponse`, `ThemeDisplay`, and `ServiceContext` are not used in API method signatures. |
JavaAbstractMethodCheck | .java | Finds incorrect `abstract` methods in `interface`. |
JavaAnnotationsCheck | .java | Performs several checks on annotations. |
[JavaAnonymousInnerClassCheck](checks/java_anonymous_inner_class_check.markdown#javaanonymousinnerclasscheck) | .java | Performs several checks on anonymous classes. |
JavaBooleanStatementCheck | .java | Performs several checks on variable declaration of type `Boolean`. |
JavaBooleanUsageCheck | .java | Finds incorrect use of passing boolean values in `setAttribute` calls. |
JavaCleanUpMethodSuperCleanUpCheck | .java | Checks that `cleanUp` method in `*Tag` class with `@Override` annotation calls the `cleanUp` method of the superclass. |
[JavaCleanUpMethodVariablesCheck](checks/java_clean_up_method_variables_check.markdown#javacleanupmethodvariablescheck) | .java | Checks that variables in `Tag` classes get cleaned up properly. |
[JavaCollatorUtilCheck](checks/java_collator_util_check.markdown#javacollatorutilcheck) | .java | Checks for correct use of `Collator`. |
JavaComponentAnnotationsCheck | .java | Performs several checks on classes with `@Component` annotation. |
[JavaConfigurationAdminCheck](checks/java_configuration_admin_check.markdown#javaconfigurationadmincheck) | .java | Checks for correct use of `location == ?` when calling `org.osgi.service.cm.ConfigurationAdmin#createFactoryConfiguration`. |
[JavaConfigurationCategoryCheck](checks/java_configuration_category_check.markdown#javaconfigurationcategorycheck) | .java | Checks that the value of `category` in `@ExtendedObjectClassDefinition` matches the `categoryKey` of the corresponding class in `configuration-admin-web`. |
JavaDeprecatedKernelClassesCheck | .java | Finds calls to deprecated classes `com.liferay.portal.kernel.util.CharPool` and `com.liferay.portal.kernel.util.StringPool`. |
[JavaFinderCacheCheck](checks/java_finder_cache_check.markdown#javafindercachecheck) | .java | Checks that the method `BasePersistenceImpl.fetchByPrimaryKey` is overridden, when using `FinderPath`. |
JavaFinderImplCustomSQLCheck | .java | Checks that hardcoded SQL values in `*FinderImpl` classes match the SQL in the `.xml` file in the `custom-sql` directory. |
JavaIgnoreAnnotationCheck | .java | Finds methods with `@Ignore` annotation in test classes. |
JavaIllegalImportsCheck | .java | Finds cases of incorrect use of certain classes. |
[JavaIndexableCheck](checks/java_indexable_check.markdown#javaindexablecheck) | .java | Checks that the type gets returned when using annotation `@Indexable`. |
JavaInterfaceCheck | .java | Checks that `interface` is not `static`. |
JavaInternalPackageCheck | .java | Performs several checks on class in `internal` package. |
JavaJSPDynamicIncludeCheck | .java | Performs several checks on `*JSPDynamicInclude` class. |
[JavaLocalSensitiveComparisonCheck](checks/java_local_sensitive_comparison_check.markdown#javalocalsensitivecomparisoncheck) | .java | Checks that `java.text.Collator` is used when comparing localized values. |
JavaLogClassNameCheck | .java | Checks the name of the class that is passed in `LogFactoryUtil.getLog`. |
[JavaLogLevelCheck](checks/java_log_level_check.markdown#javaloglevelcheck) | .java | Checks that the correct log messages are printed. |
JavaLogParametersCheck | .java | Validates the values of parameters passed to `_log.*` calls. |
JavaMapBuilderGenericsCheck | .java | Finds missing or unnecessary generics on `*MapBuilder.put` calls. |
[JavaMetaAnnotationsCheck](checks/java_meta_annotations_check.markdown#javametaannotationscheck) | .java | Checks for correct use of attributes `description` and `name` in annotation `@aQute.bnd.annotation.metatype.Meta`. |
JavaModifiedServiceMethodCheck | .java | Finds missing empty lines before `removedService` or `addingService` calls. |
[JavaModuleComponentCheck](checks/java_module_component_check.markdown#javamodulecomponentcheck) | .java | Checks for use of `@Component` in `-api` or `-spi` modules. |
[JavaModuleExposureCheck](checks/java_module_exposure_check.markdown#javamoduleexposurecheck) | .java | Checks for exposure of `SPI` types in `API`. |
JavaModuleIllegalImportsCheck | .java | Finds cases of incorrect use of certain classes in modules. |
JavaModuleInternalImportsCheck | .java | Finds cases where a module imports an `internal` class from another class. |
JavaModuleJavaxPortletInitParamTemplatePathCheck | .java | Validates the value of `javax.portlet.init-param.template-path`. |
JavaModuleServiceProxyFactoryCheck | .java | Finds cases of `ServiceProxyFactory.newServiceTrackedInstance`. |
JavaModuleServiceReferenceCheck | .java | Finds cases where `@BeanReference` annotation should be used instead of `@ServiceReference` annotation. |
[JavaModuleTestCheck](checks/java_module_test_check.markdown#javamoduletestcheck) | .java | Checks package names in tests. |
JavaOSGiReferenceCheck | .java | Performs several checks on classes with `@Component` annotation. |
[JavaPackagePathCheck](checks/java_package_path_check.markdown#javapackagepathcheck) | .java | Checks that the package name matches the file location. |
JavaParameterAnnotationsCheck | .java | Performs several checks on parameters with annotations. |
[JavaProcessCallableCheck](checks/java_process_callable_check.markdown#javaprocesscallablecheck) | .java | Checks that a class implementing `ProcessCallable` assigns a `serialVersionUID`. |
JavaProviderTypeAnnotationCheck | .java | Performs several checks on classes with `@ProviderType` annotation. |
JavaRedundantConstructorCheck | .java | Finds unnecessary empty constructor. |
JavaReleaseInfoCheck | .java | Validates information in `ReleaseInfo.java`. |
[JavaResultSetCheck](checks/java_result_set_check.markdown#javaresultsetcheck) | .java | Checks for correct use `java.sql.ResultSet.getInt(int)`. |
[JavaSeeAnnotationCheck](checks/java_see_annotation_check.markdown#javaseeannotationcheck) | .java | Checks for nested annotations inside `@see`. |
JavaServiceImplCheck | .java | Ensures that `afterPropertiesSet` and `destroy` methods in `*ServiceImpl` always call the method with the same name in the superclass. |
[JavaServiceUtilCheck](checks/java_service_util_check.markdown#javaserviceutilcheck) | .java | Checks that there are no calls to `*ServiceImpl` from a `*ServiceUtil` class. |
JavaStagedModelDataHandlerCheck | .java | Finds missing method `setMvccVersion` in class extending `BaseStagedModelDataHandler` in module that has `mvcc-enabled=true` in `service.xml`. |
JavaStaticBlockCheck | .java | Performs several checks on `static` blocks. |
JavaStaticMethodCheck | .java | Finds cases where methods are unncessarily declared static. |
JavaStaticVariableDependencyCheck | .java | Checks that static variables in the same class that depend on each other are correctly defined. |
[JavaStopWatchCheck](checks/java_stop_watch_check.markdown#javastopwatchcheck) | .java | Checks for potential NullPointerException when using `StopWatch`. |
JavaStringStartsWithSubstringCheck | .java | Checks for uses of `contains` followed by `substring`, which should be `startsWith` instead. |
JavaSystemEventAnnotationCheck | .java | Finds missing method `setDeletionSystemEventStagedModelTypes` in class with annotation @SystemEvent. |
JavaSystemExceptionCheck | .java | Finds unnecessary SystemExceptions. |
JavaTaglibMethodCheck | .java | Checks that a `*Tag` class has a `set*` and `get*` or `is*` method for each attribute. |
JavaTransactionBoundaryCheck | .java | Finds direct `add*` or `get*` calls in `*ServiceImpl` (those should use the `*service` global variable instead). |
[JavaUnsafeCastingCheck](checks/java_unsafe_casting_check.markdown#javaunsafecastingcheck) | .java | Checks for potential ClassCastException. |
[JavaUpgradeClassCheck](checks/java_upgrade_class_check.markdown#javaupgradeclasscheck) | .java | Performs several checks on Upgrade classes. |
JavaUpgradeConnectionCheck | .java | Finds cases where `DataAccess.getConnection` is used (instead of using the availabe global variable `connection`). |
JavaUpgradeVersionCheck | .java | Verifies that the correct upgrade versions are used in classes that implement `UpgradeStepRegistrator`. |
JavaVariableTypeCheck | .java | Performs several checks on the modifiers on variables. |
JavaVerifyUpgradeConnectionCheck | .java | Finds cases where `DataAccess.getConnection` is used (instead of using the availabe global variable `connection`). |
LFRBuildContentCheck | .lfrbuild-* | Finds `.lfrbuild*` files that are not empty. |
LPS42924Check | .java | Finds cases where `PortalUtil.getClassName*` (instead of calling `classNameLocalService` directly). |
LanguageKeysCheck | .java, .js or .jsx | Finds missing language keys in `Language.properties`. |
LocaleUtilCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | Finds cases where `com.liferay.portal.kernel.util.LocaleUtil` should be used (instead of `java.util.Locale`). |
[MissingDeprecatedCheck](https://checkstyle.sourceforge.io/config_annotation.html#MissingDeprecated) | .java | Verifies that the annotation @Deprecated and the Javadoc tag @deprecated are both present when either of them is present. |
MissingDiamondOperatorCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks for missing diamond operator for types that require diamond operator. |
MissingModifierCheck | .java | Verifies that a method or global variable has a modifier specified. |
MissingOverrideCheck | .java | Verifies that a method that overrides a method in a superclass has the @Override annotation. |
NewFileCheck | | Finds new files in directories that should not have added files. |
PackageinfoBNDExportPackageCheck | packageinfo | Finds legacy `packageinfo` files. |
PersistenceCallCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | Finds illegal persistence calls across component boundaries. |
[PersistenceUpdateCheck](checks/persistence_update_check.markdown#persistenceupdatecheck) | .java | Checks that there are no stale references in service code from persistence updates. |
PrimitiveWrapperInstantiationCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | Finds cases where `new Type` is used for primitive types (use `Type.valueOf` instead). |
PrincipalExceptionCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | Finds calls to `PrincipalException.class.getName()` (use `PrincipalException.getNestedClasses()` instead). |
PropertiesArchivedModulesCheck | .eslintignore, .prettierignore or .properties | Finds `test.batch.class.names.includes` property value pointing to archived modules in `test.properties`. |
PropertiesBuildIncludeDirsCheck | .eslintignore, .prettierignore or .properties | Verifies property value of `build.include.dirs` in `build.properties`. |
PropertiesImportedFilesContentCheck | .eslintignore, .prettierignore or .properties | Performs several checks on `imported-files.properties` file. |
[PropertiesLanguageKeysCheck](checks/properties_language_keys_check.markdown#propertieslanguagekeyscheck) | .eslintignore, .prettierignore or .properties | Checks that there is no HTML markup in language keys. |
PropertiesLiferayPluginPackageFileCheck | .eslintignore, .prettierignore or .properties | Performs several checks on `liferay-plugin-package.properties` file. |
PropertiesLiferayPluginPackageLiferayVersionsCheck | .eslintignore, .prettierignore or .properties | Validates the version in `liferay-plugin-package.properties` file. |
PropertiesPortalFileCheck | .eslintignore, .prettierignore or .properties | Performs several checks on `portal.properties` or `portal-*.properties` file. |
PropertiesPortletFileCheck | .eslintignore, .prettierignore or .properties | Performs several checks on `portlet.properties` file. |
PropertiesReleaseBuildCheck | .eslintignore, .prettierignore or .properties | Verifies that the information in `release.properties` matches the information in `ReleaseInfo.java`. |
PropertiesServiceKeysCheck | .eslintignore, .prettierignore or .properties | Finds usage of legacy properties in `service.properties`. |
PropertiesSourceFormatterContentCheck | .eslintignore, .prettierignore or .properties | Performs several checks on `source-formatter.properties` file. |
PropertiesSourceFormatterFileCheck | .eslintignore, .prettierignore or .properties | Performs several checks on `source-formatter.properties` file. |
PropertiesVerifyPropertiesCheck | .eslintignore, .prettierignore or .properties | Finds usage of legacy properties in `portal.properties` or `system.properties`. |
ReferenceAnnotationCheck | .java | Performs several checks on classes with @Reference annotation. |
[RequireThisCheck](https://checkstyle.sourceforge.io/config_coding.html#RequireThis) | .java | Checks that references to instance variables and methods of the present object are explicitly of the form 'this.varName' or 'this.methodName(args)' and that those references don't rely on the default behavior when 'this.' is absent. |
[ResourceBundleCheck](checks/resource_bundle_check.markdown#resourcebundlecheck) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that there are no calls to `java.util.ResourceBundle.getBundle`. |
[SQLLongNamesCheck](checks/sql_long_names_check.markdown#sqllongnamescheck) | .sql | Checks for table and column names that exceed 30 characters. |
SelfReferenceCheck | .java | Finds cases of unnecessary reference to its own class. |
[StaticBlockCheck](checks/static_block_check.markdown#staticblockcheck) | .java | Performs several checks on static blocks. |
TLDTypeCheck | .tld | Ensures the fully qualified name is used for types in `.tld` file. |
TransactionalTestRuleCheck | .java | Finds usage of `TransactionalTestRule` in `*StagedModelDataHandlerTest`. |
UnparameterizedClassCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | Finds `Class` instantation without generic type. |
UnwrappedVariableInfoCheck | .java | Finds cases where the variable should be wrapped into an inner class in order to defer array elements initialization. |
ValidatorIsNullCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | Ensures that only variable of type `Long`, `Serializable` or `String` is passed to method `com.liferay.portal.kernel.util.Validator.isNull`. |
XMLBuildFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on `build.xml`. |
XMLCDATACheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on `CDATA` inside `xml`. |
XMLCheckstyleFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on `checkstyle.xml` file. |
XMLLookAndFeelCompatibilityVersionCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Finds missing attribute `version` in `compatibility` element in `*--look-and-feel.xml` file. |
XMLPortletFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on `portlet.xml` file. |
XMLPoshiFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on poshi files. |
XMLProjectElementCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks the project name in `.pom` file. |
[XMLServiceEntityNameCheck](checks/xml_service_entity_name_check.markdown#xmlserviceentitynamecheck) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks that the `entity name` in `service.xml` does not equal the `package name`. |
XMLServiceFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on `service.xml` file. |
XMLServiceReferenceCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks for unused references in `service.xml` file. |
XMLSourcechecksFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on `sourcechecks.xml` file. |
XMLSuppressionsFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on `source-formatter-suppressions.xml` file. |
XMLTagAttributesCheck | .action, .function, .html, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on tag attributes. |
XMLWebFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on `web.xml` file. |