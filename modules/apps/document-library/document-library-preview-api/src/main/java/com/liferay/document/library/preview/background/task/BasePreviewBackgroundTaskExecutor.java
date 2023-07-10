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

package com.liferay.document.library.preview.background.task;

import com.liferay.document.library.configuration.DLFileEntryConfiguration;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
public abstract class BasePreviewBackgroundTaskExecutor
	extends BaseBackgroundTaskExecutor {

	@Override
	public BackgroundTaskExecutor clone() {
		return this;
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long companyId = GetterUtil.getLong(taskContextMap.get("COMPANY_ID"));

		generatePreviews(companyId);

		return BackgroundTaskResult.SUCCESS;
	}

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		return null;
	}

	protected abstract void generatePreview(FileVersion fileVersion)
		throws Exception;

	protected void generatePreviews(long companyId) {
		ActionableDynamicQuery actionableDynamicQuery =
			dlFileEntryLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property companyIdProperty = PropertyFactoryUtil.forName(
					"companyId");

				dynamicQuery.add(companyIdProperty.eq(companyId));

				Property mimeTypeProperty = PropertyFactoryUtil.forName(
					"mimeType");

				dynamicQuery.add(mimeTypeProperty.in(getMimeTypes()));

				Property sizeProperty = PropertyFactoryUtil.forName("size");

				dynamicQuery.add(
					sizeProperty.le(
						dlFileEntryConfiguration.
							previewableProcessorMaxSize()));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(DLFileEntry dlFileEntry) -> {
				FileEntry fileEntry = new LiferayFileEntry(dlFileEntry);

				try {
					generatePreview(fileEntry.getLatestFileVersion());
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to process file entry " +
								fileEntry.getFileEntryId(),
							exception);
					}
				}
			});

		try {
			actionableDynamicQuery.performActions();
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	protected abstract String[] getMimeTypes();

	protected volatile DLFileEntryConfiguration dlFileEntryConfiguration;

	@Reference
	protected DLFileEntryLocalService dlFileEntryLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		BasePreviewBackgroundTaskExecutor.class);

}