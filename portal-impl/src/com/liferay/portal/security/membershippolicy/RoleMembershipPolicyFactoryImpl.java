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

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

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
		return _instance._serviceTracker.getService();
	}

	private RoleMembershipPolicyFactoryImpl() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(RoleMembershipPolicy.class);

		_serviceTracker.open();
	}

	private static RoleMembershipPolicyFactoryImpl _instance =
		new RoleMembershipPolicyFactoryImpl();

	private ServiceTracker<?, RoleMembershipPolicy> _serviceTracker;

}