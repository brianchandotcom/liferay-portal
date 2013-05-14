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

package com.liferay.portalweb.demo.fundamentals.socialequityblogs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EnableSocialActivityBlogsEntryTest extends BaseTestCase {
	public void testEnableSocialActivityBlogsEntry() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Social Activity",
			RuntimeVariables.replace("Social Activity"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertFalse(selenium.isChecked(
				"//input[@id='_179_com.liferay.portlet.blogs.model.BlogsEntry.enabledCheckbox']"));
		selenium.clickAt("//input[@id='_179_com.liferay.portlet.blogs.model.BlogsEntry.enabledCheckbox']",
			RuntimeVariables.replace("Blogs Entry"));
		assertTrue(selenium.isChecked(
				"//input[@id='_179_com.liferay.portlet.blogs.model.BlogsEntry.enabledCheckbox']"));
		selenium.clickAt("link=Blogs Entry",
			RuntimeVariables.replace("Blogs Entry"));
		selenium.waitForVisible(
			"//div[@class='settings-display-content']/ul/li[2]/div/span");
		assertEquals(RuntimeVariables.replace("Adds a Comment"),
			selenium.getText(
				"//div[@class='settings-display-content']/ul/li[2]/div/span"));
		selenium.select("//select[@id='ADD_COMMENT_participationIncrement']",
			RuntimeVariables.replace("2"));
		selenium.select("//select[@id='ADD_COMMENT_contributionIncrement']",
			RuntimeVariables.replace("2"));
		assertEquals(RuntimeVariables.replace("Limit"),
			selenium.getText(
				"//div[@class='settings-display-content']/ul/li[2]/div/div/div[2]/a[1]/span"));
		selenium.clickAt("//div[@class='settings-display-content']/ul/li[2]/div/div/div[2]/a[1]/span",
			RuntimeVariables.replace("Limit"));
		selenium.waitForVisible(
			"//select[@id='ADD_COMMENT_contributionLimitValue']");
		selenium.select("//select[@id='ADD_COMMENT_contributionLimitValue']",
			RuntimeVariables.replace("2"));
		assertEquals(RuntimeVariables.replace("Reads a Blog"),
			selenium.getText(
				"//div[@class='settings-display-content']/ul/li[3]/div/span"));
		selenium.clickAt("//div[@class='settings-display-content']/ul/li[3]/div/div/div[2]/a[2]/span",
			RuntimeVariables.replace("Close"));
		selenium.waitForVisible(
			"//ul[@class='settings-actions']/li[2]/div/span");
		assertEquals(RuntimeVariables.replace("Reads a Blog"),
			selenium.getText("//ul[@class='settings-actions']/li[2]/div/span"));
		assertEquals(RuntimeVariables.replace("Subscribes to a Blog"),
			selenium.getText(
				"//div[@class='settings-display-content']/ul/li[3]/div/span"));
		selenium.clickAt("//div[@class='settings-display-content']/ul/li[3]/div/div/div[2]/a[2]/span",
			RuntimeVariables.replace("Close"));
		selenium.waitForText("//ul[@class='settings-actions']/li[3]/div/span",
			"Subscribes to a Blog");
		assertEquals(RuntimeVariables.replace("Subscribes to a Blog"),
			selenium.getText("//ul[@class='settings-actions']/li[3]/div/span"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}