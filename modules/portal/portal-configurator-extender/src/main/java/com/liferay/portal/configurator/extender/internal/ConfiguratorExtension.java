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
import com.liferay.portal.configurator.extender.SingleConfigurationDescription;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Supplier;

import java.io.IOException;

import java.util.Collection;
import java.util.Dictionary;

import org.apache.felix.utils.extender.Extension;
import org.apache.felix.utils.log.Logger;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
* @author Carlos Sierra Andrés
*/
public class ConfiguratorExtension implements Extension {

	public ConfiguratorExtension(
		ConfigurationAdmin configurationAdmin, Logger logger, String namespace,
		Collection<NamedConfigurationContent> namedConfigurationContents,
		Collection<ConfigurationDescriptionFactory>
			configurationDescriptionFactories) {

		_configurationAdmin = configurationAdmin;
		_logger = logger;
		_namespace = namespace;
		_configurationContents = namedConfigurationContents;
		_configurationDescriptionFactories = configurationDescriptionFactories;
	}

	@Override
	public void destroy() throws Exception {
	}

	@Override
	public void start() throws Exception {
		for (NamedConfigurationContent namedConfigurationContent :
				_configurationContents) {

			try {
				_createConfiguration(namedConfigurationContent);
			}
			catch (IOException ioe) {
				continue;
			}
		}
	}

	private boolean _configurationExists(String filter)
		throws InvalidSyntaxException, IOException {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			filter);

		if (ArrayUtil.isNotEmpty(configurations)) {
			return true;
		}

		return false;
	}

	private void _createConfiguration(
			NamedConfigurationContent namedConfigurationContent)
		throws Exception {

		for (ConfigurationDescriptionFactory configurationDescriptionFactory :
				_configurationDescriptionFactories) {

			ConfigurationDescription configurationDescription =
				configurationDescriptionFactory.create(
					namedConfigurationContent);

			if (configurationDescription == null) {
				continue;
			}

			if (configurationDescription
					instanceof FactoryConfigurationDescription) {

				_process(
					(FactoryConfigurationDescription)configurationDescription);
			}
			else if (configurationDescription
						instanceof SingleConfigurationDescription) {

				_process(
					(SingleConfigurationDescription)configurationDescription);
			}
			else {
				_logger.log(
					Logger.LOG_ERROR,
					configurationDescriptionFactory + " returned an " +
						"unsupported configuration description " +
							configurationDescription);
			}
		}
	}

	private void _process(
			FactoryConfigurationDescription factoryConfigurationDescription)
		throws InvalidSyntaxException, IOException {

		String factoryPid = factoryConfigurationDescription.getFactoryPid();
		String pid = factoryConfigurationDescription.getPid();

		String configuratorURL = _namespace + "#" + pid;

		if (_configurationExists(
				"(configurator.url=" + configuratorURL + ")")) {

			return;
		}

		Configuration configuration =
			_configurationAdmin.createFactoryConfiguration(factoryPid, null);

		Dictionary<String, Object> properties = null;

		Supplier<Dictionary<String, Object>> propertiesSupplier =
			factoryConfigurationDescription.getPropertiesSupplier();

		try {
			properties = propertiesSupplier.get();
		}
		catch (Throwable t) {
			_logger.log(
				Logger.LOG_WARNING,
				"Supplier from factory configuration description " +
					factoryConfigurationDescription + " threw an " +
						"exception: ",
				t);

			return;
		}

		properties.put("configurator.url", configuratorURL);

		configuration.update(properties);
	}

	private void _process(SingleConfigurationDescription description)
		throws InvalidSyntaxException, IOException {

		String pid = description.getPid();

		if (_configurationExists("(service.pid=" + pid + ")")) {
			return;
		}

		Configuration configuration = _configurationAdmin.getConfiguration(
			pid, null);

		Dictionary<String, Object> properties = null;

		Supplier<Dictionary<String, Object>> propertiesSupplier =
			description.getPropertiesSupplier();

		try {
			properties = propertiesSupplier.get();
		}
		catch (Throwable t) {
			_logger.log(
				Logger.LOG_WARNING,
				"Supplier from description " + description + " threw " +
					" an exception: ",
				t);

			return;
		}

		configuration.update(properties);
	}

	private final ConfigurationAdmin _configurationAdmin;
	private final Collection<NamedConfigurationContent> _configurationContents;
	private final Collection<ConfigurationDescriptionFactory>
		_configurationDescriptionFactories;
	private final Logger _logger;
	private final String _namespace;

}