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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
	extends BaseModelWrapper<CommerceInventoryAudit>
	implements CommerceInventoryAudit, ModelWrapper<CommerceInventoryAudit> {

	public CommerceInventoryAuditWrapper(
		CommerceInventoryAudit commerceInventoryAudit) {

		super(commerceInventoryAudit);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
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
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

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
	public CommerceInventoryAudit cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the commerce inventory audit ID of this commerce inventory audit.
	 *
	 * @return the commerce inventory audit ID of this commerce inventory audit
	 */
	@Override
	public long getCommerceInventoryAuditId() {
		return model.getCommerceInventoryAuditId();
	}

	/**
	 * Returns the company ID of this commerce inventory audit.
	 *
	 * @return the company ID of this commerce inventory audit
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce inventory audit.
	 *
	 * @return the create date of this commerce inventory audit
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the log type of this commerce inventory audit.
	 *
	 * @return the log type of this commerce inventory audit
	 */
	@Override
	public String getLogType() {
		return model.getLogType();
	}

	/**
	 * Returns the log type settings of this commerce inventory audit.
	 *
	 * @return the log type settings of this commerce inventory audit
	 */
	@Override
	public String getLogTypeSettings() {
		return model.getLogTypeSettings();
	}

	/**
	 * Returns the modified date of this commerce inventory audit.
	 *
	 * @return the modified date of this commerce inventory audit
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce inventory audit.
	 *
	 * @return the mvcc version of this commerce inventory audit
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce inventory audit.
	 *
	 * @return the primary key of this commerce inventory audit
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the quantity of this commerce inventory audit.
	 *
	 * @return the quantity of this commerce inventory audit
	 */
	@Override
	public int getQuantity() {
		return model.getQuantity();
	}

	/**
	 * Returns the sku of this commerce inventory audit.
	 *
	 * @return the sku of this commerce inventory audit
	 */
	@Override
	public String getSku() {
		return model.getSku();
	}

	/**
	 * Returns the user ID of this commerce inventory audit.
	 *
	 * @return the user ID of this commerce inventory audit
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce inventory audit.
	 *
	 * @return the user name of this commerce inventory audit
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce inventory audit.
	 *
	 * @return the user uuid of this commerce inventory audit
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the commerce inventory audit ID of this commerce inventory audit.
	 *
	 * @param commerceInventoryAuditId the commerce inventory audit ID of this commerce inventory audit
	 */
	@Override
	public void setCommerceInventoryAuditId(long commerceInventoryAuditId) {
		model.setCommerceInventoryAuditId(commerceInventoryAuditId);
	}

	/**
	 * Sets the company ID of this commerce inventory audit.
	 *
	 * @param companyId the company ID of this commerce inventory audit
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce inventory audit.
	 *
	 * @param createDate the create date of this commerce inventory audit
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the log type of this commerce inventory audit.
	 *
	 * @param logType the log type of this commerce inventory audit
	 */
	@Override
	public void setLogType(String logType) {
		model.setLogType(logType);
	}

	/**
	 * Sets the log type settings of this commerce inventory audit.
	 *
	 * @param logTypeSettings the log type settings of this commerce inventory audit
	 */
	@Override
	public void setLogTypeSettings(String logTypeSettings) {
		model.setLogTypeSettings(logTypeSettings);
	}

	/**
	 * Sets the modified date of this commerce inventory audit.
	 *
	 * @param modifiedDate the modified date of this commerce inventory audit
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce inventory audit.
	 *
	 * @param mvccVersion the mvcc version of this commerce inventory audit
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce inventory audit.
	 *
	 * @param primaryKey the primary key of this commerce inventory audit
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the quantity of this commerce inventory audit.
	 *
	 * @param quantity the quantity of this commerce inventory audit
	 */
	@Override
	public void setQuantity(int quantity) {
		model.setQuantity(quantity);
	}

	/**
	 * Sets the sku of this commerce inventory audit.
	 *
	 * @param sku the sku of this commerce inventory audit
	 */
	@Override
	public void setSku(String sku) {
		model.setSku(sku);
	}

	/**
	 * Sets the user ID of this commerce inventory audit.
	 *
	 * @param userId the user ID of this commerce inventory audit
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce inventory audit.
	 *
	 * @param userName the user name of this commerce inventory audit
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce inventory audit.
	 *
	 * @param userUuid the user uuid of this commerce inventory audit
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected CommerceInventoryAuditWrapper wrap(
		CommerceInventoryAudit commerceInventoryAudit) {

		return new CommerceInventoryAuditWrapper(commerceInventoryAudit);
	}

}