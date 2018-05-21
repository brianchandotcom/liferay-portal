/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.persistence.service.util;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.StringPool;
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
		catch (IOException ioe) {
			samlConfiguration = ConfigurableUtil.createConfigurable(
				SamlConfiguration.class, Collections.emptyMap());
		}

		return samlConfiguration;
	}

}