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

import java.io.IOException;
import java.io.InputStream;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;

/**
 * @author Javier Gamarra
 */
@Produces("application/json")
@Provider
public class JSONMessageBodyReader implements MessageBodyReader {

	@Override
	public boolean isReadable(
		Class type, Type genericType, Annotation[] annotations,
		MediaType mediaType) {

		return true;
	}

	@Override
	public Object readFrom(
			Class clazz, Type genericType, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap httpHeaders,
			InputStream entityStream)
		throws IOException, WebApplicationException {

		ObjectReader objectMapper = _getObjectMapper(
			clazz
		).readerFor(
			clazz
		);

		Object value = objectMapper.readValue(entityStream);

		_validateValue(value);

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

	private void _validateValue(Object value) {
		Validator validator =
			ApacheValidatorFactory.SINGLE_INSTANCE.getValidator();

		Set<ConstraintViolation<Object>> constraintViolations =
			validator.validate(value);

		StringBuilder stringBuilder = new StringBuilder("");

		for (ConstraintViolation<Object> constraintViolation :
				constraintViolations) {

			stringBuilder.append(constraintViolation.getPropertyPath());
			stringBuilder.append(" ");
			stringBuilder.append(constraintViolation.getMessage());
		}

		if (stringBuilder.length() > 0) {
			throw new ValidationException(stringBuilder.toString());
		}
	}

	@Context
	private Providers _providers;

}