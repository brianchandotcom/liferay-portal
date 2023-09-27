/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.io;

import com.liferay.dynamic.data.mapping.model.DDMForm;

/**
 * @author Leonardo Barros
 */
public final class DDMFormDeserializerDeserializeResponse {

	public DDMForm getDDMForm() {
		return _ddmForm;
	}

	public Exception getException() {
		return _exception;
	}

	public static class Builder {

		public static Builder newBuilder(DDMForm ddmForm) {
			return new Builder(ddmForm);
		}

		public DDMFormDeserializerDeserializeResponse build() {
			return _ddmFormDeserializerDeserializeResponse;
		}

		public Builder exception(Exception exception) {
			_ddmFormDeserializerDeserializeResponse._exception = exception;

			return this;
		}

		private Builder(DDMForm ddmForm) {
			_ddmFormDeserializerDeserializeResponse._ddmForm = ddmForm;
		}

		private final DDMFormDeserializerDeserializeResponse
			_ddmFormDeserializerDeserializeResponse =
				new DDMFormDeserializerDeserializeResponse();

	}

	private DDMFormDeserializerDeserializeResponse() {
	}

	private DDMForm _ddmForm;
	private Exception _exception;

}