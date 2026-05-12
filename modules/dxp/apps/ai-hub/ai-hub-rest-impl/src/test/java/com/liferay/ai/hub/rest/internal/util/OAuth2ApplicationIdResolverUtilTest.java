/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.internal.util;

import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationLocalService;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.service.Snapshot;
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
public class OAuth2ApplicationIdResolverUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_oAuth2AuthorizationLocalService = Mockito.mock(
			OAuth2AuthorizationLocalService.class);

		ReflectionTestUtil.setFieldValue(
			OAuth2AuthorizationLocalServiceUtil.class, "_serviceSnapshot",
			new Snapshot<OAuth2AuthorizationLocalService>(
				OAuth2AuthorizationLocalServiceUtil.class,
				OAuth2AuthorizationLocalService.class) {

				@Override
				public OAuth2AuthorizationLocalService get() {
					return _oAuth2AuthorizationLocalService;
				}

			});
	}

	@Test(expected = PrincipalException.class)
	public void testResolveWithBasicAuthScheme() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader(
			HttpHeaders.AUTHORIZATION,
			"Basic " + RandomTestUtil.randomString());

		OAuth2ApplicationIdResolverUtil.resolve(mockHttpServletRequest);
	}

	@Test
	public void testResolveWithBlankHeader() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader(
			HttpHeaders.AUTHORIZATION, StringPool.BLANK);

		Assert.assertEquals(
			0L,
			OAuth2ApplicationIdResolverUtil.resolve(mockHttpServletRequest));
	}

	@Test(expected = PrincipalException.class)
	public void testResolveWithMalformedAuthorization() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader(
			HttpHeaders.AUTHORIZATION, RandomTestUtil.randomString());

		OAuth2ApplicationIdResolverUtil.resolve(mockHttpServletRequest);
	}

	@Test
	public void testResolveWithMissingHeader() throws Exception {
		Assert.assertEquals(
			0L,
			OAuth2ApplicationIdResolverUtil.resolve(
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

		OAuth2ApplicationIdResolverUtil.resolve(mockHttpServletRequest);
	}

	@Test
	public void testResolveWithValidBearerToken() throws Exception {
		OAuth2Authorization oAuth2Authorization = Mockito.mock(
			OAuth2Authorization.class);

		long oAuth2ApplicationId = RandomTestUtil.randomLong();

		Mockito.when(
			oAuth2Authorization.getOAuth2ApplicationId()
		).thenReturn(
			oAuth2ApplicationId
		);

		String token = RandomTestUtil.randomString();

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
			OAuth2ApplicationIdResolverUtil.resolve(mockHttpServletRequest));
	}

	private OAuth2AuthorizationLocalService _oAuth2AuthorizationLocalService;

}