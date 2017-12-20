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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link CShippingFixedOptionRel}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CShippingFixedOptionRel
 * @generated
 */
@ProviderType
public class CShippingFixedOptionRelWrapper implements CShippingFixedOptionRel,
	ModelWrapper<CShippingFixedOptionRel> {
	public CShippingFixedOptionRelWrapper(
		CShippingFixedOptionRel cShippingFixedOptionRel) {
		_cShippingFixedOptionRel = cShippingFixedOptionRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CShippingFixedOptionRel.class;
	}

	@Override
	public String getModelClassName() {
		return CShippingFixedOptionRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("CShippingFixedOptionRelId",
			getCShippingFixedOptionRelId());
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
		Long CShippingFixedOptionRelId = (Long)attributes.get(
				"CShippingFixedOptionRelId");

		if (CShippingFixedOptionRelId != null) {
			setCShippingFixedOptionRelId(CShippingFixedOptionRelId);
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

		Double fixedPrice = (Double)attributes.get("fixedPrice");

		if (fixedPrice != null) {
			setFixedPrice(fixedPrice);
		}

		Double rateUnitWeightPrice = (Double)attributes.get(
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
		return new CShippingFixedOptionRelWrapper((CShippingFixedOptionRel)_cShippingFixedOptionRel.clone());
	}

	@Override
	public int compareTo(CShippingFixedOptionRel cShippingFixedOptionRel) {
		return _cShippingFixedOptionRel.compareTo(cShippingFixedOptionRel);
	}

	@Override
	public com.liferay.commerce.model.CommerceCountry getCommerceCountry()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cShippingFixedOptionRel.getCommerceCountry();
	}

	/**
	* Returns the commerce country ID of this c shipping fixed option rel.
	*
	* @return the commerce country ID of this c shipping fixed option rel
	*/
	@Override
	public long getCommerceCountryId() {
		return _cShippingFixedOptionRel.getCommerceCountryId();
	}

	@Override
	public com.liferay.commerce.model.CommerceRegion getCommerceRegion()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cShippingFixedOptionRel.getCommerceRegion();
	}

	/**
	* Returns the commerce region ID of this c shipping fixed option rel.
	*
	* @return the commerce region ID of this c shipping fixed option rel
	*/
	@Override
	public long getCommerceRegionId() {
		return _cShippingFixedOptionRel.getCommerceRegionId();
	}

	@Override
	public CommerceShippingFixedOption getCommerceShippingFixedOption()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cShippingFixedOptionRel.getCommerceShippingFixedOption();
	}

	/**
	* Returns the commerce shipping fixed option ID of this c shipping fixed option rel.
	*
	* @return the commerce shipping fixed option ID of this c shipping fixed option rel
	*/
	@Override
	public long getCommerceShippingFixedOptionId() {
		return _cShippingFixedOptionRel.getCommerceShippingFixedOptionId();
	}

	@Override
	public com.liferay.commerce.model.CommerceShippingMethod getCommerceShippingMethod()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cShippingFixedOptionRel.getCommerceShippingMethod();
	}

	/**
	* Returns the commerce shipping method ID of this c shipping fixed option rel.
	*
	* @return the commerce shipping method ID of this c shipping fixed option rel
	*/
	@Override
	public long getCommerceShippingMethodId() {
		return _cShippingFixedOptionRel.getCommerceShippingMethodId();
	}

	@Override
	public com.liferay.commerce.model.CommerceWarehouse getCommerceWarehouse()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cShippingFixedOptionRel.getCommerceWarehouse();
	}

	/**
	* Returns the commerce warehouse ID of this c shipping fixed option rel.
	*
	* @return the commerce warehouse ID of this c shipping fixed option rel
	*/
	@Override
	public long getCommerceWarehouseId() {
		return _cShippingFixedOptionRel.getCommerceWarehouseId();
	}

	/**
	* Returns the company ID of this c shipping fixed option rel.
	*
	* @return the company ID of this c shipping fixed option rel
	*/
	@Override
	public long getCompanyId() {
		return _cShippingFixedOptionRel.getCompanyId();
	}

	/**
	* Returns the create date of this c shipping fixed option rel.
	*
	* @return the create date of this c shipping fixed option rel
	*/
	@Override
	public Date getCreateDate() {
		return _cShippingFixedOptionRel.getCreateDate();
	}

	/**
	* Returns the c shipping fixed option rel ID of this c shipping fixed option rel.
	*
	* @return the c shipping fixed option rel ID of this c shipping fixed option rel
	*/
	@Override
	public long getCShippingFixedOptionRelId() {
		return _cShippingFixedOptionRel.getCShippingFixedOptionRelId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cShippingFixedOptionRel.getExpandoBridge();
	}

	/**
	* Returns the fixed price of this c shipping fixed option rel.
	*
	* @return the fixed price of this c shipping fixed option rel
	*/
	@Override
	public double getFixedPrice() {
		return _cShippingFixedOptionRel.getFixedPrice();
	}

	/**
	* Returns the group ID of this c shipping fixed option rel.
	*
	* @return the group ID of this c shipping fixed option rel
	*/
	@Override
	public long getGroupId() {
		return _cShippingFixedOptionRel.getGroupId();
	}

	/**
	* Returns the modified date of this c shipping fixed option rel.
	*
	* @return the modified date of this c shipping fixed option rel
	*/
	@Override
	public Date getModifiedDate() {
		return _cShippingFixedOptionRel.getModifiedDate();
	}

	/**
	* Returns the primary key of this c shipping fixed option rel.
	*
	* @return the primary key of this c shipping fixed option rel
	*/
	@Override
	public long getPrimaryKey() {
		return _cShippingFixedOptionRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cShippingFixedOptionRel.getPrimaryKeyObj();
	}

	/**
	* Returns the rate percentage of this c shipping fixed option rel.
	*
	* @return the rate percentage of this c shipping fixed option rel
	*/
	@Override
	public double getRatePercentage() {
		return _cShippingFixedOptionRel.getRatePercentage();
	}

	/**
	* Returns the rate unit weight price of this c shipping fixed option rel.
	*
	* @return the rate unit weight price of this c shipping fixed option rel
	*/
	@Override
	public double getRateUnitWeightPrice() {
		return _cShippingFixedOptionRel.getRateUnitWeightPrice();
	}

	/**
	* Returns the user ID of this c shipping fixed option rel.
	*
	* @return the user ID of this c shipping fixed option rel
	*/
	@Override
	public long getUserId() {
		return _cShippingFixedOptionRel.getUserId();
	}

	/**
	* Returns the user name of this c shipping fixed option rel.
	*
	* @return the user name of this c shipping fixed option rel
	*/
	@Override
	public java.lang.String getUserName() {
		return _cShippingFixedOptionRel.getUserName();
	}

	/**
	* Returns the user uuid of this c shipping fixed option rel.
	*
	* @return the user uuid of this c shipping fixed option rel
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _cShippingFixedOptionRel.getUserUuid();
	}

	/**
	* Returns the weight from of this c shipping fixed option rel.
	*
	* @return the weight from of this c shipping fixed option rel
	*/
	@Override
	public double getWeightFrom() {
		return _cShippingFixedOptionRel.getWeightFrom();
	}

	/**
	* Returns the weight to of this c shipping fixed option rel.
	*
	* @return the weight to of this c shipping fixed option rel
	*/
	@Override
	public double getWeightTo() {
		return _cShippingFixedOptionRel.getWeightTo();
	}

	/**
	* Returns the zip of this c shipping fixed option rel.
	*
	* @return the zip of this c shipping fixed option rel
	*/
	@Override
	public java.lang.String getZip() {
		return _cShippingFixedOptionRel.getZip();
	}

	@Override
	public int hashCode() {
		return _cShippingFixedOptionRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _cShippingFixedOptionRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cShippingFixedOptionRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _cShippingFixedOptionRel.isNew();
	}

	@Override
	public void persist() {
		_cShippingFixedOptionRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cShippingFixedOptionRel.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce country ID of this c shipping fixed option rel.
	*
	* @param commerceCountryId the commerce country ID of this c shipping fixed option rel
	*/
	@Override
	public void setCommerceCountryId(long commerceCountryId) {
		_cShippingFixedOptionRel.setCommerceCountryId(commerceCountryId);
	}

	/**
	* Sets the commerce region ID of this c shipping fixed option rel.
	*
	* @param commerceRegionId the commerce region ID of this c shipping fixed option rel
	*/
	@Override
	public void setCommerceRegionId(long commerceRegionId) {
		_cShippingFixedOptionRel.setCommerceRegionId(commerceRegionId);
	}

	/**
	* Sets the commerce shipping fixed option ID of this c shipping fixed option rel.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID of this c shipping fixed option rel
	*/
	@Override
	public void setCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId) {
		_cShippingFixedOptionRel.setCommerceShippingFixedOptionId(commerceShippingFixedOptionId);
	}

	/**
	* Sets the commerce shipping method ID of this c shipping fixed option rel.
	*
	* @param commerceShippingMethodId the commerce shipping method ID of this c shipping fixed option rel
	*/
	@Override
	public void setCommerceShippingMethodId(long commerceShippingMethodId) {
		_cShippingFixedOptionRel.setCommerceShippingMethodId(commerceShippingMethodId);
	}

	/**
	* Sets the commerce warehouse ID of this c shipping fixed option rel.
	*
	* @param commerceWarehouseId the commerce warehouse ID of this c shipping fixed option rel
	*/
	@Override
	public void setCommerceWarehouseId(long commerceWarehouseId) {
		_cShippingFixedOptionRel.setCommerceWarehouseId(commerceWarehouseId);
	}

	/**
	* Sets the company ID of this c shipping fixed option rel.
	*
	* @param companyId the company ID of this c shipping fixed option rel
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cShippingFixedOptionRel.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this c shipping fixed option rel.
	*
	* @param createDate the create date of this c shipping fixed option rel
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cShippingFixedOptionRel.setCreateDate(createDate);
	}

	/**
	* Sets the c shipping fixed option rel ID of this c shipping fixed option rel.
	*
	* @param CShippingFixedOptionRelId the c shipping fixed option rel ID of this c shipping fixed option rel
	*/
	@Override
	public void setCShippingFixedOptionRelId(long CShippingFixedOptionRelId) {
		_cShippingFixedOptionRel.setCShippingFixedOptionRelId(CShippingFixedOptionRelId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cShippingFixedOptionRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cShippingFixedOptionRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cShippingFixedOptionRel.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the fixed price of this c shipping fixed option rel.
	*
	* @param fixedPrice the fixed price of this c shipping fixed option rel
	*/
	@Override
	public void setFixedPrice(double fixedPrice) {
		_cShippingFixedOptionRel.setFixedPrice(fixedPrice);
	}

	/**
	* Sets the group ID of this c shipping fixed option rel.
	*
	* @param groupId the group ID of this c shipping fixed option rel
	*/
	@Override
	public void setGroupId(long groupId) {
		_cShippingFixedOptionRel.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this c shipping fixed option rel.
	*
	* @param modifiedDate the modified date of this c shipping fixed option rel
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cShippingFixedOptionRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_cShippingFixedOptionRel.setNew(n);
	}

	/**
	* Sets the primary key of this c shipping fixed option rel.
	*
	* @param primaryKey the primary key of this c shipping fixed option rel
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cShippingFixedOptionRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cShippingFixedOptionRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the rate percentage of this c shipping fixed option rel.
	*
	* @param ratePercentage the rate percentage of this c shipping fixed option rel
	*/
	@Override
	public void setRatePercentage(double ratePercentage) {
		_cShippingFixedOptionRel.setRatePercentage(ratePercentage);
	}

	/**
	* Sets the rate unit weight price of this c shipping fixed option rel.
	*
	* @param rateUnitWeightPrice the rate unit weight price of this c shipping fixed option rel
	*/
	@Override
	public void setRateUnitWeightPrice(double rateUnitWeightPrice) {
		_cShippingFixedOptionRel.setRateUnitWeightPrice(rateUnitWeightPrice);
	}

	/**
	* Sets the user ID of this c shipping fixed option rel.
	*
	* @param userId the user ID of this c shipping fixed option rel
	*/
	@Override
	public void setUserId(long userId) {
		_cShippingFixedOptionRel.setUserId(userId);
	}

	/**
	* Sets the user name of this c shipping fixed option rel.
	*
	* @param userName the user name of this c shipping fixed option rel
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_cShippingFixedOptionRel.setUserName(userName);
	}

	/**
	* Sets the user uuid of this c shipping fixed option rel.
	*
	* @param userUuid the user uuid of this c shipping fixed option rel
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_cShippingFixedOptionRel.setUserUuid(userUuid);
	}

	/**
	* Sets the weight from of this c shipping fixed option rel.
	*
	* @param weightFrom the weight from of this c shipping fixed option rel
	*/
	@Override
	public void setWeightFrom(double weightFrom) {
		_cShippingFixedOptionRel.setWeightFrom(weightFrom);
	}

	/**
	* Sets the weight to of this c shipping fixed option rel.
	*
	* @param weightTo the weight to of this c shipping fixed option rel
	*/
	@Override
	public void setWeightTo(double weightTo) {
		_cShippingFixedOptionRel.setWeightTo(weightTo);
	}

	/**
	* Sets the zip of this c shipping fixed option rel.
	*
	* @param zip the zip of this c shipping fixed option rel
	*/
	@Override
	public void setZip(java.lang.String zip) {
		_cShippingFixedOptionRel.setZip(zip);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CShippingFixedOptionRel> toCacheModel() {
		return _cShippingFixedOptionRel.toCacheModel();
	}

	@Override
	public CShippingFixedOptionRel toEscapedModel() {
		return new CShippingFixedOptionRelWrapper(_cShippingFixedOptionRel.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _cShippingFixedOptionRel.toString();
	}

	@Override
	public CShippingFixedOptionRel toUnescapedModel() {
		return new CShippingFixedOptionRelWrapper(_cShippingFixedOptionRel.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _cShippingFixedOptionRel.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CShippingFixedOptionRelWrapper)) {
			return false;
		}

		CShippingFixedOptionRelWrapper cShippingFixedOptionRelWrapper = (CShippingFixedOptionRelWrapper)obj;

		if (Objects.equals(_cShippingFixedOptionRel,
					cShippingFixedOptionRelWrapper._cShippingFixedOptionRel)) {
			return true;
		}

		return false;
	}

	@Override
	public CShippingFixedOptionRel getWrappedModel() {
		return _cShippingFixedOptionRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cShippingFixedOptionRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cShippingFixedOptionRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cShippingFixedOptionRel.resetOriginalValues();
	}

	private final CShippingFixedOptionRel _cShippingFixedOptionRel;
}