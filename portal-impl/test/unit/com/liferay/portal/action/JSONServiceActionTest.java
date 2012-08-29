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

package com.liferay.portal.action;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portlet.messageboards.service.MBMessageServiceUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Igor Spasic
 */
public class JSONServiceActionTest extends TestCase {

	@Override
	public void setUp() throws Exception {
		new JSONFactoryUtil().setJSONFactory(new JSONFactoryImpl());
	}

	public void testGetArgumentValue() throws Exception {
		JSONServiceAction jsonServiceAction = new JSONServiceAction();

		String[] methodParameterNames = new String[] {
			"groupId", "categoryId", "subject", "body", "format",
			"inputStreamOVPs", "anonymous", "priority", "allowPingbacks",
			"serviceContext"};

		Object[] methodAndParameterTypes =
			jsonServiceAction.getMethodAndParameterTypes(
				MBMessageServiceUtil.class, "addMessage", methodParameterNames,
				new String[0]);

		Method method = (Method)methodAndParameterTypes[0];
		Type[] parameterTypes = (Type[])methodAndParameterTypes[1];

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setParameter("inputStreamOVPs", "[]");

		Object value = jsonServiceAction.getArgValue(
			mockHttpServletRequest, MBMessageServiceUtil.class,
			method.getName(), methodParameterNames[5], parameterTypes[5]);

		assertEquals("[]", value.toString());

		mockHttpServletRequest.setParameter(
			"inputStreamOVPs",
			"{'class' : 'com.liferay.portal.kernel.dao.orm.EntityCacheUtil'}");

		value = jsonServiceAction.getArgValue(
			mockHttpServletRequest, MBMessageServiceUtil.class,
			method.getName(), methodParameterNames[5], parameterTypes[5]);

		assertEquals(
			"{class=com.liferay.portal.kernel.dao.orm.EntityCacheUtil}",
			value.toString());
	}

}