/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.upgrade.v7_0_0.util.CompanyTable;

import java.sql.Connection;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Mariano Álvaro Sáiz
 */
public class UpgradeShardingTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_upgradeTable = Mockito.mock(UpgradeTable.class);

		_upgradeTableFactoryUtilMockedStatic = Mockito.mockStatic(
			UpgradeTableFactoryUtil.class);

		_upgradeTableFactoryUtilMockedStatic.when(
			() -> UpgradeTableFactoryUtil.getUpgradeTable(
				Mockito.any(String.class), Mockito.any(Object[][].class))
		).thenReturn(
			_upgradeTable
		);
	}

	@After
	public void tearDown() {
		_upgradeTableFactoryUtilMockedStatic.close();
	}

	@Test
	public void testCopyControlTableAddsIndexes() throws Exception {
		UpgradeSharding upgradeSharding = new UpgradeSharding() {

			@Override
			public void dropTable(Connection connection, String tableName) {
			}

			@Override
			public boolean hasRows(Connection connection, String tableName) {
				return false;
			}

		};

		upgradeSharding.copyControlTable(
			Mockito.mock(Connection.class), Mockito.mock(Connection.class),
			CompanyTable.TABLE_NAME, CompanyTable.TABLE_COLUMNS,
			CompanyTable.TABLE_SQL_CREATE, CompanyTable.TABLE_SQL_ADD_INDEXES);

		Mockito.verify(
			_upgradeTable
		).setIndexesSQL(
			CompanyTable.TABLE_SQL_ADD_INDEXES
		);
	}

	protected void dropTable(Connection connection, String tableName) {
	}

	protected boolean hasRows(Connection connection, String tableName) {
		return false;
	}

	private UpgradeTable _upgradeTable;
	private MockedStatic<UpgradeTableFactoryUtil>
		_upgradeTableFactoryUtilMockedStatic;

}