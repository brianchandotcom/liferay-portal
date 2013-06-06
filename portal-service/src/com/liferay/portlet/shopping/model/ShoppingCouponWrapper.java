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
 * This class is a wrapper for {@link ShoppingCoupon}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingCoupon
 * @generated
 */
public class ShoppingCouponWrapper implements ShoppingCoupon,
	ModelWrapper<ShoppingCoupon> {
	public ShoppingCouponWrapper(ShoppingCoupon shoppingCoupon) {
		_shoppingCoupon = shoppingCoupon;
	}

	@Override
	public Class<?> getModelClass() {
		return ShoppingCoupon.class;
	}

	@Override
	public String getModelClassName() {
		return ShoppingCoupon.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("couponId", getCouponId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("code", getCode());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("startDate", getStartDate());
		attributes.put("endDate", getEndDate());
		attributes.put("active", getActive());
		attributes.put("limitCategories", getLimitCategories());
		attributes.put("limitSkus", getLimitSkus());
		attributes.put("minOrder", getMinOrder());
		attributes.put("discount", getDiscount());
		attributes.put("discountType", getDiscountType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long couponId = (Long)attributes.get("couponId");

		if (couponId != null) {
			setCouponId(couponId);
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

		String code = (String)attributes.get("code");

		if (code != null) {
			setCode(code);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Date startDate = (Date)attributes.get("startDate");

		if (startDate != null) {
			setStartDate(startDate);
		}

		Date endDate = (Date)attributes.get("endDate");

		if (endDate != null) {
			setEndDate(endDate);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		String limitCategories = (String)attributes.get("limitCategories");

		if (limitCategories != null) {
			setLimitCategories(limitCategories);
		}

		String limitSkus = (String)attributes.get("limitSkus");

		if (limitSkus != null) {
			setLimitSkus(limitSkus);
		}

		Double minOrder = (Double)attributes.get("minOrder");

		if (minOrder != null) {
			setMinOrder(minOrder);
		}

		Double discount = (Double)attributes.get("discount");

		if (discount != null) {
			setDiscount(discount);
		}

		String discountType = (String)attributes.get("discountType");

		if (discountType != null) {
			setDiscountType(discountType);
		}
	}

	/**
	* Returns the primary key of this shopping coupon.
	*
	* @return the primary key of this shopping coupon
	*/
	@Override
	public long getPrimaryKey() {
		return _shoppingCoupon.getPrimaryKey();
	}

	/**
	* Sets the primary key of this shopping coupon.
	*
	* @param primaryKey the primary key of this shopping coupon
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_shoppingCoupon.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the coupon ID of this shopping coupon.
	*
	* @return the coupon ID of this shopping coupon
	*/
	@Override
	public long getCouponId() {
		return _shoppingCoupon.getCouponId();
	}

	/**
	* Sets the coupon ID of this shopping coupon.
	*
	* @param couponId the coupon ID of this shopping coupon
	*/
	@Override
	public void setCouponId(long couponId) {
		_shoppingCoupon.setCouponId(couponId);
	}

	/**
	* Returns the group ID of this shopping coupon.
	*
	* @return the group ID of this shopping coupon
	*/
	@Override
	public long getGroupId() {
		return _shoppingCoupon.getGroupId();
	}

	/**
	* Sets the group ID of this shopping coupon.
	*
	* @param groupId the group ID of this shopping coupon
	*/
	@Override
	public void setGroupId(long groupId) {
		_shoppingCoupon.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this shopping coupon.
	*
	* @return the company ID of this shopping coupon
	*/
	@Override
	public long getCompanyId() {
		return _shoppingCoupon.getCompanyId();
	}

	/**
	* Sets the company ID of this shopping coupon.
	*
	* @param companyId the company ID of this shopping coupon
	*/
	@Override
	public void setCompanyId(long companyId) {
		_shoppingCoupon.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this shopping coupon.
	*
	* @return the user ID of this shopping coupon
	*/
	@Override
	public long getUserId() {
		return _shoppingCoupon.getUserId();
	}

	/**
	* Sets the user ID of this shopping coupon.
	*
	* @param userId the user ID of this shopping coupon
	*/
	@Override
	public void setUserId(long userId) {
		_shoppingCoupon.setUserId(userId);
	}

	/**
	* Returns the user uuid of this shopping coupon.
	*
	* @return the user uuid of this shopping coupon
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingCoupon.getUserUuid();
	}

	/**
	* Sets the user uuid of this shopping coupon.
	*
	* @param userUuid the user uuid of this shopping coupon
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_shoppingCoupon.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this shopping coupon.
	*
	* @return the user name of this shopping coupon
	*/
	@Override
	public java.lang.String getUserName() {
		return _shoppingCoupon.getUserName();
	}

	/**
	* Sets the user name of this shopping coupon.
	*
	* @param userName the user name of this shopping coupon
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_shoppingCoupon.setUserName(userName);
	}

	/**
	* Returns the create date of this shopping coupon.
	*
	* @return the create date of this shopping coupon
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _shoppingCoupon.getCreateDate();
	}

	/**
	* Sets the create date of this shopping coupon.
	*
	* @param createDate the create date of this shopping coupon
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_shoppingCoupon.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this shopping coupon.
	*
	* @return the modified date of this shopping coupon
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _shoppingCoupon.getModifiedDate();
	}

	/**
	* Sets the modified date of this shopping coupon.
	*
	* @param modifiedDate the modified date of this shopping coupon
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_shoppingCoupon.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the code of this shopping coupon.
	*
	* @return the code of this shopping coupon
	*/
	@Override
	public java.lang.String getCode() {
		return _shoppingCoupon.getCode();
	}

	/**
	* Sets the code of this shopping coupon.
	*
	* @param code the code of this shopping coupon
	*/
	@Override
	public void setCode(java.lang.String code) {
		_shoppingCoupon.setCode(code);
	}

	/**
	* Returns the name of this shopping coupon.
	*
	* @return the name of this shopping coupon
	*/
	@Override
	public java.lang.String getName() {
		return _shoppingCoupon.getName();
	}

	/**
	* Sets the name of this shopping coupon.
	*
	* @param name the name of this shopping coupon
	*/
	@Override
	public void setName(java.lang.String name) {
		_shoppingCoupon.setName(name);
	}

	/**
	* Returns the description of this shopping coupon.
	*
	* @return the description of this shopping coupon
	*/
	@Override
	public java.lang.String getDescription() {
		return _shoppingCoupon.getDescription();
	}

	/**
	* Sets the description of this shopping coupon.
	*
	* @param description the description of this shopping coupon
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_shoppingCoupon.setDescription(description);
	}

	/**
	* Returns the start date of this shopping coupon.
	*
	* @return the start date of this shopping coupon
	*/
	@Override
	public java.util.Date getStartDate() {
		return _shoppingCoupon.getStartDate();
	}

	/**
	* Sets the start date of this shopping coupon.
	*
	* @param startDate the start date of this shopping coupon
	*/
	@Override
	public void setStartDate(java.util.Date startDate) {
		_shoppingCoupon.setStartDate(startDate);
	}

	/**
	* Returns the end date of this shopping coupon.
	*
	* @return the end date of this shopping coupon
	*/
	@Override
	public java.util.Date getEndDate() {
		return _shoppingCoupon.getEndDate();
	}

	/**
	* Sets the end date of this shopping coupon.
	*
	* @param endDate the end date of this shopping coupon
	*/
	@Override
	public void setEndDate(java.util.Date endDate) {
		_shoppingCoupon.setEndDate(endDate);
	}

	/**
	* Returns the active of this shopping coupon.
	*
	* @return the active of this shopping coupon
	*/
	@Override
	public boolean getActive() {
		return _shoppingCoupon.getActive();
	}

	/**
	* Returns <code>true</code> if this shopping coupon is active.
	*
	* @return <code>true</code> if this shopping coupon is active; <code>false</code> otherwise
	*/
	@Override
	public boolean isActive() {
		return _shoppingCoupon.isActive();
	}

	/**
	* Sets whether this shopping coupon is active.
	*
	* @param active the active of this shopping coupon
	*/
	@Override
	public void setActive(boolean active) {
		_shoppingCoupon.setActive(active);
	}

	/**
	* Returns the limit categories of this shopping coupon.
	*
	* @return the limit categories of this shopping coupon
	*/
	@Override
	public java.lang.String getLimitCategories() {
		return _shoppingCoupon.getLimitCategories();
	}

	/**
	* Sets the limit categories of this shopping coupon.
	*
	* @param limitCategories the limit categories of this shopping coupon
	*/
	@Override
	public void setLimitCategories(java.lang.String limitCategories) {
		_shoppingCoupon.setLimitCategories(limitCategories);
	}

	/**
	* Returns the limit skus of this shopping coupon.
	*
	* @return the limit skus of this shopping coupon
	*/
	@Override
	public java.lang.String getLimitSkus() {
		return _shoppingCoupon.getLimitSkus();
	}

	/**
	* Sets the limit skus of this shopping coupon.
	*
	* @param limitSkus the limit skus of this shopping coupon
	*/
	@Override
	public void setLimitSkus(java.lang.String limitSkus) {
		_shoppingCoupon.setLimitSkus(limitSkus);
	}

	/**
	* Returns the min order of this shopping coupon.
	*
	* @return the min order of this shopping coupon
	*/
	@Override
	public double getMinOrder() {
		return _shoppingCoupon.getMinOrder();
	}

	/**
	* Sets the min order of this shopping coupon.
	*
	* @param minOrder the min order of this shopping coupon
	*/
	@Override
	public void setMinOrder(double minOrder) {
		_shoppingCoupon.setMinOrder(minOrder);
	}

	/**
	* Returns the discount of this shopping coupon.
	*
	* @return the discount of this shopping coupon
	*/
	@Override
	public double getDiscount() {
		return _shoppingCoupon.getDiscount();
	}

	/**
	* Sets the discount of this shopping coupon.
	*
	* @param discount the discount of this shopping coupon
	*/
	@Override
	public void setDiscount(double discount) {
		_shoppingCoupon.setDiscount(discount);
	}

	/**
	* Returns the discount type of this shopping coupon.
	*
	* @return the discount type of this shopping coupon
	*/
	@Override
	public java.lang.String getDiscountType() {
		return _shoppingCoupon.getDiscountType();
	}

	/**
	* Sets the discount type of this shopping coupon.
	*
	* @param discountType the discount type of this shopping coupon
	*/
	@Override
	public void setDiscountType(java.lang.String discountType) {
		_shoppingCoupon.setDiscountType(discountType);
	}

	@Override
	public boolean isNew() {
		return _shoppingCoupon.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_shoppingCoupon.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _shoppingCoupon.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_shoppingCoupon.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _shoppingCoupon.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _shoppingCoupon.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_shoppingCoupon.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _shoppingCoupon.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_shoppingCoupon.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_shoppingCoupon.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_shoppingCoupon.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new ShoppingCouponWrapper((ShoppingCoupon)_shoppingCoupon.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.shopping.model.ShoppingCoupon shoppingCoupon) {
		return _shoppingCoupon.compareTo(shoppingCoupon);
	}

	@Override
	public int hashCode() {
		return _shoppingCoupon.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.shopping.model.ShoppingCoupon> toCacheModel() {
		return _shoppingCoupon.toCacheModel();
	}

	@Override
	public com.liferay.portlet.shopping.model.ShoppingCoupon toEscapedModel() {
		return new ShoppingCouponWrapper(_shoppingCoupon.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.shopping.model.ShoppingCoupon toUnescapedModel() {
		return new ShoppingCouponWrapper(_shoppingCoupon.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _shoppingCoupon.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _shoppingCoupon.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_shoppingCoupon.persist();
	}

	@Override
	public boolean hasValidDateRange() {
		return _shoppingCoupon.hasValidDateRange();
	}

	@Override
	public boolean hasValidEndDate() {
		return _shoppingCoupon.hasValidEndDate();
	}

	@Override
	public boolean hasValidStartDate() {
		return _shoppingCoupon.hasValidStartDate();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public ShoppingCoupon getWrappedShoppingCoupon() {
		return _shoppingCoupon;
	}

	@Override
	public ShoppingCoupon getWrappedModel() {
		return _shoppingCoupon;
	}

	@Override
	public void resetOriginalValues() {
		_shoppingCoupon.resetOriginalValues();
	}

	private ShoppingCoupon _shoppingCoupon;
}