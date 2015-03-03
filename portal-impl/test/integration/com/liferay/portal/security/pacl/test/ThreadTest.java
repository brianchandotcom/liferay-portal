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

package com.liferay.portal.security.pacl.test;

import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.executor.PortalExecutorManagerUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.PACLTestRule;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class ThreadTest {

	@ClassRule
	@Rule
	public static final PACLTestRule paclTestRule = new PACLTestRule();

	@Test
	public void testCurrent1() throws Exception {
		try {
			Thread.currentThread().checkAccess();
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void testCurrent2() throws Exception {
		try {
			Thread.currentThread().getContextClassLoader();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testCurrent3() throws Exception {
		try {
			ClassLoader classLoader = getClass().getClassLoader();

			Thread.currentThread().setContextClassLoader(classLoader);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testCurrent4() throws Exception {
		try {
			Thread.getAllStackTraces();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testCurrent5() throws Exception {
		try {
			Thread.setDefaultUncaughtExceptionHandler(null);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testMessageBus1() throws Exception {
		Message message = new Message();

		Map<String, Object> results =
			(Map<String, Object>)MessageBusUtil.sendSynchronousMessage(
				"liferay/test_pacl", message, Time.SECOND * 60 * 5);

		Assert.assertNotNull(results.get("PortalServiceUtil#getBuildNumber"));
	}

	@Test
	public void testMessageBus2() throws Exception {
		Message message = new Message();

		Map<String, Object> results =
			(Map<String, Object>)MessageBusUtil.sendSynchronousMessage(
				"liferay/test_pacl", message, Time.SECOND * 60 * 5);

		Assert.assertNull(results.get("UserLocalServiceUtil#getUser"));
	}

	@Test
	public void testNew1() throws Exception {
		try {
			Thread thread = new Thread() {

				@Override
				public void run() {
				}

			};

			thread.start();
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void testNew2() throws Exception {
		try {
			Thread thread = new Thread() {

				@Override
				public ClassLoader getContextClassLoader() {
					return super.getContextClassLoader();
				}

				@Override
				public void run() {
				}

			};

			thread.start();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testNew3() throws Exception {
		try {
			Thread thread = new Thread() {

				@Override
				public void setContextClassLoader(ClassLoader cl) {
					super.setContextClassLoader(cl);
				}

				@Override
				public void run() {
				}

			};

			thread.start();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testNew4() throws Exception {
		try {
			Thread thread = new Thread();

			thread.start();
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void testNew5() throws Exception {
		try {
			Thread thread = new Thread();

			thread.getStackTrace();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testNew6() throws Exception {
		FutureTask<Exception> futureTask = new FutureTask<Exception>(
			new Callable<Exception>() {

				@Override
				public Exception call() throws Exception {
					try {
						Thread.currentThread().checkAccess();
					}
					catch (SecurityException se) {
						return se;
					}

					return null;
				}

			}
		);

		Thread thread = new Thread(futureTask);

		thread.start();

		Exception exception = futureTask.get();

		Assert.assertNull(exception);
	}

	@Test
	public void testNew7() throws Exception {
		FutureTask<Exception> futureTask = new FutureTask<Exception>(
			new Callable<Exception>() {

				@Override
				public Exception call() throws Exception {
					try {
						Thread.getAllStackTraces();
					}
					catch (SecurityException se) {
						return se;
					}

					return null;
				}

			}
		);

		Thread thread = new Thread(futureTask);

		thread.start();

		Exception exception = futureTask.get();

		Assert.assertNotNull(exception);
		Assert.assertTrue(exception instanceof SecurityException);
	}

	@Test
	public void testNew8() throws Exception {
		FutureTask<Exception> futureTask = new FutureTask<Exception>(
			new Callable<Exception>() {

				@Override
				public Exception call() throws Exception {
					try {
						Thread.currentThread().getContextClassLoader();
					}
					catch (SecurityException se) {
						return se;
					}

					return null;
				}

			}
		);

		Thread thread = new Thread(futureTask);

		thread.start();

		Exception exception = futureTask.get();

		Assert.assertNotNull(exception);
		Assert.assertTrue(exception instanceof SecurityException);
	}

	@Test
	public void testNew9() throws Exception {
		FutureTask<Exception> futureTask = new FutureTask<Exception>(
			new Callable<Exception>() {

				@Override
				public Exception call() throws Exception {
					try {
						Thread.currentThread().getStackTrace();
					}
					catch (SecurityException se) {
						return se;
					}

					return null;
				}

			}
		);

		Thread thread = new Thread(futureTask);

		thread.start();

		Exception exception = futureTask.get();

		Assert.assertNull(exception);
	}

	@Test
	public void testNew10() throws Exception {
		final ClassLoader classLoader = getClass().getClassLoader();

		FutureTask<Exception> futureTask = new FutureTask<Exception>(
			new Callable<Exception>() {

				@Override
				public Exception call() throws Exception {
					try {
						Thread.currentThread().setContextClassLoader(
							classLoader);
					}
					catch (SecurityException se) {
						return se;
					}

					return null;
				}

			}
		);

		Thread thread = new Thread(futureTask);

		thread.start();

		Exception exception = futureTask.get();

		Assert.assertNotNull(exception);
		Assert.assertTrue(exception instanceof SecurityException);
	}

	@Test
	public void testNew11() throws Exception {
		FutureTask<Exception> futureTask = new FutureTask<Exception>(
			new Callable<Exception>() {

				@Override
				public Exception call() throws Exception {
					try {
						Thread.setDefaultUncaughtExceptionHandler(null);
					}
					catch (SecurityException se) {
						return se;
					}

					return null;
				}

			}
		);

		Thread thread = new Thread(futureTask);

		thread.start();

		Exception exception = futureTask.get();

		Assert.assertNotNull(exception);
		Assert.assertTrue(exception instanceof SecurityException);
	}

	@Test
	public void testNew12() throws Exception {
		try {
			Thread thread = new Thread(
				new Runnable() {

					@Override
					public void run() {
					}

				}
			);

			thread.start();
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void testPortalExecutor1() throws Exception {
		try {
			ThreadPoolExecutor threadPoolExecutor =
				PortalExecutorManagerUtil.getPortalExecutor(
					"liferay/hot_deploy");

			threadPoolExecutor.submit(
				new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						return null;
					}

				}
			);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testPortalExecutor2() throws Exception {
		try {
			ThreadPoolExecutor threadPoolExecutor =
				PortalExecutorManagerUtil.getPortalExecutor(
					"liferay/test_pacl");

			threadPoolExecutor.submit(
				new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						return null;
					}

				}
			);
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void testPortalExecutor3() throws Exception {
		try {
			PortalExecutorManagerUtil.getPortalExecutor("liferay/hot_deploy");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testPortalExecutor4() throws Exception {
		try {
			PortalExecutorManagerUtil.getPortalExecutor("liferay/test_pacl");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void testPortalExecutor5() throws Exception {
		try {
			ThreadPoolExecutor threadPoolExecutor =
				PortalExecutorManagerUtil.getPortalExecutor(
					"liferay/hot_deploy");

			threadPoolExecutor.shutdown();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testPortalExecutor6() throws Exception {
		try {
			ThreadPoolExecutor threadPoolExecutor =
				PortalExecutorManagerUtil.getPortalExecutor(
					"liferay/test_pacl");

			threadPoolExecutor.shutdown();

			threadPoolExecutor.awaitTermination(1, TimeUnit.MINUTES);

			PortalExecutorManagerUtil.getPortalExecutor(
				"liferay/test_pacl", true);

			MessageBus messageBus = MessageBusUtil.getMessageBus();

			Destination destination = messageBus.getDestination(
				"liferay/test_pacl");

			destination.open();
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

}