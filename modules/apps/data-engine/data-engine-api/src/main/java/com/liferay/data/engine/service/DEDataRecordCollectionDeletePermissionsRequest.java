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
 * This class represents a request to remove Data Record Collection permission
 * to one or more roles
 *
 * @author Leonardo Barros
 */
public final class DEDataRecordCollectionDeletePermissionsRequest {

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
	 * Constructs the Delete Data Record Collections Permissions request.
	 * The company ID, the scoped group ID, and the role names list must be an
	 * argument in the request.
	 *
	 * @return {@link DEDataRecordCollectionDeletePermissionsRequest}
	 * @review
	 */
	public static final class Builder {

		/**
		 * Constructs the Delete Data Record Collections Permission request.
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
			_deDataRecordCollectionDeletePermissionsRequest._companyId =
				companyId;
			_deDataRecordCollectionDeletePermissionsRequest._scopedGroupId =
				scopedGroupId;
			_deDataRecordCollectionDeletePermissionsRequest._roleNames =
				ListUtil.fromArray(roleNames);
		}

		/**
		 * Constructs the Delete Data Record Collections Permission request.
		 *
		 * @return {@link DEDataRecordCollectionDeletePermissionsRequest}
		 * @review
		 */
		public DEDataRecordCollectionDeletePermissionsRequest build() {
			return _deDataRecordCollectionDeletePermissionsRequest;
		}

		private final DEDataRecordCollectionDeletePermissionsRequest
			_deDataRecordCollectionDeletePermissionsRequest =
				new DEDataRecordCollectionDeletePermissionsRequest();

	}

	private long _companyId;
	private List<String> _roleNames;
	private long _scopedGroupId;

}