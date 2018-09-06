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

package com.liferay.sharing.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link SharingEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SharingEntry
 * @generated
 */
@ProviderType
public class SharingEntryWrapper implements SharingEntry,
	ModelWrapper<SharingEntry> {
	public SharingEntryWrapper(SharingEntry sharingEntry) {
		_sharingEntry = sharingEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return SharingEntry.class;
	}

	@Override
	public String getModelClassName() {
		return SharingEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("sharingEntryId", getSharingEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("fromUserId", getFromUserId());
		attributes.put("toUserId", getToUserId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("shareable", isShareable());
		attributes.put("actionIds", getActionIds());
		attributes.put("expirationDate", getExpirationDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long sharingEntryId = (Long)attributes.get("sharingEntryId");

		if (sharingEntryId != null) {
			setSharingEntryId(sharingEntryId);
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

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long fromUserId = (Long)attributes.get("fromUserId");

		if (fromUserId != null) {
			setFromUserId(fromUserId);
		}

		Long toUserId = (Long)attributes.get("toUserId");

		if (toUserId != null) {
			setToUserId(toUserId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Boolean shareable = (Boolean)attributes.get("shareable");

		if (shareable != null) {
			setShareable(shareable);
		}

		Long actionIds = (Long)attributes.get("actionIds");

		if (actionIds != null) {
			setActionIds(actionIds);
		}

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}
	}

	@Override
	public Object clone() {
		return new SharingEntryWrapper((SharingEntry)_sharingEntry.clone());
	}

	@Override
	public int compareTo(SharingEntry sharingEntry) {
		return _sharingEntry.compareTo(sharingEntry);
	}

	/**
	* Returns the action IDs of this sharing entry.
	*
	* @return the action IDs of this sharing entry
	*/
	@Override
	public long getActionIds() {
		return _sharingEntry.getActionIds();
	}

	/**
	* Returns the fully qualified class name of this sharing entry.
	*
	* @return the fully qualified class name of this sharing entry
	*/
	@Override
	public String getClassName() {
		return _sharingEntry.getClassName();
	}

	/**
	* Returns the class name ID of this sharing entry.
	*
	* @return the class name ID of this sharing entry
	*/
	@Override
	public long getClassNameId() {
		return _sharingEntry.getClassNameId();
	}

	/**
	* Returns the class pk of this sharing entry.
	*
	* @return the class pk of this sharing entry
	*/
	@Override
	public long getClassPK() {
		return _sharingEntry.getClassPK();
	}

	/**
	* Returns the company ID of this sharing entry.
	*
	* @return the company ID of this sharing entry
	*/
	@Override
	public long getCompanyId() {
		return _sharingEntry.getCompanyId();
	}

	/**
	* Returns the create date of this sharing entry.
	*
	* @return the create date of this sharing entry
	*/
	@Override
	public Date getCreateDate() {
		return _sharingEntry.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _sharingEntry.getExpandoBridge();
	}

	/**
	* Returns the expiration date of this sharing entry.
	*
	* @return the expiration date of this sharing entry
	*/
	@Override
	public Date getExpirationDate() {
		return _sharingEntry.getExpirationDate();
	}

	/**
	* Returns the from user ID of this sharing entry.
	*
	* @return the from user ID of this sharing entry
	*/
	@Override
	public long getFromUserId() {
		return _sharingEntry.getFromUserId();
	}

	/**
	* Returns the from user uuid of this sharing entry.
	*
	* @return the from user uuid of this sharing entry
	*/
	@Override
	public String getFromUserUuid() {
		return _sharingEntry.getFromUserUuid();
	}

	/**
	* Returns the group ID of this sharing entry.
	*
	* @return the group ID of this sharing entry
	*/
	@Override
	public long getGroupId() {
		return _sharingEntry.getGroupId();
	}

	/**
	* Returns the modified date of this sharing entry.
	*
	* @return the modified date of this sharing entry
	*/
	@Override
	public Date getModifiedDate() {
		return _sharingEntry.getModifiedDate();
	}

	/**
	* Returns the primary key of this sharing entry.
	*
	* @return the primary key of this sharing entry
	*/
	@Override
	public long getPrimaryKey() {
		return _sharingEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _sharingEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the shareable of this sharing entry.
	*
	* @return the shareable of this sharing entry
	*/
	@Override
	public boolean getShareable() {
		return _sharingEntry.getShareable();
	}

	/**
	* Returns the sharing entry ID of this sharing entry.
	*
	* @return the sharing entry ID of this sharing entry
	*/
	@Override
	public long getSharingEntryId() {
		return _sharingEntry.getSharingEntryId();
	}

	/**
	* Returns the to user ID of this sharing entry.
	*
	* @return the to user ID of this sharing entry
	*/
	@Override
	public long getToUserId() {
		return _sharingEntry.getToUserId();
	}

	/**
	* Returns the to user uuid of this sharing entry.
	*
	* @return the to user uuid of this sharing entry
	*/
	@Override
	public String getToUserUuid() {
		return _sharingEntry.getToUserUuid();
	}

	/**
	* Returns the uuid of this sharing entry.
	*
	* @return the uuid of this sharing entry
	*/
	@Override
	public String getUuid() {
		return _sharingEntry.getUuid();
	}

	@Override
	public int hashCode() {
		return _sharingEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _sharingEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _sharingEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _sharingEntry.isNew();
	}

	/**
	* Returns <code>true</code> if this sharing entry is shareable.
	*
	* @return <code>true</code> if this sharing entry is shareable; <code>false</code> otherwise
	*/
	@Override
	public boolean isShareable() {
		return _sharingEntry.isShareable();
	}

	@Override
	public void persist() {
		_sharingEntry.persist();
	}

	/**
	* Sets the action IDs of this sharing entry.
	*
	* @param actionIds the action IDs of this sharing entry
	*/
	@Override
	public void setActionIds(long actionIds) {
		_sharingEntry.setActionIds(actionIds);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_sharingEntry.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(String className) {
		_sharingEntry.setClassName(className);
	}

	/**
	* Sets the class name ID of this sharing entry.
	*
	* @param classNameId the class name ID of this sharing entry
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_sharingEntry.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this sharing entry.
	*
	* @param classPK the class pk of this sharing entry
	*/
	@Override
	public void setClassPK(long classPK) {
		_sharingEntry.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this sharing entry.
	*
	* @param companyId the company ID of this sharing entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_sharingEntry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this sharing entry.
	*
	* @param createDate the create date of this sharing entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_sharingEntry.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_sharingEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_sharingEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_sharingEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the expiration date of this sharing entry.
	*
	* @param expirationDate the expiration date of this sharing entry
	*/
	@Override
	public void setExpirationDate(Date expirationDate) {
		_sharingEntry.setExpirationDate(expirationDate);
	}

	/**
	* Sets the from user ID of this sharing entry.
	*
	* @param fromUserId the from user ID of this sharing entry
	*/
	@Override
	public void setFromUserId(long fromUserId) {
		_sharingEntry.setFromUserId(fromUserId);
	}

	/**
	* Sets the from user uuid of this sharing entry.
	*
	* @param fromUserUuid the from user uuid of this sharing entry
	*/
	@Override
	public void setFromUserUuid(String fromUserUuid) {
		_sharingEntry.setFromUserUuid(fromUserUuid);
	}

	/**
	* Sets the group ID of this sharing entry.
	*
	* @param groupId the group ID of this sharing entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_sharingEntry.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this sharing entry.
	*
	* @param modifiedDate the modified date of this sharing entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_sharingEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_sharingEntry.setNew(n);
	}

	/**
	* Sets the primary key of this sharing entry.
	*
	* @param primaryKey the primary key of this sharing entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_sharingEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_sharingEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets whether this sharing entry is shareable.
	*
	* @param shareable the shareable of this sharing entry
	*/
	@Override
	public void setShareable(boolean shareable) {
		_sharingEntry.setShareable(shareable);
	}

	/**
	* Sets the sharing entry ID of this sharing entry.
	*
	* @param sharingEntryId the sharing entry ID of this sharing entry
	*/
	@Override
	public void setSharingEntryId(long sharingEntryId) {
		_sharingEntry.setSharingEntryId(sharingEntryId);
	}

	/**
	* Sets the to user ID of this sharing entry.
	*
	* @param toUserId the to user ID of this sharing entry
	*/
	@Override
	public void setToUserId(long toUserId) {
		_sharingEntry.setToUserId(toUserId);
	}

	/**
	* Sets the to user uuid of this sharing entry.
	*
	* @param toUserUuid the to user uuid of this sharing entry
	*/
	@Override
	public void setToUserUuid(String toUserUuid) {
		_sharingEntry.setToUserUuid(toUserUuid);
	}

	/**
	* Sets the uuid of this sharing entry.
	*
	* @param uuid the uuid of this sharing entry
	*/
	@Override
	public void setUuid(String uuid) {
		_sharingEntry.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<SharingEntry> toCacheModel() {
		return _sharingEntry.toCacheModel();
	}

	@Override
	public SharingEntry toEscapedModel() {
		return new SharingEntryWrapper(_sharingEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _sharingEntry.toString();
	}

	@Override
	public SharingEntry toUnescapedModel() {
		return new SharingEntryWrapper(_sharingEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _sharingEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SharingEntryWrapper)) {
			return false;
		}

		SharingEntryWrapper sharingEntryWrapper = (SharingEntryWrapper)obj;

		if (Objects.equals(_sharingEntry, sharingEntryWrapper._sharingEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _sharingEntry.getStagedModelType();
	}

	@Override
	public SharingEntry getWrappedModel() {
		return _sharingEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _sharingEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _sharingEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_sharingEntry.resetOriginalValues();
	}

	private final SharingEntry _sharingEntry;
}