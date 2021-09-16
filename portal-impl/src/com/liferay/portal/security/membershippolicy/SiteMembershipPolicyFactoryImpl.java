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

package com.liferay.portal.security.membershippolicy;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.security.membershippolicy.SiteMembershipPolicy;
import com.liferay.portal.kernel.security.membershippolicy.SiteMembershipPolicyFactory;
import com.liferay.portal.util.PropsValues;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Sergio González
 * @author Shuyang Zhou
 * @author Roberto Díaz
 * @author Peter Fellwock
 */
public class SiteMembershipPolicyFactoryImpl
	implements SiteMembershipPolicyFactory {

	@Override
	public SiteMembershipPolicy getSiteMembershipPolicy() {
		return _serviceTracker.getService();
	}

	private SiteMembershipPolicyFactoryImpl() {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SiteMembershipPolicyFactoryImpl.class);

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private static final ServiceTracker<?, SiteMembershipPolicy>
		_serviceTracker;

	private static class SiteMembershipPolicyTrackerCustomizer
		implements ServiceTrackerCustomizer
			<SiteMembershipPolicy, SiteMembershipPolicy> {

		@Override
		public SiteMembershipPolicy addingService(
			ServiceReference<SiteMembershipPolicy> serviceReference) {

			SiteMembershipPolicy siteMembershipPolicy =
				_bundleContext.getService(serviceReference);

			if (PropsValues.MEMBERSHIP_POLICY_AUTO_VERIFY) {
				try {
					siteMembershipPolicy.verifyPolicy();
				}
				catch (PortalException portalException) {
					_log.error(portalException, portalException);
				}
			}

			return siteMembershipPolicy;
		}

		@Override
		public void modifiedService(
			ServiceReference<SiteMembershipPolicy> serviceReference,
			SiteMembershipPolicy siteMembershipPolicy) {
		}

		@Override
		public void removedService(
			ServiceReference<SiteMembershipPolicy> serviceReference,
			SiteMembershipPolicy siteMembershipPolicy) {

			_bundleContext.ungetService(serviceReference);
		}

	}

	static {
		_serviceTracker = new ServiceTracker<>(
			_bundleContext, SiteMembershipPolicy.class,
			new SiteMembershipPolicyTrackerCustomizer());

		_serviceTracker.open();
	}

}