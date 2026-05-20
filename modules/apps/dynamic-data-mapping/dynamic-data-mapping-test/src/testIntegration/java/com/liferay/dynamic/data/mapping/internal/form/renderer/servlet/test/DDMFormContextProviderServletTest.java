/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.form.renderer.servlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Pedro Queiroz
 */
@RunWith(Arquillian.class)
public class DDMFormContextProviderServletTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testThemeDisplayIsPresent()
		throws IOException, ServletException {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		mockHttpServletRequest.setAttribute(
			WebKeys.CURRENT_URL,
			"http://liferay.com/web/guest/dynamic-data-mapping-form-context-" +
				"provider");

		_ddmFormContextProviderServlet.service(
			mockHttpServletRequest, mockHttpServletResponse);

		Assert.assertNotNull(
			mockHttpServletRequest.getAttribute(WebKeys.THEME_DISPLAY));
	}

	@Test
	public void testUploadExceptionReturnsJSONErrorResponse() throws Exception {

		// Exceeded Liferay file item size limit

		_testUploadExceptionReturnsJSONErrorResponse(_createUploadException(false, true, false));

		// Exceeded file size limit

		_testUploadExceptionReturnsJSONErrorResponse(_createUploadException(true, false, false));

		// Exceeded upload request size limit

		_testUploadExceptionReturnsJSONErrorResponse(_createUploadException(false, false, true));
	}

	private void _testUploadExceptionReturnsJSONErrorResponse(UploadException uploadException)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addPreferredLocale(LocaleUtil.US);
		mockHttpServletRequest.setAttribute(
			WebKeys.UPLOAD_EXCEPTION, uploadException);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_ddmFormContextProviderServlet.service(
			mockHttpServletRequest, mockHttpServletResponse);

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			mockHttpServletResponse.getContentAsString());

		Assert.assertEquals(
			_language.get(LocaleUtil.US, "upload-size-is-too-large"),
			jsonObject.getString("error"));

		Assert.assertEquals(
			ContentTypes.APPLICATION_JSON,
			mockHttpServletResponse.getContentType());
		Assert.assertEquals(
			HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE,
			mockHttpServletResponse.getStatus());
	}

	private UploadException _createUploadException(
		boolean exceededFileSizeLimit, boolean exceededLiferayFileItemSizeLimit,
		boolean exceededUploadRequestSizeLimit) {

		UploadException uploadException = new UploadException(
			RandomTestUtil.randomString());

		uploadException.setExceededFileSizeLimit(exceededFileSizeLimit);
		uploadException.setExceededLiferayFileItemSizeLimit(
			exceededLiferayFileItemSizeLimit);
		uploadException.setExceededUploadRequestSizeLimit(
			exceededUploadRequestSizeLimit);

		return uploadException;
	}

	@Inject(
		filter = "osgi.http.whiteboard.servlet.name=com.liferay.dynamic.data.mapping.form.renderer.internal.servlet.DDMFormContextProviderServlet"
	)
	private Servlet _ddmFormContextProviderServlet;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private Language _language;

}