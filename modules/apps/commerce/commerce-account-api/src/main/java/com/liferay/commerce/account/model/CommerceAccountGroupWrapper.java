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

package com.liferay.commerce.account.model;

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
 * This class is a wrapper for {@link CommerceAccountGroup}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceAccountGroup
 * @generated
 */
public class CommerceAccountGroupWrapper
	implements CommerceAccountGroup, ModelWrapper<CommerceAccountGroup> {

	public CommerceAccountGroupWrapper(
		CommerceAccountGroup commerceAccountGroup) {

		_commerceAccountGroup = commerceAccountGroup;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceAccountGroup.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceAccountGroup.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("commerceAccountGroupId", getCommerceAccountGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("type", getType());
		attributes.put("system", isSystem());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long commerceAccountGroupId = (Long)attributes.get(
			"commerceAccountGroupId");

		if (commerceAccountGroupId != null) {
			setCommerceAccountGroupId(commerceAccountGroupId);
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

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Boolean system = (Boolean)attributes.get("system");

		if (system != null) {
			setSystem(system);
		}
	}

	@Override
	public Object clone() {
		return new CommerceAccountGroupWrapper(
			(CommerceAccountGroup)_commerceAccountGroup.clone());
	}

	@Override
	public int compareTo(CommerceAccountGroup commerceAccountGroup) {
		return _commerceAccountGroup.compareTo(commerceAccountGroup);
	}

	/**
	 * Returns the commerce account group ID of this commerce account group.
	 *
	 * @return the commerce account group ID of this commerce account group
	 */
	@Override
	public long getCommerceAccountGroupId() {
		return _commerceAccountGroup.getCommerceAccountGroupId();
	}

	/**
	 * Returns the company ID of this commerce account group.
	 *
	 * @return the company ID of this commerce account group
	 */
	@Override
	public long getCompanyId() {
		return _commerceAccountGroup.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce account group.
	 *
	 * @return the create date of this commerce account group
	 */
	@Override
	public Date getCreateDate() {
		return _commerceAccountGroup.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceAccountGroup.getExpandoBridge();
	}

	/**
	 * Returns the external reference code of this commerce account group.
	 *
	 * @return the external reference code of this commerce account group
	 */
	@Override
	public String getExternalReferenceCode() {
		return _commerceAccountGroup.getExternalReferenceCode();
	}

	/**
	 * Returns the modified date of this commerce account group.
	 *
	 * @return the modified date of this commerce account group
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceAccountGroup.getModifiedDate();
	}

	/**
	 * Returns the name of this commerce account group.
	 *
	 * @return the name of this commerce account group
	 */
	@Override
	public String getName() {
		return _commerceAccountGroup.getName();
	}

	/**
	 * Returns the primary key of this commerce account group.
	 *
	 * @return the primary key of this commerce account group
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceAccountGroup.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceAccountGroup.getPrimaryKeyObj();
	}

	/**
	 * Returns the system of this commerce account group.
	 *
	 * @return the system of this commerce account group
	 */
	@Override
	public boolean getSystem() {
		return _commerceAccountGroup.getSystem();
	}

	/**
	 * Returns the type of this commerce account group.
	 *
	 * @return the type of this commerce account group
	 */
	@Override
	public int getType() {
		return _commerceAccountGroup.getType();
	}

	/**
	 * Returns the user ID of this commerce account group.
	 *
	 * @return the user ID of this commerce account group
	 */
	@Override
	public long getUserId() {
		return _commerceAccountGroup.getUserId();
	}

	/**
	 * Returns the user name of this commerce account group.
	 *
	 * @return the user name of this commerce account group
	 */
	@Override
	public String getUserName() {
		return _commerceAccountGroup.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce account group.
	 *
	 * @return the user uuid of this commerce account group
	 */
	@Override
	public String getUserUuid() {
		return _commerceAccountGroup.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceAccountGroup.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceAccountGroup.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceAccountGroup.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceAccountGroup.isNew();
	}

	/**
	 * Returns <code>true</code> if this commerce account group is system.
	 *
	 * @return <code>true</code> if this commerce account group is system; <code>false</code> otherwise
	 */
	@Override
	public boolean isSystem() {
		return _commerceAccountGroup.isSystem();
	}

	@Override
	public void persist() {
		_commerceAccountGroup.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceAccountGroup.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce account group ID of this commerce account group.
	 *
	 * @param commerceAccountGroupId the commerce account group ID of this commerce account group
	 */
	@Override
	public void setCommerceAccountGroupId(long commerceAccountGroupId) {
		_commerceAccountGroup.setCommerceAccountGroupId(commerceAccountGroupId);
	}

	/**
	 * Sets the company ID of this commerce account group.
	 *
	 * @param companyId the company ID of this commerce account group
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceAccountGroup.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce account group.
	 *
	 * @param createDate the create date of this commerce account group
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceAccountGroup.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceAccountGroup.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceAccountGroup.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceAccountGroup.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the external reference code of this commerce account group.
	 *
	 * @param externalReferenceCode the external reference code of this commerce account group
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		_commerceAccountGroup.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the modified date of this commerce account group.
	 *
	 * @param modifiedDate the modified date of this commerce account group
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceAccountGroup.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this commerce account group.
	 *
	 * @param name the name of this commerce account group
	 */
	@Override
	public void setName(String name) {
		_commerceAccountGroup.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_commerceAccountGroup.setNew(n);
	}

	/**
	 * Sets the primary key of this commerce account group.
	 *
	 * @param primaryKey the primary key of this commerce account group
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceAccountGroup.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceAccountGroup.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets whether this commerce account group is system.
	 *
	 * @param system the system of this commerce account group
	 */
	@Override
	public void setSystem(boolean system) {
		_commerceAccountGroup.setSystem(system);
	}

	/**
	 * Sets the type of this commerce account group.
	 *
	 * @param type the type of this commerce account group
	 */
	@Override
	public void setType(int type) {
		_commerceAccountGroup.setType(type);
	}

	/**
	 * Sets the user ID of this commerce account group.
	 *
	 * @param userId the user ID of this commerce account group
	 */
	@Override
	public void setUserId(long userId) {
		_commerceAccountGroup.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce account group.
	 *
	 * @param userName the user name of this commerce account group
	 */
	@Override
	public void setUserName(String userName) {
		_commerceAccountGroup.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce account group.
	 *
	 * @param userUuid the user uuid of this commerce account group
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceAccountGroup.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceAccountGroup>
		toCacheModel() {

		return _commerceAccountGroup.toCacheModel();
	}

	@Override
	public CommerceAccountGroup toEscapedModel() {
		return new CommerceAccountGroupWrapper(
			_commerceAccountGroup.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceAccountGroup.toString();
	}

	@Override
	public CommerceAccountGroup toUnescapedModel() {
		return new CommerceAccountGroupWrapper(
			_commerceAccountGroup.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceAccountGroup.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceAccountGroupWrapper)) {
			return false;
		}

		CommerceAccountGroupWrapper commerceAccountGroupWrapper =
			(CommerceAccountGroupWrapper)object;

		if (Objects.equals(
				_commerceAccountGroup,
				commerceAccountGroupWrapper._commerceAccountGroup)) {

			return true;
		}

		return false;
	}

	@Override
	public CommerceAccountGroup getWrappedModel() {
		return _commerceAccountGroup;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceAccountGroup.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceAccountGroup.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceAccountGroup.resetOriginalValues();
	}

	private final CommerceAccountGroup _commerceAccountGroup;

}