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

package com.liferay.portalweb.demo.knowledgebase;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SelectKBAArticleSectionsCMKBArTest extends BaseTestCase {
	public void testSelectKBAArticleSectionsCMKBAr() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Knowledge Base Article Test Page",
			RuntimeVariables.replace("Knowledge Base Article Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Please configure this portlet to make it visible to all users."),
			selenium.getText(
				"//div[@class='portlet-configuration portlet-msg-info']/a"));
		selenium.clickAt("//div[@class='portlet-configuration portlet-msg-info']/a",
			RuntimeVariables.replace(
				"Please configure this portlet to make it visible to all users."));
		selenium.waitForElementPresent(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.selectFrame(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible("//div[@class='kb-edit-link']/a");
		assertEquals(RuntimeVariables.replace("Select Article \u00bb"),
			selenium.getText("//div[@class='kb-edit-link']/a"));
		selenium.clickAt("//div[@class='kb-edit-link']/a",
			RuntimeVariables.replace("Select Article \u00bb"));
		Thread.sleep(5000);
		selenium.selectWindow("title=Knowledge Base Article");
		selenium.waitForVisible(
			"//tr[contains(.,'Knowledge Base Article 1')]/td[1]/a");
		assertEquals(RuntimeVariables.replace("Knowledge Base Article 1"),
			selenium.getText(
				"//tr[contains(.,'Knowledge Base Article 1')]/td[1]/a"));
		selenium.click(
			"//tr[contains(.,'Knowledge Base Article 1')]/td[7]/span/span/input[@value='Choose']");
		Thread.sleep(5000);
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.selectFrame(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.waitForText("//div[@id='_86_configurationKBArticle']",
			"Knowledge Base Article 1");
		assertEquals(RuntimeVariables.replace("Knowledge Base Article 1"),
			selenium.getText("//div[@id='_86_configurationKBArticle']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.selectFrame("relative=top");
		selenium.waitForVisible("//div[@class='kb-title']");
		selenium.waitForVisible("//div[@class='kb-entity-body']/p");
		assertEquals(RuntimeVariables.replace("Knowledge Base Article 1"),
			selenium.getText("//div[@class='kb-title']"));
		assertEquals(RuntimeVariables.replace(
				"This is the content of Article 1"),
			selenium.getText("//div[@class='kb-entity-body']/p"));
	}
}