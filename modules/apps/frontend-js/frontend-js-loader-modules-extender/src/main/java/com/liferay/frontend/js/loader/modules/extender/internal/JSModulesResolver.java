package com.liferay.frontend.js.loader.modules.extender.internal;

import com.liferay.frontend.js.loader.modules.extender.internal.adapter.JSLoaderModuleAdapter;
import com.liferay.frontend.js.loader.modules.extender.internal.adapter.JSModuleAdapter;
import com.liferay.frontend.js.loader.modules.extender.internal.adapter.NPMRegistryModuleAdapter;
import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Rodolfo Roza Miranda
 */
@Component(
	immediate = true,
	service = JSModulesResolver.class
)
public class JSModulesResolver {

	public List<String> resolve(List<String> modules) {
		return modules.stream()
			.map(this::_resolve)
			.flatMap(Collection::stream)
			.filter(Objects::nonNull)
			.distinct()
			.collect(Collectors.toList());
	}

	@Reference(unbind = "-")
	protected void setJSLoaderModulesTracker(JSLoaderModulesTracker jsLoaderModulesTracker) {
		_jsLoaderModulesTracker = jsLoaderModulesTracker;
	}

	@Reference(unbind = "-")
	protected void setJSModulesNameMapper(JSModulesNameMapper jsModulesNameMapper) {
		_mapper = jsModulesNameMapper;
	}

	@Reference(unbind = "-")
	protected void setNPMRegistry(NPMRegistry npmRegistry) {
		_npmRegistry = npmRegistry;
	}

	private List<String> _findModule(String moduleName) {
		ArrayList<JSModuleAdapter> allModules = _getAllModules();

		JSModuleAdapter module = null;

		for (JSModuleAdapter m : allModules) {
			if (m.getAlias().equals(moduleName)) {
				module = m;
				break;
			}
		}

		if (module != null) {
			List<String> result = _processModule(module, new ArrayList<>());

			result.add(0, module.getAlias());

			Collections.reverse(result);

			return result;
		}

		return null;
	}

	private ArrayList<JSModuleAdapter> _getAllModules() {
		List<JSLoaderModuleAdapter> jsLoaderModuleAdapters = _jsLoaderModulesTracker.getJSLoaderModules().stream()
			.map(JSLoaderModuleAdapter::new)
			.collect(Collectors.toList());

		List<NPMRegistryModuleAdapter> npmRegistryModules = _npmRegistry.getResolvedJSModules().stream()
			.map(m -> new NPMRegistryModuleAdapter(m, _npmRegistry))
			.collect(Collectors.toList());

		ArrayList<JSModuleAdapter> allModules = new ArrayList<>();

		allModules.addAll(jsLoaderModuleAdapters);
		allModules.addAll(npmRegistryModules);

		return allModules;
	}

	private String _mapModuleName(String module, Map<String, String> contextMap) {
		return _mapper.mapModule(module, contextMap);
	}

	private String _mapModuleName(String module) {
		return _mapper.mapModule(module);
	}

	private List<String> _moduleNotFoundMessage(String moduleName) {
		return Collections.singletonList(":ERROR: Module " + moduleName + " not found");
	}

	private List<String> _processModule(JSModuleAdapter adapter, List<String> results) {

		if (adapter == null) {
			return Collections.emptyList();
		}

		List<String> finalResults = results;

		Collection<String> dependencies = adapter.getDependencies();

		String alias = adapter.getAlias();

		for (String dependency : dependencies) {

			if (!dependency.equals("require") &&
				!dependency.equals("exports") &&
				!dependency.equals("module")) {

				String resolvedPath = PathResolver.resolvePath(alias, dependency);

				String mappedModuleName = _mapModuleName(resolvedPath, adapter.getMap());

				if (!finalResults.contains(mappedModuleName)) {
					finalResults = _processModule(mappedModuleName, results);

					finalResults.add(0, mappedModuleName);
				}
			}
		}

		return finalResults;
	}

	private List<String> _processModule(String module, List<String> results) {
		JSModule jsModule = _npmRegistry.getResolvedJSModule(module);

		if (jsModule == null) {
			System.out.println("NOT FOUND: " + module);
		}

		return _processModule(new NPMRegistryModuleAdapter(jsModule, _npmRegistry), results);
	}

	private List<String> _resolve(String module) {

		String mappedModule = _mapModuleName(module);

		List<String> modules = _findModule(mappedModule);

		if (modules != null) {
			return modules;
		}

		return _moduleNotFoundMessage(module);
	}

	private JSLoaderModulesTracker _jsLoaderModulesTracker;

	private JSModulesNameMapper _mapper;

	private NPMRegistry _npmRegistry;

}
