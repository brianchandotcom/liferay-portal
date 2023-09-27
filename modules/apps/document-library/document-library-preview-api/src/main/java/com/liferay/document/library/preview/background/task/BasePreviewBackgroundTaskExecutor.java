/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.preview.background.task;

import com.liferay.document.library.configuration.DLFileEntryConfiguration;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
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

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Modified;
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

		long companyId = GetterUtil.getLong(taskContextMap.get("companyId"));

		try {
			generatePreviews(companyId);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return BackgroundTaskResult.SUCCESS;
	}

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		return null;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		dlFileEntryConfiguration = ConfigurableUtil.createConfigurable(
			DLFileEntryConfiguration.class, properties);
	}

	protected abstract void generatePreview(FileVersion fileVersion)
		throws Exception;

	protected void generatePreviews(long companyId) throws PortalException {
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

		actionableDynamicQuery.performActions();
	}

	protected abstract String[] getMimeTypes();

	protected volatile DLFileEntryConfiguration dlFileEntryConfiguration;

	@Reference
	protected DLFileEntryLocalService dlFileEntryLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		BasePreviewBackgroundTaskExecutor.class);

}