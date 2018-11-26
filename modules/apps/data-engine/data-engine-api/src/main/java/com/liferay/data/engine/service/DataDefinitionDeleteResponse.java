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

/**
 * @author Leonardo Barros
 */
public final class DataDefinitionDeleteResponse {

	public long getDataDefinitionId() {
		return _dataDefinitionId;
	}

	public static final class Builder {

		public static Builder newBuilder(long dataDefinitionId) {
			return new Builder(dataDefinitionId);
		}

		public static DataDefinitionDeleteResponse of(long dataDefinitionId) {
			return newBuilder(
				dataDefinitionId
			).build();
		}

		public DataDefinitionDeleteResponse build() {
			return _dataDefinitionDeleteResponse;
		}

		private Builder(long dataDefinitionId) {
			_dataDefinitionDeleteResponse._dataDefinitionId = dataDefinitionId;
		}

		private final DataDefinitionDeleteResponse
			_dataDefinitionDeleteResponse = new DataDefinitionDeleteResponse();

	}

	private DataDefinitionDeleteResponse() {
	}

	private long _dataDefinitionId;

}