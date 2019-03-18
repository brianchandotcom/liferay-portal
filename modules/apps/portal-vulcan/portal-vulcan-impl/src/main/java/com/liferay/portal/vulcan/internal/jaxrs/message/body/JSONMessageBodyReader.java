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

package com.liferay.portal.vulcan.internal.jaxrs.message.body;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.vulcan.internal.jaxrs.validation.ValidatorFactory;

import java.io.IOException;
import java.io.InputStream;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import javax.ws.rs.Consumes;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;

/**
 * @author Javier Gamarra
 */
@Consumes("application/json")
@Provider
public class JSONMessageBodyReader implements MessageBodyReader {

	@Override
	public boolean isReadable(
		Class clazz, Type type, Annotation[] annotations, MediaType mediaType) {

		return true;
	}

	@Override
	public Object readFrom(
			Class clazz, Type type, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap multivaluedMap,
			InputStream inputStream)
		throws IOException {

		ObjectReader objectMapper = _getObjectMapper(
			clazz
		).readerFor(
			clazz
		);

		Object value = objectMapper.readValue(inputStream);

		_validate(value);

		return value;
	}

	private ObjectMapper _getObjectMapper(Class<?> clazz) {
		return Optional.ofNullable(
			_providers.getContextResolver(
				ObjectMapper.class, MediaType.APPLICATION_JSON_TYPE)
		).map(
			contextResolver -> contextResolver.getContext(clazz)
		).map(
			ObjectMapper::copy
		).orElseThrow(
			() -> new InternalServerErrorException(
				"Unable to generate object mapper for class " + clazz)
		);
	}

	private void _validate(Object value) {
		Validator validator = ValidatorFactory.getValidator();

		Set<ConstraintViolation<Object>> constraintViolations =
			validator.validate(value);

		if (constraintViolations.isEmpty()) {
			return;
		}

		StringBundler sb = new StringBundler(constraintViolations.size() * 3);

		for (ConstraintViolation<Object> constraintViolation :
				constraintViolations) {

			sb.append(constraintViolation.getPropertyPath());
			sb.append(StringPool.SPACE);
			sb.append(constraintViolation.getMessage());
		}

		throw new ValidationException(sb.toString());
	}

	@Context
	private Providers _providers;

}