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

package com.liferay.portalweb.plugins.netvibeswidget.portlet.removeportletnw;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPageNWTest extends BaseTestCase {
	public void testAddPageNW() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//nav[@id='navigation']",
			RuntimeVariables.replace("Navigation"));
		selenium.waitForElementPresent("//a[@id='addPage']");
		selenium.clickAt("//a[@id='addPage']",
			RuntimeVariables.replace("Add Page"));
		selenium.waitForVisible("//input[@type='text']");
		selenium.type("//input[@type='text']",
			RuntimeVariables.replace("Netvibes Widget Test Page"));
		selenium.clickAt("//button[contains(@id,'Save')]",
			RuntimeVariables.replace("Save"));
		selenium.waitForVisible("link=Netvibes Widget Test Page");
		selenium.clickAt("link=Netvibes Widget Test Page",
			RuntimeVariables.replace("Netvibes Widget Test Page"));
		selenium.waitForPageToLoad("30000");
	}
}