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

import com.liferay.portalweb.portal.util.RuntimeVariables;

import com.thoughtworks.selenium.Selenium;

/**
 * @author Brian Wing Shun Chan
 */
public class SeleniumWrapper implements Selenium {

	public SeleniumWrapper(Selenium selenium) {
		_selenium = selenium;
	}

	public void addCustomRequestHeader(String key, String value) {
		_selenium.addCustomRequestHeader(key, value);
	}

	public void addLocationStrategy(
		String strategyName, String functionDefinition) {

		_selenium.addLocationStrategy(strategyName, functionDefinition);
	}

	public void addScript(String scriptContent, String scriptTagId) {
		_selenium.addScript(scriptContent, scriptTagId);
	}

	public void addSelection(String locator, String optionLocator) {
		_selenium.addSelection(locator, optionLocator);
	}

	public void allowNativeXpath(String allow) {
		_selenium.allowNativeXpath(allow);
	}

	public void altKeyDown() {
		_selenium.altKeyDown();
	}

	public void altKeyUp() {
		_selenium.altKeyUp();
	}

	public void answerOnNextPrompt(String answer) {
		_selenium.answerOnNextPrompt(answer);
	}

	public void assignId(String locator, String identifier) {
		_selenium.assignId(locator, identifier);
	}

	public void attachFile(String fieldLocator, String fileLocator) {
		_selenium.attachFile(fieldLocator, fileLocator);
	}

	public void captureEntirePageScreenshot(String fileName, String kwargs) {
		_selenium.captureEntirePageScreenshot(fileName, kwargs);
	}

	public String captureEntirePageScreenshotToString(String kwargs) {
		return _selenium.captureEntirePageScreenshotToString(kwargs);
	}

	public String captureNetworkTraffic(String type) {
		return _selenium.captureNetworkTraffic(type);
	}

	public void captureScreenshot(String fileName) {
		_selenium.captureScreenshot(fileName);
	}

	public String captureScreenshotToString() {
		return _selenium.captureScreenshotToString();
	}

	public void check(String locator) {
		_selenium.check(locator);
	}

	public void chooseCancelOnNextConfirmation() {
		_selenium.chooseCancelOnNextConfirmation();
	}

	public void chooseOkOnNextConfirmation() {
		_selenium.chooseOkOnNextConfirmation();
	}

	public void click(String locator) {
		_selenium.click(locator);
	}

	public void clickAt(String locator, String coordString) {
		_selenium.clickAt(locator, coordString);
	}

	public void close() {
		_selenium.close();
	}

	public void contextMenu(String locator) {
		_selenium.contextMenu(locator);
	}

	public void contextMenuAt(String locator, String coordString) {
		_selenium.contextMenuAt(locator, coordString);
	}

	public void controlKeyDown() {
		_selenium.controlKeyDown();
	}

	public void controlKeyUp() {
		_selenium.controlKeyUp();
	}

	public void createCookie(String nameValuePair, String optionsString) {
		_selenium.createCookie(nameValuePair, optionsString);
	}

	public void deleteAllVisibleCookies() {
		_selenium.deleteAllVisibleCookies();
	}

	public void deleteCookie(String name, String optionsString) {
		_selenium.deleteCookie(name, optionsString);
	}

	public void deselectPopUp() {
		_selenium.deselectPopUp();
	}

	public void doubleClick(String locator) {
		_selenium.doubleClick(locator);
	}

	public void doubleClickAt(String locator, String coordString) {
		_selenium.doubleClickAt(locator, coordString);
	}

	public void dragAndDrop(String locator, String movementsString) {
		_selenium.dragAndDrop(locator, movementsString);
	}

	public void dragAndDropToObject(
		String locatorOfObjectToBeDragged,
		String locatorOfDragDestinationObject) {

		_selenium.dragAndDropToObject(
			locatorOfObjectToBeDragged, locatorOfDragDestinationObject);
	}

