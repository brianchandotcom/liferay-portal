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

package com.liferay.portalweb.blocks.portal.usersandorganizations.controlpanel.paths.home;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPUsersAndOrganizationsSearchAllInactiveUsersPaths {
	public static Map<String, String[]> getPaths() {
		return _paths;
	}

	private static String[] _PAGE_NAME = {
			"",
			"Control Panel Users and Organizations Search All Inactive Users Page"
		};
	private static String[] _BREADCRUMB_1 = {
			"//nav[@id='breadcrumbs']/ul/li[1]/span/a", "Breadcrumb 1"
		};
	private static String[] _BREADCRUMB_2 = {
			"//nav[@id='breadcrumbs']/ul/li[2]/span", "Breadcrumb 2"
		};
	private static String[] _BREADCRUMB_3 = {
			"//nav[@id='breadcrumbs']/ul/li[3]/span/a", "Breadcrumb 3"
		};
	private static String[] _HEADER_PORTLET_TITLE = {
			"//h1[@id='cpPortletTitle']/span", "Portlet Title"
		};
	private static String[] _HEADER_PORTLET_DESCRIPTION = {
			"//div[@id='cpContextPanelTemplate']", "Portlet Description"
		};
	private static String[] _HEADER_PORTLET_SUCCESS = {
			"//div[@class='portlet-msg-success']", "Portlet Succes Message"
		};
	private static String[] _HEADER_CONFIRMATION = { "", "Confirmation Message" };
	private static String[] _TOOLBAR_BROWSE = {
			"xpath=(//span[contains(@class,'lfr-toolbar-button')][1])/a",
			"Browse"
		};
	private static String[] _TOOLBAR_VIEW_ORGANIZATIONS = {
			"xpath=(//span[contains(@class,'lfr-toolbar-button')][2])/a",
			"View Organizations"
		};
	private static String[] _TOOLBAR_VIEW_USERS = {
			"xpath=(//span[contains(@class,'lfr-toolbar-button')][3])/a",
			"View Users"
		};
	private static String[] _TOOLBAR_ADD = {
			"//span[@title='Add']/ul/li/strong/a/span", "Add"
		};
	private static String[] _TOOLBAR_EXPORT_USERS = {
			"xpath=(//span[contains(@class,'lfr-toolbar-button')][4])/a",
			"Export Users"
		};
	private static String[] _ADD_USER = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'User')]",
			"Add User"
		};
	private static String[] _ADD_REGULAR_ORGANIZATION = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Regular Organization')]",
			"Add Regular Organization"
		};
	private static String[] _ADD_LOCATION = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Location')]",
			"Location"
		};
	private static String[] _BASIC_SEARCH_FIELD = {
			"//input[@id='_125_toggle_id_users_admin_user_searchkeywords']",
			"Basic Search Field"
		};
	private static String[] _BASIC_SEARCH_BUTTON = {
			"//input[@value='Search']", "Basic Search Button"
		};
	private static String[] _BASIC_SEARCH_ADVANCED_LINK = {
			"//a[contains(.,'Advanced')]", "Advanced Search Link"
		};
	private static String[] _ADVANCED_SEARCH_MATCH = {
			"//select[@id='_125_andOperator']", "Advanced Search Match Dropdown"
		};
	private static String[] _ADVANCED_SEARCH_FIRST_NAME = {
			"//input[@id='_125_firstName']", "User's First Name"
		};
	private static String[] _ADVANCED_SEARCH_MIDDLE_NAME = {
			"//input[@id='_125_middleName']", "User's Middle Name"
		};
	private static String[] _ADVANCED_SEARCH_LAST_NAME = {
			"//input[@id='_125_lastName']", "User's Last Name"
		};
	private static String[] _ADVANCED_SEARCH_SCREEN_NAME = {
			"//input[@id='_125_screenName']", "User's Screen Name"
		};
	private static String[] _ADVANCED_SEARCH_EMAIL_ADDRESS = {
			"//input[@id='_125_emailAddress']", "User's Email Address"
		};
	private static String[] _ADVANCED_SEARCH_STATUS = {
			"//select[@id='_125_status']", "User's Status Drop Down"
		};
	private static String[] _ADVANCED_SEARCH_BUTTON = {
			"xpath=(//input[@value='Search'])[2]", "Advanced Search Button"
		};
	private static String[] _ADVANCED_SEARCH_BASIC_LINK = {
			"//a[contains(.,'Basic')]", "Basic Search Link"
		};
	private static String[] _USERS_DELETE = {
			"//input[@value='Delete']", "Delete User Button"
		};
	private static String[] _USERS_RESTORE = {
			"//input[@value='Restore']", "Restore User Button"
		};
	private static String[] _USERS_CHECK_ALL = {
			"//input[@name='_125_allRowIds']", "Flag All Users"
		};
	private static String[] _USERS_USER_1_CHECK = {
			"xpath=(//td[@headers='_125_usersSearchContainer_col-rowChecker'])[1]/input",
			"Flag First User"
		};
	private static String[] _USERS_USER_1_FIRST_NAME = {
			"xpath=(//td[@headers='_125_usersSearchContainer_col-first-name'])[1]/a",
			"First User's First Name"
		};
	private static String[] _USERS_USER_1_LAST_NAME = {
			"xpath=(//td[@headers='_125_usersSearchContainer_col-last-name'])[1]/a",
			"First User's Last Name"
		};
	private static String[] _USERS_USER_1_SCREEN_NAME = {
			"xpath=(//td[@headers='_125_usersSearchContainer_col-screen-name'])[1]/a",
			"First User's Screen Name"
		};
	private static String[] _USERS_USER_1_JOB_TITLE = {
			"xpath=(//td[@headers='_125_usersSearchContainer_col-job-title'])[1]/a",
			"First User's Job Title"
		};
	private static String[] _USERS_USER_1_ORGANIZATIONS = {
			"xpath=(//td[@headers='_125_usersSearchContainer_col-organizations'])[1]/a",
			"First User's Organizations"
		};
	private static String[] _USERS_USER_1_USER_GROUPS = {
			"xpath=(//td[@headers='_125_usersSearchContainer_col-user-groups'])[1]/a",
			"First User's User Groups"
		};
	private static String[] _USERS_USER_1_ACTIONS = {
			"xpath=(//td[@headers='_125_usersSearchContainer_col-8'])[1]//span[@title='Actions']/ul/li/strong/a",
			"First User's Actions"
		};
	private static String[] _USERS_USER_2_CHECK = {
			"xpath=(//td[@headers='_125_usersSearchContainer_col-rowChecker'])[2]/input",
			"Flag Second User"
		};
	private static String[] _USERS_USER_2_FIRST_NAME = {
			"xpath=(//td[@headers='_125_usersSearchContainer_col-first-name'])[2]/a",
			"Second User's First Name"
		};
	private static String[] _USERS_USER_2_LAST_NAME = {
			"xpath=(//td[@headers='_125_usersSearchContainer_col-last-name'])[2]/a",
			"Second User's Last Name"
		};
	private static String[] _USERS_USER_2_SCREEN_NAME = {
			"xpath=(//td[@headers='_125_usersSearchContainer_col-screen-name'])[2]/a",
			"Second User's Screen Name"
		};
	private static String[] _USERS_USER_2_JOB_TITLE = {
			"xpath=(//td[@headers='_125_usersSearchContainer_col-job-title'])[2]/a",
			"Second User's Job Title"
		};
	private static String[] _USERS_USER_2_ORGANIZATIONS = {
			"xpath=(//td[@headers='_125_usersSearchContainer_col-organizations'])[2]/a",
			"Second User's Organizations"
		};
	private static String[] _USERS_USER_2_USER_GROUPS = {
			"xpath=(//td[@headers='_125_usersSearchContainer_col-user-groups'])[2]/a",
			"Second User's User Groups"
		};
	private static String[] _USERS_USER_2_ACTIONS = {
			"xpath=(//td[@headers='_125_usersSearchContainer_col-8'])[2]//span[@title='Actions']/ul/li/strong/a",
			"Second User's Actions"
		};
	private static String[] _USERS_USER_3_CHECK = {
			"xpath=(//td[@headers='_125_usersSearchContainer_col-rowChecker'])[3]/input",
			"Flag Third User"
		};
	private static String[] _USERS_USER_3_FIRST_NAME = {
			"xpath=(//td[@headers='_125_usersSearchContainer_col-first-name'])[3]/a",
			"Third User's First Name"
		};
	private static String[] _USERS_USER_3_LAST_NAME = {
			"xpath=(//td[@headers='_125_usersSearchContainer_col-last-name'])[3]/a",
			"Third User's Last Name"
		};
	private static String[] _USERS_USER_3_SCREEN_NAME = {
			"xpath=(//td[@headers='_125_usersSearchContainer_col-screen-name'])[3]/a",
			"Third User's Screen Name"
		};
	private static String[] _USERS_USER_3_JOB_TITLE = {
			"xpath=(//td[@headers='_125_usersSearchContainer_col-job-title'])[3]/a",
			"Third User's Job Title"
		};
	private static String[] _USERS_USER_3_ORGANIZATIONS = {
			"xpath=(//td[@headers='_125_usersSearchContainer_col-organizations'])[3]/a",
			"Third User's Organizations"
		};
	private static String[] _USERS_USER_3_USER_GROUPS = {
			"xpath=(//td[@headers='_125_usersSearchContainer_col-user-groups'])[3]/a",
			"Third User's User Groups"
		};
	private static String[] _USERS_USER_3_ACTIONS = {
			"xpath=(//td[@headers='_125_usersSearchContainer_col-8'])[3]//span[@title='Actions']/ul/li/strong/a",
			"Third User's Actions"
		};
	private static String[] _USERS_MESSAGE = {
			"//div[@class='portlet-msg-info']", "Users Message"
		};
	private static String[] _USERS_ACTIONS_EDIT = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]",
			"Edit"
		};
	private static String[] _USERS_ACTIONS_PERMISSIONS = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Permissions')]",
			"Permissions"
		};
	private static String[] _USERS_ACTIONS_MANAGE_PAGES = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Manage Pages')]",
			"Manage Pages"
		};
	private static String[] _USERS_ACTIONS_IMPERSONATE_USER = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Impersonate User')]",
			"Impersonate User"
		};
	private static String[] _USERS_ACTIONS_ACTIVATE = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Activate')]",
			"Activate"
		};
	private static String[] _USERS_ACTIONS_DELETE = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Delete')]",
			"Delete"
		};
	private static Map<String, String[]> _paths = new HashMap<String, String[]>();

	static {
		_paths.put("PAGE_NAME", _PAGE_NAME);
		_paths.put("BREADCRUMB_1", _BREADCRUMB_1);
		_paths.put("BREADCRUMB_2", _BREADCRUMB_2);
		_paths.put("BREADCRUMB_3", _BREADCRUMB_3);
		_paths.put("HEADER_PORTLET_TITLE", _HEADER_PORTLET_TITLE);
		_paths.put("HEADER_PORTLET_DESCRIPTION", _HEADER_PORTLET_DESCRIPTION);
		_paths.put("HEADER_PORTLET_SUCCESS", _HEADER_PORTLET_SUCCESS);
		_paths.put("HEADER_CONFIRMATION", _HEADER_CONFIRMATION);
		_paths.put("TOOLBAR_BROWSE", _TOOLBAR_BROWSE);
		_paths.put("TOOLBAR_VIEW_ORGANIZATIONS", _TOOLBAR_VIEW_ORGANIZATIONS);
		_paths.put("TOOLBAR_VIEW_USERS", _TOOLBAR_VIEW_USERS);
		_paths.put("TOOLBAR_ADD", _TOOLBAR_ADD);
		_paths.put("TOOLBAR_EXPORT_USERS", _TOOLBAR_EXPORT_USERS);
		_paths.put("ADD_USER", _ADD_USER);
		_paths.put("ADD_REGULAR_ORGANIZATION", _ADD_REGULAR_ORGANIZATION);
		_paths.put("ADD_LOCATION", _ADD_LOCATION);
		_paths.put("BASIC_SEARCH_FIELD", _BASIC_SEARCH_FIELD);
		_paths.put("BASIC_SEARCH_BUTTON", _BASIC_SEARCH_BUTTON);
		_paths.put("BASIC_SEARCH_ADVANCED_LINK", _BASIC_SEARCH_ADVANCED_LINK);
		_paths.put("ADVANCED_SEARCH_MATCH", _ADVANCED_SEARCH_MATCH);
		_paths.put("ADVANCED_SEARCH_FIRST_NAME", _ADVANCED_SEARCH_FIRST_NAME);
		_paths.put("ADVANCED_SEARCH_MIDDLE_NAME", _ADVANCED_SEARCH_MIDDLE_NAME);
		_paths.put("ADVANCED_SEARCH_LAST_NAME", _ADVANCED_SEARCH_LAST_NAME);
		_paths.put("ADVANCED_SEARCH_SCREEN_NAME", _ADVANCED_SEARCH_SCREEN_NAME);
		_paths.put("ADVANCED_SEARCH_EMAIL_ADDRESS",
			_ADVANCED_SEARCH_EMAIL_ADDRESS);
		_paths.put("ADVANCED_SEARCH_STATUS", _ADVANCED_SEARCH_STATUS);
		_paths.put("ADVANCED_SEARCH_BUTTON", _ADVANCED_SEARCH_BUTTON);
		_paths.put("ADVANCED_SEARCH_BASIC_LINK", _ADVANCED_SEARCH_BASIC_LINK);
		_paths.put("USERS_DELETE", _USERS_DELETE);
		_paths.put("USERS_RESTORE", _USERS_RESTORE);
		_paths.put("USERS_CHECK_ALL", _USERS_CHECK_ALL);
		_paths.put("USERS_USER_1_CHECK", _USERS_USER_1_CHECK);
		_paths.put("USERS_USER_1_FIRST_NAME", _USERS_USER_1_FIRST_NAME);
		_paths.put("USERS_USER_1_LAST_NAME", _USERS_USER_1_LAST_NAME);
		_paths.put("USERS_USER_1_SCREEN_NAME", _USERS_USER_1_SCREEN_NAME);
		_paths.put("USERS_USER_1_JOB_TITLE", _USERS_USER_1_JOB_TITLE);
		_paths.put("USERS_USER_1_ORGANIZATIONS", _USERS_USER_1_ORGANIZATIONS);
		_paths.put("USERS_USER_1_USER_GROUPS", _USERS_USER_1_USER_GROUPS);
		_paths.put("USERS_USER_1_ACTIONS", _USERS_USER_1_ACTIONS);
		_paths.put("USERS_USER_2_CHECK", _USERS_USER_2_CHECK);
		_paths.put("USERS_USER_2_FIRST_NAME", _USERS_USER_2_FIRST_NAME);
		_paths.put("USERS_USER_2_LAST_NAME", _USERS_USER_2_LAST_NAME);
		_paths.put("USERS_USER_2_SCREEN_NAME", _USERS_USER_2_SCREEN_NAME);
		_paths.put("USERS_USER_2_JOB_TITLE", _USERS_USER_2_JOB_TITLE);
		_paths.put("USERS_USER_2_ORGANIZATIONS", _USERS_USER_2_ORGANIZATIONS);
		_paths.put("USERS_USER_2_USER_GROUPS", _USERS_USER_2_USER_GROUPS);
		_paths.put("USERS_USER_2_ACTIONS", _USERS_USER_2_ACTIONS);
		_paths.put("USERS_USER_3_CHECK", _USERS_USER_3_CHECK);
		_paths.put("USERS_USER_3_FIRST_NAME", _USERS_USER_3_FIRST_NAME);
		_paths.put("USERS_USER_3_LAST_NAME", _USERS_USER_3_LAST_NAME);
		_paths.put("USERS_USER_3_SCREEN_NAME", _USERS_USER_3_SCREEN_NAME);
		_paths.put("USERS_USER_3_JOB_TITLE", _USERS_USER_3_JOB_TITLE);
		_paths.put("USERS_USER_3_ORGANIZATIONS", _USERS_USER_3_ORGANIZATIONS);
		_paths.put("USERS_USER_3_USER_GROUPS", _USERS_USER_3_USER_GROUPS);
		_paths.put("USERS_USER_3_ACTIONS", _USERS_USER_3_ACTIONS);
		_paths.put("USERS_MESSAGE", _USERS_MESSAGE);
		_paths.put("USERS_ACTIONS_EDIT", _USERS_ACTIONS_EDIT);
		_paths.put("USERS_ACTIONS_PERMISSIONS", _USERS_ACTIONS_PERMISSIONS);
		_paths.put("USERS_ACTIONS_MANAGE_PAGES", _USERS_ACTIONS_MANAGE_PAGES);
		_paths.put("USERS_ACTIONS_IMPERSONATE_USER",
			_USERS_ACTIONS_IMPERSONATE_USER);
		_paths.put("USERS_ACTIONS_ACTIVATE", _USERS_ACTIONS_ACTIVATE);
		_paths.put("USERS_ACTIONS_DELETE", _USERS_ACTIONS_DELETE);
	}
}