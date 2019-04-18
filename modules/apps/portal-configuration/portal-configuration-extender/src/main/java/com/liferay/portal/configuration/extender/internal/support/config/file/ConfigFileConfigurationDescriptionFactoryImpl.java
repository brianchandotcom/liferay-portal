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

package com.liferay.portal.configuration.extender.internal.support.config.file;

import com.liferay.portal.configuration.extender.internal.ConfigurationDescription;
import com.liferay.portal.configuration.extender.internal.ConfigurationDescriptionFactory;
import com.liferay.portal.configuration.extender.internal.FactoryConfigurationDescription;
import com.liferay.portal.configuration.extender.internal.NamedConfigurationContent;
import com.liferay.portal.configuration.extender.internal.SingleConfigurationDescription;

import java.io.IOException;
import java.io.InputStream;

import java.util.Dictionary;
import java.util.function.Supplier;

import org.apache.felix.cm.file.ConfigurationHandler;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Sierra Andrés
 */
@Component(immediate = true, service = ConfigurationDescriptionFactory.class)
public class ConfigFileConfigurationDescriptionFactoryImpl
	implements ConfigurationDescriptionFactory {

	@Override
	public ConfigurationDescription create(
		NamedConfigurationContent namedConfigurationContent) {

		if (!(namedConfigurationContent instanceof
				ConfigFileNamedConfigurationContent)) {

			return null;
		}

		String pid = null;

		String name = namedConfigurationContent.getName();

		int index = name.lastIndexOf('-');

		if (index > 0) {
			String factoryPid = name.substring(0, index);
			pid = name.substring(index + 1);

			return new FactoryConfigurationDescription(
				factoryPid, pid,
				new PropertiesSupplier(
					namedConfigurationContent.getInputStream()));
		}

		pid = name;

		return new SingleConfigurationDescription(
			pid,
			new PropertiesSupplier(namedConfigurationContent.getInputStream()));
	}

	private class PropertiesSupplier
		implements Supplier<Dictionary<String, Object>> {

		public PropertiesSupplier(InputStream inputStream) {
			_inputStream = inputStream;
		}

		@Override
		public Dictionary<String, Object> get() {
			try {
				return _loadProperties();
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}

		private Dictionary<String, Object> _loadProperties()
			throws IOException {

			Dictionary<?, ?> properties = ConfigurationHandler.read(
				_inputStream);

			return (Dictionary<String, Object>)properties;
		}

		private final InputStream _inputStream;

	}

}