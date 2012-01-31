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

package com.liferay.portalweb.portlet.blogs.usecase.demo1;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigureSeverAdministrationMailTest extends BaseTestCase {
	public void testConfigureSeverAdministrationMail()
		throws Exception {
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
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Server Administration",
			RuntimeVariables.replace("Server Administration"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Mail", RuntimeVariables.replace("Mail"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@id='_137_pop3Host']",
			RuntimeVariables.replace("pop.gmail.com"));
		selenium.type("//input[@id='_137_pop3Port']",
			RuntimeVariables.replace("995"));
		selenium.clickAt("//input[@id='_137_pop3SecureCheckbox']",
			RuntimeVariables.replace("Use a Secure Network Connection"));
		selenium.type("//input[@id='_137_pop3User']",
			RuntimeVariables.replace("liferay.qa.testing.trunk@gmail.com"));
		selenium.type("//input[@id='_137_pop3Password']",
			RuntimeVariables.replace("loveispatient"));
		selenium.type("//input[@id='_137_smtpHost']",
			RuntimeVariables.replace("smtp.gmail.com"));
		selenium.type("//input[@id='_137_smtpPort']",
			RuntimeVariables.replace("465"));
		selenium.clickAt("//input[@id='_137_smtpSecureCheckbox']",
			RuntimeVariables.replace("Use a Secure Network Connection"));
		selenium.type("//input[@id='_137_smtpUser']",
			RuntimeVariables.replace("liferay.qa.testing.trunk@gmail.com"));
		selenium.type("//input[@id='_137_smtpPassword']",
			RuntimeVariables.replace("loveispatient"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}