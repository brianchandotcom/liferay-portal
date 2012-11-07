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

package com.liferay.portalweb.portlet.blogs.portlet.configureportletremoveallpermissions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownPortletRemoveAllPermissionTest extends BaseTestCase {
	public void testTearDownPortletRemoveAllPermission()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Blogs Test Page",
					RuntimeVariables.replace("Blogs Test Page"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Options"),
					selenium.getText("//span[@title='Options']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
					RuntimeVariables.replace("Options"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Configuration')]");
				assertEquals(RuntimeVariables.replace("Configuration"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Configuration')]"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Configuration')]",
					RuntimeVariables.replace("Configuration"));
				selenium.waitForVisible(
					"//iframe[@id='_33_configurationIframeDialog']");
				selenium.selectFrame(
					"//iframe[@id='_33_configurationIframeDialog']");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/navigation_interaction.js')]");
				selenium.waitForVisible("link=Permissions");
				selenium.clickAt("link=Permissions",
					RuntimeVariables.replace("Permissions"));
				selenium.waitForPageToLoad("30000");

				boolean guestViewNotChecked = selenium.isChecked(
						"//td[4]/input");

				if (guestViewNotChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//td[4]/input",
					RuntimeVariables.replace("Guest View"));

			case 2:
				assertTrue(selenium.isChecked("//td[4]/input"));

				boolean ownerAddToPageNotChecked = selenium.isChecked(
						"//tr[4]/td[2]/input");

				if (ownerAddToPageNotChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//tr[4]/td[2]/input",
					RuntimeVariables.replace("Owner Add to Page"));

			case 3:
				assertTrue(selenium.isChecked("//tr[4]/td[2]/input"));

				boolean ownerConfigurationNotChecked = selenium.isChecked(
						"//tr[4]/td[3]/input");

				if (ownerConfigurationNotChecked) {
					label = 4;

					continue;
				}

				selenium.clickAt("//tr[4]/td[3]/input",
					RuntimeVariables.replace("Owner Configuration"));

			case 4:
				assertTrue(selenium.isChecked("//tr[4]/td[3]/input"));

				boolean ownerViewNotChecked = selenium.isChecked(
						"//tr[4]/td[4]/input");

				if (ownerViewNotChecked) {
					label = 5;

					continue;
				}

				selenium.clickAt("//tr[4]/td[4]/input",
					RuntimeVariables.replace("Owner View"));

			case 5:
				assertTrue(selenium.isChecked("//tr[4]/td[4]/input"));

				boolean ownerPermissionsNotChecked = selenium.isChecked(
						"//tr[4]/td[5]/input");

				if (ownerPermissionsNotChecked) {
					label = 6;

					continue;
				}

				selenium.clickAt("//tr[4]/td[5]/input",
					RuntimeVariables.replace("Owner Permissions"));

			case 6:
				assertTrue(selenium.isChecked("//tr[4]/td[5]/input"));

				boolean siteMemberPermissionsNotChecked = selenium.isChecked(
						"//tr[7]/td[4]/input");

				if (siteMemberPermissionsNotChecked) {
					label = 7;

					continue;
				}

				selenium.clickAt("//tr[7]/td[4]/input",
					RuntimeVariables.replace("Site Member Permissions"));

			case 7:
				assertTrue(selenium.isChecked("//tr[7]/td[4]/input"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertTrue(selenium.isChecked("//td[4]/input"));
				assertTrue(selenium.isChecked("//tr[4]/td[2]/input"));
				assertTrue(selenium.isChecked("//tr[4]/td[3]/input"));
				assertTrue(selenium.isChecked("//tr[4]/td[4]/input"));
				assertTrue(selenium.isChecked("//tr[4]/td[5]/input"));
				assertTrue(selenium.isChecked("//tr[7]/td[4]/input"));
				selenium.selectFrame("relative=top");

			case 100:
				label = -1;
			}
		}
	}
}