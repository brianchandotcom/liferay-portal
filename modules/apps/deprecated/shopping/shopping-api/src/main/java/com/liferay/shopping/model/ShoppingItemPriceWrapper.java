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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link ShoppingItemPrice}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingItemPrice
 * @generated
 */
@ProviderType
public class ShoppingItemPriceWrapper implements ShoppingItemPrice,
	ModelWrapper<ShoppingItemPrice> {
	public ShoppingItemPriceWrapper(ShoppingItemPrice shoppingItemPrice) {
		_shoppingItemPrice = shoppingItemPrice;
	}

	@Override
	public Class<?> getModelClass() {
		return ShoppingItemPrice.class;
	}

	@Override
	public String getModelClassName() {
		return ShoppingItemPrice.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("itemPriceId", getItemPriceId());
		attributes.put("companyId", getCompanyId());
		attributes.put("itemId", getItemId());
		attributes.put("minQuantity", getMinQuantity());
		attributes.put("maxQuantity", getMaxQuantity());
		attributes.put("price", getPrice());
		attributes.put("discount", getDiscount());
		attributes.put("taxable", getTaxable());
		attributes.put("shipping", getShipping());
		attributes.put("useShippingFormula", getUseShippingFormula());
		attributes.put("status", getStatus());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long itemPriceId = (Long)attributes.get("itemPriceId");

		if (itemPriceId != null) {
			setItemPriceId(itemPriceId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long itemId = (Long)attributes.get("itemId");

		if (itemId != null) {
			setItemId(itemId);
		}

		Integer minQuantity = (Integer)attributes.get("minQuantity");

		if (minQuantity != null) {
			setMinQuantity(minQuantity);
		}

		Integer maxQuantity = (Integer)attributes.get("maxQuantity");

		if (maxQuantity != null) {
			setMaxQuantity(maxQuantity);
		}

		Double price = (Double)attributes.get("price");

		if (price != null) {
			setPrice(price);
		}

		Double discount = (Double)attributes.get("discount");

		if (discount != null) {
			setDiscount(discount);
		}

		Boolean taxable = (Boolean)attributes.get("taxable");

		if (taxable != null) {
			setTaxable(taxable);
		}

		Double shipping = (Double)attributes.get("shipping");

		if (shipping != null) {
			setShipping(shipping);
		}

		Boolean useShippingFormula = (Boolean)attributes.get(
				"useShippingFormula");

		if (useShippingFormula != null) {
			setUseShippingFormula(useShippingFormula);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	@Override
	public ShoppingItemPrice toEscapedModel() {
		return new ShoppingItemPriceWrapper(_shoppingItemPrice.toEscapedModel());
	}

	@Override
	public ShoppingItemPrice toUnescapedModel() {
		return new ShoppingItemPriceWrapper(_shoppingItemPrice.toUnescapedModel());
	}

	/**
	* Returns the taxable of this shopping item price.
	*
	* @return the taxable of this shopping item price
	*/
	@Override
	public boolean getTaxable() {
		return _shoppingItemPrice.getTaxable();
	}

	/**
	* Returns the use shipping formula of this shopping item price.
	*
	* @return the use shipping formula of this shopping item price
	*/
	@Override
	public boolean getUseShippingFormula() {
		return _shoppingItemPrice.getUseShippingFormula();
	}

	@Override
	public boolean isCachedModel() {
		return _shoppingItemPrice.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _shoppingItemPrice.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _shoppingItemPrice.isNew();
	}

	/**
	* Returns <code>true</code> if this shopping item price is taxable.
	*
	* @return <code>true</code> if this shopping item price is taxable; <code>false</code> otherwise
	*/
	@Override
	public boolean isTaxable() {
		return _shoppingItemPrice.isTaxable();
	}

	/**
	* Returns <code>true</code> if this shopping item price is use shipping formula.
	*
	* @return <code>true</code> if this shopping item price is use shipping formula; <code>false</code> otherwise
	*/
	@Override
	public boolean isUseShippingFormula() {
		return _shoppingItemPrice.isUseShippingFormula();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _shoppingItemPrice.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<ShoppingItemPrice> toCacheModel() {
		return _shoppingItemPrice.toCacheModel();
	}

	/**
	* Returns the discount of this shopping item price.
	*
	* @return the discount of this shopping item price
	*/
	@Override
	public double getDiscount() {
		return _shoppingItemPrice.getDiscount();
	}

	/**
	* Returns the price of this shopping item price.
	*
	* @return the price of this shopping item price
	*/
	@Override
	public double getPrice() {
		return _shoppingItemPrice.getPrice();
	}

	/**
	* Returns the shipping of this shopping item price.
	*
	* @return the shipping of this shopping item price
	*/
	@Override
	public double getShipping() {
		return _shoppingItemPrice.getShipping();
	}

	@Override
	public int compareTo(ShoppingItemPrice shoppingItemPrice) {
		return _shoppingItemPrice.compareTo(shoppingItemPrice);
	}

	/**
	* Returns the max quantity of this shopping item price.
	*
	* @return the max quantity of this shopping item price
	*/
	@Override
	public int getMaxQuantity() {
		return _shoppingItemPrice.getMaxQuantity();
	}

	/**
	* Returns the min quantity of this shopping item price.
	*
	* @return the min quantity of this shopping item price
	*/
	@Override
	public int getMinQuantity() {
		return _shoppingItemPrice.getMinQuantity();
	}

	/**
	* Returns the status of this shopping item price.
	*
	* @return the status of this shopping item price
	*/
	@Override
	public int getStatus() {
		return _shoppingItemPrice.getStatus();
	}

	@Override
	public int hashCode() {
		return _shoppingItemPrice.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _shoppingItemPrice.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new ShoppingItemPriceWrapper((ShoppingItemPrice)_shoppingItemPrice.clone());
	}

	@Override
	public java.lang.String toString() {
		return _shoppingItemPrice.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _shoppingItemPrice.toXmlString();
	}

	/**
	* Returns the company ID of this shopping item price.
	*
	* @return the company ID of this shopping item price
	*/
	@Override
	public long getCompanyId() {
		return _shoppingItemPrice.getCompanyId();
	}

	/**
	* Returns the item ID of this shopping item price.
	*
	* @return the item ID of this shopping item price
	*/
	@Override
	public long getItemId() {
		return _shoppingItemPrice.getItemId();
	}

	/**
	* Returns the item price ID of this shopping item price.
	*
	* @return the item price ID of this shopping item price
	*/
	@Override
	public long getItemPriceId() {
		return _shoppingItemPrice.getItemPriceId();
	}

	/**
	* Returns the primary key of this shopping item price.
	*
	* @return the primary key of this shopping item price
	*/
	@Override
	public long getPrimaryKey() {
		return _shoppingItemPrice.getPrimaryKey();
	}

	@Override
	public void persist() {
		_shoppingItemPrice.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_shoppingItemPrice.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this shopping item price.
	*
	* @param companyId the company ID of this shopping item price
	*/
	@Override
	public void setCompanyId(long companyId) {
		_shoppingItemPrice.setCompanyId(companyId);
	}

	/**
	* Sets the discount of this shopping item price.
	*
	* @param discount the discount of this shopping item price
	*/
	@Override
	public void setDiscount(double discount) {
		_shoppingItemPrice.setDiscount(discount);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_shoppingItemPrice.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_shoppingItemPrice.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_shoppingItemPrice.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the item ID of this shopping item price.
	*
	* @param itemId the item ID of this shopping item price
	*/
	@Override
	public void setItemId(long itemId) {
		_shoppingItemPrice.setItemId(itemId);
	}

	/**
	* Sets the item price ID of this shopping item price.
	*
	* @param itemPriceId the item price ID of this shopping item price
	*/
	@Override
	public void setItemPriceId(long itemPriceId) {
		_shoppingItemPrice.setItemPriceId(itemPriceId);
	}

	/**
	* Sets the max quantity of this shopping item price.
	*
	* @param maxQuantity the max quantity of this shopping item price
	*/
	@Override
	public void setMaxQuantity(int maxQuantity) {
		_shoppingItemPrice.setMaxQuantity(maxQuantity);
	}

	/**
	* Sets the min quantity of this shopping item price.
	*
	* @param minQuantity the min quantity of this shopping item price
	*/
	@Override
	public void setMinQuantity(int minQuantity) {
		_shoppingItemPrice.setMinQuantity(minQuantity);
	}

	@Override
	public void setNew(boolean n) {
		_shoppingItemPrice.setNew(n);
	}

	/**
	* Sets the price of this shopping item price.
	*
	* @param price the price of this shopping item price
	*/
	@Override
	public void setPrice(double price) {
		_shoppingItemPrice.setPrice(price);
	}

	/**
	* Sets the primary key of this shopping item price.
	*
	* @param primaryKey the primary key of this shopping item price
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_shoppingItemPrice.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_shoppingItemPrice.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the shipping of this shopping item price.
	*
	* @param shipping the shipping of this shopping item price
	*/
	@Override
	public void setShipping(double shipping) {
		_shoppingItemPrice.setShipping(shipping);
	}

	/**
	* Sets the status of this shopping item price.
	*
	* @param status the status of this shopping item price
	*/
	@Override
	public void setStatus(int status) {
		_shoppingItemPrice.setStatus(status);
	}

	/**
	* Sets whether this shopping item price is taxable.
	*
	* @param taxable the taxable of this shopping item price
	*/
	@Override
	public void setTaxable(boolean taxable) {
		_shoppingItemPrice.setTaxable(taxable);
	}

	/**
	* Sets whether this shopping item price is use shipping formula.
	*
	* @param useShippingFormula the use shipping formula of this shopping item price
	*/
	@Override
	public void setUseShippingFormula(boolean useShippingFormula) {
		_shoppingItemPrice.setUseShippingFormula(useShippingFormula);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ShoppingItemPriceWrapper)) {
			return false;
		}

		ShoppingItemPriceWrapper shoppingItemPriceWrapper = (ShoppingItemPriceWrapper)obj;

		if (Objects.equals(_shoppingItemPrice,
					shoppingItemPriceWrapper._shoppingItemPrice)) {
			return true;
		}

		return false;
	}

	@Override
	public ShoppingItemPrice getWrappedModel() {
		return _shoppingItemPrice;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _shoppingItemPrice.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _shoppingItemPrice.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_shoppingItemPrice.resetOriginalValues();
	}

	private final ShoppingItemPrice _shoppingItemPrice;
}