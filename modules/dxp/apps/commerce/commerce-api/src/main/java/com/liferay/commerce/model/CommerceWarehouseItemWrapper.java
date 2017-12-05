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

package com.liferay.commerce.model;

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
 * This class is a wrapper for {@link CommerceWarehouseItem}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceWarehouseItem
 * @generated
 */
@ProviderType
public class CommerceWarehouseItemWrapper implements CommerceWarehouseItem,
	ModelWrapper<CommerceWarehouseItem> {
	public CommerceWarehouseItemWrapper(
		CommerceWarehouseItem commerceWarehouseItem) {
		_commerceWarehouseItem = commerceWarehouseItem;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceWarehouseItem.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceWarehouseItem.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceWarehouseItemId", getCommerceWarehouseItemId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceWarehouseId", getCommerceWarehouseId());
		attributes.put("CPInstanceId", getCPInstanceId());
		attributes.put("quantity", getQuantity());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceWarehouseItemId = (Long)attributes.get(
				"commerceWarehouseItemId");

		if (commerceWarehouseItemId != null) {
			setCommerceWarehouseItemId(commerceWarehouseItemId);
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

		Long commerceWarehouseId = (Long)attributes.get("commerceWarehouseId");

		if (commerceWarehouseId != null) {
			setCommerceWarehouseId(commerceWarehouseId);
		}

		Long CPInstanceId = (Long)attributes.get("CPInstanceId");

		if (CPInstanceId != null) {
			setCPInstanceId(CPInstanceId);
		}

		Integer quantity = (Integer)attributes.get("quantity");

		if (quantity != null) {
			setQuantity(quantity);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new CommerceWarehouseItemWrapper((CommerceWarehouseItem)_commerceWarehouseItem.clone());
	}

	@Override
	public int compareTo(CommerceWarehouseItem commerceWarehouseItem) {
		return _commerceWarehouseItem.compareTo(commerceWarehouseItem);
	}

	@Override
	public CommerceWarehouse getCommerceWarehouse()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWarehouseItem.getCommerceWarehouse();
	}

	/**
	* Returns the commerce warehouse ID of this commerce warehouse item.
	*
	* @return the commerce warehouse ID of this commerce warehouse item
	*/
	@Override
	public long getCommerceWarehouseId() {
		return _commerceWarehouseItem.getCommerceWarehouseId();
	}

	/**
	* Returns the commerce warehouse item ID of this commerce warehouse item.
	*
	* @return the commerce warehouse item ID of this commerce warehouse item
	*/
	@Override
	public long getCommerceWarehouseItemId() {
		return _commerceWarehouseItem.getCommerceWarehouseItemId();
	}

	/**
	* Returns the company ID of this commerce warehouse item.
	*
	* @return the company ID of this commerce warehouse item
	*/
	@Override
	public long getCompanyId() {
		return _commerceWarehouseItem.getCompanyId();
	}

	@Override
	public com.liferay.commerce.product.model.CPInstance getCPInstance()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWarehouseItem.getCPInstance();
	}

	/**
	* Returns the cp instance ID of this commerce warehouse item.
	*
	* @return the cp instance ID of this commerce warehouse item
	*/
	@Override
	public long getCPInstanceId() {
		return _commerceWarehouseItem.getCPInstanceId();
	}

	/**
	* Returns the create date of this commerce warehouse item.
	*
	* @return the create date of this commerce warehouse item
	*/
	@Override
	public Date getCreateDate() {
		return _commerceWarehouseItem.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceWarehouseItem.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce warehouse item.
	*
	* @return the group ID of this commerce warehouse item
	*/
	@Override
	public long getGroupId() {
		return _commerceWarehouseItem.getGroupId();
	}

	/**
	* Returns the modified date of this commerce warehouse item.
	*
	* @return the modified date of this commerce warehouse item
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceWarehouseItem.getModifiedDate();
	}

	/**
	* Returns the primary key of this commerce warehouse item.
	*
	* @return the primary key of this commerce warehouse item
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceWarehouseItem.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceWarehouseItem.getPrimaryKeyObj();
	}

	/**
	* Returns the quantity of this commerce warehouse item.
	*
	* @return the quantity of this commerce warehouse item
	*/
	@Override
	public int getQuantity() {
		return _commerceWarehouseItem.getQuantity();
	}

	/**
	* Returns the user ID of this commerce warehouse item.
	*
	* @return the user ID of this commerce warehouse item
	*/
	@Override
	public long getUserId() {
		return _commerceWarehouseItem.getUserId();
	}

	/**
	* Returns the user name of this commerce warehouse item.
	*
	* @return the user name of this commerce warehouse item
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceWarehouseItem.getUserName();
	}

	/**
	* Returns the user uuid of this commerce warehouse item.
	*
	* @return the user uuid of this commerce warehouse item
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceWarehouseItem.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceWarehouseItem.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceWarehouseItem.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceWarehouseItem.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceWarehouseItem.isNew();
	}

	@Override
	public void persist() {
		_commerceWarehouseItem.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceWarehouseItem.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce warehouse ID of this commerce warehouse item.
	*
	* @param commerceWarehouseId the commerce warehouse ID of this commerce warehouse item
	*/
	@Override
	public void setCommerceWarehouseId(long commerceWarehouseId) {
		_commerceWarehouseItem.setCommerceWarehouseId(commerceWarehouseId);
	}

	/**
	* Sets the commerce warehouse item ID of this commerce warehouse item.
	*
	* @param commerceWarehouseItemId the commerce warehouse item ID of this commerce warehouse item
	*/
	@Override
	public void setCommerceWarehouseItemId(long commerceWarehouseItemId) {
		_commerceWarehouseItem.setCommerceWarehouseItemId(commerceWarehouseItemId);
	}

	/**
	* Sets the company ID of this commerce warehouse item.
	*
	* @param companyId the company ID of this commerce warehouse item
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceWarehouseItem.setCompanyId(companyId);
	}

	/**
	* Sets the cp instance ID of this commerce warehouse item.
	*
	* @param CPInstanceId the cp instance ID of this commerce warehouse item
	*/
	@Override
	public void setCPInstanceId(long CPInstanceId) {
		_commerceWarehouseItem.setCPInstanceId(CPInstanceId);
	}

	/**
	* Sets the create date of this commerce warehouse item.
	*
	* @param createDate the create date of this commerce warehouse item
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceWarehouseItem.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceWarehouseItem.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceWarehouseItem.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceWarehouseItem.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce warehouse item.
	*
	* @param groupId the group ID of this commerce warehouse item
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceWarehouseItem.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce warehouse item.
	*
	* @param modifiedDate the modified date of this commerce warehouse item
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceWarehouseItem.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceWarehouseItem.setNew(n);
	}

	/**
	* Sets the primary key of this commerce warehouse item.
	*
	* @param primaryKey the primary key of this commerce warehouse item
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceWarehouseItem.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceWarehouseItem.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the quantity of this commerce warehouse item.
	*
	* @param quantity the quantity of this commerce warehouse item
	*/
	@Override
	public void setQuantity(int quantity) {
		_commerceWarehouseItem.setQuantity(quantity);
	}

	/**
	* Sets the user ID of this commerce warehouse item.
	*
	* @param userId the user ID of this commerce warehouse item
	*/
	@Override
	public void setUserId(long userId) {
		_commerceWarehouseItem.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce warehouse item.
	*
	* @param userName the user name of this commerce warehouse item
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceWarehouseItem.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce warehouse item.
	*
	* @param userUuid the user uuid of this commerce warehouse item
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceWarehouseItem.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceWarehouseItem> toCacheModel() {
		return _commerceWarehouseItem.toCacheModel();
	}

	@Override
	public CommerceWarehouseItem toEscapedModel() {
		return new CommerceWarehouseItemWrapper(_commerceWarehouseItem.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _commerceWarehouseItem.toString();
	}

	@Override
	public CommerceWarehouseItem toUnescapedModel() {
		return new CommerceWarehouseItemWrapper(_commerceWarehouseItem.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceWarehouseItem.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceWarehouseItemWrapper)) {
			return false;
		}

		CommerceWarehouseItemWrapper commerceWarehouseItemWrapper = (CommerceWarehouseItemWrapper)obj;

		if (Objects.equals(_commerceWarehouseItem,
					commerceWarehouseItemWrapper._commerceWarehouseItem)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceWarehouseItem getWrappedModel() {
		return _commerceWarehouseItem;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceWarehouseItem.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceWarehouseItem.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceWarehouseItem.resetOriginalValues();
	}

	private final CommerceWarehouseItem _commerceWarehouseItem;
}