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

import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.List;

/**
 * This class represents a response of the delete Data Record Collection
 * permissions request to one or more roles.
 *
 * @author Leonardo Barros
 */
public final class DEDataRecordCollectionDeletePermissionsResponse {

	/**
	 * Returns the role names of the delete Permission response.
	 *
	 * @return a list of roleNames
	 * @review
	 */
	public List<String> getRoleNames() {
		return Collections.unmodifiableList(_roleNames);
	}

	/**
	 * Constructs the Delete Data Record Collections Permissions response.
	 * The role names list must be an argument in the response builder.
	 *
	 * @return {@link DEDataRecordCollectionDeletePermissionsResponse}
	 * @review
	 */
	public static final class Builder {

		/**
		 * Returns the Delete Data Record Collection Permissions builder.
		 *
		 * @param roleNames the role names list that that will have the revoked
		 * permission
		 * @return {@link Builder}
		 * @review
		 */
		public static Builder newBuilder(String... roleNames) {
			return new Builder(roleNames);
		}

		/**
		 * Includes the role names list in the Delete Permission response.
		 *
		 * @param roleNames the role names list that that will have the revoked
		 * permission
		 * @return {@link DEDataRecordCollectionDeletePermissionsResponse}
		 * @review
		 */
		public static DEDataRecordCollectionDeletePermissionsResponse of(
			String... roleNames) {

			return newBuilder(
				roleNames
			).build();
		}

		/**
		 * Constructs the Delete Data Record Collections Permissions response.
		 *
		 * @return {@link DEDataRecordCollectionDeletePermissionsResponse}
		 * @review
		 */
		public DEDataRecordCollectionDeletePermissionsResponse build() {
			return _deDataRecordCollectionDeletePermissionsResponse;
		}

		private Builder(String... roleNames) {
			_deDataRecordCollectionDeletePermissionsResponse._roleNames =
				ListUtil.fromArray(roleNames);
		}

		private final DEDataRecordCollectionDeletePermissionsResponse
			_deDataRecordCollectionDeletePermissionsResponse =
				new DEDataRecordCollectionDeletePermissionsResponse();

	}

	private DEDataRecordCollectionDeletePermissionsResponse() {
	}

	private List<String> _roleNames;

}