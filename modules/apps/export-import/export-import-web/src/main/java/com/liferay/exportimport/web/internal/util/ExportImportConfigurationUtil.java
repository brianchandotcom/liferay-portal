/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.web.internal.util;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Magdalena Jedraszak
 */
public class ExportImportConfigurationUtil {

	public static long getExportImportConfigurationId() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return 0;
		}

		long backgroundTaskId = 0;

		LiferayPortletRequest liferayPortletRequest =
			serviceContext.getLiferayPortletRequest();

		if (liferayPortletRequest != null) {
			backgroundTaskId = ParamUtil.getLong(
				liferayPortletRequest, "backgroundTaskId");

			if (backgroundTaskId == 0) {
				backgroundTaskId = ParamUtil.getLong(
					liferayPortletRequest, "importProcessId");
			}
		}

		if (backgroundTaskId == 0) {
			HttpServletRequest httpServletRequest = serviceContext.getRequest();

			if (httpServletRequest != null) {
				HttpServletRequest originalHttpServletRequest =
					PortalUtil.getOriginalServletRequest(httpServletRequest);

				backgroundTaskId = ParamUtil.getLong(
					originalHttpServletRequest, "backgroundTaskId");

				if (backgroundTaskId == 0) {
					backgroundTaskId = ParamUtil.getLong(
						originalHttpServletRequest, "importProcessId");
				}
			}
		}

		if (backgroundTaskId == 0) {
			return 0;
		}

		BackgroundTaskManager backgroundTaskManager =
			_backgroundTaskManagerSnapshot.get();

		BackgroundTask backgroundTask =
			backgroundTaskManager.fetchBackgroundTask(backgroundTaskId);

		if (backgroundTask == null) {
			return 0;
		}

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		return GetterUtil.getLong(
			taskContextMap.get("exportImportConfigurationId"));
	}

	private static final Snapshot<BackgroundTaskManager>
		_backgroundTaskManagerSnapshot = new Snapshot<>(
			ExportImportConfigurationUtil.class, BackgroundTaskManager.class);

}