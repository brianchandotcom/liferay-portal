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
 * This class represents a request to grant Data Layout permission
 * to one or more roles
 *
 * @author Marcela Cunha
 */
public class DEDataLayoutSavePermissionsRequest {

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
	 * Returns true or false to inform if one of the permissions to grant is
	 * the one that allows the user to add a {@link DEDataLayout}
	 *
	 * @return addDataLayout
	 * @review
	 */
	public boolean isAddDataLayout() {
		return _addDataLayout;
	}

	/**
	 * Returns true or false to inform if one of the permissions to grant is
	 * the one that allows the user to give permission to another user to
	 * perform actions involving {@link DEDataLayout}
	 *
	 * @return definePermissions
	 * @review
	 */
	public boolean isDefinePermissions() {
		return _definePermissions;
	}

	/**
	 * Constructs the Save Data Layout Permissions request.
	 * The company ID, the scoped group ID, and the role names must be an
	 * argument in the request builder. The permission to allow add a
	 * {@link DEDataLayout} and to define permissions to another user
	 *  can be used as an alternative parameter
	 *
	 * @review
	 */
	public static final class Builder {

		/**
		 * Constructs the Save Data Layout Permission request.
		 *
		 * @param companyId the primary key of the portal instance
		 * @param scopedGroupId the primary key of the group adding the
		 * resources
		 * @param roleNames the role names list that are going to receive the
		 * permissions
		 * @return {@link Builder}
		 * @review
		 */
		public Builder(long companyId, long scopedGroupId, String[] roleNames) {
			_deDataLayoutSavePermissionsRequest._companyId = companyId;
			_deDataLayoutSavePermissionsRequest._scopedGroupId = scopedGroupId;
			_deDataLayoutSavePermissionsRequest._roleNames = ListUtil.fromArray(
				roleNames);
		}

		/**
		 * If this method is set on the permission request, it will set the
		 * permission to allow the user to add {@link DEDataLayout}
		 *
		 * @return {@link Builder}
		 * @review
		 */
		public Builder allowAddDataLayout() {
			_deDataLayoutSavePermissionsRequest._addDataLayout = true;

			return this;
		}

		/**
		 * If this method is set on the permission request, it will allow
		 * the user to define permissions to another user to perform actions
		 * involving {@link DEDataLayout}
		 *
		 * @return {@link Builder}
		 * @review
		 */
		public Builder allowDefinePermissions() {
			_deDataLayoutSavePermissionsRequest._definePermissions = true;

			return this;
		}

		/**
		 * Constructs the Save Data Layout Permission request.
		 *
		 * @return {@link DEDataLayoutSavePermissionsRequest}
		 * @review
		 */
		public DEDataLayoutSavePermissionsRequest build() {
			return _deDataLayoutSavePermissionsRequest;
		}

		private final DEDataLayoutSavePermissionsRequest
			_deDataLayoutSavePermissionsRequest =
				new DEDataLayoutSavePermissionsRequest();

	}

	private boolean _addDataLayout;
	private long _companyId;
	private boolean _definePermissions;
	private List<String> _roleNames;
	private long _scopedGroupId;

}