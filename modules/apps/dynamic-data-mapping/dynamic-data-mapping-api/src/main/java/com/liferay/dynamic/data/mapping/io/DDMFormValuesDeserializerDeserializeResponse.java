/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public Exception getException() {
		return _exception;
	}

	public static class Builder {

		public static Builder newBuilder(DDMFormValues ddmFormValues) {
			return new Builder(ddmFormValues);
		}

		public DDMFormValuesDeserializerDeserializeResponse build() {
			return _ddmFormValuesDeserializerDeserializeResponse;
		}

		public Builder exception(Exception exception) {
			_ddmFormValuesDeserializerDeserializeResponse._exception =
				exception;

			return this;
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
	private Exception _exception;

}