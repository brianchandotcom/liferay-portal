/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.util.liferayselenium;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portalweb.portal.util.TestPropsValues;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseWebDriverImpl
	extends WebDriverToSeleniumBridge implements LiferaySelenium {

	public BaseWebDriverImpl(
		String projectDir, String browserURL, WebDriver webDriver) {

		super(webDriver);

		_projectDir = projectDir;

		webDriver.get(browserURL);
	}

	public void downloadTempFile(String value) {
		LiferaySeleniumHelper.downloadTempFile(value);
	}

	public String getCurrentDay() {
		Calendar calendar = Calendar.getInstance();

		return StringUtil.valueOf(calendar.get(Calendar.DATE));
	}

	public String getCurrentMonth() {
		Calendar calendar = Calendar.getInstance();

		return StringUtil.valueOf(calendar.get(Calendar.MONTH) + 1);
	}

	public String getCurrentYear() {
		Calendar calendar = Calendar.getInstance();

		return StringUtil.valueOf(calendar.get(Calendar.YEAR));
	}

	public String getFirstNumber(String locator) {
		String text = getWebElement(locator).getText();

		if (text == null) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler();

		char[] chars = text.toCharArray();

		for (char c : chars) {
			boolean digit = false;

			if (Validator.isDigit(c)) {
				sb.append(c);

				digit = true;
			}

			String s = sb.toString();

			if (Validator.isNotNull(s) && !digit) {
				return s;
			}
		}

		return sb.toString();
	}

	public String getFirstNumberIncrement(String locator) {
		String firstNumber = getFirstNumber(locator);

		return StringUtil.valueOf(GetterUtil.getInteger(firstNumber) + 1);
	}

	public boolean isElementNotPresent(String locator) {
		WebDriver.Options options = manage();

		Timeouts timeouts = options.timeouts();

		timeouts.implicitlyWait(3, TimeUnit.MILLISECONDS);

		List<WebElement> webElements = getWebElements(locator);

		timeouts.implicitlyWait(30, TimeUnit.MILLISECONDS);

		return webElements.isEmpty();
	}

	public boolean isPartialText(String locator, String value) {
		WebElement webElement = getWebElement(locator);

		String text = webElement.getText();

		return text.contains(value);
	}

	public void saveScreenShotAndSource() throws Exception {
	}

	public void setBrowserOption() {
		LiferaySeleniumHelper.setBrowserOption();
	}

	public void uploadCommonFile(String location, String value) {
		uploadFile(
			location,
			_projectDir + "portal-web\\test\\com\\liferay\\portalweb\\" +
				"dependencies\\" + value);
	}

	public void uploadTempFile(String location, String value) {
		uploadFile(location, TestPropsValues.OUTPUT_DIR + value);
	}

	protected void uploadFile(String location, String value) {
	}

	private String _projectDir;

}