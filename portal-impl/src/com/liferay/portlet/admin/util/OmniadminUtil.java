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

package com.liferay.portlet.admin.util;

import com.liferay.admin.kernel.util.Omniadmin;
import com.liferay.portal.kernel.model.User;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

/**
 * Provides utility methods for determining if a user is a universal
 * administrator. Universal administrators have administrator permissions in
 * every company.
 *
 * <p>
 * A user can be made a universal administrator by adding their primary key to
 * the list in <code>portal.properties</code> under the key
 * <code>omniadmin.users</code>. If this property is left blank, administrators
 * of the default company will automatically be universal administrators.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class OmniadminUtil {

	public static boolean isOmniadmin(long userId) {
		return _getInstance().isOmniadmin(userId);
	}

	public static boolean isOmniadmin(User user) {
		return _getInstance().isOmniadmin(user);
	}

	private static Omniadmin _getInstance() {
		return _instance._serviceTracker.getService();
	}

	private OmniadminUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(Omniadmin.class);

		_serviceTracker.open();
	}

	private static final OmniadminUtil _instance = new OmniadminUtil();

	private final ServiceTracker<?, Omniadmin> _serviceTracker;

}