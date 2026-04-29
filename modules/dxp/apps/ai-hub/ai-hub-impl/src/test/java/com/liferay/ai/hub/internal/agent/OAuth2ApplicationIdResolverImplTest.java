/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.agent;

import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Christopher Kian
 */
public class OAuth2ApplicationIdResolverImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_oAuth2AuthorizationLocalService = Mockito.mock(
			OAuth2AuthorizationLocalService.class);

		ReflectionTestUtil.setFieldValue(
			_oAuth2ApplicationIdResolverImpl,
			"_oAuth2AuthorizationLocalService",
			_oAuth2AuthorizationLocalService);
	}

	@Test(expected = PrincipalException.class)
	public void testResolveWithBasicAuthScheme() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader(
			HttpHeaders.AUTHORIZATION,
			"Basic " + RandomTestUtil.randomString());

		_oAuth2ApplicationIdResolverImpl.resolve(mockHttpServletRequest);
	}

	@Test
	public void testResolveWithBlankHeader() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader(
			HttpHeaders.AUTHORIZATION, StringPool.BLANK);

		Assert.assertEquals(
			0L,
			_oAuth2ApplicationIdResolverImpl.resolve(mockHttpServletRequest));
	}

	@Test(expected = PrincipalException.class)
	public void testResolveWithMalformedAuthorization() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader(
			HttpHeaders.AUTHORIZATION, RandomTestUtil.randomString());

		_oAuth2ApplicationIdResolverImpl.resolve(mockHttpServletRequest);
	}

	@Test
	public void testResolveWithMissingHeader() throws Exception {
		Assert.assertEquals(
			0L,
			_oAuth2ApplicationIdResolverImpl.resolve(
				new MockHttpServletRequest()));
	}

	@Test(expected = PrincipalException.class)
	public void testResolveWithUnknownBearerToken() throws Exception {
		String token = RandomTestUtil.randomString();

		Mockito.when(
			_oAuth2AuthorizationLocalService.
				fetchOAuth2AuthorizationByAccessTokenContent(token)
		).thenReturn(
			null
		);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader(
			HttpHeaders.AUTHORIZATION, "Bearer " + token);

		_oAuth2ApplicationIdResolverImpl.resolve(mockHttpServletRequest);
	}

	@Test
	public void testResolveWithValidBearerToken() throws Exception {
		String token = RandomTestUtil.randomString();

		long oAuth2ApplicationId = RandomTestUtil.randomLong();

		OAuth2Authorization oAuth2Authorization = Mockito.mock(
			OAuth2Authorization.class);

		Mockito.when(
			oAuth2Authorization.getOAuth2ApplicationId()
		).thenReturn(
			oAuth2ApplicationId
		);

		Mockito.when(
			_oAuth2AuthorizationLocalService.
				fetchOAuth2AuthorizationByAccessTokenContent(token)
		).thenReturn(
			oAuth2Authorization
		);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader(
			HttpHeaders.AUTHORIZATION, "Bearer " + token);

		Assert.assertEquals(
			oAuth2ApplicationId,
			_oAuth2ApplicationIdResolverImpl.resolve(mockHttpServletRequest));
	}

	private final OAuth2ApplicationIdResolverImpl
		_oAuth2ApplicationIdResolverImpl =
			new OAuth2ApplicationIdResolverImpl();
	private OAuth2AuthorizationLocalService _oAuth2AuthorizationLocalService;

}