/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.internal.operation;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.instances.internal.configuration.CopyPortalInstanceConfiguration;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
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
	configurationPid = "com.liferay.portal.instances.internal.configuration.CopyPortalInstanceConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, enabled = false,
	service = {}
)
public class CopyPortalInstanceOperation extends BasePortalInstanceOperation {

	@Override
	public String getOperationCompletedMessage(long companyId) {
		return "Portal instance with company ID " + companyId +
			" copied successfully";
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		if (!FeatureFlagManagerUtil.isEnabled("LPD-11342")) {
			throw new UnsupportedOperationException(
				"Feature flag LPD-11342 must be enabled");
		}

		onPortalInstance(
			() -> {
				CopyPortalInstanceConfiguration
					copyPortalInstanceConfiguration =
						ConfigurableUtil.createConfigurable(
							CopyPortalInstanceConfiguration.class, properties);

				long sourceCompanyId =
					copyPortalInstanceConfiguration.sourceCompanyId();

				Company sourceCompany = _companyLocalService.fetchCompany(
					sourceCompanyId);

				if (sourceCompany == null) {
					_log.error(
						"Portal instance with company ID " + sourceCompanyId +
							" does not exist");

					return null;
				}

				if (sourceCompanyId ==
						PortalInstancePool.getDefaultCompanyId()) {

					_log.error(
						"Portal instance with company ID " + sourceCompanyId +
							" is the default company");

					return null;
				}

				long destinationCompanyId =
					copyPortalInstanceConfiguration.destinationCompanyId();

				Company destinationCompany = _companyLocalService.fetchCompany(
					destinationCompanyId);

				if (destinationCompany != null) {
					_log.error(
						StringBundler.concat(
							"Portal instance with company ID ",
							destinationCompanyId, " already exists"));

					return null;
				}

				return _companyLocalService.copyDBPartitionCompany(
					sourceCompanyId,
					(destinationCompanyId > 0) ? destinationCompanyId : null,
					copyPortalInstanceConfiguration.name(),
					copyPortalInstanceConfiguration.virtualHostname(),
					copyPortalInstanceConfiguration.webId());
			},
			properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CopyPortalInstanceOperation.class);

	@Reference
	private CompanyLocalService _companyLocalService;

}