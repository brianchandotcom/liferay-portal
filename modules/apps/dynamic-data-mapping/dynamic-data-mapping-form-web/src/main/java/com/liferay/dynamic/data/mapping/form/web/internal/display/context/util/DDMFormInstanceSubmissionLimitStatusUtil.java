/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		if (user.isGuestUser() ||
			!isLimitToOneSubmissionPerUser(ddmFormInstance)) {

			return false;
		}

		List<DDMFormInstanceRecordVersion> ddmFormInstanceRecordVersions =
			ddmFormInstanceRecordVersionLocalService.
				getFormInstanceRecordVersions(
					user.getUserId(), ddmFormInstance.getFormInstanceId());

		for (DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion :
				ddmFormInstanceRecordVersions) {

			if ((ddmFormInstanceRecordVersion.getStatus() !=
					WorkflowConstants.STATUS_DRAFT) &&
				(ddmFormInstanceRecordVersion.getStatus() !=
					WorkflowConstants.STATUS_PENDING)) {

				return true;
			}
		}

		return false;
	}

}