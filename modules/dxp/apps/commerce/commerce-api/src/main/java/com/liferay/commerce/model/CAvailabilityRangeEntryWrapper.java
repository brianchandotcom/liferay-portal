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

package com.liferay.commerce.model;

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
 * This class is a wrapper for {@link CAvailabilityRangeEntry}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CAvailabilityRangeEntry
 * @generated
 */
@ProviderType
public class CAvailabilityRangeEntryWrapper implements CAvailabilityRangeEntry,
	ModelWrapper<CAvailabilityRangeEntry> {
	public CAvailabilityRangeEntryWrapper(
		CAvailabilityRangeEntry cAvailabilityRangeEntry) {
		_cAvailabilityRangeEntry = cAvailabilityRangeEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return CAvailabilityRangeEntry.class;
	}

	@Override
	public String getModelClassName() {
		return CAvailabilityRangeEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("CAvailabilityRangeEntryId",
			getCAvailabilityRangeEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CPDefinitionId", getCPDefinitionId());
		attributes.put("commerceAvailabilityRangeId",
			getCommerceAvailabilityRangeId());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long CAvailabilityRangeEntryId = (Long)attributes.get(
				"CAvailabilityRangeEntryId");

		if (CAvailabilityRangeEntryId != null) {
			setCAvailabilityRangeEntryId(CAvailabilityRangeEntryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long CPDefinitionId = (Long)attributes.get("CPDefinitionId");

		if (CPDefinitionId != null) {
			setCPDefinitionId(CPDefinitionId);
		}

		Long commerceAvailabilityRangeId = (Long)attributes.get(
				"commerceAvailabilityRangeId");

		if (commerceAvailabilityRangeId != null) {
			setCommerceAvailabilityRangeId(commerceAvailabilityRangeId);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new CAvailabilityRangeEntryWrapper((CAvailabilityRangeEntry)_cAvailabilityRangeEntry.clone());
	}

	@Override
	public int compareTo(CAvailabilityRangeEntry cAvailabilityRangeEntry) {
		return _cAvailabilityRangeEntry.compareTo(cAvailabilityRangeEntry);
	}

	/**
	* Returns the c availability range entry ID of this c availability range entry.
	*
	* @return the c availability range entry ID of this c availability range entry
	*/
	@Override
	public long getCAvailabilityRangeEntryId() {
		return _cAvailabilityRangeEntry.getCAvailabilityRangeEntryId();
	}

	/**
	* Returns the commerce availability range ID of this c availability range entry.
	*
	* @return the commerce availability range ID of this c availability range entry
	*/
	@Override
	public long getCommerceAvailabilityRangeId() {
		return _cAvailabilityRangeEntry.getCommerceAvailabilityRangeId();
	}

	/**
	* Returns the company ID of this c availability range entry.
	*
	* @return the company ID of this c availability range entry
	*/
	@Override
	public long getCompanyId() {
		return _cAvailabilityRangeEntry.getCompanyId();
	}

	/**
	* Returns the cp definition ID of this c availability range entry.
	*
	* @return the cp definition ID of this c availability range entry
	*/
	@Override
	public long getCPDefinitionId() {
		return _cAvailabilityRangeEntry.getCPDefinitionId();
	}

	/**
	* Returns the create date of this c availability range entry.
	*
	* @return the create date of this c availability range entry
	*/
	@Override
	public Date getCreateDate() {
		return _cAvailabilityRangeEntry.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cAvailabilityRangeEntry.getExpandoBridge();
	}

	/**
	* Returns the group ID of this c availability range entry.
	*
	* @return the group ID of this c availability range entry
	*/
	@Override
	public long getGroupId() {
		return _cAvailabilityRangeEntry.getGroupId();
	}

	/**
	* Returns the last publish date of this c availability range entry.
	*
	* @return the last publish date of this c availability range entry
	*/
	@Override
	public Date getLastPublishDate() {
		return _cAvailabilityRangeEntry.getLastPublishDate();
	}

	/**
	* Returns the modified date of this c availability range entry.
	*
	* @return the modified date of this c availability range entry
	*/
	@Override
	public Date getModifiedDate() {
		return _cAvailabilityRangeEntry.getModifiedDate();
	}

	/**
	* Returns the primary key of this c availability range entry.
	*
	* @return the primary key of this c availability range entry
	*/
	@Override
	public long getPrimaryKey() {
		return _cAvailabilityRangeEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cAvailabilityRangeEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this c availability range entry.
	*
	* @return the user ID of this c availability range entry
	*/
	@Override
	public long getUserId() {
		return _cAvailabilityRangeEntry.getUserId();
	}

	/**
	* Returns the user name of this c availability range entry.
	*
	* @return the user name of this c availability range entry
	*/
	@Override
	public java.lang.String getUserName() {
		return _cAvailabilityRangeEntry.getUserName();
	}

	/**
	* Returns the user uuid of this c availability range entry.
	*
	* @return the user uuid of this c availability range entry
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _cAvailabilityRangeEntry.getUserUuid();
	}

	/**
	* Returns the uuid of this c availability range entry.
	*
	* @return the uuid of this c availability range entry
	*/
	@Override
	public java.lang.String getUuid() {
		return _cAvailabilityRangeEntry.getUuid();
	}

	@Override
	public int hashCode() {
		return _cAvailabilityRangeEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _cAvailabilityRangeEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cAvailabilityRangeEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _cAvailabilityRangeEntry.isNew();
	}

	@Override
	public void persist() {
		_cAvailabilityRangeEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cAvailabilityRangeEntry.setCachedModel(cachedModel);
	}

	/**
	* Sets the c availability range entry ID of this c availability range entry.
	*
	* @param CAvailabilityRangeEntryId the c availability range entry ID of this c availability range entry
	*/
	@Override
	public void setCAvailabilityRangeEntryId(long CAvailabilityRangeEntryId) {
		_cAvailabilityRangeEntry.setCAvailabilityRangeEntryId(CAvailabilityRangeEntryId);
	}

	/**
	* Sets the commerce availability range ID of this c availability range entry.
	*
	* @param commerceAvailabilityRangeId the commerce availability range ID of this c availability range entry
	*/
	@Override
	public void setCommerceAvailabilityRangeId(long commerceAvailabilityRangeId) {
		_cAvailabilityRangeEntry.setCommerceAvailabilityRangeId(commerceAvailabilityRangeId);
	}

	/**
	* Sets the company ID of this c availability range entry.
	*
	* @param companyId the company ID of this c availability range entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cAvailabilityRangeEntry.setCompanyId(companyId);
	}

	/**
	* Sets the cp definition ID of this c availability range entry.
	*
	* @param CPDefinitionId the cp definition ID of this c availability range entry
	*/
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		_cAvailabilityRangeEntry.setCPDefinitionId(CPDefinitionId);
	}

	/**
	* Sets the create date of this c availability range entry.
	*
	* @param createDate the create date of this c availability range entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cAvailabilityRangeEntry.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cAvailabilityRangeEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cAvailabilityRangeEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cAvailabilityRangeEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this c availability range entry.
	*
	* @param groupId the group ID of this c availability range entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_cAvailabilityRangeEntry.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this c availability range entry.
	*
	* @param lastPublishDate the last publish date of this c availability range entry
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_cAvailabilityRangeEntry.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this c availability range entry.
	*
	* @param modifiedDate the modified date of this c availability range entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cAvailabilityRangeEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_cAvailabilityRangeEntry.setNew(n);
	}

	/**
	* Sets the primary key of this c availability range entry.
	*
	* @param primaryKey the primary key of this c availability range entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cAvailabilityRangeEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cAvailabilityRangeEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this c availability range entry.
	*
	* @param userId the user ID of this c availability range entry
	*/
	@Override
	public void setUserId(long userId) {
		_cAvailabilityRangeEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this c availability range entry.
	*
	* @param userName the user name of this c availability range entry
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_cAvailabilityRangeEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this c availability range entry.
	*
	* @param userUuid the user uuid of this c availability range entry
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_cAvailabilityRangeEntry.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this c availability range entry.
	*
	* @param uuid the uuid of this c availability range entry
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_cAvailabilityRangeEntry.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CAvailabilityRangeEntry> toCacheModel() {
		return _cAvailabilityRangeEntry.toCacheModel();
	}

	@Override
	public CAvailabilityRangeEntry toEscapedModel() {
		return new CAvailabilityRangeEntryWrapper(_cAvailabilityRangeEntry.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _cAvailabilityRangeEntry.toString();
	}

	@Override
	public CAvailabilityRangeEntry toUnescapedModel() {
		return new CAvailabilityRangeEntryWrapper(_cAvailabilityRangeEntry.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _cAvailabilityRangeEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CAvailabilityRangeEntryWrapper)) {
			return false;
		}

		CAvailabilityRangeEntryWrapper cAvailabilityRangeEntryWrapper = (CAvailabilityRangeEntryWrapper)obj;

		if (Objects.equals(_cAvailabilityRangeEntry,
					cAvailabilityRangeEntryWrapper._cAvailabilityRangeEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _cAvailabilityRangeEntry.getStagedModelType();
	}

	@Override
	public CAvailabilityRangeEntry getWrappedModel() {
		return _cAvailabilityRangeEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cAvailabilityRangeEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cAvailabilityRangeEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cAvailabilityRangeEntry.resetOriginalValues();
	}

	private final CAvailabilityRangeEntry _cAvailabilityRangeEntry;
}