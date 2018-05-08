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

import static com.liferay.apio.architect.wiring.osgi.internal.manager.TypeArgumentProperties.KEY_PRINCIPAL_TYPE_ARGUMENT;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.util.ManagerUtil.getGenericClassFromPropertyOrElse;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.util.ManagerUtil.getTypeParamOrFail;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper.Emitter;

import java.util.Optional;

import org.osgi.framework.ServiceReference;

/**
 * Manages services that have a generic type using the generic type's class name
 * as key.
 *
 * @author Alejandro Hernández
 */
public abstract class ClassNameBaseManager<T> extends BaseManager<T, String> {

	public ClassNameBaseManager(
		Class<T> managedClass, Integer principalTypeParamPosition) {

		super(managedClass);

		_managedClass = managedClass;
		_principalTypeParamPosition = principalTypeParamPosition;
	}

	protected void emit(
		ServiceReference<T> serviceReference, Emitter<String> emitter) {

		T t = bundleContext.getService(serviceReference);

		Class<?> genericClass = getGenericClassFromPropertyOrElse(
			serviceReference, KEY_PRINCIPAL_TYPE_ARGUMENT,
			() -> getTypeParamOrFail(
				t, _managedClass, _principalTypeParamPosition));

		emitter.emit(genericClass.getName());
	}

	/**
	 * Returns a service from the inner map based on the service's generic inner
	 * class, if the service exists. Returns {@code Optional#empty()} otherwise.
	 *
	 * @param  clazz the generic inner class
	 * @return the service, if present; {@code Optional#empty()} otherwise
	 */
	protected <V> Optional<T> getServiceOptional(Class<V> clazz) {
		return Optional.ofNullable(
			serviceTrackerMap.getService(clazz.getName()));
	}

	private final Class<T> _managedClass;
	private final Integer _principalTypeParamPosition;

}