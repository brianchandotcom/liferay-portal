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
 * This class represents a response of the save Data Layout permission
 * request to one or more roles
 *
 * @author Marcela Cunha
 */
public class DEDataLayoutSavePermissionsResponse {

	/**
	 * Returns the role names list of the Permission response.
	 *
	 * @return a list of roleNames
	 * @review
	 */
	public List<String> getRoleNames() {
		return Collections.unmodifiableList(_roleNames);
	}

	/**
	 * Constructs the Save Data Layout Permissions response.
	 * The role names must be an argument in the response builder.
	 *
	 * @return {@link DEDataLayoutSavePermissionsResponse}
	 * @review
	 */
	public static final class Builder {

		/**
		 * Returns the Save Data Layout Permissions builder.
		 *
		 * @param roleNames the list of the roles names that have been granted
		 * permission in the request
		 * @return {@link Builder}
		 * @review
		 */
		public static Builder newBuilder(String... roleNames) {
			return new Builder(roleNames);
		}

		/**
		 * Includes a role names list in the Save Permission response.
		 *
		 * @param roleNames the role names that are going to receive the
		 * permissions
		 * @return {@link DEDataLayoutSavePermissionsResponse}
		 * @review
		 */
		public static DEDataLayoutSavePermissionsResponse of(
			String... roleNames) {

			return newBuilder(
				roleNames
			).build();
		}

		/**
		 * Constructs the Save Data Layout Permissions response.
		 *
		 * @return {@link DEDataLayoutSavePermissionsResponse}
		 * @review
		 */
		public DEDataLayoutSavePermissionsResponse build() {
			return _deDataLayoutSavePermissionsResponse;
		}

		private Builder(String... roleNames) {
			_deDataLayoutSavePermissionsResponse._roleNames =
				ListUtil.fromArray(roleNames);
		}

		private final DEDataLayoutSavePermissionsResponse
			_deDataLayoutSavePermissionsResponse =
				new DEDataLayoutSavePermissionsResponse();

	}

	private DEDataLayoutSavePermissionsResponse() {
	}

	private List<String> _roleNames;

}