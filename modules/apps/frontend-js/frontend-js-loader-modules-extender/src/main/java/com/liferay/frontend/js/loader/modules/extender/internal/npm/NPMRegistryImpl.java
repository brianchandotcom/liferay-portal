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

import com.github.yuchi.semver.Range;
import com.github.yuchi.semver.Version;

import com.liferay.frontend.js.loader.modules.extender.internal.config.generator.JSConfigGeneratorPackage;
import com.liferay.frontend.js.loader.modules.extender.internal.configuration.Details;
import com.liferay.frontend.js.loader.modules.extender.npm.JSBundle;
import com.liferay.frontend.js.loader.modules.extender.npm.JSBundleProcessor;
import com.liferay.frontend.js.loader.modules.extender.npm.JSBundleTracker;
import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSModuleAlias;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackageDependency;
import com.liferay.frontend.js.loader.modules.extender.npm.ModuleNameUtil;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
	immediate = true, service = NPMRegistry.class
)
public class NPMRegistryImpl implements NPMRegistry {

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public void addJSBundleTracker(JSBundleTracker jsBundleTracker) {
	}

	@Override
	public Map<String, String> getGlobalAliases() {
		return _globalAliases;
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
		return _jsModules.get(identifier);
	}

	/**
	 * Returns the NPM package with the ID.
	 *
	 * @param  identifier the NPM package's ID
	 * @return the NPM package descriptor with the ID
	 */
	@Override
	public JSPackage getJSPackage(String identifier) {
		return _jsPackages.get(identifier);
	}

	/**
	 * Returns all deployed NPM packages.
	 *
	 * @return the deployed NPM packages
	 */
	@Override
	public Collection<JSPackage> getJSPackages() {
		return _jsPackages.values();
	}

	/**
	 * Returns the resolved module with the ID.
	 *
	 * @param  identifier the resolved module's ID
	 * @return the resolved module with the ID
	 */
	@Override
	public JSModule getResolvedJSModule(String identifier) {
		return _resolvedJSModules.get(identifier);
	}

	/**
	 * Returns all resolved modules deployed to the portal.
	 *
	 * @return the resolved modules deployed to the portal
	 */
	@Override
	public Collection<JSModule> getResolvedJSModules() {
		return _resolvedJSModules.values();
	}

	@Override
	public JSPackage getResolvedJSPackage(String identifier) {
		return _resolvedJSPackages.get(identifier);
	}

	/**
	 * Returns all resolved packages deployed to the portal.
	 *
	 * @return the resolved packages deployed to the portal
	 * @review
	 */
	@Override
	public Collection<JSPackage> getResolvedJSPackages() {
		return _resolvedJSPackages.values();
	}

	@Override
	public String mapModuleName(String moduleName) {
		String mappedModuleName = _exactMatchMap.get(moduleName);

		if (Validator.isNotNull(mappedModuleName)) {
			return mappedModuleName;
		}

		for (Map.Entry<String, String> entry : _globalAliases.entrySet()) {
			String resolvedId = entry.getKey();

			if (resolvedId.equals(moduleName) ||
				moduleName.startsWith(resolvedId + StringPool.SLASH)) {

				return entry.getValue() +
					moduleName.substring(resolvedId.length());
			}
		}

		for (Map.Entry<String, String> entry : _partialMatchMap.entrySet()) {
			String resolvedId = entry.getKey();

			if (resolvedId.equals(moduleName) ||
				moduleName.startsWith(resolvedId + StringPool.SLASH)) {

				return entry.getValue() +
					moduleName.substring(resolvedId.length());
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

		String packageName = jsPackageDependency.getPackageName();
		String versionConstraints = jsPackageDependency.getVersionConstraints();

		String cacheKey = StringBundler.concat(
			packageName, StringPool.UNDERLINE, versionConstraints);

		JSPackage jsPackage = _dependencyJSPackages.get(cacheKey);

		if (jsPackage != null) {
			if (jsPackage == _NULL_JS_PACKAGE) {
				return null;
			}

			return jsPackage;
		}

		Range range = Range.from(versionConstraints, true);

		for (JSPackageVersion jsPackageVersion : _jsPackageVersions) {
			JSPackage innerJSPackage = jsPackageVersion._jsPackage;
			Version version = jsPackageVersion._version;

			if (packageName.equals(innerJSPackage.getName()) &&
				range.test(version)) {

				jsPackage = innerJSPackage;

				break;
			}
		}

		if (jsPackage == null) {
			_dependencyJSPackages.put(cacheKey, _NULL_JS_PACKAGE);
		}
		else {
			_dependencyJSPackages.put(cacheKey, jsPackage);
		}

		return jsPackage;
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_bundleContext = bundleContext;

		_bundleTracker = new BundleTracker<>(
			_bundleContext, Bundle.ACTIVE,
			new NPMRegistryBundleTrackerCustomizer());

		_activationThreadLocal.set(Boolean.TRUE);

		_bundleTracker.open();

		_activationThreadLocal.set(Boolean.FALSE);

		Map<Bundle, JSBundle> tracked = _bundleTracker.getTracked();

		_refreshJSModuleCaches(tracked.values());

		Details details = ConfigurableUtil.createConfigurable(
			Details.class, properties);

		_applyVersioning = details.applyVersioning();

		_serviceTracker = _openServiceTracker();
	}

	@Deactivate
	protected void deactivate() {
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

			_serviceTracker = _openServiceTracker();
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
			catch (IOException ioe) {
				return null;
			}

			if (content == null) {
				return null;
			}

			return _jsonFactory.createJSONObject(content);
		}
		catch (Exception e) {
			return null;
		}
	}

	private ServiceTracker<ServletContext, JSConfigGeneratorPackage>
		_openServiceTracker() {

		return ServiceTrackerFactory.open(
			_bundleContext,
			"(&(objectClass=" + ServletContext.class.getName() +
				")(osgi.web.contextpath=*))",
			new ServiceTrackerCustomizer
				<ServletContext, JSConfigGeneratorPackage>() {

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

					String jsConfigGeneratorPackageResolvedId =
						jsConfigGeneratorPackage.getName() + StringPool.AT +
							jsConfigGeneratorPackage.getVersion();

					_partialMatchMap.put(
						jsConfigGeneratorPackage.getName(),
						jsConfigGeneratorPackageResolvedId);

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

					_partialMatchMap.remove(jsConfigGeneratorPackage.getName());
				}

			});
	}

