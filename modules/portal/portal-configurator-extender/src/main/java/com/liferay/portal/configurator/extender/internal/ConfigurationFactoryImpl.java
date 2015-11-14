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

package com.liferay.portal.configurator.extender.internal;

import com.liferay.portal.configurator.extender.Configuration;
import com.liferay.portal.configurator.extender.ConfigurationFactory;
import com.liferay.portal.kernel.util.CharPool;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Component;

/**
* @author Carlos Sierra Andrés
*/
@Component(immediate = true)
public class ConfigurationFactoryImpl implements ConfigurationFactory {

	@Override
	public List<Configuration> create(Bundle bundle) throws IOException {
		Dictionary<String, String> headers = bundle.getHeaders();

		String configurationPath = headers.get("ConfigurationPath");

		if (configurationPath == null) {
			return null;
		}

		Enumeration<URL> entries = bundle.findEntries(
			configurationPath, "*", true);

		List<Configuration> configurations = new ArrayList<>();

		while (entries.hasMoreElements()) {
			configurations.add(createConfiguration(entries.nextElement()));
		}

		return configurations;
	}

	@SuppressWarnings("unchecked")
	protected static Dictionary<String, Object> cast(
		Dictionary<?, ?> dictionary) {

		return (Dictionary<String, Object>)dictionary;
	}

	protected Configuration createConfiguration(URL url) throws IOException {
		String fileName = url.getFile();

		int index = fileName.lastIndexOf(CharPool.SLASH);

		if (index > 0) {
			fileName = fileName.substring(index + 1);
		}

		String factoryPid = null;
		String pid = fileName;

		index = fileName.lastIndexOf(CharPool.DASH);

		if (index > 0) {
			factoryPid = fileName.substring(0, index);
			pid = fileName.substring(index + 1);
		}

		try (InputStream inputStream = url.openStream()) {
			Properties properties = new Properties();

			properties.load(inputStream);

			return new Configuration(factoryPid, pid, cast(properties));
		}
	}

}