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
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsDriver;

/**
 * @author Brian Wing Shun Chan
 */
public class RetryWebElementImpl implements Locatable, WebElement, WrapsDriver {

	public RetryWebElementImpl(String locator, WebElement webElement) {
		_locator = locator;
		_webElement = webElement;
	}

	@Override
	public void clear() {
		try {
			_webElement.clear();
		}
		catch (StaleElementReferenceException sere) {
			_refreshWebElement(sere);

			_webElement.clear();
		}
	}

	@Override
	public void click() {
		try {
			_webElement.click();
		}
		catch (StaleElementReferenceException sere) {
			_refreshWebElement(sere);

			_webElement.click();
		}
	}

	@Override
	public WebElement findElement(By by) {
		try {
			return _webElement.findElement(by);
		}
		catch (StaleElementReferenceException sere) {
			_refreshWebElement(sere);

			return _webElement.findElement(by);
		}
	}

	@Override
	public List<WebElement> findElements(By by) {
		try {
			return _webElement.findElements(by);
		}
		catch (StaleElementReferenceException sere) {
			_refreshWebElement(sere);

			return _webElement.findElements(by);
		}
	}

	@Override
	public String getAttribute(String name) {
		try {
			return _webElement.getAttribute(name);
		}
		catch (StaleElementReferenceException sere) {
			_refreshWebElement(sere);

			return _webElement.getAttribute(name);
		}
	}

	@Override
	public Coordinates getCoordinates() {
		try {
			Locatable locatable = (Locatable)_webElement;

			return locatable.getCoordinates();
		}
		catch (StaleElementReferenceException sere) {
			_refreshWebElement(sere);

			Locatable locatable = (Locatable)_webElement;

			return locatable.getCoordinates();
		}
	}

	@Override
	public String getCssValue(String propertyName) {
		try {
			return _webElement.getCssValue(propertyName);
		}
		catch (StaleElementReferenceException sere) {
			_refreshWebElement(sere);

			return _webElement.getCssValue(propertyName);
		}
	}

	@Override
	public Point getLocation() {
		try {
			return _webElement.getLocation();
		}
		catch (StaleElementReferenceException sere) {
			_refreshWebElement(sere);

			return _webElement.getLocation();
		}
	}

	@Override
	public <X> X getScreenshotAs(OutputType<X> target)
		throws WebDriverException {

		try {
			return _webElement.getScreenshotAs(target);
		}
		catch (StaleElementReferenceException sere) {
			_refreshWebElement(sere);

			return _webElement.getScreenshotAs(target);
		}
	}

	@Override
	public Dimension getSize() {
		try {
			return _webElement.getSize();
		}
		catch (StaleElementReferenceException sere) {
			_refreshWebElement(sere);

			return _webElement.getSize();
		}
	}

	@Override
	public String getTagName() {
		try {
			return _webElement.getTagName();
		}
		catch (StaleElementReferenceException sere) {
			_refreshWebElement(sere);

			return _webElement.getTagName();
		}
	}

	@Override
	public String getText() {
		try {
			return _webElement.getText();
		}
		catch (StaleElementReferenceException sere) {
			_refreshWebElement(sere);

			return _webElement.getText();
		}
	}

	@Override
	public WebDriver getWrappedDriver() {
		return WebDriverUtil.getWebDriver();
	}

	@Override
	public boolean isDisplayed() {
		try {
			return _webElement.isDisplayed();
		}
		catch (StaleElementReferenceException sere) {
			_refreshWebElement(sere);

			return _webElement.isDisplayed();
		}
	}

	@Override
	public boolean isEnabled() {
		try {
			return _webElement.isEnabled();
		}
		catch (StaleElementReferenceException sere) {
			_refreshWebElement(sere);

			return _webElement.isEnabled();
		}
	}

	@Override
	public boolean isSelected() {
		try {
			return _webElement.isSelected();
		}
		catch (StaleElementReferenceException sere) {
			_refreshWebElement(sere);

			return _webElement.isSelected();
		}
	}

	public void sendKeys(CharSequence... keysToSend) {
		try {
			_webElement.sendKeys(keysToSend);
		}
		catch (StaleElementReferenceException sere) {
			_refreshWebElement(sere);

			_webElement.sendKeys(keysToSend);
		}
	}

	@Override
	public void submit() {
		try {
			_webElement.submit();
		}
		catch (StaleElementReferenceException sere) {
			_refreshWebElement(sere);

			_webElement.submit();
		}
	}

	private void _refreshWebElement(Throwable throwable) {
		System.out.println("\n" + throwable.getMessage());
		System.out.println(
			"\nWill retry command in " + _RETRY_WAIT_TIME + " seconds\n");

		try {
			Thread.sleep(_RETRY_WAIT_TIME * 1000);
		}
		catch (Exception e) {
		}

		WebDriver webDriver = WebDriverUtil.getWebDriver();

		_webElement = webDriver.findElement(WebDriverHelper.getBy(_locator));
	}

	private static final int _RETRY_WAIT_TIME = 3;

	private final String _locator;
	private WebElement _webElement;

}