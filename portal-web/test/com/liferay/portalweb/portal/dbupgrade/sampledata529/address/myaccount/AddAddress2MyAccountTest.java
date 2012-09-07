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

package com.liferay.portalweb.portal.dbupgrade.sampledata529.address.myaccount;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddAddress2MyAccountTest extends BaseTestCase {
	public void testAddAddress2MyAccount() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=My Account", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("addressesLink", RuntimeVariables.replace(""));
		selenium.waitForVisible("//div[2]/div/span/a[1]");
		selenium.clickAt("//div[2]/div/span/a[1]", RuntimeVariables.replace(""));
		selenium.waitForVisible("_2_addressStreet1_2");
		selenium.type("_2_addressStreet1_2",
			RuntimeVariables.replace("123 Lets"));
		selenium.select("_2_addressTypeId2",
			RuntimeVariables.replace("label=Other"));
		selenium.type("_2_addressStreet2_2",
			RuntimeVariables.replace("897 Hope"));
		selenium.type("_2_addressZip2", RuntimeVariables.replace("00000"));
		selenium.type("_2_addressStreet3_2",
			RuntimeVariables.replace("7896 This"));
		selenium.type("_2_addressCity2", RuntimeVariables.replace("Works"));
		selenium.waitForPartialText("_2_addressCountryId2", "Canada");
		selenium.select("_2_addressCountryId2",
			RuntimeVariables.replace("label=Canada"));
		selenium.waitForPartialText("_2_addressRegionId2", "Ontario");
		selenium.select("_2_addressRegionId2",
			RuntimeVariables.replace("label=Ontario"));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.waitForSelectedLabel("_2_addressCountryId1", "Canada");
		selenium.waitForSelectedLabel("_2_addressRegionId1", "Ontario");
		assertEquals("123 Lets", selenium.getValue("_2_addressStreet1_1"));
		assertEquals("Other", selenium.getSelectedLabel("_2_addressTypeId1"));
		assertEquals("897 Hope", selenium.getValue("_2_addressStreet2_1"));
		assertEquals("00000", selenium.getValue("_2_addressZip1"));
		assertEquals("7896 This", selenium.getValue("_2_addressStreet3_1"));
		assertEquals("Works", selenium.getValue("_2_addressCity1"));
		assertEquals("Canada", selenium.getSelectedLabel("_2_addressCountryId1"));
		assertEquals("Ontario", selenium.getSelectedLabel("_2_addressRegionId1"));
	}
}