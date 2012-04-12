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

package com.liferay.portalweb.socialofficehome.sites.site.viewsitesdirectory;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewSitesDirectoryTest extends BaseTestCase {
	public void testViewSitesDirectory() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/user/joebloggs/so/dashboard/");
				loadRequiredJavaScriptModules();

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//li[contains(@class, 'selected')]/a/span")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Home"),
					selenium.getText(
						"//li[contains(@class, 'selected')]/a/span"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//button[contains(.,'More Sites')]/span[2]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("More Sites"),
					selenium.getText(
						"//button[contains(.,'More Sites')]/span[2]"));
				selenium.clickAt("//button[contains(.,'More Sites')]/span[2]",
					RuntimeVariables.replace("More Sites"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"xPath=(//h1[@class='header-title']/span)[1]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Directory"),
					selenium.getText(
						"xPath=(//h1[@class='header-title']/span)[1]"));

				boolean mySitesChecked = selenium.isChecked(
						"//input[@id='_5_WAR_soportlet_userSites']");

				if (!mySitesChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_5_WAR_soportlet_userSites']",
					RuntimeVariables.replace("My Sites"));

			case 2:
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Liferay"),
					selenium.getText("xPath=(//span[@class='name']/a)[1]"));
				assertEquals(RuntimeVariables.replace("Open Site1 Name"),
					selenium.getText("xPath=(//span[@class='name']/a)[2]"));
				assertEquals(RuntimeVariables.replace("Open Site1 Description"),
					selenium.getText("xPath=(//span[@class='description'])[2]"));
				assertEquals(RuntimeVariables.replace("Restricted Site3 Name"),
					selenium.getText("xPath=(//span[@class='name']/a)[3]"));
				assertEquals(RuntimeVariables.replace(
						"Restricted Site3 Description"),
					selenium.getText("xPath=(//span[@class='description'])[3]"));
				assertFalse(selenium.isPartialText(
						"//ul[@class='directory-list']", "Private Site2 Name"));

			case 100:
				label = -1;
			}
		}
	}
}