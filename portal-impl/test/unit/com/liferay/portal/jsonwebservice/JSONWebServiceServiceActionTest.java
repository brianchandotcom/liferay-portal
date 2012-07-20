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

import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PortalUtil;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.LinkedHashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

/**
 * @author Igor Spasic
 */
@PowerMockIgnore("javax.xml.datatype.*")
@PrepareForTest(PortalUtil.class)
@RunWith(PowerMockRunner.class)
public class JSONWebServiceServiceActionTest
	extends BaseJSONWebServiceTestCase {

	@BeforeClass
	public static void init() throws Exception {
		initPortalServices();

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());

		_jsonWebServiceServiceAction = new JSONWebServiceServiceAction("", null);

		registerActionClass(FooService.class);
	}

	@Before
	public void setUp() {
		spy(PortalUtil.class);

		when(
			PortalUtil.getPortalLibDir()
		).thenReturn(
			""
		);
	}

	@Test
	public void testInvokerNullCall() throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map.put("/foo/null-return", params);

		String json = toJSON(map);

		MockHttpServletRequest mockHttpServletRequest =
			createInvokerHttpServletRequest(json);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		json = _jsonWebServiceServiceAction.getJSON(new ActionMapping(),
			new DynaActionForm(), mockHttpServletRequest,
			mockHttpServletResponse);

		assertEquals("{}", json);
	}

	@Test
	public void testInvokerSimpleCall() throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map.put("/foo/hello-world", params);

		params.put("userId", 173);
		params.put("worldName", "Jupiter");

		String json = toJSON(map);

		MockHttpServletRequest mockHttpServletRequest =
			createInvokerHttpServletRequest(json);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		json = _jsonWebServiceServiceAction.getJSON(new ActionMapping(),
			new DynaActionForm(), mockHttpServletRequest,
			mockHttpServletResponse);

		assertEquals("\"Welcome 173 to Jupiter\"", json);
	}

	protected MockHttpServletRequest createInvokerHttpServletRequest(
		String content) {

		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/invoke");

		mockHttpServletRequest.setContent(content.getBytes());

		mockHttpServletRequest.setRemoteUser("root");

		mockHttpServletRequest.setMethod("POST");

		return mockHttpServletRequest;
	}

	private static JSONWebServiceServiceAction _jsonWebServiceServiceAction;

}