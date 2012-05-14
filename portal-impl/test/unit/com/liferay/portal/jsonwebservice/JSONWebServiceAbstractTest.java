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

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManagerUtil;
import com.liferay.portal.kernel.util.MethodParametersResolverUtil;
import com.liferay.portal.util.MethodParametersResolverImpl;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceAbstractTest {

	protected String toJson(Object object) {
		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		jsonSerializer.exclude("*.class");

		return jsonSerializer.serialize(object);
	}

	protected static void initPortalServices() {
		new MethodParametersResolverUtil().setMethodParametersResolver(
			new MethodParametersResolverImpl());

		new JSONFactoryUtil().setJSONFactory(new JSONFactoryImpl());

		new JSONWebServiceActionsManagerUtil().setJSONWebServiceActionsManager(
			new JSONWebServiceActionsManagerImpl());

	}

	protected static void registerActionClass(Class actionClass) {

		JSONWebServiceMappingResolver jsonWebServiceMappingResolver =
				new JSONWebServiceMappingResolver();

		Method[] methods = actionClass.getMethods();

		for (Method actionMethod : methods) {
			if (actionMethod.getDeclaringClass() != actionClass) {
				continue;
			}

			String actionPath = jsonWebServiceMappingResolver.resolvePath(
				actionClass, actionMethod);

			String actionHttpMethod =
				jsonWebServiceMappingResolver.resolveHttpMethod(actionMethod);

			JSONWebServiceActionsManagerUtil.registerJSONWebServiceAction(
				"", actionClass, actionMethod, actionPath, actionHttpMethod);
		}
	}

	protected MockHttpServletRequest createHttpRequest(String pathInfo) {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setPathInfo(pathInfo);

		return mockHttpServletRequest;
	}

	protected JSONWebServiceAction lookupAction(
		HttpServletRequest httpServletRequest) {

		return JSONWebServiceActionsManagerUtil.getJSONWebServiceAction(
			httpServletRequest);
	}

}