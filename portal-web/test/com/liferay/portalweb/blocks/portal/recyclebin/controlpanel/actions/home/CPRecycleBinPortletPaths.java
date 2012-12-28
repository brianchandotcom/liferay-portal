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

package com.liferay.portalweb.blocks.portal.recyclebin.controlpanel.actions.home;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPRecycleBinPortletPaths {
	public static Map<String, String[]> getPaths() {
		return _paths;
	}

	private static String[] _PAGE_NAME = {
			"", "Control Panel Recycle Bin Home Page"
		};
	private static String[] _BREADCRUMB_1 = {
			"//nav[@id='breadcrumbs']/ul/li[1]/span/a", "First Breadcrumb"
		};
	private static String[] _BREADCRUMB_2 = {
			"//nav[@id='breadcrumbs']/ul/li[2]/span/a", "Second Breadcrumb"
		};
	private static String[] _BREADCRUMB_3 = {
			"//nav[@id='breadcrumbs']/ul/li[3]/span/a", "Third Breadcrumb"
		};
	private static String[] _HEADER_TITLE = {
			"//h1[@id='cpPortletTitle']/span", "Portlet Title"
		};
	private static String[] _HEADER_DESCRIPTION = {
			"//div[@id='cpContextPanelTemplate']", "Portlet Description"
		};
	private static String[] _HEADER_INFO_EMPTY = {
			"//form[@id='_182_emptyForm']",
			"Entries that have been in Recycle Bin for more than 30 days will be automatically deleted."
		};
	private static String[] _PORTLET_SUCCESS = {
			"//div[@class='portlet-msg-success']", "Portlet Success Message"
		};
	private static String[] _EMPTY_RECYCLE_BIN_LINK = {
			"//a[@id='_182_empty']", "Empty Recycle Bin Link"
		};
	private static String[] _EMPTY_RECYCLE_BIN_CONFIRM = {
			"", "Empty Recycle Bin Confirmation"
		};
	private static String[] _SEARCH_FIELD = {
			"//input[@id='_182_keywords']", "Search Field"
		};
	private static String[] _SEARCH_BUTTON = {
			"//input[@value='Search']", "Search Button"
		};
	private static String[] _TABLE_EMPTY_MESSAGE = {
			"//div[@class='portlet-msg-info']", "Portlet Info Message"
		};
	private static String[] _TABLE_HEADER_NAME = { "//th[1]", "Name" };
	private static String[] _TABLE_HEADER_TYPE = { "//th[2]/span/a", "Type" };
	private static String[] _TABLE_HEADER_REMOVED_DATE = {
			"//th[3]/span/a", "Removed Date"
		};
	private static String[] _TABLE_HEADER_REMOVED_BY = {
			"//th[4]/span/a", "Removed By"
		};
	private static String[] _TABLE_NAME_1 = { "//tr[3]/td[1]/span/a/span", "" };
	private static String[] _TABLE_NAME_2 = { "//tr[4]/td[1]/span/a/span", "" };
	private static String[] _TABLE_NAME_3 = { "//tr[5]/td[1]/span/a/span", "" };
	private static String[] _TABLE_TYPE_1 = { "//tr[3]/td[2]", "" };
	private static String[] _TABLE_TYPE_2 = { "//tr[4]/td[2]", "" };
	private static String[] _TABLE_TYPE_3 = { "//tr[5]/td[2]", "" };
	private static String[] _TABLE_REMOVED_DATE_1 = { "//tr[3]/td[3]/span", "" };
	private static String[] _TABLE_REMOVED_DATE_2 = { "//tr[4]/td[3]/span", "" };
	private static String[] _TABLE_REMOVED_DATE_3 = { "//tr[5]/td[3]/span", "" };
	private static String[] _TABLE_REMOVED_BY_1 = { "//tr[3]/td[4]", "" };
	private static String[] _TABLE_REMOVED_BY_2 = { "//tr[4]/td[4]", "" };
	private static String[] _TABLE_REMOVED_BY_3 = { "//tr[5]/td[4]", "" };
	private static String[] _TABLE_ACTIONS_1 = {
			"//tr[3]/td[5]/span[@title='Actions']/ul/li/strong/a/span",
			"Actions"
		};
	private static String[] _TABLE_ACTIONS_2 = {
			"//tr[4]/td[5]/span[@title='Actions']/ul/li/strong/a/span",
			"Actions"
		};
	private static String[] _TABLE_ACTIONS_3 = {
			"//tr[5]/td[5]/span[@title='Actions']/ul/li/strong/a/span",
			"Actions"
		};
	private static String[] _ACTIONS_RESTORE = {
			"//a[@role='menuitem' and contains(.,'Restore')]", "Restore"
		};
	private static String[] _ACTIONS_DELETE = {
			"//a[@role='menuitem' and contains(.,'Delete')]", "Delete"
		};
	private static Map<String, String[]> _paths = new HashMap<String, String[]>();

	static {
		_paths.put("PAGE_NAME", _PAGE_NAME);
		_paths.put("BREADCRUMB_1", _BREADCRUMB_1);
		_paths.put("BREADCRUMB_2", _BREADCRUMB_2);
		_paths.put("BREADCRUMB_3", _BREADCRUMB_3);
		_paths.put("HEADER_TITLE", _HEADER_TITLE);
		_paths.put("HEADER_DESCRIPTION", _HEADER_DESCRIPTION);
		_paths.put("HEADER_INFO_EMPTY", _HEADER_INFO_EMPTY);
		_paths.put("PORTLET_SUCCESS", _PORTLET_SUCCESS);
		_paths.put("EMPTY_RECYCLE_BIN_LINK", _EMPTY_RECYCLE_BIN_LINK);
		_paths.put("EMPTY_RECYCLE_BIN_CONFIRM", _EMPTY_RECYCLE_BIN_CONFIRM);
		_paths.put("SEARCH_FIELD", _SEARCH_FIELD);
		_paths.put("SEARCH_BUTTON", _SEARCH_BUTTON);
		_paths.put("TABLE_EMPTY_MESSAGE", _TABLE_EMPTY_MESSAGE);
		_paths.put("TABLE_HEADER_NAME", _TABLE_HEADER_NAME);
		_paths.put("TABLE_HEADER_TYPE", _TABLE_HEADER_TYPE);
		_paths.put("TABLE_HEADER_REMOVED_DATE", _TABLE_HEADER_REMOVED_DATE);
		_paths.put("TABLE_HEADER_REMOVED_BY", _TABLE_HEADER_REMOVED_BY);
		_paths.put("TABLE_NAME_1", _TABLE_NAME_1);
		_paths.put("TABLE_NAME_2", _TABLE_NAME_2);
		_paths.put("TABLE_NAME_3", _TABLE_NAME_3);
		_paths.put("TABLE_TYPE_1", _TABLE_TYPE_1);
		_paths.put("TABLE_TYPE_2", _TABLE_TYPE_2);
		_paths.put("TABLE_TYPE_3", _TABLE_TYPE_3);
		_paths.put("TABLE_REMOVED_DATE_1", _TABLE_REMOVED_DATE_1);
		_paths.put("TABLE_REMOVED_DATE_2", _TABLE_REMOVED_DATE_2);
		_paths.put("TABLE_REMOVED_DATE_3", _TABLE_REMOVED_DATE_3);
		_paths.put("TABLE_REMOVED_BY_1", _TABLE_REMOVED_BY_1);
		_paths.put("TABLE_REMOVED_BY_2", _TABLE_REMOVED_BY_2);
		_paths.put("TABLE_REMOVED_BY_3", _TABLE_REMOVED_BY_3);
		_paths.put("TABLE_ACTIONS_1", _TABLE_ACTIONS_1);
		_paths.put("TABLE_ACTIONS_2", _TABLE_ACTIONS_2);
		_paths.put("TABLE_ACTIONS_3", _TABLE_ACTIONS_3);
		_paths.put("ACTIONS_RESTORE", _ACTIONS_RESTORE);
		_paths.put("ACTIONS_DELETE", _ACTIONS_DELETE);
	}
}