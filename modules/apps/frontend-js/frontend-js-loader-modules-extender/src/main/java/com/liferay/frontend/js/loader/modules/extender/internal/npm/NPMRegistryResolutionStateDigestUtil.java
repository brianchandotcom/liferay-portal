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
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
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
import java.util.Objects;

/**
 * Computes a digest (hash) with ALL values that can make module resolutions
 * change.
 *
 * The data added to the digest is based on package names, versions,
 * dependencies and any other information that, when changed, may alter the way
 * modules are resolved.
 *
 * The digest is stable throughout different cluster nodes so that if two nodes
 * share the same state, their digests are identical.
 *
 * @author Iván Zaera Avellón
 * @review
 */
public class NPMRegistryResolutionStateDigestUtil {

	public static String digest(NPMRegistry npmRegistry) {
		MessageDigest messageDigest;

		try {
			messageDigest = MessageDigest.getInstance("SHA-1");
		}
		catch (NoSuchAlgorithmException noSuchAlgorithmException) {
			throw new RuntimeException(noSuchAlgorithmException);
		}

		// Hash dynamic JS modules that do not honor immutability of packages

		List<DynamicJSModule> dynamicJSModules = new ArrayList<>();

		for (JSModule jsModule : npmRegistry.getResolvedJSModules()) {
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
			npmRegistry.getResolvedJSPackages());

		Collections.sort(
			jsPackages,
			(jsPackage1, jsPackage2) -> {
				String resolvedId = jsPackage1.getResolvedId();

				return resolvedId.compareTo(jsPackage2.getResolvedId());
			});

		for (JSPackage jsPackage : jsPackages) {
			_update(messageDigest, jsPackage);
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

	private static void _update(
		MessageDigest messageDigest, DynamicJSModule dynamicJSModule) {

		_update(messageDigest, dynamicJSModule.getResolvedId());

		List<String> dependencies = new ArrayList<>(
			dynamicJSModule.getDependencies());

		Collections.sort(dependencies);

		for (String dependency : dependencies) {
			_update(messageDigest, dependency);
		}
	}

	private static void _update(
		MessageDigest messageDigest, JSPackage jsPackage) {

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
					packageName1.compareTo(packageName2);
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

	private static void _update(MessageDigest messageDigest, String string) {
		try {
			messageDigest.update(string.getBytes(StringPool.UTF8));
		}
		catch (UnsupportedEncodingException unsupportedEncodingException) {
			throw new RuntimeException(unsupportedEncodingException);
		}
	}

}