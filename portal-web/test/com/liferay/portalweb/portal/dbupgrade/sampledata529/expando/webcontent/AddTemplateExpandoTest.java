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

package com.liferay.portalweb.portal.dbupgrade.sampledata529.expando.webcontent;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddTemplateExpandoTest extends BaseTestCase {
	public void testAddTemplateExpando() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");
				loadRequiredJavaScriptModules();

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
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				selenium.clickAt("link=Communities",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				selenium.type("_134_name",
					RuntimeVariables.replace("Expando Web Content Community"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				selenium.clickAt("//tr[@class='portlet-section-body results-row last']/td[1]/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				selenium.clickAt("link=Web Content",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				selenium.clickAt("link=Templates", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				selenium.clickAt("//input[@value='Add Template']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				selenium.type("_15_newTemplateId",
					RuntimeVariables.replace("test_expando"));
				selenium.type("_15_name",
					RuntimeVariables.replace("Expando Template Test"));
				selenium.type("_15_description",
					RuntimeVariables.replace(
						"This is an expando template test."));

				boolean cacheableChecked = selenium.isChecked(
						"_15_cacheableCheckbox");

				if (!cacheableChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("_15_cacheableCheckbox",
					RuntimeVariables.replace(""));

			case 2:
				selenium.clickAt("//input[@value='Select']",
					RuntimeVariables.replace(""));
				selenium.waitForPopUp("structure",
					RuntimeVariables.replace("30000"));
				selenium.selectWindow("name=structure");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=TEST_EXPANDO")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("link=TEST_EXPANDO");
				selenium.selectWindow("null");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("Expando Structure Test")
												.equals(selenium.getText(
										"_15_structureName"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Expando Structure Test"),
					selenium.getText("_15_structureName"));
				selenium.type("_15_xsl",
					RuntimeVariables.replace(
						"L:\\portal\\build\\portal-web\\test\\com\\liferay\\portalweb\\portal\\dbupgrade\\sampledata529\\expando\\webcontent\\dependencies\\Expando.htm"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));
				assertEquals(RuntimeVariables.replace("TEST_EXPANDO"),
					selenium.getText(
						"//tr[@class='portlet-section-body results-row last']/td[2]"));
				assertEquals(RuntimeVariables.replace(
						"Expando Template Test\n This is an expando template test."),
					selenium.getText(
						"//tr[@class='portlet-section-body results-row last']/td[3]"));

			case 100:
				label = -1;
			}
		}
	}
}