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

package com.liferay.osgi.felix.file.install.configuration.cleaner.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Tambara
 */
@Component(immediate = true)
public class ConfigurationCleaner {

	@Activate
	protected void activate() throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			null);

		if (configurations == null) {
			return;
		}

		for (Configuration configuration : configurations) {
			Dictionary<String, Object> dictionary =
				configuration.getProperties();

			String ignore = (String)dictionary.get(
				"configuration.cleaner.ignore");

			if (Boolean.valueOf(ignore)) {
				continue;
			}

			String fileName = (String)dictionary.get(
				"felix.fileinstall.filename");

			if (fileName == null) {
				continue;
			}

			try {
				File file = new File(new URI(fileName));

				if (!file.exists()) {
					configuration.delete();

					if (_log.isInfoEnabled()) {
						_log.info(
							"Configuration was reset because " + fileName +
								" being deleted");
					}
				}
			}
			catch (URISyntaxException urise) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to recreate configuration file URI", urise);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ConfigurationCleaner.class);

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}