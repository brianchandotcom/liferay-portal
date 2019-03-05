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

import com.liferay.frontend.js.loader.modules.extender.internal.config.generator.JSConfigGeneratorPackage;
import com.liferay.frontend.js.loader.modules.extender.internal.config.generator.JSConfigGeneratorPackagesTracker;
import com.liferay.frontend.js.loader.modules.extender.npm.JSBundle;
import com.liferay.frontend.js.loader.modules.extender.npm.JSBundleTracker;
import com.liferay.frontend.js.loader.modules.extender.npm.JSModuleAlias;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.ModuleNameUtil;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodolfo Roza Miranda
 */
@Component(immediate = true, service = JSModuleNameMapper.class)
public class JSModuleNameMapper {

	public String mapModuleName(String moduleName) {
		return mapModuleName(moduleName, null);
	}

	public String mapModuleName(
		String moduleName, Map<String, String> dependenciesMap) {

		JSModuleNameMapperCache cache = _cache.get();

		if (cache.isOlderThan(
				_jsConfigGeneratorPackagesTracker.getLastModified())) {

			_clearCache();

			cache = _cache.get();
		}

		String mappedModuleName = cache.get(moduleName, dependenciesMap);

		if (mappedModuleName != null) {
			return mappedModuleName;
		}

		mappedModuleName = moduleName;

		if (dependenciesMap != null) {
			mappedModuleName = _map(
				moduleName, dependenciesMap, dependenciesMap);
		}

		mappedModuleName = _map(
			mappedModuleName, cache.getExactMatchMap(),
			cache.getPartialMatchMap());

		cache.put(moduleName, dependenciesMap, mappedModuleName);

		return mappedModuleName;
	}

	@Activate
	protected void activate() {
		_npmRegistry.addJSBundleTracker(_jsBundleTracker);
	}

	@Deactivate
	protected void deactivate() {
		_npmRegistry.removeJSBundleTracker(_jsBundleTracker);
	}

	private void _clearCache() {
		_cache.set(
			new JSModuleNameMapperCache(
				_getExactMatchMap(), _getPartialMatchMap()));
	}

	private Map<String, String> _getExactMatchMap() {
		Map<String, String> exactMatchMap = new HashMap<>();

		for (JSPackage jsPackage : _npmRegistry.getResolvedJSPackages()) {
			String mainModuleResolvedId = ModuleNameUtil.getModuleResolvedId(
				jsPackage, jsPackage.getMainModuleName());

			exactMatchMap.put(jsPackage.getResolvedId(), mainModuleResolvedId);

			for (JSModuleAlias jsModuleAlias : jsPackage.getJSModuleAliases()) {
				String aliasResolvedId = ModuleNameUtil.getModuleResolvedId(
					jsPackage, jsModuleAlias.getAlias());

				String moduleResolvedId = ModuleNameUtil.getModuleResolvedId(
					jsPackage, jsModuleAlias.getModuleName());

				exactMatchMap.put(aliasResolvedId, moduleResolvedId);
			}
		}

		return exactMatchMap;
	}

	private Map<String, String> _getPartialMatchMap() {
		Map<String, String> partialMatchMap = new HashMap<>();

		Collection<JSConfigGeneratorPackage> jsConfigGeneratorPackages =
			_jsConfigGeneratorPackagesTracker.getJSConfigGeneratorPackages();

		for (JSConfigGeneratorPackage jsConfigGeneratorPackage :
				jsConfigGeneratorPackages) {

			String jsConfigGeneratorPackageResolvedId =
				jsConfigGeneratorPackage.getName() + StringPool.AT +
					jsConfigGeneratorPackage.getVersion();

			partialMatchMap.put(
				jsConfigGeneratorPackage.getName(),
				jsConfigGeneratorPackageResolvedId);
		}

		partialMatchMap.putAll(_npmRegistry.getGlobalAliases());

		return partialMatchMap;
	}

	private String _map(
		String moduleName, Map<String, String> exactMatchMap,
		Map<String, String> partialMatchMap) {

		String mappedModuleName = exactMatchMap.get(moduleName);

		if (Validator.isNotNull(mappedModuleName)) {
			return mappedModuleName;
		}

		for (Map.Entry<String, String> entry : partialMatchMap.entrySet()) {
			String resolvedId = entry.getKey();

			if (resolvedId.equals(moduleName) ||
				moduleName.startsWith(resolvedId + StringPool.SLASH)) {

				return entry.getValue() +
					moduleName.substring(resolvedId.length());
			}
		}

		return moduleName;
	}

	private final AtomicReference<JSModuleNameMapperCache> _cache =
		new AtomicReference<>();

	private JSBundleTracker _jsBundleTracker = new JSBundleTracker() {

		@Override
		public void addedJSBundle(
			JSBundle jsBundle, Bundle bundle, NPMRegistry npmRegistry) {

			_clearCache();
		}

		@Override
		public void removedJSBundle(
			JSBundle jsBundle, Bundle bundle, NPMRegistry npmRegistry) {

			_clearCache();
		}

	};

	@Reference
	private JSConfigGeneratorPackagesTracker _jsConfigGeneratorPackagesTracker;

	@Reference
	private NPMRegistry _npmRegistry;

}