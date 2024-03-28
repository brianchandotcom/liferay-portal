# Poshi Script

Poshi Script is a domain-specific language created to simplify the writing of
functional tests that run on the Poshi Runner framework (which is built on
[Selenium WebDriver](https://www.selenium.dev/documentation/webdriver/)).

This document details the syntax structure of Poshi Script. To learn how to best
use Poshi, more information is available at
[Poshi Test Automation](https://learn.liferay.com/web/guest/w/dxp/building-applications/tooling/poshi-test-automation).

## Table of Contents

1. [Introduction](#introduction)
    - [Origins](#origins)
    - [Key Syntax Updates](#key-syntax-updates)

1. [Variables](#variables)
    - [Declaring and assigning variables](#declaring-and-assigning-variables)
        - [`var` assignments](#var-assignments)
            - [Integers](#integers)
            - [Basic Strings](#basic-strings)
            - [Multiline Strings](#multiline-strings)
            - [Assigning `var`'s to macro invocations](#assigning-vars-to-macro-invocations)
            - [Assigning `var`'s to class/method invocations](#assigning-vars-to-classmethod-invocations)
        - [Referencing a `var`](#referencing-a-var)
            - [String Concatenation](#string-concatenation)
        - [`property` assignments](#property-assignments)

1. [Using Poshi functions, Poshi macros, and Java methods](#using-poshi-functions-poshi-macros-and-java-methods)
    - [Creating a function](#creating-a-function)
        - [Creating a `.function` file](#creating-a-function-file)
        - [Creating a function command](#creating-a-function-command)
        - [Default functions](#default-functions)
    - [Creating a macro](#creating-a-macro)
        - [Creating a `.macro` file](#creating-a-macro-file)
        - [Creating a macro command](#creating-a-macro-command)
        - [Returning values](#returning-values)
    - [Creating or requesting Java functionality](#creating-or-requesting-java-functionality)
    - [Executing Poshi functions, Poshi macros, and Java methods](#executing-poshi-functions-poshi-macros-and-java-methods)
        - [Executing Poshi functions](#executing-poshi-functions)
        - [Executing Poshi macros](#executing-poshi-macros)
        - [Executing Java Methods](#executing-java-methods)
        - [Additional utilities](#additional-utilities)
            - [`echo`](#echo)
            - [`fail`](#fail)

1. [Control Flow](#control-flow)
    - [Conditional logic](#conditional-logic)
        - [`if`, `else if`, and `else` conditions](#if-else-if-and-else-conditions)
    - [Conditional expressions](#conditional-expressions)
        - [isSet](#isset)
        - [equals](#equals)
        - [contains](#contains)
        - [Conditional Poshi function](#conditional-poshi-function)
        - [Logical operators (and, or, not)](#logical-operators-and-or-not)
            - [And](#and)
            - [Or](#or)
            - [Not](#not)
    - [Loops](#loops)
        - [`while` loops](#while-loops)
        - [`for` loops](#for-loops)
        - [`break` and `continue` in loops](#break-and-continue-in-loops)

1. [Writing a Test](#writing-a-test)
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
            - [`@disable-webdriver`](#disable-webdriver)
            - [`@ignore`](#ignore-1)
            - [`@priority`](#priority)

1. [Other](#other)
    - [Comments](#comments)
        - [Inline comments](#inline-comments)
        - [Multiline comments](#multiline-comments)
    - [Grouping tasks](#grouping-tasks)

1. [Debugging Syntax Errors](#debugging-syntax-errors)
    - [How to run Poshi Script validation](#how-to-run-poshi-script-validation)
    - [Types of Poshi Script syntax errors](#types-of-poshi-script-syntax-errors)
        - [Data Loss](#data-loss)
        - [Unbalanced Code](#unbalanced-code)
        - [Invalid Poshi Script syntax](#invalid-poshi-script-syntax)

## Introduction

### Origins

Poshi Script is a domain-specific language (DSL) used to write Poshi functional
tests. Before Poshi Script, the syntax used for test writing in Poshi was XML-
based in syntax structure and is referred to now as Poshi XML. In order to make
the syntax more readable, Poshi Script was created, which is more structurally
similar to Groovy and JavaScript syntax.

The Poshi XML structure is still used when parsing Poshi files and is still
the primary data structure used to contain Poshi file data in run time. This
allows syntax changes to be made as needed (and the automatic regeneration of
those changes) to improve usability and readability for users.

### Key Syntax Updates

- **Poshi XML to Poshi Script**. This was a major syntax change from XML syntax
  to new DSL. *(September 2018)*
- **Improved variable references**. This was a minor change that removed
  unnecessary quotes from variable references and integer declarations.
  *(August 2023)*

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

1. Variable name

1. Equals sign

1. Notation of a valid assignment

For specific examples, see below.

#### `var` assignments

`var` assignments are how information is stored and referenced within Poshi
files. These are generally strings and can be directly assigned from some
invocations. These assignments can also reference objects and can support
typing, but additional development is still necessary to polish this feature.
For various ways to assign `var`s, see the examples below.

##### Integers

```javascript
var index = 1;
```

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
var breadcrumbNameUppercase = StringUtil.upperCase(${breadcrumbName});
```

#### Referencing a `var`

Assigned `var`s can be referenced in parameters or new variable declarations 
using the `${}` notation.

*Examples:*

Simple variable reference:

```javascript
var newVariable = ${oldVariable};
```

Macro execution that passes in a variable reference:

```
Navigator.openSpecificURL(url = ${rssURL}); // macro execution
```

Java method execution that passes in a variable reference:

```
var breadcrumbNameUppercase = StringUtil.upperCase(${breadcrumbName}); 
```

##### String Concatenation

`var`s can be referenced in any string using the `${}` notation to simplify 
concatenation.

*Examples:*

```javascript
var userEmailAddress = "${firstName}.${lastName}@liferay.com";

var upperCaseFilePath = StringUtil.upperCase("${baseFilePath}/${fileName}"); 
```

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

Functions are the building blocks of all tests and exist as wrappers to the
methods defined in (LiferaySelenium.java)[https://github.com/liferay/liferay-portal/blob/master/modules/test/poshi/poshi-core/src/main/java/com/liferay/poshi/core/selenium/LiferaySelenium.java].
Functions can only reference other functions, Java utility methods, and 
primarily the methods in LiferaySelenium.java.

Variables can be passed into functions as they are referenced.

#### Creating a `.function` file

Any Poshi Script file must first be declared with a `definition` block. All
other syntax is contained within this `definition` block.

`Function.function`:

```javascript
definition {

}
```

#### Creating a function command

Within the `definition` block of the `.function` file, `function` blocks are 
used to define individual macros. To do so, use the `function` keyword, followed 
by a string identifier (note that each function name must be unique) that is 
used to reference the function command.

To reference a method from (LiferaySelenium.java)[https://github.com/liferay/liferay-portal/blob/master/modules/test/poshi/poshi-core/src/main/java/com/liferay/poshi/core/selenium/LiferaySelenium.java],
simply use `selenium.` and the method name. This syntax is pending changes that
will enforce a command signature and explicit variables that are passed into the 
LiferaySelenium method. 

`Open.function`:

```javascript
definition {
	function open {
		selenium.open();
	}
}
```

#### Default functions

For every function file, a default function command can be denoted as an 
[annotation](#annotations) in the function file using `@default`. To reference 
the default function, see 
[Executing Poshi functions](#executing-poshi-functions).

`Open.function`:

```javascript
@default = "open"
definition {
	function open {
		selenium.open();
	}
}
```

### Creating a macro

Macros exist as larger building blocks of reusable code. If a function contains
one generic action of a test (clicking on a web element, typing into a field, 
etc.), a macro may be a collection of actions, or a more specific use of that 
action (logging into a website, clicking a button on a specific page).

Variables can be passed into macros as they are referenced.

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
	var breadcrumbNameUppercase = StringUtil.upperCase(${breadcrumbName});

	AssertTextEquals(
		locator1 = "Breadcrumb#BREADCRUMB_ENTRY",
		value1 = ${breadcrumbNameUppercase}
	);

	return ${breadcrumbNameUppercase};
}
```

### Creating or requesting Java functionality

Java method executions are limited to methods from classes in the
[`com.liferay.poshi.core.util`](https://github.com/liferay/com-liferay-poshi-core/tree/master/poshi-core/src/main/java/com/liferay/poshi/core/util) and
[`com.liferay.poshi.runner.util`](https://github.com/liferay/com-liferay-poshi-runner/tree/master/poshi-runner/src/main/java/com/liferay/poshi/runner/util) packages.

If additional functionality is required, please contact @kenjiheigel or
@CalumR23.

### Executing Poshi functions, Poshi macros, and Java methods

#### Executing Poshi functions

Poshi functions have the following required parameters: *locator1*, *value1*,
*locator2*, *value2* and/or *value3*.

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
	value1 = ${kbArticleTitle},
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
`com.liferay.poshi.runner.util` and `com.liferay.poshi.core.util`.

Parameter names are not required when invoking Java methods. Parameters may be
passed using raw values wrapped in double quotes (different from the single
quoted syntax used in Poshi XML for *var method* parameters). Additionally, the
full class name or simple class name can be used to invoke methods.

*Examples:*

Java method invocation using the full class name:

```javascript
com.liferay.poshi.runner.util.JSONCurlUtil.post(${curl});
```

Java method invocation using the simple class name:

```javascript
JSONCurlUtil.post(${curl});
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

## Control Flow

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
if (${pageStaging} == "true") {
	Navigator.gotoStagedSitePage(
		pageName = ${pageName},
		siteName = ${siteName}
	);
}
else if (${siteURL} == "true") {
	Navigator.gotoSitePage(
		pageName = ${pageName},
		siteName = ${siteName}
	);
}
else {
	Navigator.gotoPage(pageName = ${pageName});
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

1. A `==` to denote an equality evaluation.

1. The second string.

Please note that the `!=` operator is not currently supported.

*Example:*

```javascript
if (${check} == "true") {
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
if ((IsElementPresent(locator1 = "Blogs#ADD_BLOGS_ENTRY")) && (${check} == "true") && (isSet(duplicate))) {
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
if ((IsElementPresent(locator1 = "Blogs#ADD_BLOGS_ENTRY")) || (${check} == "true") || (isSet(duplicate))) {
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
Currently, the only valid collections are lists and tables. 
*Examples:*

List example #1

```javascript
var tagNameList = "tag1,tag2";

for (var tagName : list ${tagNameList}) {
	Type.clickAtType(locator1 = "AssetCategorization#CATEGORIES_SEARCH_FIELD", value1 = ${tagName});

	AssertClick(locator1 = "Button#ADD_TAGS", value1 = "Add");
}
```

List example #2

```javascript
for (var panel : list "Source,Filter,Custom User Attributes,Ordering and Grouping") {
	AssertElementPresent(locator1 = "Panel#PANEL_COLLAPSED", key_panel = ${panel});
}
```

Raw table example:

```javascript
var rawTable = TableUtil.newTable(${table});

for (var row : table ${rawTable}) {
	TableEcho.echoTwoVars(
		v0 = "${row[0]}",
		v1 = "${row[1]}"
	);
}
```

Hash table example:

```javascript
var rowsHashTable = TableUtil.newTableWithRowNames(${table});

for (var row : table ${rowsHashTable}) {
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

#### `break` and `continue` in loops

Within a `for` or `while` loop you can use the `break` or `continue` keyword 
if a certain condition is met within the loop.

`break` example:

```javascript
for(var test : list "1,2,3,4,5,6,7,8,9") {
	if(${test} == 3){
		break;
	}
	echo(${test});
}
```

In the example above, when the loop reaches the value of `3` it exits the loop 
as the break condition would be met.

`continue` example:

```javascript
for(var test : list "1,2,3,4,5,6,7,8,9") {
	if(${test} == 3){
		continue;
	}
	echo(${test});
}
```

In the example above, when the loop reaches the value of `3` it skips to the 
next iteration in the loop as the continue condition would be met.

**Valid parent blocks:**

- Within `while` or `for` blocks.

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

##### `@disable-webdriver`

This can be set to `true` which will disable the use of Selenium WebDriver and
will not start up a browser. This is set above the testcase you wish to disable
webdriver in or at the beginning of a `.testcase` file to apply to all testcases
within that file.

*Example:*

```javascript
@disable-webdriver= "true"
test TestName {
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

### Grouping tasks

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