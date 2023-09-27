/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.io;

import com.liferay.dynamic.data.mapping.model.DDMFormLayout;

/**
 * @author Leonardo Barros
 */
public final class DDMFormLayoutDeserializerDeserializeResponse {

	public DDMFormLayout getDDMFormLayout() {
		return _ddmFormLayout;
	}

	public Exception getException() {
		return _exception;
	}

	public static class Builder {

		public static Builder newBuilder(DDMFormLayout ddmFormLayout) {
			return new Builder(ddmFormLayout);
		}

		public DDMFormLayoutDeserializerDeserializeResponse build() {
			return _ddmFormLayoutDeserializerDeserializeResponse;
		}

		public Builder exception(Exception exception) {
			_ddmFormLayoutDeserializerDeserializeResponse._exception =
				exception;

			return this;
		}

		private Builder(DDMFormLayout ddmFormLayout) {
			_ddmFormLayoutDeserializerDeserializeResponse._ddmFormLayout =
				ddmFormLayout;
		}

		private final DDMFormLayoutDeserializerDeserializeResponse
			_ddmFormLayoutDeserializerDeserializeResponse =
				new DDMFormLayoutDeserializerDeserializeResponse();

	}

	private DDMFormLayoutDeserializerDeserializeResponse() {
	}

	private DDMFormLayout _ddmFormLayout;
	private Exception _exception;

}