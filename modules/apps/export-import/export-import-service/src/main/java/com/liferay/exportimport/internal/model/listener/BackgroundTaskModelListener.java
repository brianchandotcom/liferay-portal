/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = ModelListener.class)
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

	@Reference
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

}