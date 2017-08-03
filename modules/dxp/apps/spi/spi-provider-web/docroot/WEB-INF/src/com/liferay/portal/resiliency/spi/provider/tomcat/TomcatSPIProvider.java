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

package com.liferay.portal.resiliency.spi.provider.tomcat;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.provider.BaseSPIProvider;
import com.liferay.portal.kernel.resiliency.spi.remote.RemoteSPI;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.util.resiliency.spi.provider.SPIClassPathContextListener;

import java.io.File;

import java.lang.reflect.Constructor;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class TomcatSPIProvider extends BaseSPIProvider {

	public static final String NAME = "Tomcat SPI Provider";

	@Override
	public RemoteSPI createRemoteSPI(SPIConfiguration spiConfiguration) {
		List<URL> urls = new ArrayList<>();

		String[] paths = StringUtil.split(
			getClassPath(), File.pathSeparatorChar);

		for (String path : paths) {
			File file = new File(path);

			URI uri = file.toURI();

			try {
				urls.add(uri.toURL());
			}
			catch (MalformedURLException murle) {
				_log.error(
					"Unable to create URL for file " + file.getAbsolutePath(),
					murle);
			}
		}

		ClassLoader classLoader = new URLClassLoader(
			urls.toArray(new URL[urls.size()]),
			RemoteSPI.class.getClassLoader());

		try {
			Class<? extends RemoteSPI> clazz =
				(Class<? extends RemoteSPI>)classLoader.loadClass(
					TomcatRemoteSPI.class.getName());

			Constructor<? extends RemoteSPI> constructor = clazz.getConstructor(
				SPIConfiguration.class);

			return constructor.newInstance(spiConfiguration);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getClassPath() {
		return SPIClassPathContextListener.SPI_CLASS_PATH;
	}

	@Override
	public String getName() {
		return NAME;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TomcatSPIProvider.class);

}