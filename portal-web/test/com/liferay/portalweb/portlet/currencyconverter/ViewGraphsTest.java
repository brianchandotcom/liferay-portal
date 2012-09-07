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

package com.liferay.portalweb.portlet.currencyconverter;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewGraphsTest extends BaseTestCase {
	public void testViewGraphs() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Currency Converter Test Page");
		selenium.clickAt("link=Currency Converter Test Page",
			RuntimeVariables.replace("Currency Converter Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("//input[@value='Convert']");
		selenium.clickAt("//input[@value='Convert']",
			RuntimeVariables.replace("Convert"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=1y", RuntimeVariables.replace("1y"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("//td/img"));
		selenium.clickAt("link=2y", RuntimeVariables.replace("2y"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("//td/img"));
		selenium.clickAt("link=3m", RuntimeVariables.replace("3m"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("//td/img"));
	}
}