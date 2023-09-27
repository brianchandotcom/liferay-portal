/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.expression;

/**
 * @author Leonardo Barros
 */
public final class GetFieldPropertyResponse {

	public Object getValue() {
		return _value;
	}

	public static class Builder {

		public static Builder newBuilder(Object value) {
			return new Builder(value);
		}

		public GetFieldPropertyResponse build() {
			return _getFieldPropertyResponse;
		}

		private Builder(Object value) {
			_getFieldPropertyResponse._value = value;
		}

		private final GetFieldPropertyResponse _getFieldPropertyResponse =
			new GetFieldPropertyResponse();

	}

	private GetFieldPropertyResponse() {
	}

	private Object _value;

}