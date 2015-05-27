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

package com.liferay.portlet.dynamicdatalists.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion;
import com.liferay.portlet.dynamicdatalists.service.base.DDLRecordVersionServiceBaseImpl;
import com.liferay.portlet.dynamicdatalists.service.permission.DDLRecordPermission;

import java.util.List;

/**
 * @author Marcellus Tavares
 */
public class DDLRecordVersionServiceImpl
	extends DDLRecordVersionServiceBaseImpl {

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

	@Override
	public DDLRecordVersion getRecordVersion(long recordId, String version)
		throws PortalException {

		DDLRecordPermission.check(
			getPermissionChecker(), recordId, ActionKeys.VIEW);

		return ddlRecordVersionPersistence.findByR_V(recordId, version);
	}

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

	@Override
	public int getRecordVersionsCount(long recordId) throws PortalException {
		DDLRecordPermission.check(
			getPermissionChecker(), recordId, ActionKeys.VIEW);

		return ddlRecordVersionPersistence.countByRecordId(recordId);
	}

}