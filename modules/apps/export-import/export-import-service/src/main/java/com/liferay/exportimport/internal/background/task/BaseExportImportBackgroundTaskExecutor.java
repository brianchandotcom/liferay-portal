/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.background.task;

import com.liferay.exportimport.internal.background.task.display.ExportImportBackgroundTaskDisplay;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.util.MapUtil;

/**
 * @author Akos Thurzo
 */
public abstract class BaseExportImportBackgroundTaskExecutor
	extends BaseBackgroundTaskExecutor {

	public BaseExportImportBackgroundTaskExecutor() {
		setBackgroundTaskStatusMessageTranslator(
			new DefaultExportImportBackgroundTaskStatusMessageTranslator());
	}

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		return new ExportImportBackgroundTaskDisplay(backgroundTask);
	}

	@Override
	public String handleException(
		BackgroundTask backgroundTask, Exception exception) {

		JSONObject jsonObject = StagingUtil.getExceptionMessagesJSONObject(
			getLocale(backgroundTask), exception,
			getExportImportConfiguration(backgroundTask));

		return jsonObject.toString();
	}

	protected ExportImportConfiguration getExportImportConfiguration(
		BackgroundTask backgroundTask) {

		long exportImportConfigurationId = MapUtil.getLong(
			backgroundTask.getTaskContextMap(), "exportImportConfigurationId");

		return ExportImportConfigurationLocalServiceUtil.
			fetchExportImportConfiguration(exportImportConfigurationId);
	}

	protected static final TransactionConfig transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

}