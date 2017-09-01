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

package com.liferay.fileinstall.helper;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Tambara
 */
@Component(immediate = true)
public class ConfigurationResetter {

	@Activate
	protected void activate(BundleContext bundleContext) throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			null);

		for (Configuration configuration : configurations) {
			Dictionary<String, Object> dictionary =
				configuration.getProperties();

			String cfgFilePath = (String)dictionary.get(
				"felix.fileinstall.filename");

			if (cfgFilePath == null) {
				continue;
			}

			try {
				File cfgFile = new File(new URI(cfgFilePath));

				if (!cfgFile.exists()) {
					configuration.delete();

					if (_log.isInfoEnabled()) {
						_log.info(
							"Configuration has been reset due to " +
								cfgFilePath + " being removed");
					}
				}
			}
			catch (URISyntaxException urise) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to recreate cfg file URI", urise);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ConfigurationResetter.class);

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}