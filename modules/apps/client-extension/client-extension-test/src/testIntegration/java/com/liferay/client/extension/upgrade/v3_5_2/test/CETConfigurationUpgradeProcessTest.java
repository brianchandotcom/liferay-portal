/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.upgrade.v3_5_2.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Anthony Chu
 */
@RunWith(Arquillian.class)
public class CETConfigurationUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		DB db = DBManagerUtil.getDB();

		db.runSQL("delete from Configuration_ where " + _pidsInClause());
	}

	@Test
	public void testUpgrade() throws Exception {
		_insertConfiguration(_STALE_CET_PID_1, "");
		_insertConfiguration(_STALE_CET_PID_2, null);
		_insertConfiguration(
			_TRACKER_CET_PID, ".client.extension.config.bundle.id=L\"1\"\n");
		_insertConfiguration(_UNRELATED_PID, "");

		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.client.extension.internal.upgrade.v3_5_2." +
				"CETConfigurationUpgradeProcess");

		upgradeProcess.upgrade();

		try (Connection connection = DataAccess.getConnection();

			Statement statement = connection.createStatement();

			ResultSet resultSet = statement.executeQuery(
				"select configurationId from Configuration_ where " +
					_pidsInClause())) {

			Set<String> survivingPids = new HashSet<>();

			while (resultSet.next()) {
				survivingPids.add(resultSet.getString("configurationId"));
			}

			Assert.assertEquals(
				SetUtil.fromArray(_TRACKER_CET_PID, _UNRELATED_PID),
				survivingPids);
		}
	}

	private void _insertConfiguration(String pid, String dictionary)
		throws Exception {

		try (Connection connection = DataAccess.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				"insert into Configuration_ (configurationId, dictionary) " +
					"values(?, ?)")) {

			preparedStatement.setString(1, pid);
			preparedStatement.setString(2, dictionary);

			preparedStatement.execute();
		}
	}

	private String _pidsInClause() {
		return StringBundler.concat(
			"configurationId in ('", _STALE_CET_PID_1, "', '", _STALE_CET_PID_2,
			"', '", _TRACKER_CET_PID, "', '", _UNRELATED_PID, "')");
	}

	private static final String _CET_CONFIGURATION_PID_PREFIX =
		"com.liferay.client.extension.type.configuration.CETConfiguration~";

	private static final String _STALE_CET_PID_1 =
		_CET_CONFIGURATION_PID_PREFIX + "upgrade-test-cet-1/liferay.com";

	private static final String _STALE_CET_PID_2 =
		_CET_CONFIGURATION_PID_PREFIX + "upgrade-test-cet-2/liferay.com";

	private static final String _TRACKER_CET_PID =
		_CET_CONFIGURATION_PID_PREFIX + "upgrade-test-tracker/liferay.com";

	private static final String _UNRELATED_PID =
		"com.liferay.client.extension.upgrade.test.unrelated";

	@Inject(
		filter = "component.name=com.liferay.client.extension.internal.upgrade.registry.ClientExtensionUpgradeStepRegistrator"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}