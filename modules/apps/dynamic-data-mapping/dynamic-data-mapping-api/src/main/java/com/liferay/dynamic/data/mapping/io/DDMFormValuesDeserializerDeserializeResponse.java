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

package com.liferay.dynamic.data.mapping.io;

import com.liferay.dynamic.data.mapping.storage.DDMFormValues;

/**
 * @author Leonardo Barros
 */
public final class DDMFormValuesDeserializerDeserializeResponse {

	public DDMFormValues getDDMFormValues() {
		return _ddmFormValues;
	}

	public static class Builder {

		public static Builder newBuilder(DDMFormValues ddmFormValues) {
			return new Builder(ddmFormValues);
		}

		public DDMFormValuesDeserializerDeserializeResponse build() {
			return _ddmFormValuesDeserializerDeserializeResponse;
		}

		private Builder(DDMFormValues ddmFormValues) {
			_ddmFormValuesDeserializerDeserializeResponse._ddmFormValues =
				ddmFormValues;
		}

		private final DDMFormValuesDeserializerDeserializeResponse
			_ddmFormValuesDeserializerDeserializeResponse =
				new DDMFormValuesDeserializerDeserializeResponse();

	}

	private DDMFormValuesDeserializerDeserializeResponse() {
	}

	private DDMFormValues _ddmFormValues;

}