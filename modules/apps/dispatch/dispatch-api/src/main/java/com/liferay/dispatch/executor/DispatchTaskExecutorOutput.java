/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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