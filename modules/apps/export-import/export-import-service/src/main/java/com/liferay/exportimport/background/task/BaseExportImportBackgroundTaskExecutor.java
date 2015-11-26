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

package com.liferay.exportimport.background.task;

import com.liferay.exportimport.background.task.display.ExportImportBackgroundTaskDisplay;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionAttribute;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portlet.exportimport.model.ExportImportConfiguration;
import com.liferay.portlet.exportimport.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.portlet.exportimport.staging.StagingUtil;

import java.io.Serializable;

import java.util.Map;

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
	public String handleException(BackgroundTask backgroundTask, Exception e) {
		JSONObject jsonObject = StagingUtil.getExceptionMessagesJSONObject(
			getLocale(backgroundTask), e,
			getExportImportConfiguration(backgroundTask));

		return jsonObject.toString();
	}

	protected ExportImportConfiguration getExportImportConfiguration(
		BackgroundTask backgroundTask) {

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long exportImportConfigurationId = MapUtil.getLong(
			taskContextMap, "exportImportConfigurationId");

		return ExportImportConfigurationLocalServiceUtil.
			fetchExportImportConfiguration(exportImportConfigurationId);
	}

	protected static final TransactionAttribute transactionAttribute =
		TransactionAttribute.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

}