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
 * This class is a wrapper for {@link ShoppingCart}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingCart
 * @generated
 */
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

	/**
	* Returns the primary key of this shopping cart.
	*
	* @return the primary key of this shopping cart
	*/
	@Override
	public long getPrimaryKey() {
		return _shoppingCart.getPrimaryKey();
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
	* Sets the cart ID of this shopping cart.
	*
	* @param cartId the cart ID of this shopping cart
	*/
	@Override
	public void setCartId(long cartId) {
		_shoppingCart.setCartId(cartId);
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
	* Sets the group ID of this shopping cart.
	*
	* @param groupId the group ID of this shopping cart
	*/
	@Override
	public void setGroupId(long groupId) {
		_shoppingCart.setGroupId(groupId);
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
	* Returns the user ID of this shopping cart.
	*
	* @return the user ID of this shopping cart
	*/
	@Override
	public long getUserId() {
		return _shoppingCart.getUserId();
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
	* Returns the user uuid of this shopping cart.
	*
	* @return the user uuid of this shopping cart
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingCart.getUserUuid();
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
	* Sets the user name of this shopping cart.
	*
	* @param userName the user name of this shopping cart
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_shoppingCart.setUserName(userName);
	}

	/**
	* Returns the create date of this shopping cart.
	*
	* @return the create date of this shopping cart
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _shoppingCart.getCreateDate();
	}

	/**
	* Sets the create date of this shopping cart.
	*
	* @param createDate the create date of this shopping cart
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_shoppingCart.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this shopping cart.
	*
	* @return the modified date of this shopping cart
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _shoppingCart.getModifiedDate();
	}

	/**
	* Sets the modified date of this shopping cart.
	*
	* @param modifiedDate the modified date of this shopping cart
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_shoppingCart.setModifiedDate(modifiedDate);
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
	* Returns the coupon codes of this shopping cart.
	*
	* @return the coupon codes of this shopping cart
	*/
	@Override
	public java.lang.String getCouponCodes() {
		return _shoppingCart.getCouponCodes();
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
	* Returns the alt shipping of this shopping cart.
	*
	* @return the alt shipping of this shopping cart
	*/
	@Override
	public int getAltShipping() {
		return _shoppingCart.getAltShipping();
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
	* Returns <code>true</code> if this shopping cart is insure.
	*
	* @return <code>true</code> if this shopping cart is insure; <code>false</code> otherwise
	*/
	@Override
	public boolean isInsure() {
		return _shoppingCart.isInsure();
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

	@Override
	public boolean isNew() {
		return _shoppingCart.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_shoppingCart.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _shoppingCart.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_shoppingCart.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _shoppingCart.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _shoppingCart.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_shoppingCart.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _shoppingCart.getExpandoBridge();
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

	@Override
	public java.lang.Object clone() {
		return new ShoppingCartWrapper((ShoppingCart)_shoppingCart.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart) {
		return _shoppingCart.compareTo(shoppingCart);
	}

	@Override
	public int hashCode() {
		return _shoppingCart.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.shopping.model.ShoppingCart> toCacheModel() {
		return _shoppingCart.toCacheModel();
	}

	@Override
	public com.liferay.portlet.shopping.model.ShoppingCart toEscapedModel() {
		return new ShoppingCartWrapper(_shoppingCart.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.shopping.model.ShoppingCart toUnescapedModel() {
		return new ShoppingCartWrapper(_shoppingCart.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _shoppingCart.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _shoppingCart.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_shoppingCart.persist();
	}

	@Override
	public void addItemId(long itemId, java.lang.String fields) {
		_shoppingCart.addItemId(itemId, fields);
	}

	@Override
	public com.liferay.portlet.shopping.model.ShoppingCoupon getCoupon()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shoppingCart.getCoupon();
	}

	@Override
	public java.util.Map<com.liferay.portlet.shopping.model.ShoppingCartItem, java.lang.Integer> getItems()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingCart.getItems();
	}

	@Override
	public int getItemsSize() {
		return _shoppingCart.getItemsSize();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public ShoppingCart getWrappedShoppingCart() {
		return _shoppingCart;
	}

	@Override
	public ShoppingCart getWrappedModel() {
		return _shoppingCart;
	}

	@Override
	public void resetOriginalValues() {
		_shoppingCart.resetOriginalValues();
	}

	private ShoppingCart _shoppingCart;
}