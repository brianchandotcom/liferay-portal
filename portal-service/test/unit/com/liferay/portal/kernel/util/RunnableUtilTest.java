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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;

import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.FutureTask;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class RunnableUtilTest {

	@Test
	public void testSomeMethod() throws Exception {
		final CyclicBarrier cyclicBarrier1 = new CyclicBarrier(2);
		final CyclicBarrier cyclicBarrier2 = new CyclicBarrier(2);

		final Runnable runnable = new Runnable() {

			@Override
			public void run() {
				try {
					Thread thread = Thread.currentThread();

					String name = thread.getName();

					cyclicBarrier1.await();

					System.out.println(name + " passed 1");

					cyclicBarrier2.await();

					System.out.println(name + " passed 2");
				}
				catch (Exception e) {
					ReflectionUtil.throwException(e);
				}
			}

		};

		Callable<String> callable = new Callable<String>() {

			@Override
			public String call() throws Exception {
				UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
					new UnsyncByteArrayOutputStream();

				RunnableUtil.runWithSwappedSystemOut(
					runnable, unsyncByteArrayOutputStream);

				return unsyncByteArrayOutputStream.toString();
			}

		};

		FutureTask<String> futureTask1 = new FutureTask<>(callable);

		Thread thread1 = new Thread(futureTask1, "Test thread 1");

		thread1.start();

		FutureTask<String> futureTask2 = new FutureTask<>(callable);

		Thread thread2 = new Thread(futureTask2, "Test thread 2");

		thread2.start();

		Assert.assertEquals(
			"Test thread 1 passed 1\nTest thread 1 passed 2\n",
			futureTask1.get());
		Assert.assertEquals(
			"Test thread 2 passed 1\nTest thread 2 passed 2\n",
			futureTask2.get());
	}

}