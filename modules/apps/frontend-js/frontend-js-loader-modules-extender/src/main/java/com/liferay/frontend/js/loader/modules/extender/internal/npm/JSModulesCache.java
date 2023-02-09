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

package com.liferay.frontend.js.loader.modules.extender.internal.npm;

import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Iván Zaera
 */
public class JSModulesCache {

	public JSModulesCache(
		Map<String, String> exactMatchMap, Map<String, String> globalAliases,
		Map<String, JSModule> jsModules, Map<String, JSPackage> jsPackages,
		List<JSPackageVersion> jsPackageVersions,
		Map<String, String> partialMatchMap,
		Map<String, JSModule> resolvedJSModules,
		Map<String, JSPackage> resolvedJSPackages) {

		_exactMatchMap = exactMatchMap;
		_globalAliases = globalAliases;
		_jsModules = jsModules;
		_jsPackages = jsPackages;
		_jsPackageVersions = jsPackageVersions;
		_partialMatchMap = partialMatchMap;
		_resolvedJSModules = resolvedJSModules;
		_resolvedJSPackages = resolvedJSPackages;
	}

	public ConcurrentHashMap<String, JSPackage>
		getCachedDependencyJSPackages() {

		return _cachedDependencyJSPackages;
	}

	public Map<String, String> getExactMatchMap() {
		return _exactMatchMap;
	}

	public Map<String, String> getGlobalAliases() {
		return _globalAliases;
	}

	public Map<String, JSModule> getJSModules() {
		return _jsModules;
	}

	public Map<String, JSPackage> getJSPackages() {
		return _jsPackages;
	}

	public List<JSPackageVersion> getJSPackageVersions() {
		return _jsPackageVersions;
	}

	public Map<String, String> getPartialMatchMap() {
		return _partialMatchMap;
	}

	public String getResolutionStateDigest() {
		if (_resolutionStateDigest == null) {
			_resolutionStateDigest =
				NPMRegistryResolutionStateDigestUtil.digest(
					_resolvedJSModules.values(), _resolvedJSPackages.values());
		}

		return _resolutionStateDigest;
	}

	public Map<String, JSModule> getResolvedJSModules() {
		return _resolvedJSModules;
	}

	public Map<String, JSPackage> getResolvedJSPackages() {
		return _resolvedJSPackages;
	}

	private final ConcurrentHashMap<String, JSPackage>
		_cachedDependencyJSPackages = new ConcurrentHashMap<>();
	private final Map<String, String> _exactMatchMap;
	private final Map<String, String> _globalAliases;
	private final Map<String, JSModule> _jsModules;
	private final Map<String, JSPackage> _jsPackages;
	private final List<JSPackageVersion> _jsPackageVersions;
	private final Map<String, String> _partialMatchMap;
	private volatile String _resolutionStateDigest;
	private final Map<String, JSModule> _resolvedJSModules;
	private final Map<String, JSPackage> _resolvedJSPackages;

}