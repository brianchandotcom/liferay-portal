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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Rodolfo Roza Miranda
 */
public class JSModuleNameMapperCache {

	public JSModuleNameMapperCache(
		Map<String, String> exactMatchMap,
		Map<String, String> partialMatchMap) {

		_exactMatchMap = exactMatchMap;
		_partialMatchMap = partialMatchMap;

		_lastModified = System.currentTimeMillis();
	}

	public String get(String moduleName, Map<String, String> dependenciesMap) {
		return _cache.get(_buildKey(moduleName, dependenciesMap));
	}

	public Map<String, String> getExactMatchMap() {
		return _exactMatchMap;
	}

	public Map<String, String> getPartialMatchMap() {
		return _partialMatchMap;
	}

	public boolean isOlderThan(long lastModified) {
		if (_lastModified < lastModified) {
			return true;
		}

		return false;
	}

	public void put(
		String moduleName, Map<String, String> dependenciesMap,
		String mappedModuleName) {

		_cache.put(_buildKey(moduleName, dependenciesMap), mappedModuleName);
	}

	private String _buildKey(
		String moduleName, Map<String, String> dependenciesMap) {

		if (dependenciesMap == null) {
			return moduleName;
		}

		StringBundler sb = new StringBundler();

		sb.append(moduleName);

		for (Map.Entry<String, String> entry : dependenciesMap.entrySet()) {
			sb.append(StringPool.PIPE);
			sb.append(entry.getKey());
			sb.append(StringPool.PIPE);
			sb.append(entry.getValue());
		}

		return sb.toString();
	}

	private final Map<String, String> _cache = new ConcurrentHashMap<>();
	private final Map<String, String> _exactMatchMap;
	private final long _lastModified;
	private final Map<String, String> _partialMatchMap;

}