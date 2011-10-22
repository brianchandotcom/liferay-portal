/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.imagegallery.image.viewfolderimagemyimages;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewFolderImageMyImagesTest extends BaseTestCase {
	public void testViewFolderImageMyImages() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Image Gallery Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Image Gallery Test Page",
			RuntimeVariables.replace("Image Gallery Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=My Images", RuntimeVariables.replace("My Images"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("My Images"),
			selenium.getText("//div/h1/span"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//img[@alt='MG Folder Image Title - MG Folder Image Description']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isVisible(
				"//img[@alt='MG Folder Image Title - MG Folder Image Description']"));
		assertEquals(RuntimeVariables.replace("MG Folder Image Title"),
			selenium.getText("//span[@class='image-title']"));
	}
}