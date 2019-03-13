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
 * This class represents a response of the delete Data Layout model
 * permissions request to one or more roles.
 *
 * @author Marcela Cunha
 */
public class DEDataLayoutDeleteModelPermissionsResponse {

	/**
	 * Returns the Data Layout ID of the delete model permissions response.
	 *
	 * @return deDataLayoutId
	 * @review
	 */
	public long getDEDataLayoutId() {
		return _deDataLayoutId;
	}

	/**
	 * Constructs the Delete Data Layout Model Permissions response.
	 * The Data Layout ID must be an argument in the response builder.
	 *
	 * @return {@link DEDataLayoutDeleteModelPermissionsResponse}
	 * @review
	 */
	public static final class Builder {

		/**
		 * Returns the Delete Data Layout Model Permissions builder
		 *
		 * @param deDataLayoutId the primary key of the Data Layout
		 * @return {@link Builder}
		 * @review
		 */
		public static Builder newBuilder(long deDataLayoutId) {
			return new Builder(deDataLayoutId);
		}

		/**
		 * Includes a data layout ID in the Delete Model Permission
		 * response.
		 *
		 * @param deDataLayoutId the primary key of the Data Layout
		 * @return {@link DEDataLayoutDeleteModelPermissionsResponse}
		 * @review
		 */
		public static DEDataLayoutDeleteModelPermissionsResponse of(
			long deDataLayoutId) {

			return newBuilder(
				deDataLayoutId
			).build();
		}

		/**
		 * Constructs the Delete Data Layout Model Permissions response.
		 *
		 * @return {@link DEDataLayoutDeleteModelPermissionsResponse}
		 * @review
		 */
		public DEDataLayoutDeleteModelPermissionsResponse build() {
			return _deDataLayoutDeleteModelPermissionsResponse;
		}

		private Builder(long deDataLayoutId) {
			_deDataLayoutDeleteModelPermissionsResponse._deDataLayoutId =
				deDataLayoutId;
		}

		private final DEDataLayoutDeleteModelPermissionsResponse
			_deDataLayoutDeleteModelPermissionsResponse =
				new DEDataLayoutDeleteModelPermissionsResponse();

	}

	private DEDataLayoutDeleteModelPermissionsResponse() {
	}

	private long _deDataLayoutId;

}