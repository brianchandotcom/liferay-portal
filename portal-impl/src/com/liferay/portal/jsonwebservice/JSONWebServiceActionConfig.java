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

import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionMapping;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.MethodParameter;
import com.liferay.portal.kernel.util.MethodParametersResolverUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.lang.reflect.Method;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceActionConfig
	implements Comparable<JSONWebServiceActionConfig>,
	JSONWebServiceActionMapping {

	public JSONWebServiceActionConfig(
		String servletContextName, Class<?> actionClass, Method actionMethod,
		String path, String method) {

		_servletContextName = servletContextName;
		_actionClass = actionClass;
		_actionMethod = actionMethod;
		_path = path;
		_method = method;

		_parameters =
			MethodParametersResolverUtil.resolveParameters(actionMethod);

		StringBundler sb = new StringBundler(_parameters.length * 2 + 4);

		sb.append(_path);
		sb.append(CharPool.MINUS);
		sb.append(_parameters.length);

		for (MethodParameter parameter : _parameters) {
			sb.append(CharPool.MINUS);
			sb.append(parameter.getName());
		}

		_fullPath = sb.toString();
	}

	public int compareTo(
		JSONWebServiceActionConfig jsonWebServiceActionConfig) {

		return _fullPath.compareTo(jsonWebServiceActionConfig._fullPath);
	}

	public Class<?> getActionClass() {
		return _actionClass;
	}

	public Method getActionMethod() {
		return _actionMethod;
	}

	public String getMethod() {
		return _method;
	}

	public MethodParameter[] getMethodParameters() {
		return _parameters;
	}

	public String getPath() {
		return _path;
	}

	public String getServletContextName() {
		return _servletContextName;
	}

	public String getSignature() {
		return _fullPath;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{actionClass=");
		sb.append(_actionClass);
		sb.append(", actionMethod=");
		sb.append(_actionMethod);
		sb.append(", fullPath=");
		sb.append(_fullPath);
		sb.append(", method=");
		sb.append(_method);
		sb.append(", parameterNames=");
		sb.append(_parameters);
		sb.append(", path=");
		sb.append(_path);
		sb.append("}");

		return sb.toString();
	}

	private Class<?> _actionClass;
	private Method _actionMethod;
	private String _fullPath;
	private String _method;
	private MethodParameter[] _parameters;
	private String _path;
	private String _servletContextName;

}