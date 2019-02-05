package com.liferay.frontend.js.loader.modules.extender.internal.resolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author Rodolfo Roza Miranda
 */
public class JSModulesResolution {

	public void addResolvedModule(String alias) {
		_resolvedModules.add(0, alias);
	}

	public Map<String, String> getConfigMap() {
		return _configMap;
	}

	public Map<String, Map<String, String>> getModuleMap() {
		return _moduleMap;
	}

	public Map<String, String> getPathMap() {
		return _pathMap;
	}

	public List<String> getResolvedModules() {
		ArrayList<String> copy = new ArrayList<>(_resolvedModules);
		Collections.reverse(copy);
		return copy;
	}

	public void putConfig(String module, String mappedModule) {
		_configMap.put(module, mappedModule);
	}

	public void putModuleDependencyMap(
		String alias, Map<String, String> dependenciesMap) {
		_moduleMap.put(alias, dependenciesMap);
	}

	public void putPath(String alias, String path) {
		_pathMap.put(alias, path);
	}

	public void addProcessedModule(String module) {
		_processedModules.add(module);
	}

	public boolean processedModule(String module) {
		return _processedModules.contains(module);
	}

	private final Map<String, String> _configMap = new ConcurrentHashMap<>();
	private final Map<String, String> _pathMap = new ConcurrentHashMap<>();
	private final Map<String, Map<String, String>> _moduleMap =
		new ConcurrentHashMap<>();
	private final Set<String> _processedModules = new ConcurrentSkipListSet<>();
	private final List<String> _resolvedModules = new ArrayList<>();
}
