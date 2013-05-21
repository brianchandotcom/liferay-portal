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

package com.liferay.portal.resiliency.spi.agent;

import com.liferay.portal.kernel.io.BigEndianCodec;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.MockIntraband;
import com.liferay.portal.kernel.nio.intraband.MockRegistrationReference;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.mailbox.MailboxException;
import com.liferay.portal.kernel.nio.intraband.mailbox.MailboxUtil;
import com.liferay.portal.kernel.resiliency.spi.agent.annotation.Direction;
import com.liferay.portal.kernel.resiliency.spi.agent.annotation.DistributedRegistry;
import com.liferay.portal.kernel.resiliency.spi.agent.annotation.MatchType;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.util.ClassLoaderPool;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtilAdvice;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.ThreadLocalDistributor;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.AspectJMockingNewClassLoaderJUnitTestRunner;

import java.io.EOFException;
import java.io.IOException;
import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.nio.ByteBuffer;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

/**
 * @author Shuyang Zhou
 */
@RunWith(AspectJMockingNewClassLoaderJUnitTestRunner.class)
public class AgentSerializableTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Test
	public void testExtractDistributedRequestAttributes() {

		// Without log

		String distributedSerializable = "DISTRIBUTED_SERIALIZABLE";

		DistributedRegistry.registerDistributed(
			distributedSerializable, Direction.DUPLEX, MatchType.EXACT);

		final String distributedNonserializable = "DISTRIBUTED_NONSERIALIZABLE";

		DistributedRegistry.registerDistributed(
			distributedNonserializable, Direction.DUPLEX, MatchType.EXACT);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			distributedSerializable, distributedSerializable);
		mockHttpServletRequest.setAttribute(
			distributedNonserializable,
			new Object() {

				@Override
				public String toString() {
					return distributedNonserializable;
				}

			});

		String nondistributed = "NONDISTRIBUTED";

		mockHttpServletRequest.setAttribute(nondistributed, nondistributed);

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			AgentSerializable.class.getName(), Level.OFF);

		Map<String, Serializable> distributedRequestAttributes =
			AgentSerializable.extractDistributedRequestAttributes(
				mockHttpServletRequest, Direction.DUPLEX);

		Assert.assertTrue(logRecords.isEmpty());
		Assert.assertEquals(1, distributedRequestAttributes.size());
		Assert.assertEquals(
			distributedSerializable,
			distributedRequestAttributes.get(distributedSerializable));

		// With log, warn

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			AgentSerializable.class.getName(), Level.WARNING);

		distributedRequestAttributes =
			AgentSerializable.extractDistributedRequestAttributes(
				mockHttpServletRequest, Direction.DUPLEX);

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Nonserializable distributed request attribute name " +
				distributedNonserializable + " with value " +
					distributedNonserializable, logRecord.getMessage());

		Assert.assertEquals(1, distributedRequestAttributes.size());
		Assert.assertEquals(
			distributedSerializable,
			distributedRequestAttributes.get(distributedSerializable));

		// With log, debug

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			AgentSerializable.class.getName(), Level.FINE);

		distributedRequestAttributes =
			AgentSerializable.extractDistributedRequestAttributes(
				mockHttpServletRequest, Direction.DUPLEX);

		Assert.assertEquals(2, logRecords.size());

		logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Nonserializable distributed request attribute name " +
				distributedNonserializable + " with value " +
					distributedNonserializable, logRecord.getMessage());

		logRecord = logRecords.get(1);

		Assert.assertEquals(
			"Nondistributed request attribute name " + nondistributed +
				" with direction DUPLEX and value " + nondistributed,
			logRecord.getMessage());

		Assert.assertEquals(1, distributedRequestAttributes.size());
		Assert.assertEquals(
			distributedSerializable,
			distributedRequestAttributes.get(distributedSerializable));
	}

	@Test
	public void testExtractRequestHeaders() {
		final String nullHeaderName = "nullHeaderName";

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest() {

				@Override
				public Enumeration<String> getHeaders(String name) {
					if (nullHeaderName.equals(name)) {
						return null;
					}

					return super.getHeaders(name);
				}

			};

		mockHttpServletRequest.addHeader(HttpHeaders.ACCEPT_ENCODING, "x-zip");
		mockHttpServletRequest.addHeader(HttpHeaders.COOKIE, "a=b");
		mockHttpServletRequest.addHeader(nullHeaderName, nullHeaderName);

		Map<String, List<String>> headers =
			AgentSerializable.extractRequestHeaders(mockHttpServletRequest);

		Assert.assertTrue(headers.isEmpty());

		String emptyHeaderName = "emptyHeaderName";

		mockHttpServletRequest.addHeader(
			emptyHeaderName, Collections.emptyList());

		String headerName = "headerName";

		List<String> headerValues = Arrays.asList("header1", "header2");

		mockHttpServletRequest.addHeader(headerName, headerValues);

		headers = AgentSerializable.extractRequestHeaders(
			mockHttpServletRequest);

		Assert.assertEquals(2, headers.size());

		List<String> emptyHeaders = headers.get(emptyHeaderName.toLowerCase());

		Assert.assertNotNull(emptyHeaders);
		Assert.assertTrue(emptyHeaders.isEmpty());

		List<String> actualHeaderValues = headers.get(headerName.toLowerCase());

		Assert.assertNotNull(actualHeaderValues);
		Assert.assertTrue(headerValues.equals(actualHeaderValues));
	}

	@Test
	public void testExtractSessionAttributes() {

		// Without log

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			AgentSerializable.class.getName(), Level.OFF);

		MockHttpSession mockHttpSession = new MockHttpSession();

		String serializeableAttribute = "serializeableAttribute";

		mockHttpSession.setAttribute(
			serializeableAttribute, serializeableAttribute);

		final String nonserializableAttribute = "nonserializableAttribute";

		mockHttpSession.setAttribute(
			nonserializableAttribute,
			new Object() {

				@Override
				public String toString() {
					return nonserializableAttribute;
				}

			});

		Map<String, Serializable> sessionAttributes =
			AgentSerializable.extractSessionAttributes(mockHttpSession);

		Assert.assertTrue(logRecords.isEmpty());
		Assert.assertEquals(1, sessionAttributes.size());
		Assert.assertEquals(
			serializeableAttribute,
			sessionAttributes.get(serializeableAttribute));

		// With log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			AgentSerializable.class.getName(), Level.WARNING);

		sessionAttributes = AgentSerializable.extractSessionAttributes(
			mockHttpSession);

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Nonserializable session attribute name " +
				nonserializableAttribute + " with value " +
					nonserializableAttribute, logRecord.getMessage());

		Assert.assertEquals(1, sessionAttributes.size());
		Assert.assertEquals(
			serializeableAttribute,
			sessionAttributes.get(serializeableAttribute));
	}

	@AdviseWith(
		adviceClasses = {PropsUtilAdvice.class}
	)
	@Test
	public void testSerialization() throws IOException {

		// Unable to send

		PropsUtilAdvice.setProps(
			PropsKeys.INTRABAND_MAILBOX_REAPER_THREAD_ENABLED,
			Boolean.FALSE.toString());
		PropsUtilAdvice.setProps(
			PropsKeys.INTRABAND_MAILBOX_STORAGE_LIFE,
			String.valueOf(Long.MAX_VALUE));

		final AtomicBoolean throwException = new AtomicBoolean(true);
		final AtomicLong receiptReference = new AtomicLong();

		MockIntraband mockIntraband = new MockIntraband() {

			@Override
			public Datagram sendSyncDatagram(
					RegistrationReference registrationReference,
					Datagram datagram)
				throws IOException {

				if (throwException.get()) {
					throw new IOException("Unable to send");
				}

				try {
					Method depositMailMethod = ReflectionUtil.getDeclaredMethod(
						MailboxUtil.class, "depositMail", ByteBuffer.class);

					long receipt = (Long)depositMailMethod.invoke(
						null, datagram.getDataByteBuffer());

					receiptReference.set(receipt);

					byte[] data = new byte[8];

					BigEndianCodec.putLong(data, 0, receipt);

					return Datagram.createResponseDatagram(
						datagram, ByteBuffer.wrap(data));
				}
				catch (Exception e) {
					throw new IOException(e);
				}
			}

		};

		AgentSerializable agentSerializable = new AgentSerializable();

		try {
			agentSerializable.writeTo(
				new MockRegistrationReference(mockIntraband),
				new UnsyncByteArrayOutputStream());

			Assert.fail();
		}
		catch (IOException ioe) {
			Throwable throwable = ioe.getCause();

			Assert.assertSame(MailboxException.class, throwable.getClass());

			throwable = throwable.getCause();

			Assert.assertSame(IOException.class, throwable.getClass());
			Assert.assertEquals("Unable to send", throwable.getMessage());
		}

		// Successfully send

		throwException.set(false);

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		agentSerializable.writeTo(
			new MockRegistrationReference(mockIntraband),
			unsyncByteArrayOutputStream);

		long actualReceipt = BigEndianCodec.getLong(
			unsyncByteArrayOutputStream.unsafeGetByteArray(), 0);

		Assert.assertEquals(receiptReference.get(), actualReceipt);

		// Incomplete receipt

		try {
			AgentSerializable.readFrom(
				new UnsyncByteArrayInputStream(new byte[7]));

			Assert.fail();
		}
		catch (EOFException eofe) {
		}

		// No such receipt

		byte[] badReceiptData = new byte[8];

		BigEndianCodec.putLong(badReceiptData, 0, actualReceipt + 1);

		try {
			AgentSerializable.readFrom(
				new UnsyncByteArrayInputStream(badReceiptData));

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"No mail with receipt " + (actualReceipt + 1),
				iae.getMessage());
		}

		// Class not found

		ClassLoader incapableClassLoader = new ClassLoader() {

			@Override
			public Class<?> loadClass(String name)
				throws ClassNotFoundException {

				if (name.equals(AgentSerializable.class.getName())) {
					throw new ClassNotFoundException();
				}

				return super.loadClass(name);
			}

		};

		ClassLoader oldClassLoader = ClassLoaderPool.getClassLoader(
			StringPool.BLANK);

		ClassLoaderPool.register(StringPool.BLANK, incapableClassLoader);

		byte[] receiptData = new byte[8];

		BigEndianCodec.putLong(receiptData, 0, actualReceipt);

		try {
			AgentSerializable.readFrom(
				new UnsyncByteArrayInputStream(receiptData));

			Assert.fail();
		}
		catch (IOException ioe) {
			Throwable throwable = ioe.getCause();

			Assert.assertSame(
				ClassNotFoundException.class, throwable.getClass());
		}
		finally {
			ClassLoaderPool.register(StringPool.BLANK, oldClassLoader);
		}

		// Successfully receive

		unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();

		agentSerializable.writeTo(
			new MockRegistrationReference(mockIntraband),
			unsyncByteArrayOutputStream);

		actualReceipt = BigEndianCodec.getLong(
			unsyncByteArrayOutputStream.unsafeGetByteArray(), 0);

		Assert.assertEquals(receiptReference.get(), actualReceipt);

		BigEndianCodec.putLong(receiptData, 0, actualReceipt);

		AgentSerializable receivedAgentSerializable =
			AgentSerializable.readFrom(
				new UnsyncByteArrayInputStream(receiptData));

		Assert.assertNotNull(receivedAgentSerializable);
	}

	@Test
	public void testThreadLocalTransfer() throws Exception {
		ThreadLocalDistributor threadLocalDistributor =
			new ThreadLocalDistributor();

		threadLocalDistributor.setThreadLocalSources(
			Arrays.asList(
				new KeyValuePair(
					AgentSerializableTest.class.getName(), "_threadLocal")));

		threadLocalDistributor.afterPropertiesSet();

		Field threadLocalValuesField = ReflectionUtil.getDeclaredField(
			ThreadLocalDistributor.class, "_threadLocalValues");

		Serializable[] serializables =
			(Serializable[])threadLocalValuesField.get(threadLocalDistributor);

		Assert.assertNotNull(serializables);
		Assert.assertEquals(1, serializables.length);
		Assert.assertNull(serializables[0]);

		String threadLocalValue = "threadLocalValue";

		_threadLocal.set(threadLocalValue);

		AgentSerializable agentSerializable = new AgentSerializable();

		Assert.assertNull(agentSerializable.threadLocalDistributors);

		agentSerializable.captureThreadLocals();

		ThreadLocalDistributor[] threadLocalDistributors =
			agentSerializable.threadLocalDistributors;

		Assert.assertNotNull(threadLocalDistributors);
		Assert.assertEquals(1, threadLocalDistributors.length);
		Assert.assertSame(threadLocalDistributor, threadLocalDistributors[0]);

		serializables = (Serializable[])threadLocalValuesField.get(
			threadLocalDistributor);

		Assert.assertNotNull(serializables);
		Assert.assertEquals(1, serializables.length);
		Assert.assertEquals(threadLocalValue, serializables[0]);

		_threadLocal.remove();

		agentSerializable.restoreThreadLocals();

		Assert.assertEquals(threadLocalValue, _threadLocal.get());

		_threadLocal.remove();
	}

	private static ThreadLocal<String> _threadLocal = new ThreadLocal<String>();

}