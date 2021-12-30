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

package com.liferay.document.library.web.internal.exportimport.lifecycle;

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lifecycle.EventAwareExportImportLifecycleListener;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleListener;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(service = ExportImportLifecycleListener.class)
public class CascadeFileEntryTypesExportImportLifecycleListener
	implements EventAwareExportImportLifecycleListener {

	@Override
	public boolean isParallel() {
		return true;
	}

	@Override
	public void onLayoutExportFailed(
		PortletDataContext portletDataContext, Throwable throwable) {
	}

	@Override
	public void onLayoutExportStarted(PortletDataContext portletDataContext) {
	}

	@Override
	public void onLayoutExportSucceeded(PortletDataContext portletDataContext) {
	}

	@Override
	public void onLayoutImportFailed(
		PortletDataContext portletDataContext, Throwable throwable) {
	}

	@Override
	public void onLayoutImportProcessFinished(
			PortletDataContext portletDataContext)
		throws Exception {

		_importedFolderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DLFolder.class);

		if (MapUtil.isEmpty(_importedFolderIds)) {
			return;
		}

		_processedFolderIds = new HashSet<>();

		_processFolderIds(_importedFolderIds.values());
	}

	@Override
	public void onLayoutImportStarted(PortletDataContext portletDataContext) {
	}

	@Override
	public void onLayoutImportSucceeded(PortletDataContext portletDataContext) {
	}

	@Override
	public void onLayoutLocalPublicationFailed(
		ExportImportConfiguration exportImportConfiguration,
		Throwable throwable) {
	}

	@Override
	public void onLayoutLocalPublicationStarted(
		ExportImportConfiguration exportImportConfiguration) {
	}

	@Override
	public void onLayoutLocalPublicationSucceeded(
		ExportImportConfiguration exportImportConfiguration) {
	}

	@Override
	public void onLayoutRemotePublicationFailed(
		ExportImportConfiguration exportImportConfiguration,
		Throwable throwable) {
	}

	@Override
	public void onLayoutRemotePublicationStarted(
		ExportImportConfiguration exportImportConfiguration) {
	}

	@Override
	public void onLayoutRemotePublicationSucceeded(
		ExportImportConfiguration exportImportConfiguration) {
	}

	@Override
	public void onPortletExportFailed(
		PortletDataContext portletDataContext, Throwable throwable) {
	}

	@Override
	public void onPortletExportStarted(PortletDataContext portletDataContext) {
	}

	@Override
	public void onPortletExportSucceeded(
		PortletDataContext portletDataContext) {
	}

	@Override
	public void onPortletImportFailed(
		PortletDataContext portletDataContext, Throwable throwable) {
	}

	@Override
	public void onPortletImportProcessFinished(
			PortletDataContext portletDataContext)
		throws Exception {

		_importedFolderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DLFolder.class);

		if (MapUtil.isEmpty(_importedFolderIds)) {
			return;
		}

		_processedFolderIds = new HashSet<>();

		_processFolderIds(_importedFolderIds.values());
	}

	@Override
	public void onPortletImportStarted(PortletDataContext portletDataContext) {
	}

	@Override
	public void onPortletImportSucceeded(
		PortletDataContext portletDataContext) {
	}

	@Override
	public void onPortletPublicationFailed(
		ExportImportConfiguration exportImportConfiguration,
		Throwable throwable) {
	}

	@Override
	public void onPortletPublicationStarted(
		ExportImportConfiguration exportImportConfiguration) {
	}

	@Override
	public void onPortletPublicationSucceeded(
		ExportImportConfiguration exportImportConfiguration) {
	}

	@Override
	public void onStagedModelExportFailed(
		PortletDataContext portletDataContext, StagedModel stagedModel,
		Throwable throwable) {
	}

	@Override
	public void onStagedModelExportStarted(
		PortletDataContext portletDataContext, StagedModel stagedModel) {
	}

	@Override
	public void onStagedModelExportSucceeded(
		PortletDataContext portletDataContext, StagedModel stagedModel) {
	}

	@Override
	public void onStagedModelImportFailed(
		PortletDataContext portletDataContext, StagedModel stagedModel,
		Throwable throwable) {
	}

	@Override
	public void onStagedModelImportStarted(
		PortletDataContext portletDataContext, StagedModel stagedModel) {
	}

	@Override
	public void onStagedModelImportSucceeded(
		PortletDataContext portletDataContext, StagedModel stagedModel) {
	}

	private DLFolder _getProcessableRootFolder(DLFolder dlFolder)
		throws Exception {

		long dlFolderId = dlFolder.getFolderId();

		if (_processedFolderIds.contains(dlFolderId)) {
			return null;
		}

		_processedFolderIds.add(dlFolderId);

		DLFolder parentFolder = dlFolder.getParentFolder();

		if ((parentFolder == null) ||
			!_importedFolderIds.containsValue(parentFolder.getFolderId())) {

			return dlFolder;
		}

		return _getProcessableRootFolder(parentFolder);
	}

	private void _processFolderIds(Collection<Long> folderIds)
		throws Exception {

		for (Long folderId : folderIds) {
			DLFolder dlFolder = _dlFolderLocalService.fetchDLFolder(folderId);

			DLFolder rootFolder = _getProcessableRootFolder(dlFolder);

			if (rootFolder != null) {
				_dlFileEntryTypeLocalService.cascadeFileEntryTypes(
					rootFolder.getUserId(), rootFolder);
			}
		}
	}

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private DLFolderLocalService _dlFolderLocalService;

	private Map<Long, Long> _importedFolderIds;
	private Set<Long> _processedFolderIds;

}