	private void _processLegacyBridges(Bundle bundle) {
		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		String jsSubmodulesBridge = GetterUtil.getString(
			headers.get("Liferay-JS-Submodules-Bridge"));

		if (Validator.isNotNull(jsSubmodulesBridge)) {
			String[] bridges = jsSubmodulesBridge.split(",");

			JSONObject packageJSONObject = _getPackageJSONObject(bundle);

			for (String bridge : bridges) {
				bridge = bridge.trim();

				StringBundler sb = new StringBundler(5);

				sb.append(packageJSONObject.getString("name"));
				sb.append(StringPool.AT);
				sb.append(packageJSONObject.getString("version"));
				sb.append("/bridge/");
				sb.append(bridge);

				_globalAliases.put(bridge, sb.toString());
			}
		}
	}

	private void _refreshJSModuleCaches(Collection<JSBundle> jsBundles) {
		_dependencyJSPackages.clear();

		Map<String, JSModule> jsModules = new HashMap<>();
		Map<String, JSPackage> jsPackages = new HashMap<>();
		List<JSPackageVersion> jsPackageVersions = new ArrayList<>();
		Map<String, JSModule> resolvedJSModules = new HashMap<>();
		Map<String, JSPackage> resolvedJSPackages = new HashMap<>();
		Map<String, String> exactMatchMap = new HashMap<>();

		for (JSBundle jsBundle : jsBundles) {
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
					String moduleResolvedId =
						ModuleNameUtil.getModuleResolvedId(
							jsPackage, jsModuleAlias.getModuleName());

					exactMatchMap.put(aliasResolvedId, moduleResolvedId);
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

		_jsModules = jsModules;
		_jsPackages = jsPackages;
		_jsPackageVersions = jsPackageVersions;
		_resolvedJSModules = resolvedJSModules;
		_resolvedJSPackages = resolvedJSPackages;
		_exactMatchMap = exactMatchMap;
	}

	private static final JSPackage _NULL_JS_PACKAGE =
		ProxyFactory.newDummyInstance(JSPackage.class);

	private static final ThreadLocal<Boolean> _activationThreadLocal =
		new CentralizedThreadLocal<>(
			NPMRegistryImpl.class.getName() + "._activationThreadLocal",
			() -> Boolean.FALSE);

	private Boolean _applyVersioning;
	private BundleContext _bundleContext;
	private BundleTracker<JSBundle> _bundleTracker;
	private final Map<String, JSPackage> _dependencyJSPackages =
		new ConcurrentHashMap<>();
	private Map<String, String> _exactMatchMap;
	private final Map<String, String> _globalAliases = new HashMap<>();

	@Reference
	private JSBundleProcessor _jsBundleProcessor;

	private Map<String, JSModule> _jsModules = new HashMap<>();

	@Reference
	private JSONFactory _jsonFactory;

	private Map<String, JSPackage> _jsPackages = new HashMap<>();
	private List<JSPackageVersion> _jsPackageVersions = new ArrayList<>();
	private final Map<String, String> _partialMatchMap =
		new ConcurrentHashMap<>();
	private Map<String, JSModule> _resolvedJSModules = new HashMap<>();
	private Map<String, JSPackage> _resolvedJSPackages = new HashMap<>();
	private ServiceTracker<ServletContext, JSConfigGeneratorPackage>
		_serviceTracker;

	private static class JSPackageVersion {

		public Version getVersion() {
			return _version;
		}

		private JSPackageVersion(JSPackage jsPackage) {
			_jsPackage = jsPackage;

			_version = Version.from(jsPackage.getVersion(), true);
		}

		private final JSPackage _jsPackage;
		private final Version _version;

	}

	private class NPMRegistryBundleTrackerCustomizer
		implements BundleTrackerCustomizer<JSBundle> {

		@Override
		public JSBundle addingBundle(Bundle bundle, BundleEvent bundleEvent) {
			JSBundle jsBundle = _jsBundleProcessor.process(bundle);

			if (jsBundle == null) {
				return null;
			}

			_processLegacyBridges(bundle);

			if (!_activationThreadLocal.get()) {
				Map<Bundle, JSBundle> tracked = _bundleTracker.getTracked();

				Collection<JSBundle> jsBundles = new ArrayList<>(
					tracked.values());

				jsBundles.add(jsBundle);

				_refreshJSModuleCaches(jsBundles);
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
				Map<Bundle, JSBundle> tracked = _bundleTracker.getTracked();

				_refreshJSModuleCaches(tracked.values());
			}
		}

	}

}