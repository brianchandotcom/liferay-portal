/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.math.BigDecimal;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommercePaymentEntryAudit}.
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommercePaymentEntryAudit
 * @generated
 */
public class CommercePaymentEntryAuditWrapper
	extends BaseModelWrapper<CommercePaymentEntryAudit>
	implements CommercePaymentEntryAudit,
			   ModelWrapper<CommercePaymentEntryAudit> {

	public CommercePaymentEntryAuditWrapper(
		CommercePaymentEntryAudit commercePaymentEntryAudit) {

		super(commercePaymentEntryAudit);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"commercePaymentEntryAuditId", getCommercePaymentEntryAuditId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commercePaymentEntryId", getCommercePaymentEntryId());
		attributes.put("amount", getAmount());
		attributes.put("currencyCode", getCurrencyCode());
		attributes.put("logType", getLogType());
		attributes.put("logTypeSettings", getLogTypeSettings());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long commercePaymentEntryAuditId = (Long)attributes.get(
			"commercePaymentEntryAuditId");

		if (commercePaymentEntryAuditId != null) {
			setCommercePaymentEntryAuditId(commercePaymentEntryAuditId);
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

		Long commercePaymentEntryId = (Long)attributes.get(
			"commercePaymentEntryId");

		if (commercePaymentEntryId != null) {
			setCommercePaymentEntryId(commercePaymentEntryId);
		}

		BigDecimal amount = (BigDecimal)attributes.get("amount");

		if (amount != null) {
			setAmount(amount);
		}

		String currencyCode = (String)attributes.get("currencyCode");

		if (currencyCode != null) {
			setCurrencyCode(currencyCode);
		}

		String logType = (String)attributes.get("logType");

		if (logType != null) {
			setLogType(logType);
		}

		String logTypeSettings = (String)attributes.get("logTypeSettings");

		if (logTypeSettings != null) {
			setLogTypeSettings(logTypeSettings);
		}
	}

	@Override
	public CommercePaymentEntryAudit cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the amount of this commerce payment entry audit.
	 *
	 * @return the amount of this commerce payment entry audit
	 */
	@Override
	public BigDecimal getAmount() {
		return model.getAmount();
	}

	/**
	 * Returns the commerce payment entry audit ID of this commerce payment entry audit.
	 *
	 * @return the commerce payment entry audit ID of this commerce payment entry audit
	 */
	@Override
	public long getCommercePaymentEntryAuditId() {
		return model.getCommercePaymentEntryAuditId();
	}

	/**
	 * Returns the commerce payment entry ID of this commerce payment entry audit.
	 *
	 * @return the commerce payment entry ID of this commerce payment entry audit
	 */
	@Override
	public long getCommercePaymentEntryId() {
		return model.getCommercePaymentEntryId();
	}

	/**
	 * Returns the company ID of this commerce payment entry audit.
	 *
	 * @return the company ID of this commerce payment entry audit
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce payment entry audit.
	 *
	 * @return the create date of this commerce payment entry audit
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the currency code of this commerce payment entry audit.
	 *
	 * @return the currency code of this commerce payment entry audit
	 */
	@Override
	public String getCurrencyCode() {
		return model.getCurrencyCode();
	}

	/**
	 * Returns the log type of this commerce payment entry audit.
	 *
	 * @return the log type of this commerce payment entry audit
	 */
	@Override
	public String getLogType() {
		return model.getLogType();
	}

	/**
	 * Returns the log type settings of this commerce payment entry audit.
	 *
	 * @return the log type settings of this commerce payment entry audit
	 */
	@Override
	public String getLogTypeSettings() {
		return model.getLogTypeSettings();
	}

	/**
	 * Returns the modified date of this commerce payment entry audit.
	 *
	 * @return the modified date of this commerce payment entry audit
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce payment entry audit.
	 *
	 * @return the mvcc version of this commerce payment entry audit
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce payment entry audit.
	 *
	 * @return the primary key of this commerce payment entry audit
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this commerce payment entry audit.
	 *
	 * @return the user ID of this commerce payment entry audit
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce payment entry audit.
	 *
	 * @return the user name of this commerce payment entry audit
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce payment entry audit.
	 *
	 * @return the user uuid of this commerce payment entry audit
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
	 * Sets the amount of this commerce payment entry audit.
	 *
	 * @param amount the amount of this commerce payment entry audit
	 */
	@Override
	public void setAmount(BigDecimal amount) {
		model.setAmount(amount);
	}

	/**
	 * Sets the commerce payment entry audit ID of this commerce payment entry audit.
	 *
	 * @param commercePaymentEntryAuditId the commerce payment entry audit ID of this commerce payment entry audit
	 */
	@Override
	public void setCommercePaymentEntryAuditId(
		long commercePaymentEntryAuditId) {

		model.setCommercePaymentEntryAuditId(commercePaymentEntryAuditId);
	}

	/**
	 * Sets the commerce payment entry ID of this commerce payment entry audit.
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID of this commerce payment entry audit
	 */
	@Override
	public void setCommercePaymentEntryId(long commercePaymentEntryId) {
		model.setCommercePaymentEntryId(commercePaymentEntryId);
	}

	/**
	 * Sets the company ID of this commerce payment entry audit.
	 *
	 * @param companyId the company ID of this commerce payment entry audit
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce payment entry audit.
	 *
	 * @param createDate the create date of this commerce payment entry audit
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the currency code of this commerce payment entry audit.
	 *
	 * @param currencyCode the currency code of this commerce payment entry audit
	 */
	@Override
	public void setCurrencyCode(String currencyCode) {
		model.setCurrencyCode(currencyCode);
	}

	/**
	 * Sets the log type of this commerce payment entry audit.
	 *
	 * @param logType the log type of this commerce payment entry audit
	 */
	@Override
	public void setLogType(String logType) {
		model.setLogType(logType);
	}

	/**
	 * Sets the log type settings of this commerce payment entry audit.
	 *
	 * @param logTypeSettings the log type settings of this commerce payment entry audit
	 */
	@Override
	public void setLogTypeSettings(String logTypeSettings) {
		model.setLogTypeSettings(logTypeSettings);
	}

	/**
	 * Sets the modified date of this commerce payment entry audit.
	 *
	 * @param modifiedDate the modified date of this commerce payment entry audit
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce payment entry audit.
	 *
	 * @param mvccVersion the mvcc version of this commerce payment entry audit
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce payment entry audit.
	 *
	 * @param primaryKey the primary key of this commerce payment entry audit
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this commerce payment entry audit.
	 *
	 * @param userId the user ID of this commerce payment entry audit
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce payment entry audit.
	 *
	 * @param userName the user name of this commerce payment entry audit
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce payment entry audit.
	 *
	 * @param userUuid the user uuid of this commerce payment entry audit
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected CommercePaymentEntryAuditWrapper wrap(
		CommercePaymentEntryAudit commercePaymentEntryAudit) {

		return new CommercePaymentEntryAuditWrapper(commercePaymentEntryAudit);
	}

}