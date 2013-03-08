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

package com.liferay.portalweb.permissions.wcwebcontentdisplay.wcwebcontent.viewwcwebcontent.guestinline;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Guest_ViewWCWebContentWCDTest extends BaseTestCase {
	public void testGuest_ViewWCWebContentWCD() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Web Content Display Test Page",
			RuntimeVariables.replace("Web Content Display Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		assertTrue(selenium.isElementNotPresent(
				"//span[@title='Options']/ul/li/strong/a"));
		assertTrue(selenium.isElementNotPresent(
				"//span[@class='portlet-minimize portlet-minimize-icon']/a"));
		assertTrue(selenium.isElementNotPresent(
				"//span[@class='portlet-maximize portlet-maximize-icon']/a"));
		assertTrue(selenium.isElementNotPresent(
				"//span[@class='portlet-close portlet-portlet-icon']/a"));
		assertEquals(RuntimeVariables.replace("WC WebContent Content"),
			selenium.getText("//div[@class='journal-content-article']"));
		assertTrue(selenium.isElementNotPresent(
				"//span[@class='icon-action icon-action-edit']/a"));
		assertTrue(selenium.isElementNotPresent(
				"//span[@class='icon-action icon-action-configuration']/a"));
		assertTrue(selenium.isElementNotPresent(
				"//span[@class='icon-action icon-action-add']/a"));
	}
}