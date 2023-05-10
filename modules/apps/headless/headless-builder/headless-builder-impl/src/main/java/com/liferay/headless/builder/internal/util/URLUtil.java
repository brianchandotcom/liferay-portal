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

package com.liferay.headless.builder.internal.util;

import com.liferay.headless.builder.internal.constants.HeadlessBuilderConstants;
import com.liferay.headless.builder.internal.operation.Operation;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Carlos Correa
 */
public class URLUtil {

	public static Operation.PathConfiguration getPathConfiguration(
		String path, String version) {

		final String finalPath;

		if (Validator.isBlank(version)) {
			finalPath =
				Portal.PATH_MODULE + HeadlessBuilderConstants.BASE_PATH + path;
		}
		else {
			finalPath = StringBundler.concat(
				Portal.PATH_MODULE, HeadlessBuilderConstants.BASE_PATH,
				StringPool.SLASH, version, path);
		}

		return new Operation.PathConfiguration() {

			@Override
			public String getPath() {
				return finalPath;
			}

			@Override
			public List<String> getPathParameterNames() {
				return URLUtil.getPathParameterNames(finalPath, getPattern());
			}

			@Override
			public Pattern getPattern() {
				return URLUtil.getPattern(finalPath);
			}

		};
	}

	public static List<String> getPathParameterNames(
		String path, Pattern pattern) {

		List<String> parameterNames = new ArrayList<>();

		Matcher matcher = pattern.matcher(path);

		while (matcher.find()) {
			for (int i = 1; i <= matcher.groupCount(); i++) {
				String value = matcher.group(i);

				parameterNames.add(value.substring(1, value.length() - 1));
			}
		}

		return parameterNames;
	}

	public static Map<String, String> getPathParameters(
		String path, Operation.PathConfiguration pathConfiguration) {

		List<String> pathParameterNames =
			pathConfiguration.getPathParameterNames();
		List<String> pathParameterValues = _getPathParameterValues(
			path, pathConfiguration.getPattern());

		if (pathParameterNames.size() != pathParameterValues.size()) {
			throw new IllegalStateException();
		}

		Map<String, String> pathParameters = new HashMap<>();

		for (int i = 0; i < pathParameterNames.size(); i++) {
			pathParameters.put(
				pathParameterNames.get(i), pathParameterValues.get(i));
		}

		return pathParameters;
	}

	public static Pattern getPattern(String path) {
		String pathRegex = path.replaceAll("\\{.+\\}", "(.+)");

		return Pattern.compile(pathRegex);
	}

	private static List<String> _getPathParameterValues(
		String path, Pattern pathPattern) {

		List<String> pathParameterValues = new ArrayList<>();

		Matcher matcher = pathPattern.matcher(path);

		while (matcher.find()) {
			for (int i = 1; i <= matcher.groupCount(); i++) {
				pathParameterValues.add(matcher.group(i));
			}
		}

		return pathParameterValues;
	}

}