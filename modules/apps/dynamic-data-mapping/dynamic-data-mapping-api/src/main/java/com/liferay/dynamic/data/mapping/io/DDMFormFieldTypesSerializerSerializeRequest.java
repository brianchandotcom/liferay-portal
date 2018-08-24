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