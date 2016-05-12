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

package com.liferay.portal.profile.gatekeeper.internal;

import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.profile.gatekeeper.Profile;

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
@Component(immediate = true)
public class ProfileGatekeeper {

	@Activate
	public void activate(BundleContext bundleContext) {
		Set<String> portalProfileNames = SetUtil.fromArray(
			StringUtil.split(
				bundleContext.getProperty(Profile.PORTAL_PROFILE_NAMES)));

		if (portalProfileNames.isEmpty()) {
			String name = ReleaseInfo.getName();

			if (name.contains("Community")) {
				portalProfileNames.add(Profile.CE_PORTAL_PROFILE_NAME);
			}
			else {
				portalProfileNames.add(Profile.EE_PORTAL_PROFILE_NAME);
			}
		}

		_serviceTracker = new ServiceTracker<>(
			bundleContext, Profile.class,
			new ProfileServiceTrackerCustomizer(
				bundleContext, portalProfileNames));

		_serviceTracker.open();
	}

	@Deactivate
	public void deactivate() {
		_serviceTracker.close();
	}

	private ServiceTracker<Profile, Void> _serviceTracker;

	private static class ProfileServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<Profile, Void> {

		@Override
		public Void addingService(ServiceReference<Profile> serviceReference) {
			Profile profile = _bundleContext.getService(serviceReference);

			for (String portalProfileName :
					profile.getSupportedPortalProfileNames()) {

				if (_portalProfileNames.contains(portalProfileName)) {
					profile.activate();

					break;
				}
			}

			return null;
		}

		@Override
		public void modifiedService(
			ServiceReference<Profile> serviceReference, Void v) {
		}

		@Override
		public void removedService(
			ServiceReference<Profile> serviceReference, Void v) {
		}

		private ProfileServiceTrackerCustomizer(
			BundleContext bundleContext, Set<String> portalProfileNames) {

			_bundleContext = bundleContext;
			_portalProfileNames = portalProfileNames;
		}

		private final BundleContext _bundleContext;
		private final Set<String> _portalProfileNames;

	}

}