/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.configuration.internal.resource.v1_0;

import com.liferay.configuration.admin.exportimport.ConfigurationExportImportProcessor;
import com.liferay.configuration.admin.util.ConfigurationFilterStringUtil;
import com.liferay.headless.admin.configuration.dto.v1_0.InstanceConfiguration;
import com.liferay.headless.admin.configuration.internal.util.ConfigurationUtil;
import com.liferay.headless.admin.configuration.resource.v1_0.InstanceConfigurationResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.settings.SettingsLocatorHelper;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.pagination.Page;

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
	properties = "OSGI-INF/liferay/rest/v1_0/instance-configuration.properties",
	scope = ServiceScope.PROTOTYPE,
	service = InstanceConfigurationResource.class
)
public class InstanceConfigurationResourceImpl
	extends BaseInstanceConfigurationResourceImpl {

	@Override
	public InstanceConfiguration getInstanceConfiguration(
			String instanceConfigurationExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-65399")) {

			throw new UnsupportedOperationException();
		}

		_checkPermission();

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			ConfigurationFilterStringUtil.getCompanyScopedFilterString(
				String.valueOf(contextCompany.getCompanyId()),
				instanceConfigurationExternalReferenceCode,
				contextCompany.getDefaultWebId()));

		if (ArrayUtil.isEmpty(configurations)) {
			throw new NotFoundException(
				"Unable to find instance configuration with external " +
					"reference code " +
						instanceConfigurationExternalReferenceCode);
		}

		if (configurations.length > 1) {
			List<String> pids = new ArrayList<>();

			for (Configuration configuration : configurations) {
				pids.add(configuration.getPid());
			}

			throw new BadRequestException(
				StringBundler.concat(
					instanceConfigurationExternalReferenceCode,
					" is a factory configuration. Specify one of the entries ",
					"pid ",
					ListUtil.toString(pids, StringPool.BLANK, StringPool.COMMA),
					"."));
		}

		return _toInstanceConfiguration(configurations[0]);
	}

	@Override
	public Page<InstanceConfiguration> getInstanceConfigurationsPage()
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-65399")) {

			throw new UnsupportedOperationException();
		}

		_checkPermission();

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			ConfigurationFilterStringUtil.getCompanyScopedFilterString(
				String.valueOf(contextCompany.getCompanyId()),
				contextCompany.getDefaultWebId()));

		if (ArrayUtil.isEmpty(configurations)) {
			return Page.of(Collections.emptyList());
		}

		List<InstanceConfiguration> instanceConfigurations = new ArrayList<>();

		for (Configuration configuration : configurations) {
			InstanceConfiguration instanceConfiguration =
				_toInstanceConfiguration(configuration);

			if (instanceConfiguration == null) {
				continue;
			}

			instanceConfigurations.add(instanceConfiguration);
		}

		return Page.of(instanceConfigurations);
	}

	private void _checkPermission() {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (!permissionChecker.isCompanyAdmin() &&
			!permissionChecker.isOmniadmin()) {

			throw new NotAuthorizedException(Response.Status.UNAUTHORIZED);
		}
	}

	private InstanceConfiguration _toInstanceConfiguration(
			Configuration configuration)
		throws Exception {

		Map<String, Object> properties = ConfigurationUtil.getProperties(
			configuration, _configurationExportImportProcessor,
			_settingsLocatorHelper);

		if (properties.isEmpty()) {
			return null;
		}

		InstanceConfiguration instanceConfiguration =
			new InstanceConfiguration();

		instanceConfiguration.setExternalReferenceCode(configuration::getPid);
		instanceConfiguration.setProperties(() -> properties);

		return instanceConfiguration;
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ConfigurationExportImportProcessor
		_configurationExportImportProcessor;

	@Reference
	private SettingsLocatorHelper _settingsLocatorHelper;

}