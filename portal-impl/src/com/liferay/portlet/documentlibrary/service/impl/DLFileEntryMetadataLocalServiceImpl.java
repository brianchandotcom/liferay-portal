/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.service.base.DLFileEntryMetadataLocalServiceBaseImpl;
import com.liferay.portlet.dynamicdatamapping.StorageException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.FieldConstants;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMUtil;

import java.util.List;
import java.util.Map;

/**
 * @author Alexander Chow
 */
public class DLFileEntryMetadataLocalServiceImpl
	extends DLFileEntryMetadataLocalServiceBaseImpl {

	@Override
	public void deleteFileEntryMetadata(long fileEntryId)
		throws PortalException, SystemException {

		List<DLFileEntryMetadata> fileEntryMetadatas =
			dlFileEntryMetadataPersistence.findByFileEntryId(fileEntryId);

		for (DLFileEntryMetadata fileEntryMetadata : fileEntryMetadatas) {
			deleteFileEntryMetadata(fileEntryMetadata);
		}
	}

	@Override
	public void deleteFileVersionFileEntryMetadata(long fileVersionId)
		throws PortalException, SystemException {

		List<DLFileEntryMetadata> fileEntryMetadatas =
			dlFileEntryMetadataPersistence.findByFileVersionId(fileVersionId);

		for (DLFileEntryMetadata fileEntryMetadata : fileEntryMetadatas) {
			deleteFileEntryMetadata(fileEntryMetadata);
		}
	}

	@Override
	public DLFileEntryMetadata fetchFileEntryMetadata(long fileEntryMetadataId)
		throws SystemException {

		return dlFileEntryMetadataPersistence.fetchByPrimaryKey(
			fileEntryMetadataId);
	}

	@Override
	public DLFileEntryMetadata fetchFileEntryMetadata(
			long ddmStructureId, long fileVersionId)
		throws SystemException {

		return dlFileEntryMetadataPersistence.fetchByD_F(
			ddmStructureId, fileVersionId);
	}

	@Override
	public DLFileEntryMetadata getFileEntryMetadata(long fileEntryMetadataId)
		throws PortalException, SystemException {

		return dlFileEntryMetadataPersistence.findByPrimaryKey(
			fileEntryMetadataId);
	}

	@Override
	public DLFileEntryMetadata getFileEntryMetadata(
			long ddmStructureId, long fileVersionId)
		throws PortalException, SystemException {

		return dlFileEntryMetadataPersistence.findByD_F(
			ddmStructureId, fileVersionId);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #getFileVersionFileEntryMetadatasCount(long)}
	 */
	@Override
	public long getFileEntryMetadataCount(long fileEntryId, long fileVersionId)
		throws SystemException {

		return getFileVersionFileEntryMetadatasCount(fileVersionId);
	}

	@Override
	public List<DLFileEntryMetadata> getFileVersionFileEntryMetadatas(
			long fileVersionId)
		throws SystemException {

		return dlFileEntryMetadataPersistence.findByFileVersionId(
			fileVersionId);
	}

	@Override
	public long getFileVersionFileEntryMetadatasCount(long fileVersionId)
		throws SystemException {

		return dlFileEntryMetadataPersistence.countByFileVersionId(
			fileVersionId);
	}

	@Override
	public void updateFileEntryMetadata(
			long companyId, List<DDMStructure> ddmStructures,
			long fileEntryTypeId, long fileEntryId, long fileVersionId,
			Map<String, Fields> fieldsMap, ServiceContext serviceContext)
		throws PortalException, SystemException {

		for (DDMStructure ddmStructure : ddmStructures) {
			Fields fields = fieldsMap.get(ddmStructure.getStructureKey());

			if (fields != null) {
				updateFileEntryMetadata(
					companyId, ddmStructure, fileEntryTypeId, fileEntryId,
					fileVersionId, fields, serviceContext);
			}
		}
	}

	@Override
	public void updateFileEntryMetadata(
			long fileEntryTypeId, long fileEntryId, long fileVersionId,
			Map<String, Fields> fieldsMap, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntryType fileEntryType =
			dlFileEntryTypeLocalService.getFileEntryType(fileEntryTypeId);

		List<DDMStructure> ddmStructures = fileEntryType.getDDMStructures();

		updateFileEntryMetadata(
			fileEntryType.getCompanyId(), ddmStructures, fileEntryTypeId,
			fileEntryId, fileVersionId, fieldsMap, serviceContext);
	}

	protected void deleteFileEntryMetadata(
			DLFileEntryMetadata fileEntryMetadata)
		throws PortalException, SystemException {

		// FileEntry metadata

		dlFileEntryMetadataPersistence.remove(fileEntryMetadata);

		// Dynamic data mapping storage

		StorageEngineUtil.deleteByClass(fileEntryMetadata.getDDMStorageId());

		// Dynamic data mapping structure link

		ddmStructureLinkLocalService.deleteClassStructureLink(
			fileEntryMetadata.getFileEntryMetadataId());
	}

	protected void updateFileEntryMetadata(
			long companyId, DDMStructure ddmStructure, long fileEntryTypeId,
			long fileEntryId, long fileVersionId, Fields fields,
			ServiceContext serviceContext)
		throws StorageException, SystemException {

		DLFileEntryMetadata fileEntryMetadata =
			dlFileEntryMetadataPersistence.fetchByD_F(
				ddmStructure.getStructureId(), fileVersionId);

		if (fileEntryMetadata != null) {
			StorageEngineUtil.update(
				fileEntryMetadata.getDDMStorageId(), fields, true,
				serviceContext);
		}
		else {

			// File entry metadata

			long fileEntryMetadataId = counterLocalService.increment();

			fileEntryMetadata = dlFileEntryMetadataPersistence.create(
				fileEntryMetadataId);

			long ddmStorageId = StorageEngineUtil.create(
				companyId, ddmStructure.getStructureId(), fields,
				serviceContext);

			fileEntryMetadata.setDDMStorageId(ddmStorageId);

			fileEntryMetadata.setDDMStructureId(ddmStructure.getStructureId());
			fileEntryMetadata.setFileEntryTypeId(fileEntryTypeId);
			fileEntryMetadata.setFileEntryId(fileEntryId);
			fileEntryMetadata.setFileVersionId(fileVersionId);

			dlFileEntryMetadataPersistence.update(fileEntryMetadata);

			// Dynamic data mapping structure link

			long classNameId = PortalUtil.getClassNameId(
				DLFileEntryMetadata.class);

			ddmStructureLinkLocalService.addStructureLink(
				classNameId, fileEntryMetadata.getFileEntryMetadataId(),
				ddmStructure.getStructureId(), serviceContext);
		}

		try {
			String namespace = String.valueOf(ddmStructure.getStructureId());

			for (String fieldName : ddmStructure.getFieldNames()) {
				String fieldDataType = ddmStructure.getFieldDataType(fieldName);

				if (fieldDataType.equals(FieldConstants.FILE_UPLOAD)) {
					DDMUtil.uploadFieldFile(
						fileEntryMetadata.getDDMStructureId(),
						fileEntryMetadata.getDDMStorageId(), fileEntryMetadata,
						fieldName, namespace, serviceContext);
				}
			}
		}
		catch (Exception e) {
		}
	}

}