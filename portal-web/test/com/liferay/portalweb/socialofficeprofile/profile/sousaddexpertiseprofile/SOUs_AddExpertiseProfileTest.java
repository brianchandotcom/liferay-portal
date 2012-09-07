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

package com.liferay.portalweb.socialofficeprofile.profile.sousaddexpertiseprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_AddExpertiseProfileTest extends BaseTestCase {
	public void testSOUs_AddExpertiseProfile() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/socialoffice01/so/profile");
				selenium.waitForVisible("//div[@class='lfr-contact-name']/a");
				assertEquals(RuntimeVariables.replace(
						"Social01 Office01 User01"),
					selenium.getText("//div[@class='lfr-contact-name']/a"));
				assertEquals(RuntimeVariables.replace(
						"To complete your profile, please add:"),
					selenium.getText(
						"//p[@class='portlet-msg portlet-msg-info']"));
				assertEquals(RuntimeVariables.replace("Add"),
					selenium.getText("//li[@data-title='Projects']"));
				selenium.clickAt("//li[@data-title='Projects']",
					RuntimeVariables.replace("Add"));
				selenium.waitForVisible(
					"//input[contains(@id,'_2_projectsEntryTitle0')]");
				selenium.type("//input[contains(@id,'_2_projectsEntryTitle0')]",
					RuntimeVariables.replace("Expertise Title"));
				selenium.select("//select[contains(@id,'_2_projectsEntryStartDateMonth0')]",
					RuntimeVariables.replace("January"));

				boolean currentExpertiseChecked = selenium.isChecked(
						"_2_projectsEntryCurrent0Checkbox");

				if (currentExpertiseChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_2_projectsEntryCurrent0Checkbox']",
					RuntimeVariables.replace("Enabled"));

			case 2:
				selenium.waitForVisible(
					"//textarea[@id='_2_projectsEntryDescription0']");
				selenium.type("//textarea[@id='_2_projectsEntryDescription0']",
					RuntimeVariables.replace("Expertise Description"));
				Thread.sleep(5000);
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForVisible("//div[@data-title='Projects']/div/h3");
				assertEquals(RuntimeVariables.replace("Expertise Title:"),
					selenium.getText("//div[@data-title='Projects']/div/h3"));
				assertEquals(RuntimeVariables.replace("01 Jan 2012 - Current"),
					selenium.getText(
						"//div[@class='project-date property-list']"));
				assertEquals(RuntimeVariables.replace("Expertise Description"),
					selenium.getText(
						"//div[@class='project-description property-list']"));

			case 100:
				label = -1;
			}
		}
	}
}