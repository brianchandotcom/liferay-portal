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

package com.liferay.batch.engine.internal.auto.deploy;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.petra.string.CharPool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Arrays;

/**
 * @author Igor Beslic
 */
public class AdvancedJSONReader<T> {

	public AdvancedJSONReader(InputStream inputStream) {
		_inputStream = inputStream;
	}

	public T getObject(String name, Class<T> clazz) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		transferJsonObject(name, byteArrayOutputStream);

		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.readValue(
			byteArrayOutputStream.toByteArray(), clazz);
	}

	public boolean hasKey(String name) throws IOException {
		_readUntil("\"" + name + "\":");

		if (_inputStream.available() <= 0) {
			return false;
		}

		return true;
	}

	public void transferJsonArray(String name, OutputStream outputStream)
		throws IOException {

		_readUntil("\"" + name + "\"");

		_transferBlock(
			CharPool.OPEN_BRACKET, CharPool.CLOSE_BRACKET, outputStream);
	}

	public void transferJsonObject(String name, OutputStream outputStream)
		throws IOException {

		_readUntil("\"" + name + "\"");

		_transferBlock(
			CharPool.OPEN_CURLY_BRACE, CharPool.CLOSE_CURLY_BRACE,
			outputStream);
	}

	private void _readUntil(String expression) throws IOException {
		byte[] bufferTemplate = expression.getBytes();

		byte[] buffer = new byte[bufferTemplate.length];

		int read = _inputStream.read(buffer);

		while (read > -1) {
			if (Arrays.equals(bufferTemplate, buffer)) {
				break;
			}

			for (int i = 1; i < buffer.length; i++) {
				buffer[i - 1] = buffer[i];
			}

			read = _inputStream.read(buffer, buffer.length - 1, 1);
		}
	}

	private void _transferBlock(
			char opener, char closer, OutputStream outputStream)
		throws IOException {

		int read = -1;

		do {
			read = _inputStream.read();

			if (read == -1) {
				throw new IllegalStateException(
					"Unable to find character " + opener);
			}
		}
		while (read != (int)opener);

		int opened = 0;

		do {
			outputStream.write(read);

			if (read == (int)opener) {
				opened++;
			}

			if (read == (int)closer) {
				opened--;
			}

			read = _inputStream.read();
		}
		while ((opened != 0) && (read > -1));
	}

	private final InputStream _inputStream;

}