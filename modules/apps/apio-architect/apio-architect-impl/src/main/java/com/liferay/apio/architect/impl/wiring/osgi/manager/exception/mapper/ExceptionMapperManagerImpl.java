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

package com.liferay.apio.architect.impl.wiring.osgi.manager.exception.mapper;

import static com.liferay.apio.architect.impl.unsafe.Unsafe.unsafeCast;

import com.liferay.apio.architect.error.APIError;
import com.liferay.apio.architect.exception.mapper.ExceptionMapper;
import com.liferay.apio.architect.impl.wiring.osgi.manager.base.ClassNameBaseManager;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Hernández
 */
@Component
public class ExceptionMapperManagerImpl
	extends ClassNameBaseManager<ExceptionMapper>
	implements ExceptionMapperManager {

	public ExceptionMapperManagerImpl() {
		super(ExceptionMapper.class, 0);
	}

	@Override
	public <T extends Exception> Optional<APIError> map(T exception) {
		return _convert(exception, unsafeCast(exception.getClass()));
	}

	private <T extends Exception> Optional<APIError> _convert(
		T exception, Class<T> exceptionClass) {

		Optional<ExceptionMapper<T>> optional = unsafeCast(
			getServiceOptional(exceptionClass));

		return optional.map(
			exceptionConverter -> exceptionConverter.map(exception)
		).map(
			Optional::of
		).orElseGet(
			() -> Optional.ofNullable(
				exceptionClass.getSuperclass()
			).filter(
				Exception.class::isAssignableFrom
			).flatMap(
				clazz -> _convert(exception, unsafeCast(clazz))
			)
		);
	}

}