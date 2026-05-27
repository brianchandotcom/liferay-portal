/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.service.impl;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.net.HttpURLConnection;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

/**
 * @author Rafael Praxedes
 */
public class OAuthClientEntryLocalServiceImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testValidateAuthServerWellKnownURI() throws Exception {
		OAuthClientEntryLocalServiceImpl oAuthClientEntryLocalServiceImpl =
			new OAuthClientEntryLocalServiceImpl();

		Http http = Mockito.mock(Http.class);

		Mockito.when(
			http.URLtoString(Mockito.any(Http.Options.class))
		).thenAnswer(
			invocation -> {
				Http.Options httpOptions = invocation.getArgument(0);

				Http.Response httpResponse = new Http.Response();

				httpResponse.setResponseCode(HttpURLConnection.HTTP_OK);

				httpOptions.setResponse(httpResponse);

				return "{}";
			}
		);

		ReflectionTestUtil.setFieldValue(
			oAuthClientEntryLocalServiceImpl, "_http", http);

		ReflectionTestUtil.invoke(
			oAuthClientEntryLocalServiceImpl, "_validateAuthServerWellKnownURI",
			new Class<?>[] {long.class, String.class},
			RandomTestUtil.randomLong(),
			"http://" + RandomTestUtil.randomString() +
				"/.well-known/openid-configuration");

		ArgumentCaptor<Http.Options> argumentCaptor = ArgumentCaptor.forClass(
			Http.Options.class);

		Mockito.verify(
			http
		).URLtoString(
			argumentCaptor.capture()
		);

		Http.Options httpOptions = argumentCaptor.getValue();

		Assert.assertEquals(
			Http.CookieSpec.STANDARD, httpOptions.getCookieSpec());
	}

}