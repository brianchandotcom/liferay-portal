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

import static com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleConstants.EVENT_PUBLICATION_LAYOUT_REMOTE_FAILED;
import static com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleConstants.EVENT_PUBLICATION_LAYOUT_REMOTE_STARTED;
import static com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleConstants.EVENT_PUBLICATION_LAYOUT_REMOTE_SUCCEEDED;
import static com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleConstants.PROCESS_FLAG_LAYOUT_STAGING_IN_PROCESS;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ClassLoaderUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.http.LayoutServiceHttp;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.exportimport.lar.ExportImportHelperUtil;
import com.liferay.portlet.exportimport.lar.ExportImportThreadLocal;
import com.liferay.portlet.exportimport.lar.MissingReferences;
import com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleManagerUtil;
import com.liferay.portlet.exportimport.model.ExportImportConfiguration;
import com.liferay.portlet.exportimport.service.ExportImportLocalServiceUtil;
import com.liferay.portlet.exportimport.service.http.StagingServiceHttp;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;

import java.util.ArrayList;
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
	public BackgroundTaskExecutor clone() {
		LayoutRemoteStagingBackgroundTaskExecutor
			layoutRemoteStagingBackgroundTaskExecutor =
				new LayoutRemoteStagingBackgroundTaskExecutor();

		layoutRemoteStagingBackgroundTaskExecutor.
			setBackgroundTaskStatusMessageTranslator(
				getBackgroundTaskStatusMessageTranslator());
		layoutRemoteStagingBackgroundTaskExecutor.setIsolationLevel(
			getIsolationLevel());

		return layoutRemoteStagingBackgroundTaskExecutor;
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask) {
		ExportImportConfiguration exportImportConfiguration =
			getExportImportConfiguration(backgroundTask);

		clearBackgroundTaskStatus(backgroundTask);

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		File file = null;
		HttpPrincipal httpPrincipal = null;
		MissingReferences missingReferences = null;
		long stagingRequestId = 0L;

		try {
			currentThread.setContextClassLoader(
				ClassLoaderUtil.getPortalClassLoader());

			ExportImportThreadLocal.setLayoutStagingInProcess(true);

			ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
				EVENT_PUBLICATION_LAYOUT_REMOTE_STARTED,
				PROCESS_FLAG_LAYOUT_STAGING_IN_PROCESS,
				exportImportConfiguration);

			Map<String, Serializable> settingsMap =
				exportImportConfiguration.getSettingsMap();

			long sourceGroupId = MapUtil.getLong(settingsMap, "sourceGroupId");
			boolean privateLayout = MapUtil.getBoolean(
				settingsMap, "privateLayout");

			initThreadLocals(sourceGroupId, privateLayout);

			Map<Long, Boolean> layoutIdMap =
				(Map<Long, Boolean>)settingsMap.get("layoutIdMap");
			long targetGroupId = MapUtil.getLong(settingsMap, "targetGroupId");

			Map<String, Serializable> taskContextMap =
				backgroundTask.getTaskContextMap();

			httpPrincipal = (HttpPrincipal)taskContextMap.get("httpPrincipal");

			file = exportLayoutsAsFile(
				exportImportConfiguration, layoutIdMap, targetGroupId,
				httpPrincipal);

			String checksum = FileUtil.getMD5Checksum(file);

			stagingRequestId = StagingServiceHttp.createStagingRequest(
				httpPrincipal, targetGroupId, checksum);

			transferFileToRemoteLive(file, stagingRequestId, httpPrincipal);

			markBackgroundTask(
				backgroundTask.getBackgroundTaskId(), "exported");

			missingReferences = StagingServiceHttp.publishStagingRequest(
				httpPrincipal, stagingRequestId, exportImportConfiguration);

			ExportImportThreadLocal.setLayoutStagingInProcess(false);

			ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
				EVENT_PUBLICATION_LAYOUT_REMOTE_SUCCEEDED,
				PROCESS_FLAG_LAYOUT_STAGING_IN_PROCESS,
				exportImportConfiguration);
		}
		catch (Throwable t) {
			ExportImportThreadLocal.setLayoutStagingInProcess(false);

			ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
				EVENT_PUBLICATION_LAYOUT_REMOTE_FAILED,
				PROCESS_FLAG_LAYOUT_STAGING_IN_PROCESS,
				exportImportConfiguration);

			if (_log.isDebugEnabled()) {
				_log.debug(t, t);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn("Unable to publish layout: " + t.getMessage());
			}

			deleteTempLarOnFailure(file);

			throw new SystemException(t);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);

			if ((stagingRequestId > 0) && (httpPrincipal != null)) {
				try {
					StagingServiceHttp.cleanUpStagingRequest(
						httpPrincipal, stagingRequestId);
				}
				catch (PortalException pe) {
					_log.warn("Unable to clean up the remote live site");
				}
			}
		}

		deleteTempLarOnSuccess(file);

		return processMissingReferences(
			backgroundTask.getBackgroundTaskId(), missingReferences);
	}

	protected File exportLayoutsAsFile(
			ExportImportConfiguration exportImportConfiguration,
			Map<Long, Boolean> layoutIdMap, long remoteGroupId,
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

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		settingsMap.remove("layoutIdMap");

		settingsMap.put("layoutIds", layoutIds);

		return ExportImportLocalServiceUtil.exportLayoutsAsFile(
			exportImportConfiguration);
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

	protected void transferFileToRemoteLive(
			File file, long stagingRequestId, HttpPrincipal httpPrincipal)
		throws Exception {

		byte[] bytes =
			new byte[PropsValues.STAGING_REMOTE_TRANSFER_BUFFER_SIZE];

		int i = 0;
		int j = 0;

		String numberFormat = String.format(
			"%%0%dd",
			String.valueOf((int) (file.length() / bytes.length)).length() + 1);

		FileInputStream fileInputStream = null;

		try {
			fileInputStream = new FileInputStream(file);

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
		}
		finally {
			StreamUtil.cleanUp(fileInputStream);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutRemoteStagingBackgroundTaskExecutor.class);

}