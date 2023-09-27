/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.test.util;

import com.liferay.osgi.util.service.OSGiServiceUtil;
import com.liferay.petra.string.StringPool;

import java.util.Dictionary;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Drew Brokke
 */
public class ConfigurationTemporarySwapper implements AutoCloseable {

	public ConfigurationTemporarySwapper(
			String pid, Dictionary<String, Object> properties)
		throws Exception {

		_configuration = OSGiServiceUtil.callService(
			_bundleContext, ConfigurationAdmin.class,
			configurationAdmin -> configurationAdmin.getConfiguration(
				pid, StringPool.QUESTION));

		_oldProperties = _configuration.getProperties();

		ConfigurationTestUtil.saveConfiguration(_configuration, properties);
	}

	@Override
	public void close() throws Exception {
		ConfigurationTestUtil.saveConfiguration(_configuration, _oldProperties);
	}

	private static final BundleContext _bundleContext;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ConfigurationTemporarySwapper.class);

		_bundleContext = bundle.getBundleContext();
	}

	private final Configuration _configuration;
	private final Dictionary<String, Object> _oldProperties;

}