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

package com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.address.myaccount;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewAddress3MyAccountTest extends BaseTestCase {
	public void testViewAddress3MyAccount() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=My Account",
			RuntimeVariables.replace("My Account"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//a[@id='_2_addressesLink']");
		assertTrue(selenium.isPartialText("//a[@id='_2_addressesLink']",
				"Addresses"));
		selenium.clickAt("//a[@id='_2_addressesLink']",
			RuntimeVariables.replace("Addresses"));
		selenium.waitForVisible("//input[@id='_2_addressStreet1_0']");
		selenium.waitForSelectedLabel("//select[@id='_2_addressCountryId2']",
			"United States");
		selenium.waitForSelectedLabel("//select[@id='_2_addressRegionId2']",
			"California");
		assertEquals("1220 Brea Canyon Rd",
			selenium.getValue("//input[@id='_2_addressStreet1_2']"));
		assertEquals("Business",
			selenium.getSelectedLabel("//select[@id='_2_addressTypeId2']"));
		assertEquals("Ste 12",
			selenium.getValue("//input[@id='_2_addressStreet2_2']"));
		assertEquals("91789", selenium.getValue("//input[@id='_2_addressZip2']"));
		assertEquals("Walnut",
			selenium.getValue("//input[@id='_2_addressStreet3_2']"));
		assertEquals("Los Angeles",
			selenium.getValue("//input[@id='_2_addressCity2']"));
		assertEquals("United States",
			selenium.getSelectedLabel("//select[@id='_2_addressCountryId2']"));
		assertEquals("California",
			selenium.getSelectedLabel("//select[@id='_2_addressRegionId2']"));
	}
}