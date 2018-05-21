/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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