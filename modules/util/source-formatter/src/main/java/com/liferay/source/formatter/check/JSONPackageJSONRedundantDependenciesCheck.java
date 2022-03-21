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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

/**
 * @author Alan Huang
 */
public class JSONPackageJSONRedundantDependenciesCheck extends BaseFileCheck {

	@Override
	public boolean isModuleSourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!absolutePath.endsWith("/package.json")) {
			return content;
		}

		_getInternalDependenciesNames(absolutePath);

		if (_internalDependenciesNames.isEmpty()) {
			return content;
		}

		for (String excludedDirName : _excludedDirNames) {
			if (absolutePath.contains(excludedDirName)) {
				return content;
			}
		}

		JSONObject jsonObject = new JSONObject(content);

		if (jsonObject.isNull("dependencies")) {
			return content;
		}

		JSONObject dependenciesJSONObject = jsonObject.getJSONObject(
			"dependencies");

		Iterator<String> iterator = dependenciesJSONObject.keys();

		while (iterator.hasNext()) {
			String dependencyName = iterator.next();

			if (_internalDependenciesNames.contains(dependencyName)) {
				content = content.replaceFirst(
					"\"" + dependencyName + "\": \".*\",?", "");
			}
		}

		return content;
	}

	private synchronized List<String> _getInternalDependenciesNames(
			String absolutePath)
		throws IOException {

		if (_internalDependenciesNames != null) {
			return _internalDependenciesNames;
		}

		_internalDependenciesNames = new ArrayList<>();

		String content = getPortalContent(
			"modules/npmscripts.config.js", absolutePath);

		if (Validator.isNull(content)) {
			return _internalDependenciesNames;
		}

		int x = content.indexOf("imports: {");

		if (x == -1) {
			return _internalDependenciesNames;
		}

		x = x + 9;

		int y = x;

		String imports = null;

		while (true) {
			y = content.indexOf("}", y + 1);

			if (y == -1) {
				return _internalDependenciesNames;
			}

			imports = content.substring(x, y);

			int level = getLevel(imports, "{", "}");

			if (level == 0) {
				break;
			}
		}

		_excludedDirNames = getAttributeValues(
			_EXCLUDED_DIR_NAMES_KEY, absolutePath);

		JSONObject jsonObject = new JSONObject(imports);

		Iterator<String> iterator = jsonObject.keys();

		while (iterator.hasNext()) {
			String dependencyName = iterator.next();

			_excludedDirNames.add(
				StringUtil.replaceFirst(
					dependencyName, "@liferay/", StringPool.BLANK));

			_internalDependenciesNames.add(dependencyName);

			JSONObject nestedJSONObject = jsonObject.getJSONObject(
				dependencyName);

			for (String key : nestedJSONObject.keySet()) {
				_internalDependenciesNames.add(key);
			}
		}

		return _internalDependenciesNames;
	}

	private static final String _EXCLUDED_DIR_NAMES_KEY = "excludedDirNames";

	private List<String> _excludedDirNames;
	private List<String> _internalDependenciesNames;

}