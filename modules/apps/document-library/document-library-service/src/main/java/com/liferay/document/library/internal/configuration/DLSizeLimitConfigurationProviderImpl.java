/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.configuration;

import com.liferay.document.library.configuration.DLSizeLimitConfigurationProvider;
import com.liferay.document.library.internal.configuration.helper.DLSizeLimitConfigurationHelper;
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
		return _dlSizeLimitConfigurationHelper.getCompanyFileMaxSize(companyId);
	}

	@Override
	public long getCompanyMaxSizeToCopy(long companyId) {
		return _dlSizeLimitConfigurationHelper.getCompanyMaxSizeToCopy(
			companyId);
	}

	@Override
	public Map<String, Long> getCompanyMimeTypeSizeLimit(long companyId) {
		return _dlSizeLimitConfigurationHelper.getCompanyMimeTypeSizeLimit(
			companyId);
	}

	@Override
	public long getGroupFileMaxSize(long groupId) {
		return _dlSizeLimitConfigurationHelper.getGroupFileMaxSize(groupId);
	}

	@Override
	public long getGroupMaxSizeToCopy(long groupId) {
		return _dlSizeLimitConfigurationHelper.getGroupMaxSizeToCopy(groupId);
	}

	@Override
	public Map<String, Long> getGroupMimeTypeSizeLimit(long groupId) {
		return _dlSizeLimitConfigurationHelper.getGroupMimeTypeSizeLimit(
			groupId);
	}

	@Override
	public long getSystemFileMaxSize() {
		return _dlSizeLimitConfigurationHelper.getSystemFileMaxSize();
	}

	@Override
	public long getSystemMaxSizeToCopy() {
		return _dlSizeLimitConfigurationHelper.getSystemMaxSizeToCopy();
	}

	@Override
	public Map<String, Long> getSystemMimeTypeSizeLimit() {
		return _dlSizeLimitConfigurationHelper.getSystemMimeTypeSizeLimit();
	}

	@Override
	public void updateCompanySizeLimit(
			long companyId, long fileMaxSize, long maxSizeToCopy,
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
		properties.put("maxSizeToCopy", maxSizeToCopy);

		configuration.update(properties);
	}

	@Override
	public void updateGroupSizeLimit(
			long groupId, long fileMaxSize, long maxSizeToCopy,
			Map<String, Long> mimeTypeSizeLimit)
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
		properties.put("maxSizeToCopy", maxSizeToCopy);

		configuration.update(properties);
	}

	@Override
	public void updateSystemSizeLimit(
			long fileMaxSize, long maxSizeToCopy,
			Map<String, Long> mimeTypeSizeLimit)
		throws Exception {

		Configuration configuration = _configurationAdmin.getConfiguration(
			DLSizeLimitConfiguration.class.getName(), StringPool.QUESTION);

		Dictionary<String, Object> properties = configuration.getProperties();

		if (properties == null) {
			properties = new HashMapDictionary<>();
		}

		_updateMimeTypeSizeLimitProperty(properties, mimeTypeSizeLimit);

		properties.put("fileMaxSize", fileMaxSize);
		properties.put("maxSizeToCopy", maxSizeToCopy);

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
	private DLSizeLimitConfigurationHelper _dlSizeLimitConfigurationHelper;

}