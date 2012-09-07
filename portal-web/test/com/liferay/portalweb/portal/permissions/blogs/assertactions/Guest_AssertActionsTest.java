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

package com.liferay.portalweb.portal.permissions.blogs.assertactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Guest_AssertActionsTest extends BaseTestCase {
	public void testGuest_AssertActions() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Blogs Permissions Page");
		selenium.clickAt("link=Blogs Permissions Page",
			RuntimeVariables.replace("Blogs Permissions Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("//input[@value='Search']"));
		assertTrue(selenium.isElementPresent(
				"link=Permissions Blogs Test Entry"));
		assertTrue(selenium.isElementNotPresent("link=Edit"));
		assertTrue(selenium.isElementNotPresent("link=Permissions"));
		assertTrue(selenium.isElementNotPresent("link=Delete"));
		assertTrue(selenium.isElementNotPresent(
				"//input[@value='Add Blog Entry']"));
		selenium.clickAt("link=Permissions Blogs Test Entry",
			RuntimeVariables.replace("Permissions Blogs Test Entry"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Add Comment"),
			selenium.getText(
				"//fieldset[contains(@class,'add-comment')]/div/span/a"));
		selenium.click("//fieldset[contains(@class,'add-comment')]/div/span/a");
		selenium.waitForVisible("//textarea[@name='_33_postReplyBody0']");
		assertTrue(selenium.isElementNotPresent("//input[@value='Reply']"));
		assertTrue(selenium.isElementNotPresent("link=Edit"));
		assertTrue(selenium.isElementNotPresent("link=Permissions"));
		assertTrue(selenium.isElementNotPresent("link=Delete"));
		assertTrue(selenium.isElementPresent("link=Sign in to vote."));
		selenium.clickAt("//input[@value='Reply as...']",
			RuntimeVariables.replace("Reply as..."));
		selenium.waitForVisible("//iframe[@id='_33_']");
		selenium.selectFrame("//iframe[@id='_33_']");
		selenium.waitForVisible("//label[@for='_164_login']");
		assertEquals(RuntimeVariables.replace("Email Address"),
			selenium.getText("//label[@for='_164_login']"));
		assertTrue(selenium.isVisible("//input[@id='_164_login']"));
		assertEquals(RuntimeVariables.replace("Password"),
			selenium.getText("//label[@for='_164_password']"));
		assertTrue(selenium.isVisible("//input[@id='_164_password']"));
		assertFalse(selenium.isChecked("//input[@id='_164_rememberMeCheckbox']"));
		assertEquals(RuntimeVariables.replace("Remember Me"),
			selenium.getText("//label[@for='_164_rememberMeCheckbox']"));
		assertTrue(selenium.isVisible("//input[@value='Sign In']"));
		selenium.selectFrame("relative=top");
		selenium.clickAt("//button[@id='closethick']",
			RuntimeVariables.replace(""));
	}
}