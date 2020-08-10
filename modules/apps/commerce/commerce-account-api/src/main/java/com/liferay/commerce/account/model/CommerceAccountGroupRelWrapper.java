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
 * This class is a wrapper for {@link CommerceAccountGroupRel}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceAccountGroupRel
 * @generated
 */
public class CommerceAccountGroupRelWrapper
	implements CommerceAccountGroupRel, ModelWrapper<CommerceAccountGroupRel> {

	public CommerceAccountGroupRelWrapper(
		CommerceAccountGroupRel commerceAccountGroupRel) {

		_commerceAccountGroupRel = commerceAccountGroupRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceAccountGroupRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceAccountGroupRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"commerceAccountGroupRelId", getCommerceAccountGroupRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("commerceAccountGroupId", getCommerceAccountGroupId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceAccountGroupRelId = (Long)attributes.get(
			"commerceAccountGroupRelId");

		if (commerceAccountGroupRelId != null) {
			setCommerceAccountGroupRelId(commerceAccountGroupRelId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long commerceAccountGroupId = (Long)attributes.get(
			"commerceAccountGroupId");

		if (commerceAccountGroupId != null) {
			setCommerceAccountGroupId(commerceAccountGroupId);
		}
	}

	@Override
	public Object clone() {
		return new CommerceAccountGroupRelWrapper(
			(CommerceAccountGroupRel)_commerceAccountGroupRel.clone());
	}

	@Override
	public int compareTo(CommerceAccountGroupRel commerceAccountGroupRel) {
		return _commerceAccountGroupRel.compareTo(commerceAccountGroupRel);
	}

	/**
	 * Returns the fully qualified class name of this commerce account group rel.
	 *
	 * @return the fully qualified class name of this commerce account group rel
	 */
	@Override
	public String getClassName() {
		return _commerceAccountGroupRel.getClassName();
	}

	/**
	 * Returns the class name ID of this commerce account group rel.
	 *
	 * @return the class name ID of this commerce account group rel
	 */
	@Override
	public long getClassNameId() {
		return _commerceAccountGroupRel.getClassNameId();
	}

	/**
	 * Returns the class pk of this commerce account group rel.
	 *
	 * @return the class pk of this commerce account group rel
	 */
	@Override
	public long getClassPK() {
		return _commerceAccountGroupRel.getClassPK();
	}

	@Override
	public CommerceAccountGroup getCommerceAccountGroup()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupRel.getCommerceAccountGroup();
	}

	/**
	 * Returns the commerce account group ID of this commerce account group rel.
	 *
	 * @return the commerce account group ID of this commerce account group rel
	 */
	@Override
	public long getCommerceAccountGroupId() {
		return _commerceAccountGroupRel.getCommerceAccountGroupId();
	}

	/**
	 * Returns the commerce account group rel ID of this commerce account group rel.
	 *
	 * @return the commerce account group rel ID of this commerce account group rel
	 */
	@Override
	public long getCommerceAccountGroupRelId() {
		return _commerceAccountGroupRel.getCommerceAccountGroupRelId();
	}

	/**
	 * Returns the company ID of this commerce account group rel.
	 *
	 * @return the company ID of this commerce account group rel
	 */
	@Override
	public long getCompanyId() {
		return _commerceAccountGroupRel.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce account group rel.
	 *
	 * @return the create date of this commerce account group rel
	 */
	@Override
	public Date getCreateDate() {
		return _commerceAccountGroupRel.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceAccountGroupRel.getExpandoBridge();
	}

	/**
	 * Returns the modified date of this commerce account group rel.
	 *
	 * @return the modified date of this commerce account group rel
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceAccountGroupRel.getModifiedDate();
	}

	/**
	 * Returns the primary key of this commerce account group rel.
	 *
	 * @return the primary key of this commerce account group rel
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceAccountGroupRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceAccountGroupRel.getPrimaryKeyObj();
	}

	/**
	 * Returns the user ID of this commerce account group rel.
	 *
	 * @return the user ID of this commerce account group rel
	 */
	@Override
	public long getUserId() {
		return _commerceAccountGroupRel.getUserId();
	}

	/**
	 * Returns the user name of this commerce account group rel.
	 *
	 * @return the user name of this commerce account group rel
	 */
	@Override
	public String getUserName() {
		return _commerceAccountGroupRel.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce account group rel.
	 *
	 * @return the user uuid of this commerce account group rel
	 */
	@Override
	public String getUserUuid() {
		return _commerceAccountGroupRel.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceAccountGroupRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceAccountGroupRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceAccountGroupRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceAccountGroupRel.isNew();
	}

	@Override
	public void persist() {
		_commerceAccountGroupRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceAccountGroupRel.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(String className) {
		_commerceAccountGroupRel.setClassName(className);
	}

	/**
	 * Sets the class name ID of this commerce account group rel.
	 *
	 * @param classNameId the class name ID of this commerce account group rel
	 */
	@Override
	public void setClassNameId(long classNameId) {
		_commerceAccountGroupRel.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this commerce account group rel.
	 *
	 * @param classPK the class pk of this commerce account group rel
	 */
	@Override
	public void setClassPK(long classPK) {
		_commerceAccountGroupRel.setClassPK(classPK);
	}

	/**
	 * Sets the commerce account group ID of this commerce account group rel.
	 *
	 * @param commerceAccountGroupId the commerce account group ID of this commerce account group rel
	 */
	@Override
	public void setCommerceAccountGroupId(long commerceAccountGroupId) {
		_commerceAccountGroupRel.setCommerceAccountGroupId(
			commerceAccountGroupId);
	}

	/**
	 * Sets the commerce account group rel ID of this commerce account group rel.
	 *
	 * @param commerceAccountGroupRelId the commerce account group rel ID of this commerce account group rel
	 */
	@Override
	public void setCommerceAccountGroupRelId(long commerceAccountGroupRelId) {
		_commerceAccountGroupRel.setCommerceAccountGroupRelId(
			commerceAccountGroupRelId);
	}

	/**
	 * Sets the company ID of this commerce account group rel.
	 *
	 * @param companyId the company ID of this commerce account group rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceAccountGroupRel.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce account group rel.
	 *
	 * @param createDate the create date of this commerce account group rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceAccountGroupRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceAccountGroupRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceAccountGroupRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceAccountGroupRel.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the modified date of this commerce account group rel.
	 *
	 * @param modifiedDate the modified date of this commerce account group rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceAccountGroupRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceAccountGroupRel.setNew(n);
	}

	/**
	 * Sets the primary key of this commerce account group rel.
	 *
	 * @param primaryKey the primary key of this commerce account group rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceAccountGroupRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceAccountGroupRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the user ID of this commerce account group rel.
	 *
	 * @param userId the user ID of this commerce account group rel
	 */
	@Override
	public void setUserId(long userId) {
		_commerceAccountGroupRel.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce account group rel.
	 *
	 * @param userName the user name of this commerce account group rel
	 */
	@Override
	public void setUserName(String userName) {
		_commerceAccountGroupRel.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce account group rel.
	 *
	 * @param userUuid the user uuid of this commerce account group rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceAccountGroupRel.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceAccountGroupRel>
		toCacheModel() {

		return _commerceAccountGroupRel.toCacheModel();
	}

	@Override
	public CommerceAccountGroupRel toEscapedModel() {
		return new CommerceAccountGroupRelWrapper(
			_commerceAccountGroupRel.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceAccountGroupRel.toString();
	}

	@Override
	public CommerceAccountGroupRel toUnescapedModel() {
		return new CommerceAccountGroupRelWrapper(
			_commerceAccountGroupRel.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceAccountGroupRel.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceAccountGroupRelWrapper)) {
			return false;
		}

		CommerceAccountGroupRelWrapper commerceAccountGroupRelWrapper =
			(CommerceAccountGroupRelWrapper)object;

		if (Objects.equals(
				_commerceAccountGroupRel,
				commerceAccountGroupRelWrapper._commerceAccountGroupRel)) {

			return true;
		}

		return false;
	}

	@Override
	public CommerceAccountGroupRel getWrappedModel() {
		return _commerceAccountGroupRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceAccountGroupRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceAccountGroupRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceAccountGroupRel.resetOriginalValues();
	}

	private final CommerceAccountGroupRel _commerceAccountGroupRel;

}