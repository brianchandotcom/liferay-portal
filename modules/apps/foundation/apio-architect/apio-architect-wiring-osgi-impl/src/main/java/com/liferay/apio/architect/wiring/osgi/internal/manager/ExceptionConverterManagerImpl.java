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

package com.liferay.apio.architect.wiring.osgi.internal.manager;

import static com.liferay.apio.architect.unsafe.Unsafe.unsafeCast;

import com.liferay.apio.architect.converter.ExceptionConverter;
import com.liferay.apio.architect.error.APIError;
import com.liferay.apio.architect.wiring.osgi.internal.manager.base.SimpleBaseManager;
import com.liferay.apio.architect.wiring.osgi.manager.ExceptionConverterManager;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class ExceptionConverterManagerImpl
	extends SimpleBaseManager<ExceptionConverter>
	implements ExceptionConverterManager {

	public ExceptionConverterManagerImpl() {
		super(ExceptionConverter.class);
	}

	@Override
	public <T extends Exception> Optional<APIError> convert(T exception) {
		return _convert(exception, unsafeCast(exception.getClass()));
	}

	private <T extends Exception> Optional<APIError> _convert(
		T exception, Class<T> exceptionClass) {

		Optional<ExceptionConverter<T>> optional = unsafeCast(
			getServiceOptional(exceptionClass));

		if (!optional.isPresent()) {
			Optional<Class<?>> classOptional = Optional.ofNullable(
				exceptionClass.getSuperclass());

			return classOptional.filter(
				Exception.class::isAssignableFrom
			).flatMap(
				clazz -> _convert(exception, unsafeCast(clazz))
			);
		}

		return optional.map(
			exceptionConverter -> exceptionConverter.convert(exception));
	}

}