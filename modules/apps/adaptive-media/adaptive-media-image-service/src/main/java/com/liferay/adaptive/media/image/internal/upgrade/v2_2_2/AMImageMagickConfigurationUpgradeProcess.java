/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.upgrade.v2_2_2;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.io.IOException;

import java.util.Dictionary;

import org.osgi.framework.Constants;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Sam Ziemer
 */
public class AMImageMagickConfigurationUpgradeProcess extends UpgradeProcess {

	public AMImageMagickConfigurationUpgradeProcess(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Override
	protected void doUpgrade() throws InvalidSyntaxException, IOException {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			StringBundler.concat(
				"(", Constants.SERVICE_PID,
				"=com.liferay.adaptive.media.image.internal.configuration.",
				"AMImageMagickConfiguration)"));

		if (configurations == null) {
			return;
		}

		Configuration configuration = configurations[0];

		Dictionary<String, Object> dictionary = configuration.getProperties();

		Object object = dictionary.remove("supportedMimeTypes");

		if (object != null) {
			dictionary.put("mimeTypes", object);

			configuration.updateIfDifferent(dictionary);
		}
	}

	private final ConfigurationAdmin _configurationAdmin;

}