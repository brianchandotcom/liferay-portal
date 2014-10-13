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
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

/**
 * @author Sergio González
 * @author Shuyang Zhou
 * @author Peter Fellwock
 */
public class UserGroupMembershipPolicyFactoryImpl
	implements UserGroupMembershipPolicyFactory {

	@Override
	public UserGroupMembershipPolicy getUserGroupMembershipPolicy() {
		return _instance._serviceTracker.getService();
	}

	private UserGroupMembershipPolicyFactoryImpl() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			UserGroupMembershipPolicy.class,
			new UserGroupMembershipPolicyTrackerCustomizer());

		_serviceTracker.open();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserGroupMembershipPolicyFactoryImpl.class);

	private static final UserGroupMembershipPolicyFactoryImpl
		_instance = new UserGroupMembershipPolicyFactoryImpl();

	private final ServiceTracker<?, UserGroupMembershipPolicy> _serviceTracker;

	private class UserGroupMembershipPolicyTrackerCustomizer
		implements
			ServiceTrackerCustomizer
				<UserGroupMembershipPolicy, UserGroupMembershipPolicy> {

		@Override
		public UserGroupMembershipPolicy addingService(
			ServiceReference<UserGroupMembershipPolicy> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			UserGroupMembershipPolicy userGroupMembershipPolicy =
				registry.getService(serviceReference);

			if (PropsValues.MEMBERSHIP_POLICY_AUTO_VERIFY) {
				try {
					userGroupMembershipPolicy.verifyPolicy();
				}
				catch (PortalException pe) {
					_log.error(pe, pe);
				}
			}

			return userGroupMembershipPolicy;
		}

		@Override
		public void modifiedService(
			ServiceReference<UserGroupMembershipPolicy> serviceReference,
			UserGroupMembershipPolicy userGroupMembershipPolicy) {
		}

		@Override
		public void removedService(
			ServiceReference<UserGroupMembershipPolicy> serviceReference,
			UserGroupMembershipPolicy userGroupMembershipPolicy) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);
		}

	}

}