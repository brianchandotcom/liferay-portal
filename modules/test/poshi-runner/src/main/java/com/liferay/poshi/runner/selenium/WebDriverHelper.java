/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.poshi.runner.selenium;

import com.liferay.poshi.runner.exception.PoshiRunnerWarningException;
import com.liferay.poshi.runner.util.CharPool;
import com.liferay.poshi.runner.util.FileUtil;
import com.liferay.poshi.runner.util.GetterUtil;
import com.liferay.poshi.runner.util.HtmlUtil;
import com.liferay.poshi.runner.util.PropsValues;
import com.liferay.poshi.runner.util.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import net.jsourcerer.webdriver.jserrorcollector.JavaScriptError;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Kenji Heigel
 */
public class WebDriverHelper {

	public static void addSelection(
		WebDriver webDriver, String locator, String optionLocator) {

		Select select = new Select(getWebElement(webDriver, locator));

		if (optionLocator.startsWith("index=")) {
			select.selectByIndex(
				GetterUtil.getInteger(optionLocator.substring(6)));
		}
		else if (optionLocator.startsWith("label=")) {
			select.selectByVisibleText(optionLocator.substring(6));
		}
		else if (optionLocator.startsWith("value=")) {
			select.selectByValue(optionLocator.substring(6));
		}
		else {
			select.selectByVisibleText(optionLocator);
		}
	}

	public static void assertCssValue(
			WebDriver webDriver, String locator, String cssAttribute,
			String cssValue)
		throws Exception {

		WebElement webElement = getWebElement(webDriver, locator);

		String actualCssValue = webElement.getCssValue(cssAttribute);

		if (!actualCssValue.equals(cssValue)) {
			throw new Exception(
				"CSS Value " + actualCssValue + " does not match " + cssValue);
		}
	}

	public static void assertJavaScriptErrors(
			WebDriver webDriver, String ignoreJavaScriptError)
		throws Exception {

		if (!PropsValues.TEST_ASSERT_JAVASCRIPT_ERRORS) {
			return;
		}

		String location = getLocation(webDriver);

		if (!location.contains("localhost")) {
			return;
		}

		String pageSource = null;

		try {
			pageSource = webDriver.getPageSource();
		}
		catch (Exception e) {
			WebDriver.TargetLocator targetLocator = webDriver.switchTo();

			targetLocator.window(_defaultWindowHandle);

			pageSource = webDriver.getPageSource();
		}

		if (pageSource.contains(
				"html id=\"feedHandler\" xmlns=" +
					"\"http://www.w3.org/1999/xhtml\"")) {

			return;
		}

		List<JavaScriptError> javaScriptErrors = new ArrayList<>();

		try {
			WebElement webElement = getWebElement(webDriver, "//body");

			WrapsDriver wrapsDriver = (WrapsDriver)webElement;

			WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

			javaScriptErrors.addAll(
				JavaScriptError.readErrors(wrappedWebDriver));
		}
		catch (Exception e) {
		}

		List<Exception> exceptions = new ArrayList<>();

		if (!javaScriptErrors.isEmpty()) {
			for (JavaScriptError javaScriptError : javaScriptErrors) {
				String javaScriptErrorValue = javaScriptError.toString();

				if (Validator.isNotNull(ignoreJavaScriptError) &&
					javaScriptErrorValue.contains(ignoreJavaScriptError)) {

					continue;
				}

				if (LiferaySeleniumHelper.isInIgnoreErrorsFile(
						javaScriptErrorValue, "javascript")) {

					continue;
				}

				String message = "JAVA_SCRIPT_ERROR: " + javaScriptErrorValue;

				System.out.println(message);

				exceptions.add(new PoshiRunnerWarningException(message));
			}
		}

		if (!exceptions.isEmpty()) {
			LiferaySeleniumHelper.addToJavaScriptExceptions(exceptions);

			throw exceptions.get(0);
		}
	}

	public static void check(WebDriver webDriver, String locator) {
		WebElement webElement = getWebElement(webDriver, locator);

		if (!webElement.isSelected()) {
			webElement.click();
		}
	}

