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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Leonardo Barros
 */
public final class UpdateFieldPropertyRequest {

	public String getField() {
		return _field;
	}

	public String getInstanceId() {
		return _instanceId;
	}

	public Map<String, Object> getProperties() {
		return Collections.unmodifiableMap(_properties);
	}

	public <T> Optional<T> getPropertyOptional(String name) {
		if (!_properties.containsKey(name)) {
			return Optional.empty();
		}

		return Optional.of((T)_properties.get(name));
	}

	public static class Builder {

		public static Builder newBuilder(
			String field, String property, Object value) {

			return new Builder(field, property, value);
		}

		public UpdateFieldPropertyRequest build() {
			return _updateFieldPropertyRequest;
		}

		public Builder withInstanceId(String instanceId) {
			_updateFieldPropertyRequest._instanceId = instanceId;

			return this;
		}

		public Builder withParameter(String name, Object value) {
			_updateFieldPropertyRequest._properties.put(name, value);

			return this;
		}

		private Builder(String field, String property, Object value) {
			_updateFieldPropertyRequest._field = field;
			_updateFieldPropertyRequest._properties.put(property, value);
		}

		private final UpdateFieldPropertyRequest _updateFieldPropertyRequest =
			new UpdateFieldPropertyRequest();

	}

	private UpdateFieldPropertyRequest() {
	}

	private String _field;
	private String _instanceId;
	private Map<String, Object> _properties = new HashMap<>();

}