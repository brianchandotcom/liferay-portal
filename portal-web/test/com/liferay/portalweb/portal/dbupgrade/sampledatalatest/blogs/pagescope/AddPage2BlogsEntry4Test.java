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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.blogs.pagescope;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPage2BlogsEntry4Test extends BaseTestCase {
	public void testAddPage2BlogsEntry4() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/blogs-page-scope-community/");
		selenium.waitForVisible("link=Blogs Test Page2");
		selenium.clickAt("link=Blogs Test Page2",
			RuntimeVariables.replace("Blogs Test Page2"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//span[@class='portlet-title-text']",
			"Blogs (Blogs Test Page2)");
		assertEquals(RuntimeVariables.replace("Blogs (Blogs Test Page2)"),
			selenium.getText("//span[@class='portlet-title-text']"));
		selenium.clickAt("//input[@value='Add Blog Entry']",
			RuntimeVariables.replace("Add Blog Entry"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_33_title']",
			RuntimeVariables.replace("Blogs Entry4 Title"));
		selenium.waitForElementPresent(
			"//textarea[@id='_33_editor' and @style='display: none;']");
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForVisible("//a[@class='cke_button_source cke_on']");
		selenium.waitForVisible("//td[@id='cke_contents__33_editor']/textarea");
		selenium.type("//td[@id='cke_contents__33_editor']/textarea",
			RuntimeVariables.replace("Blogs Entry4 Content"));
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForElementPresent(
			"//textarea[@id='_33_editor' and @style='display: none;']");
		selenium.waitForVisible("//td[@id='cke_contents__33_editor']/iframe");
		selenium.selectFrame("//td[@id='cke_contents__33_editor']/iframe");
		selenium.waitForText("//body", "Blogs Entry4 Content");
		selenium.selectFrame("relative=top");
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Blogs Entry4 Title"),
			selenium.getText("xPath=(//div[@class='entry-title'])[1]/h2/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry4 Content"),
			selenium.getText("xPath=(//div[@class='entry-body'])[1]/p"));
		assertEquals(RuntimeVariables.replace("Blogs Entry3 Title"),
			selenium.getText("xPath=(//div[@class='entry-title'])[2]/h2/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry3 Content"),
			selenium.getText("xPath=(//div[@class='entry-body'])[2]/p"));
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Title"),
			selenium.getText("xPath=(//div[@class='entry-title'])[3]/h2/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Content"),
			selenium.getText("xPath=(//div[@class='entry-body'])[3]/p"));
		selenium.open("/web/blogs-page-scope-community/");
		selenium.waitForVisible("link=Blogs Test Page1");
		selenium.clickAt("link=Blogs Test Page1",
			RuntimeVariables.replace("Blogs Test Page1"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		selenium.waitForText("//span[@class='portlet-title-text']", "Blogs");
		assertEquals(RuntimeVariables.replace("Blogs"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertFalse(selenium.isTextPresent("Blogs Entry4 Title"));
		assertFalse(selenium.isTextPresent("Blogs Entry4 Content"));
		assertFalse(selenium.isTextPresent("Blogs Entry3 Title"));
		assertFalse(selenium.isTextPresent("Blogs Entry3 Content"));
		assertFalse(selenium.isTextPresent("Blogs Entry2 Title"));
		assertFalse(selenium.isTextPresent("Blogs Entry2 Content"));
		selenium.open("/web/blogs-page-scope-community/");
		selenium.waitForVisible("link=Blogs Test Page2");
		selenium.clickAt("link=Blogs Test Page2",
			RuntimeVariables.replace("Blogs Test Page2"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//span[@class='portlet-title-text']",
			"Blogs (Blogs Test Page2)");
		assertEquals(RuntimeVariables.replace("Blogs (Blogs Test Page2)"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertEquals(RuntimeVariables.replace("Blogs Entry4 Title"),
			selenium.getText("xPath=(//div[@class='entry-title'])[1]/h2/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry4 Content"),
			selenium.getText("xPath=(//div[@class='entry-body'])[1]/p"));
		assertEquals(RuntimeVariables.replace("Blogs Entry3 Title"),
			selenium.getText("xPath=(//div[@class='entry-title'])[2]/h2/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry3 Content"),
			selenium.getText("xPath=(//div[@class='entry-body'])[2]/p"));
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Title"),
			selenium.getText("xPath=(//div[@class='entry-title'])[3]/h2/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Content"),
			selenium.getText("xPath=(//div[@class='entry-body'])[3]/p"));
		selenium.open("/web/blogs-page-scope-community/");
		selenium.waitForVisible("link=Blogs Test Page3");
		selenium.clickAt("link=Blogs Test Page3",
			RuntimeVariables.replace("Blogs Test Page3"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//span[@class='portlet-title-text']",
			"Blogs (Blogs Test Page2)");
		assertEquals(RuntimeVariables.replace("Blogs (Blogs Test Page2)"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertEquals(RuntimeVariables.replace("Blogs Entry4 Title"),
			selenium.getText("xPath=(//div[@class='entry-title'])[1]/h2/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry4 Content"),
			selenium.getText("xPath=(//div[@class='entry-body'])[1]/p"));
		assertEquals(RuntimeVariables.replace("Blogs Entry3 Title"),
			selenium.getText("xPath=(//div[@class='entry-title'])[2]/h2/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry3 Content"),
			selenium.getText("xPath=(//div[@class='entry-body'])[2]/p"));
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Title"),
			selenium.getText("xPath=(//div[@class='entry-title'])[3]/h2/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Content"),
			selenium.getText("xPath=(//div[@class='entry-body'])[3]/p"));
	}
}