	public static void executeJavaScriptMouseEvent(
		WebDriver webDriver, String locator, String event) {

		WebElement webElement = getWebElement(webDriver, locator);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		if (!webElement.isDisplayed()) {
			scrollWebElementIntoView(webDriver, webElement);
		}

		StringBuilder sb = new StringBuilder(6);

		sb.append("var element = arguments[0];");
		sb.append("var event = document.createEvent('MouseEvents');");
		sb.append("event.initEvent('");
		sb.append(event);
		sb.append("', true, false);");
		sb.append("element.dispatchEvent(event);");

		javascriptExecutor.executeScript(sb.toString(), webElement);
	}

	public static String getAttribute(
		WebDriver webDriver, String attributeLocator) {

		int pos = attributeLocator.lastIndexOf(CharPool.AT);

		String locator = attributeLocator.substring(0, pos);

		WebElement webElement = getWebElement(webDriver, locator);

		String attribute = attributeLocator.substring(pos + 1);

		return webElement.getAttribute(attribute);
	}

	public static By getBy(String locator) {
		if (locator.startsWith("//")) {
			return By.xpath(locator);
		}
		else if (locator.startsWith("class=")) {
			locator = locator.substring(6);

			return By.className(locator);
		}
		else if (locator.startsWith("css=")) {
			locator = locator.substring(4);

			return By.cssSelector(locator);
		}
		else if (locator.startsWith("link=")) {
			locator = locator.substring(5);

			return By.linkText(locator);
		}
		else if (locator.startsWith("name=")) {
			locator = locator.substring(5);

			return By.name(locator);
		}
		else if (locator.startsWith("tag=")) {
			locator = locator.substring(4);

			return By.tagName(locator);
		}
		else if (locator.startsWith("xpath=") || locator.startsWith("xPath=")) {
			locator = locator.substring(6);

			return By.xpath(locator);
		}
		else {
			return By.id(locator);
		}
	}

	public static String getConfirmation(WebDriver webDriver) {
		webDriver.switchTo();

		WebDriverWait webDriverWait = new WebDriverWait(webDriver, 1);

		try {
			Alert alert = webDriverWait.until(
				ExpectedConditions.alertIsPresent());

			String confirmation = alert.getText();

			alert.accept();

			return confirmation;
		}
		catch (Exception e) {
			throw new WebDriverException();
		}
	}

	public static String getCSSSource(String htmlSource) throws Exception {
		Document htmlDocument = Jsoup.parse(htmlSource);

		Elements elements = htmlDocument.select("link[type=text/css]");

		StringBuilder sb = new StringBuilder();

		for (Element element : elements) {
			String href = element.attr("href");

			if (!href.contains(PropsValues.PORTAL_URL)) {
				href = PropsValues.PORTAL_URL + href;
			}

			Connection connection = Jsoup.connect(href);

			Document document = connection.get();

			sb.append(document.text());
			sb.append("\n");
		}

		return sb.toString();
	}

	public static String getDefaultWindowHandle() {
		return _defaultWindowHandle;
	}

	public static int getElementHeight(WebDriver webDriver, String locator) {
		WebElement webElement = getWebElement(webDriver, locator, "1");

		Dimension dimension = webElement.getSize();

		return dimension.getHeight();
	}

	public static int getElementPositionBottom(
		WebDriver webDriver, String locator) {

		return getElementPositionTop(webDriver, locator) +
			getElementHeight(webDriver, locator);
	}

	public static int getElementPositionCenterX(
		WebDriver webDriver, String locator) {

		return getElementPositionLeft(webDriver, locator) +
			(getElementWidth(webDriver, locator) / 2);
	}

	public static int getElementPositionCenterY(
		WebDriver webDriver, String locator) {

		return getElementPositionTop(webDriver, locator) +
			(getElementHeight(webDriver, locator) / 2);
	}

	public static int getElementPositionLeft(
		WebDriver webDriver, String locator) {

		WebElement webElement = getWebElement(webDriver, locator, "1");

		Point point = webElement.getLocation();

		return point.getX();
	}

	public static int getElementPositionRight(
		WebDriver webDriver, String locator) {

		return getElementPositionLeft(webDriver, locator) +
			getElementWidth(webDriver, locator);
	}

