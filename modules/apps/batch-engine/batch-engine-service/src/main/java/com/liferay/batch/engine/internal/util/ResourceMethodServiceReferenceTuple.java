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

package com.liferay.batch.engine.internal.util;

import java.lang.reflect.Method;

import org.osgi.framework.ServiceReference;

/**
 * @author Ivica Cardic
 */
public class ResourceMethodServiceReferenceTuple {

	public ResourceMethodServiceReferenceTuple(
		Method method, String[] methodParameterNames,
		ServiceReference<Object> serviceReference) {

		_method = method;
		_methodParameterNames = methodParameterNames;
		_serviceReference = serviceReference;
	}

	public Method getMethod() {
		return _method;
	}

	public String[] getMethodParameterNames() {
		return _methodParameterNames;
	}

	public ServiceReference<Object> getServiceReference() {
		return _serviceReference;
	}

	private final Method _method;
	private final String[] _methodParameterNames;
	private final ServiceReference<Object> _serviceReference;

}