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

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.safari.SafariDriver;

/**
 * @author Brian Wing Shun Chan
 */
public class SafariWebDriverImpl extends BaseWebDriverImpl {

	public SafariWebDriverImpl(String projectDirName, String browserURL) {
		super(projectDirName, browserURL, new SafariDriver());
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

			StringBuilder sb = new StringBuilder();

			sb.append("confirm = function(){return true;};");

			try {
				javascriptExecutor.executeScript(sb.toString());

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
		WebElement webElement = getWebElement(locator);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		if (!webElement.isDisplayed()) {
			scrollWebElementIntoView(webElement);
		}

		StringBuilder sb = new StringBuilder();

		sb.append("var element = arguments[0];");
		sb.append("var event = document.createEvent('MouseEvents');");
		sb.append("event.initEvent('mousedown', true, false);");
		sb.append("element.dispatchEvent(event);");

		javascriptExecutor.executeScript(sb.toString(), webElement);
	}

	@Override
	public void mouseOver(String locator) {
		WebElement webElement = getWebElement(locator);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		if (!webElement.isDisplayed()) {
			scrollWebElementIntoView(webElement);
		}

		StringBuilder sb = new StringBuilder();

		sb.append("var element = arguments[0];");
		sb.append("var event = document.createEvent('MouseEvents');");
		sb.append("event.initEvent('mouseover', true, false);");
		sb.append("element.dispatchEvent(event);");

		javascriptExecutor.executeScript(sb.toString(), webElement);
	}

	@Override
	public void mouseUp(String locator) {
		WebElement webElement = getWebElement(locator);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		if (!webElement.isDisplayed()) {
			scrollWebElementIntoView(webElement);
		}

		StringBuilder sb = new StringBuilder();

		sb.append("var element = arguments[0];");
		sb.append("var event = document.createEvent('MouseEvents');");
		sb.append("event.initEvent('mouseup', true, false);");
		sb.append("event.initEvent('click', true, false);");
		sb.append("element.dispatchEvent(event)");

		javascriptExecutor.executeScript(sb.toString(), webElement);
	}

}