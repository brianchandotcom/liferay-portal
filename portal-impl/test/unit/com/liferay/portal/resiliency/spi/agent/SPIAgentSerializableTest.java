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

package com.liferay.portal.resiliency.spi.agent;

import com.liferay.portal.kernel.io.BigEndianCodec;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.mailbox.MailboxException;
import com.liferay.portal.kernel.nio.intraband.mailbox.MailboxUtil;
import com.liferay.portal.kernel.nio.intraband.test.MockIntraband;
import com.liferay.portal.kernel.nio.intraband.test.MockRegistrationReference;
import com.liferay.portal.kernel.resiliency.spi.agent.annotation.Direction;
import com.liferay.portal.kernel.resiliency.spi.agent.annotation.DistributedRegistry;
import com.liferay.portal.kernel.resiliency.spi.agent.annotation.MatchType;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.ClassLoaderPool;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtilAdvice;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.ThreadLocalDistributor;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.runners.AspectJMockingNewClassLoaderJUnitTestRunner;

import java.io.EOFException;
import java.io.IOException;
import java.io.Serializable;

import java.net.URL;
import java.net.URLClassLoader;

import java.nio.ByteBuffer;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

/**
 * @author Shuyang Zhou
 */
@RunWith(AspectJMockingNewClassLoaderJUnitTestRunner.class)
public class SPIAgentSerializableTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Before
	public void setUp() {
		Thread currentThread = Thread.currentThread();

		_classLoader = new URLClassLoader(
			new URL[0], currentThread.getContextClassLoader());

		ClassLoaderPool.register(_SERVLET_CONTEXT_NAME, _classLoader);
	}

	@Test
	public void testExtractDistributedRequestAttributes() {
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

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			SPIAgentSerializable.class.getName(), Level.OFF);

		try {

			// Without log

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Map<String, Serializable> distributedRequestAttributes =
				SPIAgentSerializable.extractDistributedRequestAttributes(
					mockHttpServletRequest, Direction.DUPLEX);

			Assert.assertTrue(logRecords.isEmpty());
			Assert.assertEquals(1, distributedRequestAttributes.size());
			Assert.assertEquals(
				distributedSerializable,
				distributedRequestAttributes.get(distributedSerializable));

			// With log, warn

			logRecords = captureHandler.resetLogLevel(Level.WARNING);

			distributedRequestAttributes =
				SPIAgentSerializable.extractDistributedRequestAttributes(
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

			logRecords = captureHandler.resetLogLevel(Level.FINE);

			distributedRequestAttributes =
				SPIAgentSerializable.extractDistributedRequestAttributes(
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
		finally {
			captureHandler.close();
		}
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
			SPIAgentSerializable.extractRequestHeaders(mockHttpServletRequest);

		Assert.assertTrue(headers.isEmpty());

		String emptyHeaderName = "emptyHeaderName";

		mockHttpServletRequest.addHeader(
			emptyHeaderName, Collections.emptyList());

		String headerName = "headerName";

		List<String> headerValues = Arrays.asList("header1", "header2");

		mockHttpServletRequest.addHeader(headerName, headerValues);

		headers = SPIAgentSerializable.extractRequestHeaders(
			mockHttpServletRequest);

		Assert.assertEquals(2, headers.size());

		List<String> emptyHeaders = headers.get(
			StringUtil.toLowerCase(emptyHeaderName));

		Assert.assertNotNull(emptyHeaders);
		Assert.assertTrue(emptyHeaders.isEmpty());

		List<String> actualHeaderValues = headers.get(
			StringUtil.toLowerCase(headerName));

		Assert.assertNotNull(actualHeaderValues);
		Assert.assertTrue(headerValues.equals(actualHeaderValues));
	}

	@Test
	public void testExtractSessionAttributes() {
		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			SPIAgentSerializable.class.getName(), Level.OFF);

		try {

			// Without log, no portlet session

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			MockHttpServletRequest mockHttpServletRequest =
				new MockHttpServletRequest();

			MockHttpSession mockHttpSession = new MockHttpSession();

			String serializeableAttribute = "serializeableAttribute";

			mockHttpSession.setAttribute(
				serializeableAttribute, serializeableAttribute);

			final String servletContextName1 = "servletContextName1";

			String portletSessionAttributesName1 =
				WebKeys.PORTLET_SESSION_ATTRIBUTES.concat(servletContextName1);

			mockHttpSession.setAttribute(
				portletSessionAttributesName1, portletSessionAttributesName1);

			String servletContextName2 = "servletContextName2";

			String portletSessionAttributesName2 =
				WebKeys.PORTLET_SESSION_ATTRIBUTES.concat(servletContextName2);

			mockHttpSession.setAttribute(
				portletSessionAttributesName2, portletSessionAttributesName2);

			final String nonserializableAttribute = "nonserializableAttribute";

			mockHttpSession.setAttribute(
				nonserializableAttribute,
				new Object() {

					@Override
					public String toString() {
						return nonserializableAttribute;
					}

				});

			Portlet portlet = new PortletImpl() {

				@Override
				public String getContextName() {
					return servletContextName1;
				}

			};

			mockHttpServletRequest.setAttribute(
				WebKeys.SPI_AGENT_PORTLET, portlet);

			mockHttpServletRequest.setSession(mockHttpSession);

			Map<String, Serializable> sessionAttributes =
				SPIAgentSerializable.extractSessionAttributes(
					mockHttpServletRequest);

			Assert.assertTrue(logRecords.isEmpty());
			Assert.assertEquals(2, sessionAttributes.size());
			Assert.assertEquals(
				serializeableAttribute,
				sessionAttributes.get(serializeableAttribute));
			Assert.assertEquals(
				portletSessionAttributesName1,
				sessionAttributes.get(portletSessionAttributesName1));

			// Without log, with empty portlet session

			MockHttpSession portletMockHttpSession = new MockHttpSession();

			mockHttpServletRequest.setAttribute(
				WebKeys.PORTLET_SESSION, portletMockHttpSession);

			sessionAttributes = SPIAgentSerializable.extractSessionAttributes(
				mockHttpServletRequest);

			Assert.assertNull(
				mockHttpServletRequest.getAttribute(WebKeys.PORTLET_SESSION));
			Assert.assertTrue(logRecords.isEmpty());
			Assert.assertEquals(2, sessionAttributes.size());
			Assert.assertEquals(
				serializeableAttribute,
				sessionAttributes.get(serializeableAttribute));

			Map<String, Serializable> portletSessionAttributes =
				(Map<String, Serializable>)sessionAttributes.get(
					portletSessionAttributesName1);

			Assert.assertNotNull(portletSessionAttributes);
			Assert.assertTrue(portletSessionAttributes.isEmpty());

			// Without log, with nonempty portlet session

			portletMockHttpSession.setAttribute(
				serializeableAttribute, serializeableAttribute);

			portletMockHttpSession.setAttribute(
				nonserializableAttribute,
				new Object() {

					@Override
					public String toString() {
						return nonserializableAttribute;
					}

				});

			mockHttpServletRequest.setAttribute(
				WebKeys.PORTLET_SESSION, portletMockHttpSession);

			sessionAttributes = SPIAgentSerializable.extractSessionAttributes(
				mockHttpServletRequest);

			Assert.assertNull(
				mockHttpServletRequest.getAttribute(WebKeys.PORTLET_SESSION));
			Assert.assertTrue(logRecords.isEmpty());
			Assert.assertEquals(2, sessionAttributes.size());
			Assert.assertEquals(
				serializeableAttribute,
				sessionAttributes.get(serializeableAttribute));

			portletSessionAttributes =
				(Map<String, Serializable>)sessionAttributes.get(
					portletSessionAttributesName1);

			Assert.assertNotNull(portletSessionAttributes);
			Assert.assertEquals(1, portletSessionAttributes.size());
			Assert.assertEquals(
				serializeableAttribute,
				portletSessionAttributes.get(serializeableAttribute));

			// With log, no portlet session

			logRecords = captureHandler.resetLogLevel(Level.WARNING);

			sessionAttributes = SPIAgentSerializable.extractSessionAttributes(
				mockHttpServletRequest);

			Assert.assertEquals(1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Nonserializable session attribute name " +
					nonserializableAttribute + " with value " +
						nonserializableAttribute, logRecord.getMessage());

			Assert.assertEquals(2, sessionAttributes.size());
			Assert.assertEquals(
				serializeableAttribute,
				sessionAttributes.get(serializeableAttribute));
			Assert.assertEquals(
				portletSessionAttributesName1,
				sessionAttributes.get(portletSessionAttributesName1));

			// With log, with nonempty portlet session

			logRecords = captureHandler.resetLogLevel(Level.WARNING);

			mockHttpServletRequest.setAttribute(
				WebKeys.PORTLET_SESSION, portletMockHttpSession);

			sessionAttributes = SPIAgentSerializable.extractSessionAttributes(
				mockHttpServletRequest);

			Assert.assertNull(
				mockHttpServletRequest.getAttribute(WebKeys.PORTLET_SESSION));
			Assert.assertEquals(2, logRecords.size());

			logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Nonserializable session attribute name " +
					nonserializableAttribute + " with value " +
						nonserializableAttribute, logRecord.getMessage());

			logRecord = logRecords.get(1);

			Assert.assertEquals(
				"Nonserializable session attribute name " +
					nonserializableAttribute + " with value " +
						nonserializableAttribute, logRecord.getMessage());

			Assert.assertEquals(2, sessionAttributes.size());
			Assert.assertEquals(
				serializeableAttribute,
				sessionAttributes.get(serializeableAttribute));

			portletSessionAttributes =
				(Map<String, Serializable>)sessionAttributes.get(
					portletSessionAttributesName1);

			Assert.assertNotNull(portletSessionAttributes);
			Assert.assertEquals(1, portletSessionAttributes.size());
			Assert.assertEquals(
				serializeableAttribute,
				portletSessionAttributes.get(serializeableAttribute));
		}
		finally {
			captureHandler.close();
		}
	}

	@AdviseWith(
		adviceClasses = {DeserializerAdvice.class, PropsUtilAdvice.class}
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

		final AtomicLong receiptReference = new AtomicLong();

		MockIntraband mockIntraband = new MockIntraband() {

			@Override
			protected Datagram processDatagram(Datagram datagram) {
				try {
					long receipt = ReflectionTestUtil.invoke(
						MailboxUtil.class, "depositMail",
						new Class<?>[] {ByteBuffer.class},
						datagram.getDataByteBuffer());

					receiptReference.set(receipt);

					byte[] data = new byte[8];

					BigEndianCodec.putLong(data, 0, receipt);

					return Datagram.createResponseDatagram(
						datagram, ByteBuffer.wrap(data));
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
			}

		};

		SPIAgentSerializable agentSerializable = new SPIAgentSerializable(
			_SERVLET_CONTEXT_NAME);

		IOException ioException = new IOException();

		mockIntraband.setIOException(ioException);

		try {
			agentSerializable.writeTo(
				new MockRegistrationReference(mockIntraband),
				new UnsyncByteArrayOutputStream());

			Assert.fail();
		}
		catch (IOException ioe) {
			Throwable throwable = ioe.getCause();

			Assert.assertSame(MailboxException.class, throwable.getClass());
			Assert.assertSame(ioException, throwable.getCause());
		}

		// Successfully send

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		mockIntraband.setIOException(null);

		agentSerializable.writeTo(
			new MockRegistrationReference(mockIntraband),
			unsyncByteArrayOutputStream);

		long actualReceipt = BigEndianCodec.getLong(
			unsyncByteArrayOutputStream.unsafeGetByteArray(), 0);

		Assert.assertEquals(receiptReference.get(), actualReceipt);

		// Incomplete receipt

		try {
			SPIAgentSerializable.readFrom(
				new UnsyncByteArrayInputStream(new byte[7]));

			Assert.fail();
		}
		catch (EOFException eofe) {
		}

		// No such receipt

		byte[] badReceiptData = new byte[8];

		BigEndianCodec.putLong(badReceiptData, 0, actualReceipt + 1);

		try {
			SPIAgentSerializable.readFrom(
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

				if (name.equals(SPIAgentSerializable.class.getName())) {
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
			SPIAgentSerializable.readFrom(
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

		SPIAgentSerializable receivedAgentSerializable =
			SPIAgentSerializable.readFrom(
				new UnsyncByteArrayInputStream(receiptData));

		Assert.assertNotNull(receivedAgentSerializable);

		Assert.assertSame(
			_classLoader, DeserializerAdvice.getContextClassLoader());
	}

	@Test
	public void testThreadLocalTransfer() throws Exception {
		ThreadLocalDistributor threadLocalDistributor =
			new ThreadLocalDistributor();

		threadLocalDistributor.setThreadLocalSources(
			Arrays.asList(
				new KeyValuePair(
					SPIAgentSerializableTest.class.getName(), "_threadLocal")));

		threadLocalDistributor.afterPropertiesSet();

		Serializable[] serializables = ReflectionTestUtil.getFieldValue(
			threadLocalDistributor, "_threadLocalValues");

		Assert.assertNotNull(serializables);
		Assert.assertEquals(1, serializables.length);
		Assert.assertNull(serializables[0]);

		String threadLocalValue = "threadLocalValue";

		_threadLocal.set(threadLocalValue);

		SPIAgentSerializable agentSerializable = new SPIAgentSerializable(
			_SERVLET_CONTEXT_NAME);

		Assert.assertNull(agentSerializable.threadLocalDistributors);

		agentSerializable.captureThreadLocals();

		ThreadLocalDistributor[] threadLocalDistributors =
			agentSerializable.threadLocalDistributors;

		Assert.assertNotNull(threadLocalDistributors);
		Assert.assertEquals(1, threadLocalDistributors.length);
		Assert.assertSame(threadLocalDistributor, threadLocalDistributors[0]);

		serializables = ReflectionTestUtil.getFieldValue(
			threadLocalDistributor, "_threadLocalValues");

		Assert.assertNotNull(serializables);
		Assert.assertEquals(1, serializables.length);
		Assert.assertEquals(threadLocalValue, serializables[0]);

		_threadLocal.remove();

		agentSerializable.restoreThreadLocals();

		Assert.assertEquals(threadLocalValue, _threadLocal.get());

		_threadLocal.remove();
	}

	@Aspect
	public static class DeserializerAdvice {

		public static ClassLoader getContextClassLoader() {
			return _contextClassLoader;
		}

		@Around(
			"execution(public * " +
				"com.liferay.portal.kernel.io.Deserializer.readObject())")
		public Object readObject(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			Thread currentThread = Thread.currentThread();

			_contextClassLoader = currentThread.getContextClassLoader();

			return proceedingJoinPoint.proceed();
		}

		private static ClassLoader _contextClassLoader;

	}

	private static final String _SERVLET_CONTEXT_NAME = "SERVLET_CONTEXT_NAME";

	private static final ThreadLocal<String> _threadLocal =
		new ThreadLocal<String>();

	private ClassLoader _classLoader;

}