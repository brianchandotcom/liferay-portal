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

/**
 * @author Brian Wing Shun Chan
 */
@SuppressWarnings("deprecation")
public interface LiferaySelenium {

	public void addCustomRequestHeader(String key, String value);

	public void addLocationStrategy(
		String strategyName, String functionDefinition);

	public void addScript(String scriptContent, String scriptTagId);

	public void addSelection(String locator, String optionLocator);

	public void allowNativeXpath(String allow);

	public void altKeyDown();

	public void altKeyUp();

	public void answerOnNextPrompt(String answer);

	public void antCommand(String fileName, String target) throws Exception;

	public void assertAlert(String pattern) throws Exception;

	public void assertAlertNotPresent() throws Exception;

	public void assertChecked(String pattern) throws Exception;

	public void assertConfirmation(String pattern) throws Exception;

	public void assertConsoleErrors() throws Exception;

	public void assertConsoleTextNotPresent(String text) throws Exception;

	public void assertConsoleTextPresent(String text) throws Exception;

	public void assertCssValue(
			String locator, String cssAttribute, String cssValue)
		throws Exception;

	public void assertEditable(String locator) throws Exception;

	public void assertElementNotPresent(String locator) throws Exception;

	public void assertElementPresent(String locator) throws Exception;

	public void assertEmailBody(String index, String body) throws Exception;

	public void assertEmailSubject(String index, String subject)
		throws Exception;

	public void assertHTMLSourceTextNotPresent(String value) throws Exception;

	public void assertHTMLSourceTextPresent(String value) throws Exception;

	public void assertJavaScriptErrors(String ignoreJavaScriptError)
		throws Exception;

	public void assertLiferayErrors() throws Exception;

	public void assertLocation(String pattern) throws Exception;

	public void assertNoJavaScriptExceptions() throws Exception;

	public void assertNoLiferayExceptions() throws Exception;

	public void assertNotAlert(String pattern);

	public void assertNotChecked(String locator) throws Exception;

	public void assertNotEditable(String locator) throws Exception;

	public void assertNotLocation(String pattern) throws Exception;

	public void assertNotPartialText(String locator, String pattern)
		throws Exception;

	public void assertNotSelectedLabel(String selectLocator, String pattern)
		throws Exception;

	public void assertNotText(String locator, String pattern) throws Exception;

	public void assertNotValue(String locator, String pattern) throws Exception;

	public void assertNotVisible(String locator) throws Exception;

	public void assertPartialConfirmation(String pattern) throws Exception;

	public void assertPartialText(String locator, String pattern)
		throws Exception;

	public void assertSelectedLabel(String selectLocator, String pattern)
		throws Exception;

	public void assertText(String locator, String pattern) throws Exception;

	public void assertTextNotPresent(String pattern) throws Exception;

	public void assertTextPresent(String pattern) throws Exception;

	public void assertValue(String locator, String pattern) throws Exception;

	public void assertVisible(String locator) throws Exception;

	public void assignId(String locator, String identifier);

	public void attachFile(String fieldLocator, String fileLocator);

	public void captureEntirePageScreenshot(String filename, String kwargs);

	public String captureEntirePageScreenshotToString(String kwargs);

	public String captureNetworkTraffic(String type);

	public void captureScreenshot(String filename);

	public String captureScreenshotToString();

	public void check(String locator);

	public void chooseCancelOnNextConfirmation();

	public void chooseOkOnNextConfirmation();

	public void click(String locator) throws Exception;

	public void clickAndWait(String locator);

	public void clickAt(String locator, String coordString);

	public void clickAtAndWait(String locator, String coordString);

	public void close();

	public void connectToEmailAccount(String emailAddress, String emailPassword)
		throws Exception;

	public void contextMenu(String locator);

	public void contextMenuAt(String locator, String coordString);

	public void controlKeyDown();

	public void controlKeyUp();

	public void copyText(String locator) throws Exception;

	public void copyValue(String locator) throws Exception;

	public void createCookie(String nameValuePair, String optionsString);

	public void deleteAllEmails() throws Exception;

	public void deleteAllVisibleCookies();

	public void deleteCookie(String name, String optionsString);

	public void deselectPopUp();

	public void doubleClick(String locator);

	public void doubleClickAt(String locator, String coordString);

	public void dragAndDrop(String locator, String movementsString);

	public void dragAndDropToObject(
		String locatorOfObjectToBeDragged,
		String locatorOfDragDestinationObject);

	public void dragdrop(String locator, String movementsString);

	public void echo(String message);

	public void fail(String message);

	public void fireEvent(String locator, String eventName);

	public void focus(String locator);

	public String getAlert();

	public String[] getAllButtons();

	public String[] getAllFields();

	public String[] getAllLinks();

	public String[] getAllWindowIds();

	public String[] getAllWindowNames();

	public String[] getAllWindowTitles();

	public String getAttribute(String attributeLocator);

	public String[] getAttributeFromAllWindows(String attributeName);

	public String getBodyText();

	public String getConfirmation();

	public String getCookie();

