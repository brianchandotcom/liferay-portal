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
public final class DEDataDefinitionSaveModelPermissionsResponse {

	public long getDEDataDefinitionId() {
		return _deDataDefinitionId;
	}

	public static final class Builder {

		public static Builder newBuilder(long deDataDefinitionId) {
			return new Builder(deDataDefinitionId);
		}

		public static DEDataDefinitionSaveModelPermissionsResponse of(
			long deDataDefinitionId) {

			return newBuilder(
				deDataDefinitionId
			).build();
		}

		public DEDataDefinitionSaveModelPermissionsResponse build() {
			return _deDataDefinitionSaveModelPermissionsResponse;
		}

		private Builder(long deDataDefinitionId) {
			_deDataDefinitionSaveModelPermissionsResponse._deDataDefinitionId =
				deDataDefinitionId;
		}

		private final DEDataDefinitionSaveModelPermissionsResponse
			_deDataDefinitionSaveModelPermissionsResponse =
				new DEDataDefinitionSaveModelPermissionsResponse();

	}

	private DEDataDefinitionSaveModelPermissionsResponse() {
	}

	private long _deDataDefinitionId;

}