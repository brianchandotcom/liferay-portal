/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletCategoryKeys {

	public static final String[] ALL = {
		PortletCategoryKeys.CONTROL_PANEL_APPS,
		PortletCategoryKeys.CONTROL_PANEL_CONFIGURATION,
		PortletCategoryKeys.CONTROL_PANEL_SITES,
		PortletCategoryKeys.CONTROL_PANEL_SYSTEM,
		PortletCategoryKeys.CONTROL_PANEL_USERS,
		PortletCategoryKeys.CONTROL_PANEL_WORKFLOW
	};

	public static final String CONTROL_PANEL_APPS = "control_panel.apps";

	public static final String CONTROL_PANEL_CONFIGURATION =
		"control_panel.configuration";

	public static final String CONTROL_PANEL_SITES = "control_panel.sites";

	public static final String CONTROL_PANEL_SYSTEM = "control_panel.system";

	public static final String CONTROL_PANEL_USERS = "control_panel.users";

	public static final String CONTROL_PANEL_WORKFLOW =
		"control_panel.workflow";

	public static final String CURRENT_SITE = "current_site";

	public static final String PORTLET = "portlet";

	public static final String SITE_ADMINISTRATION = "site_administration.";

	public static final String[] SITE_ADMINISTRATION_ALL = {
		PortletCategoryKeys.SITE_ADMINISTRATION_CONFIGURATION,
		PortletCategoryKeys.SITE_ADMINISTRATION_CONTENT,
		PortletCategoryKeys.SITE_ADMINISTRATION_MEMBERS,
		PortletCategoryKeys.SITE_ADMINISTRATION_NAVIGATION,
		PortletCategoryKeys.SITE_ADMINISTRATION_PUBLISHING
	};

	public static final String SITE_ADMINISTRATION_CONFIGURATION =
		"site_administration.configuration";

	public static final String SITE_ADMINISTRATION_CONTENT =
		"site_administration.content";

	public static final String SITE_ADMINISTRATION_MEMBERS =
		"site_administration.members";

	public static final String SITE_ADMINISTRATION_NAVIGATION =
		"site_administration.navigation";

	public static final String SITE_ADMINISTRATION_PUBLISHING =
		"site_administration.publishing";

	public static final String USER_MY_ACCOUNT = "user.my_account";

}