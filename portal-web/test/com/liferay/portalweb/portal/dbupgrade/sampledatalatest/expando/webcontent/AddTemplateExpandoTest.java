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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.expando.webcontent;

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
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/expando-web-content-community/");
				selenium.waitForVisible("link=Web Content Display Page");
				selenium.clickAt("link=Web Content Display Page",
					RuntimeVariables.replace("Web Content Display Page"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Web Content",
					RuntimeVariables.replace("Web Content"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Templates",
					RuntimeVariables.replace("Templates"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//input[@value='Add Template']",
					RuntimeVariables.replace("Add Template"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_15_name_en_US']",
					RuntimeVariables.replace("Expando Template Test"));
				selenium.type("//textarea[@id='_15_description_en_US']",
					RuntimeVariables.replace(
						"This is an expando template test."));

				boolean cacheableChecked = selenium.isChecked(
						"_15_cacheableCheckbox");

				if (!cacheableChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_15_cacheableCheckbox']",
					RuntimeVariables.replace("Cacheable Checkbox"));

			case 2:
				selenium.clickAt("//input[@value='Select']",
					RuntimeVariables.replace("Select"));
				Thread.sleep(5000);
				selenium.waitForVisible("//td[2]/a");
				selenium.clickAt("//td[2]/a",
					RuntimeVariables.replace(
						"Expando Structure Test\nThis is an expando structure test."));
				selenium.waitForText("//a[@id='_15_structureName']",
					"Expando Structure Test");
				assertEquals(RuntimeVariables.replace("Expando Structure Test"),
					selenium.getText("//a[@id='_15_structureName']"));
				selenium.type("//input[@id='_15_xsl']",
					RuntimeVariables.replace(
						"L:\\portal\\build\\portal-web\\test\\com\\liferay\\portalweb\\portal\\dbupgrade\\sampledata610\\expando\\webcontent\\dependencies\\Expando.htm"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace("Expando Template Test"),
					selenium.getText(
						"//tr[@class='portlet-section-body results-row last']/td[3]"));

			case 100:
				label = -1;
			}
		}
	}
}