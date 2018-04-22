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

package com.liferay.commerce.shipping.engine.fixed.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link CommerceShippingFixedOptionRel}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionRel
 * @generated
 */
@ProviderType
public class CommerceShippingFixedOptionRelWrapper
	implements CommerceShippingFixedOptionRel,
		ModelWrapper<CommerceShippingFixedOptionRel> {
	public CommerceShippingFixedOptionRelWrapper(
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel) {
		_commerceShippingFixedOptionRel = commerceShippingFixedOptionRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceShippingFixedOptionRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceShippingFixedOptionRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceShippingFixedOptionRelId",
			getCommerceShippingFixedOptionRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceShippingMethodId", getCommerceShippingMethodId());
		attributes.put("commerceShippingFixedOptionId",
			getCommerceShippingFixedOptionId());
		attributes.put("commerceWarehouseId", getCommerceWarehouseId());
		attributes.put("commerceCountryId", getCommerceCountryId());
		attributes.put("commerceRegionId", getCommerceRegionId());
		attributes.put("zip", getZip());
		attributes.put("weightFrom", getWeightFrom());
		attributes.put("weightTo", getWeightTo());
		attributes.put("fixedPrice", getFixedPrice());
		attributes.put("rateUnitWeightPrice", getRateUnitWeightPrice());
		attributes.put("ratePercentage", getRatePercentage());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceShippingFixedOptionRelId = (Long)attributes.get(
				"commerceShippingFixedOptionRelId");

		if (commerceShippingFixedOptionRelId != null) {
			setCommerceShippingFixedOptionRelId(commerceShippingFixedOptionRelId);
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

		Long commerceShippingMethodId = (Long)attributes.get(
				"commerceShippingMethodId");

		if (commerceShippingMethodId != null) {
			setCommerceShippingMethodId(commerceShippingMethodId);
		}

		Long commerceShippingFixedOptionId = (Long)attributes.get(
				"commerceShippingFixedOptionId");

		if (commerceShippingFixedOptionId != null) {
			setCommerceShippingFixedOptionId(commerceShippingFixedOptionId);
		}

		Long commerceWarehouseId = (Long)attributes.get("commerceWarehouseId");

		if (commerceWarehouseId != null) {
			setCommerceWarehouseId(commerceWarehouseId);
		}

		Long commerceCountryId = (Long)attributes.get("commerceCountryId");

		if (commerceCountryId != null) {
			setCommerceCountryId(commerceCountryId);
		}

		Long commerceRegionId = (Long)attributes.get("commerceRegionId");

		if (commerceRegionId != null) {
			setCommerceRegionId(commerceRegionId);
		}

		String zip = (String)attributes.get("zip");

		if (zip != null) {
			setZip(zip);
		}

		Double weightFrom = (Double)attributes.get("weightFrom");

		if (weightFrom != null) {
			setWeightFrom(weightFrom);
		}

		Double weightTo = (Double)attributes.get("weightTo");

		if (weightTo != null) {
			setWeightTo(weightTo);
		}

		BigDecimal fixedPrice = (BigDecimal)attributes.get("fixedPrice");

		if (fixedPrice != null) {
			setFixedPrice(fixedPrice);
		}

		BigDecimal rateUnitWeightPrice = (BigDecimal)attributes.get(
				"rateUnitWeightPrice");

		if (rateUnitWeightPrice != null) {
			setRateUnitWeightPrice(rateUnitWeightPrice);
		}

		Double ratePercentage = (Double)attributes.get("ratePercentage");

		if (ratePercentage != null) {
			setRatePercentage(ratePercentage);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new CommerceShippingFixedOptionRelWrapper((CommerceShippingFixedOptionRel)_commerceShippingFixedOptionRel.clone());
	}

	@Override
	public int compareTo(
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel) {
		return _commerceShippingFixedOptionRel.compareTo(commerceShippingFixedOptionRel);
	}

	@Override
	public com.liferay.commerce.model.CommerceCountry getCommerceCountry()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShippingFixedOptionRel.getCommerceCountry();
	}

	/**
	* Returns the commerce country ID of this commerce shipping fixed option rel.
	*
	* @return the commerce country ID of this commerce shipping fixed option rel
	*/
	@Override
	public long getCommerceCountryId() {
		return _commerceShippingFixedOptionRel.getCommerceCountryId();
	}

	@Override
	public com.liferay.commerce.model.CommerceRegion getCommerceRegion()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShippingFixedOptionRel.getCommerceRegion();
	}

	/**
	* Returns the commerce region ID of this commerce shipping fixed option rel.
	*
	* @return the commerce region ID of this commerce shipping fixed option rel
	*/
	@Override
	public long getCommerceRegionId() {
		return _commerceShippingFixedOptionRel.getCommerceRegionId();
	}

	@Override
	public CommerceShippingFixedOption getCommerceShippingFixedOption()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShippingFixedOptionRel.getCommerceShippingFixedOption();
	}

	/**
	* Returns the commerce shipping fixed option ID of this commerce shipping fixed option rel.
	*
	* @return the commerce shipping fixed option ID of this commerce shipping fixed option rel
	*/
	@Override
	public long getCommerceShippingFixedOptionId() {
		return _commerceShippingFixedOptionRel.getCommerceShippingFixedOptionId();
	}

	/**
	* Returns the commerce shipping fixed option rel ID of this commerce shipping fixed option rel.
	*
	* @return the commerce shipping fixed option rel ID of this commerce shipping fixed option rel
	*/
	@Override
	public long getCommerceShippingFixedOptionRelId() {
		return _commerceShippingFixedOptionRel.getCommerceShippingFixedOptionRelId();
	}

	@Override
	public com.liferay.commerce.model.CommerceShippingMethod getCommerceShippingMethod()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShippingFixedOptionRel.getCommerceShippingMethod();
	}

	/**
	* Returns the commerce shipping method ID of this commerce shipping fixed option rel.
	*
	* @return the commerce shipping method ID of this commerce shipping fixed option rel
	*/
	@Override
	public long getCommerceShippingMethodId() {
		return _commerceShippingFixedOptionRel.getCommerceShippingMethodId();
	}

	@Override
	public com.liferay.commerce.model.CommerceWarehouse getCommerceWarehouse()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShippingFixedOptionRel.getCommerceWarehouse();
	}

	/**
	* Returns the commerce warehouse ID of this commerce shipping fixed option rel.
	*
	* @return the commerce warehouse ID of this commerce shipping fixed option rel
	*/
	@Override
	public long getCommerceWarehouseId() {
		return _commerceShippingFixedOptionRel.getCommerceWarehouseId();
	}

	/**
	* Returns the company ID of this commerce shipping fixed option rel.
	*
	* @return the company ID of this commerce shipping fixed option rel
	*/
	@Override
	public long getCompanyId() {
		return _commerceShippingFixedOptionRel.getCompanyId();
	}

	/**
	* Returns the create date of this commerce shipping fixed option rel.
	*
	* @return the create date of this commerce shipping fixed option rel
	*/
	@Override
	public Date getCreateDate() {
		return _commerceShippingFixedOptionRel.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceShippingFixedOptionRel.getExpandoBridge();
	}

	/**
	* Returns the fixed price of this commerce shipping fixed option rel.
	*
	* @return the fixed price of this commerce shipping fixed option rel
	*/
	@Override
	public BigDecimal getFixedPrice() {
		return _commerceShippingFixedOptionRel.getFixedPrice();
	}

	/**
	* Returns the group ID of this commerce shipping fixed option rel.
	*
	* @return the group ID of this commerce shipping fixed option rel
	*/
	@Override
	public long getGroupId() {
		return _commerceShippingFixedOptionRel.getGroupId();
	}

	/**
	* Returns the modified date of this commerce shipping fixed option rel.
	*
	* @return the modified date of this commerce shipping fixed option rel
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceShippingFixedOptionRel.getModifiedDate();
	}

	/**
	* Returns the primary key of this commerce shipping fixed option rel.
	*
	* @return the primary key of this commerce shipping fixed option rel
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceShippingFixedOptionRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceShippingFixedOptionRel.getPrimaryKeyObj();
	}

	/**
	* Returns the rate percentage of this commerce shipping fixed option rel.
	*
	* @return the rate percentage of this commerce shipping fixed option rel
	*/
	@Override
	public double getRatePercentage() {
		return _commerceShippingFixedOptionRel.getRatePercentage();
	}

	/**
	* Returns the rate unit weight price of this commerce shipping fixed option rel.
	*
	* @return the rate unit weight price of this commerce shipping fixed option rel
	*/
	@Override
	public BigDecimal getRateUnitWeightPrice() {
		return _commerceShippingFixedOptionRel.getRateUnitWeightPrice();
	}

	/**
	* Returns the user ID of this commerce shipping fixed option rel.
	*
	* @return the user ID of this commerce shipping fixed option rel
	*/
	@Override
	public long getUserId() {
		return _commerceShippingFixedOptionRel.getUserId();
	}

	/**
	* Returns the user name of this commerce shipping fixed option rel.
	*
	* @return the user name of this commerce shipping fixed option rel
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceShippingFixedOptionRel.getUserName();
	}

	/**
	* Returns the user uuid of this commerce shipping fixed option rel.
	*
	* @return the user uuid of this commerce shipping fixed option rel
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceShippingFixedOptionRel.getUserUuid();
	}

	/**
	* Returns the weight from of this commerce shipping fixed option rel.
	*
	* @return the weight from of this commerce shipping fixed option rel
	*/
	@Override
	public double getWeightFrom() {
		return _commerceShippingFixedOptionRel.getWeightFrom();
	}

	/**
	* Returns the weight to of this commerce shipping fixed option rel.
	*
	* @return the weight to of this commerce shipping fixed option rel
	*/
	@Override
	public double getWeightTo() {
		return _commerceShippingFixedOptionRel.getWeightTo();
	}

	/**
	* Returns the zip of this commerce shipping fixed option rel.
	*
	* @return the zip of this commerce shipping fixed option rel
	*/
	@Override
	public java.lang.String getZip() {
		return _commerceShippingFixedOptionRel.getZip();
	}

	@Override
	public int hashCode() {
		return _commerceShippingFixedOptionRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceShippingFixedOptionRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceShippingFixedOptionRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceShippingFixedOptionRel.isNew();
	}

	@Override
	public void persist() {
		_commerceShippingFixedOptionRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceShippingFixedOptionRel.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce country ID of this commerce shipping fixed option rel.
	*
	* @param commerceCountryId the commerce country ID of this commerce shipping fixed option rel
	*/
	@Override
	public void setCommerceCountryId(long commerceCountryId) {
		_commerceShippingFixedOptionRel.setCommerceCountryId(commerceCountryId);
	}

	/**
	* Sets the commerce region ID of this commerce shipping fixed option rel.
	*
	* @param commerceRegionId the commerce region ID of this commerce shipping fixed option rel
	*/
	@Override
	public void setCommerceRegionId(long commerceRegionId) {
		_commerceShippingFixedOptionRel.setCommerceRegionId(commerceRegionId);
	}

	/**
	* Sets the commerce shipping fixed option ID of this commerce shipping fixed option rel.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID of this commerce shipping fixed option rel
	*/
	@Override
	public void setCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId) {
		_commerceShippingFixedOptionRel.setCommerceShippingFixedOptionId(commerceShippingFixedOptionId);
	}

	/**
	* Sets the commerce shipping fixed option rel ID of this commerce shipping fixed option rel.
	*
	* @param commerceShippingFixedOptionRelId the commerce shipping fixed option rel ID of this commerce shipping fixed option rel
	*/
	@Override
	public void setCommerceShippingFixedOptionRelId(
		long commerceShippingFixedOptionRelId) {
		_commerceShippingFixedOptionRel.setCommerceShippingFixedOptionRelId(commerceShippingFixedOptionRelId);
	}

	/**
	* Sets the commerce shipping method ID of this commerce shipping fixed option rel.
	*
	* @param commerceShippingMethodId the commerce shipping method ID of this commerce shipping fixed option rel
	*/
	@Override
	public void setCommerceShippingMethodId(long commerceShippingMethodId) {
		_commerceShippingFixedOptionRel.setCommerceShippingMethodId(commerceShippingMethodId);
	}

	/**
	* Sets the commerce warehouse ID of this commerce shipping fixed option rel.
	*
	* @param commerceWarehouseId the commerce warehouse ID of this commerce shipping fixed option rel
	*/
	@Override
	public void setCommerceWarehouseId(long commerceWarehouseId) {
		_commerceShippingFixedOptionRel.setCommerceWarehouseId(commerceWarehouseId);
	}

	/**
	* Sets the company ID of this commerce shipping fixed option rel.
	*
	* @param companyId the company ID of this commerce shipping fixed option rel
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceShippingFixedOptionRel.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce shipping fixed option rel.
	*
	* @param createDate the create date of this commerce shipping fixed option rel
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceShippingFixedOptionRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceShippingFixedOptionRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceShippingFixedOptionRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceShippingFixedOptionRel.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the fixed price of this commerce shipping fixed option rel.
	*
	* @param fixedPrice the fixed price of this commerce shipping fixed option rel
	*/
	@Override
	public void setFixedPrice(BigDecimal fixedPrice) {
		_commerceShippingFixedOptionRel.setFixedPrice(fixedPrice);
	}

	/**
	* Sets the group ID of this commerce shipping fixed option rel.
	*
	* @param groupId the group ID of this commerce shipping fixed option rel
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceShippingFixedOptionRel.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce shipping fixed option rel.
	*
	* @param modifiedDate the modified date of this commerce shipping fixed option rel
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceShippingFixedOptionRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceShippingFixedOptionRel.setNew(n);
	}

	/**
	* Sets the primary key of this commerce shipping fixed option rel.
	*
	* @param primaryKey the primary key of this commerce shipping fixed option rel
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceShippingFixedOptionRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceShippingFixedOptionRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the rate percentage of this commerce shipping fixed option rel.
	*
	* @param ratePercentage the rate percentage of this commerce shipping fixed option rel
	*/
	@Override
	public void setRatePercentage(double ratePercentage) {
		_commerceShippingFixedOptionRel.setRatePercentage(ratePercentage);
	}

	/**
	* Sets the rate unit weight price of this commerce shipping fixed option rel.
	*
	* @param rateUnitWeightPrice the rate unit weight price of this commerce shipping fixed option rel
	*/
	@Override
	public void setRateUnitWeightPrice(BigDecimal rateUnitWeightPrice) {
		_commerceShippingFixedOptionRel.setRateUnitWeightPrice(rateUnitWeightPrice);
	}

	/**
	* Sets the user ID of this commerce shipping fixed option rel.
	*
	* @param userId the user ID of this commerce shipping fixed option rel
	*/
	@Override
	public void setUserId(long userId) {
		_commerceShippingFixedOptionRel.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce shipping fixed option rel.
	*
	* @param userName the user name of this commerce shipping fixed option rel
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceShippingFixedOptionRel.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce shipping fixed option rel.
	*
	* @param userUuid the user uuid of this commerce shipping fixed option rel
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceShippingFixedOptionRel.setUserUuid(userUuid);
	}

	/**
	* Sets the weight from of this commerce shipping fixed option rel.
	*
	* @param weightFrom the weight from of this commerce shipping fixed option rel
	*/
	@Override
	public void setWeightFrom(double weightFrom) {
		_commerceShippingFixedOptionRel.setWeightFrom(weightFrom);
	}

	/**
	* Sets the weight to of this commerce shipping fixed option rel.
	*
	* @param weightTo the weight to of this commerce shipping fixed option rel
	*/
	@Override
	public void setWeightTo(double weightTo) {
		_commerceShippingFixedOptionRel.setWeightTo(weightTo);
	}

	/**
	* Sets the zip of this commerce shipping fixed option rel.
	*
	* @param zip the zip of this commerce shipping fixed option rel
	*/
	@Override
	public void setZip(java.lang.String zip) {
		_commerceShippingFixedOptionRel.setZip(zip);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceShippingFixedOptionRel> toCacheModel() {
		return _commerceShippingFixedOptionRel.toCacheModel();
	}

	@Override
	public CommerceShippingFixedOptionRel toEscapedModel() {
		return new CommerceShippingFixedOptionRelWrapper(_commerceShippingFixedOptionRel.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _commerceShippingFixedOptionRel.toString();
	}

	@Override
	public CommerceShippingFixedOptionRel toUnescapedModel() {
		return new CommerceShippingFixedOptionRelWrapper(_commerceShippingFixedOptionRel.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceShippingFixedOptionRel.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceShippingFixedOptionRelWrapper)) {
			return false;
		}

		CommerceShippingFixedOptionRelWrapper commerceShippingFixedOptionRelWrapper =
			(CommerceShippingFixedOptionRelWrapper)obj;

		if (Objects.equals(_commerceShippingFixedOptionRel,
					commerceShippingFixedOptionRelWrapper._commerceShippingFixedOptionRel)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceShippingFixedOptionRel getWrappedModel() {
		return _commerceShippingFixedOptionRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceShippingFixedOptionRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceShippingFixedOptionRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceShippingFixedOptionRel.resetOriginalValues();
	}

	private final CommerceShippingFixedOptionRel _commerceShippingFixedOptionRel;
}