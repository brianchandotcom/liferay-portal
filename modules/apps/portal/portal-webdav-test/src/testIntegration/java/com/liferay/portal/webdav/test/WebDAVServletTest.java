/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2024-09
 */

package com.liferay.portal.webdav.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.ProtectedServletRequest;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.webdav.WebDAVUtil;
import com.liferay.portal.kernel.webdav.methods.Method;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.webdav.WebDAVServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Jorge Gracía Jiménez
 * @author Nikoletta Buza
 */
@RunWith(Arquillian.class)
public class WebDAVServletTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
	}

	@AfterClass
	public static void tearDownClass() {
	}

	@Test
	public void testSessionContainsAuthenticatedUserData()
		throws PortalException {

		ProtectedServletRequest protectedServletRequest =
			_createProtectedServletRequest();

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_webDAVServlet.service(
			protectedServletRequest, mockHttpServletResponse);

		Assert.assertEquals(
			WebDAVUtil.SC_MULTI_STATUS, mockHttpServletResponse.getStatus());

		HttpSession httpSession = protectedServletRequest.getSession();

		User user = (User)httpSession.getAttribute(WebKeys.USER);

		Assert.assertNotNull(user);
		Assert.assertEquals(TestPropsValues.getUserId(), user.getUserId());
		Assert.assertTrue(user.isActive());

		Assert.assertNotNull(user.getDigest());
	}

	private ProtectedServletRequest _createProtectedServletRequest()
		throws PortalException {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setContextPath(_CONTEXT_PATH);
		mockHttpServletRequest.setMethod(Method.PROPFIND);
		mockHttpServletRequest.setPathInfo(_PATH_INFO_PREFACE);
		mockHttpServletRequest.addHeader(
			HttpHeaders.USER_AGENT, _DEFAULT_USER_AGENT);

		return new ProtectedServletRequest(
			mockHttpServletRequest, String.valueOf(TestPropsValues.getUserId()),
			HttpServletRequest.DIGEST_AUTH);
	}

	private static final String _CONTEXT_PATH = "/webdav";

	private static final String _DEFAULT_USER_AGENT = "litmus";

	private static final String _PATH_INFO_PREFACE =
		"/guest/document_library/Provided by Liferay/stars.png";

	private final WebDAVServlet _webDAVServlet = new WebDAVServlet();

}