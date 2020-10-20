# All Checks

Check | Category | File Extensions | Description
----- | -------- | --------------- | -----------
[AnnotationUseStyleCheck](https://checkstyle.sourceforge.io/config_annotation.html#AnnotationUseStyle) | [Styling](styling_checks.markdown#styling-checks) | .java | Checks the style of elements in annotations. |
AnonymousClassCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for serialization issue when using anonymous class. |
AppendCheck | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks instances where literal Strings are appended. |
ArquillianCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for correct use of `com.liferay.arquillian.extension.junit.bridge.junit.Arquillian`. |
[ArrayCheck](checks/array_check.markdown#arraycheck) | [Performance](performance_checks.markdown#performance-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks if performance can be improved by using different mehods that can be used by collections. |
[ArrayTypeStyleCheck](https://checkstyle.sourceforge.io/config_misc.html#ArrayTypeStyle) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks the style of array type definitions. |
AssertEqualsCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Checks that additional information is provided when calling `Assert.assertEquals`. |
AttributeOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Checks that attributes in anonymous classes are ordered alphabetically. |
[AvoidNestedBlocksCheck](https://checkstyle.sourceforge.io/config_blocks.html#AvoidNestedBlocks) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Finds nested blocks (blocks that are used freely in the code). |
[AvoidStarImportCheck](https://checkstyle.sourceforge.io/config_imports.html#AvoidStarImport) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that there are no import statements that use the * notation. |
[BNDBundleActivatorCheck](checks/bnd_bundle_activator_check.markdown#bndbundleactivatorcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Validates property value for `Bundle-Activator`. |
[BNDBundleCheck](checks/bnd_bundle_check.markdown#bndbundlecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Validates `Liferay-Releng-*` properties. |
[BNDBundleInformationCheck](checks/bnd_bundle_information_check.markdown#bndbundleinformationcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Validates property values for `Bundle-Version`, `Bundle-Name` and `Bundle-SymbolicName`. |
BNDCapabilityCheck | [Styling](styling_checks.markdown#styling-checks) | .bnd | Sorts and applies logic to fix line breaks to property values for `Provide-Capability` and `Require-Capability`. |
[BNDDefinitionKeysCheck](checks/bnd_definition_keys_check.markdown#bnddefinitionkeyscheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Validates definition keys in `.bnd` files. |
[BNDDeprecatedAppBNDsCheck](checks/bnd_deprecated_app_bnds_check.markdown#bnddeprecatedappbndscheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .bnd | Checks for redundant `app.bnd` in deprecated or archived modules. |
[BNDDirectoryNameCheck](checks/bnd_directory_name_check.markdown#bnddirectorynamecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Checks if the directory names of the submodules match the parent module name. |
[BNDExportsCheck](checks/bnd_exports_check.markdown#bndexportscheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Checks that modules not ending with `-api`, `-client`, `-spi`, `-tablig`, `-test-util` do not export packages. |
[BNDImportsCheck](checks/bnd_imports_check.markdown#bndimportscheck) | [Styling](styling_checks.markdown#styling-checks) | .bnd | Sorts class names and checks for use of wildcards in property values for `-conditionalpackage`, `-exportcontents` and `Export-Package`. |
[BNDIncludeResourceCheck](checks/bnd_include_resource_check.markdown#bndincluderesourcecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Checks for unnesecarry including of `test-classes/integration`. |
BNDLiferayEnterpriseAppCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Checks for correct use of property `Liferay-Enterprise-App`. |
[BNDLiferayRelengCategoryCheck](checks/bnd_liferay_releng_category_check.markdown#bndliferayrelengcategorycheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Validates `Liferay-Releng-Category` properties |
BNDLineBreaksCheck | [Styling](styling_checks.markdown#styling-checks) | .bnd | Finds missing and unnecessary line breaks in `.bnd` files. |
[BNDMultipleAppBNDsCheck](checks/bnd_multiple_app_bnds_check.markdown#bndmultipleappbndscheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Checks for duplicate `app.bnd` (when both `/apps/` and `/apps/dxp/` contain the same module). |
[BNDRangeCheck](checks/bnd_range_check.markdown#bndrangecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Checks for use or range expressions. |
BNDRunInstructionsOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .bndrun | Sorts definition keys alphabetically. |
[BNDSchemaVersionCheck](checks/bnd_schema_version_check.markdown#bndschemaversioncheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Checks for incorrect use of property `Liferay-Require-SchemaVersion`. |
BNDStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .bnd | Applies rules to enforce consisteny in code style. |
[BNDSuiteCheck](checks/bnd_suite_check.markdown#bndsuitecheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .bnd | Checks that deprecated apps are moved to the `archived` folder. |
[BNDWebContextPathCheck](checks/bnd_web_context_path_check.markdown#bndwebcontextpathcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Checks if the property value for `Web-ContextPath` matches the module directory. |
BNDWhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | .bnd | Finds missing and unnecessary whitespace in `.bnd` files. |
CDNCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | | Checks the URL in `artifact.properties` files. |
CQLKeywordCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .cql | Checks that Cassandra keywords are upper case. |
CSSCommentsCheck | [Styling](styling_checks.markdown#styling-checks) | .css or .scss | Validates comments in `.css` files. |
CSSImportsCheck | [Styling](styling_checks.markdown#styling-checks) | .css or .scss | Sorts and groups imports in `.css` files. |
CSSPropertiesOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .css or .scss | Sorts properties in `.css` files. |
CamelCaseNameCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks variable names for correct use of `CamelCase`. |
[ChainingCheck](checks/chaining_check.markdown#chainingcheck) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that chaining is only applied on certain types and methods. |
[CodeownersFileLocationCheck](checks/codeowners_file_location_check.markdown#codeownersfilelocationcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | CODEOWNERS | Checks that `CODEOWNERS` files are located in `.github` directory. |
CodeownersWhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | CODEOWNERS | Finds missing and unnecessary whitespace in `CODEOWNERS` files. |
CompatClassImportsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that classes are imported from `compat` modules, when possible. |
ConcatCheck | [Performance](performance_checks.markdown#performance-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks for correct use of `StringBundler.concat`. |
ConfigDefinitionKeysCheck | [Styling](styling_checks.markdown#styling-checks) | .cfg or .config | Sorts definition keys in `.config` files. |
ConstantNameCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that variable names of constants follow correct naming rules. |
ConstructorMissingEmptyLineCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Checks for line breaks when assiging variables in constructor. |
ContractionsCheck | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Finds contractions in Strings (such as `can't` or `you're`). |
[CopyrightCheck](checks/copyright_check.markdown#copyrightcheck) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Validates `copyright` header. |
[CreationMenuBuilderCheck](checks/builder_check.markdown#buildercheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | Checks that `CreationMenuBuilder` is used when possible. |
[DefaultComesLastCheck](https://checkstyle.sourceforge.io/config_coding.html#DefaultComesLast) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that the `default` is after all the cases in a `switch` statement. |
DeprecatedUsageCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds calls to deprecated classes or methods. |
DockerfileEmptyLinesCheck | [Styling](styling_checks.markdown#styling-checks) | Dockerfile | Finds missing and unnecessary empty lines. |
DockerfileInstructionCheck | [Styling](styling_checks.markdown#styling-checks) | Dockerfile | Performs styling rules on instructions in `Dockerfile` files. |
EmptyCollectionCheck | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that there are no calls to `Collections.EMPTY_LIST`, `Collections.EMPTY_MAP` or `Collections.EMPTY_SET`. |
EnumConstantDividerCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Find unnecessary empty lines between enum constants. |
EnumConstantOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Checks the order of enum constants. |
[ExceptionCheck](checks/exception_check.markdown#exceptioncheck) | [Performance](performance_checks.markdown#performance-checks) | .java | Finds private methods that throw unnecessary exception. |
[ExceptionMessageCheck](checks/message_check.markdown#messagecheck) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Validates messages that are passed to exceptions. |
ExceptionVariableNameCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java | Validates variable names that have type `*Exception`. |
FTLEmptyLinesCheck | [Styling](styling_checks.markdown#styling-checks) | .ftl | Finds missing and unnecessary empty lines. |
[FTLIfStatementCheck](checks/if_statement_check.markdown#ifstatementcheck) | [Styling](styling_checks.markdown#styling-checks) | .ftl | Finds incorrect use of parentheses in statement. |
FTLImportsCheck | [Styling](styling_checks.markdown#styling-checks) | .ftl | Sorts and groups imports in `.ftl` files. |
FTLLiferayVariableOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .ftl | Sorts assign statement of `liferay_*` variables. |
FTLStringRelationalOperatorCheck | [Styling](styling_checks.markdown#styling-checks) | .ftl | Finds cases of `==` or `!=` where `stringUtil.equals`, `validator.isNotNull` or `validator.isNull` can be used instead. |
FTLStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .ftl | Applies rules to enforce consisteny in code style. |
FTLTagAttributesCheck | [Styling](styling_checks.markdown#styling-checks) | .ftl | Sorts and formats attributes values in tags. |
FTLTagCheck | [Styling](styling_checks.markdown#styling-checks) | .ftl | Finds cases where consecutive `#assign` can be combined. |
FTLWhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | .ftl | Finds missing and unnecessary whitespace in `.ftl` files. |
FactoryCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Finds cases where `*Factory` should be used when creating new instances of an object. |
FilterStringWhitespaceCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds missing and unnecessary whitespace in the value of the filter string in `ServiceTrackerFactory.open` or `WaiterUtil.waitForFilter`. |
[FrameworkBundleCheck](checks/framework_bundle_check.markdown#frameworkbundlecheck) | [Performance](performance_checks.markdown#performance-checks) | .java | Checks that `org.osgi.framework.Bundle.getHeaders()` is not used. |
FullyQualifiedNameCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | Finds cases where a Fully Qualified Name is used instead of importing a class. |
[GenericTypeCheck](checks/generic_type_check.markdown#generictypecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that generics are always specified to provide compile-time checking and removing the risk of `ClassCastException` during runtime. |
[GetterUtilCheck](checks/getter_util_check.markdown#getterutilcheck) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Finds cases where the default value is passed to `GetterUtil.get*` or `ParamUtil.get*`. |
GradleBlockOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .gradle | Sorts logic in gradle build files. |
GradleBodyCheck | [Styling](styling_checks.markdown#styling-checks) | .gradle | Applies rules to enforce consisteny in the body of gradle build files. |
[GradleDependenciesCheck](checks/gradle_dependencies_check.markdown#gradledependenciescheck) | [Performance](performance_checks.markdown#performance-checks) | .gradle | Checks that `petra` modules are not depending on other modules. |
[GradleDependencyArtifactsCheck](checks/gradle_dependency_artifacts_check.markdown#gradledependencyartifactscheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .gradle | Checks that value `default` is not used for attribute `version`. |
GradleDependencyConfigurationCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .gradle | Validates the scope of dependencies in build gradle files. |
GradleDependencyVersionCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .gradle | Checks the version for dependencies in gradle build files. |
GradleExportedPackageDependenciesCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .gradle | Validates dependencies in gradle build files. |
GradleImportsCheck | [Styling](styling_checks.markdown#styling-checks) | .gradle | Sorts and groups imports in `.gradle` files. |
GradleIndentationCheck | [Styling](styling_checks.markdown#styling-checks) | .gradle | Finds incorrect indentation in gradle build files. |
GradleJavaVersionCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .gradle | Checks values of properties `sourceCompatibility` and `targetCompatibility` in gradle build files. |
GradlePropertiesCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .gradle | Validates property values in gradle build files. |
GradleProvidedDependenciesCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .gradle | Validates the scope of dependencies in build gradle files. |
[GradleRequiredDependenciesCheck](checks/gradle_required_dependencies_check.markdown#gradlerequireddependenciescheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .gradle | Validates the dependencies in `/required-dependencies/required-dependencies/build.gradle`. |
GradleStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .gradle | Applies rules to enforce consisteny in code style. |
[GradleTaskCreationCheck](checks/gradle_task_creation_check.markdown#gradletaskcreationcheck) | [Styling](styling_checks.markdown#styling-checks) | .gradle | Checks that a task is declared on a separate line before the closure. |
GradleTestDependencyVersionCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .gradle | Checks the version for dependencies in gradle build files. |
HTMLEmptyLinesCheck | [Styling](styling_checks.markdown#styling-checks) | .html | Finds missing and unnecessary empty lines. |
HTMLWhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | .html | Finds missing and unnecessary whitespace in `.html` files. |
[IncorrectFileLocationCheck](checks/incorrect_file_location_check.markdown#incorrectfilelocationcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | | Checks that `/src/*/java/` only contains `.java` files. |
InstanceofOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Check the order of `instanceof` calls. |
[ItemListBuilderCheck](checks/builder_check.markdown#buildercheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | Checks that `DropdownItemListBuilder`, `LabelItemListBuilder` or `NavigationItemListBuilder` is used when possible. |
JSLodashDependencyCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .js or .jsx | Finds incorrect use of `AUI._`. |
[JSONDeprecatedPackagesCheck](checks/json_deprecated_packages_check.markdown#jsondeprecatedpackagescheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .json or .npmbridgerc | Finds incorrect use of deprecated packages in `package.json` files. |
JSONNamingCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks if variable names follow naming conventions. |
JSONPackageJSONBNDVersionCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .json or .npmbridgerc | Checks the version for dependencies in `package.json` files. |
JSONPackageJSONCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .json or .npmbridgerc | Checks content of `package.json` files. |
JSONPackageJSONDependencyVersionCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .json or .npmbridgerc | Checks the version for dependencies in `package.json` files. |
JSONStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .json or .npmbridgerc | Applies rules to enforce consisteny in code style. |
[JSONUtilCheck](checks/json_util_check.markdown#jsonutilcheck) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks for utilization of class `JSONUtil`. |
[JSONValidationCheck](checks/json_validation_check.markdown#jsonvalidationcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .json or .npmbridgerc | Validates content of `.json` files. |
[JSPArrowFunctionCheck](checks/jsp_arrow_function_check.markdown#jsparrowfunctioncheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .jsp, .jspf, .tag, .tpl or .vm | Checks that there are no array functions. |
[JSPDefineObjectsCheck](checks/jsp_define_objects_check.markdown#jspdefineobjectscheck) | [Performance](performance_checks.markdown#performance-checks) | .jsp, .jspf, .tag, .tpl or .vm | Checks for unnesecarry duplication of code that already exists in `defineObjects`. |
JSPEmptyLinesCheck | [Styling](styling_checks.markdown#styling-checks) | .jsp, .jspf, .tag, .tpl or .vm | Finds missing and unnecessary empty lines. |
JSPExceptionOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .jsp, .jspf, .tag, .tpl or .vm | Checks the order of exceptions in `.jsp` files. |
JSPFileNameCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .jsp, .jspf, .tag, .tpl or .vm | Checks if the file name of `.jsp` or `.jspf` follows the naming conventions. |
[JSPFunctionNameCheck](checks/jsp_function_name_check.markdown#jspfunctionnamecheck) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .jsp, .jspf, .tag, .tpl or .vm | Check if the names of functions in `.jsp` files follow naming conventions. |
[JSPIllegalSyntaxCheck](checks/jsp_illegal_syntax_check.markdown#jspillegalsyntaxcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .jsp, .jspf, .tag, .tpl or .vm | Finds incorrect use of `System.out.print`, `console.log` or `debugger.*` in `.jsp` files. |
[JSPImportsCheck](checks/jsp_imports_check.markdown#jspimportscheck) | [Styling](styling_checks.markdown#styling-checks) | .jsp, .jspf, .tag, .tpl or .vm | Sorts and groups imports in `.jsp` files. |
[JSPIncludeCheck](checks/jsp_include_check.markdown#jspincludecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .jsp, .jspf, .tag, .tpl or .vm | Validates values of `include` in `.jsp` files. |
JSPIndentationCheck | [Styling](styling_checks.markdown#styling-checks) | .jsp, .jspf, .tag, .tpl or .vm | Finds incorrect indentation in `.jsp` files. |
JSPInlineVariableCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | |
JSPJavaParserCheck | [Styling](styling_checks.markdown#styling-checks) | .jsp, .jspf, .tag, .tpl or .vm | Performs JavaParser on `.java` files. |
JSPLanguageKeysCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .jsp, .jspf, .tag, .tpl or .vm | Finds missing language keys in `Language.properties`. |
JSPLanguageUtilCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .jsp, .jspf, .tag, .tpl or .vm | Finds cases where Locale is passed to `LanguageUtil.get` instead of `HttpServletRequest`. |
JSPLineBreakCheck | [Styling](styling_checks.markdown#styling-checks) | .jsp, .jspf, .tag, .tpl or .vm | Finds missing and unnecessary line breaks in `.jsp` lines. |
JSPLogFileNameCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .jsp, .jspf, .tag, .tpl or .vm | Validates the value that is passed to `LogFactoryUtil.getLog` in `.jsp`. |
JSPLogParametersCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .jsp, .jspf, .tag, .tpl or .vm | Validates the values of parameters passed to `_log.*` calls. |
[JSPMethodCallsCheck](checks/jsp_method_calls_check.markdown#jspmethodcallscheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .jsp, .jspf, .tag, .tpl or .vm | Checks that type `LiferayPortletResponse` is used to call `getNamespace()`. |
[JSPMissingTaglibsCheck](checks/jsp_missing_taglibs_check.markdown#jspmissingtaglibscheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .jsp, .jspf, .tag, .tpl or .vm | Checks for missing taglibs. |
[JSPModuleIllegalImportsCheck](checks/jsp_module_illegal_imports_check.markdown#jspmoduleillegalimportscheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | Finds incorrect use of `com.liferay.registry.Registry` or `com.liferay.util.ContentUtil`. |
[JSPParenthesesCheck](checks/if_statement_check.markdown#ifstatementcheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | Finds incorrect use of parentheses in statement. |
JSPRedirectBackURLCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | Validates values of variable `redirect`. |
[JSPSendRedirectCheck](checks/jsp_send_redirect_check.markdown#jspsendredirectcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .jsp, .jspf, .tag, .tpl or .vm | Checks that there are no calls to `HttpServletResponse.sendRedirect` from `jsp` files. |
[JSPServiceUtilCheck](checks/jsp_service_util_check.markdown#jspserviceutilcheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | Finds incorrect use of `*ServiceUtil` in `.jsp` files in modules. |
JSPSessionKeysCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .jsp, .jspf, .tag, .tpl or .vm | Checks that messages send to `SessionsErrors` or `SessionMessages` follow naming conventions. |
[JSPStringMethodsCheck](checks/string_methods_check.markdown#stringmethodscheck) | [Performance](performance_checks.markdown#performance-checks) | .jsp, .jspf, .tag, .tpl or .vm | Finds cases of inefficient String operations. |
JSPStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .jsp, .jspf, .tag, .tpl or .vm | Applies rules to enforce consisteny in code style. |
JSPTagAttributesCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .jsp, .jspf, .tag, .tpl or .vm | Performs several checks on tag attributes. |
[JSPTaglibVariableCheck](checks/jsp_taglib_variable_check.markdown#jsptaglibvariablecheck) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .jsp, .jspf, .tag, .tpl or .vm | Checks if variable names follow naming conventions. |
JSPUnusedJSPF | [Performance](performance_checks.markdown#performance-checks) | .jsp, .jspf, .tag, .tpl or .vm | Finds `.jspf` files that are not used. |
JSPUnusedTermsCheck | [Performance](performance_checks.markdown#performance-checks) | .jsp, .jspf, .tag, .tpl or .vm | Finds taglibs, variables and imports that are unused. |
JSPWhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | .jsp, .jspf, .tag, .tpl or .vm | Finds missing and unnecessary whitespace in `.jsp` files. |
JSPXSSVulnerabilitiesCheck | [Security](security_checks.markdown#security-checks) | .jsp, .jspf, .tag, .tpl or .vm | Finds xss vulnerabilities. |
JSStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .js or .jsx | Applies rules to enforce consisteny in code style. |
JSWhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | .js or .jsx | Finds missing and unnecessary whitespace in `.js` files. |
Java2HTMLCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | Finds incorrect use of `.java.html` in `.jsp` files. |
[JavaAPISignatureCheck](checks/java_api_signature_check.markdown#javaapisignaturecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that types `HttpServletRequest`, `HttpServletResponse`, `ThemeDisplay`, and `ServiceContext` are not used in API method signatures. |
JavaAbstractMethodCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds incorrect `abstract` methods in `interface`. |
JavaAggregateTestRuleParameterOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Checks the order of parameters in `new AggregateTestRule` calls. |
JavaAnnotationDefaultAttributeCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Finds cases where the default value is passed to annotations in package `*.bnd.annotations` or `*.bind.annotations`. |
JavaAnnotationsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Performs several checks on annotations. |
[JavaAnonymousInnerClassCheck](checks/java_anonymous_inner_class_check.markdown#javaanonymousinnerclasscheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Performs several checks on anonymous classes. |
JavaAssertEqualsCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Validates `Assert.assertEquals` calls. |
JavaBooleanStatementCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Performs several checks on variable declaration of type `Boolean`. |
JavaBooleanUsageCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds incorrect use of passing boolean values in `setAttribute` calls. |
JavaClassNameCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java | Checks if class names follow naming conventions. |
JavaCleanUpMethodSuperCleanUpCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that `cleanUp` method in `*Tag` class with `@Override` annotation calls the `cleanUp` method of the superclass. |
[JavaCleanUpMethodVariablesCheck](checks/java_clean_up_method_variables_check.markdown#javacleanupmethodvariablescheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that variables in `Tag` classes get cleaned up properly. |
[JavaCollatorUtilCheck](checks/java_collator_util_check.markdown#javacollatorutilcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for correct use of `Collator`. |
[JavaComponentActivateCheck](checks/java_component_activate_check.markdown#javacomponentactivatecheck) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java | Checks if methods with annotation `@Activate` or `@Deactivate` follow naming conventions. |
JavaComponentAnnotationsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Performs several checks on classes with `@Component` annotation. |
[JavaConfigurationAdminCheck](checks/java_configuration_admin_check.markdown#javaconfigurationadmincheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for correct use of `location == ?` when calling `org.osgi.service.cm.ConfigurationAdmin#createFactoryConfiguration`. |
[JavaConfigurationCategoryCheck](checks/java_configuration_category_check.markdown#javaconfigurationcategorycheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that the value of `category` in `@ExtendedObjectClassDefinition` matches the `categoryKey` of the corresponding class in `configuration-admin-web`. |
[JavaConstructorParametersCheck](checks/java_constructor_parameters_check.markdown#javaconstructorparameterscheck) | [Styling](styling_checks.markdown#styling-checks) | .java | Checks that the order of variable assignments matches the order of the parameters in the constructor signature. |
JavaConstructorSuperCallCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Finds unnecessary call to no-argument constructor of the superclass. |
JavaDataAccessConnectionCheck | [Performance](performance_checks.markdown#performance-checks) | .java | Finds calls to `DataAccess.getConnection` (use `DataAccess.getUpgradeOptimizedConnection` instead). |
JavaDeprecatedJavadocCheck | [Javadoc](javadoc_checks.markdown#javadoc-checks) | .java | Checks if the `@deprecated` javadoc is pointing to the correct version. |
JavaDeprecatedKernelClassesCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds calls to deprecated classes `com.liferay.portal.kernel.util.CharPool` and `com.liferay.portal.kernel.util.StringPool`. |
JavaDeserializationSecurityCheck | [Security](security_checks.markdown#security-checks) | .java | Finds Java serialization vulnerabilities. |
JavaDiamondOperatorCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | Finds cases where Diamond Operator is not used. |
JavaDuplicateVariableCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | Finds variables where a variable with the same name already exists in an extended class. |
[JavaElseStatementCheck](checks/java_else_statement_check.markdown#javaelsestatementcheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | Finds unnecessary `else` statements (when the `if` statement ends with a `return` statement). |
JavaEmptyLineAfterSuperCallCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | Finds missing emptly line after a `super` call. |
JavaEmptyLinesCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Finds missing and unnecessary empty lines. |
JavaExceptionCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java | Checks that variable names of exceptions in `catch` statements follow naming conventions. |
[JavaFinderCacheCheck](checks/java_finder_cache_check.markdown#javafindercachecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that the method `BasePersistenceImpl.fetchByPrimaryKey` is overridden, when using `FinderPath`. |
JavaFinderImplCustomSQLCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that hardcoded SQL values in `*FinderImpl` classes match the SQL in the `.xml` file in the `custom-sql` directory. |
[JavaForLoopCheck](checks/java_for_loop_check.markdown#javaforloopcheck) | [Styling](styling_checks.markdown#styling-checks) | .java | Checks if a Enhanced For Loop can be used instead of a Simple For Loop. |
[JavaHelperUtilCheck](checks/java_helper_util_check.markdown#javahelperutilcheck) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java | Finds incorrect use of `*Helper` or `*Util` classes. |
JavaHibernateSQLCheck | [Performance](performance_checks.markdown#performance-checks) | .java | Finds calls to `com.liferay.portal.kernel.dao.orm.Session.createSQLQuery` (use `Session.createSynchronizedSQLQuery` instead). |
JavaIOExceptionCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Validates use of `IOException`. |
JavaIgnoreAnnotationCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds methods with `@Ignore` annotation in test classes. |
JavaIllegalImportsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds cases of incorrect use of certain classes. |
JavaImportsCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Sorts and groups imports in `.java` files. |
[JavaIndexableCheck](checks/java_indexable_check.markdown#javaindexablecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that the type gets returned when using annotation `@Indexable`. |
JavaInnerClassImportsCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Finds cases where inner classes are imported. |
JavaInterfaceCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that `interface` is not `static`. |
JavaInternalPackageCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Performs several checks on class in `internal` package. |
JavaJSPDynamicIncludeCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Performs several checks on `*JSPDynamicInclude` class. |
[JavaLocalSensitiveComparisonCheck](checks/java_local_sensitive_comparison_check.markdown#javalocalsensitivecomparisoncheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that `java.text.Collator` is used when comparing localized values. |
JavaLogClassNameCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks the name of the class that is passed in `LogFactoryUtil.getLog`. |
[JavaLogLevelCheck](checks/java_log_level_check.markdown#javaloglevelcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that the correct log messages are printed. |
JavaLogParametersCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Validates the values of parameters passed to `_log.*` calls. |
JavaLongLinesCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Finds lines that are longer than the specified maximum line length. |
JavaMapBuilderGenericsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds missing or unnecessary generics on `*MapBuilder.put` calls. |
[JavaMetaAnnotationsCheck](checks/java_meta_annotations_check.markdown#javametaannotationscheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for correct use of attributes `description` and `name` in annotation `@aQute.bnd.annotation.metatype.Meta`. |
JavaModifiedServiceMethodCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds missing empty lines before `removedService` or `addingService` calls. |
[JavaModuleComponentCheck](checks/java_module_component_check.markdown#javamodulecomponentcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for use of `@Component` in `-api` or `-spi` modules. |
[JavaModuleExposureCheck](checks/java_module_exposure_check.markdown#javamoduleexposurecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for exposure of `SPI` types in `API`. |
JavaModuleIllegalImportsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds cases of incorrect use of certain classes in modules. |
JavaModuleInternalImportsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds cases where a module imports an `internal` class from another class. |
JavaModuleJavaxPortletInitParamTemplatePathCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Validates the value of `javax.portlet.init-param.template-path`. |
JavaModuleServiceProxyFactoryCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds cases of `ServiceProxyFactory.newServiceTrackedInstance`. |
JavaModuleServiceReferenceCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds cases where `@BeanReference` annotation should be used instead of `@ServiceReference` annotation. |
[JavaModuleTestCheck](checks/java_module_test_check.markdown#javamoduletestcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks package names in tests. |
[JavaMultiPlusConcatCheck](checks/java_multi_plus_concat_check.markdown#javamultiplusconcatcheck) | [Performance](performance_checks.markdown#performance-checks) | .java | Checks that we do not concatenate more than 3 String objects. |
JavaOSGiReferenceCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Performs several checks on classes with `@Component` annotation. |
[JavaPackagePathCheck](checks/java_package_path_check.markdown#javapackagepathcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that the package name matches the file location. |
JavaParameterAnnotationsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Performs several checks on parameters with annotations. |
[JavaProcessCallableCheck](checks/java_process_callable_check.markdown#javaprocesscallablecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that a class implementing `ProcessCallable` assigns a `serialVersionUID`. |
JavaProviderTypeAnnotationCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Performs several checks on classes with `@ProviderType` annotation. |
JavaRedundantConstructorCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaReleaseInfoCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Validates information in `ReleaseInfo.java`. |
[JavaResultSetCheck](checks/java_result_set_check.markdown#javaresultsetcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for correct use `java.sql.ResultSet.getInt(int)`. |
JavaReturnStatementCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Finds unnecessary `else` statement (when `if` and `else` statement both end with `return` statement). |
[JavaSeeAnnotationCheck](checks/java_see_annotation_check.markdown#javaseeannotationcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for nested annotations inside `@see`. |
JavaServiceImplCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Ensures that `afterPropertiesSet` and `destroy` methods in `*ServiceImpl` always call the method with the same name in the superclass. |
JavaServiceObjectCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Checks for correct use of `*.is*` instead of `*.get*` when calling methods generated by ServiceBuilder. |
[JavaServiceTrackerFactoryCheck](checks/java_service_tracker_factory_check.markdown#javaservicetrackerfactorycheck) | [Performance](performance_checks.markdown#performance-checks) | .java | Checks that there are no calls to deprecatred method `ServiceTrackerFactory.open(java.lang.Class)`. |
[JavaServiceUtilCheck](checks/java_service_util_check.markdown#javaserviceutilcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that there are no calls to `*ServiceImpl` from a `*ServiceUtil` class. |
JavaSessionCheck | [Performance](performance_checks.markdown#performance-checks) | .java | Finds unnecessary calls to `Session.flush()` (calls that are followed by `Session.clear()`). |
[JavaSignatureParametersCheck](checks/java_signature_parameters_check.markdown#javasignatureparameterscheck) | [Styling](styling_checks.markdown#styling-checks) | .java | Checks the order of parameters. |
JavaSourceFormatterDocumentationCheck | [Documentation](documentation_checks.markdown#documentation-checks) | .java | Finds SourceFormatter checks that have no documentation. |
JavaStagedModelDataHandlerCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaStaticBlockCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Performs several checks on `static` blocks. |
[JavaStaticImportsCheck](checks/java_static_imports_check.markdown#javastaticimportscheck) | [Styling](styling_checks.markdown#styling-checks) | .java | Checks that there are no static imports. |
JavaStaticMethodCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaStaticVariableDependencyCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[JavaStopWatchCheck](checks/java_stop_watch_check.markdown#javastopwatchcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for potential NullPointerException when using `StopWatch`. |
[JavaStringBundlerConcatCheck](checks/java_string_bundler_concat_check.markdown#javastringbundlerconcatcheck) | [Performance](performance_checks.markdown#performance-checks) | .java | Finds calls to `StringBundler.concat` with less than 3 parameters. |
JavaStringBundlerInitialCapacityCheck | [Performance](performance_checks.markdown#performance-checks) | .java | Checks the initial capacity of new instances of `StringBundler`. |
JavaStringStartsWithSubstringCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for uses of `contains` followed by `substring`, which should be `startsWith` instead. |
JavaStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Applies rules to enforce consisteny in code style. |
[JavaSwitchCheck](checks/java_switch_check.markdown#javaswitchcheck) | [Styling](styling_checks.markdown#styling-checks) | .java | Checks that `if/else` statement is used instead of `switch` statement. |
JavaSystemEventAnnotationCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaSystemExceptionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaTaglibMethodCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaTermDividersCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Finds missing or unnecessary empty lines between javaterms. |
JavaTermOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Checks the order of javaterms. |
JavaTermStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Applies rules to enforce consisteny in code style. |
[JavaTestMethodAnnotationsCheck](checks/java_test_method_annotations_check.markdown#javatestmethodannotationscheck) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java | Checks if methods with test annotations follow the naming conventions. |
JavaTransactionBoundaryCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[JavaUnsafeCastingCheck](checks/java_unsafe_casting_check.markdown#javaunsafecastingcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for potential ClassCastException. |
JavaUnusedSourceFormatterChecksCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | Finds `*Check` classes that are not configured. |
[JavaUpgradeClassCheck](checks/java_upgrade_class_check.markdown#javaupgradeclasscheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Performs several checks on Upgrade classes. |
JavaUpgradeConnectionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaUpgradeVersionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaVariableTypeCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaVerifyUpgradeConnectionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaXMLSecurityCheck | [Security](security_checks.markdown#security-checks) | .java | Finds possible XXE or Quadratic Blowup security vulnerabilities. |
JavadocCheck | [Javadoc](javadoc_checks.markdown#javadoc-checks) | .java | Performs several checks on javadoc. |
[JavadocStyleCheck](https://checkstyle.sourceforge.io/config_javadoc.html#JavadocStyle) | [Javadoc](javadoc_checks.markdown#javadoc-checks) | .java | Validates Javadoc comments to help ensure they are well formed. |
LFRBuildContentCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .lfrbuild-* | |
LFRBuildReadmeCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .lfrbuild-* | |
LPS42924Check | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[LambdaCheck](checks/lambda_check.markdown#lambdacheck) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that `lambda` statements are as simple as possible. |
LanguageKeysCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java, .js or .jsx | Finds missing language keys in `Language.properties`. |
[ListUtilCheck](checks/list_util_check.markdown#listutilcheck) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks for utilization of class `ListUtil`. |
LiteralStringEqualsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[LocalFinalVariableNameCheck](https://checkstyle.sourceforge.io/config_naming.html#LocalFinalVariableName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that local final variable names conform to a specified pattern. |
LocalPatternCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[LocalVariableNameCheck](https://checkstyle.sourceforge.io/config_naming.html#LocalVariableName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that local, non-final variable names conform to a specified pattern. |
LocaleUtilCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[LogMessageCheck](checks/message_check.markdown#messagecheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[MapBuilderCheck](checks/builder_check.markdown#buildercheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that `ConcurrentHashMapBuilder`, `HashMapBuilder`, `LinkedHashMapBuilder` or `TreeMapBuilder` is used when possible. |
[MapIterationCheck](checks/map_iteration_check.markdown#mapiterationcheck) | [Performance](performance_checks.markdown#performance-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that there are no unnecessary map iterations. |
MarkdownFileExtensionCheck | [Styling](styling_checks.markdown#styling-checks) | .markdown or .md | Finds `markdown` files with `.md` extension (use `.markdown`). |
MarkdownSourceFormatterDocumentationCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .markdown or .md | |
MarkdownSourceFormatterReadmeCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .markdown or .md | |
MarkdownStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .markdown or .md | Applies rules to enforce consisteny in code style. |
MarkdownWhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | .markdown or .md | Finds missing and unnecessary whitespace in `.markdown` files. |
[MemberNameCheck](https://checkstyle.sourceforge.io/config_naming.html#MemberName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java, .java, .jsp, .jsp, .jspf, .jspf, .tag, .tag, .tpl, .tpl, .vm or .vm | Checks that instance variable names conform to a specified pattern. |
MethodCallsOrderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[MethodNameCheck](https://checkstyle.sourceforge.io/config_naming.html#MethodName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java, .java, .jsp, .jsp, .jspf, .jspf, .tag, .tag, .tpl, .tpl, .vm or .vm | Checks that method names conform to a specified pattern. |
MethodNamingCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that method names follow naming conventions. |
[MethodParamPadCheck](https://checkstyle.sourceforge.io/config_whitespace.html#MethodParamPad) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks the padding between the identifier of a method definition, constructor definition, method call, or constructor invocation; and the left parenthesis of the parameter list. |
MissingAuthorCheck | [Javadoc](javadoc_checks.markdown#javadoc-checks) | .java | Finds classes that have no `@author` specified. |
[MissingDeprecatedCheck](https://checkstyle.sourceforge.io/config_annotation.html#MissingDeprecated) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Verifies that the annotation @Deprecated and the Javadoc tag @deprecated are both present when either of them is present. |
MissingDeprecatedJavadocCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
MissingDiamondOperatorCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[MissingEmptyLineCheck](checks/missing_empty_line_check.markdown#missingemptylinecheck) | [Styling](styling_checks.markdown#styling-checks) | .java | Checks for missing line breaks around variable declarations. |
MissingModifierCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
MissingOverrideCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
MissingParenthesesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[ModifierOrderCheck](https://checkstyle.sourceforge.io/config_modifier.html#ModifierOrder) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that the order of modifiers conforms to the suggestions in the Java Language specification, § 8.1.1, 8.3.1, 8.4.3 and 9.4. |
[MultipleVariableDeclarationsCheck](https://checkstyle.sourceforge.io/config_coding.html#MultipleVariableDeclarations) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that each variable declaration is in its own statement and on its own line. |
NestedIfStatementCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
NewFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | | |
[NoLineWrapCheck](https://checkstyle.sourceforge.io/config_whitespace.html#NoLineWrap) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that chosen statements are not line-wrapped. |
[NoWhitespaceAfterCheck](https://checkstyle.sourceforge.io/config_whitespace.html#NoWhitespaceAfter) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that there is no whitespace after a token. |
[NoWhitespaceBeforeCheck](https://checkstyle.sourceforge.io/config_whitespace.html#NoWhitespaceBefore) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that there is no whitespace before a token. |
NonbreakingSpaceCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | | |
NotRequireThisCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
NumberSuffixCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[OneStatementPerLineCheck](https://checkstyle.sourceforge.io/config_coding.html#OneStatementPerLine) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that there is only one statement per line. |
OperatorOperandCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
OperatorOrderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[OperatorWrapCheck](https://checkstyle.sourceforge.io/config_whitespace.html#OperatorWrap) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks the policy on how to wrap lines on operators. |
[PackageNameCheck](https://checkstyle.sourceforge.io/config_naming.html#PackageName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java | Checks that package names conform to a specified pattern. |
PackageinfoBNDExportPackageCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | packageinfo | |
[ParameterNameCheck](https://checkstyle.sourceforge.io/config_naming.html#ParameterName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that method parameter names conform to a specified pattern. |
ParsePrimitiveTypeCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
PersistenceCallCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[PersistenceUpdateCheck](checks/persistence_update_check.markdown#persistenceupdatecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that there are no stale references in service code from persistence updates. |
PlusStatementCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
PoshiAnnotationsOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .function, .macro or .testcase | Checks the order of annotations. |
PoshiCommandsOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .function, .macro or .testcase | Checks the order of poshi commands. |
PoshiEmptyLinesCheck | [Styling](styling_checks.markdown#styling-checks) | .function, .macro or .testcase | Finds missing and unnecessary empty lines. |
PoshiIndentationCheck | [Styling](styling_checks.markdown#styling-checks) | .function, .macro or .testcase | Finds incorrect indentation in poshi files. |
PoshiParametersOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .function, .macro or .testcase | Checks the order of parameters in `.function` and `.macro` files. |
PoshiStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .function, .macro or .testcase | Applies rules to enforce consisteny in code style. |
PoshiWhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | .function, .macro or .testcase | Finds missing and unnecessary whitespace in poshi files. |
PrimitiveWrapperInstantiationCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
PrincipalExceptionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
PropertiesArchivedModulesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesBuildIncludeDirsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesCommentsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesDefinitionKeysCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesDependenciesFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesEmptyLinesCheck | [Styling](styling_checks.markdown#styling-checks) | .prettierignore or .properties | Finds missing and unnecessary empty lines. |
PropertiesImportedFilesContentCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
[PropertiesLanguageKeysCheck](checks/properties_language_keys_check.markdown#propertieslanguagekeyscheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .prettierignore or .properties | Checks that there is no HTML markup in language keys. |
PropertiesLanguageKeysOrderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesLiferayPluginPackageFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesLiferayPluginPackageLiferayVersionsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesLongLinesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesMultiLineValuesOrderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesPortalEnvironmentVariablesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesPortalFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesPortletFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesReleaseBuildCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesServiceKeysCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesSourceFormatterContentCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesSourceFormatterFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .prettierignore or .properties | Applies rules to enforce consisteny in code style. |
PropertiesVerifyPropertiesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesWhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | .prettierignore or .properties | Finds missing and unnecessary whitespace in `.properties` files. |
RedundantBranchingStatementCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
ReferenceAnnotationCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[RequireThisCheck](https://checkstyle.sourceforge.io/config_coding.html#RequireThis) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that references to instance variables and methods of the present object are explicitly of the form 'this.varName' or 'this.methodName(args)' and that those references don't rely on the default behavior when 'this.' is absent. |
[ResourceBundleCheck](checks/resource_bundle_check.markdown#resourcebundlecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that there are no calls to `java.util.ResourceBundle.getBundle`. |
SQLEmptyLinesCheck | [Styling](styling_checks.markdown#styling-checks) | .sql | Finds missing and unnecessary empty lines. |
[SQLLongNamesCheck](checks/sql_long_names_check.markdown#sqllongnamescheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .sql | Checks for table and column names that exceed 30 characters. |
SQLStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .sql | Applies rules to enforce consisteny in code style. |
SelfReferenceCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
SemiColonCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
SessionKeysCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java | Checks that messages send to `SessionsErrors` or `SessionMessages` follow naming conventions. |
SingleStatementClauseCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
SizeIsZeroCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
SlantedQuotesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | | |
SoyEmptyLinesCheck | [Styling](styling_checks.markdown#styling-checks) | .soy | Finds missing and unnecessary empty lines. |
[StaticBlockCheck](checks/static_block_check.markdown#staticblockcheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[StaticVariableNameCheck](https://checkstyle.sourceforge.io/config_naming.html#StaticVariableName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java, .java, .jsp, .jsp, .jspf, .jspf, .tag, .tag, .tpl, .tpl, .vm or .vm | Checks that static, non-final variable names conform to a specified pattern. |
StringBundlerNamingCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks for consistent naming on variables of type 'StringBundler'. |
StringCastCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[StringLiteralEqualityCheck](https://checkstyle.sourceforge.io/config_coding.html#StringLiteralEquality) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that string literals are not used with == or !=. |
[StringMethodsCheck](checks/string_methods_check.markdown#stringmethodscheck) | [Performance](performance_checks.markdown#performance-checks) | .java | Checks if performance can be improved by using different String operation methods. |
SubstringCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
TLDElementOrderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .tld | |
TLDTypeCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .tld | |
TernaryOperatorCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
TestClassCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
ThreadLocalUtilCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
ThreadNameCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
TransactionalTestRuleCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
TryWithResourcesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[TypeNameCheck](https://checkstyle.sourceforge.io/config_naming.html#TypeName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java | Checks that type names conform to a specified pattern. |
[UnnecessaryAssignCheck](checks/unnecessary_assign_check.markdown#unnecessaryassigncheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[UnnecessaryParenthesesCheck](https://checkstyle.sourceforge.io/config_coding.html#UnnecessaryParentheses) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks if unnecessary parentheses are used in a statement or expression. |
UnnecessaryTypeCastCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[UnnecessaryVariableDeclarationCheck](checks/unnecessary_variable_declaration_check.markdown#unnecessaryvariabledeclarationcheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
UnparameterizedClassCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
UnprocessedExceptionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
UnusedMethodCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
UnusedParameterCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
UnusedVariableCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
UnwrappedVariableInfoCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[ValidatorEqualsCheck](checks/validator_equals_check.markdown#validatorequalscheck) | [Performance](performance_checks.markdown#performance-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that there are no calls to `Validator.equals(Object, Object)`. |
ValidatorIsNullCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
VariableDeclarationAsUsedCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
VariableNameCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[WhitespaceAfterCheck](https://checkstyle.sourceforge.io/config_whitespace.html#WhitespaceAfter) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that a token is followed by whitespace, with the exception that it does not check for whitespace after the semicolon of an empty for iterator. |
WhitespaceAfterParameterAnnotationCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[WhitespaceAroundCheck](https://checkstyle.sourceforge.io/config_whitespace.html#WhitespaceAround) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that a token is surrounded by whitespace. |
WhitespaceAroundGenericsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
WhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | .cfg, .config, .cql, .css, .dtd, .expect, .gradle, .groovy, .scss, .sh, .soy, .sql, .tld, .ts, Dockerfile or packageinfo | Finds missing and unnecessary whitespace. |
XMLBuildFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLCDATACheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLCheckstyleFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLCustomSQLOrderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLCustomSQLStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Applies rules to enforce consisteny in code style for `.xml` files in directory `custom-sql`. |
XMLDDLStructuresFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLEmptyLinesCheck | [Styling](styling_checks.markdown#styling-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Finds missing and unnecessary empty lines. |
XMLFSBExcludeFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLFriendlyURLRoutesFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLHBMFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLIndentationCheck | [Styling](styling_checks.markdown#styling-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .tld, .toggle, .tpl, .wsdl, .xml or .xsd | Finds incorrect indentation in `.xml` files. |
XMLIvyFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLLog4jFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLLookAndFeelCompatibilityVersionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLLookAndFeelFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLModelHintsFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLPomFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLPortletFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLPortletPreferencesFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLPoshiFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLProjectElementCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLResourceActionsFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
[XMLServiceEntityNameCheck](checks/xml_service_entity_name_check.markdown#xmlserviceentitynamecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks that the `entity name` in `service.xml` does not equal the `package name`. |
XMLServiceFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLServiceReferenceCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLSolrSchemaFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLSourcechecksFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLSpringFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLStrutsConfigFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Applies rules to enforce consisteny in code style. |
XMLSuppressionsFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLTagAttributesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .html, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLTestIgnorableErrorLinesFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLTilesDefsFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLToggleFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLWebFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLWhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Finds missing and unnecessary whitespace in `.xml` files. |
YMLDefinitionOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .yaml or .yml | Sorts definitions alphabetically in `.yml` files. |
YMLEmptyLinesCheck | [Styling](styling_checks.markdown#styling-checks) | .yaml or .yml | Finds missing and unnecessary empty lines. |
YMLLongLinesCheck | [Styling](styling_checks.markdown#styling-checks) | .yaml or .yml | Finds lines that are longer than the specified maximum line length. |
YMLStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .yaml or .yml | Applies rules to enforce consisteny in code style. |
YMLWhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | .yaml or .yml | Finds missing and unnecessary whitespace in `.yml` files. |