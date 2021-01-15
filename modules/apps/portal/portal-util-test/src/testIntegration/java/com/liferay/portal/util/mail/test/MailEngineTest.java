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

package com.liferay.portal.util.mail.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.petra.mail.MailEngine;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.mail.MailServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.portal.util.PropsUtil;

import java.io.Closeable;
import java.io.IOException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.net.URL;
import java.net.URLClassLoader;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.mail.internet.InternetAddress;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
public class MailEngineTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@Test
	public void testSendMail() throws Exception {
		MailEngine.send(
			new MailMessage(
				new InternetAddress("from@test.com"),
				new InternetAddress("to@test.com"), "Hello",
				"My name is Inigo Montoya.", true));

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());

		List<com.liferay.portal.test.mail.MailMessage> mailMessages =
			MailServiceTestUtil.getMailMessages(
				"Body", "My name is Inigo Montoya.");

		Assert.assertEquals(mailMessages.toString(), 1, mailMessages.size());

		mailMessages = MailServiceTestUtil.getMailMessages("Subject", "Hello");

		Assert.assertEquals(mailMessages.toString(), 1, mailMessages.size());

		mailMessages = MailServiceTestUtil.getMailMessages("To", "to@test.com");

		Assert.assertEquals(mailMessages.toString(), 1, mailMessages.size());
	}

	@Test
	public void testSendMailWithLimit() throws Throwable {
		MailMessage mailMessage = new MailMessage(
			new InternetAddress("from@test.com"),
			new InternetAddress("to@test.com"), "Hello",
			"My name is Inigo Montoya.", true);

		try (LimitedMailEngine limitedMailEngine = new LimitedMailEngine(
				2L, Time.YEAR / Time.SECOND)) {

			limitedMailEngine.send(mailMessage);
			limitedMailEngine.send(mailMessage);

			try {
				limitedMailEngine.send(mailMessage);

				Assert.fail();
			}
			catch (Throwable throwable) {
				Assert.assertEquals(
					"Unable to exceed maximum number of allowed mail messages",
					throwable.getMessage());
			}

			limitedMailEngine.setLastResetTime(
				System.currentTimeMillis() - Time.YEAR - Time.SECOND);

			limitedMailEngine.send(mailMessage);
			limitedMailEngine.send(mailMessage);

			try {
				limitedMailEngine.send(mailMessage);

				Assert.fail();
			}
			catch (Throwable throwable) {
				Assert.assertEquals(
					"Unable to exceed maximum number of allowed mail messages",
					throwable.getMessage());
			}
		}
	}

	private static class LimitedMailEngine implements Closeable {

		@Override
		public void close() throws IOException {
			PropsUtil.set(
				PropsKeys.DATA_LIMIT_MAX_MAIL_MESSAGE_COUNT,
				_originalMaxMailMessageCount);
			PropsUtil.set(
				PropsKeys.DATA_LIMIT_MAX_MAIL_MESSAGE_PERIOD,
				_originalMaxMailMessagePeriod);
		}

		public void send(MailMessage mailMessage) throws Throwable {
			try {
				_sendMethod.invoke(null, mailMessage);
			}
			catch (InvocationTargetException invocationTargetException) {
				throw invocationTargetException.getTargetException();
			}
		}

		public void setLastResetTime(long lastResetTime) {
			_lastResetTime.set(lastResetTime);
		}

		private LimitedMailEngine(
				long maxMailMessageCount, long maxMailMessagePeriod)
			throws Exception {

			_originalMaxMailMessageCount = PropsUtil.get(
				PropsKeys.DATA_LIMIT_MAX_MAIL_MESSAGE_COUNT);
			_originalMaxMailMessagePeriod = PropsUtil.get(
				PropsKeys.DATA_LIMIT_MAX_MAIL_MESSAGE_PERIOD);

			PropsUtil.set(
				PropsKeys.DATA_LIMIT_MAX_MAIL_MESSAGE_COUNT,
				String.valueOf(maxMailMessageCount));
			PropsUtil.set(
				PropsKeys.DATA_LIMIT_MAX_MAIL_MESSAGE_PERIOD,
				String.valueOf(maxMailMessagePeriod));

			ProtectionDomain protectionDomain =
				MailEngine.class.getProtectionDomain();

			CodeSource codeSource = protectionDomain.getCodeSource();

			ClassLoader classLoader = new URLClassLoader(
				new URL[] {codeSource.getLocation()},
				PortalException.class.getClassLoader());

			Class<?> clazz = classLoader.loadClass(MailEngine.class.getName());

			_sendMethod = ReflectionUtil.getDeclaredMethod(
				clazz, "send", MailMessage.class);

			Field field = ReflectionUtil.getDeclaredField(
				clazz, "_lastResetTime");

			_lastResetTime = (AtomicLong)field.get(null);
		}

		private final AtomicLong _lastResetTime;
		private final String _originalMaxMailMessageCount;
		private final String _originalMaxMailMessagePeriod;
		private final Method _sendMethod;

	}

}