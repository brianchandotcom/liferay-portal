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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Minhchau Dang
 */
public class SharedSessionServletRequestTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@Test
	public void testGetSharedSession() {
		_testGetSharedSession(true);
		_testGetSharedSession(false);
	}

	@Test
	public void testInvalidateSession() {
		_testInvalidateSession(true);
		_testInvalidateSession(false);
	}

	private void _testGetSharedSession(boolean shared) {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		SharedSessionServletRequest sharedSessionServletRequest =
			new SharedSessionServletRequest(mockHttpServletRequest, shared);

		Assert.assertSame(
			sharedSessionServletRequest.getSharedSession(),
			sharedSessionServletRequest.getSharedSession());
	}

	private void _testInvalidateSession(boolean shared) {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		SharedSessionServletRequest sharedSessionServletRequest =
			new SharedSessionServletRequest(mockHttpServletRequest, shared);

		HttpSession httpSession = sharedSessionServletRequest.getSession();

		httpSession.invalidate();

		Assert.assertNull(sharedSessionServletRequest.getSession(false));

		HttpSession newHttpSession = sharedSessionServletRequest.getSession(
			true);

		Assert.assertNotNull(newHttpSession);

		Assert.assertNotSame(httpSession, newHttpSession);
	}

}