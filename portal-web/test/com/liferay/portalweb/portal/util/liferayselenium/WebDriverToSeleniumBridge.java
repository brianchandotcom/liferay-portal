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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portalweb.portal.util.TestPropsValues;

import com.thoughtworks.selenium.Selenium;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
		WebElement webElement = getWebElement(locator);

		if (!webElement.isSelected()) {
			webElement.click();
		}
	}

	public void chooseCancelOnNextConfirmation() {
		throw new UnsupportedOperationException();
	}

	public void chooseOkOnNextConfirmation() {
		throw new UnsupportedOperationException();
	}

	public void click(String locator) {
		WebElement webElement = getWebElement(locator);

		webElement.click();
	}

	public void clickAt(String locator, String coordString) {
		Actions actions = new Actions(this);

		WebElement webElement = getWebElement(locator);

		if (coordString.contains(",")) {
			String[] coords = coordString.split(",");

			int x = GetterUtil.getInteger(coords[0]);
			int y = GetterUtil.getInteger(coords[1]);

			actions.moveToElement(webElement, x, y);

			actions.click();
		}
		else {
			webElement.click();
		}
	}

	@Override
	public void close() {
		super.close();
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
		Actions actions = new Actions(this);

		WebElement webElement = getWebElement(locator);

		actions.doubleClick(webElement);

		actions.build();

		actions.perform();
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

		Actions actions = new Actions(this);

		WebElement objectToBeDraggedWebElement = getWebElement(
			locatorOfObjectToBeDragged);
		WebElement dragDestinationObjectWebElement = getWebElement(
			locatorOfDragDestinationObject);

		actions.dragAndDrop(
			objectToBeDraggedWebElement, dragDestinationObjectWebElement);

		actions.build();

		actions.perform();
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
		WebDriver.TargetLocator targetLocator = switchTo();

		Alert alert = targetLocator.alert();

		return alert.getText();
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
		int pos = attributeLocator.lastIndexOf(CharPool.AT);

		String locator = attributeLocator.substring(0, pos - 1);

		WebElement webElement = getWebElement(locator);

		String attribute = attributeLocator.substring(pos);

		return webElement.getAttribute(attribute);
	}

	public String[] getAttributeFromAllWindows(String attributeName) {
		throw new UnsupportedOperationException();
	}

	public String getBodyText() {
		WebElement webElement = findElement(By.tagName("body"));

		return webElement.getText();
	}

	public String getConfirmation() {
		WebDriver.TargetLocator targetLocator = switchTo();

		Alert alert = targetLocator.alert();

		acceptConfirmation();

		return alert.getText();
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
		return getPageSource();
	}

	public String getLocation() {
		return getCurrentUrl();
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
		WebElement selectLocatorWebElement = getWebElement(selectLocator);

		Select select = new Select(selectLocatorWebElement);

		WebElement firstSelectedOptionWebElement =
			select.getFirstSelectedOption();

		return firstSelectedOptionWebElement.getText();
	}

	public String[] getSelectedLabels(String selectLocator) {
		WebElement selectLocatorWebElement = getWebElement(selectLocator);

		Select select = new Select(selectLocatorWebElement);

		List<WebElement> allSelectedOptionsWebElements =
			select.getAllSelectedOptions();

		return StringUtil.split(
			ListUtil.toString(allSelectedOptionsWebElements, "text"));
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
		WebElement webElement = getWebElement(locator);

		return webElement.getText();
	}

	@Override
	public String getTitle() {
		return super.getTitle();
	}

	public String getValue(String locator) {
		WebElement webElement = getWebElement(locator);

		return webElement.getAttribute("value");
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
		WebElement webElement = getWebElement(locator);

		return webElement.isSelected();
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
		List<WebElement> webElements = getWebElements(locator);

		return !webElements.isEmpty();
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
		WebElement webElement = findElement(By.tagName("body"));

		String text = webElement.getText();

		return text.contains(pattern);
	}

	public boolean isVisible(String locator) {
		WebElement webElement = getWebElement(locator);

		return webElement.isDisplayed();
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
		WebDriverBackedSelenium webDriverBackedSelenium =
			new WebDriverBackedSelenium(this, getCurrentUrl());

		webDriverBackedSelenium.mouseDown(locator);
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
		WebDriverBackedSelenium webDriverBackedSelenium =
			new WebDriverBackedSelenium(this, getCurrentUrl());

		webDriverBackedSelenium.mouseMove(locator);
	}

	public void mouseMoveAt(String locator, String coordString) {
		throw new UnsupportedOperationException();
	}

	public void mouseOut(String locator) {
		throw new UnsupportedOperationException();
	}

	public void mouseOver(String locator) {
		WebDriverBackedSelenium webDriverBackedSelenium =
			new WebDriverBackedSelenium(this, getCurrentUrl());

		webDriverBackedSelenium.mouseOver(locator);
	}

	public void mouseUp(String locator) {
		WebDriverBackedSelenium webDriverBackedSelenium =
			new WebDriverBackedSelenium(this, getCurrentUrl());

		webDriverBackedSelenium.mouseUp(locator);
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
		if (url.startsWith("/")) {
			get(TestPropsValues.PORTAL_URL + url);
		}
		else {
			get(url);
		}
	}

	public void open(String url, String ignoreResponseCode) {
		throw new UnsupportedOperationException();
	}

	public void openWindow(String url, String windowID) {
		open(url);
	}

	public void refresh() {
		WebDriver.Navigation navigation = navigate();

		navigation.refresh();
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
		WebElement webElement = getWebElement(selectLocator);

		Select select = new Select(webElement);

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

	public void selectFrame(String locator) {
		if (locator.equals("relative=top")) {
			WebDriver.TargetLocator targetLocator = switchTo();

			targetLocator.defaultContent();
		}
		else {
			WebElement webElement = getWebElement(locator);

			WebDriver.TargetLocator targetLocator = switchTo();

			targetLocator.frame(webElement);
		}
	}

	public void selectPopUp(String windowID) {
		throw new UnsupportedOperationException();
	}

	public void selectWindow(String windowID) {
		Set<String> windowHandles = getWindowHandles();

        if (!windowHandles.isEmpty()) {
        	String selectWindowTitle;

			if (windowID.startsWith("title=")) {
	        	selectWindowTitle = windowID.substring(6);
	        }
	        else {
	        	selectWindowTitle = windowID;
	        }

	        for (String windowHandle : windowHandles) {
	        	WebDriver.TargetLocator targetLocator = switchTo();

	        	targetLocator.window(windowHandle);

	        	String currentWindowTitle = getTitle();

	        	if (currentWindowTitle.equals(selectWindowTitle)) {
	        		break;
	        	}
	        }
	    }
	    else if (windowID.equals("null")) {
			WebDriver.TargetLocator targetLocator = switchTo();

			targetLocator.defaultContent();
		}
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
		WebElement webElement = getWebElement(locator);

		if (webElement.isEnabled()) {
			webElement.clear();

			webElement.sendKeys(value);
		}
	}

	public void typeKeys(String locator, String value) {
		WebElement webElement = getWebElement(locator);

		if (webElement.isEnabled()) {
			webElement.sendKeys(value);
		}
	}

	public void uncheck(String locator) {
		WebElement webElement = getWebElement(locator);

		if (webElement.isSelected()){
			webElement.click();
		} 
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