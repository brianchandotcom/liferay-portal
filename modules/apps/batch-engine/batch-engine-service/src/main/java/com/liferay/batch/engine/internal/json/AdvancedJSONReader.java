/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.json;

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

		transferJSONObject(name, byteArrayOutputStream);

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

	public void transferJSONArray(String name, OutputStream outputStream)
		throws IOException {

		_readUntil("\"" + name + "\"");

		_transferBlock(
			CharPool.OPEN_BRACKET, CharPool.CLOSE_BRACKET, outputStream);
	}

	public void transferJSONObject(String name, OutputStream outputStream)
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