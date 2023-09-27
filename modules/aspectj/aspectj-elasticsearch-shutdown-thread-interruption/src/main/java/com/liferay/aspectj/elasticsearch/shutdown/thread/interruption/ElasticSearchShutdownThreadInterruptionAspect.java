/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.aspectj.elasticsearch.shutdown.thread.interruption;

import java.lang.reflect.Field;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.SuppressAjWarnings;

/**
 * @author Shuyang Zhou
 */
@Aspect
@SuppressAjWarnings("adviceDidNotMatch")
public class ElasticSearchShutdownThreadInterruptionAspect {

	@AfterReturning(
		pointcut = "execution(private boolean org.eclipse.osgi.internal.loader.ModuleClassLoader.lockClassName(String)) && args(className)",
		returning = "locked"
	)
	public void afterLockClassName(String className, boolean locked)
		throws InterruptedException {

		if (!className.equals("org.jboss.netty.util.internal.ByteBufferUtil") ||
			!locked) {

			return;
		}

		Thread currentThread = Thread.currentThread();

		String threadName = currentThread.getName();

		if (!threadName.contains("[http_server_worker]")) {
			return;
		}

		if (_shutdownCountDownLatch.getCount() > 0) {
			_locked = true;

			LogUtil.log("Locked " + className);

			_shutdownCountDownLatch.await();
		}
	}

	@AfterReturning(
		"call(public java.util.List<Runnable> " +
			"java.util.concurrent.ExecutorService.shutdownNow()) && " +
				"target(executorService)"
	)
	public void afterShutdownNow(ExecutorService executorService)
		throws Exception {

		if (!(executorService instanceof ThreadPoolExecutor)) {
			return;
		}

		Field threadFactoryField = ThreadPoolExecutor.class.getDeclaredField(
			"threadFactory");

		threadFactoryField.setAccessible(true);

		ThreadFactory threadFactory = (ThreadFactory)threadFactoryField.get(
			executorService);

		Class<?> threadFactoryClass = threadFactory.getClass();

		String threadFactoryClassName = threadFactoryClass.getName();

		if (!threadFactoryClassName.equals(
				"org.elasticsearch.common.util.concurrent." +
					"EsExecutors$EsThreadFactory")) {

			return;
		}

		Field namePrefixField = threadFactoryClass.getDeclaredField(
			"namePrefix");

		namePrefixField.setAccessible(true);

		String namePrefix = (String)namePrefixField.get(threadFactory);

		if (namePrefix.endsWith("[http_server_worker]")) {
			if (_locked) {
				LogUtil.log(
					"Called " + executorService +
						"#shutdownNow with name prefix " + namePrefix);
			}

			_shutdownCountDownLatch.countDown();
		}
	}

	private volatile boolean _locked;
	private final CountDownLatch _shutdownCountDownLatch = new CountDownLatch(
		1);

}