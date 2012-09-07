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

package com.liferay.portalweb.portal.dbupgrade.sampledata6011.stagingorganization.webcontentdisplay;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWebContentTest extends BaseTestCase {
	public void testAddWebContent() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/staging-organization-wcd/");
		selenium.clickAt("link=Page Staging Organization Web Content Display",
			RuntimeVariables.replace(
				"Page Staging Organization Web Content Display"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible(
				"//body[@class='blue live-view controls-visible signed-in public-page']"));
		assertTrue(selenium.isElementNotPresent(
				"//body[@class='blue staging controls-visible signed-in public-page']"));
		assertEquals(RuntimeVariables.replace("Staging"),
			selenium.getText("//li[@id='_145_staging']/a/span"));
		selenium.mouseOver("//li[@id='_145_staging']/a/span");
		selenium.waitForVisible("link=View Staged Page");
		selenium.clickAt("link=View Staged Page",
			RuntimeVariables.replace("View Staged Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementNotPresent(
				"//body[@class='blue live-view controls-visible signed-in public-page']"));
		assertTrue(selenium.isVisible(
				"//body[@class='blue staging controls-visible signed-in public-page']"));
		assertEquals(RuntimeVariables.replace("Manage"),
			selenium.getText("//li[@id='_145_manageContent']/a/span"));
		selenium.mouseOver("//li[@id='_145_manageContent']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Web Content']",
			RuntimeVariables.replace("Add Web Content"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_15_title']",
			RuntimeVariables.replace("WC Web Content Name"));
		selenium.waitForElementPresent(
			"//textarea[@id='CKEditor1' and @style=\"display: none;\"]");
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//a/span[.='Source']"));
		selenium.clickAt("//a/span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForVisible("//a[@class='cke_button_source cke_on']");
		selenium.waitForVisible("//td[@id='cke_contents_CKEditor1']/textarea");
		selenium.type("//td[@id='cke_contents_CKEditor1']/textarea",
			RuntimeVariables.replace("WC Web Content Content"));
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//a/span[.='Source']"));
		selenium.clickAt("//a/span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForElementPresent(
			"//textarea[@id='CKEditor1' and @style=\"display: none;\"]");
		selenium.waitForVisible("//td[@id='cke_contents_CKEditor1']/iframe");
		selenium.selectFrame("//td[@id='cke_contents_CKEditor1']/iframe");
		selenium.waitForText("//body", "WC Web Content Content");
		selenium.selectFrame("relative=top");
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isVisible("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("WC Web Content Name"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("1.0"),
			selenium.getText("//td[4]/a"));
		assertEquals(RuntimeVariables.replace("Approved"),
			selenium.getText("//td[5]/a"));
		assertTrue(selenium.isVisible("//td[6]/a"));
		assertTrue(selenium.isVisible("//td[7]/a"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//td[8]/a"));
	}
}