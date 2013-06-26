/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.json.JSONIncludesManagerImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONIncludesManagerUtil;
import com.liferay.portal.kernel.json.JSONSerializable;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManagerUtil;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceClassVisitorFactoryUtil;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMappingResolver;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceNaming;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.MethodParametersResolverUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.MethodParametersResolverImpl;
import com.liferay.portal.util.PropsImpl;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.powermock.api.mockito.PowerMockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Igor Spasic
 * @author Raymond Augé
 */
public abstract class BaseJSONWebServiceTestCase extends PowerMockito {

	protected static void initPortalServices() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		JSONIncludesManagerUtil jsonIncludesManagerUtil =
			new JSONIncludesManagerUtil();

		jsonIncludesManagerUtil.setJSONIncludesManager(
			new JSONIncludesManagerImpl());

		JSONWebServiceActionsManagerUtil jsonWebServiceActionsManagerUtil =
			new JSONWebServiceActionsManagerUtil();

		jsonWebServiceActionsManagerUtil.setJSONWebServiceActionsManager(
			new JSONWebServiceActionsManagerImpl());

		JSONWebServiceClassVisitorFactoryUtil
			jsonWebServiceClassVisitorFactoryUtil =
				new JSONWebServiceClassVisitorFactoryUtil();

		jsonWebServiceClassVisitorFactoryUtil.
			setJSONWebServiceClassVisitorFactory(
				new JSONWebServiceClassVisitorFactoryImpl());

		MethodParametersResolverUtil methodParametersResolverUtil =
			new MethodParametersResolverUtil();

		methodParametersResolverUtil.setMethodParametersResolver(
			new MethodParametersResolverImpl());

		PropsUtil.setProps(new PropsImpl());
	}

	protected static void registerActionClass(Class<?> actionClass) {
		registerActionClass(actionClass, StringPool.BLANK);
	}

	protected static void registerActionClass(
		Class<?> actionClass, String servletContextPath) {

		JSONWebServiceMappingResolver jsonWebServiceMappingResolver =
			new JSONWebServiceMappingResolver(new JSONWebServiceNaming());

		Method[] methods = actionClass.getMethods();

		for (Method actionMethod : methods) {
			if (actionMethod.getDeclaringClass() != actionClass) {
				continue;
			}

			String path = jsonWebServiceMappingResolver.resolvePath(
				actionClass, actionMethod);
			String method = jsonWebServiceMappingResolver.resolveHttpMethod(
				actionMethod);

			JSONWebServiceActionsManagerUtil.registerJSONWebServiceAction(
				servletContextPath, actionClass, actionMethod, path, method);
		}
	}

	protected MockHttpServletRequest createHttpRequest(String pathInfo) {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setMethod(HttpMethods.GET);
		mockHttpServletRequest.setPathInfo(pathInfo);

		return mockHttpServletRequest;
	}

	protected MockHttpServletRequest createHttpRequest(
		String pathInfo, String method) {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setMethod(method);
		mockHttpServletRequest.setPathInfo(pathInfo);

		return mockHttpServletRequest;
	}

	protected JSONWebServiceAction lookupJSONWebServiceAction(
		HttpServletRequest httpServletRequest) {

		return JSONWebServiceActionsManagerUtil.getJSONWebServiceAction(
			httpServletRequest);
	}

	protected String toJSON(Object object) {
		if (object instanceof JSONSerializable) {
			return ((JSONSerializable)object).toJSONString();
		}

		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		jsonSerializer.exclude("*.class");

		return jsonSerializer.serialize(object);
	}

	protected String toJSON(Object object, String... includes) {
		if (object instanceof JSONSerializable) {
			return ((JSONSerializable)object).toJSONString();
		}

		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		jsonSerializer.exclude("*.class");
		jsonSerializer.include(includes);

		return jsonSerializer.serialize(object);
	}

}