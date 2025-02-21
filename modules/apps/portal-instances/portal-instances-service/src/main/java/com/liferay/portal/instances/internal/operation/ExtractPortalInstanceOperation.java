/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.internal.operation;

import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.db.partition.util.DBPartitionUtil;
import com.liferay.portal.instances.internal.configuration.ExtractPortalInstanceConfiguration;
import com.liferay.portal.kernel.db.partition.DBPartition;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.felix.cm.file.ConfigurationHandler;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mariano Álvaro Sáiz
 */
@Component(
	configurationPid = "com.liferay.portal.instances.internal.configuration.ExtractPortalInstanceConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, service = {}
)
public class ExtractPortalInstanceOperation
	extends BasePortalInstanceOperation {

	@Override
	public String getOperationCompletedMessage(long companyId) {
		return "Portal instance with company ID " + companyId +
			" extracted successfully";
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		onPortalInstance(
			() -> {
				ExtractPortalInstanceConfiguration
					extractPortalInstanceConfiguration =
						ConfigurableUtil.createConfigurable(
							ExtractPortalInstanceConfiguration.class,
							properties);

				long companyId =
					extractPortalInstanceConfiguration.extractCompanyId();

				if (_companyLocalService.fetchCompany(companyId) == null) {
					_log.error(
						"Portal instance with company ID " + companyId +
							" does not exist");

					return null;
				}

				Company company = _companyLocalService.extractCompany(
					companyId);

				_copyConfigurations(companyId);

				return company;
			},
			properties);
	}

	private void _copyConfigurations(long companyId) throws Exception {
		if (DBPartition.isPartitionEnabled()) {
			return;
		}

		List<ScopedConfiguration> scopedConfigurations = new ArrayList<>();

		Map<String, String> configurations = DBPartitionUtil.getConfigurations(
			CompanyConstants.SYSTEM);

		for (Map.Entry<String, String> entry : configurations.entrySet()) {
			String dictionaryString = entry.getValue();

			ScopedConfiguration scopedConfiguration = _getScopedConfiguration(
				entry.getKey(), dictionaryString);

			if (scopedConfiguration != null) {
				if (Objects.equals(
						scopedConfiguration.getScope(),
						ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE)) {

					scopedConfigurations.add(scopedConfiguration);

					continue;
				}

				if (_isApplicable(scopedConfiguration, companyId)) {
					scopedConfigurations.add(scopedConfiguration);
				}
			}
		}

		for (ScopedConfiguration currentScopedConfiguration :
				scopedConfigurations) {

			DBPartitionUtil.insertExtractedConfiguration(
				companyId, currentScopedConfiguration.getConfigurationId(),
				currentScopedConfiguration.getDictionary());
		}
	}

	private ScopedConfiguration _getScopedConfiguration(
			String configurationId, String dictionary)
		throws Exception {

		Dictionary<String, String> dictionaryMap = ConfigurationHandler.read(
			new UnsyncByteArrayInputStream(
				dictionary.getBytes(StringPool.UTF8)));

		Object value = dictionaryMap.get(
			ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey());

		if (value != null) {
			return new ScopedConfiguration(
				configurationId, dictionary, GetterUtil.getLong(value),
				ExtendedObjectClassDefinition.Scope.COMPANY);
		}

		value = dictionaryMap.get(
			ExtendedObjectClassDefinition.Scope.GROUP.getPropertyKey());

		if (value != null) {
			return new ScopedConfiguration(
				configurationId, dictionary, GetterUtil.getLong(value),
				ExtendedObjectClassDefinition.Scope.GROUP);
		}

		value = dictionaryMap.get(
			ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE.
				getPropertyKey());

		if (value != null) {
			return new ScopedConfiguration(
				configurationId, dictionary, GetterUtil.getString(value),
				ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE);
		}

		return null;
	}

	private boolean _isApplicable(
			ScopedConfiguration scopedConfiguration, long companyId)
		throws Exception {

		if (Objects.equals(
				scopedConfiguration.getScope(),
				ExtendedObjectClassDefinition.Scope.COMPANY)) {

			if (companyId == (long)scopedConfiguration.getScopePK()) {
				return true;
			}

			return false;
		}

		if (Objects.equals(
				scopedConfiguration.getScope(),
				ExtendedObjectClassDefinition.Scope.GROUP)) {

			Group group = _groupLocalService.getGroup(
				(long)scopedConfiguration.getScopePK());

			if (group.getCompanyId() == companyId) {
				return true;
			}

			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExtractPortalInstanceOperation.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	private class ScopedConfiguration {

		public ScopedConfiguration(
			String configurationId, String dictionary, Serializable scopePK,
			ExtendedObjectClassDefinition.Scope scope) {

			_configurationId = configurationId;
			_dictionary = dictionary;
			_scopePK = scopePK;
			_scope = scope;
		}

		public String getConfigurationId() {
			return _configurationId;
		}

		public String getDictionary() {
			return _dictionary;
		}

		public ExtendedObjectClassDefinition.Scope getScope() {
			return _scope;
		}

		public Object getScopePK() {
			return _scopePK;
		}

		private final String _configurationId;
		private final String _dictionary;
		private final ExtendedObjectClassDefinition.Scope _scope;
		private final Object _scopePK;

	}

}