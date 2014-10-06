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

package com.liferay.portal.jsonwebservice;

import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManagerUtil;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceActionsManagerTest
		extends BaseJSONWebServiceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		initPortalServices();

		registerAction(new AbcService());
	}

	@Test
	public void testOverloadedMethodsAndDefaultParams1() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/abc/hello");

		mockHttpServletRequest.setParameter("a", "123");
		mockHttpServletRequest.setAttribute("c", "qwe");

		JSONWebServiceAction jsonWebServiceAction =
			JSONWebServiceActionsManagerUtil.getJSONWebServiceAction(
				mockHttpServletRequest);

		String string = (String)jsonWebServiceAction.invoke();

		Assert.assertEquals("hello:123", string);
	}

	@Test
	public void testOverloadedMethodsAndDefaultParams2() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/abc/hello");

		mockHttpServletRequest.setParameter("a", "123");
		mockHttpServletRequest.setParameter("b", "456");

		mockHttpServletRequest.setAttribute("c", "qwe");

		JSONWebServiceAction jsonWebServiceAction =
			JSONWebServiceActionsManagerUtil.getJSONWebServiceAction(
				mockHttpServletRequest);

		String string = (String)jsonWebServiceAction.invoke();

		Assert.assertEquals("hello:123", string);
	}

	@Test
	public void testOverloadedMethodsAndDefaultParams3() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/abc/hello");

		mockHttpServletRequest.setParameter("a", "123");
		mockHttpServletRequest.setParameter("b", "456");
		mockHttpServletRequest.setParameter("c", "789");

		mockHttpServletRequest.setAttribute("c", "qwe");

		JSONWebServiceAction jsonWebServiceAction =
			JSONWebServiceActionsManagerUtil.getJSONWebServiceAction(
				mockHttpServletRequest);

		String string = (String)jsonWebServiceAction.invoke();

		Assert.assertEquals("hello:123:456:789", string);
	}

	@Test
	public void testOverloadedMethodsAndDefaultParams4() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/abc/hello");

		mockHttpServletRequest.setParameter("a", "123");
		mockHttpServletRequest.setParameter("b", "456");
		mockHttpServletRequest.setParameter("d", "abc");

		mockHttpServletRequest.setAttribute("c", "qwe");

		JSONWebServiceAction jsonWebServiceAction =
			JSONWebServiceActionsManagerUtil.getJSONWebServiceAction(
				mockHttpServletRequest);

		String string = (String)jsonWebServiceAction.invoke();

		Assert.assertEquals("hello:123:456>abc", string);
	}

}