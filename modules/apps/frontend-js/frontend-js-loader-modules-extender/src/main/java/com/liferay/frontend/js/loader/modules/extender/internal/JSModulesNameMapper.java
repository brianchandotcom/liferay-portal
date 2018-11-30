package com.liferay.frontend.js.loader.modules.extender.internal;

import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
import com.liferay.petra.string.StringPool;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Rodolfo Roza Miranda
 */
public class JSModulesNameMapper {

	public JSModulesNameMapper(JSLoaderModulesTracker jsLoaderModulesTracker, NPMRegistry npmRegistry) {
		_jsLoaderModulesTracker = jsLoaderModulesTracker;
		_npmRegistry = npmRegistry;
	}

	public String mapModule(String module) {
		return mapModule(module, null);
	}

	public String mapModule(String module, Map<String, String> contextMap) {

		String matchModule = module;

		if (contextMap != null) {
			matchModule = _map(matchModule, contextMap);
		}

		return _mapWithConfig(matchModule);
	}

	private String _mapWithConfig(String module) {

		Map<String, String> map = this._getExactMatchContextMap();

		String match = _mapExactMatch(module, map);

		if (match != null) {
			return match;
		}

		Map<String, String> partialMatchContextMap = this._getPartialMatchContextMap();

		String partialMatch = _mapPartialMatch(module, partialMatchContextMap);

		if (partialMatch != null) {
			return partialMatch;
		}

		return module;
	}

	private Map<String, String> _getExactMatchContextMap() {

		Collection<JSPackage> npmRegistryModules = _npmRegistry.getResolvedJSPackages();

		Function<JSPackage, String> valueMapper = m -> m.getResolvedId() + StringPool.SLASH + m.getMainModuleName();

		return npmRegistryModules.stream()
			.collect(Collectors.toMap(JSPackage::getResolvedId, valueMapper));
	}

	private Map<String, String> _getPartialMatchContextMap() {
		Collection<JSLoaderModule> loaderModules = _jsLoaderModulesTracker.getJSLoaderModules();

		Function<JSLoaderModule, String> valueMapper = m -> m.getName() + StringPool.AT + m.getVersion();

		Map<String, String> map = loaderModules.stream()
			.collect(Collectors.toMap(JSLoaderModule::getName, valueMapper));

		map.putAll(_npmRegistry.getGlobalAliases());

		return map;
	}

	private String _map(String module, Map<String, String> map) {
		String match = _mapExactMatch(module, map);

		if (match != null) {
			return match;
		}

		match = _mapPartialMatch(module, map);

		if (match != null) {
			return match;
		}

		return module;
	}

	private String _mapExactMatch(String module, Map<String, String> map) {
		if (map != null) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				String alias = entry.getKey();
				String aliasValue = entry.getValue();

				if (alias.equals(module)) {
					return aliasValue;
				}
			}
		}

		return null;
	}

	private String _mapPartialMatch(String module, Map<String, String> map) {
		if (map != null) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				String alias = entry.getKey();
				String aliasValue = entry.getValue();

				if (alias.equals(module) || module.startsWith(alias + StringPool.SLASH)) {
					return aliasValue + module.substring(alias.length());
				}
			}
		}

		return null;
	}

	private final JSLoaderModulesTracker _jsLoaderModulesTracker;

	private final NPMRegistry _npmRegistry;
}