	public static int getElementPositionTop(
		WebDriver webDriver, String locator) {

		WebElement webElement = getWebElement(webDriver, locator, "1");

		Point point = webElement.getLocation();

		return point.getY();
	}

	public static int getElementWidth(WebDriver webDriver, String locator) {
		WebElement webElement = getWebElement(webDriver, locator, "1");

		Dimension dimension = webElement.getSize();

		return dimension.getWidth();
	}

	public static String getEval(WebDriver webDriver, String script) {
		WebElement webElement = getWebElement(webDriver, "//body");

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		return (String)javascriptExecutor.executeScript(script);
	}

	public static Point getFramePoint(WebDriver webDriver) {
		int x = 0;
		int y = 0;

		WebElement bodyWebElement = getWebElement(webDriver, "//body");

		WrapsDriver wrapsDriver = (WrapsDriver)bodyWebElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		WebDriver.TargetLocator targetLocator = wrappedWebDriver.switchTo();

		targetLocator.window(_defaultWindowHandle);

		for (WebElement webElement : _frameWebElements) {
			Point point = webElement.getLocation();

			x += point.getX();
			y += point.getY();

			targetLocator.frame(webElement);
		}

		return new Point(x, y);
	}

	public static int getFramePositionLeft(WebDriver webDriver) {
		Point point = getFramePoint(webDriver);

		return point.getX();
	}

	public static int getFramePositionTop(WebDriver webDriver) {
		Point point = getFramePoint(webDriver);

		return point.getY();
	}

	public static String getLocation(WebDriver webDriver) {
		return webDriver.getCurrentUrl();
	}

	public static int getNavigationBarHeight() {
		return _navigationBarHeight;
	}

	public static int getScrollOffsetX(WebDriver webDriver) {
		WebElement bodyWebElement = getWebElement(webDriver, "//body");

		WrapsDriver wrapsDriver = (WrapsDriver)bodyWebElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		Object pageXOffset = javascriptExecutor.executeScript(
			"return window.pageXOffset;");

		return GetterUtil.getInteger(pageXOffset);
	}

	public static int getScrollOffsetY(WebDriver webDriver) {
		WebElement bodyWebElement = getWebElement(webDriver, "//body");

		WrapsDriver wrapsDriver = (WrapsDriver)bodyWebElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		Object pageYOffset = javascriptExecutor.executeScript(
			"return window.pageYOffset;");

		return GetterUtil.getInteger(pageYOffset);
	}

	public static String getSelectedLabel(
		WebDriver webDriver, String selectLocator, String timeout) {

		try {
			WebElement selectLocatorWebElement = getWebElement(
				webDriver, selectLocator, timeout);

			Select select = new Select(selectLocatorWebElement);

			WebElement firstSelectedOptionWebElement =
				select.getFirstSelectedOption();

			return firstSelectedOptionWebElement.getText();
		}
		catch (Exception e) {
			return null;
		}
	}

	public static String[] getSelectedLabels(
		WebDriver webDriver, String selectLocator) {

		WebElement selectLocatorWebElement = getWebElement(
			webDriver, selectLocator);

		Select select = new Select(selectLocatorWebElement);

		List<WebElement> allSelectedOptionsWebElements =
			select.getAllSelectedOptions();

		String[] selectedOptionsWebElements =
			new String[allSelectedOptionsWebElements.size()];

		for (int i = 0; i < allSelectedOptionsWebElements.size(); i++) {
			WebElement webElement = allSelectedOptionsWebElements.get(i);

			if (webElement != null) {
				selectedOptionsWebElements[i] = webElement.getText();
			}
		}

		return selectedOptionsWebElements;
	}

	public static int getViewportHeight(WebDriver webDriver) {
		WebElement bodyWebElement = getWebElement(webDriver, "//body");

		WrapsDriver wrapsDriver = (WrapsDriver)bodyWebElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		return GetterUtil.getInteger(
			javascriptExecutor.executeScript("return window.innerHeight;"));
	}

	public static int getViewportPositionBottom(WebDriver webDriver) {
		return getScrollOffsetY(webDriver) + getViewportHeight(webDriver);
	}

