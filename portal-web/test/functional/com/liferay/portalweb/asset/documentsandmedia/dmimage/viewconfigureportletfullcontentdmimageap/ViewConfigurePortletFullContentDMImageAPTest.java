/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.asset.documentsandmedia.dmimage.viewconfigureportletfullcontentdmimageap;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewConfigurePortletFullContentDMImageAPTest extends BaseTestCase {
	public void testViewConfigurePortletFullContentDMImageAP()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Add New"),
			selenium.getText("//span[@title='Add New']/ul/li/strong/a"));
		assertEquals(RuntimeVariables.replace("DM Folder Image Title"),
			selenium.getText("xPath=(//h1[@class='header-title']/span)[1]"));
		assertTrue(selenium.isVisible(
				"//div[@class='lfr-preview-file-image-container']/img"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-meta-actions asset-actions']/span/a/span",
				"Edit"));
		assertEquals(RuntimeVariables.replace(
				"Automatically Extracted Metadata"),
			selenium.getText("//div[@class='lfr-panel-title']/span"));
		assertEquals(RuntimeVariables.replace("Content Type image/jpeg"),
			selenium.getText(
				"//div[@class='lfr-panel-content']/div/div[contains(.,'Content Type')]"));
		assertEquals(RuntimeVariables.replace("Bits Per Sample 8"),
			selenium.getText(
				"//div[@class='lfr-panel-content']/div/div[contains(.,'Bits Per Sample')]"));
		assertEquals(RuntimeVariables.replace("Image Length 92"),
			selenium.getText(
				"//div[@class='lfr-panel-content']/div/div[contains(.,'Image Length')]"));
		assertEquals(RuntimeVariables.replace("Image Width 394"),
			selenium.getText(
				"//div[@class='lfr-panel-content']/div/div[contains(.,'Image Width')]"));
		assertTrue(selenium.isVisible(
				"xPath=(//li[@class='taglib-social-bookmark-twitter'])[1]"));
		assertTrue(selenium.isVisible(
				"xPath=(//li[@class='taglib-social-bookmark-facebook'])[1]"));
		assertTrue(selenium.isVisible(
				"xPath=(//li[@class='taglib-social-bookmark-plusone'])[1]"));
		assertEquals(RuntimeVariables.replace("View in Context \u00bb"),
			selenium.getText("xPath=(//div[@class='asset-more']/a)[1]"));
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("xPath=(//h1[@class='header-title']/span)[2]"));
		assertTrue(selenium.isVisible(
				"xPath=(//li[@class='taglib-social-bookmark-twitter'])[2]"));
		assertTrue(selenium.isVisible(
				"xPath=(//li[@class='taglib-social-bookmark-facebook'])[2]"));
		assertTrue(selenium.isVisible(
				"xPath=(//li[@class='taglib-social-bookmark-plusone'])[2]"));
		assertEquals(RuntimeVariables.replace("View in Context \u00bb"),
			selenium.getText("xPath=(//div[@class='asset-more']/a)[2]"));
		selenium.clickAt("xPath=(//div[@class='asset-more']/a)[1]",
			RuntimeVariables.replace("View in Context \u00bb"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder Image Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertTrue(selenium.isVisible(
				"//img[@class='lfr-preview-file-image-current']"));
	}
}