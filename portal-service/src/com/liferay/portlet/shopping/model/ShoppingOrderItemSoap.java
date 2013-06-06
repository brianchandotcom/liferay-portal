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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ShoppingOrderItemSoap implements Serializable {
	public static ShoppingOrderItemSoap toSoapModel(ShoppingOrderItem model) {
		ShoppingOrderItemSoap soapModel = new ShoppingOrderItemSoap();

		soapModel.setOrderItemId(model.getOrderItemId());
		soapModel.setOrderId(model.getOrderId());
		soapModel.setItemId(model.getItemId());
		soapModel.setSku(model.getSku());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setProperties(model.getProperties());
		soapModel.setPrice(model.getPrice());
		soapModel.setQuantity(model.getQuantity());
		soapModel.setShippedDate(model.getShippedDate());

		return soapModel;
	}

	public static ShoppingOrderItemSoap[] toSoapModels(
		ShoppingOrderItem[] models) {
		ShoppingOrderItemSoap[] soapModels = new ShoppingOrderItemSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ShoppingOrderItemSoap[][] toSoapModels(
		ShoppingOrderItem[][] models) {
		ShoppingOrderItemSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ShoppingOrderItemSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ShoppingOrderItemSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ShoppingOrderItemSoap[] toSoapModels(
		List<ShoppingOrderItem> models) {
		List<ShoppingOrderItemSoap> soapModels = new ArrayList<ShoppingOrderItemSoap>(models.size());

		for (ShoppingOrderItem model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ShoppingOrderItemSoap[soapModels.size()]);
	}

	public ShoppingOrderItemSoap() {
	}

	public long getPrimaryKey() {
		return _orderItemId;
	}

	public void setPrimaryKey(long pk) {
		setOrderItemId(pk);
	}

	public long getOrderItemId() {
		return _orderItemId;
	}

	public void setOrderItemId(long orderItemId) {
		_orderItemId = orderItemId;
	}

	public long getOrderId() {
		return _orderId;
	}

	public void setOrderId(long orderId) {
		_orderId = orderId;
	}

	public String getItemId() {
		return _itemId;
	}

	public void setItemId(String itemId) {
		_itemId = itemId;
	}

	public String getSku() {
		return _sku;
	}

	public void setSku(String sku) {
		_sku = sku;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getProperties() {
		return _properties;
	}

	public void setProperties(String properties) {
		_properties = properties;
	}

	public double getPrice() {
		return _price;
	}

	public void setPrice(double price) {
		_price = price;
	}

	public int getQuantity() {
		return _quantity;
	}

	public void setQuantity(int quantity) {
		_quantity = quantity;
	}

	public Date getShippedDate() {
		return _shippedDate;
	}

	public void setShippedDate(Date shippedDate) {
		_shippedDate = shippedDate;
	}

	private long _orderItemId;
	private long _orderId;
	private String _itemId;
	private String _sku;
	private String _name;
	private String _description;
	private String _properties;
	private double _price;
	private int _quantity;
	private Date _shippedDate;
}