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
public final class ExecuteActionRequest {

	public String getAction() {
		return _action;
	}

	public <T> T getParameter(String name) {
		return (T)_parameters.get(name);
	}

	public static class Builder {

		public static Builder newBuilder(String action) {
			return new Builder(action);
		}

		public ExecuteActionRequest build() {
			return _executeActionRequest;
		}

		public Builder withParameter(String name, Object value) {
			_executeActionRequest._parameters.put(name, value);

			return this;
		}

		private Builder(String action) {
			_executeActionRequest._action = action;
		}

		private final ExecuteActionRequest _executeActionRequest =
			new ExecuteActionRequest();

	}

	private ExecuteActionRequest() {
	}

	private String _action;
	private final Map<String, Object> _parameters = new HashMap<>();

}