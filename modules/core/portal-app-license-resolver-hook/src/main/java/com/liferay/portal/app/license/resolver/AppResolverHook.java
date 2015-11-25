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

package com.liferay.portal.app.license.resolver;

import aQute.bnd.header.Attrs;
import aQute.bnd.header.OSGiHeader;

import com.liferay.portal.app.license.AppLicenseVerifier;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Iterator;
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

		Dictionary<String, String> headers = bundle.getHeaders();

		String xLiferayMarketplace = headers.get("X-Liferay-Marketplace");

		if (xLiferayMarketplace == null) {
			return;
		}

		Attrs attrs = OSGiHeader.parseProperties(xLiferayMarketplace);

		String productId = attrs.get("productId");

		if (productId == null) {
			return;
		}

		boolean verified = false;

		String licenseVersion = attrs.get("licenseVersion");

		Filter filter = FrameworkUtil.createFilter(
			"(version=" + licenseVersion + ")");

		SortedMap<ServiceReference<AppLicenseVerifier>, AppLicenseVerifier>
			serviceReferences = _serviceTracker.getTracked();

		for (ServiceReference<AppLicenseVerifier> serviceReference :
				serviceReferences.keySet()) {

			if (!filter.match(serviceReference)) {
				continue;
			}

			AppLicenseVerifier appLicenseVerifier = serviceReferences.get(
				serviceReference);

			String productType = attrs.get("productType");
			String productVersion = attrs.get("productVersion");

			appLicenseVerifier.verify(
				bundle, productId, productType, productVersion);

			verified = true;

			break;
		}

		if (!verified) {
			throw new Exception(
				"Unable to resolve " + AppLicenseVerifier.class.getName());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AppResolverHook.class);

	private final ServiceTracker<AppLicenseVerifier, AppLicenseVerifier>
		_serviceTracker;

}