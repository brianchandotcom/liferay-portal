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

package com.liferay.portalweb.socialofficehome.events.event.vieweventsiteed;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddSitesSiteTest extends BaseTestCase {
	public void testAddSitesSite() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/so/dashboard/");
		selenium.waitForVisible("//li[contains(@class, 'selected')]/a/span");
		assertEquals(RuntimeVariables.replace("Dashboard"),
			selenium.getText("//li[contains(@class, 'selected')]/a/span"));
		assertEquals(RuntimeVariables.replace("Sites"),
			selenium.getText("//div[@id='so-sidebar']/h3"));
		selenium.waitForVisible("//button[contains(.,'Add Site')]/span[2]");
		assertEquals(RuntimeVariables.replace("Add Site"),
			selenium.getText("//button[contains(.,'Add Site')]/span[2]"));
		selenium.clickAt("//button[contains(.,'Add Site')]/span[2]",
			RuntimeVariables.replace("Add Site"));
		selenium.waitForVisible("//label[contains(@for,'_5_WAR_soportlet')]");
		assertTrue(selenium.isVisible(
				"//label[contains(@for,'_5_WAR_soportlet')]"));
		assertTrue(selenium.isVisible("//input[@id='_5_WAR_soportlet_name']"));
		selenium.type("//input[@id='_5_WAR_soportlet_name']",
			RuntimeVariables.replace("Open Site Name"));
		assertTrue(selenium.isVisible(
				"//textarea[@id='_5_WAR_soportlet_description']"));
		selenium.type("//textarea[@id='_5_WAR_soportlet_description']",
			RuntimeVariables.replace("Open Site Description"));
		assertEquals("Next", selenium.getValue("//input[@value='Next']"));
		selenium.clickAt("//input[@value='Next']",
			RuntimeVariables.replace("Next"));
		selenium.waitForVisible(
			"//select[@id='_5_WAR_soportlet_layoutSetPrototypeSelect']");
		assertTrue(selenium.isVisible(
				"//select[@id='_5_WAR_soportlet_layoutSetPrototypeSelect']"));
		selenium.select("//select[@id='_5_WAR_soportlet_layoutSetPrototypeSelect']",
			RuntimeVariables.replace("Default Social Office Site"));
		assertTrue(selenium.isVisible(
				"//select[@id='_5_WAR_soportlet_typeSelect']"));
		selenium.select("//select[@id='_5_WAR_soportlet_typeSelect']",
			RuntimeVariables.replace("Open"));
		assertTrue(selenium.isChecked(
				"//div[2]/div/div/div/div/div/div[contains(.,'Home')]/input"));
		assertTrue(selenium.isChecked(
				"//div[2]/div/div/div/div/div/div[contains(.,'Calendar')]/input"));
		assertTrue(selenium.isChecked(
				"//div[2]/div/div/div/div/div/div[contains(.,'Documents')]/input"));
		assertTrue(selenium.isChecked(
				"//div[2]/div/div/div/div/div/div[contains(.,'Forums')]/input"));
		assertTrue(selenium.isChecked(
				"//div[2]/div/div/div/div/div/div[contains(.,'Blog')]/input"));
		assertTrue(selenium.isChecked(
				"//div[2]/div/div/div/div/div/div[contains(.,'Wiki')]/input"));
		assertTrue(selenium.isChecked(
				"//div[2]/div/div/div/div/div/div[contains(.,'Members')]/input"));
		assertEquals(RuntimeVariables.replace(
				"Open sites are listed, pages are public, and users are free to join and collaborate."),
			selenium.getText("//div[@class='message']"));
		assertEquals("Save", selenium.getValue("//input[@value='Save']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForVisible("//span[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//span[@class='portlet-msg-success']"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Open Site Name"),
			selenium.getText(
				"//li[contains(@class, 'social-office-enabled')]/span[2]/a"));
	}
}