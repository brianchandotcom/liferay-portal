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

package com.liferay.portalweb.plugins.sampleapplicationadapter.site.configuresitesapplicationadapternone;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletApplicationAdapterNoneTest extends BaseTestCase {
	public void testViewPortletApplicationAdapterNone()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/community-name/public-page");
		selenium.waitForVisible("link=Public Page");
		selenium.clickAt("link=Public Page",
			RuntimeVariables.replace("Public Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//section");
		assertTrue(selenium.isVisible("//section"));
		assertEquals(RuntimeVariables.replace("Liferay"),
			selenium.getText(
				"//div[@class='portlet-body']/div/ul[contains(@class,'breadcrumbs-horizontal')]/li[1]/span/a"));
		assertEquals(RuntimeVariables.replace("Community Name"),
			selenium.getText(
				"//div[@class='portlet-body']/div/ul[contains(@class,'breadcrumbs-horizontal')]/li[2]/span/a"));
		assertEquals(RuntimeVariables.replace("Public Page"),
			selenium.getText(
				"//div[@class='portlet-body']/div/ul[contains(@class,'breadcrumbs-horizontal')]/li[3]/span/a"));
		assertFalse(selenium.isTextPresent(
				"This was modified by the Sample Application Adapter."));
	}
}