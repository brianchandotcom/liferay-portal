/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.web.internal.frontend.data.set.filter;

import com.liferay.frontend.data.set.constants.FDSEntityFieldTypes;
import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Magdalena Jedraszak
 */
public abstract class BaseImportReportEntrySelectionFDSFilter
	extends BaseSelectionFDSFilter {

	@Override
	public String getEntityFieldType() {
		return FDSEntityFieldTypes.STRING;
	}

	@Override
	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems(
		Locale locale) {

		long exportImportConfigurationId = _getExportImportConfigurationId();

		if (exportImportConfigurationId == 0) {
			return Collections.emptyList();
		}

		return doGetSelectionFDSFilterItems(
			exportImportConfigurationId, locale);
	}

	protected abstract List<SelectionFDSFilterItem>
		doGetSelectionFDSFilterItems(
			long exportImportConfigurationId, Locale locale);

	@Reference
	protected BackgroundTaskManager backgroundTaskManager;

	private long _getExportImportConfigurationId() {
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

}