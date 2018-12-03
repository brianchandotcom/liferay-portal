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
import java.util.Optional;
import java.util.function.Predicate;
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
	protected void setNPMRegistry(NPMRegistry npmRegistry) {
		_npmRegistry = npmRegistry;
	}

	private Predicate<String> _filterDependencies() {
		return dependency -> !dependency.equals("require") &&
			!dependency.equals("exports") &&
			!dependency.equals("module");
	}

	private Optional<List<String>> _findModule(String moduleName) {
		ArrayList<JSModuleAdapter> allModules = _getAllModules();

		Optional<JSModuleAdapter> module = allModules.stream()
			.filter(m -> m.getAlias().equals(moduleName))
			.findAny();

		if (module.isPresent()) {
			JSModuleAdapter adapter = module.get();

			ArrayList<String> modules = new ArrayList<>();

			List<String> result = _processModule(adapter);

			if (result != null) {
				modules.addAll(result.stream()
					.filter(Objects::nonNull)
					.collect(Collectors.toList()));
			}

			Collections.reverse(modules);

			return Optional.of(modules);
		}

		return Optional.empty();
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
		JSModulesNameMapper mapper = new JSModulesNameMapper(_jsLoaderModulesTracker, _npmRegistry);

		return mapper.mapModule(module, contextMap);
	}

	private String _mapModuleName(String module) {
		JSModulesNameMapper mapper = new JSModulesNameMapper(_jsLoaderModulesTracker, _npmRegistry);

		return mapper.mapModule(module);
	}

	private List<String> _moduleNotFoundMessage(String moduleName) {
		return Collections.singletonList(":ERROR: Module " + moduleName + " not found");
	}

	private List<String> _processModule(JSModuleAdapter adapter) {

		if (adapter == null) {
			return Collections.emptyList();
		}

		Collection<String> dependencies = adapter.getDependencies();

		String alias = adapter.getAlias();

		ArrayList<String> dependenciesMap = dependencies.stream()
			.filter(_filterDependencies())
			.map(dependency -> PathResolver.resolvePath(alias, dependency))
			.map(d -> _mapModuleName(d, adapter.getMap()))
			.map(this::_processModule)
			.flatMap(Collection::stream)
			.collect(Collectors.toCollection(ArrayList::new));

		dependenciesMap.add(0, alias);

		return dependenciesMap;
	}

	private List<String> _processModule(String module) {
		JSModule jsModule = _npmRegistry.getResolvedJSModule(module);

		if (jsModule == null) {
			System.out.println("NOT FOUND: " + module);
		}

		return _processModule(new NPMRegistryModuleAdapter(jsModule, _npmRegistry));
	}

	private List<String> _resolve(String module) {

		String mappedModule = _mapModuleName(module);

		Optional<List<String>> modules = _findModule(mappedModule);

		return modules.orElse(this._moduleNotFoundMessage(module));
	}

	private JSLoaderModulesTracker _jsLoaderModulesTracker;

	private NPMRegistry _npmRegistry;
}
