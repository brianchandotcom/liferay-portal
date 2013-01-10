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

package com.liferay.portalweb.portlet.breadcrumb.portlet.viewportletbreadcrumb;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletBreadcrumbTest extends BaseTestCase {
	public void testViewPortletBreadcrumb() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Breadcrumb Test Page",
			RuntimeVariables.replace("Breadcrumb Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Breadcrumb Test Page"),
			selenium.getText(
				"//nav[@id='breadcrumbs']/ul/li[@class='last']/span/a"));
		assertEquals(RuntimeVariables.replace("Liferay"),
			selenium.getText(
				"//div[@class='portlet-body']/ul/li[@class='first']/span/a"));
		assertEquals(RuntimeVariables.replace("Breadcrumb Test Page"),
			selenium.getText(
				"//div[@class='portlet-body']/ul/li[@class='last']/span/a"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='portlet-body']/ul/li[3]"));
		selenium.mouseOver("link=Breadcrumb Test Page");
		selenium.waitForVisible("link=Child Test Page");
		selenium.clickAt("link=Child Test Page",
			RuntimeVariables.replace("Child Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Breadcrumb Test Page"),
			selenium.getText(
				"//nav[@id='breadcrumbs']/ul/li[@class='first']/span/a"));
		assertEquals(RuntimeVariables.replace("Child Test Page"),
			selenium.getText(
				"//nav[@id='breadcrumbs']/ul/li[@class='last']/span/a"));
		assertEquals(RuntimeVariables.replace("Liferay"),
			selenium.getText(
				"//div[@class='portlet-body']/ul/li[@class='first']/span/a"));
		assertEquals(RuntimeVariables.replace("Breadcrumb Test Page"),
			selenium.getText("//div[@class='portlet-body']/ul/li[2]/span/a"));
		assertEquals(RuntimeVariables.replace("Child Test Page"),
			selenium.getText(
				"//div[@class='portlet-body']/ul/li[@class='last']/span/a"));
	}
}