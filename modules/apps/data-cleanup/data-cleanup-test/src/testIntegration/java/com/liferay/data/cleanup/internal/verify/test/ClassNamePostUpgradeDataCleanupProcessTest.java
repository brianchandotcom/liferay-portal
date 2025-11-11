/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.verify.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.module.util.BundleUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.lpkg.deployer.LPKGDeployer;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class ClassNamePostUpgradeDataCleanupProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_connection = DataAccess.getConnection();

		_dbInspector = new DBInspector(_connection);
	}

	@AfterClass
	public static void tearDownClass() {
		DataAccess.cleanUp(_connection);
	}

	@Test
	public void testFoundLiferayClassNameWithDashIsNotDeleted()
		throws Exception {

		AtomicReference<ClassName> classNameAtomicReference =
			new AtomicReference<>();
		String classNameValue =
			ObjectDefinition.class.getName() + StringPool.DASH +
				RandomTestUtil.randomString(4);

		_test(
			logCapture -> {
				List<String> messages = logCapture.getMessages();

				Assert.assertTrue(messages.toString(), messages.isEmpty());

				ClassName className = _classNameLocalService.fetchClassName(
					classNameValue);

				Assert.assertEquals(classNameValue, className.getValue());
			},
			() -> {
				if (classNameAtomicReference.get() != null) {
					_classNameLocalService.deleteClassName(
						classNameAtomicReference.get());
				}
			},
			() -> classNameAtomicReference.set(
				_classNameLocalService.addClassName(classNameValue)));
	}

	@Test
	public void testFoundLiferayClassNameWithPoundIsNotDeleted()
		throws Exception {

		AtomicReference<ClassName> classNameAtomicReference =
			new AtomicReference<>();
		String classNameValue =
			ObjectDefinition.class.getName() + StringPool.POUND +
				RandomTestUtil.randomString(4);

		_test(
			logCapture -> {
				List<String> messages = logCapture.getMessages();

				Assert.assertTrue(messages.toString(), messages.isEmpty());

				ClassName className = _classNameLocalService.fetchClassName(
					classNameValue);

				Assert.assertEquals(classNameValue, className.getValue());
			},
			() -> {
				if (classNameAtomicReference.get() != null) {
					_classNameLocalService.deleteClassName(
						classNameAtomicReference.get());
				}
			},
			() -> classNameAtomicReference.set(
				_classNameLocalService.addClassName(classNameValue)));
	}

	@Test
	public void testNonliferayClassNameIsNotDeleted() throws Exception {
		AtomicReference<ClassName> classNameAtomicReference =
			new AtomicReference<>();
		String classNameValue =
			"com.test.not.liferay." + RandomTestUtil.randomString();

		_test(
			logCapture -> {
				List<String> messages = logCapture.getMessages();

				Assert.assertTrue(messages.toString(), messages.isEmpty());

				ClassName className = _classNameLocalService.fetchClassName(
					classNameValue);

				Assert.assertEquals(classNameValue, className.getValue());
			},
			() -> {
				if (classNameAtomicReference.get() != null) {
					_classNameLocalService.deleteClassName(
						classNameAtomicReference.get());
				}
			},
			() -> classNameAtomicReference.set(
				_classNameLocalService.addClassName(classNameValue)));
	}

	@Test
	public void testNotFoundLiferayClassNameUnusedIsDeleted() throws Exception {
		AtomicReference<ClassName> classNameAtomicReference =
			new AtomicReference<>();
		String classNameValue =
			"com.liferay.test." + RandomTestUtil.randomString();

		_test(
			logCapture -> {
				List<String> messages = logCapture.getMessages();

				Assert.assertTrue(
					messages.toString(),
					messages.contains(
						"ClassName " + classNameValue +
							" has been deleted because it is not in use"));

				ClassName className = _classNameLocalService.fetchClassName(
					classNameValue);

				Assert.assertEquals(StringPool.BLANK, className.getValue());
			},
			() -> {
				if (classNameAtomicReference.get() != null) {
					_classNameLocalService.deleteClassName(
						classNameAtomicReference.get());
				}
			},
			() -> classNameAtomicReference.set(
				_classNameLocalService.addClassName(classNameValue)));
	}

	@Test
	public void testNotFoundLiferayClassNameUsedInResourcePermissionIsNotDeleted()
		throws Exception {

		AtomicReference<ClassName> classNameAtomicReference =
			new AtomicReference<>();
		String classNameValue =
			"com.liferay.test." + RandomTestUtil.randomString();
		long resourcePermissionId = RandomTestUtil.nextLong();

		_test(
			logCapture -> {
				List<String> messages = logCapture.getMessages();

				Assert.assertTrue(
					messages.toString(),
					messages.contains(
						StringBundler.concat(
							"ClassName ", classNameValue,
							" has not been found but is referenced in the ",
							"next tables: ",
							_dbInspector.normalizeName("ResourcePermission"))));

				ClassName className = _classNameLocalService.fetchClassName(
					classNameValue);

				Assert.assertEquals(classNameValue, className.getValue());
			},
			() -> {
				if (classNameAtomicReference.get() != null) {
					_classNameLocalService.deleteClassName(
						classNameAtomicReference.get());
				}

				try (PreparedStatement preparedStatement =
						_connection.prepareStatement(
							"delete from ResourcePermission where " +
								"resourcePermissionId = ?")) {

					preparedStatement.setLong(1, resourcePermissionId);
					preparedStatement.executeUpdate();
				}
			},
			() -> {
				ClassName className = _classNameLocalService.addClassName(
					classNameValue);

				classNameAtomicReference.set(className);

				try (PreparedStatement preparedStatement =
						_connection.prepareStatement(
							"insert into ResourcePermission (mvccVersion, " +
								"ctCollectionId, resourcePermissionId, name) " +
									"values (0, 0, ?, ?)")) {

					preparedStatement.setLong(1, resourcePermissionId);
					preparedStatement.setString(2, classNameValue);

					preparedStatement.executeUpdate();
				}
			});
	}

	@Test
	public void testNotFoundLiferayClassNameUsedIsNotDeleted()
		throws Exception {

		AtomicReference<ClassName> classNameAtomicReference =
			new AtomicReference<>();
		String classNameValue =
			"com.liferay.test." + RandomTestUtil.randomString();
		long addressId = RandomTestUtil.nextLong();

		_test(
			logCapture -> {
				List<String> messages = logCapture.getMessages();

				Assert.assertTrue(
					messages.toString(),
					messages.contains(
						StringBundler.concat(
							"ClassName ", classNameValue,
							" has not been found but is referenced in the ",
							"next tables: ",
							_dbInspector.normalizeName("Address"))));

				ClassName className = _classNameLocalService.fetchClassName(
					classNameValue);

				Assert.assertEquals(classNameValue, className.getValue());
			},
			() -> {
				if (classNameAtomicReference.get() != null) {
					_classNameLocalService.deleteClassName(
						classNameAtomicReference.get());
				}

				try (PreparedStatement preparedStatement =
						_connection.prepareStatement(
							"delete from Address where addressId = ?")) {

					preparedStatement.setLong(1, addressId);
					preparedStatement.executeUpdate();
				}
			},
			() -> {
				ClassName className = _classNameLocalService.addClassName(
					classNameValue);

				classNameAtomicReference.set(className);

				try (PreparedStatement preparedStatement =
						_connection.prepareStatement(
							"insert into Address (mvccVersion, " +
								"ctCollectionId, addressId, classNameId) " +
									"values (0, 0, ?, ?)")) {

					preparedStatement.setLong(1, addressId);
					preparedStatement.setLong(2, className.getClassNameId());

					preparedStatement.executeUpdate();
				}
			});
	}

	@Test
	public void testVerifyDoesNotRunIfModulesNotStarted() throws Exception {
		AtomicReference<Bundle> bundleAtomicReference = new AtomicReference<>();

		_test(
			logCapture -> {
				List<String> messages = logCapture.getMessages();

				Assert.assertTrue(
					messages.toString(),
					messages.contains(
						"ClassNameDataCleanupVerifyProcess cannot be " +
							"executed because there are modules with " +
								"unsatisfied references"));
			},
			() -> {
				Bundle bundle = bundleAtomicReference.get();

				if (bundle != null) {
					_installBundle(bundle);
				}
			},
			() -> bundleAtomicReference.set(_uninstallBundle()));
	}

	private void _installBundle(Bundle bundle) throws Exception {
		Bundle currentBundle = FrameworkUtil.getBundle(
			ClassNamePostUpgradeDataCleanupProcessTest.class);

		BundleContext bundleContext = currentBundle.getBundleContext();

		com.liferay.osgi.util.BundleUtil.installBundle(
			bundleContext, _lpkgDeployer, bundle.getLocation(), 1);

		List<Bundle> bundlesToRefresh = new ArrayList<>();

		bundlesToRefresh.add(bundle);

		com.liferay.osgi.util.BundleUtil.refreshBundles(
			bundleContext, bundlesToRefresh);
	}

	private void _runPostUpgradeDataCleanUpVerifyProcess() throws Exception {
		Bundle bundle = BundleUtil.getBundle(
			SystemBundleUtil.getBundleContext(), "com.liferay.data.cleanup");

		Class<?> postUpgradeDataCleanupProcessClass = bundle.loadClass(
			_POST_UPGRADE_DATA_CLEANUP_PROCESS_CLASS_NAME);

		Constructor<?> constructor =
			postUpgradeDataCleanupProcessClass.getConstructor(
				ClassNameLocalService.class, Connection.class);

		Object object = constructor.newInstance(
			_classNameLocalService, _connection);

		Method method = postUpgradeDataCleanupProcessClass.getMethod("cleanUp");

		method.invoke(object);
	}

	private void _test(
			UnsafeConsumer<LogCapture, Exception> assertUnsafeConsumer,
			UnsafeRunnable<Exception> cleanUpDataUnsafeRunnable,
			UnsafeRunnable<Exception> initializeDataUnsafeRunnable)
		throws Exception {

		long classNameCount = _classNameLocalService.getClassNamesCount();

		initializeDataUnsafeRunnable.run();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_POST_UPGRADE_DATA_CLEANUP_PROCESS_CLASS_NAME,
				LoggerTestUtil.INFO)) {

			_runPostUpgradeDataCleanUpVerifyProcess();

			assertUnsafeConsumer.accept(logCapture);
		}
		finally {
			cleanUpDataUnsafeRunnable.run();

			Assert.assertEquals(
				classNameCount, _classNameLocalService.getClassNamesCount());
		}
	}

	private Bundle _uninstallBundle() throws Exception {
		Bundle currentBundle = FrameworkUtil.getBundle(
			ClassNamePostUpgradeDataCleanupProcessTest.class);

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

	private static final String _POST_UPGRADE_DATA_CLEANUP_PROCESS_CLASS_NAME =
		"com.liferay.data.cleanup.internal.verify." +
			"ClassNamePostUpgradeDataCleanupProcess";

	private static Connection _connection;
	private static DBInspector _dbInspector;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private LPKGDeployer _lpkgDeployer;

}