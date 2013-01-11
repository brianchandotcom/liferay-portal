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

package com.liferay.portalweb.portal.tags.blogs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DeleteTagTest extends BaseTestCase {
	public void testDeleteTag() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Tags", RuntimeVariables.replace("Tags"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("xPath=(//span[@class='tag-item']/a)[2]");
		assertEquals(RuntimeVariables.replace("selenium2 liferay2"),
			selenium.getText("xPath=(//span[@class='tag-item']/a)[2]"));
		selenium.clickAt("xPath=(//span[@class='tag-item']/a)[2]",
			RuntimeVariables.replace("selenium2 liferay2"));
		selenium.waitForText("//h1[@class='header-title']/span",
			"selenium2 liferay2");
		assertEquals(RuntimeVariables.replace("selenium2 liferay2"),
			selenium.getText("//h1[@class='header-title']/span"));
		selenium.clickAt("//input[@value='Delete']",
			RuntimeVariables.replace("Delete"));
		selenium.waitForConfirmation(
			"Are you sure you want to delete this tag?");
		selenium.waitForTextNotPresent("selenium2 liferay2");
		assertFalse(selenium.isTextPresent("selenium2 liferay2"));
	}
}