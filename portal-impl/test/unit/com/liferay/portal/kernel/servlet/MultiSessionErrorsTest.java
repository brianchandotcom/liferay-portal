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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.servlet.util.MockLiferayPortletRequest;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.util.PortalImpl;

import javax.portlet.PortletRequest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

/**
 * @author Alicia García García
 */
public class MultiSessionErrorsTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());
	}

	@Test
	public void testClearHttpServletRequest() {
		String key = "test_error";

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		MockHttpSession mockHttpSession = new MockHttpSession();

		mockHttpServletRequest.setSession(mockHttpSession);

		PortletRequest mockPortletRequest = new MockLiferayPortletRequest(
			mockHttpServletRequest);

		SessionErrors.add(mockHttpServletRequest, key);

		Assert.assertFalse(MultiSessionErrors.isEmpty(mockPortletRequest));

		MultiSessionErrors.clear(mockPortletRequest);

		Assert.assertTrue(MultiSessionErrors.isEmpty(mockPortletRequest));
	}

	@Test
	public void testClearPortletRequest() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		MockHttpSession mockHttpSession = new MockHttpSession();

		mockHttpServletRequest.setSession(mockHttpSession);

		PortletRequest mockPortletRequest = new MockLiferayPortletRequest(
			mockHttpServletRequest);

		String key = "test_error";

		SessionErrors.add(mockPortletRequest, key);

		Assert.assertFalse(MultiSessionErrors.isEmpty(mockPortletRequest));

		MultiSessionErrors.clear(mockPortletRequest);

		Assert.assertTrue(MultiSessionErrors.isEmpty(mockPortletRequest));
	}

	@Test
	public void testContainsOnHttpServletRequest() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		MockHttpSession mockHttpSession = new MockHttpSession();

		mockHttpServletRequest.setSession(mockHttpSession);

		PortletRequest mockPortletRequest = new MockLiferayPortletRequest(
			mockHttpServletRequest);

		String key = "test_error";

		SessionErrors.add(mockHttpServletRequest, key);

		Assert.assertTrue(MultiSessionErrors.contains(mockPortletRequest, key));
	}

	@Test
	public void testContainsOnPortletRequest() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		MockHttpSession mockHttpSession = new MockHttpSession();

		mockHttpServletRequest.setSession(mockHttpSession);

		PortletRequest mockPortletRequest = new MockLiferayPortletRequest(
			mockHttpServletRequest);

		String key = "test_error";

		SessionErrors.add(mockPortletRequest, key);

		Assert.assertTrue(MultiSessionErrors.contains(mockPortletRequest, key));
	}

	@Test
	public void testGetFoundHttpServletRequest() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		MockHttpSession mockHttpSession = new MockHttpSession();

		mockHttpServletRequest.setSession(mockHttpSession);

		PortletRequest mockPortletRequest = new MockLiferayPortletRequest(
			mockHttpServletRequest);

		String key = "test_error";

		SessionErrors.add(mockHttpServletRequest, key);

		Assert.assertEquals(
			key, MultiSessionErrors.get(mockPortletRequest, key));
	}

	@Test
	public void testGetFoundPortletRequest() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		MockHttpSession mockHttpSession = new MockHttpSession();

		mockHttpServletRequest.setSession(mockHttpSession);

		PortletRequest mockPortletRequest = new MockLiferayPortletRequest(
			mockHttpServletRequest);

		String key = "test_error";

		SessionErrors.add(mockPortletRequest, key);

		Assert.assertEquals(
			key, MultiSessionErrors.get(mockPortletRequest, key));
	}

	@Test
	public void testGetNotFound() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		MockHttpSession mockHttpSession = new MockHttpSession();

		mockHttpServletRequest.setSession(mockHttpSession);

		PortletRequest mockPortletRequest = new MockLiferayPortletRequest(
			mockHttpServletRequest);

		String key = "test_error";

		Assert.assertNull(MultiSessionErrors.get(mockPortletRequest, key));
	}

	@Test
	public void testIsEmpty() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		MockHttpSession mockHttpSession = new MockHttpSession();

		mockHttpServletRequest.setSession(mockHttpSession);

		PortletRequest mockPortletRequest = new MockLiferayPortletRequest(
			mockHttpServletRequest);

		Assert.assertTrue(MultiSessionErrors.isEmpty(mockPortletRequest));
	}

	@Test
	public void testIsEmptyFalseByHttpServletRequest() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		MockHttpSession mockHttpSession = new MockHttpSession();

		mockHttpServletRequest.setSession(mockHttpSession);

		PortletRequest mockPortletRequest = new MockLiferayPortletRequest(
			mockHttpServletRequest);

		String key = "test_error";

		SessionErrors.add(mockHttpServletRequest, key);

		Assert.assertFalse(MultiSessionErrors.isEmpty(mockPortletRequest));
	}

	@Test
	public void testIsEmptyFalseByPortletRequest() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		MockHttpSession mockHttpSession = new MockHttpSession();

		mockHttpServletRequest.setSession(mockHttpSession);

		PortletRequest mockPortletRequest = new MockLiferayPortletRequest(
			mockHttpServletRequest);
		String key = "test_error";

		SessionErrors.add(mockPortletRequest, key);

		Assert.assertFalse(MultiSessionErrors.isEmpty(mockPortletRequest));
	}

}