	public String getCookieByName(String name);

	public Number getCssCount(String css);

	public String getCurrentDay();

	public String getCurrentDayName();

	public String getCurrentMonth();

	public String getCurrentYear();

	public Number getCursorPosition(String locator);

	public Number getElementHeight(String locator);

	public Number getElementIndex(String locator);

	public Number getElementPositionLeft(String locator);

	public Number getElementPositionTop(String locator);

	public String getElementValue(String locator) throws Exception;

	public Number getElementWidth(String locator);

	public String getEmailBody(String index) throws Exception;

	public String getEmailSubject(String index) throws Exception;

	public String getEval(String script);

	public String getExpression(String expression);

	public String getFirstNumber(String locator);

	public String getFirstNumberIncrement(String locator);

	public String getHtmlSource();

	public String getLocation() throws Exception;

	public String getLog();

	public Number getMouseSpeed();

	public String getNumberDecrement(String value);

	public String getNumberIncrement(String value);

	public String getOutputDirName();

	public String getPrimaryTestSuiteName();

	public String getPrompt();

	public String getSelectedId(String selectLocator);

	public String[] getSelectedIds(String selectLocator);

	public String getSelectedIndex(String selectLocator);

	public String[] getSelectedIndexes(String selectLocator);

	public String getSelectedLabel(String selectLocator);

	public String[] getSelectedLabels(String selectLocator);

	public String getSelectedValue(String selectLocator);

	public String[] getSelectedValues(String selectLocator);

	public String[] getSelectOptions(String selectLocator);

	public String getSikuliImagesDirName();

	public String getSpeed();

	public String getTable(String tableCellAddress);

	public String getTestDependenciesDirName();

	public String getText(String locator) throws Exception;

	public String getTitle();

	public String getValue(String locator);

	public boolean getWhetherThisFrameMatchFrameExpression(
		String currentFrameString, String target);

	public boolean getWhetherThisWindowMatchWindowExpression(
		String currentWindowString, String target);

	public Number getXpathCount(String xpath);

	public void goBack();

	public void goBackAndWait();

	public void highlight(String locator);

	public void ignoreAttributesWithoutValue(String ignore);

	public boolean isAlertPresent();

	public boolean isChecked(String locator);

	public boolean isConfirmation(String pattern);

	public boolean isConfirmationPresent();

	public boolean isCookiePresent(String name);

	public boolean isEditable(String locator);

	public boolean isElementNotPresent(String locator);

	public boolean isElementPresent(String locator);

	public boolean isElementPresentAfterWait(String locator) throws Exception;

	public boolean isHTMLSourceTextPresent(String value) throws Exception;

	public boolean isNotChecked(String locator);

	public boolean isNotEditable(String locator);

	public boolean isNotPartialText(String locator, String value);

	public boolean isNotSelectedLabel(String selectLocator, String pattern);

	public boolean isNotText(String locator, String value) throws Exception;

	public boolean isNotValue(String locator, String value) throws Exception;

	public boolean isNotVisible(String locator);

	public boolean isOrdered(String locator1, String locator2);

	public boolean isPartialText(String locator, String value);

	public boolean isPromptPresent();

	public boolean isSelectedLabel(String selectLocator, String pattern);

	public boolean isSikuliImagePresent(String image) throws Exception;

	public boolean isSomethingSelected(String selectLocator);

	public boolean isTCatEnabled();

	public boolean isTestName(String testName);

	public boolean isText(String locator, String value) throws Exception;

	public boolean isTextNotPresent(String pattern);

	public boolean isTextPresent(String pattern);

	public boolean isValue(String locator, String value) throws Exception;

	public boolean isVisible(String locator);

	public void javaScriptMouseDown(String locator);

	public void javaScriptMouseUp(String locator);

	public void keyDown(String locator, String keySequence);

	public void keyDownAndWait(String locator, String keySequence);

	public void keyDownNative(String keycode);

	public void keyPress(String locator, String keySequence);

	public void keyPressAndWait(String locator, String keySequence);

	public void keyPressNative(String keycode);

	public void keyUp(String locator, String keySequence);

	public void keyUpAndWait(String locator, String keySequence);

	public void keyUpNative(String keycode);

	public void makeVisible(String locator);

	public void metaKeyDown();

	public void metaKeyUp();

	public void mouseDown(String locator);

	public void mouseDownAt(String locator, String coordString);

	public void mouseDownRight(String locator);

	public void mouseDownRightAt(String locator, String coordString);

	public void mouseMove(String locator);

	public void mouseMoveAt(String locator, String coordString);

	public void mouseOut(String locator);

	public void mouseOver(String locator);

	public void mouseRelease();

	public void mouseUp(String locator);

	public void mouseUpAt(String locator, String coordString);

	public void mouseUpRight(String locator);

	public void mouseUpRightAt(String locator, String coordString);

	public void open(String url) throws Exception;

	public void open(String url, String ignoreResponseCode);

	public void openWindow(String url, String windowID) throws Exception;

	public void paste(String locator);

