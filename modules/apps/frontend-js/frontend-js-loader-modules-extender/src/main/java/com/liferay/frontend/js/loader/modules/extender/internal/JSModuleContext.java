package com.liferay.frontend.js.loader.modules.extender.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Rodolfo Roza Miranda
 */
public class JSModuleContext {

	public void addResolvedModule(String alias) {
		_resolvedModules.add(0, alias);
	}

	public Map<String, String> getConfigMap() {
		return _configMap;
	}

	public Map<String, Map<String, String>> getModuleMap() {
		return _moduleMap;
	}

	public List<String> getResolvedModules() {
		ArrayList<String> copy = new ArrayList<>(_resolvedModules);
		Collections.reverse(copy);
		return copy;
	}

	public void putConfig(String module, String mappedModule) {
		_configMap.put(module, mappedModule);
	}

	public void putModuleDependencyMap(String alias, Map<String, String> dependenciesMap) {
		_moduleMap.put(alias, dependenciesMap);
	}

	private final Map<String, String> _configMap = new ConcurrentHashMap<>();

	private final Map<String, Map<String, String>> _moduleMap = new ConcurrentHashMap<>();

	private final List<String> _resolvedModules = new ArrayList<>();

}
