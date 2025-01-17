/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.virtual.instances.internal.operation;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.virtual.instances.internal.configuration.CopyVirtualInstanceConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author István András Dézsi
 */
@Component(
	configurationPid = "com.liferay.portal.virtual.instances.internal.configuration.CopyVirtualInstanceConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, enabled = false,
	service = {}
)
public class CopyVirtualInstanceOperation extends BaseVirtualInstanceOperation {

	@Override
	public String getOperationCompletedMessage(long companyId) {
		return "Virtual instance with company ID " + companyId +
			" copied successfully";
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		onVirtualInstance(
			() -> {
				CopyVirtualInstanceConfiguration
					copyVirtualInstanceConfiguration =
						ConfigurableUtil.createConfigurable(
							CopyVirtualInstanceConfiguration.class, properties);

				long sourceCompanyId =
					copyVirtualInstanceConfiguration.sourceCompanyId();

				Company sourceCompany = _companyLocalService.fetchCompany(
					sourceCompanyId);

				if (sourceCompany == null) {
					_log.error(
						"Virtual instance with company ID " + sourceCompanyId +
							" does not exist");

					return null;
				}

				if (sourceCompanyId ==
						PortalInstancePool.getDefaultCompanyId()) {

					_log.error(
						"Virtual instance with company ID " + sourceCompanyId +
							" is the default company");

					return null;
				}

				long destinationCompanyId =
					copyVirtualInstanceConfiguration.destinationCompanyId();

				Company destinationCompany = _companyLocalService.fetchCompany(
					destinationCompanyId);

				if (destinationCompany != null) {
					_log.error(
						StringBundler.concat(
							"Virtual instance with company ID ",
							destinationCompanyId, " already exists"));

					return null;
				}

				return _companyLocalService.copyDBPartitionCompany(
					sourceCompanyId,
					(destinationCompanyId > 0) ? destinationCompanyId : null,
					copyVirtualInstanceConfiguration.name(),
					copyVirtualInstanceConfiguration.virtualHostname(),
					copyVirtualInstanceConfiguration.webId());
			},
			properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CopyVirtualInstanceOperation.class);

	@Reference
	private CompanyLocalService _companyLocalService;

}