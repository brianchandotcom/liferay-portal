/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.asset.messageboards.mbthread.viewportlettablembcategorythreadmessageap;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletTableMBCategoryThreadMessageAPTest extends BaseTestCase {
	public void testViewPortletTableMBCategoryThreadMessageAP()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Add New"),
			selenium.getText("//span[@title='Add New']/ul/li/strong/a/span"));
		assertEquals(RuntimeVariables.replace("Title"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th"));
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message Subject"),
			selenium.getText(
				"//tr[contains(.,'MB Category Thread Message Subject')]/td[1]/a"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'MB Category Thread Message Subject')]/td[2]/div/span/a"));
		selenium.clickAt("//tr[contains(.,'MB Category Thread Message Subject')]/td[1]/a",
			RuntimeVariables.replace("MB Category Thread Message Subject"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message Subject"),
			selenium.getText("//h1[@class='header-title']"));
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("//span[@class='header-back-to']/a"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-meta-actions asset-actions']/span/a[contains(.,Edit)]/span",
				"Edit"));
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message Subject"),
			selenium.getText("//div[@class='subject']/strong"));
		assertEquals(RuntimeVariables.replace("MB Category Thread Message Body"),
			selenium.getText("//div[@class='thread-body']"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//span[@class='user-name']"));
		assertTrue(selenium.isVisible(
				"//li[@class='taglib-social-bookmark-twitter']"));
		assertTrue(selenium.isVisible(
				"//li[@class='taglib-social-bookmark-facebook']"));
		assertTrue(selenium.isVisible(
				"//li[@class='taglib-social-bookmark-plusone']"));
		assertEquals(RuntimeVariables.replace("View in Context \u00bb"),
			selenium.getText("//div[@class='asset-more']/a"));
		selenium.clickAt("//div[@class='asset-more']/a",
			RuntimeVariables.replace("View in Context \u00bb"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message Subject"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message Subject"),
			selenium.getText("//div[@class='subject']/a/strong"));
		assertEquals(RuntimeVariables.replace("MB Category Thread Message Body"),
			selenium.getText("//div[@class='thread-body']"));
	}
}