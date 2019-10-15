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

import com.liferay.document.library.opener.oauth.OAuth2State;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Collections;
import java.util.Optional;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

/**
 * @author Alicia García García
 * @author Cristina González
 */
public class OAuth2StateUtilTest {

	@BeforeClass
	public static void setUpClass() {
		PropsTestUtil.setProps(Collections.emptyMap());
	}

	@Test
	public void testCleanUp() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		OAuth2StateUtil.save(
			mockHttpServletRequest,
			new OAuth2State(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString()));

		OAuth2StateUtil.cleanUp(mockHttpServletRequest);

		Optional<OAuth2State> oAuth2StateOptional =
			OAuth2StateUtil.getOAuth2StateOptional(mockHttpServletRequest);

		Assert.assertFalse(oAuth2StateOptional.isPresent());
	}

	@Test
	public void testGetOAuth2StateOptionalWithNotNullState() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		String state = RandomTestUtil.randomString();

		OAuth2State initialOAuth2State = new OAuth2State(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), state);

		OAuth2StateUtil.save(mockHttpServletRequest, initialOAuth2State);

		Optional<OAuth2State> oAuth2StateOptional =
			OAuth2StateUtil.getOAuth2StateOptional(mockHttpServletRequest);

		OAuth2State oAuth2State = oAuth2StateOptional.get();

		_assertOAuth2State(initialOAuth2State, state, oAuth2State);
	}

	@Test
	public void testGetOAuth2StateOptionalWithNullState() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		MockHttpSession mockHttpSession = new MockHttpSession();

		mockHttpServletRequest.setSession(mockHttpSession);

		Optional<OAuth2State> oAuth2StateOptional =
			OAuth2StateUtil.getOAuth2StateOptional(mockHttpServletRequest);

		Assert.assertFalse(oAuth2StateOptional.isPresent());
	}

	@Test
	public void testIsValidWithDifferentState() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

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
			new MockHttpServletRequest();

		String state = RandomTestUtil.randomString();

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
			new MockHttpServletRequest();

		String state = RandomTestUtil.randomString();

		OAuth2State initialOAuth2State = new OAuth2State(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), state);

		OAuth2StateUtil.save(mockHttpServletRequest, initialOAuth2State);

		Optional<OAuth2State> oAuth2StateOptional =
			OAuth2StateUtil.getOAuth2StateOptional(mockHttpServletRequest);

		OAuth2State oAuth2State = oAuth2StateOptional.get();

		_assertOAuth2State(initialOAuth2State, state, oAuth2State);
	}

	private void _assertOAuth2State(
		OAuth2State expectedOAuth2State, String state,
		OAuth2State actualOAuth2State) {

		Assert.assertNotNull(actualOAuth2State);

		Assert.assertEquals(
			expectedOAuth2State.getFailureURL(),
			actualOAuth2State.getFailureURL());
		Assert.assertEquals(
			expectedOAuth2State.getSuccessURL(),
			actualOAuth2State.getSuccessURL());
		Assert.assertEquals(
			expectedOAuth2State.getUserId(), actualOAuth2State.getUserId());

		Assert.assertTrue(actualOAuth2State.isValid(state));
	}

}