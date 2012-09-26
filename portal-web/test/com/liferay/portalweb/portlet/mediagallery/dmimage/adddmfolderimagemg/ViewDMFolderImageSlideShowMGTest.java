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

package com.liferay.portalweb.portlet.mediagallery.dmimage.adddmfolderimagemg;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDMFolderImageSlideShowMGTest extends BaseTestCase {
	public void testViewDMFolderImageSlideShowMG() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Media Gallery Test Page",
			RuntimeVariables.replace("Media Gallery Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//span[@class='image-title']"));
		selenium.clickAt("//span[@class='image-title']",
			RuntimeVariables.replace("DM Folder Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder Image Title"),
			selenium.getText("//span[@class='image-title']"));
		assertEquals(RuntimeVariables.replace("View Slide Show"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'View Slide Show')]"));
		selenium.clickAt("//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'View Slide Show')]",
			RuntimeVariables.replace("View Slide Show"));
		Thread.sleep(5000);
		selenium.selectWindow("title=Media Gallery");
		Thread.sleep(5000);
		selenium.waitForVisible("//img[@name='_31_slideShow']");
		assertTrue(selenium.isVisible("//img[@name='_31_slideShow']"));
		selenium.close();
		selenium.selectWindow("null");
		selenium.waitForVisible(
			"//img[@alt='DM Folder Image Title - DM Folder Image Description']");
		assertTrue(selenium.isVisible(
				"//img[@alt='DM Folder Image Title - DM Folder Image Description']"));
	}
}