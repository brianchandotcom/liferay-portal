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

package com.liferay.portalweb.socialofficeprofile.profile.souseditwebsiteprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_EditWebsiteProfileTest extends BaseTestCase {
	public void testSOUs_EditWebsiteProfile() throws Exception {
		selenium.open("/web/socialoffice01/so/profile");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[@class='lfr-contact-name']/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText("//div[@class='lfr-contact-name']/a"));
		assertEquals(RuntimeVariables.replace("Websites:"),
			selenium.getText("//div[@data-title='Websites']/h3"));
		assertEquals(RuntimeVariables.replace("Personal"),
			selenium.getText("//div[@data-title='Websites']/ul/li/span"));
		assertEquals(RuntimeVariables.replace("http://www.socialoffice01.com"),
			selenium.getText("//div[@data-title='Websites']/ul/li/span[2]"));
		selenium.clickAt("//div[@data-title='Websites']",
			RuntimeVariables.replace("Websites:"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//input[contains(@id,'websiteUrl')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("//input[contains(@id,'websiteUrl')]",
			RuntimeVariables.replace("http://www.socialoffice01edit.com"));
		selenium.select("//select[contains(@id,'websiteType')]",
			RuntimeVariables.replace("Personal"));
		selenium.clickAt("//input[contains(@id,'websitePrimary')]",
			RuntimeVariables.replace("Primary"));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace(
							"http://www.socialoffice01edit.com")
										.equals(selenium.getText(
								"//div[@data-title='Websites']/ul/li/span[2]"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Websites:"),
			selenium.getText("//div[@data-title='Websites']/h3"));
		assertEquals(RuntimeVariables.replace("Personal"),
			selenium.getText("//div[@data-title='Websites']/ul/li/span"));
		assertEquals(RuntimeVariables.replace(
				"http://www.socialoffice01edit.com"),
			selenium.getText("//div[@data-title='Websites']/ul/li/span[2]"));
	}
}