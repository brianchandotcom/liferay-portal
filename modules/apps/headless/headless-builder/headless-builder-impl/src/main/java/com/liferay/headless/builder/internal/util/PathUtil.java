/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.util;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.constants.HeadlessBuilderConstants;
import com.liferay.petra.string.StringPool;

/**
 * @author Luis Miguel Barcos
 */
public class PathUtil {

	public static String getPathPrefix(APIApplication.Endpoint.Scope scope) {
		if (scope == APIApplication.Endpoint.Scope.GROUP) {
			return HeadlessBuilderConstants.BASE_PATH_SCOPES_SUFFIX;
		}

		return StringPool.BLANK;
	}

}