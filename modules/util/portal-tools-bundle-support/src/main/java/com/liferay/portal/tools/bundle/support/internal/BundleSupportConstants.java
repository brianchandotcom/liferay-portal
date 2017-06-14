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

package com.liferay.portal.tools.bundle.support.internal;

import java.io.File;

/**
 * @author Andrea Di Giorgi
 */
public class BundleSupportConstants {

	public static final String DEFAULT_BUNDLE_CACHE_DIR_NAME =
		".liferay/bundles";

	public static final String DEFAULT_BUNDLE_FORMAT = "zip";

	public static final String DEFAULT_BUNDLE_URL =
		"https://cdn.lfrs.sl/releases.liferay.com/portal/7.0.2-ga3" +
			"/liferay-ce-portal-tomcat-7.0-ga3-20160804222206210.zip";

	public static final String DEFAULT_ENVIRONMENT = "local";

	public static final boolean DEFAULT_INCLUDE_FOLDER = true;

	public static final int DEFAULT_STRIP_COMPONENTS = 1;

	public static final File DEFAULT_TOKEN_FILE;

	public static final String DEFAULT_TOKEN_FILE_NAME = ".liferay/token";

	public static final String DEFAULT_TOKEN_URL =
		"https://web.liferay.com/token-auth-portlet/api/secure/jsonws" +
			"/tokenauthentry/add-token-auth-entry";

	static {
		DEFAULT_TOKEN_FILE = new File(
			System.getProperty("user.home"), DEFAULT_TOKEN_FILE_NAME);
	}

}