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

package com.liferay.portalweb.portal.controlpanel.polls;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewQuestionGraphsTest extends BaseTestCase {
	public void testViewQuestionGraphs() throws Exception {
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
		selenium.clickAt("link=Polls", RuntimeVariables.replace("Polls"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test Poll Question"),
			selenium.getText("//tr[contains(.,'Test Poll Question')]/td[1]/a"));
		selenium.clickAt("//tr[contains(.,'Test Poll Question')]/td[1]/a",
			RuntimeVariables.replace("Test Poll Question"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Area"),
			selenium.getText("//a[contains(.,'Area')]"));
		selenium.clickAt("//a[contains(.,'Area')]",
			RuntimeVariables.replace("Area"));
		Thread.sleep(5000);
		selenium.selectPopUp("");
		selenium.waitForVisible("//body/img");
		assertTrue(selenium.isVisible("//body/img"));
		selenium.close();
		selenium.selectWindow("null");
		selenium.waitForVisible("//a[contains(.,'Horizontal Bar')]");
		assertEquals(RuntimeVariables.replace("Horizontal Bar"),
			selenium.getText("//a[contains(.,'Horizontal Bar')]"));
		selenium.clickAt("//a[contains(.,'Horizontal Bar')]",
			RuntimeVariables.replace("Horizontal Bar"));
		Thread.sleep(5000);
		selenium.selectPopUp("");
		selenium.waitForVisible("//body/img");
		assertTrue(selenium.isVisible("//body/img"));
		selenium.close();
		selenium.selectWindow("null");
		selenium.waitForVisible("//a[contains(.,'Line')]");
		assertEquals(RuntimeVariables.replace("Line"),
			selenium.getText("//a[contains(.,'Line')]"));
		selenium.clickAt("//a[contains(.,'Line')]",
			RuntimeVariables.replace("Line"));
		Thread.sleep(5000);
		selenium.selectPopUp("");
		selenium.waitForVisible("//body/img");
		assertTrue(selenium.isVisible("//body/img"));
		selenium.close();
		selenium.selectWindow("null");
		selenium.waitForVisible("//a[contains(.,'Pie')]");
		assertEquals(RuntimeVariables.replace("Pie"),
			selenium.getText("//a[contains(.,'Pie')]"));
		selenium.clickAt("//a[contains(.,'Pie')]",
			RuntimeVariables.replace("Pie"));
		Thread.sleep(5000);
		selenium.selectPopUp("");
		selenium.waitForVisible("//body/img");
		assertTrue(selenium.isVisible("//body/img"));
		selenium.close();
		selenium.selectWindow("null");
		selenium.waitForVisible("//a[contains(.,'Vertical Bar')]");
		assertEquals(RuntimeVariables.replace("Vertical Bar"),
			selenium.getText("//a[contains(.,'Vertical Bar')]"));
		selenium.clickAt("//a[contains(.,'Vertical Bar')]",
			RuntimeVariables.replace("Vertical Bar"));
		Thread.sleep(5000);
		selenium.selectPopUp("");
		selenium.waitForVisible("//body/img");
		assertTrue(selenium.isVisible("//body/img"));
		selenium.close();
		selenium.selectWindow("null");
	}
}