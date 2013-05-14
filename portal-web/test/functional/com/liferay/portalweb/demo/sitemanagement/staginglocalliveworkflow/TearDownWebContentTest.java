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

package com.liferay.portalweb.demo.sitemanagement.staginglocalliveworkflow;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownWebContentTest extends BaseTestCase {
	public void testTearDownWebContent() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/community-site-test/home");
				selenium.waitForElementPresent("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//strong/a",
					RuntimeVariables.replace("liferay.com"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a");
				assertEquals(RuntimeVariables.replace("Community Site Test"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Web Content",
					RuntimeVariables.replace("Web Content"));
				selenium.waitForPageToLoad("30000");

				boolean webContentPresent = selenium.isElementPresent(
						"_15_rowIds");

				if (!webContentPresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@name='_15_allRowIds']",
					RuntimeVariables.replace("Select All"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected web content[\\s\\S]$"));

			case 2:
				selenium.clickAt("link=Templates",
					RuntimeVariables.replace("Templates"));
				selenium.waitForPageToLoad("30000");

				boolean templatePresent = selenium.isElementPresent(
						"_15_rowIds");

				if (!templatePresent) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@name='_15_allRowIds']",
					RuntimeVariables.replace("Select All"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected templates[\\s\\S]$"));

			case 3:
				selenium.clickAt("link=Structures",
					RuntimeVariables.replace("Structures"));
				selenium.waitForPageToLoad("30000");

				boolean structurePresent = selenium.isElementPresent(
						"_15_rowIds");

				if (!structurePresent) {
					label = 4;

					continue;
				}

				selenium.clickAt("//input[@name='_15_allRowIds']",
					RuntimeVariables.replace("Select All"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected structures[\\s\\S]$"));

			case 4:
			case 100:
				label = -1;
			}
		}
	}
}