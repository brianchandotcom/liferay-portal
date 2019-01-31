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
 * This class represents a response of the delete Data Definition model
 * permissions request to one or more roles.
 *
 * @author Marcela Cunha
 */
public class DEDataDefinitionDeleteModelPermissionsResponse {

	/**
	 * Returns the Data Definition ID of the delete model permissions response.
	 *
	 * @return deDataDefinitionId
	 * @review
	 */
	public long getDEDataDefinitionId() {
		return _deDataDefinitionId;
	}

	/**
	 * Constructs the Delete Data Definition Model Permissions response.
	 * The Data Definition ID must be an argument in the response builder.
	 *
	 * @return {@link DEDataDefinitionDeleteModelPermissionsResponse}
	 * @review
	 */
	public static final class Builder {

		/**
		 * Returns the Delete Data Definition Model Permissions builder
		 *
		 * @param deDataDefinitionId the primary key of the Data Definition
		 * @return {@link Builder}
		 * @review
		 */
		public static Builder newBuilder(long deDataDefinitionId) {
			return new Builder(deDataDefinitionId);
		}

		/**
		 * Includes a data definition ID in the Delete Model Permission
		 * response.
		 *
		 * @param deDataDefinitionId the primary key of the Data Definition
		 * @return {@link DEDataDefinitionDeleteModelPermissionsResponse}
		 * @review
		 */
		public static DEDataDefinitionDeleteModelPermissionsResponse of(
			long deDataDefinitionId) {

			return newBuilder(
				deDataDefinitionId
			).build();
		}

		/**
		 * Constructs the Delete Data Definition Model Permissions response.
		 *
		 * @return {@link DEDataDefinitionDeleteModelPermissionsResponse}
		 * @review
		 */
		public DEDataDefinitionDeleteModelPermissionsResponse build() {
			return _deDataDefinitionDeleteModelPermissionsResponse;
		}

		private Builder(long deDataDefinitionId) {
			_deDataDefinitionDeleteModelPermissionsResponse.
				_deDataDefinitionId = deDataDefinitionId;
		}

		private final DEDataDefinitionDeleteModelPermissionsResponse
			_deDataDefinitionDeleteModelPermissionsResponse =
				new DEDataDefinitionDeleteModelPermissionsResponse();

	}

	private DEDataDefinitionDeleteModelPermissionsResponse() {
	}

	private long _deDataDefinitionId;

}