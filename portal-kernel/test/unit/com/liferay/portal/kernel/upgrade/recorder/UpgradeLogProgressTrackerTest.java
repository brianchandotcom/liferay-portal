/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade.recorder;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.dao.db.BaseDBProcess;
import com.liferay.portal.kernel.test.util.PropsValuesTestUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.Method;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author István András Dézsi
 */
public class UpgradeLogProgressTrackerTest {

	@Test
	public void testBaseDBProcessGetConnectionCallsGetConnectionWrapper()
		throws Exception {

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			MockedStatic<UpgradeLogProgressTracker>
				upgradeLogProgressTrackerMockedStatic = Mockito.mockStatic(
					UpgradeLogProgressTracker.class,
					Mockito.CALLS_REAL_METHODS)) {

			UpgradeLogProgressTracker.start();

			upgradeLogProgressTrackerMockedStatic.when(
				() -> UpgradeLogProgressTracker.getConnectionWrapper(
					Mockito.any(Connection.class))
			).thenAnswer(
				invocation -> invocation.getArgument(0)
			);

			Method method = BaseDBProcess.class.getDeclaredMethod(
				"getConnection");

			method.setAccessible(true);

			method.invoke(
				new BaseDBProcess() {
				});

			upgradeLogProgressTrackerMockedStatic.verify(
				() -> UpgradeLogProgressTracker.getConnectionWrapper(
					Mockito.any(Connection.class)));

			UpgradeLogProgressTracker.stop();
		}
	}

	@Test
	public void testGetConnectionWrapperDisabled() throws Exception {
		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", false)) {

			UpgradeLogProgressTracker.start();

			Connection connection = Mockito.mock(Connection.class);

			Connection wrappedConnection =
				UpgradeLogProgressTracker.getConnectionWrapper(connection);

			Assert.assertSame(connection, wrappedConnection);

			UpgradeLogProgressTracker.stop();
		}
	}

	@Test
	public void testGetConnectionWrapperEnabled() throws Exception {
		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true)) {

			try {
				UpgradeLogProgressTracker.start();

				Connection connection = Mockito.mock(Connection.class);

				Connection wrappedConnection =
					UpgradeLogProgressTracker.getConnectionWrapper(connection);

				Assert.assertNotSame(connection, wrappedConnection);

				Statement rawStatement = Mockito.mock(Statement.class);

				Mockito.when(
					connection.createStatement()
				).thenReturn(
					rawStatement
				);

				Statement statement = wrappedConnection.createStatement();

				Assert.assertSame(wrappedConnection, statement.getConnection());

				Assert.assertTrue(ProxyUtil.isProxyClass(statement.getClass()));

				Mockito.verify(
					rawStatement, Mockito.never()
				).getConnection();

				ResultSet rawResultSet = Mockito.mock(ResultSet.class);

				Mockito.when(
					statement.executeQuery(Mockito.anyString())
				).thenReturn(
					rawResultSet
				);

				ResultSet resultSet = statement.executeQuery("select 1");

				Assert.assertSame(statement, resultSet.getStatement());

				Assert.assertTrue(ProxyUtil.isProxyClass(resultSet.getClass()));

				Mockito.verify(
					rawResultSet, Mockito.never()
				).getStatement();

				Mockito.when(
					connection.prepareStatement(Mockito.anyString())
				).thenReturn(
					Mockito.mock(PreparedStatement.class)
				);

				PreparedStatement preparedStatement =
					wrappedConnection.prepareStatement("select 1");

				Assert.assertTrue(
					ProxyUtil.isProxyClass(preparedStatement.getClass()));

				Mockito.when(
					preparedStatement.executeQuery()
				).thenReturn(
					Mockito.mock(ResultSet.class)
				);

				Assert.assertTrue(
					ProxyUtil.isProxyClass(
						preparedStatement.executeQuery(
						).getClass()));

				Mockito.when(
					connection.prepareCall(Mockito.anyString())
				).thenReturn(
					Mockito.mock(CallableStatement.class)
				);

				CallableStatement callableStatement =
					wrappedConnection.prepareCall("call test()");

				Assert.assertTrue(
					ProxyUtil.isProxyClass(callableStatement.getClass()));

				Mockito.when(
					callableStatement.executeQuery()
				).thenReturn(
					Mockito.mock(ResultSet.class)
				);

				Assert.assertTrue(
					ProxyUtil.isProxyClass(
						callableStatement.executeQuery(
						).getClass()));
			}
			finally {
				UpgradeLogProgressTracker.stop();
			}
		}
	}

}