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

package com.liferay.portalweb.portlet.messageboards.message.userviewmbthreadmessagegmail;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletCategoryMailingListActiveTest extends BaseTestCase {
	public void testConfigurePortletCategoryMailingListActive()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Site Name", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Message Boards Test Page",
					RuntimeVariables.replace("Message Boards Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//td[5]/span/ul/li/strong/a/span"));
				selenium.clickAt("//td[5]/span/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Edit"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a"));
				selenium.click(
					"//div[@class='lfr-component lfr-menu-list']/ul/li/a");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//input[@id='_19_mailingListActiveCheckbox']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();

				boolean MailingListActiveChecked = selenium.isChecked(
						"_19_mailingListActiveCheckbox");

				if (MailingListActiveChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_19_mailingListActiveCheckbox']",
					RuntimeVariables.replace("Active"));

			case 2:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//input[@id='_19_emailAddress']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.type("//input[@id='_19_emailAddress']",
					RuntimeVariables.replace(
						"liferay-mailinglist@googlegroups.com"));
				selenium.saveScreenShotAndSource();

				boolean ProtocolPopChecked = selenium.isChecked(
						"_19_inProtocol");

				if (ProtocolPopChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@name='_19_inProtocol']",
					RuntimeVariables.replace("POP"));

			case 3:
				selenium.type("//input[@id='_19_inServerName']",
					RuntimeVariables.replace("pop.gmail.com"));
				selenium.saveScreenShotAndSource();
				selenium.type("//input[@id='_19_inServerPort']",
					RuntimeVariables.replace("995"));
				selenium.saveScreenShotAndSource();

				boolean SecureNetworkConnectionChecked = selenium.isChecked(
						"_19_inUseSSLCheckbox");

				if (SecureNetworkConnectionChecked) {
					label = 4;

					continue;
				}

				selenium.clickAt("//input[@id='_19_inUseSSLCheckbox']",
					RuntimeVariables.replace("Use a Secure Network Connection"));

			case 4:
				selenium.type("//input[@id='_19_inUserName']",
					RuntimeVariables.replace(
						"liferay.qa.server.trunk@gmail.com"));
				selenium.saveScreenShotAndSource();
				selenium.type("//input[@id='_19_inPassword']",
					RuntimeVariables.replace("loveispatient"));
				selenium.saveScreenShotAndSource();
				selenium.type("//input[@id='_19_inReadInterval']",
					RuntimeVariables.replace("1"));
				selenium.saveScreenShotAndSource();
				selenium.type("//input[@id='_19_outEmailAddress']",
					RuntimeVariables.replace(
						"liferay.qa.server.trunk@gmail.com"));
				selenium.saveScreenShotAndSource();

				boolean UseCustomOutgoingServerChecked = selenium.isChecked(
						"_19_outCustomCheckbox");

				if (UseCustomOutgoingServerChecked) {
					label = 5;

					continue;
				}

				selenium.clickAt("//input[@id='_19_outCustomCheckbox']",
					RuntimeVariables.replace("Use Customer Outgoing Server"));

			case 5:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//input[@id='_19_outEmailAddress']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.type("//input[@id='_19_outEmailAddress']",
					RuntimeVariables.replace(
						"liferay.qa.server.trunk@gmail.com"));
				selenium.saveScreenShotAndSource();
				selenium.type("//input[@id='_19_outServerName']",
					RuntimeVariables.replace("smtp.gmail.com"));
				selenium.saveScreenShotAndSource();
				selenium.type("//input[@id='_19_outServerPort']",
					RuntimeVariables.replace("465"));
				selenium.saveScreenShotAndSource();

				boolean OutgoingSecureNetworkConnectionChecked = selenium.isChecked(
						"_19_outUseSSLCheckbox");

				if (OutgoingSecureNetworkConnectionChecked) {
					label = 6;

					continue;
				}

				selenium.clickAt("//input[@id='_19_outUseSSLCheckbox']",
					RuntimeVariables.replace("Use a Secure Network Connection"));

			case 6:
				selenium.type("//input[@id='_19_outUserName']",
					RuntimeVariables.replace(
						"liferay.qa.server.trunk@gmail.com"));
				selenium.saveScreenShotAndSource();
				selenium.type("//input[@id='_19_outPassword']",
					RuntimeVariables.replace("loveispatient"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 100:
				label = -1;
			}
		}
	}
}