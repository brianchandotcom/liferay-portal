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
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodolfo Roza Miranda
 */
@Component(immediate = true, service = JSModulesNameMapper.class)
public class JSModulesNameMapper {

	@Activate
	public void activate() {
		_clearCacheState(null);
	}

	public String mapModule(String moduleName) {
		return mapModule(moduleName, null);
	}

	public String mapModule(String moduleName, Map<String, String> mappings) {
		CacheState cacheState = _cacheState.get();

		if (cacheState.isOlderThan(
				_jsConfigGeneratorPackagesTracker.getLastModified())) {

			_clearCacheState(cacheState.getNPMRegistry());

			cacheState = _cacheState.get();
		}

		String resolvedModuleName = cacheState.get(moduleName);

		if (resolvedModuleName != null) {
			return resolvedModuleName;
		}

		resolvedModuleName = moduleName;

		if (mappings != null) {
			resolvedModuleName = _map(moduleName, mappings, mappings);
		}

		resolvedModuleName = _map(
			resolvedModuleName, cacheState.getExactMatchMappings(),
			cacheState.getPartialMatchMappings());

		cacheState.put(moduleName, resolvedModuleName);

		return resolvedModuleName;
	}

	@Component(immediate = true, service = JSBundleTracker.class)
	public static class JSModulesNameMapperJSBundleTracker
		implements JSBundleTracker {

		@Override
		public void addedJSBundle(
			JSBundle jsBundle, Bundle bundle, NPMRegistry npmRegistry) {

			_jsModulesNameMapper._clearCacheState(npmRegistry);
		}

		@Override
		public void removedJSBundle(
			JSBundle jsBundle, Bundle bundle, NPMRegistry npmRegistry) {

			_jsModulesNameMapper._clearCacheState(npmRegistry);
		}

		@Reference
		private JSModulesNameMapper _jsModulesNameMapper;

	}

	private void _clearCacheState(NPMRegistry npmRegistry) {
		_cacheState.set(new CacheState(npmRegistry));
	}

	private String _map(
		String moduleName, Map<String, String> exactMatchMap,
		Map<String, String> partialMatchMap) {

		String mappedModuleName = exactMatchMap.get(moduleName);

		if (Validator.isNotNull(mappedModuleName)) {
			return mappedModuleName;
		}

		for (Map.Entry<String, String> entry : partialMatchMap.entrySet()) {
			String alias = entry.getKey();

			if (alias.equals(moduleName) ||
				moduleName.startsWith(alias + StringPool.SLASH)) {

				return entry.getValue() + moduleName.substring(alias.length());
			}
		}

		return moduleName;
	}

	private final AtomicReference<CacheState> _cacheState =
		new AtomicReference<>();

	@Reference
	private JSConfigGeneratorPackagesTracker _jsConfigGeneratorPackagesTracker;

	private class CacheState {

		public CacheState(NPMRegistry npmRegistry) {
			_npmRegistry = npmRegistry;
			_lastModified = System.currentTimeMillis();

			if (_npmRegistry != null) {
				_setExactMatchMappings();
				_setPartialMatchMappings();
			}
		}

		public String get(String key) {
			return _cache.get(key);
		}

		public Map<String, String> getExactMatchMappings() {
			return _exactMatchMappings;
		}

		public NPMRegistry getNPMRegistry() {
			return _npmRegistry;
		}

		public Map<String, String> getPartialMatchMappings() {
			return _partialMatchMappings;
		}

		public boolean isOlderThan(long lastModified) {
			if (_lastModified < lastModified) {
				return true;
			}

			return false;
		}

		public void put(String key, String value) {
			_cache.put(key, value);
		}

		private void _setExactMatchMappings() {
			for (JSPackage jsPackage : _npmRegistry.getResolvedJSPackages()) {
				String jsPackageResolvedId = jsPackage.getResolvedId();

				String mainModulePath =
					jsPackageResolvedId + StringPool.SLASH +
						jsPackage.getMainModuleName();

				_exactMatchMappings.put(jsPackageResolvedId, mainModulePath);

				for (JSModuleAlias jsModuleAlias :
						jsPackage.getJSModuleAliases()) {

					String aliasPath =
						jsPackageResolvedId + StringPool.SLASH +
							jsModuleAlias.getAlias();

					String moduleNamePath =
						jsPackageResolvedId + StringPool.SLASH +
							jsModuleAlias.getModuleName();

					_exactMatchMappings.put(aliasPath, moduleNamePath);
				}
			}
		}

		private void _setPartialMatchMappings() {
			Collection<JSConfigGeneratorPackage> jsConfigGeneratorPackages =
				_jsConfigGeneratorPackagesTracker.
					getJSConfigGeneratorPackages();

			Function<JSConfigGeneratorPackage, String> valueMapper =
				jsConfigGeneratorPackage ->
					jsConfigGeneratorPackage.getName() + StringPool.AT +
						jsConfigGeneratorPackage.getVersion();

			Stream<JSConfigGeneratorPackage> jsConfigGeneratorPackagesStream =
				jsConfigGeneratorPackages.stream();

			Map<String, String> mappings =
				jsConfigGeneratorPackagesStream.collect(
					Collectors.toMap(
						JSConfigGeneratorPackage::getName, valueMapper));

			_partialMatchMappings.putAll(mappings);

			_partialMatchMappings.putAll(_npmRegistry.getGlobalAliases());
		}

		private final Map<String, String> _cache = new ConcurrentHashMap<>();
		private final Map<String, String> _exactMatchMappings = new HashMap<>();
		private final long _lastModified;
		private final NPMRegistry _npmRegistry;
		private final Map<String, String> _partialMatchMappings =
			new HashMap<>();

	}

}