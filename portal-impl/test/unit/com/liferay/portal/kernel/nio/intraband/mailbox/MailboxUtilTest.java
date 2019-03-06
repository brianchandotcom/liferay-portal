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

package com.liferay.portal.kernel.nio.intraband.mailbox;

import com.liferay.portal.kernel.io.BigEndianCodec;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.test.MockIntraband;
import com.liferay.portal.kernel.nio.intraband.test.MockRegistrationReference;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ThreadUtil;
import com.liferay.portal.test.rule.AdviseWith;
import com.liferay.portal.test.rule.AspectJNewEnvTestRule;

import java.io.IOException;

import java.lang.reflect.Constructor;

import java.nio.ByteBuffer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
@NewEnv(type = NewEnv.Type.JVM)
public class MailboxUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			AspectJNewEnvTestRule.INSTANCE, CodeCoverageAssertor.INSTANCE);

	@Test
	public void testConstructor() {
		PropsTestUtil.setProps(Collections.emptyMap());

		new MailboxUtil();
	}

	@Test
	public void testDepositMailWithReaperThreadDisabled() {
		Map<String, Object> properties = new HashMap<>();

		properties.put(
			PropsKeys.INTRABAND_MAILBOX_REAPER_THREAD_ENABLED,
			Boolean.FALSE.toString());
		properties.put(
			PropsKeys.INTRABAND_MAILBOX_STORAGE_LIFE, String.valueOf(0));

		PropsTestUtil.setProps(properties);

		Assert.assertEquals(0, MailboxUtil.depositMail(ByteBuffer.allocate(0)));
		Assert.assertEquals(1, MailboxUtil.depositMail(ByteBuffer.allocate(0)));
		Assert.assertEquals(2, MailboxUtil.depositMail(ByteBuffer.allocate(0)));
		Assert.assertEquals(3, MailboxUtil.depositMail(ByteBuffer.allocate(0)));

		Thread reaperThread = null;

		for (Thread thread : ThreadUtil.getThreads()) {
			if (thread == null) {
				continue;
			}

			String name = thread.getName();

			if (name.equals(MailboxUtil.class.getName())) {
				reaperThread = thread;

				break;
			}
		}

		Assert.assertNull(reaperThread);
	}

	@AdviseWith(adviceClasses = ReceiptStubAdvice.class)
	@Test
	public void testDepositMailWithReaperThreadEnabled() throws Exception {
		Map<String, Object> properties = new HashMap<>();

		properties.put(
			PropsKeys.INTRABAND_MAILBOX_REAPER_THREAD_ENABLED,
			Boolean.TRUE.toString());
		properties.put(
			PropsKeys.INTRABAND_MAILBOX_STORAGE_LIFE, String.valueOf(0));

		PropsTestUtil.setProps(properties);

		Assert.assertEquals(0, MailboxUtil.depositMail(ByteBuffer.allocate(0)));
		Assert.assertEquals(1, MailboxUtil.depositMail(ByteBuffer.allocate(0)));
		Assert.assertEquals(2, MailboxUtil.depositMail(ByteBuffer.allocate(0)));
		Assert.assertEquals(3, MailboxUtil.depositMail(ByteBuffer.allocate(0)));

		Thread reaperThread = null;

		for (Thread thread : ThreadUtil.getThreads()) {
			if ((thread != null) &&
				Objects.equals(thread.getName(), MailboxUtil.class.getName())) {

				reaperThread = thread;

				break;
			}
		}

		Assert.assertNotNull(reaperThread);

		reaperThread.interrupt();

		while (reaperThread.isInterrupted());

		Assert.assertTrue(reaperThread.isAlive());

		BlockingQueue<Object> overdueMailQueue =
			ReflectionTestUtil.getFieldValue(
				MailboxUtil.class, "_overdueMailQueue");

		while (!overdueMailQueue.isEmpty());

		ReceiptStubAdvice._throwException = true;

		RecorderUncaughtExceptionHandler recorderUncaughtExceptionHandler =
			new RecorderUncaughtExceptionHandler();

		reaperThread.setUncaughtExceptionHandler(
			recorderUncaughtExceptionHandler);

		overdueMailQueue.offer(createReceiptStub());

		reaperThread.join();

		Assert.assertSame(
			reaperThread, recorderUncaughtExceptionHandler._thread);

		Throwable throwable = recorderUncaughtExceptionHandler._throwable;

		Assert.assertSame(IllegalStateException.class, throwable.getClass());

		Assert.assertFalse(reaperThread.isAlive());
	}

	@Test
	public void testReceiveMailWithReaperThreadDisabled() {
		Map<String, Object> properties = new HashMap<>();

		properties.put(
			PropsKeys.INTRABAND_MAILBOX_REAPER_THREAD_ENABLED,
			Boolean.FALSE.toString());
		properties.put(
			PropsKeys.INTRABAND_MAILBOX_STORAGE_LIFE, String.valueOf(10000));

		PropsTestUtil.setProps(properties);

		Assert.assertNull(MailboxUtil.receiveMail(0));

		ByteBuffer byteBuffer1 = ByteBuffer.allocate(0);

		long receipt1 = MailboxUtil.depositMail(byteBuffer1);

		ByteBuffer byteBuffer2 = ByteBuffer.allocate(0);

		long receipt2 = MailboxUtil.depositMail(byteBuffer2);

		Assert.assertSame(byteBuffer2, MailboxUtil.receiveMail(receipt2));

		Assert.assertSame(byteBuffer1, MailboxUtil.receiveMail(receipt1));
	}

	@Test
	public void testReceiveMailWithReaperThreadEnabled() {
		Map<String, Object> properties = new HashMap<>();

		properties.put(
			PropsKeys.INTRABAND_MAILBOX_REAPER_THREAD_ENABLED,
			Boolean.TRUE.toString());
		properties.put(
			PropsKeys.INTRABAND_MAILBOX_STORAGE_LIFE, String.valueOf(10000));

		PropsTestUtil.setProps(properties);

		Assert.assertNull(MailboxUtil.receiveMail(0));

		ByteBuffer byteBuffer1 = ByteBuffer.allocate(0);

		long receipt1 = MailboxUtil.depositMail(byteBuffer1);

		ByteBuffer byteBuffer2 = ByteBuffer.allocate(0);

		long receipt2 = MailboxUtil.depositMail(byteBuffer2);

		Assert.assertSame(byteBuffer2, MailboxUtil.receiveMail(receipt2));

		Assert.assertSame(byteBuffer1, MailboxUtil.receiveMail(receipt1));
	}

	@Test
	public void testSendMailFail() {
		PropsTestUtil.setProps(Collections.emptyMap());

		MockIntraband mockIntraband = new MockIntraband();

		IOException iOException = new IOException();

		mockIntraband.setIOException(iOException);

		try {
			MailboxUtil.sendMail(
				new MockRegistrationReference(mockIntraband),
				ByteBuffer.allocate(0));

			Assert.fail();
		}
		catch (MailboxException me) {
			Assert.assertSame(iOException, me.getCause());
		}
	}

	@Test
	public void testSendMailSuccess() throws MailboxException {
		PropsTestUtil.setProps(Collections.emptyMap());

		final long receipt = 100;

		MockIntraband mockIntraband = new MockIntraband() {

			@Override
			protected Datagram processDatagram(Datagram datagram) {
				byte[] data = new byte[8];

				BigEndianCodec.putLong(data, 0, receipt);

				return Datagram.createResponseDatagram(
					datagram, ByteBuffer.wrap(data));
			}

		};

		Assert.assertEquals(
			receipt,
			MailboxUtil.sendMail(
				new MockRegistrationReference(mockIntraband),
				ByteBuffer.allocate(0)));
	}

	@Aspect
	public static class ReceiptStubAdvice {

		@Around(
			"execution(public long com.liferay.portal.kernel.nio.intraband." +
				"mailbox.MailboxUtil$ReceiptStub.getReceipt())"
		)
		public Object getReceipt(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			if (_throwException) {
				throw new IllegalStateException();
			}

			return proceedingJoinPoint.proceed();
		}

		private static volatile boolean _throwException;

	}

	protected Object createReceiptStub() throws Exception {
		String mailboxUtilClassName = MailboxUtil.class.getName();

		Class<?> clazz = Class.forName(
			mailboxUtilClassName.concat("$ReceiptStub"));

		Constructor<?> constructor = clazz.getDeclaredConstructor(long.class);

		constructor.setAccessible(true);

		Object object = constructor.newInstance(0);

		Assert.assertEquals(0, object.hashCode());

		return object;
	}

	private static class RecorderUncaughtExceptionHandler
		implements Thread.UncaughtExceptionHandler {

		@Override
		public void uncaughtException(Thread thread, Throwable throwable) {
			_thread = thread;
			_throwable = throwable;
		}

		private volatile Thread _thread;
		private volatile Throwable _throwable;

	}

}