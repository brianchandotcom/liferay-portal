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

package com.liferay.apio.architect.wiring.osgi.internal.manager.base;

import org.osgi.framework.ServiceReference;

/**
 * Manages services that have a generic type. It doesn't perform any
 * transformation on the stored services.
 *
 * @author Alejandro Hernández
 * @review
 */
public abstract class SimpleBaseManager<T> extends BaseManager<T, T> {

	public SimpleBaseManager(Class<T> managedClass) {
		super(managedClass);
	}

	@Override
	public Integer getPrincipalTypeParamPosition() {
		return 0;
	}

	@Override
	public T map(T t, ServiceReference<T> serviceReference, Class<?> clazz) {
		return t;
	}

}