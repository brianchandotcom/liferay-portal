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

package com.liferay.portalweb.blocks.portal.recyclebin.controlpanel.actions.home.rc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPRecycleBinPortletPaths {
	public static Map<String, String> getPaths() {
		return _paths;
	}

	private static Map<String, String> _paths = new HashMap<String, String>();

	static {
		_paths.put("TOP", "relative=top");
		_paths.put("PAGE_NAME", "");
		_paths.put("BREADCRUMB_1", "//nav[@id='breadcrumbs']/ul/li[1]/span/a");
		_paths.put("BREADCRUMB_2", "//nav[@id='breadcrumbs']/ul/li[2]/span/a");
		_paths.put("BREADCRUMB_3", "//nav[@id='breadcrumbs']/ul/li[3]/span/a");
		_paths.put("HEADER_TITLE", "//h1[@id='cpPortletTitle']/span");
		_paths.put("HEADER_DESCRIPTION", "//div[@id='cpContextPanelTemplate']");
		_paths.put("HEADER_INFO_EMPTY", "//form[@id='_182_emptyForm']");
		_paths.put("PORTLET_SUCCESS", "//div[@class='portlet-msg-success']");
		_paths.put("EMPTY_RECYCLE_BIN_LINK", "//a[@id='_182_empty']");
		_paths.put("EMPTY_RECYCLE_BIN_CONFIRM", "");
		_paths.put("SEARCH_FIELD", "//input[@id='_182_keywords']");
		_paths.put("SEARCH_BUTTON", "//input[@value='Search']");
		_paths.put("TABLE_EMPTY_MESSAGE", "//div[@class='portlet-msg-info']");
		_paths.put("TABLE_HEADER_NAME", "//th[1]");
		_paths.put("TABLE_HEADER_TYPE", "//th[2]/span/a");
		_paths.put("TABLE_HEADER_REMOVED_DATE", "//th[3]/span/a");
		_paths.put("TABLE_HEADER_REMOVED_BY", "//th[4]/span/a");
		_paths.put("TABLE_NAME_1", "//tr[3]/td[1]/span/a/span");
		_paths.put("TABLE_NAME_2", "//tr[4]/td[1]/span/a/span");
		_paths.put("TABLE_NAME_3", "//tr[5]/td[1]/span/a/span");
		_paths.put("TABLE_TYPE_1", "//tr[3]/td[2]");
		_paths.put("TABLE_TYPE_2", "//tr[4]/td[2]");
		_paths.put("TABLE_TYPE_3", "//tr[5]/td[2]");
		_paths.put("TABLE_REMOVED_DATE_1", "//tr[3]/td[3]/span");
		_paths.put("TABLE_REMOVED_DATE_2", "//tr[4]/td[3]/span");
		_paths.put("TABLE_REMOVED_DATE_3", "//tr[5]/td[3]/span");
		_paths.put("TABLE_REMOVED_BY_1", "//tr[3]/td[4]");
		_paths.put("TABLE_REMOVED_BY_2", "//tr[4]/td[4]");
		_paths.put("TABLE_REMOVED_BY_3", "//tr[5]/td[4]");
		_paths.put("TABLE_ACTIONS_1",
			"//tr[3]/td[5]/span[@title='Actions']/ul/li/strong/a/span");
		_paths.put("TABLE_ACTIONS_2",
			"//tr[4]/td[5]/span[@title='Actions']/ul/li/strong/a/span");
		_paths.put("TABLE_ACTIONS_3",
			"//tr[5]/td[5]/span[@title='Actions']/ul/li/strong/a/span");
		_paths.put("ACTIONS_RESTORE",
			"//a[@role='menuitem' and contains(.,'Restore')]");
		_paths.put("ACTIONS_DELETE",
			"//a[@role='menuitem' and contains(.,'Delete')]");
	}
}