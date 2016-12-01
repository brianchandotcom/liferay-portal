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

	public void addSelection(String locator, String optionLocator);

	public void antCommand(String fileName, String target) throws Exception;

	public void assertAccessible() throws Exception;

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

	public void assertPartialTextAceEditor(String locator, String pattern)
		throws Exception;

	public void assertSelectedLabel(String selectLocator, String pattern)
		throws Exception;

	public void assertText(String locator, String pattern) throws Exception;

	public void assertTextNotPresent(String pattern) throws Exception;

	public void assertTextPresent(String pattern) throws Exception;

	public void assertValue(String locator, String pattern) throws Exception;

	public void assertVisible(String locator) throws Exception;

	public void check(String locator);

	public void click(String locator) throws Exception;

	public void clickAndWait(String locator);

	public void clickAt(String locator, String coordString);

	public void clickAtAndWait(String locator, String coordString);

	public void close();

	public void connectToEmailAccount(String emailAddress, String emailPassword)
		throws Exception;

	public void copyText(String locator) throws Exception;

	public void copyValue(String locator) throws Exception;

	public void deleteAllEmails() throws Exception;

	public void doubleClick(String locator);

	public void doubleClickAt(String locator, String coordString);

	public void dragAndDrop(String locator, String movementsString);

	public void dragAndDropToObject(
		String locatorOfObjectToBeDragged,
		String locatorOfDragDestinationObject);

	public void echo(String message);

	public void fail(String message);

	public String getAlert();

	public String getAttribute(String attributeLocator);

	public String getBodyText();

	public String getConfirmation();

	public String getCurrentDay();

	public String getCurrentDayName();

	public String getCurrentHour();

	public String getCurrentMonth();

	public String getCurrentYear();

	public int getElementHeight(String locator);

	public String getElementValue(String locator) throws Exception;

	public int getElementWidth(String locator);

	public String getEmailBody(String index) throws Exception;

	public String getEmailSubject(String index) throws Exception;

	public String getEval(String script);

	public String getFirstNumber(String locator);

	public String getFirstNumberIncrement(String locator);

	public String getHtmlSource();

	public String getLocation() throws Exception;

	public String getNumberDecrement(String value);

	public String getNumberIncrement(String value);

	public String getOutputDirName();

	public String getPrimaryTestSuiteName();

	public String getSelectedLabel(String selectLocator);

	public String[] getSelectedLabels(String selectLocator);

	public String getSikuliImagesDirName();

	public String getTestDependenciesDirName();

	public String getText(String locator) throws Exception;

	public String getTitle();

	public void goBack();

	public void goBackAndWait();

	public boolean isAlertPresent();

	public boolean isChecked(String locator);

	public boolean isConfirmation(String pattern);

	public boolean isEditable(String locator);

	public boolean isElementNotPresent(String locator);

	public boolean isElementPresent(String locator);

	public boolean isElementPresentAfterWait(String locator) throws Exception;

	public boolean isHTMLSourceTextPresent(String value) throws Exception;

	public boolean isNotChecked(String locator);

	public boolean isNotEditable(String locator);

	public boolean isNotPartialText(String locator, String value);

	public boolean isNotPartialTextAceEditor(String locator, String value);

	public boolean isNotSelectedLabel(String selectLocator, String pattern);

	public boolean isNotText(String locator, String value) throws Exception;

	public boolean isNotValue(String locator, String value) throws Exception;

	public boolean isNotVisible(String locator);

	public boolean isPartialText(String locator, String value);

	public boolean isPartialTextAceEditor(String locator, String value);

	public boolean isSelectedLabel(String selectLocator, String pattern);

	public boolean isSikuliImagePresent(String image) throws Exception;

	public boolean isTCatEnabled();

	public boolean isTestName(String testName);

	public boolean isText(String locator, String value) throws Exception;

	public boolean isTextNotPresent(String pattern);

	public boolean isTextPresent(String pattern);

	public boolean isValue(String locator, String value) throws Exception;

	public boolean isVisible(String locator);

	public void javaScriptClick(String locator);

	public void javaScriptMouseDown(String locator);

	public void javaScriptMouseUp(String locator);

	public void keyDown(String locator, String keySequence);

	public void keyDownAndWait(String locator, String keySequence);

	public void keyPress(String locator, String keySequence);

	public void keyPressAndWait(String locator, String keySequence);

	public void keyUp(String locator, String keySequence);

	public void keyUpAndWait(String locator, String keySequence);

	public void makeVisible(String locator);

	public void mouseDown(String locator);

	public void mouseDownAt(String locator, String coordString);

	public void mouseMove(String locator);

	public void mouseMoveAt(String locator, String coordString);

	public void mouseOut(String locator);

	public void mouseOver(String locator);

	public void mouseRelease();

	public void mouseUp(String locator);

	public void mouseUpAt(String locator, String coordString);

	public void open(String url) throws Exception;

	public void openWindow(String url, String windowID) throws Exception;

	public void paste(String locator);

	public void pause(String waitTime) throws Exception;

	public void pauseLoggerCheck() throws Exception;

	public void refresh();

	public void refreshAndWait();

	public void replyToEmail(String to, String body) throws Exception;

	public void runScript(String script);

	public void saveScreenshot() throws Exception;

	public void saveScreenshotAndSource() throws Exception;

	public void saveScreenshotBeforeAction(boolean actionFailed)
		throws Exception;

	public void scrollBy(String coordString);

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

	public void setDefaultTimeout();

	public void setDefaultTimeoutImplicit();

	public void setPrimaryTestSuiteName(String primaryTestSuiteName);

	public void setTimeout(String timeout);

	public void setTimeoutImplicit(String timeout);

	public void setWindowSize(String coordString);

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

	public void startLogger();

	public void stop();

	public void stopLogger();

	public void type(String locator, String value);

	public void typeAceEditor(String locator, String value);

	public void typeAlloyEditor(String locator, String value);

	public void typeCKEditor(String locator, String value);

	public void typeEditor(String locator, String value);

	public void typeKeys(String locator, String value);

	public void typeScreen(String value);

	public void uncheck(String locator);

	public void uploadCommonFile(String locator, String value) throws Exception;

	public void uploadFile(String locator, String value);

	public void uploadTempFile(String locator, String value);

	public void waitForConfirmation(String pattern) throws Exception;

	public void waitForElementNotPresent(String locator) throws Exception;

	public void waitForElementPresent(String locator) throws Exception;

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

	public void waitForPartialTextAceEditor(String locator, String value)
		throws Exception;

	public void waitForPopUp(String windowID, String timeout);

	public void waitForSelectedLabel(String selectLocator, String pattern)
		throws Exception;

	public void waitForText(String locator, String value) throws Exception;

	public void waitForTextNotPresent(String value) throws Exception;

	public void waitForTextPresent(String value) throws Exception;

	public void waitForValue(String locator, String value) throws Exception;

	public void waitForVisible(String locator) throws Exception;

}