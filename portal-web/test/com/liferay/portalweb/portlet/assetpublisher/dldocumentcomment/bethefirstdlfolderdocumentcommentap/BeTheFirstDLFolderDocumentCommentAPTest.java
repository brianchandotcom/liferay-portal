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

package com.liferay.portalweb.portlet.assetpublisher.dldocumentcomment.bethefirstdlfolderdocumentcommentap;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class BeTheFirstDLFolderDocumentCommentAPTest extends BaseTestCase {
	public void testBeTheFirstDLFolderDocumentCommentAP()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Asset Publisher Test Page");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Be the first."),
			selenium.getText("//fieldset/div/a"));
		selenium.clickAt("//fieldset/div/a",
			RuntimeVariables.replace("Be the first."));
		selenium.waitForVisible("//textarea");
		selenium.type("//textarea",
			RuntimeVariables.replace("DM Folder Document Comment Body"));
		selenium.clickAt("//input[@value='Reply']",
			RuntimeVariables.replace("Reply"));
		selenium.waitForVisible(
			"//div[@class='lfr-message-response portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText(
				"//div[@class='lfr-message-response portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("xpath=(//span[@class='user-name'])[1]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText("//div[2]/div/a"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("xpath=(//span[@class='user-name'])[2]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document Comment Body"),
			selenium.getText("//div/div[3]/div/div[1]"));
	}
}