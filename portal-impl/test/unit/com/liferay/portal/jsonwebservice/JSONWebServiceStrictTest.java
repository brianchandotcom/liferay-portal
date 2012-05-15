/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.jsonwebservice.dependencies.CamelFooService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockHttpServletRequest;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;

/**
 * @author Igor Spasic
 */
@PrepareForTest(PropsUtil.class)
@RunWith(PowerMockRunner.class)
public class JSONWebServiceStrictTest extends JSONWebServiceAbstractTest {

	@Before
	public void setUp() throws Exception {
		spy(PropsUtil.class);

		when(
			PropsUtil.get(PropsKeys.JSONWS_WEB_SERVICE_STRICT_HTTP_METHOD)
		).thenReturn(
			"true"
		);

	}

	@Test
	public void testStrictHttpMethod() throws Exception {
		initPortalServices();
		registerActionClass(CamelFooService.class);

		MockHttpServletRequest mockHttpServletRequest =
			createHttpRequest("/camelfoo/post/value/123");

		try {
			lookupAction(mockHttpServletRequest);

			fail();
		}
		catch (RuntimeException ignore) {
		}

		mockHttpServletRequest = createHttpRequest(
			"/camelfoo/post/value/123", "POST");

		JSONWebServiceAction jsonWebServiceAction =
			lookupAction(mockHttpServletRequest);

		assertNotNull(jsonWebServiceAction);

		assertEquals("post 123", jsonWebServiceAction.invoke());
	}

}