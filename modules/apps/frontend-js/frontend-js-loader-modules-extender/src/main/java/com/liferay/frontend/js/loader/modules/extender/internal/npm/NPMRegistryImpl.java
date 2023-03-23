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

package com.liferay.frontend.js.loader.modules.extender.internal.npm;

import com.liferay.frontend.js.loader.modules.extender.internal.config.generator.JSConfigGeneratorPackage;
import com.liferay.frontend.js.loader.modules.extender.internal.configuration.Details;
import com.liferay.frontend.js.loader.modules.extender.npm.JSBundle;
import com.liferay.frontend.js.loader.modules.extender.npm.JSBundleProcessor;
import com.liferay.frontend.js.loader.modules.extender.npm.JSBundleTracker;
import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSModuleAlias;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackageDependency;
import com.liferay.frontend.js.loader.modules.extender.npm.JavaScriptAwarePortalWebResources;
import com.liferay.frontend.js.loader.modules.extender.npm.ModuleNameUtil;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistryUpdate;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistryUpdatesListener;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * Provides the central class for the NPM package support of Liferay Portal.
 * This class features a central registry where all NPM packages and modules
 * deployed with OSGi bundles are tracked.
 *
 * @author Iván Zaera
 */
@Component(
	configurationPid = "com.liferay.frontend.js.loader.modules.extender.internal.configuration.Details",
	service = NPMRegistry.class
)
public class NPMRegistryImpl implements NPMRegistry {

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public void addJSBundleTracker(JSBundleTracker jsBundleTracker) {
	}

	public void finishUpdate(NPMRegistryUpdate npmRegistryUpdate) {
		_refreshJSModuleCaches(null, null, _getNPMRegistryUpdatesListeners());
	}

	@Override
	public Map<String, String> getGlobalAliases() {
		return _jsModulesCache.getGlobalAliases();
	}

	/**
	 * Returns the OSGi bundles containing NPM packages that have been deployed
	 * to the portal.
	 *
	 * @return the OSGi bundles
	 */
	public Collection<JSBundle> getJSBundles() {
		Map<Bundle, JSBundle> tracked = _bundleTracker.getTracked();

		return tracked.values();
	}

	/**
	 * Returns the NPM module descriptor with the ID.
	 *
	 * @param  identifier the NPM module's ID
	 * @return the NPM module descriptor with the ID
	 */
	@Override
	public JSModule getJSModule(String identifier) {
		Map<String, JSModule> jsModules = _jsModulesCache.getJSModules();

		return jsModules.get(identifier);
	}

	/**
	 * Returns the NPM package with the ID.
	 *
	 * @param  identifier the NPM package's ID
	 * @return the NPM package descriptor with the ID
	 */
	@Override
	public JSPackage getJSPackage(String identifier) {
		Map<String, JSPackage> jsPackages = _jsModulesCache.getJSPackages();

		return jsPackages.get(identifier);
	}

	/**
	 * Returns all deployed NPM packages.
	 *
	 * @return the deployed NPM packages
	 */
	@Override
	public Collection<JSPackage> getJSPackages() {
		Map<String, JSPackage> jsPackages = _jsModulesCache.getJSPackages();

		return jsPackages.values();
	}

	@Override
	public String getResolutionStateDigest() {
		return _jsModulesCache.getResolutionStateDigest();
	}

	/**
	 * Returns the resolved module with the ID.
	 *
	 * @param  identifier the resolved module's ID
	 * @return the resolved module with the ID
	 */
	@Override
	public JSModule getResolvedJSModule(String identifier) {
		Map<String, JSModule> resolvedJSModules =
			_jsModulesCache.getResolvedJSModules();

		return resolvedJSModules.get(identifier);
	}

	/**
	 * Returns all resolved modules deployed to the portal.
	 *
	 * @return the resolved modules deployed to the portal
	 */
	@Override
	public Collection<JSModule> getResolvedJSModules() {
		Map<String, JSModule> resolvedJSModules =
			_jsModulesCache.getResolvedJSModules();

		return resolvedJSModules.values();
	}

	@Override
	public JSPackage getResolvedJSPackage(String identifier) {
		Map<String, JSPackage> resolvedJSPackages =
			_jsModulesCache.getResolvedJSPackages();

		return resolvedJSPackages.get(identifier);
	}

