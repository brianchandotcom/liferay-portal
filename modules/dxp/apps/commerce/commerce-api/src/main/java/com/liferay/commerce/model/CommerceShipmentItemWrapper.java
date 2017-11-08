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
 * This class is a wrapper for {@link CommerceShipmentItem}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentItem
 * @generated
 */
@ProviderType
public class CommerceShipmentItemWrapper implements CommerceShipmentItem,
	ModelWrapper<CommerceShipmentItem> {
	public CommerceShipmentItemWrapper(
		CommerceShipmentItem commerceShipmentItem) {
		_commerceShipmentItem = commerceShipmentItem;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceShipmentItem.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceShipmentItem.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceShipmentItemId", getCommerceShipmentItemId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceShipmentId", getCommerceShipmentId());
		attributes.put("commerceOrderItemId", getCommerceOrderItemId());
		attributes.put("quantity", getQuantity());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceShipmentItemId = (Long)attributes.get(
				"commerceShipmentItemId");

		if (commerceShipmentItemId != null) {
			setCommerceShipmentItemId(commerceShipmentItemId);
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

		Long commerceShipmentId = (Long)attributes.get("commerceShipmentId");

		if (commerceShipmentId != null) {
			setCommerceShipmentId(commerceShipmentId);
		}

		Long commerceOrderItemId = (Long)attributes.get("commerceOrderItemId");

		if (commerceOrderItemId != null) {
			setCommerceOrderItemId(commerceOrderItemId);
		}

		Integer quantity = (Integer)attributes.get("quantity");

		if (quantity != null) {
			setQuantity(quantity);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new CommerceShipmentItemWrapper((CommerceShipmentItem)_commerceShipmentItem.clone());
	}

	@Override
	public int compareTo(CommerceShipmentItem commerceShipmentItem) {
		return _commerceShipmentItem.compareTo(commerceShipmentItem);
	}

	@Override
	public CommerceOrderItem fetchCommerceOrderItem() {
		return _commerceShipmentItem.fetchCommerceOrderItem();
	}

	/**
	* Returns the commerce order item ID of this commerce shipment item.
	*
	* @return the commerce order item ID of this commerce shipment item
	*/
	@Override
	public long getCommerceOrderItemId() {
		return _commerceShipmentItem.getCommerceOrderItemId();
	}

	/**
	* Returns the commerce shipment ID of this commerce shipment item.
	*
	* @return the commerce shipment ID of this commerce shipment item
	*/
	@Override
	public long getCommerceShipmentId() {
		return _commerceShipmentItem.getCommerceShipmentId();
	}

	/**
	* Returns the commerce shipment item ID of this commerce shipment item.
	*
	* @return the commerce shipment item ID of this commerce shipment item
	*/
	@Override
	public long getCommerceShipmentItemId() {
		return _commerceShipmentItem.getCommerceShipmentItemId();
	}

	/**
	* Returns the company ID of this commerce shipment item.
	*
	* @return the company ID of this commerce shipment item
	*/
	@Override
	public long getCompanyId() {
		return _commerceShipmentItem.getCompanyId();
	}

	/**
	* Returns the create date of this commerce shipment item.
	*
	* @return the create date of this commerce shipment item
	*/
	@Override
	public Date getCreateDate() {
		return _commerceShipmentItem.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceShipmentItem.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce shipment item.
	*
	* @return the group ID of this commerce shipment item
	*/
	@Override
	public long getGroupId() {
		return _commerceShipmentItem.getGroupId();
	}

	/**
	* Returns the modified date of this commerce shipment item.
	*
	* @return the modified date of this commerce shipment item
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceShipmentItem.getModifiedDate();
	}

	/**
	* Returns the primary key of this commerce shipment item.
	*
	* @return the primary key of this commerce shipment item
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceShipmentItem.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceShipmentItem.getPrimaryKeyObj();
	}

	/**
	* Returns the quantity of this commerce shipment item.
	*
	* @return the quantity of this commerce shipment item
	*/
	@Override
	public int getQuantity() {
		return _commerceShipmentItem.getQuantity();
	}

	/**
	* Returns the user ID of this commerce shipment item.
	*
	* @return the user ID of this commerce shipment item
	*/
	@Override
	public long getUserId() {
		return _commerceShipmentItem.getUserId();
	}

	/**
	* Returns the user name of this commerce shipment item.
	*
	* @return the user name of this commerce shipment item
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceShipmentItem.getUserName();
	}

	/**
	* Returns the user uuid of this commerce shipment item.
	*
	* @return the user uuid of this commerce shipment item
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceShipmentItem.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceShipmentItem.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceShipmentItem.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceShipmentItem.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceShipmentItem.isNew();
	}

	@Override
	public void persist() {
		_commerceShipmentItem.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceShipmentItem.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce order item ID of this commerce shipment item.
	*
	* @param commerceOrderItemId the commerce order item ID of this commerce shipment item
	*/
	@Override
	public void setCommerceOrderItemId(long commerceOrderItemId) {
		_commerceShipmentItem.setCommerceOrderItemId(commerceOrderItemId);
	}

	/**
	* Sets the commerce shipment ID of this commerce shipment item.
	*
	* @param commerceShipmentId the commerce shipment ID of this commerce shipment item
	*/
	@Override
	public void setCommerceShipmentId(long commerceShipmentId) {
		_commerceShipmentItem.setCommerceShipmentId(commerceShipmentId);
	}

	/**
	* Sets the commerce shipment item ID of this commerce shipment item.
	*
	* @param commerceShipmentItemId the commerce shipment item ID of this commerce shipment item
	*/
	@Override
	public void setCommerceShipmentItemId(long commerceShipmentItemId) {
		_commerceShipmentItem.setCommerceShipmentItemId(commerceShipmentItemId);
	}

	/**
	* Sets the company ID of this commerce shipment item.
	*
	* @param companyId the company ID of this commerce shipment item
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceShipmentItem.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce shipment item.
	*
	* @param createDate the create date of this commerce shipment item
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceShipmentItem.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceShipmentItem.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceShipmentItem.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceShipmentItem.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce shipment item.
	*
	* @param groupId the group ID of this commerce shipment item
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceShipmentItem.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce shipment item.
	*
	* @param modifiedDate the modified date of this commerce shipment item
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceShipmentItem.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceShipmentItem.setNew(n);
	}

	/**
	* Sets the primary key of this commerce shipment item.
	*
	* @param primaryKey the primary key of this commerce shipment item
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceShipmentItem.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceShipmentItem.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the quantity of this commerce shipment item.
	*
	* @param quantity the quantity of this commerce shipment item
	*/
	@Override
	public void setQuantity(int quantity) {
		_commerceShipmentItem.setQuantity(quantity);
	}

	/**
	* Sets the user ID of this commerce shipment item.
	*
	* @param userId the user ID of this commerce shipment item
	*/
	@Override
	public void setUserId(long userId) {
		_commerceShipmentItem.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce shipment item.
	*
	* @param userName the user name of this commerce shipment item
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceShipmentItem.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce shipment item.
	*
	* @param userUuid the user uuid of this commerce shipment item
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceShipmentItem.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceShipmentItem> toCacheModel() {
		return _commerceShipmentItem.toCacheModel();
	}

	@Override
	public CommerceShipmentItem toEscapedModel() {
		return new CommerceShipmentItemWrapper(_commerceShipmentItem.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _commerceShipmentItem.toString();
	}

	@Override
	public CommerceShipmentItem toUnescapedModel() {
		return new CommerceShipmentItemWrapper(_commerceShipmentItem.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceShipmentItem.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceShipmentItemWrapper)) {
			return false;
		}

		CommerceShipmentItemWrapper commerceShipmentItemWrapper = (CommerceShipmentItemWrapper)obj;

		if (Objects.equals(_commerceShipmentItem,
					commerceShipmentItemWrapper._commerceShipmentItem)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceShipmentItem getWrappedModel() {
		return _commerceShipmentItem;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceShipmentItem.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceShipmentItem.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceShipmentItem.resetOriginalValues();
	}

	private final CommerceShipmentItem _commerceShipmentItem;
}