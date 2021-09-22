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

package com.liferay.dynamic.data.mapping.data.provider;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Leonardo Barros
 */
public final class DDMDataProviderResponse implements Serializable {

	public <T> Optional<T> getOutputOptional(String name, Class<?> clazz) {
		Object value = _ddmDataProviderResponseOutputs.get(name);

		if (value == null) {
			return Optional.empty();
		}

		Class<?> valueClass = value.getClass();

		if (clazz.isAssignableFrom(valueClass)) {
			return Optional.of((T)value);
		}

		return Optional.empty();
	}

	public DDMDataProviderResponseStatus getStatus() {
		return _ddmDataProviderResponseStatus;
	}

	public boolean hasOutput(String output) {
		return _ddmDataProviderResponseOutputs.containsKey(output);
	}

	public int size() {
		return _ddmDataProviderResponseOutputs.size();
	}

	public static class Builder {

		public static Builder newBuilder() {
			return new Builder();
		}

		public DDMDataProviderResponse build() {
			return _ddmDataProviderResponse;
		}

		public Builder withOutput(String name, Object value) {
			_ddmDataProviderResponse._ddmDataProviderResponseOutputs.put(
				name, value);

			return this;
		}

		public Builder withStatus(
			DDMDataProviderResponseStatus ddmDataProviderResponseStatus) {

			_ddmDataProviderResponse._ddmDataProviderResponseStatus =
				ddmDataProviderResponseStatus;

			return this;
		}

		private Builder() {
		}

		private DDMDataProviderResponse _ddmDataProviderResponse =
			new DDMDataProviderResponse();

	}

	private DDMDataProviderResponse() {
	}

	private final Map<String, Object> _ddmDataProviderResponseOutputs =
		new HashMap<>();
	private DDMDataProviderResponseStatus _ddmDataProviderResponseStatus =
		DDMDataProviderResponseStatus.OK;

}