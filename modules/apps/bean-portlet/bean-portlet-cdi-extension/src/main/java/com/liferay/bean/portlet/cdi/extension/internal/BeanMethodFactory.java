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

package com.liferay.bean.portlet.cdi.extension.internal;

import com.liferay.petra.string.StringBundler;

import java.lang.reflect.Method;

import javax.enterprise.inject.spi.BeanManager;

/**
 * @author Neil Griffin
 */
public class BeanMethodFactory {

	public BeanMethodFactory(
		Class<?> clazz, Method method, MethodType methodType) {

		_clazz = clazz;
		_method = method;
		_methodType = methodType;
	}

	public BeanMethod create(BeanManager beanManager) {
		return new BeanMethod(
			beanManager, beanManager.resolve(beanManager.getBeans(_clazz)),
			_method, _methodType);
	}

	public String[] getPortletNames() {
		return _methodType.getPortletNames(_method);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{clazz=");
		sb.append(_clazz);
		sb.append(", method=");
		sb.append(_method);
		sb.append(", methodType=");
		sb.append(_methodType);
		sb.append("}");

		return sb.toString();
	}

	private final Class<?> _clazz;
	private final Method _method;
	private final MethodType _methodType;

}