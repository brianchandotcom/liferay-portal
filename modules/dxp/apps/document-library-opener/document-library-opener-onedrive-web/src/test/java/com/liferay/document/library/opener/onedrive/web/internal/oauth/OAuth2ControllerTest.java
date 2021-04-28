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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.servlet.BrowserSnifferImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.UnsupportedEncodingException;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Cristina González
 */
public class OAuth2ControllerTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws PortalException {
		BrowserSnifferUtil browserSnifferUtil = new BrowserSnifferUtil();

		browserSnifferUtil.setBrowserSniffer(new BrowserSnifferImpl());

		_oAuth2Manager = Mockito.mock(OAuth2Manager.class);

		Mockito.when(
			_oAuth2Manager.getAuthorizationURL(
				Matchers.anyLong(), Matchers.anyString(), Matchers.anyString())
		).thenReturn(
			"authorizationURL"
		);

		_portal = Mockito.mock(Portal.class);

		Mockito.when(
			_portal.getPortalURL((PortletRequest)Matchers.any())
		).thenReturn(
			RandomTestUtil.randomString()
		);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(_portal);

		_portletURLFactory = Mockito.mock(PortletURLFactory.class);

		_liferayPortletURL = Mockito.mock(LiferayPortletURL.class);

		Mockito.when(
			_liferayPortletURL.toString()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_portletURLFactory.create(
				Matchers.any(PortletRequest.class), Matchers.anyString(),
				Matchers.anyLong(), Matchers.anyString())
		).thenReturn(
			_liferayPortletURL
		);

		Mockito.when(
			_portletURLFactory.create(
				Matchers.any(PortletRequest.class), Matchers.anyString(),
				Matchers.anyString())
		).thenReturn(
			_liferayPortletURL
		);

		_oAuth2ControllerFactory = new OAuth2ControllerFactory();

		ReflectionTestUtil.setFieldValue(
			_oAuth2ControllerFactory, "_oAuth2Manager", _oAuth2Manager);

		ReflectionTestUtil.setFieldValue(
			_oAuth2ControllerFactory, "_portal", _portal);

