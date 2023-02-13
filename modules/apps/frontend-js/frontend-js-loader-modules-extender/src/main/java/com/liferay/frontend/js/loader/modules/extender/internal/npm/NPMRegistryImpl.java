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
import com.liferay.frontend.js.loader.modules.extender.internal.npm.dynamic.DynamicJSModule;
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
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistryStateSnapshot;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistryUpdate;
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
import org.osgi.framework.InvalidSyntaxException;
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

	public void finishUpdate(NPMRegistryUpdateImpl npmRegistryUpdateImpl) {
		_refreshNPMRegistryStateSnapshot(null, null, npmRegistryUpdateImpl);
	}

	@Override
	public Map<String, String> getGlobalAliases() {
		return _npmRegistryStateSnapshotImpl.getGlobalAliases();
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
		Map<String, JSModule> jsModules =
			_npmRegistryStateSnapshotImpl.getJSModules();

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
		Map<String, JSPackage> jsPackages =
			_npmRegistryStateSnapshotImpl.getJSPackages();

		return jsPackages.get(identifier);
	}

	/**
	 * Returns all deployed NPM packages.
	 *
	 * @return the deployed NPM packages
	 */
	@Override
	public Collection<JSPackage> getJSPackages() {
		Map<String, JSPackage> jsPackages =
			_npmRegistryStateSnapshotImpl.getJSPackages();

		return jsPackages.values();
	}

	@Override
	public NPMRegistryStateSnapshot getNPMRegistryStateSnapshot() {
		return _npmRegistryStateSnapshotImpl;
	}

	/**
	 * Returns the resolved module with the ID.
	 *
	 * @param  identifier the resolved module's ID
	 * @return the resolved module with the ID
	 */
	@Override
	public JSModule getResolvedJSModule(String identifier) {
		return _npmRegistryStateSnapshotImpl.getResolvedJSModule(identifier);
	}

	/**
	 * Returns all resolved modules deployed to the portal.
	 *
	 * @return the resolved modules deployed to the portal
	 */
	@Override
	public Collection<JSModule> getResolvedJSModules() {
		Map<String, JSModule> resolvedJSModules =
			_npmRegistryStateSnapshotImpl.getResolvedJSModules();

		return resolvedJSModules.values();
	}

	@Override
	public JSPackage getResolvedJSPackage(String identifier) {
		return _npmRegistryStateSnapshotImpl.getResolvedJSPackage(identifier);
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
			_npmRegistryStateSnapshotImpl.getResolvedJSPackages();

		return resolvedJSPackages.values();
	}

	@Override
	public String mapModuleName(String moduleName) {
		return _npmRegistryStateSnapshotImpl.mapModuleName(moduleName);
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

		return _npmRegistryStateSnapshotImpl.resolveJSPackageDependency(
			jsPackageDependency);
	}

	@Override
	public NPMRegistryUpdate update() {
		return new NPMRegistryUpdateImpl(this);
	}

	@Activate
	protected void activate(
			BundleContext bundleContext, Map<String, Object> properties)
		throws InvalidSyntaxException {

		_bundleContext = bundleContext;

		_bundleTracker = new BundleTracker<>(
			_bundleContext, Bundle.ACTIVE,
			new NPMRegistryBundleTrackerCustomizer());

		_serviceTracker = new ServiceTracker<>(
			_bundleContext,
			bundleContext.createFilter(
				"(&(objectClass=" + ServletContext.class.getName() +
					")(osgi.web.contextpath=*))"),
			new NPMRegistryServiceTrackerCustomizer());

		Details details = ConfigurableUtil.createConfigurable(
			Details.class, properties);

		_applyVersioning = details.applyVersioning();

		_activationThreadLocal.set(Boolean.TRUE);

		_bundleTracker.open();

		_serviceTracker.open();

		_activationThreadLocal.set(Boolean.FALSE);

		_refreshNPMRegistryStateSnapshot(null, null, null);

		_javaScriptAwarePortalWebResources = ServiceTrackerListFactory.open(
			bundleContext, JavaScriptAwarePortalWebResources.class);
	}

	@Deactivate
	protected void deactivate() {
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

	private void _refreshNPMRegistryStateSnapshot(
		Map<Bundle, JSBundle> jsBundlesMap,
		Collection<JSConfigGeneratorPackage> jsConfigGeneratorPackages,
		NPMRegistryUpdateImpl npmRegistryUpdateImpl) {

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
					if (npmRegistryUpdateImpl != null) {
						if (npmRegistryUpdateImpl.isUnregistered(
								jsModule.getId())) {

							continue;
						}

						NPMRegistryUpdateImpl.JSModuleUpdate jsModuleUpdate =
							npmRegistryUpdateImpl.getJSModuleUpdate(
								jsModule.getId());

						if (jsModuleUpdate != null) {
							jsModule = new DynamicJSModule(
								jsPackage, jsModuleUpdate.getModuleName(),
								jsModuleUpdate.getDependencies(),
								jsModuleUpdate.getJS(),
								jsModuleUpdate.getMap());
						}
					}

					jsModules.put(jsModule.getId(), jsModule);
					resolvedJSModules.put(jsModule.getResolvedId(), jsModule);
				}

				if (npmRegistryUpdateImpl != null) {
					for (NPMRegistryUpdateImpl.JSModuleRegistration
							jsModuleRegistration :
								npmRegistryUpdateImpl.getJSModuleRegistrations(
									jsPackage)) {

						JSModule jsModule = new DynamicJSModule(
							jsPackage, jsModuleRegistration.getModuleName(),
							jsModuleRegistration.getDependencies(),
							jsModuleRegistration.getJS(),
							jsModuleRegistration.getMap());

						if (jsModules.containsKey(jsModule.getName())) {
							throw new IllegalStateException(
								StringBundler.concat(
									"Unable to register dynamic module ",
									jsModule.getId(),
									": a JS module with the same name already ",
									"exists"));
						}

						jsModules.put(jsModule.getId(), jsModule);
						resolvedJSModules.put(
							jsModule.getResolvedId(), jsModule);
					}
				}
			}
		}

		Comparator<JSPackageVersion> comparator = Comparator.comparing(
			JSPackageVersion::getVersion);

		jsPackageVersions.sort(comparator.reversed());

		_npmRegistryStateSnapshotImpl = new NPMRegistryStateSnapshotImpl(
			exactMatchMap, globalAliases, jsModules, jsPackages,
			jsPackageVersions, partialMatchMap, resolvedJSModules,
			resolvedJSPackages);
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

	@Reference
	private JSONFactory _jsonFactory;

	private volatile NPMRegistryStateSnapshotImpl
		_npmRegistryStateSnapshotImpl = new NPMRegistryStateSnapshotImpl(
			Collections.emptyMap(), Collections.emptyMap(),
			Collections.emptyMap(), Collections.emptyMap(),
			Collections.emptyList(), Collections.emptyMap(),
			Collections.emptyMap(), Collections.emptyMap());
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
				_refreshNPMRegistryStateSnapshot(
					HashMapBuilder.create(
						_bundleTracker.getTracked()
					).put(
						bundle, jsBundle
					).build(),
					null, null);

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
				_refreshNPMRegistryStateSnapshot(null, null, null);
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

			_refreshNPMRegistryStateSnapshot(
				null, jsConfigGeneratorPackages, null);

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

			_refreshNPMRegistryStateSnapshot(null, null, null);
		}

	}

}