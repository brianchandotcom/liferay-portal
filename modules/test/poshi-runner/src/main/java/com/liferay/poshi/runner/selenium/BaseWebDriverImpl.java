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

import com.liferay.poshi.runner.util.FileUtil;
import com.liferay.poshi.runner.util.GetterUtil;
import com.liferay.poshi.runner.util.OSDetector;
import com.liferay.poshi.runner.util.PropsValues;
import com.liferay.poshi.runner.util.StringPool;
import com.liferay.poshi.runner.util.StringUtil;
import com.liferay.poshi.runner.util.Validator;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import java.io.StringReader;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import junit.framework.TestCase;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseWebDriverImpl
	extends WebDriverWrapper implements LiferaySelenium {

	public BaseWebDriverImpl(String browserURL, WebDriver webDriver) {
		super(webDriver);

		initKeysSpecialChars();

		WebDriverHelper.setDefaultWindowHandle(webDriver.getWindowHandle());
		WebDriverHelper.setNavigationBarHeight(120);

		System.setProperty("java.awt.headless", "false");

		String outputDirName = _OUTPUT_DIR_NAME;
		String sikuliImagesDirName =
			_TEST_DEPENDENCIES_DIR_NAME + "//sikuli//linux//";
		String testDependenciesDirName = _TEST_DEPENDENCIES_DIR_NAME;

		if (OSDetector.isWindows()) {
			outputDirName = StringUtil.replace(outputDirName, "//", "\\");

			sikuliImagesDirName = StringUtil.replace(
				sikuliImagesDirName, "//", "\\");
			sikuliImagesDirName = StringUtil.replace(
				sikuliImagesDirName, "linux", "windows");

			testDependenciesDirName = StringUtil.replace(
				testDependenciesDirName, "//", "\\");
		}

		_outputDirName = outputDirName;
		_sikuliImagesDirName = sikuliImagesDirName;
		_testDependenciesDirName = testDependenciesDirName;

		WebDriver.Options options = webDriver.manage();

		WebDriver.Window window = options.window();

		window.setSize(new Dimension(1280, 1040));

		webDriver.get(browserURL);
	}

	@Override
	public void addCustomRequestHeader(String key, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addLocationStrategy(
		String strategyName, String functionDefinition) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void addScript(String script, String scriptTagId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addSelection(String locator, String optionLocator) {
		WebDriverHelper.addSelection(this, locator, optionLocator);
	}

	@Override
	public void allowNativeXpath(String allow) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void altKeyDown() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void altKeyUp() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void answerOnNextPrompt(String answer) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void antCommand(String fileName, String target) throws Exception {
		LiferaySeleniumHelper.antCommand(this, fileName, target);
	}

	@Override
	public void assertAlert(String pattern) throws Exception {
		LiferaySeleniumHelper.assertAlert(this, pattern);
	}

	@Override
	public void assertAlertNotPresent() throws Exception {
		LiferaySeleniumHelper.assertAlertNotPresent(this);
	}

	@Override
	public void assertChecked(String locator) throws Exception {
		LiferaySeleniumHelper.assertChecked(this, locator);
	}

	@Override
	public void assertConfirmation(String pattern) throws Exception {
		LiferaySeleniumHelper.assertConfirmation(this, pattern);
	}

	@Override
	public void assertConsoleErrors() throws Exception {
		LiferaySeleniumHelper.assertConsoleErrors();
	}

	@Override
	public void assertConsoleTextNotPresent(String text) throws Exception {
		LiferaySeleniumHelper.assertConsoleTextNotPresent(text);
	}

	@Override
	public void assertConsoleTextPresent(String text) throws Exception {
		LiferaySeleniumHelper.assertConsoleTextPresent(text);
	}

	@Override
	public void assertCssValue(
			String locator, String cssAttribute, String cssValue)
		throws Exception {

		WebDriverHelper.assertCssValue(this, locator, cssAttribute, cssValue);
	}

	@Override
	public void assertEditable(String locator) throws Exception {
		LiferaySeleniumHelper.assertEditable(this, locator);
	}

	@Override
	public void assertElementNotPresent(String locator) throws Exception {
		LiferaySeleniumHelper.assertElementNotPresent(this, locator);
	}

	@Override
	public void assertElementPresent(String locator) throws Exception {
		LiferaySeleniumHelper.assertElementPresent(this, locator);
	}

	@Override
	public void assertEmailBody(String index, String body) throws Exception {
		LiferaySeleniumHelper.assertEmailBody(this, index, body);
	}

	@Override
	public void assertEmailSubject(String index, String subject)
		throws Exception {

		LiferaySeleniumHelper.assertEmailSubject(this, index, subject);
	}

	@Override
	public void assertHTMLSourceTextNotPresent(String value) throws Exception {
		LiferaySeleniumHelper.assertHTMLSourceTextPresent(this, value);
	}

	@Override
	public void assertHTMLSourceTextPresent(String value) throws Exception {
		LiferaySeleniumHelper.assertHTMLSourceTextPresent(this, value);
	}

	@Override
	public void assertJavaScriptErrors(String ignoreJavaScriptError)
		throws Exception {

		WebDriverHelper.assertJavaScriptErrors(this, ignoreJavaScriptError);
	}

	@Override
	public void assertLiferayErrors() throws Exception {
		LiferaySeleniumHelper.assertConsoleErrors();
	}

	@Override
	public void assertLocation(String pattern) throws Exception {
		LiferaySeleniumHelper.assertLocation(this, pattern);
	}

	@Override
	public void assertNoJavaScriptExceptions() throws Exception {
		LiferaySeleniumHelper.assertNoJavaScriptExceptions();
	}

	@Override
	public void assertNoLiferayExceptions() throws Exception {
		LiferaySeleniumHelper.assertNoLiferayExceptions();
	}

	@Override
	public void assertNotAlert(String pattern) {
		LiferaySeleniumHelper.assertNotAlert(this, pattern);
	}

	@Override
	public void assertNotChecked(String locator) throws Exception {
		LiferaySeleniumHelper.assertNotChecked(this, locator);
	}

	@Override
	public void assertNotEditable(String locator) throws Exception {
		LiferaySeleniumHelper.assertNotEditable(this, locator);
	}

	@Override
	public void assertNotLocation(String pattern) throws Exception {
		LiferaySeleniumHelper.assertNotLocation(this, pattern);
	}

	@Override
	public void assertNotPartialText(String locator, String pattern)
		throws Exception {

		LiferaySeleniumHelper.assertNotPartialText(this, locator, pattern);
	}

	@Override
	public void assertNotSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		LiferaySeleniumHelper.assertNotSelectedLabel(
			this, selectLocator, pattern);
	}

	@Override
	public void assertNotText(String locator, String pattern) throws Exception {
		LiferaySeleniumHelper.assertNotText(this, locator, pattern);
	}

	@Override
	public void assertNotValue(String locator, String pattern)
		throws Exception {

		LiferaySeleniumHelper.assertNotValue(this, locator, pattern);
	}

	@Override
	public void assertNotVisible(String locator) throws Exception {
		LiferaySeleniumHelper.assertNotVisible(this, locator);
	}

	@Override
	public void assertPartialConfirmation(String pattern) throws Exception {
		LiferaySeleniumHelper.assertPartialConfirmation(this, pattern);
	}

	@Override
	public void assertPartialText(String locator, String pattern)
		throws Exception {

		LiferaySeleniumHelper.assertPartialText(this, locator, pattern);
	}

	@Override
	public void assertSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		LiferaySeleniumHelper.assertSelectedLabel(this, selectLocator, pattern);
	}

	@Override
	public void assertText(String locator, String pattern) throws Exception {
		LiferaySeleniumHelper.assertText(this, locator, pattern);
	}

	@Override
	public void assertTextNotPresent(String pattern) throws Exception {
		LiferaySeleniumHelper.assertTextNotPresent(this, pattern);
	}

	@Override
	public void assertTextPresent(String pattern) throws Exception {
		LiferaySeleniumHelper.assertTextPresent(this, pattern);
	}

	@Override
	public void assertValue(String locator, String pattern) throws Exception {
		LiferaySeleniumHelper.assertValue(this, locator, pattern);
	}

	@Override
	public void assertVisible(String locator) throws Exception {
		LiferaySeleniumHelper.assertVisible(this, locator);
	}

	@Override
	public void assignId(String locator, String identifier) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void attachFile(String fieldLocator, String fileLocator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void captureEntirePageScreenshot(String fileName, String kwargs) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String captureEntirePageScreenshotToString(String kwargs) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String captureNetworkTraffic(String type) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void captureScreenshot(String fileName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String captureScreenshotToString() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void check(String locator) {
		WebDriverHelper.check(this, locator);
	}

	@Override
	public void chooseCancelOnNextConfirmation() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void chooseOkOnNextConfirmation() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void click(String locator) {
		if (locator.contains("x:")) {
			String url = getHtmlNodeHref(locator);

			open(url);
		}
		else {
			WebElement webElement = getWebElement(locator);

			try {
				webElement.click();
			}
			catch (Exception e) {
				if (!webElement.isDisplayed()) {
					scrollWebElementIntoView(webElement);
				}

				webElement.click();
			}
		}
	}

	@Override
	public void clickAndWait(String locator) {
		click(locator);
		waitForPageToLoad("30000");
	}

	@Override
	public void clickAt(String locator, String coordString) {
		clickAt(locator, coordString, true);
	}

	public void clickAt(
		String locator, String coordString, boolean scrollIntoView) {

		int offsetX = 0;
		int offsetY = 0;

		if (Validator.isNotNull(coordString) && coordString.contains(",")) {
			String[] coords = coordString.split(",");

			offsetX = GetterUtil.getInteger(coords[0]);
			offsetY = GetterUtil.getInteger(coords[1]);
		}

		if ((offsetX == 0) && (offsetY == 0)) {
			click(locator);
		}
		else {
			WebElement bodyWebElement = getWebElement("//body");

			WrapsDriver wrapsDriver = (WrapsDriver)bodyWebElement;

			WebDriver webDriver = wrapsDriver.getWrappedDriver();

			WebDriver.Options options = webDriver.manage();

			WebDriver.Window window = options.window();

			Point windowPoint = window.getPosition();

			WebElement webElement = getWebElement(locator);

			Point webElementPoint = webElement.getLocation();

			int clickDestinationX = 0;
			int clickDestinationY = 0;

			if (scrollIntoView) {
				scrollWebElementIntoView(webElement);

				clickDestinationX =
					windowPoint.getX() + webElementPoint.getX() + offsetX;
				clickDestinationY = windowPoint.getY() + offsetY;
			}
			else {
				clickDestinationX =
					windowPoint.getX() + webElementPoint.getX() + offsetX;
				clickDestinationY =
					windowPoint.getY() + webElementPoint.getY() + offsetY;
			}

			try {
				Robot robot = new Robot();

				robot.mouseMove(clickDestinationX, clickDestinationY);

				robot.mousePress(KeyEvent.BUTTON1_MASK);

				robot.delay(1500);

				robot.mouseRelease(KeyEvent.BUTTON1_MASK);

				robot.delay(1500);
			}
			catch (Exception e) {
			}
		}
	}

	@Override
	public void clickAtAndWait(String locator, String coordString) {
		clickAt(locator, coordString);
		waitForPageToLoad("30000");
	}

	@Override
	public void connectToEmailAccount(String emailAddress, String emailPassword)
		throws Exception {

		LiferaySeleniumHelper.connectToEmailAccount(
			emailAddress, emailPassword);
	}

	@Override
	public void contextMenu(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void contextMenuAt(String locator, String coordString) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void controlKeyDown() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void controlKeyUp() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void copyText(String locator) throws Exception {
		_clipBoard = getText(locator);
	}

	@Override
	public void copyValue(String locator) throws Exception {
		_clipBoard = getElementValue(locator);
	}

	@Override
	public void createCookie(String nameValuePair, String optionsString) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteAllEmails() throws Exception {
		LiferaySeleniumHelper.deleteAllEmails();
	}

	@Override
	public void deleteAllVisibleCookies() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteCookie(String name, String optionsString) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deselectPopUp() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void doubleClick(String locator) {
		WebElement webElement = getWebElement(locator);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		actions.doubleClick(webElement);

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void doubleClickAt(String locator, String coordString) {
		WebElement webElement = getWebElement(locator);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		if (Validator.isNotNull(coordString) && coordString.contains(",")) {
			String[] coords = coordString.split(",");

			int x = GetterUtil.getInteger(coords[0]);
			int y = GetterUtil.getInteger(coords[1]);

			actions.moveToElement(webElement, x, y);

			actions.doubleClick();
		}
		else {
			actions.doubleClick(webElement);
		}

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void dragAndDrop(String locator, String coordString) {
		try {
			int x = WebDriverHelper.getElementPositionCenterX(this, locator);

			x += WebDriverHelper.getFramePositionLeft(this);
			x += WebDriverHelper.getWindowPositionLeft(this);
			x -= WebDriverHelper.getScrollOffsetX(this);

			int y = WebDriverHelper.getElementPositionCenterY(this, locator);

			y += WebDriverHelper.getFramePositionTop(this);
			y += WebDriverHelper.getNavigationBarHeight();
			y += WebDriverHelper.getWindowPositionTop(this);
			y -= WebDriverHelper.getScrollOffsetY(this);

			Robot robot = new Robot();

			robot.mouseMove(x, y);

			robot.delay(1500);

			robot.mousePress(InputEvent.BUTTON1_MASK);

			robot.delay(1500);

			String[] coords = coordString.split(",");

			x += GetterUtil.getInteger(coords[0]);
			y += GetterUtil.getInteger(coords[1]);

			robot.mouseMove(x, y);

			robot.delay(1500);

			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		}
		catch (Exception e) {
		}
	}

	@Override
	public void dragAndDropToObject(
		String locatorOfObjectToBeDragged,
		String locatorOfDragDestinationObject) {

		WebElement objectToBeDraggedWebElement = getWebElement(
			locatorOfObjectToBeDragged);

		WrapsDriver wrapsDriver = (WrapsDriver)objectToBeDraggedWebElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		WebElement dragDestinationObjectWebElement = getWebElement(
			locatorOfDragDestinationObject);

		actions.dragAndDrop(
			objectToBeDraggedWebElement, dragDestinationObjectWebElement);

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void dragdrop(String locator, String movementsString) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void echo(String message) {
		LiferaySeleniumHelper.echo(message);
	}

	@Override
	public void fail(String message) {
		LiferaySeleniumHelper.fail(message);
	}

	@Override
	public void fireEvent(String locator, String eventName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void focus(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getAlert() {
		switchTo();

		WebDriverWait webDriverWait = new WebDriverWait(this, 1);

		Alert alert = webDriverWait.until(ExpectedConditions.alertIsPresent());

		return alert.getText();
	}

	@Override
	public String[] getAllButtons() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String[] getAllFields() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String[] getAllLinks() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String[] getAllWindowIds() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String[] getAllWindowNames() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String[] getAllWindowTitles() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getAttribute(String attributeLocator) {
		return WebDriverHelper.getAttribute(this, attributeLocator);
	}

	@Override
	public String[] getAttributeFromAllWindows(String attributeName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getBodyText() {
		WebElement webElement = findElement(By.tagName("body"));

		return webElement.getText();
	}

	@Override
	public String getConfirmation() {
		return WebDriverHelper.getConfirmation(this);
	}

	@Override
	public String getCookie() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCookieByName(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Number getCssCount(String css) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCurrentDay() {
		Calendar calendar = Calendar.getInstance();

		return StringUtil.valueOf(calendar.get(Calendar.DATE));
	}

	@Override
	public String getCurrentDayName() {
		Calendar calendar = Calendar.getInstance();

		return StringUtil.valueOf(
			calendar.getDisplayName(
				Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US));
	}

	@Override
	public String getCurrentMonth() {
		Calendar calendar = Calendar.getInstance();

		return StringUtil.valueOf(calendar.get(Calendar.MONTH) + 1);
	}

	@Override
	public String getCurrentYear() {
		Calendar calendar = Calendar.getInstance();

		return StringUtil.valueOf(calendar.get(Calendar.YEAR));
	}

	@Override
	public Number getCursorPosition(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Number getElementHeight(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Number getElementIndex(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Number getElementPositionLeft(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Number getElementPositionTop(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getElementValue(String locator) throws Exception {
		return getElementValue(locator, null);
	}

	public String getElementValue(String locator, String timeout)
		throws Exception {

		WebElement webElement = getWebElement(locator, timeout);

		if (webElement == null) {
			throw new Exception(
				"Element is not present at \"" + locator + "\"");
		}

		if (!webElement.isDisplayed()) {
			scrollWebElementIntoView(webElement);
		}

		return webElement.getAttribute("value");
	}

	@Override
	public Number getElementWidth(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getEmailBody(String index) throws Exception {
		return LiferaySeleniumHelper.getEmailBody(index);
	}

	@Override
	public String getEmailSubject(String index) throws Exception {
		return LiferaySeleniumHelper.getEmailSubject(index);
	}

	@Override
	public String getEval(String script) {
		return WebDriverHelper.getEval(this, script);
	}

	@Override
	public String getExpression(String expression) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getFirstNumber(String locator) {
		WebElement webElement = getWebElement(locator);

		String text = webElement.getText();

		if (text == null) {
			return StringPool.BLANK;
		}

		StringBuilder sb = new StringBuilder();

		char[] chars = text.toCharArray();

		for (char c : chars) {
			boolean digit = false;

			if (Validator.isDigit(c)) {
				sb.append(c);

				digit = true;
			}

			String s = sb.toString();

			if (Validator.isNotNull(s) && !digit) {
				return s;
			}
		}

		return sb.toString();
	}

	@Override
	public String getFirstNumberIncrement(String locator) {
		String firstNumber = getFirstNumber(locator);

		return StringUtil.valueOf(GetterUtil.getInteger(firstNumber) + 1);
	}

	public Node getHtmlNode(String locator) {
		try {
			XPathFactory xPathFactory = XPathFactory.newInstance();

			XPath xPath = xPathFactory.newXPath();

			locator = StringUtil.replace(locator, "x:", "");

			XPathExpression xPathExpression = xPath.compile(locator);

			DocumentBuilderFactory documentBuilderFactory =
				DocumentBuilderFactory.newInstance();

			DocumentBuilder documentBuilder =
				documentBuilderFactory.newDocumentBuilder();

			String htmlSource = getHtmlSource();

			htmlSource = htmlSource.substring(htmlSource.indexOf("<html"));

			StringReader stringReader = new StringReader(htmlSource);

			InputSource inputSource = new InputSource(stringReader);

			Document document = documentBuilder.parse(inputSource);

			NodeList nodeList = (NodeList)xPathExpression.evaluate(
				document, XPathConstants.NODESET);

			if (nodeList.getLength() < 1) {
				throw new Exception(locator + " is not present");
			}

			return nodeList.item(0);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String getHtmlNodeHref(String locator) {
		Node elementNode = getHtmlNode(locator);

		NamedNodeMap namedNodeMap = elementNode.getAttributes();

		Node attributeNode = namedNodeMap.getNamedItem("href");

		return attributeNode.getTextContent();
	}

	public String getHtmlNodeText(String locator) throws Exception {
		Node node = getHtmlNode(locator);

		if (node == null) {
			throw new Exception(locator + " is not present");
		}

		return node.getTextContent();
	}

	@Override
	public String getHtmlSource() {
		return getPageSource();
	}

	@Override
	public String getLocation() throws Exception {
		return WebDriverHelper.getLocation(this);
	}

	@Override
	public String getLog() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Number getMouseSpeed() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getNumberDecrement(String value) {
		return LiferaySeleniumHelper.getNumberDecrement(value);
	}

	@Override
	public String getNumberIncrement(String value) {
		return LiferaySeleniumHelper.getNumberIncrement(value);
	}

	@Override
	public String getOutputDirName() {
		return _outputDirName;
	}

	@Override
	public String getPrimaryTestSuiteName() {
		return _primaryTestSuiteName;
	}

	@Override
	public String getPrompt() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSelectedId(String selectLocator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String[] getSelectedIds(String selectLocator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSelectedIndex(String selectLocator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String[] getSelectedIndexes(String selectLocator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSelectedLabel(String selectLocator) {
		return getSelectedLabel(selectLocator, null);
	}

	public String getSelectedLabel(String selectLocator, String timeout) {
		return WebDriverHelper.getSelectedLabel(this, selectLocator, timeout);
	}

	@Override
	public String[] getSelectedLabels(String selectLocator) {
		return WebDriverHelper.getSelectedLabels(this, selectLocator);
	}

	@Override
	public String getSelectedValue(String selectLocator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String[] getSelectedValues(String selectLocator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String[] getSelectOptions(String selectLocator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSikuliImagesDirName() {
		return _sikuliImagesDirName;
	}

	@Override
	public String getSpeed() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getTable(String tableCellAddress) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getTestDependenciesDirName() {
		return _testDependenciesDirName;
	}

	@Override
	public String getText(String locator) throws Exception {
		return getText(locator, null);
	}

	public String getText(String locator, String timeout) throws Exception {
		if (locator.contains("x:")) {
			return getHtmlNodeText(locator);
		}

		WebElement webElement = getWebElement(locator, timeout);

		if (webElement == null) {
			throw new Exception(
				"Element is not present at \"" + locator + "\"");
		}

		if (!webElement.isDisplayed()) {
			scrollWebElementIntoView(webElement);
		}

		String text = webElement.getText();

		text = text.trim();

		return text.replace("\n", " ");
	}

	@Override
	public String getTitle() {
		return super.getTitle();
	}

	@Override
	public String getValue(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getWhetherThisFrameMatchFrameExpression(
		String currentFrameString, String target) {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getWhetherThisWindowMatchWindowExpression(
		String currentWindowString, String target) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Number getXpathCount(String xPath) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void goBack() {
		WebDriverHelper.goBack(this);
	}

	@Override
	public void goBackAndWait() {
		goBack();
		waitForPageToLoad("30000");
	}

	@Override
	public void highlight(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void ignoreAttributesWithoutValue(String ignore) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isAlertPresent() {
		boolean alertPresent = false;

		switchTo();

		try {
			WebDriverWait webDriverWait = new WebDriverWait(this, 1);

			webDriverWait.until(ExpectedConditions.alertIsPresent());

			alertPresent = true;
		}
		catch (Exception e) {
			alertPresent = false;
		}

		return alertPresent;
	}

	@Override
	public boolean isChecked(String locator) {
		WebElement webElement = getWebElement(locator, "1");

		if (!webElement.isDisplayed()) {
			scrollWebElementIntoView(webElement);
		}

		return webElement.isSelected();
	}

	@Override
	public boolean isConfirmation(String pattern) {
		return LiferaySeleniumHelper.isConfirmation(this, pattern);
	}

	@Override
	public boolean isConfirmationPresent() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCookiePresent(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isEditable(String locator) {
		WebElement webElement = getWebElement(locator);

		return webElement.isEnabled();
	}

	@Override
	public boolean isElementNotPresent(String locator) {
		return WebDriverHelper.isElementNotPresent(this, locator);
	}

	@Override
	public boolean isElementPresent(String locator) {
		return WebDriverHelper.isElementPresent(this, locator);
	}

	@Override
	public boolean isElementPresentAfterWait(String locator) throws Exception {
		return LiferaySeleniumHelper.isElementPresentAfterWait(this, locator);
	}

	@Override
	public boolean isHTMLSourceTextPresent(String value) throws Exception {
		return LiferaySeleniumHelper.isHTMLSourceTextPresent(this, value);
	}

	@Override
	public boolean isNotChecked(String locator) {
		return LiferaySeleniumHelper.isNotChecked(this, locator);
	}

	@Override
	public boolean isNotEditable(String locator) {
		return !isEditable(locator);
	}

	@Override
	public boolean isNotPartialText(String locator, String value) {
		return LiferaySeleniumHelper.isNotPartialText(this, locator, value);
	}

	@Override
	public boolean isNotSelectedLabel(String selectLocator, String pattern) {
		return WebDriverHelper.isNotSelectedLabel(this, selectLocator, pattern);
	}

	@Override
	public boolean isNotText(String locator, String value) throws Exception {
		return LiferaySeleniumHelper.isNotText(this, locator, value);
	}

	@Override
	public boolean isNotValue(String locator, String value) throws Exception {
		return LiferaySeleniumHelper.isNotValue(this, locator, value);
	}

	@Override
	public boolean isNotVisible(String locator) {
		return LiferaySeleniumHelper.isNotVisible(this, locator);
	}

	@Override
	public boolean isOrdered(String locator1, String locator2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isPartialText(String locator, String value) {
		return WebDriverHelper.isPartialText(this, locator, value);
	}

	@Override
	public boolean isPromptPresent() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSelectedLabel(String selectLocator, String pattern) {
		return WebDriverHelper.isSelectedLabel(this, selectLocator, pattern);
	}

	@Override
	public boolean isSikuliImagePresent(String image) throws Exception {
		return LiferaySeleniumHelper.isSikuliImagePresent(this, image);
	}

	@Override
	public boolean isSomethingSelected(String selectLocator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isTCatEnabled() {
		return LiferaySeleniumHelper.isTCatEnabled();
	}

	@Override
	public boolean isTestName(String testName) {
		return LiferaySeleniumHelper.isTestName(testName);
	}

	@Override
	public boolean isText(String locator, String value) throws Exception {
		return value.equals(getText(locator, "1"));
	}

	@Override
	public boolean isTextNotPresent(String pattern) {
		return LiferaySeleniumHelper.isTextNotPresent(this, pattern);
	}

	@Override
	public boolean isTextPresent(String pattern) {
		WebElement webElement = findElement(By.tagName("body"));

		String text = webElement.getText();

		return text.contains(pattern);
	}

	@Override
	public boolean isValue(String locator, String value) throws Exception {
		return value.equals(getElementValue(locator, "1"));
	}

	@Override
	public boolean isVisible(String locator) {
		WebElement webElement = getWebElement(locator, "1");

		if (!webElement.isDisplayed()) {
			scrollWebElementIntoView(webElement);
		}

		return webElement.isDisplayed();
	}

	@Override
	public void javaScriptMouseDown(String locator) {
		WebDriverHelper.executeJavaScriptEvent(
			this, locator, "MouseEvent", "mousedown");
	}

	@Override
	public void javaScriptMouseUp(String locator) {
		WebDriverHelper.executeJavaScriptEvent(
			this, locator, "MouseEvent", "mouseup");
	}

	@Override
	public void keyDown(String locator, String keySequence) {
		WebElement webElement = getWebElement(locator);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		String keycode = keySequence.substring(1);

		Keys keys = Keys.valueOf(keycode);

		actions.keyDown(webElement, keys);

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void keyDownAndWait(String locator, String keySequence) {
		keyDown(locator, keySequence);
		waitForPageToLoad("30000");
	}

	@Override
	public void keyDownNative(String keycode) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void keyPress(String locator, String keySequence) {
		WebElement webElement = getWebElement(locator);

		if (keySequence.startsWith("\\")) {
			String keycode = keySequence.substring(1);

			if (isValidKeycode(keycode)) {
				Keys keys = Keys.valueOf(keycode);

				WrapsDriver wrapsDriver = (WrapsDriver)webElement;

				WebDriver webDriver = wrapsDriver.getWrappedDriver();

				Actions actions = new Actions(webDriver);

				if (keycode.equals("ALT") || keycode.equals("COMMAND") ||
					keycode.equals("CONTROL") || keycode.equals("SHIFT")) {

					actions.keyDown(webElement, keys);
					actions.keyUp(webElement, keys);

					Action action = actions.build();

					action.perform();
				}
				else {
					webElement.sendKeys(keys);
				}
			}
		}
		else {
			webElement.sendKeys(keySequence);
		}
	}

	@Override
	public void keyPressAndWait(String locator, String keySequence) {
		keyPress(locator, keySequence);
		waitForPageToLoad("30000");
	}

	@Override
	public void keyPressNative(String keycode) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void keyUp(String locator, String keySequence) {
		WebElement webElement = getWebElement(locator);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		String keycode = keySequence.substring(1);

		Keys keys = Keys.valueOf(keycode);

		actions.keyUp(webElement, keys);

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void keyUpAndWait(String locator, String keySequence) {
		keyUp(locator, keySequence);
		waitForPageToLoad("30000");
	}

	@Override
	public void keyUpNative(String keycode) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void makeVisible(String locator) {
		WebDriverHelper.makeVisible(this, locator);
	}

	@Override
	public void metaKeyDown() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void metaKeyUp() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void mouseDown(String locator) {
		WebElement webElement = getWebElement(locator);

		if (!webElement.isDisplayed()) {
			scrollWebElementIntoView(webElement);
		}

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		actions.moveToElement(webElement);

		actions.clickAndHold(webElement);

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void mouseDownAt(String locator, String coordString) {
		WebElement webElement = getWebElement(locator);

		if (!webElement.isDisplayed()) {
			scrollWebElementIntoView(webElement);
		}

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		if (Validator.isNotNull(coordString) && coordString.contains(",")) {
			String[] coords = coordString.split(",");

			int x = GetterUtil.getInteger(coords[0]);
			int y = GetterUtil.getInteger(coords[1]);

			actions.moveToElement(webElement, x, y);

			actions.clickAndHold();
		}
		else {
			actions.moveToElement(webElement);

			actions.clickAndHold(webElement);
		}

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void mouseDownRight(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void mouseDownRightAt(String locator, String coordString) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void mouseMove(String locator) {
		WebElement webElement = getWebElement(locator);

		if (!webElement.isDisplayed()) {
			scrollWebElementIntoView(webElement);
		}

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		actions.moveToElement(webElement);

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void mouseMoveAt(String locator, String coordString) {
		WebElement webElement = getWebElement(locator);

		if (!webElement.isDisplayed()) {
			scrollWebElementIntoView(webElement);
		}

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		if (Validator.isNotNull(coordString) && coordString.contains(",")) {
			String[] coords = coordString.split(",");

			int x = GetterUtil.getInteger(coords[0]);
			int y = GetterUtil.getInteger(coords[1]);

			actions.moveToElement(webElement, x, y);
		}
		else {
			actions.moveToElement(webElement);
		}

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void mouseOut(String locator) {
		WebElement webElement = getWebElement(locator);

		if (!webElement.isDisplayed()) {
			scrollWebElementIntoView(webElement);
		}

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		actions.moveToElement(webElement);
		actions.moveByOffset(10, 10);

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void mouseOver(String locator) {
		WebElement webElement = getWebElement(locator);

		if (!webElement.isDisplayed()) {
			scrollWebElementIntoView(webElement);
		}

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		actions.moveToElement(webElement);

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void mouseRelease() {
		WebElement bodyWebElement = getWebElement("//body");

		WrapsDriver wrapsDriver = (WrapsDriver)bodyWebElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		actions.release();

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void mouseUp(String locator) {
		WebElement webElement = getWebElement(locator);

		if (!webElement.isDisplayed()) {
			scrollWebElementIntoView(webElement);
		}

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		actions.release(webElement);

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void mouseUpAt(String locator, String coordString) {
		WebElement webElement = getWebElement(locator);

		if (!webElement.isDisplayed()) {
			scrollWebElementIntoView(webElement);
		}

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		if (Validator.isNotNull(coordString) && coordString.contains(",")) {
			String[] coords = coordString.split(",");

			int x = GetterUtil.getInteger(coords[0]);
			int y = GetterUtil.getInteger(coords[1]);

			actions.moveToElement(webElement, x, y);
			actions.release();
		}
		else {
			actions.moveToElement(webElement);
			actions.release(webElement);
		}

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void mouseUpRight(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void mouseUpRightAt(String locator, String coordString) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void open(String url) {
		WebDriverHelper.open(this, url);
	}

	@Override
	public void open(String url, String ignoreResponseCode) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void openWindow(String url, String windowID) {
		open(url);
	}

	@Override
	public void paste(String location) {
		type(location, _clipBoard);
	}

	@Override
	public void pause(String waitTime) throws Exception {
		LiferaySeleniumHelper.pause(waitTime);
	}

	@Override
	public void pauseLoggerCheck() throws Exception {
	}

	@Override
	public void refresh() {
		WebDriverHelper.refresh(this);
	}

	@Override
	public void refreshAndWait() {
		refresh();
		waitForPageToLoad("30000");
	}

	@Override
	public void removeAllSelections(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeScript(String scriptTagId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeSelection(String locator, String optionLocator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void replyToEmail(String to, String body) throws Exception {
		LiferaySeleniumHelper.replyToEmail(this, to, body);
	}

	@Override
	public String retrieveLastRemoteControlLogs() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void rollup(String rollupName, String kwargs) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void runScript(String script) {
		getEval(script);
	}

	@Override
	public void saveScreenshot() throws Exception {
		if (!PropsValues.SAVE_SCREENSHOT) {
			return;
		}

		LiferaySeleniumHelper.saveScreenshot(this);
	}

	@Override
	public void saveScreenshotAndSource() throws Exception {
	}

	@Override
	public void saveScreenshotBeforeAction(boolean actionFailed)
		throws Exception {

		if (!PropsValues.SAVE_SCREENSHOT) {
			return;
		}

		LiferaySeleniumHelper.saveScreenshotBeforeAction(this, actionFailed);
	}

	@Override
	public void scrollWebElementIntoView(String locator) throws Exception {
		WebElement webElement = getWebElement(locator);

		scrollWebElementIntoView(webElement);
	}

	@Override
	public void select(String selectLocator, String optionLocator) {
		WebDriverHelper.select(this, selectLocator, optionLocator);
	}

	@Override
	public void selectAndWait(String selectLocator, String optionLocator) {
		select(selectLocator, optionLocator);
		waitForPageToLoad("30000");
	}

	@Override
	public void selectFieldText() {
		LiferaySeleniumHelper.selectFieldText();
	}

	@Override
	public void selectFrame(String locator) {
		WebDriverHelper.selectFrame(this, locator);
	}

	@Override
	public void selectPopUp(String windowID) {
		Set<String> windowHandles = getWindowHandles();

		if (windowID.equals("") || windowID.equals("null")) {
			String title = getTitle();

			for (String windowHandle : windowHandles) {
				WebDriver.TargetLocator targetLocator = switchTo();

				targetLocator.window(windowHandle);

				if (!title.equals(getTitle())) {
					return;
				}
			}
		}
		else {
			selectWindow(windowID);
		}
	}

	@Override
	public void selectWindow(String windowID) {
		WebDriverHelper.selectWindow(this, windowID);
	}

	@Override
	public void sendActionDescriptionLogger(String description) {
	}

	@Override
	public boolean sendActionLogger(String command, String[] params) {
		return true;
	}

	@Override
	public void sendEmail(String to, String subject, String body)
		throws Exception {

		LiferaySeleniumHelper.sendEmail(this, to, subject, body);
	}

	@Override
	public void sendKeys(String locator, String value) {
		typeKeys(locator, value);
	}

	@Override
	public void sendKeysAceEditor(String locator, String value) {
		WebElement webElement = getWebElement(locator);

		webElement.sendKeys(Keys.chord(Keys.CONTROL, Keys.END));

		LiferaySeleniumHelper.typeAceEditor(this, locator, value);
	}

	@Override
	public void sendLogger(String id, String status) {
	}

	@Override
	public void sendMacroDescriptionLogger(String description) {
	}

	@Override
	public void sendTestCaseCommandLogger(String command) {
	}

	@Override
	public void sendTestCaseHeaderLogger(String command) {
	}

	@Override
	public void setBrowserLogLevel(String logLevel) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setContext(String context) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCursorPosition(String locator, String position) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setDefaultTimeout() {
	}

	@Override
	public void setDefaultTimeoutImplicit() {
		WebDriverHelper.setDefaultTimeoutImplicit(this);
	}

	@Override
	public void setExtensionJs(String extensionJs) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setMouseSpeed(String pixels) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setPrimaryTestSuiteName(String primaryTestSuiteName) {
		_primaryTestSuiteName = primaryTestSuiteName;
	}

	@Override
	public void setSpeed(String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setTimeout(String timeout) {
	}

	@Override
	public void setTimeoutImplicit(String timeout) {
		WebDriverHelper.setTimeoutImplicit(this, timeout);
	}

	@Override
	public void setWindowSize(String coordString) {
		WebElement bodyWebElement = getWebElement("//body");

		WrapsDriver wrapsDriver = (WrapsDriver)bodyWebElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		WebDriver.Options options = webDriver.manage();

		WebDriver.Window window = options.window();

		String[] screenResolution = StringUtil.split(coordString, ",");

		int x = GetterUtil.getInteger(screenResolution[0]);
		int y = GetterUtil.getInteger(screenResolution[1]);

		window.setSize(new Dimension(x, y));
	}

	@Override
	public void shiftKeyDown() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void shiftKeyUp() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void showContextualBanner() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void showContextualBanner(String className, String methodName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void shutDownSeleniumServer() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sikuliAssertElementNotPresent(String image) throws Exception {
		LiferaySeleniumHelper.sikuliAssertElementNotPresent(this, image);
	}

	@Override
	public void sikuliAssertElementPresent(String image) throws Exception {
		LiferaySeleniumHelper.sikuliAssertElementPresent(this, image);
	}

	@Override
	public void sikuliClick(String image) throws Exception {
		LiferaySeleniumHelper.sikuliClick(this, image);
	}

	@Override
	public void sikuliClickByIndex(String image, String index)
		throws Exception {

		LiferaySeleniumHelper.sikuliClickByIndex(this, image, index);
	}

	@Override
	public void sikuliDragAndDrop(String image, String coordString)
		throws Exception {

		LiferaySeleniumHelper.sikuliDragAndDrop(this, image, coordString);
	}

	@Override
	public void sikuliLeftMouseDown() throws Exception {
		LiferaySeleniumHelper.sikuliLeftMouseDown(this);
	}

	@Override
	public void sikuliLeftMouseUp() throws Exception {
		LiferaySeleniumHelper.sikuliLeftMouseUp(this);
	}

	@Override
	public void sikuliMouseMove(String image) throws Exception {
		LiferaySeleniumHelper.sikuliMouseMove(this, image);
	}

	@Override
	public void sikuliRightMouseDown() throws Exception {
		LiferaySeleniumHelper.sikuliRightMouseDown(this);
	}

	@Override
	public void sikuliRightMouseUp() throws Exception {
		LiferaySeleniumHelper.sikuliRightMouseUp(this);
	}

	@Override
	public void sikuliType(String image, String value) throws Exception {
		LiferaySeleniumHelper.sikuliType(this, image, value);
	}

	@Override
	public void sikuliUploadCommonFile(String image, String value)
		throws Exception {

		LiferaySeleniumHelper.sikuliUploadCommonFile(this, image, value);
	}

	@Override
	public void sikuliUploadTCatFile(String image, String value)
		throws Exception {

		LiferaySeleniumHelper.sikuliUploadTCatFile(this, image, value);
	}

	@Override
	public void sikuliUploadTempFile(String image, String value)
		throws Exception {

		LiferaySeleniumHelper.sikuliUploadTempFile(this, image, value);
	}

	@Override
	public void start() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void start(Object optionsObject) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void start(String optionsString) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void startLogger() {
	}

	@Override
	public void stop() {
		quit();
	}

	@Override
	public void stopLogger() {
	}

	@Override
	public void submit(String formLocator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void type(String locator, String value) {
		WebElement webElement = getWebElement(locator);

		if (!webElement.isEnabled()) {
			return;
		}

		webElement.clear();

		typeKeys(locator, value);
	}

	@Override
	public void typeAceEditor(String locator, String value) {
		WebElement webElement = getWebElement(locator);

		webElement.sendKeys(Keys.chord(Keys.CONTROL, "a"));

		LiferaySeleniumHelper.typeAceEditor(this, locator, value);

		webElement.sendKeys(Keys.chord(Keys.CONTROL, Keys.SHIFT, Keys.END));

		webElement.sendKeys(Keys.DELETE);
	}

	@Override
	public void typeAlloyEditor(String locator, String value) {
		WebDriverHelper.typeAlloyEditor(this, locator, value);
	}

	@Override
	public void typeCKEditor(String locator, String value) {
		LiferaySeleniumHelper.typeCKEditor(this, locator, value);
	}

	@Override
	public void typeKeys(String locator, String value) {
		WebElement webElement = getWebElement(locator);

		if (!webElement.isEnabled()) {
			return;
		}

		if (value.contains("line-number=")) {
			value = value.replaceAll("line-number=\"\\d+\"", "");
		}

		int i = 0;

		Set<Integer> specialCharIndexes = getSpecialCharIndexes(value);

		for (int specialCharIndex : specialCharIndexes) {
			webElement.sendKeys(value.substring(i, specialCharIndex));

			String specialChar = String.valueOf(value.charAt(specialCharIndex));

			if (specialChar.equals("-")) {
				webElement.sendKeys(Keys.SUBTRACT);
			}
			else if (specialChar.equals("\t")) {
				webElement.sendKeys(Keys.TAB);
			}
			else {
				webElement.sendKeys(
					Keys.SHIFT, _keysSpecialChars.get(specialChar));
			}

			i = specialCharIndex + 1;
		}

		webElement.sendKeys(value.substring(i, value.length()));
	}

	@Override
	public void typeScreen(String value) {
		LiferaySeleniumHelper.typeScreen(value);
	}

	@Override
	public void uncheck(String locator) {
		WebDriverHelper.uncheck(this, locator);
	}

	@Override
	public void uploadCommonFile(String location, String value)
		throws Exception {

		String filePath =
			FileUtil.getSeparator() + _testDependenciesDirName +
				FileUtil.getSeparator() + value;

		filePath = LiferaySeleniumHelper.getSourceDirFilePath(filePath);

		if (OSDetector.isWindows()) {
			filePath = StringUtil.replace(filePath, "/", "\\");
		}

		uploadFile(location, filePath);
	}

	@Override
	public void uploadFile(String location, String value) {
		makeVisible(location);

		WebElement webElement = getWebElement(location);

		webElement.sendKeys(value);
	}

	@Override
	public void uploadTempFile(String location, String value) {
		String filePath = _outputDirName + FileUtil.getSeparator() + value;

		if (OSDetector.isWindows()) {
			filePath = StringUtil.replace(filePath, "/", "\\");
		}

		uploadFile(location, filePath);
	}

	@Override
	public void useXpathLibrary(String libraryName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void waitForCondition(String script, String timeout) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void waitForConfirmation(String pattern) throws Exception {
		LiferaySeleniumHelper.waitForConfirmation(this, pattern);
	}

	@Override
	public void waitForElementNotPresent(String locator) throws Exception {
		LiferaySeleniumHelper.waitForElementNotPresent(this, locator);
	}

	@Override
	public void waitForElementPresent(String locator) throws Exception {
		LiferaySeleniumHelper.waitForElementPresent(this, locator);
	}

	@Override
	public void waitForFrameToLoad(String frameAddress, String timeout) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void waitForNotPartialText(String locator, String value)
		throws Exception {

		LiferaySeleniumHelper.waitForNotPartialText(this, locator, value);
	}

	@Override
	public void waitForNotSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		LiferaySeleniumHelper.waitForNotSelectedLabel(
			this, selectLocator, pattern);
	}

	@Override
	public void waitForNotText(String locator, String value) throws Exception {
		LiferaySeleniumHelper.waitForNotText(this, locator, value);
	}

	@Override
	public void waitForNotValue(String locator, String value) throws Exception {
		LiferaySeleniumHelper.waitForNotValue(this, locator, value);
	}

	@Override
	public void waitForNotVisible(String locator) throws Exception {
		LiferaySeleniumHelper.waitForNotVisible(this, locator);
	}

	@Override
	public void waitForPageToLoad(String timeout) {
	}

	@Override
	public void waitForPartialText(String locator, String value)
		throws Exception {

		LiferaySeleniumHelper.waitForPartialText(this, locator, value);
	}

	@Override
	public void waitForPopUp(String windowID, String timeout) {
		int wait = 0;

		if (timeout.equals("")) {
			wait = 30;
		}
		else {
			wait = GetterUtil.getInteger(timeout) / 1000;
		}

		if (windowID.equals("") || windowID.equals("null")) {
			for (int i = 0; i <= wait; i++) {
				Set<String> windowHandles = getWindowHandles();

				if (windowHandles.size() > 1) {
					return;
				}

				try {
					Thread.sleep(1000);
				}
				catch (Exception e) {
				}
			}
		}
		else {
			String targetWindowTitle = windowID;

			if (targetWindowTitle.startsWith("title=")) {
				targetWindowTitle = targetWindowTitle.substring(6);
			}

			for (int i = 0; i <= wait; i++) {
				for (String windowHandle : getWindowHandles()) {
					WebDriver.TargetLocator targetLocator = switchTo();

					targetLocator.window(windowHandle);

					if (targetWindowTitle.equals(getTitle())) {
						targetLocator.window(
							WebDriverHelper.getDefaultWindowHandle());

						return;
					}
				}

				try {
					Thread.sleep(1000);
				}
				catch (Exception e) {
				}
			}
		}

		TestCase.fail("Unable to find the window ID \"" + windowID + "\"");
	}

	@Override
	public void waitForSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		LiferaySeleniumHelper.waitForSelectedLabel(
			this, selectLocator, pattern);
	}

	@Override
	public void waitForText(String locator, String value) throws Exception {
		LiferaySeleniumHelper.waitForText(this, locator, value);
	}

	@Override
	public void waitForTextNotPresent(String value) throws Exception {
		LiferaySeleniumHelper.waitForTextNotPresent(this, value);
	}

	@Override
	public void waitForTextPresent(String value) throws Exception {
		LiferaySeleniumHelper.waitForTextPresent(this, value);
	}

	@Override
	public void waitForValue(String locator, String value) throws Exception {
		LiferaySeleniumHelper.waitForValue(this, locator, value);
	}

	@Override
	public void waitForVisible(String locator) throws Exception {
		LiferaySeleniumHelper.waitForVisible(this, locator);
	}

	@Override
	public void windowFocus() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void windowMaximize() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void windowMaximizeAndWait() {
		windowMaximize();
		waitForPageToLoad("30000");
	}

	protected void acceptConfirmation() {
		WebDriver.TargetLocator targetLocator = switchTo();

		Alert alert = targetLocator.alert();

		alert.accept();
	}

	protected Set<Integer> getSpecialCharIndexes(String value) {
		Set<Integer> specialCharIndexes = new TreeSet<>();

		Set<String> specialChars = new TreeSet<>();

		specialChars.addAll(_keysSpecialChars.keySet());

		specialChars.add("-");
		specialChars.add("\t");

		for (String specialChar : specialChars) {
			while (value.contains(specialChar)) {
				specialCharIndexes.add(value.indexOf(specialChar));

				value = StringUtil.replaceFirst(value, specialChar, " ");
			}
		}

		return specialCharIndexes;
	}

	protected WebElement getWebElement(String locator) {
		return WebDriverHelper.getWebElement(this, locator);
	}

	protected WebElement getWebElement(String locator, String timeout) {
		return WebDriverHelper.getWebElement(this, locator, timeout);
	}

	protected List<WebElement> getWebElements(String locator) {
		return WebDriverHelper.getWebElements(this, locator);
	}

	protected List<WebElement> getWebElements(String locator, String timeout) {
		return WebDriverHelper.getWebElements(this, locator, timeout);
	}

	protected void initKeysSpecialChars() {
		_keysSpecialChars.put("!", "1");
		_keysSpecialChars.put("#", "3");
		_keysSpecialChars.put("$", "4");
		_keysSpecialChars.put("%", "5");
		_keysSpecialChars.put("&", "7");
		_keysSpecialChars.put("(", "9");
		_keysSpecialChars.put(")", "0");
		_keysSpecialChars.put("<", ",");
		_keysSpecialChars.put(">", ".");
	}

	protected boolean isValidKeycode(String keycode) {
		for (Keys keys : Keys.values()) {
			String keysName = keys.name();

			if (keysName.equals(keycode)) {
				return true;
			}
		}

		return false;
	}

	protected void scrollWebElementIntoView(WebElement webElement) {
		WebDriverHelper.scrollWebElementIntoView(this, webElement);
	}

	protected void selectByRegexpText(String selectLocator, String regexp) {
		WebDriverHelper.selectByRegexpText(this, selectLocator, regexp);
	}

	protected void selectByRegexpValue(String selectLocator, String regexp) {
		WebDriverHelper.selectByRegexpValue(this, selectLocator, regexp);
	}

	private static final String _OUTPUT_DIR_NAME = PropsValues.OUTPUT_DIR_NAME;

	private static final String _TEST_DEPENDENCIES_DIR_NAME =
		PropsValues.TEST_DEPENDENCIES_DIR_NAME;

	private String _clipBoard = "";
	private final Map<String, String> _keysSpecialChars = new HashMap<>();
	private final String _outputDirName;
	private String _primaryTestSuiteName;
	private final String _sikuliImagesDirName;
	private final String _testDependenciesDirName;

}