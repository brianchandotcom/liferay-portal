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

package com.liferay.dynamic.data.mapping.expression;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Leonardo Barros
 */
public final class ExecuteActionRequest {

	public String getAction() {
		return _action;
	}

	public <T> Optional<T> getParameterOptional(String name) {
		if (!_parameters.containsKey(name)) {
			return Optional.empty();
		}

		return Optional.of((T)_parameters.get(name));
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
	private Map<String, Object> _parameters = new HashMap<>();

}