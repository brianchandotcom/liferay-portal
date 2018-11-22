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

import com.liferay.data.engine.model.DataDefinitionColumn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Leonardo Barros
 */
public final class DataDefinitionColumnsDeserializerApplyResponse {

	public List<DataDefinitionColumn> getDataDefinitionColumns() {
		return Collections.unmodifiableList(_dataDefinitionColumns);
	}

	public static final class Builder {

		public static Builder newBuilder(
			List<DataDefinitionColumn> dataDefinitionColumns) {

			return new Builder(dataDefinitionColumns);
		}

		public static DataDefinitionColumnsDeserializerApplyResponse of(
			List<DataDefinitionColumn> dataDefinitionColumns) {

			return newBuilder(
				dataDefinitionColumns
			).build();
		}

		public DataDefinitionColumnsDeserializerApplyResponse build() {
			return _dataDefinitionColumnsDeserializerApplyResponse;
		}

		private Builder(List<DataDefinitionColumn> dataDefinitionColumns) {
			_dataDefinitionColumnsDeserializerApplyResponse.
				_dataDefinitionColumns.addAll(dataDefinitionColumns);
		}

		private final DataDefinitionColumnsDeserializerApplyResponse
			_dataDefinitionColumnsDeserializerApplyResponse =
				new DataDefinitionColumnsDeserializerApplyResponse();

	}

	private DataDefinitionColumnsDeserializerApplyResponse() {
	}

	private List<DataDefinitionColumn> _dataDefinitionColumns =
		new ArrayList<>();

}