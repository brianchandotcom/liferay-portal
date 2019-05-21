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

package com.liferay.bean.portlet.spring.extension.internal.mvc;

import java.lang.reflect.Method;

import javax.ws.rs.container.ResourceInfo;

/**
 * @author Neil Griffin
 */
public class ResourceInfoImpl implements ResourceInfo {

	public ResourceInfoImpl(Class<?> resourceClass, Method resourceMethod) {
		_resourceClass = resourceClass;
		_resourceMethod = resourceMethod;
	}

	@Override
	public Class<?> getResourceClass() {
		return _resourceClass;
	}

	@Override
	public Method getResourceMethod() {
		return _resourceMethod;
	}

	private final Class<?> _resourceClass;
	private final Method _resourceMethod;

}