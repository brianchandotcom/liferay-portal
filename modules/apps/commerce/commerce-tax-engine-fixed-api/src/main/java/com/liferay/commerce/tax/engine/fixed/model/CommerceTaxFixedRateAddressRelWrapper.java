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

package com.liferay.commerce.tax.engine.fixed.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceTaxFixedRateAddressRel}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxFixedRateAddressRel
 * @generated
 */
public class CommerceTaxFixedRateAddressRelWrapper
	extends BaseModelWrapper<CommerceTaxFixedRateAddressRel>
	implements CommerceTaxFixedRateAddressRel,
			   ModelWrapper<CommerceTaxFixedRateAddressRel> {

	public CommerceTaxFixedRateAddressRelWrapper(
		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel) {

		super(commerceTaxFixedRateAddressRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"commerceTaxFixedRateAddressRelId",
			getCommerceTaxFixedRateAddressRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceTaxMethodId", getCommerceTaxMethodId());
		attributes.put("CPTaxCategoryId", getCPTaxCategoryId());
		attributes.put("commerceCountryId", getCommerceCountryId());
		attributes.put("commerceRegionId", getCommerceRegionId());
		attributes.put("zip", getZip());
		attributes.put("rate", getRate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceTaxFixedRateAddressRelId = (Long)attributes.get(
			"commerceTaxFixedRateAddressRelId");

		if (commerceTaxFixedRateAddressRelId != null) {
			setCommerceTaxFixedRateAddressRelId(
				commerceTaxFixedRateAddressRelId);
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

		Long commerceTaxMethodId = (Long)attributes.get("commerceTaxMethodId");

		if (commerceTaxMethodId != null) {
			setCommerceTaxMethodId(commerceTaxMethodId);
		}

		Long CPTaxCategoryId = (Long)attributes.get("CPTaxCategoryId");

		if (CPTaxCategoryId != null) {
			setCPTaxCategoryId(CPTaxCategoryId);
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

		Double rate = (Double)attributes.get("rate");

		if (rate != null) {
			setRate(rate);
		}
	}

	@Override
	public com.liferay.commerce.model.CommerceCountry getCommerceCountry()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceCountry();
	}

	/**
	 * Returns the commerce country ID of this commerce tax fixed rate address rel.
	 *
	 * @return the commerce country ID of this commerce tax fixed rate address rel
	 */
	@Override
	public long getCommerceCountryId() {
		return model.getCommerceCountryId();
	}

	@Override
	public com.liferay.commerce.model.CommerceRegion getCommerceRegion()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceRegion();
	}

	/**
	 * Returns the commerce region ID of this commerce tax fixed rate address rel.
	 *
	 * @return the commerce region ID of this commerce tax fixed rate address rel
	 */
	@Override
	public long getCommerceRegionId() {
		return model.getCommerceRegionId();
	}

	/**
	 * Returns the commerce tax fixed rate address rel ID of this commerce tax fixed rate address rel.
	 *
	 * @return the commerce tax fixed rate address rel ID of this commerce tax fixed rate address rel
	 */
	@Override
	public long getCommerceTaxFixedRateAddressRelId() {
		return model.getCommerceTaxFixedRateAddressRelId();
	}

	@Override
	public com.liferay.commerce.tax.model.CommerceTaxMethod
			getCommerceTaxMethod()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceTaxMethod();
	}

	/**
	 * Returns the commerce tax method ID of this commerce tax fixed rate address rel.
	 *
	 * @return the commerce tax method ID of this commerce tax fixed rate address rel
	 */
	@Override
	public long getCommerceTaxMethodId() {
		return model.getCommerceTaxMethodId();
	}

	/**
	 * Returns the company ID of this commerce tax fixed rate address rel.
	 *
	 * @return the company ID of this commerce tax fixed rate address rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	@Override
	public com.liferay.commerce.product.model.CPTaxCategory getCPTaxCategory()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCPTaxCategory();
	}

	/**
	 * Returns the cp tax category ID of this commerce tax fixed rate address rel.
	 *
	 * @return the cp tax category ID of this commerce tax fixed rate address rel
	 */
	@Override
	public long getCPTaxCategoryId() {
		return model.getCPTaxCategoryId();
	}

	/**
	 * Returns the create date of this commerce tax fixed rate address rel.
	 *
	 * @return the create date of this commerce tax fixed rate address rel
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this commerce tax fixed rate address rel.
	 *
	 * @return the group ID of this commerce tax fixed rate address rel
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this commerce tax fixed rate address rel.
	 *
	 * @return the modified date of this commerce tax fixed rate address rel
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this commerce tax fixed rate address rel.
	 *
	 * @return the primary key of this commerce tax fixed rate address rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the rate of this commerce tax fixed rate address rel.
	 *
	 * @return the rate of this commerce tax fixed rate address rel
	 */
	@Override
	public double getRate() {
		return model.getRate();
	}

	/**
	 * Returns the user ID of this commerce tax fixed rate address rel.
	 *
	 * @return the user ID of this commerce tax fixed rate address rel
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce tax fixed rate address rel.
	 *
	 * @return the user name of this commerce tax fixed rate address rel
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce tax fixed rate address rel.
	 *
	 * @return the user uuid of this commerce tax fixed rate address rel
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the zip of this commerce tax fixed rate address rel.
	 *
	 * @return the zip of this commerce tax fixed rate address rel
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
	 * Sets the commerce country ID of this commerce tax fixed rate address rel.
	 *
	 * @param commerceCountryId the commerce country ID of this commerce tax fixed rate address rel
	 */
	@Override
	public void setCommerceCountryId(long commerceCountryId) {
		model.setCommerceCountryId(commerceCountryId);
	}

	/**
	 * Sets the commerce region ID of this commerce tax fixed rate address rel.
	 *
	 * @param commerceRegionId the commerce region ID of this commerce tax fixed rate address rel
	 */
	@Override
	public void setCommerceRegionId(long commerceRegionId) {
		model.setCommerceRegionId(commerceRegionId);
	}

	/**
	 * Sets the commerce tax fixed rate address rel ID of this commerce tax fixed rate address rel.
	 *
	 * @param commerceTaxFixedRateAddressRelId the commerce tax fixed rate address rel ID of this commerce tax fixed rate address rel
	 */
	@Override
	public void setCommerceTaxFixedRateAddressRelId(
		long commerceTaxFixedRateAddressRelId) {

		model.setCommerceTaxFixedRateAddressRelId(
			commerceTaxFixedRateAddressRelId);
	}

	/**
	 * Sets the commerce tax method ID of this commerce tax fixed rate address rel.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID of this commerce tax fixed rate address rel
	 */
	@Override
	public void setCommerceTaxMethodId(long commerceTaxMethodId) {
		model.setCommerceTaxMethodId(commerceTaxMethodId);
	}

	/**
	 * Sets the company ID of this commerce tax fixed rate address rel.
	 *
	 * @param companyId the company ID of this commerce tax fixed rate address rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cp tax category ID of this commerce tax fixed rate address rel.
	 *
	 * @param CPTaxCategoryId the cp tax category ID of this commerce tax fixed rate address rel
	 */
	@Override
	public void setCPTaxCategoryId(long CPTaxCategoryId) {
		model.setCPTaxCategoryId(CPTaxCategoryId);
	}

	/**
	 * Sets the create date of this commerce tax fixed rate address rel.
	 *
	 * @param createDate the create date of this commerce tax fixed rate address rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this commerce tax fixed rate address rel.
	 *
	 * @param groupId the group ID of this commerce tax fixed rate address rel
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this commerce tax fixed rate address rel.
	 *
	 * @param modifiedDate the modified date of this commerce tax fixed rate address rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this commerce tax fixed rate address rel.
	 *
	 * @param primaryKey the primary key of this commerce tax fixed rate address rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the rate of this commerce tax fixed rate address rel.
	 *
	 * @param rate the rate of this commerce tax fixed rate address rel
	 */
	@Override
	public void setRate(double rate) {
		model.setRate(rate);
	}

	/**
	 * Sets the user ID of this commerce tax fixed rate address rel.
	 *
	 * @param userId the user ID of this commerce tax fixed rate address rel
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce tax fixed rate address rel.
	 *
	 * @param userName the user name of this commerce tax fixed rate address rel
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce tax fixed rate address rel.
	 *
	 * @param userUuid the user uuid of this commerce tax fixed rate address rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the zip of this commerce tax fixed rate address rel.
	 *
	 * @param zip the zip of this commerce tax fixed rate address rel
	 */
	@Override
	public void setZip(String zip) {
		model.setZip(zip);
	}

	@Override
	protected CommerceTaxFixedRateAddressRelWrapper wrap(
		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel) {

		return new CommerceTaxFixedRateAddressRelWrapper(
			commerceTaxFixedRateAddressRel);
	}

}