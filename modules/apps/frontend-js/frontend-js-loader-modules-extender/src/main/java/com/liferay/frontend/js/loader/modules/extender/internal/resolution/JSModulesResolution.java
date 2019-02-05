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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Object to hold the results of a resolution of a list of modules.
 * @author Rodolfo Roza Miranda
 * @review
 */
public class JSModulesResolution {

	public JSModulesResolution(boolean explainResolutions) {
		_explainResolutions = explainResolutions;
	}

	public void addProcessedModule(String moduleName) {
		_processedModules.add(moduleName);
	}

	public void addResolvedModule(String moduleName) {
		_resolvedModules.add(moduleName);

		if (!_explainResolutions) {
			return;
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < _explanationIndentation; i++) {
			sb.append("  ");
		}

		sb.append(moduleName);

		_explanation.add(0, sb.toString());
	}

	public void dedentExplanation() {
		_explanationIndentation--;
	}

	public Map<String, String> getConfigMap() {
		return _configMap;
	}

	public Collection<String> getExplanation() {
		return _explanation;
	}

	public Map<String, Map<String, String>> getModuleMap() {
		return _moduleMap;
	}

	public Map<String, String> getPathMap() {
		return _pathMap;
	}

	public List<String> getResolvedModules() {
		return _resolvedModules;
	}

	public void indentExplanation() {
		_explanationIndentation++;
	}

	public boolean isProcessedModule(String moduleName) {
		return _processedModules.contains(moduleName);
	}

	public void putGlobalMapping(String moduleName, String mappedModuleName) {
		_configMap.put(moduleName, mappedModuleName);
	}

	public void putModuleMapping(
		String moduleName, Map<String, String> dependenciesMap) {

		_moduleMap.put(moduleName, dependenciesMap);
	}

	public void putPath(String moduleName, String path) {
		_pathMap.put(moduleName, path);
	}

	private final Map<String, String> _configMap = new ConcurrentHashMap<>();
	private final boolean _explainResolutions;
	private List<String> _explanation = new ArrayList<>();
	private int _explanationIndentation;
	private final Map<String, Map<String, String>> _moduleMap =
		new ConcurrentHashMap<>();
	private final Map<String, String> _pathMap = new ConcurrentHashMap<>();
	private final Set<String> _processedModules = new ConcurrentSkipListSet<>();
	private final List<String> _resolvedModules = new ArrayList<>();

}