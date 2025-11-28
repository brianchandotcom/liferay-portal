/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.configuration.internal.resource.v1_0;

import com.liferay.configuration.admin.exportimport.ConfigurationExportImportProcessor;
import com.liferay.configuration.admin.util.ConfigurationFilterStringUtil;
import com.liferay.headless.admin.configuration.dto.v1_0.SystemConfiguration;
import com.liferay.headless.admin.configuration.internal.util.ConfigurationUtil;
import com.liferay.headless.admin.configuration.resource.v1_0.SystemConfigurationResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.settings.SettingsLocatorHelper;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.pagination.Page;

import jakarta.validation.ValidationException;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Thiago Buarque
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/system-configuration.properties",
	scope = ServiceScope.PROTOTYPE, service = SystemConfigurationResource.class
)
public class SystemConfigurationResourceImpl
	extends BaseSystemConfigurationResourceImpl {

	@Override
	public SystemConfiguration getSystemConfiguration(
			String systemConfigurationExternalReferenceCode)
		throws Exception {

		_checkFeatureFlag();

		_checkPermission();

		_validateDefaultCompany();

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			ConfigurationFilterStringUtil.getSystemScopedFilterString(
				systemConfigurationExternalReferenceCode));

		if (ArrayUtil.isEmpty(configurations)) {
			throw new NotFoundException(
				"Unable to find system configuration with external reference " +
					"code " + systemConfigurationExternalReferenceCode);
		}

		if (configurations.length > 1) {
			List<String> pids = new ArrayList<>();

			for (Configuration configuration : configurations) {
				pids.add(configuration.getPid());
			}

			throw new BadRequestException(
				StringBundler.concat(
					systemConfigurationExternalReferenceCode,
					" is a factory configuration. Specify one of these PIDs: ",
					ListUtil.toString(pids, StringPool.BLANK, StringPool.COMMA),
					"."));
		}

		return _toSystemConfiguration(configurations[0]);
	}

	@Override
	public Page<SystemConfiguration> getSystemConfigurationsPage()
		throws Exception {

		_checkFeatureFlag();

		_checkPermission();

		_validateDefaultCompany();

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			ConfigurationFilterStringUtil.getSystemScopedFilterString());

		if (ArrayUtil.isEmpty(configurations)) {
			return Page.of(Collections.emptyList());
		}

		List<SystemConfiguration> systemConfigurations = new ArrayList<>();

		for (Configuration configuration : configurations) {
			SystemConfiguration systemConfiguration = _toSystemConfiguration(
				configuration);

			if (systemConfiguration == null) {
				continue;
			}

			systemConfigurations.add(systemConfiguration);
		}

		return Page.of(systemConfigurations);
	}

	@Override
	public SystemConfiguration postSystemConfiguration(
			SystemConfiguration systemConfiguration)
		throws Exception {

		return putSystemConfiguration(
			systemConfiguration.getExternalReferenceCode(),
			systemConfiguration);
	}

	@Override
	public SystemConfiguration putSystemConfiguration(
			String systemConfigurationExternalReferenceCode,
			SystemConfiguration systemConfiguration)
		throws Exception {

		_checkFeatureFlag();

		_checkPermission();

		_validateDefaultCompany();

		systemConfiguration.setExternalReferenceCode(
			() -> systemConfigurationExternalReferenceCode);

		String filterString =
			ConfigurationFilterStringUtil.getSystemScopedFilterString(
				systemConfiguration.getExternalReferenceCode());

		try {
			Configuration configuration =
				ConfigurationUtil.addOrUpdateConfiguration(
					null, _configurationAdmin,
					systemConfiguration.getExternalReferenceCode(),
					filterString, systemConfiguration.getProperties(),
					ExtendedObjectClassDefinition.Scope.SYSTEM,
					_settingsLocatorHelper);

			if (configuration == null) {
				throw new NotFoundException(
					"Unable to find system configuration with external " +
						"reference code " +
							systemConfiguration.getExternalReferenceCode());
			}

			return _toSystemConfiguration(configuration);
		}
		catch (ValidationException validationException) {
			throw new BadRequestException(validationException.getMessage());
		}
	}

	private void _checkFeatureFlag() {
		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-65399")) {

			throw new UnsupportedOperationException();
		}
	}

	private void _checkPermission() {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (!permissionChecker.isOmniadmin()) {
			throw new NotAuthorizedException(Response.Status.UNAUTHORIZED);
		}
	}

	private SystemConfiguration _toSystemConfiguration(
			Configuration configuration)
		throws Exception {

		Map<String, Object> properties = ConfigurationUtil.getProperties(
			configuration, _configurationExportImportProcessor,
			ExtendedObjectClassDefinition.Scope.SYSTEM, _settingsLocatorHelper);

		if (properties.isEmpty()) {
			return null;
		}

		SystemConfiguration systemConfiguration = new SystemConfiguration();

		systemConfiguration.setExternalReferenceCode(configuration::getPid);
		systemConfiguration.setProperties(() -> properties);

		return systemConfiguration;
	}

	private void _validateDefaultCompany() {
		if (contextCompany.getCompanyId() != _portal.getDefaultCompanyId()) {
			throw new BadRequestException(
				"You must be authenticated into the default company to " +
					"manage system configurations");
		}
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ConfigurationExportImportProcessor
		_configurationExportImportProcessor;

	@Reference
	private Portal _portal;

	@Reference
	private SettingsLocatorHelper _settingsLocatorHelper;

}