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

package com.liferay.portalweb.plugins.knowledgebase.knowledgebasedisplay.kbaarticle.searchkbaarticlekbd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchKBAArticleKBDTest extends BaseTestCase {
	public void testSearchKBAArticleKBD() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Knowledge Base Display Test Page",
			RuntimeVariables.replace("Knowledge Base Display Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@title='Search Articles']",
			RuntimeVariables.replace("Knowledge Base Admin Article Title"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Title"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[1]/span"));
		assertEquals(RuntimeVariables.replace("Author"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[2]/span"));
		assertEquals(RuntimeVariables.replace("Create Date"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[3]/span"));
		assertEquals(RuntimeVariables.replace("Modified Date"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[4]/span"));
		assertEquals(RuntimeVariables.replace("Status"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[5]/span"));
		assertEquals(RuntimeVariables.replace(
				"Knowledge Base Admin Article Title"),
			selenium.getText("//tr[contains(@class,'results-row last')]/td[1]"));
		assertEquals(RuntimeVariables.replace(
				"Knowledge Base Admin Article Title"),
			selenium.getText(
				"//tr[contains(.,'Knowledge Base Admin Article Title')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText(
				"//tr[contains(.,'Knowledge Base Admin Article Title')]/td[2]/a"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'Knowledge Base Admin Article Title')]/td[3]/a"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'Knowledge Base Admin Article Title')]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("0 (Approved)"),
			selenium.getText(
				"//tr[contains(.,'Knowledge Base Admin Article Title')]/td[5]/a"));
		assertEquals(RuntimeVariables.replace("0 Views"),
			selenium.getText(
				"//tr[contains(.,'Knowledge Base Admin Article Title')]/td[6]/a"));
		assertEquals(RuntimeVariables.replace("Showing 1 result."),
			selenium.getText("//div[@class='search-results']"));
	}
}