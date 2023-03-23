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

import com.liferay.frontend.js.loader.modules.extender.internal.npm.dynamic.DynamicJSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSModuleAlias;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackageDependency;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.patcher.PatcherUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
			_resolutionStateDigest = _computeResolutionStateDigest();
		}

		return _resolutionStateDigest;
	}

	public Map<String, JSModule> getResolvedJSModules() {
		return _resolvedJSModules;
	}

	public Map<String, JSPackage> getResolvedJSPackages() {
		return _resolvedJSPackages;
	}

	private String _computeResolutionStateDigest() {
		MessageDigest messageDigest;

		try {
			messageDigest = MessageDigest.getInstance("SHA-1");
		}
		catch (NoSuchAlgorithmException noSuchAlgorithmException) {
			throw new RuntimeException(noSuchAlgorithmException);
		}

		// Hash dynamic JS modules that do not honor immutability of packages

		List<DynamicJSModule> dynamicJSModules = new ArrayList<>();

		for (JSModule jsModule : _resolvedJSModules.values()) {
			if (jsModule instanceof DynamicJSModule) {
				dynamicJSModules.add((DynamicJSModule)jsModule);
			}
		}

		Collections.sort(
			dynamicJSModules,
			(dynamicJSModule1, dynamicJSModule2) -> {
				String resolvedId = dynamicJSModule1.getResolvedId();

				return resolvedId.compareTo(dynamicJSModule2.getResolvedId());
			});

		for (DynamicJSModule dynamicJSModule : dynamicJSModules) {
			_update(messageDigest, dynamicJSModule);
		}

		// Hash JS packages

		List<JSPackage> jsPackages = new ArrayList<>(
			_resolvedJSPackages.values());

		Collections.sort(
			jsPackages,
			(jsPackage1, jsPackage2) -> {
				String resolvedId = jsPackage1.getResolvedId();

				return resolvedId.compareTo(jsPackage2.getResolvedId());
			});

		for (JSPackage jsPackage : jsPackages) {
			_update(messageDigest, jsPackage);
		}

		// Hash the partial match map because may alter the resolutions

		ArrayList<Map.Entry<String, String>> entries = new ArrayList<>(
			_partialMatchMap.entrySet());

		Collections.sort(
			entries,
			(entry1, entry2) -> {
				String key1 = entry1.getKey();
				String key2 = entry2.getKey();

				if (!Objects.equals(key1, key2)) {
					return key1.compareTo(key2);
				}

				String value = entry1.getValue();

				return value.compareTo(entry2.getValue());
			});

		for (Map.Entry<String, String> entry : entries) {
			_update(messageDigest, entry.getKey());
			_update(messageDigest, entry.getValue());
		}

		// Hash the list of applied patches because Liferay Support's patches
		// break the immutability convention of packages

		List<String> installedPatches = Arrays.asList(
			PatcherUtil.getInstalledPatches());

		Collections.sort(installedPatches);

		for (String installedPatch : installedPatches) {
			_update(messageDigest, installedPatch);
		}

		return StringUtil.bytesToHexString(messageDigest.digest());
	}

	private void _update(
		MessageDigest messageDigest, DynamicJSModule dynamicJSModule) {

		_update(messageDigest, dynamicJSModule.getResolvedId());

		List<String> dependencies = new ArrayList<>(
			dynamicJSModule.getDependencies());

		Collections.sort(dependencies);

		for (String dependency : dependencies) {
			_update(messageDigest, dependency);
		}
	}

	private void _update(MessageDigest messageDigest, JSPackage jsPackage) {

		// Hash the fields besides (name and version) for extra safety

		_update(messageDigest, jsPackage.getMainModuleName());
		_update(messageDigest, jsPackage.getName());
		_update(messageDigest, jsPackage.getVersion());

		List<JSPackageDependency> jsPackageDependencies = new ArrayList<>(
			jsPackage.getJSPackageDependencies());

		Collections.sort(
			jsPackageDependencies,
			(jsPackageDependency1, jsPackageDependency2) -> {
				String packageName1 = jsPackageDependency1.getPackageName();
				String packageName2 = jsPackageDependency2.getPackageName();

				if (!Objects.equals(packageName1, packageName2)) {
					return packageName1.compareTo(packageName2);
				}

				String versionConstraints =
					jsPackageDependency1.getVersionConstraints();

				return versionConstraints.compareTo(
					jsPackageDependency2.getVersionConstraints());
			});

		for (JSPackageDependency jsPackageDependency : jsPackageDependencies) {
			_update(messageDigest, jsPackageDependency.getPackageName());
			_update(messageDigest, jsPackageDependency.getVersionConstraints());
		}

		List<JSModuleAlias> jsModuleAliases = new ArrayList<>(
			jsPackage.getJSModuleAliases());

		Collections.sort(
			jsModuleAliases,
			(jsModuleAlias1, jsModuleAlias2) -> {
				String alias1 = jsModuleAlias1.getAlias();
				String alias2 = jsModuleAlias2.getAlias();

				if (!Objects.equals(alias1, alias2)) {
					return alias1.compareTo(alias2);
				}

				String moduleName = jsModuleAlias1.getModuleName();

				return moduleName.compareTo(jsModuleAlias2.getModuleName());
			});

		for (JSModuleAlias jsModuleAlias : jsModuleAliases) {
			_update(messageDigest, jsModuleAlias.getAlias());
			_update(messageDigest, jsModuleAlias.getModuleName());
		}
	}

	private void _update(MessageDigest messageDigest, String string) {
		try {
			messageDigest.update(string.getBytes(StringPool.UTF8));
		}
		catch (UnsupportedEncodingException unsupportedEncodingException) {
			throw new RuntimeException(unsupportedEncodingException);
		}
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