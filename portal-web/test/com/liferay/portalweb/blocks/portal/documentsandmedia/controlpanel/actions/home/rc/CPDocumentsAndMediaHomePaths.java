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

package com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.actions.home.rc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPDocumentsAndMediaHomePaths {
	public static Map<String, String> getPaths() {
		return _paths;
	}

	private static Map<String, String> _paths = new HashMap<String, String>();

	static {
		_paths.put("TOP", "relative=top");
		_paths.put("PAGE_NAME", "");
		_paths.put("BREADCRUMB_1", "//div[@id='breadcrumbs']/ul/li[1]/span/a");
		_paths.put("BREADCRUMB_2", "//div[@id='breadcrumbs']/ul/li[2]/span/a");
		_paths.put("BREADCRUMB_3", "//div[@id='breadcrumbs']/ul/li[3]/span/a");
		_paths.put("HEADER_PORTLET_TITLE", "//span[@class='portlet-title-text']");
		_paths.put("HEADER_PORTLET_INFO", "//div[@id='cpContextPanelTemplate']");
		_paths.put("HEADER_OPTIONS_ICON",
			"//span[@title='Options']/ul/li/strong/a");
		_paths.put("HEADER_SUCCESS_MESSAGE",
			"//div[@class='portlet-msg-success']");
		_paths.put("HEADER_SUCCESS_MESSAGE_UNDO", "//form[@id='_20_undoForm']");
		_paths.put("OPTIONS_EXPORT_IMPORT",
			"//a[contains(.,'Export / Import') and @role='menuitem']");
		_paths.put("TOOLBAR_ALL_ROWS", "//input[@id='_20_allRowIdsCheckbox']");
		_paths.put("TOOLBAR_ACTIONS_BUTTON",
			"//span[@title='Actions']/ul/li/strong/a/span");
		_paths.put("TOOLBAR_ADD_BUTTON",
			"//span[@title='Add']/ul/li/strong/a/span");
		_paths.put("TOOLBAR_SORT_BY_BUTTON",
			"//span[@title='Sort By']/ul/li/strong/a/span");
		_paths.put("TOOLBAR_MANAGE_BUTTON",
			"//span[@title='Manage']/ul/li/strong/a/span");
		_paths.put("ALL_ACTIONS_CANCEL_CHECKOUT",
			"//a[contains(.,'Cancel Checkout') and @role='menuitem']");
		_paths.put("ALL_ACTIONS_CHECKIN",
			"//a[contains(.,'Checkin') and @role='menuitem']");
		_paths.put("ALL_ACTIONS_CHECKOUT",
			"//a[contains(.,'Checkout') and @role='menuitem']");
		_paths.put("ALL_ACTIONS_MOVE",
			"//a[contains(.,'Move') and @role='menuitem']");
		_paths.put("ALL_ACTIONS_MOVE_TO_THE_RECYCLE_BIN",
			"//a[contains(.,'Move to the Recycle Bin') and @role='menuitem']");
		_paths.put("ADD_FOLDER",
			"//a[contains(.,'Folder') and @role='menuitem']");
		_paths.put("ADD_SUBFOLDER",
			"//a[contains(.,'Subfolder') and @role='menuitem']");
		_paths.put("ADD_SHORTCUT",
			"//a[contains(.,'Shortcut') and @role='menuitem']");
		_paths.put("ADD_REPOSITORY",
			"//a[contains(.,'Repository') and @role='menuitem']");
		_paths.put("ADD_MULTIPLE_DOCUMENTS",
			"//a[contains(.,'Multiple Documents') and @role='menuitem']");
		_paths.put("ADD_BASIC_DOCUMENT",
			"//a[contains(.,'Basic Document') and @role='menuitem']");
		_paths.put("MANAGE_DOCUMENT_TYPES",
			"//a[contains(.,'Document Types') and @role='menuitem']");
		_paths.put("MANAGE_METADATA_SET",
			"//a[contains(.,'Metadata Set') and @role='menuitem']");
		_paths.put("FOLDER_NAME",
			"//div[@id='_20_entriesContainer']//a[@data-folder='true']/span[@class='entry-title']");
		_paths.put("FOLDER_NAME_1",
			"xpath=(//div[@id='_20_entriesContainer']//a[@data-folder='true']/span[@class='entry-title'])[1]");
		_paths.put("FOLDER_NAME_2",
			"xpath=(//div[@id='_20_entriesContainer']//a[@data-folder='true']/span[@class='entry-title'])[2]");
		_paths.put("FOLDER_NAME_3",
			"xpath=(//div[@id='_20_entriesContainer']//a[@data-folder='true']/span[@class='entry-title'])[3]");
		_paths.put("FOLDER_ACTIONS",
			"//div[@id='_20_entriesContainer']//a[@data-folder='true']/parent::div/span[contains(@class,'entry-action')]//a");
		_paths.put("FOLDER_ACTIONS_1",
			"xpath=(//div[@id='_20_entriesContainer']//a[@data-folder='true']/parent::div/span[contains(@class,'entry-action')]//a)[1]");
		_paths.put("FOLDER_ACTIONS_2",
			"xpath=(//div[@id='_20_entriesContainer']//a[@data-folder='true']/parent::div/span[contains(@class,'entry-action')]//a)[2]");
		_paths.put("FOLDER_ACTIONS_3",
			"xpath=(//div[@id='_20_entriesContainer']//a[@data-folder='true']/parent::div/span[contains(@class,'entry-action')]//a)[3]");
		_paths.put("FOLDER_ACTIONS_EDIT",
			"//a[contains(.,'Edit') and @role='menuitem']");
		_paths.put("FOLDER_ACTIONS_MOVE",
			"//a[contains(.,'Move') and @role='menuitem']");
		_paths.put("FOLDER_ACTIONS_PERMISSIONS",
			"//a[contains(.,'Permissions') and @role='menuitem']");
		_paths.put("FOLDER_ACTIONS_MOVE_TO_THE_RECYCLE_BIN",
			"//a[contains(.,'Move to the Recycle Bin') and @role='menuitem']");
		_paths.put("FOLDER_ACTIONS_ADD_SUBFOLDER",
			"//a[contains(.,'Add Subfolder') and @role='menuitem']");
		_paths.put("FOLDER_ACTIONS_ACCESS_FROM_DESKTOP",
			"//a[contains(.,'Access from Desktop') and @role='menuitem']");
		_paths.put("DOCUMENT_ACTIONS",
			"//div[@id='_20_entriesContainer']//a[@data-folder='false']/parent::div/span[contains(@class,'entry-action')]//a");
		_paths.put("DOCUMENT_ACTIONS_1",
			"xpath=(//div[@id='_20_entriesContainer']//a[@data-folder='false']/parent::div/span[contains(@class,'entry-action')]//a)[1]");
		_paths.put("DOCUMENT_ACTIONS_2",
			"xpath=(//div[@id='_20_entriesContainer']//a[@data-folder='false']/parent::div/span[contains(@class,'entry-action')]//a)[2]");
		_paths.put("DOCUMENT_ACTIONS_3",
			"xpath=(//div[@id='_20_entriesContainer']//a[@data-folder='false']/parent::div/span[contains(@class,'entry-action')]//a)[3]");
		_paths.put("DOCUMENT_IMAGE_LOCK",
			"//div[@id='_20_entriesContainer']//a[@data-folder='false']/div/img[@alt='Locked']");
		_paths.put("DOCUMENT_NAME",
			"//div[@id='_20_entriesContainer']//a[@data-folder='false']/span[@class='entry-title']");
		_paths.put("DOCUMENT_NAME_1",
			"xpath=(//div[@id='_20_entriesContainer']//a[@data-folder='false']/span[@class='entry-title'])[1]");
		_paths.put("DOCUMENT_NAME_2",
			"xpath=(//div[@id='_20_entriesContainer']//a[@data-folder='false']/span[@class='entry-title'])[2]");
		_paths.put("DOCUMENT_NAME_3",
			"xpath=(//div[@id='_20_entriesContainer']//a[@data-folder='false']/span[@class='entry-title'])[3]");
		_paths.put("DOCUMENT_ACTIONS_DOWNLOAD",
			"//a[contains(.,'Download') and @role='menuitem']");
		_paths.put("DOCUMENT_ACTIONS_EDIT",
			"//a[contains(.,'Edit') and @role='menuitem']");
		_paths.put("DOCUMENT_ACTIONS_MOVE",
			"//a[contains(.,'Move') and @role='menuitem']");
		_paths.put("DOCUMENT_ACTIONS_CANCEL_CHECKOUT",
			"//a[contains(.,'Cancel Checkout') and @role='menuitem']");
		_paths.put("DOCUMENT_ACTIONS_CHECKOUT",
			"//a[contains(.,'Checkout') and @role='menuitem']");
		_paths.put("DOCUMENT_ACTIONS_CHECKIN",
			"//a[contains(.,'Checkin') and @role='menuitem']");
		_paths.put("DOCUMENT_ACTIONS_PERMISSIONS",
			"//a[contains(.,'Permissions') and @role='menuitem']");
		_paths.put("DOCUMENT_ACTIONS_MOVE_TO_THE_RECYCLE_BIN",
			"//a[contains(.,'Move to the Recycle Bin') and @role='menuitem']");
	}
}