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

package com.liferay.portalweb.plugins.samplespring.petsite.removepetsite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RemovePetSiteTest extends BaseTestCase {
	public void testRemovePetSite() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Pet Sites Test Page",
			RuntimeVariables.replace("Pet Sites Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Pet Sites"),
			selenium.getText("//div[@class='portlet-body']/h1"));
		assertEquals(RuntimeVariables.replace("Edit Mode"),
			selenium.getText("//div/div/a"));
		selenium.clickAt("//div/div/a", RuntimeVariables.replace("Edit Mode"));
		selenium.waitForText("//div/div/a", "Add Site");
		assertEquals(RuntimeVariables.replace("Add Site"),
			selenium.getText("//div/div/a"));
		assertEquals(RuntimeVariables.replace("Remove"),
			selenium.getText("//div[@class='portlet-body']/form/a"));
		selenium.clickAt("//div[@class='portlet-body']/form/a",
			RuntimeVariables.replace("Remove"));
		selenium.waitForTextNotPresent("Remove");
		assertFalse(selenium.isTextPresent("Remove"));
		assertFalse(selenium.isTextPresent("Liferay [http://www.liferay.com]"));
		selenium.waitForText("//div/a[2]", "View Mode");
		assertEquals(RuntimeVariables.replace("View Mode"),
			selenium.getText("//div/a[2]"));
		selenium.clickAt("//div/a[2]", RuntimeVariables.replace("View Mode"));
		selenium.waitForText("//div/div/a", "Edit Mode");
		assertEquals(RuntimeVariables.replace("Edit Mode"),
			selenium.getText("//div/div/a"));
	}
}