	public void pause(String waitTime) throws Exception;

	public void pauseLoggerCheck() throws Exception;

	public void refresh();

	public void refreshAndWait();

	public void removeAllSelections(String locator);

	public void removeScript(String scriptTagId);

	public void removeSelection(String locator, String optionLocator);

	public void replyToEmail(String to, String body) throws Exception;

	public String retrieveLastRemoteControlLogs();

	public void rollup(String rollupName, String kwargs);

	public void runScript(String script);

	public void saveScreenshot() throws Exception;

	public void saveScreenshotAndSource() throws Exception;

	public void saveScreenshotBeforeAction(boolean actionFailed)
		throws Exception;

	public void scrollWebElementIntoView(String locator) throws Exception;

	public void select(String selectLocator, String optionLocator);

	public void selectAndWait(String selectLocator, String optionLocator);

	public void selectFieldText();

	public void selectFrame(String locator);

	public void selectPopUp(String windowID);

	public void selectWindow(String windowID);

	public void sendActionDescriptionLogger(String description);

	public boolean sendActionLogger(String command, String[] params);

	public void sendEmail(String to, String subject, String body)
		throws Exception;

	public void sendKeys(String locator, String value);

	public void sendKeysAceEditor(String locator, String value);

	public void sendLogger(String id, String status);

	public void sendMacroDescriptionLogger(String description);

	public void sendTestCaseCommandLogger(String command);

	public void sendTestCaseHeaderLogger(String command);

	public void setBrowserLogLevel(String logLevel);

	public void setContext(String context);

	public void setCursorPosition(String locator, String position);

	public void setDefaultTimeout();

	public void setDefaultTimeoutImplicit();

	public void setExtensionJs(String extensionJs);

	public void setMouseSpeed(String pixels);

	public void setPrimaryTestSuiteName(String primaryTestSuiteName);

	public void setSpeed(String value);

	public void setTimeout(String timeout);

	public void setTimeoutImplicit(String timeout);

	public void setWindowSize(String coordString);

	public void shiftKeyDown();

	public void shiftKeyUp();

	public void showContextualBanner();

	public void showContextualBanner(String className, String methodName);

	public void shutDownSeleniumServer();

	public void sikuliAssertElementNotPresent(String image) throws Exception;

	public void sikuliAssertElementPresent(String image) throws Exception;

	public void sikuliClick(String image) throws Exception;

	public void sikuliClickByIndex(String image, String index) throws Exception;

	public void sikuliDragAndDrop(String image, String coordString)
		throws Exception;

	public void sikuliLeftMouseDown() throws Exception;

	public void sikuliLeftMouseUp() throws Exception;

	public void sikuliMouseMove(String image) throws Exception;

	public void sikuliRightMouseDown() throws Exception;

	public void sikuliRightMouseUp() throws Exception;

	public void sikuliType(String image, String value) throws Exception;

	public void sikuliUploadCommonFile(String image, String value)
		throws Exception;

	public void sikuliUploadTCatFile(String image, String value)
		throws Exception;

	public void sikuliUploadTempFile(String image, String value)
		throws Exception;

	public void start();

	public void start(Object optionsObject);

	public void start(String optionsString);

	public void startLogger();

	public void stop();

	public void stopLogger();

	public void submit(String formLocator);

	public void type(String locator, String value);

	public void typeAceEditor(String locator, String value);

	public void typeAlloyEditor(String locator, String value);

	public void typeCKEditor(String locator, String value);

	public void typeKeys(String locator, String value);

	public void typeScreen(String value);

	public void uncheck(String locator);

	public void uploadCommonFile(String locator, String value) throws Exception;

	public void uploadFile(String locator, String value);

	public void uploadTempFile(String locator, String value);

	public void useXpathLibrary(String libraryName);

	public void waitForCondition(String script, String timeout);

	public void waitForConfirmation(String pattern) throws Exception;

	public void waitForElementNotPresent(String locator) throws Exception;

	public void waitForElementPresent(String locator) throws Exception;

	public void waitForFrameToLoad(String frameAddress, String timeout);

	public void waitForNotPartialText(String locator, String value)
		throws Exception;

	public void waitForNotSelectedLabel(String selectLocator, String pattern)
		throws Exception;

	public void waitForNotText(String locator, String value) throws Exception;

	public void waitForNotValue(String locator, String value) throws Exception;

	public void waitForNotVisible(String locator) throws Exception;

	public void waitForPageToLoad(String timeout);

	public void waitForPartialText(String locator, String value)
		throws Exception;

	public void waitForPopUp(String windowID, String timeout);

	public void waitForSelectedLabel(String selectLocator, String pattern)
		throws Exception;

	public void waitForText(String locator, String value) throws Exception;

	public void waitForTextNotPresent(String value) throws Exception;

	public void waitForTextPresent(String value) throws Exception;

	public void waitForValue(String locator, String value) throws Exception;

	public void waitForVisible(String locator) throws Exception;

	public void windowFocus();

	public void windowMaximize();

	public void windowMaximizeAndWait();

}