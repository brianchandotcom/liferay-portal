package com.liferay.frontend.js.loader.modules.extender.internal;

import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackageDependency;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

	private Optional<List<String>> _findInModulesTracker(String moduleName) {
		Collection<JSLoaderModule> jsLoaderModules = _jsLoaderModulesTracker.getJSLoaderModules();

		return jsLoaderModules.stream()
			.filter(m -> m.getName().startsWith(moduleName))
			.findAny()
			.map(m -> Collections.singletonList(m.getName()));
	}

	private Optional<List<String>> _findInNPMRegistry(String moduleName) {
		Collection<JSModule> resolvedJSModules = _npmRegistry.getResolvedJSModules();

		Optional<JSModule> module = resolvedJSModules.stream()
			.filter(m -> m.getResolvedId().equals(moduleName))
			.findAny();

		if (module.isPresent()) {
			JSModule jsModule = module.get();

			ArrayList<String> modules = new ArrayList<>();

			List<String> result = _processModule(jsModule);

			if (result != null) {
				modules.addAll(result.stream()
					.filter(Objects::nonNull)
					.collect(Collectors.toList()));
			}

			return Optional.of(modules);
		}

		return Optional.empty();
	}

	private String _mapModuleName(String module, HashMap<String, String> contextMap) {
		JSModulesNameMapper mapper = new JSModulesNameMapper(_jsLoaderModulesTracker, _npmRegistry);

		return mapper.mapModule(module, contextMap);
	}

	private String _mapModuleName(String module) {
		JSModulesNameMapper mapper = new JSModulesNameMapper(_jsLoaderModulesTracker, _npmRegistry);

		return mapper.mapModule(module);
	}

	private String _mapModuleNameForModule(String module, JSModule moduleContext) {
		JSPackage jsPackage = moduleContext.getJSPackage();

		HashMap<String, String> contextMap = new HashMap<>();

		for (String dependencyPackageName : moduleContext.getDependencyPackageNames()) {

			if (dependencyPackageName == null) {
				continue;
			}

			if (dependencyPackageName.equals(jsPackage.getName())) {
				contextMap.put(dependencyPackageName, jsPackage.getResolvedId());
			}
			else {
				JSPackageDependency dependency = jsPackage.getJSPackageDependency(dependencyPackageName);

				if (dependency == null) {
					String errorMessage = ":ERROR:Missing version constraints for " +
						dependencyPackageName +
						" in package.json of " +
						jsPackage.getResolvedId();

					contextMap.put(dependencyPackageName, errorMessage);
				}
				else {
					JSPackage packageDependency = _npmRegistry.resolveJSPackageDependency(dependency);

					if (packageDependency == null) {
						String errorMessage = ":ERROR:Package " +
							dependencyPackageName +
							" which is a dependency of " +
							jsPackage.getResolvedId() +
							" is not deployed in the server";

						contextMap.put(dependencyPackageName, errorMessage);
					}
					else {
						contextMap.put(packageDependency.getName(), packageDependency.getResolvedId());
					}
				}
			}
		}

		return _mapModuleName(module, contextMap);
	}

	private List<String> _moduleNotFoundMessage(String moduleName) {
		return Collections.singletonList(":ERROR: Module " + moduleName + " not found");
	}

	private List<String> _processModule(JSModule jsModule) {

		if (jsModule == null) {
			return Collections.emptyList();
		}

		Collection<String> dependencies = jsModule.getDependencies();

		String moduleResolvedId = jsModule.getResolvedId();

		ArrayList<String> dependenciesMap = dependencies.stream()
			.filter(_filterDependencies())
			.map(dependency -> PathResolver.resolvePath(moduleResolvedId, dependency))
			.map(d -> _mapModuleNameForModule(d, jsModule))
			.map(this::_processModule)
			.flatMap(Collection::stream)
			.collect(Collectors.toCollection(ArrayList::new));

		dependenciesMap.add(0, moduleResolvedId);

		return dependenciesMap;
	}

	private List<String> _processModule(String module) {
		JSModule jsModule = _npmRegistry.getResolvedJSModule(module);

		if (jsModule == null) {
				System.out.println("NOT FOUND: " + module);
			}

		return _processModule(jsModule);
	}

	private List<String> _resolve(String module) {

		String mappedModule = _mapModuleName(module);

		return _findInModulesTracker(mappedModule)
			.orElseGet(() -> _findInNPMRegistry(mappedModule).orElse(this._moduleNotFoundMessage(module)));
	}

	private JSLoaderModulesTracker _jsLoaderModulesTracker;

	private NPMRegistry _npmRegistry;
}
