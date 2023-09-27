/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.portlet.constants;

import com.liferay.portal.kernel.util.Portal;

/**
 * @author Guilherme Camacho
 */
public class FriendlyURLResolverConstants {

	public static final String URL_SEPARATOR_ASSET_CATEGORY = "/v/";

	public static final String URL_SEPARATOR_BLOGS_ENTRY = "/b/";

	public static final String URL_SEPARATOR_FILE_ENTRY = "/d/";

	public static final String URL_SEPARATOR_JOURNAL_ARTICLE = "/w/";

	public static final String URL_SEPARATOR_KNOWLEDGE_BASE_ARTICLE = "/k/";

	public static final String URL_SEPARATOR_OBJECT_ENTRY = "/l/";

	public static final String URL_SEPARATOR_PORTAL_RESERVED =
		Portal.PATH_MODULE + "/";

	public static final String URL_SEPARATOR_X_ASSET_CATEGORY = "/v";

	public static final String URL_SEPARATOR_X_BLOGS_ENTRY = "/b";

	public static final String URL_SEPARATOR_X_FILE_ENTRY = "/d";

	public static final String URL_SEPARATOR_X_JOURNAL_ARTICLE = "/w";

	public static final String URL_SEPARATOR_X_OBJECT_ENTRY = "/l";

	public static final String URL_SEPARATOR_X_PORTAL_RESERVED =
		Portal.PATH_MODULE;

	public static final String URL_SEPARATOR_Y_ASSET_CATEGORY = "v";

	public static final String URL_SEPARATOR_Y_BLOGS_ENTRY = "b";

	public static final String URL_SEPARATOR_Y_FILE_ENTRY = "d";

	public static final String URL_SEPARATOR_Y_JOURNAL_ARTICLE = "w";

	public static final String URL_SEPARATOR_Y_OBJECT_ENTRY = "l";

	public static final String URL_SEPARATOR_Y_PORTAL_RESERVED =
		URL_SEPARATOR_X_PORTAL_RESERVED.substring(1);

}