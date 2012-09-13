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

package com.liferay.portalweb.demo.media.dmstagingdocumenttypemusic;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigureDMMaximumFileSizeCPTest extends BaseTestCase {
	public void testConfigureDMMaximumFileSizeCP() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
				assertEquals(RuntimeVariables.replace("Go to"),
					selenium.getText("//li[@id='_145_mySites']/a/span"));
				selenium.clickAt("//li[@id='_145_mySites']/a/span",
					RuntimeVariables.replace("Go to"));
				selenium.waitForVisible("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Server Administration",
					RuntimeVariables.replace("Server Administration"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=File Uploads",
					RuntimeVariables.replace("File Uploads"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Documents and Media"),
					selenium.getText(
						"//div[@id='adminDocumentLibraryPanel']/div/div/span"));

				boolean documentsAndMediaCollapsed = selenium.isElementPresent(
						"//div[@id='adminDocumentLibraryPanel' and contains(@class,'lfr-collapsed')]");

				if (!documentsAndMediaCollapsed) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@id='adminDocumentLibraryPanel']/div/div/span",
					RuntimeVariables.replace("Documents and Media"));

			case 2:
				selenium.waitForVisible("//input[@id='_137_dlFileMaxSize']");
				selenium.type("//input[@id='_137_dlFileMaxSize']",
					RuntimeVariables.replace("10000000"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals("10000000",
					selenium.getValue("//input[@id='_137_dlFileMaxSize']"));

			case 100:
				label = -1;
			}
		}
	}
}