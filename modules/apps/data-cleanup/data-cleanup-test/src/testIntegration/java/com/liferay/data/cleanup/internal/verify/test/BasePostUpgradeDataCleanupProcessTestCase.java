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
import com.liferay.portal.lpkg.deployer.LPKGDeployer;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

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

	protected void installBundle(Bundle bundle) throws Exception {
		Bundle currentBundle = FrameworkUtil.getBundle(
			BasePostUpgradeDataCleanupProcessTestCase.class);

		BundleContext bundleContext = currentBundle.getBundleContext();

		com.liferay.osgi.util.BundleUtil.installBundle(
			bundleContext, _lpkgDeployer, bundle.getLocation(), 1);

		List<Bundle> bundlesToRefresh = new ArrayList<>();

		bundlesToRefresh.add(bundle);

		com.liferay.osgi.util.BundleUtil.refreshBundles(
			bundleContext, bundlesToRefresh);
	}

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

	protected Bundle uninstallBundle() throws Exception {
		Bundle currentBundle = FrameworkUtil.getBundle(
			BasePostUpgradeDataCleanupProcessTestCase.class);

		BundleContext bundleContext = currentBundle.getBundleContext();

		for (Bundle bundle : bundleContext.getBundles()) {
			if (Objects.equals(
					bundle.getSymbolicName(),
					"com.liferay.dynamic.data.mapping.service") &&
				(bundle.getState() == Bundle.ACTIVE)) {

				bundle.uninstall();

				List<Bundle> bundlesToRefresh = new ArrayList<>();

				bundlesToRefresh.add(bundle);

				com.liferay.osgi.util.BundleUtil.refreshBundles(
					bundleContext, bundlesToRefresh);

				return bundle;
			}
		}

		return null;
	}

	protected static Connection connection;
	protected static DBInspector dbInspector;

	private void _runPostUpgradeDataCleanUpVerifyProcess() throws Exception {
		Bundle bundle = BundleUtil.getBundle(
			SystemBundleUtil.getBundleContext(), "com.liferay.data.cleanup");

		Class<?> postUpgradeDataCleanupProcessClass = bundle.loadClass(
			getPostUpgradeDataCleanupProcessClassName());

		Method method = postUpgradeDataCleanupProcessClass.getMethod("cleanUp");

		Constructor<?> constructor =
			postUpgradeDataCleanupProcessClass.getConstructor(
				getPostUpgradeDataCleanupProcessArgumentTypes());

		Object object = constructor.newInstance(
			getPostUpgradeDataCleanupProcessArguments());

		method.invoke(object);
	}

	@Inject
	private LPKGDeployer _lpkgDeployer;

}