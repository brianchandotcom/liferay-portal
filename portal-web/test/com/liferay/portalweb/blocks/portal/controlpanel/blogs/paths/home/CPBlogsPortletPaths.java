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
public class CPBlogsPortletPaths {
	public static Map<String, String[]> getPaths() {
		return _paths;
	}

	private static String[] _PAGE_NAME = { "", "Control Panel Blogs Portlet" };
	private static String[] _BLOGS_ENTRY_LINK_CHECKBOX_ALL = {
			"//input[@name='_161_allRowIds']",
			"Select All Blog Entries Checkbox"
		};
	private static String[] _BLOGS_ENTRY_LINK_CHECKBOX = {
			"//tr[contains(.,'Blogs Entry Title')]/td[1]/input[@name='_161_rowIds']",
			"Blog Entry Select Checkbox"
		};
	private static String[] _BLOGS_ENTRY_LINK_CHECKBOX_1 = {
			"//tr[3]/td[1]/input[@name='_161_rowIds']",
			"First Blog Entry Select Checkbox"
		};
	private static String[] _BLOGS_ENTRY_LINK_CHECKBOX_2 = {
			"//tr[4]/td[1]/input[@name='_161_rowIds']",
			"Second Blog Entry Select Checkbox"
		};
	private static String[] _BLOGS_ENTRY_LINK_CHECKBOX_3 = {
			"//tr[5]/td[1]/input[@name='_161_rowIds']",
			"Third Blog Entry Select Checkbox"
		};
	private static String[] _BLOGS_ENTRY_LINK_TITLE = {
			"//tr[contains(.,'Blogs Entry Title')]/td[2]/a",
			"Blogs Entry Title Link"
		};
	private static String[] _BLOGS_ENTRY_LINK_TITLE_1 = {
			"//tr[3]/td[2]/a", "First Blogs Entry Title Link"
		};
	private static String[] _BLOGS_ENTRY_LINK_TITLE_2 = {
			"//tr[4]/td[2]/a", "Second Blogs Entry Title Link"
		};
	private static String[] _BLOGS_ENTRY_LINK_TITLE_3 = {
			"//tr[5]/td[2]/a", "Third Blogs Entry Title Link"
		};
	private static String[] _BLOGS_ENTRY_LINK_AUTHOR = {
			"//tr[contains(.,'Blogs Entry Title')]/td[3]/a",
			"Blog Entry Author Link"
		};
	private static String[] _BLOGS_ENTRY_LINK_AUTHOR_1 = {
			"//tr[3]/td[3]/a", "First Blog Entry Author Link"
		};
	private static String[] _BLOGS_ENTRY_LINK_AUTHOR_2 = {
			"//tr[4]/td[3]/a", "Second Blog Entry Author Link"
		};
	private static String[] _BLOGS_ENTRY_LINK_AUTHOR_3 = {
			"//tr[5]/td[3]/a", "Third Blog Entry Author Link"
		};
	private static String[] _BLOGS_ENTRY_LINK_CREATED_DATE = {
			"//tr[contains(.,'Blogs Entry Title')]/td[4]/a",
			"Blog Entry Created Date Link"
		};
	private static String[] _BLOGS_ENTRY_LINK_CREATED_DATE_1 = {
			"//tr[3]/td[4]/a", "First Blog Entry Created Date Link"
		};
	private static String[] _BLOGS_ENTRY_LINK_CREATED_DATE_2 = {
			"//tr[4]/td[4]/a", "Second Blog Entry Created Date Link"
		};
	private static String[] _BLOGS_ENTRY_LINK_CREATED_DATE_3 = {
			"//tr[5]/td[4]/a", "Third Blog Entry Created Date Link"
		};
	private static String[] _BLOGS_ENTRY_LINK_STATUS = {
			"//tr[contains(.,'Blogs Entry Title')]/td[5]/a",
			"Blog Entry Status Link"
		};
	private static String[] _BLOGS_ENTRY_LINK_STATUS_1 = {
			"//tr[3]/td[5]/a", "First Blog Entry Status Link"
		};
	private static String[] _BLOGS_ENTRY_LINK_STATUS_2 = {
			"//tr[4]/td[5]/a", "Second Blog Entry Status Link"
		};
	private static String[] _BLOGS_ENTRY_LINK_STATUS_3 = {
			"//tr[5]/td[5]/a", "Third Blog Entry Status Link"
		};
	private static String[] _BLOGS_ENTRY_LINK_ACTIONS = {
			"//tr[contains(.,'Blogs Entry Title')]/td/span/ul/li/strong/a/span",
			"Actions Link"
		};
	private static String[] _BLOGS_ENTRY_LINK_ACTIONS_1 = {
			"//tr[3]/td/span/ul/li/strong/a/span", "Blog Entry 1 Actions Link"
		};
	private static String[] _BLOGS_ENTRY_LINK_ACTIONS_2 = {
			"//tr[4]/td/span/ul/li/strong/a/span", "Blog Entry 2 Actions Link"
		};
	private static String[] _BLOGS_ENTRY_LINK_ACTIONS_3 = {
			"//tr[5]/td/span/ul/li/strong/a/span", "Blog Entry 3 Actions Link"
		};
	private static String[] _BLOGS_ENTRY_LINK_ACTIONS_DELETE = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Delete')]",
			"Actions Menu Delete Link"
		};
	private static String[] _BLOGS_ENTRY_LINK_ACTIONS_EDIT = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]",
			"Actions Menu Edit Link"
		};
	private static String[] _BLOGS_ENTRY_LINK_ACTIONS_PERMISSION = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Permissions')]",
			"Actions Menu Permissions Link"
		};
	private static String[] _BLOGS_ENTRY_LINK_ACTIONS_VIEW = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'View')]",
			"Actions Menu View Link"
		};
	private static String[] _BLOGS_ENTRY_LINK_CONFIRM_DELETE_ACTIONS = {
			"",
			"Are you sure you want to delete this? Entry Delete Confirm Message"
		};
	private static String[] _BLOGS_ENTRY_LINK_CONFIRM_DELETE_LIST = {
			"",
			"Are you sure you want to delete the selected entries? Delete Multiple Entries Confirm Message"
		};
	private static String[] _PORTLET_LINK_BREADCRUMB_1 = {
			"//nav[@id='breadcrumbs']/ul/li[1]/span/a", "1st Breadcrumb"
		};
	private static String[] _PORTLET_LINK_BREADCRUMB_2 = {
			"//nav[@id='breadcrumbs']/ul/li[2]/span/a", "2nd Breadcrumb"
		};
	private static String[] _PORTLET_LINK_BREADCRUMB_3 = {
			"//nav[@id='breadcrumbs']/ul/li[3]/span/a", "3rd Breadcrumb"
		};
	private static String[] _PORTLET_TEXT_TITLE = {
			"//span[@class='portlet-title-text']", "Portlet Title"
		};
	private static String[] _PORTLET_LINK_OPTIONS = {
			"//span[@title='Options']/ul/li/strong/a",
			"Portlet Configuration Options Button"
		};
	private static String[] _PORTLET_LINK_OPTIONS_EXPORT_IMPORT = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Export / Import')]",
			"Portlet Configuration Options Menu Export / Import Link"
		};
	private static String[] _PORTLET_TEXT_DESCRIPTION = {
			"cpContextPanelTemplate", "Portlet Description"
		};
	private static String[] _PORTLET_LINK_ADD = {
			"//span[contains(@class,'add-button')]/a",
			"Add New Blog Entry Button"
		};
	private static String[] _PORTLET_LINK_VIEW_ALL = {
			"//span[contains(@class,'view-button')]/a", "Portlet View All Tab"
		};
	private static String[] _PORTLET_FIELD_SEARCH = {
			"//input[@id='_161_keywords']", "Portlet Search Field"
		};
	private static String[] _PORTLET_LINK_SEARCH = {
			"//input[@value='Search']", "Portlet Search Button"
		};
	private static String[] _PORTLET_LINK_DELETE = {
			"//input[@value='Delete']", "Delete Button"
		};
	private static String[] _PORTLET_TEXT_RESULTS = {
			"//div[@class='search-results']", "Portlet Search Results"
		};
	private static String[] _PORTLET_TEXT_INFO = {
			"//div[@class='portlet-msg-info']",
			"No entries were found. Search Results Message"
		};
	private static String[] _PORTLET_TEXT_SUCCESS = {
			"//div[@class='portlet-msg-success']",
			"Your request completed successfully. Success Message"
		};
	private static Map<String, String[]> _paths = new HashMap<String, String[]>();

	static {
		_paths.put("PAGE_NAME", _PAGE_NAME);
		_paths.put("BLOGS_ENTRY_LINK_CHECKBOX_ALL",
			_BLOGS_ENTRY_LINK_CHECKBOX_ALL);
		_paths.put("BLOGS_ENTRY_LINK_CHECKBOX", _BLOGS_ENTRY_LINK_CHECKBOX);
		_paths.put("BLOGS_ENTRY_LINK_CHECKBOX_1", _BLOGS_ENTRY_LINK_CHECKBOX_1);
		_paths.put("BLOGS_ENTRY_LINK_CHECKBOX_2", _BLOGS_ENTRY_LINK_CHECKBOX_2);
		_paths.put("BLOGS_ENTRY_LINK_CHECKBOX_3", _BLOGS_ENTRY_LINK_CHECKBOX_3);
		_paths.put("BLOGS_ENTRY_LINK_TITLE", _BLOGS_ENTRY_LINK_TITLE);
		_paths.put("BLOGS_ENTRY_LINK_TITLE_1", _BLOGS_ENTRY_LINK_TITLE_1);
		_paths.put("BLOGS_ENTRY_LINK_TITLE_2", _BLOGS_ENTRY_LINK_TITLE_2);
		_paths.put("BLOGS_ENTRY_LINK_TITLE_3", _BLOGS_ENTRY_LINK_TITLE_3);
		_paths.put("BLOGS_ENTRY_LINK_AUTHOR", _BLOGS_ENTRY_LINK_AUTHOR);
		_paths.put("BLOGS_ENTRY_LINK_AUTHOR_1", _BLOGS_ENTRY_LINK_AUTHOR_1);
		_paths.put("BLOGS_ENTRY_LINK_AUTHOR_2", _BLOGS_ENTRY_LINK_AUTHOR_2);
		_paths.put("BLOGS_ENTRY_LINK_AUTHOR_3", _BLOGS_ENTRY_LINK_AUTHOR_3);
		_paths.put("BLOGS_ENTRY_LINK_CREATED_DATE",
			_BLOGS_ENTRY_LINK_CREATED_DATE);
		_paths.put("BLOGS_ENTRY_LINK_CREATED_DATE_1",
			_BLOGS_ENTRY_LINK_CREATED_DATE_1);
		_paths.put("BLOGS_ENTRY_LINK_CREATED_DATE_2",
			_BLOGS_ENTRY_LINK_CREATED_DATE_2);
		_paths.put("BLOGS_ENTRY_LINK_CREATED_DATE_3",
			_BLOGS_ENTRY_LINK_CREATED_DATE_3);
		_paths.put("BLOGS_ENTRY_LINK_STATUS", _BLOGS_ENTRY_LINK_STATUS);
		_paths.put("BLOGS_ENTRY_LINK_STATUS_1", _BLOGS_ENTRY_LINK_STATUS_1);
		_paths.put("BLOGS_ENTRY_LINK_STATUS_2", _BLOGS_ENTRY_LINK_STATUS_2);
		_paths.put("BLOGS_ENTRY_LINK_STATUS_3", _BLOGS_ENTRY_LINK_STATUS_3);
		_paths.put("BLOGS_ENTRY_LINK_ACTIONS", _BLOGS_ENTRY_LINK_ACTIONS);
		_paths.put("BLOGS_ENTRY_LINK_ACTIONS_1", _BLOGS_ENTRY_LINK_ACTIONS_1);
		_paths.put("BLOGS_ENTRY_LINK_ACTIONS_2", _BLOGS_ENTRY_LINK_ACTIONS_2);
		_paths.put("BLOGS_ENTRY_LINK_ACTIONS_3", _BLOGS_ENTRY_LINK_ACTIONS_3);
		_paths.put("BLOGS_ENTRY_LINK_ACTIONS_DELETE",
			_BLOGS_ENTRY_LINK_ACTIONS_DELETE);
		_paths.put("BLOGS_ENTRY_LINK_ACTIONS_EDIT",
			_BLOGS_ENTRY_LINK_ACTIONS_EDIT);
		_paths.put("BLOGS_ENTRY_LINK_ACTIONS_PERMISSION",
			_BLOGS_ENTRY_LINK_ACTIONS_PERMISSION);
		_paths.put("BLOGS_ENTRY_LINK_ACTIONS_VIEW",
			_BLOGS_ENTRY_LINK_ACTIONS_VIEW);
		_paths.put("BLOGS_ENTRY_LINK_CONFIRM_DELETE_ACTIONS",
			_BLOGS_ENTRY_LINK_CONFIRM_DELETE_ACTIONS);
		_paths.put("BLOGS_ENTRY_LINK_CONFIRM_DELETE_LIST",
			_BLOGS_ENTRY_LINK_CONFIRM_DELETE_LIST);
		_paths.put("PORTLET_LINK_BREADCRUMB_1", _PORTLET_LINK_BREADCRUMB_1);
		_paths.put("PORTLET_LINK_BREADCRUMB_2", _PORTLET_LINK_BREADCRUMB_2);
		_paths.put("PORTLET_LINK_BREADCRUMB_3", _PORTLET_LINK_BREADCRUMB_3);
		_paths.put("PORTLET_TEXT_TITLE", _PORTLET_TEXT_TITLE);
		_paths.put("PORTLET_LINK_OPTIONS", _PORTLET_LINK_OPTIONS);
		_paths.put("PORTLET_LINK_OPTIONS_EXPORT_IMPORT",
			_PORTLET_LINK_OPTIONS_EXPORT_IMPORT);
		_paths.put("PORTLET_TEXT_DESCRIPTION", _PORTLET_TEXT_DESCRIPTION);
		_paths.put("PORTLET_LINK_ADD", _PORTLET_LINK_ADD);
		_paths.put("PORTLET_LINK_VIEW_ALL", _PORTLET_LINK_VIEW_ALL);
		_paths.put("PORTLET_FIELD_SEARCH", _PORTLET_FIELD_SEARCH);
		_paths.put("PORTLET_LINK_SEARCH", _PORTLET_LINK_SEARCH);
		_paths.put("PORTLET_LINK_DELETE", _PORTLET_LINK_DELETE);
		_paths.put("PORTLET_TEXT_RESULTS", _PORTLET_TEXT_RESULTS);
		_paths.put("PORTLET_TEXT_INFO", _PORTLET_TEXT_INFO);
		_paths.put("PORTLET_TEXT_SUCCESS", _PORTLET_TEXT_SUCCESS);
	}
}