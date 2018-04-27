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

package com.liferay.commerce.product.type.grouped.model;

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
 * This class is a wrapper for {@link CPDefinitionGroupedEntry}.
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CPDefinitionGroupedEntry
 * @generated
 */
@ProviderType
public class CPDefinitionGroupedEntryWrapper implements CPDefinitionGroupedEntry,
	ModelWrapper<CPDefinitionGroupedEntry> {
	public CPDefinitionGroupedEntryWrapper(
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry) {
		_cpDefinitionGroupedEntry = cpDefinitionGroupedEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return CPDefinitionGroupedEntry.class;
	}

	@Override
	public String getModelClassName() {
		return CPDefinitionGroupedEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("CPDefinitionGroupedEntryId",
			getCPDefinitionGroupedEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CPDefinitionId", getCPDefinitionId());
		attributes.put("entryCPDefinitionId", getEntryCPDefinitionId());
		attributes.put("priority", getPriority());
		attributes.put("quantity", getQuantity());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long CPDefinitionGroupedEntryId = (Long)attributes.get(
				"CPDefinitionGroupedEntryId");

		if (CPDefinitionGroupedEntryId != null) {
			setCPDefinitionGroupedEntryId(CPDefinitionGroupedEntryId);
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

		Long entryCPDefinitionId = (Long)attributes.get("entryCPDefinitionId");

		if (entryCPDefinitionId != null) {
			setEntryCPDefinitionId(entryCPDefinitionId);
		}

		Double priority = (Double)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Integer quantity = (Integer)attributes.get("quantity");

		if (quantity != null) {
			setQuantity(quantity);
		}
	}

	@Override
	public Object clone() {
		return new CPDefinitionGroupedEntryWrapper((CPDefinitionGroupedEntry)_cpDefinitionGroupedEntry.clone());
	}

	@Override
	public int compareTo(CPDefinitionGroupedEntry cpDefinitionGroupedEntry) {
		return _cpDefinitionGroupedEntry.compareTo(cpDefinitionGroupedEntry);
	}

	/**
	* Returns the company ID of this cp definition grouped entry.
	*
	* @return the company ID of this cp definition grouped entry
	*/
	@Override
	public long getCompanyId() {
		return _cpDefinitionGroupedEntry.getCompanyId();
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition getCPDefinition()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionGroupedEntry.getCPDefinition();
	}

	/**
	* Returns the cp definition grouped entry ID of this cp definition grouped entry.
	*
	* @return the cp definition grouped entry ID of this cp definition grouped entry
	*/
	@Override
	public long getCPDefinitionGroupedEntryId() {
		return _cpDefinitionGroupedEntry.getCPDefinitionGroupedEntryId();
	}

	/**
	* Returns the cp definition ID of this cp definition grouped entry.
	*
	* @return the cp definition ID of this cp definition grouped entry
	*/
	@Override
	public long getCPDefinitionId() {
		return _cpDefinitionGroupedEntry.getCPDefinitionId();
	}

	/**
	* Returns the create date of this cp definition grouped entry.
	*
	* @return the create date of this cp definition grouped entry
	*/
	@Override
	public Date getCreateDate() {
		return _cpDefinitionGroupedEntry.getCreateDate();
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition getEntryCPDefinition()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionGroupedEntry.getEntryCPDefinition();
	}

	/**
	* Returns the entry cp definition ID of this cp definition grouped entry.
	*
	* @return the entry cp definition ID of this cp definition grouped entry
	*/
	@Override
	public long getEntryCPDefinitionId() {
		return _cpDefinitionGroupedEntry.getEntryCPDefinitionId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cpDefinitionGroupedEntry.getExpandoBridge();
	}

	/**
	* Returns the group ID of this cp definition grouped entry.
	*
	* @return the group ID of this cp definition grouped entry
	*/
	@Override
	public long getGroupId() {
		return _cpDefinitionGroupedEntry.getGroupId();
	}

	/**
	* Returns the modified date of this cp definition grouped entry.
	*
	* @return the modified date of this cp definition grouped entry
	*/
	@Override
	public Date getModifiedDate() {
		return _cpDefinitionGroupedEntry.getModifiedDate();
	}

	/**
	* Returns the primary key of this cp definition grouped entry.
	*
	* @return the primary key of this cp definition grouped entry
	*/
	@Override
	public long getPrimaryKey() {
		return _cpDefinitionGroupedEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cpDefinitionGroupedEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the priority of this cp definition grouped entry.
	*
	* @return the priority of this cp definition grouped entry
	*/
	@Override
	public double getPriority() {
		return _cpDefinitionGroupedEntry.getPriority();
	}

	/**
	* Returns the quantity of this cp definition grouped entry.
	*
	* @return the quantity of this cp definition grouped entry
	*/
	@Override
	public int getQuantity() {
		return _cpDefinitionGroupedEntry.getQuantity();
	}

	/**
	* Returns the user ID of this cp definition grouped entry.
	*
	* @return the user ID of this cp definition grouped entry
	*/
	@Override
	public long getUserId() {
		return _cpDefinitionGroupedEntry.getUserId();
	}

	/**
	* Returns the user name of this cp definition grouped entry.
	*
	* @return the user name of this cp definition grouped entry
	*/
	@Override
	public String getUserName() {
		return _cpDefinitionGroupedEntry.getUserName();
	}

	/**
	* Returns the user uuid of this cp definition grouped entry.
	*
	* @return the user uuid of this cp definition grouped entry
	*/
	@Override
	public String getUserUuid() {
		return _cpDefinitionGroupedEntry.getUserUuid();
	}

	/**
	* Returns the uuid of this cp definition grouped entry.
	*
	* @return the uuid of this cp definition grouped entry
	*/
	@Override
	public String getUuid() {
		return _cpDefinitionGroupedEntry.getUuid();
	}

	@Override
	public int hashCode() {
		return _cpDefinitionGroupedEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _cpDefinitionGroupedEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cpDefinitionGroupedEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _cpDefinitionGroupedEntry.isNew();
	}

	@Override
	public void persist() {
		_cpDefinitionGroupedEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cpDefinitionGroupedEntry.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this cp definition grouped entry.
	*
	* @param companyId the company ID of this cp definition grouped entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cpDefinitionGroupedEntry.setCompanyId(companyId);
	}

	/**
	* Sets the cp definition grouped entry ID of this cp definition grouped entry.
	*
	* @param CPDefinitionGroupedEntryId the cp definition grouped entry ID of this cp definition grouped entry
	*/
	@Override
	public void setCPDefinitionGroupedEntryId(long CPDefinitionGroupedEntryId) {
		_cpDefinitionGroupedEntry.setCPDefinitionGroupedEntryId(CPDefinitionGroupedEntryId);
	}

	/**
	* Sets the cp definition ID of this cp definition grouped entry.
	*
	* @param CPDefinitionId the cp definition ID of this cp definition grouped entry
	*/
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		_cpDefinitionGroupedEntry.setCPDefinitionId(CPDefinitionId);
	}

	/**
	* Sets the create date of this cp definition grouped entry.
	*
	* @param createDate the create date of this cp definition grouped entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cpDefinitionGroupedEntry.setCreateDate(createDate);
	}

	/**
	* Sets the entry cp definition ID of this cp definition grouped entry.
	*
	* @param entryCPDefinitionId the entry cp definition ID of this cp definition grouped entry
	*/
	@Override
	public void setEntryCPDefinitionId(long entryCPDefinitionId) {
		_cpDefinitionGroupedEntry.setEntryCPDefinitionId(entryCPDefinitionId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cpDefinitionGroupedEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cpDefinitionGroupedEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cpDefinitionGroupedEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this cp definition grouped entry.
	*
	* @param groupId the group ID of this cp definition grouped entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_cpDefinitionGroupedEntry.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this cp definition grouped entry.
	*
	* @param modifiedDate the modified date of this cp definition grouped entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cpDefinitionGroupedEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_cpDefinitionGroupedEntry.setNew(n);
	}

	/**
	* Sets the primary key of this cp definition grouped entry.
	*
	* @param primaryKey the primary key of this cp definition grouped entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cpDefinitionGroupedEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cpDefinitionGroupedEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this cp definition grouped entry.
	*
	* @param priority the priority of this cp definition grouped entry
	*/
	@Override
	public void setPriority(double priority) {
		_cpDefinitionGroupedEntry.setPriority(priority);
	}

	/**
	* Sets the quantity of this cp definition grouped entry.
	*
	* @param quantity the quantity of this cp definition grouped entry
	*/
	@Override
	public void setQuantity(int quantity) {
		_cpDefinitionGroupedEntry.setQuantity(quantity);
	}

	/**
	* Sets the user ID of this cp definition grouped entry.
	*
	* @param userId the user ID of this cp definition grouped entry
	*/
	@Override
	public void setUserId(long userId) {
		_cpDefinitionGroupedEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this cp definition grouped entry.
	*
	* @param userName the user name of this cp definition grouped entry
	*/
	@Override
	public void setUserName(String userName) {
		_cpDefinitionGroupedEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this cp definition grouped entry.
	*
	* @param userUuid the user uuid of this cp definition grouped entry
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_cpDefinitionGroupedEntry.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this cp definition grouped entry.
	*
	* @param uuid the uuid of this cp definition grouped entry
	*/
	@Override
	public void setUuid(String uuid) {
		_cpDefinitionGroupedEntry.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CPDefinitionGroupedEntry> toCacheModel() {
		return _cpDefinitionGroupedEntry.toCacheModel();
	}

	@Override
	public CPDefinitionGroupedEntry toEscapedModel() {
		return new CPDefinitionGroupedEntryWrapper(_cpDefinitionGroupedEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _cpDefinitionGroupedEntry.toString();
	}

	@Override
	public CPDefinitionGroupedEntry toUnescapedModel() {
		return new CPDefinitionGroupedEntryWrapper(_cpDefinitionGroupedEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _cpDefinitionGroupedEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPDefinitionGroupedEntryWrapper)) {
			return false;
		}

		CPDefinitionGroupedEntryWrapper cpDefinitionGroupedEntryWrapper = (CPDefinitionGroupedEntryWrapper)obj;

		if (Objects.equals(_cpDefinitionGroupedEntry,
					cpDefinitionGroupedEntryWrapper._cpDefinitionGroupedEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _cpDefinitionGroupedEntry.getStagedModelType();
	}

	@Override
	public CPDefinitionGroupedEntry getWrappedModel() {
		return _cpDefinitionGroupedEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cpDefinitionGroupedEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cpDefinitionGroupedEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cpDefinitionGroupedEntry.resetOriginalValues();
	}

	private final CPDefinitionGroupedEntry _cpDefinitionGroupedEntry;
}