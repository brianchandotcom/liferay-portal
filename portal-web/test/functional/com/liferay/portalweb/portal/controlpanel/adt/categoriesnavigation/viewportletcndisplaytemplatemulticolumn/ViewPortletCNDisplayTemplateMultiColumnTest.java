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

package com.liferay.portalweb.portal.controlpanel.adt.categoriesnavigation.viewportletcndisplaytemplatemulticolumn;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletCNDisplayTemplateMultiColumnTest extends BaseTestCase {
	public void testViewPortletCNDisplayTemplateMultiColumn()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Categories Navigation Test Page",
			RuntimeVariables.replace("Categories Navigation Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Categories Navigation"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertEquals(RuntimeVariables.replace("Topic"),
			selenium.getText(
				"xPath=(//div[@class='results-header']/h3[contains(.,'Topic')])[1]"));
		assertEquals(RuntimeVariables.replace("Topic"),
			selenium.getText(
				"xPath=(//div[@class='results-header']/h3[contains(.,'Topic')])[2]"));
		assertEquals(RuntimeVariables.replace("Category Name"),
			selenium.getText(
				"//ul[@class='categories']/li[contains(.,'Category Name')]"));
	}
}