	public void dragdrop(String locator, String movementsString) {
		_selenium.dragdrop(locator, movementsString);
	}

	public void fireEvent(String locator, String eventName) {
		_selenium.fireEvent(locator, eventName);
	}

	public void focus(String locator) {
		_selenium.focus(locator);
	}

	public String getAlert() {
		return _selenium.getAlert();
	}

	public String[] getAllButtons() {
		return _selenium.getAllButtons();
	}

	public String[] getAllFields() {
		return _selenium.getAllFields();
	}

	public String[] getAllLinks() {
		return _selenium.getAllLinks();
	}

	public String[] getAllWindowIds() {
		return _selenium.getAllWindowIds();
	}

	public String[] getAllWindowNames() {
		return _selenium.getAllWindowNames();
	}

	public String[] getAllWindowTitles() {
		return _selenium.getAllWindowTitles();
	}

	public String getAttribute(String attributeLocator) {
		return _selenium.getAttribute(attributeLocator);
	}

	public String[] getAttributeFromAllWindows(String attributeName) {
		return _selenium.getAttributeFromAllWindows(attributeName);
	}

	public String getBodyText() {
		return _selenium.getBodyText();
	}

	public String getConfirmation() {
		return _selenium.getConfirmation();
	}

	public String getCookie() {
		return _selenium.getCookie();
	}

	public String getCookieByName(String name) {
		return _selenium.getCookieByName(name);
	}

	public Number getCssCount(String css) {
		return _selenium.getCssCount(css);
	}

	public Number getCursorPosition(String locator) {
		return _selenium.getCursorPosition(locator);
	}

	public Number getElementHeight(String locator) {
		return _selenium.getElementHeight(locator);
	}

	public Number getElementIndex(String locator) {
		return _selenium.getElementIndex(locator);
	}

	public Number getElementPositionLeft(String locator) {
		return _selenium.getElementPositionLeft(locator);
	}

	public Number getElementPositionTop(String locator) {
		return _selenium.getElementPositionTop(locator);
	}

	public Number getElementWidth(String locator) {
		return _selenium.getElementWidth(locator);
	}

	public String getEval(String script) {
		return _selenium.getEval(script);
	}

	public String getExpression(String expression) {
		return _selenium.getExpression(expression);
	}

	public String getHtmlSource() {
		return _selenium.getHtmlSource();
	}

	public String getLocation() {
		return _selenium.getLocation();
	}

	public String getLog() {
		return _selenium.getLog();
	}

	public Number getMouseSpeed() {
		return _selenium.getMouseSpeed();
	}

	public String getPrompt() {
		return _selenium.getPrompt();
	}

	public String getSelectedId(String selectLocator) {
		return _selenium.getSelectedId(selectLocator);
	}

	public String[] getSelectedIds(String selectLocator) {
		return _selenium.getSelectedIds(selectLocator);
	}

	public String getSelectedIndex(String selectLocator) {
		return _selenium.getSelectedIndex(selectLocator);
	}

	public String[] getSelectedIndexes(String selectLocator) {
		return _selenium.getSelectedIndexes(selectLocator);
	}

	public String getSelectedLabel(String selectLocator) {
		return _selenium.getSelectedLabel(selectLocator);
	}

	public String[] getSelectedLabels(String selectLocator) {
		return _selenium.getSelectedLabels(selectLocator);
	}

	public String getSelectedValue(String selectLocator) {
		return _selenium.getSelectedValue(selectLocator);
	}

	public String[] getSelectedValues(String selectLocator) {
		return _selenium.getSelectedValues(selectLocator);
	}

	public String[] getSelectOptions(String selectLocator) {
		return _selenium.getSelectOptions(selectLocator);
	}

	public String getSpeed() {
		return _selenium.getSpeed();
	}

	public String getTable(String tableCellAddress) {
		return _selenium.getTable(tableCellAddress);
	}

	public String getText(String locator) {
		return _selenium.getText(locator);
	}

	public String getTitle() {
		return _selenium.getTitle();
	}

