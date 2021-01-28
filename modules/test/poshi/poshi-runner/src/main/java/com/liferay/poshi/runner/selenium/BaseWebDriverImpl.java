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

import com.deque.axe.AXE;

import com.liferay.poshi.core.PoshiContext;
import com.liferay.poshi.core.PoshiGetterUtil;
import com.liferay.poshi.core.selenium.LiferaySelenium;
import com.liferay.poshi.core.util.CharPool;
import com.liferay.poshi.core.util.FileUtil;
import com.liferay.poshi.core.util.GetterUtil;
import com.liferay.poshi.core.util.OSDetector;
import com.liferay.poshi.core.util.PropsValues;
import com.liferay.poshi.core.util.StringPool;
import com.liferay.poshi.core.util.StringUtil;
import com.liferay.poshi.core.util.Validator;
import com.liferay.poshi.runner.exception.ElementNotFoundPoshiRunnerException;
import com.liferay.poshi.runner.exception.PoshiRunnerWarningException;
import com.liferay.poshi.runner.util.AntCommands;
import com.liferay.poshi.runner.util.ArchiveUtil;
import com.liferay.poshi.runner.util.EmailCommands;
import com.liferay.poshi.runner.util.HtmlUtil;

import com.testautomationguru.ocular.Ocular;
import com.testautomationguru.ocular.OcularConfiguration;
import com.testautomationguru.ocular.comparator.OcularResult;
import com.testautomationguru.ocular.sample.SampleBuilder;
import com.testautomationguru.ocular.snapshot.SnapshotBuilder;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import java.io.File;
import java.io.StringReader;

import java.net.URI;
import java.net.URL;

import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import junit.framework.TestCase;

import net.jsourcerer.webdriver.jserrorcollector.JavaScriptError;

