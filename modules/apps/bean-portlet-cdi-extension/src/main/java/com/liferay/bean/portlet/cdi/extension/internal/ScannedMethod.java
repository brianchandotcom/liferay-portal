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

	public static ScannedMethod create(
		Class<?> clazz, MethodType methodType, Method method) {

		return new ScannedMethod(clazz, methodType, method);
	}

	public ScannedMethod(Class<?> clazz, MethodType methodType, Method method) {
		this(clazz, methodType, method, null);
	}

	public ScannedMethod(
		Class<?> clazz, MethodType methodType, Method method,
		String configuredPortletName) {

		_clazz = clazz;
		_method = method;

		if (methodType == MethodType.ACTION) {
			ActionMethod actionMethod = method.getAnnotation(
				ActionMethod.class);

			if (actionMethod != null) {
				_portletNames = new String[] {actionMethod.portletName()};
			}
		}
		else if (methodType == MethodType.DESTROY) {
			DestroyMethod destroyMethod = method.getAnnotation(
				DestroyMethod.class);

			if (destroyMethod != null) {
				_portletNames = new String[] {destroyMethod.value()};
			}
		}
		else if (methodType == MethodType.EVENT) {
			EventMethod eventMethod = method.getAnnotation(EventMethod.class);

			if (eventMethod != null) {
				_portletNames = new String[] {eventMethod.portletName()};
			}
		}
		else if (methodType == MethodType.HEADER) {
			HeaderMethod headerMethod = method.getAnnotation(
				HeaderMethod.class);

			if (headerMethod != null) {
				_ordinal = headerMethod.ordinal();
				_portletNames = headerMethod.portletNames();
			}
		}
		else if (methodType == MethodType.INIT) {
			InitMethod initMethod = method.getAnnotation(InitMethod.class);

			if (initMethod != null) {
				_portletNames = new String[] {initMethod.value()};
			}
		}
		else if (methodType == MethodType.RENDER) {
			RenderMethod renderMethod = method.getAnnotation(
				RenderMethod.class);

			if (renderMethod != null) {
				_ordinal = renderMethod.ordinal();
				_portletNames = renderMethod.portletNames();
			}
		}
		else if (methodType == MethodType.SERVE_RESOURCE) {
			ServeResourceMethod serveResourceMethod = method.getAnnotation(
				ServeResourceMethod.class);

			if (serveResourceMethod != null) {
				_ordinal = serveResourceMethod.ordinal();
				_portletNames = serveResourceMethod.portletNames();
			}
		}

		if ((_portletNames == null) && (configuredPortletName != null)) {
			_portletNames = new String[] {configuredPortletName};
		}
	}

	public Class<?> getClazz() {
		return _clazz;
	}

	public Method getMethod() {
		return _method;
	}

	public int getOrdinal() {
		return _ordinal;
	}

	public String[] getPortletNames() {
		return _portletNames;
	}

	private Class<?> _clazz;
	private Method _method;
	private int _ordinal;
	private String[] _portletNames;

}