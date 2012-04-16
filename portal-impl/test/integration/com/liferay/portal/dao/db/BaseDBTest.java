/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.dao.db;

import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

/**
 * @author Daniel Kocsis
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class BaseDBTest {

	@Test
	public void testIsPortalIndex() throws IOException, SQLException {
		Thread currentThread = Thread.currentThread();

		BaseDB baseDB = (BaseDB) DBFactoryUtil.getDB();

		Connection connection = null;

		try {
			connection = DataAccess.getConnection();

			boolean result = baseDB.isPortalIndex(
				connection, "IX_1E9D371D", true, "AssetEntry");

			Assert.assertTrue(result);

			result = baseDB.isPortalIndex(
				connection, "IX_FC1F9C7B", false, "AssetEntry");

			Assert.assertTrue(result);

			result = baseDB.isPortalIndex(
				connection, "IX_TEST_NON_PORTAL", true, "AssetEntry");

			Assert.assertFalse(result);

			result = baseDB.isPortalIndex(
				connection, Mockito.anyString(), Mockito.anyBoolean(),
				Mockito.anyString());

			Assert.assertFalse(result);
		}
		finally {
			DataAccess.cleanUp(connection);
		}
	}

}