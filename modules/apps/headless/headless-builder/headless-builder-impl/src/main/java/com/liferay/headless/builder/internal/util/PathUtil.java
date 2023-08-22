/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.util;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.constants.HeadlessBuilderConstants;
import com.liferay.petra.string.StringPool;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Luis Miguel Barcos
 */
public class PathUtil {

	public static String getPathParameterValue(String path) {
		return path.substring(path.lastIndexOf('/') + 1);
	}

	public static String getPathPrefix(APIApplication.Endpoint.Scope scope) {
		if (scope == APIApplication.Endpoint.Scope.GROUP) {
			return HeadlessBuilderConstants.BASE_PATH_SCOPES_SUFFIX;
		}

		return StringPool.BLANK;
	}

	public static boolean matchEndpoint(
		APIApplication.Endpoint endpoint, String path) {

		String endpointPath = endpoint.getPath();

		if (Objects.equals(path, endpointPath)) {
			return true;
		}

		Pattern endpointPattern = null;

		endpointPath = endpointPath.substring(0, endpointPath.lastIndexOf("/"));

		endpointPath = endpointPath + StringPool.FORWARD_SLASH;

		if (Objects.equals(
				endpoint.getRetrieveType(),
				APIApplication.Endpoint.RetrieveType.SINGLE_ELEMENT) &&
			Objects.equals(
				endpoint.getPathParameter(),
				APIApplication.Endpoint.PathParameter.ID)) {

			endpointPattern = Pattern.compile(endpointPath + "\\d+");
		}
		else {
			endpointPattern = Pattern.compile(endpointPath + "\\D+");
		}

		Matcher matcher = endpointPattern.matcher(path);

		return matcher.matches();
	}

}