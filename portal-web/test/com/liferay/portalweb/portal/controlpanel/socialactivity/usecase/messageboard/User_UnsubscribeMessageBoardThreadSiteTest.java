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

package com.liferay.portalweb.portal.controlpanel.socialactivity.usecase.messageboard;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_UnsubscribeMessageBoardThreadSiteTest extends BaseTestCase {
	public void testUser_UnsubscribeMessageBoardThreadSite()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/site-name/");
		selenium.waitForVisible("link=Message Boards Test Page");
		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementNotPresent("//body[contains(@id,'aui')]");
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//a[@id='_19_mbThreadsSearchContainer_1_menu_unsubscribe']");
		assertEquals(RuntimeVariables.replace("Unsubscribe"),
			selenium.getText(
				"//a[@id='_19_mbThreadsSearchContainer_1_menu_unsubscribe']"));
		selenium.clickAt("//a[@id='_19_mbThreadsSearchContainer_1_menu_unsubscribe']",
			RuntimeVariables.replace("Unsubscribe"));
		selenium.waitForPageToLoad("30000");
	}
}