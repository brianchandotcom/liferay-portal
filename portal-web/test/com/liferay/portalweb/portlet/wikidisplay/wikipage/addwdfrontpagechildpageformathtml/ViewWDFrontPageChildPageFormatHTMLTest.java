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

package com.liferay.portalweb.portlet.wikidisplay.wikipage.addwdfrontpagechildpageformathtml;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWDFrontPageChildPageFormatHTMLTest extends BaseTestCase {
	public void testViewWDFrontPageChildPageFormatHTML()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Display Test Page",
			RuntimeVariables.replace("Wiki Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Wiki FrontPage ChildPage Title"),
			selenium.getText("//div[@class='child-pages']/ul/li/a"));
		selenium.clickAt("//div[@class='child-pages']/ul/li/a",
			RuntimeVariables.replace("Wiki FrontPage ChildPage Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Wiki FrontPage ChildPage Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Welcome to LIFERAY"),
			selenium.getText("//div[@class='wiki-body']/p/a"));
		assertEquals(RuntimeVariables.replace("Details"),
			selenium.getText(
				"//div[@class='page-actions top-actions']/span/a[contains(.,'Details')]"));
		selenium.clickAt("//div[@class='page-actions top-actions']/span/a[contains(.,'Details')]",
			RuntimeVariables.replace("Details"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("FrontPage"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span[contains(.,'FrontPage')]"));
		assertEquals(RuntimeVariables.replace("Recent Changes"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span[contains(.,'Recent Changes')]"));
		assertEquals(RuntimeVariables.replace("All Pages"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span[contains(.,'All Pages')]"));
		assertEquals(RuntimeVariables.replace("Orphan Pages"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span[contains(.,'Orphan Pages')]"));
		assertEquals(RuntimeVariables.replace("Draft Pages"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span[contains(.,'Draft Pages')]"));
		assertTrue(selenium.isVisible(
				"//span[@class='aui-search-bar']/span/span/span/input"));
		assertTrue(selenium.isVisible("//input[@title='Search Pages']"));
		assertEquals(RuntimeVariables.replace("Wiki FrontPage ChildPage Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("//span[@class='header-back-to']/a"));
		assertEquals(RuntimeVariables.replace("Content"),
			selenium.getText(
				"//ul[contains(@class,'tabview-list')]/li/span/a[contains(.,'Content')]"));
		assertEquals(RuntimeVariables.replace("Details"),
			selenium.getText(
				"//ul[contains(@class,'tabview-list')]/li/span/a[contains(.,'Details')]"));
		assertEquals(RuntimeVariables.replace("History"),
			selenium.getText(
				"//ul[contains(@class,'tabview-list')]/li/span/a[contains(.,'History')]"));
		assertEquals(RuntimeVariables.replace("Incoming Links"),
			selenium.getText(
				"//ul[contains(@class,'tabview-list')]/li/span/a[contains(.,'Incoming Links')]"));
		assertEquals(RuntimeVariables.replace("Outgoing Links"),
			selenium.getText(
				"//ul[contains(@class,'tabview-list')]/li/span/a[contains(.,'Outgoing Links')]"));
		assertEquals(RuntimeVariables.replace("Attachments"),
			selenium.getText(
				"//ul[contains(@class,'tabview-list')]/li/span/a[contains(.,'Attachments')]"));
		assertEquals(RuntimeVariables.replace("Title"),
			selenium.getText("//tr[1]/th"));
		assertEquals(RuntimeVariables.replace("Wiki FrontPage ChildPage Title"),
			selenium.getText("//tr[1]/td"));
		assertEquals(RuntimeVariables.replace("Format"),
			selenium.getText("//tr[2]/th"));
		assertEquals(RuntimeVariables.replace("HTML"),
			selenium.getText("//tr[2]/td"));
		assertEquals(RuntimeVariables.replace("Latest Version"),
			selenium.getText("//tr[3]/th"));
		assertEquals(RuntimeVariables.replace("1.0"),
			selenium.getText("//tr[3]/td"));
		assertEquals(RuntimeVariables.replace("Created By"),
			selenium.getText("//tr[4]/th"));
		assertTrue(selenium.isPartialText("//tr[4]/td", "Joe Bloggs"));
		assertEquals(RuntimeVariables.replace("Last Changed By"),
			selenium.getText("//tr[5]/th"));
		assertTrue(selenium.isPartialText("//tr[5]/td", "Joe Bloggs"));
		assertEquals(RuntimeVariables.replace("Attachments"),
			selenium.getText("//tr[6]/th"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//tr[6]/td"));
		assertEquals(RuntimeVariables.replace("RSS Subscription"),
			selenium.getText("//tr[7]/th"));
		assertEquals(RuntimeVariables.replace(
				"Atom 1.0 (Opens New Window) RSS 1.0 (Opens New Window) RSS 2.0 (Opens New Window)"),
			selenium.getText("//tr[7]/td"));
		assertEquals(RuntimeVariables.replace("Email Subscription"),
			selenium.getText("//tr[8]/th"));
		assertEquals(RuntimeVariables.replace(
				"You are not subscribed to this page. Subscribe You are not subscribed to this wiki. Subscribe"),
			selenium.getText("//tr[8]/td"));
		assertEquals(RuntimeVariables.replace("Advanced Actions"),
			selenium.getText("//tr[9]/th"));
		assertEquals(RuntimeVariables.replace("Permissions Copy Move Delete"),
			selenium.getText("//tr[9]/td"));
	}
}