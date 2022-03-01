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

package com.liferay.account.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AccountEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AccountEntry
 * @generated
 */
public class AccountEntryWrapper
	extends BaseModelWrapper<AccountEntry>
	implements AccountEntry, ModelWrapper<AccountEntry> {

	public AccountEntryWrapper(AccountEntry accountEntry) {
		super(accountEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("accountEntryId", getAccountEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("defaultBillingAddressId", getDefaultBillingAddressId());
		attributes.put(
			"defaultCPaymentMethodKey", getDefaultCPaymentMethodKey());
		attributes.put(
			"defaultDeliveryCTermEntryId", getDefaultDeliveryCTermEntryId());
		attributes.put(
			"defaultPaymentCTermEntryId", getDefaultPaymentCTermEntryId());
		attributes.put(
			"defaultShippingAddressId", getDefaultShippingAddressId());
		attributes.put("parentAccountEntryId", getParentAccountEntryId());
		attributes.put("description", getDescription());
		attributes.put("domains", getDomains());
		attributes.put("emailAddress", getEmailAddress());
		attributes.put("logoId", getLogoId());
		attributes.put("name", getName());
		attributes.put("taxExemptionCode", getTaxExemptionCode());
		attributes.put("taxIdNumber", getTaxIdNumber());
		attributes.put("type", getType());
		attributes.put("status", getStatus());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long accountEntryId = (Long)attributes.get("accountEntryId");

		if (accountEntryId != null) {
			setAccountEntryId(accountEntryId);
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

		Long defaultBillingAddressId = (Long)attributes.get(
			"defaultBillingAddressId");

		if (defaultBillingAddressId != null) {
			setDefaultBillingAddressId(defaultBillingAddressId);
		}

		String defaultCPaymentMethodKey = (String)attributes.get(
			"defaultCPaymentMethodKey");

		if (defaultCPaymentMethodKey != null) {
			setDefaultCPaymentMethodKey(defaultCPaymentMethodKey);
		}

		Long defaultDeliveryCTermEntryId = (Long)attributes.get(
			"defaultDeliveryCTermEntryId");

		if (defaultDeliveryCTermEntryId != null) {
			setDefaultDeliveryCTermEntryId(defaultDeliveryCTermEntryId);
		}

		Long defaultPaymentCTermEntryId = (Long)attributes.get(
			"defaultPaymentCTermEntryId");

		if (defaultPaymentCTermEntryId != null) {
			setDefaultPaymentCTermEntryId(defaultPaymentCTermEntryId);
		}

		Long defaultShippingAddressId = (Long)attributes.get(
			"defaultShippingAddressId");

		if (defaultShippingAddressId != null) {
			setDefaultShippingAddressId(defaultShippingAddressId);
		}

		Long parentAccountEntryId = (Long)attributes.get(
			"parentAccountEntryId");

		if (parentAccountEntryId != null) {
			setParentAccountEntryId(parentAccountEntryId);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String domains = (String)attributes.get("domains");

		if (domains != null) {
			setDomains(domains);
		}

		String emailAddress = (String)attributes.get("emailAddress");

		if (emailAddress != null) {
			setEmailAddress(emailAddress);
		}

		Long logoId = (Long)attributes.get("logoId");

		if (logoId != null) {
			setLogoId(logoId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String taxExemptionCode = (String)attributes.get("taxExemptionCode");

		if (taxExemptionCode != null) {
			setTaxExemptionCode(taxExemptionCode);
		}

		String taxIdNumber = (String)attributes.get("taxIdNumber");

		if (taxIdNumber != null) {
			setTaxIdNumber(taxIdNumber);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	@Override
	public AccountEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public com.liferay.portal.kernel.model.Group getAccountEntryGroup() {
		return model.getAccountEntryGroup();
	}

	@Override
	public long getAccountEntryGroupId() {
		return model.getAccountEntryGroupId();
	}

	/**
	 * Returns the account entry ID of this account entry.
	 *
	 * @return the account entry ID of this account entry
	 */
	@Override
	public long getAccountEntryId() {
		return model.getAccountEntryId();
	}

	/**
	 * Returns the company ID of this account entry.
	 *
	 * @return the company ID of this account entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this account entry.
	 *
	 * @return the create date of this account entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	@Override
	public com.liferay.portal.kernel.model.Address getDefaultBillingAddress() {
		return model.getDefaultBillingAddress();
	}

	/**
	 * Returns the default billing address ID of this account entry.
	 *
	 * @return the default billing address ID of this account entry
	 */
	@Override
	public long getDefaultBillingAddressId() {
		return model.getDefaultBillingAddressId();
	}

	/**
	 * Returns the default c payment method key of this account entry.
	 *
	 * @return the default c payment method key of this account entry
	 */
	@Override
	public String getDefaultCPaymentMethodKey() {
		return model.getDefaultCPaymentMethodKey();
	}

	/**
	 * Returns the default delivery c term entry ID of this account entry.
	 *
	 * @return the default delivery c term entry ID of this account entry
	 */
	@Override
	public long getDefaultDeliveryCTermEntryId() {
		return model.getDefaultDeliveryCTermEntryId();
	}

	/**
	 * Returns the default payment c term entry ID of this account entry.
	 *
	 * @return the default payment c term entry ID of this account entry
	 */
	@Override
	public long getDefaultPaymentCTermEntryId() {
		return model.getDefaultPaymentCTermEntryId();
	}

	@Override
	public com.liferay.portal.kernel.model.Address getDefaultShippingAddress() {
		return model.getDefaultShippingAddress();
	}

	/**
	 * Returns the default shipping address ID of this account entry.
	 *
	 * @return the default shipping address ID of this account entry
	 */
	@Override
	public long getDefaultShippingAddressId() {
		return model.getDefaultShippingAddressId();
	}

	/**
	 * Returns the description of this account entry.
	 *
	 * @return the description of this account entry
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the domains of this account entry.
	 *
	 * @return the domains of this account entry
	 */
	@Override
	public String getDomains() {
		return model.getDomains();
	}

	@Override
	public String[] getDomainsArray() {
		return model.getDomainsArray();
	}

	/**
	 * Returns the email address of this account entry.
	 *
	 * @return the email address of this account entry
	 */
	@Override
	public String getEmailAddress() {
		return model.getEmailAddress();
	}

	/**
	 * Returns the external reference code of this account entry.
	 *
	 * @return the external reference code of this account entry
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the logo ID of this account entry.
	 *
	 * @return the logo ID of this account entry
	 */
	@Override
	public long getLogoId() {
		return model.getLogoId();
	}

	/**
	 * Returns the modified date of this account entry.
	 *
	 * @return the modified date of this account entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this account entry.
	 *
	 * @return the mvcc version of this account entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this account entry.
	 *
	 * @return the name of this account entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the parent account entry ID of this account entry.
	 *
	 * @return the parent account entry ID of this account entry
	 */
	@Override
	public long getParentAccountEntryId() {
		return model.getParentAccountEntryId();
	}

	/**
	 * Returns the primary key of this account entry.
	 *
	 * @return the primary key of this account entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the status of this account entry.
	 *
	 * @return the status of this account entry
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the tax exemption code of this account entry.
	 *
	 * @return the tax exemption code of this account entry
	 */
	@Override
	public String getTaxExemptionCode() {
		return model.getTaxExemptionCode();
	}

	/**
	 * Returns the tax ID number of this account entry.
	 *
	 * @return the tax ID number of this account entry
	 */
	@Override
	public String getTaxIdNumber() {
		return model.getTaxIdNumber();
	}

	/**
	 * Returns the type of this account entry.
	 *
	 * @return the type of this account entry
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this account entry.
	 *
	 * @return the user ID of this account entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this account entry.
	 *
	 * @return the user name of this account entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this account entry.
	 *
	 * @return the user uuid of this account entry
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
	 * Sets the account entry ID of this account entry.
	 *
	 * @param accountEntryId the account entry ID of this account entry
	 */
	@Override
	public void setAccountEntryId(long accountEntryId) {
		model.setAccountEntryId(accountEntryId);
	}

	/**
	 * Sets the company ID of this account entry.
	 *
	 * @param companyId the company ID of this account entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this account entry.
	 *
	 * @param createDate the create date of this account entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the default billing address ID of this account entry.
	 *
	 * @param defaultBillingAddressId the default billing address ID of this account entry
	 */
	@Override
	public void setDefaultBillingAddressId(long defaultBillingAddressId) {
		model.setDefaultBillingAddressId(defaultBillingAddressId);
	}

	/**
	 * Sets the default c payment method key of this account entry.
	 *
	 * @param defaultCPaymentMethodKey the default c payment method key of this account entry
	 */
	@Override
	public void setDefaultCPaymentMethodKey(String defaultCPaymentMethodKey) {
		model.setDefaultCPaymentMethodKey(defaultCPaymentMethodKey);
	}

	/**
	 * Sets the default delivery c term entry ID of this account entry.
	 *
	 * @param defaultDeliveryCTermEntryId the default delivery c term entry ID of this account entry
	 */
	@Override
	public void setDefaultDeliveryCTermEntryId(
		long defaultDeliveryCTermEntryId) {

		model.setDefaultDeliveryCTermEntryId(defaultDeliveryCTermEntryId);
	}

	/**
	 * Sets the default payment c term entry ID of this account entry.
	 *
	 * @param defaultPaymentCTermEntryId the default payment c term entry ID of this account entry
	 */
	@Override
	public void setDefaultPaymentCTermEntryId(long defaultPaymentCTermEntryId) {
		model.setDefaultPaymentCTermEntryId(defaultPaymentCTermEntryId);
	}

	/**
	 * Sets the default shipping address ID of this account entry.
	 *
	 * @param defaultShippingAddressId the default shipping address ID of this account entry
	 */
	@Override
	public void setDefaultShippingAddressId(long defaultShippingAddressId) {
		model.setDefaultShippingAddressId(defaultShippingAddressId);
	}

	/**
	 * Sets the description of this account entry.
	 *
	 * @param description the description of this account entry
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the domains of this account entry.
	 *
	 * @param domains the domains of this account entry
	 */
	@Override
	public void setDomains(String domains) {
		model.setDomains(domains);
	}

	/**
	 * Sets the email address of this account entry.
	 *
	 * @param emailAddress the email address of this account entry
	 */
	@Override
	public void setEmailAddress(String emailAddress) {
		model.setEmailAddress(emailAddress);
	}

	/**
	 * Sets the external reference code of this account entry.
	 *
	 * @param externalReferenceCode the external reference code of this account entry
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the logo ID of this account entry.
	 *
	 * @param logoId the logo ID of this account entry
	 */
	@Override
	public void setLogoId(long logoId) {
		model.setLogoId(logoId);
	}

	/**
	 * Sets the modified date of this account entry.
	 *
	 * @param modifiedDate the modified date of this account entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this account entry.
	 *
	 * @param mvccVersion the mvcc version of this account entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this account entry.
	 *
	 * @param name the name of this account entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the parent account entry ID of this account entry.
	 *
	 * @param parentAccountEntryId the parent account entry ID of this account entry
	 */
	@Override
	public void setParentAccountEntryId(long parentAccountEntryId) {
		model.setParentAccountEntryId(parentAccountEntryId);
	}

	/**
	 * Sets the primary key of this account entry.
	 *
	 * @param primaryKey the primary key of this account entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the status of this account entry.
	 *
	 * @param status the status of this account entry
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the tax exemption code of this account entry.
	 *
	 * @param taxExemptionCode the tax exemption code of this account entry
	 */
	@Override
	public void setTaxExemptionCode(String taxExemptionCode) {
		model.setTaxExemptionCode(taxExemptionCode);
	}

	/**
	 * Sets the tax ID number of this account entry.
	 *
	 * @param taxIdNumber the tax ID number of this account entry
	 */
	@Override
	public void setTaxIdNumber(String taxIdNumber) {
		model.setTaxIdNumber(taxIdNumber);
	}

	/**
	 * Sets the type of this account entry.
	 *
	 * @param type the type of this account entry
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this account entry.
	 *
	 * @param userId the user ID of this account entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this account entry.
	 *
	 * @param userName the user name of this account entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this account entry.
	 *
	 * @param userUuid the user uuid of this account entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected AccountEntryWrapper wrap(AccountEntry accountEntry) {
		return new AccountEntryWrapper(accountEntry);
	}

}