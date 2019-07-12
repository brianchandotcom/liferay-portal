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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodolfo Roza Miranda
 */
@Component(immediate = true, service = BrowserModuleNameMapper.class)
public class BrowserModuleNameMapper {

	public void clearCache() {
		_browserModuleNameMapperCache.set(null);
	}

	public String mapModuleName(NPMRegistry npmRegistry, String moduleName) {
		return mapModuleName(npmRegistry, moduleName, null);
	}

	public String mapModuleName(
		NPMRegistry npmRegistry, String moduleName,
		Map<String, String> dependenciesMap) {

		BrowserModuleNameMapperCache browserModuleNameMapperCache =
			_browserModuleNameMapperCache.get();

		if ((browserModuleNameMapperCache == null) ||
			browserModuleNameMapperCache.isOlderThan(
				_jsConfigGeneratorPackagesTracker.getModifiedCount())) {

			browserModuleNameMapperCache = new BrowserModuleNameMapperCache(
				_getExactMatchMap(npmRegistry),
				_getPartialMatchMap(npmRegistry),
				_jsConfigGeneratorPackagesTracker.getModifiedCount());

			_browserModuleNameMapperCache.set(browserModuleNameMapperCache);
		}

		String mappedModuleName = browserModuleNameMapperCache.get(
			moduleName, dependenciesMap);

		if (mappedModuleName != null) {
			return mappedModuleName;
		}

		mappedModuleName = moduleName;

		if (dependenciesMap != null) {
			mappedModuleName = _map(
				moduleName, dependenciesMap, dependenciesMap);
		}

		mappedModuleName = _map(
			mappedModuleName, browserModuleNameMapperCache.getExactMatchMap(),
			browserModuleNameMapperCache.getPartialMatchMap());

		browserModuleNameMapperCache.put(
			moduleName, dependenciesMap, mappedModuleName);

		return mappedModuleName;
	}

	private Map<String, String> _getExactMatchMap(NPMRegistry npmRegistry) {
		Map<String, String> exactMatchMap = new HashMap<>();

		for (JSPackage jsPackage : npmRegistry.getResolvedJSPackages()) {
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

	private Map<String, String> _getPartialMatchMap(NPMRegistry npmRegistry) {
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

		partialMatchMap.putAll(npmRegistry.getGlobalAliases());

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

	private final AtomicReference<BrowserModuleNameMapperCache>
		_browserModuleNameMapperCache = new AtomicReference<>();

	@Reference
	private JSConfigGeneratorPackagesTracker _jsConfigGeneratorPackagesTracker;

}