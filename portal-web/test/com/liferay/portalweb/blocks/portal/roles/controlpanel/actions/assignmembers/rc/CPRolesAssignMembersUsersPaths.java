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

package com.liferay.portalweb.blocks.portal.roles.controlpanel.actions.assignmembers.rc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPRolesAssignMembersUsersPaths {
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
		_paths.put("BREADCRUMB_4", "//div[@id='breadcrumbs']/ul/li[4]/span");
		_paths.put("BREADCRUMB_5", "//div[@id='breadcrumbs']/ul/li[5]/span/a");
		_paths.put("TOOLBAR_USERS",
			"//a[@class='aui-tab-label' and contains(.,'Users')]");
		_paths.put("TOOLBAR_SITES",
			"//a[@class='aui-tab-label' and contains(.,'Sites')]");
		_paths.put("TOOLBAR_ASSIGN_MEMBERS",
			"//a[@class='aui-tab-label' and contains(.,'Organizations')]");
		_paths.put("TOOLBAR_USER_GROUPS",
			"//a[@class='aui-tab-label' and contains(.,'User Groups')]");
		_paths.put("STATUS_CURRENT",
			"//a[@class='aui-tab-label' and contains(.,'Current')]");
		_paths.put("STATUS_AVAILABLE",
			"//a[@class='aui-tab-label' and contains(.,'Available')]");
		_paths.put("BASIC_SEARCH_FIELD", "//input[@name='_128_keywords']");
		_paths.put("BASIC_SEARCH_BUTTON", "//input[@value='Search']");
		_paths.put("BASIC_SEARCH_ADVANCED_LINK", "//a[contains(.,'Advanced')]");
		_paths.put("ADVANCED_SEARCH_MATCH", "//select[@id='_128_andOperator']");
		_paths.put("ADVANCED_SEARCH_FIRST_NAME", "//input[@id='_128_firstName']");
		_paths.put("ADVANCED_SEARCH_MIDDLE_NAME",
			"//input[@id='_128_middleName']");
		_paths.put("ADVANCED_SEARCH_LAST_NAME", "//input[@id='_128_lastName']");
		_paths.put("ADVANCED_SEARCH_SCREEN_NAME",
			"//input[@id='_128_screenName']");
		_paths.put("ADVANCED_SEARCH_EMAIL_ADDRESS",
			"//input[@id='_128_emailAddress']");
		_paths.put("ADVANCED_SEARCH_STATUS", "//select[@id='_128_status']");
		_paths.put("ADVANCED_SEARCH_BUTTON",
			"xpath=(//input[@value='Search'])[2]");
		_paths.put("ADVANCED_SEARCH_BASIC_LINK", "//a[contains(.,'Basic')]");
		_paths.put("BUTTON_UPDATE_ASSOCIATIONS",
			"//input[@value='Update Associations']");
		_paths.put("USERS_CHECK_ALL", "xPath=(//input[@name='_128_allRowIds'])");
		_paths.put("USERS_USER_1_CHECK",
			"xpath=(//td[@headers='_128_usersSearchContainer_col-rowChecker'])[1]/input");
		_paths.put("USERS_USER_1_NAME",
			"xpath=(//td[@headers='_128_usersSearchContainer_col-first-name'])[1]/a");
		_paths.put("USERS_USER_2_CHECK",
			"xpath=(//td[@headers='_128_usersSearchContainer_col-rowChecker'])[2]/input");
		_paths.put("USERS_USER_2_NAME",
			"xpath=(//td[@headers='_128_usersSearchContainer_col-first-name'])[2]/a");
		_paths.put("USERS_USER_3_CHECK",
			"xpath=(//td[@headers='_128_usersSearchContainer_col-rowChecker'])[3]/input");
		_paths.put("USERS_USER_3_NAME",
			"xpath=(//td[@headers='_128_usersSearchContainer_col-first-name'])[3]/a");
	}
}