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

package com.liferay.portalweb.portlet.wiki.wikinode.addwikinodenamenumber;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWikiNodeNameNumberTest extends BaseTestCase {
	public void testAddWikiNodeNameNumber() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Wiki"),
			selenium.getText("//ul[@class='category-portlets']/li[11]/a"));
		selenium.clickAt("//ul[@class='category-portlets']/li[11]/a",
			RuntimeVariables.replace("Wiki"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Wiki']",
			RuntimeVariables.replace("Add Wiki"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_154_name']",
			RuntimeVariables.replace("Wiki1 Node1 Name1"));
		selenium.type("//textarea[@id='_154_description']",
			RuntimeVariables.replace("Wiki1 Node1 Description1"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Wiki1 Node1 Name1"),
			selenium.getText("//tr[contains(.,'Wiki1 Node1 Name1')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//tr[contains(.,'Wiki1 Node1 Name1')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Never"),
			selenium.getText("//tr[contains(.,'Wiki1 Node1 Name1')]/td[3]/a"));
	}
}