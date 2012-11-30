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

package com.liferay.portalweb.blocks.portal.controlpanel.blogs.paths.home;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPBlogsPortletSearchPaths {
	public static Map<String, String[]> getPaths() {
		return _paths;
	}

	private static String[] _PAGE_NAME = {
			"", "Control Panel Blogs Portlet Search"
		};
	private static String[] _PORTLET_TITLE = {
			"//span[@class='portlet-title-text']", "Portlet Title"
		};
	private static String[] _PORTLET_DESCRIPTION = {
			"//div[@id='cpContextPanelTemplate']", "Portlet Description"
		};
	private static String[] _PORTLET_PAGE_TITLE = {
			"//h1[@class='header-title']/span", "Portlet Search Title"
		};
	private static String[] _PORTLET_INFO = {
			"//div[@class='portlet-msg-info']", "Portlet Info"
		};
	private static String[] _PORTLET_BACK_LINK = {
			"//a[@id='_161_TabsBack']", "Back Link"
		};
	private static String[] _SEARCH_FIELD = {
			"//input[@id='_161_keywords']", "Search Input Field"
		};
	private static String[] _SEARCH_BUTTON = {
			"//input[@value='Search']", "Search Submit Button"
		};
	private static String[] _SEARCH_RESULTS = {
			"//div[@class='search-results']", "Search Results Message"
		};
	private static String[] _TABLE_NUMBER_HEADER = {
			"//tr[1]/th[1]", "Table Number Header"
		};
	private static String[] _TABLE_ENTRY_HEADER = {
			"//tr[1]/th[2]", "Table Entry Header"
		};
	private static String[] _TABLE_NUMBER = {
			"//tr[contains(.,'Blogs Entry Title')]/td[1]", "Table Number"
		};
	private static String[] _TABLE_NUMBER_1 = {
			"//tr[3]/td[1]", "Table Entry 1 Number"
		};
	private static String[] _TABLE_NUMBER_2 = {
			"//tr[3]/td[1]", "Table Entry 2 Number"
		};
	private static String[] _TABLE_NUMBER_3 = {
			"//tr[3]/td[1]", "Table Entry 3 Number"
		};
	private static String[] _TABLE_ENTRY = {
			"//tr[contains(.,'Blogs Entry Title')]/td[2]/a", "Table Entry Title"
		};
	private static String[] _TABLE_ENTRY_1 = {
			"//tr[3]/td[2]/a", "Table Entry 1 Title"
		};
	private static String[] _TABLE_ENTRY_2 = {
			"//tr[3]/td[2]/a", "Table Entry 2 Title"
		};
	private static String[] _TABLE_ENTRY_3 = {
			"//tr[3]/td[2]/a", "Table Entry 3 Title"
		};
	private static Map<String, String[]> _paths = new HashMap<String, String[]>();

	static {
		_paths.put("PAGE_NAME", _PAGE_NAME);
		_paths.put("PORTLET_TITLE", _PORTLET_TITLE);
		_paths.put("PORTLET_DESCRIPTION", _PORTLET_DESCRIPTION);
		_paths.put("PORTLET_PAGE_TITLE", _PORTLET_PAGE_TITLE);
		_paths.put("PORTLET_INFO", _PORTLET_INFO);
		_paths.put("PORTLET_BACK_LINK", _PORTLET_BACK_LINK);
		_paths.put("SEARCH_FIELD", _SEARCH_FIELD);
		_paths.put("SEARCH_BUTTON", _SEARCH_BUTTON);
		_paths.put("SEARCH_RESULTS", _SEARCH_RESULTS);
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
	}
}