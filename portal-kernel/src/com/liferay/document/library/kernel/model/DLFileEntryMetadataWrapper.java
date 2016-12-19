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

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
public class DLFileEntryMetadataWrapper implements DLFileEntryMetadata,
	ModelWrapper<DLFileEntryMetadata> {
	public DLFileEntryMetadataWrapper(DLFileEntryMetadata dlFileEntryMetadata) {
		_dlFileEntryMetadata = dlFileEntryMetadata;
	}

	@Override
	public Class<?> getModelClass() {
		return DLFileEntryMetadata.class;
	}

	@Override
	public String getModelClassName() {
		return DLFileEntryMetadata.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("fileEntryMetadataId", getFileEntryMetadataId());
		attributes.put("companyId", getCompanyId());
		attributes.put("DDMStorageId", getDDMStorageId());
		attributes.put("DDMStructureId", getDDMStructureId());
		attributes.put("fileEntryId", getFileEntryId());
		attributes.put("fileVersionId", getFileVersionId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long fileEntryMetadataId = (Long)attributes.get("fileEntryMetadataId");

		if (fileEntryMetadataId != null) {
			setFileEntryMetadataId(fileEntryMetadataId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long DDMStorageId = (Long)attributes.get("DDMStorageId");

		if (DDMStorageId != null) {
			setDDMStorageId(DDMStorageId);
		}

		Long DDMStructureId = (Long)attributes.get("DDMStructureId");

		if (DDMStructureId != null) {
			setDDMStructureId(DDMStructureId);
		}

		Long fileEntryId = (Long)attributes.get("fileEntryId");

		if (fileEntryId != null) {
			setFileEntryId(fileEntryId);
		}

		Long fileVersionId = (Long)attributes.get("fileVersionId");

		if (fileVersionId != null) {
			setFileVersionId(fileVersionId);
		}
	}

	@Override
	public DLFileEntryMetadata toEscapedModel() {
		return new DLFileEntryMetadataWrapper(_dlFileEntryMetadata.toEscapedModel());
	}

	@Override
	public DLFileEntryMetadata toUnescapedModel() {
		return new DLFileEntryMetadataWrapper(_dlFileEntryMetadata.toUnescapedModel());
	}

	@Override
	public DLFileVersion getFileVersion()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntryMetadata.getFileVersion();
	}

	@Override
	public boolean isCachedModel() {
		return _dlFileEntryMetadata.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _dlFileEntryMetadata.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _dlFileEntryMetadata.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _dlFileEntryMetadata.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<DLFileEntryMetadata> toCacheModel() {
		return _dlFileEntryMetadata.toCacheModel();
	}

	@Override
	public int compareTo(DLFileEntryMetadata dlFileEntryMetadata) {
		return _dlFileEntryMetadata.compareTo(dlFileEntryMetadata);
	}

	@Override
	public int hashCode() {
		return _dlFileEntryMetadata.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _dlFileEntryMetadata.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new DLFileEntryMetadataWrapper((DLFileEntryMetadata)_dlFileEntryMetadata.clone());
	}

	/**
	* Returns the uuid of this document library file entry metadata.
	*
	* @return the uuid of this document library file entry metadata
	*/
	@Override
	public java.lang.String getUuid() {
		return _dlFileEntryMetadata.getUuid();
	}

	@Override
	public java.lang.String toString() {
		return _dlFileEntryMetadata.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _dlFileEntryMetadata.toXmlString();
	}

	/**
	* Returns the company ID of this document library file entry metadata.
	*
	* @return the company ID of this document library file entry metadata
	*/
	@Override
	public long getCompanyId() {
		return _dlFileEntryMetadata.getCompanyId();
	}

	/**
	* Returns the ddm storage ID of this document library file entry metadata.
	*
	* @return the ddm storage ID of this document library file entry metadata
	*/
	@Override
	public long getDDMStorageId() {
		return _dlFileEntryMetadata.getDDMStorageId();
	}

	/**
	* Returns the ddm structure ID of this document library file entry metadata.
	*
	* @return the ddm structure ID of this document library file entry metadata
	*/
	@Override
	public long getDDMStructureId() {
		return _dlFileEntryMetadata.getDDMStructureId();
	}

	/**
	* Returns the file entry ID of this document library file entry metadata.
	*
	* @return the file entry ID of this document library file entry metadata
	*/
	@Override
	public long getFileEntryId() {
		return _dlFileEntryMetadata.getFileEntryId();
	}

	/**
	* Returns the file entry metadata ID of this document library file entry metadata.
	*
	* @return the file entry metadata ID of this document library file entry metadata
	*/
	@Override
	public long getFileEntryMetadataId() {
		return _dlFileEntryMetadata.getFileEntryMetadataId();
	}

	/**
	* Returns the file version ID of this document library file entry metadata.
	*
	* @return the file version ID of this document library file entry metadata
	*/
	@Override
	public long getFileVersionId() {
		return _dlFileEntryMetadata.getFileVersionId();
	}

	/**
	* Returns the primary key of this document library file entry metadata.
	*
	* @return the primary key of this document library file entry metadata
	*/
	@Override
	public long getPrimaryKey() {
		return _dlFileEntryMetadata.getPrimaryKey();
	}

	@Override
	public void persist() {
		_dlFileEntryMetadata.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_dlFileEntryMetadata.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this document library file entry metadata.
	*
	* @param companyId the company ID of this document library file entry metadata
	*/
	@Override
	public void setCompanyId(long companyId) {
		_dlFileEntryMetadata.setCompanyId(companyId);
	}

	/**
	* Sets the ddm storage ID of this document library file entry metadata.
	*
	* @param DDMStorageId the ddm storage ID of this document library file entry metadata
	*/
	@Override
	public void setDDMStorageId(long DDMStorageId) {
		_dlFileEntryMetadata.setDDMStorageId(DDMStorageId);
	}

	/**
	* Sets the ddm structure ID of this document library file entry metadata.
	*
	* @param DDMStructureId the ddm structure ID of this document library file entry metadata
	*/
	@Override
	public void setDDMStructureId(long DDMStructureId) {
		_dlFileEntryMetadata.setDDMStructureId(DDMStructureId);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_dlFileEntryMetadata.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_dlFileEntryMetadata.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_dlFileEntryMetadata.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the file entry ID of this document library file entry metadata.
	*
	* @param fileEntryId the file entry ID of this document library file entry metadata
	*/
	@Override
	public void setFileEntryId(long fileEntryId) {
		_dlFileEntryMetadata.setFileEntryId(fileEntryId);
	}

	/**
	* Sets the file entry metadata ID of this document library file entry metadata.
	*
	* @param fileEntryMetadataId the file entry metadata ID of this document library file entry metadata
	*/
	@Override
	public void setFileEntryMetadataId(long fileEntryMetadataId) {
		_dlFileEntryMetadata.setFileEntryMetadataId(fileEntryMetadataId);
	}

	/**
	* Sets the file version ID of this document library file entry metadata.
	*
	* @param fileVersionId the file version ID of this document library file entry metadata
	*/
	@Override
	public void setFileVersionId(long fileVersionId) {
		_dlFileEntryMetadata.setFileVersionId(fileVersionId);
	}

	@Override
	public void setNew(boolean n) {
		_dlFileEntryMetadata.setNew(n);
	}

	/**
	* Sets the primary key of this document library file entry metadata.
	*
	* @param primaryKey the primary key of this document library file entry metadata
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_dlFileEntryMetadata.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_dlFileEntryMetadata.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the uuid of this document library file entry metadata.
	*
	* @param uuid the uuid of this document library file entry metadata
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_dlFileEntryMetadata.setUuid(uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DLFileEntryMetadataWrapper)) {
			return false;
		}

		DLFileEntryMetadataWrapper dlFileEntryMetadataWrapper = (DLFileEntryMetadataWrapper)obj;

		if (Objects.equals(_dlFileEntryMetadata,
					dlFileEntryMetadataWrapper._dlFileEntryMetadata)) {
			return true;
		}

		return false;
	}

	@Override
	public DLFileEntryMetadata getWrappedModel() {
		return _dlFileEntryMetadata;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _dlFileEntryMetadata.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _dlFileEntryMetadata.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_dlFileEntryMetadata.resetOriginalValues();
	}

	private final DLFileEntryMetadata _dlFileEntryMetadata;
}