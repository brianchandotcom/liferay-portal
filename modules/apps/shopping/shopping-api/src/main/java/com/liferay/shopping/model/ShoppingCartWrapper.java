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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ShoppingCart}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingCart
 * @generated
 */
@ProviderType
public class ShoppingCartWrapper implements ShoppingCart,
	ModelWrapper<ShoppingCart> {
	public ShoppingCartWrapper(ShoppingCart shoppingCart) {
		_shoppingCart = shoppingCart;
	}

	@Override
	public Class<?> getModelClass() {
		return ShoppingCart.class;
	}

	@Override
	public String getModelClassName() {
		return ShoppingCart.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("cartId", getCartId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("itemIds", getItemIds());
		attributes.put("couponCodes", getCouponCodes());
		attributes.put("altShipping", getAltShipping());
		attributes.put("insure", getInsure());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long cartId = (Long)attributes.get("cartId");

		if (cartId != null) {
			setCartId(cartId);
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

		String itemIds = (String)attributes.get("itemIds");

		if (itemIds != null) {
			setItemIds(itemIds);
		}

		String couponCodes = (String)attributes.get("couponCodes");

		if (couponCodes != null) {
			setCouponCodes(couponCodes);
		}

		Integer altShipping = (Integer)attributes.get("altShipping");

		if (altShipping != null) {
			setAltShipping(altShipping);
		}

		Boolean insure = (Boolean)attributes.get("insure");

		if (insure != null) {
			setInsure(insure);
		}
	}

	@Override
	public void addItemId(long itemId, java.lang.String fields) {
		_shoppingCart.addItemId(itemId, fields);
	}

	@Override
	public java.lang.Object clone() {
		return new ShoppingCartWrapper((ShoppingCart)_shoppingCart.clone());
	}

	@Override
	public int compareTo(com.liferay.shopping.model.ShoppingCart shoppingCart) {
		return _shoppingCart.compareTo(shoppingCart);
	}

	/**
	* Returns the alt shipping of this shopping cart.
	*
	* @return the alt shipping of this shopping cart
	*/
	@Override
	public int getAltShipping() {
		return _shoppingCart.getAltShipping();
	}

	/**
	* Returns the cart ID of this shopping cart.
	*
	* @return the cart ID of this shopping cart
	*/
	@Override
	public long getCartId() {
		return _shoppingCart.getCartId();
	}

	/**
	* Returns the company ID of this shopping cart.
	*
	* @return the company ID of this shopping cart
	*/
	@Override
	public long getCompanyId() {
		return _shoppingCart.getCompanyId();
	}

	@Override
	public com.liferay.shopping.model.ShoppingCoupon getCoupon()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingCart.getCoupon();
	}

	/**
	* Returns the coupon codes of this shopping cart.
	*
	* @return the coupon codes of this shopping cart
	*/
	@Override
	public java.lang.String getCouponCodes() {
		return _shoppingCart.getCouponCodes();
	}

	/**
	* Returns the create date of this shopping cart.
	*
	* @return the create date of this shopping cart
	*/
	@Override
	public Date getCreateDate() {
		return _shoppingCart.getCreateDate();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _shoppingCart.getExpandoBridge();
	}

	/**
	* Returns the group ID of this shopping cart.
	*
	* @return the group ID of this shopping cart
	*/
	@Override
	public long getGroupId() {
		return _shoppingCart.getGroupId();
	}

	/**
	* Returns the insure of this shopping cart.
	*
	* @return the insure of this shopping cart
	*/
	@Override
	public boolean getInsure() {
		return _shoppingCart.getInsure();
	}

	/**
	* Returns the item IDs of this shopping cart.
	*
	* @return the item IDs of this shopping cart
	*/
	@Override
	public java.lang.String getItemIds() {
		return _shoppingCart.getItemIds();
	}

	@Override
	public Map<com.liferay.shopping.model.ShoppingCartItem, java.lang.Integer> getItems() {
		return _shoppingCart.getItems();
	}

	@Override
	public int getItemsSize() {
		return _shoppingCart.getItemsSize();
	}

	/**
	* Returns the modified date of this shopping cart.
	*
	* @return the modified date of this shopping cart
	*/
	@Override
	public Date getModifiedDate() {
		return _shoppingCart.getModifiedDate();
	}

	/**
	* Returns the primary key of this shopping cart.
	*
	* @return the primary key of this shopping cart
	*/
	@Override
	public long getPrimaryKey() {
		return _shoppingCart.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _shoppingCart.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this shopping cart.
	*
	* @return the user ID of this shopping cart
	*/
	@Override
	public long getUserId() {
		return _shoppingCart.getUserId();
	}

	/**
	* Returns the user name of this shopping cart.
	*
	* @return the user name of this shopping cart
	*/
	@Override
	public java.lang.String getUserName() {
		return _shoppingCart.getUserName();
	}

	/**
	* Returns the user uuid of this shopping cart.
	*
	* @return the user uuid of this shopping cart
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _shoppingCart.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _shoppingCart.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _shoppingCart.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _shoppingCart.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this shopping cart is insure.
	*
	* @return <code>true</code> if this shopping cart is insure; <code>false</code> otherwise
	*/
	@Override
	public boolean isInsure() {
		return _shoppingCart.isInsure();
	}

	@Override
	public boolean isNew() {
		return _shoppingCart.isNew();
	}

	@Override
	public void persist() {
		_shoppingCart.persist();
	}

	/**
	* Sets the alt shipping of this shopping cart.
	*
	* @param altShipping the alt shipping of this shopping cart
	*/
	@Override
	public void setAltShipping(int altShipping) {
		_shoppingCart.setAltShipping(altShipping);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_shoppingCart.setCachedModel(cachedModel);
	}

	/**
	* Sets the cart ID of this shopping cart.
	*
	* @param cartId the cart ID of this shopping cart
	*/
	@Override
	public void setCartId(long cartId) {
		_shoppingCart.setCartId(cartId);
	}

	/**
	* Sets the company ID of this shopping cart.
	*
	* @param companyId the company ID of this shopping cart
	*/
	@Override
	public void setCompanyId(long companyId) {
		_shoppingCart.setCompanyId(companyId);
	}

	/**
	* Sets the coupon codes of this shopping cart.
	*
	* @param couponCodes the coupon codes of this shopping cart
	*/
	@Override
	public void setCouponCodes(java.lang.String couponCodes) {
		_shoppingCart.setCouponCodes(couponCodes);
	}

	/**
	* Sets the create date of this shopping cart.
	*
	* @param createDate the create date of this shopping cart
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_shoppingCart.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_shoppingCart.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_shoppingCart.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_shoppingCart.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this shopping cart.
	*
	* @param groupId the group ID of this shopping cart
	*/
	@Override
	public void setGroupId(long groupId) {
		_shoppingCart.setGroupId(groupId);
	}

	/**
	* Sets whether this shopping cart is insure.
	*
	* @param insure the insure of this shopping cart
	*/
	@Override
	public void setInsure(boolean insure) {
		_shoppingCart.setInsure(insure);
	}

	/**
	* Sets the item IDs of this shopping cart.
	*
	* @param itemIds the item IDs of this shopping cart
	*/
	@Override
	public void setItemIds(java.lang.String itemIds) {
		_shoppingCart.setItemIds(itemIds);
	}

	/**
	* Sets the modified date of this shopping cart.
	*
	* @param modifiedDate the modified date of this shopping cart
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_shoppingCart.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_shoppingCart.setNew(n);
	}

	/**
	* Sets the primary key of this shopping cart.
	*
	* @param primaryKey the primary key of this shopping cart
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_shoppingCart.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_shoppingCart.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this shopping cart.
	*
	* @param userId the user ID of this shopping cart
	*/
	@Override
	public void setUserId(long userId) {
		_shoppingCart.setUserId(userId);
	}

	/**
	* Sets the user name of this shopping cart.
	*
	* @param userName the user name of this shopping cart
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_shoppingCart.setUserName(userName);
	}

	/**
	* Sets the user uuid of this shopping cart.
	*
	* @param userUuid the user uuid of this shopping cart
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_shoppingCart.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.shopping.model.ShoppingCart> toCacheModel() {
		return _shoppingCart.toCacheModel();
	}

	@Override
	public com.liferay.shopping.model.ShoppingCart toEscapedModel() {
		return new ShoppingCartWrapper(_shoppingCart.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _shoppingCart.toString();
	}

	@Override
	public com.liferay.shopping.model.ShoppingCart toUnescapedModel() {
		return new ShoppingCartWrapper(_shoppingCart.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _shoppingCart.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ShoppingCartWrapper)) {
			return false;
		}

		ShoppingCartWrapper shoppingCartWrapper = (ShoppingCartWrapper)obj;

		if (Validator.equals(_shoppingCart, shoppingCartWrapper._shoppingCart)) {
			return true;
		}

		return false;
	}

	@Override
	public ShoppingCart getWrappedModel() {
		return _shoppingCart;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _shoppingCart.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _shoppingCart.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_shoppingCart.resetOriginalValues();
	}

	private final ShoppingCart _shoppingCart;
}