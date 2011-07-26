/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.service.ServiceContext;

import java.lang.reflect.Method;

import jodd.util.ReflectUtil;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceActionImpl implements JSONWebServiceAction {

	public JSONWebServiceActionImpl(
		JSONWebServiceActionConfig jsonWebServiceActionConfig,
		JSONWebServiceActionParameters jsonWebServiceActionParameters) {

		_jsonWebServiceActionConfig = jsonWebServiceActionConfig;
		_jsonWebServiceActionParameters = jsonWebServiceActionParameters;
	}

	public Object invoke() throws Exception {
		JSONRPCRequest jsonRpcRequest =
			_jsonWebServiceActionParameters.getJSONRPCRequest();

		if (jsonRpcRequest == null) {
			return _invokeActionMethod();
		}

		Object result = null;
		Exception exception = null;

		try {
			result = _invokeActionMethod();
		}
		catch (Exception e) {
			exception = e;
		}

		return new JSONRPCResponse(jsonRpcRequest, result, exception);
	}

	private Object _createDefaultParameterValue(
		String parameterName, Class<?> parameterType) {

		if (parameterName.equals("serviceContext") &&
				parameterType.equals(ServiceContext.class)) {

			return new ServiceContext();
		}

		return null;
	}

	private Object _invokeActionMethod() throws Exception {
		Method actionMethod = _jsonWebServiceActionConfig.getActionMethod();

		Class<?> actionClass = _jsonWebServiceActionConfig.getActionClass();

		Object[] parameters = _prepareParameters();

		return actionMethod.invoke(actionClass, parameters);
	}

	private Object[] _prepareParameters() {
		String[] parameterNames =
			_jsonWebServiceActionConfig.getParameterNames();

		Class<?>[] parameterTypes =
			_jsonWebServiceActionConfig.getParameterTypes();

		Object[] parameters = new Object[parameterNames.length];

		for (int i = 0; i < parameterNames.length; i++) {
			String parameterName = parameterNames[i];

			Object value =
				_jsonWebServiceActionParameters.getParameter(parameterName);

			Object parameterValue = null;

			if (value != null) {
				Class<?> parameterType = parameterTypes[i];

				if (value == Void.TYPE) {

					parameterValue = _createDefaultParameterValue(
						parameterName, parameterType);

				}
				else {
					parameterValue = ReflectUtil.castType(value, parameterType);
				}
			}

			parameters[i] = parameterValue;
		}

		return parameters;
	}

	private JSONWebServiceActionConfig _jsonWebServiceActionConfig;
	private JSONWebServiceActionParameters _jsonWebServiceActionParameters;

}