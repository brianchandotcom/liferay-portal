/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.internal.upgrade.v3_0_3;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.redirect.constants.RedirectConstants;

import java.util.Dictionary;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Alicia García
 */
public class RedirectPatternConfigurationUpgradeProcess extends UpgradeProcess {

	public RedirectPatternConfigurationUpgradeProcess(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Override
	protected void doUpgrade() throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			String.format(
				"(%s=%s*)", Constants.SERVICE_PID,
				"com.liferay.redirect.internal.configuration." +
					"RedirectPatternConfiguration"));

		if (ArrayUtil.isEmpty(configurations)) {
			return;
		}

		for (Configuration configuration : configurations) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			if (properties == null) {
				continue;
			}

			String[] patternStrings = (String[])properties.get(
				"patternStrings");

			if (ArrayUtil.isEmpty(patternStrings)) {
				continue;
			}

			String[] patternStringsArray = new String[patternStrings.length];

			int i = 0;

			for (String patternString : patternStrings) {
				String[] values = StringUtil.split(
					patternString, StringPool.SPACE);

				if (values.length > 2) {
					patternStringsArray[i++] = patternString;
				}
				else {
					patternStringsArray[i++] = StringBundler.concat(
						values[0], StringPool.SPACE, values[1],
						StringPool.SPACE, RedirectConstants.USER_AGENT_ALL);
				}
			}

			properties.put("patternStrings", patternStringsArray);

			configuration.update(properties);
		}
	}

	private final ConfigurationAdmin _configurationAdmin;

}