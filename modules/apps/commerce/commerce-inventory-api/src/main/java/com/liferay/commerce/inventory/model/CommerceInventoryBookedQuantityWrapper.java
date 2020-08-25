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

package com.liferay.commerce.inventory.model;

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
 * This class is a wrapper for {@link CommerceInventoryBookedQuantity}.
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryBookedQuantity
 * @generated
 */
public class CommerceInventoryBookedQuantityWrapper
	implements CommerceInventoryBookedQuantity,
			   ModelWrapper<CommerceInventoryBookedQuantity> {

	public CommerceInventoryBookedQuantityWrapper(
		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity) {

		_commerceInventoryBookedQuantity = commerceInventoryBookedQuantity;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceInventoryBookedQuantity.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceInventoryBookedQuantity.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"commerceInventoryBookedQuantityId",
			getCommerceInventoryBookedQuantityId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("sku", getSku());
		attributes.put("quantity", getQuantity());
		attributes.put("expirationDate", getExpirationDate());
		attributes.put("bookedNote", getBookedNote());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long commerceInventoryBookedQuantityId = (Long)attributes.get(
			"commerceInventoryBookedQuantityId");

		if (commerceInventoryBookedQuantityId != null) {
			setCommerceInventoryBookedQuantityId(
				commerceInventoryBookedQuantityId);
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

		String sku = (String)attributes.get("sku");

		if (sku != null) {
			setSku(sku);
		}

		Integer quantity = (Integer)attributes.get("quantity");

		if (quantity != null) {
			setQuantity(quantity);
		}

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}

		String bookedNote = (String)attributes.get("bookedNote");

		if (bookedNote != null) {
			setBookedNote(bookedNote);
		}
	}

	@Override
	public Object clone() {
		return new CommerceInventoryBookedQuantityWrapper(
			(CommerceInventoryBookedQuantity)
				_commerceInventoryBookedQuantity.clone());
	}

	@Override
	public int compareTo(
		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity) {

		return _commerceInventoryBookedQuantity.compareTo(
			commerceInventoryBookedQuantity);
	}

	/**
	 * Returns the booked note of this commerce inventory booked quantity.
	 *
	 * @return the booked note of this commerce inventory booked quantity
	 */
	@Override
	public String getBookedNote() {
		return _commerceInventoryBookedQuantity.getBookedNote();
	}

	/**
	 * Returns the commerce inventory booked quantity ID of this commerce inventory booked quantity.
	 *
	 * @return the commerce inventory booked quantity ID of this commerce inventory booked quantity
	 */
	@Override
	public long getCommerceInventoryBookedQuantityId() {
		return _commerceInventoryBookedQuantity.
			getCommerceInventoryBookedQuantityId();
	}

	/**
	 * Returns the company ID of this commerce inventory booked quantity.
	 *
	 * @return the company ID of this commerce inventory booked quantity
	 */
	@Override
	public long getCompanyId() {
		return _commerceInventoryBookedQuantity.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce inventory booked quantity.
	 *
	 * @return the create date of this commerce inventory booked quantity
	 */
	@Override
	public Date getCreateDate() {
		return _commerceInventoryBookedQuantity.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceInventoryBookedQuantity.getExpandoBridge();
	}

	/**
	 * Returns the expiration date of this commerce inventory booked quantity.
	 *
	 * @return the expiration date of this commerce inventory booked quantity
	 */
	@Override
	public Date getExpirationDate() {
		return _commerceInventoryBookedQuantity.getExpirationDate();
	}

	/**
	 * Returns the modified date of this commerce inventory booked quantity.
	 *
	 * @return the modified date of this commerce inventory booked quantity
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceInventoryBookedQuantity.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce inventory booked quantity.
	 *
	 * @return the mvcc version of this commerce inventory booked quantity
	 */
	@Override
	public long getMvccVersion() {
		return _commerceInventoryBookedQuantity.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce inventory booked quantity.
	 *
	 * @return the primary key of this commerce inventory booked quantity
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceInventoryBookedQuantity.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceInventoryBookedQuantity.getPrimaryKeyObj();
	}

	/**
	 * Returns the quantity of this commerce inventory booked quantity.
	 *
	 * @return the quantity of this commerce inventory booked quantity
	 */
	@Override
	public int getQuantity() {
		return _commerceInventoryBookedQuantity.getQuantity();
	}

	/**
	 * Returns the sku of this commerce inventory booked quantity.
	 *
	 * @return the sku of this commerce inventory booked quantity
	 */
	@Override
	public String getSku() {
		return _commerceInventoryBookedQuantity.getSku();
	}

	/**
	 * Returns the user ID of this commerce inventory booked quantity.
	 *
	 * @return the user ID of this commerce inventory booked quantity
	 */
	@Override
	public long getUserId() {
		return _commerceInventoryBookedQuantity.getUserId();
	}

	/**
	 * Returns the user name of this commerce inventory booked quantity.
	 *
	 * @return the user name of this commerce inventory booked quantity
	 */
	@Override
	public String getUserName() {
		return _commerceInventoryBookedQuantity.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce inventory booked quantity.
	 *
	 * @return the user uuid of this commerce inventory booked quantity
	 */
	@Override
	public String getUserUuid() {
		return _commerceInventoryBookedQuantity.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceInventoryBookedQuantity.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceInventoryBookedQuantity.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceInventoryBookedQuantity.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceInventoryBookedQuantity.isNew();
	}

	@Override
	public void persist() {
		_commerceInventoryBookedQuantity.persist();
	}

	/**
	 * Sets the booked note of this commerce inventory booked quantity.
	 *
	 * @param bookedNote the booked note of this commerce inventory booked quantity
	 */
	@Override
	public void setBookedNote(String bookedNote) {
		_commerceInventoryBookedQuantity.setBookedNote(bookedNote);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceInventoryBookedQuantity.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce inventory booked quantity ID of this commerce inventory booked quantity.
	 *
	 * @param commerceInventoryBookedQuantityId the commerce inventory booked quantity ID of this commerce inventory booked quantity
	 */
	@Override
	public void setCommerceInventoryBookedQuantityId(
		long commerceInventoryBookedQuantityId) {

		_commerceInventoryBookedQuantity.setCommerceInventoryBookedQuantityId(
			commerceInventoryBookedQuantityId);
	}

	/**
	 * Sets the company ID of this commerce inventory booked quantity.
	 *
	 * @param companyId the company ID of this commerce inventory booked quantity
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceInventoryBookedQuantity.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce inventory booked quantity.
	 *
	 * @param createDate the create date of this commerce inventory booked quantity
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceInventoryBookedQuantity.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceInventoryBookedQuantity.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceInventoryBookedQuantity.setExpandoBridgeAttributes(
			expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceInventoryBookedQuantity.setExpandoBridgeAttributes(
			serviceContext);
	}

	/**
	 * Sets the expiration date of this commerce inventory booked quantity.
	 *
	 * @param expirationDate the expiration date of this commerce inventory booked quantity
	 */
	@Override
	public void setExpirationDate(Date expirationDate) {
		_commerceInventoryBookedQuantity.setExpirationDate(expirationDate);
	}

	/**
	 * Sets the modified date of this commerce inventory booked quantity.
	 *
	 * @param modifiedDate the modified date of this commerce inventory booked quantity
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceInventoryBookedQuantity.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce inventory booked quantity.
	 *
	 * @param mvccVersion the mvcc version of this commerce inventory booked quantity
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		_commerceInventoryBookedQuantity.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_commerceInventoryBookedQuantity.setNew(n);
	}

	/**
	 * Sets the primary key of this commerce inventory booked quantity.
	 *
	 * @param primaryKey the primary key of this commerce inventory booked quantity
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceInventoryBookedQuantity.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceInventoryBookedQuantity.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the quantity of this commerce inventory booked quantity.
	 *
	 * @param quantity the quantity of this commerce inventory booked quantity
	 */
	@Override
	public void setQuantity(int quantity) {
		_commerceInventoryBookedQuantity.setQuantity(quantity);
	}

	/**
	 * Sets the sku of this commerce inventory booked quantity.
	 *
	 * @param sku the sku of this commerce inventory booked quantity
	 */
	@Override
	public void setSku(String sku) {
		_commerceInventoryBookedQuantity.setSku(sku);
	}

	/**
	 * Sets the user ID of this commerce inventory booked quantity.
	 *
	 * @param userId the user ID of this commerce inventory booked quantity
	 */
	@Override
	public void setUserId(long userId) {
		_commerceInventoryBookedQuantity.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce inventory booked quantity.
	 *
	 * @param userName the user name of this commerce inventory booked quantity
	 */
	@Override
	public void setUserName(String userName) {
		_commerceInventoryBookedQuantity.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce inventory booked quantity.
	 *
	 * @param userUuid the user uuid of this commerce inventory booked quantity
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceInventoryBookedQuantity.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel
		<CommerceInventoryBookedQuantity> toCacheModel() {

		return _commerceInventoryBookedQuantity.toCacheModel();
	}

	@Override
	public CommerceInventoryBookedQuantity toEscapedModel() {
		return new CommerceInventoryBookedQuantityWrapper(
			_commerceInventoryBookedQuantity.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceInventoryBookedQuantity.toString();
	}

	@Override
	public CommerceInventoryBookedQuantity toUnescapedModel() {
		return new CommerceInventoryBookedQuantityWrapper(
			_commerceInventoryBookedQuantity.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceInventoryBookedQuantity.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceInventoryBookedQuantityWrapper)) {
			return false;
		}

		CommerceInventoryBookedQuantityWrapper
			commerceInventoryBookedQuantityWrapper =
				(CommerceInventoryBookedQuantityWrapper)object;

		if (Objects.equals(
				_commerceInventoryBookedQuantity,
				commerceInventoryBookedQuantityWrapper.
					_commerceInventoryBookedQuantity)) {

			return true;
		}

		return false;
	}

	@Override
	public CommerceInventoryBookedQuantity getWrappedModel() {
		return _commerceInventoryBookedQuantity;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceInventoryBookedQuantity.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceInventoryBookedQuantity.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceInventoryBookedQuantity.resetOriginalValues();
	}

	private final CommerceInventoryBookedQuantity
		_commerceInventoryBookedQuantity;

}