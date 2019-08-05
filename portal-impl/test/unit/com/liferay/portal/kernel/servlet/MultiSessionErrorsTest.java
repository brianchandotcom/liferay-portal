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
import org.junit.Before;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

/**
 * @author Alicia García García
 */
public class MultiSessionErrorsTest {

	@Before
	public void setUp() throws Exception {
		_mockHttpServletRequest = new MockHttpServletRequest();
		PortalUtil portalUtil = new PortalUtil();
		PortalImpl portal = new PortalImpl();

		portalUtil.setPortal(portal);

		MockHttpSession mockHttpSession = new MockHttpSession();
		_mockPortletRequest = new MockLiferayPortletRequest(
			_mockHttpServletRequest);
		_mockHttpServletRequest.setSession(mockHttpSession);
	}

	@Test
	public void testClearHttpServletRequest() {
		String key = "test_error";

		SessionErrors.add(_mockHttpServletRequest, key);

		Assert.assertFalse(MultiSessionErrors.isEmpty(_mockPortletRequest));

		MultiSessionErrors.clear(_mockPortletRequest);

		Assert.assertTrue(MultiSessionErrors.isEmpty(_mockPortletRequest));
	}

	@Test
	public void testClearPortletRequest() {
		String key = "test_error";

		SessionErrors.add(_mockPortletRequest, key);

		Assert.assertFalse(MultiSessionErrors.isEmpty(_mockPortletRequest));

		MultiSessionErrors.clear(_mockPortletRequest);

		Assert.assertTrue(MultiSessionErrors.isEmpty(_mockPortletRequest));
	}

	@Test
	public void testContainsOnHttpServletRequest() {
		String key = "test_error";

		SessionErrors.add(_mockHttpServletRequest, key);

		Assert.assertFalse(SessionErrors.contains(_mockPortletRequest, key));

		Assert.assertTrue(
			MultiSessionErrors.contains(_mockPortletRequest, key));
	}

	@Test
	public void testContainsOnPortletRequest() {
		String key = "test_error";

		SessionErrors.add(_mockPortletRequest, key);

		Assert.assertTrue(SessionErrors.contains(_mockPortletRequest, key));
		Assert.assertTrue(
			MultiSessionErrors.contains(_mockPortletRequest, key));
	}

	@Test
	public void testGetFoundHttpServletRequest() {
		String key = "test_error";

		SessionErrors.add(_mockHttpServletRequest, key);

		Assert.assertEquals(
			key, MultiSessionErrors.get(_mockPortletRequest, key));
	}

	@Test
	public void testGetFoundPortletRequest() {
		String key = "test_error";

		SessionErrors.add(_mockPortletRequest, key);

		Assert.assertEquals(
			key, MultiSessionErrors.get(_mockPortletRequest, key));
	}

	@Test
	public void testGetNotFound() {
		String key = "test_error";

		Assert.assertNull(MultiSessionErrors.get(_mockPortletRequest, key));
	}

	@Test
	public void testIsEmpty() {
		Assert.assertTrue(MultiSessionErrors.isEmpty(_mockPortletRequest));
	}

	@Test
	public void testIsEmptyFalseByHttpServletRequest() {
		String key = "test_error";

		SessionErrors.add(_mockHttpServletRequest, key);

		Assert.assertFalse(MultiSessionErrors.isEmpty(_mockPortletRequest));
	}

	@Test
	public void testIsEmptyFalseByPortletRequest() {
		String key = "test_error";

		SessionErrors.add(_mockPortletRequest, key);

		Assert.assertFalse(MultiSessionErrors.isEmpty(_mockPortletRequest));
	}

	private MockHttpServletRequest _mockHttpServletRequest;
	private PortletRequest _mockPortletRequest;

}