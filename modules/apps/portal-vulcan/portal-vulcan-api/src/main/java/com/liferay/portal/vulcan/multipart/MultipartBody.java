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

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import java.util.Map;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;

/**
 * @author Javier Gamarra
 */
public class MultipartBody {

	public static MultipartBody of(
		Map<String, BinaryFile> binaryFiles, Map<String, String> values,
		ObjectMapperProvider objectMapperProvider) {

		return new MultipartBody(binaryFiles, values, objectMapperProvider);
	}

	public BinaryFile getBinaryFile(String key) {
		return _binaryFiles.get(key);
	}

	public <T> T getJSONObjectValue(String key, Class<T> tClass)
		throws IOException {

		String stringValue = getStringValue(key);

		if (stringValue == null) {
			throw new BadRequestException(
				"Missing JSON field with key {" + key + "}");
		}

		ObjectMapper objectMapper = _objectMapperProvider.provide(tClass);

		if (objectMapper == null) {
			throw new InternalServerErrorException(
				"Unable to find ObjectMapper for class " + tClass);
		}

		return objectMapper.readValue(stringValue, tClass);
	}

	public String getStringValue(String key) {
		return _values.get(key);
	}

	public interface ObjectMapperProvider {

		public ObjectMapper provide(Class<?> clazz);

	}

	private MultipartBody(
		Map<String, BinaryFile> binaryFiles, Map<String, String> values,
		ObjectMapperProvider objectMapperProvider) {

		_binaryFiles = binaryFiles;
		_values = values;
		_objectMapperProvider = objectMapperProvider;
	}

	private final Map<String, BinaryFile> _binaryFiles;
	private final ObjectMapperProvider _objectMapperProvider;
	private final Map<String, String> _values;

}