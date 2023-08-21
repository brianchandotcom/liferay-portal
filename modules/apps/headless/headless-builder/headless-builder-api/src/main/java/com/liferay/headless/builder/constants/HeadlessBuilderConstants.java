/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.constants;

import com.liferay.portal.kernel.util.Portal;

/**
 * @author Sergio Jiménez del Coso
 */
public class HeadlessBuilderConstants {

	public static final String BASE_PATH =
		Portal.PATH_MODULE + HeadlessBuilderConstants.BASE_PATH_SUFFIX;

	public static final String BASE_PATH_SCOPES_SUFFIX = "/scopes/{scopeKey}";

	public static final String BASE_PATH_SUFFIX = "/c/";

}