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

package com.liferay.portalweb.demo.sitemanagement.staginglocallive;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_ViewHistoryVersionNumbersTest extends BaseTestCase {
	public void testUser_ViewHistoryVersionNumbers() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Site Name");
		selenium.clickAt("link=Site Name", RuntimeVariables.replace("Site Name"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'live-view')]"));
		assertTrue(selenium.isElementNotPresent(
				"//body[contains(@class,'live-staging')]"));
		assertEquals(RuntimeVariables.replace("Staging"),
			selenium.getText("//div[@class='staging-bar']/ul/li[2]/span/a"));
		selenium.clickAt("//div[@class='staging-bar']/ul/li[2]/span/a",
			RuntimeVariables.replace("Staging"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'local-staging')]"));
		assertTrue(selenium.isElementNotPresent(
				"//body[contains(@class,'live-view')]"));
		assertEquals(RuntimeVariables.replace("Draft"),
			selenium.getText("//span[@class='workflow-status']/strong"));
		assertEquals(RuntimeVariables.replace("History"),
			selenium.getText("//button/span[.='History']"));
		selenium.clickAt("//button/span[.='History']",
			RuntimeVariables.replace("History"));
		selenium.waitForVisible("//tr[3]/td[2]/span/span/span");
		assertEquals(RuntimeVariables.replace("Draft"),
			selenium.getText("//tr[3]/td[2]/span/span/span"));
		assertEquals(RuntimeVariables.replace("Current Version"),
			selenium.getText("//tr[3]/td[3]/span[2]"));
		assertEquals(RuntimeVariables.replace("selen01 lenn nium01"),
			selenium.getText("//tr[3]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("Draft"),
			selenium.getText("//tr[4]/td[2]/span/span/span"));
		assertEquals(RuntimeVariables.replace("selen01 lenn nium01"),
			selenium.getText("//tr[4]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("Ready for Publication"),
			selenium.getText("//tr[5]/td[2]/span/span/span"));
		assertEquals(RuntimeVariables.replace("selen01 lenn nium01"),
			selenium.getText("//tr[5]/td[4]/a"));

		String versionNumberDraft1 = selenium.getText("//tr[3]/td[3]/span[1]");
		RuntimeVariables.setValue("versionNumberDraft1", versionNumberDraft1);

		String versionNumberDraft2 = selenium.getText("//tr[4]/td[3]/a");
		RuntimeVariables.setValue("versionNumberDraft2", versionNumberDraft2);

		String versionNumberPublication3 = selenium.getText("//tr[5]/td[3]/a");
		RuntimeVariables.setValue("versionNumberPublication3",
			versionNumberPublication3);
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Site Name");
		selenium.clickAt("link=Site Name", RuntimeVariables.replace("Site Name"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'live-view')]"));
		assertTrue(selenium.isElementNotPresent(
				"//body[contains(@class,'live-staging')]"));
		assertEquals(RuntimeVariables.replace("Staging"),
			selenium.getText("//div[@class='staging-bar']/ul/li[2]/span/a"));
		selenium.clickAt("//div[@class='staging-bar']/ul/li[2]/span/a",
			RuntimeVariables.replace("Staging"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'local-staging')]"));
		assertTrue(selenium.isElementNotPresent(
				"//body[contains(@class,'live-view')]"));
		assertEquals(RuntimeVariables.replace("Draft"),
			selenium.getText("//span[@class='workflow-status']/strong"));
		assertEquals(RuntimeVariables.replace("Undo"),
			selenium.getText("//button/span[.='Undo']"));
		selenium.clickAt("//button/span[.='Undo']",
			RuntimeVariables.replace("Undo"));
		selenium.waitForConfirmation(
			"Are you sure you want to undo your last changes?");
		selenium.waitForElementPresent(
			"//div[@id='column-2']/div/div[contains(@class,'portlet-message-boards')]");
		assertTrue(selenium.isVisible(
				"//div[@id='column-2']/div/div[contains(@class,'portlet-message-boards')]"));
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Site Name");
		selenium.clickAt("link=Site Name", RuntimeVariables.replace("Site Name"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'live-view')]"));
		assertTrue(selenium.isElementNotPresent(
				"//body[contains(@class,'live-staging')]"));
		assertEquals(RuntimeVariables.replace("Staging"),
			selenium.getText("//div[@class='staging-bar']/ul/li[2]/span/a"));
		selenium.clickAt("//div[@class='staging-bar']/ul/li[2]/span/a",
			RuntimeVariables.replace("Staging"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'local-staging')]"));
		assertTrue(selenium.isElementNotPresent(
				"//body[contains(@class,'live-view')]"));
		assertEquals(RuntimeVariables.replace("Draft"),
			selenium.getText("//span[@class='workflow-status']/strong"));
		assertEquals(RuntimeVariables.replace("History"),
			selenium.getText("//button/span[.='History']"));
		selenium.clickAt("//button/span[.='History']",
			RuntimeVariables.replace("History"));
		selenium.waitForVisible("//tr[3]/td[2]/span/span/span");
		assertEquals(RuntimeVariables.replace("Draft"),
			selenium.getText("//tr[3]/td[2]/span/span/span"));
		assertEquals(RuntimeVariables.replace("Current Version"),
			selenium.getText("//tr[3]/td[3]/span[2]"));
		assertEquals(RuntimeVariables.replace("selen01 lenn nium01"),
			selenium.getText("//tr[3]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("Ready for Publication"),
			selenium.getText("//tr[4]/td[2]/span/span/span"));
		assertEquals(RuntimeVariables.replace("selen01 lenn nium01"),
			selenium.getText("//tr[4]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("${versionNumberDraft2}"),
			selenium.getText("//tr[3]/td[3]/span[1]"));
		assertEquals(RuntimeVariables.replace("${versionNumberPublication3}"),
			selenium.getText("//tr[4]/td[3]/a"));
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Site Name");
		selenium.clickAt("link=Site Name", RuntimeVariables.replace("Site Name"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'live-view')]"));
		assertTrue(selenium.isElementNotPresent(
				"//body[contains(@class,'live-staging')]"));
		assertEquals(RuntimeVariables.replace("Staging"),
			selenium.getText("//div[@class='staging-bar']/ul/li[2]/span/a"));
		selenium.clickAt("//div[@class='staging-bar']/ul/li[2]/span/a",
			RuntimeVariables.replace("Staging"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'local-staging')]"));
		assertTrue(selenium.isElementNotPresent(
				"//body[contains(@class,'live-view')]"));
		assertEquals(RuntimeVariables.replace("Draft"),
			selenium.getText("//span[@class='workflow-status']/strong"));
		assertEquals(RuntimeVariables.replace("Redo"),
			selenium.getText("//button/span[.='Redo']"));
		selenium.clickAt("//button/span[.='Redo']",
			RuntimeVariables.replace("Redo"));
		selenium.waitForConfirmation(
			"Are you sure you want to redo your last changes?");
		selenium.waitForElementPresent(
			"//div[@id='column-1']/div/div[contains(@class,'portlet-message-boards')]");
		assertTrue(selenium.isVisible(
				"//div[@id='column-1']/div/div[contains(@class,'portlet-message-boards')]"));
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Site Name");
		selenium.clickAt("link=Site Name", RuntimeVariables.replace("Site Name"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'live-view')]"));
		assertTrue(selenium.isElementNotPresent(
				"//body[contains(@class,'live-staging')]"));
		assertEquals(RuntimeVariables.replace("Staging"),
			selenium.getText("//div[@class='staging-bar']/ul/li[2]/span/a"));
		selenium.clickAt("//div[@class='staging-bar']/ul/li[2]/span/a",
			RuntimeVariables.replace("Staging"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'local-staging')]"));
		assertTrue(selenium.isElementNotPresent(
				"//body[contains(@class,'live-view')]"));
		assertEquals(RuntimeVariables.replace("Draft"),
			selenium.getText("//span[@class='workflow-status']/strong"));
		assertEquals(RuntimeVariables.replace("History"),
			selenium.getText("//button/span[.='History']"));
		selenium.clickAt("//button/span[.='History']",
			RuntimeVariables.replace("History"));
		selenium.waitForVisible("//tr[3]/td[2]/span/span/span");
		assertEquals(RuntimeVariables.replace("Draft"),
			selenium.getText("//tr[3]/td[2]/span/span/span"));
		assertEquals(RuntimeVariables.replace("Current Version"),
			selenium.getText("//tr[3]/td[3]/span[2]"));
		assertEquals(RuntimeVariables.replace("selen01 lenn nium01"),
			selenium.getText("//tr[3]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("Draft"),
			selenium.getText("//tr[4]/td[2]/span/span/span"));
		assertEquals(RuntimeVariables.replace("selen01 lenn nium01"),
			selenium.getText("//tr[4]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("Ready for Publication"),
			selenium.getText("//tr[5]/td[2]/span/span/span"));
		assertEquals(RuntimeVariables.replace("selen01 lenn nium01"),
			selenium.getText("//tr[5]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("${versionNumberDraft1}"),
			selenium.getText("//tr[3]/td[3]/span[1]"));
		assertEquals(RuntimeVariables.replace("${versionNumberDraft2}"),
			selenium.getText("//tr[4]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("${versionNumberPublication3}"),
			selenium.getText("//tr[5]/td[3]/a"));
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Site Name");
		selenium.clickAt("link=Site Name", RuntimeVariables.replace("Site Name"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'live-view')]"));
		assertTrue(selenium.isElementNotPresent(
				"//body[contains(@class,'live-staging')]"));
		assertEquals(RuntimeVariables.replace("Staging"),
			selenium.getText("//div[@class='staging-bar']/ul/li[2]/span/a"));
		selenium.clickAt("//div[@class='staging-bar']/ul/li[2]/span/a",
			RuntimeVariables.replace("Staging"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'local-staging')]"));
		assertTrue(selenium.isElementNotPresent(
				"//body[contains(@class,'live-view')]"));
		assertEquals(RuntimeVariables.replace("Draft"),
			selenium.getText("//span[@class='workflow-status']/strong"));
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent("//li[contains(@class,'manage-page')]/a");
		selenium.clickAt("//li[contains(@class,'manage-page')]/a",
			RuntimeVariables.replace("Manage Pages"));
		selenium.waitForVisible("//a[@id='_88_layoutLink']");
		assertTrue(selenium.isPartialText("//a[@id='_88_layoutLink']", "Page"));
		selenium.clickAt("//a[@id='_88_layoutLink']",
			RuntimeVariables.replace("Page"));
		Thread.sleep(5000);
		selenium.waitForVisible("//input[@value='1_column']");
		selenium.clickAt("//input[@value='1_column']",
			RuntimeVariables.replace("1 Column"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully. The page will be refreshed when you close this dialog. Alternatively you can hide this dialog."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Site Name");
		selenium.clickAt("link=Site Name", RuntimeVariables.replace("Site Name"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'live-view')]"));
		assertTrue(selenium.isElementNotPresent(
				"//body[contains(@class,'live-staging')]"));
		assertEquals(RuntimeVariables.replace("Staging"),
			selenium.getText("//div[@class='staging-bar']/ul/li[2]/span/a"));
		selenium.clickAt("//div[@class='staging-bar']/ul/li[2]/span/a",
			RuntimeVariables.replace("Staging"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'local-staging')]"));
		assertTrue(selenium.isElementNotPresent(
				"//body[contains(@class,'live-view')]"));
		assertEquals(RuntimeVariables.replace("Draft"),
			selenium.getText("//span[@class='workflow-status']/strong"));
		assertTrue(selenium.isVisible(
				"//div[@id='column-1']/div/div[contains(@class,'portlet-message-boards')]"));
		assertTrue(selenium.isVisible(
				"//div[@id='column-1']/div/div[contains(@class,'portlet-user-statistics')]"));
		assertTrue(selenium.isVisible(
				"//div[@id='column-1']/div/div[contains(@class,'portlet-search')]"));
		assertTrue(selenium.isVisible(
				"//div[@id='column-1']/div/div[contains(@class,'portlet-polls-display')]"));
		assertTrue(selenium.isVisible(
				"//div[@id='column-1']/div/div[contains(@class,'portlet-document-library')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@id='column-2']/div/div[contains(@class,'portlet-message-boards')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@id='column-2']/div/div[contains(@class,'portlet-user-statistics')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@id='column-2']/div/div[contains(@class,'portlet-search')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@id='column-2']/div/div[contains(@class,'portlet-polls-display')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@id='column-2']/div/div[contains(@class,'portlet-document-library')]"));
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Site Name");
		selenium.clickAt("link=Site Name", RuntimeVariables.replace("Site Name"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'live-view')]"));
		assertTrue(selenium.isElementNotPresent(
				"//body[contains(@class,'live-staging')]"));
		assertEquals(RuntimeVariables.replace("Staging"),
			selenium.getText("//div[@class='staging-bar']/ul/li[2]/span/a"));
		selenium.clickAt("//div[@class='staging-bar']/ul/li[2]/span/a",
			RuntimeVariables.replace("Staging"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'local-staging')]"));
		assertTrue(selenium.isElementNotPresent(
				"//body[contains(@class,'live-view')]"));
		assertEquals(RuntimeVariables.replace("Draft"),
			selenium.getText("//span[@class='workflow-status']/strong"));
		assertEquals(RuntimeVariables.replace("History"),
			selenium.getText("//button/span[.='History']"));
		selenium.clickAt("//button/span[.='History']",
			RuntimeVariables.replace("History"));
		selenium.waitForVisible("//tr[3]/td[2]/span/span/span");
		assertEquals(RuntimeVariables.replace("Draft"),
			selenium.getText("//tr[3]/td[2]/span/span/span"));
		assertEquals(RuntimeVariables.replace("Current Version"),
			selenium.getText("//tr[3]/td[3]/span[2]"));
		assertEquals(RuntimeVariables.replace("selen01 lenn nium01"),
			selenium.getText("//tr[3]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("Draft"),
			selenium.getText("//tr[4]/td[2]/span/span/span"));
		assertEquals(RuntimeVariables.replace("selen01 lenn nium01"),
			selenium.getText("//tr[4]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("Draft"),
			selenium.getText("//tr[5]/td[2]/span/span/span"));
		assertEquals(RuntimeVariables.replace("selen01 lenn nium01"),
			selenium.getText("//tr[5]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("Ready for Publication"),
			selenium.getText("//tr[6]/td[2]/span/span/span"));
		assertEquals(RuntimeVariables.replace("selen01 lenn nium01"),
			selenium.getText("//tr[6]/td[4]/a"));
		assertNotEquals(RuntimeVariables.replace("${versionNumberDraft1}"),
			selenium.getText("//tr[3]/td[3]/span[1]"));
		assertNotEquals(RuntimeVariables.replace("${versionNumberDraft2}"),
			selenium.getText("//tr[3]/td[3]/span[1]"));
		assertNotEquals(RuntimeVariables.replace("${versionNumberPublication3}"),
			selenium.getText("//tr[3]/td[3]/span[1]"));
		assertEquals(RuntimeVariables.replace("${versionNumberDraft1}"),
			selenium.getText("//tr[4]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("${versionNumberDraft2}"),
			selenium.getText("//tr[5]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("${versionNumberPublication3}"),
			selenium.getText("//tr[6]/td[3]/a"));
	}
}