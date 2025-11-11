/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.verify.test;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.module.util.BundleUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import java.sql.Connection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;

import org.osgi.framework.Bundle;

/**
 * @author Luis Ortiz
 */
public abstract class BasePostUpgradeDataCleanupProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		connection = DataAccess.getConnection();

		dbInspector = new DBInspector(connection);
	}

	@AfterClass
	public static void tearDownClass() {
		DataAccess.cleanUp(connection);
	}

	protected abstract Object[] getPostUpgradeDataCleanupProcessArguments();

	protected abstract Class<?>[]
		getPostUpgradeDataCleanupProcessArgumentTypes();

	protected abstract String getPostUpgradeDataCleanupProcessClassName();

	protected void test(
			UnsafeConsumer<LogCapture, Exception> assertUnsafeConsumer,
			UnsafeRunnable<Exception> cleanUpDataUnsafeRunnable,
			UnsafeRunnable<Exception> initializeDataUnsafeRunnable)
		throws Exception {

		initializeDataUnsafeRunnable.run();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				getPostUpgradeDataCleanupProcessClassName(),
				LoggerTestUtil.INFO)) {

			_runPostUpgradeDataCleanUpVerifyProcess();

			assertUnsafeConsumer.accept(logCapture);
		}
		finally {
			cleanUpDataUnsafeRunnable.run();
		}
	}

	protected static Connection connection;
	protected static DBInspector dbInspector;

	private void _runPostUpgradeDataCleanUpVerifyProcess() throws Exception {
		Bundle bundle = BundleUtil.getBundle(
			SystemBundleUtil.getBundleContext(), "com.liferay.data.cleanup");

		Class<?> postUpgradeDataCleanupProcessClass = bundle.loadClass(
			getPostUpgradeDataCleanupProcessClassName());

		Constructor<?> constructor =
			postUpgradeDataCleanupProcessClass.getConstructor(
				getPostUpgradeDataCleanupProcessArgumentTypes());

		Object object = constructor.newInstance(
			getPostUpgradeDataCleanupProcessArguments());

		Method method = postUpgradeDataCleanupProcessClass.getMethod("cleanUp");

		method.invoke(object);
	}

}