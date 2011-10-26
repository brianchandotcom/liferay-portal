/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.controlpanel.dynamicdatalists.record.editrecord;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewEditRecordTest extends BaseTestCase {
	public void testViewEditRecord() throws Exception {
		selenium.open("/web/guest/home/");

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
		selenium.clickAt("link=Dynamic Data Lists",
			RuntimeVariables.replace("Dynamic Data Lists"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='_167_keywords']",
			RuntimeVariables.replace("List Name"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("List Name"),
			selenium.getText("//tr[3]/td[2]/a"));
		selenium.clickAt("//tr[3]/td[2]/a",
			RuntimeVariables.replace("List Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Boolean"),
			selenium.getText("//tr[1]/th[1]"));
		assertEquals(RuntimeVariables.replace("false"),
			selenium.getText("//tr[3]/td[1]"));
		assertEquals(RuntimeVariables.replace("Date"),
			selenium.getText("//tr[1]/th[2]"));
		assertEquals(RuntimeVariables.replace("8/9/10"),
			selenium.getText("//tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("Decimal"),
			selenium.getText("//tr[1]/th[3]"));
		assertEquals(RuntimeVariables.replace("8.91"),
			selenium.getText("//tr[3]/td[3]"));
		assertEquals(RuntimeVariables.replace("Document Library"),
			selenium.getText("//tr[1]/th[4]"));
		assertTrue(selenium.isPartialText("//tr[3]/td[4]", "File Upload"));
		assertEquals(RuntimeVariables.replace("File Upload"),
			selenium.getText("//tr[1]/th[5]"));
		assertTrue(selenium.isPartialText("//tr[3]/td[5]", "File Upload"));
		assertEquals(RuntimeVariables.replace("Integer"),
			selenium.getText("//tr[1]/th[6]"));
		assertEquals(RuntimeVariables.replace("8910"),
			selenium.getText("//tr[3]/td[6]"));
		assertEquals(RuntimeVariables.replace("Number"),
			selenium.getText("//tr[1]/th[7]"));
		assertEquals(RuntimeVariables.replace("111213.0"),
			selenium.getText("//tr[3]/td[7]"));
		assertEquals(RuntimeVariables.replace("Password"),
			selenium.getText("//tr[1]/th[8]"));
		assertEquals(RuntimeVariables.replace(
				"\u25cf\u25cf\u25cf\u25cf\u25cf\u25cf\u25cf\u25cf\u25cf\u25cf\u25cf\u25cf\u25cf\u25cf"),
			selenium.getText("//tr[3]/td[8]"));
		assertEquals(RuntimeVariables.replace("Radio"),
			selenium.getText("//tr[1]/th[9]"));
		assertEquals(RuntimeVariables.replace("option 3"),
			selenium.getText("//tr[3]/td[9]"));
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText("//tr[1]/th[10]"));
		assertEquals(RuntimeVariables.replace("option 1"),
			selenium.getText("//tr[3]/td[10]"));
		assertEquals(RuntimeVariables.replace("Text"),
			selenium.getText("//tr[1]/th[11]"));
		assertEquals(RuntimeVariables.replace("Text Field Edited"),
			selenium.getText("//tr[3]/td[11]"));
		assertEquals(RuntimeVariables.replace("Text Box"),
			selenium.getText("//tr[1]/th[12]"));
		assertEquals(RuntimeVariables.replace("Text Box Edited"),
			selenium.getText("//tr[3]/td[12]"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("View"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Boolean false"),
			selenium.getText("//div[@class='aui-fieldset-content ']/div[1]"));
		assertEquals(RuntimeVariables.replace("Date 8/9/10"),
			selenium.getText("//div[@class='aui-fieldset-content ']/div[2]"));
		assertEquals(RuntimeVariables.replace("Decimal 8.91"),
			selenium.getText("//div[@class='aui-fieldset-content ']/div[3]"));
		assertTrue(selenium.isPartialText(
				"//div[@class='aui-fieldset-content ']/div[4]",
				"Document Library File Upload"));
		assertTrue(selenium.isPartialText(
				"//div[@class='aui-fieldset-content ']/div[5]",
				"File Upload File Upload"));
		assertEquals(RuntimeVariables.replace("Integer 8910"),
			selenium.getText("//div[@class='aui-fieldset-content ']/div[6]"));
		assertEquals(RuntimeVariables.replace("Number 111213.0"),
			selenium.getText("//div[@class='aui-fieldset-content ']/div[7]"));
		assertEquals(RuntimeVariables.replace(
				"Password \u25cf\u25cf\u25cf\u25cf\u25cf\u25cf\u25cf\u25cf\u25cf\u25cf\u25cf\u25cf\u25cf\u25cf"),
			selenium.getText("//div[@class='aui-fieldset-content ']/div[8]"));
		assertEquals(RuntimeVariables.replace("Radio option 3"),
			selenium.getText("//div[@class='aui-fieldset-content ']/div[9]"));
		assertEquals(RuntimeVariables.replace("Select option 1"),
			selenium.getText("//div[@class='aui-fieldset-content ']/div[10]"));
		assertEquals(RuntimeVariables.replace("Text Text Field Edited"),
			selenium.getText("//div[@class='aui-fieldset-content ']/div[11]"));
		assertEquals(RuntimeVariables.replace("Text Box Text Box Edited"),
			selenium.getText("//div[@class='aui-fieldset-content ']/div[12]"));
	}
}