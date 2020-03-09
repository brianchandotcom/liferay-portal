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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.persistence.upgrade.ConfigurationUpgradeStepFactory;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
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

import org.junit.Assert;
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

	@Test
	public void testUpgradeConfigWithDBAndFile() throws Exception {
		_testUpgradeConfig(true, true, false);
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
		_testUpgradeConfig(true, false, false);
	}

	@Test
	public void testUpgradeFactoryConfigWithDBAndFile() throws Exception {
		_testUpgradeConfig(true, true, true);
	}

	@Test
	public void testUpgradeFactoryConfigWithDBWithoutFile() throws Exception {
		_testUpgradeConfig(false, true, true);
	}

	@Test
	public void testUpgradeFactoryConfigWithDBWithoutFileWithFileName()
		throws Exception {

		_testUpgradeConfig(false, true, true, true);
	}

	@Test
	public void testUpgradeFactoryConfigWithoutDBWithFile() throws Exception {
		_testUpgradeConfig(true, false, true);
	}

	private void _testUpgradeConfig(
			boolean configFile, boolean data, boolean factory)
		throws Exception {

		_testUpgradeConfig(configFile, data, factory, configFile);
	}

	private void _testUpgradeConfig(
			boolean configFile, boolean data, boolean factory,
			boolean felixFileName)
		throws Exception {

		Configuration configuration = null;

		String oldPid = _OLD_PID;

		String newPid = _NEW_PID;

		String parentDir = _props.get(PropsKeys.MODULE_FRAMEWORK_CONFIGS_DIR);

		File oldConfigFile = new File(parentDir, oldPid + ".config");

		File newConfigFile = new File(parentDir, newPid + ".config");

		if (factory) {
			oldConfigFile = new File(parentDir, oldPid + "-instance.config");

			newConfigFile = new File(parentDir, newPid + "-instance.config");
		}

		try {
			if (configFile) {
				oldConfigFile.createNewFile();
			}

			if (data) {
				if (factory) {
					configuration =
						_configurationAdmin.createFactoryConfiguration(
							oldPid, StringPool.QUESTION);

					oldPid = configuration.getPid();

					newPid = StringUtil.replace(oldPid, _OLD_PID, _NEW_PID);
				}
				else {
					configuration = _configurationAdmin.getConfiguration(
						oldPid);
				}

				if (felixFileName) {
					URI uri = oldConfigFile.toURI();

					ConfigurationTestUtil.saveConfiguration(
						configuration,
						MapUtil.singletonDictionary(
							"felix.fileinstall.filename", uri.toString()));
				}
				else if (factory) {

					// Factory configuration instance will only be persisted
					// after the first update with dictionary

					ConfigurationTestUtil.saveConfiguration(
						configuration, new HashMapDictionary<>());
				}
			}

			UpgradeStep upgradeStep =
				_configurationUpgradeStepFactory.createUpgradeStep(
					_OLD_PID, _NEW_PID);

			upgradeStep.upgrade(null);

			if (data) {
				Assert.assertFalse(
					"Configuration " + oldPid + " still exists",
					_persistenceManager.exists(oldPid));
				Assert.assertTrue(
					"Configuration " + newPid + " does not exist",
					_persistenceManager.exists(newPid));

				Dictionary<String, String> dictionary =
					_persistenceManager.load(newPid);

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
		finally {
			if (configFile) {
				oldConfigFile.delete();
				newConfigFile.delete();
			}

			if (!factory || data) {
				_persistenceManager.delete(oldPid);
				_persistenceManager.delete(newPid);
			}
			else {
				DB db = DBManagerUtil.getDB();

				db.runSQL(
					StringBundler.concat(
						"delete from Configuration_ where configurationId ",
						"like '", _OLD_PID, "%' or configurationId like '",
						_NEW_PID, "%'"));
			}

			if (configuration != null) {
				ConfigurationTestUtil.deleteConfiguration(configuration);
			}
		}
	}

	private static final String _NEW_PID = "test.new.pid";

	private static final String _OLD_PID = "test.old.pid";

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	@Inject
	private ConfigurationUpgradeStepFactory _configurationUpgradeStepFactory;

	@Inject
	private PersistenceManager _persistenceManager;

	@Inject
	private Props _props;

}