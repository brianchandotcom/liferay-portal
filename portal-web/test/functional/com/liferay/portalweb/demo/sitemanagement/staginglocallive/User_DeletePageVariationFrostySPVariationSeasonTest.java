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

package com.liferay.portalweb.demo.sitemanagement.staginglocallive;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_DeletePageVariationFrostySPVariationSeasonTest
	extends BaseTestCase {
	public void testUser_DeletePageVariationFrostySPVariationSeason()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.waitForElementPresent("link=Site Name");
				selenium.clickAt("link=Site Name",
					RuntimeVariables.replace("Site Name"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//body[contains(@class,'live-view')]"));
				assertTrue(selenium.isElementNotPresent(
						"//body[contains(@class,'local-staging')]"));
				assertEquals(RuntimeVariables.replace("Staging"),
					selenium.getText(
						"//div[@class='staging-bar']/ul/li[2]/span/a"));
				selenium.clickAt("//div[@class='staging-bar']/ul/li[2]/span/a",
					RuntimeVariables.replace("Staging"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//body[contains(@class,'local-staging')]"));
				assertTrue(selenium.isElementNotPresent(
						"//body[contains(@class,'live-view')]"));

				boolean seasonPresent = selenium.isElementPresent("link=Season");

				if (!seasonPresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=Season",
					RuntimeVariables.replace("Season"));
				selenium.waitForPageToLoad("30000");

			case 2:
				assertEquals(RuntimeVariables.replace(
						"Season Site Pages Variation of Site Name"),
					selenium.getText(
						"//span[@class='layout-set-branch-description']"));
				assertEquals(RuntimeVariables.replace("Frosty"),
					selenium.getText("//form/div/ul/li[3]/span/a"));
				assertEquals(RuntimeVariables.replace("Manage Page Variations"),
					selenium.getText(
						"//a[@id='_170_manageLayoutRevisions']/span"));
				selenium.clickAt("//a[@id='_170_manageLayoutRevisions']/span",
					RuntimeVariables.replace("Manage Page Variations"));
				Thread.sleep(5000);
				selenium.selectFrame(
					"//div[@class='yui3-widget-bd panel-bd dialog-bd dialog-iframe-bd']/iframe");
				selenium.waitForVisible(
					"//span[@title='Actions']/ul/li/strong/a/span");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForText("//div[@class='lfr-menu-list unstyled']/ul/li[2]/a[contains(.,'Delete')]",
					"Delete");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a[contains(.,'Delete')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a[contains(.,'Delete')]"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				assertEquals(RuntimeVariables.replace(
						"Page variation was deleted."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.selectFrame("relative=top");

			case 100:
				label = -1;
			}
		}
	}
}