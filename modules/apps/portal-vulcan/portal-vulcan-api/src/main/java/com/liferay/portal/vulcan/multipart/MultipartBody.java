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

package com.liferay.portal.vulcan.multipart;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import java.io.IOException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;

/**
 * @author Javier Gamarra
 */
public class MultipartBody {

	public static MultipartBody of(
		Map<String, BinaryFile> binaryFiles, Map<String, String> values) {

		return new MultipartBody(binaryFiles, values);
	}

	public BinaryFile getBinaryFile(String key) {
		return _binaryFiles.get(key);
	}

	public <T> T getJSONObjectValue(String key, Class<T> tClass) {
		try {
			String stringValue = getStringValue(key);

			if (stringValue == null) {
				throw new BadRequestException(
					"Missing JSON field with key {" + key + "}");
			}

			return _objectMapper.readValue(stringValue, tClass);
		}
		catch (JsonParseException jpe) {
			throw new BadRequestException(
				"Field {" + key + "} is not a JSON", jpe);
		}
		catch (InvalidFormatException ife) {
			List<JsonMappingException.Reference> references = ife.getPath();

			Stream<JsonMappingException.Reference> stream = references.stream();

			String path = stream.map(
				JsonMappingException.Reference::getFieldName
			).collect(
				Collectors.joining(".")
			);

			Class<?> targetType = ife.getTargetType();

			String message =
				"Unable to match field {" + path + "} with value {" +
					ife.getValue() + "} to " + targetType.getSimpleName() +
						" inside JSON field with key {" + key + "}";

			throw new BadRequestException(message, ife);
		}
		catch (JsonMappingException jme) {
			List<JsonMappingException.Reference> references = jme.getPath();

			Stream<JsonMappingException.Reference> stream = references.stream();

			String path = stream.map(
				JsonMappingException.Reference::getFieldName
			).collect(
				Collectors.joining(".")
			);

			String message =
				"An error occurs mapping " + path +
					" field inside JSON field with key { " + key + "}";

			throw new BadRequestException(message, jme);
		}
		catch (IOException ioe) {
			throw new InternalServerErrorException(ioe);
		}
	}

	public String getStringValue(String key) {
		return _values.get(key);
	}

	private MultipartBody(
		Map<String, BinaryFile> binaryFiles, Map<String, String> values) {

		_binaryFiles = binaryFiles;
		_values = values;
	}

	private static final ObjectMapper _objectMapper = new ObjectMapper() {
		{
			setDateFormat(new ISO8601DateFormat());
		}
	};

	private final Map<String, BinaryFile> _binaryFiles;
	private final Map<String, String> _values;

}