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

package com.liferay.portalweb.portal.dbupgrade.sampledata6012.announcements.announcementsentry;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddAnnouncementsEntryTest extends BaseTestCase {
	public void testAddAnnouncementsEntry() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Communities", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.type("_134_name",
			RuntimeVariables.replace("Announcements Entry Community"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//td[1]/a", RuntimeVariables.replace("Open"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("link=Announcements Entry Page");
		selenium.clickAt("link=Announcements Entry Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Manage Entries", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Entry']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.type("_84_title",
			RuntimeVariables.replace("Announcements Entry Name"));
		selenium.type("_84_url",
			RuntimeVariables.replace("http://www.liferay.com"));
		selenium.type("_84_content",
			RuntimeVariables.replace("Announcements Entry Content"));
		selenium.select("//select[@id='_84_displayDateMonth']",
			RuntimeVariables.replace("November"));
		selenium.select("//select[@id='_84_displayDateDay']",
			RuntimeVariables.replace("1"));
		selenium.select("//select[@id='_84_displayDateYear']",
			RuntimeVariables.replace("2011"));
		selenium.select("//select[@id='_84_expirationDateMonth']",
			RuntimeVariables.replace("December"));
		selenium.select("//select[@id='_84_expirationDateDay']",
			RuntimeVariables.replace("31"));
		selenium.select("//select[@id='_84_expirationDateYear']",
			RuntimeVariables.replace("2012"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Entries", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Announcements Entry Name"),
			selenium.getText("//div/h3/a"));
		assertTrue(selenium.isPartialText("//p", "Announcements Entry Content"));
		selenium.clickAt("//div/h3/a",
			RuntimeVariables.replace("Announcements Entry Name"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("//img[@alt='Liferay']"));
	}
}