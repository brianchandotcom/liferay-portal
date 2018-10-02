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