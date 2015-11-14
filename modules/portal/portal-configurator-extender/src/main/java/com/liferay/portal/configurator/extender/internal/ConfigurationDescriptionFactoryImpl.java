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

import com.liferay.portal.configurator.extender.ConfigurationContent;
import com.liferay.portal.configurator.extender.ConfigurationDescription;
import com.liferay.portal.configurator.extender.ConfigurationDescriptionFactory;

import java.io.IOException;
import java.io.InputStream;

import java.util.Dictionary;
import java.util.Properties;

import org.osgi.service.component.annotations.Component;

/**
* @author Carlos Sierra Andrés
*/
@Component(immediate = true)
public class ConfigurationDescriptionFactoryImpl
	implements ConfigurationDescriptionFactory {

	@Override
	public ConfigurationDescription create(
			ConfigurationContent configurationContent)
		throws IOException {

		try (InputStream inputStream = configurationContent.getInputStream()) {
			Properties properties = new Properties();

			properties.load(inputStream);

			return new ConfigurationDescription(
				configurationContent.getFactoryPid(),
				configurationContent.getPid(), _cast(properties));
		}
	}

	@SuppressWarnings("unchecked")
	private static Dictionary<String, Object> _cast(
		Dictionary<?, ?> dictionary) {

		return (Dictionary<String, Object>)dictionary;
	}

}