	public static Point getWindowPoint(WebDriver webDriver) {
		WebElement bodyWebElement = getWebElement(webDriver, "//body");

		WrapsDriver wrapsDriver = (WrapsDriver)bodyWebElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		WebDriver.Options options = wrappedWebDriver.manage();

		WebDriver.Window window = options.window();

		return window.getPosition();
	}

	public static int getWindowPositionLeft(WebDriver webDriver) {
		Point point = getWindowPoint(webDriver);

		return point.getX();
	}

	public static int getWindowPositionTop(WebDriver webDriver) {
		Point point = getWindowPoint(webDriver);

		return point.getY();
	}

	public static void goBack(WebDriver webDriver) {
		WebDriver.Navigation navigation = webDriver.navigate();

		navigation.back();
	}

	public static boolean isElementNotPresent(
		WebDriver webDriver, String locator) {

		return !isElementPresent(webDriver, locator);
	}

	public static boolean isElementPresent(
		WebDriver webDriver, String locator) {

		List<WebElement> webElements = getWebElements(webDriver, locator, "1");

		return !webElements.isEmpty();
	}

	public static boolean isNotSelectedLabel(
		WebDriver webDriver, String selectLocator, String pattern) {

		if (isElementNotPresent(webDriver, selectLocator)) {
			return false;
		}

		String[] selectedLabels = getSelectedLabels(webDriver, selectLocator);

		List<String> selectedLabelsList = Arrays.asList(selectedLabels);

		return !selectedLabelsList.contains(pattern);
	}

	public static boolean isPartialText(
		WebDriver webDriver, String locator, String value) {

		WebElement webElement = getWebElement(webDriver, locator, "1");

		String text = webElement.getText();

		return text.contains(value);
	}

	public static boolean isSelectedLabel(
		WebDriver webDriver, String selectLocator, String pattern) {

		if (isElementNotPresent(webDriver, selectLocator)) {
			return false;
		}

		return pattern.equals(getSelectedLabel(webDriver, selectLocator, "1"));
	}

	public static void makeVisible(WebDriver webDriver, String locator) {
		WebElement bodyWebElement = getWebElement(webDriver, "//body");

		WrapsDriver wrapsDriver = (WrapsDriver)bodyWebElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		StringBuilder sb = new StringBuilder();

		sb.append("var element = arguments[0];");
		sb.append("element.style.cssText = 'display:inline !important';");
		sb.append("element.style.overflow = 'visible';");
		sb.append("element.style.minHeight = '1px';");
		sb.append("element.style.minWidth = '1px';");
		sb.append("element.style.opacity = '1';");
		sb.append("element.style.visibility = 'visible';");

		WebElement locatorWebElement = getWebElement(webDriver, locator);

		javascriptExecutor.executeScript(sb.toString(), locatorWebElement);
	}

	public static void open(WebDriver webDriver, String url) {
		String targetURL = "";

		if (url.startsWith("/")) {
			targetURL = PropsValues.PORTAL_URL + url;
		}
		else {
			targetURL = url;
		}

		for (int i = 0; i < 2; i++) {
			try {
				webDriver.get(targetURL);

				if (PropsValues.BROWSER_TYPE.equals("internetexplorer")) {
					refresh(webDriver);
				}

				if (targetURL.equals(getLocation(webDriver))) {
					break;
				}

				Thread.sleep(1000);
			}
			catch (Exception e) {
			}
		}
	}

	public static void refresh(WebDriver webDriver) {
		WebDriver.Navigation navigation = webDriver.navigate();

		navigation.refresh();

		if (isAlertPresent(webDriver)) {
			getConfirmation(webDriver);
		}
	}

	public static void saveWebPage(String fileName, String htmlSource)
		throws Exception {

		if (!PropsValues.SAVE_WEB_PAGE) {
			return;
		}

		StringBuilder sb = new StringBuilder(3);

		sb.append("<style>");
		sb.append(getCSSSource(htmlSource));
		sb.append("</style></html>");

		FileUtil.write(fileName, htmlSource.replace("<\\html>", sb.toString()));
	}

