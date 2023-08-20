/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.a11y.web.internal.verify;

import com.liferay.frontend.js.a11y.web.internal.configuration.A11yConfiguration;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.verify.VerifyProcess;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(property = "initial.deployment=true", service = VerifyProcess.class)
public class A11yConfigurationVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.pid=com.liferay.frontend.js.a11y.web.internal." +
				"configuration.FFA11yConfiguration)");

		if (ArrayUtil.isEmpty(configurations)) {
			return;
		}

		Configuration ffA11yConfiguration = configurations[0];

		Dictionary<String, Object> ffA11yProperties =
			ffA11yConfiguration.getProperties();

		if (GetterUtil.getBoolean(ffA11yProperties.get("enable"))) {
			Configuration a11yConfiguration =
				_configurationAdmin.getConfiguration(
					A11yConfiguration.class.getName(), "?");

			Dictionary<String, Object> a11yProperties =
				a11yConfiguration.getProperties();

			if (a11yProperties == null) {
				a11yProperties = new HashMapDictionary<>();
			}

			if (!GetterUtil.getBoolean(a11yProperties.get("enable"))) {
				a11yProperties.put("enable", true);

				a11yConfiguration.update(a11yProperties);
			}
		}

		ffA11yConfiguration.delete();
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}