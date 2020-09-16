# Poshi Runner Change Log

## 1.0.274

### Utils

* [POSHI-45](https://issues.liferay.com/browse/POSHI-45) - Investigate ArchiveUtil jar issues
* [POSHI-96](https://issues.liferay.com/browse/POSHI-96) - As a AC Test Engineer, I would like to start WebDriver util passing BrowserMob Proxy as a DesiredCapability, so I can retrieve the HAR file and validate the requests

## 1.0.272

### WebDriver

* [POSHI-91](https://issues.liferay.com/browse/POSHI-91) - As an Edge browser tester, I would like to use the new built-in webdriver
* [POSHI-93](https://issues.liferay.com/browse/POSHI-93) - Make waitForLiferayEvent not fail the test

## 1.0.271

### WebDriver

* [POSHI-88](https://issues.liferay.com/browse/POSHI-88) - Make executeJavaScript more flexible to receive strings and xpath locators

## 1.0.270

### Other

* [LPS-105380](https://issues.liferay.com/browse/LPS-105380) - SourceFormatter Improvements
* [LPS-114165](https://issues.liferay.com/browse/LPS-114165) - Add missing Override annotations
* [LPS-114513](https://issues.liferay.com/browse/LPS-114513) - Upgrade javax.mail to 1.6.2 to be able to use mail.<prot>.ssl.protocols

### Runner

* [POSHI-89](https://issues.liferay.com/browse/POSHI-89) - Set a max length for each Poshi Warning

### Webdriver

* [LRQA-58977](https://issues.liferay.com/browse/LRQA-58977) - Address Safari 12 failures on Environment Tester

## 1.0.269

### Runner

* [POSHI-79](https://issues.liferay.com/browse/POSHI-79) - Delete lingering artifacts in the output directory when a Poshi test starts running
* [POSHI-80](https://issues.liferay.com/browse/POSHI-80) - String literal newline character doesn't get translated in JSON WS API Calls
* [POSHI-81](https://issues.liferay.com/browse/POSHI-81) - As a Test Engineer, I'd like validation in JSONCurlUtil.java to be changed so that I can pass in multiple URLs as arguments when creating JSON macros

### Utils

* [POSHI-46](https://issues.liferay.com/browse/POSHI-46) - UploadCommonFile can not copy the target file to destination on win10 OS
* [POSHI-86](https://issues.liferay.com/browse/POSHI-86) - As a test engineer, I should be able to delete a file

## 1.0.266

### Other

* [LPS-105380](https://issues.liferay.com/browse/LPS-105380) - SourceFormatter Improvements

### WebDriver

* [POSHI-71](https://issues.liferay.com/browse/POSHI-71) - Clear caches/history for IE before/after each test
* [POSHI-75](https://issues.liferay.com/browse/POSHI-75) - As a test engineer / developer, I should be able to easily execute and wait for custom javascript test code

## 1.0.264

### Other

* [LPS-105380](https://issues.liferay.com/browse/LPS-105380) - SourceFormatter Improvements
* [LPS-111020](https://issues.liferay.com/browse/LPS-111020) - Use of library with known vulnerability: dom4j-1.6.1
* [LRCI-1129](https://issues.liferay.com/browse/LRCI-1129) - Add Liferay Components & Teams to Spira

### WebDriver

* [POSHI-65](https://issues.liferay.com/browse/POSHI-65) - Use new method to type in CodeMirror editor
* [POSHI-68](https://issues.liferay.com/browse/POSHI-68) - Type slower on type retries

## 1.0.261

### Gradle

* [POSHI-43](https://issues.liferay.com/browse/POSHI-43) - Match com-liferay-poshi-runner source formatting to master

### Other

* [LPS-105380](https://issues.liferay.com/browse/LPS-105380) - SourceFormatter Improvements
* [LPS-109946](https://issues.liferay.com/browse/LPS-109946) - *Util class is static, *Helper class is not
* [LRCI-1145](https://issues.liferay.com/browse/LRCI-1145) - Change relevant queries from portal.upstream to portal.acceptance

### PQL

* [POSHI-60](https://issues.liferay.com/browse/POSHI-60) - Add additional validation messaging to PQL query error logic

### Release

* [POSHI-59](https://issues.liferay.com/browse/POSHI-59) - Migrate changelog to repository

### Runner

* [POSHI-61](https://issues.liferay.com/browse/POSHI-61) - Catch and retry: "org.openqa.selenium.WebDriverException: unknown error: unable to discover open pages"
* [POSHI-63](https://issues.liferay.com/browse/POSHI-63) - As a test engineer / developer, it would be helpful to for Poshi to retry failures on CI to lower flaky results

### WebDriver

* [POSHI-30](https://issues.liferay.com/browse/POSHI-30) - Add type validation in the event that the full text is not typed correctly.
* [POSHI-35](https://issues.liferay.com/browse/POSHI-35) - Deal with large log files in poshi's assertConsoleErrors
* [POSHI-57](https://issues.liferay.com/browse/POSHI-57) - Add way to drag the second fragment to the first fragment's position in react editor
* [POSHI-58](https://issues.liferay.com/browse/POSHI-58) - Add robotType and robotTypeShortcut methods

## 1.0.255

### PQL

* [LRCI-1084](https://issues.liferay.com/browse/LRCI-1084) - PQL will assume boolean & integers variables for all comparison operators
* [LRCI-1092](https://issues.liferay.com/browse/LRCI-1092) - Remove unused poshi property that adds complexity

### WebDriver

* [POSHI-48](https://issues.liferay.com/browse/POSHI-48) - Find solution for Page Editor drag and drop in ChromeDriver

## 1.0.252

### Gradle

* [POSHI-43](https://issues.liferay.com/browse/POSHI-43) - Match com-liferay-poshi-runner source formatting to master

### Maintenance

* [POSHI-49](https://issues.liferay.com/browse/POSHI-49) - Fix Poshi SF post subrepo changes

### Runner

* [POSHI-38](https://issues.liferay.com/browse/POSHI-38) - java.util.concurrent.RejectedExecutionException sometimes happens

### Validation

* [POSHI-44](https://issues.liferay.com/browse/POSHI-44) - Poshi Script validation information isn't being displayed
* [POSHI-50](https://issues.liferay.com/browse/POSHI-50) - Investigate sporadic Poshi validation issues

## 1.0.249

### Runner

* [POSHI-39](https://issues.liferay.com/browse/POSHI-39) - Improve PoshiRunnerContext file reading and processing time

### Script

* [LRCI-295](https://issues.liferay.com/browse/LRCI-295) - Poshi Syntax error in while loops with maxIterations parameter
* [POSHI-4](https://issues.liferay.com/browse/POSHI-4) - Investigate conditional logic in poshi upstream

### Utils

* [POSHI-3](https://issues.liferay.com/browse/POSHI-3) - Update ArchiveUtil to handle adding the manifest file when creating jars
* [POSHI-41](https://issues.liferay.com/browse/POSHI-41) - Create new methods in FileUtil and BaseWebDriverImpl needed for Virtual Instance Test Module

### Validation

* [LRCI-911](https://issues.liferay.com/browse/LRCI-911) - Allow for simple class name usage of util classes

## 1.0.246

### Utils

* [LRQA-54560](https://issues.liferay.com/browse/LRQA-54560) - Add substring method to StringUtil.java

### Webdriver

* [LRCI-771](https://issues.liferay.com/browse/LRCI-771) - Allow chrome as a headless browser for functional testing

## 1.0.243

### Script

* [LRCI-743](https://issues.liferay.com/browse/LRCI-743) - Poshi Runner resource jars paths are not being set correctly for Poshi Script validation

## 1.0.242

### Backend

* [LRCI-683](https://issues.liferay.com/browse/LRCI-683) - Bug: Reflection's random method order creates unexpected and inconsistent behavior when invoking overloaded WebDriver methods

### Script

* [LRCI-700](https://issues.liferay.com/browse/LRCI-700) - Bug: Poshi Script syntax doesn't evaluate the last statement of a block

## 1.0.239

### Backend

* [LRCI-383](https://issues.liferay.com/browse/LRCI-383) - Re implement takeScreenshot
* [LRCI-564](https://issues.liferay.com/browse/LRCI-564) - AssertLocation fails with ClassCastException
* [LRCI-571](https://issues.liferay.com/browse/LRCI-571) - BaseWebDriverImpl.assertJavaScriptErrors throws NPE

### Logger

* [LRCI-641](https://issues.liferay.com/browse/LRCI-641) - Move off of rawgit before October 2019

### Script

* [LRCI-580](https://issues.liferay.com/browse/LRCI-580) - Add new Poshi operators such as !=, ^, +, -, *, / instead of having to use MathUtil or StringUtil for simple operations
* [LRCI-647](https://issues.liferay.com/browse/LRCI-647) - Make Poshi Script logical condition syntax more flexible

### Webdriver

* [LRCI-203](https://issues.liferay.com/browse/LRCI-203) - Add verify function in POSHI
* [LRCI-270](https://issues.liferay.com/browse/LRCI-270) - Refactor "waitFor*" methods in BaseWebDriverImpl to reuse logic

## 1.0.232

### Utils

* [LRCI-462](https://issues.liferay.com/browse/LRCI-462) - Update uploadCommonFile to support archiving lar file

## 1.0.231

### Backend

* [LRCI-294](https://issues.liferay.com/browse/LRCI-294) - Poshi BUG: java.lang.ClassCastException: junit.framework.AssertionFailedError cannot be cast to java.lang.Exception

### Webdriver

* [LRCI-336](https://issues.liferay.com/browse/LRCI-336) - Implement javascript drag and drop to use with HTML5 drag and drop.
* [LRCI-343](https://issues.liferay.com/browse/LRCI-343) - Fix ArchiveUtil for Windows

## 1.0.229

### Backend

* [LRCI-225](https://issues.liferay.com/browse/LRCI-225) - Wrap LiferaySelenium calls with a timeout & simplify exception stacktrace

### Script

* [LRCI-126](https://issues.liferay.com/browse/LRCI-126) - Fix Poshi Script parsing with inline commented Poshi Script

### Utils

* [LRCI-213](https://issues.liferay.com/browse/LRCI-213) - Add new Util functions essential to implementing JSON Portal calls
* [LRCI-176](https://issues.liferay.com/browse/LRCI-176) - Added ArchiveUtil.java

### Webdriver

* [LRCI-160](https://issues.liferay.com/browse/LRCI-160) - Add methods to wait for input fields to be (not) editable
* [LRCI-176](https://issues.liferay.com/browse/LRCI-176) - Update uploadCommonFile to detect if a path is a directory and archive the file appropriately
* [LRCI-239](https://issues.liferay.com/browse/LRCI-239) - Fix typeAceEditor for invisible elements

## 1.0.222

### PQL

* [LRCI-65](https://issues.liferay.com/browse/LRCI-65) - Add a pure PQL execution command in poshi runner

### Resources

* [LRCI-47](https://issues.liferay.com/browse/LRCI-47) - Remove unused/obsolete branch specific poshi runner resource files

### Script

* [LRCI-48](https://issues.liferay.com/browse/LRCI-48) - Bug: Poshi Script line numbers are incorrect for definition elements
* [LRCI-49](https://issues.liferay.com/browse/LRCI-49) - Bug: Function attributes are not generated into Poshi Script correctly in .function files
* [LRCI-69](https://issues.liferay.com/browse/LRCI-69) - Rework Poshi Script validation
* [LRCI-86](https://issues.liferay.com/browse/LRCI-86) - Standardize Poshi Script snippets in console output

### Validation

* [LRCI-26](https://issues.liferay.com/browse/LRCI-26) - Allow var elements in functions
* [LRCI-56](https://issues.liferay.com/browse/LRCI-56) - Validate when duplicate var names are used in execute elements

### Webdriver

* [LRQA-47609](https://issues.liferay.com/browse/LRQA-47609) - AssertTextEquals#assertPartialText function throws false exception msg

## 1.0.218

### Webdriver

* [LRCI-25](https://issues.liferay.com/browse/LRCI-25) - Implement assertPartialTextCaseInsensitive in BaseWebDriverImpl

## 1.0.217

### Backend

* [LRQA-46795](https://issues.liferay.com/browse/LRQA-46795) - Fix Poshi validation for test.case.required.property.names and update for test.case.available.property.values

### Script

* [LRQA-45601](https://issues.liferay.com/browse/LRQA-45601) - Print out line numbers in error snippets for Poshi Script syntax errors

### Utils

* [LRQA-46717](https://issues.liferay.com/browse/LRQA-46717) - Intermittent error in prepare-poshi-runner-properties

## 1.0.214

### Script

* [LRQA-46447](https://issues.liferay.com/browse/LRQA-46447) - Account for namespaced functions/macros

### WebDriver

* [LRQA-46064](https://issues.liferay.com/browse/LRQA-46064) - Bug: Path for file downloads is not being set correctly for Chrome

## 1.0.212

### Backend

* [LRQA-45521](https://issues.liferay.com/browse/LRQA-45521) - Update poshi file load order

### Logger

* [LRQA-45641](https://issues.liferay.com/browse/LRQA-45641) - Poshi line numbers are calculated incorrectly on Windows

### Resources

* [LRQA-45617](https://issues.liferay.com/browse/LRQA-45617) - Fix the Poshi node factory to read in files from the class path (for resource jars)

### Script

* [LRQA-45587](https://issues.liferay.com/browse/LRQA-45587) - Update Poshi Script documentation
* [LRQA-45588](https://issues.liferay.com/browse/LRQA-45588) - Validate for missing commas between variables in functions and macros
* [LRQA-45770](https://issues.liferay.com/browse/LRQA-45770) - Line number interpretation for Poshi description annotation is incorrect

### Utils

* [LRQA-45648](https://issues.liferay.com/browse/LRQA-45648) - BUG: cURL calls on Windows for Zendesk is broken

## 1.0.207

### Backend

* [LRQA-45521](https://issues.liferay.com/browse/LRQA-45521) - Update poshi file load order

### Logger

* [LRQA-45514](https://issues.liferay.com/browse/LRQA-45514) - Poshi script log does not contain expand/collapse button for macros

### Resources

* [LRQA-45488](https://issues.liferay.com/browse/LRQA-45488) - The 7.1.x poshi resource jar is causing duplicate namespace issue in 7.1.x

### Script

* [LRQA-45468](https://issues.liferay.com/browse/LRQA-45468) - Ensure data loss does not occur when using Poshi Script translation
* [LRQA-45549](https://issues.liferay.com/browse/LRQA-45549) - Validate for missing semi colons in statements

### Utils

* [LRQA-43548](https://issues.liferay.com/browse/LRQA-43548) - Create documentation for Poshi properties

## 1.0.205

### Backend

* [LRQA-45209](https://issues.liferay.com/browse/LRQA-45209) - Temporarily exclude certain files from Poshi Script source formatting

### Resources

* [LRQA-45286](https://issues.liferay.com/browse/LRQA-45286) - Allow tag name as part of poshi resource jar pattern

### Script

* [LRQA-45379](https://issues.liferay.com/browse/LRQA-45379) - Add support for parsing nested logical operators
* [LRQA-45390](https://issues.liferay.com/browse/LRQA-45390) - Allow end characters (';' and '}') to be used as the last character in inline comments
* [LRQA-45431](https://issues.liferay.com/browse/LRQA-45431) - Account for NPE's when getting line numbers

### Webdriver

* [LRQA-22065](https://issues.liferay.com/browse/LRQA-22065) - BUG: AssertLiferayErrors does not catch shutdown/startup errors
* [LRQA-45011](https://issues.liferay.com/browse/LRQA-45011) - Sporadic java.util.concurrent.TimeoutException in assertJavaScriptErrors after clicking a link

## 1.0.200

### Logging

* [LRQA-45001](https://issues.liferay.com/browse/LRQA-45001) - Find WARN level errors upon startup & shutdown within Poshi Runner

## 1.0.199

### Backend

* [LRQA-44470](https://issues.liferay.com/browse/LRQA-44470) - Regression: Static var values do not persist into subsequent command contexts when overriden
* [LRQA-44586](https://issues.liferay.com/browse/LRQA-44586) - Fix file loading / variable initialization for function files in Poshi Script

### Prose

* [LRQA-44599](https://issues.liferay.com/browse/LRQA-44599) - Add support for properties to allow test selection via PQL

### Script

* [LRQA-43938](https://issues.liferay.com/browse/LRQA-43938) - Rework exception infrastructure for Poshi Script parsing
* [LRQA-44079](https://issues.liferay.com/browse/LRQA-44079) - Update Poshi Script code balance logic to be more helpful
* [LRQA-44375](https://issues.liferay.com/browse/LRQA-44375) - Handle parsing exceptions within the tree to allow for multiple error messages

### Utils

* [LRQA-44653](https://issues.liferay.com/browse/LRQA-44653) - Error message is not shown if test fails on var-method calls

### Webdriver

* [LRQA-44531](https://issues.liferay.com/browse/LRQA-44531) - Chrome/ChromeDriver: inner text of 'option' tags that are children of 'select' elements are not trimmed
* [LRQA-44532](https://issues.liferay.com/browse/LRQA-44532) - ChromeDriver: Element not clickable or Element not focused errors
* [LRQA-44547](https://issues.liferay.com/browse/LRQA-44547) - ChromeDriver: The iframe WebElement object stored in Poshi is stale and child elements are not fully loaded
* [LRQA-44684](https://issues.liferay.com/browse/LRQA-44684) - Allow Poshi Runner to use a custom Chrome binary

## 1.0.191

### Backend

* [LRQA-44122](https://issues.liferay.com/browse/LRQA-44122) - Load PropsValues.TEST_INCLUDE_DIR_NAMES when running PoshiRunner
* [LRQA-44296](https://issues.liferay.com/browse/LRQA-44296) - Fix Poshi Runner unit tests
* [LRQA-44334](https://issues.liferay.com/browse/LRQA-44334) - Allow Generating a CSV Report to be Optional
* [LRQA-44401](https://issues.liferay.com/browse/LRQA-44401) - BUG: Poshi property 'test.include.dir.names' should not be required.

### Logger

* [LRQA-44108](https://issues.liferay.com/browse/LRQA-44108) - Errors are not logged correctly when failures are in conditionals
* [LRQA-44140](https://issues.liferay.com/browse/LRQA-44140) - BUG: Poshi Log does not display 'expand' button on macros

### Prose

* [LRQA-44117](https://issues.liferay.com/browse/LRQA-44117) - Add validation for Prose XML child element

### Webdriver

* [LRQA-43966](https://issues.liferay.com/browse/LRQA-43966) - Audit Poshi webdriver interactions to throw explicit exceptions when elements are not found

## 1.0.185

### Backend

* [LRQA-43447](https://issues.liferay.com/browse/LRQA-43447) - Write a script to list POSHI tests and status
* [LRQA-43690](https://issues.liferay.com/browse/LRQA-43690) - Poshi Runner ExecUtil will hang if the command output is high
* [LRQA-43924](https://issues.liferay.com/browse/LRQA-43924) - Poshi Runner should check if method parameters are locator keys

### Logger

* [LRQA-43806](https://issues.liferay.com/browse/LRQA-43806) - BUG: Variables button not showing up in Poshi Syntax Log

### Script

* [LRQA-43794](https://issues.liferay.com/browse/LRQA-43794) - Bug Fixes: Poshi Script translation
* [LRQA-43831](https://issues.liferay.com/browse/LRQA-43831) - Add the ability to translate function files
* [LRQA-43869](https://issues.liferay.com/browse/LRQA-43869) - Update poshi script java method syntax to use double quotes instead of single quotes
* [LRQA-43933](https://issues.liferay.com/browse/LRQA-43933) - Boost performance again

### Utils

* [LRQA-43548](https://issues.liferay.com/browse/LRQA-43548) - Create documentation for Poshi properties
* [LRQA-43550](https://issues.liferay.com/browse/LRQA-43550) - Remove obsolete and unused properties
* [LRQA-43970](https://issues.liferay.com/browse/LRQA-43970) - Avoid posting <?> characters to the Jenkins Console output in CI

### Webdriver

* [LRQA-43588](https://issues.liferay.com/browse/LRQA-43588) - Investigate stalling Upgrade Test runs

## 1.0.176

### Backend

* [LRQA-43513](https://issues.liferay.com/browse/LRQA-43513) - Fix regression from SF
* [LRQA-43596](https://issues.liferay.com/browse/LRQA-43596) - Fixes for new SF rules in poshi subrepo

### Logger

* [LRQA-43126](https://issues.liferay.com/browse/LRQA-43126) - Create logger handler for PoshiScript
* [LRQA-43446](https://issues.liferay.com/browse/LRQA-43446) - Remove XML references in logger handler and util classes
* [LRQA-43493](https://issues.liferay.com/browse/LRQA-43493) - Poshi report interactions do not work on an error occurs
* [LRQA-43509](https://issues.liferay.com/browse/LRQA-43509) - Update resource url to use newest CSS/JS

### Script

* [LRQA-41682](https://issues.liferay.com/browse/LRQA-41682) - Update syntax checks for poshi script 'conditionals' to use regular expressions

### Reporting

* [LRQA-43447](https://issues.liferay.com/browse/LRQA-43447) - Write a script to list POSHI tests and status

## 1.0.169

### Backend

* [LRQA-43124](https://issues.liferay.com/browse/LRQA-43124) - Return a PoshiElement directly when getting root elements
* [LRQA-43125](https://issues.liferay.com/browse/LRQA-43125) - Generate poshi log descriptor string
* [LRQA-43362](https://issues.liferay.com/browse/LRQA-43362) - Modify line number logic to get the correct poshi script line number
* [LRQA-43407](https://issues.liferay.com/browse/LRQA-43407) - StringUtil.countStartingNewLines should ignore whilespaces
* [LRQA-43429](https://issues.liferay.com/browse/LRQA-43429) - Modify HTML report template to remove XML references

### Resources

* [LRQA-43451](https://issues.liferay.com/browse/LRQA-43451) - Locators are not working correctly with namespace in poshi-runner

### Script

* [LRQA-41590](https://issues.liferay.com/browse/LRQA-41590) - Create documentation for Poshi Script

## 1.0.162

### Backend

* [LRQA-43145](https://issues.liferay.com/browse/LRQA-43145) - Poshi files in subrepositories are not read when executing poshi-runner
* [LRQA-43147](https://issues.liferay.com/browse/LRQA-43147) - BUG: Poshi 'assertConsoleTextPresent' should check throwable messages as well

## 1.0.158

### Backend

* [LRQA-37921](https://issues.liferay.com/browse/LRQA-37921) - Toggle off "Toggle" Feature within Poshi Runner
* [LRQA-41351](https://issues.liferay.com/browse/LRQA-41351) - Audit and potentially update poshi validation for 'var' elements

### Resources

* [LRQA-42976](https://issues.liferay.com/browse/LRQA-42976) - Resolve validation errors in the liferay-qa-portal-legacy-repository for version 7.0.10

### Script

* [LRQA-41436](https://issues.liferay.com/browse/LRQA-41436) - Consolidate syntax generation logic for 'execute' elements
* [LRQA-41684](https://issues.liferay.com/browse/LRQA-41684) - Allow for nested statement syntax to be more resilient
* [LRQA-41708](https://issues.liferay.com/browse/LRQA-41708) - Add generation rules for line breaks between poshi script blocks and statements

## 1.0.154

### Backend

* [LRQA-41817](https://issues.liferay.com/browse/LRQA-41817) - Make a white list of valid java classes that can be executed from Poshi
* [LRQA-42409](https://issues.liferay.com/browse/LRQA-42409) - Poshi Variables: Fix variable regressions
* [LRQA-42410](https://issues.liferay.com/browse/LRQA-42410) - Add integration tests
* [LRQA-42606](https://issues.liferay.com/browse/LRQA-42606) - Bug Fix: Allow for basic execute macros to be used after return elements

### Logger

* [LRQA-42698](https://issues.liferay.com/browse/LRQA-42698) - BUG: Logging of variables was removed from Poshi logger

### Webdriver

* [LRQA-42537](https://issues.liferay.com/browse/LRQA-42537) - Update LiferaySelenium's isTestName implementation to used the non namespaced test name
* [LRQA-42657](https://issues.liferay.com/browse/LRQA-42657) - Add selenium methods to determine if attribute is present in a given element

## 1.0.148

### Backend

* [LRQA-35911](https://issues.liferay.com/browse/LRQA-35911) - Add logic to override Poshi function, macro, testcase, and path files
* [LRQA-40567](https://issues.liferay.com/browse/LRQA-40567) - Remove all code to make the "${string?method}" notation work
* [LRQA-40568](https://issues.liferay.com/browse/LRQA-40568) - Only return one value for 'macro return' usage
* [LRQA-40985](https://issues.liferay.com/browse/LRQA-40985) - Poshi backend: Load poshi files in specified order by sorting list of file path URLs
* [LRQA-41130](https://issues.liferay.com/browse/LRQA-41130) - Give more specific error messages when calling external methods
* [LRQA-41205](https://issues.liferay.com/browse/LRQA-41205) - Global variables do not resolve correctly when referencing other global variables
* [LRQA-41229](https://issues.liferay.com/browse/LRQA-41229) - Set macro return variables in the static context if it exists
* [LRQA-41355](https://issues.liferay.com/browse/LRQA-41355) - Allow a 'static' attribute to be set with 'method' attributes for 'var' elements
* [LRQA-41432](https://issues.liferay.com/browse/LRQA-41432) - Make static vars available in all command contexts
* [LRQA-41472](https://issues.liferay.com/browse/LRQA-41472) - Variables should retain type when passed to and returned from macro calls
* [LRQA-42265](https://issues.liferay.com/browse/LRQA-42265) - Load only necessary testcase files when executing Poshi tests
* [LRQA-42279](https://issues.liferay.com/browse/LRQA-42279) - Bug: Some objects cannot be cast to String in PoshiRunnerExecutor
* [LRQA-42411](https://issues.liferay.com/browse/LRQA-42411) - Restore behavior and priority of evaluating 'execute vars'

### Logger

* [LRQA-42052](https://issues.liferay.com/browse/LRQA-42052) - Investigate Poshi Logger failures at HEAD
* [LRQA-42175](https://issues.liferay.com/browse/LRQA-42175) - Remove live mode on poshi runner logging

### Prose

* [LRQA-40666](https://issues.liferay.com/browse/LRQA-40666) - Implement prose file parsing
* [LRQA-40667](https://issues.liferay.com/browse/LRQA-40667) - Additional poshi runner package updates
* [LRQA-41044](https://issues.liferay.com/browse/LRQA-41044) - Poshi Prose change requests
* [LRQA-41056](https://issues.liferay.com/browse/LRQA-41056) - Add support for spaces in scenario names
* [LRQA-41057](https://issues.liferay.com/browse/LRQA-41057) - Add Setup and Teardown sections
* [LRQA-41058](https://issues.liferay.com/browse/LRQA-41058) - Add support for tables
* [LRQA-41059](https://issues.liferay.com/browse/LRQA-41059) - Add Feature section
* [LRQA-41060](https://issues.liferay.com/browse/LRQA-41060) - Allow steps to begin with '*'
* [LRQA-41228](https://issues.liferay.com/browse/LRQA-41228) - Prose files are failing Poshi Validation
* [LRQA-41311](https://issues.liferay.com/browse/LRQA-41311) - Allow comments in poshi prose files
* [LRQA-41313](https://issues.liferay.com/browse/LRQA-41313) - Add exception message when a match for prose is not found
* [LRQA-41314](https://issues.liferay.com/browse/LRQA-41314) - There is NPE when prose file is being executed
* [LRQA-41433](https://issues.liferay.com/browse/LRQA-41433) - Allow tags in prose files
* [LRQA-41434](https://issues.liferay.com/browse/LRQA-41434) - Allow commenting of lines beginning with a keyword (Given/When/Then/And)
* [LRQA-41624](https://issues.liferay.com/browse/LRQA-41624) - Add syntax for the new 'Table' related attributes (var & for elements)
* [LRQA-41662](https://issues.liferay.com/browse/LRQA-41662) - Allow descriptions in Scenarios
* [LRQA-41663](https://issues.liferay.com/browse/LRQA-41663) - Add support for optional text in the prose matching process
* [LRQA-41664](https://issues.liferay.com/browse/LRQA-41664) - Add support for alternation in the prose matching process
* [LRQA-42001](https://issues.liferay.com/browse/LRQA-42001) - Store prose statement when translating from Prose to XML

### Resources

* [LRQA-40747](https://issues.liferay.com/browse/LRQA-40747) - Allow publication of poshi resource jar in ee-6.1.x

### Script

* [LRQA-40614](https://issues.liferay.com/browse/LRQA-40614) - Enable Poshiscript usage in macro files
* [LRQA-40617](https://issues.liferay.com/browse/LRQA-40617) - Determine type of 'execute' element by a static list of function files and util classes
* [LRQA-40754](https://issues.liferay.com/browse/LRQA-40754) - Add support for "execute class" syntax
* [LRQA-40755](https://issues.liferay.com/browse/LRQA-40755) - Add support for 'contains' elements
* [LRQA-40766](https://issues.liferay.com/browse/LRQA-40766) - Add support for static vars
* [LRQA-40860](https://issues.liferay.com/browse/LRQA-40860) - Add support for return vars from macros
* [LRQA-40919](https://issues.liferay.com/browse/LRQA-40919) - Add prose syntax into poshi element translation tests
* [LRQA-40958](https://issues.liferay.com/browse/LRQA-40958) - Add syntax for add/retrieve from Context object
* [LRQA-41233](https://issues.liferay.com/browse/LRQA-41233) - Bug fix: 'static var' syntax for 'var method' calls is parsed as an 'execute macro'
* [LRQA-41307](https://issues.liferay.com/browse/LRQA-41307) - Add full XML escaping when translating between Poshi XML and Poshiscript
* [LRQA-41435](https://issues.liferay.com/browse/LRQA-41435) - Consolidate existing syntax checks for poshiscript 'blocks'
* [LRQA-41437](https://issues.liferay.com/browse/LRQA-41437) - Rename 'Readable' and 'ReadableSyntax' to 'PoshiScript'
* [LRQA-41498](https://issues.liferay.com/browse/LRQA-41498) - Consolidate syntax checks for poshiscript 'statements'
* [LRQA-41500](https://issues.liferay.com/browse/LRQA-41500) - Delete 'on' 'off' and 'toggle' classes
* [LRQA-41683](https://issues.liferay.com/browse/LRQA-41683) - Consolidate logic for snippet splitting in getPoshiScriptSnippets for 'task' and control flow blocks
* [LRQA-41685](https://issues.liferay.com/browse/LRQA-41685) - Consolidate parsing logic for 'definition' and 'command' blocks to use new methods/regex
* [LRQA-41801](https://issues.liferay.com/browse/LRQA-41801) - Various translation fixes
* [LRQA-41812](https://issues.liferay.com/browse/LRQA-41812) - Consolidate syntax generation for poshi script blocks
* [LRQA-41899](https://issues.liferay.com/browse/LRQA-41899) - Balance check poshi script syntax for single qutoes
* [LRQA-42040](https://issues.liferay.com/browse/LRQA-42040) - Optimize performance of poshi script parsing

### Utilities

* [LRQA-40561](https://issues.liferay.com/browse/LRQA-40561) - Move poshi runner request utility away from using curl
* [LRQA-41128](https://issues.liferay.com/browse/LRQA-41128) - Add printouts to HttpRequestUtil to quickly see what options were given to a request
* [LRQA-41129](https://issues.liferay.com/browse/LRQA-41129) - Do not fail HttpRequestUtil calls that return a non-200 response code
* [LRQA-41813](https://issues.liferay.com/browse/LRQA-41813) - Add util for java.net.URLEncoder.encode
* [LRQA-41859](https://issues.liferay.com/browse/LRQA-41859) - Printout URL to standard out when using HttpRequestUtil
* [LRQA-41863](https://issues.liferay.com/browse/LRQA-41863) - Add unit test for HttpRequestUtil
* [LRQA-41881](https://issues.liferay.com/browse/LRQA-41881) - Make a poshi util method for 'org.apache.commons.io.FilenameUtils.getName'

### WebDriver

* [LRQA-38852](https://issues.liferay.com/browse/LRQA-38852) - Do not scroll into view when using assertNotVisible

## 1.0.102

### Features

#### Script

* [LRQA-40441](https://issues.liferay.com/browse/LRQA-40441) - Account for 'ignore' attributes in both definition and command elements

#### Utilities

* [LRQA-40172](https://issues.liferay.com/browse/LRQA-40172) - Add POST method to JSONCurlUtil
* [LRQA-40476](https://issues.liferay.com/browse/LRQA-40476) - As a Test Writer, I would like the ability to sort a list of Strings
* [LRQA-40542](https://issues.liferay.com/browse/LRQA-40542) - As a Test Writer, I would like the ability to format a JSON string

#### WebDriver

* [LRQA-40455](https://issues.liferay.com/browse/LRQA-40455) - EdgeDriver: Get innerText of elements using javascript instead of native Edge

### Fixes

#### Script

* [LRQA-35687](https://issues.liferay.com/browse/LRQA-35687) - PoshiElementFactory throws "URI is not hierarchical" exception when running poshi script files

#### Resources

* [LRQA-40725](https://issues.liferay.com/browse/LRQA-40725) - Fix the regex used for parsing poshi resource jar

#### WebDriver

Remove refresh from the open internetexplorer command

## 1.0.87

### Features

### WebDriver

* [LRQA-39692](https://issues.liferay.com/browse/LRQA-39692) - Allow ChromeDriver to read in command line arguments
* [LRQA-39880](https://issues.liferay.com/browse/LRQA-39880) - Retry PoshiRunner when encountering 'Timed out waiting 45 seconds for Firefox to start.'
* [LRQA-40116](https://issues.liferay.com/browse/LRQA-40116) - Update Poshi Runner to work with Safari 11

### PQL

* [LRQA-40063](https://issues.liferay.com/browse/LRQA-40063) - Always add parentheses around generated PQL Queries
Code Clean Up

### WebDriver

* [LRQA-39697](https://issues.liferay.com/browse/LRQA-39697) - *Capabilities and *Profile WebDriver class usages to use *Options
* [LRQA-39698](https://issues.liferay.com/browse/LRQA-39698) - Remove BrowserCommands class and associated properties and variables

## 1.0.82

### Features

* [LRQA-35105](https://issues.liferay.com/browse/LRQA-35105) - As a Legacy Automation Tester, I want to see duplicate locator prevention added into poshi-validation
* [LRQA-38551](https://issues.liferay.com/browse/LRQA-38551) - Update Poshi Runner to work with FF52

### Fixes

* [LRQA-39025](https://issues.liferay.com/browse/LRQA-39025) - Update sikuli commands to work on CentOS 7 for portal

## 1.0.80

### Features

* [LRQA-37422](https://issues.liferay.com/browse/LRQA-37422) - As a Test Writer, I would like to validate attribute value(s) for a given element
* [LRQA-22781](https://issues.liferay.com/browse/LRQA-22781) - Update WebDriver refersh to use something more similar to an actual refresh
* [LRQA-36863](https://issues.liferay.com/browse/LRQA-36863) - Create a function to assert text without case sensitivity
* [LRQA-37375](https://issues.liferay.com/browse/LRQA-37375) - Refactor dragAndDrop to use actions instead of robot, allow variable coordinates to be passed in to imitate a 'wiggle' behavior
* [LRQA-36161](https://issues.liferay.com/browse/LRQA-36161) - Extend poshi-runner to run namespaced testcases
* [LRQA-38159](https://issues.liferay.com/browse/LRQA-38159) - As a Test Writer, I would like to dynmaically replace path class names at runtime

### Fixes

* [LRQA-36905](https://issues.liferay.com/browse/LRQA-36905) - Fix source format errors within the poshi runner subrepository

## 1.0.78

### Fixes

* [LRQA-33621](https://issues.liferay.com/browse/LRQA-33621) - Investigate AssertHTMLSourceTextPresent and AssertHTMLSourceTextNotPresent

## 1.0.63

### Fixes

* [LRQA-34604](https://issues.liferay.com/browse/LRQA-34604) - Fix assertPartialConfirmation to use value instead of locator
* [LRQA-34535](https://issues.liferay.com/browse/LRQA-34535) - The poshi-runner module should not read subrepo poshi files

## 1.0.62

### Fixes

* [LRQA-34064](https://issues.liferay.com/browse/LRQA-34064) - BUG: Poshi Path file extension does not work on Windows

## 1.0.61

### Features

* [LRQA-32815](https://issues.liferay.com/browse/LRQA-32815) - Add logic to read resources from poshi-runner project
* [LRQA-33725](https://issues.liferay.com/browse/LRQA-33725) - Allow condition elements to accept var child elements in validation
* [LRQA-33771](https://issues.liferay.com/browse/LRQA-33771) - Create method to count string length in StringUtil

### Fixes

* [LRQA-33636](https://issues.liferay.com/browse/LRQA-33636) - Add exception handling for null pointer selenium 'type' commands

## 1.0.59

### Features

* [LRQA-32783](https://issues.liferay.com/browse/LRQA-32783) - Move core Poshi functions and macrodefs into Poshi runner jar
* [LRQA-32801](https://issues.liferay.com/browse/LRQA-32801) - Implement round-trip testcase conversion logic
* [LRQA-32800](https://issues.liferay.com/browse/LRQA-32800) - Move core functionalities into poshi-runner-resources

### Fixes

* [LRQA-33180](https://issues.liferay.com/browse/LRQA-33180) - Add numerical comparison functions to poshi runner

## 1.0.58

### Features

* [LRQA-32965](https://issues.liferay.com/browse/LRQA-32965) - Ignore basedirs that do not exist

## 1.0.54

### Features

* [LRQA-31813](https://issues.liferay.com/browse/LRQA-31813) - Subrepo functional test process restructure
* [LRQA-29622](https://issues.liferay.com/browse/LRQA-29622) - Remove *AndWait commands from LiferaySelenium and waitForPageToLoad
* [LRQA-31642](https://issues.liferay.com/browse/LRQA-31642) - As Loop tester, I'd like to have geolocation disabled on Firefox web driver
* [LRQA-31988](https://issues.liferay.com/browse/LRQA-31988) - Create random number generator in Poshi Runner
* [LRQA-32011](https://issues.liferay.com/browse/LRQA-32011) - Add get time in nanoseconds function into Poshi Runner
* [LRQA-32058](https://issues.liferay.com/browse/LRQA-32058) - As a tester, I would like to get future dates

### Fixes

* [LRQA-31508](https://issues.liferay.com/browse/LRQA-31508) - Fix Module Functional Tests for Poshi Runner
* [LRQA-27178](https://issues.liferay.com/browse/LRQA-27178) - Remove WebDriverHelper

## 1.0.52

### Features

* [LRQA-30198](https://issues.liferay.com/browse/LRQA-30198) - Add new LiferaySelenium commands to wait for console text
* [LRQA-30971](https://issues.liferay.com/browse/LRQA-30971) - Create assertPartialLocation function
* [LRQA-31246](https://issues.liferay.com/browse/LRQA-31246) - As a Test Writer, I would like to be able to enter values for prompts pop-ups

## 1.0.50

### Features

* [LRQA-28179](https://issues.liferay.com/browse/LRQA-28179) - As a test engineer, I would like Poshi to be able to handle OAuth authentications.
* [LRQA-28702](https://issues.liferay.com/browse/LRQA-28702) - I'd like a way to avoid running a testcase by definition or command
* [LRQA-29185](https://issues.liferay.com/browse/LRQA-29185) - As a test analyst, I should know which poshi files cause DocumentExceptions in validation

### Fixes

* [LRQA-28736](https://issues.liferay.com/browse/LRQA-28736) - The test generated properties does not include properties that were exclueded
* [LRQA-27233](https://issues.liferay.com/browse/LRQA-27233) - Remove duplicate methods from LiferaySeleniumHelper
* [LRQA-28478](https://issues.liferay.com/browse/LRQA-28478) - EdgeDriver: Element is obscured issue
* [LRQA-29165](https://issues.liferay.com/browse/LRQA-29165) - EdgeDriver: org.openqa.selenium.TimeoutException
* [LRQA-28538](https://issues.liferay.com/browse/LRQA-28538) - Update theme tests to behave differently based on the resources in the theme

## 1.0.48

### Features

* [LRQA-28184](https://issues.liferay.com/browse/LRQA-28184) - Text returned from script editor fields should not be broken by newline characters

### Fixes

* [LRQA-28079](https://issues.liferay.com/browse/LRQA-28079) - Update OpenCV version within poshi runner gradle file
* [LRQA-28195](https://issues.liferay.com/browse/LRQA-28195) - Allow sikuli dependencies for OSX

## 1.0.46

### Features

* [LRQA-27637](https://issues.liferay.com/browse/LRQA-27637) - Add known-issues as a search filed within PQL
* [LRQA-27651](https://issues.liferay.com/browse/LRQA-27651) - Add test run environment as an additional filter when getting list of testcases
* [LRQA-27163](https://issues.liferay.com/browse/LRQA-27163) - Create a new method to determine percentages for use in Poshi
* [LRQA-27124](https://issues.liferay.com/browse/LRQA-27124) - Make getElementWidth available to use as a selenium method

### Fixes

* [LRQA-27213](https://issues.liferay.com/browse/LRQA-27213) - Mitigate UnreachableBrowserException in Firefox 45
* [LRQA-27289](https://issues.liferay.com/browse/LRQA-27289) - BUG: sikuliUploadCommonFile does not work on OSX machines

## 1.0.45

### Features

* [LRQA-26525](https://issues.liferay.com/browse/LRQA-26525) - As a test writer, I should be able to extend the waiting timeout when the SPA loading bar is still present
* [LRQA-26691](https://issues.liferay.com/browse/LRQA-26691) - Create a query language to group tests with similar poshi properties
* [LRQA-26694](https://issues.liferay.com/browse/LRQA-26694) - Create a method to apply 'test toggles' to poshi so that we can remove a toggle once a new feature has been merged
* [LRQA-26786](https://issues.liferay.com/browse/LRQA-26786) - Allow a subset of tests to be selected using poshi properties

### Fixes

* [LRQA-24996](https://issues.liferay.com/browse/LRQA-24996) - Update assertion failures to be clearer
* [LRQA-26889](https://issues.liferay.com/browse/LRQA-26889) - Do not set timeout values for safari

## 1.0.43

### Features

* [LRQA-26139](https://issues.liferay.com/browse/LRQA-26139) - Update Poshi Selenium WebDriver version to 2.53.0
* [LRQA-26153](https://issues.liferay.com/browse/LRQA-26153) - Modify Poshi Runner to take in a specified Firefox binary
* [LRQA-26192](https://issues.liferay.com/browse/LRQA-26192) - Add dependencies to Poshi Runner for upgrading to WebDriver 2.53.0
* [LRQA-26247](https://issues.liferay.com/browse/LRQA-26247) - As a Test Manger, I'd like to disable a testcase, so that no jobs will run these bad tests

## 1.0.40

### Features

* [LRQA-24219](https://issues.liferay.com/browse/LRQA-24219) - As a test writer, I want to get the current hour of the day to create conditional logic
* [LRQA-24148](https://issues.liferay.com/browse/LRQA-24148) - Add method to poshi for accessibility testing
* [LRQA-24352](https://issues.liferay.com/browse/LRQA-24352) - As a test writer, obscured elements should be scrolled into view before interacting with them
* [LRQA-24430](https://issues.liferay.com/browse/LRQA-24430) - As a test writer, I should be able to scroll through the page by a specified number of pixels
* [LRQA-24680](https://issues.liferay.com/browse/LRQA-24680) - As a Test Writer, I'd like for JSON calls to work in Windows and Linux when using more complex objects
* [LRQA-24631](https://issues.liferay.com/browse/LRQA-24631) - As a Test Writer, I'd like to use variables as arguments when executing groovy scripts
* [LRQA-24176](https://issues.liferay.com/browse/LRQA-24176) - As a Jenkins Admin, I'd like to avoid using the actual test names to avoid have an excess amount of axes within jenkins

### Fixes

* [LRQA-23131](https://issues.liferay.com/browse/LRQA-23131) - BUG: The highest level function does not always show up in the summary

## 1.0.38

### Features

* [LRQA-22064](https://issues.liferay.com/browse/LRQA-22064) - As a Test Analyst, I'd like for the Summary Logger to separate their reports so that the last report is less confusing

### Fixes

* [LRQA-23107](https://issues.liferay.com/browse/LRQA-23107) - Running Edge on Poshi doesn't work locally (only works in RemoteWebDriver)

## 1.0.37

### Features

* [LRQA-22312](https://issues.liferay.com/browse/LRQA-22312) - Replace text within a file during a poshi test run
* [LRQA-21135](https://issues.liferay.com/browse/LRQA-21135) - As a Test Writer, I'd like the ability to run custom groovy scripts within Poshi Runner

### Fixes

* [LRQA-21925](https://issues.liferay.com/browse/LRQA-21925) - Tests are sometimes failing on the PR tester due to "Element not found in the cache"

## 1.0.36

### Features

* [LRQA-21362](https://issues.liferay.com/browse/LRQA-21362) - As a Test Analyst, I'd like the cause above the steps in the poshi log

### Fixes

* [LRQA-22168](https://issues.liferay.com/browse/LRQA-22168) - Update scrollWebElementIntoView to not align to top of the page

## 1.0.35

### Features

* [LRQA-16480](https://issues.liferay.com/browse/LRQA-16480) - Research FakeSMTP or other similar utilities
* [LRQA-20196](https://issues.liferay.com/browse/LRQA-20196) - selenium#getElementText ends in java.lang.Exception
* [LRQA-19337](https://issues.liferay.com/browse/LRQA-19337) - Add functionality to allow poshi tests to double click into Alloy Editor
* [LRQA-19127](https://issues.liferay.com/browse/LRQA-19127) - As a test writer, one CKEditor function should work for all types of CKEditors
* [LRQA-21649](https://issues.liferay.com/browse/LRQA-21649) - As a Test Engineer, I'd like for to add 'Windows Server 2012r2 - Tomcat 8 - MySql 5.6 - Oracle Sun JDK 7 - IE 11' to the Environment Tester

### Fixes

* [LRQA-20435](https://issues.liferay.com/browse/LRQA-20435) - Improve Exception Handling in the Logger
* [LRQA-21163](https://issues.liferay.com/browse/LRQA-21163) - As a Test Writer, I'd like for any 'arg' elements used for 'execute method' elements to use variable replacements
* [LRQA-21648](https://issues.liferay.com/browse/LRQA-21648) - Remove all references to the 'getElementText' command within all existing 'testcase' files

## 1.0.32

### Features

* [LRQA-17937](https://issues.liferay.com/browse/LRQA-17937) - As a Test Analyst, I want to have a 'Pause' button in the Poshi logger, to pause a running test (pause on failure)
* [LRQA-21164](https://issues.liferay.com/browse/LRQA-21164) - As a Test Writer, I'd like the 'JSONCurlUtil' to return a blank string when the JSON path provided does not point to a valid value
* [LRQA-19650](https://issues.liferay.com/browse/LRQA-19650) - Run the first test in each batch on localhost instead of another virtual host

### Fixes

* [LRQA-21170](https://issues.liferay.com/browse/LRQA-21170) - BUG: Poshi Tests are timing out

## 1.0.29

### Features

* [LRQA-17906](https://issues.liferay.com/browse/LRQA-17906) - As a Test Writer, I would like to have the ability to have additional var functionality in global testcase file added to poshi runner
* [LRQA-17787](https://issues.liferay.com/browse/LRQA-17787) - As a Test Engineer, I would like to be able to set timeout.explicit.wait
* [LRQA-19938](https://issues.liferay.com/browse/LRQA-19938) - As a test engineer, it should be possible for a failed test to store the html of the page upon failure to streamline analysis
* [LRQA-17494](https://issues.liferay.com/browse/LRQA-17494) - As a test analyst, PortalSmoke#Smoke should work on IE10 with poshi runner
* [LRQA-20001](https://issues.liferay.com/browse/LRQA-20001) - As a Test Analyst, I'd like for a way to assert 'FATAL' level failures within the Liferay Logs
* [LRQA-20225](https://issues.liferay.com/browse/LRQA-20225) - As a Test Writer, I'd like a way to pass along a variable from the setup / commands along to the teardown so that we can save important variables
* [LRQA-17470](https://issues.liferay.com/browse/LRQA-17470) - Double click to expand lines
* [LRQA-17278](https://issues.liferay.com/browse/LRQA-17278) - General Poshi Logger Speed Improvments
* [LRQA-18316](https://issues.liferay.com/browse/LRQA-18316) - Update iOS version on poshi runner to 8.4
* [LRQA-18797](https://issues.liferay.com/browse/LRQA-18797) - Generate a group of test names to run in batches based on their properties in the PR tester
* [LRQA-18327](https://issues.liferay.com/browse/LRQA-18327) - As a Test Analyst, I would like easy distinction between warnings and failures in the logger
* [LRQA-19237](https://issues.liferay.com/browse/LRQA-19237) - Print full java stacktrace on poshi test failure to console output
* [LRQA-20671](https://issues.liferay.com/browse/LRQA-20671) - As a test writer, I would like to get the current day name to pass into a variable
* [LRQA-17937](https://issues.liferay.com/browse/LRQA-17937) - As a Test Analyst, I want to have a 'Pause' button in the Poshi logger, to pause a running test (manual pause only)

### Fixes

* [LRQA-19891](https://issues.liferay.com/browse/LRQA-19891) - As a Test Writer, I'd like for var locators to be replaced by path locator values when available
* [LRQA-19934](https://issues.liferay.com/browse/LRQA-19934) - As a Test Writer, I'd like for macro returns to work within other macros
* [LRQA-20017](https://issues.liferay.com/browse/LRQA-20017) - As a Testray Analyst, I'd like to get rid of the unneeded parenthesis within all warnings
* [LRQA-18992](https://issues.liferay.com/browse/LRQA-18992) - BUG: AssertNotLocation does not work in poshi runner
* [LRQA-19241](https://issues.liferay.com/browse/LRQA-19241) - BUG: Curls against the Portal are not working as expected
* [LRQA-20572](https://issues.liferay.com/browse/LRQA-20572) - Java - Increase explicit timeout value for tests that run on the android emulator
* [LRQA-19233](https://issues.liferay.com/browse/LRQA-19233) - BUG: Selecting by value/regex does not work

## 1.0.26

### Features

* [LRQA-15442](https://issues.liferay.com/browse/LRQA-15442) - As a Test Writer, I'd like a way to return a variable from a macro so that I can gather information and return it to the testcase
* [LRQA-19554](https://issues.liferay.com/browse/LRQA-19554) - Abstract property logic in PoshiRunnerContext into smaller reusable methods and use them

### Fixes

* [LRQA-18219](https://issues.liferay.com/browse/LRQA-18219) - BUG: Screenshots are added to the command logger if function step in testcase file fails
* [LRQA-19133](https://issues.liferay.com/browse/LRQA-19133) - Testray error comment gives generic "java.lang.Exception"
* [LRQA-19288](https://issues.liferay.com/browse/LRQA-19288) - Ensure appium is shutdown after running mobile tests

## 1.0.25

### Features

* [LRQA-19004](https://issues.liferay.com/browse/LRQA-19004) - As a Build Script Dev, I'd like for 'record-test-generated-properties' to build every single property so that we can limit the number of times we run this task
* [LRQA-19050](https://issues.liferay.com/browse/LRQA-19050) - As a Test Engineer, I'd like to allow PR Tester to also read poshi attributes so that I can build a list of jobs based off of priority

### Fixes

* [LRQA-18523](https://issues.liferay.com/browse/LRQA-18523) - BUG: selenium#getText fails with java.lang.Exception
* [LRQA-18996](https://issues.liferay.com/browse/LRQA-18996) - BUG: Summary log steps appear with escaped HTML
* [LRQA-18984](https://issues.liferay.com/browse/LRQA-18984) - As a mobile test writer, the popup dialog for the android emulator should not appear upon starting
* [LRQA-18977](https://issues.liferay.com/browse/LRQA-18977) - The android emulator must be shut down with in the run-selenium-test target
* [LRQA-19152](https://issues.liferay.com/browse/LRQA-19152) - BUG: Random stale element failure
* [LRQA-19196](https://issues.liferay.com/browse/LRQA-19196) - BUG: The 'liferay-log.xml' file sometimes get cut in half which results in a poshi test failure

## 1.0.22

### Updates

* [LPS-59564](https://issues.liferay.com/browse/LPS-59564) - As a developer, I would like to use the standard Gradle/Maven directory layout for the portal modules

### Features

* [LRQA-17556](https://issues.liferay.com/browse/LRQA-17556) - As a Test Writer, I'd like a class to make CURLs against liferay that returns a value from the response
* [LRQA-18127](https://issues.liferay.com/browse/LRQA-18127) - As a Test Writer, I'd like a class to parse through JSON objects
* [LRQA-18184](https://issues.liferay.com/browse/LRQA-18184) - Our test servers and testers should be able to run their automated tests against Firefox builds that Liferay 7.0 will support
* [LRQA-18793](https://issues.liferay.com/browse/LRQA-18793) - Update test.skip.tear.down conditional to be specific and dynamic
* [LRQA-18618](https://issues.liferay.com/browse/LRQA-18618) - Update PoshiRunner java logic to accept a comma delimited list of test names and run them sequentially
* [LRQA-18819](https://issues.liferay.com/browse/LRQA-18819) - Pass portal instance property through build-test.xml
* [LRQA-18794](https://issues.liferay.com/browse/LRQA-18794) - Update certain tests to have the option of running a test on a portal instance

### Fixes

* [LRQA-17917](https://issues.liferay.com/browse/LRQA-17917) - BUG: Tests that use XSS alert scripts will add alerts in the poshi runner logger.
* [LRQA-18676](https://issues.liferay.com/browse/LRQA-18676) - BUG: Fix the 'download/uploadTempFile' commands to work with poshi runner
* [LRQA-18572](https://issues.liferay.com/browse/LRQA-18572) - As a Test Engineer, I want a new way to escape tags into a Javascript string format, so they aren't interpreted as tags
* [LRQA-18757](https://issues.liferay.com/browse/LRQA-18757) - Reset PoshiRunner logger variables before running each test

## 1.0.21

* [LRQA-18046](https://issues.liferay.com/browse/LRQA-18046) - As a Test Analyst, I'd like to print a jstack log from poshi runner if it fails
* [LRQA-16148](https://issues.liferay.com/browse/LRQA-16148) - As a Test Writer, I'd like echo/fail elements to be logged in the poshi runner command log so that I know where those commands are run
* [LRQA-16457](https://issues.liferay.com/browse/LRQA-16457) - As a Test Writer, I would like to be able to use more conditional elements within .function files so that I can have more flow control at that level
* [LRQA-17838](https://issues.liferay.com/browse/LRQA-17838) - As a test writer, I would like poshi validation to let me know if I wrote an invalid testray component name
* [LRQA-10616](https://issues.liferay.com/browse/LRQA-10616) - Mobile: Make it possible to run POSHI on real mobile devices that are connected locally to a computer for chrome mobile
* [LRQA-18170](https://issues.liferay.com/browse/LRQA-18170) - Consolidate properties for browsers and mobile browsers
* [LRQA-18219](https://issues.liferay.com/browse/LRQA-18219) - BUG: Screenshots are added to the command logger if function step in testcase file fails

## 1.0.20

* [LRQA-17722](https://issues.liferay.com/browse/LRQA-17722) - As a test writer, I'd like for a way to run poshi tests from multiple base directories
* [LRQA-17907](https://issues.liferay.com/browse/LRQA-17907) - As a Test Writer, I would like to run tests without needing to give a log4j file for poshi runner
* [LRQA-18221](https://issues.liferay.com/browse/LRQA-18221) - Fix the UploadCommonFile function
* [LRQA-17845](https://issues.liferay.com/browse/LRQA-17845) - As a tester, I want SikuliUploadCommonFile to use the correct path when uploading something using poshi runner
* [LRQA-18148](https://issues.liferay.com/browse/LRQA-18148) - Warning count in Testray results is 0 when there are warning s