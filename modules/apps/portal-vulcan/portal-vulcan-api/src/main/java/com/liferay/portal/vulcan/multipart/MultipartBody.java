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

import com.liferay.portal.kernel.util.StreamUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.Map;
import java.util.Optional;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;

/**
 * @author Javier Gamarra
 */
public class MultipartBody {

	public static MultipartBody of(
		Map<String, BinaryFile> binaryFiles,
		ObjectMapperProvider objectMapperProvider, Map<String, String> values) {

		return new MultipartBody(binaryFiles, objectMapperProvider, values);
	}

	public BinaryFile getBinaryFile(String key) {
		return _binaryFiles.get(key);
	}

	public byte[] getBinaryFileAsBytes(String key) throws IOException {
		BinaryFile binaryFile = getBinaryFile(key);

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		StreamUtil.transfer(binaryFile.getInputStream(), byteArrayOutputStream);

		return byteArrayOutputStream.toByteArray();
	}

	public <T> T getValueAsInstance(String key, Class<T> clazz)
		throws IOException {

		String valueAsString = getValueAsString(key);

		if (valueAsString == null) {
			throw new BadRequestException(
				"Missing JSON property with the key: " + key);
		}

		return _parseValue(valueAsString, clazz);
	}

	public <T> Optional<T> getValueAsInstanceOptional(
			String key, Class<T> clazz)
		throws IOException {

		String valueAsString = getValueAsString(key);

		if (valueAsString == null) {
			return Optional.empty();
		}

		return Optional.ofNullable(_parseValue(valueAsString, clazz));
	}

	public String getValueAsString(String key) {
		if ((_values != null) && _values.containsKey(key)) {
			return _values.get(key);
		}

		return null;
	}

	public Map<String, String> getValues() {
		return _values;
	}

	public interface ObjectMapperProvider {

		public ObjectMapper provide(Class<?> clazz);

	}

	private MultipartBody(
		Map<String, BinaryFile> binaryFiles,
		ObjectMapperProvider objectMapperProvider, Map<String, String> values) {

		_binaryFiles = binaryFiles;
		_objectMapperProvider = objectMapperProvider;
		_values = values;
	}

	private <T> T _parseValue(String valueAsString, Class<T> clazz)
		throws IOException {

		ObjectMapper objectMapper = _objectMapperProvider.provide(clazz);

		if (objectMapper == null) {
			throw new InternalServerErrorException(
				"Unable to get object mapper for class " + clazz.getName());
		}

		return objectMapper.readValue(valueAsString, clazz);
	}

	private final Map<String, BinaryFile> _binaryFiles;
	private final ObjectMapperProvider _objectMapperProvider;
	private final Map<String, String> _values;

}