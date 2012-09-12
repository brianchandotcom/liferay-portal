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
public class ViewCheckoutDMDocumentTxtTest extends BaseTestCase {
	public void testViewCheckoutDMDocumentTxt() throws Exception {
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
		assertEquals(RuntimeVariables.replace(
				"You now have an indefinite lock on this document. No one else can edit this document until you unlock it. This lock will never expire."),
			selenium.getText(
				"//div[@class='portlet-msg-lock portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Version PWC"),
			selenium.getText("//h3[@class='version document-locked']"));
		assertEquals(RuntimeVariables.replace("Status: Draft"),
			selenium.getText("//span[@class='workflow-status']"));
		assertEquals(RuntimeVariables.replace("PWC"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row']/td[2]"));
		assertEquals(RuntimeVariables.replace("0.3k"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row']/td[4]"));
		assertEquals(RuntimeVariables.replace("Draft"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row']/td[5]"));
		assertEquals(RuntimeVariables.replace("1.0"),
			selenium.getText(
				"//tr[@class='portlet-section-alternate results-row alt last']/td[2]"));
		assertEquals(RuntimeVariables.replace("0.3k"),
			selenium.getText(
				"//tr[@class='portlet-section-alternate results-row alt last']/td[4]"));
		assertEquals(RuntimeVariables.replace("Approved"),
			selenium.getText(
				"//tr[@class='portlet-section-alternate results-row alt last']/td[5]"));
	}
}