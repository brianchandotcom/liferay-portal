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

package com.liferay.document.library.document.conversion.constants;

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