	public String getValue(String locator) {
		return _selenium.getValue(locator);
	}

	public boolean getWhetherThisFrameMatchFrameExpression(
		String currentFrameString, String target) {

		return _selenium.getWhetherThisFrameMatchFrameExpression(
			currentFrameString, target);
	}

	public boolean getWhetherThisWindowMatchWindowExpression(
		String currentWindowString, String target) {

		return _selenium.getWhetherThisWindowMatchWindowExpression(
			currentWindowString, target);
	}

	public Selenium getWrappedSelenium() {
		return _selenium;
	}

	public Number getXpathCount(String xpath) {
		return _selenium.getXpathCount(xpath);
	}

	public void goBack() {
		_selenium.goBack();
	}

	public void highlight(String locator) {
		_selenium.highlight(locator);
	}

	public void ignoreAttributesWithoutValue(String ignore) {
		_selenium.ignoreAttributesWithoutValue(ignore);
	}

	public boolean isAlertPresent() {
		return _selenium.isAlertPresent();
	}

	public boolean isChecked(String locator) {
		return _selenium.isChecked(locator);
	}

	public boolean isConfirmationPresent() {
		return _selenium.isConfirmationPresent();
	}

	public boolean isCookiePresent(String name) {
		return _selenium.isCookiePresent(name);
	}

	public boolean isEditable(String locator) {
		return _selenium.isEditable(locator);
	}

	public boolean isElementPresent(String locator) {
		return _selenium.isElementPresent(locator);
	}

	public boolean isOrdered(String locator1, String locator2) {
		return _selenium.isOrdered(locator1, locator2);
	}

	public boolean isPromptPresent() {
		return _selenium.isPromptPresent();
	}

	public boolean isSomethingSelected(String selectLocator) {
		return _selenium.isSomethingSelected(selectLocator);
	}

	public boolean isTextPresent(String pattern) {
		return _selenium.isTextPresent(pattern);
	}

	public boolean isVisible(String locator) {
		return _selenium.isVisible(locator);
	}

	public void keyDown(String locator, String keySequence) {
		_selenium.keyDown(locator, keySequence);
	}

	public void keyDownNative(String keycode) {
		_selenium.keyDownNative(keycode);
	}

	public void keyPress(String locator, String keySequence) {
		_selenium.keyPress(locator, keySequence);
	}

	public void keyPressNative(String keycode) {
		_selenium.keyPressNative(keycode);
	}

	public void keyUp(String locator, String keySequence) {
		_selenium.keyUp(locator, keySequence);
	}

	public void keyUpNative(String keycode) {
		_selenium.keyUpNative(keycode);
	}

	public void metaKeyDown() {
		_selenium.metaKeyDown();
	}

	public void metaKeyUp() {
		_selenium.metaKeyUp();
	}

	public void mouseDown(String locator) {
		_selenium.mouseDown(locator);
	}

	public void mouseDownAt(String locator, String coordString) {
		_selenium.mouseDownAt(locator, coordString);
	}

	public void mouseDownRight(String locator) {
		_selenium.mouseDownRight(locator);
	}

	public void mouseDownRightAt(String locator, String coordString) {
		_selenium.mouseDownRightAt(locator, coordString);
	}

	public void mouseMove(String locator) {
		_selenium.mouseMove(locator);
	}

	public void mouseMoveAt(String locator, String coordString) {
		_selenium.mouseMoveAt(locator, coordString);
	}

	public void mouseOut(String locator) {
		_selenium.mouseOut(locator);
	}

	public void mouseOver(String locator) {
		_selenium.mouseOver(locator);
	}

	public void mouseUp(String locator) {
		_selenium.mouseUp(locator);
	}

	public void mouseUpAt(String locator, String coordString) {
		_selenium.mouseUpAt(locator, coordString);
	}

	public void mouseUpRight(String locator) {
		_selenium.mouseUpRight(locator);
	}

	public void mouseUpRightAt(String locator, String coordString) {
		_selenium.mouseUpRightAt(locator, coordString);
	}

