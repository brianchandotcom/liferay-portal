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

package com.liferay.portal.kernel.bean;

import com.liferay.portal.kernel.util.HtmlUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Wraps a bean so that all strings returned from <code>@AutoEscape</code>
 * annotated methods are automatically HTML escaped.
 *
 * <p>
 * For a usage example see {@link
 * com.liferay.portlet.shopping.util.ShoppingUtil#getBreadcrumbs(
 * com.liferay.portlet.shopping.model.ShoppingCategory,
 * javax.servlet.jsp.PageContext, javax.portlet.RenderRequest,
 * javax.portlet.RenderResponse) ShoppingUtil.getBreadcrumbs} .
 * </p>
 *
 * @author Shuyang Zhou
 * @see    AutoEscape
 */
public class AutoEscapeBeanHandler implements InvocationHandler {

	public AutoEscapeBeanHandler(Object bean) {
		_bean = bean;
	}

	public Object getBean() {
		return _bean;
	}

	public Object invoke(Object proxy, Method method, Object[] args)
		throws Throwable {

		String methodName = method.getName();

		if (methodName.startsWith("set")) {
			throw new IllegalAccessException(
				"Setter methods cannot be called on an escaped bean");
		}

		if (methodName.endsWith("isEscapedModel")) {
			return true;
		}

		Object result = null;

		try {
			result = method.invoke(_bean, args);
		}
		catch (InvocationTargetException ite) {
			throw ite.getTargetException();
		}

		if (method.getAnnotation(AutoEscape.class) != null) {
			result = HtmlUtil.escape((String)result);
		}

		return result;
	}

	private Object _bean;

}