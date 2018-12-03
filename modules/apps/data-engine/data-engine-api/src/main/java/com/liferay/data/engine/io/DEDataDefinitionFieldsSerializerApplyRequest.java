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

package com.liferay.data.engine.io;

import com.liferay.data.engine.model.DEDataDefinitionField;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Leonardo Barros
 */
public final class DEDataDefinitionFieldsSerializerApplyRequest {

	public List<DEDataDefinitionField> getDEDataDefinitionFields() {
		return Collections.unmodifiableList(_deDataDefinitionFields);
	}

	public static final class Builder {

		public static Builder newBuilder(
			List<DEDataDefinitionField> deDataDefinitionFields) {

			return new Builder(deDataDefinitionFields);
		}

		public static DEDataDefinitionFieldsSerializerApplyRequest of(
			List<DEDataDefinitionField> deDataDefinitionFields) {

			return newBuilder(
				deDataDefinitionFields
			).build();
		}

		public DEDataDefinitionFieldsSerializerApplyRequest build() {
			return _deDataDefinitionSerializerApplyRequest;
		}

		private Builder(List<DEDataDefinitionField> deDataDefinitionFields) {
			_deDataDefinitionSerializerApplyRequest._deDataDefinitionFields.
				addAll(deDataDefinitionFields);
		}

		private DEDataDefinitionFieldsSerializerApplyRequest
			_deDataDefinitionSerializerApplyRequest =
				new DEDataDefinitionFieldsSerializerApplyRequest();

	}

	private DEDataDefinitionFieldsSerializerApplyRequest() {
	}

	private List<DEDataDefinitionField> _deDataDefinitionFields =
		new ArrayList<>();

}