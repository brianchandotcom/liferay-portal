/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.runner.selenium;

import com.liferay.poshi.core.PoshiProperties;
import com.liferay.poshi.core.util.OSDetector;
import com.liferay.poshi.core.util.Validator;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
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
			_clear();
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			_clear();
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
	public boolean equals(Object object) {
		try {
			return _remoteWebElement.equals(object);
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _remoteWebElement.equals(object);
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
	public String getAccessibleName() {
		try {
			return _webElement.getAccessibleName();
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _webElement.getAccessibleName();
		}
	}

	@Override
	public String getAriaRole() {
		try {
			return _webElement.getAriaRole();
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _webElement.getAriaRole();
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
	public String getDomAttribute(String name) {
		try {
			return _webElement.getDomAttribute(name);
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _webElement.getDomAttribute(name);
		}
	}

	@Override
	public String getDomProperty(String name) {
		try {
			return _webElement.getDomProperty(name);
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _webElement.getDomProperty(name);
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
	public Rectangle getRect() {
		try {
			return _webElement.getRect();
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _webElement.getRect();
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
	public SearchContext getShadowRoot() {
		try {
			return _webElement.getShadowRoot();
		}
		catch (StaleElementReferenceException staleElementReferenceException) {
			_refreshWebElement(staleElementReferenceException);

			return _webElement.getShadowRoot();
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
		return _remoteWebElement.getWrappedDriver();
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

	private void _clear() {
		CharSequence controlCharSequence = Keys.CONTROL;

		if (OSDetector.isApple() &&
			!(_remoteWebElement.getWrappedDriver() instanceof
				RemoteWebDriver)) {

			controlCharSequence = Keys.COMMAND;
		}

		_webElement.sendKeys(Keys.chord(controlCharSequence, "a", Keys.DELETE));

		String webElementValue = _webElement.getAttribute("value");

		if (Validator.isNull(webElementValue) || webElementValue.isEmpty()) {
			return;
		}

		_webElement.click();

		for (int i = 0; i < webElementValue.length(); i++) {
			_webElement.sendKeys(Keys.BACK_SPACE);
		}
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

		WebDriver webDriver = _remoteWebElement.getWrappedDriver();

		WebElement webElement = webDriver.findElement(
			LiferaySeleniumUtil.getBy(_locator));

		if (webElement == _webElement) {
			System.out.println("Unable to find a new web element");
		}
		else {
			System.out.println("Found a new web element");
		}

		_webElement = webElement;

		_remoteWebElement = (RemoteWebElement)_webElement;
	}

	private static final int _RETRY_WAIT_TIME;

	static {
		PoshiProperties poshiProperties = PoshiProperties.getPoshiProperties();

		_RETRY_WAIT_TIME = poshiProperties.testRetryCommandWaitTime;
	}

	private final String _locator;
	private RemoteWebElement _remoteWebElement;
	private WebElement _webElement;

}