	public static void select(
		WebDriver webDriver, String selectLocator, String optionLocator) {

		WebElement webElement = getWebElement(webDriver, selectLocator);

		Select select = new Select(webElement);

		String label = optionLocator;

		if (optionLocator.startsWith("index=")) {
			String indexString = optionLocator.substring(6);

			int index = GetterUtil.getInteger(indexString);

			select.selectByIndex(index - 1);
		}
		else if (optionLocator.startsWith("value=")) {
			String value = optionLocator.substring(6);

			if (value.startsWith("regexp:")) {
				String regexp = value.substring(7);

				selectByRegexpValue(webDriver, selectLocator, regexp);
			}
			else {
				List<WebElement> optionWebElements = select.getOptions();

				for (WebElement optionWebElement : optionWebElements) {
					String optionWebElementValue =
						optionWebElement.getAttribute("value");

					if (optionWebElementValue.equals(value)) {
						label = optionWebElement.getText();

						break;
					}
				}

				select.selectByValue(label);
			}
		}
		else {
			if (optionLocator.startsWith("label=")) {
				label = optionLocator.substring(6);
			}

			if (label.startsWith("regexp:")) {
				String regexp = label.substring(7);

				selectByRegexpText(webDriver, selectLocator, regexp);
			}
			else {
				select.selectByVisibleText(label);
			}
		}
	}

	public static void selectFrame(WebDriver webDriver, String locator) {
		WebDriver.TargetLocator targetLocator = webDriver.switchTo();

		if (locator.equals("relative=parent")) {
			targetLocator.window(_defaultWindowHandle);

			if (!_frameWebElements.isEmpty()) {
				_frameWebElements.pop();

				if (!_frameWebElements.isEmpty()) {
					targetLocator.frame(_frameWebElements.peek());
				}
			}
		}
		else if (locator.equals("relative=top")) {
			_frameWebElements = new Stack<>();

			targetLocator.window(_defaultWindowHandle);
		}
		else {
			_frameWebElements.push(getWebElement(webDriver, locator));

			targetLocator.frame(_frameWebElements.peek());
		}
	}

	public static void selectWindow(WebDriver webDriver, String windowID) {
		Set<String> windowHandles = webDriver.getWindowHandles();

		if (windowID.equals("name=undefined")) {
			String title = webDriver.getTitle();

			for (String windowHandle : windowHandles) {
				WebDriver.TargetLocator targetLocator = webDriver.switchTo();

				targetLocator.window(windowHandle);

				if (!title.equals(webDriver.getTitle())) {
					return;
				}
			}

			TestCase.fail("Unable to find the window ID \"" + windowID + "\"");
		}
		else if (windowID.equals("null")) {
			WebDriver.TargetLocator targetLocator = webDriver.switchTo();

			targetLocator.window(_defaultWindowHandle);
		}
		else {
			String targetWindowTitle = windowID;

			if (targetWindowTitle.startsWith("title=")) {
				targetWindowTitle = targetWindowTitle.substring(6);
			}

			for (String windowHandle : windowHandles) {
				WebDriver.TargetLocator targetLocator = webDriver.switchTo();

				targetLocator.window(windowHandle);

				if (targetWindowTitle.equals(webDriver.getTitle())) {
					return;
				}
			}

			TestCase.fail("Unable to find the window ID \"" + windowID + "\"");
		}
	}

	public static void setDefaultTimeoutImplicit(WebDriver webDriver) {
		int timeout = PropsValues.TIMEOUT_IMPLICIT_WAIT * 1000;

		setTimeoutImplicit(webDriver, String.valueOf(timeout));
	}

	public static void setDefaultWindowHandle(String defaultWindowHandle) {
		_defaultWindowHandle = defaultWindowHandle;
	}

	public static void setNavigationBarHeight(int navigationBarHeight) {
		_navigationBarHeight = navigationBarHeight;
	}

	public static void setTimeoutImplicit(WebDriver webDriver, String timeout) {
		WebDriver.Options options = webDriver.manage();

		WebDriver.Timeouts timeouts = options.timeouts();

		timeouts.implicitlyWait(
			GetterUtil.getInteger(timeout), TimeUnit.MILLISECONDS);
	}

	public static void type(WebDriver webDriver, String locator, String value) {
		WebElement webElement = getWebElement(webDriver, locator);

		if (!webElement.isEnabled()) {
			return;
		}

		webElement.clear();

		webElement.sendKeys(value);
	}

