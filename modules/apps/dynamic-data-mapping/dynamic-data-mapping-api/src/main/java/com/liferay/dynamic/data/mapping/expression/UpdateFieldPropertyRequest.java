/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.expression;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

	public <T> T getProperty(String name) {
		return (T)_properties.get(name);
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
	private final Map<String, Object> _properties = new HashMap<>();

}