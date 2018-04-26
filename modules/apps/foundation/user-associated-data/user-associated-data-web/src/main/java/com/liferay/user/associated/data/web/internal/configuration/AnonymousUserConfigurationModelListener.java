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

package com.liferay.user.associated.data.web.internal.configuration;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.user.associated.data.web.internal.configuration.AnonymousUserConfiguration",
	service = ConfigurationModelListener.class
)
public class AnonymousUserConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		try {
			long companyId = (long)properties.get("companyId");

			_companyLocalService.getCompanyById(companyId);

			_userLocalService.getUserById(
				companyId, (long)properties.get("userId"));

			_validateUniqueConfiguration(pid, companyId);
		}
		catch (Exception e) {
			throw new ConfigurationModelListenerException(
				e.getMessage(), AnonymousUserConfiguration.class, getClass(),
				properties);
		}
	}

	private void _validateUniqueConfiguration(String pid, long companyId)
		throws Exception {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.factoryPid=com.liferay.user.associated.data.web." +
				"internal.configuration.AnonymousUserConfiguration)");

		if (configurations == null) {
			return;
		}

		for (Configuration configuration : configurations) {
			if (pid.equals(configuration.getPid())) {
				continue;
			}

			Dictionary<String, Object> properties =
				configuration.getProperties();

			if (companyId == (long)properties.get("companyId")) {
				throw new Exception(
					"An anonymous user is already defined for the company: " +
						companyId);
			}
		}
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private UserLocalService _userLocalService;

}