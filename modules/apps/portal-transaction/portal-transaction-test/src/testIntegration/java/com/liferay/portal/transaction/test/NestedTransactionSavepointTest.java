/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.transaction.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;

import java.util.concurrent.Callable;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(Arquillian.class)
public class NestedTransactionSavepointTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		_nestedTransactionConfig = TransactionConfig.Factory.create(
			Propagation.NESTED, new Class<?>[] {Exception.class});
		_requiredTransactionConfig = TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});
	}

	@Test
	public void testNestedCommitRolledBackWithOuter() throws Throwable {
		long nestedClassNameId = _counterLocalService.increment();

		Exception exception = new Exception();

		try {
			TransactionInvokerUtil.invoke(
				_requiredTransactionConfig,
				(Callable<Void>)() -> {
					try {
						TransactionInvokerUtil.invoke(
							_nestedTransactionConfig,
							(Callable<Void>)() -> {
								_addClassName(nestedClassNameId);

								return null;
							});
					}
					catch (Throwable throwable) {
						throw new Exception(throwable);
					}

					throw exception;
				});

			Assert.fail("Outer rollback did not rethrow");
		}
		catch (Throwable throwable) {
			Assert.assertSame(exception, throwable);
		}

		// The savepoint released, but the outer transaction rolled back, so
		// the item is gone from both the database and the entity cache.

		Assert.assertNull(
			_classNameLocalService.fetchClassName(nestedClassNameId));
	}

	@Test
	public void testNestedRollbackKeepsOuterAndDropsNestedItem()
		throws Throwable {

		long outerPreClassNameId = _counterLocalService.increment();
		long nestedClassNameId = _counterLocalService.increment();
		long outerPostClassNameId = _counterLocalService.increment();

		Exception exception = new Exception();

		try {
			TransactionInvokerUtil.invoke(
				_requiredTransactionConfig,
				(Callable<Void>)() -> {
					_addClassName(outerPreClassNameId);

					Connection connection = _getCurrentConnection();

					Assert.assertNotNull(connection);

					try {
						TransactionInvokerUtil.invoke(
							_nestedTransactionConfig,
							(Callable<Void>)() -> {
								_addClassName(nestedClassNameId);

								// Same connection as the outer transaction,
								// unlike REQUIRES_NEW.

								Assert.assertSame(
									connection, _getCurrentConnection());

								throw exception;
							});

						Assert.fail("Nested rollback did not rethrow");
					}
					catch (Throwable throwable) {
						Assert.assertSame(exception, throwable);
					}

					// The outer transaction stays usable after the nested
					// rollback.

					_addClassName(outerPostClassNameId);

					return null;
				});

			// The outer transaction committed its pre-nested and post-nested
			// work.

			Assert.assertNotNull(
				_classNameLocalService.fetchClassName(outerPreClassNameId));
			Assert.assertNotNull(
				_classNameLocalService.fetchClassName(outerPostClassNameId));

			// The nested item rolled back with its savepoint.

			Assert.assertNull(
				_classNameLocalService.fetchClassName(nestedClassNameId));
		}
		finally {

			// The nested item rolled back, so only the outer items exist.

			_classNameLocalService.deleteClassName(outerPreClassNameId);
			_classNameLocalService.deleteClassName(outerPostClassNameId);
		}
	}

	@Test
	public void testNestedSuccessCommitsWithOuter() throws Throwable {
		long outerPreClassNameId = _counterLocalService.increment();
		long nestedClassNameId = _counterLocalService.increment();
		long outerPostClassNameId = _counterLocalService.increment();

		try {
			TransactionInvokerUtil.invoke(
				_requiredTransactionConfig,
				(Callable<Void>)() -> {
					_addClassName(outerPreClassNameId);

					Connection connection = _getCurrentConnection();

					Assert.assertNotNull(connection);

					try {
						TransactionInvokerUtil.invoke(
							_nestedTransactionConfig,
							(Callable<Void>)() -> {
								_addClassName(nestedClassNameId);

								// Same connection as the outer transaction,
								// unlike REQUIRES_NEW.

								Assert.assertSame(
									connection, _getCurrentConnection());

								return null;
							});
					}
					catch (Throwable throwable) {
						throw new Exception(throwable);
					}

					_addClassName(outerPostClassNameId);

					return null;
				});

			Assert.assertNotNull(
				_classNameLocalService.fetchClassName(outerPreClassNameId));
			Assert.assertNotNull(
				_classNameLocalService.fetchClassName(nestedClassNameId));
			Assert.assertNotNull(
				_classNameLocalService.fetchClassName(outerPostClassNameId));
		}
		finally {
			_classNameLocalService.deleteClassName(outerPreClassNameId);
			_classNameLocalService.deleteClassName(nestedClassNameId);
			_classNameLocalService.deleteClassName(outerPostClassNameId);
		}
	}

	@Test
	public void testNestedWithoutOuterTransaction() throws Throwable {
		long committedClassNameId = _counterLocalService.increment();
		long rolledBackClassNameId = _counterLocalService.increment();

		Exception exception = new Exception();

		try {

			// With no outer transaction, a nested transaction behaves like
			// REQUIRED and commits as its own transaction.

			TransactionInvokerUtil.invoke(
				_nestedTransactionConfig,
				(Callable<Void>)() -> {
					_addClassName(committedClassNameId);

					return null;
				});

			Assert.assertNotNull(
				_classNameLocalService.fetchClassName(committedClassNameId));

			// It also rolls back as its own transaction.

			try {
				TransactionInvokerUtil.invoke(
					_nestedTransactionConfig,
					(Callable<Void>)() -> {
						_addClassName(rolledBackClassNameId);

						throw exception;
					});

				Assert.fail("Nested rollback did not rethrow");
			}
			catch (Throwable throwable) {
				Assert.assertSame(exception, throwable);
			}

			Assert.assertNull(
				_classNameLocalService.fetchClassName(rolledBackClassNameId));
		}
		finally {
			_classNameLocalService.deleteClassName(committedClassNameId);
		}
	}

	private void _addClassName(long classNameId) {
		ClassName className = _classNamePersistence.create(classNameId);

		className.setValue(PwdGenerator.getPassword());

		_classNamePersistence.update(className);
	}

	private Connection _getCurrentConnection() {
		Connection[] connection = new Connection[1];

		Session session = _classNamePersistence.getCurrentSession();

		session.apply(jdbcConnection -> connection[0] = jdbcConnection);

		return connection[0];
	}

	private static TransactionConfig _nestedTransactionConfig;
	private static TransactionConfig _requiredTransactionConfig;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private ClassNamePersistence _classNamePersistence;

	@Inject
	private CounterLocalService _counterLocalService;

}