package com.liferay.frontend.js.loader.modules.extender.internal.resolution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Object to hold the results of a resolution of a list of modules.
 * @author Rodolfo Roza Miranda
 * @review
 */
public class JSModulesResolution {

	private final boolean _explainResolutions;

	public JSModulesResolution(boolean explainResolutions) {
		_explainResolutions = explainResolutions;
	}

	public void indentExplanation() {
		_explanationIndentation++;
	}

	public void dedentExplanation() {
		_explanationIndentation--;
	}

	public void addResolvedModule(String alias) {
		_resolvedModules.add(0, alias);

		if (!_explainResolutions) {
			return;
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < _explanationIndentation; i++) {
			sb.append("  ");
		}

		sb.append(alias);

		_explanation.add(0, sb.toString());
	}

	public Collection<String> getExplanation() {
		return _explanation;
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

	private int _explanationIndentation = 0;
	private List<String> _explanation = new ArrayList<>();
	private final Map<String, String> _configMap = new ConcurrentHashMap<>();
	private final Map<String, String> _pathMap = new ConcurrentHashMap<>();
	private final Map<String, Map<String, String>> _moduleMap =
		new ConcurrentHashMap<>();
	private final Set<String> _processedModules = new ConcurrentSkipListSet<>();
	private final List<String> _resolvedModules = new ArrayList<>();
}
