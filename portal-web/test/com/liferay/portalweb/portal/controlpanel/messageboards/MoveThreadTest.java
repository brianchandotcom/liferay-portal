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
public class MoveThreadTest extends BaseTestCase {
	public void testMoveThread() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
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
				selenium.clickAt("link=Message Boards",
					RuntimeVariables.replace("Message Boards"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("T\u00e9st Cat\u00e9gory"),
					selenium.getText(
						"//tr[contains(.,'T\u00e9st Cat\u00e9gory')]/td[2]/a/strong"));
				selenium.clickAt("//tr[contains(.,'T\u00e9st Cat\u00e9gory')]/td[2]/a/strong",
					RuntimeVariables.replace("T\u00e9st Cat\u00e9gory"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"T\u00e9st Subcat\u00e9gory"),
					selenium.getText(
						"//tr[contains(.,'T\u00e9st Subcat\u00e9gory')]/td[2]/a/strong"));
				selenium.clickAt("//tr[contains(.,'T\u00e9st Subcat\u00e9gory')]/td[2]/a/strong",
					RuntimeVariables.replace("T\u00e9st Subcat\u00e9gory"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"S\u00e9cond T\u00e9st Subcat\u00e9gory"),
					selenium.getText(
						"//tr[contains(.,'S\u00e9cond T\u00e9st Subcat\u00e9gory')]/td[2]/a/strong"));
				selenium.clickAt("//tr[contains(.,'S\u00e9cond T\u00e9st Subcat\u00e9gory')]/td[2]/a/strong",
					RuntimeVariables.replace(
						"S\u00e9cond T\u00e9st Subcat\u00e9gory"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"T\u00e9st M\u00e9ssag\u00e9 to b\u00e9 D\u00e9l\u00e9t\u00e9d"),
					selenium.getText(
						"//tr[contains(.,'T\u00e9st M\u00e9ssag\u00e9 to b\u00e9 D\u00e9l\u00e9t\u00e9d')]/td[2]/a"));
				selenium.clickAt("//tr[contains(.,'T\u00e9st M\u00e9ssag\u00e9 to b\u00e9 D\u00e9l\u00e9t\u00e9d')]/td[2]/a",
					RuntimeVariables.replace(
						"T\u00e9st M\u00e9ssag\u00e9 to b\u00e9 D\u00e9l\u00e9t\u00e9d"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"T\u00e9st M\u00e9ssag\u00e9 to b\u00e9 D\u00e9l\u00e9t\u00e9d"),
					selenium.getText("//h1[@class='header-title']/span"));
				assertEquals(RuntimeVariables.replace(
						"T\u00e9st M\u00e9ssag\u00e9 to b\u00e9 D\u00e9l\u00e9t\u00e9d"),
					selenium.getText("//div[@class='subject']/a/strong"));
				assertEquals(RuntimeVariables.replace(
						"This m\u00e9ssag\u00e9 will b\u00e9 d\u00e9l\u00e9t\u00e9d!"),
					selenium.getText("//div[@class='thread-body']"));
				assertEquals(RuntimeVariables.replace("Move Thread"),
					selenium.getText(
						"//div[contains(.,'Move Thread')]/ul/li[5]/a/span"));
				selenium.clickAt("//div[contains(.,'Move Thread')]/ul/li[5]/a/span",
					RuntimeVariables.replace("Move Thread"));
				selenium.waitForPageToLoad("30000");

				boolean addExplanationPostChecked = selenium.isChecked(
						"//input[@id='_162_addExplanationPostCheckbox']");

				if (addExplanationPostChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_162_addExplanationPostCheckbox']",
					RuntimeVariables.replace("Add post explanation."));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@id='_162_addExplanationPostCheckbox']"));
				selenium.waitForVisible("//input[@id='_162_subject']");
				selenium.type("//input[@id='_162_subject']",
					RuntimeVariables.replace("Moved to Sujr"));
				selenium.waitForElementPresent(
					"//textarea[@id='_162_editor' and @style='display: none;']");
				assertEquals(RuntimeVariables.replace("Source"),
					selenium.getText("//span[.='Source']"));
				selenium.clickAt("//span[.='Source']",
					RuntimeVariables.replace("Source"));
				selenium.waitForVisible(
					"//a[@class='cke_button_source cke_on']");
				selenium.waitForVisible(
					"//td[@id='cke_contents__162_editor']/textarea");
				selenium.type("//td[@id='cke_contents__162_editor']/textarea",
					RuntimeVariables.replace(
						"Trust and paths will be straightened."));
				assertEquals(RuntimeVariables.replace("Source"),
					selenium.getText("//span[.='Source']"));
				selenium.clickAt("//span[.='Source']",
					RuntimeVariables.replace("Source"));
				selenium.waitForVisible(
					"//td[@id='cke_contents__162_editor']/iframe");
				selenium.selectFrame(
					"//td[@id='cke_contents__162_editor']/iframe");
				selenium.waitForText("//body",
					"Trust and paths will be straightened.");
				selenium.selectFrame("relative=top");
				selenium.clickAt("//input[@value='Select']",
					RuntimeVariables.replace("Select"));
				Thread.sleep(5000);
				selenium.selectWindow("title=Message Boards");
				selenium.waitForVisible(
					"//ul[contains(.,'Categories')]/li/span/a");
				assertEquals(RuntimeVariables.replace("Categories"),
					selenium.getText("//ul[contains(.,'Categories')]/li/span/a"));
				selenium.clickAt("//ul[contains(.,'Categories')]/li/span/a",
					RuntimeVariables.replace("Categories"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Sujr"),
					selenium.getText("//tr[contains(.,'Sujr')]/td[1]/a"));
				selenium.click("//tr[contains(.,'Sujr')]/td[5]/input");
				selenium.selectWindow("null");
				selenium.clickAt("//input[@value='Move Thread']",
					RuntimeVariables.replace("Move Thread"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"T\u00e9st M\u00e9ssag\u00e9 to b\u00e9 D\u00e9l\u00e9t\u00e9d"),
					selenium.getText("//h1[@class='header-title']/span"));
				assertEquals(RuntimeVariables.replace("Moved to Sujr"),
					selenium.getText(
						"//tr[contains(.,'Moved to Sujr')]/td[1]/a/strong"));
				assertEquals(RuntimeVariables.replace(
						"T\u00e9st M\u00e9ssag\u00e9 to b\u00e9 D\u00e9l\u00e9t\u00e9d"),
					selenium.getText(
						"xpath=(//div[@class='subject']/a/strong)[1]"));
				assertEquals(RuntimeVariables.replace(
						"This m\u00e9ssag\u00e9 will b\u00e9 d\u00e9l\u00e9t\u00e9d!"),
					selenium.getText("xpath=(//div[@class='thread-body'])[1]"));
				assertEquals(RuntimeVariables.replace("Moved to Sujr"),
					selenium.getText(
						"xpath=(//div[@class='subject']/a/strong)[2]"));
				assertEquals(RuntimeVariables.replace(
						"Trust and paths will be straightened."),
					selenium.getText("xpath=(//div[@class='thread-body'])[2]"));
				assertFalse(selenium.isTextPresent("T\u00e9st Subcat\u00e9gory"));
				assertFalse(selenium.isTextPresent("T\u00e9st Cat\u00e9gory"));

			case 100:
				label = -1;
			}
		}
	}
}