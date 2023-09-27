/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.persistence.internal.upgrade.v2_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Stian Sigvartsen
 */
public class SamlSpSessionDataUpgradeProcess extends UpgradeProcess {

	public SamlSpSessionDataUpgradeProcess(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	public void migrateSAMLProviderConfiguration() throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			StringBundler.concat(
				"(&(", ConfigurationAdmin.SERVICE_FACTORYPID, "=", _FACTORY_PID,
				")(companyId=*))"));

		if (configurations == null) {
			return;
		}

		for (Configuration configuration : configurations) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			long companyId = GetterUtil.getLong(
				properties.get("companyId"), -1);

			if (companyId < 1) {
				continue;
			}

			String samlSpDefaultIdpEntityId = GetterUtil.getString(
				properties.get("saml.sp.default.idp.entity.id"));

			if (Validator.isBlank(samlSpDefaultIdpEntityId)) {
				continue;
			}

			runSQL(
				StringBundler.concat(
					"update SamlSpSession set samlIdpEntityId = '",
					samlSpDefaultIdpEntityId, "' where companyId = ",
					companyId));

			properties.remove("saml.sp.default.idp.entity.id");

			configuration.update(properties);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			migrateSAMLProviderConfiguration();
		}
	}

	private static final String _FACTORY_PID =
		SamlProviderConfiguration.class.getName();

	private final ConfigurationAdmin _configurationAdmin;

}