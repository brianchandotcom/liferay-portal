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

package com.liferay.apio.architect.jaxrs.json.internal.reader;

import com.google.gson.Gson;

import com.liferay.apio.architect.functional.Try;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import java.util.Map;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.osgi.service.component.annotations.Component;

/**
 * Reads JSON objects as a {@code Map<String, Object>}.
 *
 * @author Alejandro Hernández
 */
@Component(
	immediate = true,
	property = "liferay.apio.architect.message.body.reader=true"
)
@Consumes(MediaType.APPLICATION_JSON)
@Provider
public class MapMessageBodyReader
	implements MessageBodyReader<Map<String, Object>> {

	@Override
	public boolean isReadable(
		Class<?> type, Type genericType, Annotation[] annotations,
		MediaType mediaType) {

		return true;
	}

	@Override
	public Map<String, Object> readFrom(
			Class<Map<String, Object>> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders,
			InputStream entityStream)
		throws IOException {

		Gson gson = new Gson();

		Try<Map<String, Object>> mapTry = Try.fromFallibleWithResources(
			() -> new InputStreamReader(entityStream, "UTF-8"),
			streamReader -> gson.fromJson(streamReader, genericType));

		return mapTry.orElseThrow(
			() -> new BadRequestException("Body is not a valid JSON"));
	}

}