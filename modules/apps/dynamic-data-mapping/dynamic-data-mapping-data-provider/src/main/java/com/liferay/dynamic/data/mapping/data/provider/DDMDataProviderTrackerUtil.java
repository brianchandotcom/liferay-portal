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

package com.liferay.dynamic.data.mapping.data.provider;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Marcellus Tavares
 */
public class DDMDataProviderTrackerUtil {

	public static DDMDataProvider getDDMDataProvider(String type) {
		return getDDMDataProviderTracker().getDDMDataProvider(type);
	}

	public static DDMDataProviderTracker getDDMDataProviderTracker() {
		PortalRuntimePermission.checkGetBeanProperty(
			DDMDataProviderTrackerUtil.class);

		return _serviceTracker.getService();
	}

	public static Set<String> getDDMDataProviderTypes() {
		return getDDMDataProviderTracker().getDDMDataProviderTypes();
	}

	private static final ServiceTracker
		<DDMDataProviderTracker, DDMDataProviderTracker> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DDMDataProviderTrackerUtil.class);

		_serviceTracker = new ServiceTracker<>(
			bundle.getBundleContext(), DDMDataProviderTracker.class, null);

		_serviceTracker.open();
	}

}