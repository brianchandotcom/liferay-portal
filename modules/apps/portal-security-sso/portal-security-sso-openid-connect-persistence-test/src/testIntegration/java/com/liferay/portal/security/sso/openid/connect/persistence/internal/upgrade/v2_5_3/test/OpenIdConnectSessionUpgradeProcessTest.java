/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.persistence.internal.upgrade.v2_5_3.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.IndexMetadataFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.sql.Connection;

import java.util.Collections;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jorge García Jiménez
 */
@RunWith(Arquillian.class)
public class OpenIdConnectSessionUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule integrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		DB db = DBManagerUtil.getDB();

		try (Connection connection = DataAccess.getConnection()) {
			db.dropIndexes(
				connection, Collections.singletonList("IX_B1915EA3"),
				_TABLE_NAME);

			db.addIndexes(
				connection,
				Collections.singletonList(
					IndexMetadataFactoryUtil.createIndexMetadata(
						true, _TABLE_NAME, "issuer", "sessionId")));
		}
	}

	@After
	public void tearDown() throws Exception {
		DB db = DBManagerUtil.getDB();

		try (Connection connection = DataAccess.getConnection()) {
			db.dropIndexes(
				connection, Collections.singletonList("IX_DBE1CBFD"),
				_TABLE_NAME);

			DBInspector dbInspector = new DBInspector(connection);

			if (!dbInspector.hasIndex(_TABLE_NAME, "IX_B1915EA3")) {
				db.addIndexes(
					connection,
					Collections.singletonList(
						IndexMetadataFactoryUtil.createIndexMetadata(
							false, _TABLE_NAME, "companyId", "issuer",
							"sessionId")));
			}
		}
	}

	@Test
	public void testUpgrade() throws Exception {
		try (Connection connection = DataAccess.getConnection()) {
			DBInspector dbInspector = new DBInspector(connection);

			Assert.assertFalse(
				dbInspector.hasIndex(_TABLE_NAME, "IX_B1915EA3"));
			Assert.assertTrue(dbInspector.hasIndex(_TABLE_NAME, "IX_DBE1CBFD"));
		}

		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.portal.security.sso.openid.connect.persistence." +
				"internal.upgrade.v2_5_3.OpenIdConnectSessionUpgradeProcess");

		upgradeProcess.upgrade();

		_assertUpgraded();

		upgradeProcess.upgrade();

		_assertUpgraded();
	}

	private void _assertUpgraded() throws Exception {
		try (Connection connection = DataAccess.getConnection()) {
			DBInspector dbInspector = new DBInspector(connection);

			Assert.assertFalse(
				dbInspector.hasIndex(_TABLE_NAME, "IX_DBE1CBFD"));
			Assert.assertTrue(dbInspector.hasIndex(_TABLE_NAME, "IX_B1915EA3"));
		}
	}

	private static final String _TABLE_NAME = "OpenIdConnectSession";

	@Inject(
		filter = "component.name=com.liferay.portal.security.sso.openid.connect.persistence.internal.upgrade.registry.OpenIdConnectServiceUpgradeStepRegistrator"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}