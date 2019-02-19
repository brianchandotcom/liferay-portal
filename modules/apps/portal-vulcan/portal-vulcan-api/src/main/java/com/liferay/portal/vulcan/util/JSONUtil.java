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

package com.liferay.portal.vulcan.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.petra.string.StringBundler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;

/**
 * Contains different utilities regarding JSON HTTP bodies.
 *
 * @author Alejandro Hernández
 * @review
 */
public class JSONUtil {

	/**
	 * Deserialize JSON content from given JSON input stream.
	 *
	 * @throws BadRequestException if a format-relating error occurs (invalid
	 *         JSON, invalid field format...)
	 * @throws InternalServerErrorException if a low-level I/O problem occurs
	 * @review
	 */
	public static <T> T readValueFrom(InputStream inputStream, Class<T> clazz)
		throws BadRequestException, InternalServerErrorException {

		try {
			return _objectMapper.readValue(inputStream, clazz);
		}
		catch (JsonParseException jpe) {
			throw new BadRequestException("Input is not a valid JSON", jpe);
		}
		catch (InvalidFormatException ife) {
			String path = _getPath(ife);

			Class<?> targetType = ife.getTargetType();

			String message = StringBundler.concat(
				"Unable to match field {", path, "} with value {",
				ife.getValue(), "} to " + targetType.getSimpleName());

			throw new BadRequestException(message, ife);
		}
		catch (JsonMappingException jme) {
			String path = _getPath(jme);

			String message = "An error occurred mapping {" + path + "} field";

			throw new BadRequestException(message, jme);
		}
		catch (IOException ioe) {
			throw new InternalServerErrorException(ioe);
		}
	}

	/**
	 * Deserialize JSON content from given String.
	 *
	 * @throws BadRequestException if a format-relating error occurs (invalid
	 *         JSON, invalid field format...)
	 * @throws InternalServerErrorException if a low-level I/O problem occurs
	 * @review
	 */
	public static <T> T readValueFrom(String string, Class<T> clazz)
		throws BadRequestException, InternalServerErrorException {

		InputStream inputStream = new ByteArrayInputStream(string.getBytes());

		return readValueFrom(inputStream, clazz);
	}

	private static String _getPath(JsonMappingException jme) {
		List<JsonMappingException.Reference> references = jme.getPath();

		Stream<JsonMappingException.Reference> stream = references.stream();

		return stream.map(
			JsonMappingException.Reference::getFieldName
		).collect(
			Collectors.joining(".")
		);
	}

	private static final ObjectMapper _objectMapper = new ObjectMapper() {
		{
			setDateFormat(new ISO8601DateFormat());
		}
	};

}