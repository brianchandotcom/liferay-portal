/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.defaultpermissions.web.internal.configuration.manager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.defaultpermissions.configuration.PortalDefaultPermissionsCompanyConfiguration;
import com.liferay.portal.kernel.defaultpermissions.configuration.manager.PortalDefaultPermissionsConfigurationManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(
	property = "portal.default.permissions.scope=company",
	service = PortalDefaultPermissionsConfigurationManager.class
)
public class CompanyPortalDefaultPermissionsConfigurationManagerImpl
	implements PortalDefaultPermissionsConfigurationManager {

	@Override
	public Map<String, Map<String, String[]>> getDefaultPermissions(
		long companyId, long groupId) {

		try {
			PortalDefaultPermissionsCompanyConfiguration
				portalDefaultPermissionsCompanyConfiguration =
					_configurationProvider.getCompanyConfiguration(
						PortalDefaultPermissionsCompanyConfiguration.class,
						companyId);

			String defaultPermissions =
				portalDefaultPermissionsCompanyConfiguration.
					defaultPermissions();

			if (Validator.isNull(defaultPermissions)) {
				return new HashMap<>();
			}

			return _objectMapper.readValue(defaultPermissions, _typeReference);
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return null;
	}

	@Override
	public Map<String, String[]> getDefaultPermissions(
		long companyId, long groupId, String className) {

		Map<String, Map<String, String[]>> defaultPermissions =
			getDefaultPermissions(companyId, groupId);

		if (defaultPermissions == null) {
			return null;
		}

		return defaultPermissions.get(className);
	}

	@Override
	public String getScope() {
		return ExtendedObjectClassDefinition.Scope.COMPANY.toString();
	}

	@Override
	public void saveDefaultPermissions(
		long primaryKey,
		Map<String, Map<String, String[]>> defaultPermissions) {

		try {
			_configurationProvider.saveCompanyConfiguration(
				PortalDefaultPermissionsCompanyConfiguration.class, primaryKey,
				HashMapDictionaryBuilder.<String, Object>put(
					"defaultPermissions",
					_objectMapper.writeValueAsString(defaultPermissions)
				).build());
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyPortalDefaultPermissionsConfigurationManagerImpl.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	private final ObjectMapper _objectMapper = new ObjectMapper();
	private final TypeReference<Map<String, Map<String, String[]>>>
		_typeReference =
			new TypeReference<Map<String, Map<String, String[]>>>() {
			};

}