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

import java.lang.reflect.Method;

import javax.portlet.annotations.ActionMethod;
import javax.portlet.annotations.DestroyMethod;
import javax.portlet.annotations.EventMethod;
import javax.portlet.annotations.HeaderMethod;
import javax.portlet.annotations.InitMethod;
import javax.portlet.annotations.RenderMethod;
import javax.portlet.annotations.ServeResourceMethod;

/**
 * @author Neil Griffin
 */
public class ScannedMethod {

	public ScannedMethod(Class<?> clazz, MethodType methodType, Method method) {
		_clazz = clazz;
		_methodType = methodType;
		_method = method;

		int ordinal = 0;
		String[] portletNames = null;

		if (methodType == MethodType.ACTION) {
			ActionMethod actionMethod = method.getAnnotation(
				ActionMethod.class);

			portletNames = new String[] {actionMethod.portletName()};
		}
		else if (methodType == MethodType.DESTROY) {
			DestroyMethod destroyMethod = method.getAnnotation(
				DestroyMethod.class);

			portletNames = new String[] {destroyMethod.value()};
		}
		else if (methodType == MethodType.EVENT) {
			EventMethod eventMethod = method.getAnnotation(EventMethod.class);

			portletNames = new String[] {eventMethod.portletName()};
		}
		else if (methodType == MethodType.HEADER) {
			HeaderMethod headerMethod = method.getAnnotation(
				HeaderMethod.class);

			ordinal = headerMethod.ordinal();
			portletNames = headerMethod.portletNames();
		}
		else if (methodType == MethodType.INIT) {
			InitMethod initMethod = method.getAnnotation(InitMethod.class);

			portletNames = new String[] {initMethod.value()};
		}
		else if (methodType == MethodType.RENDER) {
			RenderMethod renderMethod = method.getAnnotation(
				RenderMethod.class);

			ordinal = renderMethod.ordinal();
			portletNames = renderMethod.portletNames();
		}
		else if (methodType == MethodType.SERVE_RESOURCE) {
			ServeResourceMethod serveResourceMethod = method.getAnnotation(
				ServeResourceMethod.class);

			ordinal = serveResourceMethod.ordinal();
			portletNames = serveResourceMethod.portletNames();
		}

		_ordinal = ordinal;
		_portletNames = portletNames;
	}

	public Class<?> getClazz() {
		return _clazz;
	}

	public Method getMethod() {
		return _method;
	}

	public MethodType getMethodType() {
		return _methodType;
	}

	public int getOrdinal() {
		return _ordinal;
	}

	public String[] getPortletNames() {
		return _portletNames;
	}

	private final Class<?> _clazz;
	private final Method _method;
	private final MethodType _methodType;
	private final int _ordinal;
	private final String[] _portletNames;

}