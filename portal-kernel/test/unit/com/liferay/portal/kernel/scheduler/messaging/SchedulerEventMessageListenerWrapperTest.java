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
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.rule.NewEnvTestRule;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Tina Tian
 */
@NewEnv(type = NewEnv.Type.CLASSLOADER)
public class SchedulerEventMessageListenerWrapperTest {

	@ClassRule
	@Rule
	public static final NewEnvTestRule newEnvTestRule = NewEnvTestRule.INSTANCE;

	@Before
	public void setUp() {
		RegistryUtil.setRegistry(new BasicRegistryImpl());

		_testMessageListener = new TestMessageListener();

		_testMessage1 = new Message();

		_testMessage1.setPayload("Test Message 1");

		_testMessage2 = new Message();

		_testMessage2.setPayload("Test Message 2");
	}

	@Test
	public void testConcurrentReceiveWithoutTimeout() throws Exception {
		PropsTestUtil.setProps(
			PropsKeys.SCHEDULER_EVENT_MESSAGE_LISTENER_LOCK_TIMEOUT, "0");

		SchedulerEventMessageListenerWrapper
			schedulerEventMessageListenerWrapper =
				new SchedulerEventMessageListenerWrapper();

		schedulerEventMessageListenerWrapper.setMessageListener(
			_testMessageListener);

		FutureTask<Void> futureTask1 = _startThread(
			schedulerEventMessageListenerWrapper, "Thread1", _testMessage1);

		_testMessageListener.waitUntilBlock();

		FutureTask<Void> futureTask2 = _startThread(
			schedulerEventMessageListenerWrapper, "Thread2", _testMessage2);

		try {
			futureTask2.get(1000, TimeUnit.MICROSECONDS);

			Assert.fail("Should throw TimeoutException");
		}
		catch (Exception e) {
			Assert.assertTrue(e instanceof TimeoutException);
		}

		_testMessageListener.unblock();

		futureTask1.get();
		futureTask2.get();

		Assert.assertSame(
			"Message is not processed", _testMessage1,
			_testMessage1.getResponse());
		Assert.assertSame(
			"Message is not processed", _testMessage2,
			_testMessage2.getResponse());
	}

	@Test
	public void testConcurrentReceiveWithTimeout() throws Exception {
		PropsTestUtil.setProps(
			PropsKeys.SCHEDULER_EVENT_MESSAGE_LISTENER_LOCK_TIMEOUT, "1000");

		SchedulerEventMessageListenerWrapper
			schedulerEventMessageListenerWrapper =
				new SchedulerEventMessageListenerWrapper();

		schedulerEventMessageListenerWrapper.setMessageListener(
			_testMessageListener);

		Registry registry = RegistryUtil.getRegistry();

		Message[] receivedMessage = new Message[1];

		registry.registerService(
			MessageBus.class,
			(MessageBus)ProxyUtil.newProxyInstance(
				MessageBus.class.getClassLoader(),
				new Class<?>[] {MessageBus.class},
				(proxy, method, args) -> {
					receivedMessage[0] = (Message)args[1];

					return null;
				}));

		FutureTask<Void> futureTask1 = _startThread(
			schedulerEventMessageListenerWrapper, "Thread1", _testMessage1);

		_testMessageListener.waitUntilBlock();

		FutureTask<Void> futureTask2 = _startThread(
			schedulerEventMessageListenerWrapper, "Thread2", _testMessage2);

		futureTask2.get();

		Assert.assertNull(_testMessage2.getResponse());
		Assert.assertSame(_testMessage2, receivedMessage[0]);

		_testMessageListener.unblock();

		futureTask1.get();

		Assert.assertSame(
			"Message is not processed", _testMessage1,
			_testMessage1.getResponse());
	}

	private FutureTask<Void> _startThread(
		SchedulerEventMessageListenerWrapper
			schedulerEventMessageListenerWrapper,
		String threadName, Message message) {

		FutureTask<Void> futureTask = new FutureTask<>(
			() -> {
				schedulerEventMessageListenerWrapper.receive(message);

				return null;
			});

		Thread thread = new Thread(
			futureTask,
			"SchedulerEventMessageListenerWrapperTest_startThread_" +
				threadName);

		thread.start();

		return futureTask;
	}

	private Message _testMessage1;
	private Message _testMessage2;
	private TestMessageListener _testMessageListener;

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