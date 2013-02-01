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

package com.liferay.portalweb.portal.controlpanel.adt.blogs.viewportletblogsdisplaytemplatebasic;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletBlogsEntryDisplayTemplateBasicTest extends BaseTestCase {
	public void testViewPortletBlogsEntryDisplayTemplateBasic()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Blogs Test Page",
			RuntimeVariables.replace("Blogs Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs Entry Title"),
			selenium.getText("//div[@class='entry-title']"));
		assertTrue(selenium.isVisible("//div[@class='entry-body']/a"));
		assertTrue(selenium.isElementNotPresent(
				"//table[@class='lfr-table']/tbody/tr/td/span/a"));
		assertTrue(selenium.isElementNotPresent(
				"//table[@class='lfr-table']/tbody/tr/td[2]/span/a"));
		assertTrue(selenium.isElementNotPresent(
				"//table[@class='lfr-table']/tbody/tr/td[3]/span/a"));
		assertTrue(selenium.isElementNotPresent("//span[@class='comments']"));
		assertTrue(selenium.isElementNotPresent("//div[@id='widget']/div/a"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='pluginConnectButton']/div"));
		assertTrue(selenium.isElementNotPresent(
				"//span[@id='widget_bounds']/table/tbody/tr/td/div/span"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@id='zyfa_ratingStarContent']"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@id='zyfa_ratingScoreContent']"));
	}
}