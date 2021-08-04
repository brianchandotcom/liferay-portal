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

package com.liferay.portal.instances.on.demand.admin.test.util;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

/**
 * @author Pei-Jung Lan
 */
public class OnDemandAdminTestUtil {

	public static User addOnDemandAdminUser(Company company, long userId) {
		Object onDemandAdminTokenGenerator = _serviceTracker.getService();

		return ReflectionTestUtil.invoke(
			onDemandAdminTokenGenerator, "_addOnDemandAdminUser",
			new Class<?>[] {Company.class, long.class}, company, userId);
	}

	private static final ServiceTracker<Object, Object> _serviceTracker;

	static {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			"com.liferay.portal.instances.on.demand.admin.internal.generator." +
				"OnDemandAdminTokenGenerator");

		_serviceTracker.open();
	}

}