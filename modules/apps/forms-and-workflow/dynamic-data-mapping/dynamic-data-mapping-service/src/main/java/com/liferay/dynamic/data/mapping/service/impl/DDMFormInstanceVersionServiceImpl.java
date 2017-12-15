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

package com.liferay.dynamic.data.mapping.service.impl;

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
import com.liferay.dynamic.data.mapping.service.base.DDMFormInstanceVersionServiceBaseImpl;
import com.liferay.dynamic.data.mapping.service.permission.DDMFormInstancePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Leonardo Barros
 */
public class DDMFormInstanceVersionServiceImpl
	extends DDMFormInstanceVersionServiceBaseImpl {

	@Override
	public DDMFormInstanceVersion getFormInstanceVersion(
			long ddmFormInstanceVersionId)
		throws PortalException {

		DDMFormInstanceVersion ddmFormInstanceVersion =
			ddmFormInstanceVersionLocalService.getFormInstanceVersion(
				ddmFormInstanceVersionId);

		DDMFormInstancePermission.check(
			getPermissionChecker(), ddmFormInstanceVersion.getFormInstanceId(),
			ActionKeys.VIEW);

		return ddmFormInstanceVersion;
	}

	@Override
	public List<DDMFormInstanceVersion> getFormInstanceVersions(
			long ddmFormInstanceId, int start, int end,
			OrderByComparator<DDMFormInstanceVersion> orderByComparator)
		throws PortalException {

		DDMFormInstancePermission.check(
			getPermissionChecker(), ddmFormInstanceId, ActionKeys.VIEW);

		return ddmFormInstanceVersionLocalService.getFormInstanceVersions(
			ddmFormInstanceId, start, end, orderByComparator);
	}

	@Override
	public int getFormInstanceVersionsCount(long ddmFormInstanceId)
		throws PortalException {

		DDMFormInstancePermission.check(
			getPermissionChecker(), ddmFormInstanceId, ActionKeys.VIEW);

		return ddmFormInstanceVersionLocalService.getFormInstanceVersionsCount(
			ddmFormInstanceId);
	}

	@Override
	public DDMFormInstanceVersion getLatestFormInstanceVersion(
			long ddmFormInstanceId)
		throws PortalException {

		DDMFormInstancePermission.check(
			getPermissionChecker(), ddmFormInstanceId, ActionKeys.VIEW);

		return ddmFormInstanceVersionLocalService.getLatestFormInstanceVersion(
			ddmFormInstanceId);
	}

}