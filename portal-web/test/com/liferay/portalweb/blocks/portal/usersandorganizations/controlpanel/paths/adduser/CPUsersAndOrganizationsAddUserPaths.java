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

package com.liferay.portalweb.blocks.portal.usersandorganizations.controlpanel.paths.adduser;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPUsersAndOrganizationsAddUserPaths {
	public static Map<String, String[]> getPaths() {
		return _paths;
	}

	private static String[] _PAGE_NAME = {
			"", "Control Panel Users and Organizations Add User Page"
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
	private static String[] _BREADCRUMB_4 = {
			"//nav[@id='breadcrumbs']/ul/li[4]/span/a", "Breadcrumb 4"
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
	private static String[] _USER_SCREEN_NAME = {
			"//input[@id='_125_screenName']", "User's Screen Name"
		};
	private static String[] _USER_EMAIL_ADDRESS = {
			"//input[@id='_125_emailAddress']", "User's Email Address"
		};
	private static String[] _USER_PREFIX = {
			"//select[@id='_125_prefixId']", "User's Prefix Title"
		};
	private static String[] _USER_FIRST_NAME = {
			"//input[@id='_125_firstName']", "User's First Name"
		};
	private static String[] _USER_MIDDLE_NAME = {
			"//input[@id='_125_middleName']", "User's Middle Name"
		};
	private static String[] _USER_LAST_NAME = {
			"//input[@id='_125_lastName']", "User's Last Name"
		};
	private static String[] _USER_SUFFIX = {
			"//select[@id='_125_suffixId']", "User's Suffix"
		};
	private static String[] _USER_BIRTHDAY_MONTH = {
			"//select[@id='_125_birthdayMonth']", "User's Birthday Month"
		};
	private static String[] _USER_BIRTHDAY_DAY = {
			"//select[@id='_125_birthdayDay']", "User's Birthday Day"
		};
	private static String[] _USER_BIRTHDAY_YEAR = {
			"//select[@id='_125_birthdayYear']", "User's Birthday Year"
		};
	private static String[] _USER_BIRTHDAY_CALENDAR = {
			"//button[@id='buttonTest']", "Birthday Calendar"
		};
	private static String[] _USER_GENDER = {
			"//select[@id='_125_male']", "User's Gender"
		};
	private static String[] _USER_JOB_TITLE = {
			"//input[@id='_125_jobTitle']", "User's Job Title"
		};
	private static String[] _USER_INFORMATION_DETAILS = {
			"//a[@id='_125_detailsLink']", "Details"
		};
	private static String[] _USER_INFORMATION_ORGANIZATIONS = {
			"//a[@id='_125_organizationsLink']", "Organizations"
		};
	private static String[] _USER_INFORMATION_PERSONAL_SITE = {
			"//a[@id='_125_personalSiteLink']", "Personal site"
		};
	private static String[] _BUTTONS_SAVE = { "//input[@value='Save']", "Save" };
	private static String[] _BUTTONS_CANCEL = {
			"//input[@value='Cancel']", "Cancel"
		};
	private static Map<String, String[]> _paths = new HashMap<String, String[]>();

	static {
		_paths.put("PAGE_NAME", _PAGE_NAME);
		_paths.put("BREADCRUMB_1", _BREADCRUMB_1);
		_paths.put("BREADCRUMB_2", _BREADCRUMB_2);
		_paths.put("BREADCRUMB_3", _BREADCRUMB_3);
		_paths.put("BREADCRUMB_4", _BREADCRUMB_4);
		_paths.put("HEADER_PORTLET_TITLE", _HEADER_PORTLET_TITLE);
		_paths.put("HEADER_PORTLET_DESCRIPTION", _HEADER_PORTLET_DESCRIPTION);
		_paths.put("HEADER_PORTLET_SUCCESS", _HEADER_PORTLET_SUCCESS);
		_paths.put("TOOLBAR_BROWSE", _TOOLBAR_BROWSE);
		_paths.put("TOOLBAR_VIEW_ORGANIZATIONS", _TOOLBAR_VIEW_ORGANIZATIONS);
		_paths.put("TOOLBAR_VIEW_USERS", _TOOLBAR_VIEW_USERS);
		_paths.put("TOOLBAR_ADD", _TOOLBAR_ADD);
		_paths.put("TOOLBAR_EXPORT_USERS", _TOOLBAR_EXPORT_USERS);
		_paths.put("USER_SCREEN_NAME", _USER_SCREEN_NAME);
		_paths.put("USER_EMAIL_ADDRESS", _USER_EMAIL_ADDRESS);
		_paths.put("USER_PREFIX", _USER_PREFIX);
		_paths.put("USER_FIRST_NAME", _USER_FIRST_NAME);
		_paths.put("USER_MIDDLE_NAME", _USER_MIDDLE_NAME);
		_paths.put("USER_LAST_NAME", _USER_LAST_NAME);
		_paths.put("USER_SUFFIX", _USER_SUFFIX);
		_paths.put("USER_BIRTHDAY_MONTH", _USER_BIRTHDAY_MONTH);
		_paths.put("USER_BIRTHDAY_DAY", _USER_BIRTHDAY_DAY);
		_paths.put("USER_BIRTHDAY_YEAR", _USER_BIRTHDAY_YEAR);
		_paths.put("USER_BIRTHDAY_CALENDAR", _USER_BIRTHDAY_CALENDAR);
		_paths.put("USER_GENDER", _USER_GENDER);
		_paths.put("USER_JOB_TITLE", _USER_JOB_TITLE);
		_paths.put("USER_INFORMATION_DETAILS", _USER_INFORMATION_DETAILS);
		_paths.put("USER_INFORMATION_ORGANIZATIONS",
			_USER_INFORMATION_ORGANIZATIONS);
		_paths.put("USER_INFORMATION_PERSONAL_SITE",
			_USER_INFORMATION_PERSONAL_SITE);
		_paths.put("BUTTONS_SAVE", _BUTTONS_SAVE);
		_paths.put("BUTTONS_CANCEL", _BUTTONS_CANCEL);
	}
}