	public void open(String url) {
		url = RuntimeVariables.replace(url);

		_selenium.open(url);
	}

	public void open(String url, String ignoreResponseCode) {
		_selenium.open(url, ignoreResponseCode);
	}

	public void openWindow(String url, String windowID) {
		_selenium.openWindow(url, windowID);
	}

	public void refresh() {
		_selenium.refresh();
	}

	public void removeAllSelections(String locator) {
		_selenium.removeAllSelections(locator);
	}

	public void removeScript(String scriptTagId) {
		_selenium.removeScript(scriptTagId);
	}

	public void removeSelection(String locator, String optionLocator) {
		_selenium.removeSelection(locator, optionLocator);
	}

	public String retrieveLastRemoteControlLogs() {
		return _selenium.retrieveLastRemoteControlLogs();
	}

	public void rollup(String rollupName, String kwargs) {
		_selenium.rollup(rollupName, kwargs);
	}

	public void runScript(String script) {
		_selenium.runScript(script);
	}

	public void select(String selectLocator, String optionLocator) {
		_selenium.select(selectLocator, optionLocator);
	}

	public void selectFrame(String locator) {
		_selenium.selectFrame(locator);
	}

	public void selectPopUp(String windowID) {
		_selenium.selectPopUp(windowID);
	}

	public void selectWindow(String windowID) {
		_selenium.selectWindow(windowID);
	}

	public void setBrowserLogLevel(String logLevel) {
		_selenium.setBrowserLogLevel(logLevel);
	}

	public void setContext(String context) {
		_selenium.setContext(context);
	}

	public void setCursorPosition(String locator, String position) {
		_selenium.setCursorPosition(locator, position);
	}

	public void setExtensionJs(String extensionJs) {
		_selenium.setExtensionJs(extensionJs);
	}

	public void setMouseSpeed(String pixels) {
		_selenium.setMouseSpeed(pixels);
	}

	public void setSpeed(String value) {
		_selenium.setSpeed(value);
	}

	public void setTimeout(String timeout) {
		_selenium.setTimeout(timeout);
	}

	public void shiftKeyDown() {
		_selenium.shiftKeyDown();
	}

	public void shiftKeyUp() {
		_selenium.shiftKeyUp();
	}

	public void showContextualBanner() {
		_selenium.showContextualBanner();
	}

	public void showContextualBanner(String className, String methodName) {
		_selenium.showContextualBanner(className, methodName);
	}

	public void shutDownSeleniumServer() {
		_selenium.shutDownSeleniumServer();
	}

	public void start() {
		_selenium.start();
	}

	public void start(Object optionsObject) {
		_selenium.start(optionsObject);
	}

	public void start(String optionsString) {
		_selenium.start(optionsString);
	}

	public void stop() {
		_selenium.stop();
	}

	public void submit(String formLocator) {
		_selenium.submit(formLocator);
	}

	public void type(String locator, String value) {
		_selenium.type(locator, value);
	}

	public void typeKeys(String locator, String value) {
		_selenium.typeKeys(locator, value);
	}

	public void uncheck(String locator) {
		_selenium.uncheck(locator);
	}

	public void useXpathLibrary(String libraryName) {
		_selenium.useXpathLibrary(libraryName);
	}

	public void waitForCondition(String script, String timeout) {
		_selenium.waitForCondition(script, timeout);
	}

	public void waitForFrameToLoad(String frameAddress, String timeout) {
		_selenium.waitForFrameToLoad(frameAddress, timeout);
	}

	public void waitForPageToLoad(String timeout) {
		_selenium.waitForPageToLoad(timeout);
	}

	public void waitForPopUp(String windowID, String timeout) {
		_selenium.waitForPopUp(windowID, timeout);
	}

	public void windowFocus() {
		_selenium.windowFocus();
	}

	public void windowMaximize() {
		_selenium.windowMaximize();
	}

	private Selenium _selenium;

}