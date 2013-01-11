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

package com.liferay.portalweb.portal.permissions.announcements;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Guest_AssertViewTest extends BaseTestCase {
	public void testGuest_AssertView() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/site-name/");
		selenium.clickAt("link=Announcements Permissions Page",
			RuntimeVariables.replace("Announcements Permissions Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test General Announcement"),
			selenium.getText("xPath=(//h3[@class='entry-title']/a)[2]"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class=' entry-content entry-type-general'])[2]",
				"This is a test General Announcement."));
		assertFalse(selenium.isTextPresent("Test CA Announcement"));
		assertFalse(selenium.isTextPresent("Test Member Announcement"));
	}
}