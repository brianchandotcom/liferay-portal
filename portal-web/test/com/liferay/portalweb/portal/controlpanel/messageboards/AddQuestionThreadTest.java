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

package com.liferay.portalweb.portal.controlpanel.messageboards;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddQuestionThreadTest extends BaseTestCase {
	public void testAddQuestionThread() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Message Boards",
			RuntimeVariables.replace("Message Boards"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"T\u00e9st Cat\u00e9gory Edit\u00e9d"),
			selenium.getText("//tr[4]/td[2]/a[1]/strong"));
		selenium.clickAt("//tr[4]/td[2]/a[1]/strong",
			RuntimeVariables.replace("T\u00e9st Cat\u00e9gory Edit\u00e9d"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Post New Thread']",
			RuntimeVariables.replace("Post New Thread"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isChecked("//input[@name='_162_questionCheckbox']"));
		selenium.clickAt("//input[@name='_162_questionCheckbox']",
			RuntimeVariables.replace("Mark as a Question Checkbox"));
		assertTrue(selenium.isChecked("//input[@name='_162_questionCheckbox']"));
		selenium.type("//input[@name='_162_subject']",
			RuntimeVariables.replace("Favorite Color Test Question"));
		selenium.waitForElementPresent(
			"//textarea[@id='_162_editor' and @style='display: none;']");
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[@id='cke_34_label' and .='Source']"));
		selenium.clickAt("//span[@id='cke_34_label' and .='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForVisible("//td[@id='cke_contents__162_editor']/textarea");
		selenium.type("//td[@id='cke_contents__162_editor']/textarea",
			RuntimeVariables.replace("What is your favorite color?"));
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[@id='cke_34_label' and .='Source']"));
		selenium.clickAt("//span[@id='cke_34_label' and .='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForElementPresent(
			"//textarea[@id='_162_editor' and @style='display: none;']");
		selenium.waitForVisible("//td[@id='cke_contents__162_editor']/iframe");
		selenium.selectFrame("//td[@id='cke_contents__162_editor']/iframe");
		selenium.waitForText("//body", "What is your favorite color?");
		selenium.selectFrame("relative=top");
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=T\u00e9st Cat\u00e9gory Edit\u00e9d",
			RuntimeVariables.replace("T\u00e9st Cat\u00e9gory Edit\u00e9d"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("link=Waiting for an Answer");
		assertTrue(selenium.isElementPresent("link=Waiting for an Answer"));
	}
}