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
 * This class represents a request to remove Data Definition permission to one
 * or more roles
 *
 * @author Marcela Cunha
 */
public class DEDataDefinitionDeletePermissionsRequest {

	/**
	 * Returns the company ID of the Permission request.
	 *
	 * @return companyId
	 * @review
	 */
	public long getCompanyId() {
		return _companyId;
	}

	/**
	 * Returns the role names of the Permission request.
	 *
	 * @return a list of roleNames
	 * @review
	 */
	public List<String> getRoleNames() {
		return Collections.unmodifiableList(_roleNames);
	}

	/**
	 * Returns the scoped group ID of the Permission request.
	 *
	 * @return scopedGroupId
	 * @review
	 */
	public long getScopedGroupId() {
		return _scopedGroupId;
	}

	/**
	 * Constructs the Delete Data Definition Permissions request.
	 * The company ID, the scoped group ID, and the role names list must be an
	 * argument in the request.
	 *
	 * @return {@link DEDataDefinitionDeletePermissionsRequest}
	 * @review
	 */
	public static final class Builder {

		/**
		 * Constructs the Delete Data Definition Permission request.
		 *
		 * @param companyId the primary key of the portal instance
		 * @param scopedGroupId the primary key of the group adding the
		 * resources
		 * @param roleNames the role names list that that will have the revoked
		 * permission
		 * @return {@link Builder}
		 * @review
		 */
		public Builder(long companyId, long scopedGroupId, String[] roleNames) {
			_deDataDefinitionDeletePermissionsRequest._companyId = companyId;
			_deDataDefinitionDeletePermissionsRequest._scopedGroupId =
				scopedGroupId;
			_deDataDefinitionDeletePermissionsRequest._roleNames =
				ListUtil.fromArray(roleNames);
		}

		/**
		 * Constructs the Delete Data Definition Permission request.
		 *
		 * @return {@link DEDataDefinitionDeletePermissionsRequest}
		 * @review
		 */
		public DEDataDefinitionDeletePermissionsRequest build() {
			return _deDataDefinitionDeletePermissionsRequest;
		}

		private final DEDataDefinitionDeletePermissionsRequest
			_deDataDefinitionDeletePermissionsRequest =
				new DEDataDefinitionDeletePermissionsRequest();

	}

	private long _companyId;
	private List<String> _roleNames;
	private long _scopedGroupId;

}