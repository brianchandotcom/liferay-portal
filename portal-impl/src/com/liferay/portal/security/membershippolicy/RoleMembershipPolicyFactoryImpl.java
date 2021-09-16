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
import com.liferay.portal.kernel.security.membershippolicy.RoleMembershipPolicy;
import com.liferay.portal.kernel.security.membershippolicy.RoleMembershipPolicyFactory;
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
public class RoleMembershipPolicyFactoryImpl
	implements RoleMembershipPolicyFactory {

	@Override
	public RoleMembershipPolicy getRoleMembershipPolicy() {
		return _serviceTracker.getService();
	}

	private RoleMembershipPolicyFactoryImpl() {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RoleMembershipPolicyFactoryImpl.class);

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private static final ServiceTracker<?, RoleMembershipPolicy>
		_serviceTracker;

	private static class RoleMembershipPolicyTrackerCustomizer
		implements ServiceTrackerCustomizer
			<RoleMembershipPolicy, RoleMembershipPolicy> {

		@Override
		public RoleMembershipPolicy addingService(
			ServiceReference<RoleMembershipPolicy> serviceReference) {

			RoleMembershipPolicy roleMembershipPolicy =
				_bundleContext.getService(serviceReference);

			if (PropsValues.MEMBERSHIP_POLICY_AUTO_VERIFY) {
				try {
					roleMembershipPolicy.verifyPolicy();
				}
				catch (PortalException portalException) {
					_log.error(portalException, portalException);
				}
			}

			return roleMembershipPolicy;
		}

		@Override
		public void modifiedService(
			ServiceReference<RoleMembershipPolicy> serviceReference,
			RoleMembershipPolicy roleMembershipPolicy) {
		}

		@Override
		public void removedService(
			ServiceReference<RoleMembershipPolicy> serviceReference,
			RoleMembershipPolicy roleMembershipPolicy) {

			_bundleContext.ungetService(serviceReference);
		}

	}

	static {
		_serviceTracker = new ServiceTracker<>(
			_bundleContext, RoleMembershipPolicy.class,
			new RoleMembershipPolicyTrackerCustomizer());

		_serviceTracker.open();
	}

}