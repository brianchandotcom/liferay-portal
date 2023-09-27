/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnect;
import com.liferay.portal.security.sso.openid.connect.configuration.OpenIdConnectConfiguration;
import com.liferay.portal.security.sso.openid.connect.constants.OpenIdConnectConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = OpenIdConnect.class)
public class OpenIdConnectImpl implements OpenIdConnect {

	@Override
	public boolean isEnabled(long companyId) {
		try {
			OpenIdConnectConfiguration openIdConnectConfiguration =
				_configurationProvider.getConfiguration(
					OpenIdConnectConfiguration.class,
					new CompanyServiceSettingsLocator(
						companyId, OpenIdConnectConstants.SERVICE_NAME));

			return openIdConnectConfiguration.enabled();
		}
		catch (ConfigurationException configurationException) {
			_log.error(
				StringBundler.concat(
					"Unable to get OpenId configuration for company ",
					companyId, ": ", configurationException.getMessage()),
				configurationException);
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdConnectImpl.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

}