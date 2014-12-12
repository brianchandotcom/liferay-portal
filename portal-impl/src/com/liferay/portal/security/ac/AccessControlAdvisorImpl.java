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
import com.liferay.registry.ServiceTracker;

import java.lang.reflect.Method;

import java.util.Collection;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Tomas Polesovsky
 * @author Igor Spasic
 * @author Michael C. Han
 * @author Raymond Augé
 */
public class AccessControlAdvisorImpl implements AccessControlAdvisor {

	public AccessControlAdvisorImpl() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(AccessControlPolicy.class);

		_serviceTracker.open();
	}

	@Override
	public void accept(
			MethodInvocation methodInvocation,
			AccessControlled accessControlled)
		throws SecurityException {

		Object[] arguments = methodInvocation.getArguments();
		Method targetMethod = methodInvocation.getMethod();

		Collection<AccessControlPolicy> policies =
			_serviceTracker.getTrackedServiceReferences().values();

		boolean remoteAccess = AccessControlThreadLocal.isRemoteAccess();

		if (remoteAccess) {
			for (AccessControlPolicy policy : policies) {
				policy.onServiceRemoteAccess(
					targetMethod, arguments, accessControlled);
			}
		}
		else {
			for (AccessControlPolicy policy : policies) {
				policy.onServiceAccess(
					targetMethod, arguments, accessControlled);
			}
		}
	}

	private final ServiceTracker<?, AccessControlPolicy> _serviceTracker;

}