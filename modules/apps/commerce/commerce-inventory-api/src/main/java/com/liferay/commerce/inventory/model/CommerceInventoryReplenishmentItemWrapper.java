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
 * This class is a wrapper for {@link CommerceInventoryReplenishmentItem}.
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryReplenishmentItem
 * @generated
 */
public class CommerceInventoryReplenishmentItemWrapper
	implements CommerceInventoryReplenishmentItem,
			   ModelWrapper<CommerceInventoryReplenishmentItem> {

	public CommerceInventoryReplenishmentItemWrapper(
		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem) {

		_commerceInventoryReplenishmentItem =
			commerceInventoryReplenishmentItem;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceInventoryReplenishmentItem.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceInventoryReplenishmentItem.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"commerceInventoryReplenishmentItemId",
			getCommerceInventoryReplenishmentItemId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put(
			"commerceInventoryWarehouseId", getCommerceInventoryWarehouseId());
		attributes.put("sku", getSku());
		attributes.put("availabilityDate", getAvailabilityDate());
		attributes.put("quantity", getQuantity());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long commerceInventoryReplenishmentItemId = (Long)attributes.get(
			"commerceInventoryReplenishmentItemId");

		if (commerceInventoryReplenishmentItemId != null) {
			setCommerceInventoryReplenishmentItemId(
				commerceInventoryReplenishmentItemId);
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

		Long commerceInventoryWarehouseId = (Long)attributes.get(
			"commerceInventoryWarehouseId");

		if (commerceInventoryWarehouseId != null) {
			setCommerceInventoryWarehouseId(commerceInventoryWarehouseId);
		}

		String sku = (String)attributes.get("sku");

		if (sku != null) {
			setSku(sku);
		}

		Date availabilityDate = (Date)attributes.get("availabilityDate");

		if (availabilityDate != null) {
			setAvailabilityDate(availabilityDate);
		}

		Integer quantity = (Integer)attributes.get("quantity");

		if (quantity != null) {
			setQuantity(quantity);
		}
	}

	@Override
	public Object clone() {
		return new CommerceInventoryReplenishmentItemWrapper(
			(CommerceInventoryReplenishmentItem)
				_commerceInventoryReplenishmentItem.clone());
	}

	@Override
	public int compareTo(
		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem) {

		return _commerceInventoryReplenishmentItem.compareTo(
			commerceInventoryReplenishmentItem);
	}

	/**
	 * Returns the availability date of this commerce inventory replenishment item.
	 *
	 * @return the availability date of this commerce inventory replenishment item
	 */
	@Override
	public Date getAvailabilityDate() {
		return _commerceInventoryReplenishmentItem.getAvailabilityDate();
	}

	/**
	 * Returns the commerce inventory replenishment item ID of this commerce inventory replenishment item.
	 *
	 * @return the commerce inventory replenishment item ID of this commerce inventory replenishment item
	 */
	@Override
	public long getCommerceInventoryReplenishmentItemId() {
		return _commerceInventoryReplenishmentItem.
			getCommerceInventoryReplenishmentItemId();
	}

	@Override
	public CommerceInventoryWarehouse getCommerceInventoryWarehouse()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryReplenishmentItem.
			getCommerceInventoryWarehouse();
	}

	/**
	 * Returns the commerce inventory warehouse ID of this commerce inventory replenishment item.
	 *
	 * @return the commerce inventory warehouse ID of this commerce inventory replenishment item
	 */
	@Override
	public long getCommerceInventoryWarehouseId() {
		return _commerceInventoryReplenishmentItem.
			getCommerceInventoryWarehouseId();
	}

	/**
	 * Returns the company ID of this commerce inventory replenishment item.
	 *
	 * @return the company ID of this commerce inventory replenishment item
	 */
	@Override
	public long getCompanyId() {
		return _commerceInventoryReplenishmentItem.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce inventory replenishment item.
	 *
	 * @return the create date of this commerce inventory replenishment item
	 */
	@Override
	public Date getCreateDate() {
		return _commerceInventoryReplenishmentItem.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceInventoryReplenishmentItem.getExpandoBridge();
	}

	/**
	 * Returns the modified date of this commerce inventory replenishment item.
	 *
	 * @return the modified date of this commerce inventory replenishment item
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceInventoryReplenishmentItem.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce inventory replenishment item.
	 *
	 * @return the mvcc version of this commerce inventory replenishment item
	 */
	@Override
	public long getMvccVersion() {
		return _commerceInventoryReplenishmentItem.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce inventory replenishment item.
	 *
	 * @return the primary key of this commerce inventory replenishment item
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceInventoryReplenishmentItem.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceInventoryReplenishmentItem.getPrimaryKeyObj();
	}

	/**
	 * Returns the quantity of this commerce inventory replenishment item.
	 *
	 * @return the quantity of this commerce inventory replenishment item
	 */
	@Override
	public int getQuantity() {
		return _commerceInventoryReplenishmentItem.getQuantity();
	}

	/**
	 * Returns the sku of this commerce inventory replenishment item.
	 *
	 * @return the sku of this commerce inventory replenishment item
	 */
	@Override
	public String getSku() {
		return _commerceInventoryReplenishmentItem.getSku();
	}

	/**
	 * Returns the user ID of this commerce inventory replenishment item.
	 *
	 * @return the user ID of this commerce inventory replenishment item
	 */
	@Override
	public long getUserId() {
		return _commerceInventoryReplenishmentItem.getUserId();
	}

	/**
	 * Returns the user name of this commerce inventory replenishment item.
	 *
	 * @return the user name of this commerce inventory replenishment item
	 */
	@Override
	public String getUserName() {
		return _commerceInventoryReplenishmentItem.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce inventory replenishment item.
	 *
	 * @return the user uuid of this commerce inventory replenishment item
	 */
	@Override
	public String getUserUuid() {
		return _commerceInventoryReplenishmentItem.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceInventoryReplenishmentItem.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceInventoryReplenishmentItem.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceInventoryReplenishmentItem.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceInventoryReplenishmentItem.isNew();
	}

	@Override
	public void persist() {
		_commerceInventoryReplenishmentItem.persist();
	}

	/**
	 * Sets the availability date of this commerce inventory replenishment item.
	 *
	 * @param availabilityDate the availability date of this commerce inventory replenishment item
	 */
	@Override
	public void setAvailabilityDate(Date availabilityDate) {
		_commerceInventoryReplenishmentItem.setAvailabilityDate(
			availabilityDate);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceInventoryReplenishmentItem.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce inventory replenishment item ID of this commerce inventory replenishment item.
	 *
	 * @param commerceInventoryReplenishmentItemId the commerce inventory replenishment item ID of this commerce inventory replenishment item
	 */
	@Override
	public void setCommerceInventoryReplenishmentItemId(
		long commerceInventoryReplenishmentItemId) {

		_commerceInventoryReplenishmentItem.
			setCommerceInventoryReplenishmentItemId(
				commerceInventoryReplenishmentItemId);
	}

	/**
	 * Sets the commerce inventory warehouse ID of this commerce inventory replenishment item.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID of this commerce inventory replenishment item
	 */
	@Override
	public void setCommerceInventoryWarehouseId(
		long commerceInventoryWarehouseId) {

		_commerceInventoryReplenishmentItem.setCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId);
	}

	/**
	 * Sets the company ID of this commerce inventory replenishment item.
	 *
	 * @param companyId the company ID of this commerce inventory replenishment item
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceInventoryReplenishmentItem.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce inventory replenishment item.
	 *
	 * @param createDate the create date of this commerce inventory replenishment item
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceInventoryReplenishmentItem.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceInventoryReplenishmentItem.setExpandoBridgeAttributes(
			baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceInventoryReplenishmentItem.setExpandoBridgeAttributes(
			expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceInventoryReplenishmentItem.setExpandoBridgeAttributes(
			serviceContext);
	}

	/**
	 * Sets the modified date of this commerce inventory replenishment item.
	 *
	 * @param modifiedDate the modified date of this commerce inventory replenishment item
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceInventoryReplenishmentItem.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce inventory replenishment item.
	 *
	 * @param mvccVersion the mvcc version of this commerce inventory replenishment item
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		_commerceInventoryReplenishmentItem.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_commerceInventoryReplenishmentItem.setNew(n);
	}

	/**
	 * Sets the primary key of this commerce inventory replenishment item.
	 *
	 * @param primaryKey the primary key of this commerce inventory replenishment item
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceInventoryReplenishmentItem.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceInventoryReplenishmentItem.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the quantity of this commerce inventory replenishment item.
	 *
	 * @param quantity the quantity of this commerce inventory replenishment item
	 */
	@Override
	public void setQuantity(int quantity) {
		_commerceInventoryReplenishmentItem.setQuantity(quantity);
	}

	/**
	 * Sets the sku of this commerce inventory replenishment item.
	 *
	 * @param sku the sku of this commerce inventory replenishment item
	 */
	@Override
	public void setSku(String sku) {
		_commerceInventoryReplenishmentItem.setSku(sku);
	}

	/**
	 * Sets the user ID of this commerce inventory replenishment item.
	 *
	 * @param userId the user ID of this commerce inventory replenishment item
	 */
	@Override
	public void setUserId(long userId) {
		_commerceInventoryReplenishmentItem.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce inventory replenishment item.
	 *
	 * @param userName the user name of this commerce inventory replenishment item
	 */
	@Override
	public void setUserName(String userName) {
		_commerceInventoryReplenishmentItem.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce inventory replenishment item.
	 *
	 * @param userUuid the user uuid of this commerce inventory replenishment item
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceInventoryReplenishmentItem.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel
		<CommerceInventoryReplenishmentItem> toCacheModel() {

		return _commerceInventoryReplenishmentItem.toCacheModel();
	}

	@Override
	public CommerceInventoryReplenishmentItem toEscapedModel() {
		return new CommerceInventoryReplenishmentItemWrapper(
			_commerceInventoryReplenishmentItem.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceInventoryReplenishmentItem.toString();
	}

	@Override
	public CommerceInventoryReplenishmentItem toUnescapedModel() {
		return new CommerceInventoryReplenishmentItemWrapper(
			_commerceInventoryReplenishmentItem.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceInventoryReplenishmentItem.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceInventoryReplenishmentItemWrapper)) {
			return false;
		}

		CommerceInventoryReplenishmentItemWrapper
			commerceInventoryReplenishmentItemWrapper =
				(CommerceInventoryReplenishmentItemWrapper)object;

		if (Objects.equals(
				_commerceInventoryReplenishmentItem,
				commerceInventoryReplenishmentItemWrapper.
					_commerceInventoryReplenishmentItem)) {

			return true;
		}

		return false;
	}

	@Override
	public CommerceInventoryReplenishmentItem getWrappedModel() {
		return _commerceInventoryReplenishmentItem;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceInventoryReplenishmentItem.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceInventoryReplenishmentItem.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceInventoryReplenishmentItem.resetOriginalValues();
	}

	private final CommerceInventoryReplenishmentItem
		_commerceInventoryReplenishmentItem;

}