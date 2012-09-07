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

package com.liferay.portalweb.portal.dbupgrade.sampledata529.wiki.wikipage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class CleanupWikiNodeTest extends BaseTestCase {
	public void testCleanupWikiNode() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.waitForElementPresent("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Communities",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_134_name",
					RuntimeVariables.replace("Wiki Wiki Page Community"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//td[1]/a", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.waitForElementPresent("link=Wiki Page Test");
				selenium.clickAt("link=Wiki Page Test",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//img[@alt='Manage Wikis']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean wikiNodePresent = selenium.isElementPresent(
						"//tr[4]/td[4]/ul/li/strong/span");

				if (!wikiNodePresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("//tr[4]/td[4]/ul/li/strong/span",
					RuntimeVariables.replace(""));
				selenium.waitForElementPresent(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a");
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 2:
			case 100:
				label = -1;
			}
		}
	}
}