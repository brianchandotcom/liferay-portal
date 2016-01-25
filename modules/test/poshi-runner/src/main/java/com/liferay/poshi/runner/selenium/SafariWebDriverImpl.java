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

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;

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

				webElement.click();
			}
			catch (Exception e) {
				if (!webElement.isDisplayed()) {
					scrollWebElementIntoView(webElement);
				}

				webElement.click();
			}
		}
	}

	@Override
	public void mouseDown(String locator) {
		WebDriverHelper.executeJavaScriptEvent(
			this, locator, "MouseEvent", "mousedown");
	}

	@Override
	public void mouseOver(String locator) {
		WebDriverHelper.executeJavaScriptEvent(
			this, locator, "MouseEvent", "mouseover");
	}

	@Override
	public void mouseUp(String locator) {
		WebDriverHelper.executeJavaScriptEvent(
			this, locator, "MouseEvent", "mouseup");

		WebDriverHelper.executeJavaScriptEvent(
			this, locator, "MouseEvent", "click");
	}

}