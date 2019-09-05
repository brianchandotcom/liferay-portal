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

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Hugo Huijser
 */
public class JSONPackageJSONDependencyVersionCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!absolutePath.endsWith("/package.json") ||
			(!absolutePath.contains("/modules/apps/") &&
			 !absolutePath.contains("/modules/private/apps/"))) {

			return content;
		}

		return _fixDependencyVersions(absolutePath, content);
	}

	private String _fixDependencyVersions(String absolutePath, String content)
		throws IOException {

		JSONObject jsonObject = new JSONObject(content);

		if (jsonObject.isNull("dependencies")) {
			return content;
		}

		Map<String, String> expectedDependencyVersionsMap =
			_getExpectedDependencyVersionsMap(absolutePath);

		JSONObject dependenciesJSONObject = jsonObject.getJSONObject(
			"dependencies");

		Iterator<String> keys = dependenciesJSONObject.keys();

		while (keys.hasNext()) {
			String dependencyName = keys.next();

			String actualVersion = dependenciesJSONObject.getString(
				dependencyName);
			String expectedVersion = expectedDependencyVersionsMap.get(
				dependencyName);

			if ((expectedVersion != null) &&
				!expectedVersion.equals(actualVersion)) {

				content = StringUtil.replace(
					content,
					StringBundler.concat(
						"\"", dependencyName, "\": \"", actualVersion, "\""),
					StringBundler.concat(
						"\"", dependencyName, "\": \"", expectedVersion, "\""));
			}
		}

		return content;
	}

	private Map<String, String> _getDependencyVersionsMap(
			String fileName, String absolutePath, String regex)
		throws IOException {

		Map<String, String> dependencyVersionsMap = new HashMap<>();

		String content = getPortalContent(fileName, absolutePath);

		if (Validator.isNull(content)) {
			return dependencyVersionsMap;
		}

		JSONObject jsonObject = new JSONObject(content);

		JSONObject dependenciesJSONObject = jsonObject.getJSONObject(
			"dependencies");

		Iterator<String> keys = dependenciesJSONObject.keys();

		while (keys.hasNext()) {
			String dependencyName = keys.next();

			if (dependencyName.matches(regex)) {
				dependencyVersionsMap.put(
					dependencyName,
					dependenciesJSONObject.getString(dependencyName));
			}
		}

		return dependencyVersionsMap;
	}

	private synchronized Map<String, String> _getExpectedDependencyVersionsMap(
			String absolutePath)
		throws IOException {

		if (_expectedDependencyVersionsMap != null) {
			return _expectedDependencyVersionsMap;
		}

		_expectedDependencyVersionsMap = new HashMap<>();

		_expectedDependencyVersionsMap.putAll(
			_getDependencyVersionsMap(
				"modules/apps/frontend-js/frontend-js-metal-web/package.json",
				absolutePath, "metal(-.*)?"));
		_expectedDependencyVersionsMap.putAll(
			_getDependencyVersionsMap(
				"modules/apps/frontend-js/frontend-js-react-web/package.json",
				absolutePath, ".*"));
		_expectedDependencyVersionsMap.putAll(
			_getDependencyVersionsMap(
				"modules/apps/frontend-js/frontend-js-spa-web/package.json",
				absolutePath, "senna"));
		_expectedDependencyVersionsMap.putAll(
			_getDependencyVersionsMap(
				"modules/apps/frontend-taglib/frontend-taglib-clay" +
					"/package.json",
				absolutePath, "clay-.*"));
		_expectedDependencyVersionsMap.putAll(
			_getDependencyVersionsMap(
				"modules/apps/frontend-taglib/frontend-taglib-clay" +
					"/package.json",
				absolutePath, "@clayui/.*"));

		String content = getModulesPropertiesContent(absolutePath);

		if (Validator.isNull(content)) {
			return _expectedDependencyVersionsMap;
		}

		List<String> lines = ListUtil.fromString(content);

		for (String line : lines) {
			String[] array = StringUtil.split(line, StringPool.EQUAL);

			if (array.length != 2) {
				continue;
			}

			String key = array[0];

			if (key.startsWith("bundle.symbolic.name[")) {
				_expectedDependencyVersionsMap.put(
					key.substring(21, key.length() - 1), StringPool.STAR);
			}
		}

		return _expectedDependencyVersionsMap;
	}

	private Map<String, String> _expectedDependencyVersionsMap;

}