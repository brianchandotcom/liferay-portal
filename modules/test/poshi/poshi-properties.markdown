# Poshi Properties

Below are the list of properties that are available in the
[poshi-runner.properties](poshi-runner/src/main/resources/poshi-runner.properties)
file that can be overridden in a `poshi-runner-ext.properties` file.

### accessibility.standards.json

Sets a JSON string that determines accessibility rule evaluation while using
the `assertAccessible` Selenium method. See more information for rule formation
at [https://axe-core.org/docs/](https://axe-core.org/docs/).

### browser.chrome.bin.args

Sets the arguments to pass to the Chrome binary.

### browser.chrome.bin.file

Sets the path of a Chrome binary so a particular binary can be used in
WebDriver. This is useful if there are multiple Chrome installations on a
machine. WebDriver uses this binary file when executing tests.

### browser.firefox.bin.file

Sets the path of a Firefox binary so a particular binary can be used in
WebDriver. This is useful if there are multiple Firefox installations on a
machine. WebDriver uses this binary file when executing tests.

### browser.type

Sets the browser that WebDriver runs with. Valid values include `android`,
`androidchrome`, `chrome`, `edge`, `firefox`, `internetexplorer`, `iossafari`,
and `safari`. Note that all the mobile browsers have not been tested recently,
and require other setups. Also note, some browsers are OS specific.

### browser.version

Sets the version of the browser to run. This is required for Internet Explorer
and RemoteDriver instances.

### get.location.max.retries

Sets the number of attempts WebDriver will make to a location if the
WebElement's location cannot be returned.

### get.location.timeout

Sets the duration of the timeout (in seconds) that WebDriver waits before
determining the WebElement's location cannot be returned.

### ignore.errors

Sets a delimited list of ignorable console errors using
[`ignore.errors.delimiter`](#ignoreerrorsdelimiter).

### ignore.errors.delimiter

Sets the delimiter character to split separate errors in the
[`ignore.errors`](#ignoreerrors) property.

### ignore.errors.file.name

Sets the path of an XML file that specifies errors that can be ignored.

### liferay.portal.branch

Sets the portal branch name that is being tested. This is only for Liferay
Portal and will soon be removed.

### liferay.portal.bundle

Sets the portal bundle name that is being tested. This is only for Liferay
Portal and will soon be removed.

### logger.resources.url

Sets the URL that Poshi log files (`index.html` files) use for CSS and
JavaScript resources. This is maintained by QA Engineering and should not be
updated for custom settings.

### mobile.android.home

Sets the directory path of the Android home directory.

### mobile.browser

Sets a boolean for whether a browser is a mobile browser.

### mobile.device.name

Sets the mobile device's name.

### output.dir.name

Sets a directory path where downloaded files are stored.

### portal.url

Sets the default URL to which WebDriver opens. Note, Poshi was intended for use
primarily with Liferay Portal, which explains the `portal.url` property name.
Despite the name, Poshi is product-agnostic and does not necessarily require a
*Portal* URL.

### print.java.process.on.fail

Sets the name of a Java process to print JStack output for when tests fail.

### proxy.server.enabled

Sets a boolean that determines whether BrowserMob Proxy is enabled. The proxy can be configured with `com.liferay.poshi.runner.util.ProxyUtil`.

### save.screenshot

Sets a boolean for whether a screenshot is saved when tests fail.

### save.web.page

Sets a boolean for whether the web page CSS, HTML, and JavaScript is saved to a
file when tests fail.

### selenium.chrome.driver.executable

Sets the name of the ChromeDriver executable file to be found in `selenium.executable.dir.name`.

### selenium.desired.capabilities.platform

Sets the name of the platform for desired capabilities. This is only necessary
for Internet Explorer and RemoteDriver.

### selenium.edge.driver.executable

Sets the name of the Edge driver executable file to be found in `selenium.executable.dir.name`. If unset, WebDriver will use the system Edge Driver binary.

### selenium.executable.dir.name

Sets the directory path where all Selenium executables are found.

### selenium.ie.driver.executable

Sets the name of the IEDriver executable file to be found in `selenium.executable.dir.name`.

### selenium.remote.driver.enabled

Sets a boolean for whether the RemoteDriver is enabled.

### selenium.remote.driver.hub

Sets the Remote Driver hub's base URL.

### tcat.admin.repository

Sets the path of the TCat directory repository.

### tcat.enabled

This is a boolean for whether a portal bundle is using TCat.

### test.assert.console.errors

Sets a boolean for whether Liferay console errors are logged when using the
`assertLiferayErrors` Selenium function.

### test.assert.javascript.errors

Sets a boolean for whether JavaScript errors are logged when using the
`assertJavaScriptErrors` Selenium function.

### test.assert.warning.exceptions

Sets a boolean for whether to fail if the test had any Liferay console errors or
JavaScript errors.

### test.base.dir.name

Sets the path of the main directory containing Poshi files used for the test
project. Additional directory paths may be set using
[`test.include.dir.names`](#testincludedirnames) or
[`test.subrepo.dirs`](#testsubrepodirs).

### test.batch.group.ignore.regex

Sets a regular expression string that filters which properties to ignore when
grouping tests together in a batch. This is only necessary for sequential tests.
Batch group determination for sequential tests is dependent on properties, as
all tests in a group must share the same properties to be grouped together.

### test.batch.max.group.size

Sets the maximum number of tests running in a batch.

### test.batch.max.subgroup.size

Sets the maximum number of tests running in an axis.

### test.batch.run.type

Sets how groups of tests are organized; valid values are `sequential` or
`single`. The `sequential` option groups the tests based off the other
`test.batch.*` properties, allowing for multiple tests to be set in one axis.
The `single` option sets one test per axis. The grouping of tests is printed to
another properties file which is read by the CI infrastructure.

### test.case.available.property.names

Sets a comma-delimited list of properties that are available to set for tests
within Poshi.

### test.case.required.property.names

Sets a comma-delimited list of properties that are required to set for tests
within Poshi.

### test.console.log.file.name

Sets the path to the log file that should be evaluated. This is generally used
more with Liferay Portal testing.

### test.console.shut.down.file.name

Sets the path to the log file containing the shutdown process of an application
server.

### test.csv.report.property.names

Sets a comma-delimited list of Poshi properties that is attributed to each
individual test within a generated CSV report file.

### test.dependencies.dir.name

Sets the path to a directory that contains dependency files used for Poshi
tests.

### test.include.dir.names

Sets a comma-delimited list of directory paths to include in the Poshi project
files. This does not include `*.testcase` files.

### test.jvm.max.retries

Sets the maximum cumulative amount of attempts that failing tests in the test
group may be reexecuted.

### test.name

Sets the test case(s) to run. The tests can be specified by the test case
command name, the test case file's name, or a comma-delimited list of both that
runs sequentially. To run sequentially, the tests must be configured with proper
teardowns.

### test.poshi.script.validation

Sets a boolean to determine if additional Poshi Script validation will be run.

### test.poshi.warnings.file.name

Sets the path to a file that contains valid Poshi warnings. This is used
primarily for Testray.

### test.retry.command.wait.time

Sets the time (in seconds) to wait before retrying element retrieval when
elements have turned stale.

### test.run.environment

Sets a string to store the run environment. Generally, public or private
environments are denoted by the *CE* or *EE* values, respectively.

### test.run.locally

Sets a boolean for whether Poshi log frontend resources are used locally or
remotely.

### test.skip.tear.down

Sets a boolean for whether the tear down is skipped.

### test.subrepo.dirs

Sets a comma-delimited list of subrepository directory paths to include in the
Poshi project files. This includes all Poshi file types.

### test.testcase.max.retries

Sets the maximum amount of attempts that an individual failing testcase may be
reexecuted.

### timeout.explicit.wait

Sets the time (in seconds) before a *wait* function times out.

### timeout.implicit.wait

Sets the time (in seconds) before all non-*wait* functions time out.

*This document has been reviewed through commit `39ffa06`.*