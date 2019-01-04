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

package com.liferay.document.library.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

/**
 * <p>
 * This class is a wrapper for {@link DLFileEntryMetadata}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryMetadata
 * @generated
 */
@ProviderType
public class DLFileEntryMetadataWrapper extends BaseModelWrapper<DLFileEntryMetadata>
	implements DLFileEntryMetadata, ModelWrapper<DLFileEntryMetadata> {
	public DLFileEntryMetadataWrapper(DLFileEntryMetadata dlFileEntryMetadata) {
		super(dlFileEntryMetadata);
	}

	/**
	* Returns the company ID of this document library file entry metadata.
	*
	* @return the company ID of this document library file entry metadata
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the ddm storage ID of this document library file entry metadata.
	*
	* @return the ddm storage ID of this document library file entry metadata
	*/
	@Override
	public long getDDMStorageId() {
		return model.getDDMStorageId();
	}

	/**
	* Returns the ddm structure ID of this document library file entry metadata.
	*
	* @return the ddm structure ID of this document library file entry metadata
	*/
	@Override
	public long getDDMStructureId() {
		return model.getDDMStructureId();
	}

	/**
	* Returns the file entry ID of this document library file entry metadata.
	*
	* @return the file entry ID of this document library file entry metadata
	*/
	@Override
	public long getFileEntryId() {
		return model.getFileEntryId();
	}

	/**
	* Returns the file entry metadata ID of this document library file entry metadata.
	*
	* @return the file entry metadata ID of this document library file entry metadata
	*/
	@Override
	public long getFileEntryMetadataId() {
		return model.getFileEntryMetadataId();
	}

	@Override
	public DLFileVersion getFileVersion()
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getFileVersion();
	}

	/**
	* Returns the file version ID of this document library file entry metadata.
	*
	* @return the file version ID of this document library file entry metadata
	*/
	@Override
	public long getFileVersionId() {
		return model.getFileVersionId();
	}

	/**
	* Returns the primary key of this document library file entry metadata.
	*
	* @return the primary key of this document library file entry metadata
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the uuid of this document library file entry metadata.
	*
	* @return the uuid of this document library file entry metadata
	*/
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the company ID of this document library file entry metadata.
	*
	* @param companyId the company ID of this document library file entry metadata
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the ddm storage ID of this document library file entry metadata.
	*
	* @param DDMStorageId the ddm storage ID of this document library file entry metadata
	*/
	@Override
	public void setDDMStorageId(long DDMStorageId) {
		model.setDDMStorageId(DDMStorageId);
	}

	/**
	* Sets the ddm structure ID of this document library file entry metadata.
	*
	* @param DDMStructureId the ddm structure ID of this document library file entry metadata
	*/
	@Override
	public void setDDMStructureId(long DDMStructureId) {
		model.setDDMStructureId(DDMStructureId);
	}

	/**
	* Sets the file entry ID of this document library file entry metadata.
	*
	* @param fileEntryId the file entry ID of this document library file entry metadata
	*/
	@Override
	public void setFileEntryId(long fileEntryId) {
		model.setFileEntryId(fileEntryId);
	}

	/**
	* Sets the file entry metadata ID of this document library file entry metadata.
	*
	* @param fileEntryMetadataId the file entry metadata ID of this document library file entry metadata
	*/
	@Override
	public void setFileEntryMetadataId(long fileEntryMetadataId) {
		model.setFileEntryMetadataId(fileEntryMetadataId);
	}

	/**
	* Sets the file version ID of this document library file entry metadata.
	*
	* @param fileVersionId the file version ID of this document library file entry metadata
	*/
	@Override
	public void setFileVersionId(long fileVersionId) {
		model.setFileVersionId(fileVersionId);
	}

	/**
	* Sets the primary key of this document library file entry metadata.
	*
	* @param primaryKey the primary key of this document library file entry metadata
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the uuid of this document library file entry metadata.
	*
	* @param uuid the uuid of this document library file entry metadata
	*/
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	protected DLFileEntryMetadataWrapper wrap(
		DLFileEntryMetadata dlFileEntryMetadata) {
		return new DLFileEntryMetadataWrapper(dlFileEntryMetadata);
	}
}