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
 * This class represents a response of the save Data Definition model
 * permissions request to a user
 *
 * @author Leonardo Barros
 * @review
 */
public final class DEDataDefinitionSaveModelPermissionsResponse {

	/**
	 * Returns the Data Definition ID of the save model permissions response.
	 *
	 * @return deDataDefinitionId
	 * @review
	 */
	public long getDEDataDefinitionId() {
		return _deDataDefinitionId;
	}

	/**
	 * Constructs the Save Data Definition Model Permissions response.
	 * The Data Definition ID must be an argument in the response
	 * builder.
	 *
	 * @return {@link DEDataDefinitionSaveModelPermissionsResponse}
	 * @review
	 */
	public static final class Builder {

		/**
		 * Returns the Save Data Definition Model Permissions builder
		 *
		 * @param deDataDefinitionId the primary key of the
		 * {@link DEDataDefinition}
		 * @return {@link Builder}
		 * @review
		 */
		public static Builder newBuilder(long deDataDefinitionId) {
			return new Builder(deDataDefinitionId);
		}

		/**
		 * Includes a data definition ID in the Save Model Permission response.
		 *
		 * @param deDataDefinitionId the primary key of the
		 * {@link DEDataDefinition}
		 * @return {@link DEDataDefinitionSaveModelPermissionsResponse}
		 * @review
		 */
		public static DEDataDefinitionSaveModelPermissionsResponse of(
			long deDataDefinitionId) {

			return newBuilder(
				deDataDefinitionId
			).build();
		}

		/**
		 * Constructs the Save Data Definition Model Permissions response.
		 *
		 * @return {@link DEDataDefinitionSaveModelPermissionsResponse}
		 * @review
		 */
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