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

package com.liferay.dynamic.data.mapping.form.web.internal.display.context.util;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Carolina Barbosa
 */
public class DDMFormInstanceSubmissionLimitStatusUtil {

	public static boolean isLimitToOneSubmissionPerUser(
			DDMFormInstance ddmFormInstance)
		throws PortalException {

		if (ddmFormInstance == null) {
			return false;
		}

		DDMFormInstanceSettings ddmFormInstanceSettings =
			ddmFormInstance.getSettingsModel();

		return ddmFormInstanceSettings.limitToOneSubmissionPerUser();
	}

	public static boolean isSubmissionLimitReached(
			DDMFormInstance ddmFormInstance,
			DDMFormInstanceRecordVersionLocalService
				ddmFormInstanceRecordVersionLocalService,
			User user)
		throws PortalException {

		if (user.isDefaultUser() ||
			!isLimitToOneSubmissionPerUser(ddmFormInstance)) {

			return false;
		}

		List<DDMFormInstanceRecordVersion> ddmFormInstanceRecordVersions =
			ddmFormInstanceRecordVersionLocalService.
				getFormInstanceRecordVersions(
					user.getUserId(), ddmFormInstance.getFormInstanceId());

		Stream<DDMFormInstanceRecordVersion> stream =
			ddmFormInstanceRecordVersions.stream();

		Optional<DDMFormInstanceRecordVersion> optional = stream.filter(
			ddmFormInstanceRecordVersion ->
				ddmFormInstanceRecordVersion.getStatus() !=
					WorkflowConstants.STATUS_DRAFT
		).findFirst();

		if (optional.isPresent()) {
			return true;
		}

		return false;
	}

}