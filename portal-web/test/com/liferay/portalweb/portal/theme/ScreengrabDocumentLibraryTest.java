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

package com.liferay.portalweb.portal.theme;

import com.liferay.portal.kernel.util.FileUtil;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ScreengrabDocumentLibraryTest extends BaseTestCase {
	public void testScreengrabDocumentLibrary() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.waitForElementPresent("link=Test Page 3");
		selenium.click(RuntimeVariables.replace("link=Test Page 3"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Test Folder"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace(
				"//input[@value='Add Document']"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		selenium.waitForElementPresent("link=Use the classic uploader.");
		selenium.click("link=Use the classic uploader.");
		selenium.waitForElementPresent("_20_title");
		FileUtil.mkdirs(RuntimeVariables.replace(
				"L:\\portal\\build\\portal-web\\test-output\\brochure\\"));
		selenium.captureEntirePageScreenshot(RuntimeVariables.replace(
				"L:\\portal\\build\\portal-web\\test-output\\brochure\\ScreengrabTest04.jpg"),
			"");
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		FileUtil.mkdirs(RuntimeVariables.replace(
				"L:\\portal\\build\\portal-web\\test-output\\brochure\\"));
		selenium.captureEntirePageScreenshot(RuntimeVariables.replace(
				"L:\\portal\\build\\portal-web\\test-output\\brochure\\ScreengrabTest05.jpg"),
			"");
	}
}