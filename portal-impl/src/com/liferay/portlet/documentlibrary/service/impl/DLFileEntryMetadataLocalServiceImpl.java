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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureLinkManagerUtil;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManagerUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portlet.documentlibrary.service.base.DLFileEntryMetadataLocalServiceBaseImpl;

import java.util.List;
import java.util.Map;

/**
 * @author Alexander Chow
 */
public class DLFileEntryMetadataLocalServiceImpl
	extends DLFileEntryMetadataLocalServiceBaseImpl {

	@Override
	public void deleteFileEntryMetadata(DLFileEntryMetadata fileEntryMetadata)
		throws PortalException {

		// File entry metadata

		dlFileEntryMetadataPersistence.remove(fileEntryMetadata);

		// Dynamic data mapping storage

		StorageEngineManagerUtil.deleteByClass(
			fileEntryMetadata.getDDMStorageId());

		// Dynamic data mapping structure link

		long classNameId = classNameLocalService.getClassNameId(
			DLFileEntryMetadata.class);

		DDMStructureLinkManagerUtil.deleteStructureLinks(
			classNameId, fileEntryMetadata.getFileEntryMetadataId());
	}

	@Override
	public void deleteFileEntryMetadata(long fileEntryId)
		throws PortalException {

		List<DLFileEntryMetadata> fileEntryMetadatas =
			dlFileEntryMetadataPersistence.findByFileEntryId(fileEntryId);

		for (DLFileEntryMetadata fileEntryMetadata : fileEntryMetadatas) {
			deleteFileEntryMetadata(fileEntryMetadata);
		}
	}

	@Override
	public void deleteFileVersionFileEntryMetadata(long fileVersionId)
		throws PortalException {

		List<DLFileEntryMetadata> fileEntryMetadatas =
			dlFileEntryMetadataPersistence.findByFileVersionId(fileVersionId);

		for (DLFileEntryMetadata fileEntryMetadata : fileEntryMetadatas) {
			deleteFileEntryMetadata(fileEntryMetadata);
		}
	}

	@Override
	public DLFileEntryMetadata fetchFileEntryMetadata(
		long fileEntryMetadataId) {

		return dlFileEntryMetadataPersistence.fetchByPrimaryKey(
			fileEntryMetadataId);
	}

	@Override
	public DLFileEntryMetadata fetchFileEntryMetadata(
		long ddmStructureId, long fileVersionId) {

		return dlFileEntryMetadataPersistence.fetchByD_F(
			ddmStructureId, fileVersionId);
	}

	@Override
	public DLFileEntryMetadata getFileEntryMetadata(long fileEntryMetadataId)
		throws PortalException {

		return dlFileEntryMetadataPersistence.findByPrimaryKey(
			fileEntryMetadataId);
	}

	@Override
	public DLFileEntryMetadata getFileEntryMetadata(
			long ddmStructureId, long fileVersionId)
		throws PortalException {

		return dlFileEntryMetadataPersistence.findByD_F(
			ddmStructureId, fileVersionId);
	}

	@Override
	public List<DLFileEntryMetadata> getFileVersionFileEntryMetadatas(
		long fileVersionId) {

		return dlFileEntryMetadataPersistence.findByFileVersionId(
			fileVersionId);
	}

	@Override
	public long getFileVersionFileEntryMetadatasCount(long fileVersionId) {
		return dlFileEntryMetadataPersistence.countByFileVersionId(
			fileVersionId);
	}

	@Override
	public List<DLFileEntryMetadata>
		getMismatchedCompanyIdFileEntryMetadatas() {

		return dlFileEntryMetadataFinder.findByMismatchedCompanyId();
	}

	@Override
	public List<DLFileEntryMetadata> getNoStructuresFileEntryMetadatas() {
		return dlFileEntryMetadataFinder.findByNoStructures();
	}

	@Override
	public void updateFileEntryMetadata(
			long companyId, List<DDMStructure> ddmStructures, long fileEntryId,
			long fileVersionId, Map<String, DDMFormValues> ddmFormValuesMap,
			ServiceContext serviceContext)
		throws PortalException {

		for (DDMStructure ddmStructure : ddmStructures) {
			DDMFormValues ddmFormValues = ddmFormValuesMap.get(
				ddmStructure.getStructureKey());

			if (ddmFormValues != null) {
				updateFileEntryMetadata(
					companyId, ddmStructure, fileEntryId, fileVersionId,
					ddmFormValues, serviceContext);
			}
		}
	}

	@Override
	public void updateFileEntryMetadata(
			long fileEntryTypeId, long fileEntryId, long fileVersionId,
			Map<String, DDMFormValues> ddmFormValuesMap,
			ServiceContext serviceContext)
		throws PortalException {

		DLFileEntryType fileEntryType =
			dlFileEntryTypeLocalService.getFileEntryType(fileEntryTypeId);

		updateFileEntryMetadata(
			fileEntryType.getCompanyId(), fileEntryType.getDDMStructures(),
			fileEntryId, fileVersionId, ddmFormValuesMap, serviceContext);
	}

	protected void updateFileEntryMetadata(
			long companyId, DDMStructure ddmStructure, long fileEntryId,
			long fileVersionId, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws PortalException {

		DLFileEntryMetadata fileEntryMetadata =
			dlFileEntryMetadataPersistence.fetchByD_F(
				ddmStructure.getStructureId(), fileVersionId);

		if (fileEntryMetadata != null) {
			StorageEngineManagerUtil.update(
				fileEntryMetadata.getDDMStorageId(), ddmFormValues,
				serviceContext);
		}
		else {

			// File entry metadata

			long fileEntryMetadataId = counterLocalService.increment();

			fileEntryMetadata = dlFileEntryMetadataPersistence.create(
				fileEntryMetadataId);

			long ddmStorageId = StorageEngineManagerUtil.create(
				companyId, ddmStructure.getStructureId(), ddmFormValues,
				serviceContext);

			fileEntryMetadata.setDDMStorageId(ddmStorageId);

			fileEntryMetadata.setDDMStructureId(ddmStructure.getStructureId());
			fileEntryMetadata.setFileEntryId(fileEntryId);
			fileEntryMetadata.setFileVersionId(fileVersionId);

			dlFileEntryMetadataPersistence.update(fileEntryMetadata);

			// Dynamic data mapping structure link

			long classNameId = classNameLocalService.getClassNameId(
				DLFileEntryMetadata.class);

			DDMStructureLinkManagerUtil.addStructureLink(
				classNameId, fileEntryMetadata.getFileEntryMetadataId(),
				ddmStructure.getStructureId());
		}
	}

}