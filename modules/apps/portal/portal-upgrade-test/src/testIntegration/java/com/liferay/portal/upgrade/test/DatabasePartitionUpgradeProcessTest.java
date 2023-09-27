/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.db.partition.DBPartitionUtil;
import com.liferay.portal.kernel.db.partition.DBPartition;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import java.util.concurrent.FutureTask;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class DatabasePartitionUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		_originalDatabasePartitionEnabled = PropsUtil.get(
			"database.partition.enabled");
		_originalDatabasePartitionThreadPoolEnabled =
			ReflectionTestUtil.getFieldValue(
				DBPartitionUtil.class,
				"_DATABASE_PARTITION_THREAD_POOL_ENABLED");
	}

	@AfterClass
	public static void tearDownClass() {
		ReflectionTestUtil.setFieldValue(
			DBPartitionUtil.class, "_DATABASE_PARTITION_THREAD_POOL_ENABLED",
			_originalDatabasePartitionThreadPoolEnabled);

		PropsUtil.set(
			"database.partition.enabled", _originalDatabasePartitionEnabled);
	}

	@Test
	public void testUpgradeWithDatabasePartitionDisabled()
		throws UpgradeException {

		PropsUtil.set("database.partition.enabled", "false");

		ReflectionTestUtil.setFieldValue(
			DBPartitionUtil.class, "_DATABASE_PARTITION_THREAD_POOL_ENABLED",
			true);

		UpgradeProcess upgradeProcess = new AssertConnectionUpgradeProcess();

		upgradeProcess.upgrade();
	}

	@Test
	public void testUpgradeWithDatabasePartitionEnabled()
		throws UpgradeException {

		PropsUtil.set("database.partition.enabled", "true");

		ReflectionTestUtil.setFieldValue(
			DBPartitionUtil.class, "_DATABASE_PARTITION_THREAD_POOL_ENABLED",
			true);

		UpgradeProcess upgradeProcess = new AssertConnectionUpgradeProcess();

		upgradeProcess.upgrade();
	}

	private static String _originalDatabasePartitionEnabled;
	private static boolean _originalDatabasePartitionThreadPoolEnabled;

	private class AssertConnectionUpgradeProcess extends DummyUpgradeProcess {

		@Override
		protected void process(UnsafeConsumer<Long, Exception> unsafeConsumer)
			throws Exception {

			if (DBPartition.isPartitionEnabled()) {
				Assert.assertNotSame(_getConnection(), _getConnection());
			}
			else {
				Assert.assertSame(_getConnection(), _getConnection());
			}
		}

		private Connection _getConnection() throws Exception {
			FutureTask<Connection> futureTask = new FutureTask<>(
				() -> {
					DatabaseMetaData databaseMetaData =
						connection.getMetaData();

					return databaseMetaData.getConnection();
				});

			Thread thread = new Thread(futureTask);

			thread.start();

			return futureTask.get();
		}

	}

}