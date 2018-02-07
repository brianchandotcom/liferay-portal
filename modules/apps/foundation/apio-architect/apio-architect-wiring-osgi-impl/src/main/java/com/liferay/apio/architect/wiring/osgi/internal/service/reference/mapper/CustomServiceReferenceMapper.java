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

package com.liferay.apio.architect.wiring.osgi.internal.service.reference.mapper;

import static com.liferay.apio.architect.wiring.osgi.internal.manager.TypeArgumentProperties.PRINCIPAL_TYPE_ARGUMENT;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.util.ManagerUtil.getGenericClassFromPropertyOrElse;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.util.ManagerUtil.getTypeParamOrFail;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * An implementation of a {@link ServiceReferenceMapper} that emits the first
 * generic type param class' name as the key.
 *
 * @author Alejandro Hernández
 * @review
 */
public class CustomServiceReferenceMapper<T>
	implements ServiceReferenceMapper<String, T> {

	public CustomServiceReferenceMapper(
		BundleContext bundleContext, Class<T> clazz,
		Integer typeParamPosition) {

		_bundleContext = bundleContext;
		_clazz = clazz;
		_typeParamPosition = typeParamPosition;
	}

	@Override
	public void map(
		ServiceReference<T> serviceReference, Emitter<String> emitter) {

		T t = _bundleContext.getService(serviceReference);

		Class<?> genericClass = getGenericClassFromPropertyOrElse(
			serviceReference, PRINCIPAL_TYPE_ARGUMENT,
			() -> getTypeParamOrFail(t, _clazz, _typeParamPosition));

		emitter.emit(genericClass.getName());
	}

	private final BundleContext _bundleContext;
	private final Class<T> _clazz;
	private final Integer _typeParamPosition;

}