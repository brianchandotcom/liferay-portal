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

package com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.paths.documenttypes;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPDocumentsAndMediaDocumentTypeHomePaths {
	public static Map<String, String[]> getPaths() {
		return _paths;
	}

	private static String[] _PAGE_NAME = {
			"//iframe[@id='_20_openFileEntryTypeView']",
			"Control Panel Documents and Media Document Types Home Page"
		};
	private static String[] _TOP = { "relative=top", "" };
	private static String[] _HEADER_SUCCESS_MESSAGE = {
			"//div[@class='portlet-msg-success']",
			"Document Type Success Message"
		};
	private static String[] _BUTTON_VIEW_ALL = {
			"//span[contains(@class,'view-button')]/a",
			"Document Types 'View All' Button"
		};
	private static String[] _BUTTON_ADD = {
			"//span[contains(@class,'add-button')]/a",
			"Document Types 'View All' Button"
		};
	private static String[] _SEARCH_FIELD = {
			"//input[@id='_20_keywords']", "Document Type Search Field"
		};
	private static String[] _SEARCH_BUTTON = {
			"//input[@value='Search']", "Document Type Search Button"
		};
	private static String[] _TABLE_NAME_1 = {
			"//tr[3]/td[1]", "1st Document Type 'Name' Entry"
		};
	private static String[] _TABLE_MODIFIED_DATE_1 = {
			"//tr[3]/td[2]", "1st Document Type 'Modified Date' Entry"
		};
	private static String[] _TABLE_ACTIONS_1 = {
			"//tr[3]/td[3]/span[@title='Actions']/ul/li/strong/a/span",
			"1st Document Type 'Actions' Entry"
		};
	private static String[] _TABLE_NAME_2 = {
			"//tr[4]/td[1]", "2nd Document Type 'Name' Entry"
		};
	private static String[] _TABLE_MODIFIED_DATE_2 = {
			"//tr[4]/td[2]", "2nd Document Type 'Modified Date' Entry"
		};
	private static String[] _TABLE_ACTIONS_2 = {
			"//tr[4]/td[3]/span[@title='Actions']/ul/li/strong/a/span",
			"2nd Document Type 'Actions' Entry"
		};
	private static String[] _TABLE_NAME_3 = {
			"//tr[5]/td[1]", "2nd Document Type 'Name' Entry"
		};
	private static String[] _TABLE_MODIFIED_DATE_3 = {
			"//tr[5]/td[2]", "3rd Document Type 'Modified Date' Entry"
		};
	private static String[] _TABLE_ACTIONS_3 = {
			"//tr[5]/td[3]/span[@title='Actions']/ul/li/strong/a/span",
			"3rd Document Type 'Actions' Entry"
		};
	private static String[] _ACTIONS_EDIT = {
			"//a[contains(.,'Edit') and @role='menuitem']",
			"Document Type Actions 'Edit' Link"
		};
	private static String[] _ACTIONS_PERMISSIONS = {
			"//a[contains(.,'Permissions') and @role='menuitem']",
			"Document Type Actions 'Permissions' Link"
		};
	private static String[] _ACTIONS_DELETE = {
			"//a[contains(.,'Delete') and @role='menuitem']",
			"Document Type Actions 'Delete' Link"
		};
	private static String[] _ACTIONS_DELETE_CONFIRMATION = {
			"", "Delete Confirmation Popup"
		};
	private static Map<String, String[]> _paths = new HashMap<String, String[]>();

	static {
		_paths.put("PAGE_NAME", _PAGE_NAME);
		_paths.put("TOP", _TOP);
		_paths.put("HEADER_SUCCESS_MESSAGE", _HEADER_SUCCESS_MESSAGE);
		_paths.put("BUTTON_VIEW_ALL", _BUTTON_VIEW_ALL);
		_paths.put("BUTTON_ADD", _BUTTON_ADD);
		_paths.put("SEARCH_FIELD", _SEARCH_FIELD);
		_paths.put("SEARCH_BUTTON", _SEARCH_BUTTON);
		_paths.put("TABLE_NAME_1", _TABLE_NAME_1);
		_paths.put("TABLE_MODIFIED_DATE_1", _TABLE_MODIFIED_DATE_1);
		_paths.put("TABLE_ACTIONS_1", _TABLE_ACTIONS_1);
		_paths.put("TABLE_NAME_2", _TABLE_NAME_2);
		_paths.put("TABLE_MODIFIED_DATE_2", _TABLE_MODIFIED_DATE_2);
		_paths.put("TABLE_ACTIONS_2", _TABLE_ACTIONS_2);
		_paths.put("TABLE_NAME_3", _TABLE_NAME_3);
		_paths.put("TABLE_MODIFIED_DATE_3", _TABLE_MODIFIED_DATE_3);
		_paths.put("TABLE_ACTIONS_3", _TABLE_ACTIONS_3);
		_paths.put("ACTIONS_EDIT", _ACTIONS_EDIT);
		_paths.put("ACTIONS_PERMISSIONS", _ACTIONS_PERMISSIONS);
		_paths.put("ACTIONS_DELETE", _ACTIONS_DELETE);
		_paths.put("ACTIONS_DELETE_CONFIRMATION", _ACTIONS_DELETE_CONFIRMATION);
	}
}