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

package com.liferay.application.list.constants;

import com.liferay.portal.kernel.util.PortletCategoryKeys;

/**
 * @author Adolfo Pérez
 */
public interface PanelCategoryKeys {

	public static final String CONTROL_PANEL = "control_panel";

	public static final String CONTROL_PANEL_APPS =
		PortletCategoryKeys.CONTROL_PANEL_APPS;

	public static final String CONTROL_PANEL_CONFIGURATION =
		PortletCategoryKeys.CONTROL_PANEL_CONFIGURATION;

	public static final String CONTROL_PANEL_SITES =
		PortletCategoryKeys.CONTROL_PANEL_SITES;

	public static final String CONTROL_PANEL_SYSTEM =
		PortletCategoryKeys.CONTROL_PANEL_SYSTEM;

	public static final String CONTROL_PANEL_USERS =
		PortletCategoryKeys.CONTROL_PANEL_USERS;

	public static final String CONTROL_PANEL_WORKFLOW =
		PortletCategoryKeys.CONTROL_PANEL_WORKFLOW;

	public static final String HIDDEN = "hidden";

	public static final String ROOT = "root";

	public static final String SITE_ADMINISTRATION = "site_administration";

	public static final String SITE_ADMINISTRATION_BUILD =
		"site_administration.build";

	public static final String SITE_ADMINISTRATION_CATEGORIZATION =
		"site_administration.categorization";

	public static final String SITE_ADMINISTRATION_CONFIGURATION =
		PortletCategoryKeys.SITE_ADMINISTRATION_CONFIGURATION;

	public static final String SITE_ADMINISTRATION_CONTENT =
		PortletCategoryKeys.SITE_ADMINISTRATION_CONTENT;

	public static final String SITE_ADMINISTRATION_MEMBERS =
		PortletCategoryKeys.SITE_ADMINISTRATION_MEMBERS;

	public static final String SITE_ADMINISTRATION_NAVIGATION =
		PortletCategoryKeys.SITE_ADMINISTRATION_NAVIGATION;

	public static final String SITE_ADMINISTRATION_PUBLISHING =
		PortletCategoryKeys.SITE_ADMINISTRATION_PUBLISHING;

	public static final String SITE_ADMINISTRATION_RECYCLE_BIN =
		"site_administration.recycle_bin";

	public static final String SITES_ADMINISTRATION_MY_SITES =
		"site_administration.my_sites";

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public static final String USER = "user";

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public static final String USER_MY_ACCOUNT =
		PortletCategoryKeys.USER_MY_ACCOUNT;

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public static final String USER_SIGN_OUT = "user.sign_out";

}