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

package com.liferay.dynamic.data.mapping.uad.test;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Gabriel Ibson
 */
public class DDMFormInstanceRecordUADTestUtil {

	public static DDMFormInstanceRecord addDDMFormInstanceRecord(
			DDMFormInstanceRecordLocalService ddmFormInstanceRecordLocalService,
			long userId, Group group)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("text");

		DDMFormValues settingsDDMFormValues =
			DDMFormValuesTestUtil.createDDMFormValues(ddmForm);

		DDMFormInstance ddmFormInstance =
			DDMFormInstanceUADTestUtil.addDDMFormInstance(
				ddmForm, group, settingsDDMFormValues, serviceContext, userId);

		return ddmFormInstanceRecordLocalService.addFormInstanceRecord(
			userId, group.getGroupId(), ddmFormInstance.getFormInstanceId(),
			settingsDDMFormValues, serviceContext);
	}

	public static DDMFormInstanceRecord
			addDDMFormInstanceRecordWithStatusByUserId(
				DDMFormInstanceRecordLocalService
					ddmFormInstanceRecordLocalService,
				long userId, long statusByUserId, Group group)
		throws Exception {

		DDMFormInstanceRecord ddmFormInstanceRecord = addDDMFormInstanceRecord(
			ddmFormInstanceRecordLocalService, userId, group);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId());

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			ddmFormInstanceRecord.getFormInstanceRecordVersion();

		ddmFormInstanceRecord = ddmFormInstanceRecordLocalService.updateStatus(
			statusByUserId,
			ddmFormInstanceRecordVersion.getFormInstanceRecordVersionId(),
			WorkflowConstants.STATUS_APPROVED, serviceContext);

		return ddmFormInstanceRecord;
	}

}