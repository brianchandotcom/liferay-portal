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

package com.liferay.document.library.internal.configuration;

import com.liferay.document.library.configuration.DLSizeLimitConfigurationProvider;
import com.liferay.document.library.internal.configuration.admin.service.DLSizeLimitManagedServiceFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.Dictionary;
import java.util.Map;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = DLSizeLimitConfigurationProvider.class)
public class DLSizeLimitConfigurationProviderImpl
	implements DLSizeLimitConfigurationProvider {

	@Override
	public Map<String, Long> getGroupMimeTypeSizeLimit(long groupId) {
		return _dlSizeLimitManagedServiceFactory.getGroupMimeTypeSizeLimit(
			groupId);
	}

	@Override
	public void updateGroupMimeTypeSizeLimit(
			long groupId, Map<String, Long> mimeTypeSizeLimit)
		throws Exception {

		Dictionary<String, Object> properties;

		Configuration configuration = _getGroupConfiguration(groupId);

		if (configuration == null) {
			configuration = _configurationAdmin.createFactoryConfiguration(
				DLSizeLimitConfiguration.class.getName() + ".scoped",
				StringPool.QUESTION);

			properties = HashMapDictionaryBuilder.<String, Object>put(
				ExtendedObjectClassDefinition.Scope.GROUP.getPropertyKey(),
				groupId
			).build();
		}
		else {
			properties = configuration.getProperties();
		}

		if (mimeTypeSizeLimit.isEmpty()) {
			properties.put("mimeTypeSizeLimit", new String[0]);
		}
		else {
			String[] mimeTypeSizeLimitArray =
				new String[mimeTypeSizeLimit.size()];

			int i = 0;

			for (Map.Entry<String, Long> entry : mimeTypeSizeLimit.entrySet()) {
				mimeTypeSizeLimitArray[i] =
					entry.getKey() + StringPool.COLON + entry.getValue();

				i++;
			}

			properties.put("mimeTypeSizeLimit", mimeTypeSizeLimitArray);
		}

		configuration.update(properties);
	}

	private Configuration _getGroupConfiguration(long groupId)
		throws Exception {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			String.format(
				"(&(service.factoryPid=%s)(%s=%d))",
				DLSizeLimitConfiguration.class.getName() + ".scoped",
				ExtendedObjectClassDefinition.Scope.GROUP.getPropertyKey(),
				groupId));

		if (configurations == null) {
			return null;
		}

		return configurations[0];
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private DLSizeLimitManagedServiceFactory _dlSizeLimitManagedServiceFactory;

}