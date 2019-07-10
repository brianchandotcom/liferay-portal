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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.internal.jaxrs.validation.ValidationUtil;

import java.io.IOException;
import java.io.InputStream;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Providers;

/**
 * @author Javier Gamarra
 * @author Ivica Cardic
 */
public abstract class BaseMessageBodyReader implements MessageBodyReader {

	public BaseMessageBodyReader(
		Class<? extends ObjectMapper> contextType, MediaType mediaType) {

		_contextType = contextType;
		_mediaType = mediaType;
	}

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

		if (!StringUtil.equals(
				_httpServletRequest.getMethod(), HttpMethod.PATCH)) {

			ValidationUtil.validate(value);
		}

		return value;
	}

	private ObjectMapper _getObjectMapper(Class<?> clazz) {
		return Optional.ofNullable(
			_providers.getContextResolver(_contextType, _mediaType)
		).map(
			contextResolver -> contextResolver.getContext(clazz)
		).orElseThrow(
			() -> new InternalServerErrorException(
				"Unable to generate object mapper for class " + clazz)
		);
	}

	private final Class<? extends ObjectMapper> _contextType;

	@Context
	private HttpServletRequest _httpServletRequest;

	private final MediaType _mediaType;

	@Context
	private Providers _providers;

}