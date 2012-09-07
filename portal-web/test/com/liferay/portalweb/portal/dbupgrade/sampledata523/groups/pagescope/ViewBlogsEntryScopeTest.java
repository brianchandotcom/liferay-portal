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

package com.liferay.portalweb.portal.dbupgrade.sampledata523.groups.pagescope;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewBlogsEntryScopeTest extends BaseTestCase {
	public void testViewBlogsEntryScope() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Communities",
			RuntimeVariables.replace("Communities"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_134_name']",
			RuntimeVariables.replace("Group Page Scope Community"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//tr[@class='portlet-section-body results-row']/td[1]/a",
			RuntimeVariables.replace("Public Pages"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Blogs Page Scope Current Page",
			RuntimeVariables.replace("Blogs Page Scope Current Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs Entry Scope Current Page"),
			selenium.getText("//div[@class='entry-title']/a"));
		assertEquals(RuntimeVariables.replace(
				"This is a blogs entry scope current page test."),
			selenium.getText("//p"));
		selenium.waitForElementPresent("link=Blogs Page Scope Default");
		selenium.clickAt("link=Blogs Page Scope Default",
			RuntimeVariables.replace("Blogs Page Scope Default"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent("Blogs Entry Scope Current Page"));
		assertTrue(selenium.isElementNotPresent("//div[@class='entry-title']/a"));
		assertFalse(selenium.isTextPresent(
				"This is a blogs entry scope current page test."));
		assertTrue(selenium.isElementNotPresent("//p"));
	}
}