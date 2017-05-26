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

import com.liferay.frontend.js.loader.modules.extender.npm.JSBundle;
import com.liferay.frontend.js.loader.modules.extender.npm.JSBundleProcessor;
import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackageDependency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Iván Zaera
 */
@Component(immediate = true, service = NPMRegistry.class)
public class NPMRegistry {

	public Collection<JSBundle> getJSBundles() {
		return _jsBundles;
	}

	public JSModule getJSModule(String identifier) {
		return _jsModules.get(identifier);
	}

	public Collection<JSPackage> getJSPackages() {
		return _jsPackages.values();
	}

	public JSModule getResolvedJSModule(String identifier) {
		return _resolvedJSModules.get(identifier);
	}

	public Collection<JSModule> getResolvedJSModules() {
		return _resolvedJSModules.values();
	}

	public JSPackage resolveJSPackageDependency(
		JSPackageDependency jsPackageDependency) {

		String packageName = jsPackageDependency.getPackageName();

		List<JSPackage> jsPackages = new ArrayList<>();

		for (JSPackage jsPackage : _jsPackages.values()) {
			if (packageName.equals(jsPackage.getName())) {
				jsPackages.add(jsPackage);
			}
		}

		Collections.sort(
			jsPackages,
			new Comparator<JSPackage>() {

				@Override
				public int compare(JSPackage o1, JSPackage o2) {
					Version version1 = Version.from(o1.getVersion(), true);
					Version version2 = Version.from(o2.getVersion(), true);

					return version1.compareTo(version2);
				}

			});

		Range range = Range.from(
			jsPackageDependency.getVersionConstraints(), true);

		for (JSPackage jsPackage : jsPackages) {
			Version version = Version.from(jsPackage.getVersion(), true);

			if (range.test(version)) {
				return jsPackage;
			}
		}

		return null;
	}

	@Activate
	protected void activate(
			ComponentContext componentContext, Map<String, Object> properties)
		throws Exception {

		if (_bundleTracker != null) {
			_bundleTracker.close();
		}

		_bundleContext = componentContext.getBundleContext();

		_bundleTracker = new BundleTracker<>(
			_bundleContext, Bundle.ACTIVE,
			new NPMRegistryBundleTrackerCustomizer());

		_bundleTracker.open();
	}

	protected void bindBundleProcessor(JSBundleProcessor jsBundleProcessor) {
		_jsBundleProcessors.add(jsBundleProcessor);
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();

		_bundleTracker = null;
	}

	protected void unbindBundleProcessor(JSBundleProcessor jsBundleProcessor) {
		_jsBundleProcessors.remove(jsBundleProcessor);
	}

	private void _addBundle(JSBundle jsBundle) {
		_jsBundles.add(jsBundle);

		_refreshJSModuleCaches();
	}

	private void _refreshJSModuleCaches() {
		Map<String, JSPackage> jsPackages = new HashMap<>();
		Map<String, JSModule> jsModules = new HashMap<>();
		Map<String, JSModule> resolvedJSModules = new HashMap<>();

		for (JSBundle jsBundle : _jsBundles) {
			for (JSPackage jsPackage : jsBundle.getJSPackages()) {
				jsPackages.put(jsPackage.getId(), jsPackage);

				for (JSModule jsModule : jsPackage.getJSModules()) {
					resolvedJSModules.put(jsModule.getResolvedId(), jsModule);
					jsModules.put(jsModule.getId(), jsModule);
				}
			}
		}

		_resolvedJSModules = resolvedJSModules;
		_jsModules = jsModules;
		_jsPackages = jsPackages;
	}

	private boolean _removeBundle(JSBundle jsBundle) {
		boolean removed = _jsBundles.remove(jsBundle);

		if (removed) {
			_refreshJSModuleCaches();
		}

		return removed;
	}

	private BundleContext _bundleContext;
	private BundleTracker<JSBundle> _bundleTracker;

	@Reference(
		bind = "bindBundleProcessor",
		cardinality = ReferenceCardinality.AT_LEAST_ONE,
		unbind = "unbindBundleProcessor"
	)
	private List<JSBundleProcessor> _jsBundleProcessors = new ArrayList<>();

	private final Set<JSBundle> _jsBundles = new ConcurrentSkipListSet<>(
		new Comparator<JSBundle>() {

			@Override
			public int compare(JSBundle o1, JSBundle o2) {
				return o1.getId().compareTo(o2.getId());
			}

		});

	private Map<String, JSModule> _jsModules = new HashMap<>();
	private Map<String, JSPackage> _jsPackages = new HashMap<>();
	private Map<String, JSModule> _resolvedJSModules = new HashMap<>();

	private class NPMRegistryBundleTrackerCustomizer
		implements BundleTrackerCustomizer<JSBundle> {

		@Override
		public JSBundle addingBundle(Bundle bundle, BundleEvent event) {
			for (JSBundleProcessor jsBundleProcessor : _jsBundleProcessors) {
				JSBundle jsBundle = jsBundleProcessor.process(bundle);

				if (jsBundle != null) {
					_addBundle(jsBundle);

					return jsBundle;
				}
			}

			return null;
		}

		@Override
		public void modifiedBundle(
			Bundle bundle, BundleEvent event, JSBundle jsBundle) {

			removedBundle(bundle, event, jsBundle);

			addingBundle(bundle, event);
		}

		@Override
		public void removedBundle(
			Bundle bundle, BundleEvent event, JSBundle jsBundle) {

			_removeBundle(jsBundle);
		}

	}

}