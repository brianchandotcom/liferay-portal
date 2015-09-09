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

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.kernel.util.ProgressTracker;
import com.liferay.portal.kernel.util.PropsKeys;
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
import com.liferay.portal.util.PrefsPropsUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;

/**
 * @author Manuel de la Peña
 */
public class PortletContainerTestUtil {

	public static HttpServletRequest getHttpServletRequest(
			Group group, Layout layout)
		throws Exception {

		if ((group == null) || (layout == null)) {
			throw new IllegalArgumentException("Arguments cannot be null.");
		}

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

	public static String getString(Map<String, List<String>> map, String key) {
		List<String> values = map.get(key);

		return values.get(0);
	}

	public static LiferayServletRequest mockLiferayServletRequest(
			String fileNameParameter, byte[] bytes)
		throws Exception {

		MockMultipartHttpServletRequest mockMultipartHttpServletRequest =
			new MockMultipartHttpServletRequest();

		mockMultipartHttpServletRequest.setContentType(
			"multipart/form-data;boundary=" + new Date().getTime());

		MockMultipartFile multipartFile = new MockMultipartFile(
			fileNameParameter, bytes);

		mockMultipartHttpServletRequest.addFile(multipartFile);

		mockMultipartHttpServletRequest.setCharacterEncoding("UTF-8");

		mockMultipartHttpServletRequest.setContent(bytes);

		MockHttpSession mockHttpSession = new MockHttpSession();

		mockHttpSession.setAttribute(ProgressTracker.PERCENT, new Object());

		mockMultipartHttpServletRequest.setSession(mockHttpSession);

		ServletFileUpload servletFileUpload = new ServletFileUpload(
			new LiferayFileItemFactory(UploadServletRequestImpl.getTempDir()));

		servletFileUpload.setSizeMax(
			PrefsPropsUtil.getLong(
				PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE));

		servletFileUpload.setHeaderEncoding(
			StandardCharsets.UTF_8.displayName());

		LiferayServletRequest liferayServletRequest = new LiferayServletRequest(
			mockMultipartHttpServletRequest);

		return liferayServletRequest;
	}

	public static void putFileParameter(
			Class<?> clazz, String dependency,
			Map<String, FileItem[]> fileParameters)
		throws Exception {

		putFileParameter(clazz, dependency, fileParameters, null);
	}

	public static void putFileParameter(
			Class<?> clazz, String dependency,
			Map<String, FileItem[]> fileParameters, String namespace)
		throws Exception {

		InputStream inputStream = clazz.getResourceAsStream(dependency);

		byte[] bytes = toByteArray(inputStream);

		int currentIndex = fileParameters.size();

		String fileParameter = "fileParameter" + currentIndex;

		if (namespace != null) {
			fileParameter = namespace.concat(fileParameter);
		}

		FileItem[] fileItems = new FileItem[2];

		LiferayFileItemFactory fileItemFactory = new LiferayFileItemFactory(
			UploadServletRequestImpl.getTempDir());

		for (int i = 0; i < fileItems.length; i++) {
			org.apache.commons.fileupload.FileItem fileItem =
				fileItemFactory.createItem(
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString(), true,
					RandomTestUtil.randomString());

			LiferayFileItem liferayFileItem = (LiferayFileItem)fileItem;

			// force a temp file for the file item

			OutputStream outputStream = liferayFileItem.getOutputStream();

			outputStream.write(bytes);
			outputStream.flush();
			outputStream.close();

			fileItems[i] = liferayFileItem;
		}

		fileParameters.put(fileParameter, fileItems);
	}

	public static void putRegularParameter(
		Map<String, List<String>> fileParameters) {

		int currentIndex = fileParameters.size();

		String fileParameter = "regularParameter" + currentIndex;

		List<String> regularItems = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			regularItems.add(RandomTestUtil.randomString());
		}

		fileParameters.put(fileParameter, regularItems);
	}

	public static Map<String, List<String>> request(String url)
		throws IOException {

		return request(url, null);
	}

	public static Map<String, List<String>> request(
			String url, Map<String, List<String>> headers)
		throws IOException {

		URL urlObject = new URL(url);

		HttpURLConnection httpURLConnection =
			(HttpURLConnection)urlObject.openConnection();

		httpURLConnection.setInstanceFollowRedirects(true);
		httpURLConnection.setConnectTimeout(1500 * 1000);
		httpURLConnection.setReadTimeout(1500 * 1000);

		if (headers != null) {
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
		}

		InputStream inputStream = null;

		try {
			inputStream = httpURLConnection.getInputStream();
		}
		catch (IOException ioe) {
			inputStream = httpURLConnection.getErrorStream();
		}

		try {
			Map<String, List<String>> responseMap = new HashMap<>(
				httpURLConnection.getHeaderFields());

			responseMap.put("body", Arrays.asList(read(inputStream)));

			String code = String.valueOf(httpURLConnection.getResponseCode());

			responseMap.put("code", Arrays.asList(code));

			return responseMap;
		}
		finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}

	public static byte[] toByteArray(InputStream is) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		int nRead = 0;
		byte[] data = new byte[16384];

		while ((nRead = is.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}

		buffer.flush();

		return buffer.toByteArray();
	}

	protected static String read(InputStream inputStream) throws IOException {
		if (inputStream == null) {
			return "";
		}

		return StringUtil.read(inputStream);
	}

}