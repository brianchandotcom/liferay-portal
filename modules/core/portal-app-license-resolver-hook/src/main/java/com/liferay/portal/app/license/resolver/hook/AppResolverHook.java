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

package com.liferay.portal.app.license.resolver.hook;

import com.liferay.portal.app.license.AppLicenseVerifier;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.net.URL;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.hooks.resolver.ResolverHook;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleRequirement;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Amos Fong
 */
public class AppResolverHook implements ResolverHook {

	public AppResolverHook(
		ServiceTracker<AppLicenseVerifier, AppLicenseVerifier> serviceTracker) {

		_serviceTracker = serviceTracker;
	}

	@Override
	public void end() {
	}

	@Override
	public void filterMatches(
		BundleRequirement requirement,
		Collection<BundleCapability> candidates) {
	}

	@Override
	public void filterResolvable(Collection<BundleRevision> candidates) {
		Iterator<BundleRevision> iterator = candidates.iterator();

		while (iterator.hasNext()) {
			BundleRevision bundleRevision = iterator.next();

			try {
				_filterResolvable(bundleRevision);
			}
			catch (Exception e) {
				_log.error(
					"Unable to resolve " + bundleRevision.getSymbolicName(), e);

				iterator.remove();
			}
		}
	}

	@Override
	public void filterSingletonCollisions(
		BundleCapability singleton,
		Collection<BundleCapability> collisionCandidates) {
	}

	private void _filterResolvable(BundleRevision bundleRevision)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Resolving bundle " + bundleRevision.getSymbolicName());
		}

		Bundle bundle = bundleRevision.getBundle();

		Properties properties = _getAppLicenseProperties(bundle);

		String productId = (String)properties.get("product-id");

		if (productId == null) {
			return;
		}

		boolean verified = false;

		String licenseVersion = (String)properties.get("license-version");

		Filter filter = FrameworkUtil.createFilter(
			"(version=" + licenseVersion + ")");

		SortedMap<ServiceReference<AppLicenseVerifier>, AppLicenseVerifier>
			serviceReferences = _serviceTracker.getTracked();

		for (Map.Entry
				<ServiceReference<AppLicenseVerifier>, AppLicenseVerifier>
					entry :
						serviceReferences.entrySet()) {

			ServiceReference<AppLicenseVerifier> serviceReference =
				entry.getKey();

			if (!filter.match(serviceReference)) {
				continue;
			}

			AppLicenseVerifier appLicenseVerifier = entry.getValue();

			String productType = (String)properties.get("product-type");
			String productVersionId = (String)properties.get(
				"product-version-id");

			appLicenseVerifier.verify(
				bundle, productId, productType, productVersionId);

			verified = true;

			break;
		}

		if (!verified) {
			throw new Exception(
				"Unable to resolve " + AppLicenseVerifier.class.getName());
		}
	}

	private Properties _getAppLicenseProperties(Bundle bundle) {
		Properties properties = new Properties();

		try {
			URL url = bundle.getEntry("/META-INF/marketplace.properties");

			if (url != null) {
				properties.load(url.openStream());
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to read bundle properties", e);
			}
		}

		return properties;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AppResolverHook.class);

	private final ServiceTracker<AppLicenseVerifier, AppLicenseVerifier>
		_serviceTracker;

}