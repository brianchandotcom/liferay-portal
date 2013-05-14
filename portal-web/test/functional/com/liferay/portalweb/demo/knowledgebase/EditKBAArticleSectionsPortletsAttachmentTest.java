/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.demo.knowledgebase;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditKBAArticleSectionsPortletsAttachmentTest extends BaseTestCase {
	public void testEditKBAArticleSectionsPortletsAttachment()
		throws Exception {
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
		selenium.clickAt("link=Knowledge Base (Admin)",
			RuntimeVariables.replace("Knowledge Base (Admin)"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("KB Admin Article"),
			selenium.getText("//tr[3]/td[3]"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]",
			RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Add Attachments \u00bb"),
			selenium.getText(
				"//div[@id='_1_WAR_knowledgebaseportlet_attachments']/div/div/a"));
		selenium.clickAt("//div[@id='_1_WAR_knowledgebaseportlet_attachments']/div/div/a",
			RuntimeVariables.replace("Add Attachments \u00bb"));
		Thread.sleep(5000);
		selenium.selectWindow("title=Knowledge Base (Admin)");
		selenium.waitForVisible(
			"//input[@id='_1_WAR_knowledgebaseportlet_file']");
		selenium.uploadCommonFile("//input[@id='_1_WAR_knowledgebaseportlet_file']",
			RuntimeVariables.replace("Document_1.jpg"));
		selenium.waitForVisible("//a/span");
		assertEquals(RuntimeVariables.replace("Document_1.jpg (12.9k)"),
			selenium.getText("//a/span"));
		selenium.close();
		Thread.sleep(5000);
		selenium.selectWindow("null");
		selenium.waitForVisible(
			"//div[@id='_1_WAR_knowledgebaseportlet_attachments']/div/div/span/a/span");
		assertEquals(RuntimeVariables.replace("Document_1.jpg (12.9k)"),
			selenium.getText(
				"//div[@id='_1_WAR_knowledgebaseportlet_attachments']/div/div/span/a/span"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}