/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.internal.configuration.settings;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.users.admin.configuration.UserFileUploadsConfiguration;
import com.liferay.users.admin.kernel.file.uploads.UserFileUploadsSettings;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	configurationPid = "com.liferay.users.admin.configuration.UserFileUploadsConfiguration",
	service = UserFileUploadsSettings.class
)
public class UserFileUploadsSettingsImpl implements UserFileUploadsSettings {

	@Override
	public int getImageMaxHeight() {
		UserFileUploadsConfiguration userFileUploadsConfiguration =
			_getUserFileUploadsConfiguration();

		return userFileUploadsConfiguration.imageMaxHeight();
	}

	@Override
	public long getImageMaxSize() {
		UserFileUploadsConfiguration userFileUploadsConfiguration =
			_getUserFileUploadsConfiguration();

		return userFileUploadsConfiguration.imageMaxSize();
	}

	@Override
	public int getImageMaxWidth() {
		UserFileUploadsConfiguration userFileUploadsConfiguration =
			_getUserFileUploadsConfiguration();

		return userFileUploadsConfiguration.imageMaxWidth();
	}

	@Override
	public boolean isImageCheckToken() {
		UserFileUploadsConfiguration userFileUploadsConfiguration =
			_getUserFileUploadsConfiguration();

		return userFileUploadsConfiguration.imageCheckToken();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_defaultUserFileUploadsConfiguration =
			ConfigurableUtil.createConfigurable(
				UserFileUploadsConfiguration.class, properties);
	}

	private UserFileUploadsConfiguration _getUserFileUploadsConfiguration() {
		try {
			return _configurationProvider.getCompanyConfiguration(
				UserFileUploadsConfiguration.class,
				CompanyThreadLocal.getCompanyId());
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException);
		}

		return _defaultUserFileUploadsConfiguration;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserFileUploadsSettingsImpl.class.getName());

	@Reference
	private ConfigurationProvider _configurationProvider;

	private volatile UserFileUploadsConfiguration
		_defaultUserFileUploadsConfiguration;

}