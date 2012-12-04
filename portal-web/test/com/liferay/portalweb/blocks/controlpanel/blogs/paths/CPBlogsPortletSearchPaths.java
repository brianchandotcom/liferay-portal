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

package com.liferay.portalweb.blocks.controlpanel.blogs.paths;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPBlogsPortletSearchPaths {
	public static Map<String, String[]> getPaths() {
		return _paths;
	}

	private static String[] _PORTLET_TITLE = {
			"//span[@class='portlet-title-text']", "Blogs"
		};
	private static String[] _PORTLET_DESCRIPTION = {
			"//div[@id='cpContextPanelTemplate']",
			"Blogs are short journals, articles, or diary entries that provide a standard blog experience. Administrators can add, view, update and delete blog entries."
		};
	private static String[] _PAGE_TITLE = {
			"//h1[@class='header-title']/span", "Search"
		};
	private static String[] _BACK_LINK = { "//a[@id='_161_TabsBack']", "Back" };
	private static String[] _SEARCH_FIELD = { "//input[@id='_161_keywords']", "" };
	private static String[] _SEARCH_BUTTON = {
			"//input[@value='Search']", "Search"
		};
	private static String[] _PORTLET_INFO = {
			"//div[@class='portlet-msg-info']",
			"No entries were found that matched the keywords:"
		};
	private static String[] _TABLE_NUMBER_HEADER = { "//tr[1]/th[1]", "#" };
	private static String[] _TABLE_ENTRY_HEADER = { "//tr[1]/th[2]", "Entry" };
	private static String[] _TABLE_NUMBER = {
			"//tr[contains(.,'Blogs Entry Title')]/td[1]", "1."
		};
	private static String[] _TABLE_NUMBER_1 = { "//tr[3]/td[1]", "1." };
	private static String[] _TABLE_NUMBER_2 = { "//tr[3]/td[1]", "2." };
	private static String[] _TABLE_NUMBER_3 = { "//tr[3]/td[1]", "3." };
	private static String[] _TABLE_ENTRY = {
			"//tr[contains(.,'Blogs Entry Title')]/td[2]/a", "Blogs Entry Title"
		};
	private static String[] _TABLE_ENTRY_1 = { "//tr[3]/td[2]/a", "" };
	private static String[] _TABLE_ENTRY_2 = { "//tr[3]/td[2]/a", "" };
	private static String[] _TABLE_ENTRY_3 = { "//tr[3]/td[2]/a", "" };
	private static String[] _SEARCH_RESULTS = {
			"//div[@class='search-results']", "Showing 1 result."
		};
	private static Map<String, String[]> _paths = new HashMap<String, String[]>();

	static {
		_paths.put("PORTLET_TITLE", _PORTLET_TITLE);
		_paths.put("PORTLET_DESCRIPTION", _PORTLET_DESCRIPTION);
		_paths.put("PAGE_TITLE", _PAGE_TITLE);
		_paths.put("BACK_LINK", _BACK_LINK);
		_paths.put("SEARCH_FIELD", _SEARCH_FIELD);
		_paths.put("SEARCH_BUTTON", _SEARCH_BUTTON);
		_paths.put("PORTLET_INFO", _PORTLET_INFO);
		_paths.put("TABLE_NUMBER_HEADER", _TABLE_NUMBER_HEADER);
		_paths.put("TABLE_ENTRY_HEADER", _TABLE_ENTRY_HEADER);
		_paths.put("TABLE_NUMBER", _TABLE_NUMBER);
		_paths.put("TABLE_NUMBER_1", _TABLE_NUMBER_1);
		_paths.put("TABLE_NUMBER_2", _TABLE_NUMBER_2);
		_paths.put("TABLE_NUMBER_3", _TABLE_NUMBER_3);
		_paths.put("TABLE_ENTRY", _TABLE_ENTRY);
		_paths.put("TABLE_ENTRY_1", _TABLE_ENTRY_1);
		_paths.put("TABLE_ENTRY_2", _TABLE_ENTRY_2);
		_paths.put("TABLE_ENTRY_3", _TABLE_ENTRY_3);
		_paths.put("SEARCH_RESULTS", _SEARCH_RESULTS);
	}
}