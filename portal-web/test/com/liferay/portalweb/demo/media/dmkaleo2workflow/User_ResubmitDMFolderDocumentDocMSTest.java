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

package com.liferay.portalweb.demo.media.dmkaleo2workflow;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_ResubmitDMFolderDocumentDocMSTest extends BaseTestCase {
	public void testUser_ResubmitDMFolderDocumentDocMS()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.clickAt("//li[@id='_145_mySites']/a/span",
			RuntimeVariables.replace("Go to"));
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=My Submissions",
			RuntimeVariables.replace("My Submissions"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Single Approver"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace("DM Document Title Edit"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("Documents and Media Document"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("Update"),
			selenium.getText("//td[4]/a"));
		assertTrue(selenium.isElementPresent("//td[5]/a"));
		assertEquals(RuntimeVariables.replace("Never"),
			selenium.getText("//td[6]/a"));
		assertEquals(RuntimeVariables.replace("Withdraw Submission"),
			selenium.getText("//td[7]/span/a/span"));
		selenium.clickAt("//td[4]/a", RuntimeVariables.replace("Update"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Single Approver: DM Document Title Edit"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"Preview of Documents and Media Document"),
			selenium.getText(
				"//div[@id='preview']//div[@class='lfr-panel-title']/span"));
		assertEquals(RuntimeVariables.replace("DM Document Title Edit"),
			selenium.getText("//h3[@class='task-content-title']"));
		assertEquals(RuntimeVariables.replace("DM Document Description"),
			selenium.getText("//p[@class='asset-description']"));
		assertEquals(RuntimeVariables.replace("By userfn userln"),
			selenium.getText("//span[contains(@class,'metadata-author')]"));
		assertEquals(RuntimeVariables.replace("Update"),
			selenium.getText("//tr[3]/td[1]/span"));
		assertEquals(RuntimeVariables.replace("Never"),
			selenium.getText("//tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("No"),
			selenium.getText("//tr[3]/td[3]"));
		assertEquals(RuntimeVariables.replace("Resubmit"),
			selenium.getText("//tr[3]/td[4]/span/a/span"));
		selenium.clickAt("//tr[3]/td[4]/span/a/span",
			RuntimeVariables.replace("Resubmit"));
		selenium.waitForVisible("//button[.='OK']");
		assertEquals(RuntimeVariables.replace("OK"),
			selenium.getText("//button[.='OK']"));
		selenium.clickAt("//button[.='OK']", RuntimeVariables.replace("OK"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}