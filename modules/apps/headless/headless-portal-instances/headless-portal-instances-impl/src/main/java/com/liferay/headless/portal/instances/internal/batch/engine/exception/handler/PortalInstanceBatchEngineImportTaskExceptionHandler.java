/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.portal.instances.internal.batch.engine.exception.handler;

import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.batch.engine.exception.handler.BatchEngineImportTaskExceptionHandler;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.headless.portal.instances.dto.v1_0.PortalInstance;
import com.liferay.portal.instances.constants.PortalInstancesNotificationConstants;
import com.liferay.portal.instances.constants.PortalInstancesPortletKeys;
import com.liferay.portal.kernel.exception.RequiredCompanyException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Ortiz
 */
@Component(service = BatchEngineImportTaskExceptionHandler.class)
public class PortalInstanceBatchEngineImportTaskExceptionHandler
	implements BatchEngineImportTaskExceptionHandler {

	@Override
	public void handle(
		BatchEngineImportTask batchEngineImportTask,
		BatchEngineTaskItemDelegate<?> batchEngineTaskItemDelegate,
		Exception exception1, Object item, String message) {

		if (!(item instanceof PortalInstance)) {
			return;
		}

		String operation = BatchEngineTaskOperation.DELETE.name();

		if (!operation.equals(batchEngineImportTask.getOperation())) {
			return;
		}

		PortalInstance portalInstance = (PortalInstance)item;

		try {
			_userNotificationEventLocalService.sendUserNotificationEvents(
				batchEngineImportTask.getUserId(),
				PortalInstancesPortletKeys.PORTAL_INSTANCES,
				UserNotificationDeliveryConstants.TYPE_WEBSITE,
				JSONUtil.put(
					"errorMessageKey", _getErrorMessageKey(exception1)
				).put(
					"operationType",
					PortalInstancesNotificationConstants.OPERATION_TYPE_DELETE
				).put(
					"portalInstanceId", portalInstance.getPortalInstanceId()
				).put(
					"status", PortalInstancesNotificationConstants.STATUS_FAILED
				));
		}
		catch (Exception exception2) {
			_log.error(
				"Unable to send the user notification event for portal " +
					"instance " + portalInstance.getPortalInstanceId(),
				exception2);
		}
	}

	private String _getErrorMessageKey(Exception exception) {
		if (exception instanceof RequiredCompanyException) {
			return "the-default-instance-cannot-be-deleted";
		}

		return "an-unexpected-error-occurred";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalInstanceBatchEngineImportTaskExceptionHandler.class);

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}