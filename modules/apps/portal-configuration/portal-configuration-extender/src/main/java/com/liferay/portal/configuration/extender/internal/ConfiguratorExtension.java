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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.io.IOException;

import java.util.Collection;
import java.util.Dictionary;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Carlos Sierra Andrés
 */
public class ConfiguratorExtension {

	public ConfiguratorExtension(
		ConfigurationAdmin configurationAdmin, String bundleSymbolicName,
		Collection<NamedConfigurationContent> namedConfigurationContents) {

		_configurationAdmin = configurationAdmin;
		_bundleSymbolicName = bundleSymbolicName;
		_configurationContents = namedConfigurationContents;
	}

	public void destroy() {
	}

	public void start() {
		for (NamedConfigurationContent namedConfigurationContent :
				_configurationContents) {

			try {
				_process(namedConfigurationContent);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	private void _process(NamedConfigurationContent namedConfigurationContent)
		throws InvalidSyntaxException, IOException {

		Configuration configuration = null;
		String configuratorURL = null;

		if (namedConfigurationContent.getFactoryPid() == null) {
			String pid = namedConfigurationContent.getPid();

			if (ArrayUtil.isNotEmpty(
					_configurationAdmin.listConfigurations(
						"(service.pid=" + pid + ")"))) {

				return;
			}

			configuration = _configurationAdmin.getConfiguration(
				pid, StringPool.QUESTION);
		}
		else {
			configuratorURL =
				_bundleSymbolicName + "#" + namedConfigurationContent.getPid();

			if (ArrayUtil.isNotEmpty(
					_configurationAdmin.listConfigurations(
						"(configurator.url=" + configuratorURL + ")"))) {

				return;
			}

			configuration = _configurationAdmin.createFactoryConfiguration(
				namedConfigurationContent.getFactoryPid(), StringPool.QUESTION);
		}

		Dictionary<String, Object> properties = null;

		try {
			properties = namedConfigurationContent.getProperties();
		}
		catch (Throwable t) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Supplier from description ", namedConfigurationContent,
						" threw an exception: "),
					t);
			}

			return;
		}

		if (configuratorURL != null) {
			properties.put("configurator.url", configuratorURL);
		}

		configuration.update(properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ConfiguratorExtension.class);

	private final String _bundleSymbolicName;
	private final ConfigurationAdmin _configurationAdmin;
	private final Collection<NamedConfigurationContent> _configurationContents;

}