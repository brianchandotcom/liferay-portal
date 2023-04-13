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

package com.liferay.site.internal.configuration;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.site.configuration.MenuAccessConfiguration;
import com.liferay.site.configuration.MenuAccessConfigurationProvider;

import java.io.IOException;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mikel Lorza
 */
@Component(service = MenuAccessConfigurationProvider.class)
public class MenuAccessConfigurationProviderImpl
	implements MenuAccessConfigurationProvider {

	@Override
	public String[] getRolesCanSeeControlMenu(long groupId)
		throws ConfigurationException {

		MenuAccessConfiguration menuAccessConfiguration =
			_configurationProvider.getGroupConfiguration(
				MenuAccessConfiguration.class, groupId);

		return menuAccessConfiguration.rolesCanSeeControlMenu();
	}

	@Override
	public boolean isShowControlMenuByRole(long groupId)
		throws ConfigurationException {

		MenuAccessConfiguration menuAccessConfiguration =
			_configurationProvider.getGroupConfiguration(
				MenuAccessConfiguration.class, groupId);

		return menuAccessConfiguration.showControlMenuByRole();
	}

	@Override
	public void updateMenuAccessConfiguration(
			String[] rolesCanSeeControlMenu, boolean showControlMenuByRole)
		throws IOException {

		Configuration configuration = _configurationAdmin.getConfiguration(
			MenuAccessConfiguration.class.getName(), StringPool.QUESTION);

		Dictionary<String, Object> properties = configuration.getProperties();

		if (properties == null) {
			properties = new HashMapDictionary<>();
		}

		properties.put("rolesCanSeeControlMenu", rolesCanSeeControlMenu);
		properties.put("showControlMenuByRole", showControlMenuByRole);

		configuration.update(properties);
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ConfigurationProvider _configurationProvider;

}