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

package com.liferay.portalweb.portal.theme.setupthemes;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SetupThemesTest extends BaseTestCase {
	public void testSetupThemes() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.waitForElementPresent(
			"document.getElementById('my-community-private-pages')");
		selenium.click(RuntimeVariables.replace(
				"document.getElementById('my-community-private-pages')"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("//input[@value='Add Community']");
		selenium.click(RuntimeVariables.replace(
				"//input[@value='Add Community']"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("_29_name");
		selenium.type("_29_name", RuntimeVariables.replace("Themes Testing"));
		selenium.waitForElementPresent("_29_description");
		selenium.type("_29_description",
			RuntimeVariables.replace("Themes Testing Community"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Available Communities"));
		selenium.waitForPageToLoad("30000");
		selenium.click("//strong/span");
		selenium.click(RuntimeVariables.replace("//body/div[2]/ul/li[2]/a"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Export / Import"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("link=Import");
		selenium.click(RuntimeVariables.replace("link=Import"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("_29_importFileName");
		selenium.type("_29_importFileName",
			RuntimeVariables.replace(
				"L:\\portal\\build\\portal-web\\test\\com\\liferay\\portalweb\\portal\\theme\\setupthemes\\ThemesTestingSetup.lar"));
		selenium.waitForElementPresent("link=More Options \u00bb");
		selenium.click("link=More Options \u00bb");
		selenium.click("_29_DELETE_MISSING_LAYOUTSCheckbox");
		selenium.click("_29_DELETE_PORTLET_DATA");
		selenium.click("_29_mirror");
		selenium.click("_29_alwaysCurrentUserId");
		selenium.click(RuntimeVariables.replace("//input[@value='Import']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Pages"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Settings"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Virtual Host"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("_29_friendlyURL");
		selenium.type("_29_friendlyURL",
			RuntimeVariables.replace("/themetesting"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		selenium.open("web/themetesting/testpage06");
		selenium.waitForElementPresent("link=Add Comment");
		selenium.click("link=Add Comment");
		selenium.waitForElementPresent("_107_postReplyBody0");
		selenium.typeKeys("_107_postReplyBody0",
			RuntimeVariables.replace("Comment 1"));
		selenium.click(RuntimeVariables.replace("_107_postReplyButton0"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("link=Add Comment");
		selenium.click("link=Add Comment");
		selenium.waitForElementPresent("_107_postReplyBody0");
		selenium.typeKeys("_107_postReplyBody0",
			RuntimeVariables.replace("Comment 2"));
		selenium.click(RuntimeVariables.replace("_107_postReplyButton0"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent(
			"//tr[5]/td[2]/table[1]/tbody/tr/td[2]/span/a[2]");
		selenium.click("//tr[5]/td[2]/table[1]/tbody/tr/td[2]/span/a[2]");
		selenium.waitForElementPresent("_107_postReplyBody2");
		selenium.typeKeys("_107_postReplyBody2",
			RuntimeVariables.replace("Comment 3"));
		selenium.click(RuntimeVariables.replace("_107_postReplyButton2"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent(
			"//tr[5]/td[2]/table[1]/tbody/tr/td[2]/span/a[2]");
		selenium.click("//tr[5]/td[2]/table[1]/tbody/tr/td[2]/span/a[2]");
		selenium.waitForElementPresent("_107_postReplyBody2");
		selenium.typeKeys("_107_postReplyBody2",
			RuntimeVariables.replace("Comment 4"));
		selenium.click(RuntimeVariables.replace("_107_postReplyButton2"));
		selenium.waitForPageToLoad("30000");
	}
}