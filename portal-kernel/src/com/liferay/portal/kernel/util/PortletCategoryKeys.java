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
		PortletCategoryKeys.CONTROL_PANEL_USERS
	};

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #CONTROL_PANEL_APPS}
	 */
	@Deprecated
	public static final String APPS = "apps";

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #CONTROL_PANEL_CONFIGURATION}
	 */
	@Deprecated
	public static final String CONFIGURATION = "control_panel.configuration";

	public static final String CONTROL_PANEL_APPS = "control_panel.apps";

	public static final String CONTROL_PANEL_CONFIGURATION =
		"control_panel.configuration";

	public static final String CONTROL_PANEL_SITES = "control_panel.sites";

	public static final String CONTROL_PANEL_SYSTEM = "control_panel.system";

	public static final String CONTROL_PANEL_USERS = "control_panel.users";

	public static final String CURRENT_SITE = "current_site";

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #USER_MY_ACCOUNT}
	 */
	@Deprecated
	public static final String MY = "user";

	public static final String PORTLET = "portlet";

	public static final String SITE_ADMINISTRATION = "site_administration.";

	public static final String[] SITE_ADMINISTRATION_ALL = {
		PortletCategoryKeys.SITE_ADMINISTRATION_CONFIGURATION,
		PortletCategoryKeys.SITE_ADMINISTRATION_CONTENT,
		PortletCategoryKeys.SITE_ADMINISTRATION_MEMBERS,
		PortletCategoryKeys.SITE_ADMINISTRATION_NAVIGATION,
		PortletCategoryKeys.SITE_ADMINISTRATION_PAGES,
		PortletCategoryKeys.SITE_ADMINISTRATION_PUBLISHING,
		PortletCategoryKeys.SITE_ADMINISTRATION_USERS
	};

	public static final String SITE_ADMINISTRATION_CONFIGURATION =
		"site_administration.configuration";

	public static final String SITE_ADMINISTRATION_CONTENT =
		"site_administration.content";

	public static final String SITE_ADMINISTRATION_MEMBERS =
		"site_administration.members";

	public static final String SITE_ADMINISTRATION_NAVIGATION =
		"site_administration.navigation";

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #SITE_ADMINISTRATION_NAVIGATION}
	 */
	@Deprecated
	public static final String SITE_ADMINISTRATION_PAGES =
		"site_administration.pages";

	public static final String SITE_ADMINISTRATION_PUBLISHING =
		"site_administration.publishing";

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #SITE_ADMINISTRATION_MEMBERS}
	 */
	@Deprecated
	public static final String SITE_ADMINISTRATION_USERS =
		"site_administration.users";

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #CONTROL_PANEL_SITES}
	 */
	@Deprecated
	public static final String SITES = "sites";

	public static final String USER_MY_ACCOUNT = "user.my_account";

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #CONTROL_PANEL_USERS}
	 */
	@Deprecated
	public static final String USERS = "users";

}