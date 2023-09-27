/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.io;

import com.liferay.dynamic.data.mapping.model.DDMForm;

/**
 * @author Leonardo Barros
 */
public final class DDMFormValuesDeserializerDeserializeRequest {

	public String getContent() {
		return _content;
	}

	public DDMForm getDDMForm() {
		return _ddmForm;
	}

	public static class Builder {

		public static Builder newBuilder(String content, DDMForm ddmForm) {
			return new Builder(content, ddmForm);
		}

		public DDMFormValuesDeserializerDeserializeRequest build() {
			return _ddmFormValuesDeserializerDeserializeRequest;
		}

		private Builder(String content, DDMForm ddmForm) {
			_ddmFormValuesDeserializerDeserializeRequest._content = content;
			_ddmFormValuesDeserializerDeserializeRequest._ddmForm = ddmForm;
		}

		private final DDMFormValuesDeserializerDeserializeRequest
			_ddmFormValuesDeserializerDeserializeRequest =
				new DDMFormValuesDeserializerDeserializeRequest();

	}

	private DDMFormValuesDeserializerDeserializeRequest() {
	}

	private String _content;
	private DDMForm _ddmForm;

}