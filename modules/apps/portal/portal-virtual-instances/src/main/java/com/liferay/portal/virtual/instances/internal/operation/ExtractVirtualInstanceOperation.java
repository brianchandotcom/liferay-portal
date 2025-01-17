/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.virtual.instances.internal.operation;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.virtual.instances.internal.configuration.ExtractVirtualInstanceConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mariano Álvaro Sáiz
 */
@Component(
	configurationPid = "com.liferay.portal.virtual.instances.internal.configuration.ExtractVirtualInstanceConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, enabled = false,
	service = {}
)
public class ExtractVirtualInstanceOperation
	extends BaseVirtualInstanceOperation {

	@Override
	public String getOperationCompletedMessage(long companyId) {
		return "Virtual instance with company ID " + companyId +
			" extracted successfully";
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		onVirtualInstance(
			() -> {
				ExtractVirtualInstanceConfiguration
					extractVirtualInstanceConfiguration =
						ConfigurableUtil.createConfigurable(
							ExtractVirtualInstanceConfiguration.class,
							properties);

				long companyId =
					extractVirtualInstanceConfiguration.companyId();

				if (_companyLocalService.fetchCompany(companyId) == null) {
					_log.error(
						"Virtual instance with company ID " + companyId +
							" does not exist");

					return null;
				}

				return _companyLocalService.extractDBPartitionCompany(
					companyId);
			},
			properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExtractVirtualInstanceOperation.class);

	@Reference
	private CompanyLocalService _companyLocalService;

}