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

package com.liferay.portalweb.socialofficehome.microblogs.mbentry.viewmicroblogstimeline;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewMicroblogsTimelineTest extends BaseTestCase {
	public void testViewMicroblogsTimeline() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/so/dashboard/");
		selenium.clickAt("//nav/ul/li[contains(.,'Microblogs')]/a/span",
			RuntimeVariables.replace("Microblogs"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Timeline"),
			selenium.getText("link=Timeline"));
		selenium.clickAt("link=Timeline", RuntimeVariables.replace("Timeline"));
		selenium.waitForVisible("xPath=(//div[@class='content'])[1]");
		selenium.waitForVisible("xPath=(//div[@class='content'])[2]");
		selenium.waitForVisible("xPath=(//div[@class='content'])[3]");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Joe Bloggs says"),
			selenium.getText("xPath=(//div[@class='user-name'])[1]"));
		assertEquals(RuntimeVariables.replace("Microblogs Post"),
			selenium.getText("xPath=(//div[@class='content'])[1]"));
		assertEquals(RuntimeVariables.replace("Comment"),
			selenium.getText("xPath=(//span[@class='action comment']/a)[1]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs says"),
			selenium.getText("xPath=(//div[@class='user-name'])[2]"));
		assertEquals(RuntimeVariables.replace("Microblogs Post"),
			selenium.getText("xPath=(//div[@class='content'])[2]"));
		assertEquals(RuntimeVariables.replace("Comment"),
			selenium.getText("xPath=(//span[@class='action comment']/a)[2]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs says"),
			selenium.getText("xPath=(//div[@class='user-name'])[3]"));
		assertEquals(RuntimeVariables.replace("Microblogs Post"),
			selenium.getText("xPath=(//div[@class='content'])[3]"));
		assertEquals(RuntimeVariables.replace("Comment"),
			selenium.getText("xPath=(//span[@class='action comment']/a)[3]"));
	}
}