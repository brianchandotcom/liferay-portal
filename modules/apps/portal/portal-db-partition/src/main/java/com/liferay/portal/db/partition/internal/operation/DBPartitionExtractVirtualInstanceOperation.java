/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.internal.operation;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.db.partition.internal.configuration.DBPartitionExtractVirtualInstanceConfiguration;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
	configurationPid = "com.liferay.portal.db.partition.internal.configuration.DBPartitionExtractVirtualInstanceConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, enabled = false,
	service = {}
)
public class DBPartitionExtractVirtualInstanceOperation
	extends BaseVirtualInstanceOperation {

	@Override
	public String getOperationCompletedMessage(long companyId) {
		return "Virtual Instance with company ID " + companyId +
			" extracted successfully";
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		onVirtualInstance(
			() -> {
				DBPartitionExtractVirtualInstanceConfiguration
					dBPartitionExtractVirtualInstanceConfiguration =
						ConfigurableUtil.createConfigurable(
							DBPartitionExtractVirtualInstanceConfiguration.
								class,
							properties);

				long companyId =
					dBPartitionExtractVirtualInstanceConfiguration.
						partitionCompanyId();

				if (_companyLocalService.fetchCompany(companyId) == null) {
					_log.error(
						"Virtual Instance with company ID " + companyId +
							" does not exist");

					return null;
				}

				return _companyLocalService.extractDBPartitionCompany(
					companyId);
			},
			properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DBPartitionExtractVirtualInstanceOperation.class);

	@Reference
	private CompanyLocalService _companyLocalService;

}