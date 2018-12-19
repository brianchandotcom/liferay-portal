package com.liferay.frontend.js.loader.modules.extender.internal;

import com.liferay.frontend.js.loader.modules.extender.npm.JSModuleAlias;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
import com.liferay.petra.string.StringPool;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Rodolfo Roza Miranda
 */
@Component(
	immediate = true,
	service = JSModulesNameMapper.class
)
public class JSModulesNameMapper {

	public String mapModule(String module) {
		return mapModule(module, null);
	}

	public String mapModule(String module, Map<String, String> contextMap) {

		if (_cache.containsKey(module)) {
			return _cache.get(module);
		}

		String matchModule = module;

		if (contextMap != null) {
			matchModule = _map(matchModule, contextMap);
		}

		String resolved = _map(matchModule, null);

		_cache.put(module, resolved);

		return resolved;
	}

	@Reference(unbind = "-")
	protected void setJSLoaderModulesTracker(JSLoaderModulesTracker jsLoaderModulesTracker) {
		_jsLoaderModulesTracker = jsLoaderModulesTracker;
	}

	@Reference(unbind = "-")
	protected void setNPMRegistry(NPMRegistry npmRegistry) {
		_npmRegistry = npmRegistry;
	}

	private Map<String, String> _getExactMatchContextMap() {

		Collection<JSPackage> npmRegistryModules =
			_npmRegistry.getResolvedJSPackages();

		Map<String, String> registryModules = new HashMap<>();

		for (JSPackage jsPackage : npmRegistryModules) {

			String resolvedId = jsPackage.getResolvedId();

			String jsPackageValue = resolvedId + StringPool.SLASH + jsPackage.getMainModuleName();

			registryModules.put(resolvedId, jsPackageValue);

			for (JSModuleAlias jsModuleAlias : jsPackage.getJSModuleAliases()) {
				String key = resolvedId + StringPool.SLASH + jsModuleAlias.getAlias();
				String value = resolvedId + StringPool.SLASH + jsModuleAlias.getModuleName();

				registryModules.put(key, value);
			}
		}

		return registryModules;
	}

	private Map<String, String> _getPartialMatchContextMap() {
		if (_jsLoaderModulesTracker.getLastModified() > _jsLoaderModulesTrackerLastModified) {
			_partialMatchContextMap.clear();
			_cache.clear();

			Collection<JSLoaderModule> loaderModules = _jsLoaderModulesTracker.getJSLoaderModules();

			Function<JSLoaderModule, String> valueMapper = m -> m.getName() + StringPool.AT + m.getVersion();

			Map<String, String> map = loaderModules.stream()
				.collect(Collectors.toMap(JSLoaderModule::getName, valueMapper));

			_partialMatchContextMap.putAll(map);
			_partialMatchContextMap.putAll(_npmRegistry.getGlobalAliases());

			_jsLoaderModulesTrackerLastModified = _jsLoaderModulesTracker.getLastModified();
		}

		return _partialMatchContextMap;
	}

	private String _map(String module, Map<String, String> map) {
		Map<String, String> exactMap = this._getExactMatchContextMap();
		Map<String, String> partialMap = this._getPartialMatchContextMap();

		if (map != null) {
			exactMap = map;
			partialMap = map;
		}

		for (Map.Entry<String, String> entry : exactMap.entrySet()) {
			String alias = entry.getKey();
			String aliasValue = entry.getValue();

			if (alias.equals(module)) {
				return aliasValue;
			}
		}

		for (Map.Entry<String, String> entry : partialMap.entrySet()) {
			String alias = entry.getKey();
			String aliasValue = entry.getValue();

			if (alias.equals(module) || module.startsWith(alias + StringPool.SLASH)) {
				return aliasValue + module.substring(alias.length());
			}
		}

		return module;
	}

	private final Map<String, String> _cache = new ConcurrentHashMap<>();

	private final Map<String, String> _partialMatchContextMap = new ConcurrentHashMap<>();

	private JSLoaderModulesTracker _jsLoaderModulesTracker;

	private long _jsLoaderModulesTrackerLastModified = 0L;

	private NPMRegistry _npmRegistry;
}
