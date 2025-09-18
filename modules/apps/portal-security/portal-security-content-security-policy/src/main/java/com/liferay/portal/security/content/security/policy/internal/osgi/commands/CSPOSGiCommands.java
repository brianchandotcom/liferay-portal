/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.content.security.policy.internal.osgi.commands;

import com.liferay.osgi.util.osgi.commands.OSGiCommands;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.security.content.security.policy.internal.configuration.ContentSecurityPolicyConfiguration;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge García Jiménez
 */
@Component(
	property = {
		"osgi.command.function=resetCompanyConfiguration",
		"osgi.command.function=resetGroupConfiguration",
		"osgi.command.function=resetSystemConfiguration",
		"osgi.command.scope=csp"
	},
	service = OSGiCommands.class
)
public class CSPOSGiCommands implements OSGiCommands {

	public void resetCompanyConfiguration(String companyId)
		throws ConfigurationException {

		ContentSecurityPolicyConfiguration contentSecurityPolicyConfiguration =
			_configurationProvider.getCompanyConfiguration(
				ContentSecurityPolicyConfiguration.class,
				GetterUtil.getLong(companyId));

		if (contentSecurityPolicyConfiguration == null) {
			System.out.println(
				"There is no company level " +
					"ContentSecurityPolicyConfiguration for companyId " +
						companyId);

			return;
		}

		_configurationProvider.deleteCompanyConfiguration(
			ContentSecurityPolicyConfiguration.class,
			GetterUtil.getLong(companyId));
	}

	public void resetGroupConfiguration(String groupId)
		throws ConfigurationException {

		ContentSecurityPolicyConfiguration contentSecurityPolicyConfiguration =
			_configurationProvider.getGroupConfiguration(
				ContentSecurityPolicyConfiguration.class,
				GetterUtil.getLong(groupId));

		if (contentSecurityPolicyConfiguration == null) {
			System.out.println(
				"There is no group level ContentSecurityPolicyConfiguration " +
					"for groupId " + groupId);

			return;
		}

		_configurationProvider.deleteGroupConfiguration(
			ContentSecurityPolicyConfiguration.class,
			GetterUtil.getLong(groupId));
	}

	public void resetSystemConfiguration() throws ConfigurationException {
		ContentSecurityPolicyConfiguration contentSecurityPolicyConfiguration =
			_configurationProvider.getSystemConfiguration(
				ContentSecurityPolicyConfiguration.class);

		if (contentSecurityPolicyConfiguration == null) {
			System.out.println(
				"There is no system level ContentSecurityPolicyConfiguration");

			return;
		}

		_configurationProvider.deleteSystemConfiguration(
			ContentSecurityPolicyConfiguration.class);
	}

	@Activate
	@Modified
	protected void activate() throws Exception {
		if (!FeatureFlagManagerUtil.isEnabled(
				CompanyThreadLocal.getNonsystemCompanyId(), "LPS-134060")) {

			throw new UnsupportedOperationException();
		}
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

}