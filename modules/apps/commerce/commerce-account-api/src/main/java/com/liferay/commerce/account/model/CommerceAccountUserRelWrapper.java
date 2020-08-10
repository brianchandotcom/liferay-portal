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
 * This class is a wrapper for {@link CommerceAccountUserRel}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceAccountUserRel
 * @generated
 */
public class CommerceAccountUserRelWrapper
	implements CommerceAccountUserRel, ModelWrapper<CommerceAccountUserRel> {

	public CommerceAccountUserRelWrapper(
		CommerceAccountUserRel commerceAccountUserRel) {

		_commerceAccountUserRel = commerceAccountUserRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceAccountUserRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceAccountUserRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceAccountId", getCommerceAccountId());
		attributes.put("commerceAccountUserId", getCommerceAccountUserId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceAccountId = (Long)attributes.get("commerceAccountId");

		if (commerceAccountId != null) {
			setCommerceAccountId(commerceAccountId);
		}

		Long commerceAccountUserId = (Long)attributes.get(
			"commerceAccountUserId");

		if (commerceAccountUserId != null) {
			setCommerceAccountUserId(commerceAccountUserId);
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
	public Object clone() {
		return new CommerceAccountUserRelWrapper(
			(CommerceAccountUserRel)_commerceAccountUserRel.clone());
	}

	@Override
	public int compareTo(CommerceAccountUserRel commerceAccountUserRel) {
		return _commerceAccountUserRel.compareTo(commerceAccountUserRel);
	}

	/**
	 * Returns the commerce account ID of this commerce account user rel.
	 *
	 * @return the commerce account ID of this commerce account user rel
	 */
	@Override
	public long getCommerceAccountId() {
		return _commerceAccountUserRel.getCommerceAccountId();
	}

	/**
	 * Returns the commerce account user ID of this commerce account user rel.
	 *
	 * @return the commerce account user ID of this commerce account user rel
	 */
	@Override
	public long getCommerceAccountUserId() {
		return _commerceAccountUserRel.getCommerceAccountUserId();
	}

	/**
	 * Returns the commerce account user uuid of this commerce account user rel.
	 *
	 * @return the commerce account user uuid of this commerce account user rel
	 */
	@Override
	public String getCommerceAccountUserUuid() {
		return _commerceAccountUserRel.getCommerceAccountUserUuid();
	}

	/**
	 * Returns the company ID of this commerce account user rel.
	 *
	 * @return the company ID of this commerce account user rel
	 */
	@Override
	public long getCompanyId() {
		return _commerceAccountUserRel.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce account user rel.
	 *
	 * @return the create date of this commerce account user rel
	 */
	@Override
	public Date getCreateDate() {
		return _commerceAccountUserRel.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceAccountUserRel.getExpandoBridge();
	}

	/**
	 * Returns the modified date of this commerce account user rel.
	 *
	 * @return the modified date of this commerce account user rel
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceAccountUserRel.getModifiedDate();
	}

	/**
	 * Returns the primary key of this commerce account user rel.
	 *
	 * @return the primary key of this commerce account user rel
	 */
	@Override
	public
		com.liferay.commerce.account.service.persistence.
			CommerceAccountUserRelPK getPrimaryKey() {

		return _commerceAccountUserRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceAccountUserRel.getPrimaryKeyObj();
	}

	@Override
	public com.liferay.portal.kernel.model.User getUser()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountUserRel.getUser();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.UserGroupRole>
			getUserGroupRoles()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountUserRel.getUserGroupRoles();
	}

	/**
	 * Returns the user ID of this commerce account user rel.
	 *
	 * @return the user ID of this commerce account user rel
	 */
	@Override
	public long getUserId() {
		return _commerceAccountUserRel.getUserId();
	}

	/**
	 * Returns the user name of this commerce account user rel.
	 *
	 * @return the user name of this commerce account user rel
	 */
	@Override
	public String getUserName() {
		return _commerceAccountUserRel.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce account user rel.
	 *
	 * @return the user uuid of this commerce account user rel
	 */
	@Override
	public String getUserUuid() {
		return _commerceAccountUserRel.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceAccountUserRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceAccountUserRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceAccountUserRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceAccountUserRel.isNew();
	}

	@Override
	public void persist() {
		_commerceAccountUserRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceAccountUserRel.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce account ID of this commerce account user rel.
	 *
	 * @param commerceAccountId the commerce account ID of this commerce account user rel
	 */
	@Override
	public void setCommerceAccountId(long commerceAccountId) {
		_commerceAccountUserRel.setCommerceAccountId(commerceAccountId);
	}

	/**
	 * Sets the commerce account user ID of this commerce account user rel.
	 *
	 * @param commerceAccountUserId the commerce account user ID of this commerce account user rel
	 */
	@Override
	public void setCommerceAccountUserId(long commerceAccountUserId) {
		_commerceAccountUserRel.setCommerceAccountUserId(commerceAccountUserId);
	}

	/**
	 * Sets the commerce account user uuid of this commerce account user rel.
	 *
	 * @param commerceAccountUserUuid the commerce account user uuid of this commerce account user rel
	 */
	@Override
	public void setCommerceAccountUserUuid(String commerceAccountUserUuid) {
		_commerceAccountUserRel.setCommerceAccountUserUuid(
			commerceAccountUserUuid);
	}

	/**
	 * Sets the company ID of this commerce account user rel.
	 *
	 * @param companyId the company ID of this commerce account user rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceAccountUserRel.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce account user rel.
	 *
	 * @param createDate the create date of this commerce account user rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceAccountUserRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceAccountUserRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceAccountUserRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceAccountUserRel.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the modified date of this commerce account user rel.
	 *
	 * @param modifiedDate the modified date of this commerce account user rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceAccountUserRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceAccountUserRel.setNew(n);
	}

	/**
	 * Sets the primary key of this commerce account user rel.
	 *
	 * @param primaryKey the primary key of this commerce account user rel
	 */
	@Override
	public void setPrimaryKey(
		com.liferay.commerce.account.service.persistence.
			CommerceAccountUserRelPK primaryKey) {

		_commerceAccountUserRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceAccountUserRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the user ID of this commerce account user rel.
	 *
	 * @param userId the user ID of this commerce account user rel
	 */
	@Override
	public void setUserId(long userId) {
		_commerceAccountUserRel.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce account user rel.
	 *
	 * @param userName the user name of this commerce account user rel
	 */
	@Override
	public void setUserName(String userName) {
		_commerceAccountUserRel.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce account user rel.
	 *
	 * @param userUuid the user uuid of this commerce account user rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceAccountUserRel.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceAccountUserRel>
		toCacheModel() {

		return _commerceAccountUserRel.toCacheModel();
	}

	@Override
	public CommerceAccountUserRel toEscapedModel() {
		return new CommerceAccountUserRelWrapper(
			_commerceAccountUserRel.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceAccountUserRel.toString();
	}

	@Override
	public CommerceAccountUserRel toUnescapedModel() {
		return new CommerceAccountUserRelWrapper(
			_commerceAccountUserRel.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceAccountUserRel.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceAccountUserRelWrapper)) {
			return false;
		}

		CommerceAccountUserRelWrapper commerceAccountUserRelWrapper =
			(CommerceAccountUserRelWrapper)object;

		if (Objects.equals(
				_commerceAccountUserRel,
				commerceAccountUserRelWrapper._commerceAccountUserRel)) {

			return true;
		}

		return false;
	}

	@Override
	public CommerceAccountUserRel getWrappedModel() {
		return _commerceAccountUserRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceAccountUserRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceAccountUserRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceAccountUserRel.resetOriginalValues();
	}

	private final CommerceAccountUserRel _commerceAccountUserRel;

}