/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ShoppingOrderItem}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingOrderItem
 * @generated
 */
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

	/**
	* Returns the primary key of this shopping order item.
	*
	* @return the primary key of this shopping order item
	*/
	@Override
	public long getPrimaryKey() {
		return _shoppingOrderItem.getPrimaryKey();
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
	* Sets the order item ID of this shopping order item.
	*
	* @param orderItemId the order item ID of this shopping order item
	*/
	@Override
	public void setOrderItemId(long orderItemId) {
		_shoppingOrderItem.setOrderItemId(orderItemId);
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
	* Sets the order ID of this shopping order item.
	*
	* @param orderId the order ID of this shopping order item
	*/
	@Override
	public void setOrderId(long orderId) {
		_shoppingOrderItem.setOrderId(orderId);
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
	* Sets the item ID of this shopping order item.
	*
	* @param itemId the item ID of this shopping order item
	*/
	@Override
	public void setItemId(java.lang.String itemId) {
		_shoppingOrderItem.setItemId(itemId);
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

	/**
	* Sets the sku of this shopping order item.
	*
	* @param sku the sku of this shopping order item
	*/
	@Override
	public void setSku(java.lang.String sku) {
		_shoppingOrderItem.setSku(sku);
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
	* Sets the name of this shopping order item.
	*
	* @param name the name of this shopping order item
	*/
	@Override
	public void setName(java.lang.String name) {
		_shoppingOrderItem.setName(name);
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
	* Sets the description of this shopping order item.
	*
	* @param description the description of this shopping order item
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_shoppingOrderItem.setDescription(description);
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
	* Sets the properties of this shopping order item.
	*
	* @param properties the properties of this shopping order item
	*/
	@Override
	public void setProperties(java.lang.String properties) {
		_shoppingOrderItem.setProperties(properties);
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
	* Returns the quantity of this shopping order item.
	*
	* @return the quantity of this shopping order item
	*/
	@Override
	public int getQuantity() {
		return _shoppingOrderItem.getQuantity();
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
	* Returns the shipped date of this shopping order item.
	*
	* @return the shipped date of this shopping order item
	*/
	@Override
	public java.util.Date getShippedDate() {
		return _shoppingOrderItem.getShippedDate();
	}

	/**
	* Sets the shipped date of this shopping order item.
	*
	* @param shippedDate the shipped date of this shopping order item
	*/
	@Override
	public void setShippedDate(java.util.Date shippedDate) {
		_shoppingOrderItem.setShippedDate(shippedDate);
	}

	@Override
	public boolean isNew() {
		return _shoppingOrderItem.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_shoppingOrderItem.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _shoppingOrderItem.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_shoppingOrderItem.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _shoppingOrderItem.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _shoppingOrderItem.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_shoppingOrderItem.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _shoppingOrderItem.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_shoppingOrderItem.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_shoppingOrderItem.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_shoppingOrderItem.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new ShoppingOrderItemWrapper((ShoppingOrderItem)_shoppingOrderItem.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.shopping.model.ShoppingOrderItem shoppingOrderItem) {
		return _shoppingOrderItem.compareTo(shoppingOrderItem);
	}

	@Override
	public int hashCode() {
		return _shoppingOrderItem.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.shopping.model.ShoppingOrderItem> toCacheModel() {
		return _shoppingOrderItem.toCacheModel();
	}

	@Override
	public com.liferay.portlet.shopping.model.ShoppingOrderItem toEscapedModel() {
		return new ShoppingOrderItemWrapper(_shoppingOrderItem.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.shopping.model.ShoppingOrderItem toUnescapedModel() {
		return new ShoppingOrderItemWrapper(_shoppingOrderItem.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _shoppingOrderItem.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _shoppingOrderItem.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_shoppingOrderItem.persist();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public ShoppingOrderItem getWrappedShoppingOrderItem() {
		return _shoppingOrderItem;
	}

	@Override
	public ShoppingOrderItem getWrappedModel() {
		return _shoppingOrderItem;
	}

	@Override
	public void resetOriginalValues() {
		_shoppingOrderItem.resetOriginalValues();
	}

	private ShoppingOrderItem _shoppingOrderItem;
}