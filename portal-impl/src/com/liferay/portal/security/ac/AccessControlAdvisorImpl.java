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

package com.liferay.portal.security.ac;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.lang.reflect.Method;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Tomas Polesovsky
 * @author Igor Spasic
 * @author Michael C. Han
 * @author Raymond Augé
 */
public class AccessControlAdvisorImpl implements AccessControlAdvisor {

	public AccessControlAdvisorImpl() {
		_policies = new CopyOnWriteArrayList<>();

		Registry registry = RegistryUtil.getRegistry();

		if (registry == null) {
			_serviceTracker = null;

			return;
		}

		_serviceTracker = registry.trackServices(
			AccessControlPolicy.class,
			new AccessControlPolicyTrackerCustomizer());

		_serviceTracker.open();
	}

	@Override
	public void accept(
			MethodInvocation methodInvocation,
			AccessControlled accessControlled)
		throws SecurityException {

		Object[] arguments = methodInvocation.getArguments();
		Method targetMethod = methodInvocation.getMethod();

		boolean remoteAccess = AccessControlThreadLocal.isRemoteAccess();

		if (remoteAccess) {
			for (AccessControlPolicy policy : _policies) {
				policy.onServiceRemoteAccess(
					targetMethod, arguments, accessControlled);
			}
		}
		else {
			for (AccessControlPolicy policy : _policies) {
				policy.onServiceAccess(
					targetMethod, arguments, accessControlled);
			}
		}
	}

	private final List<AccessControlPolicy> _policies;
	private final ServiceTracker<?, AccessControlPolicy> _serviceTracker;

	private class AccessControlPolicyTrackerCustomizer
		implements
		ServiceTrackerCustomizer<AccessControlPolicy, AccessControlPolicy> {

		@Override
		public AccessControlPolicy addingService(
			ServiceReference<AccessControlPolicy> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			AccessControlPolicy accessControlPolicy = registry.getService(
				serviceReference);

			_policies.add(accessControlPolicy);

			return accessControlPolicy;
		}

		@Override
		public void modifiedService(
			ServiceReference<AccessControlPolicy> serviceReference,
			AccessControlPolicy service) {
		}

		@Override
		public void removedService(
			ServiceReference<AccessControlPolicy> serviceReference,
			AccessControlPolicy service) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_policies.remove(service);
		}

	}

}