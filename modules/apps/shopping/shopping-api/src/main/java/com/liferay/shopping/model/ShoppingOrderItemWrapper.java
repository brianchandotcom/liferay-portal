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

package com.liferay.shopping.model;

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
 * This class is a wrapper for {@link ShoppingOrderItem}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingOrderItem
 * @generated
 */
@ProviderType
public class ShoppingOrderItemWrapper implements ShoppingOrderItem,
	ModelWrapper<ShoppingOrderItem> {
	public ShoppingOrderItemWrapper(ShoppingOrderItem shoppingOrderItem) {
		_shoppingOrderItem = shoppingOrderItem;
	}

	@Override
	public Class<?> getModelClass() {
		return ShoppingOrderItem.class;
	}

	@Override
	public String getModelClassName() {
		return ShoppingOrderItem.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("orderItemId", getOrderItemId());
		attributes.put("companyId", getCompanyId());
		attributes.put("orderId", getOrderId());
		attributes.put("itemId", getItemId());
		attributes.put("sku", getSku());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("properties", getProperties());
		attributes.put("price", getPrice());
		attributes.put("quantity", getQuantity());
		attributes.put("shippedDate", getShippedDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long orderItemId = (Long)attributes.get("orderItemId");

		if (orderItemId != null) {
			setOrderItemId(orderItemId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long orderId = (Long)attributes.get("orderId");

		if (orderId != null) {
			setOrderId(orderId);
		}

		String itemId = (String)attributes.get("itemId");

		if (itemId != null) {
			setItemId(itemId);
		}

		String sku = (String)attributes.get("sku");

		if (sku != null) {
			setSku(sku);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String properties = (String)attributes.get("properties");

		if (properties != null) {
			setProperties(properties);
		}

		Double price = (Double)attributes.get("price");

		if (price != null) {
			setPrice(price);
		}

		Integer quantity = (Integer)attributes.get("quantity");

		if (quantity != null) {
			setQuantity(quantity);
		}

		Date shippedDate = (Date)attributes.get("shippedDate");

		if (shippedDate != null) {
			setShippedDate(shippedDate);
		}
	}

	@Override
	public ShoppingOrderItem toEscapedModel() {
		return new ShoppingOrderItemWrapper(_shoppingOrderItem.toEscapedModel());
	}

	@Override
	public ShoppingOrderItem toUnescapedModel() {
		return new ShoppingOrderItemWrapper(_shoppingOrderItem.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _shoppingOrderItem.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _shoppingOrderItem.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _shoppingOrderItem.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _shoppingOrderItem.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<ShoppingOrderItem> toCacheModel() {
		return _shoppingOrderItem.toCacheModel();
	}

	/**
	* Returns the price of this shopping order item.
	*
	* @return the price of this shopping order item
	*/
	@Override
	public double getPrice() {
		return _shoppingOrderItem.getPrice();
	}

	@Override
	public int compareTo(ShoppingOrderItem shoppingOrderItem) {
		return _shoppingOrderItem.compareTo(shoppingOrderItem);
	}

	/**
	* Returns the quantity of this shopping order item.
	*
	* @return the quantity of this shopping order item
	*/
	@Override
	public int getQuantity() {
		return _shoppingOrderItem.getQuantity();
	}

	@Override
	public int hashCode() {
		return _shoppingOrderItem.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _shoppingOrderItem.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new ShoppingOrderItemWrapper((ShoppingOrderItem)_shoppingOrderItem.clone());
	}

	/**
	* Returns the description of this shopping order item.
	*
	* @return the description of this shopping order item
	*/
	@Override
	public java.lang.String getDescription() {
		return _shoppingOrderItem.getDescription();
	}

	/**
	* Returns the item ID of this shopping order item.
	*
	* @return the item ID of this shopping order item
	*/
	@Override
	public java.lang.String getItemId() {
		return _shoppingOrderItem.getItemId();
	}

	/**
	* Returns the name of this shopping order item.
	*
	* @return the name of this shopping order item
	*/
	@Override
	public java.lang.String getName() {
		return _shoppingOrderItem.getName();
	}

	/**
	* Returns the properties of this shopping order item.
	*
	* @return the properties of this shopping order item
	*/
	@Override
	public java.lang.String getProperties() {
		return _shoppingOrderItem.getProperties();
	}

	/**
	* Returns the sku of this shopping order item.
	*
	* @return the sku of this shopping order item
	*/
	@Override
	public java.lang.String getSku() {
		return _shoppingOrderItem.getSku();
	}

	@Override
	public java.lang.String toString() {
		return _shoppingOrderItem.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _shoppingOrderItem.toXmlString();
	}

	/**
	* Returns the shipped date of this shopping order item.
	*
	* @return the shipped date of this shopping order item
	*/
	@Override
	public Date getShippedDate() {
		return _shoppingOrderItem.getShippedDate();
	}

	/**
	* Returns the company ID of this shopping order item.
	*
	* @return the company ID of this shopping order item
	*/
	@Override
	public long getCompanyId() {
		return _shoppingOrderItem.getCompanyId();
	}

	/**
	* Returns the order ID of this shopping order item.
	*
	* @return the order ID of this shopping order item
	*/
	@Override
	public long getOrderId() {
		return _shoppingOrderItem.getOrderId();
	}

	/**
	* Returns the order item ID of this shopping order item.
	*
	* @return the order item ID of this shopping order item
	*/
	@Override
	public long getOrderItemId() {
		return _shoppingOrderItem.getOrderItemId();
	}

	/**
	* Returns the primary key of this shopping order item.
	*
	* @return the primary key of this shopping order item
	*/
	@Override
	public long getPrimaryKey() {
		return _shoppingOrderItem.getPrimaryKey();
	}

	@Override
	public void persist() {
		_shoppingOrderItem.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_shoppingOrderItem.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this shopping order item.
	*
	* @param companyId the company ID of this shopping order item
	*/
	@Override
	public void setCompanyId(long companyId) {
		_shoppingOrderItem.setCompanyId(companyId);
	}

	/**
	* Sets the description of this shopping order item.
	*
	* @param description the description of this shopping order item
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_shoppingOrderItem.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_shoppingOrderItem.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_shoppingOrderItem.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_shoppingOrderItem.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the item ID of this shopping order item.
	*
	* @param itemId the item ID of this shopping order item
	*/
	@Override
	public void setItemId(java.lang.String itemId) {
		_shoppingOrderItem.setItemId(itemId);
	}

	/**
	* Sets the name of this shopping order item.
	*
	* @param name the name of this shopping order item
	*/
	@Override
	public void setName(java.lang.String name) {
		_shoppingOrderItem.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_shoppingOrderItem.setNew(n);
	}

	/**
	* Sets the order ID of this shopping order item.
	*
	* @param orderId the order ID of this shopping order item
	*/
	@Override
	public void setOrderId(long orderId) {
		_shoppingOrderItem.setOrderId(orderId);
	}

	/**
	* Sets the order item ID of this shopping order item.
	*
	* @param orderItemId the order item ID of this shopping order item
	*/
	@Override
	public void setOrderItemId(long orderItemId) {
		_shoppingOrderItem.setOrderItemId(orderItemId);
	}

	/**
	* Sets the price of this shopping order item.
	*
	* @param price the price of this shopping order item
	*/
	@Override
	public void setPrice(double price) {
		_shoppingOrderItem.setPrice(price);
	}

	/**
	* Sets the primary key of this shopping order item.
	*
	* @param primaryKey the primary key of this shopping order item
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_shoppingOrderItem.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_shoppingOrderItem.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the properties of this shopping order item.
	*
	* @param properties the properties of this shopping order item
	*/
	@Override
	public void setProperties(java.lang.String properties) {
		_shoppingOrderItem.setProperties(properties);
	}

	/**
	* Sets the quantity of this shopping order item.
	*
	* @param quantity the quantity of this shopping order item
	*/
	@Override
	public void setQuantity(int quantity) {
		_shoppingOrderItem.setQuantity(quantity);
	}

	/**
	* Sets the shipped date of this shopping order item.
	*
	* @param shippedDate the shipped date of this shopping order item
	*/
	@Override
	public void setShippedDate(Date shippedDate) {
		_shoppingOrderItem.setShippedDate(shippedDate);
	}

	/**
	* Sets the sku of this shopping order item.
	*
	* @param sku the sku of this shopping order item
	*/
	@Override
	public void setSku(java.lang.String sku) {
		_shoppingOrderItem.setSku(sku);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ShoppingOrderItemWrapper)) {
			return false;
		}

		ShoppingOrderItemWrapper shoppingOrderItemWrapper = (ShoppingOrderItemWrapper)obj;

		if (Objects.equals(_shoppingOrderItem,
					shoppingOrderItemWrapper._shoppingOrderItem)) {
			return true;
		}

		return false;
	}

	@Override
	public ShoppingOrderItem getWrappedModel() {
		return _shoppingOrderItem;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _shoppingOrderItem.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _shoppingOrderItem.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_shoppingOrderItem.resetOriginalValues();
	}

	private final ShoppingOrderItem _shoppingOrderItem;
}