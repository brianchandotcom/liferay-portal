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

/**
 * @author Leonardo Barros
 */
public final class GetFieldPropertyRequest {

	public String getField() {
		return _field;
	}

	public String getInstanceId() {
		return _instanceId;
	}

	public String getProperty() {
		return _property;
	}

	public static class Builder {

		public static Builder newBuilder(String field, String property) {
			return new Builder(field, property);
		}

		public GetFieldPropertyRequest build() {
			return _getFieldPropertyRequest;
		}

		public Builder withInstanceId(String instanceId) {
			_getFieldPropertyRequest._instanceId = instanceId;

			return this;
		}

		private Builder(String field, String property) {
			_getFieldPropertyRequest._field = field;
			_getFieldPropertyRequest._property = property;
		}

		private final GetFieldPropertyRequest _getFieldPropertyRequest =
			new GetFieldPropertyRequest();

	}

	private GetFieldPropertyRequest() {
	}

	private String _field;
	private String _instanceId;
	private String _property;

}