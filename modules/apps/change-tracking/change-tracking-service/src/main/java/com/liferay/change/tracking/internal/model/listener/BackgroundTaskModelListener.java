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

package com.liferay.change.tracking.internal.model.listener;

import com.liferay.change.tracking.internal.background.task.CTPublishBackgroundTaskExecutor;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTSchemaVersionLocalService;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = ModelListener.class)
public class BackgroundTaskModelListener
	extends BaseModelListener<BackgroundTask> {

	@Override
	public void onAfterUpdate(BackgroundTask backgroundTask) {
		if ((backgroundTask != null) &&
			Objects.equals(
				backgroundTask.getTaskExecutorClassName(),
				CTPublishBackgroundTaskExecutor.class.getName()) &&
			((backgroundTask.getStatus() ==
				BackgroundTaskConstants.STATUS_CANCELLED) ||
			 (backgroundTask.getStatus() ==
				 BackgroundTaskConstants.STATUS_FAILED))) {

			CTCollection ctCollection =
				_ctCollectionLocalService.fetchCTCollection(
					Long.valueOf(backgroundTask.getName()));

			if (ctCollection != null) {
				int status = WorkflowConstants.STATUS_DRAFT;

				if (!_ctSchemaVersionLocalService.isLatestSchemaVersion(
						ctCollection.getSchemaVersionId())) {

					status = WorkflowConstants.STATUS_EXPIRED;
				}

				ctCollection.setStatus(status);

				_ctCollectionLocalService.updateCTCollection(ctCollection);
			}
		}
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTSchemaVersionLocalService _ctSchemaVersionLocalService;

}