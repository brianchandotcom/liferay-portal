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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.shopping.service.http.ShoppingCouponServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.shopping.service.http.ShoppingCouponServiceSoap
 * @generated
 */
@ProviderType
public class ShoppingCouponSoap implements Serializable {
	public static ShoppingCouponSoap toSoapModel(ShoppingCoupon model) {
		ShoppingCouponSoap soapModel = new ShoppingCouponSoap();

		soapModel.setCouponId(model.getCouponId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCode(model.getCode());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setStartDate(model.getStartDate());
		soapModel.setEndDate(model.getEndDate());
		soapModel.setActive(model.getActive());
		soapModel.setLimitCategories(model.getLimitCategories());
		soapModel.setLimitSkus(model.getLimitSkus());
		soapModel.setMinOrder(model.getMinOrder());
		soapModel.setDiscount(model.getDiscount());
		soapModel.setDiscountType(model.getDiscountType());

		return soapModel;
	}

	public static ShoppingCouponSoap[] toSoapModels(ShoppingCoupon[] models) {
		ShoppingCouponSoap[] soapModels = new ShoppingCouponSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ShoppingCouponSoap[][] toSoapModels(ShoppingCoupon[][] models) {
		ShoppingCouponSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ShoppingCouponSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ShoppingCouponSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ShoppingCouponSoap[] toSoapModels(List<ShoppingCoupon> models) {
		List<ShoppingCouponSoap> soapModels = new ArrayList<ShoppingCouponSoap>(models.size());

		for (ShoppingCoupon model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ShoppingCouponSoap[soapModels.size()]);
	}

	public ShoppingCouponSoap() {
	}

	public long getPrimaryKey() {
		return _couponId;
	}

	public void setPrimaryKey(long pk) {
		setCouponId(pk);
	}

	public long getCouponId() {
		return _couponId;
	}

	public void setCouponId(long couponId) {
		_couponId = couponId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getCode() {
		return _code;
	}

	public void setCode(String code) {
		_code = code;
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

	public Date getStartDate() {
		return _startDate;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public String getLimitCategories() {
		return _limitCategories;
	}

	public void setLimitCategories(String limitCategories) {
		_limitCategories = limitCategories;
	}

	public String getLimitSkus() {
		return _limitSkus;
	}

	public void setLimitSkus(String limitSkus) {
		_limitSkus = limitSkus;
	}

	public double getMinOrder() {
		return _minOrder;
	}

	public void setMinOrder(double minOrder) {
		_minOrder = minOrder;
	}

	public double getDiscount() {
		return _discount;
	}

	public void setDiscount(double discount) {
		_discount = discount;
	}

	public String getDiscountType() {
		return _discountType;
	}

	public void setDiscountType(String discountType) {
		_discountType = discountType;
	}

	private long _couponId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _code;
	private String _name;
	private String _description;
	private Date _startDate;
	private Date _endDate;
	private boolean _active;
	private String _limitCategories;
	private String _limitSkus;
	private double _minOrder;
	private double _discount;
	private String _discountType;
}