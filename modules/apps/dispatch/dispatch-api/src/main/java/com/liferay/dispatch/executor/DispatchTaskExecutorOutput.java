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

package com.liferay.dispatch.executor;

import java.nio.charset.StandardCharsets;

/**
 * @author Matija Petanjek
 */
public class DispatchTaskExecutorOutput {

	public String getError() {
		return _error;
	}

	public String getOutput() {
		return _output;
	}

	public void setError(byte[] error) {
		_error = new String(error, StandardCharsets.UTF_8);
	}

	public void setError(String error) {
		_error = error;
	}

	public void setOutput(byte[] output) {
		_output = new String(output, StandardCharsets.UTF_8);
	}

	public void setOutput(String output) {
		_output = output;
	}

	private String _error;
	private String _output;

}