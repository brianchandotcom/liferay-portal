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

package com.liferay.commerce.inventory.model;

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
 * This class is a wrapper for {@link CommerceInventoryAudit}.
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryAudit
 * @generated
 */
public class CommerceInventoryAuditWrapper
	implements CommerceInventoryAudit, ModelWrapper<CommerceInventoryAudit> {

	public CommerceInventoryAuditWrapper(
		CommerceInventoryAudit commerceInventoryAudit) {

		_commerceInventoryAudit = commerceInventoryAudit;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceInventoryAudit.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceInventoryAudit.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"commerceInventoryAuditId", getCommerceInventoryAuditId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("sku", getSku());
		attributes.put("logType", getLogType());
		attributes.put("logTypeSettings", getLogTypeSettings());
		attributes.put("quantity", getQuantity());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceInventoryAuditId = (Long)attributes.get(
			"commerceInventoryAuditId");

		if (commerceInventoryAuditId != null) {
			setCommerceInventoryAuditId(commerceInventoryAuditId);
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

		String sku = (String)attributes.get("sku");

		if (sku != null) {
			setSku(sku);
		}

		String logType = (String)attributes.get("logType");

		if (logType != null) {
			setLogType(logType);
		}

		String logTypeSettings = (String)attributes.get("logTypeSettings");

		if (logTypeSettings != null) {
			setLogTypeSettings(logTypeSettings);
		}

		Integer quantity = (Integer)attributes.get("quantity");

		if (quantity != null) {
			setQuantity(quantity);
		}
	}

	@Override
	public Object clone() {
		return new CommerceInventoryAuditWrapper(
			(CommerceInventoryAudit)_commerceInventoryAudit.clone());
	}

	@Override
	public int compareTo(CommerceInventoryAudit commerceInventoryAudit) {
		return _commerceInventoryAudit.compareTo(commerceInventoryAudit);
	}

	/**
	 * Returns the commerce inventory audit ID of this commerce inventory audit.
	 *
	 * @return the commerce inventory audit ID of this commerce inventory audit
	 */
	@Override
	public long getCommerceInventoryAuditId() {
		return _commerceInventoryAudit.getCommerceInventoryAuditId();
	}

	/**
	 * Returns the company ID of this commerce inventory audit.
	 *
	 * @return the company ID of this commerce inventory audit
	 */
	@Override
	public long getCompanyId() {
		return _commerceInventoryAudit.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce inventory audit.
	 *
	 * @return the create date of this commerce inventory audit
	 */
	@Override
	public Date getCreateDate() {
		return _commerceInventoryAudit.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceInventoryAudit.getExpandoBridge();
	}

	/**
	 * Returns the log type of this commerce inventory audit.
	 *
	 * @return the log type of this commerce inventory audit
	 */
	@Override
	public String getLogType() {
		return _commerceInventoryAudit.getLogType();
	}

	/**
	 * Returns the log type settings of this commerce inventory audit.
	 *
	 * @return the log type settings of this commerce inventory audit
	 */
	@Override
	public String getLogTypeSettings() {
		return _commerceInventoryAudit.getLogTypeSettings();
	}

	/**
	 * Returns the modified date of this commerce inventory audit.
	 *
	 * @return the modified date of this commerce inventory audit
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceInventoryAudit.getModifiedDate();
	}

	/**
	 * Returns the primary key of this commerce inventory audit.
	 *
	 * @return the primary key of this commerce inventory audit
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceInventoryAudit.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceInventoryAudit.getPrimaryKeyObj();
	}

	/**
	 * Returns the quantity of this commerce inventory audit.
	 *
	 * @return the quantity of this commerce inventory audit
	 */
	@Override
	public int getQuantity() {
		return _commerceInventoryAudit.getQuantity();
	}

	/**
	 * Returns the sku of this commerce inventory audit.
	 *
	 * @return the sku of this commerce inventory audit
	 */
	@Override
	public String getSku() {
		return _commerceInventoryAudit.getSku();
	}

	/**
	 * Returns the user ID of this commerce inventory audit.
	 *
	 * @return the user ID of this commerce inventory audit
	 */
	@Override
	public long getUserId() {
		return _commerceInventoryAudit.getUserId();
	}

	/**
	 * Returns the user name of this commerce inventory audit.
	 *
	 * @return the user name of this commerce inventory audit
	 */
	@Override
	public String getUserName() {
		return _commerceInventoryAudit.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce inventory audit.
	 *
	 * @return the user uuid of this commerce inventory audit
	 */
	@Override
	public String getUserUuid() {
		return _commerceInventoryAudit.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceInventoryAudit.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceInventoryAudit.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceInventoryAudit.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceInventoryAudit.isNew();
	}

	@Override
	public void persist() {
		_commerceInventoryAudit.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceInventoryAudit.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce inventory audit ID of this commerce inventory audit.
	 *
	 * @param commerceInventoryAuditId the commerce inventory audit ID of this commerce inventory audit
	 */
	@Override
	public void setCommerceInventoryAuditId(long commerceInventoryAuditId) {
		_commerceInventoryAudit.setCommerceInventoryAuditId(
			commerceInventoryAuditId);
	}

	/**
	 * Sets the company ID of this commerce inventory audit.
	 *
	 * @param companyId the company ID of this commerce inventory audit
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceInventoryAudit.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce inventory audit.
	 *
	 * @param createDate the create date of this commerce inventory audit
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceInventoryAudit.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceInventoryAudit.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceInventoryAudit.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceInventoryAudit.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the log type of this commerce inventory audit.
	 *
	 * @param logType the log type of this commerce inventory audit
	 */
	@Override
	public void setLogType(String logType) {
		_commerceInventoryAudit.setLogType(logType);
	}

	/**
	 * Sets the log type settings of this commerce inventory audit.
	 *
	 * @param logTypeSettings the log type settings of this commerce inventory audit
	 */
	@Override
	public void setLogTypeSettings(String logTypeSettings) {
		_commerceInventoryAudit.setLogTypeSettings(logTypeSettings);
	}

	/**
	 * Sets the modified date of this commerce inventory audit.
	 *
	 * @param modifiedDate the modified date of this commerce inventory audit
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceInventoryAudit.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceInventoryAudit.setNew(n);
	}

	/**
	 * Sets the primary key of this commerce inventory audit.
	 *
	 * @param primaryKey the primary key of this commerce inventory audit
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceInventoryAudit.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceInventoryAudit.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the quantity of this commerce inventory audit.
	 *
	 * @param quantity the quantity of this commerce inventory audit
	 */
	@Override
	public void setQuantity(int quantity) {
		_commerceInventoryAudit.setQuantity(quantity);
	}

	/**
	 * Sets the sku of this commerce inventory audit.
	 *
	 * @param sku the sku of this commerce inventory audit
	 */
	@Override
	public void setSku(String sku) {
		_commerceInventoryAudit.setSku(sku);
	}

	/**
	 * Sets the user ID of this commerce inventory audit.
	 *
	 * @param userId the user ID of this commerce inventory audit
	 */
	@Override
	public void setUserId(long userId) {
		_commerceInventoryAudit.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce inventory audit.
	 *
	 * @param userName the user name of this commerce inventory audit
	 */
	@Override
	public void setUserName(String userName) {
		_commerceInventoryAudit.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce inventory audit.
	 *
	 * @param userUuid the user uuid of this commerce inventory audit
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceInventoryAudit.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceInventoryAudit>
		toCacheModel() {

		return _commerceInventoryAudit.toCacheModel();
	}

	@Override
	public CommerceInventoryAudit toEscapedModel() {
		return new CommerceInventoryAuditWrapper(
			_commerceInventoryAudit.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceInventoryAudit.toString();
	}

	@Override
	public CommerceInventoryAudit toUnescapedModel() {
		return new CommerceInventoryAuditWrapper(
			_commerceInventoryAudit.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceInventoryAudit.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceInventoryAuditWrapper)) {
			return false;
		}

		CommerceInventoryAuditWrapper commerceInventoryAuditWrapper =
			(CommerceInventoryAuditWrapper)object;

		if (Objects.equals(
				_commerceInventoryAudit,
				commerceInventoryAuditWrapper._commerceInventoryAudit)) {

			return true;
		}

		return false;
	}

	@Override
	public CommerceInventoryAudit getWrappedModel() {
		return _commerceInventoryAudit;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceInventoryAudit.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceInventoryAudit.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceInventoryAudit.resetOriginalValues();
	}

	private final CommerceInventoryAudit _commerceInventoryAudit;

}