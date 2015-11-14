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

import com.liferay.portal.configurator.extender.ConfigurationDescription;
import com.liferay.portal.configurator.extender.ConfigurationDescriptionFactory;
import com.liferay.portal.configurator.extender.FactoryConfigurationDescription;
import com.liferay.portal.configurator.extender.NamedConfigurationContent;
import com.liferay.portal.configurator.extender.PropertiesFileNamedConfigurationContent;
import com.liferay.portal.configurator.extender.SingleConfigurationDescription;

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
			NamedConfigurationContent namedConfigurationContent)
		throws IOException {

		if (!(namedConfigurationContent
			instanceof PropertiesFileNamedConfigurationContent)) {

			return null;
		}

		String name = namedConfigurationContent.getName();

		int lastIndexOfDash = name.lastIndexOf('-');

		try (InputStream inputStream =
				namedConfigurationContent.getInputStream()) {

			Properties properties = new Properties();

			properties.load(inputStream);

			Dictionary<String, Object> dictionary = _cast(properties);

			if (lastIndexOfDash > 0) {
				return new FactoryConfigurationDescription(
					name.substring(0, lastIndexOfDash),
					name.substring(lastIndexOfDash + 1), dictionary);
			}

			return new SingleConfigurationDescription(name, dictionary);
		}
	}

	@SuppressWarnings("unchecked")
	private static Dictionary<String, Object> _cast(
		Dictionary<?, ?> dictionary) {

		return (Dictionary<String, Object>)dictionary;
	}

}