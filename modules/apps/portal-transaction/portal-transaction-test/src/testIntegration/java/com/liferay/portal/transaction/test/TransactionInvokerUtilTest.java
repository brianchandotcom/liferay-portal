/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.transaction.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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
public class TransactionInvokerUtilTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		TransactionConfig.Builder builder = new TransactionConfig.Builder();

		builder.setPropagation(Propagation.REQUIRED);
		builder.setRollbackForClasses(Exception.class);

		_transactionConfig = builder.build();
	}

	@Test
	public void testCommit() throws Throwable {
		final long classNameId = _counterLocalService.increment();
		final String classNameValue = PwdGenerator.getPassword();

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				(Callable<Void>)() -> {
					ClassName className = _classNamePersistence.create(
						classNameId);

					className.setValue(classNameValue);

					_classNamePersistence.update(className);

					return null;
				});

			ClassName className = _classNameLocalService.fetchClassName(
				classNameId);

			Assert.assertNotNull(className);
			Assert.assertEquals(classNameValue, className.getClassName());
		}
		finally {
			_classNameLocalService.deleteClassName(classNameId);
		}
	}

	@Test
	public void testRollback() {
		final long classNameId = _counterLocalService.increment();
		final Exception exception1 = new Exception();

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				(Callable<Void>)() -> {
					ClassName className = _classNamePersistence.create(
						classNameId);

					className.setValue(PwdGenerator.getPassword());

					_classNamePersistence.update(className);

					throw exception1;
				});

			Assert.fail();
		}
		catch (Throwable throwable) {
			Assert.assertSame(exception1, throwable);

			ClassName className = _classNameLocalService.fetchClassName(
				classNameId);

			Assert.assertNull(className);
		}
		finally {
			try {
				_classNameLocalService.deleteClassName(classNameId);
			}
			catch (Exception exception2) {
			}
		}
	}

	private static TransactionConfig _transactionConfig;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private ClassNamePersistence _classNamePersistence;

	@Inject
	private CounterLocalService _counterLocalService;

}