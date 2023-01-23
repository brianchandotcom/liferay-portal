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

package com.liferay.knowledge.base.internal.configuration;

import com.liferay.knowledge.base.configuration.KBServiceConfigurationProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.io.IOException;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(service = KBServiceConfigurationProvider.class)
public class KBServiceConfigurationProviderImpl
	implements KBServiceConfigurationProvider {

	@Override
	public int getCheckInterval() throws ConfigurationException {
		KBServiceConfiguration kbServiceConfiguration =
			_configurationProvider.getSystemConfiguration(
				KBServiceConfiguration.class);

		return kbServiceConfiguration.checkInterval();
	}

	@Override
	public int getExpirationDateNotificationDateWeeks()
		throws ConfigurationException {

		KBServiceConfiguration kbServiceConfiguration =
			_configurationProvider.getSystemConfiguration(
				KBServiceConfiguration.class);

		return kbServiceConfiguration.expirationDateNotificationDateWeeks();
	}

	@Override
	public void updateExpirationDateConfiguration(
			int checkInterval, int expirationDateNotificationDateWeeks)
		throws IOException {

		Configuration configuration = _configurationAdmin.getConfiguration(
			KBServiceConfiguration.class.getName(), StringPool.QUESTION);

		Dictionary<String, Object> properties = configuration.getProperties();

		if (properties == null) {
			properties = new HashMapDictionary<>();
		}

		properties.put("checkInterval", checkInterval);

		properties.put(
			"expirationDateNotificationDateWeeks",
			expirationDateNotificationDateWeeks);

		configuration.update(properties);
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ConfigurationProvider _configurationProvider;

}