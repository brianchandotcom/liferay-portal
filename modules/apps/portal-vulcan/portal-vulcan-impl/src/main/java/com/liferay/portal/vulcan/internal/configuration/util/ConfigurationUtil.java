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

package com.liferay.portal.vulcan.internal.configuration.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.vulcan.internal.configuration.VulcanConfiguration;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Javier Gamarra
 */
public class ConfigurationUtil {

	public static Map<String, Configuration> getConfigurations(
		ConfigurationAdmin configurationAdmin) {

		Map<String, Configuration> configurations = new HashMap<>();

		try {
			String filterString = String.format(
				"(service.factoryPid=%s)", VulcanConfiguration.class.getName());

			for (Configuration configuration :
					configurationAdmin.listConfigurations(filterString)) {

				Dictionary<String, Object> properties =
					configuration.getProperties();

				configurations.put(
					(String)properties.get("path"), configuration);
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return configurations;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ConfigurationUtil.class);

}