	/**
	 * Returns all resolved packages deployed to the portal.
	 *
	 * @return the resolved packages deployed to the portal
	 * @review
	 */
	@Override
	public Collection<JSPackage> getResolvedJSPackages() {
		Map<String, JSPackage> resolvedJSPackages =
			_jsModulesCache.getResolvedJSPackages();

		return resolvedJSPackages.values();
	}

	@Override
	public String mapModuleName(String moduleName) {
		Map<String, String> exactMatchMap = _jsModulesCache.getExactMatchMap();

		String mappedModuleName = exactMatchMap.get(moduleName);

		if (Validator.isNotNull(mappedModuleName)) {
			return mapModuleName(mappedModuleName);
		}

		Map<String, String> globalAliases = _jsModulesCache.getGlobalAliases();

		for (Map.Entry<String, String> entry : globalAliases.entrySet()) {
			String resolvedId = entry.getKey();

			if (resolvedId.equals(moduleName) ||
				moduleName.startsWith(resolvedId + StringPool.SLASH)) {

				return mapModuleName(
					entry.getValue() +
						moduleName.substring(resolvedId.length()));
			}
		}

		Map<String, String> partialMatchMap =
			_jsModulesCache.getPartialMatchMap();

		for (Map.Entry<String, String> entry : partialMatchMap.entrySet()) {
			String resolvedId = entry.getKey();

			if (resolvedId.equals(moduleName) ||
				moduleName.startsWith(resolvedId + StringPool.SLASH)) {

				return mapModuleName(
					entry.getValue() +
						moduleName.substring(resolvedId.length()));
			}
		}

		return moduleName;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public void removeJSBundleTracker(JSBundleTracker jsBundleTracker) {
	}

	@Override
	public JSPackage resolveJSPackageDependency(
		JSPackageDependency jsPackageDependency) {

		return _jsModulesCache.resolveJSPackageDependency(jsPackageDependency);
	}

	@Override
	public NPMRegistryUpdate update() {
		return new NPMRegistryUpdateImpl(this);
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_bundleContext = bundleContext;

		_bundleTracker = new BundleTracker<>(
			_bundleContext, Bundle.ACTIVE,
			new NPMRegistryBundleTrackerCustomizer());

		_serviceTracker = new ServiceTracker<>(
			_bundleContext,
			"(&(objectClass=" + ServletContext.class.getName() +
				")(osgi.web.contextpath=*))",
			new NPMRegistryServiceTrackerCustomizer());

		Details details = ConfigurableUtil.createConfigurable(
			Details.class, properties);

		_applyVersioning = details.applyVersioning();

		_activationThreadLocal.set(Boolean.TRUE);

		_bundleTracker.open();

		_serviceTracker.open();

		_activationThreadLocal.set(Boolean.FALSE);

		_refreshJSModuleCaches(null, null, null);

		_javaScriptAwarePortalWebResources = ServiceTrackerListFactory.open(
			bundleContext, JavaScriptAwarePortalWebResources.class);
	}

	@Deactivate
	protected void deactivate() {
		if (_npmRegistryUpdatesListeners != null) {
			_npmRegistryUpdatesListeners.close();
		}

		_javaScriptAwarePortalWebResources.close();

		_serviceTracker.close();

		_bundleTracker.close();
	}

	@Modified
	protected void modified(Map<String, Object> properties) {
		Details details = ConfigurableUtil.createConfigurable(
			Details.class, properties);

		if (details.applyVersioning() != _applyVersioning) {
			_applyVersioning = details.applyVersioning();

			_serviceTracker.close();

			_serviceTracker.open();
		}
	}

	private ServiceTrackerList<NPMRegistryUpdatesListener>
		_getNPMRegistryUpdatesListeners() {

		if (_npmRegistryUpdatesListeners == null) {
			_npmRegistryUpdatesListeners = ServiceTrackerListFactory.open(
				_bundleContext, NPMRegistryUpdatesListener.class);
		}

		return _npmRegistryUpdatesListeners;
	}

	private JSONObject _getPackageJSONObject(Bundle bundle) {
		try {
			URL url = bundle.getEntry("package.json");

			if (url == null) {
				return null;
			}

			String content;

			try {
				content = StringUtil.read(url.openStream());
			}
			catch (IOException ioException) {
				if (_log.isDebugEnabled()) {
					_log.debug(ioException);
				}

				return null;
			}

			if (content == null) {
				return null;
			}

			return _jsonFactory.createJSONObject(content);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return null;
		}
	}

	private void _processLegacyBridges(
		Bundle bundle, Map<String, String> globalAliases) {

		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		String jsSubmodulesBridge = GetterUtil.getString(
			headers.get("Liferay-JS-Submodules-Bridge"));

		if (Validator.isNotNull(jsSubmodulesBridge)) {
			String[] bridges = jsSubmodulesBridge.split(",");

			JSONObject packageJSONObject = _getPackageJSONObject(bundle);

			for (String bridge : bridges) {
				bridge = bridge.trim();

				globalAliases.put(
					bridge,
					StringBundler.concat(
						packageJSONObject.getString("name"), StringPool.AT,
						packageJSONObject.getString("version"), "/bridge/",
						bridge));
			}
		}
	}

	private void _refreshJSModuleCaches(
		Map<Bundle, JSBundle> jsBundlesMap,
		Collection<JSConfigGeneratorPackage> jsConfigGeneratorPackages,
		ServiceTrackerList<NPMRegistryUpdatesListener>
			npmRegistryUpdatesListeners) {

		if (jsBundlesMap == null) {
			jsBundlesMap = _bundleTracker.getTracked();
		}

		if (jsConfigGeneratorPackages == null) {
			SortedMap
				<ServiceReference<ServletContext>, JSConfigGeneratorPackage>
					tracked = _serviceTracker.getTracked();

			jsConfigGeneratorPackages = tracked.values();
		}

		Map<String, String> globalAliases = new HashMap<>();
		Map<String, JSModule> jsModules = new HashMap<>();
		Map<String, JSPackage> jsPackages = new HashMap<>();
		List<JSPackageVersion> jsPackageVersions = new ArrayList<>();
		Map<String, String> partialMatchMap = new HashMap<>();
		Map<String, JSModule> resolvedJSModules = new HashMap<>();
		Map<String, JSPackage> resolvedJSPackages = new HashMap<>();
		Map<String, String> exactMatchMap = new HashMap<>();

		for (JSConfigGeneratorPackage jsConfigGeneratorPackage :
				jsConfigGeneratorPackages) {

			String jsConfigGeneratorPackageResolvedId =
				jsConfigGeneratorPackage.getName() + StringPool.AT +
					jsConfigGeneratorPackage.getVersion();

			partialMatchMap.put(
				jsConfigGeneratorPackage.getName(),
				jsConfigGeneratorPackageResolvedId);
		}

		for (Bundle bundle : jsBundlesMap.keySet()) {
			_processLegacyBridges(bundle, globalAliases);
		}

		for (JSBundle jsBundle : jsBundlesMap.values()) {
			for (JSPackage jsPackage : jsBundle.getJSPackages()) {
				jsPackages.put(jsPackage.getId(), jsPackage);
				jsPackageVersions.add(new JSPackageVersion(jsPackage));

				String resolvedId = jsPackage.getResolvedId();

				resolvedJSPackages.put(resolvedId, jsPackage);

				exactMatchMap.put(
					resolvedId,
					ModuleNameUtil.getModuleResolvedId(
						jsPackage, jsPackage.getMainModuleName()));

				for (JSModuleAlias jsModuleAlias :
						jsPackage.getJSModuleAliases()) {

					String aliasResolvedId = ModuleNameUtil.getModuleResolvedId(
						jsPackage, jsModuleAlias.getAlias());

					exactMatchMap.put(
						aliasResolvedId,
						ModuleNameUtil.getModuleResolvedId(
							jsPackage, jsModuleAlias.getModuleName()));
				}

				for (JSModule jsModule : jsPackage.getJSModules()) {
					jsModules.put(jsModule.getId(), jsModule);
					resolvedJSModules.put(jsModule.getResolvedId(), jsModule);
				}
			}
		}

		Comparator<JSPackageVersion> comparator = Comparator.comparing(
			JSPackageVersion::getVersion);

		jsPackageVersions.sort(comparator.reversed());

		_jsModulesCache = new JSModulesCache(
			globalAliases, exactMatchMap, jsModules, jsPackages,
			jsPackageVersions, partialMatchMap, resolvedJSModules,
			resolvedJSPackages);

		if (npmRegistryUpdatesListeners != null) {
			for (NPMRegistryUpdatesListener npmRegistryUpdatesListener :
					npmRegistryUpdatesListeners) {

				npmRegistryUpdatesListener.onAfterUpdate();
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NPMRegistryImpl.class);

	private static final ThreadLocal<Boolean> _activationThreadLocal =
		new CentralizedThreadLocal<>(
			NPMRegistryImpl.class.getName() + "._activationThreadLocal",
			() -> Boolean.FALSE);

	private volatile Boolean _applyVersioning;
	private BundleContext _bundleContext;
	private BundleTracker<JSBundle> _bundleTracker;
	private ServiceTrackerList<JavaScriptAwarePortalWebResources>
		_javaScriptAwarePortalWebResources;

	@Reference
	private JSBundleProcessor _jsBundleProcessor;

	private volatile JSModulesCache _jsModulesCache = new JSModulesCache(
		Collections.emptyMap(), Collections.emptyMap(), Collections.emptyMap(),
		Collections.emptyMap(), Collections.emptyList(), Collections.emptyMap(),
		Collections.emptyMap(), Collections.emptyMap());

	@Reference
	private JSONFactory _jsonFactory;

	private ServiceTrackerList<NPMRegistryUpdatesListener>
		_npmRegistryUpdatesListeners;
	private volatile ServiceTracker<ServletContext, JSConfigGeneratorPackage>
		_serviceTracker;

	private class NPMRegistryBundleTrackerCustomizer
		implements BundleTrackerCustomizer<JSBundle> {

		@Override
		public JSBundle addingBundle(Bundle bundle, BundleEvent bundleEvent) {
			JSBundle jsBundle = _jsBundleProcessor.process(bundle);

			if (jsBundle == null) {
				return null;
			}

			if (!_activationThreadLocal.get()) {
				_refreshJSModuleCaches(
					HashMapBuilder.create(
						_bundleTracker.getTracked()
					).put(
						bundle, jsBundle
					).build(),
					null, _getNPMRegistryUpdatesListeners());

				for (JavaScriptAwarePortalWebResources
						javaScriptAwarePortalWebResources :
							_javaScriptAwarePortalWebResources) {

					javaScriptAwarePortalWebResources.updateLastModifed(
						bundle.getLastModified());
				}
			}

			return jsBundle;
		}

		@Override
		public void modifiedBundle(
			Bundle bundle, BundleEvent bundleEvent, JSBundle jsBundle) {
		}

		@Override
		public void removedBundle(
			Bundle bundle, BundleEvent bundleEvent, JSBundle jsBundle) {

			if (!_activationThreadLocal.get()) {
				_refreshJSModuleCaches(
					null, null, _getNPMRegistryUpdatesListeners());
			}
		}

	}

	private class NPMRegistryServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ServletContext, JSConfigGeneratorPackage> {

		@Override
		public JSConfigGeneratorPackage addingService(
			ServiceReference<ServletContext> serviceReference) {

			Bundle bundle = serviceReference.getBundle();

			URL url = bundle.getEntry(Details.CONFIG_JSON);

			if (url == null) {
				return null;
			}

			JSConfigGeneratorPackage jsConfigGeneratorPackage =
				new JSConfigGeneratorPackage(
					_applyVersioning, serviceReference.getBundle(),
					(String)serviceReference.getProperty(
						"osgi.web.contextpath"));

			SortedMap
				<ServiceReference<ServletContext>, JSConfigGeneratorPackage>
					tracked = _serviceTracker.getTracked();

			ArrayList<JSConfigGeneratorPackage> jsConfigGeneratorPackages =
				new ArrayList<>(tracked.values());

			jsConfigGeneratorPackages.add(jsConfigGeneratorPackage);

			_refreshJSModuleCaches(
				null, jsConfigGeneratorPackages,
				_getNPMRegistryUpdatesListeners());

			return jsConfigGeneratorPackage;
		}

		@Override
		public void modifiedService(
			ServiceReference<ServletContext> serviceReference,
			JSConfigGeneratorPackage jsConfigGeneratorPackage) {
		}

		@Override
		public void removedService(
			ServiceReference<ServletContext> serviceReference,
			JSConfigGeneratorPackage jsConfigGeneratorPackage) {

			_refreshJSModuleCaches(
				null, null, _getNPMRegistryUpdatesListeners());
		}

	}

}