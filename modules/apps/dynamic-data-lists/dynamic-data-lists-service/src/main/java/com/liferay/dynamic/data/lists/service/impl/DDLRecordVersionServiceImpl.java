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

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.base.DDLRecordVersionServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the remote service for accessing dynamic data list (DDL) record
 * versions. Its methods include permission checks.
 *
 * @author Marcellus Tavares
 */
@Component(
	property = {
		"json.web.service.context.name=ddl",
		"json.web.service.context.path=DDLRecordVersion"
	},
	service = AopService.class
)
public class DDLRecordVersionServiceImpl
	extends DDLRecordVersionServiceBaseImpl {

	/**
	 * Returns the record version matching the ID.
	 *
	 * @param  recordVersionId the primary key of the record version
	 * @return the record version with the ID
	 * @throws PortalException if the matching record set could not be found or
	 *         if the user did not have the required permission to access the
	 *         record set
	 */
	@Override
	public DDLRecordVersion getRecordVersion(long recordVersionId)
		throws PortalException {

		DDLRecordVersion recordVersion =
			ddlRecordVersionLocalService.getRecordVersion(recordVersionId);

		_ddlRecordSetModelResourcePermission.check(
			getPermissionChecker(), recordVersion.getRecordSetId(),
			ActionKeys.VIEW);

		return recordVersion;
	}

	/**
	 * Returns a record version matching the record and version.
	 *
	 * @param  recordId the primary key of the record
	 * @param  version the version of the record to return
	 * @return the record version macthing the record primary key and version
	 * @throws PortalException if the matching record set is not found or if the
	 *         user do not have the required permission to access the record set
	 */
	@Override
	public DDLRecordVersion getRecordVersion(long recordId, String version)
		throws PortalException {

		DDLRecordVersion recordVersion = ddlRecordVersionPersistence.findByR_V(
			recordId, version);

		_ddlRecordSetModelResourcePermission.check(
			getPermissionChecker(), recordVersion.getRecordSetId(),
			ActionKeys.VIEW);

		return recordVersion;
	}

	/**
	 * Returns all the record versions matching the record.
	 *
	 * @param  recordId the primary key of the record
	 * @return the matching record versions
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public List<DDLRecordVersion> getRecordVersions(long recordId)
		throws PortalException {

		DDLRecord record = ddlRecordPersistence.findByPrimaryKey(recordId);

		_ddlRecordSetModelResourcePermission.check(
			getPermissionChecker(), record.getRecordSetId(), ActionKeys.VIEW);

		return ddlRecordVersionPersistence.findByRecordId(recordId);
	}

	/**
	 * Returns an ordered range of record versions matching the record.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to <code>QueryUtil.ALL_POS</code> will return the
	 * full result set.
	 * </p>
	 *
	 * @param  recordId the primary key of the record
	 * @param  start the lower bound of the range of record versions to return
	 * @param  end the upper bound of the range of record versions to return
	 *         (not inclusive)
	 * @param  orderByComparator the comparator used to order the record
	 *         versions
	 * @return the range of matching record versions ordered by the comparator
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public List<DDLRecordVersion> getRecordVersions(
			long recordId, int start, int end,
			OrderByComparator<DDLRecordVersion> orderByComparator)
		throws PortalException {

		DDLRecord record = ddlRecordPersistence.findByPrimaryKey(recordId);

		_ddlRecordSetModelResourcePermission.check(
			getPermissionChecker(), record.getRecordSetId(), ActionKeys.VIEW);

		return ddlRecordVersionPersistence.findByRecordId(
			recordId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of record versions matching the record.
	 *
	 * @param  recordId the primary key of the record
	 * @return the number of matching record versions
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public int getRecordVersionsCount(long recordId) throws PortalException {
		DDLRecord record = ddlRecordPersistence.findByPrimaryKey(recordId);

		_ddlRecordSetModelResourcePermission.check(
			getPermissionChecker(), record.getRecordSetId(), ActionKeys.VIEW);

		return ddlRecordVersionPersistence.countByRecordId(recordId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.dynamic.data.lists.model.DDLRecordSet)"
	)
	private ModelResourcePermission<DDLRecordSet>
		_ddlRecordSetModelResourcePermission;

}