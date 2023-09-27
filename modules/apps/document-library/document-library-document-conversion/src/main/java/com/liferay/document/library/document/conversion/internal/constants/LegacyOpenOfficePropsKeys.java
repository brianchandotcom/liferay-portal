/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.document.conversion.internal.constants;

/**
 * @author Pei-Jung Lan
 */
public class LegacyOpenOfficePropsKeys {

	public static final String OPENOFFICE_CACHE_ENABLED =
		"openoffice.cache.enabled";

	public static final String[] OPENOFFICE_KEYS = {
		OPENOFFICE_CACHE_ENABLED,
		LegacyOpenOfficePropsKeys.OPENOFFICE_SERVER_ENABLED,
		LegacyOpenOfficePropsKeys.OPENOFFICE_SERVER_HOST,
		LegacyOpenOfficePropsKeys.OPENOFFICE_SERVER_PORT
	};

	public static final String OPENOFFICE_SERVER_ENABLED =
		"openoffice.server.enabled";

	public static final String OPENOFFICE_SERVER_HOST =
		"openoffice.server.host";

	public static final String OPENOFFICE_SERVER_PORT =
		"openoffice.server.port";

}