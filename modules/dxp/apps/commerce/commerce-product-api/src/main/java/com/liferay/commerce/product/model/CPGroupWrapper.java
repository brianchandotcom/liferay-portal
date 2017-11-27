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

package com.liferay.commerce.product.model;

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
 * This class is a wrapper for {@link CPGroup}.
 * </p>
 *
 * @author Marco Leo
 * @see CPGroup
 * @generated
 */
@ProviderType
public class CPGroupWrapper implements CPGroup, ModelWrapper<CPGroup> {
	public CPGroupWrapper(CPGroup cpGroup) {
		_cpGroup = cpGroup;
	}

	@Override
	public Class<?> getModelClass() {
		return CPGroup.class;
	}

	@Override
	public String getModelClassName() {
		return CPGroup.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("CPGroupId", getCPGroupId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long CPGroupId = (Long)attributes.get("CPGroupId");

		if (CPGroupId != null) {
			setCPGroupId(CPGroupId);
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
	}

	@Override
	public java.lang.Object clone() {
		return new CPGroupWrapper((CPGroup)_cpGroup.clone());
	}

	@Override
	public int compareTo(CPGroup cpGroup) {
		return _cpGroup.compareTo(cpGroup);
	}

	/**
	* Returns the company ID of this cp group.
	*
	* @return the company ID of this cp group
	*/
	@Override
	public long getCompanyId() {
		return _cpGroup.getCompanyId();
	}

	/**
	* Returns the cp group ID of this cp group.
	*
	* @return the cp group ID of this cp group
	*/
	@Override
	public long getCPGroupId() {
		return _cpGroup.getCPGroupId();
	}

	/**
	* Returns the create date of this cp group.
	*
	* @return the create date of this cp group
	*/
	@Override
	public Date getCreateDate() {
		return _cpGroup.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cpGroup.getExpandoBridge();
	}

	/**
	* Returns the group ID of this cp group.
	*
	* @return the group ID of this cp group
	*/
	@Override
	public long getGroupId() {
		return _cpGroup.getGroupId();
	}

	/**
	* Returns the modified date of this cp group.
	*
	* @return the modified date of this cp group
	*/
	@Override
	public Date getModifiedDate() {
		return _cpGroup.getModifiedDate();
	}

	/**
	* Returns the primary key of this cp group.
	*
	* @return the primary key of this cp group
	*/
	@Override
	public long getPrimaryKey() {
		return _cpGroup.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cpGroup.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this cp group.
	*
	* @return the user ID of this cp group
	*/
	@Override
	public long getUserId() {
		return _cpGroup.getUserId();
	}

	/**
	* Returns the user name of this cp group.
	*
	* @return the user name of this cp group
	*/
	@Override
	public java.lang.String getUserName() {
		return _cpGroup.getUserName();
	}

	/**
	* Returns the user uuid of this cp group.
	*
	* @return the user uuid of this cp group
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _cpGroup.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _cpGroup.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _cpGroup.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cpGroup.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _cpGroup.isNew();
	}

	@Override
	public void persist() {
		_cpGroup.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cpGroup.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this cp group.
	*
	* @param companyId the company ID of this cp group
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cpGroup.setCompanyId(companyId);
	}

	/**
	* Sets the cp group ID of this cp group.
	*
	* @param CPGroupId the cp group ID of this cp group
	*/
	@Override
	public void setCPGroupId(long CPGroupId) {
		_cpGroup.setCPGroupId(CPGroupId);
	}

	/**
	* Sets the create date of this cp group.
	*
	* @param createDate the create date of this cp group
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cpGroup.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cpGroup.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cpGroup.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cpGroup.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this cp group.
	*
	* @param groupId the group ID of this cp group
	*/
	@Override
	public void setGroupId(long groupId) {
		_cpGroup.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this cp group.
	*
	* @param modifiedDate the modified date of this cp group
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cpGroup.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_cpGroup.setNew(n);
	}

	/**
	* Sets the primary key of this cp group.
	*
	* @param primaryKey the primary key of this cp group
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cpGroup.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cpGroup.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this cp group.
	*
	* @param userId the user ID of this cp group
	*/
	@Override
	public void setUserId(long userId) {
		_cpGroup.setUserId(userId);
	}

	/**
	* Sets the user name of this cp group.
	*
	* @param userName the user name of this cp group
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_cpGroup.setUserName(userName);
	}

	/**
	* Sets the user uuid of this cp group.
	*
	* @param userUuid the user uuid of this cp group
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_cpGroup.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CPGroup> toCacheModel() {
		return _cpGroup.toCacheModel();
	}

	@Override
	public CPGroup toEscapedModel() {
		return new CPGroupWrapper(_cpGroup.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _cpGroup.toString();
	}

	@Override
	public CPGroup toUnescapedModel() {
		return new CPGroupWrapper(_cpGroup.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _cpGroup.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPGroupWrapper)) {
			return false;
		}

		CPGroupWrapper cpGroupWrapper = (CPGroupWrapper)obj;

		if (Objects.equals(_cpGroup, cpGroupWrapper._cpGroup)) {
			return true;
		}

		return false;
	}

	@Override
	public CPGroup getWrappedModel() {
		return _cpGroup;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cpGroup.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cpGroup.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cpGroup.resetOriginalValues();
	}

	private final CPGroup _cpGroup;
}