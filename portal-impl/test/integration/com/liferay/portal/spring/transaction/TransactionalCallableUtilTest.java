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

package com.liferay.portal.spring.transaction;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.persistence.ClassNameUtil;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;

import java.util.concurrent.Callable;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.transaction.interceptor.TransactionAttribute;

/**
 * @author Shuyang Zhou
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class TransactionalCallableUtilTest {

	@Test
	public void testCommit() throws Throwable {
		final long classNameId = CounterLocalServiceUtil.increment();
		final String classNameValue = PwdGenerator.getPassword();

		try {
			TransactionalCallableUtil.call(
				_transactionAttribute, new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					ClassName className = ClassNameUtil.create(classNameId);

					className.setValue(classNameValue);

					ClassNameUtil.update(className);

					return null;
				}

			});

			ClassName className = ClassNameLocalServiceUtil.fetchClassName(
				classNameId);

			Assert.assertNotNull(className);
			Assert.assertEquals(classNameValue, className.getClassName());
		}
		finally {
			ClassNameLocalServiceUtil.deleteClassName(classNameId);
		}
	}

	@Test
	public void testRollback() throws Throwable {
		final long classNameId = CounterLocalServiceUtil.increment();
		final Exception exception = new Exception();

		try {
			TransactionalCallableUtil.call(
				_transactionAttribute, new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					ClassName className = ClassNameUtil.create(classNameId);

					className.setValue(PwdGenerator.getPassword());

					ClassNameUtil.update(className);

					throw exception;
				}

			});

			Assert.fail();
		}
		catch (Throwable throwable) {
			Assert.assertSame(exception, throwable);

			ClassName className = ClassNameLocalServiceUtil.fetchClassName(
				classNameId);

			Assert.assertNull(className);
		}
		finally {
			try {
				ClassNameLocalServiceUtil.deleteClassName(classNameId);
			}
			catch (Exception e) {
			}
		}
	}

	private final TransactionAttribute _transactionAttribute =
		TransactionAttributeBuilder.build(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

}