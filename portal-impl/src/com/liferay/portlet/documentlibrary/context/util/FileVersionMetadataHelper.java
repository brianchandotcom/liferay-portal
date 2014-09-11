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

package com.liferay.portlet.documentlibrary.context.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngine;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author Ivan Zaera
 */
public class FileVersionMetadataHelper {

	public FileVersionMetadataHelper(FileVersion fileVersion) {
		this(
			fileVersion, DLFileEntryMetadataLocalServiceUtil.getService(),
			DLFileEntryTypeLocalServiceUtil.getService(),
			StorageEngineUtil.getStorageEngine());
	}

	public FileVersionMetadataHelper(
		FileVersion fileVersion,
		DLFileEntryMetadataLocalService dlFileEntryMetadataLocalService,
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService,
		StorageEngine storageEngine) {

		_fileVersion = fileVersion;
		_dlFileEntryMetadataLocalService = dlFileEntryMetadataLocalService;
		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
		_storageEngine = storageEngine;
	}

	public List<DDMStructure> getDDMStructures() throws PortalException {
		if (_ddmStructures == null) {
			long fileEntryTypeId = _getFileEntryTypeId();

			if (fileEntryTypeId != -1) {
				DLFileEntryType dlFileEntryType =
					_dlFileEntryTypeLocalService.getFileEntryType(
						fileEntryTypeId);

				_ddmStructures = dlFileEntryType.getDDMStructures();
			}
			else {
				_ddmStructures = Collections.emptyList();
			}
		}

		return _ddmStructures;
	}

	public Fields getFields(DDMStructure ddmStructure) throws PortalException {
		if (_ddmStructures == null) {
			getDDMStructures();
		}

		if (!_ddmStructures.contains(ddmStructure)) {
			throw new IllegalArgumentException(
				"DDM structure does not belong to this file version");
		}

		DLFileEntryMetadata dlFileEntryMetadata =
			_dlFileEntryMetadataLocalService.getFileEntryMetadata(
				ddmStructure.getStructureId(), _fileVersion.getFileVersionId());

		return _storageEngine.getFields(dlFileEntryMetadata.getDDMStorageId());
	}

	private long _getFileEntryTypeId() {
		if (_fileVersion.getModel() instanceof DLFileVersion) {
			DLFileVersion dlFileVersion =
				(DLFileVersion)_fileVersion.getModel();

			return dlFileVersion.getFileEntryTypeId();
		}

		return -1;
	}

	private List<DDMStructure> _ddmStructures;
	private DLFileEntryMetadataLocalService _dlFileEntryMetadataLocalService;
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;
	private FileVersion _fileVersion;
	private StorageEngine _storageEngine;

}