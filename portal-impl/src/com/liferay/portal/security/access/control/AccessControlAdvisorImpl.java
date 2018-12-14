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

package com.liferay.portal.security.access.control;

import com.liferay.portal.kernel.security.access.control.AccessControlPolicy;
import com.liferay.portal.kernel.security.access.control.AccessControlThreadLocal;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.lang.reflect.Method;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Tomas Polesovsky
 * @author Igor Spasic
 * @author Michael C. Han
 * @author Raymond Augé
 */
public class AccessControlAdvisorImpl implements AccessControlAdvisor {

	@Override
	public void accept(
			Method method, Object[] arguments,
			AccessControlled accessControlled)
		throws SecurityException {

		if (AccessControlThreadLocal.isRemoteAccess()) {
			for (AccessControlPolicy accessControlPolicy :
					_accessControlPolicies) {

				accessControlPolicy.onServiceRemoteAccess(
					method, arguments, accessControlled);
			}
		}
		else {
			for (AccessControlPolicy accessControlPolicy :
					_accessControlPolicies) {

				accessControlPolicy.onServiceAccess(
					method, arguments, accessControlled);
			}
		}
	}

	private static final List<AccessControlPolicy> _accessControlPolicies =
		new CopyOnWriteArrayList<>();
	private static final ServiceTracker<?, ?> _serviceTracker;

	private static class AccessControlPolicyTrackerCustomizer
		implements ServiceTrackerCustomizer
			<AccessControlPolicy, AccessControlPolicy> {

		@Override
		public AccessControlPolicy addingService(
			ServiceReference<AccessControlPolicy> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			AccessControlPolicy accessControlPolicy = registry.getService(
				serviceReference);

			_accessControlPolicies.add(accessControlPolicy);

			return accessControlPolicy;
		}

		@Override
		public void modifiedService(
			ServiceReference<AccessControlPolicy> serviceReference,
			AccessControlPolicy accessControlPolicy) {
		}

		@Override
		public void removedService(
			ServiceReference<AccessControlPolicy> serviceReference,
			AccessControlPolicy accessControlPolicy) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_accessControlPolicies.remove(accessControlPolicy);
		}

	}

	static {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			AccessControlPolicy.class,
			new AccessControlPolicyTrackerCustomizer());

		_serviceTracker.open();
	}

}