/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.GetterUtil;

import com.thoughtworks.selenium.Selenium;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * @author Brian Wing Shun Chan
 */
public class WebDriverToSeleniumBridge
	extends WebDriverWrapper implements Selenium {

	public WebDriverToSeleniumBridge(WebDriver webDriver) {
		super(webDriver);
	}

	public void addCustomRequestHeader(String key, String value) {
		throw new UnsupportedOperationException();
	}

	public void addLocationStrategy(
		String strategyName, String functionDefinition) {

		throw new UnsupportedOperationException();
	}

	public void addScript(String scriptContent, String scriptTagId) {
		throw new UnsupportedOperationException();
	}

	public void addSelection(String locator, String optionLocator) {
		Select select = new Select(getWebElement(locator));

		if (optionLocator.startsWith("index=")) {
			select.selectByIndex(Integer.parseInt(optionLocator.substring(6)));
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

	public void allowNativeXpath(String allow) {
		throw new UnsupportedOperationException();
	}

	public void altKeyDown() {
		throw new UnsupportedOperationException();
	}

	public void altKeyUp() {
		throw new UnsupportedOperationException();
	}

	public void answerOnNextPrompt(String answer) {
		throw new UnsupportedOperationException();
	}

	public void assignId(String locator, String identifier) {
		throw new UnsupportedOperationException();
	}

	public void attachFile(String fieldLocator, String fileLocator) {
		throw new UnsupportedOperationException();
	}

	public void captureEntirePageScreenshot(String fileName, String kwargs) {
		throw new UnsupportedOperationException();
	}

	public String captureEntirePageScreenshotToString(String kwargs) {
		throw new UnsupportedOperationException();
	}

	public String captureNetworkTraffic(String type) {
		throw new UnsupportedOperationException();
	}

	public void captureScreenshot(String fileName) {
		throw new UnsupportedOperationException();
	}

	public String captureScreenshotToString() {
		throw new UnsupportedOperationException();
	}

	public void check(String locator) {
		throw new UnsupportedOperationException();
	}

	public void chooseCancelOnNextConfirmation() {
		throw new UnsupportedOperationException();
	}

	public void chooseOkOnNextConfirmation() {
		throw new UnsupportedOperationException();
	}

	public void click(String locator) {
		WebElement element = getWebElement(locator);

		element.click();
	}

	public void clickAt(String locator, String coordString) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void close() {
		throw new UnsupportedOperationException();
	}

	public void contextMenu(String locator) {
		throw new UnsupportedOperationException();
	}

	public void contextMenuAt(String locator, String coordString) {
		throw new UnsupportedOperationException();
	}

	public void controlKeyDown() {
		throw new UnsupportedOperationException();
	}

	public void controlKeyUp() {
		throw new UnsupportedOperationException();
	}

	public void createCookie(String nameValuePair, String optionsString) {
		throw new UnsupportedOperationException();
	}

	public void deleteAllVisibleCookies() {
		throw new UnsupportedOperationException();
	}

	public void deleteCookie(String name, String optionsString) {
		throw new UnsupportedOperationException();
	}

	public void deselectPopUp() {
		throw new UnsupportedOperationException();
	}

	public void doubleClick(String locator) {
		throw new UnsupportedOperationException();
	}

	public void doubleClickAt(String locator, String coordString) {
		throw new UnsupportedOperationException();
	}

	public void dragAndDrop(String locator, String movementsString) {
		throw new UnsupportedOperationException();
	}

	public void dragAndDropToObject(
		String locatorOfObjectToBeDragged,
		String locatorOfDragDestinationObject) {

		throw new UnsupportedOperationException();
	}

	public void dragdrop(String locator, String movementsString) {
		throw new UnsupportedOperationException();
	}

	public void fireEvent(String locator, String eventName) {
		throw new UnsupportedOperationException();
	}

	public void focus(String locator) {
		throw new UnsupportedOperationException();
	}

	public String getAlert() {
		throw new UnsupportedOperationException();
	}

	public String[] getAllButtons() {
		throw new UnsupportedOperationException();
	}

	public String[] getAllFields() {
		throw new UnsupportedOperationException();
	}

	public String[] getAllLinks() {
		throw new UnsupportedOperationException();
	}

	public String[] getAllWindowIds() {
		throw new UnsupportedOperationException();
	}

	public String[] getAllWindowNames() {
		throw new UnsupportedOperationException();
	}

	public String[] getAllWindowTitles() {
		throw new UnsupportedOperationException();
	}

	public String getAttribute(String attributeLocator) {
		throw new UnsupportedOperationException();
	}

	public String[] getAttributeFromAllWindows(String attributeName) {
		throw new UnsupportedOperationException();
	}

	public String getBodyText() {
		throw new UnsupportedOperationException();
	}

	public String getConfirmation() {
		throw new UnsupportedOperationException();
	}

	public String getCookie() {
		throw new UnsupportedOperationException();
	}

	public String getCookieByName(String name) {
		throw new UnsupportedOperationException();
	}

	public Number getCssCount(String css) {
		throw new UnsupportedOperationException();
	}

	public Number getCursorPosition(String locator) {
		throw new UnsupportedOperationException();
	}

	public Number getElementHeight(String locator) {
		throw new UnsupportedOperationException();
	}

	public Number getElementIndex(String locator) {
		throw new UnsupportedOperationException();
	}

	public Number getElementPositionLeft(String locator) {
		throw new UnsupportedOperationException();
	}

	public Number getElementPositionTop(String locator) {
		throw new UnsupportedOperationException();
	}

	public Number getElementWidth(String locator) {
		throw new UnsupportedOperationException();
	}

	public String getEval(String script) {
		throw new UnsupportedOperationException();
	}

	public String getExpression(String expression) {
		throw new UnsupportedOperationException();
	}

	public String getHtmlSource() {
		throw new UnsupportedOperationException();
	}

	public String getLocation() {
		throw new UnsupportedOperationException();
	}

	public String getLog() {
		throw new UnsupportedOperationException();
	}

	public Number getMouseSpeed() {
		throw new UnsupportedOperationException();
	}

	public String getPrompt() {
		throw new UnsupportedOperationException();
	}

	public String getSelectedId(String selectLocator) {
		throw new UnsupportedOperationException();
	}

	public String[] getSelectedIds(String selectLocator) {
		throw new UnsupportedOperationException();
	}

	public String getSelectedIndex(String selectLocator) {
		throw new UnsupportedOperationException();
	}

	public String[] getSelectedIndexes(String selectLocator) {
		throw new UnsupportedOperationException();
	}

	public String getSelectedLabel(String selectLocator) {
		throw new UnsupportedOperationException();
	}

	public String[] getSelectedLabels(String selectLocator) {
		throw new UnsupportedOperationException();
	}

	public String getSelectedValue(String selectLocator) {
		throw new UnsupportedOperationException();
	}

	public String[] getSelectedValues(String selectLocator) {
		throw new UnsupportedOperationException();
	}

	public String[] getSelectOptions(String selectLocator) {
		throw new UnsupportedOperationException();
	}

	public String getSpeed() {
		throw new UnsupportedOperationException();
	}

	public String getTable(String tableCellAddress) {
		throw new UnsupportedOperationException();
	}

	public String getText(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getTitle() {
		throw new UnsupportedOperationException();
	}

	public String getValue(String locator) {
		throw new UnsupportedOperationException();
	}

	public boolean getWhetherThisFrameMatchFrameExpression(
		String currentFrameString, String target) {

		throw new UnsupportedOperationException();
	}

	public boolean getWhetherThisWindowMatchWindowExpression(
		String currentWindowString, String target) {

		throw new UnsupportedOperationException();
	}

	public Number getXpathCount(String xpath) {
		throw new UnsupportedOperationException();
	}

	public void goBack() {
		throw new UnsupportedOperationException();
	}

	public void highlight(String locator) {
		throw new UnsupportedOperationException();
	}

	public void ignoreAttributesWithoutValue(String ignore) {
		throw new UnsupportedOperationException();
	}

	public boolean isAlertPresent() {
		throw new UnsupportedOperationException();
	}

	public boolean isChecked(String locator) {
		throw new UnsupportedOperationException();
	}

	public boolean isConfirmationPresent() {
		throw new UnsupportedOperationException();
	}

	public boolean isCookiePresent(String name) {
		throw new UnsupportedOperationException();
	}

	public boolean isEditable(String locator) {
		throw new UnsupportedOperationException();
	}

	public boolean isElementPresent(String locator) {
		throw new UnsupportedOperationException();
	}

	public boolean isOrdered(String locator1, String locator2) {
		throw new UnsupportedOperationException();
	}

	public boolean isPromptPresent() {
		throw new UnsupportedOperationException();
	}

	public boolean isSomethingSelected(String selectLocator) {
		throw new UnsupportedOperationException();
	}

	public boolean isTextPresent(String pattern) {
		throw new UnsupportedOperationException();
	}

	public boolean isVisible(String locator) {
		throw new UnsupportedOperationException();
	}

	public void keyDown(String locator, String keySequence) {
		throw new UnsupportedOperationException();
	}

	public void keyDownNative(String keycode) {
		throw new UnsupportedOperationException();
	}

	public void keyPress(String locator, String keySequence) {
		throw new UnsupportedOperationException();
	}

	public void keyPressNative(String keycode) {
		throw new UnsupportedOperationException();
	}

	public void keyUp(String locator, String keySequence) {
		throw new UnsupportedOperationException();
	}

	public void keyUpNative(String keycode) {
		throw new UnsupportedOperationException();
	}

	public void metaKeyDown() {
		throw new UnsupportedOperationException();
	}

	public void metaKeyUp() {
		throw new UnsupportedOperationException();
	}

	public void mouseDown(String locator) {
		throw new UnsupportedOperationException();
	}

	public void mouseDownAt(String locator, String coordString) {
		throw new UnsupportedOperationException();
	}

	public void mouseDownRight(String locator) {
		throw new UnsupportedOperationException();
	}

	public void mouseDownRightAt(String locator, String coordString) {
		throw new UnsupportedOperationException();
	}

	public void mouseMove(String locator) {
		throw new UnsupportedOperationException();
	}

	public void mouseMoveAt(String locator, String coordString) {
		throw new UnsupportedOperationException();
	}

	public void mouseOut(String locator) {
		throw new UnsupportedOperationException();
	}

	public void mouseOver(String locator) {
		throw new UnsupportedOperationException();
	}

	public void mouseUp(String locator) {
		throw new UnsupportedOperationException();
	}

	public void mouseUpAt(String locator, String coordString) {
		throw new UnsupportedOperationException();
	}

	public void mouseUpRight(String locator) {
		throw new UnsupportedOperationException();
	}

	public void mouseUpRightAt(String locator, String coordString) {
		throw new UnsupportedOperationException();
	}

	public void open(String url) {
		throw new UnsupportedOperationException();
	}

	public void open(String url, String ignoreResponseCode) {
		throw new UnsupportedOperationException();
	}

	public void openWindow(String url, String windowID) {
		throw new UnsupportedOperationException();
	}

	public void refresh() {
		throw new UnsupportedOperationException();
	}

	public void removeAllSelections(String locator) {
		throw new UnsupportedOperationException();
	}

	public void removeScript(String scriptTagId) {
		throw new UnsupportedOperationException();
	}

	public void removeSelection(String locator, String optionLocator) {
		throw new UnsupportedOperationException();
	}

	public String retrieveLastRemoteControlLogs() {
		throw new UnsupportedOperationException();
	}

	public void rollup(String rollupName, String kwargs) {
		throw new UnsupportedOperationException();
	}

	public void runScript(String script) {
		throw new UnsupportedOperationException();
	}

	public void select(String selectLocator, String optionLocator) {
		throw new UnsupportedOperationException();
	}

	public void selectFrame(String locator) {
		throw new UnsupportedOperationException();
	}

	public void selectPopUp(String windowID) {
		throw new UnsupportedOperationException();
	}

	public void selectWindow(String windowID) {
		throw new UnsupportedOperationException();
	}

	public void setBrowserLogLevel(String logLevel) {
		throw new UnsupportedOperationException();
	}

	public void setContext(String context) {
		throw new UnsupportedOperationException();
	}

	public void setCursorPosition(String locator, String position) {
		throw new UnsupportedOperationException();
	}

	public void setExtensionJs(String extensionJs) {
		throw new UnsupportedOperationException();
	}

	public void setMouseSpeed(String pixels) {
		throw new UnsupportedOperationException();
	}

	public void setSpeed(String value) {
		throw new UnsupportedOperationException();
	}

	public void setTimeout(String timeout) {
		WebDriver.Options options = manage();

		Timeouts timeouts = options.timeouts();

		timeouts.implicitlyWait(
			GetterUtil.getLong(timeout), TimeUnit.MILLISECONDS);
	}

	public void shiftKeyDown() {
		throw new UnsupportedOperationException();
	}

	public void shiftKeyUp() {
		throw new UnsupportedOperationException();
	}

	public void showContextualBanner() {
		throw new UnsupportedOperationException();
	}

	public void showContextualBanner(String className, String methodName) {
		throw new UnsupportedOperationException();
	}

	public void shutDownSeleniumServer() {
		throw new UnsupportedOperationException();
	}

	public void start() {
		throw new UnsupportedOperationException();
	}

	public void start(Object optionsObject) {
		throw new UnsupportedOperationException();
	}

	public void start(String optionsString) {
		throw new UnsupportedOperationException();
	}

	public void stop() {
		throw new UnsupportedOperationException();
	}

	public void submit(String formLocator) {
		throw new UnsupportedOperationException();
	}

	public void type(String locator, String value) {
		throw new UnsupportedOperationException();
	}

	public void typeKeys(String locator, String value) {
		throw new UnsupportedOperationException();
	}

	public void uncheck(String locator) {
		throw new UnsupportedOperationException();
	}

	public void useXpathLibrary(String libraryName) {
		throw new UnsupportedOperationException();
	}

	public void waitForCondition(String script, String timeout) {
		throw new UnsupportedOperationException();
	}

	public void waitForFrameToLoad(String frameAddress, String timeout) {
		throw new UnsupportedOperationException();
	}

	public void waitForPageToLoad(String timeout) {
	}

	public void waitForPopUp(String windowID, String timeout) {
		throw new UnsupportedOperationException();
	}

	public void windowFocus() {
		throw new UnsupportedOperationException();
	}

	public void windowMaximize() {
		throw new UnsupportedOperationException();
	}

	protected void acceptConfirmation() {
		WebDriver.TargetLocator targetLocator = switchTo();

		Alert alert = targetLocator.alert();

		alert.accept();
	}

	protected WebElement getWebElement(String locator) {
		if (locator.startsWith("//")) {
			return findElement(By.xpath(locator));
		}
		else if (locator.startsWith("class=")) {
			return findElement(By.className(locator.substring(6)));
		}
		else if (locator.startsWith("css=")) {
			return findElement(By.cssSelector(locator.substring(4)));
		}
		else if (locator.startsWith("link=")) {
			return findElement(By.linkText(locator.substring(5)));
		}
		else if (locator.startsWith("name=")) {
			return findElement(By.name(locator.substring(5)));
		}
		else if (locator.startsWith("tag=")) {
			return findElement(By.tagName(locator.substring(4)));
		}
		else if (locator.startsWith("xpath=")) {
			return findElement(By.xpath(locator.substring(6)));
		}
		else {
			return findElement(By.id(locator));
		}
	}

	protected List<WebElement> getWebElements(String locator) {
		if (locator.startsWith("//")) {
			return findElements(By.xpath(locator));
		}
		else if (locator.startsWith("class=")) {
			return findElements(By.className(locator.substring(6)));
		}
		else if (locator.startsWith("css=")) {
			return findElements(By.cssSelector(locator.substring(4)));
		}
		else if (locator.startsWith("link=")) {
			return findElements(By.linkText(locator.substring(5)));
		}
		else if (locator.startsWith("name=")) {
			return findElements(By.name(locator.substring(5)));
		}
		else if (locator.startsWith("tag=")) {
			return findElements(By.tagName(locator.substring(4)));
		}
		else if (locator.startsWith("xpath=")) {
			return findElements(By.xpath(locator.substring(6)));
		}
		else {
			return findElements(By.id(locator));
		}
	}

}