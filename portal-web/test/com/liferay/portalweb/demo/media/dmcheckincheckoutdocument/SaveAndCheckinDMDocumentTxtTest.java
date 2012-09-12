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

package com.liferay.portalweb.demo.media.dmcheckincheckoutdocument;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SaveAndCheckinDMDocumentTxtTest extends BaseTestCase {
	public void testSaveAndCheckinDMDocumentTxt() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Document Title (Draft)"),
			selenium.getText("//div[@data-title='DM Document Title']/a/span[2]"));
		assertTrue(selenium.isVisible("//img[@alt='Locked']"));
		selenium.clickAt("//div[@data-title='DM Document Title']/a/span[2]",
			RuntimeVariables.replace("DM Document Title (Draft)"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("//button[.='Edit']"));
		selenium.clickAt("//button[.='Edit']", RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		selenium.uploadCommonFile("//input[@id='_20_file']",
			RuntimeVariables.replace("Document_2.txt"));
		selenium.type("//input[@id='_20_title']",
			RuntimeVariables.replace("DM Document Title Edit"));
		selenium.clickAt("//input[@value='Save and Checkin']",
			RuntimeVariables.replace("Save and Checkin"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("DM Document Title Edit"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Download (0.5k)"),
			selenium.getText("//span[@class='download-document']/span/a/span"));
	}
}