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
 * This class represents a response of the delete Data Record Collection model
 * permissions request to one or more roles.
 *
 * @author Leonardo Barros
 */
public final class DEDataRecordCollectionDeleteModelPermissionsResponse {

	/**
	 * Returns the Data Record Collection ID of the delete model permissions
	 * response.
	 *
	 * @return deDataRecordCollectionId
	 * @review
	 */
	public long getDEDataRecordCollectionId() {
		return _deDataRecordCollectionId;
	}

	/**
	 * Constructs the Delete Data Record Collections Model Permissions response.
	 * The Data Record Collection ID must be an argument in the response
	 * builder.
	 *
	 * @return {@link DEDataRecordCollectionDeleteModelPermissionsResponse}
	 * @review
	 */
	public static final class Builder {

		/**
		 * Returns the Delete Data Record Collection Model Permissions builder
		 *
		 * @param deDataRecordCollectionId the primary key of the Data Record
		 * Collection
		 * @return {@link Builder}
		 * @review
		 */
		public static Builder newBuilder(long deDataRecordCollectionId) {
			return new Builder(deDataRecordCollectionId);
		}

		/**
		 * Includes a data record collection ID in the Delete Model Permission
		 * response.
		 *
		 * @param deDataRecordCollectionId the primary key of the Data Record
		 * Collection
		 * @return {@link DEDataRecordCollectionDeleteModelPermissionsResponse}
		 * @review
		 */
		public static DEDataRecordCollectionDeleteModelPermissionsResponse of(
			long deDataRecordCollectionId) {

			return newBuilder(
				deDataRecordCollectionId
			).build();
		}

		/**
		 * Constructs the Delete Data Record Collections Model Permissions
		 * response.
		 *
		 * @return {@link DEDataRecordCollectionDeleteModelPermissionsResponse}
		 * @review
		 */
		public DEDataRecordCollectionDeleteModelPermissionsResponse build() {
			return _deDataRecordCollectionDeleteModelPermissionsResponse;
		}

		private Builder(long deDataRecordCollectionId) {
			_deDataRecordCollectionDeleteModelPermissionsResponse.
				_deDataRecordCollectionId = deDataRecordCollectionId;
		}

		private final DEDataRecordCollectionDeleteModelPermissionsResponse
			_deDataRecordCollectionDeleteModelPermissionsResponse =
				new DEDataRecordCollectionDeleteModelPermissionsResponse();

	}

	private DEDataRecordCollectionDeleteModelPermissionsResponse() {
	}

	private long _deDataRecordCollectionId;

}