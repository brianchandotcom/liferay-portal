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

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.service.base.DDMFormInstanceRecordVersionServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Leonardo Barros
 */
public class DDMFormInstanceRecordVersionServiceImpl
	extends DDMFormInstanceRecordVersionServiceBaseImpl {

	@Override
	public DDMFormInstanceRecordVersion getFormInstanceRecordVersion(
			long ddmFormInstanceRecordVersionId)
		throws PortalException {

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			ddmFormInstanceRecordVersionLocalService.
				getFormInstanceRecordVersion(ddmFormInstanceRecordVersionId);

		_ddmFormInstanceModelResourcePermission.check(
			getPermissionChecker(),
			ddmFormInstanceRecordVersion.getFormInstance(), ActionKeys.VIEW);

		return ddmFormInstanceRecordVersion;
	}

	@Override
	public DDMFormInstanceRecordVersion getFormInstanceRecordVersion(
			long ddmFormInstanceRecordId, String version)
		throws PortalException {

		DDMFormInstanceRecord ddmFormInstanceRecord =
			ddmFormInstanceRecordPersistence.findByPrimaryKey(
				ddmFormInstanceRecordId);

		_ddmFormInstanceModelResourcePermission.check(
			getPermissionChecker(), ddmFormInstanceRecord.getFormInstance(),
			ActionKeys.VIEW);

		return ddmFormInstanceRecordVersionPersistence.findByF_V(
			ddmFormInstanceRecordId, version);
	}

	@Override
	public List<DDMFormInstanceRecordVersion> getFormInstanceRecordVersions(
			long ddmFormInstanceRecordId)
		throws PortalException {

		DDMFormInstanceRecord ddmFormInstanceRecord =
			ddmFormInstanceRecordPersistence.findByPrimaryKey(
				ddmFormInstanceRecordId);

		_ddmFormInstanceModelResourcePermission.check(
			getPermissionChecker(), ddmFormInstanceRecord.getFormInstance(),
			ActionKeys.VIEW);

		return ddmFormInstanceRecordVersionPersistence.
			findByFormInstanceRecordId(ddmFormInstanceRecordId);
	}

	@Override
	public List<DDMFormInstanceRecordVersion> getFormInstanceRecordVersions(
			long ddmFormInstanceRecordId, int start, int end,
			OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator)
		throws PortalException {

		DDMFormInstanceRecord ddmFormInstanceRecord =
			ddmFormInstanceRecordPersistence.findByPrimaryKey(
				ddmFormInstanceRecordId);

		_ddmFormInstanceModelResourcePermission.check(
			getPermissionChecker(), ddmFormInstanceRecord.getFormInstance(),
			ActionKeys.VIEW);

		return ddmFormInstanceRecordVersionPersistence.
			findByFormInstanceRecordId(
				ddmFormInstanceRecordId, start, end, orderByComparator);
	}

	@Override
	public int getFormInstanceRecordVersionsCount(long ddmFormInstanceRecordId)
		throws PortalException {

		DDMFormInstanceRecord ddmFormInstanceRecord =
			ddmFormInstanceRecordPersistence.findByPrimaryKey(
				ddmFormInstanceRecordId);

		_ddmFormInstanceModelResourcePermission.check(
			getPermissionChecker(), ddmFormInstanceRecord.getFormInstance(),
			ActionKeys.VIEW);

		return ddmFormInstanceRecordVersionPersistence.
			countByFormInstanceRecordId(ddmFormInstanceRecordId);
	}

	private static volatile ModelResourcePermission<DDMFormInstance>
		_ddmFormInstanceModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				DDMFormInstanceRecordVersionServiceImpl.class,
				"_ddmFormInstanceModelResourcePermission",
				DDMFormInstance.class);

}