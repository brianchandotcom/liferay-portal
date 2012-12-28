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

package com.liferay.portalweb.blocks.portal.usersandorganizations.controlpanel.actions.edituser;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPUsersAndOrganizationsEditUserPersonalSitePaths {
	public static Map<String, String> getPaths() {
		return _paths;
	}

	private static Map<String, String> _paths = new HashMap<String, String>();

	static {
		_paths.put("PAGE_NAME", "");
		_paths.put("BREADCRUMB_1", "//nav[@id='breadcrumbs']/ul/li[1]/span/a");
		_paths.put("BREADCRUMB_2", "//nav[@id='breadcrumbs']/ul/li[2]/span");
		_paths.put("BREADCRUMB_3", "//nav[@id='breadcrumbs']/ul/li[3]/span/a");
		_paths.put("BREADCRUMB_4", "//nav[@id='breadcrumbs']/ul/li[4]/span");
		_paths.put("BREADCRUMB_5", "//nav[@id='breadcrumbs']/ul/li[5]/span/a");
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
		_paths.put("PERSONAL_SITE_PUBLIC_PAGES",
			"//select[@id='_125_publicLayoutSetPrototypeId']");
		_paths.put("PERSONAL_SITE_PUBLIC_PAGES_ENABLE_PROPAGATION",
			"//input[@id='_125_publicLayoutSetPrototypeLinkEnabledCheckbox']");
		_paths.put("PERSONAL_SITE_PRIVATE_PAGES",
			"//select[@id='_125_privateLayoutSetPrototypeId']");
		_paths.put("PERSONAL_SITE_PRIVATE_PAGES_ENABLE_PROPAGATION",
			"//input[@id='_125_privateLayoutSetPrototypeLinkEnabledCheckbox']");
		_paths.put("USER_INFORMATION_DETAILS", "//a[@id='_125_detailsLink']");
		_paths.put("USER_INFORMATION_PASSWORD", "//a[@id='_125_passwordLink']");
		_paths.put("USER_INFORMATION_ORGANIZATIONS",
			"//a[@id='_125_organizationsLink']");
		_paths.put("USER_INFORMATION__SITES", "//a[@id='_125_sitesLink']");
		_paths.put("USER_INFORMATION__USER_GROUPS",
			"//a[@id='_125_userGroupsLink']");
		_paths.put("USER_INFORMATION_ROLES", "//a[@id='_125_rolesLink']");
		_paths.put("USER_INFORMATION_PERSONAL_SITE",
			"//a[@id='_125_personalSiteLink']");
		_paths.put("USER_INFORMATION_CATEGORIZATION",
			"//a[@id='_125_categorizationLink']");
		_paths.put("IDENTIFICATION_ADDRESSES", "//a[@id='_125_addressesLink']");
		_paths.put("IDENTIFICATION_ADDRESSES_PHONE_NUMBERS",
			"//a[@id='_125_phoneNumbersLink']");
		_paths.put("IDENTIFICATION_ADDRESSES_ADDITIONAL_EMAIL_ADDRESSES",
			"//a[@id='_125_additionalEmailAddressesLink']");
		_paths.put("IDENTIFICATION_ADDRESSES_WEBSITES",
			"//a[@id='_125_websitesLink']");
		_paths.put("IDENTIFICATION_ADDRESSES_INSTANT_MESSENGER",
			"//a[@id='_125_instantMessengerLink']");
		_paths.put("IDENTIFICATION_ADDRESSES_SOCIAL_NETWORK",
			"//a[@id='_125_socialNetworkLink']");
		_paths.put("IDENTIFICATION_ADDRESSES_SMS", "//a[@id='_125_smsLink']");
		_paths.put("IDENTIFICATION_ADDRESSES_OPENID",
			"//a[@id='_125_openIdLink']");
		_paths.put("MISCELLANEOUS_ANNOUNCEMENTS",
			"//a[@id='_125_announcementsLink']");
		_paths.put("MISCELLANEOUS_DISPLAY_SETTINGS",
			"//a[@id='_125_displaySettingsLink']");
		_paths.put("MISCELLANEOUS_COMMENTS", "//a[@id='_125_commentsLink']");
		_paths.put("MISCELLANEOUS_CUSTOM_FIELDS",
			"//a[@id='_125_customFieldsLink']");
		_paths.put("BUTTONS_SAVE", "//input[@value='Save']");
		_paths.put("BUTTONS_CANCEL", "//input[@value='Cancel']");
	}
}