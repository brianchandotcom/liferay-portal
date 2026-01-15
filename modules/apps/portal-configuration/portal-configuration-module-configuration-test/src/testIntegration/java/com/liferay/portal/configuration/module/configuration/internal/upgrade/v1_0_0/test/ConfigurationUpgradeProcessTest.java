/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.module.configuration.internal.upgrade.v1_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.db.partition.util.DBPartitionUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.io.IOException;

import java.nio.charset.StandardCharsets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Dictionary;

import org.apache.felix.cm.file.ConfigurationHandler;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Thiago Buarque
 */
@RunWith(Arquillian.class)
public class ConfigurationUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_company = CompanyTestUtil.addCompany();
	}

	@After
	public void tearDown() throws Exception {
		DBPartitionUtil.forEachCompanyId(companyId -> _deleteConfiguration());
	}

	@Test
	public void testDoUpgrade() throws Exception {
		_testDoUpgrade();
		_testDoUpgradeWhenDatabasePartitionIsEnabled();
	}

	private void _assertConfiguration() throws Exception {
		long companyId = CompanyThreadLocal.getCompanyId();

		long count = 0;

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select dictionary from Configuration_ where ",
					"configurationId like '", _CONFIGURATION_ID, "%' and ",
					"dictionary like '%companyId=%", companyId, "%'"));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				count += 1;

				Dictionary<String, Object> dictionary = _toDictionary(
					resultSet.getString(1));

				Assert.assertEquals(
					companyId,
					dictionary.get(
						ExtendedObjectClassDefinition.Scope.COMPANY.
							getPropertyKey()));
			}
		}

		Assert.assertEquals(1, count);
	}

	private void _createConfiguration() throws Exception {
		Group group = _groupLocalService.getGroup(
			CompanyThreadLocal.getCompanyId(), GroupConstants.GUEST);

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"insert into Configuration_ (configurationId, dictionary) " +
					"values(?, ?)")) {

			String factoryPid =
				_CONFIGURATION_ID + "~" + RandomTestUtil.randomString();

			preparedStatement.setString(1, factoryPid);
			preparedStatement.setString(
				2,
				_toString(
					HashMapDictionaryBuilder.<String, Object>put(
						"groupId", group.getGroupId()
					).put(
						"key", "value"
					).put(
						"service.factoryPid", factoryPid
					).put(
						"service.pid", _CONFIGURATION_ID + ".scoped"
					).build()));

			preparedStatement.execute();
		}
	}

	private void _deleteConfiguration() throws IOException, SQLException {
		DB db = DBManagerUtil.getDB();

		db.runSQL(
			"delete from Configuration_ where configurationId like '" +
				_CONFIGURATION_ID + "%'");
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, _CLASS_NAME);

		upgradeProcess.upgrade();
	}

	private void _testDoUpgrade() throws Exception {
		if (PropsValues.DATABASE_PARTITION_ENABLED) {
			return;
		}

		_companyLocalService.forEachCompany(company -> _createConfiguration());

		_runUpgrade();

		_companyLocalService.forEachCompany(company -> _assertConfiguration());
	}

	private void _testDoUpgradeWhenDatabasePartitionIsEnabled()
		throws Exception {

		if (!PropsValues.DATABASE_PARTITION_ENABLED) {
			return;
		}

		DBPartitionUtil.forEachCompanyId(companyId -> _createConfiguration());

		_runUpgrade();

		DBPartitionUtil.forEachCompanyId(companyId -> _assertConfiguration());
	}

	private Dictionary<String, Object> _toDictionary(String dictionaryString)
		throws IOException {

		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(
				dictionaryString.getBytes(StandardCharsets.UTF_8));

		return ConfigurationHandler.read(unsyncByteArrayInputStream);
	}

	private String _toString(Dictionary<String, Object> dictionary)
		throws IOException {

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		ConfigurationHandler.write(unsyncByteArrayOutputStream, dictionary);

		return new String(
			unsyncByteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
	}

	private static final String _CLASS_NAME =
		"com.liferay.portal.configuration.module.configuration.internal." +
			"upgrade.v1_0_0.ConfigurationUpgradeProcess";

	private static final String _CONFIGURATION_ID =
		RandomTestUtil.randomString();

	@Inject(
		filter = "(&(component.name=com.liferay.portal.configuration.module.configuration.internal.upgrade.registry.ConfigurationUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@DeleteAfterTestRun
	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

}