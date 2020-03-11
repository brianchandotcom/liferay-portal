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

package com.liferay.portal.configuration.persistence.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.configuration.persistence.upgrade.ConfigurationUpgradeStepFactory;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.File;

import java.net.URI;

import java.util.Dictionary;

import org.apache.felix.cm.PersistenceManager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Hai Yu
 */
@RunWith(Arquillian.class)
public class ConfigurationUpgradeStepFactoryTest {

	@ClassRule
	@Rule
	public static AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		_configsDir = new File(
			_props.get(PropsKeys.MODULE_FRAMEWORK_CONFIGS_DIR));
	}

	@After
	public void tearDown() throws Exception {
		for (File file : _configsDir.listFiles()) {
			String fileName = file.getName();

			if (fileName.startsWith(_TEST_PID)) {
				file.delete();
			}
		}

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.pid=" + _TEST_PID + "*)");

		if (configurations == null) {
			return;
		}

		for (Configuration configuration : configurations) {
			ConfigurationTestUtil.deleteConfiguration(configuration);
		}
	}

	@Test
	public void testUpgradeConfigWithDBAndFile() throws Exception {
		_testUpgradeConfig(false, true, true);
	}

	@Test
	public void testUpgradeConfigWithDBWithoutFile() throws Exception {
		_testUpgradeConfig(false, true, false);
	}

	@Test
	public void testUpgradeConfigWithDBWithoutFileWithFileName()
		throws Exception {

		_testUpgradeConfig(false, true, false, true);
	}

	@Test
	public void testUpgradeConfigWithoutDBWithFile() throws Exception {
		_testUpgradeConfig(false, false, true);
	}

	@Test
	public void testUpgradeFactoryConfigWithDBAndFile() throws Exception {
		_testUpgradeConfig(true, true, true);
	}

	@Test
	public void testUpgradeFactoryConfigWithDBWithoutFile() throws Exception {
		_testUpgradeConfig(true, true, false);
	}

	@Test
	public void testUpgradeFactoryConfigWithDBWithoutFileWithFileName()
		throws Exception {

		_testUpgradeConfig(true, true, false, true);
	}

	@Test
	public void testUpgradeFactoryConfigWithoutDBWithFile() throws Exception {
		_testUpgradeConfig(true, false, true);
	}

	private void _testUpgradeConfig(
			boolean factory, boolean data, boolean configFile)
		throws Exception {

		_testUpgradeConfig(factory, data, configFile, configFile);
	}

	private void _testUpgradeConfig(
			boolean factory, boolean data, boolean configFile,
			boolean felixFileName)
		throws Exception {

		String oldPid = _TEST_PID_OLD;

		String newPid = _TEST_PID_NEW;

		File oldConfigFile = new File(_configsDir, oldPid + ".config");

		File newConfigFile = new File(_configsDir, newPid + ".config");

		if (factory) {
			oldConfigFile = new File(_configsDir, oldPid + "-instance.config");

			newConfigFile = new File(_configsDir, newPid + "-instance.config");
		}

		if (configFile) {
			oldConfigFile.createNewFile();
		}

		if (data) {
			if (factory) {
				oldPid = ConfigurationTestUtil.createFactoryConfiguration(
					oldPid, new HashMapDictionary<>());

				newPid = StringUtil.replace(
					oldPid, _TEST_PID_OLD, _TEST_PID_NEW);
			}
			else {
				ConfigurationTestUtil.saveConfiguration(
					_configurationAdmin.getConfiguration(oldPid),
					new HashMapDictionary<>());
			}

			if (felixFileName) {
				URI uri = oldConfigFile.toURI();

				ConfigurationTestUtil.saveConfiguration(
					oldPid,
					MapUtil.singletonDictionary(
						"felix.fileinstall.filename", uri.toString()));
			}
		}

		UpgradeStep upgradeStep =
			_configurationUpgradeStepFactory.createUpgradeStep(
				_TEST_PID_OLD, _TEST_PID_NEW);

		upgradeStep.upgrade(null);

		if (data) {
			Assert.assertFalse(
				"Configuration " + oldPid + " still exists",
				_persistenceManager.exists(oldPid));
			Assert.assertTrue(
				"Configuration " + newPid + " does not exist",
				_persistenceManager.exists(newPid));

			Dictionary<String, String> dictionary = _persistenceManager.load(
				newPid);

			String fileName = dictionary.get("felix.fileinstall.filename");

			if (felixFileName) {
				URI uri = newConfigFile.toURI();

				Assert.assertEquals(
					"Configuration " + fileName + " is not inconsistent",
					fileName, uri.toString());
			}
			else {
				Assert.assertNull(
					"Configuration property felix.fileinstall.filename " +
						"should not exist",
					fileName);
			}
		}

		if (configFile) {
			Assert.assertFalse(
				"Configuration file " + oldConfigFile + " still exists",
				oldConfigFile.exists());
			Assert.assertTrue(
				"Configuration file " + newConfigFile + " does not exist",
				newConfigFile.exists());
		}
	}

	private static final String _TEST_PID = "test.pid";

	private static final String _TEST_PID_NEW = _TEST_PID + ".new";

	private static final String _TEST_PID_OLD = _TEST_PID + ".old";

	private File _configsDir;

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	@Inject
	private ConfigurationUpgradeStepFactory _configurationUpgradeStepFactory;

	@Inject
	private PersistenceManager _persistenceManager;

	@Inject
	private Props _props;

}