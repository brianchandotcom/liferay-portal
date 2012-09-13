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

package com.liferay.portalweb.kaleo.assetpublisher.dmdocument.guestviewpendingdmfolderdocumentnoaptest;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Guest_ViewPendingDMFolderDocumentNoAPTest extends BaseTestCase {
	public void testGuest_ViewPendingDMFolderDocumentNoAP()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementNotPresent("//section"));
		assertTrue(selenium.isElementNotPresent(
				"//span[@title='Add New']/ul/li/strong/a"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='portlet-msg-info']"));
		assertTrue(selenium.isElementNotPresent("//h3[@class='asset-title']/a"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='asset-resource-info']/span/a/span"));
		assertFalse(selenium.isTextPresent("DM Folder Document Title"));
	}
}