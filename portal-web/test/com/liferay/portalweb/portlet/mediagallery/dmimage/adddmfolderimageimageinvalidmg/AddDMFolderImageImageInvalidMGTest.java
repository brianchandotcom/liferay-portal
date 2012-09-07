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

package com.liferay.portalweb.portlet.mediagallery.dmimage.adddmfolderimageimageinvalidmg;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddDMFolderImageImageInvalidMGTest extends BaseTestCase {
	public void testAddDMFolderImageImageInvalidMG() throws Exception {
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
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"There are no media files in this folder."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertEquals(RuntimeVariables.replace("Add Media"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Add Media')]"));
		selenium.clickAt("//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Add Media')]",
			RuntimeVariables.replace("Add Media"));
		selenium.waitForVisible("//iframe");
		selenium.selectFrame("//iframe");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		assertEquals(RuntimeVariables.replace("Basic Document"),
			selenium.getText("//td[contains(@id,'name_row-1')]/a"));
		selenium.clickAt("//td[contains(@id,'name_row-1')]/a",
			RuntimeVariables.replace("Basic Document"));
		selenium.waitForPageToLoad("30000");
		selenium.selectFrame("relative=top");
		selenium.uploadCommonFile("//input[@id='_31_file']",
			RuntimeVariables.replace("Document_1.txt"));
		selenium.type("//input[@id='_31_title']",
			RuntimeVariables.replace("DM Folder Image Title"));
		selenium.type("//textarea[@id='_31_description']",
			RuntimeVariables.replace("DM Folder Image Description"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request failed to complete."),
			selenium.getText("xPath=(//div[@class='portlet-msg-error'])[1]"));
		assertEquals(RuntimeVariables.replace(
				"Media files must be one of the following formats: audio/basic, audio/mid, audio/midi, audio/mod, audio/mp3, audio/mpeg, audio/mpeg3, audio/ogg, audio/vorbis, audio/wav, audio/x-mid, audio/x-midi, audio/x-mod, audio/x-mpeg, audio/x-pn-realaudio, audio/x-realaudio, audio/x-wav, image/bmp, image/gif, image/jpeg, image/pjpeg, image/png, image/tiff, image/x-citrix-jpeg, image/x-citrix-png, image/x-ms-bmp, image/x-png, image/x-tiff, video/avi, video/mp4, video/mpeg, video/ogg, video/quicktime, video/x-flv, video/x-m4v, video/x-ms-wmv, video/x-msvideo."),
			selenium.getText("xPath=(//div[@class='portlet-msg-error'])[2]"));
	}
}