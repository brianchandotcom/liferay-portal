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

import java.io.Serializable;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
				JavascriptExecutor javascriptExecutor =
					(JavascriptExecutor)searchContext;

				String[] partialCssSelectors = _cssSelector.split(">>>");

				for (int i = 0; i < (partialCssSelectors.length - 1); i++) {
					By.ByCssSelector byCssSelector = new By.ByCssSelector(
						partialCssSelectors[i]);

					WebElement webElement = byCssSelector.findElement(
						searchContext);

					searchContext =
						(WebElement)javascriptExecutor.executeScript(
							"return arguments[0].shadowRoot", webElement);
				}

				By.ByCssSelector byCssSelector = new By.ByCssSelector(
					partialCssSelectors[partialCssSelectors.length - 1]);

				return byCssSelector.findElements(searchContext);
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