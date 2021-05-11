# Poshi Script

Poshi Script is a scripting language created to simplify the writing of
functional tests that run on the Poshi Runner framework (which is built on
[Selenium WebDriver](https://www.seleniumhq.org/projects/webdriver/)).
Initially, the Poshi Runner framework supported a scripting language that was
based off of XML syntax, which will be referred to as Poshi XML. Poshi Script
was created to be a more readable and writable alternative to Poshi XML and
represents all of the same functionality featured in the Poshi Runner framework,
but with a simpler syntax.

This documentation assumes basic familiarity with Poshi XML syntax and does not
attempt to thoroughly explain the file types or the Poshi Runner framework,
although it may be updated in the future to be more comprehensive.

## Table of Contents

1. [Introduction](#introduction-1)
    - [Key Changes: Poshi XML vs Poshi Script](#key-changes-poshi-xml-vs-poshi-script)
        - [Calling Poshi commands or Java methods](#calling-poshi-commands-or-java-methods)
        - [Assigning variables to values of Poshi macros or Java methods](#assigning-variables-to-values-of-poshi-macros-or-java-methods)
            - [Assigning a `var` to a value returned by a Poshi macro](#assigning-a-var-to-a-value-returned-by-a-poshi-macro)
            - [Assigning a `var` to the value of a Java method](#assigning-a-var-to-the-value-of-a-java-method)
2. [Variables](#variables-1)
    - [Declaring and assigning variables](#declaring-and-assigning-variables)
        - [`var` assignments](#var-assignments)
            - [Basic Strings](#basic-strings)
            - [Multiline Strings](#multiline-strings)
            - [Assigning `var`'s to macro invocations](#assigning-vars-to-macro-invocations)
            - [Assigning `var`'s to class/method invocations](#assigning-vars-to-classmethod-invocations)
            - [Referencing `var`'s](#referencing-vars)
        - [`property` assignments](#property-assignments)
3. [Using Poshi Functions, Poshi Macros and Java Methods](#using-poshi-functions-poshi-macros-and-java-methods-1)
    - [Creating a function](#creating-a-function)
    - [Creating a macro](#creating-a-macro)
        - [Creating a `.macro` file](#creating-a-macro-file)
        - [Creating a macro command](#creating-a-macro-command)
        - [Returning values](#returning-values)
    - [Creating or requesting Java functionality](#creating-or-requesting-java-functionality)
    - [Executing Poshi functions, Poshi macros, and Java methods](#executing-poshi-functions-poshi-macros-and-java-methods)
        - [Executing Poshi functions](#executing-poshi-functions)
        - [Executing Poshi macros](#executing-poshi-macros)
        - [Executing Java Methods](#executing-java-methods)
        - [Additional Utilities](#additional-utilities)
            - [`echo`](#echo)
            - [`fail`](#fail)
4. [Control Flow](#control-flow)
    - [Conditional logic](#conditional-logic)
        - [`if`, `else if`, and `else` conditions](#if-else-if-and-else-conditions)
    - [Conditional expressions](#conditional-expressions)
        - [isSet](#isset)
        - [equals](#equals)
        - [contains](#contains)
        - [Conditional Poshi function](#conditional-poshi-function)
        - [Logical operators (and, or, not)](#logical-operators-and-or-not)
    - [Loops](#loops)
        - [`while` loops](#while-loops)
        - [`for` loops](#for-loops)
5. [Writing a Test](#writing-a-test-1)
    - [Creating a `.testcase` file](#creating-a-testcase-file)
    - [Adding a `test`](#adding-a-test)
    - [`setUp` and `tearDown` blocks](#setup-and-teardown-blocks)
    - [Annotations](#annotations)
        - [`definition` annotations](#definition-annotations)
            - [`@component-name`](#component-name)
            - [`@ignore`](#ignore)
            - [`@ignore-command-names`](#ignore-command-names)
        - [`test` annotations](#test-annotations)
            - [`@description`](#description)
            - [`@ignore`](#ignore-1)
            - [`@priority`](#priority)
6. [Other](#other-1)
    - [Comments](#comments)
        - [Inline comments](#inline-comments)
        - [Multiline comments](#multiline-comments)
    - [Grouping `task`'s](#grouping-tasks)
7. [Debugging Syntax Errors](#debugging-syntax-errors)
    - [How to run Poshi Script validation](#how-to-run-poshi-script-validation)
    - [Types of Poshi Script syntax errors](#types-of-poshi-script-syntax-errors)
        - [Data Loss](#data-loss)
        - [Unbalanced Code](#unbalanced-code)
        - [Invalid Poshi Script syntax](#invalid-poshi-script-syntax)

## Introduction

### Key Changes: Poshi XML vs Poshi Script

Poshi Script seeks to resolve inconsistencies in Poshi XML that made it
difficult to comprehend what the test was doing. This document explains the
syntax and usage rules of Poshi Script by comparing it to Poshi XML.

#### Calling Poshi commands or Java methods

In Poshi, units of reusable code are stored in `.function` and `.macro` files.
Poshi also supports calling methods in Java classes that are found in the
classpath. In Poshi XML, the notation to call any Poshi function, Poshi macro,
or Java method required explicit meta data parameters to denote the file type,
so that the correct command or method could be executed.

In Poshi Script, only the file name, command/method name, and parameters (names
and values) are required to invoke these commands/methods; the file type or type
of invocation is not required. Some comparisons are shown below:

*Poshi XML:*

```xml
<execute function="AssertElementPresent" locator1="Home#PAGE" value1="Welcome" />

<execute function="Click#click" locator1="Home#PAGE" />

<execute function="Refresh#refresh" />

...

<execute macro="Navigator#openURL" />

<execute macro="ProductMenu#gotoPortlet">
	<var name="category" value="Content" />
	<var name="panel" value="Site Administration" />
	<var name="portlet" value="Blogs" />
</execute>

...

<execute class="com.liferay.poshi.runner.util.JSONCurlUtil" method="delete">
	<arg value="${curl}" />
</execute>

<execute class="JSONCurlUtil" method="post">
	<arg value="${curl}" />
</execute>
```

*Poshi Script:*

```javascript

AssertElementPresent(locator1="Home#PAGE", value1="Welcome");

Click.click(locator1="Home#PAGE");

Refresh.refresh();

...

Navigator.openURL();

ProductMenu.gotoPortlet(
	category = "Content",
	panel = "Site Administration",
	portlet = "Blogs"
);

...

com.liferay.poshi.runner.util.JSONCurlUtil.delete("${curl}");

JSONCurlUtil.post("${curl}");

```

For detailed syntax rules, please see [Executing Poshi functions](#executing-poshi-functions),
[Executing Poshi macros](#executing-poshi-macros), or [Executing Java Methods](#executing-java-methods).

#### Assigning variables to values of Poshi macros or Java methods

In Poshi XML, there were a few ways to set `var`s to the value of a Poshi macro
or Java method:

- The *return macro* syntax was used to set a `var` to the macro's value.
- The *var method* or *execute class* syntax was used to set a `var` to the
  Java method's value.

In Poshi Script, this is streamlined by simply declaring a variable and
assigning it to a valid macro command or Java method syntax.

##### Assigning a `var` to a value returned by a Poshi macro

The Poshi XML and Poshi Script comparisons are listed below:

*Poshi XML:*

```xml

<!-- Note that some changes were made in the Poshi XML syntax to simplify this notation in LRQA-40568 -->

<execute macro="TestCase#getSiteName">
	<return name="siteName" />
	<var name="siteName" value="${siteName}" />
</execute>
```

*Poshi Script:*

```javascript
var siteName = TestCase.getSiteName(siteName = "${siteName}");
```

##### Assigning a `var` to the value of a Java method

The Poshi XML and Poshi Script comparisons are listed below:

*Poshi XML:*

```xml
<var method="StringUtil#upperCase('${breadcrumbName}')" name="breadcrumbNameUppercase" />

<!-- Alternatively... (both notations are equivalent in Poshi XML) -->

<execute class="com.liferay.poshi.runner.util.StringUtil" method="upperCase">
	<arg value="${breadcrumbName}" />
	<return name="breadcrumbNameUppercase"
</execute>
```

*Poshi Script:*

```javascript
var breadcrumbNameUppercase = StringUtil.upperCase("${breadcrumbName}");

...

var breadcrumbNameUppercase = com.liferay.poshi.runner.util.StringUtil.upperCase("${breadcrumbName}");
```

Notice that while there are two ways to set a variable to a Java method in Poshi
XML, there is only one way in Poshi Script. The full class name is not necessary
for classes in the `com.liferay.poshi.runner.util` package, but it is still
valid. Additionally, notice that the parameter is wrapped in double quotes
instead of single quotes in Poshi Script.

## Variables

### Declaring and assigning variables

In Poshi Script, variables must always be assigned/initialized in their
declaration. It is not possible to only declare a variable. There are two
possible types of variable assignments:

- `property`
- `var`

Note that `var` is the standard variable that should be used, while `property`
is more limited in what it can be assigned to. The syntax for assignments is the
same, and is specified in this order:

1. Keyword
2. Variable name
3. Equals sign
4. Notation of a valid assignment

For specific examples, see below.

#### `var` assignments

`var` assignments are how information is stored and referenced within Poshi
files. These are generally strings and can be directly assigned from some
invocations. These assignments can also reference objects and can support
typing, but additional development is still necessary to polish this feature.
For various ways to assign `var`s, see the examples below.

##### Basic Strings

*Example:*

```javascript
var userEmailAddress = "userea@liferay.com";
```

##### Multiline Strings

*Example:*

```groovy
var wikiPageContent = '''<p id='demo'>PASS</p>

<script type='text/javascript'>
	document.getElementById('demo').innerHTML = 'FAIL';
</script>''';
```

##### Assigning `var`'s to macro invocations

*Example:*

```javascript
var siteName = TestCase.getSiteName();
```

Note that the macro must explicitly return a variable value. To create a macro
that can return a value, see [Returning values](#returning-values).

##### Assigning `var`'s to class/method invocations

*Example:*

```javascript
var breadcrumbNameUppercase = StringUtil.upperCase("${breadcrumbName}");
```

##### Referencing `var`s

*Example:*

```javascript
var userEmailAddress = "${firstName}.${lastName}@liferay.com";
```

`var`s can be referenced in any string using the `${}` notation.

#### `property` assignments

`property` assignments are variables that are intended to be used externally
(i.e., outside of the actual test context). Properties are typically used
to help filter tests that get run, as well as denote additional logic that must
be run outside of the test context before or after a test.

The property variable names are typically separated periods (`.`) for multi-word
names.

Note that because `property` assignments are for external use, there is no
direct syntax to reference a `property` value. If referencing the property value
is necessary, setting a property to the `var` value may be an adequate solution.
Additionally, the `PropsUtil.get(...)` method can be used to return the value of
a set property, whether from project property files or ones set in the testcase.

*Example:*

```javascript
property portal.release = "true";
```

## Using Poshi functions, Poshi macros, and Java methods

Within the Poshi file structure of a project, all reusable code that can be
defined by test writers is contained in `.function` and `.macro`  files. Poshi
functions should be the simplest and smallest unit of reusable code (typically
pertaining to an interaction with the HTML element), while the Poshi macros
store larger units of code that group together several Poshi functions (which
represent a larger task like adding an asset in Portal). Poshi functions can
only call the Poshi wrapper classes for Selenium WebDriver and other Poshi
functions, while the Poshi macros can call Poshi functions and macros.

This relationship of Poshi functions and Poshi macros is still preserved in
Poshi Script, but in the future, it may be possible to consolidate these
groupings, while preserving the organization of logic (granular actions vs high
level actions).

Although Poshi was meant to deal with the web page and its elements through
Selenium WebDriver, additional functionality (i.e. string manipulation,
mathematical operations, HTTP requests, etc.) is sometimes required. These
features are implemented by invoking methods on Java classes. This feature lets
Poshi leverage existing Java classes to simplify test writing.

### Creating a function

Currently, Poshi Script only supports `.macro` and `.testcase` files. Poshi
functions written in Poshi Script will be supported and translated in a future
Poshi release. See the existing documentation for instructions on creating
`.function` files in Poshi XML.

### Creating a macro

#### Creating a `.macro` file

Any Poshi Script file must first be declared with a `definition` block. All
other syntax is contained within this `definition` block.

`Macro.macro`:

```javascript
definition {

}
```

#### Creating a macro command

Within the `definition` block of the `.macro` file, `macro` blocks are used to
define individual macros. To do so, use the `macro` keyword, followed by a
string identifier (note that each macro name must be unique) that is used to
reference the macro command.

`Macro.macro`:

```javascript
definition {
	macro echoFailure { // Make sure each identifier has a unique name.
		echo("Failure!");
	}

	macro echoSuccess { // No parameters are necessary to define a macro.
		echo("Success!");
	}
}
```

**Valid content:** Any variable declaration or code execution. All blocks except
`definition`, `setUp`, `tearDown`, `test`, and `macro`.

**Valid parent blocks:** `definition`

#### Returning values

It is possible to return values from a macro command by using the `return`
keyword within the macro block followed by the variable reference in double
quoted string syntax. Currently, direct references to variables are not yet
supported.

*Example:*

```javascript
macro viewPG {
	var breadcrumbNameUppercase = StringUtil.upperCase("${breadcrumbName}");

	AssertTextEquals(
		locator1 = "Breadcrumb#BREADCRUMB_ENTRY",
		value1 = "${breadcrumbNameUppercase}"
	);

	return "${breadcrumbNameUppercase}";
}
```

### Creating or requesting Java functionality

Java method executions are limited to methods from classes in the
[`com.liferay.poshi.runner.util`](https://github.com/liferay/com-liferay-poshi-runner/tree/master/poshi-runner/src/main/java/com/liferay/poshi/runner/util)
package.

If additional functionality is required, contact a member of
[QA Engineering](https://loop.liferay.com/web/guest/home/-/loop/teams/_QA+Engineering)
for guidance.

### Executing Poshi functions, Poshi macros, and Java methods

#### Executing Poshi functions

Poshi functions have the following required parameters: *locator1*, *value1*,
*locator2*, and/or *value2*.

While executing Poshi functions macros, the parameter name and value must be
included when passing in parameters.

Additional variable parameters may also be set while executing Poshi functions.
These parameters may be added to the comma delimited list of parameters. Note
that the variable parameter name and value must be stated as an assignment
(`name = "value"`), but do not need to be prepended with a `var` keyword.

Each function file may contain multiple function commands that are available to
be invoked. A *default* function must be specified in each file. If no function
name is specified in a function call, the *default* function is invoked.

*Examples:*

Explicit invocation of a function file's function:

```javascript
AssertClick.assertClick(locator1 = "Button#ADD_TAGS", value1 = "Add");
```

Implicit invocation of a function file's *default* function:

```javascript
AssertClick(locator1 = "Button#ADD_TAGS", value1 = "Add");
```

Invocation of a function while passing in an additional `var` parameter:

```javascript
Type.sendKeys(
	locator1 = "AlloyEditor#EDITOR",
	value1 = "${kbArticleTitle}",
	key_editor = "title"
);
```

#### Executing Poshi macros

When executing Poshi macros, parameters are passed to the macro in a comma
delimited list. The parameter name and value resembles a `var` declaration
(`name = "value"`), excluding the `var` keyword.

*Example:*

```javascript
ProductMenu.gotoPortlet(
	category = "Content",
	panel = "Site Administration",
	portlet = "Blogs"
);
```

#### Executing Java Methods

Java method executions are limited to methods from classes in the
[`com.liferay.poshi.runner.util`](https://github.com/liferay/com-liferay-poshi-runner/tree/master/poshi-runner/src/main/java/com/liferay/poshi/runner/util)
package.

Parameter names are not required when invoking Java methods. Parameters may be
passed using raw values wrapped in double quotes (different from the single
quoted syntax used in Poshi XML for *var method* parameters). Additionally, the
full class name or simple class name can be used to invoke methods.

*Examples:*

Java method invocation using the full class name:

```javascript
com.liferay.poshi.runner.util.JSONCurlUtil.post("${curl}");
```

Java method invocation using the simple class name:

```javascript
JSONCurlUtil.post("${curl}");
```

#### Additional utilities

##### `echo`

The `echo` utility prints the specified text in the console. Variables may also
be referenced.

*Example:*

```javascript
echo("Selecting configuration iframe");
```
##### `fail`

The `fail` utility immediately fails the currently running test and prints the
specified text in the console. Variables may also be referenced.

*Example:*

```javascript
fail("Please set 'userScreenName'.");
```

## Flow Control

### Conditional logic

#### `if`, `else if`, and `else` conditions

Conditional statements can be evaluated in `if` and `else if` blocks to
determine which set of code to execute upon meeting specific criteria.

**Currently supported conditionals:**

- If a variable is set (see [isSet](#isset)).
- If variable values are equal (see [equals](#equals)).
- Evaluation of a selenium WebDriver boolean method (see
  [Conditional Poshi Function](#conditional-poshi-function)).
- If a string contains a substring (see [contains](#contains)).
- Other logical operators (see [Logical Operators](#logical-operators-and-or-not)).

`else` blocks do not require a condition, but must have an `if` block preceding
it. For valid conditional syntax see the [Conditional expressions](#conditional-expressions)
section below.

*Example:*

```javascript
if ("${pageStaging}" == "true") {
	Navigator.gotoStagedSitePage(
		pageName = "${pageName}",
		siteName = "${siteName}"
	);
}
else if ("${siteURL}" == "true") {
	Navigator.gotoSitePage(
		pageName = "${pageName}",
		siteName = "${siteName}"
	);
}
else {
	Navigator.gotoPage(pageName = "${pageName}");
}
```

**Valid child snippets:**

- All statements.
- All blocks except:
    - `definition`
    - `setUp`
    - `tearDown`
    - `test`
    - `macro`

**Valid parent blocks:**

- All blocks except `definition`.

### Conditional expressions

Conditional expressions are only used within the parenthetical section of an
[`if`, `else if`,](#if-else-if-and-else-conditions) or [`while`](#while-loops)
block header. When these conditions evaluate to `true`, the code within the
block will execute.

#### isSet

The isSet utility returns `true` when a given `var` of specified name is set in
the variable context (i.e., a `var` name is assigned to some value in the
current variable context).

The syntax for using this utility begins with an `isSet` keyword followed by
parenthesis wrapped around the `var` name to be evaluated.

*Example:*

```javascript
if (isSet(duplicate)) {
	Alert.viewErrorMessage(
		errorMessage = "A configuration with this ID already exists. Please enter a unique ID."
	);
}
```

#### equals

This returns `true` when two strings are equal. This is typically used to check
a variable reference against a static string or against another variable
reference.

The syntax for using this condition requires double quotes to denote a string.
To reference a variable, the following syntax and order must be followed:

1. Double quotes used in conjunction with the variable reference syntax (`${}`).
2. A `==` to denote an equality evaluation.
3. The second string.

Please note that the `!=` operator is not currently supported.

*Example:*

```javascript
if ("${check}" == "true") {
	Alert.viewSuccessMessage();
}
```

#### contains

This returns `true` when one string is contained within another string. This can
be used directly with strings or with a reference to a `var` that is a string.

The syntax for using this condition begins with a `contains` keyword followed by
parenthesis wrapped around two double quoted string parameters. The first
parameter is the string, and the second is the substring.

*Example:*

```javascript
if (contains("testing", "test")) {
	echo("String contains substring");
}
```

#### Conditional Poshi function

This returns `true` when a conditional `function` is evaluated as true.

The syntax is the same as [executing a function](#executing-functions), but
without the ending `;`, as it is simply invoking a function that returns a
boolean.

*Example:*

```javascript
while (IsElementPresent(locator1 = "AssetCategorization#TAGS_REMOVE_ICON_GENERIC")) {
	Click(locator1 = "AssetCategorization#TAGS_REMOVE_ICON_GENERIC");
}
```

#### Logical operators (and, or, not)

The only [logical operators](https://en.wikipedia.org/wiki/Logical_connective)
allowed for conditional syntax are *and*, *or*, and *not*, which lets the
condition evaluate multiple combinations of conditions and/or their negations.

##### And

This operator can join together two or more conditions and returns `true` when
all of those conditions are also true.

The current syntax requires each separate condition to be wrapped in parenthesis
and separated by `&&` between each condition.

*Example:*

```javascript
if ((IsElementPresent(locator1 = "Blogs#ADD_BLOGS_ENTRY")) && ("${check}" == "true") && (isSet(duplicate))) {
	Alert.viewSuccessMessage();
}
```

##### Or

This operator can evaluate two or more conditions and returns `true` when at
least one of those conditions is true.

The current syntax requires each separate condition to be wrapped in parenthesis
and separated by `||` between each condition.

*Example:*

```javascript
if ((IsElementPresent(locator1 = "Blogs#ADD_BLOGS_ENTRY")) || ("${check}" == "true") || (isSet(duplicate))) {
	Alert.viewSuccessMessage();
}
```

##### Not

This operator returns `true` when the condition it evaluates is false.

The current syntax requires the condition to be wrapped in parenthesis and
prepended by `!`.

Please note that the `!=` operator is not currently supported.

*Example:*

```javascript
if (!(isSet(duplicate))) {
	Alert.viewErrorMessage(
		errorMessage = "A configuration with this ID already exists. Please enter a unique ID."
	);
}
```

### Loops

#### `while` loops

In Poshi Script, *while* loops will evaluate a condition iteratively before each
execution of the block's content. For valid conditional syntax, see the
[Conditional expressions](#conditional-expressions) section above.

It is also possible to specify a maximum number of iterations within the *while*
loop. This can be used by passing in a `maxIterations` parameter within the
parenthetical content of the block header.

*Examples:*

Basic usage:

```javascript
while (IsElementPresent(locator1 = "AssetCategorization#TAGS_REMOVE_ICON_GENERIC")) {
	Click.click(locator1 = "AssetCategorization#TAGS_REMOVE_ICON_GENERIC");
}
```

Usage with `maxIterations` parameter:

```javascript
while (IsElementPresent(locator1 = "AssetCategorization#TAGS_REMOVE_ICON_GENERIC") && (maxIterations = "16")) {
	Click(locator1 = "AssetCategorization#TAGS_REMOVE_ICON_GENERIC");
}
```

**Valid child snippets:**

- All statements.
- All blocks except:
    - `definition`
    - `setUp`
    - `tearDown`
    - `test`
    - `macro`

**Valid parent blocks:**

- All blocks except `definition`.

#### `for` loops

`for` blocks are loops that iterate through each item in a given collection.
Currently, the only valid collections are lists and tables. Note that tables are
only for use in conjunction with Poshi Prose syntax.

*Examples:*

List example #1
```javascript
var tagNameList = "tag1,tag2";

for (var tagName : list "${tagNameList}") {
	Type.clickAtType(locator1 = "AssetCategorization#CATEGORIES_SEARCH_FIELD", value1 = "${tagName}");

	AssertClick(locator1 = "Button#ADD_TAGS", value1 = "Add");
}
```

List example #2

```javascript
for (var panel : list "Source,Filter,Custom User Attributes,Ordering and Grouping") {
	AssertElementPresent(locator1 = "Panel#PANEL_COLLAPSED", key_panel = "${panel}");
}
```

Raw table example:

```javascript
var RawTable rawTable = new RawTable("${table}");

for (var row : table "${rawTable}") {
	TableEcho.echoTwoVars(
		v0 = "${row[0]}",
		v1 = "${row[1]}"
	);
}
```

Hash table example:

```javascript
var RowsHashTable rowsHashTable = new RowsHashTable("${table}");

for (var row : table "${rowsHashTable}") {
	TableEcho.echoTwoVars(
		v0 = "${row.hash('project_id')}",
		v1 = "${row.hash('status')}"
	);
}
```

**Valid child snippets:**

- All statements.
- All blocks except:
    - `definition`
    - `setUp`
    - `tearDown`
    - `test`
    - `macro`

**Valid parent blocks:**

- All blocks except `definition`.

## Writing a Test

### Creating a `.testcase` file

All `.macro` and `.testcase` files must contain and start with a `definition`
block. All other blocks and statements are contained within this `definition`
block. [Annotations](#definition-annotations) can also be used before
`definition` blocks.

*Example:*

```javascript
definition {
	...
}
```

**Valid child snippets:**

- All blocks except:
    - For `.testcase` files
        - `definition`
        - `setUp`
        - `tearDown`
        - `test`
        - `property`
        - `var`
    - For `.macro` files
        - `macro`
        - `var`

**Valid parent blocks:**

- None

### Adding a `test`

In a `.testcase file`, `test` blocks are used to contain a test case. These are
required to create test cases. [Annotations](#test-annotations) can also be used
before individual tests.

*Example:*

```javascript
test TestCaseName {
	...
}
```

**Valid child snippets:** All statements. All blocks except `definition`,
`setUp`, `tearDown`, `test`, and `macro`.

**Valid parent blocks:** `definition`

### `setUp` and `tearDown` blocks

`setUp` and `tearDown` blocks are used in `.testcase` files and run before and
after each `test` block, respectively. These can contain common pieces of test
code that are run before and/or after each test. These blocks are not required
though.

*Examples:*

```javascript
setUp {
	...
}
```

```javascript
tearDown {
	...
}
```

**Valid child snippets:**

- All statements.
- All blocks except:
    - `definition`
    - `setUp`
    - `tearDown`
    - `test`
    - `macro`

**Valid parent blocks:**

 - `definition`

### Annotations

Annotations in Poshi Script are used to store additional meta data for testcases
and testcase files. The syntax is similar to an assignment with the annotation
variable being prepended by an `@`, followed by an `=`, then followed by a
double quoted string of the value.

#### `definition` annotations

These are valid annotations for a [`definition` block](#definition-block).

##### `@component-name`

Every test file requires a `component-name` annotation; valid component name
values must be listed per project in a `poshi-runner-ext.properties` file by the
[`component.names`](https://github.com/liferay/com-liferay-poshi-runner/blob/6339925/poshi-runner/src/main/resources/poshi-runner.properties#L18)
property.

*Example:*

```javascript
@component-name = "portal-acceptance"
definition {
	...
}
```

##### `@ignore`

When this annotation is set, this test is not stored when Poshi files are
loaded into the JVM.

*Examples:*

```javascript
@ignore = "Reason for ignoring all test cases"
definition {
	...
}
...
@ignore = "Reason for ignoring specific test case"
test TestName {
    ...
}
```

##### `@ignore-command-names`

This can be set to a comma delimited list of testcase command names within the
current file; those tests are not stored when Poshi files are loaded into the
JVM.

*Example:*

```javascript
@ignore-command-names = "TestCaseCommandName1, TestCaseCommandName2"
definition {
	...
}
```

#### `test` annotations

These are valid annotations for [`test` blocks](#test-blocks).

##### `@description`

This is used to describe the use case of the test.

*Example:*
```javascript
@description = "Ensure that the super admin can add pages, add portlets, navigate to the product menu, use the WYSIWYG editor, and view alert messages."
test Smoke {
	...
}
```

##### `@ignore`

When set to `true`, this test is not stored when Poshi files are loaded in the
JVM.

*Example:*

```javascript
@ignore = "true"
test Smoke {
	...
}
```

##### `@priority`

This is used to denote the priority of the testcase.

*Examples:*

```javascript
@priority = "5"
test Smoke {
	...
}
```

## Other

### Comments

Comments are used to add descriptions or notes within the test code. They're
also used to wrap existing code so it is not parsed and executed.

#### Inline comments

To use an inline comment, simply prepending the line with `//` will *comment
out* that line.

*Examples:*

```javascript
// This is an inline comment. Only one line is allowed and surrounding white space is not preserved.
```

```javascript
// Multiple inline comments can be used if desired.
// This is equivalent to wrapping text in the multiline syntax.
```

#### Multiline comments

To wrap multiple lines of text or code using a multiline comment, prepend the
section with `/*` and end the section with `*/`.

*Example:*

```javascript
/*

This is a multiline comment.
Surrounding white space will be preserved.

*/
```

### Grouping `task`s

`task` blocks are used to group snippets together and provide a description of
that group of snippets. The output is displayed in the console log and in the
Poshi summary log. Functionally, it makes no impact.

*Example:*

```javascript
task ("Add a blogs entry called 'Blogs Entry1 Title' with content 'Blogs Entry1 Content'") {
	Navigator.openURL();

	ProductMenu.gotoPortlet(
		category = "Content",
		panel = "Site Administration",
		portlet = "Blogs"
	);

	Blogs.addEntry(
		entryContent = "Blogs Entry1 Content",
		entryTitle = "Blogs Entry1 Title"
	);
}
```

**Valid child snippets:**

- All statements.
- All blocks except:
    - `definition`
    - `setUp`
    - `tearDown`
    - `test`
    - `macro`

**Valid parent blocks:**

- All blocks except `definition`.

## Debugging Syntax Errors

### How to run Poshi Script validation

Syntax validation for Poshi Script is built into the existing Poshi validation
logic. To run normal validation, use your project's script that has been
configured to run Poshi validation. This can be performed by running `gradlew
validatePoshi` from a `build.gradle` file, or `ant -f build-test.xml
run-poshi-validation` in liferay-portal branches.

If you need help identifying the error, please use the #poshi Slack channel or
contact @kenjiheigel for help.

### Types of Poshi Script syntax errors

#### Data Loss

This is the standard error message that is displayed when invalid syntax cannot
be specifically identified. Usually, this means that a more specific error
should be caught, and @kenjiheigel should be notified to create a more helpful
error message.

*Example:*

```
[exec] Data loss has occurred while parsing Poshi Script at:
[exec] /Users/kenji/Projects/github/liferay-portal/master/portal-web/test/functional/com/liferay/portalweb/tests/enduser/wem/pages/contentpages/ContentPages.testcase:1
```

#### Unbalanced Code

This error occurs when a bracket (`{`, `}`, `[`, `]`, `(`, `)`) is not properly
balanced within the file. Usually, these errors are resolved by slowly removing
chunks of code. Sometimes, the exception will identify the exact location of the
issue.

*Examples:*

```
[exec] Unexpected closing boundary at:
[exec] /Users/kenji/Projects/github/liferay-portal/master/portal-web/test/functional/com/liferay/portalweb/tests/enduser/wem/pages/contentpages/ContentPages.testcase:319
[exec]             ); ]
[exec]                ^
```

```
[exec] Unmatched opening boundary at:
[exec] /Users/kenji/Projects/github/liferay-portal/master/portal-web/test/functional/com/liferay/portalweb/tests/enduser/wem/pages/contentpages/ContentPages.testcase:2
[exec] definition {
[exec]            ^
```

#### Invalid Poshi Script syntax

This error reports the syntax error's approximate line number and prints out the
corresponding snippet of Poshi Script. Exact messaging and column number are not
currently available, but will be made available in the future.

*Example:*

```
[exec] Invalid Poshi Script syntax at:
[exec] /Users/kenji/Projects/github/liferay-portal/master/portal-web/test/functional/com/liferay/portalweb/tests/enduser/wem/pages/contentpages/ContentPages.testcase:313
[exec]
[exec]
[exec]         task ("Click away from the header so the popover will disappear") {
[exec]             Click(
[exec]                 locator1 = "PageEditor#EMPTY_MESSAGE"
[exec]             )
[exec]
[exec]             AssertElementNotPresent(
[exec]                 locator1 = "PageEditor#CONTENT_PAGE_DISABLED_POPOVER"
[exec]             );
[exec]         }
```