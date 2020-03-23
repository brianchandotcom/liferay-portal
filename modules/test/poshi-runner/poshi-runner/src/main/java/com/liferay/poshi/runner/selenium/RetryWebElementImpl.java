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

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.interactions.internal.Locatable;
import org.openqa.selenium.remote.FileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Hashimoto
 */
public class RetryWebElementImpl extends RemoteWebElement {

	public RetryWebElementImpl(String locator, WebElement webElement) {
		_locator = locator;

		_webElement = webElement;

		_remoteWebElement = (RemoteWebElement)_webElement;
	}

	@Override
	public void clear() {
		try {
			_webElement.clear();
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			_webElement.clear();
		}
	}

	@Override
	public void click() {
		try {
			_webElement.click();
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			_webElement.click();
		}
	}

	@Override
	public boolean equals(Object obj) {
		try {
			return _remoteWebElement.equals(obj);
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _remoteWebElement.equals(obj);
		}
	}

	@Override
	public WebElement findElement(By by) {
		try {
			return _webElement.findElement(by);
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _webElement.findElement(by);
		}
	}

	@Override
	public WebElement findElementByClassName(String using) {
		try {
			return _remoteWebElement.findElementByClassName(using);
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _remoteWebElement.findElementByClassName(using);
		}
	}

	@Override
	public WebElement findElementByCssSelector(String using) {
		try {
			return _remoteWebElement.findElementByCssSelector(using);
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _remoteWebElement.findElementByCssSelector(using);
		}
	}

	@Override
	public WebElement findElementByPartialLinkText(String using) {
		try {
			return _remoteWebElement.findElementByPartialLinkText(using);
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _remoteWebElement.findElementByPartialLinkText(using);
		}
	}

	@Override
	public WebElement findElementByTagName(String using) {
		try {
			return _remoteWebElement.findElementByTagName(using);
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _remoteWebElement.findElementByTagName(using);
		}
	}

	@Override
	public WebElement findElementByXPath(String using) {
		try {
			return _remoteWebElement.findElementByXPath(using);
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _remoteWebElement.findElementByXPath(using);
		}
	}

	@Override
	public List<WebElement> findElements(By by) {
		try {
			return _webElement.findElements(by);
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _webElement.findElements(by);
		}
	}

	@Override
	public List<WebElement> findElementsByClassName(String using) {
		try {
			return _remoteWebElement.findElementsByClassName(using);
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _remoteWebElement.findElementsByClassName(using);
		}
	}

	@Override
	public List<WebElement> findElementsByCssSelector(String using) {
		try {
			return _remoteWebElement.findElementsByCssSelector(using);
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _remoteWebElement.findElementsByCssSelector(using);
		}
	}

	@Override
	public List<WebElement> findElementsByPartialLinkText(String using) {
		try {
			return _remoteWebElement.findElementsByPartialLinkText(using);
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _remoteWebElement.findElementsByPartialLinkText(using);
		}
	}

	@Override
	public List<WebElement> findElementsByTagName(String using) {
		try {
			return _remoteWebElement.findElementsByTagName(using);
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _remoteWebElement.findElementsByTagName(using);
		}
	}

	@Override
	public List<WebElement> findElementsByXPath(String using) {
		try {
			return _remoteWebElement.findElementsByXPath(using);
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _remoteWebElement.findElementsByXPath(using);
		}
	}

	@Override
	public String getAttribute(String name) {
		try {
			return _webElement.getAttribute(name);
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _webElement.getAttribute(name);
		}
	}

	@Override
	public Coordinates getCoordinates() {
		try {
			Locatable locatable = (Locatable)_webElement;

			return locatable.getCoordinates();
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			Locatable locatable = (Locatable)_webElement;

			return locatable.getCoordinates();
		}
	}

	@Override
	public String getCssValue(String propertyName) {
		try {
			return _webElement.getCssValue(propertyName);
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _webElement.getCssValue(propertyName);
		}
	}

	@Override
	public String getId() {
		try {
			return _remoteWebElement.getId();
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _remoteWebElement.getId();
		}
	}

	@Override
	public Point getLocation() {
		try {
			return _webElement.getLocation();
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _webElement.getLocation();
		}
	}

	@Override
	public <X> X getScreenshotAs(OutputType<X> target)
		throws WebDriverException {

		try {
			return _webElement.getScreenshotAs(target);
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _webElement.getScreenshotAs(target);
		}
	}

	@Override
	public Dimension getSize() {
		try {
			return _webElement.getSize();
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _webElement.getSize();
		}
	}

	@Override
	public String getTagName() {
		try {
			return _webElement.getTagName();
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _webElement.getTagName();
		}
	}

	@Override
	public String getText() {
		try {
			return _webElement.getText();
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _webElement.getText();
		}
	}

	@Override
	public WebDriver getWrappedDriver() {
		return WebDriverUtil.getWebDriver();
	}

	@Override
	public int hashCode() {
		try {
			return _remoteWebElement.hashCode();
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _remoteWebElement.hashCode();
		}
	}

	@Override
	public boolean isDisplayed() {
		try {
			return _webElement.isDisplayed();
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _webElement.isDisplayed();
		}
	}

	@Override
	public boolean isEnabled() {
		try {
			return _webElement.isEnabled();
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _webElement.isEnabled();
		}
	}

	@Override
	public boolean isSelected() {
		try {
			return _webElement.isSelected();
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _webElement.isSelected();
		}
	}

	public void sendKeys(CharSequence... keys) {
		try {
			_webElement.sendKeys(keys);
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			_webElement.sendKeys(keys);
		}
	}

	@Override
	public void setFileDetector(FileDetector fileDetector) {
		try {
			_remoteWebElement.setFileDetector(fileDetector);
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			_remoteWebElement.setFileDetector(fileDetector);
		}
	}

	@Override
	public void setId(String id) {
		try {
			_remoteWebElement.setId(id);
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			_remoteWebElement.setId(id);
		}
	}

	@Override
	public void setParent(RemoteWebDriver remoteWebDriver) {
		try {
			_remoteWebElement.setParent(remoteWebDriver);
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			_remoteWebElement.setParent(remoteWebDriver);
		}
	}

	@Override
	public void submit() {
		try {
			_webElement.submit();
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			_webElement.submit();
		}
	}

	protected String getLocator() {
		return _locator;
	}

	private void _refreshWebElement(Throwable throwable) {
		System.out.println("\n" + throwable.getMessage());
		System.out.println(
			"\nWill retry command in " + _RETRY_WAIT_TIME + " seconds\n");

		try {
			Thread.sleep(_RETRY_WAIT_TIME * 1000);
		}
		catch (Exception exception) {
		}

		WebDriver webDriver = WebDriverUtil.getWebDriver();

		WebElement webElement = webDriver.findElement(
			WebDriverHelper.getBy(_locator));

		if (webElement == _webElement) {
			System.out.println("Unable to find a new web element");
		}
		else {
			System.out.println("Found a new web element");
		}

		_webElement = webElement;

		_remoteWebElement = (RemoteWebElement)_webElement;
	}

	private static final int _RETRY_WAIT_TIME =
		PropsValues.TEST_RETRY_COMMAND_WAIT_TIME;

	private final String _locator;
	private RemoteWebElement _remoteWebElement;
	private WebElement _webElement;

}