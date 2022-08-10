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

package com.liferay.cookies.internal.configuration.admin.display;

import com.liferay.configuration.admin.display.ConfigurationVisibilityController;
import com.liferay.cookies.configuration.CookiesPreferenceHandlingConfiguration;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PropsUtil;

import java.io.Serializable;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Olivér Kecskeméty
 */
@Component(
	immediate = true,
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

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-142518"))) {
			return false;
		}

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

	@Activate
	protected void activate(BundleContext bundleContext) {
		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-142518"))) {
			_serviceRegistration = bundleContext.registerService(
				ConfigurationVisibilityController.class,
				(scope, scopePK) -> false,
				HashMapDictionaryBuilder.put(
					"configuration.pid",
					"com.liferay.cookies.configuration." +
						"CookiesPreferenceHandlingConfiguration"
				).build());
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	private ServiceRegistration<ConfigurationVisibilityController>
		_serviceRegistration;

}