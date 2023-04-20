/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.functional.test.driver;

import com.liferay.osb.faro.functional.test.steps.GeneralSteps;
import com.liferay.osb.faro.functional.test.util.FaroRestUtil;
import com.liferay.osb.faro.functional.test.util.FaroSeleniumUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.poshi.core.util.PropsValues;
import com.liferay.poshi.runner.selenium.BaseWebDriverImpl;
import com.liferay.poshi.runner.selenium.WebDriverUtil;
import com.liferay.poshi.runner.util.RuntimeVariables;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import java.io.BufferedReader;
import java.io.File;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.time.Duration;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import org.junit.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Cheryl Tang
 */
public class FaroWebDriverImpl
	extends BaseWebDriverImpl implements FaroSelenium {

	public FaroWebDriverImpl(String browserURL, WebDriver webDriver) {
		super(browserURL, webDriver);
	}

	/**
	 * Asserts that a specific node (excludes text in child text) contains any
	 * text.
	 *
	 * @param xpath the xpath of the WebElement to assert
	 */
	@Override
	public void assertWebElementHasAnyText(String xpath) {
		WebElement webElement = findElement(xpath);

		String webElementText = webElement.getText();

		RemoteWebElement remoteWebElement = (RemoteWebElement)webElement;

		List<WebElement> childWebElements = remoteWebElement.findElements(
			By.xpath("./*"));

		for (WebElement childWebElement : childWebElements) {
			String childWebElementText = childWebElement.getText();

			webElementText = StringUtil.removeSubstring(
				webElementText, childWebElementText);
		}

		Assert.assertTrue(!webElementText.isEmpty());
	}

	@Override
	public void click(String locator) {
		try {
			waitForElementPresent(locator);
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}

		super.click(locator);
	}

	@Override
	public void click(WebElement webElement) {
		try {
			webElement.click();
		}
		catch (Exception exception) {
			scrollWebElementIntoView(webElement);

			webElement.click();
		}
	}

	@Override
	public void clickIcon(String className) {
		WebElement webElement = findElement(
			"//button[contains(@class,'" + className + "')]");

		webElement.click();
	}

	@Override
	public void dragAndDropChrome(String fromElement, String toElement)
		throws Exception {

		JavascriptExecutor jse =
			(JavascriptExecutor)WebDriverUtil.getWebDriver(StringPool.BLANK);

		try (BufferedReader bufferedReader = Files.newBufferedReader(
				Paths.get(
					"src/testIntegration/resources/drag_and_drop_helper.js"))) {

			Stream<String> stream = bufferedReader.lines();

			jse.executeScript(
				StringBundler.concat(
					stream.collect(Collectors.joining(" ")),
					"DndSimulator.simulate('", fromElement, "', '", toElement,
					"');"));
		}
	}

	@Override
	public WebElement findElement(String xpath) {
		return findElement(By.xpath(xpath));
	}

	@Override
	public WebElement findElement(String xpath, WebElement webElement) {
		return webElement.findElement(By.xpath(xpath));
	}

	@Override
	public List<WebElement> findElements(String xpath) {
		return findElements(By.xpath(xpath));
	}

	/**
	 * Attempt to force window focus.
	 */
	@Override
	public void forceWindowFocus() {
		String windowHandle = getWindowHandle();

		WebDriver webDriver = WebDriverUtil.getWebDriver(StringPool.BLANK);

		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)webDriver;

		javascriptExecutor.executeScript("alert('Test')");

		GeneralSteps.handleAlert("accept");

		WebDriver.TargetLocator targetLocator = switchTo();

		targetLocator.window(windowHandle);
	}

	/**
	 * Gets the ID of an item from the last segment of the current URL.
	 *
	 * @return returns the ID of the item as a String
	 */
	@Override
	public String getIdFromURL() {
		String url = getCurrentUrl();

		String salesforceUrlPath = "salesforce/";

		if (url.contains(salesforceUrlPath)) {
			return url.substring(
				url.lastIndexOf(salesforceUrlPath) + salesforceUrlPath.length(),
				url.lastIndexOf(CharPool.SLASH));
		}

		return url.substring(url.lastIndexOf(CharPool.SLASH) + 1);
	}

	/**
	 * Gets the Property ID from the current URL
	 *
	 * @return returns the Property ID as a String
	 * @throws Exception
	 */
	@Override
	public String getPropertiesIdFromURL() throws Exception {
		String url = getCurrentUrl();

		Matcher matcher = _propertyIdPattern.matcher(url);

		if (!matcher.find()) {
			StringBundler sb = new StringBundler(4);

			sb.append("Unable to find a Property ID matching regex pattern ");
			sb.append(_propertyIdPattern.toString());
			sb.append(" in the following URL: ");
			sb.append(url);

			throw new Exception(sb.toString());
		}

		return matcher.group(2);
	}

	/**
	 * Gets the Workspace ID from the current URL.
	 *
	 * @return returns the Workspace ID as a String
	 */
	@Override
	public String getWorkspaceIdFromURL() throws Exception {
		String url = getCurrentUrl();

		Matcher matcher = _workspaceIdPattern.matcher(url);

		if (!matcher.find()) {
			StringBundler sb = new StringBundler(4);

			sb.append("Unable to find a workspace ID matching regex pattern ");
			sb.append(_workspaceIdPattern.toString());
			sb.append(" in the following URL: ");
			sb.append(url);

			throw new Exception(sb.toString());
		}

		return matcher.group(1);
	}

	/**
	 * Refreshes the page using a provided timeout and polling interval to
	 * assert the absence of a WebElement.
	 *
	 * @param timeout the maximum time to wait for the web element to not be
	 *        present
	 * @param pollInterval the interval to refresh and recheck for the
	 *        WebElement
	 * @param xpath the xpath of the WebElement to check
	 */
	@Override
	public void refreshUntilElementNotPresent(
			int timeout, int pollInterval, String xpath)
		throws Exception {

		if (isElementNotPresent(xpath)) {
			return;
		}

		FluentWait fluentWait = new FluentWait(this);

		fluentWait.withTimeout(Duration.ofSeconds(timeout));

		Wait wait = fluentWait.pollingEvery(Duration.ofSeconds(pollInterval));

		Function<FaroSelenium, Boolean> function = faroSelenium -> {
			FaroRestUtil.clearCache();

			WebDriver webDriver = WebDriverUtil.getWebDriver(StringPool.BLANK);

			Navigation navigation = webDriver.navigate();

			navigation.refresh();

			waitForPageLoadingComplete();

			try {
				waitForLoadingComplete();

				return isElementNotPresent(xpath);
			}
			catch (Exception exception) {
				return false;
			}
		};

		wait.until(function);
	}

	/**
	 * Refreshes the page using a provided timeout and polling interval to
	 * assert the presence of a WebElement.
	 *
	 * @param timeout the maximum time to wait for the web element to be present
	 * @param pollInterval the interval to refresh and recheck for the
	 *        WebElement
	 * @param xpath the xpath of the WebElement to expect
	 */
	@Override
	public void refreshUntilElementPresent(
			int timeout, int pollInterval, String xpath)
		throws Exception {

		if (isElementPresent(xpath)) {
			return;
		}

		FluentWait fluentWait = new FluentWait(this);

		fluentWait.withTimeout(Duration.ofSeconds(timeout));

		Wait wait = fluentWait.pollingEvery(Duration.ofSeconds(pollInterval));

		Function<FaroSelenium, Boolean> function = faroSelenium -> {
			FaroRestUtil.clearCache();

			WebDriver webDriver = WebDriverUtil.getWebDriver(StringPool.BLANK);

			Navigation navigation = webDriver.navigate();

			navigation.refresh();

			waitForPageLoadingComplete();

			try {
				waitForLoadingComplete();

				return isElementPresent(xpath);
			}
			catch (Exception exception) {
				return false;
			}
		};

		wait.until(function);
	}

	/**
	 * Refreshes the page using a provided timeout and polling interval to
	 * assert a WebElement's text against an expected value.
	 *
	 * @param timeout the maximum time to wait for the web element's expected
	 *        value
	 * @param pollInterval the interval to refresh and recheck the WebElement
	 * @param xpath the xpath of the WebElement to assert
	 * @param expectedValue the expected vaLue of the WebElement
	 */
	@Override
	public void refreshUntilTextAsserted(
			int timeout, int pollInterval, String xpath, String expectedValue)
		throws Exception {

		FluentWait fluentWait = new FluentWait(this);

		fluentWait.withTimeout(Duration.ofSeconds(timeout));

		Wait wait = fluentWait.pollingEvery(Duration.ofSeconds(pollInterval));

		Function<FaroSelenium, Boolean> function = faroSelenium -> {
			FaroRestUtil.clearCache();

			WebDriver webDriver = WebDriverUtil.getWebDriver(StringPool.BLANK);

			Navigation navigation = webDriver.navigate();

			navigation.refresh();

			try {
				waitForNotText(xpath, StringPool.DASH, 5);
			}
			catch (Exception exception) {
			}

			WebElement webElement = findElement(xpath);

			String webElementText = webElement.getText();

			return expectedValue.equals(webElementText);
		};

		wait.until(function);
	}

	/**
	 * Saves a screenshot of the entire screen after each feature file.
	 * Screenshots are stored by run in folders named in the following format:
	 * "yyyy-MM-dd_HH-mm-ss".
	 *
	 * @throws Exception if an exception occurred
	 */
	public void saveScreenshot() throws Exception {
		if (!PropsValues.SAVE_SCREENSHOT) {
			return;
		}

		Robot robot = new Robot();

		Toolkit toolkit = Toolkit.getDefaultToolkit();

		Rectangle rectangle = new Rectangle(toolkit.getScreenSize());

		BufferedImage bufferedImage = robot.createScreenCapture(rectangle);

		ImageIO.write(
			bufferedImage, "jpg",
			new File(
				FaroSeleniumUtil.getScreenshotsDir(),
				_screenshotCount + ".jpg"));

		_screenshotCount++;
	}

	/**
	 * Enters text into a web element and checks that it was entered correctly.
	 * If the entered text does not equal the intended string, the text is
	 * entered one character at a time.
	 *
	 * @param  locator the XPath of the webElement to send text to
	 * @param  value the string of text to enter
	 * @throws Exception if an exception occurred
	 */
	@Override
	public void sendKeys(String locator, String value) throws Exception {
		type(locator, value);

		String elementValue = getElementValue(locator);

		if (elementValue.equals(value)) {
			return;
		}

		WebDriverWait webDriverWait = new WebDriverWait(
			this, Duration.ofSeconds(3));

		WebElement webElement = findElement(locator);

		Thread.sleep(200);

		webElement.sendKeys(Keys.chord(Keys.CONTROL, "a"));

		webElement.sendKeys(Keys.DELETE);

		for (int i = 0; i < value.length(); i++) {
			webElement.sendKeys(String.valueOf(value.charAt(i)));

			String substring = value.substring(0, i);

			webDriverWait.until(driver -> { 
				ExpectedCondition<Boolean> attributeContains = 
					ExpectedConditions.attributeContains(
						webElement, "value", substring);
				
				return attributeContains.apply(driver);
			});
		}
	}

	@Override
	public void setMainWindowHandle() {
		_mainWindowHandle = getWindowHandle();
	}

	/**
	 * Switches focus back to the main window launched at the start of test
	 * execution after calling {@link #switchToPopupWindow()}.
	 */
	@Override
	public void switchToMainWindow() {
		TargetLocator targetLocator = switchTo();

		targetLocator.window(_mainWindowHandle);
	}

	/**
	 * Switches focus to the most recent popup window. Call {@link
	 * #switchToMainWindow()} to return to the main window after.
	 */
	@Override
	public void switchToPopupWindow() throws Exception {

		// Switching focus is somehow interrupting the OAuth redirect. Add a
		// short delay to get around this.

		Thread.sleep(250);

		Set<String> windowHandles = getWindowHandles();

		windowHandles.remove(_mainWindowHandle);

		Stream<String> stream = windowHandles.stream();

		Optional<String> handleOptional = stream.reduce(
			(first, second) -> second);

		if (!handleOptional.isPresent()) {
			throw new Exception("There is no other window to switch to");
		}

		TargetLocator targetLocator = switchTo();

		targetLocator.window(handleOptional.get());
	}

	@Override
	public void waitForElementNotPresent(String locator) throws Exception {
		super.waitForElementNotPresent(locator, "false");
	}

	@Override
	public void waitForElementPresent(String locator) throws Exception {
		super.waitForElementPresent(locator, "false");
	}

	/**
	 * Waits for the loading icon to not be present.
	 *
	 * @throws Exception if an exception occurred
	 */
	@Override
	public void waitForLoadingComplete() throws Exception {
		waitForElementNotPresent(
			"//div[not(@aria-hidden)]" +
				"/span[contains(@class,'loading-animation')]");
	}

	public void waitForNotText(String locator, String value, int timeout)
		throws Exception {

		value = RuntimeVariables.replace(value);

		for (int second = 0;; second++) {
			if (second >= timeout) {
				assertNotText(locator, value);
			}

			try {
				if (isNotText(locator, value)) {
					break;
				}
			}
			catch (Exception exception) {
			}

			Thread.sleep(1000);
		}
	}

	/**
	 * Waits for the page's javascript to finish loading.
	 */
	@Override
	public void waitForPageLoadingComplete() {
		WebDriverWait waitDriverWait = new WebDriverWait(
			WebDriverUtil.getWebDriver(StringPool.BLANK),
			Duration.ofSeconds(30));

		waitDriverWait.until(
			webDriver -> {
				JavascriptExecutor javascriptExecutor =
					(JavascriptExecutor)webDriver;

				String readyState = (String)javascriptExecutor.executeScript(
					"return document.readyState");

				return readyState.equals("complete");
			});
	}

	/**
	 * Tests if a webElement contains a specified substring.
	 *
	 * @param  webElement the WebElement to test
	 * @param  pattern the pattern to search for
	 * @return the boolean result of whether the pattern is present
	 */
	@Override
	public boolean webElementContainsText(
		WebElement webElement, String pattern) {

		String text = webElement.getText();

		return text.contains(pattern);
	}

	private static final Pattern _propertyIdPattern = Pattern.compile(
		".*workspace/(\\d+)/(\\d+).*");
	private static final Pattern _workspaceIdPattern = Pattern.compile(
		".*workspace/(\\d+).*");

	private String _mainWindowHandle;
	private int _screenshotCount = 1;

}