/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.configuration.internal.resource.v1_0;

import com.liferay.configuration.admin.exportimport.ConfigurationExportImportProcessor;
import com.liferay.configuration.admin.util.ConfigurationFilterStringUtil;
import com.liferay.headless.admin.configuration.dto.v1_0.SiteConfiguration;
import com.liferay.headless.admin.configuration.internal.util.ConfigurationUtil;
import com.liferay.headless.admin.configuration.resource.v1_0.SiteConfigurationResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.settings.SettingsLocatorHelper;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
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
	properties = "OSGI-INF/liferay/rest/v1_0/site-configuration.properties",
	scope = ServiceScope.PROTOTYPE, service = SiteConfigurationResource.class
)
public class SiteConfigurationResourceImpl
	extends BaseSiteConfigurationResourceImpl {

	@Override
	public SiteConfiguration getSiteSiteConfiguration(
			String siteExternalReferenceCode,
			String siteConfigurationExternalReferenceCode)
		throws Exception {

		_checkFeatureFlag();

		Group group = _groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		_checkPermission(group.getGroupId());

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			ConfigurationFilterStringUtil.getGroupScopedFilterString(
				String.valueOf(group.getGroupId()),
				siteConfigurationExternalReferenceCode,
				siteExternalReferenceCode));

		if (ArrayUtil.isEmpty(configurations)) {
			throw new NotFoundException(
				"Unable to find site configuration with external reference " +
					"code " + siteConfigurationExternalReferenceCode);
		}

		if (configurations.length > 1) {
			List<String> pids = new ArrayList<>();

			for (Configuration configuration : configurations) {
				pids.add(configuration.getPid());
			}

			throw new BadRequestException(
				StringBundler.concat(
					siteConfigurationExternalReferenceCode,
					" is a factory configuration. Specify one of these PIDs: ",
					ListUtil.toString(pids, StringPool.BLANK, StringPool.COMMA),
					"."));
		}

		return _toSiteConfiguration(configurations[0]);
	}

	@Override
	public Page<SiteConfiguration> getSiteSiteConfigurationsPage(
			String siteExternalReferenceCode)
		throws Exception {

		_checkFeatureFlag();

		Group group = _groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		_checkPermission(group.getGroupId());

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			ConfigurationFilterStringUtil.getGroupScopedFilterString(
				String.valueOf(group.getGroupId()), siteExternalReferenceCode));

		if (ArrayUtil.isEmpty(configurations)) {
			return Page.of(Collections.emptyList());
		}

		List<SiteConfiguration> siteConfigurations = new ArrayList<>();

		for (Configuration configuration : configurations) {
			SiteConfiguration siteConfiguration = _toSiteConfiguration(
				configuration);

			if (siteConfiguration == null) {
				continue;
			}

			siteConfigurations.add(siteConfiguration);
		}

		return Page.of(siteConfigurations);
	}

	@Override
	public SiteConfiguration postSiteSiteConfiguration(
			String siteExternalReferenceCode,
			SiteConfiguration siteConfiguration)
		throws Exception {

		return putSiteSiteConfiguration(
			siteExternalReferenceCode,
			siteConfiguration.getExternalReferenceCode(), siteConfiguration);
	}

	@Override
	public SiteConfiguration putSiteSiteConfiguration(
			String siteExternalReferenceCode,
			String siteConfigurationExternalReferenceCode,
			SiteConfiguration siteConfiguration)
		throws Exception {

		_checkFeatureFlag();

		Group group = _groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		long groupId = group.getGroupId();

		_checkPermission(groupId);

		siteConfiguration.setExternalReferenceCode(
			() -> siteConfigurationExternalReferenceCode);

		String filterString =
			ConfigurationFilterStringUtil.getGroupScopedFilterString(
				String.valueOf(groupId),
				siteConfiguration.getExternalReferenceCode(),
				siteExternalReferenceCode);

		try {
			Configuration configuration =
				ConfigurationUtil.addOrUpdateConfiguration(
					groupId, _configurationAdmin,
					siteConfiguration.getExternalReferenceCode(), filterString,
					siteConfiguration.getProperties(),
					ExtendedObjectClassDefinition.Scope.GROUP,
					_settingsLocatorHelper);

			if (configuration == null) {
				throw new NotFoundException(
					"Unable to find site configuration with external " +
						"reference code " +
							siteConfiguration.getExternalReferenceCode());
			}

			return _toSiteConfiguration(configuration);
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

	private void _checkPermission(long groupId) {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (!permissionChecker.isCompanyAdmin() &&
			!permissionChecker.isGroupAdmin(groupId) &&
			!permissionChecker.isOmniadmin()) {

			throw new NotAuthorizedException(Response.Status.UNAUTHORIZED);
		}
	}

	private SiteConfiguration _toSiteConfiguration(Configuration configuration)
		throws Exception {

		Map<String, Object> properties = ConfigurationUtil.getProperties(
			configuration, _configurationExportImportProcessor,
			ExtendedObjectClassDefinition.Scope.GROUP, _settingsLocatorHelper);

		if (properties.isEmpty()) {
			return null;
		}

		SiteConfiguration siteConfiguration = new SiteConfiguration();

		siteConfiguration.setExternalReferenceCode(configuration::getPid);
		siteConfiguration.setProperties(() -> properties);

		return siteConfiguration;
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ConfigurationExportImportProcessor
		_configurationExportImportProcessor;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private SettingsLocatorHelper _settingsLocatorHelper;

}