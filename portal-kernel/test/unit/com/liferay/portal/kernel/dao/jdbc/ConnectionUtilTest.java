/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.jdbc;

import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Jorge Avalos
 */
public class ConnectionUtilTest {

	@Test
	public void testGetConnection() throws Exception {
		Connection boundConnection = Mockito.mock(Connection.class);

		SQLException sqlException1 = new SQLException(
			RandomTestUtil.randomString());

		Mockito.when(
			boundConnection.prepareStatement(Mockito.anyString())
		).thenThrow(
			sqlException1
		);

		DataSource dataSource = Mockito.mock(DataSource.class);

		try (MockedStatic<CurrentConnectionUtil>
				currentConnectionUtilMockedStatic = Mockito.mockStatic(
					CurrentConnectionUtil.class)) {

			currentConnectionUtilMockedStatic.when(
				() -> CurrentConnectionUtil.getConnection(dataSource)
			).thenReturn(
				boundConnection
			);

			Connection connection = ConnectionUtil.getConnection(dataSource);

			connection.close();

			Mockito.verify(
				boundConnection, Mockito.never()
			).close();

			try {
				connection.prepareStatement(RandomTestUtil.randomString());

				Assert.fail();
			}
			catch (SQLException sqlException2) {
				Assert.assertSame(sqlException1, sqlException2);
			}
		}
	}

}