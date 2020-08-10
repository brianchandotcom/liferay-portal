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
 * This class is a wrapper for {@link CommerceDiscountCommerceAccountGroupRel}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceDiscountCommerceAccountGroupRel
 * @generated
 */
public class CommerceDiscountCommerceAccountGroupRelWrapper
	implements CommerceDiscountCommerceAccountGroupRel,
			   ModelWrapper<CommerceDiscountCommerceAccountGroupRel> {

	public CommerceDiscountCommerceAccountGroupRelWrapper(
		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel) {

		_commerceDiscountCommerceAccountGroupRel =
			commerceDiscountCommerceAccountGroupRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceDiscountCommerceAccountGroupRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceDiscountCommerceAccountGroupRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"commerceDiscountCommerceAccountGroupRelId",
			getCommerceDiscountCommerceAccountGroupRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceDiscountId", getCommerceDiscountId());
		attributes.put("commerceAccountGroupId", getCommerceAccountGroupId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceDiscountCommerceAccountGroupRelId = (Long)attributes.get(
			"commerceDiscountCommerceAccountGroupRelId");

		if (commerceDiscountCommerceAccountGroupRelId != null) {
			setCommerceDiscountCommerceAccountGroupRelId(
				commerceDiscountCommerceAccountGroupRelId);
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

		Long commerceAccountGroupId = (Long)attributes.get(
			"commerceAccountGroupId");

		if (commerceAccountGroupId != null) {
			setCommerceAccountGroupId(commerceAccountGroupId);
		}
	}

	@Override
	public Object clone() {
		return new CommerceDiscountCommerceAccountGroupRelWrapper(
			(CommerceDiscountCommerceAccountGroupRel)
				_commerceDiscountCommerceAccountGroupRel.clone());
	}

	@Override
	public int compareTo(
		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel) {

		return _commerceDiscountCommerceAccountGroupRel.compareTo(
			commerceDiscountCommerceAccountGroupRel);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroup
			getCommerceAccountGroup()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDiscountCommerceAccountGroupRel.
			getCommerceAccountGroup();
	}

	/**
	 * Returns the commerce account group ID of this commerce discount commerce account group rel.
	 *
	 * @return the commerce account group ID of this commerce discount commerce account group rel
	 */
	@Override
	public long getCommerceAccountGroupId() {
		return _commerceDiscountCommerceAccountGroupRel.
			getCommerceAccountGroupId();
	}

	@Override
	public CommerceDiscount getCommerceDiscount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDiscountCommerceAccountGroupRel.getCommerceDiscount();
	}

	/**
	 * Returns the commerce discount commerce account group rel ID of this commerce discount commerce account group rel.
	 *
	 * @return the commerce discount commerce account group rel ID of this commerce discount commerce account group rel
	 */
	@Override
	public long getCommerceDiscountCommerceAccountGroupRelId() {
		return _commerceDiscountCommerceAccountGroupRel.
			getCommerceDiscountCommerceAccountGroupRelId();
	}

	/**
	 * Returns the commerce discount ID of this commerce discount commerce account group rel.
	 *
	 * @return the commerce discount ID of this commerce discount commerce account group rel
	 */
	@Override
	public long getCommerceDiscountId() {
		return _commerceDiscountCommerceAccountGroupRel.getCommerceDiscountId();
	}

	/**
	 * Returns the company ID of this commerce discount commerce account group rel.
	 *
	 * @return the company ID of this commerce discount commerce account group rel
	 */
	@Override
	public long getCompanyId() {
		return _commerceDiscountCommerceAccountGroupRel.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce discount commerce account group rel.
	 *
	 * @return the create date of this commerce discount commerce account group rel
	 */
	@Override
	public Date getCreateDate() {
		return _commerceDiscountCommerceAccountGroupRel.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceDiscountCommerceAccountGroupRel.getExpandoBridge();
	}

	/**
	 * Returns the modified date of this commerce discount commerce account group rel.
	 *
	 * @return the modified date of this commerce discount commerce account group rel
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceDiscountCommerceAccountGroupRel.getModifiedDate();
	}

	/**
	 * Returns the primary key of this commerce discount commerce account group rel.
	 *
	 * @return the primary key of this commerce discount commerce account group rel
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceDiscountCommerceAccountGroupRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceDiscountCommerceAccountGroupRel.getPrimaryKeyObj();
	}

	/**
	 * Returns the user ID of this commerce discount commerce account group rel.
	 *
	 * @return the user ID of this commerce discount commerce account group rel
	 */
	@Override
	public long getUserId() {
		return _commerceDiscountCommerceAccountGroupRel.getUserId();
	}

	/**
	 * Returns the user name of this commerce discount commerce account group rel.
	 *
	 * @return the user name of this commerce discount commerce account group rel
	 */
	@Override
	public String getUserName() {
		return _commerceDiscountCommerceAccountGroupRel.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce discount commerce account group rel.
	 *
	 * @return the user uuid of this commerce discount commerce account group rel
	 */
	@Override
	public String getUserUuid() {
		return _commerceDiscountCommerceAccountGroupRel.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceDiscountCommerceAccountGroupRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceDiscountCommerceAccountGroupRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceDiscountCommerceAccountGroupRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceDiscountCommerceAccountGroupRel.isNew();
	}

	@Override
	public void persist() {
		_commerceDiscountCommerceAccountGroupRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceDiscountCommerceAccountGroupRel.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce account group ID of this commerce discount commerce account group rel.
	 *
	 * @param commerceAccountGroupId the commerce account group ID of this commerce discount commerce account group rel
	 */
	@Override
	public void setCommerceAccountGroupId(long commerceAccountGroupId) {
		_commerceDiscountCommerceAccountGroupRel.setCommerceAccountGroupId(
			commerceAccountGroupId);
	}

	/**
	 * Sets the commerce discount commerce account group rel ID of this commerce discount commerce account group rel.
	 *
	 * @param commerceDiscountCommerceAccountGroupRelId the commerce discount commerce account group rel ID of this commerce discount commerce account group rel
	 */
	@Override
	public void setCommerceDiscountCommerceAccountGroupRelId(
		long commerceDiscountCommerceAccountGroupRelId) {

		_commerceDiscountCommerceAccountGroupRel.
			setCommerceDiscountCommerceAccountGroupRelId(
				commerceDiscountCommerceAccountGroupRelId);
	}

	/**
	 * Sets the commerce discount ID of this commerce discount commerce account group rel.
	 *
	 * @param commerceDiscountId the commerce discount ID of this commerce discount commerce account group rel
	 */
	@Override
	public void setCommerceDiscountId(long commerceDiscountId) {
		_commerceDiscountCommerceAccountGroupRel.setCommerceDiscountId(
			commerceDiscountId);
	}

	/**
	 * Sets the company ID of this commerce discount commerce account group rel.
	 *
	 * @param companyId the company ID of this commerce discount commerce account group rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceDiscountCommerceAccountGroupRel.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce discount commerce account group rel.
	 *
	 * @param createDate the create date of this commerce discount commerce account group rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceDiscountCommerceAccountGroupRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceDiscountCommerceAccountGroupRel.setExpandoBridgeAttributes(
			baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceDiscountCommerceAccountGroupRel.setExpandoBridgeAttributes(
			expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceDiscountCommerceAccountGroupRel.setExpandoBridgeAttributes(
			serviceContext);
	}

	/**
	 * Sets the modified date of this commerce discount commerce account group rel.
	 *
	 * @param modifiedDate the modified date of this commerce discount commerce account group rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceDiscountCommerceAccountGroupRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceDiscountCommerceAccountGroupRel.setNew(n);
	}

	/**
	 * Sets the primary key of this commerce discount commerce account group rel.
	 *
	 * @param primaryKey the primary key of this commerce discount commerce account group rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceDiscountCommerceAccountGroupRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceDiscountCommerceAccountGroupRel.setPrimaryKeyObj(
			primaryKeyObj);
	}

	/**
	 * Sets the user ID of this commerce discount commerce account group rel.
	 *
	 * @param userId the user ID of this commerce discount commerce account group rel
	 */
	@Override
	public void setUserId(long userId) {
		_commerceDiscountCommerceAccountGroupRel.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce discount commerce account group rel.
	 *
	 * @param userName the user name of this commerce discount commerce account group rel
	 */
	@Override
	public void setUserName(String userName) {
		_commerceDiscountCommerceAccountGroupRel.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce discount commerce account group rel.
	 *
	 * @param userUuid the user uuid of this commerce discount commerce account group rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceDiscountCommerceAccountGroupRel.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel
		<CommerceDiscountCommerceAccountGroupRel> toCacheModel() {

		return _commerceDiscountCommerceAccountGroupRel.toCacheModel();
	}

	@Override
	public CommerceDiscountCommerceAccountGroupRel toEscapedModel() {
		return new CommerceDiscountCommerceAccountGroupRelWrapper(
			_commerceDiscountCommerceAccountGroupRel.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceDiscountCommerceAccountGroupRel.toString();
	}

	@Override
	public CommerceDiscountCommerceAccountGroupRel toUnescapedModel() {
		return new CommerceDiscountCommerceAccountGroupRelWrapper(
			_commerceDiscountCommerceAccountGroupRel.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceDiscountCommerceAccountGroupRel.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof
				CommerceDiscountCommerceAccountGroupRelWrapper)) {

			return false;
		}

		CommerceDiscountCommerceAccountGroupRelWrapper
			commerceDiscountCommerceAccountGroupRelWrapper =
				(CommerceDiscountCommerceAccountGroupRelWrapper)object;

		if (Objects.equals(
				_commerceDiscountCommerceAccountGroupRel,
				commerceDiscountCommerceAccountGroupRelWrapper.
					_commerceDiscountCommerceAccountGroupRel)) {

			return true;
		}

		return false;
	}

	@Override
	public CommerceDiscountCommerceAccountGroupRel getWrappedModel() {
		return _commerceDiscountCommerceAccountGroupRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceDiscountCommerceAccountGroupRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceDiscountCommerceAccountGroupRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceDiscountCommerceAccountGroupRel.resetOriginalValues();
	}

	private final CommerceDiscountCommerceAccountGroupRel
		_commerceDiscountCommerceAccountGroupRel;

}