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

package com.liferay.portal.util.test;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ProgressTracker;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.theme.ThemeDisplayFactory;
import com.liferay.portal.upload.LiferayFileItem;
import com.liferay.portal.upload.LiferayFileItemFactory;
import com.liferay.portal.upload.LiferayServletRequest;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portlet.PortletURLFactoryUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.StatusLine;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;

/**
 * @author Manuel de la Peña
 */
public class PortletContainerTestUtil {

	public static Map<String, FileItem[]> getFileParameters(
			int size, Class<?> clazz, String dependency)
		throws Exception {

		return getFileParameters(size, null, clazz, dependency);
	}

	public static Map<String, FileItem[]> getFileParameters(
			int size, String namespace, Class<?> clazz, String dependency)
		throws Exception {

		Map<String, FileItem[]> fileParameters = new HashMap<>();

		for (int i = 0; i < size; i++) {
			String fileParameter = "fileParameter" + i;

			if (namespace != null) {
				fileParameter = namespace.concat(fileParameter);
			}

			fileParameters.put(fileParameter, _getFileItems(clazz, dependency));
		}

		return fileParameters;
	}

	public static HttpServletRequest getHttpServletRequest(
			Group group, Layout layout)
		throws PortalException {

		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		httpServletRequest.setAttribute(WebKeys.LAYOUT, layout);

		ThemeDisplay themeDisplay = ThemeDisplayFactory.create();

		Company company = CompanyLocalServiceUtil.getCompany(
			layout.getCompanyId());

		themeDisplay.setCompany(company);

		themeDisplay.setLayout(layout);
		themeDisplay.setPlid(layout.getPlid());
		themeDisplay.setPortalURL(TestPropsValues.PORTAL_URL);
		themeDisplay.setRequest(httpServletRequest);
		themeDisplay.setScopeGroupId(group.getGroupId());
		themeDisplay.setSiteGroupId(group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		return httpServletRequest;
	}

	public static LiferayServletRequest getMultipartRequest(
		String fileNameParameter, byte[] bytes) {

		MockMultipartHttpServletRequest mockMultipartHttpServletRequest =
			new MockMultipartHttpServletRequest();

		mockMultipartHttpServletRequest.setContentType(
			"multipart/form-data;boundary=" + System.currentTimeMillis());

		mockMultipartHttpServletRequest.addFile(
			new MockMultipartFile(fileNameParameter, bytes));

		mockMultipartHttpServletRequest.setCharacterEncoding("UTF-8");

		mockMultipartHttpServletRequest.setContent(bytes);

		MockHttpSession mockHttpSession = new MockHttpSession();

		mockHttpSession.setAttribute(ProgressTracker.PERCENT, new Object());

		mockMultipartHttpServletRequest.setSession(mockHttpSession);

		return new LiferayServletRequest(mockMultipartHttpServletRequest);
	}

	public static Response getPortalAuthentication(
			HttpServletRequest httpServletRequest, Layout layout,
			String portletId)
		throws Exception {

		// Get the portal authentication token by making a resource request

		PortletURL portletURL = PortletURLFactoryUtil.create(
			httpServletRequest, portletId, layout.getPlid(),
			PortletRequest.RESOURCE_PHASE);

		return request(portletURL.toString());
	}

	public static Map<String, List<String>> getRegularParameters(int size) {
		Map<String, List<String>> regularParameters = new HashMap<>();

		for (int i = 0; i < size; i++) {
			List<String> items = new ArrayList<>();

			for (int j = 0; j < 10; j++) {
				items.add(RandomTestUtil.randomString());
			}

			regularParameters.put("regularParameter" + i, items);
		}

		return regularParameters;
	}

	public static Response postMultipart(
			String url,
			MockMultipartHttpServletRequest mockMultipartHttpServletRequest,
			String fileNameParameter)
		throws IOException, URISyntaxException {

		if (mockMultipartHttpServletRequest.getInputStream() == null) {
			throw new IllegalStateException(
				"An inputStream must be present on the mock request.");
		}

		String[] cookies = mockMultipartHttpServletRequest.getParameterValues(
			"Cookie");

		if ((cookies == null) || (cookies.length == 0)) {
			throw new IllegalStateException(
				"Valid cookies must be present on the mock request.");
		}

		PostMethod postMethod = new PostMethod(url);

		for (String cookie : cookies) {
			postMethod.addRequestHeader(new Header("Cookie", cookie));
		}

		byte[] bytes = FileUtil.getBytes(
			mockMultipartHttpServletRequest.getInputStream());

		Part[] parts = {
			new FilePart(
				fileNameParameter,
				new ByteArrayPartSource(fileNameParameter, bytes))
		};

		MultipartRequestEntity multipartRequestEntity =
			new MultipartRequestEntity(parts, postMethod.getParams());

		postMethod.setRequestEntity(multipartRequestEntity);

		HttpClient client = new HttpClient();

		client.executeMethod(postMethod);

		StatusLine statusLine = postMethod.getStatusLine();

		return new Response(
			statusLine.getStatusCode(), postMethod.getResponseBodyAsString(),
			null);
	}

	public static Response request(String url) throws IOException {
		return request(url, Collections.<String, List<String>>emptyMap());
	}

	public static Response request(
			String url, Map<String, List<String>> headers)
		throws IOException {

		URL urlObject = new URL(url);

		HttpURLConnection httpURLConnection =
			(HttpURLConnection)urlObject.openConnection();

		httpURLConnection.setInstanceFollowRedirects(true);
		httpURLConnection.setConnectTimeout(1500 * 1000);
		httpURLConnection.setReadTimeout(1500 * 1000);

		for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
			String key = entry.getKey();

			for (String value : entry.getValue()) {
				if (key.equals("Cookie")) {
					httpURLConnection.addRequestProperty(
						key, value.split(";", 2)[0]);
				}
				else {
					httpURLConnection.setRequestProperty(key, value);
				}
			}
		}

		InputStream inputStream = null;

		try {
			inputStream = httpURLConnection.getInputStream();
		}
		catch (IOException ioe) {
			inputStream = httpURLConnection.getErrorStream();
		}

		try {
			Map<String, List<String>> headerFields =
				httpURLConnection.getHeaderFields();

			return new Response(
				httpURLConnection.getResponseCode(), read(inputStream),
				headerFields.get("Set-Cookie"));
		}
		finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}

	public static class Response {

		public String getBody() {
			return _body;
		}

		public int getCode() {
			return _code;
		}

		public List<String> getCookies() {
			return _cookies;
		}

		private Response(int code, String body, List<String> cookies) {
			_code = code;
			_body = body;
			_cookies = cookies;
		}

		private final String _body;
		private final int _code;
		private final List<String> _cookies;

	}

	protected static String read(InputStream inputStream) throws IOException {
		if (inputStream == null) {
			return "";
		}

		return StringUtil.read(inputStream);
	}

	private static FileItem[] _getFileItems(Class<?> clazz, String dependency)
		throws IOException {

		byte[] bytes = FileUtil.getBytes(clazz.getResourceAsStream(dependency));

		LiferayFileItem[] liferayFileItems = new LiferayFileItem[2];

		LiferayFileItemFactory fileItemFactory = new LiferayFileItemFactory(
			UploadServletRequestImpl.getTempDir());

		for (int i = 0; i < liferayFileItems.length; i++) {
			liferayFileItems[i] = fileItemFactory.createItem(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				true, RandomTestUtil.randomString());

			try (OutputStream outputStream =
				liferayFileItems[i].getOutputStream()) {

				outputStream.write(bytes);
			}
		}

		return liferayFileItems;
	}

}