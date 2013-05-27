/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletCategoryKeys {

	public static final String[] ALL = {
		PortletCategoryKeys.USERS, PortletCategoryKeys.SITES,
		PortletCategoryKeys.APPS, PortletCategoryKeys.CONFIGURATION
	};

	public static final String APPS = "apps";

	public static final String CONFIGURATION = "configuration";

	/**
	 * @deprecated As of 6.2.0
	 */
	public static final String CONTENT = "content";

	public static final String CURRENT_SITE = "current_site";

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #APPS}
	 */
	public static final String MARKETPLACE = "marketplace";

	public static final String MY = "my";

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #CONFIGURATION}, {@link
	 *             #SITES}, or {@link #USERS}.
	 */
	public static final String PORTAL = CONFIGURATION;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #APPS}
	 */
	public static final String SERVER = APPS;

	public static final String SITE_ADMINISTRATION = "site_administration.";

	public static final String[] SITE_ADMINISTRATION_ALL = {
		PortletCategoryKeys.SITE_ADMINISTRATION_MAIN,
		PortletCategoryKeys.SITE_ADMINISTRATION_CONTENT,
		PortletCategoryKeys.SITE_ADMINISTRATION_CONFIGURATION
	};

	public static final String SITE_ADMINISTRATION_CONFIGURATION =
		"site_administration.configuration";

	public static final String SITE_ADMINISTRATION_CONTENT =
		"site_administration.content";

	public static final String SITE_ADMINISTRATION_MAIN =
		"site_administration.main";

	public static final String SITES = "sites";

	public static final String USERS = "users";

}