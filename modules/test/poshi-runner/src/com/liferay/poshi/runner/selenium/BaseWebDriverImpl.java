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

import com.liferay.poshi.runner.util.GetterUtil;
import com.liferay.poshi.runner.util.OSDetector;
import com.liferay.poshi.runner.util.PropsValues;
import com.liferay.poshi.runner.util.StringPool;
import com.liferay.poshi.runner.util.StringUtil;
import com.liferay.poshi.runner.util.Validator;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.WrapsDriver;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseWebDriverImpl
	extends WebDriverToSeleniumBridge implements LiferaySelenium {

	public BaseWebDriverImpl(
		String projectDirName, String browserURL, WebDriver webDriver) {

		super(webDriver);

		System.setProperty("java.awt.headless", "false");

		String dependenciesDirName =
			"portal-web//test//functional//com//liferay//portalweb//" +
				"dependencies//";

		String outputDirName = PropsValues.OUTPUT_DIR_NAME;

		String sikuliImagesDirName = dependenciesDirName + "sikuli//linux//";

		if (OSDetector.isWindows()) {
			dependenciesDirName = StringUtil.replace(
				dependenciesDirName, "//", "\\");
			outputDirName = StringUtil.replace(outputDirName, "//", "\\");
			projectDirName = StringUtil.replace(projectDirName, "//", "\\");

			sikuliImagesDirName = StringUtil.replace(
				sikuliImagesDirName, "//", "\\");
			sikuliImagesDirName = StringUtil.replace(
				sikuliImagesDirName, "linux", "windows");
		}

		_dependenciesDirName = dependenciesDirName;
		_outputDirName = outputDirName;
		_projectDirName = projectDirName;
		_sikuliImagesDirName = sikuliImagesDirName;

		WebDriver.Options options = webDriver.manage();

		WebDriver.Window window = options.window();

		window.setSize(new Dimension(1280, 1040));

		webDriver.get(browserURL);
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
	public void assertConsoleTextNotPresent(String text) throws Exception {
		LiferaySeleniumHelper.assertConsoleTextNotPresent(text);
	}

	@Override
	public void assertConsoleTextPresent(String text) throws Exception {
		LiferaySeleniumHelper.assertConsoleTextPresent(text);
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
		if (!PropsValues.TEST_ASSERT_LIFERAY_ERRORS) {
			return;
		}

		LiferaySeleniumHelper.assertLiferayErrors();
	}

	@Override
	public void assertLocation(String pattern) {
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
	public void assertNotLocation(String pattern) {
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
	public void clickAndWait(String locator) {
		super.click(locator);
		super.waitForPageToLoad("30000");
	}

	@Override
	public void clickAtAndWait(String locator, String coordString) {
		super.clickAt(locator, coordString);
		super.waitForPageToLoad("30000");
	}

	@Override
	public void connectToEmailAccount(String emailAddress, String emailPassword)
		throws Exception {

		LiferaySeleniumHelper.connectToEmailAccount(
			emailAddress, emailPassword);
	}

	@Override
	public void copyText(String locator) {
		_clipBoard = super.getText(locator);
	}

	@Override
	public void copyValue(String locator) {
		_clipBoard = super.getValue(locator);
	}

	@Override
	public void deleteAllEmails() throws Exception {
		LiferaySeleniumHelper.deleteAllEmails();
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
	public String getCurrentDay() {
		Calendar calendar = Calendar.getInstance();

		return StringUtil.valueOf(calendar.get(Calendar.DATE));
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
	public String getDependenciesDirName() {
		return _dependenciesDirName;
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
	public String getProjectDirName() {
		return _projectDirName;
	}

	@Override
	public String getSikuliImagesDirName() {
		return _sikuliImagesDirName;
	}

	@Override
	public void goBackAndWait() {
		super.goBack();
		super.waitForPageToLoad("30000");
	}

	@Override
	public boolean isConfirmation(String pattern) {
		return LiferaySeleniumHelper.isConfirmation(this, pattern);
	}

	@Override
	public boolean isElementNotPresent(String locator) {
		return LiferaySeleniumHelper.isElementNotPresent(this, locator);
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
	public boolean isMobileDeviceEnabled() {
		return LiferaySeleniumHelper.isMobileDeviceEnabled();
	}

	@Override
	public boolean isNotChecked(String locator) {
		return LiferaySeleniumHelper.isNotChecked(this, locator);
	}

	@Override
	public boolean isNotPartialText(String locator, String value) {
		return LiferaySeleniumHelper.isNotPartialText(this, locator, value);
	}

	@Override
	public boolean isNotSelectedLabel(String selectLocator, String pattern) {
		if (isElementNotPresent(selectLocator)) {
			return false;
		}

		String[] selectedLabels = getSelectedLabels(selectLocator);

		List<String> selectedLabelsList = Arrays.asList(selectedLabels);

		return !selectedLabelsList.contains(pattern);
	}

	@Override
	public boolean isNotText(String locator, String value) {
		return LiferaySeleniumHelper.isNotText(this, locator, value);
	}

	@Override
	public boolean isNotValue(String locator, String value) {
		return LiferaySeleniumHelper.isNotValue(this, locator, value);
	}

	@Override
	public boolean isNotVisible(String locator) {
		return LiferaySeleniumHelper.isNotVisible(this, locator);
	}

	@Override
	public boolean isPartialText(String locator, String value) {
		WebElement webElement = getWebElement(locator, "1");

		String text = webElement.getText();

		return text.contains(value);
	}

	@Override
	public boolean isSelectedLabel(String selectLocator, String pattern) {
		if (isElementNotPresent(selectLocator)) {
			return false;
		}

		return pattern.equals(getSelectedLabel(selectLocator, "1"));
	}

	@Override
	public boolean isTCatEnabled() {
		return LiferaySeleniumHelper.isTCatEnabled();
	}

	@Override
	public boolean isText(String locator, String value) {
		return value.equals(getText(locator, "1"));
	}

	@Override
	public boolean isTextNotPresent(String pattern) {
		return LiferaySeleniumHelper.isTextNotPresent(this, pattern);
	}

	@Override
	public boolean isValue(String locator, String value) {
		return value.equals(getValue(locator, "1"));
	}

	@Override
	public void keyDownAndWait(String locator, String keySequence) {
		super.keyDown(locator, keySequence);
		super.waitForPageToLoad("30000");
	}

	@Override
	public void keyPressAndWait(String locator, String keySequence) {
		super.keyPress(locator, keySequence);
		super.waitForPageToLoad("30000");
	}

	@Override
	public void keyUpAndWait(String locator, String keySequence) {
		super.keyUp(locator, keySequence);
		super.waitForPageToLoad("30000");
	}

	@Override
	public void makeVisible(String locator) {
		WebDriverHelper.makeVisible(this, locator);
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
	public void paste(String location) {
		super.type(location, _clipBoard);
	}

	@Override
	public void pause(String waitTime) throws Exception {
		LiferaySeleniumHelper.pause(waitTime);
	}

	@Override
	public void pauseLoggerCheck() throws Exception {
	}

	@Override
	public void refreshAndWait() {
		super.refresh();
		super.waitForPageToLoad("30000");
	}

	@Override
	public void replyToEmail(String to, String body) throws Exception {
		LiferaySeleniumHelper.replyToEmail(this, to, body);
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

		super.scrollWebElementIntoView(webElement);
	}

	@Override
	public void selectAndWait(String selectLocator, String optionLocator) {
		super.select(selectLocator, optionLocator);
		super.waitForPageToLoad("30000");
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
	public void setDefaultTimeout() {
	}

	@Override
	public void setPrimaryTestSuiteName(String primaryTestSuiteName) {
		_primaryTestSuiteName = primaryTestSuiteName;
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
	public void startLogger() {
	}

	@Override
	public void stopLogger() {
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
	public void typeCKEditor(String locator, String value) {
		WebDriverHelper.typeCKEditor(this, locator, value);
	}

	@Override
	public void typeFrame(String locator, String value) {
		LiferaySeleniumHelper.typeFrame(this, locator, value);
	}

	@Override
	public void typeScreen(String value) {
		LiferaySeleniumHelper.typeScreen(value);
	}

	@Override
	public void uploadCommonFile(String location, String value) {
		uploadFile(location, _projectDirName + _dependenciesDirName + value);
	}

	@Override
	public void uploadFile(String location, String value) {
		makeVisible(location);

		WebElement webElement = getWebElement(location);

		webElement.sendKeys(value);
	}

	@Override
	public void uploadTempFile(String location, String value) {
		String slash = "/";

		if (OSDetector.isWindows()) {
			slash = "\\";
		}

		uploadFile(location, _outputDirName + slash + value);
	}

	@Override
	public void waitForConfirmation(String pattern) throws Exception {
		int timeout =
			PropsValues.TIMEOUT_EXPLICIT_WAIT /
				PropsValues.TIMEOUT_IMPLICIT_WAIT;

		for (int second = 0;; second++) {
			if (second >= timeout) {
				assertConfirmation(pattern);
			}

			try {
				if (isConfirmation(pattern)) {
					break;
				}
			}
			catch (Exception e) {
			}
		}
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
	public void waitForPartialText(String locator, String value)
		throws Exception {

		LiferaySeleniumHelper.waitForPartialText(this, locator, value);
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
	public void windowMaximizeAndWait() {
		super.windowMaximize();
		super.waitForPageToLoad("30000");
	}

	private String _clipBoard = "";
	private final String _dependenciesDirName;
	private final String _outputDirName;
	private String _primaryTestSuiteName;
	private final String _projectDirName;
	private final String _sikuliImagesDirName;

}