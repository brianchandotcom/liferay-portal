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
 * This class represents a request to grant Data Layout model permission
 * to one or more roles
 *
 * @author Marcela Cunha
 */
public class DEDataLayoutSaveModelPermissionsRequest {

	/**
	 * Returns the company ID of the Model Permission request.
	 *
	 * @return companyId
	 * @review
	 */
	public long getCompanyId() {
		return _companyId;
	}

	/**
	 * Returns the Data Layout ID of the Model Permission request.
	 *
	 * @return deDataLayoutId
	 * @review
	 */
	public long getDEDataLayoutId() {
		return _deDataLayoutId;
	}

	/**
	 * Returns the group ID of the Model Permission request.
	 *
	 * @return scopedGroupId
	 * @review
	 */
	public long getGroupId() {
		return _groupId;
	}

	/**
	 * Returns the role names of the Model Permission request.
	 *
	 * @return a list of roleNames
	 * @review
	 */
	public List<String> getRoleNames() {
		return Collections.unmodifiableList(_roleNames);
	}

	/**
	 * Returns the scoped group ID of the Model Permission request.
	 *
	 * @return scopedGroupId
	 * @review
	 */
	public long getScopedGroupId() {
		return _scopedGroupId;
	}

	/**
	 * Returns the scoped user ID of the Model Permission request.
	 *
	 * @return scopedUserId
	 * @review
	 */
	public long getScopedUserId() {
		return _scopedUserId;
	}

	/**
	 * Returns true or false to inform if one of the permissions to grant is
	 * the one that allows the user to delete the
	 * {@link DEDataLayout} related to the Data Layout ID set in the request
	 *
	 * @return delete
	 * @review
	 */
	public boolean isDelete() {
		return _delete;
	}

	/**
	 * Returns true or false to inform if one of the permissions to grant is
	 * the one that allows the user to update the
	 * {@link DEDataLayout} related to the Data Layout ID set in the request
	 *
	 * @return update
	 * @review
	 */
	public boolean isUpdate() {
		return _update;
	}

	/**
	 * Returns true or false to inform if one of the permissions to grant is
	 * the one that allows the user to view the
	 * {@link DEDataLayout} related to the Data Layout ID set in the request
	 *
	 * @return view
	 * @review
	 */
	public boolean isView() {
		return _view;
	}

	/**
	 * Constructs the Save Data Layout Model Permissions request.
	 * The company ID, the scoped group ID, the scoped user ID, the data
	 * layout ID, and the role names list must be an argument in the request.
	 * The permission to allow view, update, or delete a Data Layout can be
	 * used as an alternative parameter
	 *
	 * @review
	 */
	public static final class Builder {

		/**
		 * Constructs the Save Data Layout Model Permission request.
		 *
		 * @param companyId the primary key of the portal instance
		 * @param groupId the primary key of the group
		 * @param scopedUserId the primary key of the user adding the
		 * resources
		 * @param scopedGroupId the primary key of the group adding the
		 * resources
		 * @param deDataLayoutId the primary key of the
		 * {@link DEDataLayout} that want to related to the model permission
		 * in the request
		 * @param roleNames the role names list that are going to receive the
		 * permissions
		 * @return {@link Builder}
		 * @review
		 */
		public Builder(
			long companyId, long groupId, long scopedUserId, long scopedGroupId,
			long deDataLayoutId, String[] roleNames) {

			_deDataLayoutSaveModelPermissionsRequest._companyId = companyId;
			_deDataLayoutSaveModelPermissionsRequest._groupId = groupId;
			_deDataLayoutSaveModelPermissionsRequest._scopedUserId =
				scopedUserId;
			_deDataLayoutSaveModelPermissionsRequest._scopedGroupId =
				scopedGroupId;
			_deDataLayoutSaveModelPermissionsRequest._deDataLayoutId =
				deDataLayoutId;
			_deDataLayoutSaveModelPermissionsRequest._roleNames =
				ListUtil.fromArray(roleNames);
		}

		/**
		 * If this method is set on the permission request, it will set the
		 * permission to allow the user to delete {@link DEDataLayout}
		 * included in the request.
		 *
		 * @return {@link Builder}
		 * @review
		 */
		public Builder allowDelete() {
			_deDataLayoutSaveModelPermissionsRequest._delete = true;

			return this;
		}

		/**
		 * If this method is set on the permission request, it will set the
		 * permission to allow the user to update {@link DEDataLayout}
		 * included in the request.
		 *
		 * @return {@link Builder}
		 * @review
		 */
		public Builder allowUpdate() {
			_deDataLayoutSaveModelPermissionsRequest._update = true;

			return this;
		}

		/**
		 * If this method is set on the permission request, it will set the
		 * permission to allow the user to view {@link DEDataLayout}
		 * included in the request.
		 *
		 * @return {@link Builder}
		 * @review
		 */
		public Builder allowView() {
			_deDataLayoutSaveModelPermissionsRequest._view = true;

			return this;
		}

		/**
		 * Constructs the Save Data Layout Model Permission request.
		 *
		 * @return {@link DEDataLayoutSaveModelPermissionsRequest}
		 * @review
		 */
		public DEDataLayoutSaveModelPermissionsRequest build() {
			return _deDataLayoutSaveModelPermissionsRequest;
		}

		private final DEDataLayoutSaveModelPermissionsRequest
			_deDataLayoutSaveModelPermissionsRequest =
				new DEDataLayoutSaveModelPermissionsRequest();

	}

	private long _companyId;
	private long _deDataLayoutId;
	private boolean _delete;
	private long _groupId;
	private List<String> _roleNames;
	private long _scopedGroupId;
	private long _scopedUserId;
	private boolean _update;
	private boolean _view;

}