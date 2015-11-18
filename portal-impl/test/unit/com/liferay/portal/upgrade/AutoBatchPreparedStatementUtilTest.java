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

package com.liferay.portal.upgrade;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.SwappableSecurityManager;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.rule.NewEnvTestRule;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class AutoBatchPreparedStatementUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, NewEnvTestRule.INSTANCE);

	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testCINITFailure() throws ClassNotFoundException {
		final NoSuchMethodException nsme = new NoSuchMethodException();
		final AtomicInteger counter = new AtomicInteger();

		try (SwappableSecurityManager swappableSecurityManager =
				new SwappableSecurityManager() {

					@Override
					public void checkPackageAccess(String pkg) {
						if (pkg.equals("java.sql") &&
							(counter.getAndIncrement() == 1)) {

							ReflectionUtil.throwException(nsme);
						}
					}

				}) {

			swappableSecurityManager.install();

			Class.forName(AutoBatchPreparedStatementUtil.class.getName());
		}
		catch (ExceptionInInitializerError eiie) {
			Assert.assertSame(nsme, eiie.getCause());
		}
	}

	@Test
	public void testConstructor() throws ReflectiveOperationException {
		Constructor<AutoBatchPreparedStatementUtil> constructor =
			AutoBatchPreparedStatementUtil.class.getDeclaredConstructor();

		Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));

		constructor.setAccessible(true);

		constructor.newInstance();
	}

	@Test
	public void testNotSupportBatchUpdates() throws Exception {
		PreparedStatementInvocationHandler preparedStatementInvocationHandler =
			new PreparedStatementInvocationHandler(false);

		PreparedStatement preparedStatement =
			AutoBatchPreparedStatementUtil.autoBatch(
				(PreparedStatement)ProxyUtil.newProxyInstance(
					ClassLoader.getSystemClassLoader(),
					new Class<?>[] {PreparedStatement.class},
					preparedStatementInvocationHandler));

		List<Method> methods = preparedStatementInvocationHandler.getMethods();

		Assert.assertTrue(methods.toString(), methods.isEmpty());

		// Calling addBatch fallbacks to executeUpdate

		preparedStatement.addBatch();

		Assert.assertEquals(methods.toString(), 1, methods.size());
		Assert.assertEquals(
			PreparedStatement.class.getMethod("executeUpdate"),
			methods.remove(0));

		// Calling executeBatch does nothing

		Assert.assertArrayEquals(new int[0], preparedStatement.executeBatch());
		Assert.assertTrue(methods.toString(), methods.isEmpty());

		// Other methods like execute pass through

		preparedStatement.execute();

		Assert.assertEquals(methods.toString(), 1, methods.size());
		Assert.assertEquals(
			PreparedStatement.class.getMethod("execute"), methods.remove(0));
	}

	@Test
	public void testSupportBatchUpdates() throws Exception {
		PreparedStatementInvocationHandler preparedStatementInvocationHandler =
			new PreparedStatementInvocationHandler(true);

		PreparedStatement preparedStatement =
			AutoBatchPreparedStatementUtil.autoBatch(
				(PreparedStatement)ProxyUtil.newProxyInstance(
					ClassLoader.getSystemClassLoader(),
					new Class<?>[] {PreparedStatement.class},
					preparedStatementInvocationHandler));

		InvocationHandler invocationHandler = ProxyUtil.getInvocationHandler(
			preparedStatement);

		Assert.assertEquals(
			0, ReflectionTestUtil.getFieldValue(invocationHandler, "_count"));

		List<Method> methods = preparedStatementInvocationHandler.getMethods();

		Assert.assertTrue(methods.toString(), methods.isEmpty());

		int hibernateJDBCBatchSize = PropsValues.HIBERNATE_JDBC_BATCH_SIZE;

		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "HIBERNATE_JDBC_BATCH_SIZE", 2);

		try {

			// Protection for executing empty batch

			Assert.assertArrayEquals(
				new int[0], preparedStatement.executeBatch());
			Assert.assertTrue(methods.toString(), methods.isEmpty());
			Assert.assertEquals(
				0,
				ReflectionTestUtil.getFieldValue(invocationHandler, "_count"));

			// Calling addBatch passes through when within the Hibernate JDBC
			// batch size

			preparedStatement.addBatch();

			Assert.assertEquals(methods.toString(), 1, methods.size());
			Assert.assertEquals(
				PreparedStatement.class.getMethod("addBatch"),
				methods.remove(0));
			Assert.assertEquals(
				1,
				ReflectionTestUtil.getFieldValue(invocationHandler, "_count"));

			// Calling addBatch passes through and triggers executeBatch when
			// exceeding the Hibernate JDBC batch size

			preparedStatement.addBatch();

			Assert.assertEquals(methods.toString(), 2, methods.size());
			Assert.assertEquals(
				PreparedStatement.class.getMethod("addBatch"),
				methods.remove(0));
			Assert.assertEquals(
				PreparedStatement.class.getMethod("executeBatch"),
				methods.remove(0));
			Assert.assertEquals(
				0,
				ReflectionTestUtil.getFieldValue(invocationHandler, "_count"));

			// Calling addBatch passes through when within the Hibernate JDBC
			// batch size

			preparedStatement.addBatch();

			Assert.assertEquals(methods.toString(), 1, methods.size());
			Assert.assertEquals(
				PreparedStatement.class.getMethod("addBatch"),
				methods.remove(0));
			Assert.assertEquals(
				1,
				ReflectionTestUtil.getFieldValue(invocationHandler, "_count"));

			// Calling executeBatch passes through when batch is not empty

			preparedStatement.executeBatch();

			Assert.assertEquals(methods.toString(), 1, methods.size());
			Assert.assertEquals(
				PreparedStatement.class.getMethod("executeBatch"),
				methods.remove(0));
			Assert.assertEquals(
				0,
				ReflectionTestUtil.getFieldValue(invocationHandler, "_count"));

			// Other methods like execute pass through

			preparedStatement.execute();

			Assert.assertEquals(methods.toString(), 1, methods.size());
			Assert.assertEquals(
				PreparedStatement.class.getMethod("execute"),
				methods.remove(0));
			Assert.assertEquals(
				0,
				ReflectionTestUtil.getFieldValue(invocationHandler, "_count"));
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				PropsValues.class, "HIBERNATE_JDBC_BATCH_SIZE",
				hibernateJDBCBatchSize);
		}
	}

	private static class ConnectionInvocationHandler
		implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws NoSuchMethodException {

			if (method.equals(Connection.class.getMethod("getMetaData"))) {
				return ProxyUtil.newProxyInstance(
					ClassLoader.getSystemClassLoader(),
					new Class<?>[] {DatabaseMetaData.class},
					new DatabaseMetaDataInvocationHandler(
						_supportBatchUpdates));
			}

			throw new UnsupportedOperationException();
		}

		private ConnectionInvocationHandler(boolean supportBatchUpdates) {
			_supportBatchUpdates = supportBatchUpdates;
		}

		private final boolean _supportBatchUpdates;

	}

	private static class DatabaseMetaDataInvocationHandler
		implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws NoSuchMethodException {

			if (method.equals(
					DatabaseMetaData.class.getMethod("supportsBatchUpdates"))) {

				return _supportBatchUpdates;
			}

			throw new UnsupportedOperationException();
		}

		private DatabaseMetaDataInvocationHandler(boolean supportBatchUpdates) {
			_supportBatchUpdates = supportBatchUpdates;
		}

		private final boolean _supportBatchUpdates;

	}

	private static class PreparedStatementInvocationHandler
		implements InvocationHandler {

		public List<Method> getMethods() {
			return _methods;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws NoSuchMethodException {

			if (method.equals(
					PreparedStatement.class.getMethod("getConnection"))) {

				return ProxyUtil.newProxyInstance(
					ClassLoader.getSystemClassLoader(),
					new Class<?>[] {Connection.class},
					new ConnectionInvocationHandler(_supportBatchUpdates));
			}

			_methods.add(method);

			if (method.equals(PreparedStatement.class.getMethod("addBatch"))) {
				return null;
			}

			if (method.equals(PreparedStatement.class.getMethod("execute"))) {
				return false;
			}

			if (method.equals(
					PreparedStatement.class.getMethod("executeBatch"))) {

				return new int[0];
			}

			if (method.equals(
					PreparedStatement.class.getMethod("executeUpdate"))) {

				return 0;
			}

			throw new UnsupportedOperationException();
		}

		private PreparedStatementInvocationHandler(
			boolean supportBatchUpdates) {

			_supportBatchUpdates = supportBatchUpdates;
		}

		private final List<Method> _methods = new ArrayList<>();
		private final boolean _supportBatchUpdates;

	}

}