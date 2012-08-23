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

import com.thoughtworks.selenium.Selenium;

/**
 * @author Brian Wing Shun Chan
 */
public interface LiferaySelenium extends Selenium {

	public void downloadTempFile(String value);

	public String getCurrentDay();

	public String getCurrentMonth();

	public String getCurrentYear();

	public String getFirstNumber(String locator);

	public String getFirstNumberIncrement(String locator);

	public boolean isElementNotPresent(String locator);

	public boolean isPartialText(String locator, String value);

	public void saveScreenShotAndSource() throws Exception;

	public void setBrowserOption();

	public void uploadCommonFile(String locator, String value);

	public void uploadFile(String locator, String value);

	public void uploadTempFile(String locator, String value);

}