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

package com.liferay.adaptive.media.image.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link AMImageEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AMImageEntry
 * @generated
 */
@ProviderType
public class AMImageEntryWrapper implements AMImageEntry,
	ModelWrapper<AMImageEntry> {
	public AMImageEntryWrapper(AMImageEntry amImageEntry) {
		_amImageEntry = amImageEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return AMImageEntry.class;
	}

	@Override
	public String getModelClassName() {
		return AMImageEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("amImageEntryId", getAmImageEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("configurationUuid", getConfigurationUuid());
		attributes.put("fileVersionId", getFileVersionId());
		attributes.put("mimeType", getMimeType());
		attributes.put("height", getHeight());
		attributes.put("width", getWidth());
		attributes.put("size", getSize());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long amImageEntryId = (Long)attributes.get("amImageEntryId");

		if (amImageEntryId != null) {
			setAmImageEntryId(amImageEntryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		String configurationUuid = (String)attributes.get("configurationUuid");

		if (configurationUuid != null) {
			setConfigurationUuid(configurationUuid);
		}

		Long fileVersionId = (Long)attributes.get("fileVersionId");

		if (fileVersionId != null) {
			setFileVersionId(fileVersionId);
		}

		String mimeType = (String)attributes.get("mimeType");

		if (mimeType != null) {
			setMimeType(mimeType);
		}

		Integer height = (Integer)attributes.get("height");

		if (height != null) {
			setHeight(height);
		}

		Integer width = (Integer)attributes.get("width");

		if (width != null) {
			setWidth(width);
		}

		Long size = (Long)attributes.get("size");

		if (size != null) {
			setSize(size);
		}
	}

	@Override
	public Object clone() {
		return new AMImageEntryWrapper((AMImageEntry)_amImageEntry.clone());
	}

	@Override
	public int compareTo(AMImageEntry amImageEntry) {
		return _amImageEntry.compareTo(amImageEntry);
	}

	/**
	* Returns the am image entry ID of this am image entry.
	*
	* @return the am image entry ID of this am image entry
	*/
	@Override
	public long getAmImageEntryId() {
		return _amImageEntry.getAmImageEntryId();
	}

	/**
	* Returns the company ID of this am image entry.
	*
	* @return the company ID of this am image entry
	*/
	@Override
	public long getCompanyId() {
		return _amImageEntry.getCompanyId();
	}

	/**
	* Returns the configuration uuid of this am image entry.
	*
	* @return the configuration uuid of this am image entry
	*/
	@Override
	public String getConfigurationUuid() {
		return _amImageEntry.getConfigurationUuid();
	}

	/**
	* Returns the create date of this am image entry.
	*
	* @return the create date of this am image entry
	*/
	@Override
	public Date getCreateDate() {
		return _amImageEntry.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _amImageEntry.getExpandoBridge();
	}

	/**
	* Returns the file version ID of this am image entry.
	*
	* @return the file version ID of this am image entry
	*/
	@Override
	public long getFileVersionId() {
		return _amImageEntry.getFileVersionId();
	}

	/**
	* Returns the group ID of this am image entry.
	*
	* @return the group ID of this am image entry
	*/
	@Override
	public long getGroupId() {
		return _amImageEntry.getGroupId();
	}

	/**
	* Returns the height of this am image entry.
	*
	* @return the height of this am image entry
	*/
	@Override
	public int getHeight() {
		return _amImageEntry.getHeight();
	}

	/**
	* Returns the mime type of this am image entry.
	*
	* @return the mime type of this am image entry
	*/
	@Override
	public String getMimeType() {
		return _amImageEntry.getMimeType();
	}

	/**
	* Returns the primary key of this am image entry.
	*
	* @return the primary key of this am image entry
	*/
	@Override
	public long getPrimaryKey() {
		return _amImageEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _amImageEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the size of this am image entry.
	*
	* @return the size of this am image entry
	*/
	@Override
	public long getSize() {
		return _amImageEntry.getSize();
	}

	/**
	* Returns the uuid of this am image entry.
	*
	* @return the uuid of this am image entry
	*/
	@Override
	public String getUuid() {
		return _amImageEntry.getUuid();
	}

	/**
	* Returns the width of this am image entry.
	*
	* @return the width of this am image entry
	*/
	@Override
	public int getWidth() {
		return _amImageEntry.getWidth();
	}

	@Override
	public int hashCode() {
		return _amImageEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _amImageEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _amImageEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _amImageEntry.isNew();
	}

	@Override
	public void persist() {
		_amImageEntry.persist();
	}

	/**
	* Sets the am image entry ID of this am image entry.
	*
	* @param amImageEntryId the am image entry ID of this am image entry
	*/
	@Override
	public void setAmImageEntryId(long amImageEntryId) {
		_amImageEntry.setAmImageEntryId(amImageEntryId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_amImageEntry.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this am image entry.
	*
	* @param companyId the company ID of this am image entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_amImageEntry.setCompanyId(companyId);
	}

	/**
	* Sets the configuration uuid of this am image entry.
	*
	* @param configurationUuid the configuration uuid of this am image entry
	*/
	@Override
	public void setConfigurationUuid(String configurationUuid) {
		_amImageEntry.setConfigurationUuid(configurationUuid);
	}

	/**
	* Sets the create date of this am image entry.
	*
	* @param createDate the create date of this am image entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_amImageEntry.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_amImageEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_amImageEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_amImageEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the file version ID of this am image entry.
	*
	* @param fileVersionId the file version ID of this am image entry
	*/
	@Override
	public void setFileVersionId(long fileVersionId) {
		_amImageEntry.setFileVersionId(fileVersionId);
	}

	/**
	* Sets the group ID of this am image entry.
	*
	* @param groupId the group ID of this am image entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_amImageEntry.setGroupId(groupId);
	}

	/**
	* Sets the height of this am image entry.
	*
	* @param height the height of this am image entry
	*/
	@Override
	public void setHeight(int height) {
		_amImageEntry.setHeight(height);
	}

	/**
	* Sets the mime type of this am image entry.
	*
	* @param mimeType the mime type of this am image entry
	*/
	@Override
	public void setMimeType(String mimeType) {
		_amImageEntry.setMimeType(mimeType);
	}

	@Override
	public void setNew(boolean n) {
		_amImageEntry.setNew(n);
	}

	/**
	* Sets the primary key of this am image entry.
	*
	* @param primaryKey the primary key of this am image entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_amImageEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_amImageEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the size of this am image entry.
	*
	* @param size the size of this am image entry
	*/
	@Override
	public void setSize(long size) {
		_amImageEntry.setSize(size);
	}

	/**
	* Sets the uuid of this am image entry.
	*
	* @param uuid the uuid of this am image entry
	*/
	@Override
	public void setUuid(String uuid) {
		_amImageEntry.setUuid(uuid);
	}

	/**
	* Sets the width of this am image entry.
	*
	* @param width the width of this am image entry
	*/
	@Override
	public void setWidth(int width) {
		_amImageEntry.setWidth(width);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<AMImageEntry> toCacheModel() {
		return _amImageEntry.toCacheModel();
	}

	@Override
	public AMImageEntry toEscapedModel() {
		return new AMImageEntryWrapper(_amImageEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _amImageEntry.toString();
	}

	@Override
	public AMImageEntry toUnescapedModel() {
		return new AMImageEntryWrapper(_amImageEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _amImageEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AMImageEntryWrapper)) {
			return false;
		}

		AMImageEntryWrapper amImageEntryWrapper = (AMImageEntryWrapper)obj;

		if (Objects.equals(_amImageEntry, amImageEntryWrapper._amImageEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public AMImageEntry getWrappedModel() {
		return _amImageEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _amImageEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _amImageEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_amImageEntry.resetOriginalValues();
	}

	private final AMImageEntry _amImageEntry;
}