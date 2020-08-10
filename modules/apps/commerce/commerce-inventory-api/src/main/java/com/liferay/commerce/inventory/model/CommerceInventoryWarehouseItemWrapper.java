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
 * This class is a wrapper for {@link CommerceInventoryWarehouseItem}.
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryWarehouseItem
 * @generated
 */
public class CommerceInventoryWarehouseItemWrapper
	implements CommerceInventoryWarehouseItem,
			   ModelWrapper<CommerceInventoryWarehouseItem> {

	public CommerceInventoryWarehouseItemWrapper(
		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem) {

		_commerceInventoryWarehouseItem = commerceInventoryWarehouseItem;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceInventoryWarehouseItem.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceInventoryWarehouseItem.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put(
			"commerceInventoryWarehouseItemId",
			getCommerceInventoryWarehouseItemId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put(
			"commerceInventoryWarehouseId", getCommerceInventoryWarehouseId());
		attributes.put("sku", getSku());
		attributes.put("quantity", getQuantity());
		attributes.put("reservedQuantity", getReservedQuantity());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long commerceInventoryWarehouseItemId = (Long)attributes.get(
			"commerceInventoryWarehouseItemId");

		if (commerceInventoryWarehouseItemId != null) {
			setCommerceInventoryWarehouseItemId(
				commerceInventoryWarehouseItemId);
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

		Integer quantity = (Integer)attributes.get("quantity");

		if (quantity != null) {
			setQuantity(quantity);
		}

		Integer reservedQuantity = (Integer)attributes.get("reservedQuantity");

		if (reservedQuantity != null) {
			setReservedQuantity(reservedQuantity);
		}
	}

	@Override
	public Object clone() {
		return new CommerceInventoryWarehouseItemWrapper(
			(CommerceInventoryWarehouseItem)
				_commerceInventoryWarehouseItem.clone());
	}

	@Override
	public int compareTo(
		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem) {

		return _commerceInventoryWarehouseItem.compareTo(
			commerceInventoryWarehouseItem);
	}

	@Override
	public CommerceInventoryWarehouse getCommerceInventoryWarehouse()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseItem.getCommerceInventoryWarehouse();
	}

	/**
	 * Returns the commerce inventory warehouse ID of this commerce inventory warehouse item.
	 *
	 * @return the commerce inventory warehouse ID of this commerce inventory warehouse item
	 */
	@Override
	public long getCommerceInventoryWarehouseId() {
		return _commerceInventoryWarehouseItem.
			getCommerceInventoryWarehouseId();
	}

	/**
	 * Returns the commerce inventory warehouse item ID of this commerce inventory warehouse item.
	 *
	 * @return the commerce inventory warehouse item ID of this commerce inventory warehouse item
	 */
	@Override
	public long getCommerceInventoryWarehouseItemId() {
		return _commerceInventoryWarehouseItem.
			getCommerceInventoryWarehouseItemId();
	}

	/**
	 * Returns the company ID of this commerce inventory warehouse item.
	 *
	 * @return the company ID of this commerce inventory warehouse item
	 */
	@Override
	public long getCompanyId() {
		return _commerceInventoryWarehouseItem.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce inventory warehouse item.
	 *
	 * @return the create date of this commerce inventory warehouse item
	 */
	@Override
	public Date getCreateDate() {
		return _commerceInventoryWarehouseItem.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceInventoryWarehouseItem.getExpandoBridge();
	}

	/**
	 * Returns the external reference code of this commerce inventory warehouse item.
	 *
	 * @return the external reference code of this commerce inventory warehouse item
	 */
	@Override
	public String getExternalReferenceCode() {
		return _commerceInventoryWarehouseItem.getExternalReferenceCode();
	}

	/**
	 * Returns the modified date of this commerce inventory warehouse item.
	 *
	 * @return the modified date of this commerce inventory warehouse item
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceInventoryWarehouseItem.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce inventory warehouse item.
	 *
	 * @return the mvcc version of this commerce inventory warehouse item
	 */
	@Override
	public long getMvccVersion() {
		return _commerceInventoryWarehouseItem.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce inventory warehouse item.
	 *
	 * @return the primary key of this commerce inventory warehouse item
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceInventoryWarehouseItem.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceInventoryWarehouseItem.getPrimaryKeyObj();
	}

	/**
	 * Returns the quantity of this commerce inventory warehouse item.
	 *
	 * @return the quantity of this commerce inventory warehouse item
	 */
	@Override
	public int getQuantity() {
		return _commerceInventoryWarehouseItem.getQuantity();
	}

	/**
	 * Returns the reserved quantity of this commerce inventory warehouse item.
	 *
	 * @return the reserved quantity of this commerce inventory warehouse item
	 */
	@Override
	public int getReservedQuantity() {
		return _commerceInventoryWarehouseItem.getReservedQuantity();
	}

	/**
	 * Returns the sku of this commerce inventory warehouse item.
	 *
	 * @return the sku of this commerce inventory warehouse item
	 */
	@Override
	public String getSku() {
		return _commerceInventoryWarehouseItem.getSku();
	}

	/**
	 * Returns the user ID of this commerce inventory warehouse item.
	 *
	 * @return the user ID of this commerce inventory warehouse item
	 */
	@Override
	public long getUserId() {
		return _commerceInventoryWarehouseItem.getUserId();
	}

	/**
	 * Returns the user name of this commerce inventory warehouse item.
	 *
	 * @return the user name of this commerce inventory warehouse item
	 */
	@Override
	public String getUserName() {
		return _commerceInventoryWarehouseItem.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce inventory warehouse item.
	 *
	 * @return the user uuid of this commerce inventory warehouse item
	 */
	@Override
	public String getUserUuid() {
		return _commerceInventoryWarehouseItem.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceInventoryWarehouseItem.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceInventoryWarehouseItem.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceInventoryWarehouseItem.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceInventoryWarehouseItem.isNew();
	}

	@Override
	public void persist() {
		_commerceInventoryWarehouseItem.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceInventoryWarehouseItem.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce inventory warehouse ID of this commerce inventory warehouse item.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID of this commerce inventory warehouse item
	 */
	@Override
	public void setCommerceInventoryWarehouseId(
		long commerceInventoryWarehouseId) {

		_commerceInventoryWarehouseItem.setCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId);
	}

	/**
	 * Sets the commerce inventory warehouse item ID of this commerce inventory warehouse item.
	 *
	 * @param commerceInventoryWarehouseItemId the commerce inventory warehouse item ID of this commerce inventory warehouse item
	 */
	@Override
	public void setCommerceInventoryWarehouseItemId(
		long commerceInventoryWarehouseItemId) {

		_commerceInventoryWarehouseItem.setCommerceInventoryWarehouseItemId(
			commerceInventoryWarehouseItemId);
	}

	/**
	 * Sets the company ID of this commerce inventory warehouse item.
	 *
	 * @param companyId the company ID of this commerce inventory warehouse item
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceInventoryWarehouseItem.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce inventory warehouse item.
	 *
	 * @param createDate the create date of this commerce inventory warehouse item
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceInventoryWarehouseItem.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceInventoryWarehouseItem.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceInventoryWarehouseItem.setExpandoBridgeAttributes(
			expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceInventoryWarehouseItem.setExpandoBridgeAttributes(
			serviceContext);
	}

	/**
	 * Sets the external reference code of this commerce inventory warehouse item.
	 *
	 * @param externalReferenceCode the external reference code of this commerce inventory warehouse item
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		_commerceInventoryWarehouseItem.setExternalReferenceCode(
			externalReferenceCode);
	}

	/**
	 * Sets the modified date of this commerce inventory warehouse item.
	 *
	 * @param modifiedDate the modified date of this commerce inventory warehouse item
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceInventoryWarehouseItem.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce inventory warehouse item.
	 *
	 * @param mvccVersion the mvcc version of this commerce inventory warehouse item
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		_commerceInventoryWarehouseItem.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_commerceInventoryWarehouseItem.setNew(n);
	}

	/**
	 * Sets the primary key of this commerce inventory warehouse item.
	 *
	 * @param primaryKey the primary key of this commerce inventory warehouse item
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceInventoryWarehouseItem.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceInventoryWarehouseItem.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the quantity of this commerce inventory warehouse item.
	 *
	 * @param quantity the quantity of this commerce inventory warehouse item
	 */
	@Override
	public void setQuantity(int quantity) {
		_commerceInventoryWarehouseItem.setQuantity(quantity);
	}

	/**
	 * Sets the reserved quantity of this commerce inventory warehouse item.
	 *
	 * @param reservedQuantity the reserved quantity of this commerce inventory warehouse item
	 */
	@Override
	public void setReservedQuantity(int reservedQuantity) {
		_commerceInventoryWarehouseItem.setReservedQuantity(reservedQuantity);
	}

	/**
	 * Sets the sku of this commerce inventory warehouse item.
	 *
	 * @param sku the sku of this commerce inventory warehouse item
	 */
	@Override
	public void setSku(String sku) {
		_commerceInventoryWarehouseItem.setSku(sku);
	}

	/**
	 * Sets the user ID of this commerce inventory warehouse item.
	 *
	 * @param userId the user ID of this commerce inventory warehouse item
	 */
	@Override
	public void setUserId(long userId) {
		_commerceInventoryWarehouseItem.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce inventory warehouse item.
	 *
	 * @param userName the user name of this commerce inventory warehouse item
	 */
	@Override
	public void setUserName(String userName) {
		_commerceInventoryWarehouseItem.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce inventory warehouse item.
	 *
	 * @param userUuid the user uuid of this commerce inventory warehouse item
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceInventoryWarehouseItem.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel
		<CommerceInventoryWarehouseItem> toCacheModel() {

		return _commerceInventoryWarehouseItem.toCacheModel();
	}

	@Override
	public CommerceInventoryWarehouseItem toEscapedModel() {
		return new CommerceInventoryWarehouseItemWrapper(
			_commerceInventoryWarehouseItem.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceInventoryWarehouseItem.toString();
	}

	@Override
	public CommerceInventoryWarehouseItem toUnescapedModel() {
		return new CommerceInventoryWarehouseItemWrapper(
			_commerceInventoryWarehouseItem.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceInventoryWarehouseItem.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceInventoryWarehouseItemWrapper)) {
			return false;
		}

		CommerceInventoryWarehouseItemWrapper
			commerceInventoryWarehouseItemWrapper =
				(CommerceInventoryWarehouseItemWrapper)object;

		if (Objects.equals(
				_commerceInventoryWarehouseItem,
				commerceInventoryWarehouseItemWrapper.
					_commerceInventoryWarehouseItem)) {

			return true;
		}

		return false;
	}

	@Override
	public CommerceInventoryWarehouseItem getWrappedModel() {
		return _commerceInventoryWarehouseItem;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceInventoryWarehouseItem.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceInventoryWarehouseItem.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceInventoryWarehouseItem.resetOriginalValues();
	}

	private final CommerceInventoryWarehouseItem
		_commerceInventoryWarehouseItem;

}