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
import com.liferay.portal.kernel.exception.PortalException;
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

		_importedFolderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DLFolder.class);

		if (MapUtil.isEmpty(_importedFolderIds)) {
			return;
		}

		_processedFolderIds = new HashSet<>();

		processFolderIds(_importedFolderIds.values());
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

		_importedFolderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DLFolder.class);

		if (MapUtil.isEmpty(_importedFolderIds)) {
			return;
		}

		_processedFolderIds = new HashSet<>();

		processFolderIds(_importedFolderIds.values());
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

	protected DLFolder getProcessableRootFolder(DLFolder dlFolder)
		throws PortalException {

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

		return getProcessableRootFolder(parentFolder);
	}

	protected void processFolderIds(Collection<Long> folderIds)
		throws PortalException {

		for (Long folderId : folderIds) {
			DLFolder dlFolder = _dlFolderLocalService.fetchDLFolder(folderId);

			DLFolder rootFolder = getProcessableRootFolder(dlFolder);

			if (rootFolder != null) {
				_dlFileEntryTypeLocalService.cascadeFileEntryTypes(
					rootFolder.getUserId(), rootFolder);
			}
		}
	}

	@Reference(unbind = "-")
	protected void setDLFileEntryTypeLocalService(
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService) {

		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
	}

	@Reference(unbind = "-")
	protected void setDlFolderLocalService(
		DLFolderLocalService dlFolderLocalService) {

		_dlFolderLocalService = dlFolderLocalService;
	}

	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;
	private DLFolderLocalService _dlFolderLocalService;
	private Map<Long, Long> _importedFolderIds;
	private Set<Long> _processedFolderIds;

}