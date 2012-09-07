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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.expireeditwcwebcontentactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewExpireEditWCWebContentActionsTest extends BaseTestCase {
	public void testViewExpireEditWCWebContentActions()
		throws Exception {
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
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");

		String webContentID = selenium.getText("//td[2]/a");
		RuntimeVariables.setValue("webContentID", webContentID);
		assertEquals(RuntimeVariables.replace("${webContentID}"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title Edit"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("Expired"),
			selenium.getText("//td[4]/a"));
		assertTrue(selenium.isVisible("//td[5]/a"));
		assertTrue(selenium.isVisible("//td[6]/a"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//td[7]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//td[8]/span/ul/li/strong/a"));
		selenium.clickAt("//td[3]/a",
			RuntimeVariables.replace("WC WebContent Title Edit"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Version: 1.1"),
			selenium.getText("//span[@class='workflow-version']"));
		assertEquals(RuntimeVariables.replace("Status: Expired"),
			selenium.getText("//span[@class='workflow-status']"));
		assertEquals(RuntimeVariables.replace("View History"),
			selenium.getText(
				"//div[@id='_15_articleToolbar']/span/button[3]/span[2]"));
		selenium.clickAt("//div[@id='_15_articleToolbar']/span/button[3]/span[2]",
			RuntimeVariables.replace("View History"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC WebContent Title Edit"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("${webContentID}"),
			selenium.getText("//tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//tr[3]/td[3]"));
		assertEquals(RuntimeVariables.replace("1.0"),
			selenium.getText("//tr[3]/td[4]"));
		assertEquals(RuntimeVariables.replace("Expired"),
			selenium.getText("//tr[3]/td[5]"));
		assertTrue(selenium.isVisible("//tr[3]/td[6]"));
		assertTrue(selenium.isVisible("//tr[3]/td[7]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//tr[3]/td[8]"));
		assertEquals(RuntimeVariables.replace("${webContentID}"),
			selenium.getText("//tr[4]/td[2]"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title Edit"),
			selenium.getText("//tr[4]/td[3]"));
		assertEquals(RuntimeVariables.replace("1.1"),
			selenium.getText("//tr[4]/td[4]"));
		assertEquals(RuntimeVariables.replace("Expired"),
			selenium.getText("//tr[4]/td[5]"));
		assertTrue(selenium.isVisible("//tr[4]/td[6]"));
		assertTrue(selenium.isVisible("//tr[4]/td[7]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//tr[4]/td[8]"));
	}
}