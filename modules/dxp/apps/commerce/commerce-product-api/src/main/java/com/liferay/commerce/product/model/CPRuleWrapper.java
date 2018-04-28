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
 * This class is a wrapper for {@link CPRule}.
 * </p>
 *
 * @author Marco Leo
 * @see CPRule
 * @generated
 */
@ProviderType
public class CPRuleWrapper implements CPRule, ModelWrapper<CPRule> {
	public CPRuleWrapper(CPRule cpRule) {
		_cpRule = cpRule;
	}

	@Override
	public Class<?> getModelClass() {
		return CPRule.class;
	}

	@Override
	public String getModelClassName() {
		return CPRule.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("CPRuleId", getCPRuleId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("active", isActive());
		attributes.put("type", getType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long CPRuleId = (Long)attributes.get("CPRuleId");

		if (CPRuleId != null) {
			setCPRuleId(CPRuleId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}
	}

	@Override
	public Object clone() {
		return new CPRuleWrapper((CPRule)_cpRule.clone());
	}

	@Override
	public int compareTo(CPRule cpRule) {
		return _cpRule.compareTo(cpRule);
	}

	/**
	* Returns the active of this cp rule.
	*
	* @return the active of this cp rule
	*/
	@Override
	public boolean getActive() {
		return _cpRule.getActive();
	}

	/**
	* Returns the company ID of this cp rule.
	*
	* @return the company ID of this cp rule
	*/
	@Override
	public long getCompanyId() {
		return _cpRule.getCompanyId();
	}

	/**
	* Returns the cp rule ID of this cp rule.
	*
	* @return the cp rule ID of this cp rule
	*/
	@Override
	public long getCPRuleId() {
		return _cpRule.getCPRuleId();
	}

	/**
	* Returns the create date of this cp rule.
	*
	* @return the create date of this cp rule
	*/
	@Override
	public Date getCreateDate() {
		return _cpRule.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cpRule.getExpandoBridge();
	}

	/**
	* Returns the group ID of this cp rule.
	*
	* @return the group ID of this cp rule
	*/
	@Override
	public long getGroupId() {
		return _cpRule.getGroupId();
	}

	/**
	* Returns the modified date of this cp rule.
	*
	* @return the modified date of this cp rule
	*/
	@Override
	public Date getModifiedDate() {
		return _cpRule.getModifiedDate();
	}

	/**
	* Returns the name of this cp rule.
	*
	* @return the name of this cp rule
	*/
	@Override
	public String getName() {
		return _cpRule.getName();
	}

	/**
	* Returns the primary key of this cp rule.
	*
	* @return the primary key of this cp rule
	*/
	@Override
	public long getPrimaryKey() {
		return _cpRule.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cpRule.getPrimaryKeyObj();
	}

	/**
	* Returns the type of this cp rule.
	*
	* @return the type of this cp rule
	*/
	@Override
	public String getType() {
		return _cpRule.getType();
	}

	/**
	* Returns the user ID of this cp rule.
	*
	* @return the user ID of this cp rule
	*/
	@Override
	public long getUserId() {
		return _cpRule.getUserId();
	}

	/**
	* Returns the user name of this cp rule.
	*
	* @return the user name of this cp rule
	*/
	@Override
	public String getUserName() {
		return _cpRule.getUserName();
	}

	/**
	* Returns the user uuid of this cp rule.
	*
	* @return the user uuid of this cp rule
	*/
	@Override
	public String getUserUuid() {
		return _cpRule.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _cpRule.hashCode();
	}

	/**
	* Returns <code>true</code> if this cp rule is active.
	*
	* @return <code>true</code> if this cp rule is active; <code>false</code> otherwise
	*/
	@Override
	public boolean isActive() {
		return _cpRule.isActive();
	}

	@Override
	public boolean isCachedModel() {
		return _cpRule.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cpRule.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _cpRule.isNew();
	}

	@Override
	public void persist() {
		_cpRule.persist();
	}

	/**
	* Sets whether this cp rule is active.
	*
	* @param active the active of this cp rule
	*/
	@Override
	public void setActive(boolean active) {
		_cpRule.setActive(active);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cpRule.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this cp rule.
	*
	* @param companyId the company ID of this cp rule
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cpRule.setCompanyId(companyId);
	}

	/**
	* Sets the cp rule ID of this cp rule.
	*
	* @param CPRuleId the cp rule ID of this cp rule
	*/
	@Override
	public void setCPRuleId(long CPRuleId) {
		_cpRule.setCPRuleId(CPRuleId);
	}

	/**
	* Sets the create date of this cp rule.
	*
	* @param createDate the create date of this cp rule
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cpRule.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cpRule.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cpRule.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cpRule.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this cp rule.
	*
	* @param groupId the group ID of this cp rule
	*/
	@Override
	public void setGroupId(long groupId) {
		_cpRule.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this cp rule.
	*
	* @param modifiedDate the modified date of this cp rule
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cpRule.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this cp rule.
	*
	* @param name the name of this cp rule
	*/
	@Override
	public void setName(String name) {
		_cpRule.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_cpRule.setNew(n);
	}

	/**
	* Sets the primary key of this cp rule.
	*
	* @param primaryKey the primary key of this cp rule
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cpRule.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cpRule.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the type of this cp rule.
	*
	* @param type the type of this cp rule
	*/
	@Override
	public void setType(String type) {
		_cpRule.setType(type);
	}

	/**
	* Sets the user ID of this cp rule.
	*
	* @param userId the user ID of this cp rule
	*/
	@Override
	public void setUserId(long userId) {
		_cpRule.setUserId(userId);
	}

	/**
	* Sets the user name of this cp rule.
	*
	* @param userName the user name of this cp rule
	*/
	@Override
	public void setUserName(String userName) {
		_cpRule.setUserName(userName);
	}

	/**
	* Sets the user uuid of this cp rule.
	*
	* @param userUuid the user uuid of this cp rule
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_cpRule.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CPRule> toCacheModel() {
		return _cpRule.toCacheModel();
	}

	@Override
	public CPRule toEscapedModel() {
		return new CPRuleWrapper(_cpRule.toEscapedModel());
	}

	@Override
	public String toString() {
		return _cpRule.toString();
	}

	@Override
	public CPRule toUnescapedModel() {
		return new CPRuleWrapper(_cpRule.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _cpRule.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPRuleWrapper)) {
			return false;
		}

		CPRuleWrapper cpRuleWrapper = (CPRuleWrapper)obj;

		if (Objects.equals(_cpRule, cpRuleWrapper._cpRule)) {
			return true;
		}

		return false;
	}

	@Override
	public CPRule getWrappedModel() {
		return _cpRule;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cpRule.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cpRule.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cpRule.resetOriginalValues();
	}

	private final CPRule _cpRule;
}