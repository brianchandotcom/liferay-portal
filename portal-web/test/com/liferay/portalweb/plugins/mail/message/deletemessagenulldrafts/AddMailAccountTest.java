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

package com.liferay.portalweb.plugins.mail.message.deletemessagenulldrafts;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddMailAccountTest extends BaseTestCase {
	public void testAddMailAccount() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home");
		selenium.waitForVisible("link=Mail Test Page");
		selenium.clickAt("link=Mail Test Page",
			RuntimeVariables.replace("Mail Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Mail Account']",
			RuntimeVariables.replace("Add Mail Account"));
		selenium.waitForVisible("//input[@id='_1_WAR_mailportlet_address']");
		selenium.type("//input[@id='_1_WAR_mailportlet_address']",
			RuntimeVariables.replace("liferay.qa.testing.trunk@gmail.com"));
		selenium.type("//input[@id='_1_WAR_mailportlet_password']",
			RuntimeVariables.replace("loveispatient"));
		assertFalse(selenium.isChecked(
				"//input[@id='_1_WAR_mailportlet_savePasswordCheckbox']"));
		selenium.check("//input[@id='_1_WAR_mailportlet_savePasswordCheckbox']");
		assertTrue(selenium.isChecked(
				"//input[@id='_1_WAR_mailportlet_savePasswordCheckbox']"));
		selenium.clickAt("//input[@value='Add Account']",
			RuntimeVariables.replace("Add Account"));
		selenium.waitForVisible("//span[@class='message portlet-msg-success']");
		assertEquals(RuntimeVariables.replace("Account has been created."),
			selenium.getText("//span[@class='message portlet-msg-success']"));
	}
}