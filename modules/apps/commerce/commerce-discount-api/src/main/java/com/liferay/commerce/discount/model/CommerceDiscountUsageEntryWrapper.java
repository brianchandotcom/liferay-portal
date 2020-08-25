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
 * This class is a wrapper for {@link CommerceDiscountUsageEntry}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceDiscountUsageEntry
 * @generated
 */
public class CommerceDiscountUsageEntryWrapper
	implements CommerceDiscountUsageEntry,
			   ModelWrapper<CommerceDiscountUsageEntry> {

	public CommerceDiscountUsageEntryWrapper(
		CommerceDiscountUsageEntry commerceDiscountUsageEntry) {

		_commerceDiscountUsageEntry = commerceDiscountUsageEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceDiscountUsageEntry.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceDiscountUsageEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"commerceDiscountUsageEntryId", getCommerceDiscountUsageEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceAccountId", getCommerceAccountId());
		attributes.put("commerceOrderId", getCommerceOrderId());
		attributes.put("commerceDiscountId", getCommerceDiscountId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceDiscountUsageEntryId = (Long)attributes.get(
			"commerceDiscountUsageEntryId");

		if (commerceDiscountUsageEntryId != null) {
			setCommerceDiscountUsageEntryId(commerceDiscountUsageEntryId);
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

		Long commerceAccountId = (Long)attributes.get("commerceAccountId");

		if (commerceAccountId != null) {
			setCommerceAccountId(commerceAccountId);
		}

		Long commerceOrderId = (Long)attributes.get("commerceOrderId");

		if (commerceOrderId != null) {
			setCommerceOrderId(commerceOrderId);
		}

		Long commerceDiscountId = (Long)attributes.get("commerceDiscountId");

		if (commerceDiscountId != null) {
			setCommerceDiscountId(commerceDiscountId);
		}
	}

	@Override
	public Object clone() {
		return new CommerceDiscountUsageEntryWrapper(
			(CommerceDiscountUsageEntry)_commerceDiscountUsageEntry.clone());
	}

	@Override
	public int compareTo(
		CommerceDiscountUsageEntry commerceDiscountUsageEntry) {

		return _commerceDiscountUsageEntry.compareTo(
			commerceDiscountUsageEntry);
	}

	/**
	 * Returns the commerce account ID of this commerce discount usage entry.
	 *
	 * @return the commerce account ID of this commerce discount usage entry
	 */
	@Override
	public long getCommerceAccountId() {
		return _commerceDiscountUsageEntry.getCommerceAccountId();
	}

	/**
	 * Returns the commerce discount ID of this commerce discount usage entry.
	 *
	 * @return the commerce discount ID of this commerce discount usage entry
	 */
	@Override
	public long getCommerceDiscountId() {
		return _commerceDiscountUsageEntry.getCommerceDiscountId();
	}

	/**
	 * Returns the commerce discount usage entry ID of this commerce discount usage entry.
	 *
	 * @return the commerce discount usage entry ID of this commerce discount usage entry
	 */
	@Override
	public long getCommerceDiscountUsageEntryId() {
		return _commerceDiscountUsageEntry.getCommerceDiscountUsageEntryId();
	}

	/**
	 * Returns the commerce order ID of this commerce discount usage entry.
	 *
	 * @return the commerce order ID of this commerce discount usage entry
	 */
	@Override
	public long getCommerceOrderId() {
		return _commerceDiscountUsageEntry.getCommerceOrderId();
	}

	/**
	 * Returns the company ID of this commerce discount usage entry.
	 *
	 * @return the company ID of this commerce discount usage entry
	 */
	@Override
	public long getCompanyId() {
		return _commerceDiscountUsageEntry.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce discount usage entry.
	 *
	 * @return the create date of this commerce discount usage entry
	 */
	@Override
	public Date getCreateDate() {
		return _commerceDiscountUsageEntry.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceDiscountUsageEntry.getExpandoBridge();
	}

	/**
	 * Returns the modified date of this commerce discount usage entry.
	 *
	 * @return the modified date of this commerce discount usage entry
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceDiscountUsageEntry.getModifiedDate();
	}

	/**
	 * Returns the primary key of this commerce discount usage entry.
	 *
	 * @return the primary key of this commerce discount usage entry
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceDiscountUsageEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceDiscountUsageEntry.getPrimaryKeyObj();
	}

	/**
	 * Returns the user ID of this commerce discount usage entry.
	 *
	 * @return the user ID of this commerce discount usage entry
	 */
	@Override
	public long getUserId() {
		return _commerceDiscountUsageEntry.getUserId();
	}

	/**
	 * Returns the user name of this commerce discount usage entry.
	 *
	 * @return the user name of this commerce discount usage entry
	 */
	@Override
	public String getUserName() {
		return _commerceDiscountUsageEntry.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce discount usage entry.
	 *
	 * @return the user uuid of this commerce discount usage entry
	 */
	@Override
	public String getUserUuid() {
		return _commerceDiscountUsageEntry.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceDiscountUsageEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceDiscountUsageEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceDiscountUsageEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceDiscountUsageEntry.isNew();
	}

	@Override
	public void persist() {
		_commerceDiscountUsageEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceDiscountUsageEntry.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce account ID of this commerce discount usage entry.
	 *
	 * @param commerceAccountId the commerce account ID of this commerce discount usage entry
	 */
	@Override
	public void setCommerceAccountId(long commerceAccountId) {
		_commerceDiscountUsageEntry.setCommerceAccountId(commerceAccountId);
	}

	/**
	 * Sets the commerce discount ID of this commerce discount usage entry.
	 *
	 * @param commerceDiscountId the commerce discount ID of this commerce discount usage entry
	 */
	@Override
	public void setCommerceDiscountId(long commerceDiscountId) {
		_commerceDiscountUsageEntry.setCommerceDiscountId(commerceDiscountId);
	}

	/**
	 * Sets the commerce discount usage entry ID of this commerce discount usage entry.
	 *
	 * @param commerceDiscountUsageEntryId the commerce discount usage entry ID of this commerce discount usage entry
	 */
	@Override
	public void setCommerceDiscountUsageEntryId(
		long commerceDiscountUsageEntryId) {

		_commerceDiscountUsageEntry.setCommerceDiscountUsageEntryId(
			commerceDiscountUsageEntryId);
	}

	/**
	 * Sets the commerce order ID of this commerce discount usage entry.
	 *
	 * @param commerceOrderId the commerce order ID of this commerce discount usage entry
	 */
	@Override
	public void setCommerceOrderId(long commerceOrderId) {
		_commerceDiscountUsageEntry.setCommerceOrderId(commerceOrderId);
	}

	/**
	 * Sets the company ID of this commerce discount usage entry.
	 *
	 * @param companyId the company ID of this commerce discount usage entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceDiscountUsageEntry.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce discount usage entry.
	 *
	 * @param createDate the create date of this commerce discount usage entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceDiscountUsageEntry.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceDiscountUsageEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceDiscountUsageEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceDiscountUsageEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the modified date of this commerce discount usage entry.
	 *
	 * @param modifiedDate the modified date of this commerce discount usage entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceDiscountUsageEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceDiscountUsageEntry.setNew(n);
	}

	/**
	 * Sets the primary key of this commerce discount usage entry.
	 *
	 * @param primaryKey the primary key of this commerce discount usage entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceDiscountUsageEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceDiscountUsageEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the user ID of this commerce discount usage entry.
	 *
	 * @param userId the user ID of this commerce discount usage entry
	 */
	@Override
	public void setUserId(long userId) {
		_commerceDiscountUsageEntry.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce discount usage entry.
	 *
	 * @param userName the user name of this commerce discount usage entry
	 */
	@Override
	public void setUserName(String userName) {
		_commerceDiscountUsageEntry.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce discount usage entry.
	 *
	 * @param userUuid the user uuid of this commerce discount usage entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceDiscountUsageEntry.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel
		<CommerceDiscountUsageEntry> toCacheModel() {

		return _commerceDiscountUsageEntry.toCacheModel();
	}

	@Override
	public CommerceDiscountUsageEntry toEscapedModel() {
		return new CommerceDiscountUsageEntryWrapper(
			_commerceDiscountUsageEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceDiscountUsageEntry.toString();
	}

	@Override
	public CommerceDiscountUsageEntry toUnescapedModel() {
		return new CommerceDiscountUsageEntryWrapper(
			_commerceDiscountUsageEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceDiscountUsageEntry.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceDiscountUsageEntryWrapper)) {
			return false;
		}

		CommerceDiscountUsageEntryWrapper commerceDiscountUsageEntryWrapper =
			(CommerceDiscountUsageEntryWrapper)object;

		if (Objects.equals(
				_commerceDiscountUsageEntry,
				commerceDiscountUsageEntryWrapper.
					_commerceDiscountUsageEntry)) {

			return true;
		}

		return false;
	}

	@Override
	public CommerceDiscountUsageEntry getWrappedModel() {
		return _commerceDiscountUsageEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceDiscountUsageEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceDiscountUsageEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceDiscountUsageEntry.resetOriginalValues();
	}

	private final CommerceDiscountUsageEntry _commerceDiscountUsageEntry;

}