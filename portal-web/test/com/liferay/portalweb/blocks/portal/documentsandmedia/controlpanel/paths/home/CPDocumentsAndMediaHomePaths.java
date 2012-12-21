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

package com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.paths.home;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPDocumentsAndMediaHomePaths {
	public static Map<String, String[]> getPaths() {
		return _paths;
	}

	private static String[] _PAGE_NAME = {
			"", "Control Panel Documents and Media Home Page"
		};
	private static String[] _BREADCRUMB_1 = {
			"//div[@id='breadcrumbs']/ul/li[1]/span/a", "1st Breadcrumb"
		};
	private static String[] _BREADCRUMB_2 = {
			"//div[@id='breadcrumbs']/ul/li[2]/span/a", "2nd Breadcrumb"
		};
	private static String[] _BREADCRUMB_3 = {
			"//div[@id='breadcrumbs']/ul/li[3]/span/a", "3rd Breadcrumb"
		};
	private static String[] _HEADER_PORTLET_TITLE = {
			"//span[@class='portlet-title-text']", "Portlet Title Header"
		};
	private static String[] _HEADER_PORTLET_INFO = {
			"//div[@id='cpContextPanelTemplate']", "Portlet Information Header"
		};
	private static String[] _HEADER_OPTIONS_ICON = {
			"//span[@title='Options']/ul/li/strong/a", "Options Icon"
		};
	private static String[] _HEADER_SUCCESS_MESSAGE = {
			"//div[@class='portlet-msg-success']", "Success Message"
		};
	private static String[] _HEADER_SUCCESS_MESSAGE_UNDO = {
			"//form[@id='_20_undoForm']",
			"Success Message for 'Move to the Recycle Bin'"
		};
	private static String[] _OPTIONS_EXPORT_IMPORT = {
			"//a[contains(.,'Export / Import') and @role='menuitem']",
			"Options 'Export / Import' Link"
		};
	private static String[] _TOOLBAR_ALL_ROWS = {
			"//input[@id='_20_allRowIdsCheckbox']", "All Entries Checkbox"
		};
	private static String[] _TOOLBAR_ACTIONS_BUTTON = {
			"//span[@title='Actions']/ul/li/strong/a/span",
			"Actions Drop Down Button"
		};
	private static String[] _TOOLBAR_ADD_BUTTON = {
			"//span[@title='Add']/ul/li/strong/a/span", "Add Drop Down Button"
		};
	private static String[] _TOOLBAR_SORT_BY_BUTTON = {
			"//span[@title='Sort By']/ul/li/strong/a/span",
			"Sort By Drop Down Button"
		};
	private static String[] _TOOLBAR_MANAGE_BUTTON = {
			"//span[@title='Manage']/ul/li/strong/a/span",
			"Manage Drop Down Button"
		};
	private static String[] _ALL_ACTIONS_CANCEL_CHECKOUT = {
			"//a[contains(.,'Cancel Checkout') and @role='menuitem']",
			"Actions Cancel Checkout Link"
		};
	private static String[] _ALL_ACTIONS_CHECKIN = {
			"//a[contains(.,'Checkin') and @role='menuitem']",
			"Actions Checkin Link"
		};
	private static String[] _ALL_ACTIONS_CHECKOUT = {
			"//a[contains(.,'Checkout') and @role='menuitem']",
			"Actions Checkout Link"
		};
	private static String[] _ALL_ACTIONS_MOVE = {
			"//a[contains(.,'Move') and @role='menuitem']", "Actions Move Link"
		};
	private static String[] _ALL_ACTIONS_MOVE_TO_THE_RECYCLE_BIN = {
			"//a[contains(.,'Move to the Recycle Bin') and @role='menuitem']",
			"Actions Move to the Recycle Bin Link"
		};
	private static String[] _ADD_FOLDER = {
			"//a[contains(.,'Folder') and @role='menuitem']", "Add Folder Link"
		};
	private static String[] _ADD_SUBFOLDER = {
			"//a[contains(.,'Subfolder') and @role='menuitem']",
			"Add Subfolder Link"
		};
	private static String[] _ADD_SHORTCUT = {
			"//a[contains(.,'Shortcut') and @role='menuitem']",
			"Add Shortcut Link"
		};
	private static String[] _ADD_REPOSITORY = {
			"//a[contains(.,'Repository') and @role='menuitem']",
			"Add Repository Link"
		};
	private static String[] _ADD_MULTIPLE_DOCUMENTS = {
			"//a[contains(.,'Multiple Documents') and @role='menuitem']",
			"Add Multiple Documents Link"
		};
	private static String[] _ADD_BASIC_DOCUMENT = {
			"//a[contains(.,'Basic Document') and @role='menuitem']",
			"Add Basic Document Link"
		};
	private static String[] _MANAGE_DOCUMENT_TYPES = {
			"//a[contains(.,'Document Types') and @role='menuitem']",
			"Manage Document Types Link"
		};
	private static String[] _MANAGE_METADATA_SET = {
			"//a[contains(.,'Metadata Set') and @role='menuitem']",
			"Manage Metadata Set Link"
		};
	private static String[] _FOLDER_NAME = {
			"//a[@data-folder='true']/span[@class='entry-title']",
			"Folder Name's Link"
		};
	private static String[] _FOLDER_NAME_1 = {
			"xpath=(//a[@data-folder='true']/span[@class='entry-title'])[1]",
			"1st Folder Name's Link"
		};
	private static String[] _FOLDER_NAME_2 = {
			"xpath=(//a[@data-folder='true']/span[@class='entry-title'])[2]",
			"2nd Folder Name's Link"
		};
	private static String[] _FOLDER_NAME_3 = {
			"xpath=(//a[@data-folder='true']/span[@class='entry-title'])[3]",
			"3rd Folder Name's Link"
		};
	private static String[] _FOLDER_ACTIONS = {
			"//a[@data-folder='true']/parent::div/span[contains(@class,'entry-action')]//a",
			"Folder Actions Button"
		};
	private static String[] _FOLDER_ACTIONS_1 = {
			"xpath=(//a[@data-folder='true']/parent::div/span[contains(@class,'entry-action')]//a)[1]",
			"1st Folder Actions Button"
		};
	private static String[] _FOLDER_ACTIONS_2 = {
			"xpath=(//a[@data-folder='true']/parent::div/span[contains(@class,'entry-action')]//a)[2]",
			"2nd Folder Actions Button"
		};
	private static String[] _FOLDER_ACTIONS_3 = {
			"xpath=(//a[@data-folder='true']/parent::div/span[contains(@class,'entry-action')]//a)[3]",
			"3rd Folder Actions Button"
		};
	private static String[] _FOLDER_ACTIONS_EDIT = {
			"//a[contains(.,'Edit') and @role='menuitem']",
			"Folder Actions Edit Link"
		};
	private static String[] _FOLDER_ACTIONS_MOVE = {
			"//a[contains(.,'Move') and @role='menuitem']",
			"Folder Actions Move Link"
		};
	private static String[] _FOLDER_ACTIONS_PERMISSIONS = {
			"//a[contains(.,'Permissions') and @role='menuitem']",
			"Folder Actions Permissions Link"
		};
	private static String[] _FOLDER_ACTIONS_MOVE_TO_THE_RECYCLE_BIN = {
			"//a[contains(.,'Move to the Recycle Bin') and @role='menuitem']",
			"Folder Actions Move to the Recycle Bin Link"
		};
	private static String[] _FOLDER_ACTIONS_ADD_SUBFOLDER = {
			"//a[contains(.,'Add Subfolder') and @role='menuitem']",
			"Folder Actions Add Subfolder Link"
		};
	private static String[] _FOLDER_ACTIONS_ACCESS_FROM_DESKTOP = {
			"//a[contains(.,'Access from Desktop') and @role='menuitem']",
			"Folder Actions Access from Desktop Link"
		};
	private static String[] _DOCUMENT_ACTIONS = {
			"//a[@data-folder='false']/parent::div/span[contains(@class,'entry-action')]//a",
			"Document Actions Button"
		};
	private static String[] _DOCUMENT_ACTIONS_1 = {
			"xpath=(//a[@data-folder='false']/parent::div/span[contains(@class,'entry-action')]//a)[1]",
			"1st Document Actions Button"
		};
	private static String[] _DOCUMENT_ACTIONS_2 = {
			"xpath=(//a[@data-folder='false']/parent::div/span[contains(@class,'entry-action')]//a)[2]",
			"2nd Document Actions Button"
		};
	private static String[] _DOCUMENT_ACTIONS_3 = {
			"xpath=(//a[@data-folder='false']/parent::div/span[contains(@class,'entry-action')]//a)[3]",
			"3rd Document Actions Button"
		};
	private static String[] _DOCUMENT_IMAGE_LOCK = {
			"//a[@data-folder='false']/div/img[@alt='Locked']",
			"Document Lock Image"
		};
	private static String[] _DOCUMENT_NAME = {
			"//a[@data-folder='false']/span[@class='entry-title']",
			"Document Name's Link"
		};
	private static String[] _DOCUMENT_NAME_1 = {
			"xpath=(//a[@data-folder='false']/span[@class='entry-title'])[1]",
			"1st Document Name's Link"
		};
	private static String[] _DOCUMENT_NAME_2 = {
			"xpath=(//a[@data-folder='false']/span[@class='entry-title'])[2]",
			"2nd Document Name's Link"
		};
	private static String[] _DOCUMENT_NAME_3 = {
			"xpath=(//a[@data-folder='false']/span[@class='entry-title'])[3]",
			"3rd Document Name's Link"
		};
	private static String[] _DOCUMENT_ACTIONS_DOWNLOAD = {
			"//a[contains(.,'Download') and @role='menuitem']",
			"Document Actions Download Link"
		};
	private static String[] _DOCUMENT_ACTIONS_EDIT = {
			"//a[contains(.,'Edit') and @role='menuitem']",
			"Document Actions Edit Link"
		};
	private static String[] _DOCUMENT_ACTIONS_MOVE = {
			"//a[contains(.,'Move') and @role='menuitem']",
			"Document Actions Move Link"
		};
	private static String[] _DOCUMENT_ACTIONS_CANCEL_CHECKOUT = {
			"//a[contains(.,'Cancel Checkout') and @role='menuitem']",
			"Document Actions Cancel Checkout Link"
		};
	private static String[] _DOCUMENT_ACTIONS_CHECKOUT = {
			"//a[contains(.,'Checkout') and @role='menuitem']",
			"Document Actions Checkout Link"
		};
	private static String[] _DOCUMENT_ACTIONS_CHECKIN = {
			"//a[contains(.,'Checkin') and @role='menuitem']",
			"Document Actions Checkin Link"
		};
	private static String[] _DOCUMENT_ACTIONS_PERMISSIONS = {
			"//a[contains(.,'Permissions') and @role='menuitem']",
			"Document Actions Permissions Link"
		};
	private static String[] _DOCUMENT_ACTIONS_MOVE_TO_THE_RECYCLE_BIN = {
			"//a[contains(.,'Move to the Recycle Bin') and @role='menuitem']",
			"Document Actions Move to the Recycle Bin Link"
		};
	private static Map<String, String[]> _paths = new HashMap<String, String[]>();

	static {
		_paths.put("PAGE_NAME", _PAGE_NAME);
		_paths.put("BREADCRUMB_1", _BREADCRUMB_1);
		_paths.put("BREADCRUMB_2", _BREADCRUMB_2);
		_paths.put("BREADCRUMB_3", _BREADCRUMB_3);
		_paths.put("HEADER_PORTLET_TITLE", _HEADER_PORTLET_TITLE);
		_paths.put("HEADER_PORTLET_INFO", _HEADER_PORTLET_INFO);
		_paths.put("HEADER_OPTIONS_ICON", _HEADER_OPTIONS_ICON);
		_paths.put("HEADER_SUCCESS_MESSAGE", _HEADER_SUCCESS_MESSAGE);
		_paths.put("HEADER_SUCCESS_MESSAGE_UNDO", _HEADER_SUCCESS_MESSAGE_UNDO);
		_paths.put("OPTIONS_EXPORT_IMPORT", _OPTIONS_EXPORT_IMPORT);
		_paths.put("TOOLBAR_ALL_ROWS", _TOOLBAR_ALL_ROWS);
		_paths.put("TOOLBAR_ACTIONS_BUTTON", _TOOLBAR_ACTIONS_BUTTON);
		_paths.put("TOOLBAR_ADD_BUTTON", _TOOLBAR_ADD_BUTTON);
		_paths.put("TOOLBAR_SORT_BY_BUTTON", _TOOLBAR_SORT_BY_BUTTON);
		_paths.put("TOOLBAR_MANAGE_BUTTON", _TOOLBAR_MANAGE_BUTTON);
		_paths.put("ALL_ACTIONS_CANCEL_CHECKOUT", _ALL_ACTIONS_CANCEL_CHECKOUT);
		_paths.put("ALL_ACTIONS_CHECKIN", _ALL_ACTIONS_CHECKIN);
		_paths.put("ALL_ACTIONS_CHECKOUT", _ALL_ACTIONS_CHECKOUT);
		_paths.put("ALL_ACTIONS_MOVE", _ALL_ACTIONS_MOVE);
		_paths.put("ALL_ACTIONS_MOVE_TO_THE_RECYCLE_BIN",
			_ALL_ACTIONS_MOVE_TO_THE_RECYCLE_BIN);
		_paths.put("ADD_FOLDER", _ADD_FOLDER);
		_paths.put("ADD_SUBFOLDER", _ADD_SUBFOLDER);
		_paths.put("ADD_SHORTCUT", _ADD_SHORTCUT);
		_paths.put("ADD_REPOSITORY", _ADD_REPOSITORY);
		_paths.put("ADD_MULTIPLE_DOCUMENTS", _ADD_MULTIPLE_DOCUMENTS);
		_paths.put("ADD_BASIC_DOCUMENT", _ADD_BASIC_DOCUMENT);
		_paths.put("MANAGE_DOCUMENT_TYPES", _MANAGE_DOCUMENT_TYPES);
		_paths.put("MANAGE_METADATA_SET", _MANAGE_METADATA_SET);
		_paths.put("FOLDER_NAME", _FOLDER_NAME);
		_paths.put("FOLDER_NAME_1", _FOLDER_NAME_1);
		_paths.put("FOLDER_NAME_2", _FOLDER_NAME_2);
		_paths.put("FOLDER_NAME_3", _FOLDER_NAME_3);
		_paths.put("FOLDER_ACTIONS", _FOLDER_ACTIONS);
		_paths.put("FOLDER_ACTIONS_1", _FOLDER_ACTIONS_1);
		_paths.put("FOLDER_ACTIONS_2", _FOLDER_ACTIONS_2);
		_paths.put("FOLDER_ACTIONS_3", _FOLDER_ACTIONS_3);
		_paths.put("FOLDER_ACTIONS_EDIT", _FOLDER_ACTIONS_EDIT);
		_paths.put("FOLDER_ACTIONS_MOVE", _FOLDER_ACTIONS_MOVE);
		_paths.put("FOLDER_ACTIONS_PERMISSIONS", _FOLDER_ACTIONS_PERMISSIONS);
		_paths.put("FOLDER_ACTIONS_MOVE_TO_THE_RECYCLE_BIN",
			_FOLDER_ACTIONS_MOVE_TO_THE_RECYCLE_BIN);
		_paths.put("FOLDER_ACTIONS_ADD_SUBFOLDER", _FOLDER_ACTIONS_ADD_SUBFOLDER);
		_paths.put("FOLDER_ACTIONS_ACCESS_FROM_DESKTOP",
			_FOLDER_ACTIONS_ACCESS_FROM_DESKTOP);
		_paths.put("DOCUMENT_ACTIONS", _DOCUMENT_ACTIONS);
		_paths.put("DOCUMENT_ACTIONS_1", _DOCUMENT_ACTIONS_1);
		_paths.put("DOCUMENT_ACTIONS_2", _DOCUMENT_ACTIONS_2);
		_paths.put("DOCUMENT_ACTIONS_3", _DOCUMENT_ACTIONS_3);
		_paths.put("DOCUMENT_IMAGE_LOCK", _DOCUMENT_IMAGE_LOCK);
		_paths.put("DOCUMENT_NAME", _DOCUMENT_NAME);
		_paths.put("DOCUMENT_NAME_1", _DOCUMENT_NAME_1);
		_paths.put("DOCUMENT_NAME_2", _DOCUMENT_NAME_2);
		_paths.put("DOCUMENT_NAME_3", _DOCUMENT_NAME_3);
		_paths.put("DOCUMENT_ACTIONS_DOWNLOAD", _DOCUMENT_ACTIONS_DOWNLOAD);
		_paths.put("DOCUMENT_ACTIONS_EDIT", _DOCUMENT_ACTIONS_EDIT);
		_paths.put("DOCUMENT_ACTIONS_MOVE", _DOCUMENT_ACTIONS_MOVE);
		_paths.put("DOCUMENT_ACTIONS_CANCEL_CHECKOUT",
			_DOCUMENT_ACTIONS_CANCEL_CHECKOUT);
		_paths.put("DOCUMENT_ACTIONS_CHECKOUT", _DOCUMENT_ACTIONS_CHECKOUT);
		_paths.put("DOCUMENT_ACTIONS_CHECKIN", _DOCUMENT_ACTIONS_CHECKIN);
		_paths.put("DOCUMENT_ACTIONS_PERMISSIONS", _DOCUMENT_ACTIONS_PERMISSIONS);
		_paths.put("DOCUMENT_ACTIONS_MOVE_TO_THE_RECYCLE_BIN",
			_DOCUMENT_ACTIONS_MOVE_TO_THE_RECYCLE_BIN);
	}
}