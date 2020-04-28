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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link DLFileEntryMetadata}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryMetadata
 * @generated
 */
public class DLFileEntryMetadataWrapper
	extends BaseModelWrapper<DLFileEntryMetadata>
	implements DLFileEntryMetadata, ModelWrapper<DLFileEntryMetadata> {

	public DLFileEntryMetadataWrapper(DLFileEntryMetadata dlFileEntryMetadata) {
		super(dlFileEntryMetadata);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
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
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

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
	 * Returns the ct collection ID of this document library file entry metadata.
	 *
	 * @return the ct collection ID of this document library file entry metadata
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
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
	 * Returns the mvcc version of this document library file entry metadata.
	 *
	 * @return the mvcc version of this document library file entry metadata
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
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
	 * Sets the ct collection ID of this document library file entry metadata.
	 *
	 * @param ctCollectionId the ct collection ID of this document library file entry metadata
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
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
	 * Sets the mvcc version of this document library file entry metadata.
	 *
	 * @param mvccVersion the mvcc version of this document library file entry metadata
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
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
	public Map<String, Function<DLFileEntryMetadata, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<DLFileEntryMetadata, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected DLFileEntryMetadataWrapper wrap(
		DLFileEntryMetadata dlFileEntryMetadata) {

		return new DLFileEntryMetadataWrapper(dlFileEntryMetadata);
	}

}