/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.associated.data.web.internal.constants;

/**
 * @author Pei-Jung Lan
 */
public class UADConstants {

	public static final String ALL_APPLICATIONS = "all-applications";

	public static final String SCOPE_INSTANCE = "instance";

	public static final String SCOPE_PERSONAL_SITE = "personal-site";

	public static final String SCOPE_REGULAR_SITES = "regular-sites";

	/**
	 * Order matters here. These correspond to filter buttons presented in the
	 * UI.
	 *
	 * @review
	 */
	public static final String[] SCOPES = {
		SCOPE_PERSONAL_SITE, SCOPE_REGULAR_SITES, SCOPE_INSTANCE
	};

}