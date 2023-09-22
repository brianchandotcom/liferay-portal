/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.upgrade.v5_11_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Mariano Álvaro Sáiz
 */
public class ProductDefinitionConfigurationUpgradeProcess
	extends UpgradeProcess {

	public ProductDefinitionConfigurationUpgradeProcess(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Override
	protected void doUpgrade() throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			StringBundler.concat(
				"(", Constants.SERVICE_PID,
				"=com.liferay.commerce.google.merchant.internal.configuration.",
				"ProductDefinitionConfiguration*)"));

		if (configurations == null) {
			return;
		}

		for (Configuration configuration : configurations) {
			configuration.delete();
		}
	}

	private final ConfigurationAdmin _configurationAdmin;

}