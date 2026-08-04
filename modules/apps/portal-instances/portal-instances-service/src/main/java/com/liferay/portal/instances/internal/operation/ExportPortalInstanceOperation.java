/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.internal.operation;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.instances.configuration.PortalInstanceConfigurationExporter;
import com.liferay.portal.instances.internal.configuration.ExportPortalInstanceConfiguration;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mariano Álvaro Sáiz
 */
@Component(
	configurationPid = "com.liferay.portal.instances.internal.configuration.ExportPortalInstanceConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, service = {}
)
public class ExportPortalInstanceOperation extends BasePortalInstanceOperation {

	@Override
	public String getOperationCompletedMessage(long companyId) {
		return "Portal instance with company ID " + companyId +
			" exported successfully";
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		onPortalInstance(
			() -> {
				FeatureFlagManagerUtil.checkEnabled("LPD-11342");

				ExportPortalInstanceConfiguration
					exportPortalInstanceConfiguration =
						ConfigurableUtil.createConfigurable(
							ExportPortalInstanceConfiguration.class,
							properties);

				long companyId =
					exportPortalInstanceConfiguration.exportCompanyId();

				if (_companyLocalService.fetchCompany(companyId) == null) {
					_log.error(
						"Portal instance with company ID " + companyId +
							" does not exist");

					return null;
				}

				Company company = _companyLocalService.exportCompany(companyId);

				_portalInstanceConfigurationExporter.exportConfigurations(
					companyId);

				return company;
			},
			properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportPortalInstanceOperation.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private PortalInstanceConfigurationExporter
		_portalInstanceConfigurationExporter;

}