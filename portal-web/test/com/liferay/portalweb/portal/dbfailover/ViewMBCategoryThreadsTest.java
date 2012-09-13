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

package com.liferay.portalweb.portal.dbfailover;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewMBCategoryThreadsTest extends BaseTestCase {
	public void testViewMBCategoryThreads() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Message Boards Home"),
			selenium.getText("//div[@class='top-links']/ul/li/span"));
		assertEquals(RuntimeVariables.replace("Recent Posts"),
			selenium.getText("//div[@class='top-links']/ul/li[2]/span"));
		assertEquals(RuntimeVariables.replace("My Posts"),
			selenium.getText("//div[@class='top-links']/ul/li[3]/span"));
		assertEquals(RuntimeVariables.replace("My Subscriptions"),
			selenium.getText("//div[@class='top-links']/ul/li[4]/span"));
		assertEquals(RuntimeVariables.replace("Statistics"),
			selenium.getText("//div[@class='top-links']/ul/li[5]/span"));
		assertEquals(RuntimeVariables.replace("Banned Users"),
			selenium.getText("//div[@class='top-links']/ul/li[6]/span"));
		assertTrue(selenium.isVisible("//input[@id='_19_keywords1']"));
		assertTrue(selenium.isVisible("//input[@value='Search']"));
		assertEquals(RuntimeVariables.replace("RSS"),
			selenium.getText(
				"//div[@class='category-subscription-types']/span[1]/a/span"));
		assertEquals(RuntimeVariables.replace("Subscribe"),
			selenium.getText(
				"//div[@class='category-subscription-types']/span[2]/a/span"));
		assertTrue(selenium.isVisible("//input[@value='Add Category']"));
		assertTrue(selenium.isVisible("//input[@value='Post New Thread']"));
		assertTrue(selenium.isVisible("//input[@value='Permissions']"));
		assertEquals(RuntimeVariables.replace("Categories"),
			selenium.getText(
				"//div[@id='messageBoardsCategoriesPanel']/div/div/span"));
		assertEquals(RuntimeVariables.replace("MB Category Name"),
			selenium.getText("//tr[3]/td/a/strong"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//tr[3]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("5"),
			selenium.getText("//tr[3]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("5"),
			selenium.getText("//tr[3]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[3]/td[5]/span[@title='Actions']/ul/li/strong/a/span"));
		assertEquals(RuntimeVariables.replace("Showing 1 result."),
			selenium.getText("//div[@class='search-results']"));
		assertEquals(RuntimeVariables.replace("Threads"),
			selenium.getText(
				"//div[@id='messageBoardsThreadsPanel']/div/div/span"));
		assertEquals(RuntimeVariables.replace(
				"There are no threads in this category."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		selenium.clickAt("//tr[3]/td/a/strong",
			RuntimeVariables.replace("MB Category Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Message Boards Home"),
			selenium.getText("//div[@class='top-links']/ul/li/span"));
		assertEquals(RuntimeVariables.replace("Recent Posts"),
			selenium.getText("//div[@class='top-links']/ul/li[2]/span"));
		assertEquals(RuntimeVariables.replace("My Posts"),
			selenium.getText("//div[@class='top-links']/ul/li[3]/span"));
		assertEquals(RuntimeVariables.replace("My Subscriptions"),
			selenium.getText("//div[@class='top-links']/ul/li[4]/span"));
		assertEquals(RuntimeVariables.replace("Statistics"),
			selenium.getText("//div[@class='top-links']/ul/li[5]/span"));
		assertEquals(RuntimeVariables.replace("Banned Users"),
			selenium.getText("//div[@class='top-links']/ul/li[6]/span"));
		assertTrue(selenium.isElementPresent("//input[@id='_19_keywords1']"));
		assertTrue(selenium.isElementPresent("//input[@value='Search']"));
		assertTrue(selenium.isElementPresent(
				"//input[@value='Add Subcategory']"));
		assertTrue(selenium.isElementPresent(
				"//input[@value='Post New Thread']"));
		assertTrue(selenium.isElementPresent("//input[@value='Permissions']"));
		assertEquals(RuntimeVariables.replace("MB Category Name"),
			selenium.getText("//h1[@class='header-title']"));
		assertEquals(RuntimeVariables.replace(
				"\u00ab Back to Message Boards Home"),
			selenium.getText("//span[@class='header-back-to']/a"));
		assertEquals(RuntimeVariables.replace("Threads"),
			selenium.getText(
				"//div[@id='messageBoardsThreadsPanel']/div/div/span"));
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread5 Message Subject"),
			selenium.getText(
				"//td[@id='_19_mbThreadsSearchContainer_col-thread_row-1']"));
		assertTrue(selenium.isVisible(
				"//td[@id='_19_mbThreadsSearchContainer_col-flag_row-1']"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText(
				"//td[@id='_19_mbThreadsSearchContainer_col-started-by_row-1']"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText(
				"//td[@id='_19_mbThreadsSearchContainer_col-posts_row-1']"));
		assertTrue(selenium.isVisible(
				"//td[@id='_19_mbThreadsSearchContainer_col-views_row-1']"));
		assertTrue(selenium.isPartialText(
				"//td[@id='_19_mbThreadsSearchContainer_col-last-post_row-1']",
				"By: Joe Bloggs"));
		assertTrue(selenium.isPartialText(
				"//td[@id='_19_mbThreadsSearchContainer_col-7_row-1']",
				"Actions"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"xPath=(//span[@title='Actions']/ul/li/strong/a/span)[1]"));
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread4 Message Subject"),
			selenium.getText(
				"//td[@id='_19_mbThreadsSearchContainer_col-thread_row-2']"));
		assertTrue(selenium.isVisible(
				"//td[@id='_19_mbThreadsSearchContainer_col-flag_row-2']"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText(
				"//td[@id='_19_mbThreadsSearchContainer_col-started-by_row-2']"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText(
				"//td[@id='_19_mbThreadsSearchContainer_col-posts_row-2']"));
		assertTrue(selenium.isVisible(
				"//td[@id='_19_mbThreadsSearchContainer_col-views_row-2']"));
		assertTrue(selenium.isPartialText(
				"//td[@id='_19_mbThreadsSearchContainer_col-last-post_row-2']",
				"By: Joe Bloggs"));
		assertTrue(selenium.isPartialText(
				"//td[@id='_19_mbThreadsSearchContainer_col-7_row-2']",
				"Actions"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"xPath=(//span[@title='Actions']/ul/li/strong/a/span)[2]"));
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread3 Message Subject"),
			selenium.getText(
				"//td[@id='_19_mbThreadsSearchContainer_col-thread_row-3']"));
		assertTrue(selenium.isVisible(
				"//td[@id='_19_mbThreadsSearchContainer_col-flag_row-3']"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText(
				"//td[@id='_19_mbThreadsSearchContainer_col-started-by_row-3']"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText(
				"//td[@id='_19_mbThreadsSearchContainer_col-posts_row-3']"));
		assertTrue(selenium.isVisible(
				"//td[@id='_19_mbThreadsSearchContainer_col-views_row-3']"));
		assertTrue(selenium.isPartialText(
				"//td[@id='_19_mbThreadsSearchContainer_col-last-post_row-3']",
				"By: Joe Bloggs"));
		assertTrue(selenium.isPartialText(
				"//td[@id='_19_mbThreadsSearchContainer_col-7_row-3']",
				"Actions"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"xPath=(//span[@title='Actions']/ul/li/strong/a/span)[3]"));
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread2 Message Subject"),
			selenium.getText(
				"//td[@id='_19_mbThreadsSearchContainer_col-thread_row-4']"));
		assertTrue(selenium.isVisible(
				"//td[@id='_19_mbThreadsSearchContainer_col-flag_row-4']"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText(
				"//td[@id='_19_mbThreadsSearchContainer_col-started-by_row-4']"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText(
				"//td[@id='_19_mbThreadsSearchContainer_col-posts_row-4']"));
		assertTrue(selenium.isVisible(
				"//td[@id='_19_mbThreadsSearchContainer_col-views_row-4']"));
		assertTrue(selenium.isPartialText(
				"//td[@id='_19_mbThreadsSearchContainer_col-last-post_row-4']",
				"By: Joe Bloggs"));
		assertTrue(selenium.isPartialText(
				"//td[@id='_19_mbThreadsSearchContainer_col-7_row-4']",
				"Actions"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"xPath=(//span[@title='Actions']/ul/li/strong/a/span)[4]"));
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread1 Message Subject"),
			selenium.getText(
				"//td[@id='_19_mbThreadsSearchContainer_col-thread_row-5']"));
		assertTrue(selenium.isVisible(
				"//td[@id='_19_mbThreadsSearchContainer_col-flag_row-5']"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText(
				"//td[@id='_19_mbThreadsSearchContainer_col-started-by_row-5']"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText(
				"//td[@id='_19_mbThreadsSearchContainer_col-posts_row-5']"));
		assertTrue(selenium.isVisible(
				"//td[@id='_19_mbThreadsSearchContainer_col-views_row-5']"));
		assertTrue(selenium.isPartialText(
				"//td[@id='_19_mbThreadsSearchContainer_col-last-post_row-5']",
				"By: Joe Bloggs"));
		assertTrue(selenium.isPartialText(
				"//td[@id='_19_mbThreadsSearchContainer_col-7_row-5']",
				"Actions"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"xPath=(//span[@title='Actions']/ul/li/strong/a/span)[5]"));
		assertEquals(RuntimeVariables.replace("Showing 5 results."),
			selenium.getText("//div[@class='search-results']"));
	}
}