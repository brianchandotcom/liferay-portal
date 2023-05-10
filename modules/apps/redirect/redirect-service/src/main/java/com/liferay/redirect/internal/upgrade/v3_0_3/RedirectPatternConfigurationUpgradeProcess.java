/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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