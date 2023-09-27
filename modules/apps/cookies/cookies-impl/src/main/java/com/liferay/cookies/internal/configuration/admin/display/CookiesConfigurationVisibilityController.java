/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.internal.configuration.admin.display;

import com.liferay.configuration.admin.display.ConfigurationVisibilityController;
import com.liferay.cookies.configuration.CookiesPreferenceHandlingConfiguration;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;

import java.io.Serializable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Olivér Kecskeméty
 */
@Component(
	property = {
		"configuration.pid=com.liferay.cookies.configuration.banner.CookiesBannerConfiguration",
		"configuration.pid=com.liferay.cookies.configuration.consent.CookiesConsentConfiguration"
	},
	service = ConfigurationVisibilityController.class
)
public class CookiesConfigurationVisibilityController
	implements ConfigurationVisibilityController {

	@Override
	public boolean isVisible(
		ExtendedObjectClassDefinition.Scope scope, Serializable scopePK) {

		try {
			CookiesPreferenceHandlingConfiguration
				cookiesPreferenceHandlingConfiguration = null;

			if (ExtendedObjectClassDefinition.Scope.COMPANY.equals(scope)) {
				cookiesPreferenceHandlingConfiguration =
					_configurationProvider.getCompanyConfiguration(
						CookiesPreferenceHandlingConfiguration.class,
						(Long)scopePK);
			}
			else if (ExtendedObjectClassDefinition.Scope.GROUP.equals(scope)) {
				cookiesPreferenceHandlingConfiguration =
					_configurationProvider.getGroupConfiguration(
						CookiesPreferenceHandlingConfiguration.class,
						(Long)scopePK);
			}
			else if (ExtendedObjectClassDefinition.Scope.SYSTEM.equals(scope)) {
				cookiesPreferenceHandlingConfiguration =
					_configurationProvider.getSystemConfiguration(
						CookiesPreferenceHandlingConfiguration.class);
			}

			if (cookiesPreferenceHandlingConfiguration != null) {
				return cookiesPreferenceHandlingConfiguration.enabled();
			}
		}
		catch (ConfigurationException configurationException) {
			throw new SystemException(configurationException);
		}

		return false;
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

}