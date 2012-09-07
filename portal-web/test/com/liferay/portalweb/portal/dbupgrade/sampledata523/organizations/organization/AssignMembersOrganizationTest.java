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

package com.liferay.portalweb.portal.dbupgrade.sampledata523.organizations.organization;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssignMembersOrganizationTest extends BaseTestCase {
	public void testAssignMembersOrganization() throws Exception {
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
				selenium.clickAt("link=Organizations",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean basicVisible = selenium.isVisible("link=\u00ab Basic");

				if (!basicVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=\u00ab Basic",
					RuntimeVariables.replace(""));

			case 2:
				selenium.type("_126_keywords",
					RuntimeVariables.replace("organization sample test"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//strong/span", RuntimeVariables.replace(""));
				selenium.waitForElementPresent("link=Assign Members");
				selenium.clickAt("link=Assign Members",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Available", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean basic2Visible = selenium.isVisible("link=\u00ab Basic");

				if (!basic2Visible) {
					label = 3;

					continue;
				}

				selenium.clickAt("link=\u00ab Basic",
					RuntimeVariables.replace(""));

			case 3:
				selenium.type("_126_keywords",
					RuntimeVariables.replace("Joe Bloggs"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean userChecked = selenium.isChecked("_126_rowIds");

				if (userChecked) {
					label = 4;

					continue;
				}

				selenium.clickAt("_126_rowIds", RuntimeVariables.replace(""));

			case 4:
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));
				selenium.clickAt("link=Current", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Joe Bloggs"),
					selenium.getText(
						"//tr[@class='portlet-section-body results-row']/td[2]"));
				assertEquals(RuntimeVariables.replace("joebloggs"),
					selenium.getText(
						"//tr[@class='portlet-section-body results-row']/td[3]"));

			case 100:
				label = -1;
			}
		}
	}
}