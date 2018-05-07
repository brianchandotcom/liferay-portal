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

import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetVersion;
import com.liferay.dynamic.data.lists.service.base.DDLRecordSetVersionServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service for accessing, adding, deleting, and updating
 * dynamic data list (DDL) record set version. Its methods include permission
 * checks.
 *
 * @author Leonardo Barros
 */
public class DDLRecordSetVersionServiceImpl
	extends DDLRecordSetVersionServiceBaseImpl {

	@Override
	public DDLRecordSetVersion getLatestRecordSetVersion(long recordSetId)
		throws PortalException {

		_ddlRecordSetModelResourcePermission.check(
			getPermissionChecker(), recordSetId, ActionKeys.VIEW);

		return ddlRecordSetVersionLocalService.getLatestRecordSetVersion(
			recordSetId);
	}

	@Override
	public DDLRecordSetVersion getRecordSetVersion(long recordSetVersionId)
		throws PortalException {

		DDLRecordSetVersion recordSetVersion =
			ddlRecordSetVersionLocalService.getRecordSetVersion(
				recordSetVersionId);

		_ddlRecordSetModelResourcePermission.check(
			getPermissionChecker(), recordSetVersion.getRecordSetId(),
			ActionKeys.VIEW);

		return recordSetVersion;
	}

	@Override
	public List<DDLRecordSetVersion> getRecordSetVersions(
			long recordSetId, int start, int end,
			OrderByComparator<DDLRecordSetVersion> orderByComparator)
		throws PortalException {

		_ddlRecordSetModelResourcePermission.check(
			getPermissionChecker(), recordSetId, ActionKeys.VIEW);

		return ddlRecordSetVersionLocalService.getRecordSetVersions(
			recordSetId, start, end, orderByComparator);
	}

	@Override
	public int getRecordSetVersionsCount(long recordSetId)
		throws PortalException {

		_ddlRecordSetModelResourcePermission.check(
			getPermissionChecker(), recordSetId, ActionKeys.VIEW);

		return ddlRecordSetVersionLocalService.getRecordSetVersionsCount(
			recordSetId);
	}

	private static volatile ModelResourcePermission<DDLRecordSet>
		_ddlRecordSetModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				DDLRecordSetVersionServiceImpl.class,
				"_ddlRecordSetModelResourcePermission", DDLRecordSet.class);

}