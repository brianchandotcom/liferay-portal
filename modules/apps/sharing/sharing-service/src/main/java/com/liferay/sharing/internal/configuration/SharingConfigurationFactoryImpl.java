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

package com.liferay.sharing.internal.configuration;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.sharing.configuration.SharingConfiguration;
import com.liferay.sharing.configuration.SharingConfigurationFactory;
import com.liferay.sharing.constants.SharingConstants;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	configurationPid = "com.liferay.sharing.internal.configuration.SharingSystemConfiguration",
	service = SharingConfigurationFactory.class
)
public class SharingConfigurationFactoryImpl
	implements SharingConfigurationFactory {

	@Override
	public SharingConfiguration getSharingConfiguration(Group group) {
		try {
			SharingCompanyConfiguration sharingCompanyConfiguration =
				_configurationProvider.getConfiguration(
					SharingCompanyConfiguration.class,
					new CompanyServiceSettingsLocator(
						group.getCompanyId(), SharingConstants.SERVICE_NAME));

			return new SharingConfigurationImpl(
				sharingCompanyConfiguration, _sharingSystemConfiguration);
		}
		catch (ConfigurationException ce) {
			_log.error(ce, ce);

			return new SharingConfigurationImpl(
				null, _sharingSystemConfiguration);
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_sharingSystemConfiguration = ConfigurableUtil.createConfigurable(
			SharingSystemConfiguration.class, properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SharingConfigurationFactoryImpl.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	private volatile SharingSystemConfiguration _sharingSystemConfiguration;

	private static class SharingConfigurationImpl
		implements SharingConfiguration {

		public SharingConfigurationImpl(
			SharingCompanyConfiguration sharingCompanyConfiguration,
			SharingSystemConfiguration sharingSystemConfiguration) {

			_sharingCompanyConfiguration = sharingCompanyConfiguration;
			_sharingSystemConfiguration = sharingSystemConfiguration;
		}

		@Override
		public boolean isEnabled() {
			if (!_sharingSystemConfiguration.enabled()) {
				return false;
			}

			if (_sharingCompanyConfiguration != null) {
				return _sharingCompanyConfiguration.enabled();
			}

			return true;
		}

		private final SharingCompanyConfiguration _sharingCompanyConfiguration;
		private final SharingSystemConfiguration _sharingSystemConfiguration;

	}

}