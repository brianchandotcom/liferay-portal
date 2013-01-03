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

package com.liferay.portalweb.blocks.portal.blogs.controlpanel.actions.home.rc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPBlogsSearchPaths {
	public static Map<String, String> getPaths() {
		return _paths;
	}

	private static Map<String, String> _paths = new HashMap<String, String>();

	static {
		_paths.put("PAGE_NAME", "");
		_paths.put("PORTLET_TITLE", "//span[@class='portlet-title-text']");
		_paths.put("PORTLET_DESCRIPTION", "//div[@id='cpContextPanelTemplate']");
		_paths.put("PORTLET_PAGE_TITLE", "//h1[@class='header-title']/span");
		_paths.put("PORTLET_INFO", "//div[@class='portlet-msg-info']");
		_paths.put("PORTLET_BACK_LINK", "//a[@id='_161_TabsBack']");
		_paths.put("SEARCH_FIELD", "//input[@id='_161_keywords']");
		_paths.put("SEARCH_BUTTON", "//input[@value='Search']");
		_paths.put("SEARCH_RESULTS", "//div[@class='search-results']");
		_paths.put("TABLE_NUMBER_HEADER", "//tr[1]/th[1]");
		_paths.put("TABLE_ENTRY_HEADER", "//tr[1]/th[2]");
		_paths.put("TABLE_NUMBER", "//tr[contains(.,'Blogs Entry Title')]/td[1]");
		_paths.put("TABLE_NUMBER_1", "//tr[3]/td[1]");
		_paths.put("TABLE_NUMBER_2", "//tr[3]/td[1]");
		_paths.put("TABLE_NUMBER_3", "//tr[3]/td[1]");
		_paths.put("TABLE_ENTRY",
			"//tr[contains(.,'Blogs Entry Title')]/td[2]/a");
		_paths.put("TABLE_ENTRY_1", "//tr[3]/td[2]/a");
		_paths.put("TABLE_ENTRY_2", "//tr[3]/td[2]/a");
		_paths.put("TABLE_ENTRY_3", "//tr[3]/td[2]/a");
	}
}