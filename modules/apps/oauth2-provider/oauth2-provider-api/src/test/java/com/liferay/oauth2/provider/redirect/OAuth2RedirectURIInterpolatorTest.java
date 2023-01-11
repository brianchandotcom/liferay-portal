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

package com.liferay.oauth2.provider.redirect;

import com.liferay.oauth2.provider.redirect.OAuth2RedirectURIInterpolator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * @author Raymond Augé
 */
public class OAuth2RedirectURIInterpolatorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testInterpolateRedirectURIsListInsecureDefaultPort() {
		Mockito.when(
			_portal.isSecure(_httpServletRequest)
		).then(
			new Answer<Boolean>() {

				@Override
				public Boolean answer(InvocationOnMock invocation)
					throws Throwable {

					return false;
				}

			}
		);

		Mockito.when(
			_portal.getForwardedPort(_httpServletRequest)
		).then(
			new Answer<Integer>() {

				@Override
				public Integer answer(InvocationOnMock invocation)
					throws Throwable {

					return 80;
				}

			}
		);

		List<String> redirectURIsList = Arrays.asList(
			"http://localhost/foo", "@protocol@://localhost/foo",
			"http://localhost@port-with-colon@/foo");

		List<String> interpolatedRedirectURIsList =
			OAuth2RedirectURIInterpolator.interpolateRedirectURIsList(
				_httpServletRequest, redirectURIsList, _portal);

		Assert.assertEquals(
			"http://localhost/foo", interpolatedRedirectURIsList.get(0));
		Assert.assertEquals(
			"http://localhost/foo", interpolatedRedirectURIsList.get(1));
		Assert.assertEquals(
			"http://localhost/foo", interpolatedRedirectURIsList.get(2));
	}

	@Test
	public void testInterpolateRedirectURIsListInsecureNondefaultPort() {
		Mockito.when(
			_portal.isSecure(_httpServletRequest)
		).then(
			new Answer<Boolean>() {

				@Override
				public Boolean answer(InvocationOnMock invocation)
					throws Throwable {

					return false;
				}

			}
		);

		Mockito.when(
			_portal.getForwardedPort(_httpServletRequest)
		).then(
			new Answer<Integer>() {

				@Override
				public Integer answer(InvocationOnMock invocation)
					throws Throwable {

					return 8080;
				}

			}
		);

		List<String> redirectURIsList = Arrays.asList(
			"http://localhost/foo", "@protocol@://localhost/foo",
			"http://localhost@port-with-colon@/foo");

		List<String> interpolatedRedirectURIsList =
			OAuth2RedirectURIInterpolator.interpolateRedirectURIsList(
				_httpServletRequest, redirectURIsList, _portal);

		Assert.assertEquals(
			"http://localhost/foo", interpolatedRedirectURIsList.get(0));
		Assert.assertEquals(
			"http://localhost/foo", interpolatedRedirectURIsList.get(1));
		Assert.assertEquals(
			"http://localhost:8080/foo", interpolatedRedirectURIsList.get(2));
	}

	@Test
	public void testInterpolateRedirectURIsListSecureDefaultPort() {
		Mockito.when(
			_portal.isSecure(_httpServletRequest)
		).then(
			new Answer<Boolean>() {

				@Override
				public Boolean answer(InvocationOnMock invocation)
					throws Throwable {

					return true;
				}

			}
		);

		Mockito.when(
			_portal.getForwardedPort(_httpServletRequest)
		).then(
			new Answer<Integer>() {

				@Override
				public Integer answer(InvocationOnMock invocation)
					throws Throwable {

					return 443;
				}

			}
		);

		List<String> redirectURIsList = Arrays.asList(
			"https://localhost/foo", "@protocol@://localhost/foo",
			"https://localhost@port-with-colon@/foo");

		List<String> interpolatedRedirectURIsList =
			OAuth2RedirectURIInterpolator.interpolateRedirectURIsList(
				_httpServletRequest, redirectURIsList, _portal);

		Assert.assertEquals(
			"https://localhost/foo", interpolatedRedirectURIsList.get(0));
		Assert.assertEquals(
			"https://localhost/foo", interpolatedRedirectURIsList.get(1));
		Assert.assertEquals(
			"https://localhost/foo", interpolatedRedirectURIsList.get(2));
	}

	@Test
	public void testInterpolateRedirectURIsListSecureNondefaultPort() {
		Mockito.when(
			_portal.isSecure(_httpServletRequest)
		).then(
			new Answer<Boolean>() {

				@Override
				public Boolean answer(InvocationOnMock invocation)
					throws Throwable {

					return true;
				}

			}
		);

		Mockito.when(
			_portal.getForwardedPort(_httpServletRequest)
		).then(
			new Answer<Integer>() {

				@Override
				public Integer answer(InvocationOnMock invocation)
					throws Throwable {

					return 6443;
				}

			}
		);

		List<String> redirectURIsList = Arrays.asList(
			"https://localhost/foo", "@protocol@://localhost/foo",
			"https://localhost@port-with-colon@/foo");

		List<String> interpolatedRedirectURIsList =
			OAuth2RedirectURIInterpolator.interpolateRedirectURIsList(
				_httpServletRequest, redirectURIsList, _portal);

		Assert.assertEquals(
			"https://localhost/foo", interpolatedRedirectURIsList.get(0));
		Assert.assertEquals(
			"https://localhost/foo", interpolatedRedirectURIsList.get(1));
		Assert.assertEquals(
			"https://localhost:6443/foo", interpolatedRedirectURIsList.get(2));
	}

	@Mock
	private HttpServletRequest _httpServletRequest;

	@Mock
	private Portal _portal;

}