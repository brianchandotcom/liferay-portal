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

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;

/**
 * @author Brian Wing Shun Chan
 */
public class SafariWebDriverImpl extends BaseWebDriverImpl {

	public SafariWebDriverImpl(String browserURL, WebDriver webDriver) {
		super(browserURL, webDriver);
	}

	@Override
	public void assertConfirmation(String pattern) throws Exception {
	}

	@Override
	public void click(String locator) {
		if (locator.contains("x:")) {
			String url = getHtmlNodeHref(locator);

			open(url);
		}
		else {
			WebElement webElement = getWebElement(locator);

			WrapsDriver wrapsDriver = (WrapsDriver)webElement;

			WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

			JavascriptExecutor javascriptExecutor =
				(JavascriptExecutor)wrappedWebDriver;

			try {
				javascriptExecutor.executeScript(
					"confirm = function(){return true;};");

				javaScriptClick(locator);
			}
			catch (ElementNotVisibleException elementNotVisibleException) {
				if (isVisible(locator)) {
					javaScriptClick(locator);

					return;
				}

				throw elementNotVisibleException;
			}
		}
	}

	@Override
	public String getText(String locator, String timeout) throws Exception {
		return javaScriptGetText(locator, timeout);
	}

	@Override
	public boolean isVisible(String locator) {
		WebElement webElement = getWebElement(locator, "1");

		scrollWebElementIntoView(webElement);

		if (webElement.isDisplayed()) {
			return true;
		}

		if (!isObscured(webElement)) {
			return true;
		}

		return false;
	}

	@Override
	public void mouseDown(String locator) {
		executeJavaScriptEvent(locator, "MouseEvent", "mousedown");
	}

	@Override
	public void mouseOver(String locator) {
		executeJavaScriptEvent(locator, "MouseEvent", "mouseover");
	}

	@Override
	public void mouseUp(String locator) {
		executeJavaScriptEvent(locator, "MouseEvent", "mouseup");

		executeJavaScriptEvent(locator, "MouseEvent", "click");
	}

}