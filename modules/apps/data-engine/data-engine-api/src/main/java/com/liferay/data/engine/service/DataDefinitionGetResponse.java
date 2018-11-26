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

package com.liferay.data.engine.service;

import com.liferay.data.engine.model.DataDefinition;

/**
 * @author Leonardo Barros
 */
public final class DataDefinitionGetResponse {

	public DataDefinition getDataDefinition() {
		return _dataDefinition;
	}

	public static final class Builder {

		public static Builder newBuilder(DataDefinition dataDefinition) {
			return new Builder(dataDefinition);
		}

		public static DataDefinitionGetResponse of(
			DataDefinition dataDefinition) {

			return newBuilder(
				dataDefinition
			).build();
		}

		public DataDefinitionGetResponse build() {
			return _dataDefinitionGetResponse;
		}

		private Builder(DataDefinition dataDefinition) {
			_dataDefinitionGetResponse._dataDefinition = dataDefinition;
		}

		private final DataDefinitionGetResponse _dataDefinitionGetResponse =
			new DataDefinitionGetResponse();

	}

	private DataDefinitionGetResponse() {
	}

	private DataDefinition _dataDefinition;

}