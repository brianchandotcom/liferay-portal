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

package com.liferay.commerce.discount.model;

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
 * This class is a wrapper for {@link CommerceDiscountRule}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceDiscountRule
 * @generated
 */
@ProviderType
public class CommerceDiscountRuleWrapper implements CommerceDiscountRule,
	ModelWrapper<CommerceDiscountRule> {
	public CommerceDiscountRuleWrapper(
		CommerceDiscountRule commerceDiscountRule) {
		_commerceDiscountRule = commerceDiscountRule;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceDiscountRule.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceDiscountRule.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceDiscountRuleId", getCommerceDiscountRuleId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceDiscountId", getCommerceDiscountId());
		attributes.put("type", getType());
		attributes.put("typeSettings", getTypeSettings());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceDiscountRuleId = (Long)attributes.get(
				"commerceDiscountRuleId");

		if (commerceDiscountRuleId != null) {
			setCommerceDiscountRuleId(commerceDiscountRuleId);
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

		Long commerceDiscountId = (Long)attributes.get("commerceDiscountId");

		if (commerceDiscountId != null) {
			setCommerceDiscountId(commerceDiscountId);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String typeSettings = (String)attributes.get("typeSettings");

		if (typeSettings != null) {
			setTypeSettings(typeSettings);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new CommerceDiscountRuleWrapper((CommerceDiscountRule)_commerceDiscountRule.clone());
	}

	@Override
	public int compareTo(CommerceDiscountRule commerceDiscountRule) {
		return _commerceDiscountRule.compareTo(commerceDiscountRule);
	}

	/**
	* Returns the commerce discount ID of this commerce discount rule.
	*
	* @return the commerce discount ID of this commerce discount rule
	*/
	@Override
	public long getCommerceDiscountId() {
		return _commerceDiscountRule.getCommerceDiscountId();
	}

	/**
	* Returns the commerce discount rule ID of this commerce discount rule.
	*
	* @return the commerce discount rule ID of this commerce discount rule
	*/
	@Override
	public long getCommerceDiscountRuleId() {
		return _commerceDiscountRule.getCommerceDiscountRuleId();
	}

	/**
	* Returns the company ID of this commerce discount rule.
	*
	* @return the company ID of this commerce discount rule
	*/
	@Override
	public long getCompanyId() {
		return _commerceDiscountRule.getCompanyId();
	}

	/**
	* Returns the create date of this commerce discount rule.
	*
	* @return the create date of this commerce discount rule
	*/
	@Override
	public Date getCreateDate() {
		return _commerceDiscountRule.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceDiscountRule.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce discount rule.
	*
	* @return the group ID of this commerce discount rule
	*/
	@Override
	public long getGroupId() {
		return _commerceDiscountRule.getGroupId();
	}

	/**
	* Returns the modified date of this commerce discount rule.
	*
	* @return the modified date of this commerce discount rule
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceDiscountRule.getModifiedDate();
	}

	/**
	* Returns the primary key of this commerce discount rule.
	*
	* @return the primary key of this commerce discount rule
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceDiscountRule.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceDiscountRule.getPrimaryKeyObj();
	}

	/**
	* Returns the type of this commerce discount rule.
	*
	* @return the type of this commerce discount rule
	*/
	@Override
	public java.lang.String getType() {
		return _commerceDiscountRule.getType();
	}

	/**
	* Returns the type settings of this commerce discount rule.
	*
	* @return the type settings of this commerce discount rule
	*/
	@Override
	public java.lang.String getTypeSettings() {
		return _commerceDiscountRule.getTypeSettings();
	}

	/**
	* Returns the user ID of this commerce discount rule.
	*
	* @return the user ID of this commerce discount rule
	*/
	@Override
	public long getUserId() {
		return _commerceDiscountRule.getUserId();
	}

	/**
	* Returns the user name of this commerce discount rule.
	*
	* @return the user name of this commerce discount rule
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceDiscountRule.getUserName();
	}

	/**
	* Returns the user uuid of this commerce discount rule.
	*
	* @return the user uuid of this commerce discount rule
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceDiscountRule.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceDiscountRule.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceDiscountRule.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceDiscountRule.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceDiscountRule.isNew();
	}

	@Override
	public void persist() {
		_commerceDiscountRule.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceDiscountRule.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce discount ID of this commerce discount rule.
	*
	* @param commerceDiscountId the commerce discount ID of this commerce discount rule
	*/
	@Override
	public void setCommerceDiscountId(long commerceDiscountId) {
		_commerceDiscountRule.setCommerceDiscountId(commerceDiscountId);
	}

	/**
	* Sets the commerce discount rule ID of this commerce discount rule.
	*
	* @param commerceDiscountRuleId the commerce discount rule ID of this commerce discount rule
	*/
	@Override
	public void setCommerceDiscountRuleId(long commerceDiscountRuleId) {
		_commerceDiscountRule.setCommerceDiscountRuleId(commerceDiscountRuleId);
	}

	/**
	* Sets the company ID of this commerce discount rule.
	*
	* @param companyId the company ID of this commerce discount rule
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceDiscountRule.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce discount rule.
	*
	* @param createDate the create date of this commerce discount rule
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceDiscountRule.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceDiscountRule.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceDiscountRule.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceDiscountRule.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce discount rule.
	*
	* @param groupId the group ID of this commerce discount rule
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceDiscountRule.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce discount rule.
	*
	* @param modifiedDate the modified date of this commerce discount rule
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceDiscountRule.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceDiscountRule.setNew(n);
	}

	/**
	* Sets the primary key of this commerce discount rule.
	*
	* @param primaryKey the primary key of this commerce discount rule
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceDiscountRule.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceDiscountRule.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the type of this commerce discount rule.
	*
	* @param type the type of this commerce discount rule
	*/
	@Override
	public void setType(java.lang.String type) {
		_commerceDiscountRule.setType(type);
	}

	/**
	* Sets the type settings of this commerce discount rule.
	*
	* @param typeSettings the type settings of this commerce discount rule
	*/
	@Override
	public void setTypeSettings(java.lang.String typeSettings) {
		_commerceDiscountRule.setTypeSettings(typeSettings);
	}

	/**
	* Sets the user ID of this commerce discount rule.
	*
	* @param userId the user ID of this commerce discount rule
	*/
	@Override
	public void setUserId(long userId) {
		_commerceDiscountRule.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce discount rule.
	*
	* @param userName the user name of this commerce discount rule
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceDiscountRule.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce discount rule.
	*
	* @param userUuid the user uuid of this commerce discount rule
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceDiscountRule.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceDiscountRule> toCacheModel() {
		return _commerceDiscountRule.toCacheModel();
	}

	@Override
	public CommerceDiscountRule toEscapedModel() {
		return new CommerceDiscountRuleWrapper(_commerceDiscountRule.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _commerceDiscountRule.toString();
	}

	@Override
	public CommerceDiscountRule toUnescapedModel() {
		return new CommerceDiscountRuleWrapper(_commerceDiscountRule.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceDiscountRule.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceDiscountRuleWrapper)) {
			return false;
		}

		CommerceDiscountRuleWrapper commerceDiscountRuleWrapper = (CommerceDiscountRuleWrapper)obj;

		if (Objects.equals(_commerceDiscountRule,
					commerceDiscountRuleWrapper._commerceDiscountRule)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceDiscountRule getWrappedModel() {
		return _commerceDiscountRule;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceDiscountRule.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceDiscountRule.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceDiscountRule.resetOriginalValues();
	}

	private final CommerceDiscountRule _commerceDiscountRule;
}