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

package com.liferay.portalweb.plugins.knowledgebase.knowledgebaseadmin.kbaarticle.addkbaarticle;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewKBAArticleTest extends BaseTestCase {
	public void testViewKBAArticle() throws Exception {
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
		selenium.clickAt("link=Articles", RuntimeVariables.replace("Articles"));
		selenium.waitForPageToLoad("30000");
		assertEquals("1.0",
			selenium.getValue(
				"//tr[contains(.,'Knowledge Base Admin Article Title')]/td[2]/span/span/span/input"));
		assertEquals(RuntimeVariables.replace(
				"Knowledge Base Admin Article Title"),
			selenium.getText(
				"//tr[contains(.,'Knowledge Base Admin Article Title')]/td[3]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText(
				"//tr[contains(.,'Knowledge Base Admin Article Title')]/td[4]"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'Knowledge Base Admin Article Title')]/td[5]"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'Knowledge Base Admin Article Title')]/td[6]"));
		assertEquals(RuntimeVariables.replace("0 (Approved)"),
			selenium.getText(
				"//tr[contains(.,'Knowledge Base Admin Article Title')]/td[7]"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'Knowledge Base Admin Article Title')]/td[8]"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'View')]/a");
		assertEquals(RuntimeVariables.replace("View"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'View')]/a"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'View')]/a",
			RuntimeVariables.replace("View"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Knowledge Base Admin Article Title"),
			selenium.getText("//div[@class='kb-title']"));
		assertEquals(RuntimeVariables.replace("Subscribe"),
			selenium.getText(
				"//div[@class='kb-article-tools']/span/a[contains(.,'Subscribe')]"));
		assertEquals(RuntimeVariables.replace("History"),
			selenium.getText(
				"//div[@class='kb-article-tools']/span/a[contains(.,'History')]"));
		assertEquals(RuntimeVariables.replace("Print"),
			selenium.getText(
				"//div[@class='kb-article-tools']/span/a[contains(.,'Print')]"));
		assertEquals(RuntimeVariables.replace("Add Child Article"),
			selenium.getText(
				"//td[contains(.,'Add Child Article')]/span/a/span"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("//td[contains(.,'Edit')]/span/a/span"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText("//td[contains(.,'Permissions')]/span/a/span"));
		assertEquals(RuntimeVariables.replace("Move"),
			selenium.getText("//td[contains(.,'Move')]/span/a/span"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText("//td[contains(.,'Delete')]/span/a/span"));
		assertEquals(RuntimeVariables.replace("Approved"),
			selenium.getText("//strong[@class='workflow-status-approved']"));
		assertEquals(RuntimeVariables.replace(
				"Knowledge Base Admin Article Content"),
			selenium.getText("//div[@class='kb-entity-body']/p"));
		assertEquals(RuntimeVariables.replace("Your Rating"),
			selenium.getText("//div[contains(@id,'ratingStarContent')]/div"));
		assertEquals(RuntimeVariables.replace("Average (0 Votes)"),
			selenium.getText("//div[contains(@id,'ratingScoreContent')]/div"));
		assertEquals(RuntimeVariables.replace("Comments"),
			selenium.getText(
				"//div[contains(@id,'CommentsPanel')]/div/div/span"));
	}
}