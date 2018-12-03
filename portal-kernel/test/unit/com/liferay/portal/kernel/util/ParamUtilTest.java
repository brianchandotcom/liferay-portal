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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.portlet.MockPortletRequest;

/**
 * @author Preston Crary
 */
public class ParamUtilTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		PropsTestUtil.setProps(PropsKeys.UNICODE_TEXT_NORMALIZER_FORM, "NFC");
	}

	@Test
	public void testGetHttpServletRequest() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addParameter(
			"key1", " \t\n\r\u3000value \t\n\r\u3000");

		String defaultString = RandomTestUtil.randomString();

		String value = ParamUtil.get(
			mockHttpServletRequest, "key1", defaultString);

		Assert.assertEquals("value", value);

		value = ParamUtil.get(mockHttpServletRequest, "key2", defaultString);

		Assert.assertSame(defaultString, value);
	}

	@Test
	public void testGetNormalizedString() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addParameter("key1", "\u1004\u103A\u1037");
		mockHttpServletRequest.addParameter("key2", "\u1004\u1037\u103A");

		String value = ParamUtil.getString(mockHttpServletRequest, "key1", "");
		String value2 = ParamUtil.getString(mockHttpServletRequest, "key2", "");

		Assert.assertEquals(value, value2);
	}

	@Test
	public void testGetPortletRequest() {
		MockPortletRequest mockPortletRequest = new MockPortletRequest();

		mockPortletRequest.setParameter(
			"key1", " \t\n\r\u3000value \t\n\r\u3000");

		String defaultString = RandomTestUtil.randomString();

		String value = ParamUtil.get(mockPortletRequest, "key1", defaultString);

		Assert.assertEquals("value", value);

		value = ParamUtil.get(mockPortletRequest, "key2", defaultString);

		Assert.assertSame(defaultString, value);
	}

	@Test
	public void testGetServiceContext() {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAttribute("key1", " \t\n\r\u3000value \t\n\r\u3000");

		String defaultString = RandomTestUtil.randomString();

		String value = ParamUtil.get(serviceContext, "key1", defaultString);

		Assert.assertEquals("value", value);

		value = ParamUtil.get(serviceContext, "key2", defaultString);

		Assert.assertSame(defaultString, value);
	}

}