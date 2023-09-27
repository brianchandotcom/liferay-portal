/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.configuration;

import com.liferay.knowledge.base.configuration.KBServiceConfigurationProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
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