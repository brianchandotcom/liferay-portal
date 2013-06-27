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

package com.liferay.portlet.documentlibrary.model;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DLFileEntryMetadata}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryMetadata
 * @generated
 */
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
		attributes.put("DDMStorageId", getDDMStorageId());
		attributes.put("DDMStructureId", getDDMStructureId());
		attributes.put("fileEntryTypeId", getFileEntryTypeId());
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

		Long DDMStorageId = (Long)attributes.get("DDMStorageId");

		if (DDMStorageId != null) {
			setDDMStorageId(DDMStorageId);
		}

		Long DDMStructureId = (Long)attributes.get("DDMStructureId");

		if (DDMStructureId != null) {
			setDDMStructureId(DDMStructureId);
		}

		Long fileEntryTypeId = (Long)attributes.get("fileEntryTypeId");

		if (fileEntryTypeId != null) {
			setFileEntryTypeId(fileEntryTypeId);
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

	/**
	* Returns the primary key of this document library file entry metadata.
	*
	* @return the primary key of this document library file entry metadata
	*/
	@Override
	public long getPrimaryKey() {
		return _dlFileEntryMetadata.getPrimaryKey();
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

	/**
	* Returns the uuid of this document library file entry metadata.
	*
	* @return the uuid of this document library file entry metadata
	*/
	@Override
	public java.lang.String getUuid() {
		return _dlFileEntryMetadata.getUuid();
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
	* Sets the file entry metadata ID of this document library file entry metadata.
	*
	* @param fileEntryMetadataId the file entry metadata ID of this document library file entry metadata
	*/
	@Override
	public void setFileEntryMetadataId(long fileEntryMetadataId) {
		_dlFileEntryMetadata.setFileEntryMetadataId(fileEntryMetadataId);
	}

	/**
	* Returns the d d m storage ID of this document library file entry metadata.
	*
	* @return the d d m storage ID of this document library file entry metadata
	*/
	@Override
	public long getDDMStorageId() {
		return _dlFileEntryMetadata.getDDMStorageId();
	}

	/**
	* Sets the d d m storage ID of this document library file entry metadata.
	*
	* @param DDMStorageId the d d m storage ID of this document library file entry metadata
	*/
	@Override
	public void setDDMStorageId(long DDMStorageId) {
		_dlFileEntryMetadata.setDDMStorageId(DDMStorageId);
	}

	/**
	* Returns the d d m structure ID of this document library file entry metadata.
	*
	* @return the d d m structure ID of this document library file entry metadata
	*/
	@Override
	public long getDDMStructureId() {
		return _dlFileEntryMetadata.getDDMStructureId();
	}

	/**
	* Sets the d d m structure ID of this document library file entry metadata.
	*
	* @param DDMStructureId the d d m structure ID of this document library file entry metadata
	*/
	@Override
	public void setDDMStructureId(long DDMStructureId) {
		_dlFileEntryMetadata.setDDMStructureId(DDMStructureId);
	}

	/**
	* Returns the file entry type ID of this document library file entry metadata.
	*
	* @return the file entry type ID of this document library file entry metadata
	*/
	@Override
	public long getFileEntryTypeId() {
		return _dlFileEntryMetadata.getFileEntryTypeId();
	}

	/**
	* Sets the file entry type ID of this document library file entry metadata.
	*
	* @param fileEntryTypeId the file entry type ID of this document library file entry metadata
	*/
	@Override
	public void setFileEntryTypeId(long fileEntryTypeId) {
		_dlFileEntryMetadata.setFileEntryTypeId(fileEntryTypeId);
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
	* Sets the file entry ID of this document library file entry metadata.
	*
	* @param fileEntryId the file entry ID of this document library file entry metadata
	*/
	@Override
	public void setFileEntryId(long fileEntryId) {
		_dlFileEntryMetadata.setFileEntryId(fileEntryId);
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
	* Sets the file version ID of this document library file entry metadata.
	*
	* @param fileVersionId the file version ID of this document library file entry metadata
	*/
	@Override
	public void setFileVersionId(long fileVersionId) {
		_dlFileEntryMetadata.setFileVersionId(fileVersionId);
	}

	@Override
	public boolean isNew() {
		return _dlFileEntryMetadata.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_dlFileEntryMetadata.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _dlFileEntryMetadata.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_dlFileEntryMetadata.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _dlFileEntryMetadata.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _dlFileEntryMetadata.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_dlFileEntryMetadata.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _dlFileEntryMetadata.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_dlFileEntryMetadata.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_dlFileEntryMetadata.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_dlFileEntryMetadata.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new DLFileEntryMetadataWrapper((DLFileEntryMetadata)_dlFileEntryMetadata.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata dlFileEntryMetadata) {
		return _dlFileEntryMetadata.compareTo(dlFileEntryMetadata);
	}

	@Override
	public int hashCode() {
		return _dlFileEntryMetadata.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> toCacheModel() {
		return _dlFileEntryMetadata.toCacheModel();
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata toEscapedModel() {
		return new DLFileEntryMetadataWrapper(_dlFileEntryMetadata.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata toUnescapedModel() {
		return new DLFileEntryMetadataWrapper(_dlFileEntryMetadata.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _dlFileEntryMetadata.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _dlFileEntryMetadata.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryMetadata.persist();
	}

	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure getDDMStructure()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryMetadata.getDDMStructure();
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType getFileEntryType()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryMetadata.getFileEntryType();
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileVersion getFileVersion()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryMetadata.getFileVersion();
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

		if (Validator.equals(_dlFileEntryMetadata,
					dlFileEntryMetadataWrapper._dlFileEntryMetadata)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public DLFileEntryMetadata getWrappedDLFileEntryMetadata() {
		return _dlFileEntryMetadata;
	}

	@Override
	public DLFileEntryMetadata getWrappedModel() {
		return _dlFileEntryMetadata;
	}

	@Override
	public void resetOriginalValues() {
		_dlFileEntryMetadata.resetOriginalValues();
	}

	private DLFileEntryMetadata _dlFileEntryMetadata;
}