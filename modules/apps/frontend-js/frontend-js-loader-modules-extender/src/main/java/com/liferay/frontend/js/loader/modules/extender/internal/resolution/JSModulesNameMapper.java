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
import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Rodolfo Roza Miranda
 */
@Component(
	immediate = true,
	service = {JSBundleTracker.class, JSModulesNameMapper.class}
)
public class JSModulesNameMapper implements JSBundleTracker {

	@Activate
	public void activate() {
		_clearCacheState();
	}

	@Override
	public void addedJSBundle(
		JSBundle jsBundle, Bundle bundle, NPMRegistry npmRegistry) {

		_clearCacheState();
	}

	@Override
	public void removedJSBundle(
		JSBundle jsBundle, Bundle bundle, NPMRegistry npmRegistry) {

		_clearCacheState();
	}

	private void _clearCacheState() {
		_cacheState.set(new CacheState());
	}

	public String mapModule(String module) {
		return mapModule(module, null);
	}

	public String mapModule(String module, Map<String, String> contextMap) {
		CacheState cacheState = _cacheState.get();

		if (cacheState.isOlderThan(
			_jsConfigGeneratorPackagesTracker.getLastModified())) {

			_clearCacheState();

			cacheState = _cacheState.get();
		}

		String resolved = cacheState.get(module);

		if (resolved != null) {
			return resolved;
		}

		String matchModule = module;

		if (contextMap != null) {
			matchModule = _map(matchModule, contextMap, contextMap);
		}

		resolved = _map(
			matchModule, cacheState.getExactMatchContextMap(),
			cacheState.getPartialMatchContextMap());

		cacheState.put(module, resolved);

		return resolved;
	}

	private String _map(
		String module, Map<String, String> exactMap,
		Map<String, String> partialMap) {

		String aliasValue = exactMap.get(module);

		if (Validator.isNotNull(aliasValue)) {
			return aliasValue;
		}

		for (Map.Entry<String, String> entry : partialMap.entrySet()) {
			String alias = entry.getKey();

			if (alias.equals(module) ||
				module.startsWith(alias + StringPool.SLASH)) {

				return entry.getValue() + module.substring(alias.length());
			}
		}

		return module;
	}

	private final AtomicReference<CacheState> _cacheState =
		new AtomicReference<>();

	@Reference
	private JSConfigGeneratorPackagesTracker _jsConfigGeneratorPackagesTracker;

	@Reference
	private NPMRegistry _npmRegistry;

	private class CacheState {

		public CacheState() {
			_lastModified = System.currentTimeMillis();
			_exactMatchContextMap = _getExactMatchContextMap();
			_partialMatchContextMap = _getPartialMatchContextMap();
		}

		public String get(String key) {
			return _cache.get(key);
		}

		public Map<String, String> getExactMatchContextMap() {
			return _exactMatchContextMap;
		}

		public Map<String, String> getPartialMatchContextMap() {
			return _partialMatchContextMap;
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

		private Map<String, String> _getExactMatchContextMap() {
			Map<String, String> exactMatchContextMap = new HashMap<>();

			for (JSPackage jsPackage : _npmRegistry.getResolvedJSPackages()) {
				String jsPackageResolvedId = jsPackage.getResolvedId();

				String mainModulePath =
					jsPackageResolvedId + StringPool.SLASH +
					jsPackage.getMainModuleName();

				exactMatchContextMap.put(jsPackageResolvedId, mainModulePath);

				for (JSModuleAlias jsModuleAlias :
					jsPackage.getJSModuleAliases()) {

					String aliasPath =
						jsPackageResolvedId + StringPool.SLASH +
						jsModuleAlias.getAlias();

					String moduleNamePath =
						jsPackageResolvedId + StringPool.SLASH +
						jsModuleAlias.getModuleName();

					exactMatchContextMap.put(aliasPath, moduleNamePath);
				}
			}

			return exactMatchContextMap;
		}

		private Map<String, String> _getPartialMatchContextMap() {
			Map<String, String> partialContextMatch = new HashMap<>();

			Collection<JSConfigGeneratorPackage> jsConfigGeneratorPackages =
				_jsConfigGeneratorPackagesTracker.getJSConfigGeneratorPackages();

			Function<JSConfigGeneratorPackage, String> valueMapper =
				m -> m.getName() + StringPool.AT + m.getVersion();

			Stream<JSConfigGeneratorPackage> jsConfigGeneratorPackageStream =
				jsConfigGeneratorPackages.stream();

			Map<String, String> map = jsConfigGeneratorPackageStream.collect(
				Collectors.toMap(JSConfigGeneratorPackage::getName, valueMapper)
			);

			partialContextMatch.putAll(map);

			partialContextMatch.putAll(_npmRegistry.getGlobalAliases());

			return partialContextMatch;
		}

		private final Map<String, String> _cache = new ConcurrentHashMap<>();
		private final Map<String, String> _exactMatchContextMap;
		private final long _lastModified;
		private final Map<String, String> _partialMatchContextMap;

	}
}