		ReflectionTestUtil.setFieldValue(
			_oAuth2ControllerFactory, "_portletURLFactory", _portletURLFactory);
	}

	@Test
	public void testJSONOAuth2ControllerWithAccessToken()
		throws PortalException, UnsupportedEncodingException {

		Mockito.when(
			_oAuth2Manager.hasAccessToken(
				Matchers.anyLong(), Matchers.anyLong())
		).thenReturn(
			true
		);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		JSONObject jsonObject = JSONUtil.put("key", "value");

		OAuth2Controller oAuth2Controller =
			_oAuth2ControllerFactory.getJSONOAuth2Controller(null);

		oAuth2Controller.execute(
			_getMockPortletRequest(mockHttpServletRequest),
			_getMockPortletResponse(mockHttpServletResponse),
			portletRequest -> jsonObject);

		Assert.assertNull(
			mockHttpServletRequest.getAttribute(WebKeys.REDIRECT));

		Assert.assertEquals(
			jsonObject.toString(),
			mockHttpServletResponse.getContentAsString());
	}

	@Test
	public void testJSONOAuth2ControllerWithoutAccessToken()
		throws PortalException, UnsupportedEncodingException {

		Mockito.when(
			_oAuth2Manager.hasAccessToken(
				Matchers.anyLong(), Matchers.anyLong())
		).thenReturn(
			false
		);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		OAuth2Controller oAuth2Controller =
			_oAuth2ControllerFactory.getJSONOAuth2Controller(
				portalResquest -> RandomTestUtil.randomString());

		oAuth2Controller.execute(
			_getMockPortletRequest(mockHttpServletRequest),
			_getMockPortletResponse(mockHttpServletResponse),
			portletRequest -> JSONFactoryUtil.createJSONObject());

		Assert.assertNull(
			mockHttpServletRequest.getAttribute(WebKeys.REDIRECT));
		Assert.assertEquals(
			String.valueOf(JSONUtil.put("redirectURL", "authorizationURL")),
			mockHttpServletResponse.getContentAsString());
	}

	@Test
	public void testRedirectingOAuth2ControllerWithAccessToken()
		throws PortalException {

		Mockito.when(
			_oAuth2Manager.hasAccessToken(
				Matchers.anyLong(), Matchers.anyLong())
		).thenReturn(
			true
		);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		JSONObject jsonObject = JSONUtil.put("key", "value");

		OAuth2Controller oAuth2Controller =
			_oAuth2ControllerFactory.getRedirectingOAuth2Controller();

		oAuth2Controller.execute(
			_getMockPortletRequest(mockHttpServletRequest),
			_getMockPortletResponse(mockHttpServletResponse),
			portletRequest -> jsonObject);

		Assert.assertEquals(
			_liferayPortletURL.toString(),
			mockHttpServletRequest.getAttribute(WebKeys.REDIRECT));

		Assert.assertEquals(
			mockHttpServletRequest.getAttribute("key"), jsonObject.get("key"));
	}

	@Test
	public void testRedirectingOAuth2ControllerWithoutAccessToken()
		throws PortalException {

		Mockito.when(
			_oAuth2Manager.hasAccessToken(
				Matchers.anyLong(), Matchers.anyLong())
		).thenReturn(
			false
		);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		OAuth2Controller oAuth2Controller =
			_oAuth2ControllerFactory.getRedirectingOAuth2Controller();

		oAuth2Controller.execute(
			_getMockPortletRequest(mockHttpServletRequest),
			_getMockPortletResponse(mockHttpServletResponse),
			portletRequest -> JSONFactoryUtil.createJSONObject());

		Assert.assertEquals(
			"authorizationURL",
			mockHttpServletRequest.getAttribute(WebKeys.REDIRECT));

		HttpSession httpSession = mockHttpServletRequest.getSession();

		Assert.assertNotNull(
			httpSession.getAttribute(DLOpenerOneDriveWebKeys.OAUTH2_STATE));
	}

	private PortletRequest _getMockPortletRequest(
		HttpServletRequest httpServletRequest) {

		Mockito.when(
			_portal.getCurrentURL(httpServletRequest)
		).thenReturn(
			"currentURL"
		);

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

		_portletURLFactory.create(
			portletRequest, _portal.getPortletId(portletRequest),
			PortletRequest.ACTION_PHASE);

		Mockito.when(
			portletRequest.getParameter(Matchers.anyString())
		).thenAnswer(
			invocation -> {
				Object[] arguments = invocation.getArguments();

				return httpServletRequest.getParameter(
					String.valueOf(arguments[0]));
			}
		);

		Mockito.when(
			portletRequest.getAttribute(Matchers.anyString())
		).thenAnswer(
			invocation -> {
				Object[] arguments = invocation.getArguments();

				return httpServletRequest.getAttribute(
					String.valueOf(arguments[0]));
			}
		);

		Mockito.doAnswer(
			answer -> {
				Object[] arguments = answer.getArguments();

				httpServletRequest.setAttribute(
					String.valueOf(arguments[0]), arguments[1]);

				return null;
			}
		).when(
			portletRequest
		).setAttribute(
			Matchers.anyString(), Matchers.anyObject()
		);

		return portletRequest;
	}

	private PortletResponse _getMockPortletResponse(
		HttpServletResponse httpServletResponse) {

		PortletResponse portletResponse = Mockito.mock(PortletResponse.class);

		Mockito.when(
			_portal.getHttpServletResponse(portletResponse)
		).thenReturn(
			httpServletResponse
		);

		return portletResponse;
	}

	private static LiferayPortletURL _liferayPortletURL;
	private static OAuth2ControllerFactory _oAuth2ControllerFactory;
	private static OAuth2Manager _oAuth2Manager;
	private static Portal _portal;
	private static PortletURLFactory _portletURLFactory;

}