/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.runner.selenium;

import com.liferay.poshi.runner.exception.ElementNotFoundPoshiRunnerException;

import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

/**
 * @author Brian Wing Shun Chan
 */
public class ChromeWebDriverImpl extends BaseWebDriverImpl {

	public ChromeWebDriverImpl(String browserURL, WebDriver webDriver) {
		super(browserURL, webDriver);
	}

	@Override
	public void click(String locator) {
		try {
			super.click(locator);
		}
		catch (WebDriverException webDriverException) {
			String message = webDriverException.getMessage();

			Matcher matcher = _elementNotClickableErrorPattern.matcher(message);

			if (matcher.find()) {
				javaScriptClick(locator);

				return;
			}

			throw new ElementNotInteractableException(
				message, webDriverException);
		}
	}

	@Override
	public void clickAt(String locator, String coordString) {
		try {
			super.clickAt(locator, coordString);
		}
		catch (WebDriverException webDriverException) {
			String message = webDriverException.getMessage();

			Matcher matcher = _elementNotClickableErrorPattern.matcher(message);

			if (matcher.find()) {
				javaScriptClick(locator);

				return;
			}

			throw new ElementNotInteractableException(
				message, webDriverException);
		}
	}

	@Override
	public String getSelectedLabel(String selectLocator, String timeout) {
		String text = super.getSelectedLabel(selectLocator, timeout);

		return text.trim();
	}

	@Override
	public String[] getSelectedLabels(String selectLocator) {
		String[] selectedLabels = super.getSelectedLabels(selectLocator);

		for (int i = 0; i < selectedLabels.length; i++) {
			selectedLabels[i] = selectedLabels[i].trim();
		}

		return selectedLabels;
	}

	@Override
	public String getText(String locator, String timeout) throws Exception {
		String text = super.getText(locator, timeout);

		return text.trim();
	}

	@Override
	public void typeKeys(String locator, String value) {
		try {
			super.typeKeys(locator, value);
		}
		catch (WebDriverException webDriverException) {
			String message = webDriverException.getMessage();

			Matcher matcher = _cannotFocusElementErrorPattern.matcher(message);

			if (matcher.find()) {
				click(locator);

				super.typeKeys(locator, value);

				return;
			}

			throw new ElementNotInteractableException(
				message, webDriverException);
		}
	}

	@Override
	protected WebElement getWebElement(String locator, String timeout) {
		try {
			return super.getWebElement(locator, timeout);
		}
		catch (ElementNotFoundPoshiRunnerException
					elementNotFoundPoshiRunnerException) {

			_refreshFrameWebElements();

			throw elementNotFoundPoshiRunnerException;
		}
	}

	@Override
	protected List<WebElement> getWebElements(String locator, String timeout) {
		List<WebElement> webElements = super.getWebElements(locator, timeout);

		if (webElements.isEmpty()) {
			_refreshFrameWebElements();
		}

		return webElements;
	}

	private void _refreshFrameWebElements() {
		Stack<WebElement> frameWebElements = getFrameWebElements();

		if (!frameWebElements.isEmpty() &&
			(frameWebElements.peek() instanceof RetryWebElementImpl)) {

			RetryWebElementImpl retryWebElementImpl =
				(RetryWebElementImpl)frameWebElements.peek();

			String frameWebElementLocator = retryWebElementImpl.getLocator();

			frameWebElements.pop();

			frameWebElements.push(getWebElement(frameWebElementLocator));

			WebDriver.TargetLocator targetLocator = switchTo();

			targetLocator.frame(frameWebElements.peek());
		}
	}

	private static final Pattern _cannotFocusElementErrorPattern =
		Pattern.compile("cannot focus element");
	private static final Pattern _elementNotClickableErrorPattern =
		Pattern.compile(
			"Element[\\s\\S]*is not clickable at point[\\s\\S]*" +
				"Other element would receive the click");

}