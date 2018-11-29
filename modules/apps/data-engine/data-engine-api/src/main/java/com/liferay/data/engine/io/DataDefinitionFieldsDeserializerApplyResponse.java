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

import com.liferay.data.engine.model.DataDefinitionField;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Leonardo Barros
 */
public final class DataDefinitionFieldsDeserializerApplyResponse {

	public List<DataDefinitionField> getDataDefinitionFields() {
		return Collections.unmodifiableList(_dataDefinitionFields);
	}

	public static final class Builder {

		public static Builder newBuilder(
			List<DataDefinitionField> dataDefinitionFields) {

			return new Builder(dataDefinitionFields);
		}

		public static DataDefinitionFieldsDeserializerApplyResponse of(
			List<DataDefinitionField> dataDefinitionFields) {

			return newBuilder(
				dataDefinitionFields
			).build();
		}

		public DataDefinitionFieldsDeserializerApplyResponse build() {
			return _dataDefinitionFieldsDeserializerApplyResponse;
		}

		private Builder(List<DataDefinitionField> dataDefinitionFields) {
			_dataDefinitionFieldsDeserializerApplyResponse.
				_dataDefinitionFields.addAll(dataDefinitionFields);
		}

		private final DataDefinitionFieldsDeserializerApplyResponse
			_dataDefinitionFieldsDeserializerApplyResponse =
				new DataDefinitionFieldsDeserializerApplyResponse();

	}

	private DataDefinitionFieldsDeserializerApplyResponse() {
	}

	private List<DataDefinitionField> _dataDefinitionFields = new ArrayList<>();

}