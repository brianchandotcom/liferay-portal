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
 * This class is a wrapper for {@link CPDefinitionAvailabilityRange}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionAvailabilityRange
 * @generated
 */
@ProviderType
public class CPDefinitionAvailabilityRangeWrapper
	implements CPDefinitionAvailabilityRange,
		ModelWrapper<CPDefinitionAvailabilityRange> {
	public CPDefinitionAvailabilityRangeWrapper(
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange) {
		_cpDefinitionAvailabilityRange = cpDefinitionAvailabilityRange;
	}

	@Override
	public Class<?> getModelClass() {
		return CPDefinitionAvailabilityRange.class;
	}

	@Override
	public String getModelClassName() {
		return CPDefinitionAvailabilityRange.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("CPDefinitionAvailabilityRangeId",
			getCPDefinitionAvailabilityRangeId());
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

		Long CPDefinitionAvailabilityRangeId = (Long)attributes.get(
				"CPDefinitionAvailabilityRangeId");

		if (CPDefinitionAvailabilityRangeId != null) {
			setCPDefinitionAvailabilityRangeId(CPDefinitionAvailabilityRangeId);
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
	public Object clone() {
		return new CPDefinitionAvailabilityRangeWrapper((CPDefinitionAvailabilityRange)_cpDefinitionAvailabilityRange.clone());
	}

	@Override
	public int compareTo(
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange) {
		return _cpDefinitionAvailabilityRange.compareTo(cpDefinitionAvailabilityRange);
	}

	@Override
	public CommerceAvailabilityRange getCommerceAvailabilityRange()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionAvailabilityRange.getCommerceAvailabilityRange();
	}

	/**
	* Returns the commerce availability range ID of this cp definition availability range.
	*
	* @return the commerce availability range ID of this cp definition availability range
	*/
	@Override
	public long getCommerceAvailabilityRangeId() {
		return _cpDefinitionAvailabilityRange.getCommerceAvailabilityRangeId();
	}

	/**
	* Returns the company ID of this cp definition availability range.
	*
	* @return the company ID of this cp definition availability range
	*/
	@Override
	public long getCompanyId() {
		return _cpDefinitionAvailabilityRange.getCompanyId();
	}

	/**
	* Returns the cp definition availability range ID of this cp definition availability range.
	*
	* @return the cp definition availability range ID of this cp definition availability range
	*/
	@Override
	public long getCPDefinitionAvailabilityRangeId() {
		return _cpDefinitionAvailabilityRange.getCPDefinitionAvailabilityRangeId();
	}

	/**
	* Returns the cp definition ID of this cp definition availability range.
	*
	* @return the cp definition ID of this cp definition availability range
	*/
	@Override
	public long getCPDefinitionId() {
		return _cpDefinitionAvailabilityRange.getCPDefinitionId();
	}

	/**
	* Returns the create date of this cp definition availability range.
	*
	* @return the create date of this cp definition availability range
	*/
	@Override
	public Date getCreateDate() {
		return _cpDefinitionAvailabilityRange.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cpDefinitionAvailabilityRange.getExpandoBridge();
	}

	/**
	* Returns the group ID of this cp definition availability range.
	*
	* @return the group ID of this cp definition availability range
	*/
	@Override
	public long getGroupId() {
		return _cpDefinitionAvailabilityRange.getGroupId();
	}

	/**
	* Returns the last publish date of this cp definition availability range.
	*
	* @return the last publish date of this cp definition availability range
	*/
	@Override
	public Date getLastPublishDate() {
		return _cpDefinitionAvailabilityRange.getLastPublishDate();
	}

	/**
	* Returns the modified date of this cp definition availability range.
	*
	* @return the modified date of this cp definition availability range
	*/
	@Override
	public Date getModifiedDate() {
		return _cpDefinitionAvailabilityRange.getModifiedDate();
	}

	/**
	* Returns the primary key of this cp definition availability range.
	*
	* @return the primary key of this cp definition availability range
	*/
	@Override
	public long getPrimaryKey() {
		return _cpDefinitionAvailabilityRange.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cpDefinitionAvailabilityRange.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this cp definition availability range.
	*
	* @return the user ID of this cp definition availability range
	*/
	@Override
	public long getUserId() {
		return _cpDefinitionAvailabilityRange.getUserId();
	}

	/**
	* Returns the user name of this cp definition availability range.
	*
	* @return the user name of this cp definition availability range
	*/
	@Override
	public String getUserName() {
		return _cpDefinitionAvailabilityRange.getUserName();
	}

	/**
	* Returns the user uuid of this cp definition availability range.
	*
	* @return the user uuid of this cp definition availability range
	*/
	@Override
	public String getUserUuid() {
		return _cpDefinitionAvailabilityRange.getUserUuid();
	}

	/**
	* Returns the uuid of this cp definition availability range.
	*
	* @return the uuid of this cp definition availability range
	*/
	@Override
	public String getUuid() {
		return _cpDefinitionAvailabilityRange.getUuid();
	}

	@Override
	public int hashCode() {
		return _cpDefinitionAvailabilityRange.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _cpDefinitionAvailabilityRange.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cpDefinitionAvailabilityRange.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _cpDefinitionAvailabilityRange.isNew();
	}

	@Override
	public void persist() {
		_cpDefinitionAvailabilityRange.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cpDefinitionAvailabilityRange.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce availability range ID of this cp definition availability range.
	*
	* @param commerceAvailabilityRangeId the commerce availability range ID of this cp definition availability range
	*/
	@Override
	public void setCommerceAvailabilityRangeId(long commerceAvailabilityRangeId) {
		_cpDefinitionAvailabilityRange.setCommerceAvailabilityRangeId(commerceAvailabilityRangeId);
	}

	/**
	* Sets the company ID of this cp definition availability range.
	*
	* @param companyId the company ID of this cp definition availability range
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cpDefinitionAvailabilityRange.setCompanyId(companyId);
	}

	/**
	* Sets the cp definition availability range ID of this cp definition availability range.
	*
	* @param CPDefinitionAvailabilityRangeId the cp definition availability range ID of this cp definition availability range
	*/
	@Override
	public void setCPDefinitionAvailabilityRangeId(
		long CPDefinitionAvailabilityRangeId) {
		_cpDefinitionAvailabilityRange.setCPDefinitionAvailabilityRangeId(CPDefinitionAvailabilityRangeId);
	}

	/**
	* Sets the cp definition ID of this cp definition availability range.
	*
	* @param CPDefinitionId the cp definition ID of this cp definition availability range
	*/
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		_cpDefinitionAvailabilityRange.setCPDefinitionId(CPDefinitionId);
	}

	/**
	* Sets the create date of this cp definition availability range.
	*
	* @param createDate the create date of this cp definition availability range
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cpDefinitionAvailabilityRange.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cpDefinitionAvailabilityRange.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cpDefinitionAvailabilityRange.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cpDefinitionAvailabilityRange.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this cp definition availability range.
	*
	* @param groupId the group ID of this cp definition availability range
	*/
	@Override
	public void setGroupId(long groupId) {
		_cpDefinitionAvailabilityRange.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this cp definition availability range.
	*
	* @param lastPublishDate the last publish date of this cp definition availability range
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_cpDefinitionAvailabilityRange.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this cp definition availability range.
	*
	* @param modifiedDate the modified date of this cp definition availability range
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cpDefinitionAvailabilityRange.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_cpDefinitionAvailabilityRange.setNew(n);
	}

	/**
	* Sets the primary key of this cp definition availability range.
	*
	* @param primaryKey the primary key of this cp definition availability range
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cpDefinitionAvailabilityRange.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cpDefinitionAvailabilityRange.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this cp definition availability range.
	*
	* @param userId the user ID of this cp definition availability range
	*/
	@Override
	public void setUserId(long userId) {
		_cpDefinitionAvailabilityRange.setUserId(userId);
	}

	/**
	* Sets the user name of this cp definition availability range.
	*
	* @param userName the user name of this cp definition availability range
	*/
	@Override
	public void setUserName(String userName) {
		_cpDefinitionAvailabilityRange.setUserName(userName);
	}

	/**
	* Sets the user uuid of this cp definition availability range.
	*
	* @param userUuid the user uuid of this cp definition availability range
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_cpDefinitionAvailabilityRange.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this cp definition availability range.
	*
	* @param uuid the uuid of this cp definition availability range
	*/
	@Override
	public void setUuid(String uuid) {
		_cpDefinitionAvailabilityRange.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CPDefinitionAvailabilityRange> toCacheModel() {
		return _cpDefinitionAvailabilityRange.toCacheModel();
	}

	@Override
	public CPDefinitionAvailabilityRange toEscapedModel() {
		return new CPDefinitionAvailabilityRangeWrapper(_cpDefinitionAvailabilityRange.toEscapedModel());
	}

	@Override
	public String toString() {
		return _cpDefinitionAvailabilityRange.toString();
	}

	@Override
	public CPDefinitionAvailabilityRange toUnescapedModel() {
		return new CPDefinitionAvailabilityRangeWrapper(_cpDefinitionAvailabilityRange.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _cpDefinitionAvailabilityRange.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPDefinitionAvailabilityRangeWrapper)) {
			return false;
		}

		CPDefinitionAvailabilityRangeWrapper cpDefinitionAvailabilityRangeWrapper =
			(CPDefinitionAvailabilityRangeWrapper)obj;

		if (Objects.equals(_cpDefinitionAvailabilityRange,
					cpDefinitionAvailabilityRangeWrapper._cpDefinitionAvailabilityRange)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _cpDefinitionAvailabilityRange.getStagedModelType();
	}

	@Override
	public CPDefinitionAvailabilityRange getWrappedModel() {
		return _cpDefinitionAvailabilityRange;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cpDefinitionAvailabilityRange.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cpDefinitionAvailabilityRange.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cpDefinitionAvailabilityRange.resetOriginalValues();
	}

	private final CPDefinitionAvailabilityRange _cpDefinitionAvailabilityRange;
}