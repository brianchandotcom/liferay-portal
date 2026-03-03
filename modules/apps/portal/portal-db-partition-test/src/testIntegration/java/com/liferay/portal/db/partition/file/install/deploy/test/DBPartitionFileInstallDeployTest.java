/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.file.install.deploy.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.db.partition.test.util.BaseDBPartitionTestCase;
import com.liferay.portal.db.partition.util.DBPartitionUtil;
import com.liferay.portal.file.install.FileInstaller;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.test.rule.Inject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Objects;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class DBPartitionFileInstallDeployTest extends BaseDBPartitionTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseDBPartitionTestCase.setUpClass();

		BaseDBPartitionTestCase.setUpDBPartitions();

		Bundle bundle = FrameworkUtil.getBundle(
			DBPartitionFileInstallDeployTest.class);

		try (ServiceTrackerList<FileInstaller> serviceTrackerList =
				ServiceTrackerListFactory.open(
					bundle.getBundleContext(), FileInstaller.class)) {

			for (FileInstaller fileInstaller : serviceTrackerList.toList()) {
				Class<?> clazz = fileInstaller.getClass();

				if (Objects.equals(
						clazz.getName(),
						"com.liferay.portal.file.install.internal." +
							"configuration.ConfigurationFileInstaller")) {

					_fileInstaller = fileInstaller;

					break;
				}
			}
		}

		_dataSource = InfrastructureUtil.getDataSource();

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					COMPANY_IDS[1])) {

			_group = _groupLocalService.getCompanyGroup(COMPANY_IDS[1]);
		}
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		BaseDBPartitionTestCase.tearDownDBPartitions();
	}

	@Test
	public void testCompanyScopedConfiguration() throws Exception {
		_testCompanyScopedConfiguration(
			ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey(),
			COMPANY_IDS[1]);
	}

	@Test
	public void testCompanyScopedPortableKeyConfiguration() throws Exception {
		_testCompanyScopedConfiguration(
			ExtendedObjectClassDefinition.Scope.COMPANY.
				getPortablePropertyKey(),
			"Test" + COMPANY_IDS[1]);
	}

	@Test
	public void testGroupScopedConfiguration() throws Exception {
		_testGroupScopedConfiguration(
			ExtendedObjectClassDefinition.Scope.GROUP.getPropertyKey(),
			_group.getGroupId());
	}

	@Test
	public void testGroupScopedPortableKeyConfiguration() throws Exception {
		_testGroupScopedConfiguration(
			ExtendedObjectClassDefinition.Scope.GROUP.getPortablePropertyKey(),
			StringBundler.concat(
				"Test", COMPANY_IDS[1], "--", _group.getGroupKey()));
	}

	@Test
	public void testInvalidGroupScopedConfiguration() throws Exception {
		_testScopedConfiguration(
			() -> _checkConfigurationNotExists(),
			ExtendedObjectClassDefinition.Scope.GROUP.getPropertyKey(),
			_group.getGroupId(),
			illegalArgumentException -> Assert.assertEquals(
				StringBundler.concat(
					"Unable to process group scoped configuration ",
					_CONFIGURATION_FACTORY_PID,
					".config because required property \"companyId\" is ",
					"missing"),
				illegalArgumentException.getMessage()),
			false, () -> _checkConfigurationNotExists());
	}

	@Test
	public void testPortletInstanceScopedConfiguration() throws Exception {
		_testPortletInstanceScopedConfiguration(
			ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE.
				getPropertyKey(),
			RandomTestUtil.randomLong());
	}

	@Test
	public void testPortletInstanceScopedPortableKeyConfiguration()
		throws Exception {

		_testPortletInstanceScopedConfiguration(
			ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE.
				getPortablePropertyKey(),
			RandomTestUtil.randomLong());
	}

	@Test
	public void testSystemScopedConfiguration() throws Exception {
		_testScopedConfiguration(
			() -> _checkConfigurationExists(
				PortalInstancePool.getDefaultCompanyId(), _TEST_VALUE_1),
			null, null, illegalArgumentException -> Assert.fail(), true,
			() -> _checkConfigurationExists(
				PortalInstancePool.getDefaultCompanyId(), _TEST_VALUE_2));
	}

	private void _checkConfiguration(
			UnsafeBiConsumer<Long, ResultSet, Exception> unsafeBiConsumer)
		throws Exception {

		DBPartitionUtil.forEachCompanyId(
			companyId -> {
				try (Connection connection = _dataSource.getConnection();
					PreparedStatement preparedStatement =
						connection.prepareStatement(
							"select dictionary from Configuration_ where " +
								"configurationId = ?")) {

					preparedStatement.setString(1, _CONFIGURATION_FACTORY_PID);

					try (ResultSet resultSet =
							preparedStatement.executeQuery()) {

						unsafeBiConsumer.accept(companyId, resultSet);
					}
				}
			});
	}

	private void _checkConfigurationExists(long companyId, String expectedValue)
		throws Exception {

		_checkConfiguration(
			(currentCompanyId, resultSet) -> {
				if (currentCompanyId == companyId) {
					Assert.assertTrue(resultSet.next());

					String value = resultSet.getString(1);

					Assert.assertTrue(
						value.contains(
							StringBundler.concat(
								_TEST_KEY, StringPool.EQUAL, StringPool.QUOTE,
								expectedValue, StringPool.QUOTE)));
				}
				else {
					Assert.assertFalse(resultSet.next());
				}
			});
	}

	private void _checkConfigurationNotExists() throws Exception {
		_checkConfiguration(
			(companyId, resultSet) -> Assert.assertFalse(resultSet.next()));
	}

	private String _convertDictionaryValue(Object value) {
		if (value instanceof Long) {
			return StringBundler.concat("L\"", value, StringPool.QUOTE);
		}

		return StringBundler.concat(StringPool.QUOTE, value, StringPool.QUOTE);
	}

	private String _getContent(
		String dictionaryKey, Object dictionaryValue,
		boolean supportedConfiguration, String testValue) {

		StringBundler sb = new StringBundler(13);

		sb.append(_TEST_KEY);
		sb.append(StringPool.EQUAL);
		sb.append(StringPool.QUOTE);
		sb.append(testValue);
		sb.append(StringPool.QUOTE);

		if (dictionaryKey != null) {
			if (supportedConfiguration &&
				dictionaryKey.equals(
					ExtendedObjectClassDefinition.Scope.GROUP.
						getPropertyKey())) {

				sb.append(StringPool.RETURN_NEW_LINE);
				sb.append(
					ExtendedObjectClassDefinition.Scope.COMPANY.
						getPropertyKey());
				sb.append(StringPool.EQUAL);
				sb.append(_convertDictionaryValue(COMPANY_IDS[1]));
			}

			sb.append(StringPool.RETURN_NEW_LINE);
			sb.append(dictionaryKey);
			sb.append(StringPool.EQUAL);
			sb.append(_convertDictionaryValue(dictionaryValue));
		}

		return sb.toString();
	}

	private void _testCompanyScopedConfiguration(
			String dictionaryKey, Object dictionaryValue)
		throws Exception {

		_testScopedConfiguration(
			() -> _checkConfigurationExists(COMPANY_IDS[1], _TEST_VALUE_1),
			dictionaryKey, dictionaryValue,
			illegalArgumentException -> Assert.fail(), true,
			() -> _checkConfigurationExists(COMPANY_IDS[1], _TEST_VALUE_2));
	}

	private void _testGroupScopedConfiguration(
			String dictionaryKey, Object dictionaryValue)
		throws Exception {

		_testScopedConfiguration(
			() -> _checkConfigurationExists(COMPANY_IDS[1], _TEST_VALUE_1),
			dictionaryKey, dictionaryValue,
			illegalArgumentException -> Assert.fail(), true,
			() -> _checkConfigurationExists(COMPANY_IDS[1], _TEST_VALUE_2));
	}

	private void _testPortletInstanceScopedConfiguration(
			String dictionaryKey, Object dictionaryValue)
		throws Exception {

		_testScopedConfiguration(
			() -> _checkConfigurationNotExists(), dictionaryKey,
			dictionaryValue,
			unsupportedOperationException -> Assert.assertEquals(
				"Scope PORTLET_INSTANCE does not support database partition",
				unsupportedOperationException.getMessage()),
			false, () -> _checkConfigurationNotExists());
	}

	private void _testScopedConfiguration(
			UnsafeRunnable<Exception> addValidatorRunnable,
			String dictionaryKey, Object dictionaryValue,
			UnsafeConsumer<Exception, Exception> exceptionValidatorConsumer,
			boolean supportedConfiguration,
			UnsafeRunnable<Exception> updateValidatorRunnable)
		throws Exception {

		Assert.assertNotNull(_fileInstaller);

		Path path = Paths.get(
			PropsValues.LIFERAY_HOME,
			_CONFIGURATION_FACTORY_PID.concat(".config"));

		try {
			try {
				String content = _getContent(
					dictionaryKey, dictionaryValue, supportedConfiguration,
					_TEST_VALUE_1);

				Files.write(path, content.getBytes());

				_fileInstaller.transformURL(path.toFile());

				if (!supportedConfiguration) {
					Assert.fail();
				}
			}
			catch (IllegalArgumentException | UnsupportedOperationException
						exception) {

				exceptionValidatorConsumer.accept(exception);
			}

			addValidatorRunnable.run();

			try {
				String content = _getContent(
					dictionaryKey, dictionaryValue, supportedConfiguration,
					_TEST_VALUE_2);

				Files.write(path, content.getBytes());

				_fileInstaller.transformURL(path.toFile());

				if (!supportedConfiguration) {
					Assert.fail();
				}
			}
			catch (IllegalArgumentException | UnsupportedOperationException
						exception) {

				exceptionValidatorConsumer.accept(exception);
			}

			updateValidatorRunnable.run();

			Files.deleteIfExists(path);

			_fileInstaller.uninstall(path.toFile());

			_checkConfigurationNotExists();
		}
		finally {
			Files.deleteIfExists(path);

			DBPartitionUtil.forEachCompanyId(
				companyId -> {
					try (Connection connection = _dataSource.getConnection();
						PreparedStatement preparedStatement =
							connection.prepareStatement(
								"delete from Configuration_ where " +
									"configurationId = ?")) {

						preparedStatement.setString(
							1, _CONFIGURATION_FACTORY_PID);
						preparedStatement.executeUpdate();
					}
				});

			_fileInstaller.uninstall(path.toFile());
		}
	}

	private static final String _CONFIGURATION_FACTORY_PID =
		DBPartitionFileInstallDeployTest.class.getName() + "Configuration~" +
			RandomTestUtil.randomString();

	private static final String _TEST_KEY = "testKey";

	private static final String _TEST_VALUE_1 = "testValue1";

	private static final String _TEST_VALUE_2 = "testValue2";

	private static DataSource _dataSource;
	private static FileInstaller _fileInstaller;
	private static Group _group;

	@Inject
	private static GroupLocalService _groupLocalService;

}