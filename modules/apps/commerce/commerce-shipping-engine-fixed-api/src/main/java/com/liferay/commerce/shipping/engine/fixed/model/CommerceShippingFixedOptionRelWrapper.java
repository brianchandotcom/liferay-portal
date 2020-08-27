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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.math.BigDecimal;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceShippingFixedOptionRel}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionRel
 * @generated
 */
public class CommerceShippingFixedOptionRelWrapper
	extends BaseModelWrapper<CommerceShippingFixedOptionRel>
	implements CommerceShippingFixedOptionRel,
			   ModelWrapper<CommerceShippingFixedOptionRel> {

	public CommerceShippingFixedOptionRelWrapper(
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel) {

		super(commerceShippingFixedOptionRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"commerceShippingFixedOptionRelId",
			getCommerceShippingFixedOptionRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put(
			"commerceShippingMethodId", getCommerceShippingMethodId());
		attributes.put(
			"commerceShippingFixedOptionId",
			getCommerceShippingFixedOptionId());
		attributes.put(
			"commerceInventoryWarehouseId", getCommerceInventoryWarehouseId());
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
			setCommerceShippingFixedOptionRelId(
				commerceShippingFixedOptionRelId);
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

		Long commerceInventoryWarehouseId = (Long)attributes.get(
			"commerceInventoryWarehouseId");

		if (commerceInventoryWarehouseId != null) {
			setCommerceInventoryWarehouseId(commerceInventoryWarehouseId);
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
	public com.liferay.commerce.model.CommerceCountry getCommerceCountry()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceCountry();
	}

	/**
	 * Returns the commerce country ID of this commerce shipping fixed option rel.
	 *
	 * @return the commerce country ID of this commerce shipping fixed option rel
	 */
	@Override
	public long getCommerceCountryId() {
		return model.getCommerceCountryId();
	}

	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouse
			getCommerceInventoryWarehouse()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceInventoryWarehouse();
	}

	/**
	 * Returns the commerce inventory warehouse ID of this commerce shipping fixed option rel.
	 *
	 * @return the commerce inventory warehouse ID of this commerce shipping fixed option rel
	 */
	@Override
	public long getCommerceInventoryWarehouseId() {
		return model.getCommerceInventoryWarehouseId();
	}

	@Override
	public com.liferay.commerce.model.CommerceRegion getCommerceRegion()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceRegion();
	}

	/**
	 * Returns the commerce region ID of this commerce shipping fixed option rel.
	 *
	 * @return the commerce region ID of this commerce shipping fixed option rel
	 */
	@Override
	public long getCommerceRegionId() {
		return model.getCommerceRegionId();
	}

	@Override
	public CommerceShippingFixedOption getCommerceShippingFixedOption()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceShippingFixedOption();
	}

	/**
	 * Returns the commerce shipping fixed option ID of this commerce shipping fixed option rel.
	 *
	 * @return the commerce shipping fixed option ID of this commerce shipping fixed option rel
	 */
	@Override
	public long getCommerceShippingFixedOptionId() {
		return model.getCommerceShippingFixedOptionId();
	}

	/**
	 * Returns the commerce shipping fixed option rel ID of this commerce shipping fixed option rel.
	 *
	 * @return the commerce shipping fixed option rel ID of this commerce shipping fixed option rel
	 */
	@Override
	public long getCommerceShippingFixedOptionRelId() {
		return model.getCommerceShippingFixedOptionRelId();
	}

	@Override
	public com.liferay.commerce.model.CommerceShippingMethod
			getCommerceShippingMethod()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceShippingMethod();
	}

	/**
	 * Returns the commerce shipping method ID of this commerce shipping fixed option rel.
	 *
	 * @return the commerce shipping method ID of this commerce shipping fixed option rel
	 */
	@Override
	public long getCommerceShippingMethodId() {
		return model.getCommerceShippingMethodId();
	}

	/**
	 * Returns the company ID of this commerce shipping fixed option rel.
	 *
	 * @return the company ID of this commerce shipping fixed option rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce shipping fixed option rel.
	 *
	 * @return the create date of this commerce shipping fixed option rel
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the fixed price of this commerce shipping fixed option rel.
	 *
	 * @return the fixed price of this commerce shipping fixed option rel
	 */
	@Override
	public BigDecimal getFixedPrice() {
		return model.getFixedPrice();
	}

	/**
	 * Returns the group ID of this commerce shipping fixed option rel.
	 *
	 * @return the group ID of this commerce shipping fixed option rel
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this commerce shipping fixed option rel.
	 *
	 * @return the modified date of this commerce shipping fixed option rel
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this commerce shipping fixed option rel.
	 *
	 * @return the primary key of this commerce shipping fixed option rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the rate percentage of this commerce shipping fixed option rel.
	 *
	 * @return the rate percentage of this commerce shipping fixed option rel
	 */
	@Override
	public double getRatePercentage() {
		return model.getRatePercentage();
	}

	/**
	 * Returns the rate unit weight price of this commerce shipping fixed option rel.
	 *
	 * @return the rate unit weight price of this commerce shipping fixed option rel
	 */
	@Override
	public BigDecimal getRateUnitWeightPrice() {
		return model.getRateUnitWeightPrice();
	}

	/**
	 * Returns the user ID of this commerce shipping fixed option rel.
	 *
	 * @return the user ID of this commerce shipping fixed option rel
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce shipping fixed option rel.
	 *
	 * @return the user name of this commerce shipping fixed option rel
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce shipping fixed option rel.
	 *
	 * @return the user uuid of this commerce shipping fixed option rel
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the weight from of this commerce shipping fixed option rel.
	 *
	 * @return the weight from of this commerce shipping fixed option rel
	 */
	@Override
	public double getWeightFrom() {
		return model.getWeightFrom();
	}

	/**
	 * Returns the weight to of this commerce shipping fixed option rel.
	 *
	 * @return the weight to of this commerce shipping fixed option rel
	 */
	@Override
	public double getWeightTo() {
		return model.getWeightTo();
	}

	/**
	 * Returns the zip of this commerce shipping fixed option rel.
	 *
	 * @return the zip of this commerce shipping fixed option rel
	 */
	@Override
	public String getZip() {
		return model.getZip();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the commerce country ID of this commerce shipping fixed option rel.
	 *
	 * @param commerceCountryId the commerce country ID of this commerce shipping fixed option rel
	 */
	@Override
	public void setCommerceCountryId(long commerceCountryId) {
		model.setCommerceCountryId(commerceCountryId);
	}

	/**
	 * Sets the commerce inventory warehouse ID of this commerce shipping fixed option rel.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID of this commerce shipping fixed option rel
	 */
	@Override
	public void setCommerceInventoryWarehouseId(
		long commerceInventoryWarehouseId) {

		model.setCommerceInventoryWarehouseId(commerceInventoryWarehouseId);
	}

	/**
	 * Sets the commerce region ID of this commerce shipping fixed option rel.
	 *
	 * @param commerceRegionId the commerce region ID of this commerce shipping fixed option rel
	 */
	@Override
	public void setCommerceRegionId(long commerceRegionId) {
		model.setCommerceRegionId(commerceRegionId);
	}

	/**
	 * Sets the commerce shipping fixed option ID of this commerce shipping fixed option rel.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID of this commerce shipping fixed option rel
	 */
	@Override
	public void setCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId) {

		model.setCommerceShippingFixedOptionId(commerceShippingFixedOptionId);
	}

	/**
	 * Sets the commerce shipping fixed option rel ID of this commerce shipping fixed option rel.
	 *
	 * @param commerceShippingFixedOptionRelId the commerce shipping fixed option rel ID of this commerce shipping fixed option rel
	 */
	@Override
	public void setCommerceShippingFixedOptionRelId(
		long commerceShippingFixedOptionRelId) {

		model.setCommerceShippingFixedOptionRelId(
			commerceShippingFixedOptionRelId);
	}

	/**
	 * Sets the commerce shipping method ID of this commerce shipping fixed option rel.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID of this commerce shipping fixed option rel
	 */
	@Override
	public void setCommerceShippingMethodId(long commerceShippingMethodId) {
		model.setCommerceShippingMethodId(commerceShippingMethodId);
	}

	/**
	 * Sets the company ID of this commerce shipping fixed option rel.
	 *
	 * @param companyId the company ID of this commerce shipping fixed option rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce shipping fixed option rel.
	 *
	 * @param createDate the create date of this commerce shipping fixed option rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the fixed price of this commerce shipping fixed option rel.
	 *
	 * @param fixedPrice the fixed price of this commerce shipping fixed option rel
	 */
	@Override
	public void setFixedPrice(BigDecimal fixedPrice) {
		model.setFixedPrice(fixedPrice);
	}

	/**
	 * Sets the group ID of this commerce shipping fixed option rel.
	 *
	 * @param groupId the group ID of this commerce shipping fixed option rel
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this commerce shipping fixed option rel.
	 *
	 * @param modifiedDate the modified date of this commerce shipping fixed option rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this commerce shipping fixed option rel.
	 *
	 * @param primaryKey the primary key of this commerce shipping fixed option rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the rate percentage of this commerce shipping fixed option rel.
	 *
	 * @param ratePercentage the rate percentage of this commerce shipping fixed option rel
	 */
	@Override
	public void setRatePercentage(double ratePercentage) {
		model.setRatePercentage(ratePercentage);
	}

	/**
	 * Sets the rate unit weight price of this commerce shipping fixed option rel.
	 *
	 * @param rateUnitWeightPrice the rate unit weight price of this commerce shipping fixed option rel
	 */
	@Override
	public void setRateUnitWeightPrice(BigDecimal rateUnitWeightPrice) {
		model.setRateUnitWeightPrice(rateUnitWeightPrice);
	}

	/**
	 * Sets the user ID of this commerce shipping fixed option rel.
	 *
	 * @param userId the user ID of this commerce shipping fixed option rel
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce shipping fixed option rel.
	 *
	 * @param userName the user name of this commerce shipping fixed option rel
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce shipping fixed option rel.
	 *
	 * @param userUuid the user uuid of this commerce shipping fixed option rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the weight from of this commerce shipping fixed option rel.
	 *
	 * @param weightFrom the weight from of this commerce shipping fixed option rel
	 */
	@Override
	public void setWeightFrom(double weightFrom) {
		model.setWeightFrom(weightFrom);
	}

	/**
	 * Sets the weight to of this commerce shipping fixed option rel.
	 *
	 * @param weightTo the weight to of this commerce shipping fixed option rel
	 */
	@Override
	public void setWeightTo(double weightTo) {
		model.setWeightTo(weightTo);
	}

	/**
	 * Sets the zip of this commerce shipping fixed option rel.
	 *
	 * @param zip the zip of this commerce shipping fixed option rel
	 */
	@Override
	public void setZip(String zip) {
		model.setZip(zip);
	}

	@Override
	protected CommerceShippingFixedOptionRelWrapper wrap(
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel) {

		return new CommerceShippingFixedOptionRelWrapper(
			commerceShippingFixedOptionRel);
	}

}