	public static void typeAlloyEditor(
		WebDriver webDriver, String locator, String value) {

		WebElement webElement = getWebElement(webDriver, locator);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		StringBuilder sb = new StringBuilder();

		sb.append("CKEDITOR.instances[\"");

		String titleAttribute = getAttribute(webDriver, locator + "@title");

		int x = titleAttribute.indexOf(",");
		int y = titleAttribute.indexOf(",", x + 1);

		if (y == -1) {
			y = titleAttribute.length();
		}

		sb.append(titleAttribute.substring(x + 2, y));

		sb.append("\"].setData(\"");
		sb.append(HtmlUtil.escapeJS(value.replace("\\", "\\\\")));
		sb.append("\");");

		javascriptExecutor.executeScript(sb.toString());
	}

	public static void uncheck(WebDriver webdDriver, String locator) {
		WebElement webElement = getWebElement(webdDriver, locator);

		if (webElement.isSelected()) {
			webElement.click();
		}
	}

	protected static WebElement getWebElement(
		WebDriver webDriver, String locator) {

		return getWebElement(webDriver, locator, null);
	}

	protected static WebElement getWebElement(
		WebDriver webDriver, String locator, String timeout) {

		List<WebElement> webElements = getWebElements(
			webDriver, locator, timeout);

		if (!webElements.isEmpty()) {
			return webElements.get(0);
		}

		return null;
	}

	protected static List<WebElement> getWebElements(
		WebDriver webDriver, String locator) {

		return getWebElements(webDriver, locator, null);
	}

	protected static List<WebElement> getWebElements(
		WebDriver webDriver, String locator, String timeout) {

		if (timeout != null) {
			setTimeoutImplicit(webDriver, timeout);
		}

		try {
			List<WebElement> webElements = new ArrayList<>();

			for (WebElement webElement :
					webDriver.findElements(getBy(locator))) {

				webElements.add(new RetryWebElementImpl(locator, webElement));
			}

			return webElements;
		}
		finally {
			if (timeout != null) {
				setDefaultTimeoutImplicit(webDriver);
			}
		}
	}

	protected static boolean isAlertPresent(WebDriver webDriver) {
		WebDriverWait webDriverWait = new WebDriverWait(webDriver, 1);

		try {
			webDriverWait.until(ExpectedConditions.alertIsPresent());

			return true;
		}
		catch (TimeoutException te) {
			return false;
		}
	}

	protected static void scrollWebElementIntoView(
		WebDriver webDriver, WebElement webElement) {

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		javascriptExecutor.executeScript(
			"arguments[0].scrollIntoView();", webElement);
	}

	protected static void selectByRegexpText(
		WebDriver webDriver, String selectLocator, String regexp) {

		WebElement webElement = getWebElement(webDriver, selectLocator);

		Select select = new Select(webElement);

		List<WebElement> optionWebElements = select.getOptions();

		Pattern pattern = Pattern.compile(regexp);

		int index = -1;

		for (WebElement optionWebElement : optionWebElements) {
			String optionWebElementText = optionWebElement.getText();

			Matcher matcher = pattern.matcher(optionWebElementText);

			if (matcher.matches()) {
				index = optionWebElements.indexOf(optionWebElement);

				break;
			}
		}

		select.selectByIndex(index);
	}

	protected static void selectByRegexpValue(
		WebDriver webDriver, String selectLocator, String regexp) {

		WebElement webElement = getWebElement(webDriver, selectLocator);

		Select select = new Select(webElement);

		List<WebElement> optionWebElements = select.getOptions();

		Pattern pattern = Pattern.compile(regexp);

		int index = -1;

		for (WebElement optionWebElement : optionWebElements) {
			String optionWebElementValue = optionWebElement.getAttribute(
				"value");

			Matcher matcher = pattern.matcher(optionWebElementValue);

			if (matcher.matches()) {
				index = optionWebElements.indexOf(optionWebElement);

				break;
			}
		}

		select.selectByIndex(index);
	}

	private static String _defaultWindowHandle;
	private static Stack<WebElement> _frameWebElements = new Stack<>();
	private static int _navigationBarHeight;

}