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

package com.liferay.portalweb.blocks.portal.usersandorganizations.controlpanel.actions.adduser.rc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPUsersAndOrganizationsAddUserPaths {
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
		_paths.put("BREADCRUMB_4", "//nav[@id='breadcrumbs']/ul/li[4]/span/a");
		_paths.put("HEADER_PORTLET_TITLE", "//h1[@id='cpPortletTitle']/span");
		_paths.put("HEADER_PORTLET_DESCRIPTION",
			"//div[@id='cpContextPanelTemplate']");
		_paths.put("HEADER_PORTLET_SUCCESS",
			"//div[@class='portlet-msg-success']");
		_paths.put("TOOLBAR_BROWSE",
			"xpath=(//span[contains(@class,'lfr-toolbar-button')][1])/a");
		_paths.put("TOOLBAR_VIEW_ORGANIZATIONS",
			"xpath=(//span[contains(@class,'lfr-toolbar-button')][2])/a");
		_paths.put("TOOLBAR_VIEW_USERS",
			"xpath=(//span[contains(@class,'lfr-toolbar-button')][3])/a");
		_paths.put("TOOLBAR_ADD", "//span[@title='Add']/ul/li/strong/a/span");
		_paths.put("TOOLBAR_EXPORT_USERS",
			"xpath=(//span[contains(@class,'lfr-toolbar-button')][4])/a");
		_paths.put("USER_SCREEN_NAME", "//input[@id='_125_screenName']");
		_paths.put("USER_EMAIL_ADDRESS", "//input[@id='_125_emailAddress']");
		_paths.put("USER_PREFIX", "//select[@id='_125_prefixId']");
		_paths.put("USER_FIRST_NAME", "//input[@id='_125_firstName']");
		_paths.put("USER_MIDDLE_NAME", "//input[@id='_125_middleName']");
		_paths.put("USER_LAST_NAME", "//input[@id='_125_lastName']");
		_paths.put("USER_SUFFIX", "//select[@id='_125_suffixId']");
		_paths.put("USER_BIRTHDAY_MONTH", "//select[@id='_125_birthdayMonth']");
		_paths.put("USER_BIRTHDAY_DAY", "//select[@id='_125_birthdayDay']");
		_paths.put("USER_BIRTHDAY_YEAR", "//select[@id='_125_birthdayYear']");
		_paths.put("USER_BIRTHDAY_CALENDAR", "//button[@id='buttonTest']");
		_paths.put("USER_GENDER", "//select[@id='_125_male']");
		_paths.put("USER_JOB_TITLE", "//input[@id='_125_jobTitle']");
		_paths.put("USER_INFORMATION_DETAILS", "//a[@id='_125_detailsLink']");
		_paths.put("USER_INFORMATION_ORGANIZATIONS",
			"//a[@id='_125_organizationsLink']");
		_paths.put("USER_INFORMATION_PERSONAL_SITE",
			"//a[@id='_125_personalSiteLink']");
		_paths.put("BUTTONS_SAVE", "//input[@value='Save']");
		_paths.put("BUTTONS_CANCEL", "//input[@value='Cancel']");
	}
}