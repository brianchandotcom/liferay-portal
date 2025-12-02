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
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.rule.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class ClassNamePostUpgradeDataCleanupProcessTest
	extends BasePostUpgradeDataCleanupProcessTestCase {

	@Test
	public void testFoundLiferayClassNameWithDashIsNotDeleted()
		throws Exception {

		AtomicReference<ClassName> classNameAtomicReference =
			new AtomicReference<>();
		String classNameValue =
			ObjectDefinition.class.getName() + StringPool.DASH +
				RandomTestUtil.randomString(4);

		test(
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

		test(
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

		test(
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

		test(
			logCapture -> {
				List<String> messages = logCapture.getMessages();

				Assert.assertTrue(
					messages.toString(),
					messages.contains(
						StringBundler.concat(
							"Table ", dbInspector.normalizeName("ClassName_"),
							", 1 row deleted because '", classNameValue,
							"' is not defined in any deployed module and is ",
							"not in use")));

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
	public void testNotFoundLiferayClassNameUsedIsNotDeleted()
		throws Exception {

		AtomicReference<ClassName> classNameAtomicReference =
			new AtomicReference<>();
		String classNameValue =
			"com.liferay.test." + RandomTestUtil.randomString();
		long addressId = RandomTestUtil.nextLong();

		test(
			logCapture -> {
				List<String> messages = logCapture.getMessages();

				Assert.assertTrue(
					messages.toString(),
					messages.contains(
						StringBundler.concat(
							"Class name ", classNameValue,
							" is not defined in any deployed module but is ",
							"referenced in the next tables: ",
							dbInspector.normalizeName("Address"))));

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
						connection.prepareStatement(
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
						connection.prepareStatement(
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

		test(
			logCapture -> {
				List<String> messages = logCapture.getMessages();

				Assert.assertTrue(
					messages.toString(),
					messages.contains(
						"ClassNamePostUpgradeDataCleanupProcess cannot be " +
							"executed because there are modules with " +
								"unsatisfied references"));
			},
			() -> {
				Bundle bundle = bundleAtomicReference.get();

				if (bundle != null) {
					installBundle(bundle, SystemBundleUtil.getBundleContext());
				}
			},
			() -> bundleAtomicReference.set(
				uninstallBundle(
					SystemBundleUtil.getBundleContext(),
					"com.liferay.dynamic.data.mapping.service")));
	}

	@Override
	protected Object[] getPostUpgradeDataCleanupProcessArguments() {
		return new Object[] {_classNameLocalService, connection};
	}

	@Override
	protected Class<?>[] getPostUpgradeDataCleanupProcessArgumentTypes() {
		return new Class<?>[] {ClassNameLocalService.class, Connection.class};
	}

	@Override
	protected String getPostUpgradeDataCleanupProcessClassName() {
		return "com.liferay.data.cleanup.internal.verify." +
			"ClassNamePostUpgradeDataCleanupProcess";
	}

	@Override
	protected void test(
			UnsafeConsumer<LogCapture, Exception> assertUnsafeConsumer,
			UnsafeRunnable<Exception> cleanUpDataUnsafeRunnable,
			UnsafeRunnable<Exception> initializeDataUnsafeRunnable)
		throws Exception {

		long classNameCount = _classNameLocalService.getClassNamesCount();

		try {
			super.test(
				assertUnsafeConsumer, cleanUpDataUnsafeRunnable,
				initializeDataUnsafeRunnable);
		}
		finally {
			Assert.assertEquals(
				classNameCount, _classNameLocalService.getClassNamesCount());
		}
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

}