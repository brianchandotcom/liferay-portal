/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.expression;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public final class ExecuteActionResponse {

	public <T> T getOutput(String name) {
		return (T)_output.get(name);
	}

	public static class Builder {

		public static Builder newBuilder(boolean success) {
			return new Builder(success);
		}

		public ExecuteActionResponse build() {
			return _executeActionResponse;
		}

		public Builder withOutput(String name, Object value) {
			_executeActionResponse._output.put(name, value);

			return this;
		}

		private Builder(boolean success) {
			_executeActionResponse._success = success;
		}

		private final ExecuteActionResponse _executeActionResponse =
			new ExecuteActionResponse();

	}

	private ExecuteActionResponse() {
	}

	private final Map<String, Object> _output = new HashMap<>();
	private boolean _success;

}