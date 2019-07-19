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

package com.liferay.document.library.opener.google.drive.web.internal.oauth;

import com.liferay.document.library.opener.oauth.OAuth2State;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.webdav.methods.Method;

import java.util.Collections;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

/**
 * @author Alicia García García
 */
public class OAuth2StateUtilTest {

	@Before
	public void setUp() throws Exception {
		PropsTestUtil.setProps(Collections.emptyMap());
	}

	@Test
	public void testCleanUp() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(Method.GET, "/");

		long userId = RandomTestUtil.randomLong();
		String successURL = RandomTestUtil.randomString();
		String failureURL = RandomTestUtil.randomString();
		String state = RandomTestUtil.randomString(5);

		OAuth2StateUtil.save(
			mockHttpServletRequest, userId, successURL, failureURL, state);

		OAuth2StateUtil.cleanUp(mockHttpServletRequest);

		Optional<OAuth2State> oAuth2StateOptional =
			OAuth2StateUtil.getOAuth2StateOptional(mockHttpServletRequest);

		Assert.assertFalse(oAuth2StateOptional.isPresent());
	}

	@Test
	public void testGetOAuth2StateOptionalNotNull() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(Method.GET, "/");

		long userId = RandomTestUtil.randomLong();
		String successURL = RandomTestUtil.randomString();
		String failureURL = RandomTestUtil.randomString();
		String state = RandomTestUtil.randomString(5);

		OAuth2StateUtil.save(
			mockHttpServletRequest, userId, successURL, failureURL, state);

		Optional<OAuth2State> oAuth2StateOptional =
			OAuth2StateUtil.getOAuth2StateOptional(mockHttpServletRequest);

		OAuth2State oAuth2State = oAuth2StateOptional.get();

		_assertOAuth2State(userId, successURL, failureURL, state, oAuth2State);
	}

	@Test
	public void testGetOAuth2StateOptionalNull() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(Method.GET, "/");

		MockHttpSession mockHttpSession = new MockHttpSession();

		mockHttpServletRequest.setSession(mockHttpSession);

		Optional<OAuth2State> oAuth2StateOptional =
			OAuth2StateUtil.getOAuth2StateOptional(mockHttpServletRequest);

		Assert.assertFalse(oAuth2StateOptional.isPresent());
	}

	@Test
	public void testIsValidWithDifferentState() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(Method.GET, "/");

		mockHttpServletRequest.setParameter(
			"state", RandomTestUtil.randomString(5));

		OAuth2State oAuth2State = new OAuth2State(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(5));

		Assert.assertFalse(
			OAuth2StateUtil.isValid(oAuth2State, mockHttpServletRequest));
	}

	@Test
	public void testIsValidWithSameState() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(Method.GET, "/");

		String state = RandomTestUtil.randomString(5);

		mockHttpServletRequest.setParameter("state", state);

		OAuth2State oAuth2State = new OAuth2State(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), state);

		Assert.assertTrue(
			OAuth2StateUtil.isValid(oAuth2State, mockHttpServletRequest));
	}

	@Test
	public void testSave() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(Method.GET, "/");

		long userId = RandomTestUtil.randomLong();
		String successURL = RandomTestUtil.randomString();
		String failureURL = RandomTestUtil.randomString();
		String state = RandomTestUtil.randomString(5);

		OAuth2StateUtil.save(
			mockHttpServletRequest, userId, successURL, failureURL, state);

		Optional<OAuth2State> oAuth2StateOptional =
			OAuth2StateUtil.getOAuth2StateOptional(mockHttpServletRequest);

		OAuth2State oAuth2State = oAuth2StateOptional.get();

		_assertOAuth2State(userId, successURL, failureURL, state, oAuth2State);
	}

	private void _assertOAuth2State(
		long userId, String successURL, String failureURL, String state,
		OAuth2State oAuth2State) {

		Assert.assertNotNull(oAuth2State);

		Assert.assertEquals(failureURL, oAuth2State.getFailureURL());
		Assert.assertEquals(successURL, oAuth2State.getSuccessURL());
		Assert.assertEquals(userId, oAuth2State.getUserId());

		Assert.assertTrue(oAuth2State.isValid(state));
	}

}