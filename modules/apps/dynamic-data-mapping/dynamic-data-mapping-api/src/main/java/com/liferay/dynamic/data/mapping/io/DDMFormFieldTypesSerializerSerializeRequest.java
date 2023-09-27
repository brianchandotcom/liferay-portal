/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.io;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;

import java.util.List;

/**
 * @author Leonardo Barros
 */
public final class DDMFormFieldTypesSerializerSerializeRequest {

	public List<DDMFormFieldType> getDdmFormFieldTypes() {
		return _ddmFormFieldTypes;
	}

	public static class Builder {

		public static Builder newBuilder(
			List<DDMFormFieldType> ddmFormFieldTypes) {

			return new Builder(ddmFormFieldTypes);
		}

		public DDMFormFieldTypesSerializerSerializeRequest build() {
			return _ddmFormFieldTypesSerializerSerializeRequest;
		}

		private Builder(List<DDMFormFieldType> ddmFormFieldTypes) {
			_ddmFormFieldTypesSerializerSerializeRequest._ddmFormFieldTypes =
				ddmFormFieldTypes;
		}

		private final DDMFormFieldTypesSerializerSerializeRequest
			_ddmFormFieldTypesSerializerSerializeRequest =
				new DDMFormFieldTypesSerializerSerializeRequest();

	}

	private DDMFormFieldTypesSerializerSerializeRequest() {
	}

	private List<DDMFormFieldType> _ddmFormFieldTypes;

}