import org.json.JSONArray;
import org.json.JSONObject;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.Location;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.robot.Key;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.CanvasBuilder;
import org.sikuli.api.visual.DesktopCanvas;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseWebDriverImpl implements LiferaySelenium, WebDriver {

	public BaseWebDriverImpl(String browserURL, WebDriver webDriver) {
		System.setProperty("java.awt.headless", "false");

		_webDriver = webDriver;

		setDefaultWindowHandle(webDriver.getWindowHandle());

		WebDriver.Options options = webDriver.manage();

		WebDriver.Window window = options.window();

		window.setSize(new Dimension(1280, 1040));

		webDriver.get(browserURL);
	}

	@Override
	public void addSelection(String locator, String optionLocator) {
		Select select = new Select(getWebElement(locator));

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

	@Override
	public void antCommand(String fileName, String target) throws Exception {
		AntCommands antCommands = new AntCommands(fileName, target);

		ExecutorService executorService = Executors.newCachedThreadPool();

		Future<Void> future = executorService.submit(antCommands);

		try {
			future.get(150, TimeUnit.SECONDS);
		}
		catch (ExecutionException executionException) {
			throw executionException;
		}
		catch (TimeoutException timeoutException) {
		}
	}

	@Override
	public void assertAccessible() throws Exception {
		WebDriver webDriver = WebDriverUtil.getWebDriver();

		String sourceDirFilePath = LiferaySeleniumUtil.getSourceDirFilePath(
			getTestDependenciesDirName());

		File file = new File(sourceDirFilePath + "/axe.min.js");

		URI uri = file.toURI();

		URL url = uri.toURL();

		AXE.Builder axeBuilder = new AXE.Builder(webDriver, url);

		axeBuilder = axeBuilder.options(
			PropsValues.ACCESSIBILITY_STANDARDS_JSON);

		JSONObject jsonObject = axeBuilder.analyze();

		JSONArray jsonArray = jsonObject.getJSONArray("violations");

		if (jsonArray.length() != 0) {
			throw new Exception(AXE.report(jsonArray));
		}
	}

	@Override
	public void assertAlert(String pattern) throws Exception {
		TestCase.assertEquals(pattern, getAlert());
	}

	@Override
	public void assertAlertNotPresent() throws Exception {
		if (isAlertPresent()) {
			throw new Exception("Alert is present");
		}
	}

	@Override
	public void assertAttributeNotPresent(String attribute, String locator)
		throws Exception {

		if (isAttributePresent(attribute, locator)) {
			throw new Exception(
				"Unexpected attribute \"" + attribute + "\" is present");
		}
	}

	@Override
	public void assertAttributePresent(String attribute, String locator)
		throws Exception {

		if (!isAttributePresent(attribute, locator)) {
			throw new Exception(
				"Expected attribute \"" + attribute + "\" is not present");
		}
	}

	@Override
	public void assertAttributeValue(
			String attribute, String locator, String pattern)
		throws Exception {

		WebElement webElement = getWebElement(locator);

		String actualValue = webElement.getAttribute(attribute);

		if (!pattern.equals(actualValue)) {
			throw new Exception(
				"Actual value of attribute \"" + attribute + "\", \"" +
					actualValue + "\" does not match expected value \"" +
						pattern + "\"");
		}
	}

	@Override
	public void assertChecked(String locator) throws Exception {
		assertElementPresent(locator);

		if (isNotChecked(locator)) {
			throw new Exception(
				"Element is not checked at \"" + locator + "\"");
		}
	}

	@Override
	public void assertConfirmation(String pattern) throws Exception {
		Condition confirmationCondition = getConfirmationCondition(pattern);

		confirmationCondition.assertTrue();
	}

	@Override
	public void assertConsoleErrors() throws Exception {
		LiferaySeleniumUtil.assertConsoleErrors();
	}

	@Override
	public void assertConsoleTextNotPresent(String text) throws Exception {
		Condition consoleTextNotPresentCondition =
			getConsoleTextNotPresentCondition(text);

		consoleTextNotPresentCondition.assertTrue();
	}

	@Override
	public void assertConsoleTextPresent(String text) throws Exception {
		Condition consoleTextPresentCondition = getConsoleTextPresentCondition(
			text);

		consoleTextPresentCondition.assertTrue();
	}

	@Override
	public void assertCssValue(
			String locator, String cssAttribute, String cssValue)
		throws Exception {

		WebElement webElement = getWebElement(locator);

		String actualCssValue = webElement.getCssValue(cssAttribute);

		if (!actualCssValue.equals(cssValue)) {
			throw new Exception(
				"CSS Value " + actualCssValue + " does not match " + cssValue);
		}
	}

	@Override
	public void assertEditable(String locator) throws Exception {
		Condition editableCondition = getEditableCondition(locator);

		editableCondition.assertTrue();
	}

	@Override
	public void assertElementNotPresent(String locator) throws Exception {
		Condition elementNotPresentCondition = getElementNotPresentCondition(
			locator);

		elementNotPresentCondition.assertTrue();
	}

	@Override
	public void assertElementPresent(String locator) throws Exception {
		Condition elementPresentCondition = getElementPresentCondition(locator);

		elementPresentCondition.assertTrue();
	}

	@Override
	public void assertEmailBody(String index, String body) throws Exception {
		TestCase.assertEquals(body, getEmailBody(index));
	}

	@Override
	public void assertEmailSubject(String index, String subject)
		throws Exception {

		TestCase.assertEquals(subject, getEmailSubject(index));
	}

	@Override
	public void assertHTMLSourceTextNotPresent(String value) throws Exception {
		if (isHTMLSourceTextPresent(value)) {
			throw new Exception(
				"Pattern \"" + value + "\" does exists in the HTML source");
		}
	}

	@Override
	public void assertHTMLSourceTextPresent(String value) throws Exception {
		if (!isHTMLSourceTextPresent(value)) {
			throw new Exception(
				"Pattern \"" + value + "\" does not exists in the HTML source");
		}
	}

	@Override
	public void assertJavaScript(
			String javaScript, String message, String argument)
		throws Exception {

		Condition javaScriptCondition = getJavaScriptCondition(
			javaScript, message, argument);

		javaScriptCondition.assertTrue();
	}

	@Override
	public void assertJavaScriptErrors(String ignoreJavaScriptError)
		throws Exception {

		if (!PropsValues.TEST_ASSERT_JAVASCRIPT_ERRORS) {
			return;
		}

		String pageSource = null;

		try {
			pageSource = getPageSource();
		}
		catch (Exception exception) {
			WebDriver.TargetLocator targetLocator = switchTo();

			targetLocator.window(_defaultWindowHandle);

			pageSource = getPageSource();
		}

		if (pageSource == null) {
			System.out.println("Unable to obtain HTML source");
		}
		else if (pageSource.contains(
					"html id=\"feedHandler\" xmlns=" +
						"\"http://www.w3.org/1999/xhtml\"")) {

			return;
		}

		List<JavaScriptError> javaScriptErrors = new ArrayList<>();

		try {
			WebElement webElement = getWebElement("//body");

			WrapsDriver wrapsDriver = (WrapsDriver)webElement;

			WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

			javaScriptErrors.addAll(
				JavaScriptError.readErrors(wrappedWebDriver));
		}
		catch (Exception exception) {
		}

		List<Exception> exceptions = new ArrayList<>();

		if (!javaScriptErrors.isEmpty()) {
			for (JavaScriptError javaScriptError : javaScriptErrors) {
				String javaScriptErrorValue = javaScriptError.toString();

				if (Validator.isNotNull(ignoreJavaScriptError) &&
					javaScriptErrorValue.contains(ignoreJavaScriptError)) {

					continue;
				}

				if (LiferaySeleniumUtil.isInIgnoreErrorsFile(
						javaScriptErrorValue, "javascript")) {

					continue;
				}

				String message = "JAVA_SCRIPT_ERROR: " + javaScriptErrorValue;

				System.out.println(message);

				exceptions.add(new PoshiRunnerWarningException(message));
			}
		}

		if (!exceptions.isEmpty()) {
			LiferaySeleniumUtil.addToJavaScriptExceptions(exceptions);

			throw exceptions.get(0);
		}
	}

	@Override
	public void assertLiferayErrors() throws Exception {
		LiferaySeleniumUtil.assertConsoleErrors();
	}

	@Override
	public void assertLocation(String pattern) throws Exception {
		TestCase.assertEquals(pattern, getLocation());
	}

	@Override
	public void assertNoJavaScriptExceptions() throws Exception {
		LiferaySeleniumUtil.assertNoJavaScriptExceptions();
	}

	@Override
	public void assertNoLiferayExceptions() throws Exception {
		LiferaySeleniumUtil.assertNoLiferayExceptions();
	}

	@Override
	public void assertNotAlert(String pattern) {
		TestCase.assertTrue(Objects.equals(pattern, getAlert()));
	}

	@Override
	public void assertNotAttributeValue(
			String locator, String attribute, String forbiddenValue)
		throws Exception {

		WebElement webElement = getWebElement(locator);

		String actualValue = webElement.getAttribute(attribute);

		if (forbiddenValue.equals(actualValue)) {
			throw new Exception(
				"Actual value of attribute \"" + attribute +
					"\" matches forbidden value \"" + forbiddenValue + "\"");
		}
	}

	@Override
	public void assertNotChecked(String locator) throws Exception {
		assertElementPresent(locator);

		if (isChecked(locator)) {
			throw new Exception("Element is checked at \"" + locator + "\"");
		}
	}

	@Override
	public void assertNotEditable(String locator) throws Exception {
		Condition notEditable = getNotEditableCondition(locator);

		notEditable.assertTrue();
	}

	@Override
	public void assertNotLocation(String pattern) throws Exception {
		TestCase.assertTrue(Objects.equals(pattern, getLocation()));
	}

	@Override
	public void assertNotPartialText(String locator, String pattern)
		throws Exception {

		assertElementPresent(locator);

		Condition notPartialText = getNotPartialTextCondition(locator, pattern);

		notPartialText.assertTrue();
	}

	@Override
	public void assertNotSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		assertElementPresent(selectLocator);

		Condition notSelectedLabelCondition = getNotSelectedLabelCondition(
			selectLocator, pattern);

		notSelectedLabelCondition.assertTrue();
	}

	@Override
	public void assertNotText(String locator, String pattern) throws Exception {
		assertElementPresent(locator);

		Condition notTextCondition = getNotTextCondition(locator, pattern);

		notTextCondition.assertTrue();
	}

	@Override
	public void assertNotValue(String locator, String pattern)
		throws Exception {

		assertElementPresent(locator);

		Condition notValueCondition = getNotValueCondition(locator, pattern);

		notValueCondition.assertTrue();
	}

	@Override
	public void assertNotVisible(String locator) throws Exception {
		assertElementPresent(locator);

		Condition notVisibleCondition = getNotVisibleCondition(locator);

		notVisibleCondition.assertTrue();
	}

	@Override
	public void assertNotVisibleInPage(String locator) throws Exception {
		assertElementPresent(locator);

		Condition notVisibleInPageCondition = getNotVisibleInPageCondition(
			locator);

		notVisibleInPageCondition.assertTrue();
	}

	@Override
	public void assertNotVisibleInViewport(String locator) throws Exception {
		assertElementPresent(locator);

		Condition notVisibleInViewportCondition =
			getNotVisibleInViewportCondition(locator);

		notVisibleInViewportCondition.assertTrue();
	}

	@Override
	public void assertPartialConfirmation(String pattern) throws Exception {
		String confirmation = getConfirmation(null);

		if (!confirmation.contains(pattern)) {
			throw new Exception(
				"\"" + confirmation + "\" does not contain \"" + pattern +
					"\"");
		}
	}

	@Override
	public void assertPartialLocation(String pattern) throws Exception {
		String location = getLocation();

		if (!location.contains(pattern)) {
			throw new Exception(
				"\"" + location + "\" does not contain \"" + pattern + "\"");
		}
	}

	@Override
	public void assertPartialText(String locator, String pattern)
		throws Exception {

		assertElementPresent(locator);

		Condition partialTextCondition = getPartialTextCondition(
			locator, pattern);

		partialTextCondition.assertTrue();
	}

	@Override
	public void assertPartialTextAceEditor(String locator, String pattern)
		throws Exception {

		assertElementPresent(locator);

		Condition partialTextAceEditorCondition =
			getPartialTextAceEditorCondition(locator, pattern);

		partialTextAceEditorCondition.assertTrue();
	}

	@Override
	public void assertPartialTextCaseInsensitive(String locator, String pattern)
		throws Exception {

		assertElementPresent(locator);

		Condition partialTextCaseInsensitiveCondition =
			getPartialTextCaseInsensitiveCondition(locator, pattern);

		partialTextCaseInsensitiveCondition.assertTrue();
	}

	@Override
	public void assertPrompt(String pattern, String value) throws Exception {
		String confirmation = getConfirmation(value);

		if (!pattern.equals(confirmation)) {
			throw new Exception(
				"Expected text \"" + pattern +
					"\" does not match actual text \"" + confirmation + "\"");
		}
	}

	@Override
	public void assertSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		assertElementPresent(selectLocator);

		Condition selectedLabelCondition = getSelectedLabelCondition(
			selectLocator, pattern);

		selectedLabelCondition.assertTrue();
	}

	@Override
	public void assertText(String locator, String pattern) throws Exception {
		assertElementPresent(locator);

		Condition textCondition = getTextCondition(locator, pattern);

		textCondition.assertTrue();
	}

	@Override
	public void assertTextCaseInsensitive(String locator, String pattern)
		throws Exception {

		Condition textCaseInsensitiveCondition =
			getTextCaseInsensitiveCondition(locator, pattern);

		textCaseInsensitiveCondition.assertTrue();
	}

	@Override
	public void assertTextNotPresent(String pattern) throws Exception {
		Condition textNotPresentCondition = getTextNotPresentCondition(pattern);

		textNotPresentCondition.assertTrue();
	}

	@Override
	public void assertTextPresent(String pattern) throws Exception {
		Condition textPresentCondition = getTextPresentCondition(pattern);

		textPresentCondition.assertTrue();
	}

	@Override
	public void assertValue(String locator, String pattern) throws Exception {
		assertElementPresent(locator);

		Condition valueCondition = getValueCondition(locator, pattern);

		valueCondition.assertTrue();
	}

	@Override
	public void assertVisible(String locator) throws Exception {
		assertElementPresent(locator);

		Condition visibleCondition = getVisibleCondition(locator);

		visibleCondition.assertTrue();
	}

	@Override
	public void assertVisibleInPage(String locator) throws Exception {
		assertElementPresent(locator);

		Condition visibleInPageCondition = getVisibleInPageCondition(locator);

		visibleInPageCondition.assertTrue();
	}

	@Override
	public void assertVisibleInViewport(String locator) throws Exception {
		assertElementPresent(locator);

		Condition visibleInViewportCondition = getVisibleInViewportCondition(
			locator);

		visibleInViewportCondition.assertTrue();
	}

	@Override
	public void check(String locator) {
		WebElement webElement = getWebElement(locator);

		if (!webElement.isSelected()) {
			webElement.click();
		}
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
			catch (Exception exception) {
				scrollWebElementIntoView(webElement);

				webElement.click();
			}
		}
	}

	@Override
	public void clickAt(String locator, String coordString) {
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
			WebElement webElement = getWebElement(locator);

			scrollWebElementIntoView(webElement);

			WrapsDriver wrapsDriver = (WrapsDriver)webElement;

			WebDriver webDriver = wrapsDriver.getWrappedDriver();

			Actions actions = new Actions(webDriver);

			actions.moveToElement(webElement, offsetX, offsetY);

			actions.pause(1500);

			actions.click();

			Action action = actions.build();

			action.perform();
		}
	}

	@Override
	public void close() {
		_webDriver.close();
	}

	@Override
	public void connectToEmailAccount(String emailAddress, String emailPassword)
		throws Exception {

		LiferaySeleniumUtil.connectToEmailAccount(emailAddress, emailPassword);
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
	public void deleteAllEmails() throws Exception {
		LiferaySeleniumUtil.deleteAllEmails();
	}

	@Override
	public void dismissAlert() {
		switchTo();

		WebDriverWait webDriverWait = new WebDriverWait(this, 1);

		try {
			Alert alert = webDriverWait.until(
				ExpectedConditions.alertIsPresent());

			alert.dismiss();
		}
		catch (Exception exception) {
			throw new WebDriverException(exception);
		}
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
	public void dragAndDrop(String locator, String coordinatePairs) {
		try {
			Matcher matcher = _coordinatePairsPattern.matcher(coordinatePairs);

			if (!matcher.matches()) {
				throw new Exception(
					"Coordinate pairs \"" + coordinatePairs +
						"\" do not match pattern \"" +
							_coordinatePairsPattern.pattern() + "\"");
			}

			WebElement webElement = getWebElement(locator);

			WrapsDriver wrapsDriver = (WrapsDriver)webElement;

			WebDriver webDriver = wrapsDriver.getWrappedDriver();

			Actions actions = new Actions(webDriver);

			actions.clickAndHold(webElement);

			actions.pause(1500);

			for (String coordinatePair : coordinatePairs.split("\\|")) {
				String[] coordinates = coordinatePair.split(",");

				actions.moveByOffset(
					GetterUtil.getInteger(coordinates[0]),
					GetterUtil.getInteger(coordinates[1]));
			}

			actions.pause(1500);

			actions.release();

			Action action = actions.build();

			action.perform();
		}
		catch (Exception exception) {
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
	public void echo(String message) {
		LiferaySeleniumUtil.echo(message);
	}

	@Override
	public void executeJavaScript(
		String javaScript, String argument1, String argument2) {

		WebElement webElement = getWebElement("//body");

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		Object object1 = null;
		Object object2 = null;

		try {
			object1 = getWebElement(argument1);
		}
		catch (ElementNotFoundPoshiRunnerException | InvalidSelectorException
					exception) {

			object1 = argument1;
		}

		try {
			object2 = getWebElement(argument2);
		}
		catch (ElementNotFoundPoshiRunnerException | InvalidSelectorException
					exception) {

			object2 = argument2;
		}

		javascriptExecutor.executeScript(javaScript, object1, object2);
	}

	@Override
	public void fail(String message) {
		LiferaySeleniumUtil.fail(message);
	}

	@Override
	public WebElement findElement(By by) {
		return _webDriver.findElement(by);
	}

	@Override
	public List<WebElement> findElements(By by) {
		return _webDriver.findElements(by);
	}

	@Override
	public void get(String url) {
		_webDriver.get(url);
	}

	@Override
	public String getAlert() {
		switchTo();

		WebDriverWait webDriverWait = new WebDriverWait(this, 1);

		Alert alert = webDriverWait.until(ExpectedConditions.alertIsPresent());

		return alert.getText();
	}

	@Override
	public String getAttribute(String attributeLocator) {
		int pos = attributeLocator.lastIndexOf(CharPool.AT);

		String locator = attributeLocator.substring(0, pos);

		WebElement webElement = getWebElement(locator);

		String attribute = attributeLocator.substring(pos + 1);

		return webElement.getAttribute(attribute);
	}

	@Override
	public String getBodyText() {
		WebElement webElement = getWebElement("//body");

		return webElement.getText();
	}

	@Override
	public String getConfirmation(String value) {
		switchTo();

		WebDriverWait webDriverWait = new WebDriverWait(this, 1);

		try {
			Alert alert = webDriverWait.until(
				ExpectedConditions.alertIsPresent());

			String confirmation = alert.getText();

			if (Validator.isNotNull(value)) {
				alert.sendKeys(value);
			}

			alert.accept();

			return confirmation;
		}
		catch (Exception exception) {
			throw new WebDriverException(exception);
		}
	}

	@Override
	public String getCurrentUrl() {
		return _webDriver.getCurrentUrl();
	}

	@Override
	public int getElementHeight(String locator) {
		WebElement webElement = getWebElement(locator);

		Dimension dimension = webElement.getSize();

		return dimension.getHeight();
	}

	@Override
	public String getElementValue(String locator) throws Exception {
		return getElementValue(locator, null);
	}

	public String getElementValue(String locator, String timeout) {
		WebElement webElement = getWebElement(locator, timeout);

		scrollWebElementIntoView(webElement);

		return webElement.getAttribute("value");
	}

	@Override
	public int getElementWidth(String locator) {
		WebElement webElement = getWebElement(locator);

		Dimension dimension = webElement.getSize();

		return dimension.getWidth();
	}

	@Override
	public String getEmailBody(String index) throws Exception {
		return LiferaySeleniumUtil.getEmailBody(index);
	}

	@Override
	public String getEmailSubject(String index) throws Exception {
		return LiferaySeleniumUtil.getEmailSubject(index);
	}

	@Override
	public String getEval(String script) {
		WebElement webElement = getWebElement("//body");

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		return (String)javascriptExecutor.executeScript(script);
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
		return String.valueOf(
			GetterUtil.getInteger(getFirstNumber(locator)) + 1);
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
		catch (Exception exception) {
			exception.printStackTrace();
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
	public String getJavaScriptResult(
		String javaScript, String argument1, String argument2) {

		WebElement webElement = getWebElement("//body");

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		Object object1 = null;
		Object object2 = null;

		try {
			object1 = getWebElement(argument1);
		}
		catch (ElementNotFoundPoshiRunnerException | InvalidSelectorException
					exception) {

			object1 = argument1;
		}

		try {
			object2 = getWebElement(argument2);
		}
		catch (ElementNotFoundPoshiRunnerException | InvalidSelectorException
					exception) {

			object2 = argument2;
		}

		return (String)javascriptExecutor.executeScript(
			javaScript, object1, object2);
	}

	@Override
	public String getLocation() throws Exception {
		List<Exception> exceptions = new ArrayList<>();

		LocationCallable callable = new LocationCallable();

		for (int i = 0; i < PropsValues.GET_LOCATION_MAX_RETRIES; i++) {
			FutureTask<String> futureTask = new FutureTask<>(
				callable._init(this));

			Thread thread = new Thread(futureTask);

			thread.start();

			try {
				return futureTask.get(
					PropsValues.GET_LOCATION_TIMEOUT, TimeUnit.SECONDS);
			}
			catch (CancellationException cancellationException) {
				exceptions.add(cancellationException);
			}
			catch (ExecutionException executionException) {
				exceptions.add(executionException);
			}
			catch (InterruptedException interruptedException) {
				exceptions.add(interruptedException);
			}
			catch (TimeoutException timeoutException) {
				exceptions.add(timeoutException);
			}
			finally {
				thread.interrupt();
			}

			System.out.println("getLocation(WebDriver):");
			System.out.println(toString());

			Set<String> windowHandles = getWindowHandles();

			for (String windowHandle : windowHandles) {
				System.out.println(windowHandle);
			}
		}

		if (!exceptions.isEmpty()) {
			throw new Exception(exceptions.get(0));
		}

		throw new TimeoutException();
	}

	@Override
	public String getNumberDecrement(String value) {
		return LiferaySeleniumUtil.getNumberDecrement(value);
	}

	@Override
	public String getNumberIncrement(String value) {
		return LiferaySeleniumUtil.getNumberIncrement(value);
	}

	@Override
	public String getOcularResultImageDirName() {
		return _OCULAR_RESULT_IMAGE_DIR_NAME;
	}

	@Override
	public String getOcularSnapImageDirName() {
		return _OCULAR_SNAP_IMAGE_DIR_NAME;
	}

	@Override
	public String getOutputDirName() {
		return _OUTPUT_DIR_NAME;
	}

	@Override
	public String getPageSource() {
		return _webDriver.getPageSource();
	}

	@Override
	public String getPrimaryTestSuiteName() {
		return _primaryTestSuiteName;
	}

	@Override
	public String getSelectedLabel(String selectLocator) {
		return getSelectedLabel(selectLocator, null);
	}

	public String getSelectedLabel(String selectLocator, String timeout) {
		try {
			WebElement selectLocatorWebElement = getWebElement(
				selectLocator, timeout);

			Select select = new Select(selectLocatorWebElement);

			WebElement firstSelectedOptionWebElement =
				select.getFirstSelectedOption();

			return firstSelectedOptionWebElement.getText();
		}
		catch (Exception exception) {
			return null;
		}
	}

	@Override
	public String[] getSelectedLabels(String selectLocator) {
		WebElement selectLocatorWebElement = getWebElement(selectLocator);

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

	@Override
	public String getSikuliImagesDirName() {
		return _SIKULI_IMAGES_DIR_NAME;
	}

	@Override
	public String getTestDependenciesDirName() {
		return _TEST_DEPENDENCIES_DIR_NAME;
	}

	@Override
	public String getTestName() {
		return PoshiContext.getTestCaseNamespacedClassCommandName();
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

		scrollWebElementIntoView(webElement);

		String text = webElement.getText();

		text = text.trim();

		return StringUtil.replace(text, "\n", " ");
	}

	public String getTextAceEditor(String locator) throws Exception {
		return getTextAceEditor(locator, null);
	}

	public String getTextAceEditor(String locator, String timeout) {
		WebElement webElement = getWebElement(locator, timeout);

		scrollWebElementIntoView(webElement);

		String text = webElement.getText();

		text = text.trim();

		return StringUtil.replace(text, "\n", "");
	}

	@Override
	public String getTitle() {
		return _webDriver.getTitle();
	}

	@Override
	public String getWindowHandle() {
		return _webDriver.getWindowHandle();
	}

	@Override
	public Set<String> getWindowHandles() {
		return _webDriver.getWindowHandles();
	}

	public WebDriver getWrappedWebDriver() {
		return _webDriver;
	}

	@Override
	public void goBack() {
		WebDriver.Navigation navigation = navigate();

		navigation.back();
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
		catch (Exception exception) {
			alertPresent = false;
		}

		return alertPresent;
	}

	@Override
	public boolean isAttributeNotPresent(String attribute, String locator) {
		return !isAttributePresent(attribute, locator);
	}

	@Override
	public boolean isAttributePresent(String attribute, String locator) {
		WebElement webElement = getWebElement(locator);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		StringBuilder sb = new StringBuilder();

		sb.append("var element = arguments[0];");
		sb.append("return element.attributes[\'");
		sb.append(attribute);
		sb.append("\'];");

		Object returnObject = javascriptExecutor.executeScript(
			sb.toString(), webElement);

		if (returnObject != null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isChecked(String locator) {
		WebElement webElement = getWebElement(locator);

		scrollWebElementIntoView(webElement);

		return webElement.isSelected();
	}

	@Override
	public boolean isConfirmation(String pattern) throws Exception {
		Condition confirmationCondition = getConfirmationCondition(pattern);

		return confirmationCondition.evaluate();
	}

	@Override
	public boolean isConsoleTextNotPresent(String text) throws Exception {
		Condition consoleTextNotPresentCondition =
			getConsoleTextNotPresentCondition(text);

		return consoleTextNotPresentCondition.evaluate();
	}

	@Override
	public boolean isConsoleTextPresent(String text) throws Exception {
		Condition consoleTextPresentCondition = getConsoleTextPresentCondition(
			text);

		return consoleTextPresentCondition.evaluate();
	}

	@Override
	public boolean isEditable(String locator) throws Exception {
		Condition editableCondition = getEditableCondition(locator);

		return editableCondition.evaluate();
	}

	@Override
	public boolean isElementNotPresent(String locator) throws Exception {
		Condition elementNotPresentCondition = getElementNotPresentCondition(
			locator);

		return elementNotPresentCondition.evaluate();
	}

	@Override
	public boolean isElementPresent(String locator) throws Exception {
		Condition elementPresentCondition = getElementPresentCondition(locator);

		return elementPresentCondition.evaluate();
	}

	@Override
	public boolean isElementPresentAfterWait(String locator) throws Exception {
		for (int second = 0;; second++) {
			if (second >= PropsValues.TIMEOUT_EXPLICIT_WAIT) {
				return isElementPresent(locator);
			}

			if (isElementPresent(locator)) {
				break;
			}

			Thread.sleep(1000);
		}

		return isElementPresent(locator);
	}

	@Override
	public boolean isHTMLSourceTextPresent(String value) throws Exception {
		String pageSource = getPageSource();

		if (pageSource.contains(value)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isNotChecked(String locator) {
		return !isChecked(locator);
	}

	@Override
	public boolean isNotEditable(String locator) throws Exception {
		return !isEditable(locator);
	}

	@Override
	public boolean isNotPartialText(String locator, String value)
		throws Exception {

		return !isPartialText(locator, value);
	}

	@Override
	public boolean isNotPartialTextAceEditor(String locator, String value)
		throws Exception {

		return !isPartialTextAceEditor(locator, value);
	}

	@Override
	public boolean isNotSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		Condition notSelectedLabelCondition = getNotSelectedLabelCondition(
			selectLocator, pattern);

		return notSelectedLabelCondition.evaluate();
	}

	@Override
	public boolean isNotText(String locator, String value) throws Exception {
		return !isText(locator, value);
	}

	@Override
	public boolean isNotValue(String locator, String value) throws Exception {
		return !isValue(locator, value);
	}

	@Override
	public boolean isNotVisible(String locator) throws Exception {
		return !isVisible(locator);
	}

	@Override
	public boolean isNotVisibleInPage(String locator) throws Exception {
		return !isVisibleInPage(locator);
	}

	@Override
	public boolean isNotVisibleInViewport(String locator) throws Exception {
		return !isVisibleInViewport(locator);
	}

	@Override
	public boolean isPartialText(String locator, String value)
		throws Exception {

		Condition partialTextCondition = getPartialTextCondition(
			locator, value);

		return partialTextCondition.evaluate();
	}

	@Override
	public boolean isPartialTextAceEditor(String locator, String value)
		throws Exception {

		Condition partialTextAceEditorCondition =
			getPartialTextAceEditorCondition(locator, value);

		return partialTextAceEditorCondition.evaluate();
	}

	@Override
	public boolean isPartialTextCaseInsensitive(String locator, String value)
		throws Exception {

		Condition partialTextCaseInsensitiveCondition =
			getPartialTextCaseInsensitiveCondition(locator, value);

		return partialTextCaseInsensitiveCondition.evaluate();
	}

	@Override
	public boolean isSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		Condition selectedLabelCondition = getSelectedLabelCondition(
			selectLocator, pattern);

		return selectedLabelCondition.evaluate();
	}

	@Override
	public boolean isSikuliImagePresent(String image) throws Exception {
		ScreenRegion screenRegion = new DesktopScreenRegion();

		if (screenRegion.find(getImageTarget(image)) != null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isTCatEnabled() {
		return PropsValues.TCAT_ENABLED;
	}

	@Override
	public boolean isTestName(String testName) {
		String classCommandName =
			PoshiContext.getTestCaseNamespacedClassCommandName();

		classCommandName =
			PoshiGetterUtil.getClassCommandNameFromNamespacedClassCommandName(
				classCommandName);

		if (testName.equals(classCommandName)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isText(String locator, String value) throws Exception {
		Condition textCondition = getTextCondition(locator, value);

		return textCondition.evaluate();
	}

	@Override
	public boolean isTextCaseInsensitive(String locator, String value)
		throws Exception {

		Condition textCaseInsensitiveCondition =
			getTextCaseInsensitiveCondition(locator, value);

		return textCaseInsensitiveCondition.evaluate();
	}

	@Override
	public boolean isTextNotPresent(String pattern) throws Exception {
		return !isTextPresent(pattern);
	}

	@Override
	public boolean isTextPresent(String pattern) throws Exception {
		Condition textPresentCondition = getTextPresentCondition(pattern);

		return textPresentCondition.evaluate();
	}

	@Override
	public boolean isValue(String locator, String value) throws Exception {
		Condition valueConditionCondition = getValueCondition(locator, value);

		return valueConditionCondition.evaluate();
	}

	@Override
	public boolean isVisible(String locator) throws Exception {
		return isVisibleInPage(locator);
	}

	@Override
	public boolean isVisibleInPage(String locator) throws Exception {
		Condition visibleInPageCondition = getVisibleInPageCondition(locator);

		return visibleInPageCondition.evaluate();
	}

	@Override
	public boolean isVisibleInViewport(String locator) throws Exception {
		Condition visibleInViewportCondition = getVisibleInViewportCondition(
			locator);

		return visibleInViewportCondition.evaluate();
	}

	@Override
	public void javaScriptClick(String locator) {
		executeJavaScriptEvent(locator, "MouseEvent", "click");
	}

	@Override
	public void javaScriptDoubleClick(String locator) {
		executeJavaScriptEvent(locator, "MouseEvent", "dblclick");
	}

	public String javaScriptGetText(String locator, String timeout)
		throws Exception {

		if (locator.contains("x:")) {
			return getHtmlNodeText(locator);
		}

		WebElement webElement = getWebElement(locator, timeout);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		StringBuilder sb = new StringBuilder(2);

		sb.append("var element = arguments[0];");
		sb.append("return element.innerText;");

		String text = (String)javascriptExecutor.executeScript(
			sb.toString(), webElement);

		text = text.trim();

		return StringUtil.replace(text, "\n", " ");
	}

	@Override
	public void javaScriptMouseDown(String locator) {
		executeJavaScriptEvent(locator, "MouseEvent", "mousedown");
	}

	@Override
	public void javaScriptMouseUp(String locator) {
		executeJavaScriptEvent(locator, "MouseEvent", "mouseup");
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
	public void keyPress(String locator, String keySequence) {
		WebElement webElement = getWebElement(locator);

		if (keySequence.startsWith("\\")) {
			String keycode = keySequence.substring(1);

			if (isValidKeycode(keycode)) {
				Keys keys = Keys.valueOf(keycode);

				WrapsDriver wrapsDriver = (WrapsDriver)webElement;

				if (keycode.equals("ALT") || keycode.equals("COMMAND") ||
					keycode.equals("CONTROL") || keycode.equals("SHIFT")) {

					Actions actions = new Actions(
						wrapsDriver.getWrappedDriver());

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
	public void makeVisible(String locator) {
		WebElement bodyWebElement = getWebElement("//body");

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

		WebElement locatorWebElement = getWebElement(locator);

		javascriptExecutor.executeScript(sb.toString(), locatorWebElement);
	}

	@Override
	public Options manage() {
		return _webDriver.manage();
	}

	@Override
	public void mouseDown(String locator) {
		WebElement webElement = getWebElement(locator);

		scrollWebElementIntoView(webElement);

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

		scrollWebElementIntoView(webElement);

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
	public void mouseMove(String locator) {
		WebElement webElement = getWebElement(locator);

		scrollWebElementIntoView(webElement);

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

		scrollWebElementIntoView(webElement);

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

		scrollWebElementIntoView(webElement);

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

		scrollWebElementIntoView(webElement);

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

		scrollWebElementIntoView(webElement);

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

		scrollWebElementIntoView(webElement);

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
	public Navigation navigate() {
		return _webDriver.navigate();
	}

	@Override
	public void ocularAssertElementImage(String locator) throws Exception {
		ocularConfig();

		WebElement webElement = getWebElement(locator);

		SnapshotBuilder snapshotBuilder = Ocular.snapshot();

		snapshotBuilder = snapshotBuilder.from(_webDriver);

		SampleBuilder sampleBuilder = snapshotBuilder.sample();

		sampleBuilder = sampleBuilder.using(_webDriver);

		sampleBuilder.element(webElement);

		OcularResult ocularResult = sampleBuilder.compare();

		if (!ocularResult.isEqualsImages()) {
			throw new Exception(
				"Actual element image does not match expected element image");
		}
	}

	@Override
	public void open(String url) {
		String targetURL = url.trim();

		if (targetURL.startsWith("/")) {
			targetURL = PropsValues.PORTAL_URL + targetURL;
		}

		get(targetURL);
	}

	@Override
	public void openWindow(String url, String windowID) {
		open(url);
	}

	@Override
	public void paste(String location) throws Exception {
		type(location, _clipBoard);
	}

	@Override
	public void pause(String waitTime) throws Exception {
		LiferaySeleniumUtil.pause(waitTime);
	}

	@Override
	public void pauseLoggerCheck() throws Exception {
	}

	@Override
	public void quit() {
		_webDriver.quit();
	}

	@Override
	public void refresh() {
		String url = getCurrentUrl();

		open(url);

		if (isAlertPresent()) {
			getConfirmation(null);
		}
	}

	@Override
	public void replyToEmail(String to, String body) throws Exception {
		EmailCommands.replyToEmail(to, body);

		pause("3000");
	}

	@Override
	public void robotType(String value) {
		Keyboard keyboard = new DesktopKeyboard();

		keyboard.type(value);
	}

	@Override
	public void robotTypeShortcut(String value) {
		Keyboard keyboard = new DesktopKeyboard();

		List<String> keys = Arrays.asList(value.split("\\s\\+\\s"));

		Collections.sort(keys, Comparator.comparing(String::length));

		Collections.reverse(keys);

		for (String key : keys) {
			if (key.length() == 1) {
				keyboard.type(key);
			}
			else {
				keyboard.keyDown(_keyCodeMap.get(key.toUpperCase()));
			}
		}

		Collections.reverse(keys);

		for (String key : keys) {
			if (key.length() == 1) {
			}
			else {
				keyboard.keyUp(_keyCodeMap.get(key.toUpperCase()));
			}
		}
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

		_screenshotCount++;

		LiferaySeleniumUtil.captureScreen(
			_CURRENT_DIR_NAME + "test-results/functional/screenshots/" +
				_screenshotCount + ".jpg");
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

		if (actionFailed) {
			_screenshotErrorCount++;
		}

		LiferaySeleniumUtil.captureScreen(
			_CURRENT_DIR_NAME + "test-results/functional/screenshots" +
				"/ScreenshotBeforeAction" + _screenshotErrorCount + ".jpg");
	}

	@Override
	public void scrollBy(String coordString) {
		WebElement webElement = getWebElement("//html");

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		javascriptExecutor.executeScript(
			"window.scrollBy(" + coordString + ");");
	}

	@Override
	public void scrollWebElementIntoView(String locator) throws Exception {
		scrollWebElementIntoView(getWebElement(locator));
	}

	@Override
	public void select(String selectLocator, String optionLocator) {
		Select select = new Select(getWebElement(selectLocator));

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

				selectByRegexpValue(selectLocator, regexp);
			}
			else {
				List<WebElement> optionWebElements = select.getOptions();

				for (WebElement optionWebElement : optionWebElements) {
					String optionWebElementValue =
						optionWebElement.getAttribute("value");

					if (optionWebElementValue.equals(value)) {
						label = optionWebElementValue;

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

				selectByRegexpText(selectLocator, regexp);
			}
			else {
				select.selectByVisibleText(label);
			}
		}
	}

	@Override
	public void selectFieldText() {
		LiferaySeleniumUtil.selectFieldText();
	}

	@Override
	public void selectFrame(String locator) {
		WebDriver.TargetLocator targetLocator = switchTo();

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
			_frameWebElements.push(getWebElement(locator));

			targetLocator.frame(_frameWebElements.peek());
		}
	}

	@Override
	public void selectPopUp(String windowID) {
		if (windowID.equals("") || windowID.equals("null")) {
			String title = getTitle();

			Set<String> windowHandles = getWindowHandles();

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
		Set<String> windowHandles = getWindowHandles();

		if (windowID.equals("name=undefined")) {
			String title = getTitle();

			for (String windowHandle : windowHandles) {
				WebDriver.TargetLocator targetLocator = switchTo();

				targetLocator.window(windowHandle);

				if (!title.equals(getTitle())) {
					return;
				}
			}

			TestCase.fail("Unable to find the window ID \"" + windowID + "\"");
		}
		else if (windowID.equals("null")) {
			WebDriver.TargetLocator targetLocator = switchTo();

			targetLocator.window(_defaultWindowHandle);
		}
		else {
			String targetWindowTitle = windowID;

			if (targetWindowTitle.startsWith("title=")) {
				targetWindowTitle = targetWindowTitle.substring(6);
			}

			for (String windowHandle : windowHandles) {
				WebDriver.TargetLocator targetLocator = switchTo();

				targetLocator.window(windowHandle);

				if (targetWindowTitle.equals(getTitle())) {
					return;
				}
			}

			TestCase.fail("Unable to find the window ID \"" + windowID + "\"");
		}
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

		EmailCommands.sendEmail(to, subject, body);

		pause("3000");
	}

	@Override
	public void sendKeys(String locator, String value) throws Exception {
		typeKeys(locator, value);
	}

	@Override
	public void sendKeysAceEditor(String locator, String value)
		throws Exception {

		WebElement webElement = getWebElement(locator);

		webElement.sendKeys(Keys.chord(Keys.CONTROL, Keys.END));

		typeKeys(locator, "");

		Matcher matcher = _aceEditorPattern.matcher(value);

		int x = 0;

		while (matcher.find()) {
			int y = matcher.start();

			String line = value.substring(x, y);

			webElement.sendKeys(line.trim());

			String specialCharacter = matcher.group();

			if (specialCharacter.equals("(")) {
				webElement.sendKeys("(");
			}
			else if (specialCharacter.equals("${line.separator}")) {
				keyPress(locator, "\\SPACE");
				keyPress(locator, "\\RETURN");
			}

			x = y + specialCharacter.length();
		}

		String line = value.substring(x);

		webElement.sendKeys(line.trim());
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
	public void setDefaultTimeout() {
	}

	@Override
	public void setDefaultTimeoutImplicit() {
		int timeout = PropsValues.TIMEOUT_IMPLICIT_WAIT * 1000;

		setTimeoutImplicit(String.valueOf(timeout));
	}

	@Override
	public void setPrimaryTestSuiteName(String primaryTestSuiteName) {
		_primaryTestSuiteName = primaryTestSuiteName;
	}

	@Override
	public void setTimeout(String timeout) {
	}

	@Override
	public void setTimeoutImplicit(String timeout) {
		WebDriver.Options options = manage();

		if (!PropsValues.BROWSER_TYPE.equals("safari")) {
			WebDriver.Timeouts timeouts = options.timeouts();

			timeouts.implicitlyWait(
				GetterUtil.getInteger(timeout), TimeUnit.SECONDS);
		}
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
	public void sikuliAssertElementNotPresent(String image) throws Exception {
		ScreenRegion screenRegion = new DesktopScreenRegion();

		if (screenRegion.wait(getImageTarget(image), 5000) != null) {
			throw new Exception("Element is present");
		}
	}

	@Override
	public void sikuliAssertElementPresent(String image) throws Exception {
		ScreenRegion screenRegion = new DesktopScreenRegion();

		screenRegion = screenRegion.wait(getImageTarget(image), 5000);

		if (screenRegion == null) {
			throw new Exception("Element is not present");
		}

		Canvas canvas = new DesktopCanvas();

		CanvasBuilder.ElementAdder elementAdder = canvas.add();

		CanvasBuilder.ElementAreaSetter elementAreaSetter = elementAdder.box();

		elementAreaSetter.around(screenRegion);

		canvas.display(2);
	}

	@Override
	public void sikuliClick(String image) throws Exception {
		ScreenRegion screenRegion = new DesktopScreenRegion();

		ScreenRegion imageTargetScreenRegion = screenRegion.find(
			getImageTarget(image));

		if (imageTargetScreenRegion != null) {
			Mouse mouse = new DesktopMouse();

			mouse.click(imageTargetScreenRegion.getCenter());
		}
	}

	@Override
	public void sikuliClickByIndex(String image, String index)
		throws Exception {

		ScreenRegion screenRegion = new DesktopScreenRegion();

		List<ScreenRegion> imageTargetScreenRegions = screenRegion.findAll(
			getImageTarget(image));

		ScreenRegion imageTargetScreenRegion = imageTargetScreenRegions.get(
			GetterUtil.getInteger(index));

		if (imageTargetScreenRegion != null) {
			Mouse mouse = new DesktopMouse();

			mouse.click(imageTargetScreenRegion.getCenter());
		}
	}

	@Override
	public void sikuliDragAndDrop(String image, String coordString)
		throws Exception {

		ScreenRegion screenRegion = new DesktopScreenRegion();

		screenRegion = screenRegion.find(getImageTarget(image));

		Mouse mouse = new DesktopMouse();

		mouse.move(screenRegion.getCenter());

		Robot robot = new Robot();

		robot.delay(1000);

		mouse.press();

		robot.delay(2000);

		String[] coords = coordString.split(",");

		Location location = screenRegion.getCenter();

		int x = location.getX() + GetterUtil.getInteger(coords[0]);
		int y = location.getY() + GetterUtil.getInteger(coords[1]);

		robot.mouseMove(x, y);

		robot.delay(1000);

		mouse.release();
	}

	@Override
	public void sikuliLeftMouseDown() throws Exception {
		pause("1000");

		Mouse mouse = new DesktopMouse();

		mouse.press();
	}

	@Override
	public void sikuliLeftMouseUp() throws Exception {
		pause("1000");

		Mouse mouse = new DesktopMouse();

		mouse.release();
	}

	@Override
	public void sikuliMouseMove(String image) throws Exception {
		ScreenRegion screenRegion = new DesktopScreenRegion();

		screenRegion = screenRegion.find(getImageTarget(image));

		Mouse mouse = new DesktopMouse();

		mouse.move(screenRegion.getCenter());
	}

	@Override
	public void sikuliRightMouseDown() throws Exception {
		pause("1000");

		Mouse mouse = new DesktopMouse();

		mouse.rightPress();
	}

	@Override
	public void sikuliRightMouseUp() throws Exception {
		pause("1000");

		Mouse mouse = new DesktopMouse();

		mouse.rightRelease();
	}

	@Override
	public void sikuliType(String image, String value) throws Exception {
		sikuliClick(image);

		pause("1000");

		Keyboard keyboard = new DesktopKeyboard();

		if (value.contains("${line.separator}")) {
			String[] tokens = StringUtil.split(value, "${line.separator}");

			for (int i = 0; i < tokens.length; i++) {
				keyboard.type(tokens[i]);

				if ((i + 1) < tokens.length) {
					keyboard.type(Key.ENTER);
				}
			}

			if (value.endsWith("${line.separator}")) {
				keyboard.type(Key.ENTER);
			}
		}
		else {
			keyboard.type(value);
		}
	}

	@Override
	public void sikuliUploadCommonFile(String image, String value)
		throws Exception {

		sikuliClick(image);

		Keyboard keyboard = new DesktopKeyboard();

		String filePath =
			FileUtil.getSeparator() + _TEST_DEPENDENCIES_DIR_NAME +
				FileUtil.getSeparator() + value;

		filePath = LiferaySeleniumUtil.getSourceDirFilePath(filePath);

		filePath = StringUtil.replace(filePath, "/", FileUtil.getSeparator());

		if (OSDetector.isApple()) {
			keyboard.keyDown(Key.CMD);
			keyboard.keyDown(Key.SHIFT);

			keyboard.type("g");

			keyboard.keyUp(Key.CMD);
			keyboard.keyUp(Key.SHIFT);

			sikuliType(image, filePath);

			keyboard.type(Key.ENTER);
		}
		else {
			keyboard.keyDown(Key.CTRL);

			keyboard.type("a");

			keyboard.keyUp(Key.CTRL);

			sikuliType(image, filePath);
		}

		pause("1000");

		keyboard.type(Key.ENTER);
	}

	@Override
	public void sikuliUploadTCatFile(String image, String value)
		throws Exception {

		String fileName = PropsValues.TCAT_ADMIN_REPOSITORY + "/" + value;

		fileName = FileUtil.fixFilePath(fileName);

		sikuliType(image, fileName);

		Keyboard keyboard = new DesktopKeyboard();

		pause("1000");

		keyboard.type(Key.ENTER);
	}

	@Override
	public void sikuliUploadTempFile(String image, String value)
		throws Exception {

		sikuliClick(image);

		Keyboard keyboard = new DesktopKeyboard();

		keyboard.keyDown(Key.CTRL);

		keyboard.type("a");

		keyboard.keyUp(Key.CTRL);

		String fileName = getOutputDirName() + "/" + value;

		fileName = FileUtil.fixFilePath(fileName);

		sikuliType(image, fileName);

		pause("1000");

		keyboard.type(Key.ENTER);
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
	public TargetLocator switchTo() {
		return _webDriver.switchTo();
	}

	@Override
	public void tripleClick(String locator) {
		WebElement webElement = getWebElement(locator);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		int count = 3;

		while (count > 0) {
			actions.click();

			count -= 1;
		}

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void type(String locator, String value) {
		WebElement webElement = getWebElement(locator);

		if (!webElement.isEnabled()) {
			return;
		}

		int maxRetries = 3;
		int retryCount = 0;

		while (retryCount < maxRetries) {
			webElement.clear();

			if (retryCount == 0) {
				typeKeys(locator, value);
			}
			else {
				for (char c : value.toCharArray()) {
					typeKeys(locator, Character.toString(c));

					try {
						Thread.sleep(200);
					}
					catch (InterruptedException interruptedException) {
					}
				}
			}

			String webElementTagNametagName = webElement.getTagName();

			if (!webElementTagNametagName.equals("input")) {
				break;
			}

			String typedValue = webElement.getAttribute("value");

			if (typedValue.equals(value)) {
				return;
			}

			retryCount++;

			if (retryCount < maxRetries) {
				String message =
					"Actual typed value: '" + typedValue +
						"' did not match expected typed value: '" + value +
							"'.";

				System.out.println(
					message + " Retrying LiferaySelenium.type() attempt #" +
						(retryCount + 1) + ".");
			}
		}
	}

	@Override
	public void typeAceEditor(String locator, String value) {
		WebElement webElement = getWebElement(locator);

		String webElementTagName = webElement.getTagName();

		if (webElementTagName.equals("textarea")) {
			webElement.sendKeys(Keys.chord(Keys.CONTROL, "a"));

			webElement.sendKeys(Keys.DELETE);

			Matcher matcher = _aceEditorPattern.matcher(value);

			int x = 0;

			while (matcher.find()) {
				int y = matcher.start();

				String line = value.substring(x, y);

				webElement.sendKeys(line.trim());

				String specialCharacter = matcher.group();

				if (specialCharacter.equals("(")) {
					webElement.sendKeys("(");
				}
				else if (specialCharacter.equals("${line.separator}")) {
					keyPress(locator, "\\SPACE");
					keyPress(locator, "\\RETURN");
				}

				x = y + specialCharacter.length();
			}

			String line = value.substring(x);

			webElement.sendKeys(line.trim());

			webElement.sendKeys(Keys.chord(Keys.CONTROL, Keys.SHIFT, Keys.END));

			webElement.sendKeys(Keys.DELETE);

			return;
		}

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		StringBuilder sb = new StringBuilder();

		sb.append("ace.edit(");
		sb.append(getAttribute(locator + "@id"));
		sb.append(").setValue(\"");
		sb.append(HtmlUtil.escapeJS(StringUtil.replace(value, "\\", "\\\\")));
		sb.append("\");");

		javascriptExecutor.executeScript(sb.toString());
	}

	@Override
	public void typeAlloyEditor(String locator, String value) {
		WebElement webElement = getWebElement(locator);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		StringBuilder sb = new StringBuilder();

		sb.append("CKEDITOR.instances[\"");

		String titleAttribute = getAttribute(locator + "@title");

		int x = titleAttribute.indexOf(",");

		int y = titleAttribute.indexOf(",", x + 1);

		if (y == -1) {
			y = titleAttribute.length();
		}

		sb.append(titleAttribute.substring(x + 2, y));

		sb.append("\"].setData(\"");
		sb.append(HtmlUtil.escapeJS(StringUtil.replace(value, "\\", "\\\\")));
		sb.append("\");");

		javascriptExecutor.executeScript(sb.toString());
	}

	@Override
	public void typeCKEditor(String locator, String value) {
		StringBuilder sb = new StringBuilder();

		String idAttribute = getAttribute(locator + "@id");

		int x = idAttribute.indexOf("cke__");

		int y = idAttribute.indexOf("cke__", x + 1);

		if (y == -1) {
			y = idAttribute.length();
		}

		sb.append(idAttribute.substring(x + 4, y));

		sb.append(".setHTML(\"");
		sb.append(HtmlUtil.escapeJS(StringUtil.replace(value, "\\", "\\\\")));
		sb.append("\")");

		runScript(sb.toString());
	}

	@Override
	public void typeCodeMirrorEditor(String locator, String value)
		throws Exception {

		WebElement webElement = getWebElement(locator);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		javascriptExecutor.executeScript(
			"arguments[0].CodeMirror.setValue(arguments[1]);", webElement,
			value);
	}

	@Override
	public void typeEditor(String locator, String value) {
		WrapsDriver wrapsDriver = (WrapsDriver)getWebElement(locator);

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrapsDriver.getWrappedDriver();

		StringBuilder sb = new StringBuilder();

		sb.append("CKEDITOR.instances[\"");
		sb.append(getEditorName(locator));
		sb.append("\"].setData(\"");
		sb.append(HtmlUtil.escapeJS(StringUtil.replace(value, "\\", "\\\\")));
		sb.append("\");");

		javascriptExecutor.executeScript(sb.toString());
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

		webElement.sendKeys(value.substring(i));
	}

	@Override
	public void typeScreen(String value) {
		LiferaySeleniumUtil.typeScreen(value);
	}

	@Override
	public void uncheck(String locator) {
		WebElement webElement = getWebElement(locator);

		if (webElement.isSelected()) {
			webElement.click();
		}
	}

	@Override
	public void uploadCommonFile(String location, String value)
		throws Exception {

		String filePath =
			FileUtil.getSeparator() + getTestDependenciesDirName() +
				FileUtil.getSeparator() + value;

		filePath = LiferaySeleniumUtil.getSourceDirFilePath(filePath);

		if (value.endsWith(".jar") || value.endsWith(".lar") ||
			value.endsWith(".war") || value.endsWith(".zip")) {

			File file = new File(filePath);

			if (file.isDirectory()) {
				String archiveFilePath =
					getOutputDirName() + FileUtil.getSeparator() +
						file.getName();

				archiveFilePath = FileUtil.getCanonicalPath(archiveFilePath);

				ArchiveUtil.archive(filePath, archiveFilePath);

				filePath = archiveFilePath;
			}
		}

		filePath = FileUtil.fixFilePath(filePath);

		uploadFile(location, filePath);
	}

	@Override
	public void uploadFile(String location, String value) {
		makeVisible(location);

		WebElement webElement = getWebElement(location);

		webElement.sendKeys(FileUtil.getCanonicalPath(value));
	}

	@Override
	public void uploadTempFile(String location, String value) {
		String filePath = getOutputDirName() + FileUtil.getSeparator() + value;

		filePath = FileUtil.fixFilePath(filePath);

		uploadFile(location, filePath);
	}

	@Override
	public void verifyElementNotPresent(String locator) throws Exception {
		Condition elementNotPresentCondition = getElementNotPresentCondition(
			locator);

		elementNotPresentCondition.verify();
	}

	@Override
	public void verifyElementPresent(String locator) throws Exception {
		Condition elementPresentCondition = getElementPresentCondition(locator);

		elementPresentCondition.verify();
	}

	@Override
	public void verifyJavaScript(
			String javaScript, String message, String argument)
		throws Exception {

		Condition javaScriptCondition = getJavaScriptCondition(
			javaScript, message, argument);

		javaScriptCondition.verify();
	}

	@Override
	public void verifyNotVisible(String locator) throws Exception {
		Condition notVisibleCondition = getNotVisibleCondition(locator);

		notVisibleCondition.verify();
	}

	@Override
	public void verifyVisible(String locator) throws Exception {
		Condition visibleCondition = getVisibleCondition(locator);

		visibleCondition.verify();
	}

	@Override
	public void waitForConfirmation(String pattern) throws Exception {
		Condition confirmationCondition = getConfirmationCondition(pattern);

		confirmationCondition.waitFor();
	}

	@Override
	public void waitForConsoleTextNotPresent(String text) throws Exception {
		Condition consoleTextNotPresentCondition =
			getConsoleTextNotPresentCondition(text);

		consoleTextNotPresentCondition.waitFor();
	}

	@Override
	public void waitForConsoleTextPresent(String text) throws Exception {
		Condition consoleTextPresentCondition = getConsoleTextPresentCondition(
			text);

		consoleTextPresentCondition.waitFor();
	}

	@Override
	public void waitForEditable(String locator) throws Exception {
		Condition editableCondition = getEditableCondition(locator);

		editableCondition.waitFor();
	}

	@Override
	public void waitForElementNotPresent(String locator, String throwException)
		throws Exception {

		Condition elementNotPresentCondition = getElementNotPresentCondition(
			locator);

		elementNotPresentCondition.waitFor(throwException);
	}

	@Override
	public void waitForElementPresent(String locator, String throwException)
		throws Exception {

		Condition elementPresentCondition = getElementPresentCondition(locator);

		elementPresentCondition.waitFor(throwException);
	}

	@Override
	public void waitForJavaScript(
			String javaScript, String message, String argument)
		throws Exception {

		Condition javaScriptCondition = getJavaScriptCondition(
			javaScript, message, argument);

		javaScriptCondition.waitFor();
	}

	@Override
	public void waitForJavaScriptNoError(
			String javaScript, String message, String argument)
		throws Exception {

		Condition javaScriptCondition = getJavaScriptCondition(
			javaScript, message, argument);

		javaScriptCondition.waitFor("false");
	}

	@Override
	public void waitForNotEditable(String locator) throws Exception {
		Condition notEditableCondition = getNotEditableCondition(locator);

		notEditableCondition.waitFor();
	}

	@Override
	public void waitForNotPartialText(String locator, String value)
		throws Exception {

		Condition notPartialTextCondition = getNotPartialTextCondition(
			locator, value);

		notPartialTextCondition.waitFor();
	}

	@Override
	public void waitForNotSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		Condition notSelectedLabelCondition = getNotSelectedLabelCondition(
			selectLocator, pattern);

		notSelectedLabelCondition.waitFor();
	}

	@Override
	public void waitForNotText(String locator, String value) throws Exception {
		Condition notTextCondition = getNotTextCondition(locator, value);

		notTextCondition.waitFor();
	}

	@Override
	public void waitForNotValue(String locator, String value) throws Exception {
		Condition notValueCondition = getNotValueCondition(locator, value);

		notValueCondition.waitFor();
	}

	@Override
	public void waitForNotVisible(String locator, String throwException)
		throws Exception {

		Condition notVisibleCondition = getNotVisibleCondition(locator);

		notVisibleCondition.waitFor(throwException);
	}

	@Override
	public void waitForNotVisibleInPage(String locator) throws Exception {
		Condition notVisibleInPageCondition = getNotVisibleInPageCondition(
			locator);

		notVisibleInPageCondition.waitFor();
	}

	@Override
	public void waitForNotVisibleInViewport(String locator) throws Exception {
		Condition notVisibleInViewportCondition =
			getNotVisibleInViewportCondition(locator);

		notVisibleInViewportCondition.waitFor();
	}

	@Override
	public void waitForPartialText(String locator, String value)
		throws Exception {

		Condition partialTextCondition = getPartialTextCondition(
			locator, value);

		partialTextCondition.waitFor();
	}

	@Override
	public void waitForPartialTextAceEditor(String locator, String value)
		throws Exception {

		Condition partialTextAceEditorCondition =
			getPartialTextAceEditorCondition(locator, value);

		partialTextAceEditorCondition.waitFor();
	}

	@Override
	public void waitForPartialTextCaseInsensitive(
			String locator, String pattern)
		throws Exception {

		Condition partialTextCaseInsensitiveCondition =
			getPartialTextCaseInsensitiveCondition(locator, pattern);

		partialTextCaseInsensitiveCondition.waitFor();
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
				catch (Exception exception) {
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
						targetLocator.window(getDefaultWindowHandle());

						return;
					}
				}

				try {
					Thread.sleep(1000);
				}
				catch (Exception exception) {
				}
			}
		}

		TestCase.fail("Unable to find the window ID \"" + windowID + "\"");
	}

	@Override
	public void waitForSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		Condition selectedLabelCondition = getSelectedLabelCondition(
			selectLocator, pattern);

		selectedLabelCondition.waitFor();
	}

	@Override
	public void waitForText(String locator, String value) throws Exception {
		Condition textCondition = getTextCondition(locator, value);

		textCondition.waitFor();
	}

	@Override
	public void waitForTextCaseInsensitive(String locator, String pattern)
		throws Exception {

		Condition textCaseInsensitiveCondition =
			getTextCaseInsensitiveCondition(locator, pattern);

		textCaseInsensitiveCondition.waitFor();
	}

	@Override
	public void waitForTextNotPresent(String value) throws Exception {
		Condition textNotPresentCondition = getTextNotPresentCondition(value);

		textNotPresentCondition.waitFor();
	}

	@Override
	public void waitForTextPresent(String value) throws Exception {
		Condition textPresentCondition = getTextPresentCondition(value);

		textPresentCondition.waitFor();
	}

	@Override
	public void waitForValue(String locator, String value) throws Exception {
		Condition valueConditionCondition = getValueCondition(locator, value);

		valueConditionCondition.waitFor();
	}

	@Override
	public void waitForVisible(String locator, String throwException)
		throws Exception {

		Condition visibleCondition = getVisibleCondition(locator);

		visibleCondition.waitFor(throwException);
	}

	@Override
	public void waitForVisibleInPage(String locator) throws Exception {
		Condition visibleInPageCondition = getVisibleInPageCondition(locator);

		visibleInPageCondition.waitFor();
	}

	@Override
	public void waitForVisibleInViewport(String locator) throws Exception {
		Condition visibleInViewportCondition = getVisibleInViewportCondition(
			locator);

		visibleInViewportCondition.waitFor();
	}

	protected void acceptConfirmation() {
		WebDriver.TargetLocator targetLocator = switchTo();

		Alert alert = targetLocator.alert();

		alert.accept();
	}

	protected void executeJavaScriptEvent(
		String locator, String eventType, String event) {

		WebElement webElement = getWebElement(locator);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		if (!webElement.isDisplayed()) {
			scrollWebElementIntoView(webElement);
		}

		StringBuilder sb = new StringBuilder(6);

		sb.append("var element = arguments[0];");
		sb.append("var event = document.createEvent('");
		sb.append(eventType);
		sb.append("');event.initEvent('");
		sb.append(event);
		sb.append("', true, false);element.dispatchEvent(event);");

		javascriptExecutor.executeScript(sb.toString(), webElement);
	}

	protected By getBy(String locator) {
		return LiferaySeleniumUtil.getBy(locator);
	}

	protected Condition getConfirmationCondition(String pattern) {
		return new Condition() {

			@Override
			public void assertTrue() throws Exception {
				if (!evaluate()) {
					String message = StringUtil.combine(
						"Expected text \"", pattern,
						"\" does not match actual text \"",
						getConfirmation(null), "\"");

					throw new Exception(message);
				}
			}

			@Override
			public boolean evaluate() throws Exception {
				return pattern.equals(getConfirmation(null));
			}

		};
	}

	protected Condition getConsoleTextNotPresentCondition(String text) {
		String message = "\"" + text + "\" is present in console";

		return new Condition(message) {

			@Override
			public boolean evaluate() throws Exception {
				return !LiferaySeleniumUtil.isConsoleTextPresent(text);
			}

		};
	}

	protected Condition getConsoleTextPresentCondition(String text) {
		String message = "\"" + text + "\" is not present in console";

		return new Condition(message) {

			@Override
			public boolean evaluate() throws Exception {
				return LiferaySeleniumUtil.isConsoleTextPresent(text);
			}

		};
	}

	protected String getCSSSource(String htmlSource) throws Exception {
		org.jsoup.nodes.Document htmlDocument = Jsoup.parse(htmlSource);

		Elements elements = htmlDocument.select("link[type=text/css]");

		StringBuilder sb = new StringBuilder();

		for (Element element : elements) {
			String href = element.attr("href");

			if (!href.contains(PropsValues.PORTAL_URL)) {
				href = PropsValues.PORTAL_URL + href;
			}

			Connection connection = Jsoup.connect(href);

			org.jsoup.nodes.Document document = connection.get();

			sb.append(document.text());

			sb.append("\n");
		}

		return sb.toString();
	}

	protected String getDefaultWindowHandle() {
		return _defaultWindowHandle;
	}

	protected Condition getEditableCondition(String locator) {
		String message = "Element is not editable at \"" + locator + "\"";

		return new Condition(message) {

			@Override
			public boolean evaluate() throws Exception {
				WebElement webElement = getWebElement(locator);

				return webElement.isEnabled();
			}

		};
	}

	protected String getEditorName(String locator) {
		String titleAttribute = getAttribute(locator + "@title");

		if (titleAttribute.contains("Rich Text Editor,")) {
			int x = titleAttribute.indexOf(",");

			int y = titleAttribute.indexOf(",", x + 1);

			if (y == -1) {
				y = titleAttribute.length();
			}

			return titleAttribute.substring(x + 2, y);
		}

		String idAttribute = getAttribute(locator + "@id");

		if (idAttribute.contains("cke__")) {
			int x = idAttribute.indexOf("cke__");

			int y = idAttribute.indexOf("cke__", x + 1);

			if (y == -1) {
				y = idAttribute.length();
			}

			return idAttribute.substring(x + 4, y);
		}

		return idAttribute;
	}

	protected Condition getElementNotPresentCondition(String locator) {
		String message = "Element is present at \"" + locator + "\"";

		return new Condition(message) {

			@Override
			public boolean evaluate() throws Exception {
				return !isElementPresent(locator);
			}

		};
	}

	protected int getElementPositionBottom(String locator) {
		return getElementPositionTop(locator) + getElementHeight(locator);
	}

	protected int getElementPositionCenterX(String locator) {
		return getElementPositionLeft(locator) + (getElementWidth(locator) / 2);
	}

	protected int getElementPositionCenterY(String locator) {
		return getElementPositionTop(locator) + (getElementHeight(locator) / 2);
	}

	protected int getElementPositionLeft(String locator) {
		WebElement webElement = getWebElement(locator);

		Point point = webElement.getLocation();

		return point.getX();
	}

	protected int getElementPositionRight(String locator) {
		return getElementPositionLeft(locator) + getElementWidth(locator);
	}

	protected int getElementPositionTop(String locator) {
		WebElement webElement = getWebElement(locator);

		Point point = webElement.getLocation();

		return point.getY();
	}

	protected Condition getElementPresentCondition(String locator) {
		String message = "Element is not present at \"" + locator + "\"";

		return new Condition(message) {

			@Override
			public boolean evaluate() throws Exception {
				List<WebElement> webElements = getWebElements(locator);

				return !webElements.isEmpty();
			}

		};
	}

	protected Point getFramePoint() {
		int x = 0;
		int y = 0;

		WebElement bodyWebElement = getWebElement("//body");

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

	protected int getFramePositionLeft() {
		Point point = getFramePoint();

		return point.getX();
	}

	protected int getFramePositionTop() {
		Point point = getFramePoint();

		return point.getY();
	}

	protected Stack<WebElement> getFrameWebElements() {
		return _frameWebElements;
	}

	protected ImageTarget getImageTarget(String image) throws Exception {
		String filePath =
			FileUtil.getSeparator() + getSikuliImagesDirName() + image;

		File file = new File(
			LiferaySeleniumUtil.getSourceDirFilePath(filePath));

		return new ImageTarget(file);
	}

	protected Condition getJavaScriptCondition(
		String javaScript, String message, String argument) {

		return new Condition(message) {

			@Override
			public boolean evaluate() {
				WebElement bodyWebElement = getWebElement("//body");

				WrapsDriver wrapsDriver = (WrapsDriver)bodyWebElement;

				WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

				JavascriptExecutor javascriptExecutor =
					(JavascriptExecutor)wrappedWebDriver;

				Object object = null;

				try {
					object = getWebElement(argument);
				}
				catch (ElementNotFoundPoshiRunnerException |
					   InvalidSelectorException exception) {

					object = argument;
				}

				Boolean javaScriptResult =
					(Boolean)javascriptExecutor.executeScript(
						javaScript, object);

				if (javaScriptResult == null) {
					return false;
				}

				return javaScriptResult;
			}

		};
	}

	protected int getNavigationBarHeight() {
		return _navigationBarHeight;
	}

	protected Condition getNotEditableCondition(String locator) {
		String message = "Element is editable at \"" + locator + "\"";

		return new Condition(message) {

			@Override
			public boolean evaluate() throws Exception {
				return !isEditable(locator);
			}

		};
	}

	protected Condition getNotPartialTextCondition(
		String locator, String value) {

		return new Condition() {

			@Override
			public void assertTrue() throws Exception {
				if (!evaluate()) {
					String message = StringUtil.combine(
						"\"", getText(locator), "\" contains \"", value,
						"\" at \"", locator, "\"");

					throw new Exception(message);
				}
			}

			@Override
			public boolean evaluate() throws Exception {
				return !isPartialText(locator, value);
			}

		};
	}

	protected Condition getNotSelectedLabelCondition(
		String selectLocator, String pattern) {

		return new Condition() {

			@Override
			public void assertTrue() throws Exception {
				if (isSelectedLabel(selectLocator, pattern)) {
					String message = StringUtil.combine(
						"Pattern \"", pattern, "\" matches \"",
						getSelectedLabel(selectLocator), "\" at \"",
						selectLocator, "\"");

					throw new Exception(message);
				}
			}

			@Override
			public boolean evaluate() throws Exception {
				if (isElementNotPresent(selectLocator)) {
					return false;
				}

				List<String> selectedLabelsList = Arrays.asList(
					getSelectedLabels(selectLocator));

				return !selectedLabelsList.contains(pattern);
			}

		};
	}

	protected Condition getNotTextCondition(String locator, String value) {
		return new Condition() {

			@Override
			public void assertTrue() throws Exception {
				if (!evaluate()) {
					String message = StringUtil.combine(
						"Pattern \"", value, "\" matches \"", getText(locator),
						"\" at \"", locator, "\"");

					throw new Exception(message);
				}
			}

			@Override
			public boolean evaluate() throws Exception {
				return !isText(locator, value);
			}

		};
	}

	protected Condition getNotValueCondition(String locator, String value) {
		return new Condition() {

			@Override
			public void assertTrue() throws Exception {
				if (!evaluate()) {
					String message = StringUtil.combine(
						"Pattern \"", value, "\" matches \"",
						getElementValue(locator), "\" at \"", locator, "\"");

					throw new Exception(message);
				}
			}

			@Override
			public boolean evaluate() throws Exception {
				return !isValue(locator, value);
			}

		};
	}

	protected Condition getNotVisibleCondition(String locator) {
		String message = "Element is visible at \"" + locator + "\"";

		return new Condition(message) {

			@Override
			public boolean evaluate() throws Exception {
				return !isVisible(locator);
			}

		};
	}

	protected Condition getNotVisibleInPageCondition(String locator) {
		String message = "Element is visible in page at \"" + locator + "\"";

		return new Condition(message) {

			@Override
			public boolean evaluate() throws Exception {
				return !isVisibleInPage(locator);
			}

		};
	}

	protected Condition getNotVisibleInViewportCondition(String locator) {
		String message =
			"Element is visible in viewport at \"" + locator + "\"";

		return new Condition(message) {

			@Override
			public boolean evaluate() throws Exception {
				return !isVisibleInViewport(locator);
			}

		};
	}

	protected Condition getPartialTextAceEditorCondition(
		String locator, String value) {

		return new Condition() {

			@Override
			public void assertTrue() throws Exception {
				if (!evaluate()) {
					String message = StringUtil.combine(
						"Actual text \"", getTextAceEditor(locator),
						"\" does not contain expected text \"", value,
						"\" at \"", locator, "\"");

					throw new Exception(message);
				}
			}

			@Override
			public boolean evaluate() throws Exception {
				WebElement webElement = getWebElement(locator);

				String text = webElement.getText();

				text = StringUtil.replace(text, "\n", "");

				return text.contains(value);
			}

		};
	}

	protected Condition getPartialTextCaseInsensitiveCondition(
		String locator, String value) {

		return new Condition() {

			@Override
			public void assertTrue() throws Exception {
				if (!evaluate()) {
					String message = StringUtil.combine(
						"Actual text \"", getText(locator),
						"\" does not contain expected text (case insensitive) ",
						"\"", value, "\" at \"", locator, "\"");

					throw new Exception(message);
				}
			}

			@Override
			public boolean evaluate() throws Exception {
				String actual = StringUtil.toUpperCase(getText(locator));

				return actual.contains(StringUtil.toUpperCase(value));
			}

		};
	}

	protected Condition getPartialTextCondition(String locator, String value) {
		return new Condition() {

			@Override
			public void assertTrue() throws Exception {
				if (!evaluate()) {
					String message = StringUtil.combine(
						"Actual text \"", getText(locator),
						"\" does not contain expected text \"", value,
						"\" at \"", locator, "\"");

					throw new Exception(message);
				}
			}

			@Override
			public boolean evaluate() throws Exception {
				WebElement webElement = getWebElement(locator);

				String text = webElement.getText();

				return text.contains(value);
			}

		};
	}

	protected int getScrollOffsetX() {
		WebElement bodyWebElement = getWebElement("//body");

		WrapsDriver wrapsDriver = (WrapsDriver)bodyWebElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		Object pageXOffset = javascriptExecutor.executeScript(
			"return window.pageXOffset;");

		return GetterUtil.getInteger(pageXOffset);
	}

	protected int getScrollOffsetY() {
		WebElement bodyWebElement = getWebElement("//body");

		WrapsDriver wrapsDriver = (WrapsDriver)bodyWebElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		Object pageYOffset = javascriptExecutor.executeScript(
			"return window.pageYOffset;");

		return GetterUtil.getInteger(pageYOffset);
	}

	protected Condition getSelectedLabelCondition(
		String selectLocator, String pattern) {

		return new Condition() {

			@Override
			public void assertTrue() throws Exception {
				if (isNotSelectedLabel(selectLocator, pattern)) {
					String message = StringUtil.combine(
						"Expected text \"", pattern,
						"\" does not match actual text \"",
						getSelectedLabel(selectLocator), "\" at \"",
						selectLocator + "\"");

					throw new Exception(message);
				}
			}

			@Override
			public boolean evaluate() throws Exception {
				if (isElementNotPresent(selectLocator)) {
					return false;
				}

				return pattern.equals(getSelectedLabel(selectLocator));
			}

		};
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

	protected Condition getTextCaseInsensitiveCondition(
		String locator, String value) {

		return new Condition() {

			@Override
			public void assertTrue() throws Exception {
				if (!evaluate()) {
					String message = StringUtil.combine(
						"Expected text \"", value,
						"\" does not match actual text (case insensitive) \"",
						getText(locator), "\" at \"", locator, "\"");

					throw new Exception(message);
				}
			}

			@Override
			public boolean evaluate() throws Exception {
				String actual = StringUtil.toUpperCase(getText(locator));

				return actual.equals(StringUtil.toUpperCase(value));
			}

		};
	}

	protected Condition getTextCondition(String locator, String value) {
		return new Condition() {

			@Override
			public void assertTrue() throws Exception {
				if (!evaluate()) {
					String message = StringUtil.combine(
						"Expected text \"", value,
						"\" does not match actual text \"", getText(locator),
						"\" at \"", locator, "\"");

					throw new Exception(message);
				}
			}

			@Override
			public boolean evaluate() throws Exception {
				return value.equals(getText(locator));
			}

		};
	}

	protected Condition getTextNotPresentCondition(String pattern) {
		String message = "\"" + pattern + "\" is present";

		return new Condition(message) {

			@Override
			public boolean evaluate() throws Exception {
				return !isTextPresent(pattern);
			}

		};
	}

	protected Condition getTextPresentCondition(String pattern) {
		String message = "\"" + pattern + "\" is not present";

		return new Condition(message) {

			@Override
			public boolean evaluate() throws Exception {
				WebElement webElement = getWebElement("//body");

				String text = webElement.getText();

				return text.contains(pattern);
			}

		};
	}

	protected Condition getValueCondition(String locator, String value) {
		return new Condition() {

			@Override
			public void assertTrue() throws Exception {
				if (!evaluate()) {
					String message = StringUtil.combine(
						"Expected text \"", value,
						"\" does not match actual text \"",
						getElementValue(locator), "\" at \"", locator, "\"");

					throw new Exception(message);
				}
			}

			@Override
			public boolean evaluate() throws Exception {
				return value.equals(getElementValue(locator));
			}

		};
	}

	protected int getViewportHeight() {
		WebElement bodyWebElement = getWebElement("//body");

		WrapsDriver wrapsDriver = (WrapsDriver)bodyWebElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		return GetterUtil.getInteger(
			javascriptExecutor.executeScript("return window.innerHeight;"));
	}

	protected int getViewportPositionBottom() {
		return getScrollOffsetY() + getViewportHeight();
	}

	protected Condition getVisibleCondition(String locator) {
		String message = "Element is not visible at \"" + locator + "\"";

		return new Condition(message) {

			@Override
			public boolean evaluate() throws Exception {
				return isVisibleInPage(locator);
			}

		};
	}

	protected Condition getVisibleInPageCondition(String locator) {
		String message =
			"Element is not visible in page at \"" + locator + "\"";

		return new Condition(message) {

			@Override
			public boolean evaluate() throws Exception {
				WebElement webElement = getWebElement(locator);

				scrollWebElementIntoView(webElement);

				return webElement.isDisplayed();
			}

		};
	}

	protected Condition getVisibleInViewportCondition(String locator) {
		String message =
			"Element is not visible in viewport at \"" + locator + "\"";

		return new Condition(message) {

			@Override
			public boolean evaluate() throws Exception {
				WebElement webElement = getWebElement(locator);

				return webElement.isDisplayed();
			}

		};
	}

	protected WebElement getWebElement(String locator) {
		return getWebElement(locator, null);
	}

	protected WebElement getWebElement(String locator, String timeout) {
		List<WebElement> webElements = getWebElements(locator, timeout);

		if (!webElements.isEmpty()) {
			return webElements.get(0);
		}

		throw new ElementNotFoundPoshiRunnerException(
			"Element is not present at \"" + locator + "\"");
	}

	protected List<WebElement> getWebElements(String locator) {
		return getWebElements(locator, null);
	}

	protected List<WebElement> getWebElements(String locator, String timeout) {
		if (timeout != null) {
			setTimeoutImplicit(timeout);
		}

		try {
			List<WebElement> webElements = new ArrayList<>();

			for (WebElement webElement : findElements(getBy(locator))) {
				webElements.add(new RetryWebElementImpl(locator, webElement));
			}

			return webElements;
		}
		finally {
			if (timeout != null) {
				setDefaultTimeoutImplicit();
			}
		}
	}

	protected Point getWindowPoint() {
		WebElement bodyWebElement = getWebElement("//body");

		WrapsDriver wrapsDriver = (WrapsDriver)bodyWebElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		WebDriver.Options options = wrappedWebDriver.manage();

		WebDriver.Window window = options.window();

		return window.getPosition();
	}

	protected int getWindowPositionLeft() {
		Point point = getWindowPoint();

		return point.getX();
	}

	protected int getWindowPositionTop() {
		Point point = getWindowPoint();

		return point.getY();
	}

	protected boolean isObscured(WebElement webElement) {
		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrapsDriver.getWrappedDriver();

		StringBuilder sb = new StringBuilder();

		sb.append("var element = arguments[0];");
		sb.append("console.log(element);");
		sb.append("var rect = element.getBoundingClientRect();");
		sb.append("elementX = (rect.right + rect.left) / 2;");
		sb.append("elementY = (rect.top + rect.bottom) / 2;");
		sb.append("var newElement = ");
		sb.append("document.elementFromPoint(elementX, elementY);");
		sb.append("if (element == newElement) {");
		sb.append("return false;}");
		sb.append("return true;");

		Boolean obscured = (Boolean)javascriptExecutor.executeScript(
			sb.toString(), webElement);

		return obscured.booleanValue();
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

	protected void ocularConfig() {
		OcularConfiguration ocularConfiguration = Ocular.config();

		ocularConfiguration = ocularConfiguration.snapshotPath(
			Paths.get(".", getOcularSnapImageDirName()));

		ocularConfiguration.resultPath(
			Paths.get(".", getOcularResultImageDirName()));

		ocularConfiguration.globalSimilarity(99);

		ocularConfiguration.saveSnapshot(true);
	}

	protected void saveWebPage(String fileName, String htmlSource)
		throws Exception {

		if (!PropsValues.SAVE_WEB_PAGE) {
			return;
		}

		StringBuilder sb = new StringBuilder(3);

		sb.append("<style>");
		sb.append(getCSSSource(htmlSource));
		sb.append("</style></html>");

		FileUtil.write(
			fileName,
			StringUtil.replace(htmlSource, "<\\html>", sb.toString()));
	}

	protected void scrollWebElementIntoView(WebElement webElement) {
		if (!webElement.isDisplayed() || isObscured(webElement)) {
			WrapsDriver wrapsDriver = (WrapsDriver)webElement;

			WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

			JavascriptExecutor javascriptExecutor =
				(JavascriptExecutor)wrappedWebDriver;

			javascriptExecutor.executeScript(
				"arguments[0].scrollIntoView(false);", webElement);
		}
	}

	protected void selectByRegexpText(String selectLocator, String regexp) {
		Select select = new Select(getWebElement(selectLocator));

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

	protected void selectByRegexpValue(String selectLocator, String regexp) {
		Select select = new Select(getWebElement(selectLocator));

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

	protected void setDefaultWindowHandle(String defaultWindowHandle) {
		_defaultWindowHandle = defaultWindowHandle;
	}

	protected void setNavigationBarHeight(int navigationBarHeight) {
		_navigationBarHeight = navigationBarHeight;
	}

	protected abstract class Condition {

		public Condition() {
			this("");
		}

		public Condition(String message) {
			_message = message;
		}

		public void assertTrue() throws Exception {
			if (!evaluate()) {
				throw new Exception(_message);
			}
		}

		public abstract boolean evaluate() throws Exception;

		public void verify() throws Exception {
			if (!evaluate()) {
				throw new PoshiRunnerWarningException(
					"VERIFICATION_WARNING: " + _message);
			}
		}

		public void waitFor() throws Exception {
			waitFor("true");
		}

		public void waitFor(String throwException) throws Exception {
			for (int second = 0; second < PropsValues.TIMEOUT_EXPLICIT_WAIT;
				 second++) {

				try {
					if (evaluate()) {
						return;
					}
				}
				catch (Exception exception) {
				}

				Thread.sleep(1000);
			}

			if ((throwException == null) ||
				Boolean.parseBoolean(throwException)) {

				assertTrue();
			}
		}

		private final String _message;

	}

	private static final String _CURRENT_DIR_NAME = FileUtil.getCanonicalPath(
		".");

	private static final String _OCULAR_RESULT_IMAGE_DIR_NAME;

	private static final String _OCULAR_SNAP_IMAGE_DIR_NAME;

	private static final String _OUTPUT_DIR_NAME;

	private static final String _SIKULI_IMAGES_DIR_NAME;

	private static final String _TEST_DEPENDENCIES_DIR_NAME;

	private static final Pattern _aceEditorPattern = Pattern.compile(
		"\\(|\\$\\{line\\.separator\\}");
	private static final Pattern _coordinatePairsPattern = Pattern.compile(
		"[+-]?\\d+\\,[+-]?\\d+(\\|[+-]?\\d+\\,[+-]?\\d+)*");
	private static final Map<String, Integer> _keyCodeMap =
		new Hashtable<String, Integer>() {
			{
				put("ALT", Integer.valueOf(KeyEvent.VK_ALT));
				put("COMMAND", Integer.valueOf(KeyEvent.VK_META));
				put("CONTROL", Integer.valueOf(KeyEvent.VK_CONTROL));
				put("CTRL", Integer.valueOf(KeyEvent.VK_CONTROL));
				put("SHIFT", Integer.valueOf(KeyEvent.VK_SHIFT));
			}
		};
	private static final Map<String, String> _keysSpecialChars =
		new HashMap<String, String>() {
			{
				put("!", "1");
				put("#", "3");
				put("$", "4");
				put("%", "5");
				put("&", "7");
				put("(", "9");
				put(")", "0");
				put("<", ",");
				put(">", ".");
			}
		};

	static {
		String testDependenciesDirName = PropsValues.TEST_DEPENDENCIES_DIR_NAME;

		String ocularResultImageDirName =
			testDependenciesDirName + "//ocular//result";
		String ocularSnapImageDirName =
			testDependenciesDirName + "//ocular//snap";
		String sikuliImagesDirName =
			testDependenciesDirName + "//sikuli//linux//";

		String outputDirName = PropsValues.OUTPUT_DIR_NAME;

		if (OSDetector.isApple()) {
			sikuliImagesDirName = StringUtil.replace(
				sikuliImagesDirName, "linux", "osx");
		}
		else if (OSDetector.isWindows()) {
			ocularResultImageDirName = StringUtil.replace(
				ocularResultImageDirName, "//", "\\");
			ocularSnapImageDirName = StringUtil.replace(
				ocularSnapImageDirName, "//", "\\");
			outputDirName = StringUtil.replace(outputDirName, "//", "\\");
			sikuliImagesDirName = StringUtil.replace(
				sikuliImagesDirName, "//", "\\");
			sikuliImagesDirName = StringUtil.replace(
				sikuliImagesDirName, "linux", "windows");

			testDependenciesDirName = StringUtil.replace(
				testDependenciesDirName, "//", "\\");
		}

		_OUTPUT_DIR_NAME = outputDirName;
		_OCULAR_RESULT_IMAGE_DIR_NAME = ocularResultImageDirName;
		_OCULAR_SNAP_IMAGE_DIR_NAME = ocularSnapImageDirName;
		_SIKULI_IMAGES_DIR_NAME = sikuliImagesDirName;
		_TEST_DEPENDENCIES_DIR_NAME = testDependenciesDirName;
	}

	private String _clipBoard = "";
	private String _defaultWindowHandle;
	private Stack<WebElement> _frameWebElements = new Stack<>();
	private int _navigationBarHeight = 120;
	private String _primaryTestSuiteName;
	private int _screenshotCount;
	private int _screenshotErrorCount;
	private final WebDriver _webDriver;

	private class LocationCallable implements Callable<String> {

		@Override
		public String call() throws Exception {
			return _webDriver.getCurrentUrl();
		}

		private Callable<String> _init(WebDriver webDriver) throws Exception {
			_webDriver = webDriver;

			return this;
		}

		private WebDriver _webDriver;

	}

}