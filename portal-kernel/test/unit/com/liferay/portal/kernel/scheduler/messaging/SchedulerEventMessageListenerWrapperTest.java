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

package com.liferay.portal.kernel.scheduler.messaging;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.RegistryUtil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Tina Tian
 */
public class SchedulerEventMessageListenerWrapperTest {

	@Before
	public void setUp() {
		RegistryUtil.setRegistry(new BasicRegistryImpl());
	}

	@Test
	public void testConcurrentInvocation() throws Exception {
		SchedulerEventMessageListenerWrapper
			schedulerEventMessageListenerWrapper =
				new SchedulerEventMessageListenerWrapper();

		TestMessageListener testMessageListener = new TestMessageListener();

		schedulerEventMessageListenerWrapper.setMessageListener(
			testMessageListener);

		Message message1 = new Message();

		FutureTask<Void> futureTask = new FutureTask<>(
			() -> {
				schedulerEventMessageListenerWrapper.receive(message1);

				return null;
			});

		Thread thread = new Thread(
			futureTask,
			"SchedulerEventMessageListenerWrapperTest_testConcurrent_Thread");

		thread.start();

		testMessageListener.waitUntilBlock();

		Message message2 = new Message();

		schedulerEventMessageListenerWrapper.receive(message2);

		testMessageListener.unblock();

		futureTask.get();

		Assert.assertSame(
			"Message is not processed", message1, message1.getResponse());
		Assert.assertSame(
			"Message is not processed", message2, message2.getResponse());
	}

	private class TestMessageListener implements MessageListener {

		@Override
		public void receive(Message message) {
			if (_lock.tryLock()) {
				try {
					_waitCountDownLatch.countDown();

					_blockCountDownLatch.await();

					message.setResponse(message);
				}
				catch (InterruptedException ie) {
				}
				finally {
					_lock.unlock();
				}
			}
		}

		public void unblock() {
			_blockCountDownLatch.countDown();
		}

		public void waitUntilBlock() throws InterruptedException {
			_waitCountDownLatch.await();
		}

		private final CountDownLatch _blockCountDownLatch = new CountDownLatch(
			1);
		private final Lock _lock = new ReentrantLock();
		private final CountDownLatch _waitCountDownLatch = new CountDownLatch(
			1);

	}

}