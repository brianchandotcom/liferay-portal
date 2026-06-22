/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.verify.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.module.util.BundleUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import java.sql.Connection;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;

/**
 * @author Jorge Avalos
 */
@RunWith(Arquillian.class)
public class PostUpgradeDataCleanupVerifyProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_connection = DataAccess.getConnection();
	}

	@AfterClass
	public static void tearDownClass() {
		DataAccess.cleanUp(_connection);
	}

	@Test
	public void testGetPostUpgradeDataCleanupProcesses() throws Exception {
		Bundle bundle = BundleUtil.getBundle(
			SystemBundleUtil.getBundleContext(),
			"com.liferay.data.cleanup.impl");

		Class<?> postUpgradeDataCleanupVerifyProcessClass = bundle.loadClass(
			"com.liferay.data.cleanup.internal.verify." +
				"PostUpgradeDataCleanupVerifyProcess");

		Constructor<?> constructor =
			postUpgradeDataCleanupVerifyProcessClass.getDeclaredConstructor();

		Object postUpgradeDataCleanupVerifyProcess = constructor.newInstance();

		ReflectionTestUtil.setFieldValue(
			postUpgradeDataCleanupVerifyProcess, "connection", _connection);

		try (AutoCloseable autoCloseable1 =
				ReflectionTestUtil.setFieldValueWithAutoCloseable(
					postUpgradeDataCleanupVerifyProcessClass,
					"_indexInformationSnapshot",
					new Snapshot<>(
						PostUpgradeDataCleanupVerifyProcessTest.class,
						Object.class));
			AutoCloseable autoCloseable2 =
				ReflectionTestUtil.setFieldValueWithAutoCloseable(
					postUpgradeDataCleanupVerifyProcessClass,
					"_indexNameBuilderSnapshot",
					new Snapshot<>(
						PostUpgradeDataCleanupVerifyProcessTest.class,
						Object.class));
			LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.data.cleanup.internal.verify." +
					"PostUpgradeDataCleanupVerifyProcess",
				LoggerTestUtil.WARN)) {

			Method method =
				postUpgradeDataCleanupVerifyProcessClass.getDeclaredMethod(
					"_getPostUpgradeDataCleanupProcesses");

			method.setAccessible(true);

			List<?> postUpgradeDataCleanupProcesses = (List<?>)method.invoke(
				postUpgradeDataCleanupVerifyProcess);

			Assert.assertEquals(
				postUpgradeDataCleanupProcesses.toString(), 4,
				postUpgradeDataCleanupProcesses.size());

			for (Object postUpgradeDataCleanupProcess :
					postUpgradeDataCleanupProcesses) {

				Class<?> clazz = postUpgradeDataCleanupProcess.getClass();

				String simpleName = clazz.getSimpleName();

				Assert.assertFalse(
					simpleName.equals(
						"SearchIndexPostUpgradeDataCleanupProcess"));
			}

			List<String> messages = logCapture.getMessages();

			String message = messages.get(0);

			Assert.assertEquals(messages.toString(), 1, messages.size());

			Assert.assertTrue(
				message.contains(
					"Skipping search index cleanup: search engine " +
						"unavailable"));
		}
	}

	private static Connection _connection;

}