/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.application.endpoint;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.petra.string.StringPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alejandro Tardín
 */
public class EndpointMatcher {

	public EndpointMatcher(List<APIApplication.Endpoint> endpoints) {
		_endpoints = endpoints;

		for (APIApplication.Endpoint endpoint : endpoints) {
			if (Objects.equals(
					endpoint.getRetrieveType(),
					APIApplication.Endpoint.RetrieveType.SINGLE_ELEMENT)) {

				Pattern pattern = Pattern.compile(
					_getSingleElementRegex(endpoint));

				_predicates.put(
					endpoint,
					path -> {
						Matcher matcher = pattern.matcher(path);

						return matcher.matches();
					});
			}
			else {
				_predicates.put(
					endpoint, path -> Objects.equals(path, endpoint.getPath()));
			}
		}
	}

	public APIApplication.Endpoint getEndpoint(
		String path, APIApplication.Endpoint.Scope scope) {

		for (APIApplication.Endpoint endpoint : _endpoints) {
			if (scope != endpoint.getScope()) {
				continue;
			}

			Predicate<String> predicate = _predicates.get(endpoint);

			if (predicate.test(path)) {
				return endpoint;
			}
		}

		return null;
	}

	private String _getSingleElementRegex(APIApplication.Endpoint endpoint) {
		String endpointPath = endpoint.getPath();

		endpointPath = endpointPath.substring(0, endpointPath.lastIndexOf("/"));

		endpointPath = endpointPath + StringPool.FORWARD_SLASH;

		if (Objects.equals(
				endpoint.getPathParameter(),
				APIApplication.Endpoint.PathParameter.ID)) {

			return endpointPath + "\\d+";
		}

		return endpointPath + "\\S+";
	}

	private final List<APIApplication.Endpoint> _endpoints;
	private final Map<APIApplication.Endpoint, Predicate<String>> _predicates =
		new HashMap<>();

}