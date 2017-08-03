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

package com.liferay.saml.runtime.configuration;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Mika Koivisto
 */
public class SamlProviderConfigurationHelperUtil {

	public static SamlProviderConfiguration getSamlProviderConfiguration() {
		SamlProviderConfigurationHelper samlProviderConfigurationHelper =
			getSamlProviderConfigurationHelper();

		return samlProviderConfigurationHelper.getSamlProviderConfiguration();
	}

	public static SamlProviderConfigurationHelper
		getSamlProviderConfigurationHelper() {

		return _serviceTracker.getService();
	}

	public static boolean isEnabled() {
		SamlProviderConfigurationHelper samlProviderConfigurationHelper =
			getSamlProviderConfigurationHelper();

		return samlProviderConfigurationHelper.isEnabled();
	}

	public static boolean isLDAPImportEnabled() {
		SamlProviderConfigurationHelper samlProviderConfigurationHelper =
			getSamlProviderConfigurationHelper();

		return samlProviderConfigurationHelper.isLDAPImportEnabled();
	}

	public static boolean isRoleIdp() {
		SamlProviderConfigurationHelper samlProviderConfigurationHelper =
			getSamlProviderConfigurationHelper();

		return samlProviderConfigurationHelper.isRoleIdp();
	}

	public static boolean isRoleSp() {
		SamlProviderConfigurationHelper samlProviderConfigurationHelper =
			getSamlProviderConfigurationHelper();

		return samlProviderConfigurationHelper.isRoleSp();
	}

	private static final ServiceTracker<SamlProviderConfigurationHelper,
		SamlProviderConfigurationHelper>
			_serviceTracker = ServiceTrackerFactory.open(
				SamlProviderConfigurationHelper.class);

}