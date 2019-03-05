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

package com.liferay.frontend.js.loader.modules.extender.internal.resolution;

import com.liferay.portal.kernel.json.JSONFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Object to hold the results of a resolution of a list of modules.
 * @author Rodolfo Roza Miranda
 * @review
 */
public class JSModulesResolution {

	public JSModulesResolution(JSONFactory jsonFactory, boolean explain) {
		_jsonFactory = jsonFactory;

		if (explain) {
			_explanation = new ArrayList<>();
		}
	}

	public void addProcessedModuleName(String moduleName) {
		_processedModuleNames.add(moduleName);
	}

	public void addResolvedModuleName(String moduleName) {
		_resolvedModuleNames.add(moduleName);

		if (_explanation == null) {
			return;
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < _explainIndentation; i++) {
			sb.append("  ");
		}

		sb.append(moduleName);

		_explanation.add(0, sb.toString());
	}

	public void dedentExplanation() {
		_explainIndentation--;
	}

	public void indentExplanation() {
		_explainIndentation++;
	}

	public boolean isProcessedModuleName(String moduleName) {
		return _processedModuleNames.contains(moduleName);
	}

	public void putDependenciesMap(
		String moduleName, Map<String, String> dependenciesMap) {

		_dependenciesMap.put(moduleName, dependenciesMap);
	}

	public void putMappedModuleName(
		String moduleName, String mappedModuleName) {

		_mappedModuleNamesMap.put(moduleName, mappedModuleName);
	}

	public void putPath(String moduleName, String path) {
		_pathsMap.put(moduleName, path);
	}

	public String toJSON() {
		Map<String, Object> map = new HashMap<>();

		map.put("configMap", _mappedModuleNamesMap);

		if (_explanation != null) {
			map.put("explanation", _resolvedModuleNames);
		}

		map.put("moduleMap", _dependenciesMap);
		map.put("pathMap", _pathsMap);
		map.put("resolvedModules", _resolvedModuleNames);

		return _jsonFactory.looseSerializeDeep(map);
	}

	private final Map<String, Map<String, String>> _dependenciesMap =
		new HashMap<>();
	private int _explainIndentation;
	private List<String> _explanation;
	private final JSONFactory _jsonFactory;
	private final Map<String, String> _mappedModuleNamesMap = new HashMap<>();
	private final Map<String, String> _pathsMap = new HashMap<>();
	private final Set<String> _processedModuleNames = new HashSet<>();
	private final List<String> _resolvedModuleNames = new ArrayList<>();

}