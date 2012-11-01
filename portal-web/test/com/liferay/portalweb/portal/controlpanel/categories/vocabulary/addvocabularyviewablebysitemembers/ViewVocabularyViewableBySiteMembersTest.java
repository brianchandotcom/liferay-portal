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

package com.liferay.portalweb.portal.controlpanel.categories.vocabulary.addvocabularyviewablebysitemembers;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewVocabularyViewableBySiteMembersTest extends BaseTestCase {
	public void testViewVocabularyViewableBySiteMembers()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Categories",
			RuntimeVariables.replace("Categories"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Vocabulary Name"),
			selenium.getText("//span[@class='vocabulary-item']/a"));
		selenium.clickAt("//a[@class='vocabulary-item-actions-trigger']",
			RuntimeVariables.replace("Edit"));
		selenium.waitForVisible("//input[@id='_147_title_en_US']");
		assertEquals("Vocabulary Name",
			selenium.getValue("//input[@id='_147_title_en_US']"));
		assertEquals(RuntimeVariables.replace("Vocabulary Description"),
			selenium.getText("//textarea[@id='_147_description_en_US']"));
		selenium.clickAt("//input[@value='Permissions']",
			RuntimeVariables.replace("Permissions"));
		selenium.waitForVisible("//iframe");
		selenium.selectFrame("//iframe");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/search_container.js')]");
		selenium.waitForVisible("//tr[3]/td[5]/input");
		assertFalse(selenium.isChecked("//tr[3]/td[5]/input"));
		assertTrue(selenium.isChecked("//tr[4]/td[5]/input"));
		assertFalse(selenium.isChecked("//tr[5]/td[5]/input"));
		assertFalse(selenium.isChecked("//tr[6]/td[5]/input"));
		assertTrue(selenium.isChecked("//tr[7]/td[5]/input"));
		selenium.selectFrame("relative=top");
	}
}