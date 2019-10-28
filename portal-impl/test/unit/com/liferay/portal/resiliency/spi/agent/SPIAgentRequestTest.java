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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.process.local.LocalProcessLauncher;
import com.liferay.portal.kernel.resiliency.spi.MockSPI;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.servlet.ServletInputStreamAdapter;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.rule.NewEnvTestRule;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.CookieUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.ThreadLocalDistributor;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.resiliency.spi.agent.SPIAgentRequest.AgentHttpServletRequestWrapper;
import com.liferay.portal.tools.ToolDependencies;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.util.PortalImpl;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;

import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

/**
 * @author Shuyang Zhou
 */
public class SPIAgentRequestTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, NewEnvTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		ToolDependencies.wireCaches();

		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(
			new FileImpl() {

				@Override
				public File createTempFile() {
					try {
						return File.createTempFile(
							SPIAgentRequestTest.class.getName(), null);
					}
					catch (IOException ioe) {
						throw new RuntimeException(ioe);
					}
				}

			});

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());

		ThreadLocalDistributor threadLocalDistributor =
			new ThreadLocalDistributor();

		threadLocalDistributor.setThreadLocalSources(
			Arrays.asList(
				new KeyValuePair(
					SPIAgentRequestTest.class.getName(), "_threadLocal")));

		threadLocalDistributor.afterPropertiesSet();

		_mockHttpServletRequest =
			new BackwardCompatibleMockHttpServletRequest() {

				@Override
				public Enumeration<String> getHeaderNames() {
					Enumeration<String> headerNameEnumeration =
						super.getHeaderNames();

					List<String> headerNames = ListUtil.fromEnumeration(
						headerNameEnumeration);

					// Header with no value

					headerNames.add(_HEADER_NAME_3);

					return Collections.enumeration(headerNames);
				}

				@Override
				public Map<String, String[]> getParameterMap() {
					Map<String, String[]> parameterMap = new LinkedHashMap<>(
						super.getParameterMap());

					// Parameter with no value

					parameterMap.put(_PARAMETER_NAME_3, new String[0]);

					return parameterMap;
				}

			};

		_mockHttpServletRequest.addHeader(_HEADER_NAME_1, _HEADER_VALUE_1);
		_mockHttpServletRequest.addHeader(_HEADER_NAME_1, _HEADER_VALUE_2);
		_mockHttpServletRequest.addHeader(_HEADER_NAME_2, _HEADER_VALUE_3);
		_mockHttpServletRequest.addHeader(_HEADER_NAME_2, _HEADER_VALUE_4);
		_mockHttpServletRequest.addParameter(
			_PARAMETER_NAME_1, _PARAMETER_VALUE_1);
		_mockHttpServletRequest.addParameter(
			_PARAMETER_NAME_1, _PARAMETER_VALUE_2);
		_mockHttpServletRequest.addParameter(
			_PARAMETER_NAME_2, _PARAMETER_VALUE_3);
		_mockHttpServletRequest.addParameter(
			_PARAMETER_NAME_2, _PARAMETER_VALUE_4);
		_mockHttpServletRequest.setRemoteAddr(_REMOTE_ADDR);
		_mockHttpServletRequest.setRemoteHost(_REMOTE_HOST);
		_mockHttpServletRequest.setRemotePort(_REMOTE_PORT);
		_mockHttpServletRequest.setRemoteUser(_REMOTE_USER);
		_mockHttpServletRequest.setServerName(_SERVER_NAME);
		_mockHttpServletRequest.setServerPort(_SERVER_PORT);

		Portlet portlet = new PortletImpl() {

			@Override
			public String getContextName() {
				return "SERVLET_CONTEXT_NAME";
			}

		};

		_mockHttpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_PORTLET, portlet);

		RequestAttributes.setRequestAttributes(_mockHttpServletRequest);

		HttpSession session = _mockHttpServletRequest.getSession();

		session.setAttribute(
			_SESSION_ATTRIBUTE_NAME_1, _SESSION_ATTRIBUTE_VALUE_1);
		session.setAttribute(
			_SESSION_ATTRIBUTE_NAME_2, _SESSION_ATTRIBUTE_VALUE_2);
		session.setAttribute(
			_SESSION_ATTRIBUTE_NAME_3, _SESSION_ATTRIBUTE_VALUE_3);

		_captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			SPIAgentSerializable.class.getName(), Level.OFF);
	}

	@After
	public void tearDown() {
		_captureHandler.close();
	}

	@Test
	public void testContentTypeIsMultipart() throws IOException {

		// Upload servlet request with null data

		_mockHttpServletRequest.setContentType(
			ContentTypes.MULTIPART_FORM_DATA);

		SPIAgentRequest spiAgentRequest = new SPIAgentRequest(
			new UploadServletRequestImpl(_mockHttpServletRequest, null, null));

		HttpServletRequest populateHttpServletRequest =
			spiAgentRequest.populateRequest(
				new BackwardCompatibleMockHttpServletRequest());

		Assert.assertSame(
			AgentHttpServletRequestWrapper.class,
			populateHttpServletRequest.getClass());
		Assert.assertEquals(-1, populateHttpServletRequest.getContentLength());
		Assert.assertNull(populateHttpServletRequest.getContentType());
		Assert.assertNull(populateHttpServletRequest.getInputStream());

		// Upload servlet request with empty data

		spiAgentRequest = new SPIAgentRequest(
			new UploadServletRequestImpl(
				_mockHttpServletRequest, new HashMap<String, FileItem[]>(),
				new HashMap<String, List<String>>()));

		populateHttpServletRequest = spiAgentRequest.populateRequest(
			new BackwardCompatibleMockHttpServletRequest());

		Assert.assertSame(
			AgentHttpServletRequestWrapper.class,
			populateHttpServletRequest.getClass());
		Assert.assertEquals(-1, populateHttpServletRequest.getContentLength());
		Assert.assertNull(populateHttpServletRequest.getContentType());
		Assert.assertNull(populateHttpServletRequest.getInputStream());

		// Upload servlet request with multipart data

		String fileParameter = "fileParameter";

		FileItem[] fileItems = new FileItem[0];

		Map<String, FileItem[]> fileParameters = new HashMap<>();

		fileParameters.put(fileParameter, fileItems);

		spiAgentRequest = new SPIAgentRequest(
			new UploadServletRequestImpl(
				_mockHttpServletRequest, fileParameters,
				new HashMap<String, List<String>>()));

		populateHttpServletRequest = spiAgentRequest.populateRequest(
			new BackwardCompatibleMockHttpServletRequest());

		Assert.assertSame(
			UploadServletRequestImpl.class,
			populateHttpServletRequest.getClass());

		UploadServletRequestImpl uploadServletRequestImpl =
			(UploadServletRequestImpl)populateHttpServletRequest;

		Map<String, FileItem[]> populatedFileParameters =
			uploadServletRequestImpl.getMultipartParameterMap();
		Map<String, List<String>> populatedRegularParameters =
			uploadServletRequestImpl.getRegularParameterMap();

		Assert.assertEquals(
			populatedFileParameters.toString(), 1,
			populatedFileParameters.size());
		Assert.assertSame(
			fileItems, populatedFileParameters.get(fileParameter));
		Assert.assertTrue(
			populatedRegularParameters.toString(),
			populatedRegularParameters.isEmpty());
		Assert.assertEquals(-1, populateHttpServletRequest.getContentLength());
		Assert.assertNull(populateHttpServletRequest.getContentType());
		Assert.assertNull(populateHttpServletRequest.getInputStream());

		// Upload servlet request with multipart and regular data

		String regularParameter = "regularParameter";

		List<String> parameters = new ArrayList<>();

		Map<String, List<String>> regularParameters = new HashMap<>();

		regularParameters.put(regularParameter, parameters);

		spiAgentRequest = new SPIAgentRequest(
			new UploadServletRequestImpl(
				_mockHttpServletRequest, fileParameters, regularParameters));

		populateHttpServletRequest = spiAgentRequest.populateRequest(
			new BackwardCompatibleMockHttpServletRequest());

		Assert.assertSame(
			UploadServletRequestImpl.class,
			populateHttpServletRequest.getClass());

		uploadServletRequestImpl =
			(UploadServletRequestImpl)populateHttpServletRequest;

		populatedFileParameters =
			uploadServletRequestImpl.getMultipartParameterMap();
		populatedRegularParameters =
			uploadServletRequestImpl.getRegularParameterMap();

		Assert.assertEquals(
			populatedFileParameters.toString(), 1,
			populatedFileParameters.size());
		Assert.assertSame(
			fileItems, populatedFileParameters.get(fileParameter));
		Assert.assertEquals(
			populatedRegularParameters.toString(), 1,
			populatedRegularParameters.size());
		Assert.assertSame(
			parameters, populatedRegularParameters.get(regularParameter));
		Assert.assertEquals(-1, populateHttpServletRequest.getContentLength());
		Assert.assertNull(populateHttpServletRequest.getContentType());
		Assert.assertNull(populateHttpServletRequest.getInputStream());

		// Upload servlet request with regular data

		spiAgentRequest = new SPIAgentRequest(
			new UploadServletRequestImpl(
				_mockHttpServletRequest, new HashMap<String, FileItem[]>(),
				regularParameters));

		populateHttpServletRequest = spiAgentRequest.populateRequest(
			new BackwardCompatibleMockHttpServletRequest());

		Assert.assertSame(
			UploadServletRequestImpl.class,
			populateHttpServletRequest.getClass());

		uploadServletRequestImpl =
			(UploadServletRequestImpl)populateHttpServletRequest;

		populatedFileParameters =
			uploadServletRequestImpl.getMultipartParameterMap();
		populatedRegularParameters =
			uploadServletRequestImpl.getRegularParameterMap();

		Assert.assertTrue(
			populatedFileParameters.toString(),
			populatedFileParameters.isEmpty());
		Assert.assertEquals(
			populatedRegularParameters.toString(), 1,
			populatedRegularParameters.size());
		Assert.assertSame(
			parameters, populatedRegularParameters.get(regularParameter));
		Assert.assertEquals(-1, populateHttpServletRequest.getContentLength());
		Assert.assertNull(populateHttpServletRequest.getContentType());
		Assert.assertNull(populateHttpServletRequest.getInputStream());

		// Not an upload servlet request

		byte[] content = new byte[1024];

		Random random = new Random();

		random.nextBytes(content);

		_mockHttpServletRequest.setContent(content);

		spiAgentRequest = new SPIAgentRequest(
			new HttpServletRequestWrapper(_mockHttpServletRequest));

		Assert.assertEquals(
			ContentTypes.MULTIPART_FORM_DATA, spiAgentRequest.contentType);
		Assert.assertNotNull(spiAgentRequest.requestBodyFile);
		Assert.assertArrayEquals(
			content, FileUtil.getBytes(spiAgentRequest.requestBodyFile));

		populateHttpServletRequest = spiAgentRequest.populateRequest(
			new BackwardCompatibleMockHttpServletRequest());

		Assert.assertEquals(
			content.length, populateHttpServletRequest.getContentLength());
		Assert.assertEquals(
			ContentTypes.MULTIPART_FORM_DATA,
			populateHttpServletRequest.getContentType());

		ServletInputStream servletInputStream =
			populateHttpServletRequest.getInputStream();

		Assert.assertNotNull(servletInputStream);
		Assert.assertArrayEquals(
			content, FileUtil.getBytes(servletInputStream));

		// Not an upload servlet request, unable to read

		_mockHttpServletRequest.setContent(null);

		try {
			new SPIAgentRequest(
				new HttpServletRequestWrapper(_mockHttpServletRequest));

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("Input stream is null", iae.getMessage());
		}
	}

	@Test
	public void testContentTypeIsNotMultipart() throws Exception {
		_mockHttpServletRequest.setContentType(ContentTypes.APPLICATION_GZIP);

		UploadServletRequestImpl uploadServletRequestImpl =
			new UploadServletRequestImpl(_mockHttpServletRequest, null, null) {

				@Override
				public Map<String, FileItem[]> getMultipartParameterMap() {
					throw new Error();
				}

				@Override
				public Map<String, List<String>> getRegularParameterMap() {
					throw new Error();
				}

			};

		new SPIAgentRequest(uploadServletRequestImpl);
	}

	@Test
	public void testContentTypeIsNull() throws Exception {

		// Captured session attributes

		String threadLocalValue = "threadLocalValue";

		_threadLocal.set(threadLocalValue);

		SPIAgentRequest spiAgentRequest = new SPIAgentRequest(
			_mockHttpServletRequest);

		_threadLocal.remove();

		Map<String, Serializable> originalSessionAttributes =
			spiAgentRequest.getOriginalSessionAttributes();

		Assert.assertEquals(
			originalSessionAttributes.toString(), 2,
			originalSessionAttributes.size());
		Assert.assertEquals(
			_SESSION_ATTRIBUTE_VALUE_1,
			originalSessionAttributes.get(_SESSION_ATTRIBUTE_NAME_1));
		Assert.assertEquals(
			_SESSION_ATTRIBUTE_VALUE_2,
			originalSessionAttributes.get(_SESSION_ATTRIBUTE_NAME_2));

		// Cookies

		HttpServletRequest populatedHttpServletRequest =
			spiAgentRequest.populateRequest(
				new BackwardCompatibleMockHttpServletRequest());

		Assert.assertNull(populatedHttpServletRequest.getCookies());

		_mockHttpServletRequest.setCookies(_cookie1, _cookie2);

		spiAgentRequest = new SPIAgentRequest(_mockHttpServletRequest);

		populatedHttpServletRequest = spiAgentRequest.populateRequest(
			new BackwardCompatibleMockHttpServletRequest());

		Cookie[] cookies = populatedHttpServletRequest.getCookies();

		Assert.assertEquals(Arrays.toString(cookies), 2, cookies.length);
		Assert.assertTrue(CookieUtil.equals(_cookie1, cookies[0]));
		Assert.assertTrue(CookieUtil.equals(_cookie2, cookies[1]));

		// Headers

		Assert.assertEquals(
			_HEADER_VALUE_1,
			populatedHttpServletRequest.getHeader(_HEADER_NAME_1));
		Assert.assertEquals(
			_HEADER_VALUE_3,
			populatedHttpServletRequest.getHeader(_HEADER_NAME_2));
		Assert.assertNull(
			populatedHttpServletRequest.getHeader(_HEADER_NAME_3));
		Assert.assertNull(
			populatedHttpServletRequest.getHeader(_HEADER_NAME_4));

		List<String> headerNames = ListUtil.fromEnumeration(
			populatedHttpServletRequest.getHeaderNames());

		Assert.assertEquals(headerNames.toString(), 3, headerNames.size());
		Assert.assertTrue(
			headerNames.toString(),
			headerNames.contains(StringUtil.toLowerCase(_HEADER_NAME_1)));
		Assert.assertTrue(
			headerNames.toString(),
			headerNames.contains(StringUtil.toLowerCase(_HEADER_NAME_2)));
		Assert.assertTrue(
			headerNames.toString(),
			headerNames.contains(StringUtil.toLowerCase(_HEADER_NAME_3)));

		List<String> headers = ListUtil.fromEnumeration(
			populatedHttpServletRequest.getHeaders(_HEADER_NAME_1));

		Assert.assertEquals(headers.toString(), 2, headers.size());
		Assert.assertEquals(_HEADER_VALUE_1, headers.get(0));
		Assert.assertEquals(_HEADER_VALUE_2, headers.get(1));

		headers = ListUtil.fromEnumeration(
			populatedHttpServletRequest.getHeaders(_HEADER_NAME_2));

		Assert.assertEquals(headers.toString(), 2, headers.size());
		Assert.assertEquals(_HEADER_VALUE_3, headers.get(0));
		Assert.assertEquals(_HEADER_VALUE_4, headers.get(1));

		headers = ListUtil.fromEnumeration(
			populatedHttpServletRequest.getHeaders(_HEADER_NAME_3));

		Assert.assertTrue(headers.toString(), headers.isEmpty());

		headers = ListUtil.fromEnumeration(
			populatedHttpServletRequest.getHeaders(_HEADER_NAME_4));

		Assert.assertTrue(headers.toString(), headers.isEmpty());

		// Parameters

		Map<String, String[]> parameterMap =
			populatedHttpServletRequest.getParameterMap();

		Assert.assertEquals(parameterMap.toString(), 3, parameterMap.size());

		String[] parameter1 = parameterMap.get(_PARAMETER_NAME_1);

		Assert.assertEquals(Arrays.toString(parameter1), 2, parameter1.length);
		Assert.assertEquals(_PARAMETER_VALUE_1, parameter1[0]);
		Assert.assertEquals(_PARAMETER_VALUE_2, parameter1[1]);

		String[] parameter2 = parameterMap.get(_PARAMETER_NAME_2);

		Assert.assertEquals(Arrays.toString(parameter2), 2, parameter2.length);
		Assert.assertEquals(_PARAMETER_VALUE_3, parameter2[0]);
		Assert.assertEquals(_PARAMETER_VALUE_4, parameter2[1]);

		String[] parameter3 = parameterMap.get(_PARAMETER_NAME_3);

		Assert.assertEquals(Arrays.toString(parameter3), 0, parameter3.length);

		Assert.assertEquals(
			_PARAMETER_VALUE_1,
			populatedHttpServletRequest.getParameter(_PARAMETER_NAME_1));
		Assert.assertEquals(
			_PARAMETER_VALUE_3,
			populatedHttpServletRequest.getParameter(_PARAMETER_NAME_2));
		Assert.assertNull(
			populatedHttpServletRequest.getParameter(_PARAMETER_NAME_3));
		Assert.assertNull(
			populatedHttpServletRequest.getParameter(_PARAMETER_NAME_4));

		List<String> parameterNames = ListUtil.fromEnumeration(
			populatedHttpServletRequest.getParameterNames());

		Assert.assertEquals(
			parameterNames.toString(), 3, parameterNames.size());
		Assert.assertTrue(
			parameterNames.toString(),
			parameterNames.contains(_PARAMETER_NAME_1));
		Assert.assertTrue(
			parameterNames.toString(),
			parameterNames.contains(_PARAMETER_NAME_2));
		Assert.assertTrue(
			parameterNames.toString(),
			parameterNames.contains(_PARAMETER_NAME_3));

		parameter1 = populatedHttpServletRequest.getParameterValues(
			_PARAMETER_NAME_1);

		Assert.assertEquals(Arrays.toString(parameter1), 2, parameter1.length);
		Assert.assertEquals(_PARAMETER_VALUE_1, parameter1[0]);
		Assert.assertEquals(_PARAMETER_VALUE_2, parameter1[1]);

		parameter2 = populatedHttpServletRequest.getParameterValues(
			_PARAMETER_NAME_2);

		Assert.assertEquals(Arrays.toString(parameter2), 2, parameter2.length);
		Assert.assertEquals(_PARAMETER_VALUE_3, parameter2[0]);
		Assert.assertEquals(_PARAMETER_VALUE_4, parameter2[1]);

		parameter3 = populatedHttpServletRequest.getParameterValues(
			_PARAMETER_NAME_3);

		Assert.assertEquals(Arrays.toString(parameter3), 0, parameter3.length);

		// Remote address, host, port, and user

		Assert.assertEquals(
			_REMOTE_ADDR, populatedHttpServletRequest.getRemoteAddr());

		Assert.assertEquals(
			_REMOTE_HOST, populatedHttpServletRequest.getRemoteHost());

		Assert.assertEquals(
			_REMOTE_PORT, populatedHttpServletRequest.getRemotePort());

		Assert.assertEquals(
			_REMOTE_USER, populatedHttpServletRequest.getRemoteUser());

		// Server name

		Assert.assertEquals(
			_SERVER_NAME, populatedHttpServletRequest.getServerName());

		// Server port

		Assert.assertEquals(
			_SERVER_PORT, populatedHttpServletRequest.getServerPort());

		Assert.assertEquals(threadLocalValue, _threadLocal.get());

		// Populated session attributes

		MockHttpSession mockHttpSession = new MockHttpSession();

		spiAgentRequest.populateSessionAttributes(mockHttpSession);

		List<String> attributeNames = ListUtil.fromEnumeration(
			mockHttpSession.getAttributeNames());

		Assert.assertEquals(
			attributeNames.toString(), 2, attributeNames.size());
		Assert.assertTrue(
			attributeNames.toString(),
			attributeNames.contains(_SESSION_ATTRIBUTE_NAME_1));
		Assert.assertTrue(
			attributeNames.toString(),
			attributeNames.contains(_SESSION_ATTRIBUTE_NAME_2));

		Assert.assertEquals(
			_SESSION_ATTRIBUTE_VALUE_1,
			mockHttpSession.getAttribute(_SESSION_ATTRIBUTE_NAME_1));
		Assert.assertEquals(
			_SESSION_ATTRIBUTE_VALUE_2,
			mockHttpSession.getAttribute(_SESSION_ATTRIBUTE_NAME_2));

		// To string

		StringBundler sb = new StringBundler(
			13 + cookies.length * 2 + parameterMap.size() * 4);

		sb.append("{contentType=null, cookies=[");

		for (Cookie cookie : cookies) {
			sb.append(CookieUtil.toString(cookie));
			sb.append(", ");
		}

		sb.setIndex(sb.index() - 1);

		sb.append("], distributedRequestAttributes=");
		sb.append(spiAgentRequest.distributedRequestAttributes);
		sb.append(", headerMap=");
		sb.append(spiAgentRequest.headerMap);
		sb.append(", multipartParameterMap=null, originalSessionAttributes=");
		sb.append(spiAgentRequest.getOriginalSessionAttributes());
		sb.append(", parameterMap={");

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			sb.append(entry.getKey());
			sb.append("=");
			sb.append(Arrays.toString(entry.getValue()));
			sb.append(", ");
		}

		sb.setIndex(sb.index() - 1);

		sb.append("}, regularParameterMap=null, requestBodyFile=null, ");
		sb.append("serverName=");
		sb.append(_SERVER_NAME);
		sb.append(", serverPort=");
		sb.append(_SERVER_PORT);
		sb.append("}");

		Assert.assertEquals(sb.toString(), spiAgentRequest.toString());

		_mockHttpServletRequest.setCookies((Cookie[])null);

		spiAgentRequest = new SPIAgentRequest(_mockHttpServletRequest);

		sb = new StringBundler(13 + parameterMap.size() * 4);

		sb.append("{contentType=null, cookies=[], ");
		sb.append("distributedRequestAttributes=");
		sb.append(spiAgentRequest.distributedRequestAttributes);
		sb.append(", headerMap=");
		sb.append(spiAgentRequest.headerMap);
		sb.append(", multipartParameterMap=null, originalSessionAttributes=");
		sb.append(spiAgentRequest.getOriginalSessionAttributes());
		sb.append(", parameterMap={");

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			sb.append(entry.getKey());
			sb.append("=");
			sb.append(Arrays.toString(entry.getValue()));
			sb.append(", ");
		}

		sb.setIndex(sb.index() - 1);

		sb.append("}, regularParameterMap=null, requestBodyFile=null, ");
		sb.append("serverName=");
		sb.append(_SERVER_NAME);
		sb.append(", serverPort=");
		sb.append(_SERVER_PORT);
		sb.append("}");

		Assert.assertEquals(sb.toString(), spiAgentRequest.toString());
	}

	@Test
	public void testPopulatePortletSessionAttributes1() {

		// Not an SPI

		MockHttpServletRequest mockHttpServletRequest =
			new BackwardCompatibleMockHttpServletRequest();

		MockHttpSession mockHttpSession = new MockHttpSession();

		SPIAgentRequest.populatePortletSessionAttributes(
			mockHttpServletRequest, mockHttpSession);

		Enumeration<String> enumeration =
			mockHttpServletRequest.getAttributeNames();

		Assert.assertFalse(enumeration.hasMoreElements());

		enumeration = mockHttpSession.getAttributeNames();

		Assert.assertFalse(enumeration.hasMoreElements());
	}

	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testPopulatePortletSessionAttributes2() {

		// SPI, already populated

		ConcurrentMap<String, Object> attributes =
			LocalProcessLauncher.ProcessContext.getAttributes();

		attributes.put(SPI.SPI_INSTANCE_PUBLICATION_KEY, new MockSPI());

		final List<String> names = new ArrayList<>();

		MockHttpServletRequest mockHttpServletRequest =
			new BackwardCompatibleMockHttpServletRequest() {

				@Override
				public Object getAttribute(String name) {
					names.add(name);

					return super.getAttribute(name);
				}

			};

		MockHttpSession mockHttpSession = new MockHttpSession();

		mockHttpServletRequest.setAttribute(
			WebKeys.PORTLET_SESSION, mockHttpSession);

		SPIAgentRequest.populatePortletSessionAttributes(
			mockHttpServletRequest, mockHttpSession);

		Assert.assertEquals(names.toString(), 1, names.size());
		Assert.assertEquals(WebKeys.PORTLET_SESSION, names.get(0));

		Enumeration<String> enumeration =
			mockHttpServletRequest.getAttributeNames();

		Assert.assertTrue(enumeration.hasMoreElements());
		Assert.assertEquals(WebKeys.PORTLET_SESSION, enumeration.nextElement());
		Assert.assertFalse(enumeration.hasMoreElements());

		enumeration = mockHttpSession.getAttributeNames();

		Assert.assertFalse(enumeration.hasMoreElements());
	}

	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testPopulatePortletSessionAttributes3() {

		// SPI, not populated, no SPI agent request

		ConcurrentMap<String, Object> attributes =
			LocalProcessLauncher.ProcessContext.getAttributes();

		attributes.put(SPI.SPI_INSTANCE_PUBLICATION_KEY, new MockSPI());

		final List<String> names = new ArrayList<>();

		MockHttpServletRequest mockHttpServletRequest =
			new BackwardCompatibleMockHttpServletRequest() {

				@Override
				public Object getAttribute(String name) {
					names.add(name);

					return super.getAttribute(name);
				}

			};

		MockHttpSession mockHttpSession = new MockHttpSession();

		SPIAgentRequest.populatePortletSessionAttributes(
			mockHttpServletRequest, mockHttpSession);

		Assert.assertEquals(names.toString(), 2, names.size());
		Assert.assertEquals(WebKeys.PORTLET_SESSION, names.get(0));
		Assert.assertEquals(WebKeys.SPI_AGENT_REQUEST, names.get(1));

		Enumeration<String> enumeration =
			mockHttpServletRequest.getAttributeNames();

		Assert.assertFalse(enumeration.hasMoreElements());

		enumeration = mockHttpSession.getAttributeNames();

		Assert.assertFalse(enumeration.hasMoreElements());
	}

	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testPopulatePortletSessionAttributes4() throws IOException {

		// SPI, not populated, with an SPI agent request, no portlet session
		// attributes

		ConcurrentMap<String, Object> attributes =
			LocalProcessLauncher.ProcessContext.getAttributes();

		attributes.put(SPI.SPI_INSTANCE_PUBLICATION_KEY, new MockSPI());

		MockHttpServletRequest mockHttpServletRequest =
			new BackwardCompatibleMockHttpServletRequest();

		MockHttpServletRequest originalMockHttpServletRequest =
			new BackwardCompatibleMockHttpServletRequest();

		final String servletContextName = "servletContextName";

		Portlet portlet = new PortletImpl() {

			@Override
			public String getContextName() {
				return servletContextName;
			}

		};

		originalMockHttpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_PORTLET, portlet);

		SPIAgentRequest spiAgentRequest = new SPIAgentRequest(
			originalMockHttpServletRequest);

		mockHttpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_REQUEST, spiAgentRequest);

		MockHttpSession mockHttpSession = new MockHttpSession();

		SPIAgentRequest.populatePortletSessionAttributes(
			mockHttpServletRequest, mockHttpSession);

		Assert.assertSame(
			mockHttpSession,
			mockHttpServletRequest.getAttribute(WebKeys.PORTLET_SESSION));

		Enumeration<String> enumeration = mockHttpSession.getAttributeNames();

		Assert.assertFalse(enumeration.hasMoreElements());
	}

	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testPopulatePortletSessionAttributes5() throws IOException {

		// SPI, not populated, with an SPI agent request, with portlet session
		// attributes

		ConcurrentMap<String, Object> attributes =
			LocalProcessLauncher.ProcessContext.getAttributes();

		attributes.put(SPI.SPI_INSTANCE_PUBLICATION_KEY, new MockSPI());

		MockHttpServletRequest mockHttpServletRequest =
			new BackwardCompatibleMockHttpServletRequest();

		MockHttpServletRequest originalMockHttpServletRequest =
			new BackwardCompatibleMockHttpServletRequest();

		final String servletContextName = "servletContextName";

		Portlet portlet = new PortletImpl() {

			@Override
			public String getContextName() {
				return servletContextName;
			}

		};

		originalMockHttpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_PORTLET, portlet);

		MockHttpSession originalHttpSession = new MockHttpSession();

		Map<String, Serializable> portletSessionAttributes =
			HashMapBuilder.<String, Serializable>put(
				"key1", "value1"
			).put(
				"key2", "value2"
			).put(
				"key3", "value3"
			).build();

		originalHttpSession.setAttribute(
			WebKeys.PORTLET_SESSION_ATTRIBUTES.concat(servletContextName),
			portletSessionAttributes);

		originalMockHttpServletRequest.setSession(originalHttpSession);

		SPIAgentRequest spiAgentRequest = new SPIAgentRequest(
			originalMockHttpServletRequest);

		mockHttpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_REQUEST, spiAgentRequest);

		MockHttpSession mockHttpSession = new MockHttpSession();

		mockHttpSession.setAttribute("key4", "value4");
		mockHttpSession.setAttribute("key5", "value5");
		mockHttpSession.setAttribute("key6", "value6");

		SPIAgentRequest.populatePortletSessionAttributes(
			mockHttpServletRequest, mockHttpSession);

		Assert.assertSame(
			mockHttpSession,
			mockHttpServletRequest.getAttribute(WebKeys.PORTLET_SESSION));
		Assert.assertEquals("value1", mockHttpSession.getAttribute("key1"));
		Assert.assertEquals("value2", mockHttpSession.getAttribute("key2"));
		Assert.assertEquals("value3", mockHttpSession.getAttribute("key3"));

		Enumeration<String> enumeration = mockHttpSession.getAttributeNames();

		Assert.assertTrue(enumeration.hasMoreElements());
		Assert.assertNotNull(enumeration.nextElement());
		Assert.assertTrue(enumeration.hasMoreElements());
		Assert.assertNotNull(enumeration.nextElement());
		Assert.assertTrue(enumeration.hasMoreElements());
		Assert.assertNotNull(enumeration.nextElement());
		Assert.assertFalse(enumeration.hasMoreElements());
	}

	@Test
	public void testShowFooter() throws IOException {

		// No theme display

		_mockHttpServletRequest.setParameter(
			"portalResiliencyPortletShowFooter", StringPool.TRUE);

		SPIAgentRequest spiAgentRequest = new SPIAgentRequest(
			_mockHttpServletRequest);

		Map<String, String[]> parameterMap = spiAgentRequest.parameterMap;

		Assert.assertArrayEquals(
			new String[] {StringPool.TRUE},
			parameterMap.get("portalResiliencyPortletShowFooter"));

		// Has theme display, without ajax

		ThemeDisplay themeDisplay = new ThemeDisplay();

		_mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		spiAgentRequest = new SPIAgentRequest(_mockHttpServletRequest);

		parameterMap = spiAgentRequest.parameterMap;

		Assert.assertArrayEquals(
			new String[] {StringPool.TRUE},
			parameterMap.get("portalResiliencyPortletShowFooter"));

		// Has theme display, with ajax

		themeDisplay.setAjax(true);

		spiAgentRequest = new SPIAgentRequest(_mockHttpServletRequest);

		parameterMap = spiAgentRequest.parameterMap;

		Assert.assertArrayEquals(
			new String[] {StringPool.FALSE},
			parameterMap.get("portalResiliencyPortletShowFooter"));
	}

	private static final String _HEADER_NAME_1 = "HEADER_NAME_1";

	private static final String _HEADER_NAME_2 = "HEADER_NAME_2";

	private static final String _HEADER_NAME_3 = "HEADER_NAME_3";

	private static final String _HEADER_NAME_4 = "HEADER_NAME_4";

	private static final String _HEADER_VALUE_1 = "HEADER_VALUE_1";

	private static final String _HEADER_VALUE_2 = "HEADER_VALUE_2";

	private static final String _HEADER_VALUE_3 = "HEADER_VALUE_3";

	private static final String _HEADER_VALUE_4 = "HEADER_VALUE_4";

	private static final String _PARAMETER_NAME_1 = "PARAMETER_NAME_1";

	private static final String _PARAMETER_NAME_2 = "PARAMETER_NAME_2";

	private static final String _PARAMETER_NAME_3 = "PARAMETER_NAME_3";

	private static final String _PARAMETER_NAME_4 = "PARAMETER_NAME_4";

	private static final String _PARAMETER_VALUE_1 = "PARAMETER_VALUE_1";

	private static final String _PARAMETER_VALUE_2 = "PARAMETER_VALUE_2";

	private static final String _PARAMETER_VALUE_3 = "PARAMETER_VALUE_3";

	private static final String _PARAMETER_VALUE_4 = "PARAMETER_VALUE_4";

	private static final String _REMOTE_ADDR = "192.168.1.10";

	private static final String _REMOTE_HOST = "192.168.1.10";

	private static final int _REMOTE_PORT = 12345;

	private static final String _REMOTE_USER = "testUser";

	private static final String _SERVER_NAME = "SERVER_NAME";

	private static final int _SERVER_PORT = 1023;

	private static final String _SESSION_ATTRIBUTE_NAME_1 =
		"SESSION_ATTRIBUTE_NAME_1";

	private static final String _SESSION_ATTRIBUTE_NAME_2 =
		"SESSION_ATTRIBUTE_NAME_2";

	private static final String _SESSION_ATTRIBUTE_NAME_3 =
		"SESSION_ATTRIBUTE_NAME_3";

	private static final String _SESSION_ATTRIBUTE_VALUE_1 =
		"SESSION_ATTRIBUTE_VALUE_1";

	private static final String _SESSION_ATTRIBUTE_VALUE_2 =
		"SESSION_ATTRIBUTE_VALUE_2";

	private static final Object _SESSION_ATTRIBUTE_VALUE_3 = new Object();

	private static final Cookie _cookie1 = new Cookie("name1", "value1");
	private static final Cookie _cookie2 = new Cookie("name2", "value2");
	private static final ThreadLocal<String> _threadLocal = new ThreadLocal<>();

	private CaptureHandler _captureHandler;
	private MockHttpServletRequest _mockHttpServletRequest;

	private static class BackwardCompatibleMockHttpServletRequest
		extends MockHttpServletRequest {

		@Override
		public ServletInputStream getInputStream() {
			if (_content == null) {
				return null;
			}

			return new ServletInputStreamAdapter(
				new UnsyncByteArrayInputStream(_content));
		}

		@Override
		public void setContent(byte[] content) {
			super.setContent(content);

			_content = content;
		}

		private byte[] _content;

	}

}