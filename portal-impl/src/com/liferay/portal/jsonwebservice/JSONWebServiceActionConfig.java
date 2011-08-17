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
import com.liferay.portal.kernel.util.MethodParameterNamesResolverUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.lang.reflect.Method;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceActionConfig
	implements Comparable<JSONWebServiceActionConfig>,
	JSONWebServiceActionMapping {

	public JSONWebServiceActionConfig(
		Class<?> actionClass, Method actionMethod, String path, String method) {

		_actionClass = actionClass;
		_actionMethod = actionMethod;
		_path = path;
		_method = method;

		_parameterNames =
			MethodParameterNamesResolverUtil.resolveParameterNames(
				actionMethod);
		_parameterTypes = actionMethod.getParameterTypes();

		StringBundler sb = new StringBundler(_parameterNames.length * 2 + 4);

		sb.append(_path);
		sb.append(CharPool.MINUS);
		sb.append(_parameterNames.length);

		for (String parameterName : _parameterNames) {
			sb.append(CharPool.MINUS);
			sb.append(parameterName);
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

	public String[] getParameterNames() {
		return _parameterNames;
	}

	public Class<?>[] getParameterTypes() {
		return _parameterTypes;
	}

	public String getPath() {
		return _path;
	}

	public String getSignature() {
		return _fullPath;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{actionClass=");
		sb.append(_actionClass);
		sb.append(", actionMethod=");
		sb.append(_actionMethod);
		sb.append(", fullPath=");
		sb.append(_fullPath);
		sb.append(", method=");
		sb.append(_method);
		sb.append(", parameterNames=");
		sb.append(_parameterNames);
		sb.append(", parameterTypes=");
		sb.append(_parameterTypes);
		sb.append(", path=");
		sb.append(_path);
		sb.append("}");

		return sb.toString();
	}

	private Class<?> _actionClass;
	private Method _actionMethod;
	private String _fullPath;
	private String _method;
	private String[] _parameterNames;
	private Class<?>[] _parameterTypes;
	private String _path;

}