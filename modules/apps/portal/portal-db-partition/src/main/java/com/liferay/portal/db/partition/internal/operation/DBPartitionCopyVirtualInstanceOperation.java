/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.internal.operation;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.db.partition.internal.configuration.DBPartitionCopyVirtualInstanceConfiguration;
import com.liferay.portal.instances.service.PortalInstancesLocalService;
import com.liferay.portal.kernel.instance.PortalInstancePool;
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
 * @author István András Dézsi
 */
@Component(
	configurationPid = "com.liferay.portal.db.partition.internal.configuration.DBPartitionCopyVirtualInstanceConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, enabled = false,
	service = {}
)
public class DBPartitionCopyVirtualInstanceOperation
	extends BaseVirtualInstanceOperation {

	@Override
	public String getOperationCompletedMessage(long companyId) {
		return "Virtual instance with company ID " + companyId +
			" copied successfully";
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		onVirtualInstance(
			() -> {
				DBPartitionCopyVirtualInstanceConfiguration
					dBPartitionCopyVirtualInstanceConfiguration =
						ConfigurableUtil.createConfigurable(
							DBPartitionCopyVirtualInstanceConfiguration.class,
							properties);

				long sourceCompanyId =
					dBPartitionCopyVirtualInstanceConfiguration.
						sourceCompanyId();

				if (_companyLocalService.fetchCompany(sourceCompanyId) ==
						null) {

					_log.error(
						"Virtual instance with company ID " + sourceCompanyId +
							" does not exist");

					return null;
				}

				if (sourceCompanyId ==
						PortalInstancePool.getDefaultCompanyId()) {

					_log.error(
						"Company ID " + sourceCompanyId +
							" is the default company ID");

					return null;
				}

				long destinationCompanyId =
					dBPartitionCopyVirtualInstanceConfiguration.
						destinationCompanyId();

				if (_companyLocalService.fetchCompany(destinationCompanyId) !=
						null) {

					_log.error(
						StringBundler.concat(
							"Virtual instance with company ID ",
							destinationCompanyId, " already exists"));

					return null;
				}

				Company company = _companyLocalService.copyDBPartitionCompany(
					sourceCompanyId, destinationCompanyId,
					dBPartitionCopyVirtualInstanceConfiguration.name(),
					dBPartitionCopyVirtualInstanceConfiguration.
						virtualHostname(),
					dBPartitionCopyVirtualInstanceConfiguration.webId());

				_portalInstancesLocalService.synchronizePortalInstances();

				return company;
			},
			properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DBPartitionCopyVirtualInstanceOperation.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private PortalInstancesLocalService _portalInstancesLocalService;

}