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

package com.liferay.portalweb.plugins.testworkflow;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPageTest extends BaseTestCase {
	public void testAddPage() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("main-content", RuntimeVariables.replace(""));
		selenium.clickAt("dockbar", RuntimeVariables.replace(""));
		selenium.clickAt("navigation", RuntimeVariables.replace(""));
		selenium.waitForElementPresent("addPage");
		selenium.clickAt("addPage", RuntimeVariables.replace(""));
		selenium.waitForVisible("//input");
		selenium.type("//input", RuntimeVariables.replace("Test Workflow Page"));
		selenium.clickAt("save", RuntimeVariables.replace(""));
		selenium.waitForVisible("link=Test Workflow Page");
		selenium.clickAt("link=Test Workflow Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
	}
}