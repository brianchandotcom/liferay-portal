/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.frontend.js.loader.modules.extender.internal.resolution;

import com.liferay.frontend.js.loader.modules.extender.internal.Details;
import com.liferay.frontend.js.loader.modules.extender.internal.config.generator.JSConfigGeneratorModule;
import com.liferay.frontend.js.loader.modules.extender.internal.config.generator.JSConfigGeneratorPackage;
import com.liferay.frontend.js.loader.modules.extender.internal.config.generator.JSConfigGeneratorPackagesTracker;
import com.liferay.frontend.js.loader.modules.extender.internal.resolution.descriptor.JSConfigGeneratorModuleDescriptor;
import com.liferay.frontend.js.loader.modules.extender.internal.resolution.descriptor.JSModuleDescriptor;
import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.ModuleNameUtil;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodolfo Roza Miranda
 */
@Component(
	configurationPid = "com.liferay.frontend.js.loader.modules.extender.internal.Details",
	immediate = true, service = JSModulesResolver.class
)
public class JSModulesResolver {

	public JSModulesResolution resolve(List<String> moduleNames) {
		JSModulesResolution jsModulesResolution = new JSModulesResolution(
			_details.explainResolutions());

		Map<String, ModuleDescriptor> moduleDescriptors =
			_getAllModuleDescriptors();

		for (String moduleName : moduleNames) {
			_resolve(moduleDescriptors, moduleName, jsModulesResolution);
		}

		return jsModulesResolution;
	}

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

		List<JSConfigGeneratorModuleDescriptor>
			jsConfigGeneratorModuleDescriptors =
				jsConfigGeneratorPackagesStream.reduce(
					new ArrayList<>(),
					(arrayList, jsConfigGeneratorPackage) -> {
						for (JSConfigGeneratorModule jsConfigGeneratorModule :
								jsConfigGeneratorPackage.
									getJSConfigGeneratorModules()) {

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

		for (JSConfigGeneratorModuleDescriptor
				jsConfigGeneratorModuleDescriptor :
					jsConfigGeneratorModuleDescriptors) {

			moduleDescriptors.put(
				jsConfigGeneratorModuleDescriptor.getName(),
				jsConfigGeneratorModuleDescriptor);
		}

		for (JSModule jsModule : _npmRegistry.getResolvedJSModules()) {
			JSModuleDescriptor jsModuleDescriptor = new JSModuleDescriptor(
				jsModule, _npmRegistry);

			moduleDescriptors.put(
				jsModuleDescriptor.getName(), jsModuleDescriptor);
		}

		return moduleDescriptors;
	}

	private boolean _processModule(
		Map<String, ModuleDescriptor> moduleDescriptors,
		ModuleDescriptor moduleDescriptor,
		JSModulesResolution jsModulesResolution) {

		String moduleName = moduleDescriptor.getName();

		if (jsModulesResolution.isProcessedModule(moduleName)) {
			return false;
		}

		jsModulesResolution.addProcessedModule(moduleName);

		Map<String, String> dependenciesMap = new HashMap<>();

		jsModulesResolution.indentExplanation();

		for (String dependency : moduleDescriptor.getDependencies()) {
			if (ModuleNameUtil.isReservedModuleName(dependency)) {
				continue;
			}

			String resolvedPath = ModuleNameUtil.resolvePath(
				moduleName, dependency);

			String mappedModuleName = _mapper.mapModule(
				resolvedPath, moduleDescriptor.getMappings());

			dependenciesMap.put(dependency, mappedModuleName);

			ModuleDescriptor dependencyModuleDescriptor = moduleDescriptors.get(
				mappedModuleName);

			if (dependencyModuleDescriptor != null) {
				_processModule(
					moduleDescriptors, dependencyModuleDescriptor,
					jsModulesResolution);
			}
		}

		jsModulesResolution.dedentExplanation();

		jsModulesResolution.putModuleMapping(moduleName, dependenciesMap);
		jsModulesResolution.putPath(moduleName, moduleDescriptor.getPath());
		jsModulesResolution.addResolvedModule(moduleName);

		return true;
	}

	private void _resolve(
		Map<String, ModuleDescriptor> moduleDescriptors, String moduleName,
		JSModulesResolution jsModulesResolution) {

		String mappedModuleName = _mapper.mapModule(moduleName);

		ModuleDescriptor moduleDescriptor = moduleDescriptors.get(
			mappedModuleName);

		if (moduleDescriptor != null) {
			jsModulesResolution.putGlobalMapping(moduleName, mappedModuleName);

			_processModule(
				moduleDescriptors, moduleDescriptor, jsModulesResolution);
		}
	}

	private Details _details;

	@Reference
	private JSConfigGeneratorPackagesTracker _jsConfigGeneratorPackagesTracker;

	@Reference
	private JSModulesNameMapper _mapper;

	@Reference
	private NPMRegistry _npmRegistry;

	@Reference
	private Portal _portal;

}