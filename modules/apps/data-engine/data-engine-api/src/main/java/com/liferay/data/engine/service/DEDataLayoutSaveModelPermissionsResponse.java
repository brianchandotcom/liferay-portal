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
 * This class represents a response of the save Data Layout model
 * permissions request to a user
 *
 * @author Marcela Cunha
 * @review
 */
public class DEDataLayoutSaveModelPermissionsResponse {

	/**
	 * Returns the Data Layout ID of the save model permissions response.
	 *
	 * @return deDataLayoutId
	 * @review
	 */
	public long getDEDataLayoutId() {
		return _deDataLayoutId;
	}

	/**
	 * Constructs the Save Data Layout Model Permissions response.
	 * The Data Layout ID must be an argument in the response
	 * builder.
	 *
	 * @review
	 */
	public static final class Builder {

		/**
		 * Returns the Save Data Layout Model Permissions builder
		 *
		 * @param deDataLayoutId the primary key of the
		 * {@link DEDataDLayout}
		 * @return {@link Builder}
		 * @review
		 */
		public static Builder newBuilder(long deDataLayoutId) {
			return new Builder(deDataLayoutId);
		}

		/**
		 * Includes a data layout ID in the Save Model Permission response.
		 *
		 * @param deDataLayoutId the primary key of the
		 * {@link DEDataLayout}
		 * @return {@link DEDataLayoutSaveModelPermissionsResponse}
		 * @review
		 */
		public static DEDataLayoutSaveModelPermissionsResponse of(
			long deDataLayoutId) {

			return newBuilder(
				deDataLayoutId
			).build();
		}

		/**
		 * Constructs the Save Data Layout Model Permissions response.
		 *
		 * @return {@link DEDataLayoutSaveModelPermissionsResponse}
		 * @review
		 */
		public DEDataLayoutSaveModelPermissionsResponse build() {
			return _deDataLayoutSaveModelPermissionsResponse;
		}

		private Builder(long deDataLayoutId) {
			_deDataLayoutSaveModelPermissionsResponse._deDataLayoutId =
				deDataLayoutId;
		}

		private final DEDataLayoutSaveModelPermissionsResponse
			_deDataLayoutSaveModelPermissionsResponse =
				new DEDataLayoutSaveModelPermissionsResponse();

	}

	private DEDataLayoutSaveModelPermissionsResponse() {
	}

	private long _deDataLayoutId;

}