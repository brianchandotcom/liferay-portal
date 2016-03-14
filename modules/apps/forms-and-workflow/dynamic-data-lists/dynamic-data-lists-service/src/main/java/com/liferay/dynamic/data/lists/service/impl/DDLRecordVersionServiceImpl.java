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

package com.liferay.dynamic.data.lists.service.impl;

import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.base.DDLRecordVersionServiceBaseImpl;
import com.liferay.dynamic.data.lists.service.permission.DDLRecordPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service for accessing, adding, deleting, and updating
 * dynamic data list (DDL) record versions. Its methods include permission checks.
 *
 * @author Marcellus Tavares
 */
public class DDLRecordVersionServiceImpl
	extends DDLRecordVersionServiceBaseImpl {

	/**
	 * Returns a record version with the ID.
	 *
	 * @param  recordVersionId the primary key of the record version
	 * @return the record version with the ID
	 * @throws PortalException if the mathing record set is not found or if the user do not have the required permission to access the record set
	 */
	@Override
	public DDLRecordVersion getRecordVersion(long recordVersionId)
		throws PortalException {

		DDLRecordVersion recordVersion =
			ddlRecordVersionLocalService.getRecordVersion(recordVersionId);

		DDLRecordPermission.check(
			getPermissionChecker(), recordVersion.getRecordId(),
			ActionKeys.VIEW);

		return recordVersion;
	}

	/**
	 * Returns a record version matching the record's primary key and the version.
	 *
	 * @param  recordId the primary key of the record
	 * @param  version the record version
	 * @return the record version macthing the record primary key and version
	 * @throws PortalException if the mathing record set is not found or if the user do not have the required permission to access the record set
	 */
	@Override
	public DDLRecordVersion getRecordVersion(long recordId, String version)
		throws PortalException {

		DDLRecordPermission.check(
			getPermissionChecker(), recordId, ActionKeys.VIEW);

		return ddlRecordVersionPersistence.findByR_V(recordId, version);
	}

	/**
	 * Returns all the record versions matching the record
	 *
	 * @param  recordId the primary key of the record
	 * @return the matching record versions
	 */
	@Override
	public List<DDLRecordVersion> getRecordVersions(long recordId)
		throws PortalException {

		DDLRecordPermission.check(
			getPermissionChecker(), recordId, ActionKeys.VIEW);

		return ddlRecordVersionPersistence.findByRecordId(recordId);
	}

	/**
	 * Returns a range of all record versions matching the record.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  recordId the primary key of the record
	 * @param  start the lower bound of the range of record versions to
	 *         return
	 * @param  end the upper bound of the range of record versions to
	 *         return (not inclusive)
	 * @param  orderByComparator the comparator to order the record versions
	 * @return the range of matching record versions ordered by the comparator
	 */
	@Override
	public List<DDLRecordVersion> getRecordVersions(
			long recordId, int start, int end,
			OrderByComparator<DDLRecordVersion> orderByComparator)
		throws PortalException {

		DDLRecordPermission.check(
			getPermissionChecker(), recordId, ActionKeys.VIEW);

		return ddlRecordVersionPersistence.findByRecordId(
			recordId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of record versions matching the record.
	 *
	 * @param  recordId the primary key of the record
	 * @return the number of matching record versions
	 */
	@Override
	public int getRecordVersionsCount(long recordId) throws PortalException {
		DDLRecordPermission.check(
			getPermissionChecker(), recordId, ActionKeys.VIEW);

		return ddlRecordVersionPersistence.countByRecordId(recordId);
	}

}