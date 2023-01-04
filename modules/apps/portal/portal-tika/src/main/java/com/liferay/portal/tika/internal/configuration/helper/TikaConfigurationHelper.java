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

package com.liferay.portal.tika.internal.configuration.helper;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.tika.internal.configuration.TikaConfiguration;

import java.io.InputStream;

import java.util.Map;

import org.apache.tika.config.TikaConfig;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Tina Tian
 */
@Component(
	configurationPid = "com.liferay.portal.tika.internal.configuration.TikaConfiguration",
	service = TikaConfigurationHelper.class
)
public class TikaConfigurationHelper {

	public TikaConfig getTikaConfig() {
		return _tikaConfig;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		TikaConfiguration tikaConfiguration =
			ConfigurableUtil.createConfigurable(
				TikaConfiguration.class, properties);

		String tikaConfigXml = tikaConfiguration.tikaConfigXml();

		Class<?> clazz = TikaConfigurationHelper.class;

		InputStream inputStream = clazz.getResourceAsStream(tikaConfigXml);

		if (inputStream == null) {
			ClassLoader classLoader = clazz.getClassLoader();

			inputStream = classLoader.getResourceAsStream(tikaConfigXml);

			if (inputStream == null) {
				classLoader = PortalClassLoaderUtil.getClassLoader();

				inputStream = classLoader.getResourceAsStream(tikaConfigXml);

				if (inputStream == null) {
					throw new IllegalArgumentException(
						"Unable to read tika configuration " + tikaConfigXml);
				}
			}
		}

		try {
			_tikaConfig = new TikaConfig(inputStream);
		}
		catch (Exception exception) {
			throw new IllegalStateException(
				"Unable to create tika configuration", exception);
		}
	}

	private TikaConfig _tikaConfig;

}