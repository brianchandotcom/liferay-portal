/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.document.library.opener.onedrive.web.internal.oauth;

import com.liferay.document.library.opener.onedrive.web.internal.constants.DLOpenerOneDriveWebKeys;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Cristina González
 */
public class OAuth2ControllerTest {

	@BeforeClass
	public static void setUpClass() throws PortalException {
		_portal = Mockito.mock(Portal.class);

		_oAuth2Manager = Mockito.mock(OAuth2Manager.class);

		_portletURLFactory = Mockito.mock(PortletURLFactory.class);

		_liferayPortletURL = Mockito.mock(LiferayPortletURL.class);

		Mockito.when(
			_liferayPortletURL.toString()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_oAuth2Manager.getAuthorizationURL(
				Matchers.anyLong(), Matchers.anyString(), Matchers.anyString())
		).thenReturn(
			"authorizationURL"
		);

		Mockito.when(
			_portal.getPortalURL((PortletRequest)Matchers.any())
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_portletURLFactory.create(
				(PortletRequest)Matchers.any(), Matchers.anyString(),
				Matchers.anyLong(), Matchers.anyString())
		).thenReturn(
			_liferayPortletURL
		);

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		_oAuth2Controller = new OAuth2Controller(
			_oAuth2Manager, _portal, _portletURLFactory);
	}

	@Test
	public void testExecuteWithAccessToken() throws PortalException {
		Mockito.when(
			_oAuth2Manager.hasAccessToken(
				Matchers.anyLong(), Matchers.anyLong())
		).thenReturn(
			true
		);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		OAuth2Controller.OAuth2Result oAuth2Result = _oAuth2Controller.execute(
			_getMockPortletRequest(mockHttpServletRequest),
			portletRequest -> JSONUtil.put("key", "value"), "successURL");

		Assert.assertFalse(oAuth2Result.isRedirect());

		JSONObject jsonObject = oAuth2Result.getResponse();

		Assert.assertEquals("value", jsonObject.getString("key"));

		HttpSession httpSession = mockHttpServletRequest.getSession();

		Assert.assertNull(
			httpSession.getAttribute(DLOpenerOneDriveWebKeys.OAUTH2_STATE));
	}

	@Test
	public void testExecuteWithoutAccessToken() throws PortalException {
		Mockito.when(
			_oAuth2Manager.hasAccessToken(
				Matchers.anyLong(), Matchers.anyLong())
		).thenReturn(
			false
		);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		OAuth2Controller.OAuth2Result oAuth2Result = _oAuth2Controller.execute(
			_getMockPortletRequest(mockHttpServletRequest),
			portletRequest -> JSONFactoryUtil.createJSONObject(),
			RandomTestUtil.randomString());

		Assert.assertTrue(oAuth2Result.isRedirect());
		Assert.assertEquals("authorizationURL", oAuth2Result.getRedirectURL());

		HttpSession httpSession = mockHttpServletRequest.getSession();

		Assert.assertNotNull(
			httpSession.getAttribute(DLOpenerOneDriveWebKeys.OAUTH2_STATE));
	}

	private PortletRequest _getMockPortletRequest(
		HttpServletRequest httpServletRequest) {

		PortletRequest portletRequest = Mockito.mock(PortletRequest.class);

		Mockito.when(
			_portal.getHttpServletRequest(portletRequest)
		).thenReturn(
			httpServletRequest
		);

		Mockito.when(
			_portal.getOriginalServletRequest(Matchers.any())
		).thenReturn(
			httpServletRequest
		);

		return portletRequest;
	}

	private static LiferayPortletURL _liferayPortletURL;
	private static OAuth2Controller _oAuth2Controller;
	private static OAuth2Manager _oAuth2Manager;
	private static Portal _portal;
	private static PortletURLFactory _portletURLFactory;

}