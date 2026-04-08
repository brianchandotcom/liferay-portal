/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.db;

import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Mariano Álvaro Sáiz
 */
public class BaseDBProcessTest {

	@Test
	public void testGetFixedThreadPoolSize() throws Exception {
		_testGetFixedThreadPoolSize(DBType.MYSQL, 1, 1);

		Runtime runtime = Runtime.getRuntime();

		_testGetFixedThreadPoolSize(
			DBType.MYSQL, runtime.availableProcessors(), 1000);

		_testGetFixedThreadPoolSize(DBType.HYPERSONIC, 1, 1000);
	}

	private void _testGetFixedThreadPoolSize(
			DBType dbType, int expectedFixedThreadPoolSize, int maximumPoolSize)
		throws Exception {

		Runtime runtime = Runtime.getRuntime();

		try (MockedStatic<DBManagerUtil> dbManagerUtilMockedStatic =
				Mockito.mockStatic(DBManagerUtil.class);
			MockedStatic<PortalInstancePool> portalInstancePoolMockedStatic =
				Mockito.mockStatic(PortalInstancePool.class);
			MockedStatic<PropsUtil> propsUtilMockedStatic = Mockito.mockStatic(
				PropsUtil.class)) {

			DB db = Mockito.mock(DB.class);

			dbManagerUtilMockedStatic.when(
				DBManagerUtil::getDB
			).thenReturn(
				db
			);

			Mockito.when(
				db.getDBType()
			).thenReturn(
				dbType
			);

			portalInstancePoolMockedStatic.when(
				PortalInstancePool::getCompanyIds
			).thenReturn(
				new long[runtime.availableProcessors() + 2]
			);

			propsUtilMockedStatic.when(
				() -> PropsUtil.get("jdbc.default.maximumPoolSize")
			).thenReturn(
				String.valueOf(maximumPoolSize)
			);

			BaseDBProcess baseDBProcess = new BaseDBProcess() {
			};

			ReflectionTestUtil.setFieldValue(
				BaseDBProcess.class, "_fixedThreadPoolSize",
				new AtomicInteger(0));

			int fixedThreadPoolSize = ReflectionTestUtil.invoke(
				baseDBProcess, "_getFixedThreadPoolSize", new Class<?>[0]);

			Assert.assertEquals(
				expectedFixedThreadPoolSize, fixedThreadPoolSize);
		}
	}

}