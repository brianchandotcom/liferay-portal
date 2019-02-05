package com.liferay.frontend.js.loader.modules.extender.internal.resolution;

import com.liferay.frontend.js.loader.modules.extender.internal.Details;
import com.liferay.frontend.js.loader.modules.extender.internal.resolution.descriptor.JSConfigGeneratorModuleDescriptor;
import com.liferay.frontend.js.loader.modules.extender.internal.resolution.descriptor.JSModuleDescriptor;
import com.liferay.frontend.js.loader.modules.extender.internal.config.generator.JSConfigGeneratorModule;
import com.liferay.frontend.js.loader.modules.extender.internal.config.generator.JSConfigGeneratorPackage;
import com.liferay.frontend.js.loader.modules.extender.internal.config.generator.JSConfigGeneratorPackagesTracker;
import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.ModuleNameUtil;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.Portal;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Rodolfo Roza Miranda
 */
@Component(
	configurationPid = "com.liferay.frontend.js.loader.modules.extender.internal.Details",
	immediate = true,
	service = JSModulesResolver.class
)
public class JSModulesResolver {

	public JSModulesResolution resolve(List<String> modules) {
		JSModulesResolution context = new JSModulesResolution(
			_details.explainResolutions());

		Map<String, ModuleDescriptor> moduleDescriptors =
			_getAllModuleDescriptors();

		for (String module : modules) {
			_resolve(moduleDescriptors, module, context);
		}

		return context;
	}

	private Details _details;

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_details = ConfigurableUtil.createConfigurable(
			Details.class, properties);
	}

	private Map<String, ModuleDescriptor> _getAllModuleDescriptors() {
		Collection<JSConfigGeneratorPackage> jsConfigGeneratorPackages =
			_jsConfigGeneratorPackagesTracker.getJSConfigGeneratorPackages();

		Stream<JSConfigGeneratorPackage> jsConfigGeneratorPackagesStream =
			jsConfigGeneratorPackages.stream();

		List<JSConfigGeneratorModuleDescriptor> jsConfigGeneratorModuleAdapters =
			jsConfigGeneratorPackagesStream.reduce(
				new ArrayList<>(),
				(arrayList, pkg) -> {
					for (JSConfigGeneratorModule jsConfigGeneratorModule :
						pkg.getJSConfigGeneratorModules()) {

						arrayList.add(
							new JSConfigGeneratorModuleDescriptor(
								jsConfigGeneratorModule));
					}

					return arrayList;
				},
				(arrayList1, arrayList2) -> {
					ArrayList<JSConfigGeneratorModuleDescriptor> result =
						new ArrayList<>(arrayList1);

					result.addAll(arrayList2);

					return result;
				});

		Map<String, ModuleDescriptor> moduleDescriptors = new HashMap<>();

		for(ModuleDescriptor moduleDescriptor :
			jsConfigGeneratorModuleAdapters) {

			moduleDescriptors.put(
				moduleDescriptor.getName(), moduleDescriptor);
		}

		for(JSModule jsModule : _npmRegistry.getResolvedJSModules()) {
			JSModuleDescriptor npmRegistryModuleDescriptor =
				new JSModuleDescriptor(jsModule, _npmRegistry);

			moduleDescriptors.put(
				npmRegistryModuleDescriptor.getName(),
				npmRegistryModuleDescriptor);
		}

		return moduleDescriptors;
	}

	private boolean _processModule(
		Map<String, ModuleDescriptor> moduleDescriptors,
		ModuleDescriptor adapter, JSModulesResolution context) {

		String alias = adapter.getName();

		if (context.processedModule(alias)) {
			return false;
		}

		context.addProcessedModule(alias);

		Collection<String> dependencies = adapter.getDependencies();

		Map<String, String> dependenciesMap = new ConcurrentHashMap<>();

		context.indentExplanation();

		for (String dependency : dependencies) {
			if (ModuleNameUtil.isReservedModuleName(dependency)) {
				continue;
			}

			String resolvedPath = ModuleNameUtil.resolvePath(alias, dependency);

			String mappedModuleName =
				_mapper.mapModule(
					resolvedPath,
					adapter.getMappings());

			dependenciesMap.put(dependency, mappedModuleName);

			ModuleDescriptor dependencyModuleDescriptor =
				moduleDescriptors.get(mappedModuleName);

			if (dependencyModuleDescriptor != null) {
				_processModule(
					moduleDescriptors, dependencyModuleDescriptor,
					context);
			}
		}

		context.dedentExplanation();

		context.putPath(alias, adapter.getPath());
		context.putModuleDependencyMap(alias, dependenciesMap);
		context.addResolvedModule(alias);

		return true;
	}

	private void _resolve(Map<String, ModuleDescriptor> moduleDescriptors, String module, JSModulesResolution context) {
		String mappedModuleName = _mapper.mapModule(module);

		ModuleDescriptor moduleDescriptor =
			moduleDescriptors.get(mappedModuleName);

		if (moduleDescriptor != null) {
			context.putConfig(module, mappedModuleName);

			_processModule(
				moduleDescriptors, moduleDescriptor, context);
		}
	}

	@Reference
	private JSConfigGeneratorPackagesTracker _jsConfigGeneratorPackagesTracker;

	@Reference
	private JSModulesNameMapper _mapper;

	@Reference
	private NPMRegistry _npmRegistry;

	@Reference
	private Portal _portal;

}
