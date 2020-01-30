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

package com.liferay.portal.tools.bundle.support.constants;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Arrays;
import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class BundleSupportConstants {

	public static final String DEFAULT_BUNDLE_CACHE_DIR_NAME =
		".liferay/bundles";

	public static final String DEFAULT_BUNDLE_FORMAT = "tar.gz";

	public static final String DEFAULT_BUNDLE_URL =
		"https://releases-cdn.liferay.com/portal/7.3.0-ga1" +
			"/liferay-ce-portal-tomcat-7.3.0-ga1-20200127150653953.tar.gz";

	public static final URL DEFAULT_BUNDLE_URL_OBJECT;

	public static final String DEFAULT_CONFIGS_DIR_NAME = "configs";

	public static final String DEFAULT_DEV_OPS_CONFIG_DIR_NAME =
		"devops/liferay/config";

	public static final String DEFAULT_ENVIRONMENT = "local";

	public static final boolean DEFAULT_INCLUDE_FOLDER = true;

	public static final String DEFAULT_LCP_CONFIG_DIR_NAME =
		"lcp/liferay/config";

	public static final String DEFAULT_LIFERAY_HOME_DIR_NAME = "bundles";

	public static final int DEFAULT_STRIP_COMPONENTS = 1;

	public static final File DEFAULT_TOKEN_FILE;

	public static final String DEFAULT_TOKEN_FILE_NAME = ".liferay/token";

	public static final String DEFAULT_TOKEN_URL =
		"https://web.liferay.com/token-auth-portlet/api/secure/jsonws" +
			"/tokenauthentry/add-token-auth-entry";

	public static final List<File> defaultConfigsDirs = Arrays.asList(
		new File(DEFAULT_CONFIGS_DIR_NAME),
		new File(DEFAULT_LCP_CONFIG_DIR_NAME),
		new File(DEFAULT_DEV_OPS_CONFIG_DIR_NAME));

	static {
		try {
			DEFAULT_BUNDLE_URL_OBJECT = new URL(DEFAULT_BUNDLE_URL);
		}
		catch (MalformedURLException malformedURLException) {
			throw new ExceptionInInitializerError(malformedURLException);
		}

		DEFAULT_TOKEN_FILE = new File(
			System.getProperty("user.home"), DEFAULT_TOKEN_FILE_NAME);
	}

}