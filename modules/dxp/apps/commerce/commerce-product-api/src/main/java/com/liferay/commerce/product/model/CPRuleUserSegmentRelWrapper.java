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
 * This class is a wrapper for {@link CPRuleUserSegmentRel}.
 * </p>
 *
 * @author Marco Leo
 * @see CPRuleUserSegmentRel
 * @generated
 */
@ProviderType
public class CPRuleUserSegmentRelWrapper implements CPRuleUserSegmentRel,
	ModelWrapper<CPRuleUserSegmentRel> {
	public CPRuleUserSegmentRelWrapper(
		CPRuleUserSegmentRel cpRuleUserSegmentRel) {
		_cpRuleUserSegmentRel = cpRuleUserSegmentRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CPRuleUserSegmentRel.class;
	}

	@Override
	public String getModelClassName() {
		return CPRuleUserSegmentRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("CPRuleUserSegmentRelId", getCPRuleUserSegmentRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CPRuleId", getCPRuleId());
		attributes.put("commerceUserSegmentEntryId",
			isCommerceUserSegmentEntryId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long CPRuleUserSegmentRelId = (Long)attributes.get(
				"CPRuleUserSegmentRelId");

		if (CPRuleUserSegmentRelId != null) {
			setCPRuleUserSegmentRelId(CPRuleUserSegmentRelId);
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

		Long CPRuleId = (Long)attributes.get("CPRuleId");

		if (CPRuleId != null) {
			setCPRuleId(CPRuleId);
		}

		Boolean commerceUserSegmentEntryId = (Boolean)attributes.get(
				"commerceUserSegmentEntryId");

		if (commerceUserSegmentEntryId != null) {
			setCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new CPRuleUserSegmentRelWrapper((CPRuleUserSegmentRel)_cpRuleUserSegmentRel.clone());
	}

	@Override
	public int compareTo(CPRuleUserSegmentRel cpRuleUserSegmentRel) {
		return _cpRuleUserSegmentRel.compareTo(cpRuleUserSegmentRel);
	}

	/**
	* Returns the commerce user segment entry ID of this cp rule user segment rel.
	*
	* @return the commerce user segment entry ID of this cp rule user segment rel
	*/
	@Override
	public boolean getCommerceUserSegmentEntryId() {
		return _cpRuleUserSegmentRel.getCommerceUserSegmentEntryId();
	}

	/**
	* Returns the company ID of this cp rule user segment rel.
	*
	* @return the company ID of this cp rule user segment rel
	*/
	@Override
	public long getCompanyId() {
		return _cpRuleUserSegmentRel.getCompanyId();
	}

	/**
	* Returns the cp rule ID of this cp rule user segment rel.
	*
	* @return the cp rule ID of this cp rule user segment rel
	*/
	@Override
	public long getCPRuleId() {
		return _cpRuleUserSegmentRel.getCPRuleId();
	}

	/**
	* Returns the cp rule user segment rel ID of this cp rule user segment rel.
	*
	* @return the cp rule user segment rel ID of this cp rule user segment rel
	*/
	@Override
	public long getCPRuleUserSegmentRelId() {
		return _cpRuleUserSegmentRel.getCPRuleUserSegmentRelId();
	}

	/**
	* Returns the create date of this cp rule user segment rel.
	*
	* @return the create date of this cp rule user segment rel
	*/
	@Override
	public Date getCreateDate() {
		return _cpRuleUserSegmentRel.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cpRuleUserSegmentRel.getExpandoBridge();
	}

	/**
	* Returns the group ID of this cp rule user segment rel.
	*
	* @return the group ID of this cp rule user segment rel
	*/
	@Override
	public long getGroupId() {
		return _cpRuleUserSegmentRel.getGroupId();
	}

	/**
	* Returns the modified date of this cp rule user segment rel.
	*
	* @return the modified date of this cp rule user segment rel
	*/
	@Override
	public Date getModifiedDate() {
		return _cpRuleUserSegmentRel.getModifiedDate();
	}

	/**
	* Returns the primary key of this cp rule user segment rel.
	*
	* @return the primary key of this cp rule user segment rel
	*/
	@Override
	public long getPrimaryKey() {
		return _cpRuleUserSegmentRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cpRuleUserSegmentRel.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this cp rule user segment rel.
	*
	* @return the user ID of this cp rule user segment rel
	*/
	@Override
	public long getUserId() {
		return _cpRuleUserSegmentRel.getUserId();
	}

	/**
	* Returns the user name of this cp rule user segment rel.
	*
	* @return the user name of this cp rule user segment rel
	*/
	@Override
	public java.lang.String getUserName() {
		return _cpRuleUserSegmentRel.getUserName();
	}

	/**
	* Returns the user uuid of this cp rule user segment rel.
	*
	* @return the user uuid of this cp rule user segment rel
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _cpRuleUserSegmentRel.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _cpRuleUserSegmentRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _cpRuleUserSegmentRel.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this cp rule user segment rel is commerce user segment entry ID.
	*
	* @return <code>true</code> if this cp rule user segment rel is commerce user segment entry ID; <code>false</code> otherwise
	*/
	@Override
	public boolean isCommerceUserSegmentEntryId() {
		return _cpRuleUserSegmentRel.isCommerceUserSegmentEntryId();
	}

	@Override
	public boolean isEscapedModel() {
		return _cpRuleUserSegmentRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _cpRuleUserSegmentRel.isNew();
	}

	@Override
	public void persist() {
		_cpRuleUserSegmentRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cpRuleUserSegmentRel.setCachedModel(cachedModel);
	}

	/**
	* Sets whether this cp rule user segment rel is commerce user segment entry ID.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID of this cp rule user segment rel
	*/
	@Override
	public void setCommerceUserSegmentEntryId(
		boolean commerceUserSegmentEntryId) {
		_cpRuleUserSegmentRel.setCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
	}

	/**
	* Sets the company ID of this cp rule user segment rel.
	*
	* @param companyId the company ID of this cp rule user segment rel
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cpRuleUserSegmentRel.setCompanyId(companyId);
	}

	/**
	* Sets the cp rule ID of this cp rule user segment rel.
	*
	* @param CPRuleId the cp rule ID of this cp rule user segment rel
	*/
	@Override
	public void setCPRuleId(long CPRuleId) {
		_cpRuleUserSegmentRel.setCPRuleId(CPRuleId);
	}

	/**
	* Sets the cp rule user segment rel ID of this cp rule user segment rel.
	*
	* @param CPRuleUserSegmentRelId the cp rule user segment rel ID of this cp rule user segment rel
	*/
	@Override
	public void setCPRuleUserSegmentRelId(long CPRuleUserSegmentRelId) {
		_cpRuleUserSegmentRel.setCPRuleUserSegmentRelId(CPRuleUserSegmentRelId);
	}

	/**
	* Sets the create date of this cp rule user segment rel.
	*
	* @param createDate the create date of this cp rule user segment rel
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cpRuleUserSegmentRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cpRuleUserSegmentRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cpRuleUserSegmentRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cpRuleUserSegmentRel.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this cp rule user segment rel.
	*
	* @param groupId the group ID of this cp rule user segment rel
	*/
	@Override
	public void setGroupId(long groupId) {
		_cpRuleUserSegmentRel.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this cp rule user segment rel.
	*
	* @param modifiedDate the modified date of this cp rule user segment rel
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cpRuleUserSegmentRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_cpRuleUserSegmentRel.setNew(n);
	}

	/**
	* Sets the primary key of this cp rule user segment rel.
	*
	* @param primaryKey the primary key of this cp rule user segment rel
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cpRuleUserSegmentRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cpRuleUserSegmentRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this cp rule user segment rel.
	*
	* @param userId the user ID of this cp rule user segment rel
	*/
	@Override
	public void setUserId(long userId) {
		_cpRuleUserSegmentRel.setUserId(userId);
	}

	/**
	* Sets the user name of this cp rule user segment rel.
	*
	* @param userName the user name of this cp rule user segment rel
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_cpRuleUserSegmentRel.setUserName(userName);
	}

	/**
	* Sets the user uuid of this cp rule user segment rel.
	*
	* @param userUuid the user uuid of this cp rule user segment rel
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_cpRuleUserSegmentRel.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CPRuleUserSegmentRel> toCacheModel() {
		return _cpRuleUserSegmentRel.toCacheModel();
	}

	@Override
	public CPRuleUserSegmentRel toEscapedModel() {
		return new CPRuleUserSegmentRelWrapper(_cpRuleUserSegmentRel.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _cpRuleUserSegmentRel.toString();
	}

	@Override
	public CPRuleUserSegmentRel toUnescapedModel() {
		return new CPRuleUserSegmentRelWrapper(_cpRuleUserSegmentRel.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _cpRuleUserSegmentRel.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPRuleUserSegmentRelWrapper)) {
			return false;
		}

		CPRuleUserSegmentRelWrapper cpRuleUserSegmentRelWrapper = (CPRuleUserSegmentRelWrapper)obj;

		if (Objects.equals(_cpRuleUserSegmentRel,
					cpRuleUserSegmentRelWrapper._cpRuleUserSegmentRel)) {
			return true;
		}

		return false;
	}

	@Override
	public CPRuleUserSegmentRel getWrappedModel() {
		return _cpRuleUserSegmentRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cpRuleUserSegmentRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cpRuleUserSegmentRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cpRuleUserSegmentRel.resetOriginalValues();
	}

	private final CPRuleUserSegmentRel _cpRuleUserSegmentRel;
}