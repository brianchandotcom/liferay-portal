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

package com.liferay.portalweb.socialofficehome.navigation.links.viewlinkhome;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewLinkHomeTest extends BaseTestCase {
	public void testViewLinkHome() throws Exception {
		selenium.open("/user/joebloggs/so/dashboard/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//nav/ul/li[contains(.,'Home')]/a/span")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText("//li[contains(@class, 'selected')]/a/span"));
		assertEquals(RuntimeVariables.replace("Microblogs Status Update"),
			selenium.getText(
				"xPath=(//span[@class='portlet-title-default'])[1]"));
		assertEquals(RuntimeVariables.replace("Update your status..."),
			selenium.getText(
				"//div[@id='_2_WAR_microblogsportlet_autocompleteContent']"));
		assertEquals(RuntimeVariables.replace("You have no microblogs entry."),
			selenium.getText("xPath=(//div[@class='portlet-msg-info'])[1]"));
		assertEquals(RuntimeVariables.replace("Activities"),
			selenium.getText("xPath=(//h1[@class='portlet-title'])[2]"));
		assertEquals(RuntimeVariables.replace("There are no recent activities."),
			selenium.getText("xPath=(//div[@class='portlet-msg-info'])[2]"));
		assertEquals(RuntimeVariables.replace("Connections"),
			selenium.getText("link=Connections"));
		assertEquals(RuntimeVariables.replace("Following"),
			selenium.getText("link=Following"));
		assertEquals(RuntimeVariables.replace("My Sites"),
			selenium.getText("link=My Sites"));
		assertEquals(RuntimeVariables.replace("Me"), selenium.getText("link=Me"));
		assertEquals(RuntimeVariables.replace("Upcoming Tasks"),
			selenium.getText("xPath=(//h1[@class='portlet-title'])[3]"));
		assertEquals(RuntimeVariables.replace("View All Tasks"),
			selenium.getText("//div[@class='view-all-tasks']"));
		assertEquals(RuntimeVariables.replace("Events"),
			selenium.getText("xPath=(//h1[@class='portlet-title'])[4]"));
		assertEquals(RuntimeVariables.replace("There are no more events today."),
			selenium.getText("//div[2]/div/div[2]/div/section/div/div/div"));
		assertEquals(RuntimeVariables.replace("Sites"),
			selenium.getText("//div[@id='so-sidebar']/h3"));
		assertTrue(selenium.isVisible("//div/input[1]"));
		assertEquals(RuntimeVariables.replace("Liferay"),
			selenium.getText("//li[3]/span[2]"));
	}
}