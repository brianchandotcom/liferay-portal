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

package com.liferay.portalweb.blocks.portal.roles.controlpanel.actions.home.rc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPRolesHomePaths {
	public static Map<String, String> getPaths() {
		return _paths;
	}

	private static Map<String, String> _paths = new HashMap<String, String>();

	static {
		_paths.put("PAGE_NAME", "");
		_paths.put("BREADCRUMB_1", "//div[@id='breadcrumbs']/ul/li[1]/span/a");
		_paths.put("BREADCRUMB_2", "//div[@id='breadcrumbs']/ul/li[2]/span/a");
		_paths.put("BREADCRUMB_3", "//div[@id='breadcrumbs']/ul/li[3]/span/a");
		_paths.put("HEADER_PORTLET_TITLE", "//span[@class='portlet-title-text']");
		_paths.put("HEADER_PORTLET_INFO", "//div[@id='cpContextPanelTemplate']");
		_paths.put("HEADER_SUCCESS_MESSAGE",
			"//div[@class='portlet-msg-success']");
		_paths.put("HEADER_CONFIRMATION", "");
		_paths.put("TOOLBAR_VIEW_ALL", "//a[contains(.,'View All')]");
		_paths.put("TOOLBAR_ADD_BUTTON",
			"//span[@title='Add']/ul/li/strong/a/span");
		_paths.put("ADD_REGULAR_ROLE",
			"//a[contains(.,'Regular Role') and @role='menuitem']");
		_paths.put("ADD_SITE_ROLE",
			"//a[contains(.,'Site Role') and @role='menuitem']");
		_paths.put("ADD_ORGANIZATION_ROLE",
			"//a[contains(.,'Organization Role') and @role='menuitem']");
		_paths.put("SEARCH_FILED", "//input[@id='_128_keywords']");
		_paths.put("SEARCH_BUTTON", "//input[@value='Search']");
		_paths.put("ROLES_NAME", "//tr[contains(@class,'lfr-role')]/td[1]/a");
		_paths.put("ROLES_TYPE", "//tr[contains(@class,'lfr-role')]/td[2]/a");
		_paths.put("ROLES_DESCRIPTION",
			"//tr[contains(@class,'lfr-role')]/td[3]/a");
		_paths.put("ROLES_ACTIONS",
			"//span[@title='Actions']/ul/li/strong/a/span");
		_paths.put("ROLES_MESSAGE", "//div[@class='portlet-msg-info']");
		_paths.put("ACTIONS_EDIT",
			"//a[contains(.,'Edit') and @role='menuitem']");
		_paths.put("ACTIONS_PERMISSIONS",
			"//a[contains(.,'Permissions') and @role='menuitem']");
		_paths.put("ACTIONS_DEFINE_PERMISSIONS",
			"//a[contains(.,'Define Permissions') and @role='menuitem']");
		_paths.put("ACTIONS_ASSIGN_MEMBERS",
			"//a[contains(.,'Assign Members') and @role='menuitem']");
		_paths.put("ACTIONS_VIEW_USERS",
			"//a[contains(.,'View Users') and @role='menuitem']");
		_paths.put("ACTIONS_DELETE",
			"//a[contains(.,'Delete') and @role='menuitem']");
	}
}