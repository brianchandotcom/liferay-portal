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
import com.liferay.portal.kernel.util.HashMapDictionary;
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
@Component(service = DLSizeLimitConfigurationProvider.class)
public class DLSizeLimitConfigurationProviderImpl
	implements DLSizeLimitConfigurationProvider {

	@Override
	public long getCompanyFileMaxSize(long companyId) {
		return _dlSizeLimitManagedServiceFactory.getCompanyFileMaxSize(
			companyId);
	}

	@Override
	public long getCompanyMaxSizeToCopy(long companyId) {
		return _dlSizeLimitManagedServiceFactory.getCompanyMaxSizeToCopy(
			companyId);
	}

	@Override
	public Map<String, Long> getCompanyMimeTypeSizeLimit(long companyId) {
		return _dlSizeLimitManagedServiceFactory.getCompanyMimeTypeSizeLimit(
			companyId);
	}

	@Override
	public long getGroupFileMaxSize(long groupId) {
		return _dlSizeLimitManagedServiceFactory.getGroupFileMaxSize(groupId);
	}

	@Override
	public long getGroupMaxSizeToCopy(long groupId) {
		return _dlSizeLimitManagedServiceFactory.getGroupMaxSizeToCopy(groupId);
	}

	@Override
	public Map<String, Long> getGroupMimeTypeSizeLimit(long groupId) {
		return _dlSizeLimitManagedServiceFactory.getGroupMimeTypeSizeLimit(
			groupId);
	}

	@Override
	public long getSystemFileMaxSize() {
		return _dlSizeLimitManagedServiceFactory.getSystemFileMaxSize();
	}

	@Override
	public long getSystemMaxSizeToCopy() {
		return _dlSizeLimitManagedServiceFactory.getSystemMaxSizeToCopy();
	}

	@Override
	public Map<String, Long> getSystemMimeTypeSizeLimit() {
		return _dlSizeLimitManagedServiceFactory.getSystemMimeTypeSizeLimit();
	}

	@Override
	public void updateCompanySizeLimit(
			long companyId, long fileMaxSize,
			Map<String, Long> mimeTypeSizeLimit)
		throws Exception {

		Dictionary<String, Object> properties = null;

		Configuration configuration = _getScopedConfiguration(
			ExtendedObjectClassDefinition.Scope.COMPANY, companyId);

		if (configuration == null) {
			configuration = _configurationAdmin.createFactoryConfiguration(
				DLSizeLimitConfiguration.class.getName() + ".scoped",
				StringPool.QUESTION);

			properties = HashMapDictionaryBuilder.<String, Object>put(
				ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey(),
				companyId
			).build();
		}
		else {
			properties = configuration.getProperties();
		}

		_updateMimeTypeSizeLimitProperty(properties, mimeTypeSizeLimit);

		properties.put("fileMaxSize", fileMaxSize);

		configuration.update(properties);
	}

	@Override
	public void updateGroupSizeLimit(
			long groupId, long fileMaxSize, Map<String, Long> mimeTypeSizeLimit)
		throws Exception {

		Dictionary<String, Object> properties = null;

		Configuration configuration = _getScopedConfiguration(
			ExtendedObjectClassDefinition.Scope.GROUP, groupId);

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

		_updateMimeTypeSizeLimitProperty(properties, mimeTypeSizeLimit);

		properties.put("fileMaxSize", fileMaxSize);

		configuration.update(properties);
	}

	@Override
	public void updateSystemSizeLimit(
			long fileMaxSize, Map<String, Long> mimeTypeSizeLimit)
		throws Exception {

		Configuration configuration = _configurationAdmin.getConfiguration(
			DLSizeLimitConfiguration.class.getName(), StringPool.QUESTION);

		Dictionary<String, Object> properties = configuration.getProperties();

		if (properties == null) {
			properties = new HashMapDictionary<>();
		}

		_updateMimeTypeSizeLimitProperty(properties, mimeTypeSizeLimit);

		properties.put("fileMaxSize", fileMaxSize);

		configuration.update(properties);
	}

	private Configuration _getScopedConfiguration(
			ExtendedObjectClassDefinition.Scope scope, long scopePK)
		throws Exception {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			String.format(
				"(&(service.factoryPid=%s)(%s=%d))",
				DLSizeLimitConfiguration.class.getName() + ".scoped",
				scope.getPropertyKey(), scopePK));

		if (configurations == null) {
			return null;
		}

		return configurations[0];
	}

	private void _updateMimeTypeSizeLimitProperty(
		Dictionary<String, Object> properties,
		Map<String, Long> mimeTypeSizeLimit) {

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
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private DLSizeLimitManagedServiceFactory _dlSizeLimitManagedServiceFactory;

}