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

package com.liferay.dynamic.data.mapping.internal.model.listener;

import com.liferay.dynamic.data.mapping.constants.DDMFormInstanceReportConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceReportLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(service = ModelListener.class)
public class DDMFormInstanceRecordVersionModelListener
	extends BaseModelListener<DDMFormInstanceRecordVersion> {

	@Override
	public void onAfterUpdate(
			DDMFormInstanceRecordVersion originalDDMFormInstanceRecordVersion,
			DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion)
		throws ModelListenerException {

		try {
			if (ddmFormInstanceRecordVersion.getStatus() !=
					WorkflowConstants.STATUS_APPROVED) {

				return;
			}

			_processFormInstanceReportEvent(
				ddmFormInstanceRecordVersion,
				DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to update dynamic data mapping form instance ",
						"report for dynamic data mapping form instance record ",
						ddmFormInstanceRecordVersion.getFormInstanceRecordId()),
					exception);
			}
		}
	}

	@Override
	public void onBeforeUpdate(
			DDMFormInstanceRecordVersion originalDDMFormInstanceRecordVersion,
			DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion)
		throws ModelListenerException {

		try {
			if (ddmFormInstanceRecordVersion.getStatus() !=
					WorkflowConstants.STATUS_APPROVED) {

				return;
			}

			DDMFormInstanceRecordVersion latestDDMFormInstanceRecordVersion =
				_ddmFormInstanceRecordVersionLocalService.
					getLatestFormInstanceRecordVersion(
						ddmFormInstanceRecordVersion.getFormInstanceRecordId(),
						WorkflowConstants.STATUS_APPROVED);

			_processFormInstanceReportEvent(
				latestDDMFormInstanceRecordVersion,
				DDMFormInstanceReportConstants.EVENT_DELETE_RECORD_VERSION);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to update dynamic data mapping form instance ",
						"report for dynamic data mapping form instance record ",
						ddmFormInstanceRecordVersion.getFormInstanceRecordId()),
					exception);
			}
		}
	}

	@Reference
	protected DDMFormInstanceReportLocalService
		ddmFormInstanceReportLocalService;

	private void _processFormInstanceReportEvent(
			DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion,
			String formInstanceReportEvent)
		throws PortalException {

		DDMFormInstanceReport ddmFormInstanceReport =
			ddmFormInstanceReportLocalService.
				getFormInstanceReportByFormInstanceId(
					ddmFormInstanceRecordVersion.getFormInstanceId());

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				ddmFormInstanceReportLocalService.
					processFormInstanceReportEvent(
						ddmFormInstanceReport.getFormInstanceReportId(),
						ddmFormInstanceRecordVersion.
							getFormInstanceRecordVersionId(),
						formInstanceReportEvent);

				return null;
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceRecordVersionModelListener.class);

	@Reference
	private DDMFormInstanceRecordVersionLocalService
		_ddmFormInstanceRecordVersionLocalService;

}