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

package com.liferay.commerce.forecast.model;

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
 * This class is a wrapper for {@link CommerceForecastEntry}.
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CommerceForecastEntry
 * @generated
 */
@ProviderType
public class CommerceForecastEntryWrapper implements CommerceForecastEntry,
	ModelWrapper<CommerceForecastEntry> {
	public CommerceForecastEntryWrapper(
		CommerceForecastEntry commerceForecastEntry) {
		_commerceForecastEntry = commerceForecastEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceForecastEntry.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceForecastEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceForecastEntryId", getCommerceForecastEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("time", getTime());
		attributes.put("period", getPeriod());
		attributes.put("target", getTarget());
		attributes.put("customerId", getCustomerId());
		attributes.put("sku", getSku());
		attributes.put("assertivity", getAssertivity());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceForecastEntryId = (Long)attributes.get(
				"commerceForecastEntryId");

		if (commerceForecastEntryId != null) {
			setCommerceForecastEntryId(commerceForecastEntryId);
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

		Long time = (Long)attributes.get("time");

		if (time != null) {
			setTime(time);
		}

		Integer period = (Integer)attributes.get("period");

		if (period != null) {
			setPeriod(period);
		}

		Integer target = (Integer)attributes.get("target");

		if (target != null) {
			setTarget(target);
		}

		Long customerId = (Long)attributes.get("customerId");

		if (customerId != null) {
			setCustomerId(customerId);
		}

		String sku = (String)attributes.get("sku");

		if (sku != null) {
			setSku(sku);
		}

		BigDecimal assertivity = (BigDecimal)attributes.get("assertivity");

		if (assertivity != null) {
			setAssertivity(assertivity);
		}
	}

	@Override
	public Object clone() {
		return new CommerceForecastEntryWrapper((CommerceForecastEntry)_commerceForecastEntry.clone());
	}

	@Override
	public int compareTo(CommerceForecastEntry commerceForecastEntry) {
		return _commerceForecastEntry.compareTo(commerceForecastEntry);
	}

	/**
	* Returns the assertivity of this commerce forecast entry.
	*
	* @return the assertivity of this commerce forecast entry
	*/
	@Override
	public BigDecimal getAssertivity() {
		return _commerceForecastEntry.getAssertivity();
	}

	/**
	* Returns the commerce forecast entry ID of this commerce forecast entry.
	*
	* @return the commerce forecast entry ID of this commerce forecast entry
	*/
	@Override
	public long getCommerceForecastEntryId() {
		return _commerceForecastEntry.getCommerceForecastEntryId();
	}

	/**
	* Returns the company ID of this commerce forecast entry.
	*
	* @return the company ID of this commerce forecast entry
	*/
	@Override
	public long getCompanyId() {
		return _commerceForecastEntry.getCompanyId();
	}

	/**
	* Returns the create date of this commerce forecast entry.
	*
	* @return the create date of this commerce forecast entry
	*/
	@Override
	public Date getCreateDate() {
		return _commerceForecastEntry.getCreateDate();
	}

	/**
	* Returns the customer ID of this commerce forecast entry.
	*
	* @return the customer ID of this commerce forecast entry
	*/
	@Override
	public long getCustomerId() {
		return _commerceForecastEntry.getCustomerId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceForecastEntry.getExpandoBridge();
	}

	/**
	* Returns the modified date of this commerce forecast entry.
	*
	* @return the modified date of this commerce forecast entry
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceForecastEntry.getModifiedDate();
	}

	/**
	* Returns the period of this commerce forecast entry.
	*
	* @return the period of this commerce forecast entry
	*/
	@Override
	public int getPeriod() {
		return _commerceForecastEntry.getPeriod();
	}

	/**
	* Returns the primary key of this commerce forecast entry.
	*
	* @return the primary key of this commerce forecast entry
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceForecastEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceForecastEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the sku of this commerce forecast entry.
	*
	* @return the sku of this commerce forecast entry
	*/
	@Override
	public String getSku() {
		return _commerceForecastEntry.getSku();
	}

	/**
	* Returns the target of this commerce forecast entry.
	*
	* @return the target of this commerce forecast entry
	*/
	@Override
	public int getTarget() {
		return _commerceForecastEntry.getTarget();
	}

	/**
	* Returns the time of this commerce forecast entry.
	*
	* @return the time of this commerce forecast entry
	*/
	@Override
	public long getTime() {
		return _commerceForecastEntry.getTime();
	}

	/**
	* Returns the user ID of this commerce forecast entry.
	*
	* @return the user ID of this commerce forecast entry
	*/
	@Override
	public long getUserId() {
		return _commerceForecastEntry.getUserId();
	}

	/**
	* Returns the user name of this commerce forecast entry.
	*
	* @return the user name of this commerce forecast entry
	*/
	@Override
	public String getUserName() {
		return _commerceForecastEntry.getUserName();
	}

	/**
	* Returns the user uuid of this commerce forecast entry.
	*
	* @return the user uuid of this commerce forecast entry
	*/
	@Override
	public String getUserUuid() {
		return _commerceForecastEntry.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceForecastEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceForecastEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceForecastEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceForecastEntry.isNew();
	}

	@Override
	public void persist() {
		_commerceForecastEntry.persist();
	}

	/**
	* Sets the assertivity of this commerce forecast entry.
	*
	* @param assertivity the assertivity of this commerce forecast entry
	*/
	@Override
	public void setAssertivity(BigDecimal assertivity) {
		_commerceForecastEntry.setAssertivity(assertivity);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceForecastEntry.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce forecast entry ID of this commerce forecast entry.
	*
	* @param commerceForecastEntryId the commerce forecast entry ID of this commerce forecast entry
	*/
	@Override
	public void setCommerceForecastEntryId(long commerceForecastEntryId) {
		_commerceForecastEntry.setCommerceForecastEntryId(commerceForecastEntryId);
	}

	/**
	* Sets the company ID of this commerce forecast entry.
	*
	* @param companyId the company ID of this commerce forecast entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceForecastEntry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce forecast entry.
	*
	* @param createDate the create date of this commerce forecast entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceForecastEntry.setCreateDate(createDate);
	}

	/**
	* Sets the customer ID of this commerce forecast entry.
	*
	* @param customerId the customer ID of this commerce forecast entry
	*/
	@Override
	public void setCustomerId(long customerId) {
		_commerceForecastEntry.setCustomerId(customerId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceForecastEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceForecastEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceForecastEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the modified date of this commerce forecast entry.
	*
	* @param modifiedDate the modified date of this commerce forecast entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceForecastEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceForecastEntry.setNew(n);
	}

	/**
	* Sets the period of this commerce forecast entry.
	*
	* @param period the period of this commerce forecast entry
	*/
	@Override
	public void setPeriod(int period) {
		_commerceForecastEntry.setPeriod(period);
	}

	/**
	* Sets the primary key of this commerce forecast entry.
	*
	* @param primaryKey the primary key of this commerce forecast entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceForecastEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceForecastEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the sku of this commerce forecast entry.
	*
	* @param sku the sku of this commerce forecast entry
	*/
	@Override
	public void setSku(String sku) {
		_commerceForecastEntry.setSku(sku);
	}

	/**
	* Sets the target of this commerce forecast entry.
	*
	* @param target the target of this commerce forecast entry
	*/
	@Override
	public void setTarget(int target) {
		_commerceForecastEntry.setTarget(target);
	}

	/**
	* Sets the time of this commerce forecast entry.
	*
	* @param time the time of this commerce forecast entry
	*/
	@Override
	public void setTime(long time) {
		_commerceForecastEntry.setTime(time);
	}

	/**
	* Sets the user ID of this commerce forecast entry.
	*
	* @param userId the user ID of this commerce forecast entry
	*/
	@Override
	public void setUserId(long userId) {
		_commerceForecastEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce forecast entry.
	*
	* @param userName the user name of this commerce forecast entry
	*/
	@Override
	public void setUserName(String userName) {
		_commerceForecastEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce forecast entry.
	*
	* @param userUuid the user uuid of this commerce forecast entry
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_commerceForecastEntry.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceForecastEntry> toCacheModel() {
		return _commerceForecastEntry.toCacheModel();
	}

	@Override
	public CommerceForecastEntry toEscapedModel() {
		return new CommerceForecastEntryWrapper(_commerceForecastEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceForecastEntry.toString();
	}

	@Override
	public CommerceForecastEntry toUnescapedModel() {
		return new CommerceForecastEntryWrapper(_commerceForecastEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceForecastEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceForecastEntryWrapper)) {
			return false;
		}

		CommerceForecastEntryWrapper commerceForecastEntryWrapper = (CommerceForecastEntryWrapper)obj;

		if (Objects.equals(_commerceForecastEntry,
					commerceForecastEntryWrapper._commerceForecastEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceForecastEntry getWrappedModel() {
		return _commerceForecastEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceForecastEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceForecastEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceForecastEntry.resetOriginalValues();
	}

	private final CommerceForecastEntry _commerceForecastEntry;
}