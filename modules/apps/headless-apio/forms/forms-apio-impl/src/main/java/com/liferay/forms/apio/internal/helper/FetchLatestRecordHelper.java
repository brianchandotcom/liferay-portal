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

package com.liferay.forms.apio.internal.helper;

import com.liferay.apio.architect.functional.Try;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionService;
import com.liferay.portal.apio.user.CurrentUser;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Paulo Cruz
 * @review
 */
@Component(immediate = true, service = FetchLatestRecordHelper.class)
public class FetchLatestRecordHelper {

	public DDMFormInstanceRecord fetchLatestDraftRecord(
		DDMFormInstance ddmFormInstance, CurrentUser currentUser) {

		return Try.fromFallible(
			() -> _ddmFormInstanceRecordVersionService.
				fetchLatestFormInstanceRecordVersion(
					currentUser.getUserId(),
					ddmFormInstance.getFormInstanceId(),
					ddmFormInstance.getVersion(),
					WorkflowConstants.STATUS_DRAFT)
		).map(
			DDMFormInstanceRecordVersion::getFormInstanceRecord
		).orElse(
			null
		);
	}

	@Reference
	private DDMFormInstanceRecordVersionService
		_ddmFormInstanceRecordVersionService;

}