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
 * This class is a wrapper for {@link CPRuleAssetCategoryRel}.
 * </p>
 *
 * @author Marco Leo
 * @see CPRuleAssetCategoryRel
 * @generated
 */
@ProviderType
public class CPRuleAssetCategoryRelWrapper implements CPRuleAssetCategoryRel,
	ModelWrapper<CPRuleAssetCategoryRel> {
	public CPRuleAssetCategoryRelWrapper(
		CPRuleAssetCategoryRel cpRuleAssetCategoryRel) {
		_cpRuleAssetCategoryRel = cpRuleAssetCategoryRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CPRuleAssetCategoryRel.class;
	}

	@Override
	public String getModelClassName() {
		return CPRuleAssetCategoryRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("CPRuleAssetCategoryRelId", getCPRuleAssetCategoryRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CPRuleId", getCPRuleId());
		attributes.put("assetCategoryId", getAssetCategoryId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long CPRuleAssetCategoryRelId = (Long)attributes.get(
				"CPRuleAssetCategoryRelId");

		if (CPRuleAssetCategoryRelId != null) {
			setCPRuleAssetCategoryRelId(CPRuleAssetCategoryRelId);
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

		Long assetCategoryId = (Long)attributes.get("assetCategoryId");

		if (assetCategoryId != null) {
			setAssetCategoryId(assetCategoryId);
		}
	}

	@Override
	public Object clone() {
		return new CPRuleAssetCategoryRelWrapper((CPRuleAssetCategoryRel)_cpRuleAssetCategoryRel.clone());
	}

	@Override
	public int compareTo(CPRuleAssetCategoryRel cpRuleAssetCategoryRel) {
		return _cpRuleAssetCategoryRel.compareTo(cpRuleAssetCategoryRel);
	}

	/**
	* Returns the asset category ID of this cp rule asset category rel.
	*
	* @return the asset category ID of this cp rule asset category rel
	*/
	@Override
	public long getAssetCategoryId() {
		return _cpRuleAssetCategoryRel.getAssetCategoryId();
	}

	/**
	* Returns the company ID of this cp rule asset category rel.
	*
	* @return the company ID of this cp rule asset category rel
	*/
	@Override
	public long getCompanyId() {
		return _cpRuleAssetCategoryRel.getCompanyId();
	}

	/**
	* Returns the cp rule asset category rel ID of this cp rule asset category rel.
	*
	* @return the cp rule asset category rel ID of this cp rule asset category rel
	*/
	@Override
	public long getCPRuleAssetCategoryRelId() {
		return _cpRuleAssetCategoryRel.getCPRuleAssetCategoryRelId();
	}

	/**
	* Returns the cp rule ID of this cp rule asset category rel.
	*
	* @return the cp rule ID of this cp rule asset category rel
	*/
	@Override
	public long getCPRuleId() {
		return _cpRuleAssetCategoryRel.getCPRuleId();
	}

	/**
	* Returns the create date of this cp rule asset category rel.
	*
	* @return the create date of this cp rule asset category rel
	*/
	@Override
	public Date getCreateDate() {
		return _cpRuleAssetCategoryRel.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cpRuleAssetCategoryRel.getExpandoBridge();
	}

	/**
	* Returns the group ID of this cp rule asset category rel.
	*
	* @return the group ID of this cp rule asset category rel
	*/
	@Override
	public long getGroupId() {
		return _cpRuleAssetCategoryRel.getGroupId();
	}

	/**
	* Returns the modified date of this cp rule asset category rel.
	*
	* @return the modified date of this cp rule asset category rel
	*/
	@Override
	public Date getModifiedDate() {
		return _cpRuleAssetCategoryRel.getModifiedDate();
	}

	/**
	* Returns the primary key of this cp rule asset category rel.
	*
	* @return the primary key of this cp rule asset category rel
	*/
	@Override
	public long getPrimaryKey() {
		return _cpRuleAssetCategoryRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cpRuleAssetCategoryRel.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this cp rule asset category rel.
	*
	* @return the user ID of this cp rule asset category rel
	*/
	@Override
	public long getUserId() {
		return _cpRuleAssetCategoryRel.getUserId();
	}

	/**
	* Returns the user name of this cp rule asset category rel.
	*
	* @return the user name of this cp rule asset category rel
	*/
	@Override
	public String getUserName() {
		return _cpRuleAssetCategoryRel.getUserName();
	}

	/**
	* Returns the user uuid of this cp rule asset category rel.
	*
	* @return the user uuid of this cp rule asset category rel
	*/
	@Override
	public String getUserUuid() {
		return _cpRuleAssetCategoryRel.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _cpRuleAssetCategoryRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _cpRuleAssetCategoryRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cpRuleAssetCategoryRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _cpRuleAssetCategoryRel.isNew();
	}

	@Override
	public void persist() {
		_cpRuleAssetCategoryRel.persist();
	}

	/**
	* Sets the asset category ID of this cp rule asset category rel.
	*
	* @param assetCategoryId the asset category ID of this cp rule asset category rel
	*/
	@Override
	public void setAssetCategoryId(long assetCategoryId) {
		_cpRuleAssetCategoryRel.setAssetCategoryId(assetCategoryId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cpRuleAssetCategoryRel.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this cp rule asset category rel.
	*
	* @param companyId the company ID of this cp rule asset category rel
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cpRuleAssetCategoryRel.setCompanyId(companyId);
	}

	/**
	* Sets the cp rule asset category rel ID of this cp rule asset category rel.
	*
	* @param CPRuleAssetCategoryRelId the cp rule asset category rel ID of this cp rule asset category rel
	*/
	@Override
	public void setCPRuleAssetCategoryRelId(long CPRuleAssetCategoryRelId) {
		_cpRuleAssetCategoryRel.setCPRuleAssetCategoryRelId(CPRuleAssetCategoryRelId);
	}

	/**
	* Sets the cp rule ID of this cp rule asset category rel.
	*
	* @param CPRuleId the cp rule ID of this cp rule asset category rel
	*/
	@Override
	public void setCPRuleId(long CPRuleId) {
		_cpRuleAssetCategoryRel.setCPRuleId(CPRuleId);
	}

	/**
	* Sets the create date of this cp rule asset category rel.
	*
	* @param createDate the create date of this cp rule asset category rel
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cpRuleAssetCategoryRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cpRuleAssetCategoryRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cpRuleAssetCategoryRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cpRuleAssetCategoryRel.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this cp rule asset category rel.
	*
	* @param groupId the group ID of this cp rule asset category rel
	*/
	@Override
	public void setGroupId(long groupId) {
		_cpRuleAssetCategoryRel.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this cp rule asset category rel.
	*
	* @param modifiedDate the modified date of this cp rule asset category rel
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cpRuleAssetCategoryRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_cpRuleAssetCategoryRel.setNew(n);
	}

	/**
	* Sets the primary key of this cp rule asset category rel.
	*
	* @param primaryKey the primary key of this cp rule asset category rel
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cpRuleAssetCategoryRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cpRuleAssetCategoryRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this cp rule asset category rel.
	*
	* @param userId the user ID of this cp rule asset category rel
	*/
	@Override
	public void setUserId(long userId) {
		_cpRuleAssetCategoryRel.setUserId(userId);
	}

	/**
	* Sets the user name of this cp rule asset category rel.
	*
	* @param userName the user name of this cp rule asset category rel
	*/
	@Override
	public void setUserName(String userName) {
		_cpRuleAssetCategoryRel.setUserName(userName);
	}

	/**
	* Sets the user uuid of this cp rule asset category rel.
	*
	* @param userUuid the user uuid of this cp rule asset category rel
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_cpRuleAssetCategoryRel.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CPRuleAssetCategoryRel> toCacheModel() {
		return _cpRuleAssetCategoryRel.toCacheModel();
	}

	@Override
	public CPRuleAssetCategoryRel toEscapedModel() {
		return new CPRuleAssetCategoryRelWrapper(_cpRuleAssetCategoryRel.toEscapedModel());
	}

	@Override
	public String toString() {
		return _cpRuleAssetCategoryRel.toString();
	}

	@Override
	public CPRuleAssetCategoryRel toUnescapedModel() {
		return new CPRuleAssetCategoryRelWrapper(_cpRuleAssetCategoryRel.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _cpRuleAssetCategoryRel.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPRuleAssetCategoryRelWrapper)) {
			return false;
		}

		CPRuleAssetCategoryRelWrapper cpRuleAssetCategoryRelWrapper = (CPRuleAssetCategoryRelWrapper)obj;

		if (Objects.equals(_cpRuleAssetCategoryRel,
					cpRuleAssetCategoryRelWrapper._cpRuleAssetCategoryRel)) {
			return true;
		}

		return false;
	}

	@Override
	public CPRuleAssetCategoryRel getWrappedModel() {
		return _cpRuleAssetCategoryRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cpRuleAssetCategoryRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cpRuleAssetCategoryRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cpRuleAssetCategoryRel.resetOriginalValues();
	}

	private final CPRuleAssetCategoryRel _cpRuleAssetCategoryRel;
}