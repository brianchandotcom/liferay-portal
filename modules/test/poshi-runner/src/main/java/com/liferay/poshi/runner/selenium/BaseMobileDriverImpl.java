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
	}

	@Override
	public void assertAccessible() throws Exception {
		throw new UnsupportedOperationException();
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
	public void clickAt(
		String locator, String coordString, boolean scrollIntoView) {

		click(locator);
	}

	@Override
	public void close() {
		super.close();
	}

	public void closeApp() {
		_mobileDriver.closeApp();
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

	public Response execute(String driverCommand, Map<String, ?> parameters) {
		return _mobileDriver.execute(driverCommand, parameters);
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
	public String getBodyText() {
		throw new UnsupportedOperationException();
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
	public String getFirstNumber(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getFirstNumberIncrement(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Node getHtmlNode(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getHtmlNodeHref(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getHtmlNodeText(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getHtmlSource() {
		throw new UnsupportedOperationException();
	}

	public ScreenOrientation getOrientation() {
		return _mobileDriver.getOrientation();
	}

	@Override
	public String getText(String locator) throws Exception {
		return getText(locator, null);
	}

	@Override
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
	public boolean isEditable(String locator) {
		throw new UnsupportedOperationException();
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
	public boolean isNotEditable(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isTextPresent(String pattern) {
		throw new UnsupportedOperationException();
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
	public void paste(String locator) {
		throw new UnsupportedOperationException();
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
	public void refreshAndWait() {
		throw new UnsupportedOperationException();
	}

	public void removeApp(String bundleId) {
		_mobileDriver.removeApp(bundleId);
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
	public void selectFieldText() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void selectPopUp(String windowID) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sendKeysAceEditor(String locator, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setDefaultTimeout() {
	}

	public void setLocation(Location location) {
		_mobileDriver.setLocation(location);
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
	public void typeAceEditor(String locator, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void typeKeys(String locator, String value) {
		throw new UnsupportedOperationException();
	}

	public void typeKeys(String locator, String value, boolean typeAceEditor) {
		throw new UnsupportedOperationException();
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
	public void waitForPageToLoad(String timeout) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void waitForPopUp(String windowID, String timeout) {
		throw new UnsupportedOperationException();
	}

	public void zoom(int x, int y) {
		_mobileDriver.zoom(x, y);
	}

	public void zoom(WebElement el) {
		_mobileDriver.zoom(el);
	}

	protected void swipeWebElementIntoView(String locator) {
		WebElement webElement = getWebElement(locator, "1");

		WebDriverHelper.scrollWebElementIntoView(this, webElement);
	}

	protected void tap(String locator) {
	}

	private final MobileDriver _mobileDriver;

}