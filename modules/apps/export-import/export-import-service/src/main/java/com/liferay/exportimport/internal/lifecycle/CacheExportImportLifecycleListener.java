/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.lifecycle;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lifecycle.EventAwareExportImportLifecycleListener;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleListener;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.servlet.filters.cache.CacheUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Máté Thurzó
 */
@Component(service = ExportImportLifecycleListener.class)
public class CacheExportImportLifecycleListener
	implements EventAwareExportImportLifecycleListener {

	@Override
	public boolean isParallel() {
		return false;
	}

	@Override
	public void onLayoutExportFailed(
			PortletDataContext portletDataContext, Throwable throwable)
		throws Exception {
	}

	@Override
	public void onLayoutExportStarted(PortletDataContext portletDataContext)
		throws Exception {
	}

	@Override
	public void onLayoutExportSucceeded(PortletDataContext portletDataContext)
		throws Exception {
	}

	@Override
	public void onLayoutImportFailed(
			PortletDataContext portletDataContext, Throwable throwable)
		throws Exception {
	}

	@Override
	public void onLayoutImportProcessFinished(
		PortletDataContext portletDataContext) {

		_clearCache();
	}

	@Override
	public void onLayoutImportStarted(PortletDataContext portletDataContext)
		throws Exception {
	}

	@Override
	public void onLayoutImportSucceeded(PortletDataContext portletDataContext)
		throws Exception {
	}

	@Override
	public void onLayoutLocalPublicationFailed(
			ExportImportConfiguration exportImportConfiguration,
			Throwable throwable)
		throws Exception {
	}

	@Override
	public void onLayoutLocalPublicationStarted(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {
	}

	@Override
	public void onLayoutLocalPublicationSucceeded(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {
	}

	@Override
	public void onLayoutRemotePublicationFailed(
			ExportImportConfiguration exportImportConfiguration,
			Throwable throwable)
		throws Exception {
	}

	@Override
	public void onLayoutRemotePublicationStarted(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {
	}

	@Override
	public void onLayoutRemotePublicationSucceeded(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {
	}

	@Override
	public void onPortletExportFailed(
			PortletDataContext portletDataContext, Throwable throwable)
		throws Exception {
	}

	@Override
	public void onPortletExportStarted(PortletDataContext portletDataContext)
		throws Exception {
	}

	@Override
	public void onPortletExportSucceeded(PortletDataContext portletDataContext)
		throws Exception {
	}

	@Override
	public void onPortletImportFailed(
			PortletDataContext portletDataContext, Throwable throwable)
		throws Exception {
	}

	@Override
	public void onPortletImportProcessFinished(
		PortletDataContext portletDataContext) {

		_clearCache();
	}

	@Override
	public void onPortletImportStarted(PortletDataContext portletDataContext)
		throws Exception {
	}

	@Override
	public void onPortletImportSucceeded(PortletDataContext portletDataContext)
		throws Exception {
	}

	@Override
	public void onPortletPublicationFailed(
			ExportImportConfiguration exportImportConfiguration,
			Throwable throwable)
		throws Exception {
	}

	@Override
	public void onPortletPublicationStarted(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {
	}

	@Override
	public void onPortletPublicationSucceeded(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {
	}

	@Override
	public void onStagedModelExportFailed(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			Throwable throwable)
		throws Exception {
	}

	@Override
	public void onStagedModelExportStarted(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception {
	}

	@Override
	public void onStagedModelExportSucceeded(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception {
	}

	@Override
	public void onStagedModelImportFailed(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			Throwable throwable)
		throws Exception {
	}

	@Override
	public void onStagedModelImportStarted(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception {
	}

	@Override
	public void onStagedModelImportSucceeded(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception {
	}

	private void _clearCache() {
		CacheUtil.clearCache();
		PermissionCacheUtil.clearCache();
	}

}