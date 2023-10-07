/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.security.sso.openid.connect.configuration.OpenIdConnectConfiguration;

import java.util.Dictionary;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pedro Victor Silvestre
 */
@Component(
	property = "model.class.name=com.liferay.portal.security.sso.openid.connect.configuration.OpenIdConnectConfiguration",
	service = ConfigurationModelListener.class
)
public class OpenIdConnectConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		int tokenRefreshOffset = GetterUtil.getInteger(
			properties.get("tokenRefreshOffset"));

		if (tokenRefreshOffset < 30) {
			throw new ConfigurationModelListenerException(
				"Token refresh offset is less than 30 seconds",
				OpenIdConnectConfiguration.class,
				OpenIdConnectConfigurationModelListener.class, properties);
		}
	}

}