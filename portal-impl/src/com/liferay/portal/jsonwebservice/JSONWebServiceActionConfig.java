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

import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionMapping;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MethodParameter;
import com.liferay.portal.kernel.util.MethodParametersResolverUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Method;

/**
 * @author Igor Spasic
 * @author Raymond Augé
 */
public class JSONWebServiceActionConfig
	implements Comparable<JSONWebServiceActionConfig>,
			   JSONWebServiceActionMapping {

	public JSONWebServiceActionConfig(
		String contextName, String contextPath, Class<?> actionClass,
		Method actionMethod, String path, String method) {

		this(
			contextName, contextPath, null, actionClass, actionMethod, path,
			method);
	}

	public JSONWebServiceActionConfig(
		String contextName, String contextPath, Object actionObject,
		Class<?> actionClass, Method actionMethod, String path, String method) {

		_contextName = GetterUtil.getString(contextName);
		_contextPath = GetterUtil.getString(contextPath);
		_actionObject = actionObject;
		_actionClass = actionClass;

		Method newActionMethod = actionMethod;

		if (actionObject != null) {
			try {
				Class<?> actionObjectClass = actionObject.getClass();

				Method actionObjectClassActionMethod =
					actionObjectClass.getMethod(
						actionMethod.getName(),
						actionMethod.getParameterTypes());

				newActionMethod = actionObjectClassActionMethod;
			}
			catch (NoSuchMethodException nsme) {
				throw new IllegalArgumentException(nsme);
			}
		}

		_actionMethod = newActionMethod;

		if (Validator.isNotNull(_contextName)) {
			path = path.substring(1);

			path = StringPool.SLASH.concat(_contextName).concat(
				StringPool.PERIOD).concat(path);
		}

		_path = path;

		_method = method;

		Deprecated deprecated = actionMethod.getAnnotation(Deprecated.class);

		if (deprecated != null) {
			_deprecated = true;
		}
		else {
			_deprecated = false;
		}

		_methodParameters =
			MethodParametersResolverUtil.resolveMethodParameters(actionMethod);

		Method realActionMethod = null;

		try {
			realActionMethod = _actionClass.getDeclaredMethod(
				actionMethod.getName(), actionMethod.getParameterTypes());
		}
		catch (NoSuchMethodException nsme) {
		}

		_realActionMethod = realActionMethod;

		StringBundler sb = new StringBundler(_methodParameters.length * 2 + 3);

		sb.append(_path);
		sb.append(StringPool.MINUS);
		sb.append(_methodParameters.length);

		for (MethodParameter methodParameter : _methodParameters) {
			sb.append(StringPool.MINUS);
			sb.append(methodParameter.getName());
		}

		_signature = sb.toString();
	}

	@Override
	public int compareTo(
		JSONWebServiceActionConfig jsonWebServiceActionConfig) {

		return _signature.compareTo(jsonWebServiceActionConfig._signature);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof JSONWebServiceActionConfig)) {
			return false;
		}

		JSONWebServiceActionConfig jsonWebServiceActionConfig =
			(JSONWebServiceActionConfig)object;

		if (Validator.equals(
				_signature, jsonWebServiceActionConfig._signature)) {

			return true;
		}

		return false;
	}

	@Override
	public Class<?> getActionClass() {
		return _actionClass;
	}

	@Override
	public Method getActionMethod() {
		return _actionMethod;
	}

	@Override
	public Object getActionObject() {
		return _actionObject;
	}

	@Override
	public String getContextName() {
		return _contextName;
	}

	@Override
	public String getContextPath() {
		return _contextPath;
	}

	@Override
	public String getMethod() {
		return _method;
	}

	@Override
	public MethodParameter[] getMethodParameters() {
		return _methodParameters;
	}

	@Override
	public String getPath() {
		return _path;
	}

	@Override
	public Method getRealActionMethod() {
		return _realActionMethod;
	}

	@Override
	public String getSignature() {
		return _signature;
	}

	@Override
	public int hashCode() {
		return _signature.hashCode();
	}

	@Override
	public boolean isDeprecated() {
		return _deprecated;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{actionClass=");
		sb.append(_actionClass);
		sb.append(", actionMethod=");
		sb.append(_actionMethod);
		sb.append(", contextName=");
		sb.append(_contextName);
		sb.append(", contextPath=");
		sb.append(_contextPath);
		sb.append(", deprecated=");
		sb.append(_deprecated);
		sb.append(", method=");
		sb.append(_method);
		sb.append(", methodParameters=");
		sb.append(_methodParameters);
		sb.append(", path=");
		sb.append(_path);
		sb.append(", realActionMethod=");
		sb.append(_realActionMethod);
		sb.append(", signature=");
		sb.append(_signature);
		sb.append("}");

		return sb.toString();
	}

	private final Class<?> _actionClass;
	private final Method _actionMethod;
	private final Object _actionObject;
	private final String _contextName;
	private final String _contextPath;
	private final boolean _deprecated;
	private final String _method;
	private final MethodParameter[] _methodParameters;
	private final String _path;
	private final Method _realActionMethod;
	private final String _signature;

}