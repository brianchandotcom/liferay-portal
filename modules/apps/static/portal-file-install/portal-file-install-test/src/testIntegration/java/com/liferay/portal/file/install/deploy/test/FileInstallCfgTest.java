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

package com.liferay.portal.file.install.deploy.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Dictionary;
import java.util.concurrent.CountDownLatch;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ManagedService;

/**
 * @author Matthew Tambara
 */
@Ignore
@RunWith(Arquillian.class)
public class FileInstallCfgTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		Bundle bundle = FrameworkUtil.getBundle(FileInstallCfgTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@After
	public void tearDown() throws Exception {
		_deleteConfiguration();
	}

	@Test
	public void testConfiguration() throws Exception {
		String configurationPid = _CONFIGURATION_PID_PREFIX.concat(
			".testConfiguration");

		_configurationPath = Paths.get(
			PropsValues.MODULE_FRAMEWORK_CONFIGS_DIR,
			configurationPid.concat(".cfg"));

		String testKey = "testKey";
		String testValue = "testValue";

		_configuration = _createConfiguration(
			configurationPid, testKey + StringPool.EQUAL + testValue);

		Dictionary<String, Object> dictionary = _configuration.getProperties();

		Assert.assertEquals("testValue", dictionary.get(testKey));
	}

	private Configuration _createConfiguration(
			String configurationPid, String content)
		throws Exception {

		return _createConfiguration(
			configurationPid, content, Charset.defaultCharset());
	}

	private Configuration _createConfiguration(
			String configurationPid, String content, Charset charset)
		throws Exception {

		CountDownLatch countDownLatch = new CountDownLatch(2);

		ServiceRegistration<ManagedService> serviceRegistration =
			_bundleContext.registerService(
				ManagedService.class, props -> countDownLatch.countDown(),
				MapUtil.singletonDictionary(
					Constants.SERVICE_PID, configurationPid));

		try {
			Files.write(_configurationPath, content.getBytes(charset));

			countDownLatch.await();
		}
		finally {
			serviceRegistration.unregister();
		}

		return _configurationAdmin.getConfiguration(
			configurationPid, StringPool.QUESTION);
	}

	private void _deleteConfiguration() throws Exception {
		if (_configurationPath != null) {
			Files.deleteIfExists(_configurationPath);
		}

		if (_configuration != null) {
			ConfigurationTestUtil.deleteConfiguration(_configuration);
		}
	}

	private static final String _CONFIGURATION_PID_PREFIX =
		FileInstallCfgTest.class.getName() + "Configuration";

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	private BundleContext _bundleContext;
	private Configuration _configuration;
	private Path _configurationPath;

}