/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.internal.upgrade.v3_5_2.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Anthony Chu
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class CETConfigurationUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_initialConfigurationCount = _getConfigurationCount();
	}

	@After
	public void tearDown() throws Exception {
		DB db = DBManagerUtil.getDB();

		db.runSQL(
			"delete from Configuration_ where configurationId in ('" +
				StringUtil.merge(_pids, "','") + "')");
	}

	@Test
	public void testUpgrade() throws Exception {
		Assert.assertEquals(
			_initialConfigurationCount, _getConfigurationCount());

		_insertConfiguration();

		Assert.assertEquals(
			_initialConfigurationCount + 1, _getConfigurationCount());

		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.client.extension.internal.upgrade.v3_5_2." +
				"CETConfigurationUpgradeProcess");

		upgradeProcess.upgrade();

		Assert.assertEquals(0, _getConfigurationCount());
	}

	private long _getConfigurationCount() throws Exception {
		try (Connection connection = DataAccess.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				"select count(*) as count from Configuration_ where " +
					"configurationId like ?")) {

			preparedStatement.setString(1, _FACTORY_PID + "~%");

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (!resultSet.next()) {
					throw new Exception("Could not count configurations");
				}

				return resultSet.getLong("count");
			}
		}
	}

	private void _insertConfiguration() throws Exception {
		String pid = _FACTORY_PID + "~" + RandomTestUtil.randomString();

		try (Connection connection = DataAccess.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				"insert into Configuration_ (configurationId, dictionary) " +
					"values(?, ?)")) {

			preparedStatement.setString(1, pid);
			preparedStatement.setString(2, "");

			preparedStatement.execute();
		}

		_pids.add(pid);
	}

	private static final String _FACTORY_PID =
		"com.liferay.client.extension.type.configuration.CETConfiguration";

	private long _initialConfigurationCount;
	private final List<String> _pids = new ArrayList<>();

	@Inject(
		filter = "component.name=com.liferay.client.extension.internal.upgrade.registry.ClientExtensionUpgradeStepRegistrator"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}