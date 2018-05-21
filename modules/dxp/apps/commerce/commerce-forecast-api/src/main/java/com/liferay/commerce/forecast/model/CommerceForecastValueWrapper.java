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
 * This class is a wrapper for {@link CommerceForecastValue}.
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CommerceForecastValue
 * @generated
 */
@ProviderType
public class CommerceForecastValueWrapper implements CommerceForecastValue,
	ModelWrapper<CommerceForecastValue> {
	public CommerceForecastValueWrapper(
		CommerceForecastValue commerceForecastValue) {
		_commerceForecastValue = commerceForecastValue;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceForecastValue.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceForecastValue.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceForecastValueId", getCommerceForecastValueId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceForecastEntryId", getCommerceForecastEntryId());
		attributes.put("time", getTime());
		attributes.put("lowerValue", getLowerValue());
		attributes.put("value", getValue());
		attributes.put("upperValue", getUpperValue());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceForecastValueId = (Long)attributes.get(
				"commerceForecastValueId");

		if (commerceForecastValueId != null) {
			setCommerceForecastValueId(commerceForecastValueId);
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

		Long commerceForecastEntryId = (Long)attributes.get(
				"commerceForecastEntryId");

		if (commerceForecastEntryId != null) {
			setCommerceForecastEntryId(commerceForecastEntryId);
		}

		Long time = (Long)attributes.get("time");

		if (time != null) {
			setTime(time);
		}

		BigDecimal lowerValue = (BigDecimal)attributes.get("lowerValue");

		if (lowerValue != null) {
			setLowerValue(lowerValue);
		}

		BigDecimal value = (BigDecimal)attributes.get("value");

		if (value != null) {
			setValue(value);
		}

		BigDecimal upperValue = (BigDecimal)attributes.get("upperValue");

		if (upperValue != null) {
			setUpperValue(upperValue);
		}
	}

	@Override
	public Object clone() {
		return new CommerceForecastValueWrapper((CommerceForecastValue)_commerceForecastValue.clone());
	}

	@Override
	public int compareTo(CommerceForecastValue commerceForecastValue) {
		return _commerceForecastValue.compareTo(commerceForecastValue);
	}

	/**
	* Returns the commerce forecast entry ID of this commerce forecast value.
	*
	* @return the commerce forecast entry ID of this commerce forecast value
	*/
	@Override
	public long getCommerceForecastEntryId() {
		return _commerceForecastValue.getCommerceForecastEntryId();
	}

	/**
	* Returns the commerce forecast value ID of this commerce forecast value.
	*
	* @return the commerce forecast value ID of this commerce forecast value
	*/
	@Override
	public long getCommerceForecastValueId() {
		return _commerceForecastValue.getCommerceForecastValueId();
	}

	/**
	* Returns the company ID of this commerce forecast value.
	*
	* @return the company ID of this commerce forecast value
	*/
	@Override
	public long getCompanyId() {
		return _commerceForecastValue.getCompanyId();
	}

	/**
	* Returns the create date of this commerce forecast value.
	*
	* @return the create date of this commerce forecast value
	*/
	@Override
	public Date getCreateDate() {
		return _commerceForecastValue.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceForecastValue.getExpandoBridge();
	}

	/**
	* Returns the lower value of this commerce forecast value.
	*
	* @return the lower value of this commerce forecast value
	*/
	@Override
	public BigDecimal getLowerValue() {
		return _commerceForecastValue.getLowerValue();
	}

	/**
	* Returns the modified date of this commerce forecast value.
	*
	* @return the modified date of this commerce forecast value
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceForecastValue.getModifiedDate();
	}

	/**
	* Returns the primary key of this commerce forecast value.
	*
	* @return the primary key of this commerce forecast value
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceForecastValue.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceForecastValue.getPrimaryKeyObj();
	}

	/**
	* Returns the time of this commerce forecast value.
	*
	* @return the time of this commerce forecast value
	*/
	@Override
	public long getTime() {
		return _commerceForecastValue.getTime();
	}

	/**
	* Returns the upper value of this commerce forecast value.
	*
	* @return the upper value of this commerce forecast value
	*/
	@Override
	public BigDecimal getUpperValue() {
		return _commerceForecastValue.getUpperValue();
	}

	/**
	* Returns the user ID of this commerce forecast value.
	*
	* @return the user ID of this commerce forecast value
	*/
	@Override
	public long getUserId() {
		return _commerceForecastValue.getUserId();
	}

	/**
	* Returns the user name of this commerce forecast value.
	*
	* @return the user name of this commerce forecast value
	*/
	@Override
	public String getUserName() {
		return _commerceForecastValue.getUserName();
	}

	/**
	* Returns the user uuid of this commerce forecast value.
	*
	* @return the user uuid of this commerce forecast value
	*/
	@Override
	public String getUserUuid() {
		return _commerceForecastValue.getUserUuid();
	}

	/**
	* Returns the value of this commerce forecast value.
	*
	* @return the value of this commerce forecast value
	*/
	@Override
	public BigDecimal getValue() {
		return _commerceForecastValue.getValue();
	}

	@Override
	public int hashCode() {
		return _commerceForecastValue.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceForecastValue.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceForecastValue.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceForecastValue.isNew();
	}

	@Override
	public void persist() {
		_commerceForecastValue.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceForecastValue.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce forecast entry ID of this commerce forecast value.
	*
	* @param commerceForecastEntryId the commerce forecast entry ID of this commerce forecast value
	*/
	@Override
	public void setCommerceForecastEntryId(long commerceForecastEntryId) {
		_commerceForecastValue.setCommerceForecastEntryId(commerceForecastEntryId);
	}

	/**
	* Sets the commerce forecast value ID of this commerce forecast value.
	*
	* @param commerceForecastValueId the commerce forecast value ID of this commerce forecast value
	*/
	@Override
	public void setCommerceForecastValueId(long commerceForecastValueId) {
		_commerceForecastValue.setCommerceForecastValueId(commerceForecastValueId);
	}

	/**
	* Sets the company ID of this commerce forecast value.
	*
	* @param companyId the company ID of this commerce forecast value
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceForecastValue.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce forecast value.
	*
	* @param createDate the create date of this commerce forecast value
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceForecastValue.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceForecastValue.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceForecastValue.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceForecastValue.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the lower value of this commerce forecast value.
	*
	* @param lowerValue the lower value of this commerce forecast value
	*/
	@Override
	public void setLowerValue(BigDecimal lowerValue) {
		_commerceForecastValue.setLowerValue(lowerValue);
	}

	/**
	* Sets the modified date of this commerce forecast value.
	*
	* @param modifiedDate the modified date of this commerce forecast value
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceForecastValue.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceForecastValue.setNew(n);
	}

	/**
	* Sets the primary key of this commerce forecast value.
	*
	* @param primaryKey the primary key of this commerce forecast value
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceForecastValue.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceForecastValue.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the time of this commerce forecast value.
	*
	* @param time the time of this commerce forecast value
	*/
	@Override
	public void setTime(long time) {
		_commerceForecastValue.setTime(time);
	}

	/**
	* Sets the upper value of this commerce forecast value.
	*
	* @param upperValue the upper value of this commerce forecast value
	*/
	@Override
	public void setUpperValue(BigDecimal upperValue) {
		_commerceForecastValue.setUpperValue(upperValue);
	}

	/**
	* Sets the user ID of this commerce forecast value.
	*
	* @param userId the user ID of this commerce forecast value
	*/
	@Override
	public void setUserId(long userId) {
		_commerceForecastValue.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce forecast value.
	*
	* @param userName the user name of this commerce forecast value
	*/
	@Override
	public void setUserName(String userName) {
		_commerceForecastValue.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce forecast value.
	*
	* @param userUuid the user uuid of this commerce forecast value
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_commerceForecastValue.setUserUuid(userUuid);
	}

	/**
	* Sets the value of this commerce forecast value.
	*
	* @param value the value of this commerce forecast value
	*/
	@Override
	public void setValue(BigDecimal value) {
		_commerceForecastValue.setValue(value);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceForecastValue> toCacheModel() {
		return _commerceForecastValue.toCacheModel();
	}

	@Override
	public CommerceForecastValue toEscapedModel() {
		return new CommerceForecastValueWrapper(_commerceForecastValue.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceForecastValue.toString();
	}

	@Override
	public CommerceForecastValue toUnescapedModel() {
		return new CommerceForecastValueWrapper(_commerceForecastValue.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceForecastValue.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceForecastValueWrapper)) {
			return false;
		}

		CommerceForecastValueWrapper commerceForecastValueWrapper = (CommerceForecastValueWrapper)obj;

		if (Objects.equals(_commerceForecastValue,
					commerceForecastValueWrapper._commerceForecastValue)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceForecastValue getWrappedModel() {
		return _commerceForecastValue;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceForecastValue.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceForecastValue.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceForecastValue.resetOriginalValues();
	}

	private final CommerceForecastValue _commerceForecastValue;
}