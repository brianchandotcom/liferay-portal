/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.membershippolicy;

import com.liferay.petra.concurrent.DCLSingleton;
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

	public void destroy() {
		_serviceTrackerDCLSingleton.destroy(ServiceTracker::close);
	}

	@Override
	public SiteMembershipPolicy getSiteMembershipPolicy() {
		ServiceTracker<SiteMembershipPolicy, SiteMembershipPolicy>
			serviceTracker = _serviceTrackerDCLSingleton.getSingleton(
				SiteMembershipPolicyFactoryImpl::_createServiceTracker);

		return serviceTracker.getService();
	}

	private static ServiceTracker<SiteMembershipPolicy, SiteMembershipPolicy>
		_createServiceTracker() {

		ServiceTracker<SiteMembershipPolicy, SiteMembershipPolicy>
			serviceTracker = new ServiceTracker<>(
				_bundleContext, SiteMembershipPolicy.class,
				new SiteMembershipPolicyTrackerCustomizer());

		serviceTracker.open();

		return serviceTracker;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SiteMembershipPolicyFactoryImpl.class);

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private static final DCLSingleton
		<ServiceTracker<SiteMembershipPolicy, SiteMembershipPolicy>>
			_serviceTrackerDCLSingleton = new DCLSingleton<>();

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
					_log.error(portalException);
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

}