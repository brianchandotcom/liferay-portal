# Styling Checks

Check | File Extensions | Description
----- | --------------- | -----------
[AnnotationUseStyleCheck](https://checkstyle.sourceforge.io/config_annotation.html#AnnotationUseStyle) | .java | Checks the style of elements in annotations. |
[ArrayTypeStyleCheck](https://checkstyle.sourceforge.io/config_misc.html#ArrayTypeStyle) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks the style of array type definitions. |
[AvoidNestedBlocksCheck](https://checkstyle.sourceforge.io/config_blocks.html#AvoidNestedBlocks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Finds nested blocks (blocks that are used freely in the code). |
BNDCapabilityCheck | .bnd | Sorts and applies logic to fix line breaks to property values for `Provide-Capability` and `Require-Capability` |
[BNDImportsCheck](checks/bnd_imports_check.markdown#bndimportscheck) | .bnd | Sorts class names and checks for use of wildcards in property values for `-conditionalpackage`, `-exportcontents` and `Export-Package` |
BNDLineBreaksCheck | .bnd | Checks for incorrect/missing line breaks |
BNDRunInstructionsOrderCheck | .bndrun | Sorts definition keys alphabetically |
BNDStylingCheck | .bnd | Applies rules to enforce consisteny in code style |
BNDWhitespaceCheck | .bnd | Checks for incorrect/missing line whitespace |
[ChainingCheck](checks/chaining_check.markdown#chainingcheck) | .java | Checks that chaining is only applied on certain types and methods |
[DefaultComesLastCheck](https://checkstyle.sourceforge.io/config_coding.html#DefaultComesLast) | .java, .jsp, .jspf, .tag, .tpl or .vm | Check that the default is after all the cases in a switch statement. |
[GradleTaskCreationCheck](checks/gradle_task_creation_check.markdown#gradletaskcreationcheck) | .gradle | Checks that a task is declared on a separate line before the closure |
[JSONUtilCheck](checks/json_util_check.markdown#jsonutilcheck) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks for utilization of class `JSONUtil` |
[JavaConstructorParametersCheck](checks/java_constructor_parameters_check.markdown#javaconstructorparameterscheck) | .java | Checks that the order of variable assignments matches the order of the parameters in the constructor signature |
[JavaForLoopCheck](checks/java_for_loop_check.markdown#javaforloopcheck) | .java | Checks if a Enhanced For Loop can be used instead of a Simple For Loop |
[JavaStaticImportsCheck](checks/java_static_imports_check.markdown#javastaticimportscheck) | .java | Checks that there are no static imports |
[JavadocStyleCheck](https://checkstyle.sourceforge.io/config_javadoc.html#JavadocStyle) | .java | Validates Javadoc comments to help ensure they are well formed. |
[LambdaCheck](checks/lambda_check.markdown#lambdacheck) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that `lambda` statements are as simple as possible |
[ListUtilCheck](checks/list_util_check.markdown#listutilcheck) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks for utilization of class `ListUtil` |
[MethodParamPadCheck](https://checkstyle.sourceforge.io/config_whitespace.html#MethodParamPad) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks the padding between the identifier of a method definition, constructor definition, method call, or constructor invocation; and the left parenthesis of the parameter list. |
[MissingDeprecatedCheck](https://checkstyle.sourceforge.io/config_annotation.html#MissingDeprecated) | .java | Verifies that the annotation @Deprecated and the Javadoc tag @deprecated are both present when either of them is present. |
[MissingEmptyLineCheck](checks/missing_empty_line_check.markdown#missingemptylinecheck) | .java | Checks for missing line breaks around variable declarations |
[ModifierOrderCheck](https://checkstyle.sourceforge.io/config_modifier.html#ModifierOrder) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that the order of modifiers conforms to the suggestions in the Java Language specification, § 8.1.1, 8.3.1, 8.4.3 and 9.4. |
[MultipleVariableDeclarationsCheck](https://checkstyle.sourceforge.io/config_coding.html#MultipleVariableDeclarations) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that each variable declaration is in its own statement and on its own line. |
[NoLineWrapCheck](https://checkstyle.sourceforge.io/config_whitespace.html#NoLineWrap) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that chosen statements are not line-wrapped. |
[NoWhitespaceAfterCheck](https://checkstyle.sourceforge.io/config_whitespace.html#NoWhitespaceAfter) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that there is no whitespace after a token. |
[NoWhitespaceBeforeCheck](https://checkstyle.sourceforge.io/config_whitespace.html#NoWhitespaceBefore) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that there is no whitespace before a token. |
[OneStatementPerLineCheck](https://checkstyle.sourceforge.io/config_coding.html#OneStatementPerLine) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that there is only one statement per line. |
[OperatorWrapCheck](https://checkstyle.sourceforge.io/config_whitespace.html#OperatorWrap) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks the policy on how to wrap lines on operators. |
[StringLiteralEqualityCheck](https://checkstyle.sourceforge.io/config_coding.html#StringLiteralEquality) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that string literals are not used with == or !=. |
[UnnecessaryParenthesesCheck](https://checkstyle.sourceforge.io/config_coding.html#UnnecessaryParentheses) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks if unnecessary parentheses are used in a statement or expression. |
[WhitespaceAfterCheck](https://checkstyle.sourceforge.io/config_whitespace.html#WhitespaceAfter) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that a token is followed by whitespace, with the exception that it does not check for whitespace after the semicolon of an empty for iterator. |
[WhitespaceAroundCheck](https://checkstyle.sourceforge.io/config_whitespace.html#WhitespaceAround) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that a token is surrounded by whitespace. |