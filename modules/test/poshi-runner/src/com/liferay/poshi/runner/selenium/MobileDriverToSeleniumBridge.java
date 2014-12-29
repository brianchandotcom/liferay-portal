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

package com.liferay.portalweb.portal.util.liferayselenium;

import com.thoughtworks.selenium.Selenium;

import io.appium.java_client.MobileDriver;

import java.io.IOException;

import java.util.List;

import org.openqa.selenium.WebElement;

import org.w3c.dom.Node;

/**
 * @author Kenji Heigel
 */
public class MobileDriverToSeleniumBridge
	extends MobileDriverWrapper implements Selenium {

	public MobileDriverToSeleniumBridge(MobileDriver mobileDriver) {
		super(mobileDriver);

		WebDriverHelper.setDefaultWindowHandle(mobileDriver.getWindowHandle());
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
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();
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
		WebElement webElement = getWebElement(locator);

		try {
			webElement.click();
		}
		catch (Exception e) {
			if (!isInViewport(locator)) {
				swipeWebElementIntoView(locator);
			}

			webElement.click();
		}
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
	public void close() {
		throw new UnsupportedOperationException();
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
	public void createCookie(String nameValuePair, String optionsString) {
		throw new UnsupportedOperationException();
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
	public void fireEvent(String locator, String eventName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void focus(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getAlert() {
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();
	}

	@Override
	public String getConfirmation() {
		throw new UnsupportedOperationException();
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
	public Number getElementWidth(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getEval(String script) {
		return WebDriverHelper.getEval(this, script);
	}

	@Override
	public String getExpression(String expression) {
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
	public String getLocation() {
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
		throw new UnsupportedOperationException();
	}

	public String getSelectedLabel(String selectLocator, String timeout) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String[] getSelectedLabels(String selectLocator) {
		throw new UnsupportedOperationException();
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
	public String getSpeed() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getTable(String tableCellAddress) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getText(String locator) {
		return getText(locator, null);
	}

	public String getText(String locator, String timeout) {
		WebElement webElement = getWebElement(locator, timeout);

		if (!isInViewport(locator)) {
			swipeWebElementIntoView(locator);
		}

		String text = webElement.getText();

		text = text.trim();

		return text.replace("\n", " ");
	}

	@Override
	public String getTitle() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getValue(String locator) {
		throw new UnsupportedOperationException();
	}

	public String getValue(String locator, String timeout) {
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
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isChecked(String locator) {
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isElementPresent(String locator) {
		return WebDriverHelper.isElementPresent(this, locator);
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
	public boolean isOrdered(String locator1, String locator2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isPromptPresent() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSomethingSelected(String selectLocator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isTextPresent(String pattern) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isVisible(String locator) {
		WebElement webElement = getWebElement(locator, "1");

		if (!webElement.isDisplayed()) {
			return webElement.isDisplayed();
		}

		if (!isInViewport(locator)) {
			swipeWebElementIntoView(locator);
		}

		return isInViewport(locator);
	}

	@Override
	public void keyDown(String locator, String keySequence) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void keyDownNative(String keycode) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void keyPress(String locator, String keySequence) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void keyPressNative(String keycode) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void keyUp(String locator, String keySequence) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void keyUpNative(String keycode) {
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();
	}

	@Override
	public void mouseDownAt(String locator, String coordString) {
		throw new UnsupportedOperationException();
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
	public void mouseUp(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void mouseUpAt(String locator, String coordString) {
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();
	}

	@Override
	public void refresh() {
		WebDriverHelper.refresh(this);
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
	public void select(String selectLocator, String optionLocator) {
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
	public void setSpeed(String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setTimeout(String timeout) {
		throw new UnsupportedOperationException();
	}

	public void setTimeoutImplicit(String timeout) {
		WebDriverHelper.setTimeoutImplicit(this, timeout);
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
	public void stop() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void submit(String formLocator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void type(String locator, String value) {
		WebDriverHelper.type(this, locator, value);
	}

	@Override
	public void typeKeys(String locator, String value) {
		throw new UnsupportedOperationException();
	}

	public void typeKeys(String locator, String value, boolean typeAceEditor) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void uncheck(String locator) {
		throw new UnsupportedOperationException();
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
	public void waitForFrameToLoad(String frameAddress, String timeout) {
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

	@Override
	public void windowFocus() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void windowMaximize() {
		throw new UnsupportedOperationException();
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
		int elementPositionCenterY = WebDriverHelper.getElementPositionCenterY(
			this, locator);

		for (int i = 0; i < 25; i++) {
			int viewportPositionBottom =
				WebDriverHelper.getViewportPositionBottom(this);

			int viewportPositionTop = WebDriverHelper.getScrollOffsetY(this);

			if (elementPositionCenterY >= viewportPositionBottom) {
				try {
					Runtime runtime = Runtime.getRuntime();

					runtime.exec(
						"adb -s emulator-5554 shell /data/local/swipe_up.sh");
				}
				catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
			else if (elementPositionCenterY <= viewportPositionTop ) {
				try {
					Runtime runtime = Runtime.getRuntime();

					runtime.exec(
						"adb -s emulator-5554 shell /data/local/swipe_down.sh");
				}
				catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
			else {
				break;
			}

			try {
				LiferaySeleniumHelper.pause("1000");
			}
			catch (Exception e) {
			}
		}
	}

}