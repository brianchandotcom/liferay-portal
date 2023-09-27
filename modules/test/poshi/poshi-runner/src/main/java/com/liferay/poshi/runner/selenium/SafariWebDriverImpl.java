/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.runner.selenium;

import org.openqa.selenium.ElementNotInteractableException;
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
			catch (ElementNotInteractableException
						elementNotInteractableException) {

				if (isVisible(locator)) {
					javaScriptClick(locator);

					return;
				}

				throw elementNotInteractableException;
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

		if (webElement.isDisplayed() || !isObscured(webElement)) {
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