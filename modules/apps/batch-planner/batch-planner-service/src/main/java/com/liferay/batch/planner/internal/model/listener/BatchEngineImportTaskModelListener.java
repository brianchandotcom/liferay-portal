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

package com.liferay.batch.planner.internal.model.listener;

import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.planner.constants.BatchPlannerPlanConstants;
import com.liferay.batch.planner.constants.BatchPlannerPortletKeys;
import com.liferay.batch.planner.internal.notifications.BatchPlannerNotificationSender;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.service.BatchPlannerPlanLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matija Petanjek
 * @author Igor Beslic
 */
@Component(service = ModelListener.class)
public class BatchEngineImportTaskModelListener
	extends BaseModelListener<BatchEngineImportTask> {

	@Override
	public void onAfterRemove(BatchEngineImportTask batchEngineImportTask)
		throws ModelListenerException {

		try {
			_batchPlannerPlanLocalService.deactivateBatchPlannerPlan(
				batchEngineImportTask.getExternalReferenceCode());
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	@Override
	public void onAfterUpdate(
			BatchEngineImportTask originalBatchEngineImportTask,
			BatchEngineImportTask batchEngineImportTask)
		throws ModelListenerException {

		BatchPlannerPlan batchPlannerPlan = _updateStatus(
			batchEngineImportTask);

		if (batchPlannerPlan == null) {
			return;
		}

		BatchEngineTaskExecuteStatus batchEngineTaskExecuteStatus =
			BatchEngineTaskExecuteStatus.valueOf(
				batchEngineImportTask.getExecuteStatus());

		if ((batchEngineTaskExecuteStatus ==
				BatchEngineTaskExecuteStatus.COMPLETED) ||
			(batchEngineTaskExecuteStatus ==
				BatchEngineTaskExecuteStatus.FAILED)) {

			_notify(batchEngineTaskExecuteStatus, batchPlannerPlan);
		}
	}

	@Activate
	protected void activate() {
		_batchPlannerNotificationSender.setUserNotificationEventLocalService(
			_userNotificationEventLocalService);
	}

	private String _getFileName(String externalURL) {
		String fileName = FileUtil.getShortFileName(externalURL);

		return fileName.replaceAll("(.+)-(.+)\\.(\\w+)", "$1.$3");
	}

	private int _getStatus(BatchEngineImportTask batchEngineImportTask) {
		int status = BatchPlannerPlanConstants.getStatus(
			BatchEngineTaskExecuteStatus.valueOf(
				batchEngineImportTask.getExecuteStatus()));

		if (status == BatchPlannerPlanConstants.STATUS_COMPLETED) {
			int batchEngineImportTaskErrorsCount =
				batchEngineImportTask.getBatchEngineImportTaskErrorsCount();

			if (batchEngineImportTaskErrorsCount > 0) {
				int totalItemsCount =
					batchEngineImportTask.getTotalItemsCount();

				if (batchEngineImportTaskErrorsCount < totalItemsCount) {
					status =
						BatchPlannerPlanConstants.STATUS_PARTIALLY_COMPLETED;
				}
				else {
					status = BatchPlannerPlanConstants.STATUS_FAILED;
				}
			}
		}

		return status;
	}

	private void _notify(
		BatchEngineTaskExecuteStatus batchEngineTaskExecuteStatus,
		BatchPlannerPlan batchPlannerPlan) {

		_batchPlannerNotificationSender.sendUserNotificationEvents(
			batchPlannerPlan.getUserId(), BatchPlannerPortletKeys.BATCH_PLANNER,
			UserNotificationDeliveryConstants.TYPE_WEBSITE,
			_batchPlannerNotificationSender.getNotificationEventJSONObject(
				batchEngineTaskExecuteStatus,
				batchPlannerPlan.getBatchPlannerPlanId(),
				batchPlannerPlan.getInternalClassName(),
				_getFileName(batchPlannerPlan.getExternalURL())));
	}

	private BatchPlannerPlan _updateStatus(
		BatchEngineImportTask batchEngineImportTask) {

		BatchPlannerPlan batchPlannerPlan =
			_batchPlannerPlanLocalService.fetchBatchPlannerPlan(
				GetterUtil.getLong(
					batchEngineImportTask.getExternalReferenceCode()));

		if (batchPlannerPlan == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No batch planner plan found for ID " +
						batchEngineImportTask.getExternalReferenceCode());
			}

			return null;
		}

		try {
			return _batchPlannerPlanLocalService.updateStatus(
				batchPlannerPlan.getBatchPlannerPlanId(),
				_getStatus(batchEngineImportTask));
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchEngineImportTaskModelListener.class);

	private final BatchPlannerNotificationSender
		_batchPlannerNotificationSender = new BatchPlannerNotificationSender(
			"import");

	@Reference
	private BatchPlannerPlanLocalService _batchPlannerPlanLocalService;

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}