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

package com.liferay.portal.kernel.dao.jdbc;

import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.nio.intraband.CancelingPortalExecutorManagerUtilAdvice;
import com.liferay.portal.kernel.nio.intraband.PortalExecutorManagerUtilAdvice;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.SwappableSecurityManager;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtilAdvice;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.AdviseWith;
import com.liferay.portal.test.rule.AspectJNewEnvTestRule;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.RegistryUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 * @author Preston Crary
 */
@NewEnv(type = NewEnv.Type.CLASSLOADER)
public class AutoBatchPreparedStatementUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			AspectJNewEnvTestRule.INSTANCE, CodeCoverageAssertor.INSTANCE);

	@Before
	public void setUp() {
		RegistryUtil.setRegistry(new BasicRegistryImpl());
	}

	@AdviseWith(adviceClasses = {PropsUtilAdvice.class})
	@Test
	public void testCINITFailure() throws ClassNotFoundException {
		PropsUtilAdvice.setProps(PropsKeys.HIBERNATE_JDBC_BATCH_SIZE, "0");

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

	@AdviseWith(
		adviceClasses = {
			CancelingPortalExecutorManagerUtilAdvice.class,
			PropsUtilAdvice.class
		}
	)
	@Test
	public void testConcurrentCancellationException() {
		PropsUtilAdvice.setProps(PropsKeys.HIBERNATE_JDBC_BATCH_SIZE, "0");

		doTestConcurrentCancellationException(true);
		doTestConcurrentCancellationException(false);
	}

	@AdviseWith(
		adviceClasses =
			{PortalExecutorManagerUtilAdvice.class, PropsUtilAdvice.class}
	)
	@Test
	public void testConcurrentExecutionException() {
		PropsUtilAdvice.setProps(PropsKeys.HIBERNATE_JDBC_BATCH_SIZE, "0");

		doTestConcurrentExecutionExceptions(true);
		doTestConcurrentExecutionExceptions(false);
	}

	@AdviseWith(
		adviceClasses =
			{PortalExecutorManagerUtilAdvice.class, PropsUtilAdvice.class}
	)
	@Test
	public void testConcurrentWaitingForFutures() throws SQLException {
		PropsUtilAdvice.setProps(PropsKeys.HIBERNATE_JDBC_BATCH_SIZE, "0");

		doTestConcurrentWaitingForFutures(true);
		doTestConcurrentWaitingForFutures(false);
	}

	@AdviseWith(adviceClasses = {PropsUtilAdvice.class}
	)
	@Test
	public void testConstructor() throws ReflectiveOperationException {
		PropsUtilAdvice.setProps(PropsKeys.HIBERNATE_JDBC_BATCH_SIZE, "0");

		Constructor<AutoBatchPreparedStatementUtil> constructor =
			AutoBatchPreparedStatementUtil.class.getDeclaredConstructor();

		Assert.assertTrue(Modifier.isPublic(constructor.getModifiers()));

		constructor.setAccessible(true);

		constructor.newInstance();
	}

	@AdviseWith(
		adviceClasses =
			{PortalExecutorManagerUtilAdvice.class, PropsUtilAdvice.class}
	)
	@Test
	public void testNotSupportBatchUpdates() throws Exception {
		PropsUtilAdvice.setProps(PropsKeys.HIBERNATE_JDBC_BATCH_SIZE, "0");

		doTestNotSupportBatchUpdates();
		doTestNotSupportBatchUpdatesConcurrent();
	}

	@AdviseWith(
		adviceClasses =
			{PortalExecutorManagerUtilAdvice.class, PropsUtilAdvice.class}
	)
	@Test
	public void testSupportBatchUpdates() throws Exception {
		PropsUtilAdvice.setProps(PropsKeys.HIBERNATE_JDBC_BATCH_SIZE, "2");

		doTestSupportBaseUpdates();
		doTestSupportBaseUpdatesConcurrent();
	}

	protected void doTestConcurrentCancellationException(
		boolean supportBatchUpdates) {

		try (PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					(Connection)ProxyUtil.newProxyInstance(
						ClassLoader.getSystemClassLoader(),
						new Class<?>[] {Connection.class},
						new ConnectionInvocationHandler(
							new PreparedStatementInvocationHandler(
								supportBatchUpdates))),
					StringPool.BLANK)) {

			preparedStatement.addBatch();

			preparedStatement.executeBatch();

			preparedStatement.addBatch();

			preparedStatement.executeBatch();
		}
		catch (Throwable t) {
			Assert.assertSame(CancellationException.class, t.getClass());

			Throwable[] throwables = t.getSuppressed();

			Assert.assertEquals(
				Arrays.toString(throwables), 1, throwables.length);

			Throwable throwable = throwables[0];

			Assert.assertSame(
				CancellationException.class, throwable.getClass());

			return;
		}

		Assert.fail();
	}

	protected void doTestConcurrentExecutionExceptions(
		boolean supportBatchUpdates) {

		PreparedStatementInvocationHandler preparedStatementInvocationHandler =
			new PreparedStatementInvocationHandler(supportBatchUpdates);

		Set<Throwable> throwables = Collections.newSetFromMap(
			new IdentityHashMap<Throwable, Boolean>());

		try (PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					(Connection)ProxyUtil.newProxyInstance(
						ClassLoader.getSystemClassLoader(),
						new Class<?>[] {Connection.class},
						new ConnectionInvocationHandler(
							preparedStatementInvocationHandler)),
					StringPool.BLANK)) {

			RuntimeException runtimeException1 = new RuntimeException();

			throwables.add(runtimeException1);

			preparedStatementInvocationHandler.setRuntimeException(
				runtimeException1);

			preparedStatement.addBatch();

			preparedStatement.executeBatch();

			RuntimeException runtimeException2 = new RuntimeException();

			throwables.add(runtimeException2);

			preparedStatementInvocationHandler.setRuntimeException(
				runtimeException2);

			preparedStatement.addBatch();

			preparedStatement.executeBatch();
		}
		catch (Throwable t) {
			Assert.assertTrue(throwables.contains(t));

			Throwable[] suppressedThrowables = t.getSuppressed();

			Assert.assertEquals(
				Arrays.toString(suppressedThrowables), 1,
				suppressedThrowables.length);
			Assert.assertTrue(throwables.contains(suppressedThrowables[0]));

			return;
		}

		Assert.fail();
	}

	protected void doTestConcurrentWaitingForFutures(
			boolean supportBatchUpdates)
		throws SQLException {

		TestNoticeableFuture<Void> testNoticeableFuture =
			new TestNoticeableFuture<>();

		try (PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					(Connection)ProxyUtil.newProxyInstance(
						ClassLoader.getSystemClassLoader(),
						new Class<?>[] {Connection.class},
						new ConnectionInvocationHandler(
							new PreparedStatementInvocationHandler(
								supportBatchUpdates))),
					StringPool.BLANK)) {

			InvocationHandler invocationHandler =
				ProxyUtil.getInvocationHandler(preparedStatement);

			Set<Future<Void>> futures = ReflectionTestUtil.getFieldValue(
				invocationHandler, "_futures");

			futures.add(testNoticeableFuture);
		}

		Assert.assertTrue(testNoticeableFuture.hasCalledGet());
	}

	protected void doTestNotSupportBatchUpdates() throws Exception {
		PreparedStatementInvocationHandler preparedStatementInvocationHandler =
			new PreparedStatementInvocationHandler(false);

		List<Method> methods = preparedStatementInvocationHandler.getMethods();

		try (PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					(PreparedStatement)ProxyUtil.newProxyInstance(
						ClassLoader.getSystemClassLoader(),
						new Class<?>[] {PreparedStatement.class},
						preparedStatementInvocationHandler))) {

			Assert.assertTrue(methods.toString(), methods.isEmpty());

			// Calling addBatch fallbacks to executeUpdate

			preparedStatement.addBatch();

			Assert.assertEquals(methods.toString(), 1, methods.size());
			Assert.assertEquals(
				PreparedStatement.class.getMethod("executeUpdate"),
				methods.remove(0));

			// Calling executeBatch does nothing

			Assert.assertArrayEquals(
				new int[0], preparedStatement.executeBatch());
			Assert.assertTrue(methods.toString(), methods.isEmpty());

			// Other methods like execute pass through

			preparedStatement.execute();

			Assert.assertEquals(methods.toString(), 1, methods.size());
			Assert.assertEquals(
				PreparedStatement.class.getMethod("execute"),
				methods.remove(0));
		}

		Assert.assertEquals(methods.toString(), 1, methods.size());
		Assert.assertEquals(
			PreparedStatement.class.getMethod("close"), methods.remove(0));
	}

	protected void doTestNotSupportBatchUpdatesConcurrent() throws Exception {
		PreparedStatementInvocationHandler preparedStatementInvocationHandler =
			new PreparedStatementInvocationHandler(false);

		List<Method> methods = preparedStatementInvocationHandler.getMethods();

		try (PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					(Connection)ProxyUtil.newProxyInstance(
						ClassLoader.getSystemClassLoader(),
						new Class<?>[] {Connection.class},
						new ConnectionInvocationHandler(
							preparedStatementInvocationHandler)),
					StringPool.BLANK)) {

			Assert.assertTrue(methods.toString(), methods.isEmpty());

			// Calling addBatch fallbacks to executeUpdate

			preparedStatement.addBatch();

			Assert.assertEquals(methods.toString(), 2, methods.size());
			Assert.assertEquals(
				PreparedStatement.class.getMethod("executeUpdate"),
				methods.remove(0));
			Assert.assertEquals(
				PreparedStatement.class.getMethod("close"), methods.remove(0));

			// Calling executeBatch does nothing

			Assert.assertArrayEquals(
				new int[0], preparedStatement.executeBatch());
			Assert.assertTrue(methods.toString(), methods.isEmpty());

			// Other methods like execute pass through

			preparedStatement.execute();

			Assert.assertEquals(methods.toString(), 1, methods.size());
			Assert.assertEquals(
				PreparedStatement.class.getMethod("execute"),
				methods.remove(0));
		}

		Assert.assertEquals(methods.toString(), 1, methods.size());
		Assert.assertEquals(
			PreparedStatement.class.getMethod("close"), methods.remove(0));
	}

	protected void doTestSupportBaseUpdates() throws Exception {
		PreparedStatementInvocationHandler preparedStatementInvocationHandler =
			new PreparedStatementInvocationHandler(true);

		List<Method> methods = preparedStatementInvocationHandler.getMethods();

		try (PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					(PreparedStatement)ProxyUtil.newProxyInstance(
						ClassLoader.getSystemClassLoader(),
						new Class<?>[] {PreparedStatement.class},
						preparedStatementInvocationHandler))) {

			InvocationHandler invocationHandler =
				ProxyUtil.getInvocationHandler(preparedStatement);

			Assert.assertEquals(
				Integer.valueOf(0),
				ReflectionTestUtil.getFieldValue(invocationHandler, "_count"));

			Assert.assertTrue(methods.toString(), methods.isEmpty());

			// Protection for executing empty batch

			Assert.assertArrayEquals(
				new int[0], preparedStatement.executeBatch());
			Assert.assertTrue(methods.toString(), methods.isEmpty());
			Assert.assertEquals(
				Integer.valueOf(0),
				ReflectionTestUtil.getFieldValue(invocationHandler, "_count"));

			// Calling addBatch passes through when within the Hibernate JDBC
			// batch size

			preparedStatement.addBatch();

			Assert.assertEquals(methods.toString(), 1, methods.size());
			Assert.assertEquals(
				PreparedStatement.class.getMethod("addBatch"),
				methods.remove(0));
			Assert.assertEquals(
				Integer.valueOf(1),
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
				Integer.valueOf(0),
				ReflectionTestUtil.getFieldValue(invocationHandler, "_count"));

			// Calling addBatch passes through when within the Hibernate JDBC
			// batch size

			preparedStatement.addBatch();

			Assert.assertEquals(methods.toString(), 1, methods.size());
			Assert.assertEquals(
				PreparedStatement.class.getMethod("addBatch"),
				methods.remove(0));
			Assert.assertEquals(
				Integer.valueOf(1),
				ReflectionTestUtil.getFieldValue(invocationHandler, "_count"));

			// Calling executeBatch passes through when batch is not empty

			preparedStatement.executeBatch();

			Assert.assertEquals(methods.toString(), 1, methods.size());
			Assert.assertEquals(
				PreparedStatement.class.getMethod("executeBatch"),
				methods.remove(0));
			Assert.assertEquals(
				Integer.valueOf(0),
				ReflectionTestUtil.getFieldValue(invocationHandler, "_count"));

			// Other methods like execute pass through

			preparedStatement.execute();

			Assert.assertEquals(methods.toString(), 1, methods.size());
			Assert.assertEquals(
				PreparedStatement.class.getMethod("execute"),
				methods.remove(0));
			Assert.assertEquals(
				Integer.valueOf(0),
				ReflectionTestUtil.getFieldValue(invocationHandler, "_count"));
		}

		Assert.assertEquals(methods.toString(), 1, methods.size());
		Assert.assertEquals(
			PreparedStatement.class.getMethod("close"), methods.remove(0));
	}

	protected void doTestSupportBaseUpdatesConcurrent() throws Exception {
		PreparedStatementInvocationHandler preparedStatementInvocationHandler =
			new PreparedStatementInvocationHandler(true);

		List<Method> methods = preparedStatementInvocationHandler.getMethods();

		try (PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					(Connection)ProxyUtil.newProxyInstance(
						ClassLoader.getSystemClassLoader(),
						new Class<?>[] {Connection.class},
						new ConnectionInvocationHandler(
							preparedStatementInvocationHandler)),
					StringPool.BLANK)) {

			InvocationHandler invocationHandler =
				ProxyUtil.getInvocationHandler(preparedStatement);

			Assert.assertEquals(
				Integer.valueOf(0),
				ReflectionTestUtil.getFieldValue(invocationHandler, "_count"));

			Assert.assertTrue(methods.toString(), methods.isEmpty());

			// Protection for executing empty batch

			Assert.assertArrayEquals(
				new int[0], preparedStatement.executeBatch());
			Assert.assertTrue(methods.toString(), methods.isEmpty());
			Assert.assertEquals(
				Integer.valueOf(0),
				ReflectionTestUtil.getFieldValue(invocationHandler, "_count"));

			// Calling addBatch passes through when within the Hibernate JDBC
			// batch size

			preparedStatement.addBatch();

			Assert.assertEquals(methods.toString(), 1, methods.size());
			Assert.assertEquals(
				PreparedStatement.class.getMethod("addBatch"),
				methods.remove(0));
			Assert.assertEquals(
				Integer.valueOf(1),
				ReflectionTestUtil.getFieldValue(invocationHandler, "_count"));

			// Calling addBatch passes through and triggers executeBatch when
			// exceeding the Hibernate JDBC batch size

			preparedStatement.addBatch();

			Assert.assertEquals(methods.toString(), 3, methods.size());
			Assert.assertEquals(
				PreparedStatement.class.getMethod("addBatch"),
				methods.remove(0));
			Assert.assertEquals(
				PreparedStatement.class.getMethod("executeBatch"),
				methods.remove(0));
			Assert.assertEquals(
				Integer.valueOf(0),
				ReflectionTestUtil.getFieldValue(invocationHandler, "_count"));
			Assert.assertEquals(
				PreparedStatement.class.getMethod("close"), methods.remove(0));

			// Calling addBatch passes through when within the Hibernate JDBC
			// batch size

			preparedStatement.addBatch();

			Assert.assertEquals(methods.toString(), 1, methods.size());
			Assert.assertEquals(
				PreparedStatement.class.getMethod("addBatch"),
				methods.remove(0));
			Assert.assertEquals(
				Integer.valueOf(1),
				ReflectionTestUtil.getFieldValue(invocationHandler, "_count"));

			// Calling executeBatch passes through when batch is not empty

			preparedStatement.executeBatch();

			Assert.assertEquals(methods.toString(), 2, methods.size());
			Assert.assertEquals(
				PreparedStatement.class.getMethod("executeBatch"),
				methods.remove(0));
			Assert.assertEquals(
				Integer.valueOf(0),
				ReflectionTestUtil.getFieldValue(invocationHandler, "_count"));
			Assert.assertEquals(
				PreparedStatement.class.getMethod("close"), methods.remove(0));

			// Other methods like execute pass through

			preparedStatement.execute();

			Assert.assertEquals(methods.toString(), 1, methods.size());
			Assert.assertEquals(
				PreparedStatement.class.getMethod("execute"),
				methods.remove(0));
			Assert.assertEquals(
				Integer.valueOf(0),
				ReflectionTestUtil.getFieldValue(invocationHandler, "_count"));
		}

		Assert.assertEquals(methods.toString(), 1, methods.size());
		Assert.assertEquals(
			PreparedStatement.class.getMethod("close"), methods.remove(0));
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
						_preparedStatementInvocationHandler.
							_supportBatchUpdates));
			}

			if (method.equals(
					Connection.class.getMethod(
						"prepareStatement", String.class))) {

				return ProxyUtil.newProxyInstance(
					ClassLoader.getSystemClassLoader(),
					new Class<?>[] {PreparedStatement.class},
					_preparedStatementInvocationHandler);
			}

			throw new UnsupportedOperationException();
		}

		private ConnectionInvocationHandler(
			PreparedStatementInvocationHandler
				preparedStatementInvocationHandler) {

			_preparedStatementInvocationHandler =
				preparedStatementInvocationHandler;
		}

		private final PreparedStatementInvocationHandler
			_preparedStatementInvocationHandler;

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
					new ConnectionInvocationHandler(this));
			}

			_methods.add(method);

			if (method.equals(PreparedStatement.class.getMethod("addBatch"))) {
				return null;
			}

			if (method.equals(PreparedStatement.class.getMethod("close"))) {
				return null;
			}

			if (method.equals(PreparedStatement.class.getMethod("execute"))) {
				return false;
			}

			if (method.equals(
					PreparedStatement.class.getMethod("executeBatch"))) {

				if (_runtimeException != null) {
					throw _runtimeException;
				}

				return new int[0];
			}

			if (method.equals(
					PreparedStatement.class.getMethod("executeUpdate"))) {

				if (_runtimeException != null) {
					throw _runtimeException;
				}

				return 0;
			}

			throw new UnsupportedOperationException();
		}

		public void setRuntimeException(RuntimeException runtimeException) {
			_runtimeException = runtimeException;
		}

		private PreparedStatementInvocationHandler(
			boolean supportBatchUpdates) {

			_supportBatchUpdates = supportBatchUpdates;
		}

		private final List<Method> _methods = new ArrayList<>();
		private RuntimeException _runtimeException;
		private final boolean _supportBatchUpdates;

	}

	private static final class TestNoticeableFuture<T>
		extends DefaultNoticeableFuture<T> {

		@Override
		public T get() {
			_calledGet = true;

			return null;
		}

		public boolean hasCalledGet() {
			return _calledGet;
		}

		private boolean _calledGet;

	}

}