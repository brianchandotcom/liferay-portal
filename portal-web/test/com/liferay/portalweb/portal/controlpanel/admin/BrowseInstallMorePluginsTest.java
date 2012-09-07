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

package com.liferay.portalweb.portal.controlpanel.admin;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class BrowseInstallMorePluginsTest extends BaseTestCase {
	public void testBrowseInstallMorePlugins() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Plugins Installation",
			RuntimeVariables.replace("Plugins Installation"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Install More Portlets']",
			RuntimeVariables.replace("Install More Portlets"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Portlet Plugins",
			RuntimeVariables.replace("Portlet Plugins"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("//input[@id='_111_keywords']"));
		assertTrue(selenium.isVisible("//input[@value='Search']"));
		selenium.clickAt("link=Theme Plugins",
			RuntimeVariables.replace("Theme Plugins"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("No theme plugins were found."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		selenium.clickAt("link=Layout Template Plugins",
			RuntimeVariables.replace("Layout Template Plugins"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"No layout template plugins were found."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		selenium.clickAt("link=Hook Plugins",
			RuntimeVariables.replace("Hook Plugins"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("No hook plugins were found."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		selenium.clickAt("link=Web Plugins",
			RuntimeVariables.replace("Web Plugins"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("No web plugins were found."),
			selenium.getText("//div[@class='portlet-msg-info']"));
	}
}