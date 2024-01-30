/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.bean;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.HtmlUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Map;
import java.util.function.Function;

/**
 * Wraps a bean so that all strings returned from <code>@AutoEscape</code>
 * annotated methods are automatically HTML escaped.
 *
 * @author Shuyang Zhou
 * @see    AutoEscape
 */
public class AutoEscapeBeanHandler implements InvocationHandler, Serializable {

	public AutoEscapeBeanHandler(Object bean) {
		_bean = (Serializable)bean;
	}

	public Object getAttributeValue(String attributeName) throws Exception {
		BaseModel<?> baseModel = (BaseModel<?>)_bean;

		Map<String, ?> attributeGetterFunctions =
			baseModel.getAttributeGetterFunctions();

		Function<Object, Object> getterFunction =
			(Function<Object, Object>)attributeGetterFunctions.get(
				attributeName);

		Object value = getterFunction.apply(baseModel);

		if (value instanceof String) {
			Class<?> modelClass = baseModel.getModelClass();

			Method method = modelClass.getMethod(
				"get" + Character.toUpperCase(attributeName.charAt(0)) +
					attributeName.substring(1));

			if (method.getAnnotation(AutoEscape.class) != null) {
				return HtmlUtil.escape((String)value);
			}
		}

		return value;
	}

	public Object getBean() {
		return _bean;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] arguments)
		throws Throwable {

		String methodName = method.getName();

		if (methodName.startsWith("set")) {
			throw new IllegalAccessException(
				"Setter methods cannot be called on an escaped bean");
		}

		if (methodName.equals("getWrappedModel")) {
			return _bean;
		}
		else if (methodName.endsWith("isEscapedModel")) {
			return true;
		}
		else if (methodName.endsWith("toEscapedModel")) {
			return proxy;
		}

		Object result = null;

		try {
			result = method.invoke(_bean, arguments);
		}
		catch (InvocationTargetException invocationTargetException) {
			throw invocationTargetException.getTargetException();
		}

		if (method.getAnnotation(AutoEscape.class) != null) {
			result = HtmlUtil.escape((String)result);
		}

		return result;
	}

	private final Serializable _bean;

}