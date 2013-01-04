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

package com.liferay.portalweb.blocks.portal.usersandorganizations.controlpanel.actions.home.rc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPUsersAndOrganizationsSearchAllActiveUsersPaths {
	public static Map<String, String> getPaths() {
		return _paths;
	}

	private static Map<String, String> _paths = new HashMap<String, String>();

	static {
		_paths.put("TOP", "relative=top");
		_paths.put("PAGE_NAME", "");
		_paths.put("BREADCRUMB_1", "//nav[@id='breadcrumbs']/ul/li[1]/span/a");
		_paths.put("BREADCRUMB_2", "//nav[@id='breadcrumbs']/ul/li[2]/span");
		_paths.put("BREADCRUMB_3", "//nav[@id='breadcrumbs']/ul/li[3]/span/a");
		_paths.put("HEADER_PORTLET_TITLE", "//h1[@id='cpPortletTitle']/span");
		_paths.put("HEADER_PORTLET_DESCRIPTION",
			"//div[@id='cpContextPanelTemplate']");
		_paths.put("HEADER_PORTLET_SUCCESS",
			"//div[@class='portlet-msg-success']");
		_paths.put("HEADER_CONFIRMATION", "");
		_paths.put("TOOLBAR_BROWSE",
			"xpath=(//span[contains(@class,'lfr-toolbar-button')][1])/a");
		_paths.put("TOOLBAR_VIEW_ORGANIZATIONS",
			"xpath=(//span[contains(@class,'lfr-toolbar-button')][2])/a");
		_paths.put("TOOLBAR_VIEW_USERS",
			"xpath=(//span[contains(@class,'lfr-toolbar-button')][3])/a");
		_paths.put("TOOLBAR_ADD", "//span[@title='Add']/ul/li/strong/a/span");
		_paths.put("TOOLBAR_EXPORT_USERS",
			"xpath=(//span[contains(@class,'lfr-toolbar-button')][4])/a");
		_paths.put("ADD_USER",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'User')]");
		_paths.put("ADD_REGULAR_ORGANIZATION",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Regular Organization')]");
		_paths.put("ADD_LOCATION",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Location')]");
		_paths.put("BASIC_SEARCH_FIELD",
			"//input[@id='_125_toggle_id_users_admin_user_searchkeywords']");
		_paths.put("BASIC_SEARCH_BUTTON", "//input[@value='Search']");
		_paths.put("BASIC_SEARCH_ADVANCED_LINK", "//a[contains(.,'Advanced')]");
		_paths.put("ADVANCED_SEARCH_MATCH", "//select[@id='_125_andOperator']");
		_paths.put("ADVANCED_SEARCH_FIRST_NAME", "//input[@id='_125_firstName']");
		_paths.put("ADVANCED_SEARCH_MIDDLE_NAME",
			"//input[@id='_125_middleName']");
		_paths.put("ADVANCED_SEARCH_LAST_NAME", "//input[@id='_125_lastName']");
		_paths.put("ADVANCED_SEARCH_SCREEN_NAME",
			"//input[@id='_125_screenName']");
		_paths.put("ADVANCED_SEARCH_EMAIL_ADDRESS",
			"//input[@id='_125_emailAddress']");
		_paths.put("ADVANCED_SEARCH_STATUS", "//select[@id='_125_status']");
		_paths.put("ADVANCED_SEARCH_BUTTON",
			"xpath=(//input[@value='Search'])[2]");
		_paths.put("ADVANCED_SEARCH_BASIC_LINK", "//a[contains(.,'Basic')]");
		_paths.put("USERS_DEACTIVATE", "//input[@value='Deactivate']");
		_paths.put("USERS_CHECK_ALL", "//input[@name='_125_allRowIds']");
		_paths.put("USERS_USER_1_CHECK",
			"xpath=(//td[@headers='_125_usersSearchContainer_col-rowChecker'])[1]/input");
		_paths.put("USERS_USER_1_FIRST_NAME",
			"xpath=(//td[@headers='_125_usersSearchContainer_col-first-name'])[1]/a");
		_paths.put("USERS_USER_1_LAST_NAME",
			"xpath=(//td[@headers='_125_usersSearchContainer_col-last-name'])[1]/a");
		_paths.put("USERS_USER_1_SCREEN_NAME",
			"xpath=(//td[@headers='_125_usersSearchContainer_col-screen-name'])[1]/a");
		_paths.put("USERS_USER_1_JOB_TITLE",
			"xpath=(//td[@headers='_125_usersSearchContainer_col-job-title'])[1]/a");
		_paths.put("USERS_USER_1_ORGANIZATIONS",
			"xpath=(//td[@headers='_125_usersSearchContainer_col-organizations'])[1]/a");
		_paths.put("USERS_USER_1_USER_GROUPS",
			"xpath=(//td[@headers='_125_usersSearchContainer_col-user-groups'])[1]/a");
		_paths.put("USERS_USER_1_ACTIONS",
			"xpath=(//td[@headers='_125_usersSearchContainer_col-8'])[1]//span[@title='Actions']/ul/li/strong/a");
		_paths.put("USERS_USER_2_CHECK",
			"xpath=(//td[@headers='_125_usersSearchContainer_col-rowChecker'])[2]/input");
		_paths.put("USERS_USER_2_FIRST_NAME",
			"xpath=(//td[@headers='_125_usersSearchContainer_col-first-name'])[2]/a");
		_paths.put("USERS_USER_2_LAST_NAME",
			"xpath=(//td[@headers='_125_usersSearchContainer_col-last-name'])[2]/a");
		_paths.put("USERS_USER_2_SCREEN_NAME",
			"xpath=(//td[@headers='_125_usersSearchContainer_col-screen-name'])[2]/a");
		_paths.put("USERS_USER_2_JOB_TITLE",
			"xpath=(//td[@headers='_125_usersSearchContainer_col-job-title'])[2]/a");
		_paths.put("USERS_USER_2_ORGANIZATIONS",
			"xpath=(//td[@headers='_125_usersSearchContainer_col-organizations'])[2]/a");
		_paths.put("USERS_USER_2_USER_GROUPS",
			"xpath=(//td[@headers='_125_usersSearchContainer_col-user-groups'])[2]/a");
		_paths.put("USERS_USER_2_ACTIONS",
			"xpath=(//td[@headers='_125_usersSearchContainer_col-8'])[2]//span[@title='Actions']/ul/li/strong/a");
		_paths.put("USERS_USER_3_CHECK",
			"xpath=(//td[@headers='_125_usersSearchContainer_col-rowChecker'])[3]/input");
		_paths.put("USERS_USER_3_FIRST_NAME",
			"xpath=(//td[@headers='_125_usersSearchContainer_col-first-name'])[3]/a");
		_paths.put("USERS_USER_3_LAST_NAME",
			"xpath=(//td[@headers='_125_usersSearchContainer_col-last-name'])[3]/a");
		_paths.put("USERS_USER_3_SCREEN_NAME",
			"xpath=(//td[@headers='_125_usersSearchContainer_col-screen-name'])[3]/a");
		_paths.put("USERS_USER_3_JOB_TITLE",
			"xpath=(//td[@headers='_125_usersSearchContainer_col-job-title'])[3]/a");
		_paths.put("USERS_USER_3_ORGANIZATIONS",
			"xpath=(//td[@headers='_125_usersSearchContainer_col-organizations'])[3]/a");
		_paths.put("USERS_USER_3_USER_GROUPS",
			"xpath=(//td[@headers='_125_usersSearchContainer_col-user-groups'])[3]/a");
		_paths.put("USERS_USER_3_ACTIONS",
			"xpath=(//td[@headers='_125_usersSearchContainer_col-8'])[3]//span[@title='Actions']/ul/li/strong/a");
		_paths.put("USERS_ACTIONS_EDIT",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]");
		_paths.put("USERS_ACTIONS_PERMISSIONS",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Permissions')]");
		_paths.put("USERS_ACTIONS_MANAGE_PAGES",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Manage Pages')]");
	}
}