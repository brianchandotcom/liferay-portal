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
public class CPBlogsPortletPaths {
	public static Map<String, String[]> getPaths() {
		return _paths;
	}

	private static String[] _BLOGS_ENTRY_LINK_ACTIONS = {
			"//tr[contains(.,'Blogs Entry Title')]/td/span/ul/li/strong/a/span",
			"Actions"
		};
	private static String[] _BLOGS_ENTRY_LINK_ACTIONS_1 = {
			"//tr[3]/td/span/ul/li/strong/a/span", "Actions"
		};
	private static String[] _BLOGS_ENTRY_LINK_ACTIONS_2 = {
			"//tr[4]/td/span/ul/li/strong/a/span", "Actions"
		};
	private static String[] _BLOGS_ENTRY_LINK_ACTIONS_3 = {
			"//tr[5]/td/span/ul/li/strong/a/span", "Actions"
		};
	private static String[] _BLOGS_ENTRY_LINK_ACTIONS_DELETE = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Move to the Recycle Bin')]",
			"Move to the Recycle Bin"
		};
	private static String[] _BLOGS_ENTRY_LINK_ACTIONS_EDIT = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]",
			"Edit"
		};
	private static String[] _BLOGS_ENTRY_LINK_ACTIONS_PERMISSION = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Permissions')]",
			"Permissions"
		};
	private static String[] _BLOGS_ENTRY_LINK_ACTIONS_VIEW = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'View')]",
			"View"
		};
	private static String[] _BLOGS_ENTRY_LINK_AUTHOR = {
			"//tr[contains(.,'Blogs Entry Title')]/td[3]/a", "Joe Bloggs"
		};
	private static String[] _BLOGS_ENTRY_LINK_AUTHOR_1 = {
			"//tr[3]/td[3]/a", "Joe Bloggs"
		};
	private static String[] _BLOGS_ENTRY_LINK_AUTHOR_2 = {
			"//tr[4]/td[3]/a", "Joe Bloggs"
		};
	private static String[] _BLOGS_ENTRY_LINK_AUTHOR_3 = {
			"//tr[5]/td[3]/a", "Joe Bloggs"
		};
	private static String[] _BLOGS_ENTRY_LINK_CHECKBOX = {
			"//tr[contains(.,'Blogs Entry Title')]/td[1]/input[@name='_161_rowIds']",
			""
		};
	private static String[] _BLOGS_ENTRY_LINK_CHECKBOX_1 = {
			"//tr[3]/td[1]/input[@name='_161_rowIds']", ""
		};
	private static String[] _BLOGS_ENTRY_LINK_CHECKBOX_2 = {
			"//tr[4]/td[1]/input[@name='_161_rowIds']", ""
		};
	private static String[] _BLOGS_ENTRY_LINK_CHECKBOX_3 = {
			"//tr[5]/td[1]/input[@name='_161_rowIds']", ""
		};
	private static String[] _BLOGS_ENTRY_LINK_CHECKBOX_ALL = {
			"//input[@name='_161_allRowIds']", ""
		};
	private static String[] _BLOGS_ENTRY_LINK_CONFIRM_DELETE_ACTIONS = {
			"", "Are you sure you want to delete this?"
		};
	private static String[] _BLOGS_ENTRY_LINK_CONFIRM_DELETE_LIST = {
			"", "Are you sure you want to delete the selected entries?"
		};
	private static String[] _BLOGS_ENTRY_LINK_CREATED_DATE = {
			"//tr[contains(.,'Blogs Entry Title')]/td[4]/a", ""
		};
	private static String[] _BLOGS_ENTRY_LINK_CREATED_DATE_1 = {
			"//tr[3]/td[4]/a", ""
		};
	private static String[] _BLOGS_ENTRY_LINK_CREATED_DATE_2 = {
			"//tr[4]/td[4]/a", ""
		};
	private static String[] _BLOGS_ENTRY_LINK_CREATED_DATE_3 = {
			"//tr[5]/td[4]/a", ""
		};
	private static String[] _BLOGS_ENTRY_LINK_STATUS = {
			"//tr[contains(.,'Blogs Entry Title')]/td[5]/a", ""
		};
	private static String[] _BLOGS_ENTRY_LINK_STATUS_1 = { "//tr[3]/td[5]/a", "" };
	private static String[] _BLOGS_ENTRY_LINK_STATUS_2 = { "//tr[4]/td[5]/a", "" };
	private static String[] _BLOGS_ENTRY_LINK_STATUS_3 = { "//tr[5]/td[5]/a", "" };
	private static String[] _BLOGS_ENTRY_LINK_TITLE = {
			"//tr[contains(.,'Blogs Entry Title')]/td[2]/a", "Blogs Entry Title"
		};
	private static String[] _BLOGS_ENTRY_LINK_TITLE_1 = { "//tr[3]/td[2]/a", "" };
	private static String[] _BLOGS_ENTRY_LINK_TITLE_2 = { "//tr[4]/td[2]/a", "" };
	private static String[] _BLOGS_ENTRY_LINK_TITLE_3 = { "//tr[5]/td[2]/a", "" };
	private static String[] _PORTLET_FIELD_SEARCH = {
			"//input[@id='_161_keywords']", ""
		};
	private static String[] _PORTLET_LINK_ADD = {
			"//span[contains(@class,'add-button')]/a", "Add"
		};
	private static String[] _PORTLET_LINK_BREADCRUMB_1 = {
			"//nav[@id='breadcrumbs']/ul/li[1]/span/a", ""
		};
	private static String[] _PORTLET_LINK_BREADCRUMB_2 = {
			"//nav[@id='breadcrumbs']/ul/li[2]/span/a", ""
		};
	private static String[] _PORTLET_LINK_BREADCRUMB_3 = {
			"//nav[@id='breadcrumbs']/ul/li[3]/span/a", ""
		};
	private static String[] _PORTLET_LINK_DELETE = {
			"//input[@value='Move to the Recycle Bin']",
			"Move to the Recycle Bin"
		};
	private static String[] _PORTLET_LINK_OPTIONS = {
			"//span[@title='Options']/ul/li/strong/a", "Options"
		};
	private static String[] _PORTLET_LINK_OPTIONS_EXPORT_IMPORT = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Export / Import')]",
			"Export / Import"
		};
	private static String[] _PORTLET_LINK_SEARCH = {
			"//input[@value='Search']", "Search"
		};
	private static String[] _PORTLET_LINK_VIEW_ALL = {
			"//span[contains(@class,'view-button')]/a", "View All"
		};
	private static String[] _PORTLET_TEXT_DESCRIPTION = {
			"cpContextPanelTemplate", ""
		};
	private static String[] _PORTLET_TEXT_RESULTS = {
			"//div[@class='search-results']", ""
		};
	private static String[] _PORTLET_TEXT_INFO = {
			"//div[@class='portlet-msg-info']", "No entries were found."
		};
	private static String[] _PORTLET_TEXT_SUCCESS = {
			"//div[@class='portlet-msg-success']",
			"Your request completed successfully."
		};
	private static String[] _PORTLET_TEXT_SUCCESS_UNDO = {
			"//form[@id='_161_undoForm']",
			"The selected item was moved to the Recycle Bin. Undo"
		};
	private static String[] _PORTLET_TEXT_TITLE = {
			"//span[@class='portlet-title-text']", ""
		};
	private static Map<String, String[]> _paths = new HashMap<String, String[]>();

	static {
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
		_paths.put("BLOGS_ENTRY_LINK_AUTHOR", _BLOGS_ENTRY_LINK_AUTHOR);
		_paths.put("BLOGS_ENTRY_LINK_AUTHOR_1", _BLOGS_ENTRY_LINK_AUTHOR_1);
		_paths.put("BLOGS_ENTRY_LINK_AUTHOR_2", _BLOGS_ENTRY_LINK_AUTHOR_2);
		_paths.put("BLOGS_ENTRY_LINK_AUTHOR_3", _BLOGS_ENTRY_LINK_AUTHOR_3);
		_paths.put("BLOGS_ENTRY_LINK_CHECKBOX", _BLOGS_ENTRY_LINK_CHECKBOX);
		_paths.put("BLOGS_ENTRY_LINK_CHECKBOX_1", _BLOGS_ENTRY_LINK_CHECKBOX_1);
		_paths.put("BLOGS_ENTRY_LINK_CHECKBOX_2", _BLOGS_ENTRY_LINK_CHECKBOX_2);
		_paths.put("BLOGS_ENTRY_LINK_CHECKBOX_3", _BLOGS_ENTRY_LINK_CHECKBOX_3);
		_paths.put("BLOGS_ENTRY_LINK_CHECKBOX_ALL",
			_BLOGS_ENTRY_LINK_CHECKBOX_ALL);
		_paths.put("BLOGS_ENTRY_LINK_CONFIRM_DELETE_ACTIONS",
			_BLOGS_ENTRY_LINK_CONFIRM_DELETE_ACTIONS);
		_paths.put("BLOGS_ENTRY_LINK_CONFIRM_DELETE_LIST",
			_BLOGS_ENTRY_LINK_CONFIRM_DELETE_LIST);
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
		_paths.put("BLOGS_ENTRY_LINK_TITLE", _BLOGS_ENTRY_LINK_TITLE);
		_paths.put("BLOGS_ENTRY_LINK_TITLE_1", _BLOGS_ENTRY_LINK_TITLE_1);
		_paths.put("BLOGS_ENTRY_LINK_TITLE_2", _BLOGS_ENTRY_LINK_TITLE_2);
		_paths.put("BLOGS_ENTRY_LINK_TITLE_3", _BLOGS_ENTRY_LINK_TITLE_3);
		_paths.put("PORTLET_FIELD_SEARCH", _PORTLET_FIELD_SEARCH);
		_paths.put("PORTLET_LINK_ADD", _PORTLET_LINK_ADD);
		_paths.put("PORTLET_LINK_BREADCRUMB_1", _PORTLET_LINK_BREADCRUMB_1);
		_paths.put("PORTLET_LINK_BREADCRUMB_2", _PORTLET_LINK_BREADCRUMB_2);
		_paths.put("PORTLET_LINK_BREADCRUMB_3", _PORTLET_LINK_BREADCRUMB_3);
		_paths.put("PORTLET_LINK_DELETE", _PORTLET_LINK_DELETE);
		_paths.put("PORTLET_LINK_OPTIONS", _PORTLET_LINK_OPTIONS);
		_paths.put("PORTLET_LINK_OPTIONS_EXPORT_IMPORT",
			_PORTLET_LINK_OPTIONS_EXPORT_IMPORT);
		_paths.put("PORTLET_LINK_SEARCH", _PORTLET_LINK_SEARCH);
		_paths.put("PORTLET_LINK_VIEW_ALL", _PORTLET_LINK_VIEW_ALL);
		_paths.put("PORTLET_TEXT_DESCRIPTION", _PORTLET_TEXT_DESCRIPTION);
		_paths.put("PORTLET_TEXT_RESULTS", _PORTLET_TEXT_RESULTS);
		_paths.put("PORTLET_TEXT_INFO", _PORTLET_TEXT_INFO);
		_paths.put("PORTLET_TEXT_SUCCESS", _PORTLET_TEXT_SUCCESS);
		_paths.put("PORTLET_TEXT_SUCCESS_UNDO", _PORTLET_TEXT_SUCCESS_UNDO);
		_paths.put("PORTLET_TEXT_TITLE", _PORTLET_TEXT_TITLE);
	}
}