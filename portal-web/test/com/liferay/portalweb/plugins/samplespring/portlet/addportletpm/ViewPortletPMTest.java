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

package com.liferay.portalweb.plugins.samplespring.portlet.addportletpm;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletPMTest extends BaseTestCase {
	public void testViewPortletPM() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Portlet Modes Test Page",
			RuntimeVariables.replace("Portlet Modes Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("View Mode"),
			selenium.getText("//div[@class='portlet-body']/h1"));
		assertEquals(RuntimeVariables.replace(
				"This portlet simply displays a different view depending on the mode of the portlet."),
			selenium.getText("xPath=(//div[@class='portlet-body']/p)[1]"));
		assertEquals(RuntimeVariables.replace(
				"You can switch modes using the controls in your portal. You can also experiment with the URLs below for changing mode and window state."),
			selenium.getText("xPath=(//div[@class='portlet-body']/p)[2]"));
		assertEquals(RuntimeVariables.replace("Edit Mode"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/ul/li/a)[contains(.,'Edit Mode')]"));
		selenium.clickAt("xPath=(//div[@class='portlet-body']/ul/li/a)[contains(.,'Edit Mode')]",
			RuntimeVariables.replace("Edit Mode"));
		selenium.waitForText("//div[@class='portlet-body']/h1", "Edit Mode");
		assertEquals(RuntimeVariables.replace("Edit Mode"),
			selenium.getText("//div[@class='portlet-body']/h1"));
		assertEquals(RuntimeVariables.replace("Return to Full Page"),
			selenium.getText("//a[@class='portlet-icon-back']"));
		assertEquals(RuntimeVariables.replace("Help Mode"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/ul/li/a)[contains(.,'Help Mode')]"));
		selenium.clickAt("xPath=(//div[@class='portlet-body']/ul/li/a)[contains(.,'Help Mode')]",
			RuntimeVariables.replace("Help Mode"));
		selenium.waitForText("//div[@class='portlet-body']/h1", "Help Mode");
		assertEquals(RuntimeVariables.replace("Help Mode"),
			selenium.getText("//div[@class='portlet-body']/h1"));
		assertEquals(RuntimeVariables.replace("Return to Full Page"),
			selenium.getText("//a[@class='portlet-icon-back']"));
		assertEquals(RuntimeVariables.replace("View Mode"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/ul/li/a)[contains(.,'View Mode')]"));
		selenium.clickAt("xPath=(//div[@class='portlet-body']/ul/li/a)[contains(.,'View Mode')]",
			RuntimeVariables.replace("View Mode"));
		selenium.waitForText("//div[@class='portlet-body']/h1", "View Mode");
		assertEquals(RuntimeVariables.replace("View Mode"),
			selenium.getText("//div[@class='portlet-body']/h1"));
		assertFalse(selenium.isTextPresent("Return to Full Page"));
		assertEquals(RuntimeVariables.replace("Maximized State"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/ul/li/a)[contains(.,'Maximized State')]"));
		selenium.clickAt("xPath=(//div[@class='portlet-body']/ul/li/a)[contains(.,'Maximized State')]",
			RuntimeVariables.replace("Maximized State"));
		selenium.waitForVisible(
			"//body[@class='blue yui3-skin-sam controls-visible page-maximized guest-site signed-in public-page site dockbar-ready']");
		assertTrue(selenium.isVisible(
				"//body[@class='blue yui3-skin-sam controls-visible page-maximized guest-site signed-in public-page site dockbar-ready']"));
		assertEquals(RuntimeVariables.replace("Normal State"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/ul/li/a)[contains(.,'Normal State')]"));
		selenium.clickAt("xPath=(//div[@class='portlet-body']/ul/li/a)[contains(.,'Normal State')]",
			RuntimeVariables.replace("Normal State"));
		selenium.waitForVisible(
			"//body[@class='blue yui3-skin-sam controls-visible guest-site signed-in public-page site dockbar-ready']");
		assertTrue(selenium.isVisible(
				"//body[@class='blue yui3-skin-sam controls-visible guest-site signed-in public-page site dockbar-ready']"));
		assertEquals(RuntimeVariables.replace("Minimized State"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/ul/li/a)[contains(.,'Minimized State')]"));
		selenium.clickAt("xPath=(//div[@class='portlet-body']/ul/li/a)[contains(.,'Minimized State')]",
			RuntimeVariables.replace("Minimized State"));
		selenium.waitForNotVisible("//div[@class='portlet-body']/h1");
		assertFalse(selenium.isVisible("//div[@class='portlet-body']/h1"));
		assertFalse(selenium.isVisible(
				"xPath=(//div[@class='portlet-body']/p)[1]"));
		assertFalse(selenium.isVisible(
				"xPath=(//div[@class='portlet-body']/p)[2]"));
		assertFalse(selenium.isVisible(
				"xPath=(//div[@class='portlet-body']/ul/li/a)[contains(.,'View Mode')]"));
		assertFalse(selenium.isVisible(
				"xPath=(//div[@class='portlet-body']/ul/li/a)[contains(.,'Edit Mode')]"));
		assertFalse(selenium.isVisible(
				"xPath=(//div[@class='portlet-body']/ul/li/a)[contains(.,'Help Mode')]"));
		assertFalse(selenium.isVisible(
				"xPath=(//div[@class='portlet-body']/ul/li/a)[contains(.,'Normal State')]"));
		assertFalse(selenium.isVisible(
				"xPath=(//div[@class='portlet-body']/ul/li/a)[contains(.,'Maximized State')]"));
		assertFalse(selenium.isVisible(
				"xPath=(//div[@class='portlet-body']/ul/li/a)[contains(.,'Minimized State')]"));
		assertTrue(selenium.isVisible("//img[@title='Restore']"));
		selenium.clickAt("//img[@title='Restore']",
			RuntimeVariables.replace("Restore"));
		selenium.waitForVisible("//div[@class='portlet-body']/h1");
		assertTrue(selenium.isVisible("//div[@class='portlet-body']/h1"));
		assertTrue(selenium.isVisible(
				"xPath=(//div[@class='portlet-body']/p)[1]"));
		assertTrue(selenium.isVisible(
				"xPath=(//div[@class='portlet-body']/p)[2]"));
		assertTrue(selenium.isVisible(
				"xPath=(//div[@class='portlet-body']/ul/li/a)[contains(.,'View Mode')]"));
		assertTrue(selenium.isVisible(
				"xPath=(//div[@class='portlet-body']/ul/li/a)[contains(.,'Edit Mode')]"));
		assertTrue(selenium.isVisible(
				"xPath=(//div[@class='portlet-body']/ul/li/a)[contains(.,'Help Mode')]"));
		assertTrue(selenium.isVisible(
				"xPath=(//div[@class='portlet-body']/ul/li/a)[contains(.,'Normal State')]"));
		assertTrue(selenium.isVisible(
				"xPath=(//div[@class='portlet-body']/ul/li/a)[contains(.,'Maximized State')]"));
		assertTrue(selenium.isVisible(
				"xPath=(//div[@class='portlet-body']/ul/li/a)[contains(.,'Minimized State')]"));
	}
}