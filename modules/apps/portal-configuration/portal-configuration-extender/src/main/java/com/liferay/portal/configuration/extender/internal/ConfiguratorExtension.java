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

package com.liferay.portal.configuration.extender.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.io.IOException;

import java.util.Collection;
import java.util.Dictionary;
import java.util.function.Supplier;

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

			_process(configurationDescription);
		}
	}

	private void _process(ConfigurationDescription configurationDescription)
		throws InvalidSyntaxException, IOException {

		Configuration configuration = null;
		String configuratorURL = null;

		if (configurationDescription.getFactoryPid() == null) {
			String pid = configurationDescription.getPid();

			if (_configurationExists("(service.pid=" + pid + ")")) {
				return;
			}

			configuration = _configurationAdmin.getConfiguration(
				pid, StringPool.QUESTION);
		}
		else {
			configuratorURL =
				_namespace + "#" + configurationDescription.getPid();

			if (_configurationExists(
					"(configurator.url=" + configuratorURL + ")")) {

				return;
			}

			configuration = _configurationAdmin.createFactoryConfiguration(
				configurationDescription.getFactoryPid(), StringPool.QUESTION);
		}

		Dictionary<String, Object> properties = null;

		Supplier<Dictionary<String, Object>> propertiesSupplier =
			configurationDescription.getPropertiesSupplier();

		try {
			properties = propertiesSupplier.get();
		}
		catch (Throwable t) {
			_logger.log(
				Logger.LOG_WARNING,
				StringBundler.concat(
					"Supplier from description ", configurationDescription,
					" threw an exception: "),
				t);

			return;
		}

		if (configuratorURL != null) {
			properties.put("configurator.url", configuratorURL);
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