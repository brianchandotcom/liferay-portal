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

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #ConfigurationTemporarySwapper(String, Dictionary<String,
	 *             Object>)}
	 */
	@Deprecated
	public ConfigurationTemporarySwapper(
			Class<?> serviceClass, String pid,
			Dictionary<String, Object> properties)
		throws Exception {

		this(pid, properties);
	}

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