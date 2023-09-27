/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.runner.selenium;

import java.io.Serializable;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

/**
 * @author Calum Ragan
 */
public class LiferayByUtil {

	public static By cssSelectorWithShadowRoot(String selector) {
		return new ByCssSelectorWithShadowRoot(selector);
	}

	public static class ByCssSelectorWithShadowRoot
		extends By implements Serializable {

		public ByCssSelectorWithShadowRoot(String cssSelector) {
			if (cssSelector == null) {
				throw new IllegalArgumentException(
					"Unable to find elements when the selector is null");
			}

			_cssSelector = cssSelector;
		}

		@Override
		public WebElement findElement(SearchContext searchContext) {
			List<WebElement> webElements = findElements(searchContext);

			if (!webElements.isEmpty()) {
				return webElements.get(0);
			}

			throw new WebDriverException(
				"Unable to find element using CSS selector: " + _cssSelector);
		}

		@Override
		public List<WebElement> findElements(SearchContext searchContext) {
			if (searchContext instanceof WebDriver) {
				String[] partialCssSelectors = _cssSelector.split(">>>");

				for (int i = 0; i < (partialCssSelectors.length - 1); i++) {
					By.ByCssSelector byCssSelector = new By.ByCssSelector(
						partialCssSelectors[i]);

					WebElement webElement = byCssSelector.findElement(
						searchContext);

					searchContext = webElement.getShadowRoot();
				}

				return searchContext.findElements(
					By.cssSelector(
						partialCssSelectors[partialCssSelectors.length - 1]));
			}

			throw new WebDriverException(
				"Unable to find elements using CSS selector: " + _cssSelector);
		}

		@Override
		public String toString() {
			return "By.cssSelector: " + _cssSelector;
		}

		private final String _cssSelector;

	}

}