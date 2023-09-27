/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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