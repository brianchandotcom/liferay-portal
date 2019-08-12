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

package com.liferay.document.library.opener.onedrive.web.internal.util;

import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Constants;

import java.util.Collections;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

/**
 * @author Cristina González
 */
public class DLOpenerTimestampUtilTest {

	@BeforeClass
	public static void setUpClass() {
		PropsTestUtil.setProps(Collections.emptyMap());
	}

	@Test
	public void testAdd() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setSession(new MockHttpSession());

		String timestamp = RandomTestUtil.randomString();

		DLOpenerTimestampUtil.add(
			mockHttpServletRequest, Constants.ADD, timestamp);

		Assert.assertTrue(
			DLOpenerTimestampUtil.contains(
				mockHttpServletRequest, Constants.ADD, timestamp));
	}

	@Test
	public void testAddWithoutAddCommand() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setSession(new MockHttpSession());

		String cmd = RandomTestUtil.randomString(5);

		String timestamp = RandomTestUtil.randomString();

		DLOpenerTimestampUtil.add(mockHttpServletRequest, cmd, timestamp);

		Assert.assertFalse(
			DLOpenerTimestampUtil.contains(
				mockHttpServletRequest, cmd, timestamp));
	}

	@Test
	public void testContains() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setSession(new MockHttpSession());

		Assert.assertFalse(
			DLOpenerTimestampUtil.contains(
				mockHttpServletRequest, Constants.ADD,
				RandomTestUtil.randomString(5)));
	}

}