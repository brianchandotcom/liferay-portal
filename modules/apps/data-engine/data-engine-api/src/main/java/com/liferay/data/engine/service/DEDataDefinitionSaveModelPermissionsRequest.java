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
 * This class represents a request to grant Data Definition model permission
 * to one or more roles
 *
 * @author Leonardo Barros
 */
public final class DEDataDefinitionSaveModelPermissionsRequest {

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
	 * Returns the Data Definition ID of the Model Permission request.
	 *
	 * @return deDataDefinitionId
	 * @review
	 */
	public long getDEDataDefinitionId() {
		return _deDataDefinitionId;
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
	 * {@link DEDataDefinition} related to the Data Definition ID
	 * set in the request
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
	 * {@link DEDataDefinition} related to the Data Definition ID
	 * set in the request
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
	 * {@link DEDataDefinition} related to the Data Definition ID
	 * set in the request
	 *
	 * @return view
	 * @review
	 */
	public boolean isView() {
		return _view;
	}

	/**
	 * Constructs the Save Data Definition Model Permissions request.
	 * The company ID, the scoped group ID, the scoped user ID, the data
	 * definition ID, and the role names list must be an argument in the request.
	 * The permission to allow view, update, or delete a Data Definition can be
	 * used as an alternative parameter
	 *
	 * @return {@link DEDataDefinitionSaveModelPermissionsRequest}
	 * @review
	 */
	public static final class Builder {

		/**
		 * Constructs the Save Data Definition Model Permission request.
		 *
		 * @param companyId the primary key of the portal instance
		 * @param groupId the primary key of the group
		 * @param scopedUserId the primary key of the user adding the
		 * resources
		 * @param scopedGroupId the primary key of the group adding the
		 * resources
		 * @param deDataDefinitionId the primary key of the
		 * {@link DEDataDefinition} that want to related to the model permission
		 * in the request
		 * @param roleNames the role names list that are going to receive the
		 * permissions
		 * @return {@link Builder}
		 * @review
		 */
		public Builder(
			long companyId, long groupId, long scopedUserId, long scopedGroupId,
			long deDataDefinitionId, String[] roleNames) {

			_deDataDefinitionSaveModelPermissionsRequest._companyId = companyId;
			_deDataDefinitionSaveModelPermissionsRequest._groupId = groupId;
			_deDataDefinitionSaveModelPermissionsRequest._scopedUserId =
				scopedUserId;
			_deDataDefinitionSaveModelPermissionsRequest._scopedGroupId =
				scopedGroupId;
			_deDataDefinitionSaveModelPermissionsRequest._deDataDefinitionId =
				deDataDefinitionId;
			_deDataDefinitionSaveModelPermissionsRequest._roleNames =
				ListUtil.fromArray(roleNames);
		}

		/**
		 * If this method is set on the permission request, it will set the
		 * permission to allow the user to delete {@link DEDataDefinition}
		 * included in the request.
		 *
		 * @return {@link Builder}
		 * @review
		 */
		public Builder allowDelete() {
			_deDataDefinitionSaveModelPermissionsRequest._delete = true;

			return this;
		}

		/**
		 * If this method is set on the permission request, it will set the
		 * permission to allow the user to update {@link DEDataDefinition}
		 * included in the request.
		 *
		 * @return {@link Builder}
		 * @review
		 */
		public Builder allowUpdate() {
			_deDataDefinitionSaveModelPermissionsRequest._update = true;

			return this;
		}

		/**
		 * If this method is set on the permission request, it will set the
		 * permission to allow the user to view {@link DEDataDefinition}
		 * included in the request.
		 *
		 * @return {@link Builder}
		 * @review
		 */
		public Builder allowView() {
			_deDataDefinitionSaveModelPermissionsRequest._view = true;

			return this;
		}

		/**
		 * Constructs the Save Data Definition Model Permission request.
		 *
		 * @return {@link DEDataDefinitionSaveModelPermissionsRequest}
		 * @review
		 */
		public DEDataDefinitionSaveModelPermissionsRequest build() {
			return _deDataDefinitionSaveModelPermissionsRequest;
		}

		private final DEDataDefinitionSaveModelPermissionsRequest
			_deDataDefinitionSaveModelPermissionsRequest =
				new DEDataDefinitionSaveModelPermissionsRequest();

	}

	private long _companyId;
	private long _deDataDefinitionId;
	private boolean _delete;
	private long _groupId;
	private List<String> _roleNames;
	private long _scopedGroupId;
	private long _scopedUserId;
	private boolean _update;
	private boolean _view;

}