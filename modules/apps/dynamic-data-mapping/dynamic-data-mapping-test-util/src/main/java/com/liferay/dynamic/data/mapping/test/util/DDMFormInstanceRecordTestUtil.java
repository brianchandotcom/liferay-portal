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

package com.liferay.dynamic.data.mapping.test.util;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Gabriel Ibson
 */
public class DDMFormInstanceRecordTestUtil {

	public static DDMFormInstanceRecord addDDMFormInstanceRecord(
			DDMFormInstance ddmFormInstance, DDMFormValues ddmFormValues,
			Group group, long userId)
		throws Exception {

		return DDMFormInstanceRecordLocalServiceUtil.addFormInstanceRecord(
			userId, group.getGroupId(), ddmFormInstance.getFormInstanceId(),
			ddmFormValues, ServiceContextTestUtil.getServiceContext());
	}

	public static DDMFormInstanceRecord addDDMFormInstanceRecordWithoutValues(
			Group group, long userId)
		throws Exception {

		DDMFormInstance ddmFormInstance =
			DDMFormInstanceTestUtil.addDDMFormInstance(group, userId);

		return addDDMFormInstanceRecord(
			ddmFormInstance,
			DDMFormValuesTestUtil.createDDMFormValues(
				ddmFormInstance.getDDMForm()),
			group, userId);
	}

	public static DDMFormInstanceRecord
			addDDMFormInstanceRecordWithRandomValues(
				DDMFormInstance ddmFormInstance, Group group, long userId)
		throws Exception {

		DDMForm ddmForm = ddmFormInstance.getDDMForm();

		return addDDMFormInstanceRecord(
			ddmFormInstance,
			DDMFormValuesTestUtil.createDDMFormValuesWithRandomValues(ddmForm),
			group, userId);
	}

	public static DDMFormInstanceRecord
			addDDMFormInstanceRecordWithRandomValues(Group group, long userId)
		throws Exception {

		return addDDMFormInstanceRecordWithRandomValues(
			DDMFormInstanceTestUtil.addDDMFormInstance(group, userId), group,
			userId);
	}

	public static DDMFormInstanceRecord
			addDDMFormInstanceRecordWithStatusByUserIdAndNoValues(
				Group group, long statusByUserId, long userId)
		throws Exception {

		DDMFormInstanceRecord ddmFormInstanceRecord =
			addDDMFormInstanceRecordWithoutValues(group, userId);

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			ddmFormInstanceRecord.getFormInstanceRecordVersion();

		return DDMFormInstanceRecordLocalServiceUtil.updateStatus(
			statusByUserId,
			ddmFormInstanceRecordVersion.getFormInstanceRecordVersionId(),
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId()));
	}

}