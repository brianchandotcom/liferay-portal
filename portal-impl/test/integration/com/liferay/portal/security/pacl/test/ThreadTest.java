/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.executor.PortalExecutorManagerUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.security.pacl.PACLExecutionTestListener;
import com.liferay.portal.security.pacl.PACLIntegrationJUnitTestRunner;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Raymond Augé
 */
@ExecutionTestListeners(listeners = {PACLExecutionTestListener.class})
@RunWith(PACLIntegrationJUnitTestRunner.class)
public class ThreadTest {

	@Test
	public void current1() throws Exception {
		try {
			Thread.currentThread().checkAccess();
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void current2() throws Exception {
		try {
			Thread.currentThread().getContextClassLoader();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void current3() throws Exception {
		try {
			ClassLoader classLoader = getClass().getClassLoader();

			Thread.currentThread().setContextClassLoader(classLoader);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void current4() throws Exception {
		try {
			Thread.getAllStackTraces();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void current5() throws Exception {
		try {
			Thread.setDefaultUncaughtExceptionHandler(null);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void messageBus1() throws Exception {
		Message message = new Message();

		Map<String, Object> results =
			(Map<String, Object>)MessageBusUtil.sendSynchronousMessage(
				"liferay/test_pacl", message, Time.SECOND * 60 * 5);

		Assert.assertNotNull(results.get("PortalServiceUtil#getBuildNumber"));
	}

	@Test
	public void messageBus2() throws Exception {
		Message message = new Message();

		Map<String, Object> results =
			(Map<String, Object>)MessageBusUtil.sendSynchronousMessage(
				"liferay/test_pacl", message, Time.SECOND * 60 * 5);

		Assert.assertNull(results.get("UserLocalServiceUtil#getUser"));
	}

	@Test
	public void new1() throws Exception {
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
	public void new2()
		throws Exception {

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
	public void new3()
		throws Exception {

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
	public void new4() throws Exception {
		try {
			Thread thread = new Thread();

			thread.start();
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void new5() throws Exception {
		try {
			Thread thread = new Thread();

			thread.getStackTrace();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void new6() throws Exception {
		FutureTask<Exception> futureTask = new FutureTask<Exception>(
			new Callable<Exception>() {

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
	public void new7() throws Exception {
		FutureTask<Exception> futureTask = new FutureTask<Exception>(
			new Callable<Exception>() {

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
	public void new8()
		throws Exception {

		FutureTask<Exception> futureTask = new FutureTask<Exception>(
			new Callable<Exception>() {

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
	public void new9() throws Exception {
		FutureTask<Exception> futureTask = new FutureTask<Exception>(
			new Callable<Exception>() {

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
	public void new10()
		throws Exception {

		final ClassLoader classLoader = getClass().getClassLoader();

		FutureTask<Exception> futureTask = new FutureTask<Exception>(
			new Callable<Exception>() {

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
	public void new11()
		throws Exception {

		FutureTask<Exception> futureTask = new FutureTask<Exception>(
			new Callable<Exception>() {

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
	public void new12() throws Exception {
		try {
			Thread thread = new Thread(
				new Runnable() {

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
	public void portalExecutor1() throws Exception {
		try {
			PortalExecutorManagerUtil.execute(
				"liferay/hot_deploy",
				new Callable<Void>() {

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
	public void portalExecutor2() throws Exception {
		try {
			PortalExecutorManagerUtil.execute(
				"liferay/test_pacl",
				new Callable<Void>() {

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
	public void portalExecutor3() throws Exception {
		try {
			PortalExecutorManagerUtil.getPortalExecutor("liferay/hot_deploy");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void portalExecutor4() throws Exception {
		try {
			PortalExecutorManagerUtil.getPortalExecutor("liferay/test_pacl");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void portalExecutor5() throws Exception {
		try {
			PortalExecutorManagerUtil.shutdown("liferay/hot_deploy");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void portalExecutor6() throws Exception {
		try {
			PortalExecutorManagerUtil.shutdown("liferay/test_pacl");

			PortalExecutorManagerUtil.getPortalExecutor(
				"liferay/test_pacl", true);
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

}