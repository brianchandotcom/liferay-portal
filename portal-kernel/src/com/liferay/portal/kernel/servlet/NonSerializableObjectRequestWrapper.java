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

package com.liferay.portal.kernel.servlet;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.lang.reflect.Field;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Igor Spasic
 */
public class NonSerializableObjectRequestWrapper
	extends PersistentHttpServletRequestWrapper {

	public static boolean isWrapped(HttpServletRequest httpServletRequest) {
		if (!_WEBLOGIC_REQUEST_WRAP_NON_SERIALIZABLE) {
			return false;
		}

		Class<?> clazz = httpServletRequest.getClass();

		String className = clazz.getName();

		if (className.startsWith("weblogic.")) {
			httpServletRequest.removeAttribute(
				NonSerializableObjectRequestWrapper.class.getName());

			return false;
		}

		Boolean wrapped = (Boolean)httpServletRequest.getAttribute(
			NonSerializableObjectRequestWrapper.class.getName());

		if (wrapped == null) {
			return false;
		}

		return wrapped.booleanValue();
	}

	public NonSerializableObjectRequestWrapper(
		HttpServletRequest httpServletRequest) {

		super(httpServletRequest);

		httpServletRequest.setAttribute(
			NonSerializableObjectRequestWrapper.class.getName(), Boolean.TRUE);
	}

	@Override
	public Object getAttribute(String name) {
		Object object = null;

		try {
			object = super.getAttribute(name);
		}
		catch (Exception exception) {

			// LPS-31885

			String message = exception.getMessage();

			if ((message == null) || !message.contains("BEA-101362")) {
				_log.error(exception);
			}

			return null;
		}

		return NonSerializableObjectHandler.getValue(object);
	}

	@Override
	public void setAttribute(String name, Object object) {
		if (_WEBLOGIC_REQUEST_WRAP_NON_SERIALIZABLE &&
			!(object instanceof NonSerializableObjectHandler)) {

			object = new NonSerializableObjectHandler(object);
		}

		super.setAttribute(name, object);

		// See LPS-67818

		_setWebLogicClassLoaderFlag(name);
	}

	private void _setWebLogicClassLoaderFlag(String attributeName) {
		ServletRequest servletRequest = getRequest();

		try {
			Field servletRequestAttributesField =
				ReflectionUtil.getDeclaredField(
					servletRequest.getClass(), "attributes");

			Object servletRequestAttributes = servletRequestAttributesField.get(
				servletRequest);

			Field attributesField = ReflectionUtil.getDeclaredField(
				servletRequestAttributes.getClass(), "attributes");

			Map<Object, Object> map = (Map<Object, Object>)attributesField.get(
				servletRequestAttributes);

			Object attribute = map.get(attributeName);

			Field isWebLogicClassLoaderField = ReflectionUtil.getDeclaredField(
				attribute.getClass(), "isWebLogicClassLoader");

			isWebLogicClassLoaderField.set(attribute, false);
		}
		catch (NoSuchFieldException noSuchFieldException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get fields from a Weblogic servlet request",
					noSuchFieldException);
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Unable to set WebLogic class loader flag for ",
						"attribute ", attributeName, " in servlet request ",
						servletRequest),
					exception);
			}
		}
	}

	private static final boolean _WEBLOGIC_REQUEST_WRAP_NON_SERIALIZABLE =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.WEBLOGIC_REQUEST_WRAP_NON_SERIALIZABLE));

	private static final Log _log = LogFactoryUtil.getLog(
		NonSerializableObjectRequestWrapper.class);

}