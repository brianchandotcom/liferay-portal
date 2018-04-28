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

package com.liferay.commerce.discount.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.discount.service.http.CommerceDiscountServiceSoap}.
 *
 * @author Marco Leo
 * @see com.liferay.commerce.discount.service.http.CommerceDiscountServiceSoap
 * @generated
 */
@ProviderType
public class CommerceDiscountSoap implements Serializable {
	public static CommerceDiscountSoap toSoapModel(CommerceDiscount model) {
		CommerceDiscountSoap soapModel = new CommerceDiscountSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCommerceDiscountId(model.getCommerceDiscountId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setTitle(model.getTitle());
		soapModel.setTarget(model.getTarget());
		soapModel.setUseCouponCode(model.isUseCouponCode());
		soapModel.setCouponCode(model.getCouponCode());
		soapModel.setUsePercentage(model.isUsePercentage());
		soapModel.setMaximumDiscountAmount(model.getMaximumDiscountAmount());
		soapModel.setLevel1(model.getLevel1());
		soapModel.setLevel2(model.getLevel2());
		soapModel.setLevel3(model.getLevel3());
		soapModel.setLimitationType(model.getLimitationType());
		soapModel.setLimitationTimes(model.getLimitationTimes());
		soapModel.setNumberOfUse(model.getNumberOfUse());
		soapModel.setCumulative(model.isCumulative());
		soapModel.setActive(model.isActive());
		soapModel.setDisplayDate(model.getDisplayDate());
		soapModel.setExpirationDate(model.getExpirationDate());
		soapModel.setLastPublishDate(model.getLastPublishDate());
		soapModel.setStatus(model.getStatus());
		soapModel.setStatusByUserId(model.getStatusByUserId());
		soapModel.setStatusByUserName(model.getStatusByUserName());
		soapModel.setStatusDate(model.getStatusDate());

		return soapModel;
	}

	public static CommerceDiscountSoap[] toSoapModels(CommerceDiscount[] models) {
		CommerceDiscountSoap[] soapModels = new CommerceDiscountSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceDiscountSoap[][] toSoapModels(
		CommerceDiscount[][] models) {
		CommerceDiscountSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceDiscountSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceDiscountSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceDiscountSoap[] toSoapModels(
		List<CommerceDiscount> models) {
		List<CommerceDiscountSoap> soapModels = new ArrayList<CommerceDiscountSoap>(models.size());

		for (CommerceDiscount model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceDiscountSoap[soapModels.size()]);
	}

	public CommerceDiscountSoap() {
	}

	public long getPrimaryKey() {
		return _commerceDiscountId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceDiscountId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCommerceDiscountId() {
		return _commerceDiscountId;
	}

	public void setCommerceDiscountId(long commerceDiscountId) {
		_commerceDiscountId = commerceDiscountId;
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

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public String getTarget() {
		return _target;
	}

	public void setTarget(String target) {
		_target = target;
	}

	public boolean getUseCouponCode() {
		return _useCouponCode;
	}

	public boolean isUseCouponCode() {
		return _useCouponCode;
	}

	public void setUseCouponCode(boolean useCouponCode) {
		_useCouponCode = useCouponCode;
	}

	public String getCouponCode() {
		return _couponCode;
	}

	public void setCouponCode(String couponCode) {
		_couponCode = couponCode;
	}

	public boolean getUsePercentage() {
		return _usePercentage;
	}

	public boolean isUsePercentage() {
		return _usePercentage;
	}

	public void setUsePercentage(boolean usePercentage) {
		_usePercentage = usePercentage;
	}

	public BigDecimal getMaximumDiscountAmount() {
		return _maximumDiscountAmount;
	}

	public void setMaximumDiscountAmount(BigDecimal maximumDiscountAmount) {
		_maximumDiscountAmount = maximumDiscountAmount;
	}

	public BigDecimal getLevel1() {
		return _level1;
	}

	public void setLevel1(BigDecimal level1) {
		_level1 = level1;
	}

	public BigDecimal getLevel2() {
		return _level2;
	}

	public void setLevel2(BigDecimal level2) {
		_level2 = level2;
	}

	public BigDecimal getLevel3() {
		return _level3;
	}

	public void setLevel3(BigDecimal level3) {
		_level3 = level3;
	}

	public String getLimitationType() {
		return _limitationType;
	}

	public void setLimitationType(String limitationType) {
		_limitationType = limitationType;
	}

	public int getLimitationTimes() {
		return _limitationTimes;
	}

	public void setLimitationTimes(int limitationTimes) {
		_limitationTimes = limitationTimes;
	}

	public int getNumberOfUse() {
		return _numberOfUse;
	}

	public void setNumberOfUse(int numberOfUse) {
		_numberOfUse = numberOfUse;
	}

	public boolean getCumulative() {
		return _cumulative;
	}

	public boolean isCumulative() {
		return _cumulative;
	}

	public void setCumulative(boolean cumulative) {
		_cumulative = cumulative;
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

	public Date getDisplayDate() {
		return _displayDate;
	}

	public void setDisplayDate(Date displayDate) {
		_displayDate = displayDate;
	}

	public Date getExpirationDate() {
		return _expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		_expirationDate = expirationDate;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	public long getStatusByUserId() {
		return _statusByUserId;
	}

	public void setStatusByUserId(long statusByUserId) {
		_statusByUserId = statusByUserId;
	}

	public String getStatusByUserName() {
		return _statusByUserName;
	}

	public void setStatusByUserName(String statusByUserName) {
		_statusByUserName = statusByUserName;
	}

	public Date getStatusDate() {
		return _statusDate;
	}

	public void setStatusDate(Date statusDate) {
		_statusDate = statusDate;
	}

	private String _uuid;
	private long _commerceDiscountId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _title;
	private String _target;
	private boolean _useCouponCode;
	private String _couponCode;
	private boolean _usePercentage;
	private BigDecimal _maximumDiscountAmount;
	private BigDecimal _level1;
	private BigDecimal _level2;
	private BigDecimal _level3;
	private String _limitationType;
	private int _limitationTimes;
	private int _numberOfUse;
	private boolean _cumulative;
	private boolean _active;
	private Date _displayDate;
	private Date _expirationDate;
	private Date _lastPublishDate;
	private int _status;
	private long _statusByUserId;
	private String _statusByUserName;
	private Date _statusDate;
}