/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.login.web.internal.configuration;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.login.AuthLoginGroupSettings;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;

import java.util.Collections;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Erick Monteiro
 */
@Component(service = AuthLoginGroupSettings.class)
public class AuthLoginGroupSettingsImpl implements AuthLoginGroupSettings {

	@Override
	public boolean isPromptEnabled(long groupId) {
		AuthLoginConfiguration authLoginConfiguration =
			_getAuthLoginConfiguration(groupId);

		return authLoginConfiguration.promptEnabled();
	}

	private AuthLoginConfiguration _getAuthLoginConfiguration(long groupId) {
		try {
			return _configurationProvider.getGroupConfiguration(
				AuthLoginConfiguration.class, groupId);
		}
		catch (ConfigurationException configurationException) {
			if (_log.isWarnEnabled()) {
				_log.warn(configurationException);
			}

			return ConfigurableUtil.createConfigurable(
				AuthLoginConfiguration.class, Collections.emptyMap());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AuthLoginGroupSettingsImpl.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

}