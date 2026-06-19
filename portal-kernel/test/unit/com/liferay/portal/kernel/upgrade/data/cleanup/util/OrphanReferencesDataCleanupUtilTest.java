/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade.data.cleanup.util;

import com.liferay.portal.kernel.test.ReflectionTestUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Jorge Avalos
 */
public class OrphanReferencesDataCleanupUtilTest {

	@Test
	public void testExecuteDeleteWithoutRetry() throws Exception {
		PreparedStatement preparedStatement = Mockito.mock(
			PreparedStatement.class);

		Mockito.doThrow(
			new SQLException(
				"Constraint violation", _SQLSTATE_CONSTRAINT_VIOLATION)
		).when(
			preparedStatement
		).executeUpdate();

		Connection connection = Mockito.mock(Connection.class);

		Mockito.when(
			connection.prepareStatement(Mockito.anyString())
		).thenReturn(
			preparedStatement
		);

		try {
			ReflectionTestUtil.invoke(
				OrphanReferencesDataCleanupUtil.class, "_executeDelete",
				new Class<?>[] {Connection.class, String.class}, connection,
				"delete from foo where bar is null");

			Assert.fail();
		}
		catch (Exception exception) {
			SQLException sqlException = (SQLException)exception;

			Assert.assertEquals(
				_SQLSTATE_CONSTRAINT_VIOLATION, sqlException.getSQLState());
		}

		Mockito.verify(
			preparedStatement, Mockito.times(1)
		).executeUpdate();
	}

	@Test
	public void testExecuteDeleteWithRetry() throws Exception {
		PreparedStatement preparedStatement = Mockito.mock(
			PreparedStatement.class);

		AtomicInteger callCount = new AtomicInteger();

		Mockito.doAnswer(
			invocation -> {
				if (callCount.incrementAndGet() == 1) {
					throw new SQLException("Deadlock", _SQLSTATE_DEADLOCK);
				}

				return 0;
			}
		).when(
			preparedStatement
		).executeUpdate();

		Connection connection = Mockito.mock(Connection.class);

		Mockito.when(
			connection.prepareStatement(Mockito.anyString())
		).thenReturn(
			preparedStatement
		);

		ReflectionTestUtil.invoke(
			OrphanReferencesDataCleanupUtil.class, "_executeDelete",
			new Class<?>[] {Connection.class, String.class}, connection,
			"delete from foo where bar is null");

		Assert.assertEquals(2, callCount.get());

		Mockito.verify(
			preparedStatement, Mockito.times(2)
		).executeUpdate();
	}

	@Test
	public void testExecuteDeleteWithRetryExhausted() throws Exception {
		PreparedStatement preparedStatement = Mockito.mock(
			PreparedStatement.class);

		Mockito.doThrow(
			new SQLException("Deadlock", _SQLSTATE_DEADLOCK)
		).when(
			preparedStatement
		).executeUpdate();

		Connection connection = Mockito.mock(Connection.class);

		Mockito.when(
			connection.prepareStatement(Mockito.anyString())
		).thenReturn(
			preparedStatement
		);

		try {
			ReflectionTestUtil.invoke(
				OrphanReferencesDataCleanupUtil.class, "_executeDelete",
				new Class<?>[] {Connection.class, String.class}, connection,
				"delete from foo where bar is null");

			Assert.fail();
		}
		catch (Exception exception) {
			SQLException sqlException = (SQLException)exception;

			Assert.assertEquals(_SQLSTATE_DEADLOCK, sqlException.getSQLState());
		}

		int expectedAttempts =
			(int)ReflectionTestUtil.getFieldValue(
				OrphanReferencesDataCleanupUtil.class,
				"_DELETE_MAX_DEADLOCK_RETRIES") + 1;

		Mockito.verify(
			preparedStatement, Mockito.times(expectedAttempts)
		).executeUpdate();
	}

	private static final String _SQLSTATE_CONSTRAINT_VIOLATION = "23000";

	private static final String _SQLSTATE_DEADLOCK = "40001";

}