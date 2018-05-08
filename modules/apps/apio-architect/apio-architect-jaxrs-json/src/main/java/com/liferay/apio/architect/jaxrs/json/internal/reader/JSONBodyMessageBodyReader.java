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

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.liferay.apio.architect.form.Body;
import com.liferay.apio.architect.functional.Try;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.osgi.service.component.annotations.Component;

/**
 * Reads JSON objects as a {@link Body}.
 *
 * @author Alejandro Hernández
 */
@Component(
	immediate = true,
	property = "liferay.apio.architect.message.body.reader=true"
)
@Consumes(APPLICATION_JSON)
@Provider
public class JSONBodyMessageBodyReader implements MessageBodyReader<Body> {

	@Override
	public boolean isReadable(
		Class<?> type, Type genericType, Annotation[] annotations,
		MediaType mediaType) {

		return true;
	}

	@Override
	public Body readFrom(
			Class<Body> type, Type genericType, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap<String, String> httpHeaders,
			InputStream entityStream)
		throws IOException {

		Gson gson = new Gson();

		JsonObject jsonObject = Try.fromFallibleWithResources(
			() -> new InputStreamReader(entityStream, "UTF-8"),
			streamReader -> gson.fromJson(streamReader, JsonObject.class)
		).orElseThrow(
			() -> new BadRequestException("Body is not a valid JSON")
		);

		return Body.create(
			_transform(
				jsonObject,
				aTry -> aTry.filter(
					JsonElement::isJsonPrimitive
				).map(
					JsonElement::getAsString
				)),
			_transform(
				jsonObject,
				aTry -> aTry.filter(
					JsonElement::isJsonArray
				).map(
					JsonElement::getAsJsonArray
				).map(
					jsonArray -> {
						List<JsonElement> jsonElements = new ArrayList<>();

						Iterator<JsonElement> iterator = jsonArray.iterator();

						iterator.forEachRemaining(jsonElements::add);

						return jsonElements;
					}
				).map(
					List::stream
				).map(
					stream -> stream.filter(
						JsonElement::isJsonPrimitive
					).map(
						JsonElement::getAsString
					).collect(
						Collectors.toList()
					)
				)));
	}

	private <T> Function<String, Optional<T>> _transform(
		JsonObject jsonObject, Function<Try<JsonElement>, Try<T>> function) {

		return key -> function.apply(
			Try.success(
				key
			).map(
				jsonObject::get
			)
		).fold(
			__ -> Optional.empty(), Optional::of
		);
	}

}