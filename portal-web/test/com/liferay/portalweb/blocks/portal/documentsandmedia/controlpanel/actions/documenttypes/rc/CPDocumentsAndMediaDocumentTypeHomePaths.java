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

package com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.actions.documenttypes.rc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPDocumentsAndMediaDocumentTypeHomePaths {
	public static Map<String, String> getPaths() {
		return _paths;
	}

	private static Map<String, String> _paths = new HashMap<String, String>();

	static {
		_paths.put("TOP", "relative=top");
		_paths.put("PAGE_NAME", "//iframe[@id='_20_openFileEntryTypeView']");
		_paths.put("HEADER_SUCCESS_MESSAGE",
			"//div[@class='portlet-msg-success']");
		_paths.put("BUTTON_VIEW_ALL", "//span[contains(@class,'view-button')]/a");
		_paths.put("BUTTON_ADD", "//span[contains(@class,'add-button')]/a");
		_paths.put("SEARCH_FIELD", "//input[@id='_20_keywords']");
		_paths.put("SEARCH_BUTTON", "//input[@value='Search']");
		_paths.put("TABLE_NAME_1", "//tr[3]/td[1]");
		_paths.put("TABLE_MODIFIED_DATE_1", "//tr[3]/td[2]");
		_paths.put("TABLE_ACTIONS_1",
			"//tr[3]/td[3]/span[@title='Actions']/ul/li/strong/a/span");
		_paths.put("TABLE_NAME_2", "//tr[4]/td[1]");
		_paths.put("TABLE_MODIFIED_DATE_2", "//tr[4]/td[2]");
		_paths.put("TABLE_ACTIONS_2",
			"//tr[4]/td[3]/span[@title='Actions']/ul/li/strong/a/span");
		_paths.put("TABLE_NAME_3", "//tr[5]/td[1]");
		_paths.put("TABLE_MODIFIED_DATE_3", "//tr[5]/td[2]");
		_paths.put("TABLE_ACTIONS_3",
			"//tr[5]/td[3]/span[@title='Actions']/ul/li/strong/a/span");
		_paths.put("ACTIONS_EDIT",
			"//a[contains(.,'Edit') and @role='menuitem']");
		_paths.put("ACTIONS_PERMISSIONS",
			"//a[contains(.,'Permissions') and @role='menuitem']");
		_paths.put("ACTIONS_DELETE",
			"//a[contains(.,'Delete') and @role='menuitem']");
		_paths.put("ACTIONS_DELETE_CONFIRMATION", "");
	}
}