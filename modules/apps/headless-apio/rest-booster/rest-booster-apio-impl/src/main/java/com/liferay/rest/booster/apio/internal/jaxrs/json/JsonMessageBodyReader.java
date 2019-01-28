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

package com.liferay.rest.booster.apio.internal.jaxrs.json;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Ivica Cardic
 */
@Component(
	property = JaxrsWhiteboardConstants.JAX_RS_EXTENSION + "=true",
	service = MessageBodyReader.class
)
@Consumes(MediaType.APPLICATION_JSON)
@Provider
public class JsonMessageBodyReader implements MessageBodyReader<Object> {

	@Override
	public boolean isReadable(
		Class<?> type, Type genericType, Annotation[] annotations,
		MediaType mediaType) {

		return _OBJECT_MAPPER.canSerialize(type);
	}

	@Override
	public Object readFrom(
			Class<Object> type, Type genericType, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap<String, String> httpHeaders,
			InputStream inputStream)
		throws IOException, WebApplicationException {

		return _OBJECT_MAPPER.readValue(inputStream, type);
	}

	private static final ObjectMapper _OBJECT_MAPPER = new ObjectMapper();

}