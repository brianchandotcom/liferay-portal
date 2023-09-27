/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.persistence.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.saml.runtime.configuration.SamlConfiguration;

import java.io.IOException;

import java.util.Collections;
import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Michael C. Han
 */
public class SamlConfigurationUtil {

	public static SamlConfiguration getSamlConfiguration(
		ConfigurationAdmin configurationAdmin) {

		SamlConfiguration samlConfiguration = null;

		try {
			Configuration configuration = configurationAdmin.getConfiguration(
				"com.liferay.saml.runtime.configuration.SamlConfiguration",
				StringPool.QUESTION);

			Dictionary<String, Object> properties =
				configuration.getProperties();

			if (properties != null) {
				samlConfiguration = ConfigurableUtil.createConfigurable(
					SamlConfiguration.class, properties);
			}
			else {
				samlConfiguration = ConfigurableUtil.createConfigurable(
					SamlConfiguration.class, Collections.emptyMap());
			}
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioException);
			}

			samlConfiguration = ConfigurableUtil.createConfigurable(
				SamlConfiguration.class, Collections.emptyMap());
		}

		return samlConfiguration;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SamlConfigurationUtil.class);

}