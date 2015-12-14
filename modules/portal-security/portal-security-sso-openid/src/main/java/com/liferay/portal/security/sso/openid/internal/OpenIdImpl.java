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

package com.liferay.portal.security.sso.openid.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationFactory;
import com.liferay.portal.kernel.openid.OpenId;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.security.sso.openid.constants.OpenIdConstants;
import com.liferay.portal.security.sso.openid.module.configuration.OpenIdConfiguration;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Serves as the core implementation of the OpenID protocol.
 *
 * <p>
 * This class is utilized by many other classes via {@link
 * com.liferay.portal.util.OpenIdUtil} which exposes all of its methods
 * statically.
 * </p>
 *
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.security.sso.openid.module.configuration.OpenIdConfiguration",
	immediate = true, service = OpenId.class
)
public class OpenIdImpl implements OpenId {

	@Override
	public boolean isEnabled(long companyId) {
		try {
			OpenIdConfiguration openIdConfiguration =
				_configurationFactory.getConfiguration(
					OpenIdConfiguration.class,
					new CompanyServiceSettingsLocator(
						companyId, OpenIdConstants.SERVICE_NAME));

			return openIdConfiguration.enabled();
		}
		catch (ConfigurationException ce) {
			_log.error("Unable to get OpenId configuration", ce);
		}

		return false;
	}

	@Reference(unbind = "-")
	protected void setConfigurationFactory(
		ConfigurationFactory configurationFactory) {

		_configurationFactory = configurationFactory;
	}

	private static final Log _log = LogFactoryUtil.getLog(OpenIdImpl.class);

	private volatile ConfigurationFactory _configurationFactory;

}