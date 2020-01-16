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

package com.liferay.exportimport.internal.model.listener;

import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Levente Hudák
 */
@Component(immediate = true, service = ModelListener.class)
public class BackgroundTaskModelListener
	extends BaseModelListener<BackgroundTask> {

	@Override
	public void onBeforeRemove(BackgroundTask backgroundTask)
		throws ModelListenerException {

		long exportImportConfigurationId = MapUtil.getLong(
			backgroundTask.getTaskContextMap(), "exportImportConfigurationId");

		if (exportImportConfigurationId == 0) {
			return;
		}

		try {
			ExportImportConfiguration exportImportConfiguration =
				_exportImportConfigurationLocalService.
					fetchExportImportConfiguration(exportImportConfigurationId);

			if (exportImportConfiguration == null) {
				return;
			}

			if (exportImportConfiguration.getStatus() ==
					WorkflowConstants.STATUS_DRAFT) {

				_exportImportConfigurationLocalService.
					deleteExportImportConfiguration(exportImportConfiguration);
			}
		}
		catch (Exception exception) {
			throw new ModelListenerException(
				"Unable to delete the process configuration for background " +
					"task " + backgroundTask.getBackgroundTaskId(),
				exception);
		}
	}

	@Reference(unbind = "-")
	protected void setExportImportConfigurationLocalService(
		ExportImportConfigurationLocalService
			exportImportConfigurationLocalService) {

		_exportImportConfigurationLocalService =
			exportImportConfigurationLocalService;
	}

	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

}