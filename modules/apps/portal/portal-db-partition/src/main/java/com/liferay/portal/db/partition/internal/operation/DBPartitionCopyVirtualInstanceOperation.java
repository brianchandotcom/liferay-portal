/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.internal.operation;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.db.partition.internal.configuration.DBPartitionCopyVirtualInstanceConfiguration;
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

				long sourcePartitionCompanyId =
					dBPartitionCopyVirtualInstanceConfiguration.
						sourcePartitionCompanyId();

				Company sourcePartitionCompany =
					_companyLocalService.fetchCompany(sourcePartitionCompanyId);

				if (sourcePartitionCompany == null) {
					_log.error(
						"Virtual instance with company ID " +
							sourcePartitionCompanyId + " does not exist");

					return null;
				}

				if (sourcePartitionCompanyId ==
						PortalInstancePool.getDefaultCompanyId()) {

					_log.error(
						"Virtual instance with company ID " +
							sourcePartitionCompanyId +
								" is the default company");

					return null;
				}

				long destinationPartitionCompanyId =
					dBPartitionCopyVirtualInstanceConfiguration.
						destinationPartitionCompanyId();

				Company destinationPartitionCompany =
					_companyLocalService.fetchCompany(
						destinationPartitionCompanyId);

				if (destinationPartitionCompany != null) {
					_log.error(
						StringBundler.concat(
							"Virtual instance with company ID ",
							destinationPartitionCompanyId, " already exists"));

					return null;
				}

				return _companyLocalService.copyDBPartitionCompany(
					sourcePartitionCompanyId,
					(destinationPartitionCompanyId > 0) ?
						destinationPartitionCompanyId : null,
					dBPartitionCopyVirtualInstanceConfiguration.name(),
					dBPartitionCopyVirtualInstanceConfiguration.
						virtualHostname(),
					dBPartitionCopyVirtualInstanceConfiguration.webId());
			},
			properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DBPartitionCopyVirtualInstanceOperation.class);

	@Reference
	private CompanyLocalService _companyLocalService;

}