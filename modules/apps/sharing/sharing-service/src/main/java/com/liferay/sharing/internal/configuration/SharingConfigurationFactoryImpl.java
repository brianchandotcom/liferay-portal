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
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.sharing.configuration.SharingConfiguration;
import com.liferay.sharing.configuration.SharingConfigurationFactory;

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
	public SharingConfiguration getCompanySharingConfiguration(
		Company company) {

		return _getSharingConfiguration(company.getCompanyId(), null);
	}

	@Override
	public SharingConfiguration getGroupSharingConfiguration(Group group) {
		return _getSharingConfiguration(group.getCompanyId(), group);
	}

	@Override
	public SharingConfiguration getSystemSharingConfiguration() {
		return new SharingConfigurationImpl(
			null, null, null, _sharingSystemConfiguration);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_sharingSystemConfiguration = ConfigurableUtil.createConfigurable(
			SharingSystemConfiguration.class, properties);
	}

	private SharingConfiguration _getSharingConfiguration(
		long companyId, Group group) {

		try {
			SharingGroupConfiguration sharingGroupConfiguration = null;

			if (group != null) {
				sharingGroupConfiguration =
					_configurationProvider.getGroupConfiguration(
						SharingGroupConfiguration.class, group.getGroupId());
			}

			SharingCompanyConfiguration sharingCompanyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					SharingCompanyConfiguration.class, companyId);

			return new SharingConfigurationImpl(
				group, sharingGroupConfiguration, sharingCompanyConfiguration,
				_sharingSystemConfiguration);
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException, configurationException);

			return new SharingConfigurationImpl(
				null, null, null, _sharingSystemConfiguration);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SharingConfigurationFactoryImpl.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	private volatile SharingSystemConfiguration _sharingSystemConfiguration;

	private static class SharingConfigurationImpl
		implements SharingConfiguration {

		public SharingConfigurationImpl(
			Group group, SharingGroupConfiguration sharingGroupConfiguration,
			SharingCompanyConfiguration sharingCompanyConfiguration,
			SharingSystemConfiguration sharingSystemConfiguration) {

			_group = group;
			_sharingGroupConfiguration = sharingGroupConfiguration;
			_sharingCompanyConfiguration = sharingCompanyConfiguration;
			_sharingSystemConfiguration = sharingSystemConfiguration;
		}

		@Override
		public boolean isAvailable() {
			if (!_sharingSystemConfiguration.enabled()) {
				return false;
			}

			if ((_sharingCompanyConfiguration != null) &&
				!_sharingCompanyConfiguration.enabled()) {

				return false;
			}

			if ((_group != null) && _group.isStagingGroup()) {
				return false;
			}

			return true;
		}

		@Override
		public boolean isEnabled() {
			if (!_sharingSystemConfiguration.enabled()) {
				return false;
			}

			if ((_sharingCompanyConfiguration != null) &&
				!_sharingCompanyConfiguration.enabled()) {

				return false;
			}

			if (_group == null) {
				return true;
			}

			if (_group.isStagingGroup()) {
				return false;
			}

			UnicodeProperties typeSettingsProperties =
				_group.getTypeSettingsProperties();

			if (typeSettingsProperties.containsKey("sharingEnabled")) {
				return GetterUtil.getBoolean(
					typeSettingsProperties.get("sharingEnabled"));
			}

			if (_sharingGroupConfiguration == null) {
				return true;
			}

			return _sharingGroupConfiguration.enabled();
		}

		private final Group _group;
		private final SharingCompanyConfiguration _sharingCompanyConfiguration;
		private final SharingGroupConfiguration _sharingGroupConfiguration;
		private final SharingSystemConfiguration _sharingSystemConfiguration;

	}

}