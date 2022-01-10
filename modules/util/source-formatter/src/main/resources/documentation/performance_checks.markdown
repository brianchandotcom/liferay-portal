# Performance Checks

Check | File Extensions | Description
----- | --------------- | -----------
[ArrayCheck](checks/array_check.markdown#arraycheck) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks if performance can be improved by using different mehods that can be used by collections. |
AssignAsUsedCheck | .java | Finds cases where an assign statement can be inlined or moved closer to where it is used. |
ConcatCheck | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks for correct use of `StringBundler.concat`. |
ConstructorGlobalVariableDeclarationCheck | .java | Checks that initial values of global variables are not set in the constructor. |
[ExceptionCheck](checks/exception_check.markdown#exceptioncheck) | .java | Finds private methods that throw unnecessary exception. |
[FrameworkBundleCheck](checks/framework_bundle_check.markdown#frameworkbundlecheck) | .java | Checks that `org.osgi.framework.Bundle.getHeaders()` is not used. |
[GradleDependenciesCheck](checks/gradle_dependencies_check.markdown#gradledependenciescheck) | .gradle | Checks that modules are not depending on other modules. |
[JSPDefineObjectsCheck](checks/jsp_define_objects_check.markdown#jspdefineobjectscheck) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks for unnesecarry duplication of code that already exists in `defineObjects`. |
[JSPUnusedJSPFCheck](checks/jsp_unused_jspf_check.markdown#jspunusedjspfcheck) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds `.jspf` files that are not used. |
JSPUnusedTermsCheck | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds taglibs, variables and imports that are unused. |
JavaCollapseImportsCheck | .java | Collapses imports that use wildcard |
JavaHibernateSQLCheck | .java | Finds calls to `com.liferay.portal.kernel.dao.orm.Session.createSQLQuery` (use `Session.createSynchronizedSQLQuery` instead). |
[JavaMultiPlusConcatCheck](checks/java_multi_plus_concat_check.markdown#javamultiplusconcatcheck) | .java | Checks that we do not concatenate more than 3 String objects. |
[JavaServiceTrackerFactoryCheck](checks/java_service_tracker_factory_check.markdown#javaservicetrackerfactorycheck) | .java | Checks that there are no calls to deprecatred method `ServiceTrackerFactory.open(java.lang.Class)`. |
JavaSessionCheck | .java | Finds unnecessary calls to `Session.flush()` (calls that are followed by `Session.clear()`). |
[JavaStringBundlerConcatCheck](checks/java_string_bundler_concat_check.markdown#javastringbundlerconcatcheck) | .java | Finds calls to `StringBundler.concat` with less than 3 parameters. |
JavaStringBundlerInitialCapacityCheck | .java | Checks the initial capacity of new instances of `StringBundler`. |
LocalPatternCheck | .java | Checks that a `java.util.Pattern` variable is declared globally, so that it is initiated only once. |
[MapIterationCheck](checks/map_iteration_check.markdown#mapiterationcheck) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that there are no unnecessary map iterations. |
ParsePrimitiveTypeCheck | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Verifies that `GetterUtil.parse*` is used to parse primitive types, when possible. |
RedundantBranchingStatementCheck | .java | Finds unnecessary branching (`break`, `continue` or `return`) statements. |
SetUtilMethodsCheck | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds cases of inefficient SetUtil operations. |
StringCastCheck | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds cases where a redundant `toString()` is called on variable type `String`. |
[StringMethodsCheck](checks/string_methods_check.markdown#stringmethodscheck) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks if performance can be improved by using different String operation methods. |
SubstringCheck | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds cases like `s.substring(1, s.length())` (use `s.substring(1)` instead). |
ThreadLocalUtilCheck | .java | Finds new instances of `java.lang.Thread` (use `ThreadLocalUtil.create` instead). |
TryWithResourcesCheck | .java | Ensures using Try-With-Resources statement to properly close the resource. |
[UnnecessaryAssignCheck](checks/unnecessary_assign_check.markdown#unnecessaryassigncheck) | .java | Finds unnecessary assign statements (when it is either reassigned or returned right after). |
UnnecessaryTypeCastCheck | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds unnecessary Type Casting. |
[UnnecessaryVariableDeclarationCheck](checks/unnecessary_variable_declaration_check.markdown#unnecessaryvariabledeclarationcheck) | .java | Finds unnecessary variable declarations (when it is either reassigned or returned right after). |
UnprocessedExceptionCheck | .java | Finds cases where an `Exception` is swallowed without being processed. |
UnusedMethodCheck | .java | Finds private methods that are not used. |
UnusedParameterCheck | .java | Finds parameters in private methods that are not used. |
UnusedVariableCheck | .java | Finds variables that are declared, but not used. |
[ValidatorEqualsCheck](checks/validator_equals_check.markdown#validatorequalscheck) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that there are no calls to `Validator.equals(Object, Object)`. |
VariableDeclarationAsUsedCheck | .java | Finds cases where a variable declaration can be inlined or moved closer to where it is used. |