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
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.util.PropsValues;

import java.io.File;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Arrays;
import java.util.Dictionary;

import org.apache.felix.cm.file.ConfigurationHandler;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sam Ziemer
 */
@RunWith(Arquillian.class)
public class UpgradeConfigurationPidUpgradeTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		DB db = DBManagerUtil.getDB();

		db.runSQL(
			"delete from Configuration_ where configurationId like '" +
				_SERVICE_FACTORY_PID + "%'");

		for (char separator :
				Arrays.asList(
					CharPool.TILDE, CharPool.DASH, CharPool.UNDERLINE)) {

			File file = new File(
				PropsValues.MODULE_FRAMEWORK_CONFIGS_DIR,
				_SERVICE_FACTORY_PID + separator + "default.config");

			if (file.exists()) {
				file.delete();
			}
		}
	}

	@Test
	public void testUpgradeConfigurationPid() throws Exception {
		_createUIConfiguration();

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select configurationId from Configuration_ where ",
					"configurationId like '", _SERVICE_FACTORY_PID, "%'"));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				Assert.assertEquals(
					_SERVICE_FACTORY_PID + "~instance1",
					resultSet.getString("configurationId"));
			}
		}
	}

	@Test
	public void testUpgradeDashFileSeparator() throws Exception {
		_createFileConfiguration(CharPool.DASH);

		Dictionary<String, String> dictionary = _getDictionary();

		Assert.assertEquals(
			_SERVICE_FACTORY_PID + "~default.config",
			dictionary.get("service.pid"));
	}

	@Test
	public void testUpgradeDictionary() throws Exception {
		_createUIConfiguration();

		Dictionary<String, String> dictionary = _getDictionary();

		Assert.assertEquals(
			_SERVICE_FACTORY_PID, dictionary.get("service.factoryPid"));

		Assert.assertEquals(
			_SERVICE_FACTORY_PID + "~instance1", dictionary.get("service.pid"));
	}

	@Test
	public void testUpgradeTildeFileSeparator() throws Exception {
		_createFileConfiguration(CharPool.TILDE);

		Dictionary<String, String> dictionary = _getDictionary();

		Assert.assertEquals(
			_SERVICE_FACTORY_PID + "~default.config",
			dictionary.get("service.pid"));
	}

	@Test
	public void testUpgradeUnderlineFileSeparator() throws Exception {
		_createFileConfiguration(CharPool.UNDERLINE);

		Dictionary<String, String> dictionary = _getDictionary();

		Assert.assertEquals(
			_SERVICE_FACTORY_PID + "~default.config",
			dictionary.get("service.pid"));
	}

	private void _createFileConfiguration(Character separator)
		throws Exception {

		String fileName = _SERVICE_FACTORY_PID + separator + "default.config";

		File file = new File(
			PropsValues.MODULE_FRAMEWORK_CONFIGS_DIR, fileName);

		if (!file.exists()) {
			file.createNewFile();
		}

		_updateDatabase(
			HashMapDictionaryBuilder.put(
				"felix.fileinstall.filename", fileName
			).put(
				"service.factoryPid", _SERVICE_FACTORY_PID
			).put(
				"service.pid", _CONFIGURATION_PID
			).build());
	}

	private void _createUIConfiguration() throws Exception {
		_updateDatabase(
			HashMapDictionaryBuilder.put(
				"service.factoryPid", _SERVICE_FACTORY_PID
			).put(
				"service.pid", _CONFIGURATION_PID
			).build());
	}

	private Dictionary<String, String> _getDictionary() throws Exception {
		String dictionaryString = null;

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select dictionary from Configuration_ where ",
					"configurationId like '", _SERVICE_FACTORY_PID, "%'"));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				dictionaryString = resultSet.getString("dictionary");
			}
		}

		return ConfigurationHandler.read(
			new UnsyncByteArrayInputStream(
				dictionaryString.getBytes(StringPool.UTF8)));
	}

	private void _setUpUpgradeConfigurationPid() {
		_upgradeStepRegistrator.register(
			new UpgradeStepRegistrator.Registry() {

				@Override
				public void register(
					String fromSchemaVersionString,
					String toSchemaVersionString, UpgradeStep... upgradeSteps) {

					for (UpgradeStep upgradeStep : upgradeSteps) {
						Class<?> clazz = upgradeStep.getClass();

						String className = clazz.getName();

						if (className.contains(_CLASS_NAME)) {
							_upgradeConfigurationPidUpgradeProcess =
								(UpgradeProcess)upgradeStep;
						}
					}
				}

			});
	}

	private void _updateDatabase(Dictionary<String, String> dictionary)
		throws Exception {

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		ConfigurationHandler.write(unsyncByteArrayOutputStream, dictionary);

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"insert into Configuration_ (configurationId, dictionary) " +
					"values(?, ?)")) {

			preparedStatement.setString(1, _CONFIGURATION_PID);

			preparedStatement.setString(
				2, unsyncByteArrayOutputStream.toString());

			preparedStatement.execute();
		}

		_setUpUpgradeConfigurationPid();

		_upgradeConfigurationPidUpgradeProcess.upgrade();
	}

	private static final String _CLASS_NAME =
		"com.liferay.portal.configuration.persistence.internal.upgrade." +
			"v1_0_0.UpgradeConfigurationPid";

	private static final String _CONFIGURATION_PID =
		"test.configuration.instance1";

	private static final String _SERVICE_FACTORY_PID = "test.configuration";

	@Inject(
		filter = "(&(objectClass=com.liferay.portal.configuration.persistence.internal.upgrade.ConfigurationPersistenceUpgrade))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	private UpgradeProcess _upgradeConfigurationPidUpgradeProcess;

}