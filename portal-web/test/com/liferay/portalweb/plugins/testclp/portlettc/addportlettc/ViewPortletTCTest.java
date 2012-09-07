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

package com.liferay.portalweb.plugins.testclp.portlettc.addportlettc;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletTCTest extends BaseTestCase {
	public void testViewPortletTC() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Test CLP Test Page",
			RuntimeVariables.replace("Test CLP Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("//section"));
		assertTrue(selenium.isPartialText(
				"//div[@class='portlet-content']/div", "There are 1 statuses."));
		assertTrue(selenium.isPartialText(
				"//div[@class='portlet-content']/div",
				"NoSuchStatusException was properly caught."));
		assertEquals(RuntimeVariables.replace("Status ID"),
			selenium.getText("//tr[1]/td[1]/strong"));
		assertEquals(RuntimeVariables.replace("User ID"),
			selenium.getText("//tr[1]/td[2]/strong"));
		assertEquals(RuntimeVariables.replace("Modified Date"),
			selenium.getText("//tr[1]/td[3]/strong"));
		assertEquals(RuntimeVariables.replace("Online"),
			selenium.getText("//tr[1]/td[4]/strong"));
		assertEquals(RuntimeVariables.replace("Awake"),
			selenium.getText("//tr[1]/td[5]/strong"));
		assertEquals(RuntimeVariables.replace("Active Panel ID"),
			selenium.getText("//tr[1]/td[6]/strong"));
		assertEquals(RuntimeVariables.replace("Message"),
			selenium.getText("//tr[1]/td[7]/strong"));
		assertEquals(RuntimeVariables.replace("Play Sound"),
			selenium.getText("//tr[1]/td[8]/strong"));
		assertTrue(selenium.isVisible("//tr[2]/td[1]"));
		assertTrue(selenium.isVisible("//tr[2]/td[2]"));
		assertTrue(selenium.isVisible("//tr[2]/td[3]"));
		assertEquals(RuntimeVariables.replace("true"),
			selenium.getText("//tr[2]/td[4]"));
		assertEquals(RuntimeVariables.replace("true"),
			selenium.getText("//tr[2]/td[5]"));
		assertTrue(selenium.isVisible("//tr[2]/td[6]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText("//tr[2]/td[7]"));
		assertEquals(RuntimeVariables.replace("true"),
			selenium.getText("//tr[2]/td[8]"));
	}
}