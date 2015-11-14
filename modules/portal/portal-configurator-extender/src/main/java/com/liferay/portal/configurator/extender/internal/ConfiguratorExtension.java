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
import com.liferay.portal.kernel.util.ArrayUtil;

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
		Collection<ConfigurationContent> configurationContents,
		Collection<ConfigurationDescriptionFactory>
			configurationDescriptionFactories) {

		_configurationAdmin = configurationAdmin;
		_logger = logger;
		_namespace = namespace;
		_configurationContents = configurationContents;
		_configurationDescriptionFactories = configurationDescriptionFactories;
	}

	@Override
	public void destroy() throws Exception {
	}

	@Override
	public void start() throws Exception {
		for (ConfigurationContent configurationContent :
				_configurationContents) {

			try {
				_createConfiguration(configurationContent);
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

	private void _createConfiguration(ConfigurationContent configurationContent)
		throws Exception {

		for (ConfigurationDescriptionFactory configurationDescriptionFactory :
				_configurationDescriptionFactories) {

			ConfigurationDescription configurationDescription = null;

			try {
				configurationDescription =
					configurationDescriptionFactory.create(
						configurationContent);
			}
			catch (IOException ioe) {
				_logger.log(
					Logger.LOG_WARNING,
					"Unable to create ConfigurationDescription from " +
						configurationContent,
					ioe);

				continue;
			}

			if (configurationDescription == null) {
				continue;
			}

			if (configurationDescription.getFactoryPid() == null) {
				_processConfigurationDescription(configurationDescription);
			}
			else {
				_processFactoryConfigurationDescription(
					configurationDescription);
			}
		}
	}

	private void _processConfigurationDescription(
			ConfigurationDescription configurationDescription)
		throws InvalidSyntaxException, IOException {

		String pid = configurationDescription.getPid();

		if (_configurationExists("(service.pid=" + pid + ")")) {
			return;
		}

		Configuration configuration = _configurationAdmin.getConfiguration(
			pid, null);

		configuration.update(configurationDescription.getProperties());
	}

	private void _processFactoryConfigurationDescription(
			ConfigurationDescription configurationDescription)
		throws InvalidSyntaxException, IOException {

		String factoryPid = configurationDescription.getFactoryPid();
		String pid = configurationDescription.getPid();

		String configuratorUrl = _namespace + "#" + pid;

		if (_configurationExists(
				"(configurator.url=" + configuratorUrl + ")")) {

			return;
		}

		Configuration configuration =
			_configurationAdmin.createFactoryConfiguration(factoryPid, null);

		Dictionary<String, Object> properties =
			configurationDescription.getProperties();

		properties.put("configurator.url", configuratorUrl);

		configuration.update(properties);
	}

	private final ConfigurationAdmin _configurationAdmin;
	private final Collection<ConfigurationContent> _configurationContents;
	private final Collection<ConfigurationDescriptionFactory>
		_configurationDescriptionFactories;
	private final Logger _logger;
	private final String _namespace;

}