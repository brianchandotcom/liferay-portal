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

package com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.serveradministration.viewnogeneratecustomroles;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewNoGenerateCustomRolesTest extends BaseTestCase {
	public void testViewNoGenerateCustomRoles() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Server Administration",
			RuntimeVariables.replace("Server Administration"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Data Migration",
			RuntimeVariables.replace("Data Migration"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent(
				"Convert legacy permission algorithm."));
		assertTrue(selenium.isElementNotPresent(
				"//input[@id='_137_com.liferay.portal.convert.ConvertPermissionAlgorithm.generate-custom-rolesCheckbox']"));
		assertTrue(selenium.isElementNotPresent(
				"//input[@value='Execute' and @type='button' and @onclick=\"_137_saveServer('convertProcess.com.liferay.portal.convert.ConvertPermissionAlgorithm');\"]"));
	}
}