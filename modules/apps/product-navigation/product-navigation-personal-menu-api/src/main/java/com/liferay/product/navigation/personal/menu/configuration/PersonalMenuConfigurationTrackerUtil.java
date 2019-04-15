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

package com.liferay.product.navigation.personal.menu.configuration;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Samuel Trong Tran
 */
@ProviderType
public class PersonalMenuConfigurationTrackerUtil {

	public static PersonalMenuConfiguration getCompanyPersonalMenuConfiguration(
		long companyId) {

		return getService().getCompanyPersonalMenuConfiguration(companyId);
	}

	public static PersonalMenuConfigurationTracker getService() {
		return _serviceTracker.getService();
	}

	private static final ServiceTracker
		<PersonalMenuConfigurationTracker, PersonalMenuConfigurationTracker>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			PersonalMenuConfigurationTracker.class);

		ServiceTracker
			<PersonalMenuConfigurationTracker, PersonalMenuConfigurationTracker>
				serviceTracker = new ServiceTracker<>(
					bundle.getBundleContext(),
					PersonalMenuConfigurationTracker.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}