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

package com.liferay.portalweb.portlet.shopping.item.guestviewpermissionsshoppingitemguestviewoff;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Guest_ViewShoppingItemTest extends BaseTestCase {
	public void testGuest_ViewShoppingItem() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Shopping Test Page",
			RuntimeVariables.replace("Shopping Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("1111"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[1]/a"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row last']/td[2]/a",
				"Shopping Item Name"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row last']/td[2]/a",
				"Shopping Item Description"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row last']/td[2]/a",
				"Shopping: Item Properties"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[3]/a"));
		assertEquals(RuntimeVariables.replace("$9.99"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[4]/a"));
		selenium.clickAt("//tr[@class='portlet-section-body results-row last']/td[2]/a",
			RuntimeVariables.replace("Shopping Item Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Item"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("1111"),
			selenium.getText("//td[@class='lfr-top']/strong"));
		assertEquals(RuntimeVariables.replace("Shopping Item Name"),
			selenium.getText("//td[@class='lfr-top']/span/strong"));
		assertTrue(selenium.isPartialText("xPath=(//td[@class='lfr-top'])[2]",
				"Shopping Item Description"));
		assertTrue(selenium.isPartialText("xPath=(//td[@class='lfr-top'])[2]",
				"Shopping: Item Properties"));
		assertTrue(selenium.isPartialText("xPath=(//td[@class='lfr-top'])[2]",
				"Availability:"));
		assertEquals(RuntimeVariables.replace("In Stock"),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isPartialText("xPath=(//td[@class='lfr-top'])[2]",
				"Price for 1 to 1 Items"));
		assertTrue(selenium.isPartialText("xPath=(//td[@class='lfr-top'])[2]",
				"9.99"));
	}
}