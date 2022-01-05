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
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.util.PropsValues;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Dictionary;
import java.util.Objects;

import org.apache.felix.cm.file.ConfigurationHandler;

import org.junit.Assert;
import org.junit.BeforeClass;
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

	@BeforeClass
	public static void setUpClass() {
		_upgradeStepRegistrator.register(
			new UpgradeStepRegistrator.Registry() {

				@Override
				public void register(
					String fromSchemaVersionString,
					String toSchemaVersionString, UpgradeStep... upgradeSteps) {

					for (UpgradeStep upgradeStep : upgradeSteps) {
						Class<?> clazz = upgradeStep.getClass();

						if (Objects.equals(
								clazz.getName(),
								"com.liferay.portal.configuration." +
									"persistence.internal.upgrade.v1_0_0." +
										"UpgradeConfigurationPid")) {

							_upgradeConfigurationPidUpgradeProcess =
								(UpgradeProcess)upgradeStep;
						}
					}
				}

				@Override
				public void registerInitialUpgradeSteps(
					UpgradeStep... upgradeSteps) {
				}

			});
	}

	@Test
	public void testUpgradeConfigurationWhenFileNotExisted() throws Exception {
		String fileName = _SERVICE_FACTORY_PID + "-default.config";

		Path path = Paths.get(
			PropsValues.MODULE_FRAMEWORK_CONFIGS_DIR, fileName);

		if (Files.exists(path)) {
			Files.delete(path);
		}

		try {
			_addConfiguration(
				_SERVICE_FACTORY_PID, _SERVICE_FACTORY_PID + ".instance1",
				fileName);

			_upgradeConfigurationPidUpgradeProcess.upgrade();

			_assertConfiguration(
				_SERVICE_FACTORY_PID, _SERVICE_FACTORY_PID + "~instance1",
				null);
		}
		finally {
			_removeConfiguration(_SERVICE_FACTORY_PID);
		}
	}

	@Test
	public void testUpgradeConfigurationWithEmptyDictionary() throws Exception {
		try {
			String servicePid = "test.service.pid";

			_addConfiguration(servicePid, new HashMapDictionary<>());

			_upgradeConfigurationPidUpgradeProcess.upgrade();

			try (Connection connection = DataAccess.getConnection();
				PreparedStatement preparedStatement =
					connection.prepareStatement(
						StringBundler.concat(
							"select dictionary from Configuration_ where ",
							"configurationId = '", servicePid, "'"));
				ResultSet resultSet = preparedStatement.executeQuery()) {

				Assert.assertTrue(resultSet.next());

				while (resultSet.next()) {
					String dictionaryString = resultSet.getString("dictionary");

					Assert.assertTrue(
						dictionaryString + " should be null or empty",
						Validator.isNull(dictionaryString));
				}
			}
		}
		finally {
			_removeConfiguration("test.service.pid");
		}
	}

	@Test
	public void testUpgradeConfigurationWithFile() throws Exception {
		_testUpgradeConfigurationWithFile(CharPool.DASH);
		_testUpgradeConfigurationWithFile(CharPool.PERIOD);
		_testUpgradeConfigurationWithFile(CharPool.TILDE);
		_testUpgradeConfigurationWithFile(CharPool.UNDERLINE);
	}

	@Test
	public void testUpgradeConfigurationWithoutFile() throws Exception {
		_testUpgradeConfigurationWithoutFile(CharPool.DASH);
		_testUpgradeConfigurationWithoutFile(CharPool.PERIOD);
		_testUpgradeConfigurationWithoutFile(CharPool.TILDE);
		_testUpgradeConfigurationWithoutFile(CharPool.UNDERLINE);
	}

	private void _addConfiguration(
			String servicePid, Dictionary<String, String> dictionary)
		throws Exception {

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		ConfigurationHandler.write(unsyncByteArrayOutputStream, dictionary);

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"insert into Configuration_ (configurationId, dictionary) " +
					"values(?, ?)")) {

			preparedStatement.setString(1, servicePid);

			preparedStatement.setString(
				2, unsyncByteArrayOutputStream.toString());

			preparedStatement.execute();
		}
	}

	private void _addConfiguration(
			String serviceFactoryPid, String servicePid,
			String fileinstallFileName)
		throws Exception {

		Dictionary<String, String> dictionary = HashMapDictionaryBuilder.put(
			"felix.fileinstall.filename",
			() -> {
				if (fileinstallFileName != null) {
					return fileinstallFileName;
				}

				return null;
			}
		).put(
			"service.factoryPid", serviceFactoryPid
		).put(
			"service.pid", servicePid
		).build();

		_addConfiguration(servicePid, dictionary);
	}

	private void _assertConfiguration(
			String serviceFactoryPid, String servicePid,
			String fileinstallFileName)
		throws Exception {

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select dictionary from Configuration_ where ",
					"configurationId = '", servicePid, "'"));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				String dictionaryString = resultSet.getString("dictionary");

				Dictionary<String, String> dictionary =
					ConfigurationHandler.read(
						new UnsyncByteArrayInputStream(
							dictionaryString.getBytes(StringPool.UTF8)));

				Assert.assertEquals(
					serviceFactoryPid, dictionary.get("service.factoryPid"));

				Assert.assertEquals(servicePid, dictionary.get("service.pid"));

				Assert.assertEquals(
					fileinstallFileName,
					dictionary.get("felix.fileinstall.filename"));
			}
		}
	}

	private void _removeConfiguration(String serviceFactoryPid)
		throws Exception {

		DB db = DBManagerUtil.getDB();

		db.runSQL(
			"delete from Configuration_ where configurationId like '" +
				serviceFactoryPid + "%'");
	}

	private void _testUpgradeConfigurationWithFile(char separator)
		throws Exception {

		String fileName = _SERVICE_FACTORY_PID + separator + "default.config";

		Path path = Paths.get(
			PropsValues.MODULE_FRAMEWORK_CONFIGS_DIR, fileName);

		if (!Files.exists(path)) {
			Files.createFile(path);
		}

		try {
			_addConfiguration(
				_SERVICE_FACTORY_PID, _SERVICE_FACTORY_PID + ".instance1",
				fileName);

			_upgradeConfigurationPidUpgradeProcess.upgrade();

			_assertConfiguration(
				_SERVICE_FACTORY_PID, _SERVICE_FACTORY_PID + "~default",
				fileName);
		}
		finally {
			_removeConfiguration(_SERVICE_FACTORY_PID);

			Files.delete(path);
		}
	}

	private void _testUpgradeConfigurationWithoutFile(char separator)
		throws Exception {

		try {
			_addConfiguration(
				_SERVICE_FACTORY_PID,
				_SERVICE_FACTORY_PID + separator + "instance1", null);

			_upgradeConfigurationPidUpgradeProcess.upgrade();

			_assertConfiguration(
				_SERVICE_FACTORY_PID, _SERVICE_FACTORY_PID + "~instance1",
				null);
		}
		finally {
			_removeConfiguration(_SERVICE_FACTORY_PID);
		}
	}

	private static final String _SERVICE_FACTORY_PID = "test.configuration";

	private static UpgradeProcess _upgradeConfigurationPidUpgradeProcess;

	@Inject(
		filter = "component.name=com.liferay.portal.configuration.persistence.internal.upgrade.ConfigurationPersistenceUpgrade"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

}