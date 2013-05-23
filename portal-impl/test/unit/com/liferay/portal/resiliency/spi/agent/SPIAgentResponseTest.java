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

import com.liferay.portal.kernel.resiliency.PortalResiliencyException;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.servlet.MetaInfoCacheServletResponse;
import com.liferay.portal.kernel.servlet.StubHttpServletResponse;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.ThreadLocalDistributor;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.util.WebKeys;

import java.io.IOException;
import java.io.Serializable;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Shuyang Zhou
 */
public class SPIAgentResponseTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@BeforeClass
	public static void setUpClass() throws Exception {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());

		PropsUtil.setProps(new PropsImpl());

		ThreadLocalDistributor threadLocalDistributor =
			new ThreadLocalDistributor();

		threadLocalDistributor.setThreadLocalSources(
			Arrays.asList(
				new KeyValuePair(
					SPIAgentResponseTest.class.getName(), "_threadLocal")));

		threadLocalDistributor.afterPropertiesSet();
	}

	@Before
	public void setUp() {
		MockHttpServletRequest originalRequest = new MockHttpServletRequest();

		HttpSession session = originalRequest.getSession();

		session.setAttribute(_sessionAttribute1, _sessionAttribute1);
		session.setAttribute(_sessionAttribute2, _sessionAttribute2);

		_mockHttpServletRequest = new MockHttpServletRequest();

		_mockHttpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_REQUEST, new SPIAgentRequest(originalRequest));

		RequestAttributes.setRequestAttributes(_mockHttpServletRequest);
	}

	@Test
	public void testCaptureRequestSessionAttributes() {
		SPIAgentResponse spiAgentResponse = new SPIAgentResponse();

		String threadLocalValue = "threadLocalValue";

		_threadLocal.set(threadLocalValue);

		HttpSession session = _mockHttpServletRequest.getSession();

		session.setAttribute(_sessionAttribute1, _sessionAttribute1);
		session.setAttribute(_sessionAttribute2, null);
		session.setAttribute(_sessionAttribute3, _sessionAttribute3);

		spiAgentResponse.captureRequestSessionAttributes(
			_mockHttpServletRequest);

		_threadLocal.remove();

		Map<String, Serializable> distributedRequestAttributes =
			spiAgentResponse.distributedRequestAttributes;

		Assert.assertEquals(2, distributedRequestAttributes.size());
		Assert.assertEquals(
			RequestAttributes.ATTRIBUTE1,
			distributedRequestAttributes.get(RequestAttributes.ATTRIBUTE1));
		Assert.assertEquals(
			RequestAttributes.ATTRIBUTE3,
			distributedRequestAttributes.get(RequestAttributes.ATTRIBUTE3));

		Map<String, Serializable> deltaSessionAttributes =
			spiAgentResponse.deltaSessionAttributes;

		Assert.assertEquals(3, deltaSessionAttributes.size());
		Assert.assertEquals(
			_sessionAttribute1, deltaSessionAttributes.get(_sessionAttribute1));
		Assert.assertNull(deltaSessionAttributes.get(_sessionAttribute2));
		Assert.assertEquals(
			_sessionAttribute3, deltaSessionAttributes.get(_sessionAttribute3));

		spiAgentResponse.restoreThreadLocals();

		Assert.assertEquals(threadLocalValue, _threadLocal.get());

		_threadLocal.remove();
	}

	@Test
	public void testCaptureResponse() throws IOException {

		// Not portal resiliency action

		SPIAgentResponse spiAgentResponse = new SPIAgentResponse();

		spiAgentResponse.captureResponse(
			new MockHttpServletRequest(),
			new BufferCacheServletResponse(new MockHttpServletResponse()));

		Assert.assertFalse(spiAgentResponse.portalResiliencyResponse);
		Assert.assertNull(spiAgentResponse.metaData);
		Assert.assertNull(spiAgentResponse.byteData);
		Assert.assertNull(spiAgentResponse.stringData);

		// Portal resiliency action, byte model output, empty

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.PORTAL_RESILIENCY_ACTION, true);

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(new MockHttpServletResponse());

		bufferCacheServletResponse.setByteBuffer(ByteBuffer.allocate(0));

		spiAgentResponse.captureResponse(
			mockHttpServletRequest, bufferCacheServletResponse);

		Assert.assertTrue(spiAgentResponse.portalResiliencyResponse);
		Assert.assertNotNull(spiAgentResponse.metaData);
		Assert.assertNull(spiAgentResponse.byteData);
		Assert.assertNull(spiAgentResponse.stringData);

		// Portal resiliency action, byte model output, native buffer

		byte[] byteArray = new byte[] {(
			byte)0, (byte)1, (byte)2, (byte)3, (byte)4, (byte)5};

		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(byteArray.length);

		byteBuffer.put(byteArray);

		byteBuffer.clear();

		bufferCacheServletResponse.setByteBuffer(byteBuffer);

		spiAgentResponse.captureResponse(
			mockHttpServletRequest, bufferCacheServletResponse);

		Assert.assertTrue(spiAgentResponse.portalResiliencyResponse);
		Assert.assertNotNull(spiAgentResponse.metaData);
		Assert.assertArrayEquals(byteArray, spiAgentResponse.byteData);
		Assert.assertNull(spiAgentResponse.stringData);

		// Portal resiliency action, byte model output, whole buffer

		bufferCacheServletResponse.setByteBuffer(ByteBuffer.wrap(byteArray));

		spiAgentResponse.captureResponse(
			mockHttpServletRequest, bufferCacheServletResponse);

		Assert.assertTrue(spiAgentResponse.portalResiliencyResponse);
		Assert.assertNotNull(spiAgentResponse.metaData);
		Assert.assertSame(byteArray, spiAgentResponse.byteData);
		Assert.assertNull(spiAgentResponse.stringData);

		// Portal resiliency action, byte model output, partial buffer

		bufferCacheServletResponse.setByteBuffer(
			ByteBuffer.wrap(byteArray, 2, 2));

		spiAgentResponse.captureResponse(
			mockHttpServletRequest, bufferCacheServletResponse);

		Assert.assertTrue(spiAgentResponse.portalResiliencyResponse);
		Assert.assertNotNull(spiAgentResponse.metaData);
		Assert.assertArrayEquals(
			new byte[]{(byte)2, (byte)3}, spiAgentResponse.byteData);
		Assert.assertNull(spiAgentResponse.stringData);

		// Portal resiliency action, char model output, empty

		bufferCacheServletResponse.setString(StringPool.BLANK);

		spiAgentResponse.captureResponse(
			mockHttpServletRequest, bufferCacheServletResponse);

		Assert.assertTrue(spiAgentResponse.portalResiliencyResponse);
		Assert.assertNotNull(spiAgentResponse.metaData);
		Assert.assertNull(spiAgentResponse.byteData);
		Assert.assertNull(spiAgentResponse.stringData);

		// Portal resiliency action, char model output, without footer

		String content = "content";

		bufferCacheServletResponse.setString(content);

		spiAgentResponse.captureResponse(
			mockHttpServletRequest, bufferCacheServletResponse);

		Assert.assertTrue(spiAgentResponse.portalResiliencyResponse);
		Assert.assertNotNull(spiAgentResponse.metaData);
		Assert.assertNull(spiAgentResponse.byteData);
		Assert.assertSame(content, spiAgentResponse.stringData);

		// Portal resiliency action, char model output, not html, without footer

		bufferCacheServletResponse.setString(content);

		mockHttpServletRequest.setParameter("prpsf", StringPool.TRUE);

		spiAgentResponse.captureResponse(
			mockHttpServletRequest, bufferCacheServletResponse);

		Assert.assertTrue(spiAgentResponse.portalResiliencyResponse);
		Assert.assertNotNull(spiAgentResponse.metaData);
		Assert.assertNull(spiAgentResponse.byteData);
		Assert.assertSame(content, spiAgentResponse.stringData);

		// Portal resiliency action, char model output, with footer

		content = "<body>content</body>";

		bufferCacheServletResponse.setString(content);

		mockHttpServletRequest.setServerPort(1234);

		PortalUtil.setPortalPort(mockHttpServletRequest);

		mockHttpServletRequest.setParameter("prpsf", StringPool.TRUE);

		spiAgentResponse.captureResponse(
			mockHttpServletRequest, bufferCacheServletResponse);

		Assert.assertTrue(spiAgentResponse.portalResiliencyResponse);
		Assert.assertNotNull(spiAgentResponse.metaData);
		Assert.assertNull(spiAgentResponse.byteData);
		Assert.assertEquals(
			"<body>content<div class=\"portlet-msg-info\"><strong>This " +
				"portlet is from SPI 1234</strong></div></body>",
			spiAgentResponse.stringData);
	}

	@Test
	public void testPopulate() throws IOException, PortalResiliencyException {

		// Exception

		SPIAgentResponse spiAgentResponse = new SPIAgentResponse();

		Exception exception = new Exception();

		spiAgentResponse.setException(exception);

		try {
			spiAgentResponse.populate(
				new MockHttpServletRequest(), new MockHttpServletResponse());

			Assert.fail();
		}
		catch (PortalResiliencyException pre) {
			Assert.assertEquals("SPI exception", pre.getMessage());
			Assert.assertSame(exception, pre.getCause());
		}

		// Not portal resiliency response

		spiAgentResponse.setException(null);

		spiAgentResponse.populate(
			new MockHttpServletRequest(), new MockHttpServletResponse());

		// Distributed request attributes, without type setting

		spiAgentResponse.portalResiliencyResponse = true;

		Map<String, Serializable> distributedRequestAttributes =
			new HashMap<String, Serializable>();

		distributedRequestAttributes.put(
			RequestAttributes.ATTRIBUTE1, RequestAttributes.ATTRIBUTE1);
		distributedRequestAttributes.put(
			RequestAttributes.ATTRIBUTE3, RequestAttributes.ATTRIBUTE3);

		spiAgentResponse.distributedRequestAttributes =
			distributedRequestAttributes;

		Map<String, Serializable> deltaSessionAttributes =
			new HashMap<String, Serializable>();

		deltaSessionAttributes.put(_sessionAttribute1, _sessionAttribute1);
		deltaSessionAttributes.put(_sessionAttribute2, _sessionAttribute2);

		spiAgentResponse.deltaSessionAttributes = deltaSessionAttributes;

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(new MockHttpServletResponse());

		spiAgentResponse.metaData = metaInfoCacheServletResponse.getMetaData();

		spiAgentResponse.threadLocalDistributors =
			new ThreadLocalDistributor[0];

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		spiAgentResponse.populate(
			mockHttpServletRequest, new MockHttpServletResponse());

		Enumeration<String> requestAttributeNameEnumeration =
			mockHttpServletRequest.getAttributeNames();

		List<String> requestAttributeNames = ListUtil.fromEnumeration(
			requestAttributeNameEnumeration);

		Assert.assertEquals(2, requestAttributeNames.size());
		Assert.assertTrue(
			requestAttributeNames.contains(RequestAttributes.ATTRIBUTE1));
		Assert.assertTrue(
			requestAttributeNames.contains(RequestAttributes.ATTRIBUTE3));
		Assert.assertEquals(
			RequestAttributes.ATTRIBUTE1,
			mockHttpServletRequest.getAttribute(RequestAttributes.ATTRIBUTE1));
		Assert.assertEquals(
			RequestAttributes.ATTRIBUTE1,
			mockHttpServletRequest.getAttribute(RequestAttributes.ATTRIBUTE1));

		// Distributed request attributes, with type setting

		distributedRequestAttributes.clear();

		String typeSetting = "typeSetting";

		distributedRequestAttributes.put(
			WebKeys.SPI_AGENT_LAYOUT_TYPE_SETTINGS, typeSetting);

		mockHttpServletRequest.setAttribute(WebKeys.LAYOUT, new LayoutImpl());

		spiAgentResponse.populate(
			mockHttpServletRequest, new MockHttpServletResponse());

		Layout layout = (Layout)mockHttpServletRequest.getAttribute(
			WebKeys.LAYOUT);

		Assert.assertEquals(typeSetting, layout.getTypeSettings());

		// Successfully output byteData

		byte[] outputData = new byte[10];

		spiAgentResponse.byteData = outputData;

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(new MockHttpServletResponse());

		spiAgentResponse.populate(
			mockHttpServletRequest, bufferCacheServletResponse);

		ByteBuffer byteBuffer = bufferCacheServletResponse.getByteBuffer();

		Assert.assertSame(outputData, byteBuffer.array());

		// Unable to output byteData

		final IOException ioException = new IOException();

		HttpServletResponse httpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public boolean isCommitted() {
					return false;
				}

				@Override
				public void flushBuffer() throws IOException {
					throw ioException;
				}

				@Override
				public void setContentLength(int contentLength) {
				}

			};

		try {
			spiAgentResponse.populate(
				mockHttpServletRequest, httpServletResponse);

			Assert.fail();
		}
		catch (PortalResiliencyException pre) {
			Assert.assertSame(ioException, pre.getCause());
		}

		// Successfully output stringData

		String stringData = "stringData";

		spiAgentResponse.stringData = stringData;

		bufferCacheServletResponse = new BufferCacheServletResponse(
			new MockHttpServletResponse());

		spiAgentResponse.populate(
			mockHttpServletRequest, bufferCacheServletResponse);

		CharBuffer charBuffer = bufferCacheServletResponse.getCharBuffer();

		Assert.assertEquals(stringData, charBuffer.toString());

		// Unable to output stringData

		try {
			spiAgentResponse.populate(
				mockHttpServletRequest, httpServletResponse);

			Assert.fail();
		}
		catch (PortalResiliencyException pre) {
			Assert.assertSame(ioException, pre.getCause());
		}
	}

	private static String _sessionAttribute1 = "sessionAttribute1";
	private static String _sessionAttribute2 = "sessionAttribute2";
	private static String _sessionAttribute3 = "sessionAttribute3";
	private static ThreadLocal<String> _threadLocal = new ThreadLocal<String>();

	private MockHttpServletRequest _mockHttpServletRequest;

}