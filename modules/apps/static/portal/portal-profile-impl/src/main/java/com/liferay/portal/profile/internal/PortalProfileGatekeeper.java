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

package com.liferay.portal.profile.internal;

import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.profile.PortalProfile;

import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true, service = {})
public class PortalProfileGatekeeper {

	@Activate
	public void activate(BundleContext bundleContext) {
		Set<String> blacklistPortalProfileNames = SetUtil.fromArray(
			StringUtil.split(
				bundleContext.getProperty("blacklist.portal.profile.names")));

		Set<String> whitelistPortalProfileNames = SetUtil.fromArray(
			StringUtil.split(
				bundleContext.getProperty("whitelist.portal.profile.names")));

		if (whitelistPortalProfileNames.isEmpty()) {
			String name = ReleaseInfo.getName();

			if (name.contains("Community")) {
				whitelistPortalProfileNames.add(
					PortalProfile.PORTAL_PROFILE_NAME_CE);
			}
			else {
				whitelistPortalProfileNames.add(
					PortalProfile.PORTAL_PROFILE_NAME_DXP);
			}
		}

		_serviceTracker = new ServiceTracker<>(
			bundleContext, PortalProfile.class,
			new PortalProfileServiceTrackerCustomizer(
				bundleContext, blacklistPortalProfileNames,
				whitelistPortalProfileNames));

		_serviceTracker.open();
	}

	@Deactivate
	public void deactivate() {
		_serviceTracker.close();
	}

	private ServiceTracker<PortalProfile, Void> _serviceTracker;

	private static class PortalProfileServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<PortalProfile, Void> {

		@Override
		public Void addingService(
			ServiceReference<PortalProfile> serviceReference) {

			PortalProfile portalProfile = _bundleContext.getService(
				serviceReference);

			for (String portalProfileName :
					portalProfile.getPortalProfileNames()) {

				if (_blacklistPortalProfileNames.contains(portalProfileName)) {
					return null;
				}
			}

			for (String portalProfileName :
					portalProfile.getPortalProfileNames()) {

				if (_whitelistPortalProfileNames.contains(portalProfileName)) {
					portalProfile.activate();

					break;
				}
			}

			return null;
		}

		@Override
		public void modifiedService(
			ServiceReference<PortalProfile> serviceReference, Void v) {
		}

		@Override
		public void removedService(
			ServiceReference<PortalProfile> serviceReference, Void v) {
		}

		private PortalProfileServiceTrackerCustomizer(
			BundleContext bundleContext,
			Set<String> blacklistPortalProfileNames,
			Set<String> whitelistPortalProfileNames) {

			_bundleContext = bundleContext;
			_blacklistPortalProfileNames = blacklistPortalProfileNames;
			_whitelistPortalProfileNames = whitelistPortalProfileNames;
		}

		private final Set<String> _blacklistPortalProfileNames;
		private final BundleContext _bundleContext;
		private final Set<String> _whitelistPortalProfileNames;

	}

}