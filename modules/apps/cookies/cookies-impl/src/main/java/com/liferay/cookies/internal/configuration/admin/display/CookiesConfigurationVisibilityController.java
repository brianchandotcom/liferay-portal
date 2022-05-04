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
import com.liferay.cookies.configuration.CookiesBannerConfiguration;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;

import java.io.Serializable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Olivér Kecskeméty
 */
@Component(
	immediate = true,
	property = "configuration.pid=com.liferay.cookies.configuration.CookiesConsentConfiguration",
	service = ConfigurationVisibilityController.class
)
public class CookiesConfigurationVisibilityController
	implements ConfigurationVisibilityController {

	@Override
	public boolean isVisible(
		ExtendedObjectClassDefinition.Scope scope, Serializable scopePK) {

		try {
			CookiesBannerConfiguration cookiesBannerConfiguration = null;

			if (ExtendedObjectClassDefinition.Scope.SYSTEM.equals(scope)) {
				cookiesBannerConfiguration =
					_configurationProvider.getSystemConfiguration(
						CookiesBannerConfiguration.class);
			}

			if (ExtendedObjectClassDefinition.Scope.COMPANY.equals(scope)) {
				cookiesBannerConfiguration =
					_configurationProvider.getCompanyConfiguration(
						CookiesBannerConfiguration.class, (Long)scopePK);
			}

			if (ExtendedObjectClassDefinition.Scope.GROUP.equals(scope)) {
				cookiesBannerConfiguration =
					_configurationProvider.getGroupConfiguration(
						CookiesBannerConfiguration.class, (Long)scopePK);
			}

			if (cookiesBannerConfiguration != null) {
				return cookiesBannerConfiguration.enabled();
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