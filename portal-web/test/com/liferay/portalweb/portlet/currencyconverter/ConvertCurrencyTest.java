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
public class ConvertCurrencyTest extends BaseTestCase {
	public void testConvertCurrency() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Currency Converter Test Page");
		selenium.clickAt("link=Currency Converter Test Page",
			RuntimeVariables.replace("Currency Converter Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("//input[@name='_16_number']");
		selenium.type("//input[@name='_16_number']",
			RuntimeVariables.replace("2.5"));
		selenium.waitForPartialText("//select[@name='_16_from']", "KRW");
		selenium.select("//select[@name='_16_from']",
			RuntimeVariables.replace("KRW"));
		assertTrue(selenium.isPartialText("//select[@name='_16_to']", "BHD"));
		selenium.select("//select[@name='_16_to']",
			RuntimeVariables.replace("BHD"));
		selenium.clickAt("//input[@value='Convert']",
			RuntimeVariables.replace("Convert"));
		selenium.waitForPageToLoad("30000");
		assertEquals("2.5", selenium.getValue("//input[@name='_16_number']"));
		assertTrue(selenium.isTextPresent("KRW"));
		assertTrue(selenium.isTextPresent("BHD"));
		assertTrue(selenium.isVisible("//td/img"));
	}
}