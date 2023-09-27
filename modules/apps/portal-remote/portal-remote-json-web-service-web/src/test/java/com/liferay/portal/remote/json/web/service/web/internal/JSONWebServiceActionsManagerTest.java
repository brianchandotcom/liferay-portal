/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.json.web.service.web.internal;

import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManagerUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceActionsManagerTest
	extends BaseJSONWebServiceTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		initPortalServices();

		registerAction(new FooService());
	}

	@Test
	public void testOverloadedMethodsAndDefaultParams1() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/foo/hello");

		mockHttpServletRequest.setParameter("i1", "123");

		testOverloadedMethodsAndDefaultParams(
			mockHttpServletRequest, "hello:123");
	}

	@Test
	public void testOverloadedMethodsAndDefaultParams2() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/foo/hello");

		mockHttpServletRequest.setParameter("i1", "123");
		mockHttpServletRequest.setParameter("i2", "456");

		testOverloadedMethodsAndDefaultParams(
			mockHttpServletRequest, "hello:123");
	}

	@Test
	public void testOverloadedMethodsAndDefaultParams3() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/foo/hello");

		mockHttpServletRequest.setParameter("i1", "123");
		mockHttpServletRequest.setParameter("i2", "456");
		mockHttpServletRequest.setParameter("i3", "789");

		testOverloadedMethodsAndDefaultParams(
			mockHttpServletRequest, "hello:123:456:789");
	}

	@Test
	public void testOverloadedMethodsAndDefaultParams4() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/foo/hello");

		mockHttpServletRequest.setParameter("i1", "123");
		mockHttpServletRequest.setParameter("i2", "456");
		mockHttpServletRequest.setParameter("s", "abc");

		testOverloadedMethodsAndDefaultParams(
			mockHttpServletRequest, "hello:123:456>abc");
	}

	protected void testOverloadedMethodsAndDefaultParams(
			MockHttpServletRequest mockHttpServletRequest,
			String expectedString)
		throws Exception {

		mockHttpServletRequest.setAttribute("a", "qwe");

		JSONWebServiceAction jsonWebServiceAction =
			JSONWebServiceActionsManagerUtil.getJSONWebServiceAction(
				mockHttpServletRequest);

		Assert.assertEquals(expectedString, jsonWebServiceAction.invoke());
	}

}