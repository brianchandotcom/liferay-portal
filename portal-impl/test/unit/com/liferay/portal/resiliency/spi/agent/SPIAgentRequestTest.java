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

				headerNames.add(_headerName3);

				return Collections.enumeration(headerNames);
			}

			@Override
			public Map<String, String[]> getParameterMap() {
				Map<String, String[]> parameterMap =
					new LinkedHashMap<String, String[]>(
						super.getParameterMap());

				// Parameter with no value

				parameterMap.put(_parameterName3, new String[0]);

				return parameterMap;
			}

		};

		_mockHttpServletRequest.setCookies(_cookie1, _cookie2);

		_mockHttpServletRequest.addHeader(_headerName1, _headerValue1);
		_mockHttpServletRequest.addHeader(_headerName1, _headerValue2);
		_mockHttpServletRequest.addHeader(_headerName2, _headerValue3);
		_mockHttpServletRequest.addHeader(_headerName2, _headerValue4);

		_mockHttpServletRequest.addParameter(_parameterName1, _parameterValue1);
		_mockHttpServletRequest.addParameter(_parameterName1, _parameterValue2);
		_mockHttpServletRequest.addParameter(_parameterName2, _parameterValue3);
		_mockHttpServletRequest.addParameter(_parameterName2, _parameterValue4);

		RequestAttributes.setRequestAttributes(_mockHttpServletRequest);

		_mockHttpServletRequest.setServerName(_serverName);

		_mockHttpServletRequest.setServerPort(_serverPort);

		HttpSession session = _mockHttpServletRequest.getSession();

		session.setAttribute(_sessionAttributeName1, _sessionAttributeValue1);
		session.setAttribute(_sessionAttributeName2, _sessionAttributeValue2);
		session.setAttribute(_sessionAttributeName3, _sessionAttributeValue3);
	}

	@Test
	public void testContentTypeIsMultipart() {

		// UploadServletRequestImpl with null data

		_mockHttpServletRequest.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.MULTIPART_FORM_DATA);

		SPIAgentRequest spiAgentRequest = new SPIAgentRequest(
			new UploadServletRequestImpl(_mockHttpServletRequest, null, null));

		HttpServletRequest populateHttpServletRequest =
			spiAgentRequest.populateRequest(new MockHttpServletRequest());

		Assert.assertSame(
			AgentHttpServletRequestWrapper.class,
			populateHttpServletRequest.getClass());

		// UploadServletRequestImpl with empty data

		spiAgentRequest = new SPIAgentRequest(
			new UploadServletRequestImpl(
				_mockHttpServletRequest, new HashMap<String, FileItem[]>(),
				new HashMap<String, List<String>>()));

		populateHttpServletRequest = spiAgentRequest.populateRequest(
			new MockHttpServletRequest());

		Assert.assertSame(
			AgentHttpServletRequestWrapper.class,
			populateHttpServletRequest.getClass());

		// UploadServletRequestImpl with multipart data

		Map<String, FileItem[]> fileParams = new HashMap<String, FileItem[]>();

		String fileParameter = "fileParameter";

		FileItem[] fileItems = new FileItem[0];

		fileParams.put(fileParameter, fileItems);

		spiAgentRequest = new SPIAgentRequest(
			new UploadServletRequestImpl(
				_mockHttpServletRequest, fileParams,
				new HashMap<String, List<String>>()));

		populateHttpServletRequest = spiAgentRequest.populateRequest(
			new MockHttpServletRequest());

		Assert.assertSame(
			UploadServletRequestImpl.class,
			populateHttpServletRequest.getClass());

		UploadServletRequestImpl uploadServletRequestImpl =
			(UploadServletRequestImpl)populateHttpServletRequest;

		Map<String, FileItem[]> populatedFileParams =
			uploadServletRequestImpl.getMultipartParameterMap();
		Map<String, List<String>> populatedRegularParams =
			uploadServletRequestImpl.getRegularParameterMap();

		Assert.assertEquals(1, populatedFileParams.size());
		Assert.assertSame(fileItems, populatedFileParams.get(fileParameter));
		Assert.assertTrue(populatedRegularParams.isEmpty());

		// UploadServletRequestImpl with multipart and regular data

		Map<String, List<String>> regularParams =
			new HashMap<String, List<String>>();

		String regularParameter = "regularParameter";

		List<String> parameters = new ArrayList<String>();

		regularParams.put(regularParameter, parameters);

		spiAgentRequest = new SPIAgentRequest(
			new UploadServletRequestImpl(
				_mockHttpServletRequest, fileParams, regularParams));

		populateHttpServletRequest = spiAgentRequest.populateRequest(
			new MockHttpServletRequest());

		Assert.assertSame(
			UploadServletRequestImpl.class,
			populateHttpServletRequest.getClass());

		uploadServletRequestImpl =
			(UploadServletRequestImpl)populateHttpServletRequest;

		populatedFileParams =
			uploadServletRequestImpl.getMultipartParameterMap();
		populatedRegularParams =
			uploadServletRequestImpl.getRegularParameterMap();

		Assert.assertEquals(1, populatedFileParams.size());
		Assert.assertSame(fileItems, populatedFileParams.get(fileParameter));
		Assert.assertEquals(1, populatedRegularParams.size());
		Assert.assertSame(
			parameters, populatedRegularParams.get(regularParameter));

		// UploadServletRequestImpl with regular data

		spiAgentRequest = new SPIAgentRequest(
			new UploadServletRequestImpl(
				_mockHttpServletRequest, new HashMap<String, FileItem[]>(),
				regularParams));

		populateHttpServletRequest = spiAgentRequest.populateRequest(
			new MockHttpServletRequest());

		Assert.assertSame(
			UploadServletRequestImpl.class,
			populateHttpServletRequest.getClass());

		uploadServletRequestImpl =
			(UploadServletRequestImpl)populateHttpServletRequest;

		populatedFileParams =
			uploadServletRequestImpl.getMultipartParameterMap();
		populatedRegularParams =
			uploadServletRequestImpl.getRegularParameterMap();

		Assert.assertTrue(populatedFileParams.isEmpty());
		Assert.assertEquals(1, populatedRegularParams.size());
		Assert.assertSame(
			parameters, populatedRegularParams.get(regularParameter));
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
			_sessionAttributeValue1,
			originalSessionAttributes.get(_sessionAttributeName1));
		Assert.assertEquals(
			_sessionAttributeValue2,
			originalSessionAttributes.get(_sessionAttributeName2));

		// Cookies

		HttpServletRequest populatedHttpServletRequest =
			spiAgentRequest.populateRequest(new MockHttpServletRequest());

		Cookie[] cookies = populatedHttpServletRequest.getCookies();

		Assert.assertEquals(2, cookies.length);
		Assert.assertSame(_cookie1, cookies[0]);
		Assert.assertSame(_cookie2, cookies[1]);

		// Headers

		Assert.assertEquals(
			_headerValue1, populatedHttpServletRequest.getHeader(_headerName1));
		Assert.assertEquals(
			_headerValue3, populatedHttpServletRequest.getHeader(_headerName2));
		Assert.assertNull(populatedHttpServletRequest.getHeader(_headerName3));
		Assert.assertNull(populatedHttpServletRequest.getHeader(_headerName4));

		List<String> headerNames = ListUtil.fromEnumeration(
			populatedHttpServletRequest.getHeaderNames());

		Assert.assertEquals(3, headerNames.size());
		Assert.assertTrue(headerNames.contains(_headerName1.toLowerCase()));
		Assert.assertTrue(headerNames.contains(_headerName2.toLowerCase()));
		Assert.assertTrue(headerNames.contains(_headerName3.toLowerCase()));

		List<String> headers = ListUtil.fromEnumeration(
			populatedHttpServletRequest.getHeaders(_headerName1));

		Assert.assertEquals(2, headers.size());
		Assert.assertEquals(_headerValue1, headers.get(0));
		Assert.assertEquals(_headerValue2, headers.get(1));

		headers = ListUtil.fromEnumeration(
			populatedHttpServletRequest.getHeaders(_headerName2));

		Assert.assertEquals(2, headers.size());
		Assert.assertEquals(_headerValue3, headers.get(0));
		Assert.assertEquals(_headerValue4, headers.get(1));

		headers = ListUtil.fromEnumeration(
			populatedHttpServletRequest.getHeaders(_headerName3));

		Assert.assertTrue(headers.isEmpty());

		headers = ListUtil.fromEnumeration(
			populatedHttpServletRequest.getHeaders(_headerName4));

		Assert.assertTrue(headers.isEmpty());

		// Parameters

		Map<String, String[]> parameterMap =
			populatedHttpServletRequest.getParameterMap();

		Assert.assertEquals(3, parameterMap.size());

		String[] parameter1 = parameterMap.get(_parameterName1);

		Assert.assertEquals(2, parameter1.length);
		Assert.assertEquals(_parameterValue1, parameter1[0]);
		Assert.assertEquals(_parameterValue2, parameter1[1]);

		String[] parameter2 = parameterMap.get(_parameterName2);

		Assert.assertEquals(2, parameter2.length);
		Assert.assertEquals(_parameterValue3, parameter2[0]);
		Assert.assertEquals(_parameterValue4, parameter2[1]);

		String[] parameter3 = parameterMap.get(_parameterName3);

		Assert.assertEquals(0, parameter3.length);

		Assert.assertEquals(
			_parameterValue1,
			populatedHttpServletRequest.getParameter(_parameterName1));
		Assert.assertEquals(
			_parameterValue3,
			populatedHttpServletRequest.getParameter(_parameterName2));
		Assert.assertNull(
			populatedHttpServletRequest.getParameter(_parameterName3));
		Assert.assertNull(
			populatedHttpServletRequest.getParameter(_parameterName4));

		List<String> parameterNames = ListUtil.fromEnumeration(
			populatedHttpServletRequest.getParameterNames());

		Assert.assertEquals(3, parameterNames.size());
		Assert.assertEquals(_parameterName1, parameterNames.get(0));
		Assert.assertEquals(_parameterName2, parameterNames.get(1));
		Assert.assertEquals(_parameterName3, parameterNames.get(2));

		parameter1 = populatedHttpServletRequest.getParameterValues(
			_parameterName1);

		Assert.assertEquals(2, parameter1.length);
		Assert.assertEquals(_parameterValue1, parameter1[0]);
		Assert.assertEquals(_parameterValue2, parameter1[1]);

		parameter2 = populatedHttpServletRequest.getParameterValues(
			_parameterName2);

		Assert.assertEquals(2, parameter2.length);
		Assert.assertEquals(_parameterValue3, parameter2[0]);
		Assert.assertEquals(_parameterValue4, parameter2[1]);

		parameter3 = populatedHttpServletRequest.getParameterValues(
			_parameterName3);

		Assert.assertEquals(0, parameter3.length);

		// Server name

		Assert.assertEquals(
			_serverName, populatedHttpServletRequest.getServerName());

		// Server port

		Assert.assertEquals(
			_serverPort, populatedHttpServletRequest.getServerPort());

		Assert.assertEquals(threadLocalValue, _threadLocal.get());

		// Populated session attributes

		MockHttpSession mockHttpSession = new MockHttpSession();

		spiAgentRequest.populateSessionAttributes(mockHttpSession);

		List<String> attributeNames = ListUtil.fromEnumeration(
			mockHttpSession.getAttributeNames());

		Assert.assertEquals(2, attributeNames.size());
		Assert.assertTrue(attributeNames.contains(_sessionAttributeName1));
		Assert.assertTrue(attributeNames.contains(_sessionAttributeName2));

		Assert.assertEquals(
			_sessionAttributeValue1,
			mockHttpSession.getAttribute(_sessionAttributeName1));
		Assert.assertEquals(
			_sessionAttributeValue2,
			mockHttpSession.getAttribute(_sessionAttributeName2));

		// toString

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
		sb.append(_serverName);
		sb.append(", _serverPort=");
		sb.append(_serverPort);
		sb.append("}");

		Assert.assertEquals(sb.toString(), spiAgentRequest.toString());

		_mockHttpServletRequest.setCookies(null);

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
		sb.append(_serverName);
		sb.append(", _serverPort=");
		sb.append(_serverPort);
		sb.append("}");

		Assert.assertEquals(sb.toString(), spiAgentRequest.toString());
	}

	private static Cookie _cookie1 = new Cookie("name1", "value1");
	private static Cookie _cookie2 = new Cookie("name2", "value2");
	private static String _headerName1 = "headerName1";
	private static String _headerName2 = "headerName2";
	private static String _headerName3 = "headerName3";
	private static String _headerName4 = "headerName4";
	private static String _headerValue1 = "headerValue1";
	private static String _headerValue2 = "headerValue2";
	private static String _headerValue3 = "headerValue3";
	private static String _headerValue4 = "headerValue4";
	private static String _parameterName1 = "parameterName1";
	private static String _parameterName2 = "parameterName2";
	private static String _parameterName3 = "parameterName3";
	private static String _parameterName4 = "parameterName4";
	private static String _parameterValue1 = "parameterValue1";
	private static String _parameterValue2 = "parameterValue2";
	private static String _parameterValue3 = "parameterValue3";
	private static String _parameterValue4 = "parameterValue4";
	private static String _serverName = "serverName";
	private static int _serverPort = 1023;
	private static String _sessionAttributeName1 = "sessionAttributeName1";
	private static String _sessionAttributeName2 = "sessionAttributeName2";
	private static String _sessionAttributeName3 = "sessionAttributeName3";
	private static String _sessionAttributeValue1 = "sessionAttributeValue1";
	private static String _sessionAttributeValue2 = "sessionAttributeValue2";
	private static Object _sessionAttributeValue3 = new Object();
	private static ThreadLocal<String> _threadLocal = new ThreadLocal<String>();

	private MockHttpServletRequest _mockHttpServletRequest;

}