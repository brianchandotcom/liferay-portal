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

package com.liferay.bulk.rest.internal.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

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
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=bulk-application)",
		"osgi.jaxrs.extension=true", "osgi.jaxrs.name=Reader.JSON"
	},
	scope = ServiceScope.PROTOTYPE, service = MessageBodyReader.class
)
@Consumes("application/json")
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
			Class type, Type genericType, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap httpHeaders,
			InputStream entityStream)
		throws IOException, WebApplicationException {

		ObjectReader objectReader = _objectMapper.readerFor(type);

		return objectReader.readValue(entityStream);
	}

	private final ObjectMapper _objectMapper = new ObjectMapper();

}