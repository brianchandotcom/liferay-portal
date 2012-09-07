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

package com.liferay.portalweb.portal.controlpanel.webcontent.wctemplate.addwctemplate;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWCTemplateTest extends BaseTestCase {
	public void testViewWCTemplate() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Templates", RuntimeVariables.replace("Templates"));
		selenium.waitForPageToLoad("30000");

		String templateID = selenium.getText("//td[2]/a");
		RuntimeVariables.setValue("templateID", templateID);
		assertEquals(RuntimeVariables.replace("${templateID}"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("WC Template Name"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("WC Template Description"),
			selenium.getText("//td[4]/a"));
		selenium.clickAt("//td[3]/a",
			RuntimeVariables.replace("WC Template Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertEquals(RuntimeVariables.replace(
				"Web Content can be any content you would like to add to a site, such as articles, a FAQ, or a news item. Administrators can manage content, as well as assign user roles and permissions. Users may add, edit, approve, or view content depending on their role. Or disable for all portlets."),
			selenium.getText("//div[@id='cpContextPanelTemplate']"));
		assertEquals(RuntimeVariables.replace("WC Template Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertTrue(selenium.isPartialText(
				"//fieldset/div/div/div[contains(.,'ID')]",
				RuntimeVariables.getValue("templateID")));
		assertEquals("WC Template Name",
			selenium.getValue("//input[@id='_15_name_en_US']"));
		assertEquals(RuntimeVariables.replace("Other Languages (0)"),
			selenium.getText(
				"//input[@id='_15_name_en_US']/following-sibling::span/a"));
		assertEquals("WC Template Description",
			selenium.getValue("//textarea[@id='_15_description_en_US']"));
		assertEquals(RuntimeVariables.replace("Other Languages (0)"),
			selenium.getText(
				"//textarea[@id='_15_description_en_US']/following-sibling::span/a"));
		assertTrue(selenium.isChecked("//input[@id='_15_cacheableCheckbox']"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText("//*[@id='_15_structureName']"));
		assertEquals("Select",
			selenium.getValue(
				"//*[@id='_15_structureName']/following-sibling::span/span/input"));
		assertEquals("Remove",
			selenium.getValue(
				"//*[@id='_15_structureName']/following-sibling::span[2]/span/input"));
		assertEquals("VM",
			selenium.getSelectedLabel("//select[@id='_15_langType']"));
		assertTrue(selenium.isVisible("//input[@value='Launch Editor']"));
		assertTrue(selenium.isVisible("//input[@value='Download']"));
		assertTrue(selenium.isVisible("//input[@value='Save']"));
		assertTrue(selenium.isVisible("//input[@value='Save and Continue']"));
		assertTrue(selenium.isVisible("//input[@value='Cancel']"));
	}
}