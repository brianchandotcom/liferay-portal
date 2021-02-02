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

import java.io.Serializable;

import java.nio.charset.StandardCharsets;

/**
 * @author Matija Petanjek
 */
public class TalendProcessOutput implements Serializable {

	public TalendProcessOutput(byte[] errBytes, int exitCode, byte[] outBytes) {
		_error = new String(errBytes, StandardCharsets.UTF_8);

		_exitCode = exitCode;

		_output = new String(outBytes, StandardCharsets.UTF_8);
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

	private static final long serialVersionUID = 1L;

	private final String _error;
	private int _exitCode;
	private final String _output;

}