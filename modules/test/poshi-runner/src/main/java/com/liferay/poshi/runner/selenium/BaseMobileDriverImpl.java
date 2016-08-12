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

import com.liferay.poshi.runner.util.PropsValues;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.remote.Response;

import org.w3c.dom.Node;

/**
 * @author Kenji Heigel
 */
public abstract class BaseMobileDriverImpl
	extends BaseWebDriverImpl implements MobileDriver {

	public BaseMobileDriverImpl(String browserURL, WebDriver webDriver) {
		super(browserURL, webDriver);

		_mobileDriver = (MobileDriver)webDriver;

		WebDriverHelper.setDefaultWindowHandle(webDriver.getWindowHandle());

		System.setProperty("java.awt.headless", "false");
	}

	@Override
	public void addSelection(String locator, String optionLocator) {
		WebDriverHelper.addSelection(this, locator, optionLocator);
	}

	@Override
	public void antCommand(String fileName, String target) throws Exception {
		LiferaySeleniumHelper.antCommand(this, fileName, target);
	}

	@Override
	public void assertAccessible() throws Exception {
		throw new UnsupportedOperationException();
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
	public void check(String locator) {
		WebDriverHelper.check(this, locator);
	}

	@Override
	public void click(String locator) {
		try {
			tap(locator);
		}
		catch (Exception e) {
			if (!isInViewport(locator)) {
				swipeWebElementIntoView(locator);
			}

			tap(locator);
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

		click(locator);
	}

	@Override
	public void clickAtAndWait(String locator, String coordString) {
		clickAt(locator, coordString);

		waitForPageToLoad("30000");
	}

	@Override
	public void close() {
		super.close();
	}

	public void closeApp() {
		_mobileDriver.closeApp();
	}

	@Override
	public void connectToEmailAccount(String emailAddress, String emailPassword)
		throws Exception {

		LiferaySeleniumHelper.connectToEmailAccount(
			emailAddress, emailPassword);
	}

	public WebDriver context(String name) {
		return _mobileDriver.context(name);
	}

	@Override
	public void copyText(String locator) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void copyValue(String locator) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteAllEmails() throws Exception {
		LiferaySeleniumHelper.deleteAllEmails();
	}

	@Override
	public void doubleClick(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void doubleClickAt(String locator, String coordString) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void dragAndDrop(String locator, String coordString) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void dragAndDropToObject(
		String locatorOfObjectToBeDragged,
		String locatorOfDragDestinationObject) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void dragdrop(String locator, String movementsString) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void echo(String message) {
		LiferaySeleniumHelper.echo(message);
	}

	public Response execute(String driverCommand, Map<String, ?> parameters) {
		return _mobileDriver.execute(driverCommand, parameters);
	}

	@Override
	public void fail(String message) {
		LiferaySeleniumHelper.fail(message);
	}

	public WebElement findElementByAccessibilityId(String using) {
		return _mobileDriver.findElementByAccessibilityId(using);
	}

	public List<WebElement> findElementsByAccessibilityId(String using) {
		return _mobileDriver.findElementsByAccessibilityId(using);
	}

	@Override
	public String getAlert() {
		throw new UnsupportedOperationException();
	}

	public String getAppStrings() {
		return _mobileDriver.getAppStrings();
	}

	public String getAppStrings(String language) {
		return _mobileDriver.getAppStrings(language);
	}

	@Override
	public String getAttribute(String attributeLocator) {
		return WebDriverHelper.getAttribute(this, attributeLocator);
	}

	@Override
	public String getBodyText() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getConfirmation() {
		return WebDriverHelper.getConfirmation(this);
	}

	public String getContext() {
		return _mobileDriver.getContext();
	}

	public Set<String> getContextHandles() {
		return _mobileDriver.getContextHandles();
	}

	@Override
	public String getCurrentDay() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCurrentDayName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCurrentHour() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCurrentMonth() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCurrentYear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getElementHeight(String locator) {
		return WebDriverHelper.getElementHeight(this, locator);
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

		if (!isInViewport(locator)) {
			swipeWebElementIntoView(locator);
		}

		return webElement.getAttribute("value");
	}

	@Override
	public int getElementWidth(String locator) {
		return WebDriverHelper.getElementWidth(this, locator);
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
	public String getFirstNumber(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getFirstNumberIncrement(String locator) {
		throw new UnsupportedOperationException();
	}

	public Node getHtmlNode(String locator) {
		throw new UnsupportedOperationException();
	}

	public String getHtmlNodeHref(String locator) {
		throw new UnsupportedOperationException();
	}

	public String getHtmlNodeText(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getHtmlSource() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getLocation() throws Exception {
		return WebDriverHelper.getLocation(this);
	}

	@Override
	public String getNumberDecrement(String value) {
		return LiferaySeleniumHelper.getNumberDecrement(value);
	}

	@Override
	public String getNumberIncrement(String value) {
		return LiferaySeleniumHelper.getNumberIncrement(value);
	}

	public ScreenOrientation getOrientation() {
		return _mobileDriver.getOrientation();
	}

	@Override
	public String getOutputDirName() {
		return _OUTPUT_DIR_NAME;
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
		return WebDriverHelper.getSelectedLabel(this, selectLocator, timeout);
	}

	@Override
	public String[] getSelectedLabels(String selectLocator) {
		return WebDriverHelper.getSelectedLabels(this, selectLocator);
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
	public String getText(String locator) throws Exception {
		return getText(locator, null);
	}

	public String getText(String locator, String timeout) throws Exception {
		WebElement webElement = getWebElement(locator, timeout);

		if (webElement == null) {
			throw new Exception(
				"Element is not present at \"" + locator + "\"");
		}

		if (!isInViewport(locator)) {
			swipeWebElementIntoView(locator);
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
	public void goBack() {
		WebDriverHelper.goBack(this);
	}

	@Override
	public void goBackAndWait() {
		goBack();

		waitForPageToLoad("30000");
	}

	public void hideKeyboard() {
		_mobileDriver.hideKeyboard();
	}

	public void installApp(String appPath) {
		_mobileDriver.installApp(appPath);
	}

	@Override
	public boolean isAlertPresent() {
		throw new UnsupportedOperationException();
	}

	public boolean isAppInstalled(String bundleId) {
		return _mobileDriver.isAppInstalled(bundleId);
	}

	@Override
	public boolean isChecked(String locator) {
		WebElement webElement = getWebElement(locator, "1");

		if (!webElement.isDisplayed()) {
			return webElement.isDisplayed();
		}

		if (!isInViewport(locator)) {
			swipeWebElementIntoView(locator);
		}

		return webElement.isSelected();
	}

	@Override
	public boolean isConfirmation(String pattern) {
		return LiferaySeleniumHelper.isConfirmation(this, pattern);
	}

	@Override
	public boolean isEditable(String locator) {
		throw new UnsupportedOperationException();
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

	public boolean isInViewport(String locator) {
		int elementPositionCenterY = WebDriverHelper.getElementPositionCenterY(
			this, locator);

		int viewportPositionBottom = WebDriverHelper.getViewportPositionBottom(
			this);

		int viewportPositionTop = WebDriverHelper.getScrollOffsetY(this);

		if ((elementPositionCenterY >= viewportPositionBottom) ||
			(elementPositionCenterY <= viewportPositionTop)) {

			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public boolean isNotChecked(String locator) {
		return LiferaySeleniumHelper.isNotChecked(this, locator);
	}

	@Override
	public boolean isNotEditable(String locator) {
		throw new UnsupportedOperationException();
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
	public boolean isPartialText(String locator, String value) {
		return WebDriverHelper.isPartialText(this, locator, value);
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
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isValue(String locator, String value) throws Exception {
		return value.equals(getElementValue(locator, "1"));
	}

	@Override
	public boolean isVisible(String locator) {
		WebElement webElement = getWebElement(locator, "1");

		if (PropsValues.BROWSER_TYPE.equals("android")) {
			if (!isInViewport(locator)) {
				swipeWebElementIntoView(locator);
			}

			return isInViewport(locator);
		}
		else {
			WebDriverHelper.scrollWebElementIntoView(this, webElement);

			return webElement.isDisplayed();
		}
	}

	@Override
	public void javaScriptMouseDown(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void javaScriptMouseUp(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void keyDown(String locator, String keySequence) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void keyDownAndWait(String locator, String keySequence) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void keyPress(String locator, String keySequence) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void keyPressAndWait(String locator, String keySequence) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void keyUp(String locator, String keySequence) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void keyUpAndWait(String locator, String keySequence) {
		throw new UnsupportedOperationException();
	}

	public void launchApp() {
		_mobileDriver.launchApp();
	}

	public Location location() {
		return _mobileDriver.location();
	}

	@Override
	public void makeVisible(String locator) {
		WebDriverHelper.makeVisible(this, locator);
	}

	@Override
	public void mouseDown(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void mouseDownAt(String locator, String coordString) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void mouseMove(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void mouseMoveAt(String locator, String coordString) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void mouseOut(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void mouseOver(String locator) {
	}

	@Override
	public void mouseRelease() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void mouseUp(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void mouseUpAt(String locator, String coordString) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void open(String url) {
		WebDriverHelper.open(this, url);
	}

	@Override
	public void openWindow(String url, String windowID) {
		open(url);
	}

	@Override
	public void paste(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void pause(String waitTime) throws Exception {
		LiferaySeleniumHelper.pause(waitTime);
	}

	@Override
	public void pauseLoggerCheck() throws Exception {
	}

	public void performMultiTouchAction(MultiTouchAction multiAction) {
		_mobileDriver.performMultiTouchAction(multiAction);
	}

	public TouchAction performTouchAction(TouchAction touchAction) {
		return _mobileDriver.performTouchAction(touchAction);
	}

	public void pinch(int x, int y) {
		_mobileDriver.pinch(x, y);
	}

	public void pinch(WebElement el) {
		_mobileDriver.pinch(el);
	}

	public byte[] pullFile(String remotePath) {
		return _mobileDriver.pullFile(remotePath);
	}

	public byte[] pullFolder(String remotePath) {
		return _mobileDriver.pullFolder(remotePath);
	}

	@Override
	public void refresh() {
		WebDriverHelper.refresh(this);
	}

	@Override
	public void refreshAndWait() {
		throw new UnsupportedOperationException();
	}

	public void removeApp(String bundleId) {
		_mobileDriver.removeApp(bundleId);
	}

	@Override
	public void replyToEmail(String to, String body) throws Exception {
		LiferaySeleniumHelper.replyToEmail(this, to, body);
	}

	public void resetApp() {
		_mobileDriver.resetApp();
	}

	public void rotate(ScreenOrientation orientation) {
		_mobileDriver.rotate(orientation);
	}

	public void runAppInBackground(int seconds) {
		_mobileDriver.runAppInBackground(seconds);
	}

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
	public void scrollBy(String coordString) {
		throw new UnsupportedOperationException();
	}

	public WebElement scrollTo(String text) {
		return _mobileDriver.scrollTo(text);
	}

	public WebElement scrollToExact(String text) {
		return _mobileDriver.scrollToExact(text);
	}

	@Override
	public void scrollWebElementIntoView(String locator) throws Exception {
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();
	}

	@Override
	public void selectFrame(String locator) {
		WebDriverHelper.selectFrame(this, locator);
	}

	@Override
	public void selectPopUp(String windowID) {
		throw new UnsupportedOperationException();
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
		WebDriverHelper.type(this, locator, value);
	}

	@Override
	public void sendKeysAceEditor(String locator, String value) {
		throw new UnsupportedOperationException();
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
		WebDriverHelper.setDefaultTimeoutImplicit(this);
	}

	public void setLocation(Location location) {
		_mobileDriver.setLocation(location);
	}

	@Override
	public void setPrimaryTestSuiteName(String primaryTestSuiteName) {
		_primaryTestSuiteName = primaryTestSuiteName;
	}

	@Override
	public void setTimeout(String timeout) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setTimeoutImplicit(String timeout) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setWindowSize(String coordString) {
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
	public void startLogger() {
	}

	@Override
	public void stop() {
		quit();
	}

	@Override
	public void stopLogger() {
	}

	public void swipe(
		int startx, int starty, int endx, int endy, int duration) {

		_mobileDriver.swipe(startx, starty, endx, endy, duration);
	}

	public void tap(int fingers, int x, int y, int duration) {
		_mobileDriver.tap(fingers, x, y, duration);
	}

	public void tap(int fingers, WebElement element, int duration) {
		_mobileDriver.tap(fingers, element, duration);
	}

	@Override
	public void type(String locator, String value) {
		WebDriverHelper.type(this, locator, value);
	}

	@Override
	public void typeAceEditor(String locator, String value) {
		throw new UnsupportedOperationException();
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
	public void typeEditor(String locator, String value) {
		WebDriverHelper.typeEditor(this, locator, value);
	}

	@Override
	public void typeKeys(String locator, String value) {
		throw new UnsupportedOperationException();
	}

	public void typeKeys(String locator, String value, boolean typeAceEditor) {
		throw new UnsupportedOperationException();
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
	public void uploadCommonFile(String locator, String value)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	public void uploadFile(String locator, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void uploadTempFile(String locator, String value) {
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
		throw new UnsupportedOperationException();
	}

	@Override
	public void waitForPartialText(String locator, String value)
		throws Exception {

		LiferaySeleniumHelper.waitForPartialText(this, locator, value);
	}

	@Override
	public void waitForPopUp(String windowID, String timeout) {
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();
	}

	public void zoom(int x, int y) {
		_mobileDriver.zoom(x, y);
	}

	public void zoom(WebElement el) {
		_mobileDriver.zoom(el);
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

	protected void swipeWebElementIntoView(String locator) {
		WebElement webElement = getWebElement(locator, "1");

		WebDriverHelper.scrollWebElementIntoView(this, webElement);
	}

	protected void tap(String locator) {
	}

	private static final String _OUTPUT_DIR_NAME = PropsValues.OUTPUT_DIR_NAME;

	private static final String _SIKULI_IMAGES_DIR_NAME =
		PropsValues.TEST_DEPENDENCIES_DIR_NAME + "//sikuli//linux//";

	private static final String _TEST_DEPENDENCIES_DIR_NAME =
		PropsValues.TEST_DEPENDENCIES_DIR_NAME;

	private final MobileDriver _mobileDriver;
	private String _primaryTestSuiteName;

}