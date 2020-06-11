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

package com.liferay.portal.fragment.bundle.watcher.internal;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.Constants;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.FrameworkWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.BundleTracker;

/**
 * @author Shuyang Zhou
 */
@Component(service = {})
public class PortalFragmentBundleWatcher {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_installedFragmentBundleTracker = new BundleTracker<String>(
			_bundleContext, Bundle.INSTALLED, null) {

			@Override
			public String addingBundle(Bundle bundle, BundleEvent event) {
				BundleRevision bundleRevision = bundle.adapt(
					BundleRevision.class);

				int types = bundleRevision.getTypes();

				if ((types & BundleRevision.TYPE_FRAGMENT) == 0) {
					return null;
				}

				return _getFragmentHost(bundle);
			}

		};

		_installedFragmentBundleTracker.open();

		Bundle systemBundle = _bundleContext.getBundle(0);

		FrameworkWiring frameworkWiring = systemBundle.adapt(
			FrameworkWiring.class);

		_resolvedBundleListener = bundleEvent -> {
			if (bundleEvent.getType() == BundleEvent.RESOLVED) {
				Map<Bundle, String> installedFragmentBundles =
					_installedFragmentBundleTracker.getTracked();

				if (installedFragmentBundles.isEmpty()) {
					return;
				}

				Set<String> fragmentHostSymbolicNames = new HashSet<>(
					installedFragmentBundles.values());

				Bundle originBundle = bundleEvent.getOrigin();

				long originBundleId = originBundle.getBundleId();

				List<Bundle> hostBundles = new ArrayList<>();

				for (Bundle bundle : bundleContext.getBundles()) {
					if (fragmentHostSymbolicNames.remove(
							bundle.getSymbolicName()) &&
						(originBundleId != bundle.getBundleId())) {

						hostBundles.add(bundle);

						if (fragmentHostSymbolicNames.isEmpty()) {
							break;
						}
					}
				}

				if (!hostBundles.isEmpty()) {
					frameworkWiring.refreshBundles(hostBundles);
				}
			}
		};

		_bundleContext.addBundleListener(_resolvedBundleListener);
	}

	@Deactivate
	protected void deactivate() {
		_bundleContext.removeBundleListener(_resolvedBundleListener);

		_installedFragmentBundleTracker.close();
	}

	private String _getFragmentHost(Bundle bundle) {
		Dictionary<String, String> dictionary = bundle.getHeaders(
			StringPool.BLANK);

		String fragmentHost = dictionary.get(Constants.FRAGMENT_HOST);

		if (fragmentHost == null) {
			return null;
		}

		int index = fragmentHost.indexOf(CharPool.SEMICOLON);

		if (index != -1) {
			fragmentHost = fragmentHost.substring(0, index);
		}

		return fragmentHost;
	}

	private BundleContext _bundleContext;
	private BundleTracker<String> _installedFragmentBundleTracker;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	private BundleListener _resolvedBundleListener;

}