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

package com.liferay.login.web.internal.configuration;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.login.AuthLoginGroupSettings;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;

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