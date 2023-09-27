/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.app.license.resolver.hook;

import com.liferay.portal.app.license.AppLicenseVerifier;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.hooks.resolver.ResolverHook;
import org.osgi.framework.hooks.resolver.ResolverHookFactory;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Amos Fong
 */
public class AppResolverHookFactory implements ResolverHookFactory {

	public AppResolverHookFactory(BundleContext bundleContext) {
		_serviceTracker = new ServiceTracker<>(
			bundleContext, AppLicenseVerifier.class, null);

		_serviceTracker.open();
	}

	@Override
	public ResolverHook begin(Collection<BundleRevision> triggers) {
		return new AppResolverHook(
			_serviceTracker, _filteredBundleSymbolicNames, _filteredProductIds);
	}

	public void close() {
		_serviceTracker.close();
	}

	private final Set<String> _filteredBundleSymbolicNames =
		Collections.newSetFromMap(new ConcurrentHashMap<>());
	private final Set<String> _filteredProductIds = Collections.newSetFromMap(
		new ConcurrentHashMap<>());
	private final ServiceTracker<AppLicenseVerifier, AppLicenseVerifier>
		_serviceTracker;

}