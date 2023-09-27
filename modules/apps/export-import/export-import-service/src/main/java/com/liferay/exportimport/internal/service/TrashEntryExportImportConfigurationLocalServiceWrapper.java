/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.service;

import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalServiceWrapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.trash.exception.RestoreEntryException;
import com.liferay.trash.exception.TrashEntryException;
import com.liferay.trash.model.TrashEntry;
import com.liferay.trash.service.TrashEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = ServiceWrapper.class)
public class TrashEntryExportImportConfigurationLocalServiceWrapper
	extends ExportImportConfigurationLocalServiceWrapper {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ExportImportConfiguration moveExportImportConfigurationToTrash(
			long userId, long exportImportConfigurationId)
		throws PortalException {

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.getExportImportConfiguration(
				exportImportConfigurationId);

		if (exportImportConfiguration.isInTrash()) {
			throw new TrashEntryException();
		}

		int oldStatus = exportImportConfiguration.getStatus();

		exportImportConfiguration = updateStatus(
			userId, exportImportConfiguration.getExportImportConfigurationId(),
			WorkflowConstants.STATUS_IN_TRASH);

		_trashEntryLocalService.addTrashEntry(
			userId, exportImportConfiguration.getGroupId(),
			ExportImportConfiguration.class.getName(),
			exportImportConfiguration.getExportImportConfigurationId(), null,
			null, oldStatus, null, null);

		return exportImportConfiguration;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ExportImportConfiguration restoreExportImportConfigurationFromTrash(
			long userId, long exportImportConfigurationId)
		throws PortalException {

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.getExportImportConfiguration(
				exportImportConfigurationId);

		if (!exportImportConfiguration.isInTrash()) {
			throw new RestoreEntryException(
				RestoreEntryException.INVALID_STATUS);
		}

		TrashEntry trashEntry = _trashEntryLocalService.getEntry(
			ExportImportConfiguration.class.getName(),
			exportImportConfigurationId);

		exportImportConfiguration = updateStatus(
			userId, exportImportConfiguration.getExportImportConfigurationId(),
			trashEntry.getStatus());

		_trashEntryLocalService.deleteEntry(
			ExportImportConfiguration.class.getName(),
			exportImportConfiguration.getExportImportConfigurationId());

		return exportImportConfiguration;
	}

	@Reference
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Reference
	private TrashEntryLocalService _trashEntryLocalService;

}