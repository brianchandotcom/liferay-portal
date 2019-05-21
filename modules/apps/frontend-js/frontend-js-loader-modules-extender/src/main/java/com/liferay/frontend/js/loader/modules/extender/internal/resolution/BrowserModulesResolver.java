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

import com.liferay.frontend.js.loader.modules.extender.internal.config.generator.JSConfigGeneratorModule;
import com.liferay.frontend.js.loader.modules.extender.internal.config.generator.JSConfigGeneratorPackage;
import com.liferay.frontend.js.loader.modules.extender.internal.config.generator.JSConfigGeneratorPackagesTracker;
import com.liferay.frontend.js.loader.modules.extender.internal.configuration.Details;
import com.liferay.frontend.js.loader.modules.extender.internal.resolution.adapter.JSBrowserModule;
import com.liferay.frontend.js.loader.modules.extender.internal.resolution.adapter.JSConfigGeneratorBrowserModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSModuleAlias;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.ModuleNameUtil;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.Portal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodolfo Roza Miranda
 */
@Component(
	configurationPid = "com.liferay.frontend.js.loader.modules.extender.internal.Details",
	immediate = true, service = BrowserModulesResolver.class
)
public class BrowserModulesResolver {

	public BrowserModulesResolution resolve(List<String> moduleNames) {
		BrowserModulesResolution browserModulesResolution =
			new BrowserModulesResolution(
				_jsonFactory, _details.explainResolutions());

		Map<String, BrowserModule> browserModulesMap = _getBrowserModulesMap();

		for (String moduleName : moduleNames) {
			_resolve(browserModulesMap, moduleName, browserModulesResolution);
		}

		_populateMappedModuleNames(browserModulesResolution);

		return browserModulesResolution;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_details = ConfigurableUtil.createConfigurable(
			Details.class, properties);
	}

	private Map<String, BrowserModule> _getBrowserModulesMap() {
		Map<String, BrowserModule> browserModulesMap = new HashMap<>();

		for (JSConfigGeneratorPackage jsConfigGeneratorPackage :
				_jsConfigGeneratorPackagesTracker.
					getJSConfigGeneratorPackages()) {

			for (JSConfigGeneratorModule jsConfigGeneratorModule :
					jsConfigGeneratorPackage.getJSConfigGeneratorModules()) {

				JSConfigGeneratorBrowserModule jsConfigGeneratorBrowserModule =
					new JSConfigGeneratorBrowserModule(jsConfigGeneratorModule);

				browserModulesMap.put(
					jsConfigGeneratorBrowserModule.getName(),
					jsConfigGeneratorBrowserModule);
			}
		}

		for (JSModule jsModule : _npmRegistry.getResolvedJSModules()) {
			JSBrowserModule jsBrowserModule = new JSBrowserModule(
				jsModule, _npmRegistry);

			browserModulesMap.put(jsBrowserModule.getName(), jsBrowserModule);
		}

		return browserModulesMap;
	}

	private void _populateMappedModuleNames(
		BrowserModulesResolution browserModulesResolution) {

		Set<JSPackage> jsPackages = new HashSet<>();

		for (String moduleName :
				browserModulesResolution.getResolvedModuleNames()) {

			String packageName = ModuleNameUtil.getPackageName(moduleName);

			JSPackage jsPackage = _npmRegistry.getResolvedJSPackage(
				packageName);

			if (jsPackage == null) {
				continue;
			}

			jsPackages.add(jsPackage);
		}

		for (JSPackage jsPackage : jsPackages) {
			for (JSModuleAlias jsModuleAlias : jsPackage.getJSModuleAliases()) {
				browserModulesResolution.putMappedModuleName(
					jsPackage.getResolvedId() + StringPool.SLASH +
						jsModuleAlias.getAlias(),
					jsPackage.getResolvedId() + StringPool.SLASH +
						jsModuleAlias.getModuleName(),
					true);
			}
		}
	}

	private boolean _processBrowserModule(
		Map<String, BrowserModule> browserModulesMap,
		BrowserModule browserModule,
		BrowserModulesResolution browserModulesResolution) {

		String moduleName = browserModule.getName();

		if (browserModulesResolution.isProcessedModuleName(moduleName)) {
			return false;
		}

		browserModulesResolution.addProcessedModuleName(moduleName);

		Map<String, String> dependenciesMap = new HashMap<>();

		browserModulesResolution.indentExplanation();

		for (String dependency : browserModule.getDependencies()) {
			if (ModuleNameUtil.isReservedModuleName(dependency)) {
				continue;
			}

			String dependencyModuleName = ModuleNameUtil.getDependencyPath(
				moduleName, dependency);

			dependencyModuleName = _browserModuleNameMapper.mapModuleName(
				dependencyModuleName, browserModule.getDependenciesMap());

			dependenciesMap.put(dependency, dependencyModuleName);

			BrowserModule dependencyBrowserModule = browserModulesMap.get(
				dependencyModuleName);

			if (dependencyBrowserModule != null) {
				_processBrowserModule(
					browserModulesMap, dependencyBrowserModule,
					browserModulesResolution);
			}
			else {
				browserModulesResolution.addResolvedModuleName(
					StringBundler.concat(
						":ERROR:Missing dependency '", dependencyModuleName,
						"' of '", moduleName, "'"));
			}
		}

		browserModulesResolution.dedentExplanation();

		browserModulesResolution.putDependenciesMap(
			moduleName, dependenciesMap);
		browserModulesResolution.putPath(moduleName, browserModule.getPath());

		browserModulesResolution.addResolvedModuleName(moduleName);

		return true;
	}

	private void _resolve(
		Map<String, BrowserModule> browserModulesMap, String moduleName,
		BrowserModulesResolution browserModulesResolution) {

		String mappedModuleName = _browserModuleNameMapper.mapModuleName(
			moduleName);

		BrowserModule browserModule = browserModulesMap.get(mappedModuleName);

		if (browserModule == null) {
			browserModulesResolution.addResolvedModuleName(
				":ERROR:Missing required module '" + moduleName + "'");

			return;
		}

		if (!moduleName.equals(mappedModuleName)) {
			browserModulesResolution.putMappedModuleName(
				moduleName, mappedModuleName, true);
		}

		_processBrowserModule(
			browserModulesMap, browserModule, browserModulesResolution);
	}

	@Reference
	private BrowserModuleNameMapper _browserModuleNameMapper;

	private Details _details;

	@Reference
	private JSConfigGeneratorPackagesTracker _jsConfigGeneratorPackagesTracker;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private NPMRegistry _npmRegistry;

	@Reference
	private Portal _portal;

}