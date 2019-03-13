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
 * This class represents a request to remove Data Layout model permission
 * to one or more roles
 *
 * @author Marcela Cunha
 */
public class DEDataLayoutDeleteModelPermissionsRequest {

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
	 * Returns true or false to inform if one of the permissions to revoke is
	 * the one that allows the user to delete the
	 * {@link DEDataLayout} related to the Data Layout ID
	 * set in the request
	 *
	 * @return revokeDelete
	 * @review
	 */
	public boolean isRevokeDelete() {
		return _revokeDelete;
	}

	/**
	 * Returns true or false to inform if one of the permissions to revoke is
	 * the one that allows the user to update the
	 * {@link DEDataLayout} related to the Data Layout ID
	 * set in the request
	 *
	 * @return revokeUpdate
	 * @review
	 */
	public boolean isRevokeUpdate() {
		return _revokeUpdate;
	}

	/**
	 * Returns true or false to inform if one of the permissions to revoke is
	 * the one that allows the user to view the
	 * {@link DEDataLayout} related to the Data Layout ID
	 * set in the request
	 *
	 * @return revokeView
	 * @review
	 */
	public boolean isRevokeView() {
		return _revokeView;
	}

	/**
	 * Constructs the Delete Data Layout Model Permissions request.
	 * The company ID, the scoped group ID, the data record collection ID,
	 * the revoke delete/update/view permission and the role names list must
	 * be an argument in the request.
	 *
	 * @return {@link DEDataLayoutDeleteModelPermissionsRequest}
	 * @review
	 */
	public static final class Builder {

		/**
		 * Constructs the Delete Data Definition Model Permission request.
		 *
		 * @param companyId the primary key of the portal instance
		 * @param scopedGroupId the primary key of the group adding the
		 * resources
		 * @param deDataLayoutId the primary key of the data definition
		 * that want to related to the model permission in the request
		 * @param revokeDelete
		 * @param revokeUpdate
		 * @param revokeView
		 * @param roleNames the role names list that that will have the revoked
		 * permission
		 * @return {@link Builder}
		 * @review
		 */
		public Builder(
			long companyId, long scopedGroupId, long deDataLayoutId,
			boolean revokeDelete, boolean revokeUpdate, boolean revokeView,
			String[] roleNames) {

			_deDataLayoutDeleteModelPermissionsRequest._companyId = companyId;
			_deDataLayoutDeleteModelPermissionsRequest._scopedGroupId =
				scopedGroupId;
			_deDataLayoutDeleteModelPermissionsRequest._deDataLayoutId =
				deDataLayoutId;
			_deDataLayoutDeleteModelPermissionsRequest._revokeDelete =
				revokeDelete;
			_deDataLayoutDeleteModelPermissionsRequest._revokeUpdate =
				revokeUpdate;
			_deDataLayoutDeleteModelPermissionsRequest._revokeView = revokeView;
			_deDataLayoutDeleteModelPermissionsRequest._roleNames =
				ListUtil.fromArray(roleNames);
		}

		/**
		 * Constructs the Delete Data Definition Model Permission request.
		 *
		 * @return {@link DEDataLayoutDeleteModelPermissionsRequest}
		 * @review
		 */
		public DEDataLayoutDeleteModelPermissionsRequest build() {
			return _deDataLayoutDeleteModelPermissionsRequest;
		}

		private final DEDataLayoutDeleteModelPermissionsRequest
			_deDataLayoutDeleteModelPermissionsRequest =
				new DEDataLayoutDeleteModelPermissionsRequest();

	}

	private long _companyId;
	private long _deDataLayoutId;
	private boolean _revokeDelete;
	private boolean _revokeUpdate;
	private boolean _revokeView;
	private List<String> _roleNames;
	private long _scopedGroupId;

}