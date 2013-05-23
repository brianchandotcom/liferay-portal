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

import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.CookieUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.ThreadLocalDistributor;
import com.liferay.portal.resiliency.spi.agent.SPIAgentRequest.AgentHttpServletRequestWrapper;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PortalUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

/**
 * @author Shuyang Zhou
 */
public class SPIAgentRequestTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@BeforeClass
	public static void setUpClass() throws Exception {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());

		ThreadLocalDistributor threadLocalDistributor =
			new ThreadLocalDistributor();

		threadLocalDistributor.setThreadLocalSources(
			Arrays.asList(
				new KeyValuePair(
					SPIAgentRequestTest.class.getName(), "_threadLocal")));

		threadLocalDistributor.afterPropertiesSet();
	}

	@Before
	public void setUp() {
		_mockHttpServletRequest = new MockHttpServletRequest() {

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
				Map<String, String[]> parameterMap =
					new LinkedHashMap<String, String[]>(
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
		_mockHttpServletRequest.setCookies(_cookie1, _cookie2);
		_mockHttpServletRequest.setServerName(_SERVER_NAME);
		_mockHttpServletRequest.setServerPort(_SERVER_PORT);

		RequestAttributes.setRequestAttributes(_mockHttpServletRequest);

		HttpSession session = _mockHttpServletRequest.getSession();

		session.setAttribute(
			_SESSION_ATTRIBUTE_NAME_1, _SESSION_ATTRIBUTE_VALUE_1);
		session.setAttribute(
			_SESSION_ATTRIBUTE_NAME_2, _SESSION_ATTRIBUTE_VALUE_2);
		session.setAttribute(
			_SESSION_ATTRIBUTE_NAME_3, _SESSION_ATTRIBUTE_VALUE_3);
	}

	@Test
	public void testContentTypeIsMultipart() {

		// Upload servlet request with null data

		_mockHttpServletRequest.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.MULTIPART_FORM_DATA);

		SPIAgentRequest spiAgentRequest = new SPIAgentRequest(
			new UploadServletRequestImpl(_mockHttpServletRequest, null, null));

		HttpServletRequest populateHttpServletRequest =
			spiAgentRequest.populateRequest(new MockHttpServletRequest());

		Assert.assertSame(
			AgentHttpServletRequestWrapper.class,
			populateHttpServletRequest.getClass());

		// Upload servlet request with empty data

		spiAgentRequest = new SPIAgentRequest(
			new UploadServletRequestImpl(
				_mockHttpServletRequest, new HashMap<String, FileItem[]>(),
				new HashMap<String, List<String>>()));

		populateHttpServletRequest = spiAgentRequest.populateRequest(
			new MockHttpServletRequest());

		Assert.assertSame(
			AgentHttpServletRequestWrapper.class,
			populateHttpServletRequest.getClass());

		// Upload servlet request with multipart data

		Map<String, FileItem[]> fileParameters =
			new HashMap<String, FileItem[]>();

		String fileParameter = "fileParameter";

		FileItem[] fileItems = new FileItem[0];

		fileParameters.put(fileParameter, fileItems);

		spiAgentRequest = new SPIAgentRequest(
			new UploadServletRequestImpl(
				_mockHttpServletRequest, fileParameters,
				new HashMap<String, List<String>>()));

		populateHttpServletRequest = spiAgentRequest.populateRequest(
			new MockHttpServletRequest());

		Assert.assertSame(
			UploadServletRequestImpl.class,
			populateHttpServletRequest.getClass());

		UploadServletRequestImpl uploadServletRequestImpl =
			(UploadServletRequestImpl)populateHttpServletRequest;

		Map<String, FileItem[]> populatedFileParameters =
			uploadServletRequestImpl.getMultipartParameterMap();
		Map<String, List<String>> populatedRegularParameters =
			uploadServletRequestImpl.getRegularParameterMap();

		Assert.assertEquals(1, populatedFileParameters.size());
		Assert.assertSame(
			fileItems, populatedFileParameters.get(fileParameter));
		Assert.assertTrue(populatedRegularParameters.isEmpty());

		// Upload servlet request with multipart and regular data

		Map<String, List<String>> regularParameters =
			new HashMap<String, List<String>>();

		String regularParameter = "regularParameter";

		List<String> parameters = new ArrayList<String>();

		regularParameters.put(regularParameter, parameters);

		spiAgentRequest = new SPIAgentRequest(
			new UploadServletRequestImpl(
				_mockHttpServletRequest, fileParameters, regularParameters));

		populateHttpServletRequest = spiAgentRequest.populateRequest(
			new MockHttpServletRequest());

		Assert.assertSame(
			UploadServletRequestImpl.class,
			populateHttpServletRequest.getClass());

		uploadServletRequestImpl =
			(UploadServletRequestImpl)populateHttpServletRequest;

		populatedFileParameters =
			uploadServletRequestImpl.getMultipartParameterMap();
		populatedRegularParameters =
			uploadServletRequestImpl.getRegularParameterMap();

		Assert.assertEquals(1, populatedFileParameters.size());
		Assert.assertSame(
			fileItems, populatedFileParameters.get(fileParameter));
		Assert.assertEquals(1, populatedRegularParameters.size());
		Assert.assertSame(
			parameters, populatedRegularParameters.get(regularParameter));

		// Upload servlet request with regular data

		spiAgentRequest = new SPIAgentRequest(
			new UploadServletRequestImpl(
				_mockHttpServletRequest, new HashMap<String, FileItem[]>(),
				regularParameters));

		populateHttpServletRequest = spiAgentRequest.populateRequest(
			new MockHttpServletRequest());

		Assert.assertSame(
			UploadServletRequestImpl.class,
			populateHttpServletRequest.getClass());

		uploadServletRequestImpl =
			(UploadServletRequestImpl)populateHttpServletRequest;

		populatedFileParameters =
			uploadServletRequestImpl.getMultipartParameterMap();
		populatedRegularParameters =
			uploadServletRequestImpl.getRegularParameterMap();

		Assert.assertTrue(populatedFileParameters.isEmpty());
		Assert.assertEquals(1, populatedRegularParameters.size());
		Assert.assertSame(
			parameters, populatedRegularParameters.get(regularParameter));
	}

	@Test
	public void testContentTypeIsNotMultipart() throws Exception {
		_mockHttpServletRequest.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_GZIP);

		UploadServletRequestImpl uploadServletRequestImpl =
			new UploadServletRequestImpl(_mockHttpServletRequest, null, null) {

				@Override
				public Map<String, FileItem[]> getMultipartParameterMap() {
					Assert.fail();

					return super.getMultipartParameterMap();
				}

				@Override
				public Map<String, List<String>> getRegularParameterMap() {
					Assert.fail();

					return super.getRegularParameterMap();
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

		Assert.assertEquals(2, originalSessionAttributes.size());
		Assert.assertEquals(
			_SESSION_ATTRIBUTE_VALUE_1,
			originalSessionAttributes.get(_SESSION_ATTRIBUTE_NAME_1));
		Assert.assertEquals(
			_SESSION_ATTRIBUTE_VALUE_2,
			originalSessionAttributes.get(_SESSION_ATTRIBUTE_NAME_2));

		// Cookies

		HttpServletRequest populatedHttpServletRequest =
			spiAgentRequest.populateRequest(new MockHttpServletRequest());

		Cookie[] cookies = populatedHttpServletRequest.getCookies();

		Assert.assertEquals(2, cookies.length);
		Assert.assertSame(_cookie1, cookies[0]);
		Assert.assertSame(_cookie2, cookies[1]);

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

		Assert.assertEquals(3, headerNames.size());
		Assert.assertTrue(headerNames.contains(_HEADER_NAME_1.toLowerCase()));
		Assert.assertTrue(headerNames.contains(_HEADER_NAME_2.toLowerCase()));
		Assert.assertTrue(headerNames.contains(_HEADER_NAME_3.toLowerCase()));

		List<String> headers = ListUtil.fromEnumeration(
			populatedHttpServletRequest.getHeaders(_HEADER_NAME_1));

		Assert.assertEquals(2, headers.size());
		Assert.assertEquals(_HEADER_VALUE_1, headers.get(0));
		Assert.assertEquals(_HEADER_VALUE_2, headers.get(1));

		headers = ListUtil.fromEnumeration(
			populatedHttpServletRequest.getHeaders(_HEADER_NAME_2));

		Assert.assertEquals(2, headers.size());
		Assert.assertEquals(_HEADER_VALUE_3, headers.get(0));
		Assert.assertEquals(_HEADER_VALUE_4, headers.get(1));

		headers = ListUtil.fromEnumeration(
			populatedHttpServletRequest.getHeaders(_HEADER_NAME_3));

		Assert.assertTrue(headers.isEmpty());

		headers = ListUtil.fromEnumeration(
			populatedHttpServletRequest.getHeaders(_HEADER_NAME_4));

		Assert.assertTrue(headers.isEmpty());

		// Parameters

		Map<String, String[]> parameterMap =
			populatedHttpServletRequest.getParameterMap();

		Assert.assertEquals(3, parameterMap.size());

		String[] parameter1 = parameterMap.get(_PARAMETER_NAME_1);

		Assert.assertEquals(2, parameter1.length);
		Assert.assertEquals(_PARAMETER_VALUE_1, parameter1[0]);
		Assert.assertEquals(_PARAMETER_VALUE_2, parameter1[1]);

		String[] parameter2 = parameterMap.get(_PARAMETER_NAME_2);

		Assert.assertEquals(2, parameter2.length);
		Assert.assertEquals(_PARAMETER_VALUE_3, parameter2[0]);
		Assert.assertEquals(_PARAMETER_VALUE_4, parameter2[1]);

		String[] parameter3 = parameterMap.get(_PARAMETER_NAME_3);

		Assert.assertEquals(0, parameter3.length);
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

		Assert.assertEquals(3, parameterNames.size());
		Assert.assertEquals(_PARAMETER_NAME_1, parameterNames.get(0));
		Assert.assertEquals(_PARAMETER_NAME_2, parameterNames.get(1));
		Assert.assertEquals(_PARAMETER_NAME_3, parameterNames.get(2));

		parameter1 = populatedHttpServletRequest.getParameterValues(
			_PARAMETER_NAME_1);

		Assert.assertEquals(2, parameter1.length);
		Assert.assertEquals(_PARAMETER_VALUE_1, parameter1[0]);
		Assert.assertEquals(_PARAMETER_VALUE_2, parameter1[1]);

		parameter2 = populatedHttpServletRequest.getParameterValues(
			_PARAMETER_NAME_2);

		Assert.assertEquals(2, parameter2.length);
		Assert.assertEquals(_PARAMETER_VALUE_3, parameter2[0]);
		Assert.assertEquals(_PARAMETER_VALUE_4, parameter2[1]);

		parameter3 = populatedHttpServletRequest.getParameterValues(
			_PARAMETER_NAME_3);

		Assert.assertEquals(0, parameter3.length);

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

		Assert.assertEquals(2, attributeNames.size());
		Assert.assertTrue(attributeNames.contains(_SESSION_ATTRIBUTE_NAME_1));
		Assert.assertTrue(attributeNames.contains(_SESSION_ATTRIBUTE_NAME_2));

		Assert.assertEquals(
			_SESSION_ATTRIBUTE_VALUE_1,
			mockHttpSession.getAttribute(_SESSION_ATTRIBUTE_NAME_1));
		Assert.assertEquals(
			_SESSION_ATTRIBUTE_VALUE_2,
			mockHttpSession.getAttribute(_SESSION_ATTRIBUTE_NAME_2));

		// To string

		StringBundler sb = new StringBundler(
			13 + cookies.length * 2 + parameterMap.size() * 4);

		sb.append("{cookies=[");

		for (Cookie cookie : cookies) {
			sb.append(CookieUtil.toString(cookie));
			sb.append(", ");
		}

		sb.setIndex(sb.index() - 1);

		sb.append("], distributedRequestAttributes=");
		sb.append(spiAgentRequest.distributedRequestAttributes);
		sb.append(", _headerMap=");
		sb.append(spiAgentRequest.headerMap);
		sb.append(", _multipartParameterMap=null");
		sb.append(", originalSessionAttributes=");
		sb.append(spiAgentRequest.getOriginalSessionAttributes());
		sb.append(", parameterMap={");

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			sb.append(entry.getKey());
			sb.append("=");
			sb.append(Arrays.toString(entry.getValue()));
			sb.append(", ");
		}

		sb.setIndex(sb.index() - 1);

		sb.append("}, _regularParameterMap=null");
		sb.append(", _serverName=");
		sb.append(_SERVER_NAME);
		sb.append(", _serverPort=");
		sb.append(_SERVER_PORT);
		sb.append("}");

		Assert.assertEquals(sb.toString(), spiAgentRequest.toString());

		_mockHttpServletRequest.setCookies((Cookie[])null);

		spiAgentRequest = new SPIAgentRequest(_mockHttpServletRequest);

		sb = new StringBundler(13 + parameterMap.size() * 4);

		sb.append("{cookies=[], distributedRequestAttributes=");
		sb.append(spiAgentRequest.distributedRequestAttributes);
		sb.append(", _headerMap=");
		sb.append(spiAgentRequest.headerMap);
		sb.append(", _multipartParameterMap=null");
		sb.append(", originalSessionAttributes=");
		sb.append(spiAgentRequest.getOriginalSessionAttributes());
		sb.append(", parameterMap={");

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			sb.append(entry.getKey());
			sb.append("=");
			sb.append(Arrays.toString(entry.getValue()));
			sb.append(", ");
		}

		sb.setIndex(sb.index() - 1);

		sb.append("}, _regularParameterMap=null");
		sb.append(", _serverName=");
		sb.append(_SERVER_NAME);
		sb.append(", _serverPort=");
		sb.append(_SERVER_PORT);
		sb.append("}");

		Assert.assertEquals(sb.toString(), spiAgentRequest.toString());
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

	private static Cookie _cookie1 = new Cookie("name1", "value1");
	private static Cookie _cookie2 = new Cookie("name2", "value2");
	private static ThreadLocal<String> _threadLocal = new ThreadLocal<String>();

	private MockHttpServletRequest _mockHttpServletRequest;

}