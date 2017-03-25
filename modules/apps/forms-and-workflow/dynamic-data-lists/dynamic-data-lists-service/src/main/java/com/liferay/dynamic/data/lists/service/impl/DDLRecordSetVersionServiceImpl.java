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

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.lists.model.DDLRecordSetVersion;
import com.liferay.dynamic.data.lists.service.base.DDLRecordSetVersionServiceBaseImpl;
import com.liferay.dynamic.data.lists.service.permission.DDLPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service for accessing, adding, deleting, and updating
 * dynamic data list (DDL) record set version. Its methods include permission checks.
 *
 * @author Leonardo Barros
 */
@ProviderType
public class DDLRecordSetVersionServiceImpl
	extends DDLRecordSetVersionServiceBaseImpl {

	@Override
	public DDLRecordSetVersion getDDLRecordSetVersion(long recordSetVersionId)
		throws PortalException {

		DDLRecordSetVersion recordSetVersion =
			ddlRecordSetVersionLocalService.getDDLRecordSetVersion(
				recordSetVersionId);

		DDLPermission.check(
			getPermissionChecker(), recordSetVersion.getRecordSetId(),
			ActionKeys.VIEW);

		return recordSetVersion;
	}

	@Override
	public List<DDLRecordSetVersion> getDDLRecordSetVersions(
			long recordSetId, int start, int end,
			OrderByComparator<DDLRecordSetVersion> orderByComparator)
		throws PortalException {

		DDLPermission.check(
			getPermissionChecker(), recordSetId, ActionKeys.VIEW);

		return ddlRecordSetVersionLocalService.getDDLRecordSetVersions(
			recordSetId, start, end, orderByComparator);
	}

	@Override
	public int getDDLRecordSetVersionsCount(long recordSetId)
		throws PortalException {

		DDLPermission.check(
			getPermissionChecker(), recordSetId, ActionKeys.VIEW);

		return ddlRecordSetVersionLocalService.getDDLRecordSetVersionsCount(
			recordSetId);
	}

	@Override
	public DDLRecordSetVersion getLatestRecordSetVersion(long recordSetId)
		throws PortalException {

		DDLPermission.check(
			getPermissionChecker(), recordSetId, ActionKeys.VIEW);

		return ddlRecordSetVersionLocalService.getLatestRecordSetVersion(
			recordSetId);
	}

}