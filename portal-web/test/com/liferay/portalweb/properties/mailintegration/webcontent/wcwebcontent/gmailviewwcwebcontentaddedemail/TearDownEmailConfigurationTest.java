/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.properties.mailintegration.webcontent.wcwebcontent.gmailviewwcwebcontentaddedemail;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownEmailConfigurationTest extends BaseTestCase {
	public void testTearDownEmailConfiguration() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Control Panel")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Web Content",
					RuntimeVariables.replace("Web Content"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Options"),
					selenium.getText("//span[@title='Options']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
					RuntimeVariables.replace("Options"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Configuration"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a"));
				selenium.click(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Email From")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Email From",
					RuntimeVariables.replace("Email From"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_86_emailFromName']",
					RuntimeVariables.replace("Joe Bloggs"));
				selenium.type("//input[@id='_86_emailFromAddress']",
					RuntimeVariables.replace("test@liferay.com"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"You have successfully updated the setup."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.clickAt("link=Web Content Added Email",
					RuntimeVariables.replace("Web Content Added Email"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//input[@id='_86_emailArticleAddedEnabledCheckbox']"));

				boolean webContentAddedChecked = selenium.isChecked(
						"_86_emailArticleAddedEnabledCheckbox");

				if (!webContentAddedChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_86_emailArticleAddedEnabledCheckbox']",
					RuntimeVariables.replace("Enabled"));
				Thread.sleep(5000);
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"You have successfully updated the setup."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 2:
				selenium.clickAt("link=Web Content Denied Email",
					RuntimeVariables.replace("Web Content Denied Email"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//input[@id='_86_emailArticleApprovalDeniedEnabledCheckbox']"));

				boolean webContentDeniedChecked = selenium.isChecked(
						"_86_emailArticleApprovalDeniedEnabledCheckbox");

				if (!webContentDeniedChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@id='_86_emailArticleApprovalDeniedEnabledCheckbox']",
					RuntimeVariables.replace("Enabled"));
				Thread.sleep(5000);
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"You have successfully updated the setup."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 3:
				selenium.clickAt("link=Web Content Granted Email",
					RuntimeVariables.replace("Web Content Granted Email"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//input[@id='_86_emailArticleApprovalGrantedEnabledCheckbox']"));

				boolean webContentGrantedChecked = selenium.isChecked(
						"_86_emailArticleApprovalGrantedEnabledCheckbox");

				if (!webContentGrantedChecked) {
					label = 4;

					continue;
				}

				selenium.clickAt("//input[@id='_86_emailArticleApprovalGrantedEnabledCheckbox']",
					RuntimeVariables.replace("Enabled"));
				Thread.sleep(5000);
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"You have successfully updated the setup."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 4:
				selenium.clickAt("link=Web Content Requested Email",
					RuntimeVariables.replace("Web Content Requested Email"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//input[@id='_86_emailArticleApprovalRequestedEnabledCheckbox']"));

				boolean webContentRequestedChecked = selenium.isChecked(
						"_86_emailArticleApprovalRequestedEnabledCheckbox");

				if (!webContentRequestedChecked) {
					label = 5;

					continue;
				}

				selenium.clickAt("//input[@id='_86_emailArticleApprovalRequestedEnabledCheckbox']",
					RuntimeVariables.replace("Enabled"));
				Thread.sleep(5000);
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"You have successfully updated the setup."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 5:
				selenium.clickAt("link=Web Content Review Email",
					RuntimeVariables.replace("Web Content Review Email"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//input[@id='_86_emailArticleReviewEnabledCheckbox']"));

				boolean webContentReviewChecked = selenium.isChecked(
						"_86_emailArticleReviewEnabledCheckbox");

				if (!webContentReviewChecked) {
					label = 6;

					continue;
				}

				selenium.clickAt("//input[@id='_86_emailArticleReviewEnabledCheckbox']",
					RuntimeVariables.replace("Enabled"));
				Thread.sleep(5000);
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"You have successfully updated the setup."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 6:
				selenium.clickAt("link=Web Content Updated Email",
					RuntimeVariables.replace("Web Content Updated Email"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//input[@id='_86_emailArticleUpdatedEnabledCheckbox']"));

				boolean webContentUpdatedChecked = selenium.isChecked(
						"_86_emailArticleUpdatedEnabledCheckbox");

				if (!webContentUpdatedChecked) {
					label = 7;

					continue;
				}

				selenium.clickAt("//input[@id='_86_emailArticleUpdatedEnabledCheckbox']",
					RuntimeVariables.replace("Enabled"));
				Thread.sleep(5000);
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"You have successfully updated the setup."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 7:
				selenium.click("//button[@id='closethick']");

			case 100:
				label = -1;
			}
		}
	}
}