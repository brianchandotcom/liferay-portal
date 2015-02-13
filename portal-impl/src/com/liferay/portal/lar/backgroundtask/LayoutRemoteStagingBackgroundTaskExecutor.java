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

package com.liferay.portal.lar.backgroundtask;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.ExportImportDateUtil;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.lar.MissingReferences;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.lifecycle.ExportImportLifecycleConstants;
import com.liferay.portal.kernel.lar.lifecycle.ExportImportLifecycleManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.http.LayoutServiceHttp;
import com.liferay.portal.service.http.StagingServiceHttp;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
public class LayoutRemoteStagingBackgroundTaskExecutor
	extends BaseStagingBackgroundTaskExecutor {

	public LayoutRemoteStagingBackgroundTaskExecutor() {
		setBackgroundTaskStatusMessageTranslator(
			new LayoutStagingBackgroundTaskStatusMessageTranslator());
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws PortalException {

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long exportImportConfigurationId = MapUtil.getLong(
			taskContextMap, "exportImportConfigurationId");

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				getExportImportConfiguration(exportImportConfigurationId);

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long sourceGroupId = MapUtil.getLong(settingsMap, "sourceGroupId");
		boolean privateLayout = MapUtil.getBoolean(
			settingsMap, "privateLayout");

		initThreadLocals(sourceGroupId, privateLayout);

		Map<Long, Boolean> layoutIdMap = (Map<Long, Boolean>)settingsMap.get(
			"layoutIdMap");
		Map<String, String[]> parameterMap =
			(Map<String, String[]>)settingsMap.get("parameterMap");
		long remoteGroupId = MapUtil.getLong(settingsMap, "remoteGroupId");
		DateRange dateRange = ExportImportDateUtil.getDateRange(
			exportImportConfiguration,
			ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE);
		HttpPrincipal httpPrincipal = (HttpPrincipal)taskContextMap.get(
			"httpPrincipal");

		clearBackgroundTaskStatus(backgroundTask);

		long stagingRequestId = 0;

		File file = null;
		FileInputStream fileInputStream = null;
		MissingReferences missingReferences = null;

		try {
			ExportImportThreadLocal.setLayoutStagingInProcess(true);

			ExportImportLifecycleManager.fireExportImportLifecycleEvent(
				ExportImportLifecycleConstants.
					EVENT_PUBLICATION_LAYOUT_REMOTE_STARTED,
				exportImportConfiguration);

			file = exportLayoutsAsFile(
				sourceGroupId, privateLayout, layoutIdMap, parameterMap,
				remoteGroupId, dateRange.getStartDate(), dateRange.getEndDate(),
				httpPrincipal);

			String checksum = FileUtil.getMD5Checksum(file);

			fileInputStream = new FileInputStream(file);

			stagingRequestId = StagingServiceHttp.createStagingRequest(
				httpPrincipal, remoteGroupId, checksum);

			byte[] bytes =
				new byte[PropsValues.STAGING_REMOTE_TRANSFER_BUFFER_SIZE];

			int i = 0;
			int j = 0;

			String numberFormat = String.format(
				"%%0%dd",
				String.valueOf(
					(int)(file.length() / bytes.length)).length() + 1);

			while ((i = fileInputStream.read(bytes)) >= 0) {
				String fileName =
					file.getName() + String.format(numberFormat, j++);

				if (i < PropsValues.STAGING_REMOTE_TRANSFER_BUFFER_SIZE) {
					byte[] tempBytes = new byte[i];

					System.arraycopy(bytes, 0, tempBytes, 0, i);

					StagingServiceHttp.updateStagingRequest(
						httpPrincipal, stagingRequestId, fileName, tempBytes);
				}
				else {
					StagingServiceHttp.updateStagingRequest(
						httpPrincipal, stagingRequestId, fileName, bytes);
				}

				bytes =
					new byte[PropsValues.STAGING_REMOTE_TRANSFER_BUFFER_SIZE];
			}

			markBackgroundTask(
				backgroundTask.getBackgroundTaskId(), "exported");

			missingReferences = StagingServiceHttp.validateStagingRequest(
				httpPrincipal, stagingRequestId, privateLayout, parameterMap);

			markBackgroundTask(
				backgroundTask.getBackgroundTaskId(), "validated");

			StagingServiceHttp.publishStagingRequest(
				httpPrincipal, stagingRequestId, privateLayout, parameterMap);

			boolean updateLastPublishDate = MapUtil.getBoolean(
				parameterMap, PortletDataHandlerKeys.UPDATE_LAST_PUBLISH_DATE);

			if (updateLastPublishDate) {
				ExportImportDateUtil.updateLastPublishDate(
					sourceGroupId, privateLayout, dateRange,
					dateRange.getEndDate());
			}

			ExportImportLifecycleManager.fireExportImportLifecycleEvent(
				ExportImportLifecycleConstants.
					EVENT_PUBLICATION_LAYOUT_REMOTE_SUCCEEDED,
				exportImportConfiguration);
		}
		catch (Throwable t) {
			ExportImportLifecycleManager.fireExportImportLifecycleEvent(
				ExportImportLifecycleConstants.
					EVENT_PUBLICATION_LAYOUT_REMOTE_FAILED,
				exportImportConfiguration);

			if (_log.isDebugEnabled()) {
				_log.debug(t, t);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn("Unable to publish layout: " + t.getMessage());
			}

			throw new SystemException(t);
		}
		finally {
			ExportImportThreadLocal.setLayoutStagingInProcess(false);

			StreamUtil.cleanUp(fileInputStream);

			FileUtil.delete(file);

			if (stagingRequestId > 0) {
				StagingServiceHttp.cleanUpStagingRequest(
					httpPrincipal, stagingRequestId);
			}
		}

		return processMissingReferences(
			backgroundTask.getBackgroundTaskId(), missingReferences);
	}

	protected File exportLayoutsAsFile(
			long sourceGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
			long remoteGroupId, Date startDate, Date endDate,
			HttpPrincipal httpPrincipal)
		throws PortalException {

		List<Layout> layouts = new ArrayList<>();

		if (layoutIdMap != null) {
			for (Map.Entry<Long, Boolean> entry : layoutIdMap.entrySet()) {
				long plid = GetterUtil.getLong(String.valueOf(entry.getKey()));
				boolean includeChildren = entry.getValue();

				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				if (!layouts.contains(layout)) {
					layouts.add(layout);
				}

				List<Layout> parentLayouts = getMissingRemoteParentLayouts(
					httpPrincipal, layout, remoteGroupId);

				for (Layout parentLayout : parentLayouts) {
					if (!layouts.contains(parentLayout)) {
						layouts.add(parentLayout);
					}
				}

				if (includeChildren) {
					for (Layout childLayout : layout.getAllChildren()) {
						if (!layouts.contains(childLayout)) {
							layouts.add(childLayout);
						}
					}
				}
			}
		}

		long[] layoutIds = ExportImportHelperUtil.getLayoutIds(layouts);

		return LayoutLocalServiceUtil.exportLayoutsAsFile(
			sourceGroupId, privateLayout, layoutIds, parameterMap, startDate,
			endDate);
	}

	/**
	 * @see com.liferay.portal.lar.ExportImportHelperImpl#getMissingParentLayouts(
	 *      Layout, long)
	 */
	protected List<Layout> getMissingRemoteParentLayouts(
			HttpPrincipal httpPrincipal, Layout layout, long remoteGroupId)
		throws PortalException {

		List<Layout> missingRemoteParentLayouts = new ArrayList<>();

		long parentLayoutId = layout.getParentLayoutId();

		while (parentLayoutId > 0) {
			Layout parentLayout = LayoutLocalServiceUtil.getLayout(
				layout.getGroupId(), layout.isPrivateLayout(), parentLayoutId);

			try {
				LayoutServiceHttp.getLayoutByUuidAndGroupId(
					httpPrincipal, parentLayout.getUuid(), remoteGroupId,
					parentLayout.getPrivateLayout());

				// If one parent is found, all others are assumed to exist

				break;
			}
			catch (NoSuchLayoutException nsle) {
				missingRemoteParentLayouts.add(parentLayout);

				parentLayoutId = parentLayout.getParentLayoutId();
			}
		}

		return missingRemoteParentLayouts;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutRemoteStagingBackgroundTaskExecutor.class);

}