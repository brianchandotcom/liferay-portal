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
import com.liferay.portal.service.BackgroundTaskLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.http.LayoutServiceHttp;
import com.liferay.portal.service.http.StagingServiceHttp;
import com.liferay.portal.spring.transaction.TransactionHandlerUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

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
	public BackgroundTaskResult execute(BackgroundTask backgroundTask) {
		ExportImportConfiguration exportImportConfiguration =
			getExportImportConfiguration(backgroundTask);

		clearBackgroundTaskStatus(backgroundTask);

		MissingReferences missingReferences = null;

		try {
			ExportImportThreadLocal.setLayoutStagingInProcess(true);

			ExportImportLifecycleManager.fireExportImportLifecycleEvent(
				ExportImportLifecycleConstants.
					EVENT_PUBLICATION_LAYOUT_REMOTE_STARTED,
				exportImportConfiguration);

			missingReferences = TransactionHandlerUtil.invoke(
				transactionAttribute,
				new LayoutRemoteStagingCallable(
					backgroundTask.getBackgroundTaskId(),
					exportImportConfiguration));

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
		}

		return processMissingReferences(
			backgroundTask.getBackgroundTaskId(), missingReferences);
	}

	protected File exportLayoutsAsFile(
			long sourceGroupId, Map<Long, Boolean> layoutIdMap,
			Map<String, String[]> parameterMap, long remoteGroupId,
			Date startDate, Date endDate, HttpPrincipal httpPrincipal)
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
			sourceGroupId, layoutIds, parameterMap, startDate, endDate);
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
				layout.getGroupId(), parentLayoutId);

			try {
				LayoutServiceHttp.getLayoutByUuidAndGroupId(
					httpPrincipal, parentLayout.getUuid(), remoteGroupId);

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

	private class LayoutRemoteStagingCallable
		implements Callable<MissingReferences> {

		public LayoutRemoteStagingCallable(
			long backgroundTaskId,
			ExportImportConfiguration exportImportConfiguration) {

			_backgroundTaskId = backgroundTaskId;
			_exportImportConfiguration = exportImportConfiguration;
		}

		@Override
		public MissingReferences call() throws PortalException {
			long stagingRequestId = 0;

			File file = null;
			FileInputStream fileInputStream = null;
			HttpPrincipal httpPrincipal = null;
			MissingReferences missingReferences = null;

			try {
				Map<String, Serializable> settingsMap =
					_exportImportConfiguration.getSettingsMap();

				long sourceGroupId = MapUtil.getLong(
					settingsMap, "sourceGroupId");

				initThreadLocals(sourceGroupId);

				Map<Long, Boolean> layoutIdMap =
					(Map<Long, Boolean>)settingsMap.get("layoutIdMap");
				Map<String, String[]> parameterMap =
					(Map<String, String[]>)settingsMap.get("parameterMap");
				long remoteGroupId = MapUtil.getLong(
					settingsMap, "remoteGroupId");
				DateRange dateRange = ExportImportDateUtil.getDateRange(
					_exportImportConfiguration);

				BackgroundTask backgroundTask =
					BackgroundTaskLocalServiceUtil.getBackgroundTask(
						_backgroundTaskId);

				Map<String, Serializable> taskContextMap =
					backgroundTask.getTaskContextMap();

				httpPrincipal = (HttpPrincipal)taskContextMap.get(
					"httpPrincipal");

				file = exportLayoutsAsFile(
					sourceGroupId, layoutIdMap, parameterMap, remoteGroupId,
					dateRange.getStartDate(), dateRange.getEndDate(),
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
							httpPrincipal, stagingRequestId, fileName,
							tempBytes);
					}
					else {
						StagingServiceHttp.updateStagingRequest(
							httpPrincipal, stagingRequestId, fileName, bytes);
					}

					bytes =
						new byte[
							PropsValues.STAGING_REMOTE_TRANSFER_BUFFER_SIZE];
				}

				markBackgroundTask(_backgroundTaskId, "exported");

				StagingServiceHttp.publishStagingRequest(
					httpPrincipal, stagingRequestId, parameterMap);
			}
			catch (IOException ioe) {
				deleteTempLarOnFailure(file);

				throw new SystemException(ioe);
			}
			catch (Throwable t) {
				deleteTempLarOnFailure(file);

				throw t;
			}
			finally {
				StreamUtil.cleanUp(fileInputStream);

				if (stagingRequestId > 0) {
					StagingServiceHttp.cleanUpStagingRequest(
						httpPrincipal, stagingRequestId);
				}
			}

			deleteTempLarOnSuccess(file);

			return missingReferences;
		}

		private void deleteTempLarOnFailure(File file) {
			if (PropsValues.STAGING_DELETE_TEMP_LAR_ON_FAILURE) {
				FileUtil.delete(file);
			}
			else if ((file != null) && _log.isErrorEnabled()) {
				_log.error("Kept temporary LAR file " + file.getAbsolutePath());
			}
		}

		private void deleteTempLarOnSuccess(File file) {
			if (PropsValues.STAGING_DELETE_TEMP_LAR_ON_SUCCESS) {
				FileUtil.delete(file);
			}
			else if ((file != null) && _log.isDebugEnabled()) {
				_log.debug("Kept temporary LAR file " + file.getAbsolutePath());
			}
		}

		private final long _backgroundTaskId;
		private final ExportImportConfiguration _exportImportConfiguration;

	}

}