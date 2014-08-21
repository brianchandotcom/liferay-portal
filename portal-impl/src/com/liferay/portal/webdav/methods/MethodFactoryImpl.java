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

package com.liferay.portal.webdav.methods;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.methods.Method;
import com.liferay.portal.kernel.webdav.methods.MethodFactory;
import com.liferay.portal.util.PropsUtil;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class MethodFactoryImpl implements MethodFactory {

	public MethodFactoryImpl() throws Exception {
		for (String methodName : Method.SUPPORTED_METHOD_NAMES) {
			addMethod(methodName);
		}
	}

	@Override
	public Method create(HttpServletRequest request) throws WebDAVException {
		String method = request.getMethod();

		Method methodImpl = (Method)_methods.get(
			StringUtil.toUpperCase(method));

		if (methodImpl == null) {
			throw new WebDAVException(
				"Method " + method + " is not implemented");
		}

		return methodImpl;
	}

	protected void addMethod(String methodName) throws Exception {
		String defaultClassName = methodName.substring(1);

		defaultClassName = StringUtil.toLowerCase(defaultClassName);
		defaultClassName = methodName.substring(0, 1) + defaultClassName;
		defaultClassName =
			"com.liferay.portal.webdav.methods." + defaultClassName +
				"MethodImpl";

		String className = GetterUtil.getString(
			PropsUtil.get(MethodFactoryImpl.class.getName() + "." + methodName),
			defaultClassName);

		_methods.put(methodName, InstanceFactory.newInstance(className));
	}

	private final Map<String, Object> _methods = new HashMap<String, Object>();

}