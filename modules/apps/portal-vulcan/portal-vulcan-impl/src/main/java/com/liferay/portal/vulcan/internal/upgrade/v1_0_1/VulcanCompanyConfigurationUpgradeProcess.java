/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.upgrade.v1_0_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Vendel Toreki
 */
public class VulcanCompanyConfigurationUpgradeProcess extends UpgradeProcess {

	public VulcanCompanyConfigurationUpgradeProcess(
		CompanyLocalService companyLocalService,
		ConfigurationAdmin configurationAdmin) {

		_companyLocalService = companyLocalService;
		_configurationAdmin = configurationAdmin;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompany(
			company -> _upgradeVulcanCompanyConfigurations(
				company.getCompanyId()));
	}

	private void _upgradeVulcanCompanyConfigurations(long companyId)
		throws Exception {

		String factoryPid =
			"com.liferay.portal.vulcan.internal.configuration." +
				"VulcanCompanyConfiguration";

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			StringBundler.concat(
				"(&(",
				ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey(),
				"=", companyId, ")(path=*)(service.factoryPid=", factoryPid,
				"))"));

		if (configurations == null) {
			return;
		}

		for (Configuration configuration : configurations) {
			Dictionary<String, Object> dictionary =
				configuration.getProperties();

			if ((dictionary == null) || dictionary.isEmpty()) {
				continue;
			}

			Object value = dictionary.get(
				ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey());

			if ((value == null) || (value instanceof Long)) {
				continue;
			}

			dictionary.put(
				ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey(),
				GetterUtil.getLong(value));

			configuration.update(dictionary);
		}
	}

	private final CompanyLocalService _companyLocalService;
	private final ConfigurationAdmin _configurationAdmin;

}