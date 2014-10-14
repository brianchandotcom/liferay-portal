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

package com.liferay.ip.geocoder.internal.command;

import java.io.IOException;

import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * @author Julio Camarero
 */
@Component (
	immediate = true, property = {
		"osgi.command.function=updateFilePath",
		"osgi.command.function=updateFileURL", "osgi.command.scope=ipgeocoder"
	},
	service = Object.class
)
public class IPGeocoderConfigurationCommand {

	@Activate
	public void activate() {
	}

	@Reference (cardinality = ReferenceCardinality.OPTIONAL)
	public void setConfigurationAdmin(ConfigurationAdmin configurationAdmin) {
		_configurationAdmin = configurationAdmin;
	}

	public void updateFilePath(String filePath) throws IOException {
		updateConfig("ip.geocoder.file.path", filePath);
	}

	public void updateFileURL(String url) throws IOException {
		updateConfig("ip.geocoder.file.url", url);
	}

	protected void updateConfig(String key, String value) throws IOException {
		if (_configurationAdmin == null) {
			if (_logger.isInfoEnabled()) {
				_logger.info("Configuration Admin service is unavailable");
			}

			return;
		}

		Configuration configuration = _configurationAdmin.getConfiguration(
			"IPGeocoder");

		Dictionary<String, Object> properties = configuration.getProperties();

		if (properties == null) {
			properties = new Hashtable<String, Object>();
		}

		properties.put(key, value);

		configuration.update(properties);
	}

	private static final Logger _logger = Logger.getLogger(
		IPGeocoderConfigurationCommand.class);

	private ConfigurationAdmin _configurationAdmin;

}