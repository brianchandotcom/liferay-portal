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

import com.liferay.dynamic.data.mapping.constants.DDMActionKeys;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.service.base.DDMFormInstanceRecordServiceBaseImpl;
import com.liferay.dynamic.data.mapping.service.permission.DDMFormInstancePermission;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Leonardo Barros
 */
public class DDMFormInstanceRecordServiceImpl
	extends DDMFormInstanceRecordServiceBaseImpl {

	@Override
	public DDMFormInstanceRecord addFormInstanceRecord(
			long groupId, long ddmFormInstanceId, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws PortalException {

		DDMFormInstancePermission.check(
			getPermissionChecker(), ddmFormInstanceId,
			DDMActionKeys.ADD_FORM_INSTANCE_RECORD);

		return ddmFormInstanceRecordLocalService.addFormInstanceRecord(
			getGuestOrUserId(), groupId, ddmFormInstanceId, ddmFormValues,
			serviceContext);
	}

	@Override
	public void deleteFormInstanceRecord(long ddmFormInstanceRecordId)
		throws PortalException {

		DDMFormInstanceRecord ddmFormInstanceRecord =
			ddmFormInstanceRecordLocalService.getFormInstanceRecord(
				ddmFormInstanceRecordId);

		DDMFormInstancePermission.check(
			getPermissionChecker(), ddmFormInstanceRecord.getFormInstance(),
			ActionKeys.DELETE);

		ddmFormInstanceRecordLocalService.deleteFormInstanceRecord(
			ddmFormInstanceRecord);
	}

	@Override
	public DDMFormInstanceRecord getFormInstanceRecord(
			long ddmFormInstanceRecordId)
		throws PortalException {

		DDMFormInstanceRecord ddmFormInstanceRecord =
			ddmFormInstanceRecordLocalService.getFormInstanceRecord(
				ddmFormInstanceRecordId);

		DDMFormInstancePermission.check(
			getPermissionChecker(), ddmFormInstanceRecord.getFormInstance(),
			ActionKeys.VIEW);

		return ddmFormInstanceRecord;
	}

	@Override
	public List<DDMFormInstanceRecord> getFormInstanceRecords(
			long ddmFormInstanceId)
		throws PortalException {

		DDMFormInstancePermission.contains(
			getPermissionChecker(), ddmFormInstanceId, ActionKeys.VIEW);

		return ddmFormInstanceRecordLocalService.getFormInstanceRecords(
			ddmFormInstanceId);
	}

	@Override
	public void revertFormInstanceRecord(
			long ddmFormInstanceRecordId, String version,
			ServiceContext serviceContext)
		throws PortalException {

		DDMFormInstanceRecord ddmFormInstanceRecord =
			ddmFormInstanceRecordLocalService.getFormInstanceRecord(
				ddmFormInstanceRecordId);

		DDMFormInstancePermission.check(
			getPermissionChecker(), ddmFormInstanceRecord.getFormInstance(),
			ActionKeys.UPDATE);

		ddmFormInstanceRecordLocalService.revertFormInstanceRecord(
			getGuestOrUserId(), ddmFormInstanceRecordId, version,
			serviceContext);
	}

	@Override
	public DDMFormInstanceRecord updateFormInstanceRecord(
			long ddmFormInstanceRecordId, boolean majorVersion,
			DDMFormValues ddmFormValues, ServiceContext serviceContext)
		throws PortalException {

		DDMFormInstanceRecord ddmFormInstanceRecord =
			ddmFormInstanceRecordLocalService.getFormInstanceRecord(
				ddmFormInstanceRecordId);

		DDMFormInstancePermission.check(
			getPermissionChecker(), ddmFormInstanceRecord.getFormInstance(),
			ActionKeys.UPDATE);

		return ddmFormInstanceRecordLocalService.updateFormInstanceRecord(
			getUserId(), ddmFormInstanceRecordId, majorVersion, ddmFormValues,
			serviceContext);
	}

}