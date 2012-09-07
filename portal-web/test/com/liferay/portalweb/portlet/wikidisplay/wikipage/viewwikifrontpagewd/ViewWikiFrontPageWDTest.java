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

package com.liferay.portalweb.portlet.wikidisplay.wikipage.viewwikifrontpagewd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWikiFrontPageWDTest extends BaseTestCase {
	public void testViewWikiFrontPageWD() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Display Test Page",
			RuntimeVariables.replace("Wiki Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("FrontPage"),
			selenium.getText(
				"//div[@class='top-links-container']/div/ul/li[1]/span"));
		assertEquals(RuntimeVariables.replace("Recent Changes"),
			selenium.getText(
				"//div[@class='top-links-container']/div/ul/li[2]/span"));
		assertEquals(RuntimeVariables.replace("All Pages"),
			selenium.getText(
				"//div[@class='top-links-container']/div/ul/li[3]/span"));
		assertEquals(RuntimeVariables.replace("Orphan Pages"),
			selenium.getText(
				"//div[@class='top-links-container']/div/ul/li[4]/span"));
		assertEquals(RuntimeVariables.replace("Draft Pages"),
			selenium.getText(
				"//div[@class='top-links-container']/div/ul/li[5]/span"));
		assertTrue(selenium.isVisible("//input[@title='Search Pages']"));
		assertTrue(selenium.isVisible("//input[@value='Search']"));
		assertEquals(RuntimeVariables.replace("FrontPage"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='page-actions top-actions']/span[1]/a"));
		assertEquals(RuntimeVariables.replace("Details"),
			selenium.getText(
				"//div[@class='page-actions top-actions']/span[2]/a"));
		assertEquals(RuntimeVariables.replace("Print"),
			selenium.getText(
				"//div[@class='page-actions top-actions']/span[3]/a"));
		assertEquals(RuntimeVariables.replace("Wiki FrontPage Content"),
			selenium.getText("//div[@class='wiki-body']/p"));
		assertEquals(RuntimeVariables.replace("Add Child Page"),
			selenium.getText("//div[@class='article-actions']/span[1]/a"));
		assertEquals(RuntimeVariables.replace("0 Attachments"),
			selenium.getText("//div[@class='article-actions']/span[2]/a"));
		assertTrue(selenium.isPartialText("//div[@class='stats']", "Views"));
		assertTrue(selenium.isVisible(
				"//div[@class='page-ratings']/div/div[contains(@id,'ratingStar')]"));
		assertTrue(selenium.isVisible(
				"//div[@class='page-ratings']/div/div[contains(@id,'ratingScore')]"));
		assertEquals(RuntimeVariables.replace("Comments"),
			selenium.getText("//div[@class='lfr-panel-title']/span"));
		assertTrue(selenium.isPartialText(
				"//fieldset[@class='aui-fieldset add-comment ']/div",
				"No comments yet."));
		assertEquals(RuntimeVariables.replace("Be the first."),
			selenium.getText(
				"//fieldset[@class='aui-fieldset add-comment ']/div/a"));
		assertEquals(RuntimeVariables.replace("Subscribe to Comments"),
			selenium.getText(
				"//fieldset[@class='aui-fieldset add-comment ']/div/span"));
	}
}