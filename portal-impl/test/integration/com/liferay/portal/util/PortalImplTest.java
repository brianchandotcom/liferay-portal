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

package com.liferay.portal.util;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portal.upload.LiferayServletRequest;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portal.util.bundle.portalimpl.TestAlwaysAllowDoAsUser;
import com.liferay.portal.util.test.AtomicState;
import com.liferay.portal.util.test.PortletContainerTestUtil;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.portlet.MockPortletRequest;

/**
 * @author Peter Fellwock
 */
public class PortalImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.portalimpl"));

	@BeforeClass
	public static void setUpClass() {
		_atomicState = new AtomicState();
	}

	@AfterClass
	public static void tearDownClass() {
		_atomicState.close();
	}

	@Test
	public void
		testGetUploadPortletRequestFromInvalidRequestShouldThrowException() {

		try {
			PortalUtil.getUploadPortletRequest(new MockPortletRequest());

			Assert.fail("A RuntimeException should have been thrown.");
		}
		catch (Exception e) {
			Assert.assertTrue(e instanceof RuntimeException);
			Assert.assertEquals(
				"Unable to unwrap the portlet request from " +
					MockPortletRequest.class,
				e.getMessage());
		}
	}

	@Test
	public void
			testGetUploadServletRequestShouldReturnUploadServletRequestImpl()
		throws Exception {

		InputStream inputStream = getClass().getResourceAsStream(
			"/com/liferay/portal/util/dependencies/test.txt");

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				"fileParameterName",
				PortletContainerTestUtil.toByteArray(inputStream));

		UploadServletRequest uploadServletRequest =
			PortalUtil.getUploadServletRequest(
				(HttpServletRequest)liferayServletRequest.getRequest());

		Assert.assertTrue(
			uploadServletRequest instanceof UploadServletRequestImpl);
	}

	@Test
	public void testGetUserId() {
		_atomicState.reset();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setParameter(
			"_TestAlwaysAllowDoAsUser_actionName",
			TestAlwaysAllowDoAsUser.ACTION_NAME);
		mockHttpServletRequest.setParameter(
			"_TestAlwaysAllowDoAsUser_struts_action",
			TestAlwaysAllowDoAsUser.STRUTS_ACTION);
		mockHttpServletRequest.setParameter(
			"p_p_id", "TestAlwaysAllowDoAsUser");

		long userId = PortalUtil.getUserId(mockHttpServletRequest);

		Assert.assertEquals(0, userId);
		Assert.assertTrue(_atomicState.isSet());

		_atomicState.reset();

		mockHttpServletRequest = new MockHttpServletRequest();

		mockHttpServletRequest.setPathInfo(
			"/TestAlwaysAllowDoAsUser/" + RandomTestUtil.randomString());

		userId = PortalUtil.getUserId(mockHttpServletRequest);

		Assert.assertEquals(0, userId);
		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testIsValidResourceId() {
		Assert.assertTrue(PortalUtil.isValidResourceId("/view.jsp"));
		Assert.assertFalse(
			PortalUtil.isValidResourceId("/META-INF/MANIFEST.MF"));
		Assert.assertFalse(
			PortalUtil.isValidResourceId("/META-INF\\MANIFEST.MF"));
		Assert.assertFalse(
			PortalUtil.isValidResourceId("\\META-INF/MANIFEST.MF"));
		Assert.assertFalse(
			PortalUtil.isValidResourceId("\\META-INF\\MANIFEST.MF"));
		Assert.assertFalse(PortalUtil.isValidResourceId("/WEB-INF/web.xml"));
		Assert.assertFalse(PortalUtil.isValidResourceId("/WEB-INF\\web.xml"));
		Assert.assertFalse(PortalUtil.isValidResourceId("\\WEB-INF/web.xml"));
		Assert.assertFalse(PortalUtil.isValidResourceId("\\WEB-INF\\web.xml"));
	}

	private static AtomicState _atomicState;

}