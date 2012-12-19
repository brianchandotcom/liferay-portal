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

package com.liferay.portalweb.blocks.portal.controlpanel.usersandorganizations.paths.edituser;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPUsersAndOrganizationsEditUserPersonalSitePaths {
	public static Map<String, String[]> getPaths() {
		return _paths;
	}

	private static String[] _PAGE_NAME = {
			"",
			"Control Panel Users and Organizations Edit User Personal Site Page"
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
			"//nav[@id='breadcrumbs']/ul/li[4]/span", "Breadcrumb 4"
		};
	private static String[] _BREADCRUMB_5 = {
			"//nav[@id='breadcrumbs']/ul/li[5]/span/a", "Breadcrumb 5"
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
	private static String[] _PERSONAL_SITE_PUBLIC_PAGES = {
			"//select[@id='_125_publicLayoutSetPrototypeId']",
			"Select Public Pages"
		};
	private static String[] _PERSONAL_SITE_PUBLIC_PAGES_ENABLE_PROPAGATION = {
			"//input[@id='_125_publicLayoutSetPrototypeLinkEnabledCheckbox']",
			"Enable propagation of changes for Public Pages"
		};
	private static String[] _PERSONAL_SITE_PRIVATE_PAGES = {
			"//select[@id='_125_privateLayoutSetPrototypeId']",
			"Select Private Pages"
		};
	private static String[] _PERSONAL_SITE_PRIVATE_PAGES_ENABLE_PROPAGATION = {
			"//input[@id='_125_privateLayoutSetPrototypeLinkEnabledCheckbox']",
			"Enable propagation of changes for Private Pages"
		};
	private static String[] _USER_INFORMATION_DETAILS = {
			"//a[@id='_125_detailsLink']", "Details"
		};
	private static String[] _USER_INFORMATION_PASSWORD = {
			"//a[@id='_125_passwordLink']", "Password"
		};
	private static String[] _USER_INFORMATION_ORGANIZATIONS = {
			"//a[@id='_125_organizationsLink']", "Organizations"
		};
	private static String[] _USER_INFORMATION__SITES = {
			"//a[@id='_125_sitesLink']", "Sites"
		};
	private static String[] _USER_INFORMATION__USER_GROUPS = {
			"//a[@id='_125_userGroupsLink']", "User Groups"
		};
	private static String[] _USER_INFORMATION_ROLES = {
			"//a[@id='_125_rolesLink']", "Roles"
		};
	private static String[] _USER_INFORMATION_PERSONAL_SITE = {
			"//a[@id='_125_personalSiteLink']", "Personal site"
		};
	private static String[] _USER_INFORMATION_CATEGORIZATION = {
			"//a[@id='_125_categorizationLink']", "Categorization"
		};
	private static String[] _IDENTIFICATION_ADDRESSES = {
			"//a[@id='_125_addressesLink']", "Addresses"
		};
	private static String[] _IDENTIFICATION_ADDRESSES_PHONE_NUMBERS = {
			"//a[@id='_125_phoneNumbersLink']", "Phone Numbers"
		};
	private static String[] _IDENTIFICATION_ADDRESSES_ADDITIONAL_EMAIL_ADDRESSES =
		{
			"//a[@id='_125_additionalEmailAddressesLink']",
			"Additional Email Addresses"
		};
	private static String[] _IDENTIFICATION_ADDRESSES_WEBSITES = {
			"//a[@id='_125_websitesLink']", "Websites"
		};
	private static String[] _IDENTIFICATION_ADDRESSES_INSTANT_MESSENGER = {
			"//a[@id='_125_instantMessengerLink']", "Instant Messenger"
		};
	private static String[] _IDENTIFICATION_ADDRESSES_SOCIAL_NETWORK = {
			"//a[@id='_125_socialNetworkLink']", "Social Network"
		};
	private static String[] _IDENTIFICATION_ADDRESSES_SMS = {
			"//a[@id='_125_smsLink']", "SMS"
		};
	private static String[] _IDENTIFICATION_ADDRESSES_OPENID = {
			"//a[@id='_125_openIdLink']", "OpenID"
		};
	private static String[] _MISCELLANEOUS_ANNOUNCEMENTS = {
			"//a[@id='_125_announcementsLink']", "Announcements"
		};
	private static String[] _MISCELLANEOUS_DISPLAY_SETTINGS = {
			"//a[@id='_125_displaySettingsLink']", "Display Settings"
		};
	private static String[] _MISCELLANEOUS_COMMENTS = {
			"//a[@id='_125_commentsLink']", "Comments"
		};
	private static String[] _MISCELLANEOUS_CUSTOM_FIELDS = {
			"//a[@id='_125_customFieldsLink']", "Custom Fields"
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
		_paths.put("BREADCRUMB_5", _BREADCRUMB_5);
		_paths.put("HEADER_PORTLET_TITLE", _HEADER_PORTLET_TITLE);
		_paths.put("HEADER_PORTLET_DESCRIPTION", _HEADER_PORTLET_DESCRIPTION);
		_paths.put("HEADER_PORTLET_SUCCESS", _HEADER_PORTLET_SUCCESS);
		_paths.put("TOOLBAR_BROWSE", _TOOLBAR_BROWSE);
		_paths.put("TOOLBAR_VIEW_ORGANIZATIONS", _TOOLBAR_VIEW_ORGANIZATIONS);
		_paths.put("TOOLBAR_VIEW_USERS", _TOOLBAR_VIEW_USERS);
		_paths.put("TOOLBAR_ADD", _TOOLBAR_ADD);
		_paths.put("TOOLBAR_EXPORT_USERS", _TOOLBAR_EXPORT_USERS);
		_paths.put("PERSONAL_SITE_PUBLIC_PAGES", _PERSONAL_SITE_PUBLIC_PAGES);
		_paths.put("PERSONAL_SITE_PUBLIC_PAGES_ENABLE_PROPAGATION",
			_PERSONAL_SITE_PUBLIC_PAGES_ENABLE_PROPAGATION);
		_paths.put("PERSONAL_SITE_PRIVATE_PAGES", _PERSONAL_SITE_PRIVATE_PAGES);
		_paths.put("PERSONAL_SITE_PRIVATE_PAGES_ENABLE_PROPAGATION",
			_PERSONAL_SITE_PRIVATE_PAGES_ENABLE_PROPAGATION);
		_paths.put("USER_INFORMATION_DETAILS", _USER_INFORMATION_DETAILS);
		_paths.put("USER_INFORMATION_PASSWORD", _USER_INFORMATION_PASSWORD);
		_paths.put("USER_INFORMATION_ORGANIZATIONS",
			_USER_INFORMATION_ORGANIZATIONS);
		_paths.put("USER_INFORMATION__SITES", _USER_INFORMATION__SITES);
		_paths.put("USER_INFORMATION__USER_GROUPS",
			_USER_INFORMATION__USER_GROUPS);
		_paths.put("USER_INFORMATION_ROLES", _USER_INFORMATION_ROLES);
		_paths.put("USER_INFORMATION_PERSONAL_SITE",
			_USER_INFORMATION_PERSONAL_SITE);
		_paths.put("USER_INFORMATION_CATEGORIZATION",
			_USER_INFORMATION_CATEGORIZATION);
		_paths.put("IDENTIFICATION_ADDRESSES", _IDENTIFICATION_ADDRESSES);
		_paths.put("IDENTIFICATION_ADDRESSES_PHONE_NUMBERS",
			_IDENTIFICATION_ADDRESSES_PHONE_NUMBERS);
		_paths.put("IDENTIFICATION_ADDRESSES_ADDITIONAL_EMAIL_ADDRESSES",
			_IDENTIFICATION_ADDRESSES_ADDITIONAL_EMAIL_ADDRESSES);
		_paths.put("IDENTIFICATION_ADDRESSES_WEBSITES",
			_IDENTIFICATION_ADDRESSES_WEBSITES);
		_paths.put("IDENTIFICATION_ADDRESSES_INSTANT_MESSENGER",
			_IDENTIFICATION_ADDRESSES_INSTANT_MESSENGER);
		_paths.put("IDENTIFICATION_ADDRESSES_SOCIAL_NETWORK",
			_IDENTIFICATION_ADDRESSES_SOCIAL_NETWORK);
		_paths.put("IDENTIFICATION_ADDRESSES_SMS", _IDENTIFICATION_ADDRESSES_SMS);
		_paths.put("IDENTIFICATION_ADDRESSES_OPENID",
			_IDENTIFICATION_ADDRESSES_OPENID);
		_paths.put("MISCELLANEOUS_ANNOUNCEMENTS", _MISCELLANEOUS_ANNOUNCEMENTS);
		_paths.put("MISCELLANEOUS_DISPLAY_SETTINGS",
			_MISCELLANEOUS_DISPLAY_SETTINGS);
		_paths.put("MISCELLANEOUS_COMMENTS", _MISCELLANEOUS_COMMENTS);
		_paths.put("MISCELLANEOUS_CUSTOM_FIELDS", _MISCELLANEOUS_CUSTOM_FIELDS);
		_paths.put("BUTTONS_SAVE", _BUTTONS_SAVE);
		_paths.put("BUTTONS_CANCEL", _BUTTONS_CANCEL);
	}
}