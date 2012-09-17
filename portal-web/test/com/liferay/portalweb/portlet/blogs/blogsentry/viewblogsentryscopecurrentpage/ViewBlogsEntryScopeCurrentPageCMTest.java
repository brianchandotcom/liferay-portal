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

package com.liferay.portalweb.portlet.blogs.blogsentry.viewblogsentryscopecurrentpage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewBlogsEntryScopeCurrentPageCMTest extends BaseTestCase {
	public void testViewBlogsEntryScopeCurrentPageCM()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Manage"),
			selenium.getText("//li[@id='_145_manageContent']/a/span"));
		selenium.mouseOver("//li[@id='_145_manageContent']/a/span");
		selenium.waitForVisible("link=Site Content");
		selenium.clickAt("link=Site Content",
			RuntimeVariables.replace("Site Content"));
		selenium.waitForVisible("//iframe[@id='manageContentDialog']");
		selenium.selectFrame("//iframe[@id='manageContentDialog']");
		selenium.waitForVisible("link=Blogs");
		selenium.clickAt("link=Blogs", RuntimeVariables.replace("Blogs"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("No entries were found."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		selenium.mouseOver("//span[@title='Scope: Default']/ul/li/strong/a");
		selenium.clickAt("//span[@title='Scope: Default']/ul/li/strong/a",
			RuntimeVariables.replace("Scope: Default"));
		selenium.waitForVisible("//ul[contains(@class,'aui-state-active')]");
		assertEquals(RuntimeVariables.replace("Blogs Test Page"),
			selenium.getText("//a[contains(@id,'menu_blogs-test-page')]"));
		selenium.clickAt("//a[contains(@id,'menu_blogs-test-page')]",
			RuntimeVariables.replace("Blogs Test Page"));
		selenium.waitForPartialText("//span[@title='Scope: Blogs Test Page']/ul/li/strong/a",
			"Scope: Blogs Test Page");
		selenium.clickAt("link=Blogs", RuntimeVariables.replace("Blogs"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible(
				"//tr[@class='portlet-section-header results-header']/th[1]/input"));
		assertEquals(RuntimeVariables.replace("Title"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[2]"));
		assertEquals(RuntimeVariables.replace("Author"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[3]"));
		assertEquals(RuntimeVariables.replace("Create Date"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[4]"));
		assertEquals(RuntimeVariables.replace("Status"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[5]"));
		assertTrue(selenium.isVisible(
				"//tr[@class='portlet-section-body results-row last']/td[1]/input"));
		assertEquals(RuntimeVariables.replace("Blogs Entry Title"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[2]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[3]"));
		assertTrue(selenium.isVisible(
				"//tr[@class='portlet-section-body results-row last']/td[4]"));
		assertEquals(RuntimeVariables.replace("Approved"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[5]"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[6]/span/ul/li/strong/a"));
		assertEquals(RuntimeVariables.replace("Showing 1 result."),
			selenium.getText("//div[@class='search-results']"));
		selenium.selectFrame("relative=top");
	}
}