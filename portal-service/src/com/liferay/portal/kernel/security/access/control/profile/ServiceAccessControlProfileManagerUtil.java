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

package com.liferay.portal.kernel.security.access.control.profile;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.List;

/**
 * @author Mika Koivisto
 */
public class ServiceAccessControlProfileManagerUtil {

	public static ServiceAccessControlProfile
		getDefaultServiceAccessControlProfile(long companyId) {

		return getServiceAccessControlProfileManager().
			getDefaultServiceAccessControlProfile(companyId);
	}

	public static ServiceAccessControlProfile getServiceAccessControlProfile(
		long companyId, String name) {

		return getServiceAccessControlProfileManager().
			getServiceAccessControlProfile(companyId, name);
	}

	public static ServiceAccessControlProfileManager
		getServiceAccessControlProfileManager() {

		PortalRuntimePermission.checkGetBeanProperty(
			ServiceAccessControlProfileManagerUtil.class);

		return _instance._serviceTracker.getService();
	}

	public static List<ServiceAccessControlProfile>
		getServiceAccessControlProfiles(long companyId, int start, int end) {

		return getServiceAccessControlProfileManager().
			getServiceAccessControlProfiles(companyId, start, end);
	}

	public static int getServiceAccessControlProfilesCount(long companyId) {
		return getServiceAccessControlProfileManager().
			getServiceAccessControlProfilesCount(companyId);
	}

	private ServiceAccessControlProfileManagerUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			ServiceAccessControlProfileManager.class);

		_serviceTracker.open();
	}

	private static final ServiceAccessControlProfileManagerUtil _instance =
		new ServiceAccessControlProfileManagerUtil();

	private final ServiceTracker<?, ServiceAccessControlProfileManager>
		_serviceTracker;

}