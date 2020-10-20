# Checks for .jsp, .jspf, .tag, .tpl or .vm

Check | Category | Description
----- | -------- | -----------
AppendCheck | [Styling](styling_checks.markdown#styling-checks) | Checks instances where literal Strings are appended. |
[ArrayCheck](checks/array_check.markdown#arraycheck) | [Performance](performance_checks.markdown#performance-checks) | Checks if performance can be improved by using different mehods that can be used by collections. |
[ArrayTypeStyleCheck](https://checkstyle.sourceforge.io/config_misc.html#ArrayTypeStyle) | [Styling](styling_checks.markdown#styling-checks) | Checks the style of array type definitions. |
[AvoidNestedBlocksCheck](https://checkstyle.sourceforge.io/config_blocks.html#AvoidNestedBlocks) | [Styling](styling_checks.markdown#styling-checks) | Finds nested blocks (blocks that are used freely in the code). |
CamelCaseNameCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks variable names for correct use of `CamelCase`. |
[ChainingCheck](checks/chaining_check.markdown#chainingcheck) | [Styling](styling_checks.markdown#styling-checks) | Checks that chaining is only applied on certain types and methods. |
CompatClassImportsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that classes are imported from `compat` modules, when possible. |
ConcatCheck | [Performance](performance_checks.markdown#performance-checks) | Checks for correct use of `StringBundler.concat`. |
ConstantNameCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks that variable names of constants follow correct naming rules. |
ContractionsCheck | [Styling](styling_checks.markdown#styling-checks) | Finds contractions in Strings (such as `can't` or `you're`). |
[CopyrightCheck](checks/copyright_check.markdown#copyrightcheck) | [Styling](styling_checks.markdown#styling-checks) | Validates `copyright` header. |
[DefaultComesLastCheck](https://checkstyle.sourceforge.io/config_coding.html#DefaultComesLast) | [Styling](styling_checks.markdown#styling-checks) | Checks that the `default` is after all the cases in a `switch` statement. |
EmptyCollectionCheck | [Styling](styling_checks.markdown#styling-checks) | Checks that there are no calls to `Collections.EMPTY_LIST`, `Collections.EMPTY_MAP` or `Collections.EMPTY_SET`. |
[ExceptionMessageCheck](checks/message_check.markdown#messagecheck) | [Styling](styling_checks.markdown#styling-checks) | Validates messages that are passed to exceptions. |
FactoryCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds cases where `*Factory` should be used when creating new instances of an object. |
[GenericTypeCheck](checks/generic_type_check.markdown#generictypecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that generics are always specified to provide compile-time checking and removing the risk of `ClassCastException` during runtime. |
[GetterUtilCheck](checks/getter_util_check.markdown#getterutilcheck) | [Styling](styling_checks.markdown#styling-checks) | Finds cases where the default value is passed to `GetterUtil.get*` or `ParamUtil.get*`. |
InstanceofOrderCheck | [Styling](styling_checks.markdown#styling-checks) | Check the order of `instanceof` calls. |
JSONNamingCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks if variable names follow naming conventions. |
[JSONUtilCheck](checks/json_util_check.markdown#jsonutilcheck) | [Styling](styling_checks.markdown#styling-checks) | Checks for utilization of class `JSONUtil`. |
[JSPArrowFunctionCheck](checks/jsp_arrow_function_check.markdown#jsparrowfunctioncheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that there are no array functions. |
[JSPDefineObjectsCheck](checks/jsp_define_objects_check.markdown#jspdefineobjectscheck) | [Performance](performance_checks.markdown#performance-checks) | Checks for unnesecarry duplication of code that already exists in `defineObjects`. |
JSPEmptyLinesCheck | [Styling](styling_checks.markdown#styling-checks) | Finds missing and unnecessary empty lines. |
JSPExceptionOrderCheck | [Styling](styling_checks.markdown#styling-checks) | Checks the order of exceptions in `.jsp` files. |
JSPFileNameCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks if the file name of `.jsp` or `.jspf` follows the naming conventions. |
[JSPFunctionNameCheck](checks/jsp_function_name_check.markdown#jspfunctionnamecheck) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Check if the names of functions in `.jsp` files follow naming conventions. |
[JSPIllegalSyntaxCheck](checks/jsp_illegal_syntax_check.markdown#jspillegalsyntaxcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds incorrect use of `System.out.print`, `console.log` or `debugger.*` in `.jsp` files. |
[JSPImportsCheck](checks/jsp_imports_check.markdown#jspimportscheck) | [Styling](styling_checks.markdown#styling-checks) | Sorts and groups imports in `.jsp` files. |
[JSPIncludeCheck](checks/jsp_include_check.markdown#jspincludecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Validates values of `include` in `.jsp` files. |
JSPIndentationCheck | [Styling](styling_checks.markdown#styling-checks) | Finds incorrect indentation in `.jsp` files. |
JSPInlineVariableCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
JSPJavaParserCheck | [Styling](styling_checks.markdown#styling-checks) | Performs JavaParser on `.java` files. |
JSPLanguageKeysCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds missing language keys in `Language.properties`. |
JSPLanguageUtilCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds cases where Locale is passed to `LanguageUtil.get` instead of `HttpServletRequest`. |
JSPLineBreakCheck | [Styling](styling_checks.markdown#styling-checks) | Finds missing and unnecessary line breaks in `.jsp` lines. |
JSPLogFileNameCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Validates the value that is passed to `LogFactoryUtil.getLog` in `.jsp`. |
JSPLogParametersCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Validates the values of parameters passed to `_log.*` calls. |
[JSPMethodCallsCheck](checks/jsp_method_calls_check.markdown#jspmethodcallscheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that type `LiferayPortletResponse` is used to call `getNamespace()`. |
[JSPMissingTaglibsCheck](checks/jsp_missing_taglibs_check.markdown#jspmissingtaglibscheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks for missing taglibs. |
[JSPModuleIllegalImportsCheck](checks/jsp_module_illegal_imports_check.markdown#jspmoduleillegalimportscheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | Finds incorrect use of `com.liferay.registry.Registry` or `com.liferay.util.ContentUtil`. |
[JSPParenthesesCheck](checks/if_statement_check.markdown#ifstatementcheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | Finds incorrect use of parentheses in statement. |
JSPRedirectBackURLCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | Validates values of variable `redirect`. |
[JSPSendRedirectCheck](checks/jsp_send_redirect_check.markdown#jspsendredirectcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that there are no calls to `HttpServletResponse.sendRedirect` from `jsp` files. |
[JSPServiceUtilCheck](checks/jsp_service_util_check.markdown#jspserviceutilcheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | Finds incorrect use of `*ServiceUtil` in `.jsp` files in modules. |
JSPSessionKeysCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that messages send to `SessionsErrors` or `SessionMessages` follow naming conventions. |
[JSPStringMethodsCheck](checks/string_methods_check.markdown#stringmethodscheck) | [Performance](performance_checks.markdown#performance-checks) | Finds cases of inefficient String operations. |
JSPStylingCheck | [Styling](styling_checks.markdown#styling-checks) | Applies rules to enforce consisteny in code style. |
JSPTagAttributesCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Performs several checks on tag attributes. |
[JSPTaglibVariableCheck](checks/jsp_taglib_variable_check.markdown#jsptaglibvariablecheck) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks if variable names follow naming conventions. |
JSPUnusedJSPF | [Performance](performance_checks.markdown#performance-checks) | Finds `.jspf` files that are not used. |
JSPUnusedTermsCheck | [Performance](performance_checks.markdown#performance-checks) | Finds taglibs, variables and imports that are unused. |
JSPWhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | Finds missing and unnecessary whitespace in `.jsp` files. |
JSPXSSVulnerabilitiesCheck | [Security](security_checks.markdown#security-checks) | Finds xss vulnerabilities. |
[LambdaCheck](checks/lambda_check.markdown#lambdacheck) | [Styling](styling_checks.markdown#styling-checks) | Checks that `lambda` statements are as simple as possible. |
[ListUtilCheck](checks/list_util_check.markdown#listutilcheck) | [Styling](styling_checks.markdown#styling-checks) | Checks for utilization of class `ListUtil`. |
LiteralStringEqualsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
[LocalFinalVariableNameCheck](https://checkstyle.sourceforge.io/config_naming.html#LocalFinalVariableName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks that local final variable names conform to a specified pattern. |
[LocalVariableNameCheck](https://checkstyle.sourceforge.io/config_naming.html#LocalVariableName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks that local, non-final variable names conform to a specified pattern. |
LocaleUtilCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
[MapBuilderCheck](checks/builder_check.markdown#buildercheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | Checks that `ConcurrentHashMapBuilder`, `HashMapBuilder`, `LinkedHashMapBuilder` or `TreeMapBuilder` is used when possible. |
[MapIterationCheck](checks/map_iteration_check.markdown#mapiterationcheck) | [Performance](performance_checks.markdown#performance-checks) | Checks that there are no unnecessary map iterations. |
[MemberNameCheck](https://checkstyle.sourceforge.io/config_naming.html#MemberName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks that instance variable names conform to a specified pattern. |
MethodCallsOrderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
[MethodNameCheck](https://checkstyle.sourceforge.io/config_naming.html#MethodName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks that method names conform to a specified pattern. |
MethodNamingCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks that method names follow naming conventions. |
[MethodParamPadCheck](https://checkstyle.sourceforge.io/config_whitespace.html#MethodParamPad) | [Styling](styling_checks.markdown#styling-checks) | Checks the padding between the identifier of a method definition, constructor definition, method call, or constructor invocation; and the left parenthesis of the parameter list. |
MissingDiamondOperatorCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
MissingParenthesesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
[ModifierOrderCheck](https://checkstyle.sourceforge.io/config_modifier.html#ModifierOrder) | [Styling](styling_checks.markdown#styling-checks) | Checks that the order of modifiers conforms to the suggestions in the Java Language specification, § 8.1.1, 8.3.1, 8.4.3 and 9.4. |
[MultipleVariableDeclarationsCheck](https://checkstyle.sourceforge.io/config_coding.html#MultipleVariableDeclarations) | [Styling](styling_checks.markdown#styling-checks) | Checks that each variable declaration is in its own statement and on its own line. |
NestedIfStatementCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
[NoLineWrapCheck](https://checkstyle.sourceforge.io/config_whitespace.html#NoLineWrap) | [Styling](styling_checks.markdown#styling-checks) | Checks that chosen statements are not line-wrapped. |
[NoWhitespaceAfterCheck](https://checkstyle.sourceforge.io/config_whitespace.html#NoWhitespaceAfter) | [Styling](styling_checks.markdown#styling-checks) | Checks that there is no whitespace after a token. |
[NoWhitespaceBeforeCheck](https://checkstyle.sourceforge.io/config_whitespace.html#NoWhitespaceBefore) | [Styling](styling_checks.markdown#styling-checks) | Checks that there is no whitespace before a token. |
NumberSuffixCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
[OneStatementPerLineCheck](https://checkstyle.sourceforge.io/config_coding.html#OneStatementPerLine) | [Styling](styling_checks.markdown#styling-checks) | Checks that there is only one statement per line. |
OperatorOperandCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
[OperatorWrapCheck](https://checkstyle.sourceforge.io/config_whitespace.html#OperatorWrap) | [Styling](styling_checks.markdown#styling-checks) | Checks the policy on how to wrap lines on operators. |
[ParameterNameCheck](https://checkstyle.sourceforge.io/config_naming.html#ParameterName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks that method parameter names conform to a specified pattern. |
ParsePrimitiveTypeCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
PersistenceCallCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
PlusStatementCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
PrimitiveWrapperInstantiationCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
PrincipalExceptionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
[ResourceBundleCheck](checks/resource_bundle_check.markdown#resourcebundlecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that there are no calls to `java.util.ResourceBundle.getBundle`. |
SemiColonCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
SizeIsZeroCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
[StaticVariableNameCheck](https://checkstyle.sourceforge.io/config_naming.html#StaticVariableName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks that static, non-final variable names conform to a specified pattern. |
StringBundlerNamingCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks for consistent naming on variables of type 'StringBundler'. |
StringCastCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
[StringLiteralEqualityCheck](https://checkstyle.sourceforge.io/config_coding.html#StringLiteralEquality) | [Styling](styling_checks.markdown#styling-checks) | Checks that string literals are not used with == or !=. |
SubstringCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
TernaryOperatorCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
[UnnecessaryParenthesesCheck](https://checkstyle.sourceforge.io/config_coding.html#UnnecessaryParentheses) | [Styling](styling_checks.markdown#styling-checks) | Checks if unnecessary parentheses are used in a statement or expression. |
UnnecessaryTypeCastCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
UnparameterizedClassCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
[ValidatorEqualsCheck](checks/validator_equals_check.markdown#validatorequalscheck) | [Performance](performance_checks.markdown#performance-checks) | Checks that there are no calls to `Validator.equals(Object, Object)`. |
ValidatorIsNullCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
[WhitespaceAfterCheck](https://checkstyle.sourceforge.io/config_whitespace.html#WhitespaceAfter) | [Styling](styling_checks.markdown#styling-checks) | Checks that a token is followed by whitespace, with the exception that it does not check for whitespace after the semicolon of an empty for iterator. |
[WhitespaceAroundCheck](https://checkstyle.sourceforge.io/config_whitespace.html#WhitespaceAround) | [Styling](styling_checks.markdown#styling-checks) | Checks that a token is surrounded by whitespace. |