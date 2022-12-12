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
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

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
	public int getCheckInterval(long companyId) throws ConfigurationException {
		KBServiceConfiguration kbServiceConfiguration =
			_configurationProvider.getCompanyConfiguration(
				KBServiceConfiguration.class, companyId);

		return kbServiceConfiguration.checkInterval();
	}

	@Override
	public int getExpirationDateNotificationDateWeeks(long companyId)
		throws ConfigurationException {

		KBServiceConfiguration kbServiceConfiguration =
			_configurationProvider.getCompanyConfiguration(
				KBServiceConfiguration.class, companyId);

		return kbServiceConfiguration.expirationDateNotificationDateWeeks();
	}

	@Override
	public void updateExpirationDateConfiguration(
			int checkInterval, long companyId,
			int expirationDateNotificationDateWeeks)
		throws Exception {

		Dictionary<String, Object> properties = null;

		Configuration configuration = _getScopedConfiguration(
			ExtendedObjectClassDefinition.Scope.COMPANY, companyId);

		if (configuration == null) {
			configuration = _configurationAdmin.createFactoryConfiguration(
				KBServiceConfiguration.class.getName() + ".scoped",
				StringPool.QUESTION);

			properties = HashMapDictionaryBuilder.<String, Object>put(
				ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey(),
				companyId
			).build();
		}
		else {
			properties = configuration.getProperties();
		}

		properties.put("checkInterval", checkInterval);

		properties.put(
			"expirationDateNotificationDateWeeks",
			expirationDateNotificationDateWeeks);

		configuration.update(properties);
	}

	private Configuration _getScopedConfiguration(
			ExtendedObjectClassDefinition.Scope scope, long scopePK)
		throws Exception {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			String.format(
				"(&(service.factoryPid=%s)(%s=%d))",
				KBServiceConfiguration.class.getName() + ".scoped",
				scope.getPropertyKey(), scopePK));

		if (configurations == null) {
			return null;
		}

		return configurations[0];
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ConfigurationProvider _configurationProvider;

}