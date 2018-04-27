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
 * This class is a wrapper for {@link CommerceTaxFixedRate}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxFixedRate
 * @generated
 */
@ProviderType
public class CommerceTaxFixedRateWrapper implements CommerceTaxFixedRate,
	ModelWrapper<CommerceTaxFixedRate> {
	public CommerceTaxFixedRateWrapper(
		CommerceTaxFixedRate commerceTaxFixedRate) {
		_commerceTaxFixedRate = commerceTaxFixedRate;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceTaxFixedRate.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceTaxFixedRate.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceTaxFixedRateId", getCommerceTaxFixedRateId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CPTaxCategoryId", getCPTaxCategoryId());
		attributes.put("commerceTaxMethodId", getCommerceTaxMethodId());
		attributes.put("rate", getRate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceTaxFixedRateId = (Long)attributes.get(
				"commerceTaxFixedRateId");

		if (commerceTaxFixedRateId != null) {
			setCommerceTaxFixedRateId(commerceTaxFixedRateId);
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

		Long CPTaxCategoryId = (Long)attributes.get("CPTaxCategoryId");

		if (CPTaxCategoryId != null) {
			setCPTaxCategoryId(CPTaxCategoryId);
		}

		Long commerceTaxMethodId = (Long)attributes.get("commerceTaxMethodId");

		if (commerceTaxMethodId != null) {
			setCommerceTaxMethodId(commerceTaxMethodId);
		}

		Double rate = (Double)attributes.get("rate");

		if (rate != null) {
			setRate(rate);
		}
	}

	@Override
	public Object clone() {
		return new CommerceTaxFixedRateWrapper((CommerceTaxFixedRate)_commerceTaxFixedRate.clone());
	}

	@Override
	public int compareTo(CommerceTaxFixedRate commerceTaxFixedRate) {
		return _commerceTaxFixedRate.compareTo(commerceTaxFixedRate);
	}

	/**
	* Returns the commerce tax fixed rate ID of this commerce tax fixed rate.
	*
	* @return the commerce tax fixed rate ID of this commerce tax fixed rate
	*/
	@Override
	public long getCommerceTaxFixedRateId() {
		return _commerceTaxFixedRate.getCommerceTaxFixedRateId();
	}

	/**
	* Returns the commerce tax method ID of this commerce tax fixed rate.
	*
	* @return the commerce tax method ID of this commerce tax fixed rate
	*/
	@Override
	public long getCommerceTaxMethodId() {
		return _commerceTaxFixedRate.getCommerceTaxMethodId();
	}

	/**
	* Returns the company ID of this commerce tax fixed rate.
	*
	* @return the company ID of this commerce tax fixed rate
	*/
	@Override
	public long getCompanyId() {
		return _commerceTaxFixedRate.getCompanyId();
	}

	@Override
	public com.liferay.commerce.product.model.CPTaxCategory getCPTaxCategory()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxFixedRate.getCPTaxCategory();
	}

	/**
	* Returns the cp tax category ID of this commerce tax fixed rate.
	*
	* @return the cp tax category ID of this commerce tax fixed rate
	*/
	@Override
	public long getCPTaxCategoryId() {
		return _commerceTaxFixedRate.getCPTaxCategoryId();
	}

	/**
	* Returns the create date of this commerce tax fixed rate.
	*
	* @return the create date of this commerce tax fixed rate
	*/
	@Override
	public Date getCreateDate() {
		return _commerceTaxFixedRate.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceTaxFixedRate.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce tax fixed rate.
	*
	* @return the group ID of this commerce tax fixed rate
	*/
	@Override
	public long getGroupId() {
		return _commerceTaxFixedRate.getGroupId();
	}

	/**
	* Returns the modified date of this commerce tax fixed rate.
	*
	* @return the modified date of this commerce tax fixed rate
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceTaxFixedRate.getModifiedDate();
	}

	/**
	* Returns the primary key of this commerce tax fixed rate.
	*
	* @return the primary key of this commerce tax fixed rate
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceTaxFixedRate.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceTaxFixedRate.getPrimaryKeyObj();
	}

	/**
	* Returns the rate of this commerce tax fixed rate.
	*
	* @return the rate of this commerce tax fixed rate
	*/
	@Override
	public double getRate() {
		return _commerceTaxFixedRate.getRate();
	}

	/**
	* Returns the user ID of this commerce tax fixed rate.
	*
	* @return the user ID of this commerce tax fixed rate
	*/
	@Override
	public long getUserId() {
		return _commerceTaxFixedRate.getUserId();
	}

	/**
	* Returns the user name of this commerce tax fixed rate.
	*
	* @return the user name of this commerce tax fixed rate
	*/
	@Override
	public String getUserName() {
		return _commerceTaxFixedRate.getUserName();
	}

	/**
	* Returns the user uuid of this commerce tax fixed rate.
	*
	* @return the user uuid of this commerce tax fixed rate
	*/
	@Override
	public String getUserUuid() {
		return _commerceTaxFixedRate.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceTaxFixedRate.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceTaxFixedRate.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceTaxFixedRate.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceTaxFixedRate.isNew();
	}

	@Override
	public void persist() {
		_commerceTaxFixedRate.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceTaxFixedRate.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce tax fixed rate ID of this commerce tax fixed rate.
	*
	* @param commerceTaxFixedRateId the commerce tax fixed rate ID of this commerce tax fixed rate
	*/
	@Override
	public void setCommerceTaxFixedRateId(long commerceTaxFixedRateId) {
		_commerceTaxFixedRate.setCommerceTaxFixedRateId(commerceTaxFixedRateId);
	}

	/**
	* Sets the commerce tax method ID of this commerce tax fixed rate.
	*
	* @param commerceTaxMethodId the commerce tax method ID of this commerce tax fixed rate
	*/
	@Override
	public void setCommerceTaxMethodId(long commerceTaxMethodId) {
		_commerceTaxFixedRate.setCommerceTaxMethodId(commerceTaxMethodId);
	}

	/**
	* Sets the company ID of this commerce tax fixed rate.
	*
	* @param companyId the company ID of this commerce tax fixed rate
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceTaxFixedRate.setCompanyId(companyId);
	}

	/**
	* Sets the cp tax category ID of this commerce tax fixed rate.
	*
	* @param CPTaxCategoryId the cp tax category ID of this commerce tax fixed rate
	*/
	@Override
	public void setCPTaxCategoryId(long CPTaxCategoryId) {
		_commerceTaxFixedRate.setCPTaxCategoryId(CPTaxCategoryId);
	}

	/**
	* Sets the create date of this commerce tax fixed rate.
	*
	* @param createDate the create date of this commerce tax fixed rate
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceTaxFixedRate.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceTaxFixedRate.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceTaxFixedRate.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceTaxFixedRate.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce tax fixed rate.
	*
	* @param groupId the group ID of this commerce tax fixed rate
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceTaxFixedRate.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce tax fixed rate.
	*
	* @param modifiedDate the modified date of this commerce tax fixed rate
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceTaxFixedRate.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceTaxFixedRate.setNew(n);
	}

	/**
	* Sets the primary key of this commerce tax fixed rate.
	*
	* @param primaryKey the primary key of this commerce tax fixed rate
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceTaxFixedRate.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceTaxFixedRate.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the rate of this commerce tax fixed rate.
	*
	* @param rate the rate of this commerce tax fixed rate
	*/
	@Override
	public void setRate(double rate) {
		_commerceTaxFixedRate.setRate(rate);
	}

	/**
	* Sets the user ID of this commerce tax fixed rate.
	*
	* @param userId the user ID of this commerce tax fixed rate
	*/
	@Override
	public void setUserId(long userId) {
		_commerceTaxFixedRate.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce tax fixed rate.
	*
	* @param userName the user name of this commerce tax fixed rate
	*/
	@Override
	public void setUserName(String userName) {
		_commerceTaxFixedRate.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce tax fixed rate.
	*
	* @param userUuid the user uuid of this commerce tax fixed rate
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_commerceTaxFixedRate.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceTaxFixedRate> toCacheModel() {
		return _commerceTaxFixedRate.toCacheModel();
	}

	@Override
	public CommerceTaxFixedRate toEscapedModel() {
		return new CommerceTaxFixedRateWrapper(_commerceTaxFixedRate.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceTaxFixedRate.toString();
	}

	@Override
	public CommerceTaxFixedRate toUnescapedModel() {
		return new CommerceTaxFixedRateWrapper(_commerceTaxFixedRate.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceTaxFixedRate.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceTaxFixedRateWrapper)) {
			return false;
		}

		CommerceTaxFixedRateWrapper commerceTaxFixedRateWrapper = (CommerceTaxFixedRateWrapper)obj;

		if (Objects.equals(_commerceTaxFixedRate,
					commerceTaxFixedRateWrapper._commerceTaxFixedRate)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceTaxFixedRate getWrappedModel() {
		return _commerceTaxFixedRate;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceTaxFixedRate.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceTaxFixedRate.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceTaxFixedRate.resetOriginalValues();
	}

	private final CommerceTaxFixedRate _commerceTaxFixedRate;
}