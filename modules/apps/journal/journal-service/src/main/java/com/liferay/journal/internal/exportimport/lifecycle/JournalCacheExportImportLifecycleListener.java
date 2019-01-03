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

package com.liferay.journal.internal.exportimport.lifecycle;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lifecycle.EventAwareExportImportLifecycleListener;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleListener;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.util.JournalContent;
import com.liferay.portal.kernel.model.StagedModel;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(service = ExportImportLifecycleListener.class)
public class JournalCacheExportImportLifecycleListener
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
			PortletDataContext portletDataContext)
		throws Exception {

		clearCache();
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
			PortletDataContext portletDataContext)
		throws Exception {

		String rootPortletId = portletDataContext.getRootPortletId();

		if (!rootPortletId.equals(JournalPortletKeys.JOURNAL)) {
			return;
		}

		clearCache();
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

	protected void clearCache() {
		_journalContent.clearCache();
	}

	@Reference(unbind = "-")
	protected void setJournalContent(JournalContent journalContent) {
		_journalContent = journalContent;
	}

	private JournalContent _journalContent;

}