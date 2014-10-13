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

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.json.transformer.SortedHashMapJSONTransformer;
import com.liferay.portal.jsonwebservice.action.JSONWebServiceInvokerAction;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializable;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManagerUtil;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMappingResolver;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceNaming;
import com.liferay.portal.kernel.jsonwebservice.NoSuchJSONWebServiceException;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.MethodParametersResolverUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.MethodParametersResolverImpl;
import com.liferay.portal.util.PropsImpl;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.powermock.api.mockito.PowerMockito;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;

/**
 * @author Igor Spasic
 * @author Raymond Augé
 */
public abstract class BaseJSONWebServiceTestCase extends PowerMockito {

	protected static void initPortalServices() {
		PropsUtil.setProps(new PropsImpl());

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		JSONWebServiceActionsManagerUtil jsonWebServiceActionsManagerUtil =
			new JSONWebServiceActionsManagerUtil();

		jsonWebServiceActionsManagerUtil.setJSONWebServiceActionsManager(
			new JSONWebServiceActionsManagerImpl());

		MethodParametersResolverUtil methodParametersResolverUtil =
			new MethodParametersResolverUtil();

		methodParametersResolverUtil.setMethodParametersResolver(
			new MethodParametersResolverImpl());
	}

	protected static void registerAction(Object action) {
		registerAction(action, StringPool.BLANK);
	}

	protected static void registerAction(
		Object action, String servletContextName) {

		registerActionClass(action, action.getClass(), servletContextName);
	}

	protected static void registerActionClass(Class<?> actionClass) {
		registerActionClass(actionClass, StringPool.BLANK);
	}

	protected static void registerActionClass(
		Class<?> actionClass, String servletContextName) {

		registerActionClass(null, actionClass, servletContextName);
	}

	protected static void registerActionClass(
		Object action, Class<?> actionClass, String servletContextName) {

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

			if (action != null) {
				JSONWebServiceActionsManagerUtil.registerJSONWebServiceAction(
					servletContextName, StringPool.BLANK, action, actionClass,
					actionMethod, path, method);
			}
			else {
				JSONWebServiceActionsManagerUtil.registerJSONWebServiceAction(
					servletContextName, StringPool.BLANK, actionClass,
					actionMethod, path, method);
			}
		}
	}

	protected MockHttpServletRequest createHttpRequest(String pathInfo) {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest30();

		mockHttpServletRequest.setMethod(HttpMethods.GET);
		mockHttpServletRequest.setPathInfo(pathInfo);

		return mockHttpServletRequest;
	}

	protected MockHttpServletRequest createHttpRequest(
		String pathInfo, String method) {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest30();

		mockHttpServletRequest.setMethod(method);
		mockHttpServletRequest.setPathInfo(pathInfo);

		return mockHttpServletRequest;
	}

	protected JSONWebServiceAction lookupJSONWebServiceAction(
			HttpServletRequest httpServletRequest)
		throws NoSuchJSONWebServiceException {

		return JSONWebServiceActionsManagerUtil.getJSONWebServiceAction(
			httpServletRequest);
	}

	protected void setServletContext(
		MockHttpServletRequest mockHttpServletRequest, String contextName) {

		MockServletContext mockServletContext =
			(MockServletContext)mockHttpServletRequest.getServletContext();

		mockServletContext.setServletContextName(contextName);

		MockHttpSession mockHttpServletSession = new MockHttpSession(
			mockServletContext);

		mockHttpServletRequest.setSession(mockHttpServletSession);
	}

	protected String toJSON(Object object) {
		if (object instanceof JSONWebServiceInvokerAction.InvokerResult) {
			final JSONWebServiceInvokerAction.InvokerResult invokerResult =
				(JSONWebServiceInvokerAction.InvokerResult)object;

			JSONWebServiceInvokerAction jsonWebServiceInvokerAction =
				invokerResult.getJSONWebServiceInvokerAction();

			JSONWebServiceInvokerAction.InvokerResult newInvokerResult =
				jsonWebServiceInvokerAction.new InvokerResult(
					invokerResult.getResult()) {

				@Override
				protected JSONSerializer createJSONSerializer() {
					JSONSerializer jsonSerializer =
						JSONFactoryUtil.createJSONSerializer();

					jsonSerializer.transform(
						new SortedHashMapJSONTransformer(), HashMap.class);

					return jsonSerializer;
				}

			};

			object = newInvokerResult;
		}

		if (object instanceof JSONSerializable) {
			return ((JSONSerializable)object).toJSONString();
		}

		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();
		return jsonSerializer.serialize(object);
	}

	protected String toJSON(Object object, String... includes) {
		if (object instanceof JSONSerializable) {
			return ((JSONSerializable)object).toJSONString();
		}

		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();
		jsonSerializer.include(includes);

		return jsonSerializer.serialize(object);
	}

	protected List<Object> toList(String json) {
		JSONDeserializer<Map<String, Object>> jsonDeserializer =
			JSONFactoryUtil.createJSONDeserializer();

		return (List<Object>)jsonDeserializer.deserialize(json);
	}

	protected Map<String, Object> toMap(String json) {
		JSONDeserializer<Map<String, Object>> jsonDeserializer =
			JSONFactoryUtil.createJSONDeserializer();

		return jsonDeserializer.deserialize(json);
	}

	private class MockHttpServletRequest30 extends MockHttpServletRequest {

		public MockHttpServletRequest30() {
			_mockServletContext = new MockServletContext() {};

			_mockServletContext.setContextPath(StringPool.BLANK);
			_mockServletContext.setServletContextName(StringPool.BLANK);
		}

		@Override
		public MockServletContext getServletContext() {
			return _mockServletContext;
		}

		private final MockServletContext _mockServletContext;

	}

}