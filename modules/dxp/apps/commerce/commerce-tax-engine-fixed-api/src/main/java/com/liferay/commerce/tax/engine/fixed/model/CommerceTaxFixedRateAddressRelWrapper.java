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
 * This class is a wrapper for {@link CommerceTaxFixedRateAddressRel}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxFixedRateAddressRel
 * @generated
 */
@ProviderType
public class CommerceTaxFixedRateAddressRelWrapper
	implements CommerceTaxFixedRateAddressRel,
		ModelWrapper<CommerceTaxFixedRateAddressRel> {
	public CommerceTaxFixedRateAddressRelWrapper(
		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel) {
		_commerceTaxFixedRateAddressRel = commerceTaxFixedRateAddressRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceTaxFixedRateAddressRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceTaxFixedRateAddressRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceTaxFixedRateAddressRelId",
			getCommerceTaxFixedRateAddressRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceTaxMethodId", getCommerceTaxMethodId());
		attributes.put("commerceTaxFixedRateId", getCommerceTaxFixedRateId());
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
			setCommerceTaxFixedRateAddressRelId(commerceTaxFixedRateAddressRelId);
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

		Long commerceTaxFixedRateId = (Long)attributes.get(
				"commerceTaxFixedRateId");

		if (commerceTaxFixedRateId != null) {
			setCommerceTaxFixedRateId(commerceTaxFixedRateId);
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
	public java.lang.Object clone() {
		return new CommerceTaxFixedRateAddressRelWrapper((CommerceTaxFixedRateAddressRel)_commerceTaxFixedRateAddressRel.clone());
	}

	@Override
	public int compareTo(
		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel) {
		return _commerceTaxFixedRateAddressRel.compareTo(commerceTaxFixedRateAddressRel);
	}

	@Override
	public com.liferay.commerce.model.CommerceCountry getCommerceCountry()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxFixedRateAddressRel.getCommerceCountry();
	}

	/**
	* Returns the commerce country ID of this commerce tax fixed rate address rel.
	*
	* @return the commerce country ID of this commerce tax fixed rate address rel
	*/
	@Override
	public long getCommerceCountryId() {
		return _commerceTaxFixedRateAddressRel.getCommerceCountryId();
	}

	@Override
	public com.liferay.commerce.model.CommerceRegion getCommerceRegion()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxFixedRateAddressRel.getCommerceRegion();
	}

	/**
	* Returns the commerce region ID of this commerce tax fixed rate address rel.
	*
	* @return the commerce region ID of this commerce tax fixed rate address rel
	*/
	@Override
	public long getCommerceRegionId() {
		return _commerceTaxFixedRateAddressRel.getCommerceRegionId();
	}

	@Override
	public CommerceTaxFixedRate getCommerceTaxFixedRate()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxFixedRateAddressRel.getCommerceTaxFixedRate();
	}

	/**
	* Returns the commerce tax fixed rate address rel ID of this commerce tax fixed rate address rel.
	*
	* @return the commerce tax fixed rate address rel ID of this commerce tax fixed rate address rel
	*/
	@Override
	public long getCommerceTaxFixedRateAddressRelId() {
		return _commerceTaxFixedRateAddressRel.getCommerceTaxFixedRateAddressRelId();
	}

	/**
	* Returns the commerce tax fixed rate ID of this commerce tax fixed rate address rel.
	*
	* @return the commerce tax fixed rate ID of this commerce tax fixed rate address rel
	*/
	@Override
	public long getCommerceTaxFixedRateId() {
		return _commerceTaxFixedRateAddressRel.getCommerceTaxFixedRateId();
	}

	@Override
	public com.liferay.commerce.model.CommerceTaxMethod getCommerceTaxMethod()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxFixedRateAddressRel.getCommerceTaxMethod();
	}

	/**
	* Returns the commerce tax method ID of this commerce tax fixed rate address rel.
	*
	* @return the commerce tax method ID of this commerce tax fixed rate address rel
	*/
	@Override
	public long getCommerceTaxMethodId() {
		return _commerceTaxFixedRateAddressRel.getCommerceTaxMethodId();
	}

	/**
	* Returns the company ID of this commerce tax fixed rate address rel.
	*
	* @return the company ID of this commerce tax fixed rate address rel
	*/
	@Override
	public long getCompanyId() {
		return _commerceTaxFixedRateAddressRel.getCompanyId();
	}

	/**
	* Returns the create date of this commerce tax fixed rate address rel.
	*
	* @return the create date of this commerce tax fixed rate address rel
	*/
	@Override
	public Date getCreateDate() {
		return _commerceTaxFixedRateAddressRel.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceTaxFixedRateAddressRel.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce tax fixed rate address rel.
	*
	* @return the group ID of this commerce tax fixed rate address rel
	*/
	@Override
	public long getGroupId() {
		return _commerceTaxFixedRateAddressRel.getGroupId();
	}

	/**
	* Returns the modified date of this commerce tax fixed rate address rel.
	*
	* @return the modified date of this commerce tax fixed rate address rel
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceTaxFixedRateAddressRel.getModifiedDate();
	}

	/**
	* Returns the primary key of this commerce tax fixed rate address rel.
	*
	* @return the primary key of this commerce tax fixed rate address rel
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceTaxFixedRateAddressRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceTaxFixedRateAddressRel.getPrimaryKeyObj();
	}

	/**
	* Returns the rate of this commerce tax fixed rate address rel.
	*
	* @return the rate of this commerce tax fixed rate address rel
	*/
	@Override
	public double getRate() {
		return _commerceTaxFixedRateAddressRel.getRate();
	}

	/**
	* Returns the user ID of this commerce tax fixed rate address rel.
	*
	* @return the user ID of this commerce tax fixed rate address rel
	*/
	@Override
	public long getUserId() {
		return _commerceTaxFixedRateAddressRel.getUserId();
	}

	/**
	* Returns the user name of this commerce tax fixed rate address rel.
	*
	* @return the user name of this commerce tax fixed rate address rel
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceTaxFixedRateAddressRel.getUserName();
	}

	/**
	* Returns the user uuid of this commerce tax fixed rate address rel.
	*
	* @return the user uuid of this commerce tax fixed rate address rel
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceTaxFixedRateAddressRel.getUserUuid();
	}

	/**
	* Returns the zip of this commerce tax fixed rate address rel.
	*
	* @return the zip of this commerce tax fixed rate address rel
	*/
	@Override
	public java.lang.String getZip() {
		return _commerceTaxFixedRateAddressRel.getZip();
	}

	@Override
	public int hashCode() {
		return _commerceTaxFixedRateAddressRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceTaxFixedRateAddressRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceTaxFixedRateAddressRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceTaxFixedRateAddressRel.isNew();
	}

	@Override
	public void persist() {
		_commerceTaxFixedRateAddressRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceTaxFixedRateAddressRel.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce country ID of this commerce tax fixed rate address rel.
	*
	* @param commerceCountryId the commerce country ID of this commerce tax fixed rate address rel
	*/
	@Override
	public void setCommerceCountryId(long commerceCountryId) {
		_commerceTaxFixedRateAddressRel.setCommerceCountryId(commerceCountryId);
	}

	/**
	* Sets the commerce region ID of this commerce tax fixed rate address rel.
	*
	* @param commerceRegionId the commerce region ID of this commerce tax fixed rate address rel
	*/
	@Override
	public void setCommerceRegionId(long commerceRegionId) {
		_commerceTaxFixedRateAddressRel.setCommerceRegionId(commerceRegionId);
	}

	/**
	* Sets the commerce tax fixed rate address rel ID of this commerce tax fixed rate address rel.
	*
	* @param commerceTaxFixedRateAddressRelId the commerce tax fixed rate address rel ID of this commerce tax fixed rate address rel
	*/
	@Override
	public void setCommerceTaxFixedRateAddressRelId(
		long commerceTaxFixedRateAddressRelId) {
		_commerceTaxFixedRateAddressRel.setCommerceTaxFixedRateAddressRelId(commerceTaxFixedRateAddressRelId);
	}

	/**
	* Sets the commerce tax fixed rate ID of this commerce tax fixed rate address rel.
	*
	* @param commerceTaxFixedRateId the commerce tax fixed rate ID of this commerce tax fixed rate address rel
	*/
	@Override
	public void setCommerceTaxFixedRateId(long commerceTaxFixedRateId) {
		_commerceTaxFixedRateAddressRel.setCommerceTaxFixedRateId(commerceTaxFixedRateId);
	}

	/**
	* Sets the commerce tax method ID of this commerce tax fixed rate address rel.
	*
	* @param commerceTaxMethodId the commerce tax method ID of this commerce tax fixed rate address rel
	*/
	@Override
	public void setCommerceTaxMethodId(long commerceTaxMethodId) {
		_commerceTaxFixedRateAddressRel.setCommerceTaxMethodId(commerceTaxMethodId);
	}

	/**
	* Sets the company ID of this commerce tax fixed rate address rel.
	*
	* @param companyId the company ID of this commerce tax fixed rate address rel
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceTaxFixedRateAddressRel.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce tax fixed rate address rel.
	*
	* @param createDate the create date of this commerce tax fixed rate address rel
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceTaxFixedRateAddressRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceTaxFixedRateAddressRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceTaxFixedRateAddressRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceTaxFixedRateAddressRel.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce tax fixed rate address rel.
	*
	* @param groupId the group ID of this commerce tax fixed rate address rel
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceTaxFixedRateAddressRel.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce tax fixed rate address rel.
	*
	* @param modifiedDate the modified date of this commerce tax fixed rate address rel
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceTaxFixedRateAddressRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceTaxFixedRateAddressRel.setNew(n);
	}

	/**
	* Sets the primary key of this commerce tax fixed rate address rel.
	*
	* @param primaryKey the primary key of this commerce tax fixed rate address rel
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceTaxFixedRateAddressRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceTaxFixedRateAddressRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the rate of this commerce tax fixed rate address rel.
	*
	* @param rate the rate of this commerce tax fixed rate address rel
	*/
	@Override
	public void setRate(double rate) {
		_commerceTaxFixedRateAddressRel.setRate(rate);
	}

	/**
	* Sets the user ID of this commerce tax fixed rate address rel.
	*
	* @param userId the user ID of this commerce tax fixed rate address rel
	*/
	@Override
	public void setUserId(long userId) {
		_commerceTaxFixedRateAddressRel.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce tax fixed rate address rel.
	*
	* @param userName the user name of this commerce tax fixed rate address rel
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceTaxFixedRateAddressRel.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce tax fixed rate address rel.
	*
	* @param userUuid the user uuid of this commerce tax fixed rate address rel
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceTaxFixedRateAddressRel.setUserUuid(userUuid);
	}

	/**
	* Sets the zip of this commerce tax fixed rate address rel.
	*
	* @param zip the zip of this commerce tax fixed rate address rel
	*/
	@Override
	public void setZip(java.lang.String zip) {
		_commerceTaxFixedRateAddressRel.setZip(zip);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceTaxFixedRateAddressRel> toCacheModel() {
		return _commerceTaxFixedRateAddressRel.toCacheModel();
	}

	@Override
	public CommerceTaxFixedRateAddressRel toEscapedModel() {
		return new CommerceTaxFixedRateAddressRelWrapper(_commerceTaxFixedRateAddressRel.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _commerceTaxFixedRateAddressRel.toString();
	}

	@Override
	public CommerceTaxFixedRateAddressRel toUnescapedModel() {
		return new CommerceTaxFixedRateAddressRelWrapper(_commerceTaxFixedRateAddressRel.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceTaxFixedRateAddressRel.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceTaxFixedRateAddressRelWrapper)) {
			return false;
		}

		CommerceTaxFixedRateAddressRelWrapper commerceTaxFixedRateAddressRelWrapper =
			(CommerceTaxFixedRateAddressRelWrapper)obj;

		if (Objects.equals(_commerceTaxFixedRateAddressRel,
					commerceTaxFixedRateAddressRelWrapper._commerceTaxFixedRateAddressRel)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceTaxFixedRateAddressRel getWrappedModel() {
		return _commerceTaxFixedRateAddressRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceTaxFixedRateAddressRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceTaxFixedRateAddressRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceTaxFixedRateAddressRel.resetOriginalValues();
	}

	private final CommerceTaxFixedRateAddressRel _commerceTaxFixedRateAddressRel;
}