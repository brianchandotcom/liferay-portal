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

package com.liferay.dispatch.talend.web.internal.process;

import com.liferay.portal.kernel.json.JSONException;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author Matija Petanjek
 */
public class TalendProcessOutputParser {

	public TalendProcessOutputParser(byte[] resultBytes) throws JSONException {
		ByteBuffer byteBuffer = ByteBuffer.wrap(resultBytes);

		int errorLength = byteBuffer.getInt();

		int outputLength = byteBuffer.getInt();

		_exitCode = byteBuffer.getInt();

		byte[] errorBytes = new byte[errorLength];

		byteBuffer.get(errorBytes, 0, errorLength);

		_error = new String(errorBytes, StandardCharsets.UTF_8);

		byte[] outputBytes = new byte[outputLength];

		byteBuffer.get(outputBytes, 0, outputLength);

		_output = new String(outputBytes, StandardCharsets.UTF_8);
	}

	public String getError() {
		return _error;
	}

	public int getExitCode() {
		return _exitCode;
	}

	public String getOutput() {
		return _output;
	}

	public boolean hasException() {
		if (_exitCode == 0) {
			return false;
		}

		return true;
	}

	private final String _error;
	private int _exitCode;
	private final String _output;

}