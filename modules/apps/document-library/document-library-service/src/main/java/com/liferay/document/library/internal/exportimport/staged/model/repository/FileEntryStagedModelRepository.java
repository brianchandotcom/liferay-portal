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

package com.liferay.document.library.internal.exportimport.staged.model.repository;

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = StagedModelRepository.class
)
public class FileEntryStagedModelRepository
	implements StagedModelRepository<FileEntry> {

	@Override
	public FileEntry addStagedModel(
			PortletDataContext portletDataContext, FileEntry fileEntry)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(FileEntry fileEntry) throws PortalException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public FileEntry fetchMissingReference(String uuid, long groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public FileEntry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<FileEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public FileEntry getStagedModel(long fileEntryId) throws PortalException {
		return _dlAppLocalService.getFileEntry(fileEntryId);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, FileEntry fileEntry)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public FileEntry saveStagedModel(FileEntry fileEntry)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public FileEntry updateStagedModel(
			PortletDataContext portletDataContext, FileEntry fileEntry)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Reference
	private DLAppLocalService _dlAppLocalService;

}