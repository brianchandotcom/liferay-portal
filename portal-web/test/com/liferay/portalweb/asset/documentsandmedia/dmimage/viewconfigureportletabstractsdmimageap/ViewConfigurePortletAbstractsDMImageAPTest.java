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

package com.liferay.portalweb.asset.documentsandmedia.dmimage.viewconfigureportletabstractsdmimageap;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewConfigurePortletAbstractsDMImageAPTest extends BaseTestCase {
	public void testViewConfigurePortletAbstractsDMImageAP()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder Image Title"),
			selenium.getText("//h3[@class='asset-title']/a"));
		assertEquals(RuntimeVariables.replace(
				"Read More About DM Folder Image Title \u00bb"),
			selenium.getText("//div[@class='asset-more']/a"));
		assertTrue(selenium.isVisible(
				"//div[@class='asset-resource-info']/div/img"));
		assertEquals(RuntimeVariables.replace(
				"Read More About DM Folder Image Title \u00bb"),
			selenium.getText("//div[@class='asset-more']/a"));
		selenium.clickAt("//div[@class='asset-more']/a",
			RuntimeVariables.replace(
				"Read More About DM Folder Image Title \u00bb"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder Image Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertTrue(selenium.isVisible(
				"//div[@class='lfr-preview-file-image-container']/img"));
		assertEquals(RuntimeVariables.replace("View in Context \u00bb"),
			selenium.getText("//div[@class='asset-more']/a"));
		selenium.clickAt("//div[@class='asset-more']/a",
			RuntimeVariables.replace("View in Context \u00bb"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder Image Title"),
			selenium.getText("//h1[@class='header-title']"));
		assertTrue(selenium.isVisible(
				"//img[@class='lfr-preview-file-image-current']"));
	}
}