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
		String servletContextPath, Class<?> actionClass, Method actionMethod,
		String path, String method) {

		_servletContextPath = servletContextPath;
		_actionClass = actionClass;
		_actionMethod = actionMethod;
		_path = path;
		_method = method;

		_methodParameters =
			MethodParametersResolverUtil.resolveMethodParameters(actionMethod);

		_fullPath = _servletContextPath + _path;

		StringBundler sb = new StringBundler(_methodParameters.length * 2 + 4);

		sb.append(_fullPath);
		sb.append(CharPool.MINUS);
		sb.append(_methodParameters.length);

		for (MethodParameter methodParameter : _methodParameters) {
			sb.append(CharPool.MINUS);
			sb.append(methodParameter.getName());
		}

		_signature = sb.toString();
	}

	public int compareTo(
		JSONWebServiceActionConfig jsonWebServiceActionConfig) {

		return _signature.compareTo(jsonWebServiceActionConfig._signature);
	}

	public Class<?> getActionClass() {
		return _actionClass;
	}

	public Method getActionMethod() {
		return _actionMethod;
	}

	public String getFullPath() {
		return _fullPath;
	}

	public String getMethod() {
		return _method;
	}

	public MethodParameter[] getMethodParameters() {
		return _methodParameters;
	}

	public String getPath() {
		return _path;
	}

	public String getServletContextPath() {
		return _servletContextPath;
	}

	public String getSignature() {
		return _signature;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{actionClass=");
		sb.append(_actionClass);
		sb.append(", actionMethod=");
		sb.append(_actionMethod);
		sb.append(", fullPath=");
		sb.append(_fullPath);
		sb.append(", method=");
		sb.append(_method);
		sb.append(", methodParameters=");
		sb.append(_methodParameters);
		sb.append(", path=");
		sb.append(_path);
		sb.append(", servletContextPath=");
		sb.append(_servletContextPath);
		sb.append(", signature=");
		sb.append(_signature);
		sb.append("}");

		return sb.toString();
	}

	private Class<?> _actionClass;
	private Method _actionMethod;
	private String _fullPath;
	private String _method;
	private MethodParameter[] _methodParameters;
	private String _path;
	private String _servletContextPath;
	private String _signature;

}