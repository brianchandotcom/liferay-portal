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