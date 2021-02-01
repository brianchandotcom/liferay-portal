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

package com.liferay.commerce.account.model;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceAccount}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceAccount
 * @generated
 */
public class CommerceAccountWrapper
	extends BaseModelWrapper<CommerceAccount>
	implements CommerceAccount, ModelWrapper<CommerceAccount> {

	public CommerceAccountWrapper(CommerceAccount commerceAccount) {
		super(commerceAccount);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("commerceAccountId", getCommerceAccountId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("parentCommerceAccountId", getParentCommerceAccountId());
		attributes.put("name", getName());
		attributes.put("logoId", getLogoId());
		attributes.put("email", getEmail());
		attributes.put("taxId", getTaxId());
		attributes.put("type", getType());
		attributes.put("active", isActive());
		attributes.put("displayDate", getDisplayDate());
		attributes.put("defaultBillingAddressId", getDefaultBillingAddressId());
		attributes.put(
			"defaultShippingAddressId", getDefaultShippingAddressId());
		attributes.put("expirationDate", getExpirationDate());
		attributes.put("lastPublishDate", getLastPublishDate());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long commerceAccountId = (Long)attributes.get("commerceAccountId");

		if (commerceAccountId != null) {
			setCommerceAccountId(commerceAccountId);
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

		Long parentCommerceAccountId = (Long)attributes.get(
			"parentCommerceAccountId");

		if (parentCommerceAccountId != null) {
			setParentCommerceAccountId(parentCommerceAccountId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Long logoId = (Long)attributes.get("logoId");

		if (logoId != null) {
			setLogoId(logoId);
		}

		String email = (String)attributes.get("email");

		if (email != null) {
			setEmail(email);
		}

		String taxId = (String)attributes.get("taxId");

		if (taxId != null) {
			setTaxId(taxId);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		Date displayDate = (Date)attributes.get("displayDate");

		if (displayDate != null) {
			setDisplayDate(displayDate);
		}

		Long defaultBillingAddressId = (Long)attributes.get(
			"defaultBillingAddressId");

		if (defaultBillingAddressId != null) {
			setDefaultBillingAddressId(defaultBillingAddressId);
		}

		Long defaultShippingAddressId = (Long)attributes.get(
			"defaultShippingAddressId");

		if (defaultShippingAddressId != null) {
			setDefaultShippingAddressId(defaultShippingAddressId);
		}

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Long statusByUserId = (Long)attributes.get("statusByUserId");

		if (statusByUserId != null) {
			setStatusByUserId(statusByUserId);
		}

		String statusByUserName = (String)attributes.get("statusByUserName");

		if (statusByUserName != null) {
			setStatusByUserName(statusByUserName);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}
	}

	/**
	 * Returns the active of this commerce account.
	 *
	 * @return the active of this commerce account
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	@Override
	public com.liferay.portal.kernel.model.Group getCommerceAccountGroup()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceAccountGroup();
	}

	@Override
	public long getCommerceAccountGroupId()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceAccountGroupId();
	}

	/**
	 * Returns the commerce account ID of this commerce account.
	 *
	 * @return the commerce account ID of this commerce account
	 */
	@Override
	public long getCommerceAccountId() {
		return model.getCommerceAccountId();
	}

	@Override
	public java.util.List<CommerceAccountOrganizationRel>
		getCommerceAccountOrganizationRels() {

		return model.getCommerceAccountOrganizationRels();
	}

	@Override
	public java.util.List<CommerceAccountUserRel> getCommerceAccountUserRels() {
		return model.getCommerceAccountUserRels();
	}

	/**
	 * Returns the company ID of this commerce account.
	 *
	 * @return the company ID of this commerce account
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce account.
	 *
	 * @return the create date of this commerce account
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the default billing address ID of this commerce account.
	 *
	 * @return the default billing address ID of this commerce account
	 */
	@Override
	public long getDefaultBillingAddressId() {
		return model.getDefaultBillingAddressId();
	}

	/**
	 * Returns the default shipping address ID of this commerce account.
	 *
	 * @return the default shipping address ID of this commerce account
	 */
	@Override
	public long getDefaultShippingAddressId() {
		return model.getDefaultShippingAddressId();
	}

	/**
	 * Returns the display date of this commerce account.
	 *
	 * @return the display date of this commerce account
	 */
	@Override
	public Date getDisplayDate() {
		return model.getDisplayDate();
	}

	/**
	 * Returns the email of this commerce account.
	 *
	 * @return the email of this commerce account
	 */
	@Override
	public String getEmail() {
		return model.getEmail();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return model.getExpandoBridge();
	}

	/**
	 * Returns the expiration date of this commerce account.
	 *
	 * @return the expiration date of this commerce account
	 */
	@Override
	public Date getExpirationDate() {
		return model.getExpirationDate();
	}

	/**
	 * Returns the external reference code of this commerce account.
	 *
	 * @return the external reference code of this commerce account
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the last publish date of this commerce account.
	 *
	 * @return the last publish date of this commerce account
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the logo ID of this commerce account.
	 *
	 * @return the logo ID of this commerce account
	 */
	@Override
	public long getLogoId() {
		return model.getLogoId();
	}

	/**
	 * Returns the modified date of this commerce account.
	 *
	 * @return the modified date of this commerce account
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this commerce account.
	 *
	 * @return the name of this commerce account
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	@Override
	public CommerceAccount getParentCommerceAccount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getParentCommerceAccount();
	}

	/**
	 * Returns the parent commerce account ID of this commerce account.
	 *
	 * @return the parent commerce account ID of this commerce account
	 */
	@Override
	public long getParentCommerceAccountId() {
		return model.getParentCommerceAccountId();
	}

	/**
	 * Returns the primary key of this commerce account.
	 *
	 * @return the primary key of this commerce account
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the status of this commerce account.
	 *
	 * @return the status of this commerce account
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this commerce account.
	 *
	 * @return the status by user ID of this commerce account
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this commerce account.
	 *
	 * @return the status by user name of this commerce account
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this commerce account.
	 *
	 * @return the status by user uuid of this commerce account
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this commerce account.
	 *
	 * @return the status date of this commerce account
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	/**
	 * Returns the tax ID of this commerce account.
	 *
	 * @return the tax ID of this commerce account
	 */
	@Override
	public String getTaxId() {
		return model.getTaxId();
	}

	/**
	 * Returns the type of this commerce account.
	 *
	 * @return the type of this commerce account
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this commerce account.
	 *
	 * @return the user ID of this commerce account
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce account.
	 *
	 * @return the user name of this commerce account
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce account.
	 *
	 * @return the user uuid of this commerce account
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns <code>true</code> if this commerce account is active.
	 *
	 * @return <code>true</code> if this commerce account is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	/**
	 * Returns <code>true</code> if this commerce account is approved.
	 *
	 * @return <code>true</code> if this commerce account is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	@Override
	public boolean isBusinessAccount() {
		return model.isBusinessAccount();
	}

	/**
	 * Returns <code>true</code> if this commerce account is denied.
	 *
	 * @return <code>true</code> if this commerce account is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	 * Returns <code>true</code> if this commerce account is a draft.
	 *
	 * @return <code>true</code> if this commerce account is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	 * Returns <code>true</code> if this commerce account is expired.
	 *
	 * @return <code>true</code> if this commerce account is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	 * Returns <code>true</code> if this commerce account is inactive.
	 *
	 * @return <code>true</code> if this commerce account is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this commerce account is incomplete.
	 *
	 * @return <code>true</code> if this commerce account is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	/**
	 * Returns <code>true</code> if this commerce account is pending.
	 *
	 * @return <code>true</code> if this commerce account is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	@Override
	public boolean isPersonalAccount() {
		return model.isPersonalAccount();
	}

	@Override
	public boolean isRoot() {
		return model.isRoot();
	}

	/**
	 * Returns <code>true</code> if this commerce account is scheduled.
	 *
	 * @return <code>true</code> if this commerce account is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled() {
		return model.isScheduled();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets whether this commerce account is active.
	 *
	 * @param active the active of this commerce account
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets the commerce account ID of this commerce account.
	 *
	 * @param commerceAccountId the commerce account ID of this commerce account
	 */
	@Override
	public void setCommerceAccountId(long commerceAccountId) {
		model.setCommerceAccountId(commerceAccountId);
	}

	/**
	 * Sets the company ID of this commerce account.
	 *
	 * @param companyId the company ID of this commerce account
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce account.
	 *
	 * @param createDate the create date of this commerce account
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the default billing address ID of this commerce account.
	 *
	 * @param defaultBillingAddressId the default billing address ID of this commerce account
	 */
	@Override
	public void setDefaultBillingAddressId(long defaultBillingAddressId) {
		model.setDefaultBillingAddressId(defaultBillingAddressId);
	}

	/**
	 * Sets the default shipping address ID of this commerce account.
	 *
	 * @param defaultShippingAddressId the default shipping address ID of this commerce account
	 */
	@Override
	public void setDefaultShippingAddressId(long defaultShippingAddressId) {
		model.setDefaultShippingAddressId(defaultShippingAddressId);
	}

	/**
	 * Sets the display date of this commerce account.
	 *
	 * @param displayDate the display date of this commerce account
	 */
	@Override
	public void setDisplayDate(Date displayDate) {
		model.setDisplayDate(displayDate);
	}

	/**
	 * Sets the email of this commerce account.
	 *
	 * @param email the email of this commerce account
	 */
	@Override
	public void setEmail(String email) {
		model.setEmail(email);
	}

	/**
	 * Sets the expiration date of this commerce account.
	 *
	 * @param expirationDate the expiration date of this commerce account
	 */
	@Override
	public void setExpirationDate(Date expirationDate) {
		model.setExpirationDate(expirationDate);
	}

	/**
	 * Sets the external reference code of this commerce account.
	 *
	 * @param externalReferenceCode the external reference code of this commerce account
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the last publish date of this commerce account.
	 *
	 * @param lastPublishDate the last publish date of this commerce account
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the logo ID of this commerce account.
	 *
	 * @param logoId the logo ID of this commerce account
	 */
	@Override
	public void setLogoId(long logoId) {
		model.setLogoId(logoId);
	}

	/**
	 * Sets the modified date of this commerce account.
	 *
	 * @param modifiedDate the modified date of this commerce account
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this commerce account.
	 *
	 * @param name the name of this commerce account
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the parent commerce account ID of this commerce account.
	 *
	 * @param parentCommerceAccountId the parent commerce account ID of this commerce account
	 */
	@Override
	public void setParentCommerceAccountId(long parentCommerceAccountId) {
		model.setParentCommerceAccountId(parentCommerceAccountId);
	}

	/**
	 * Sets the primary key of this commerce account.
	 *
	 * @param primaryKey the primary key of this commerce account
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the status of this commerce account.
	 *
	 * @param status the status of this commerce account
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this commerce account.
	 *
	 * @param statusByUserId the status by user ID of this commerce account
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this commerce account.
	 *
	 * @param statusByUserName the status by user name of this commerce account
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this commerce account.
	 *
	 * @param statusByUserUuid the status by user uuid of this commerce account
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this commerce account.
	 *
	 * @param statusDate the status date of this commerce account
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * Sets the tax ID of this commerce account.
	 *
	 * @param taxId the tax ID of this commerce account
	 */
	@Override
	public void setTaxId(String taxId) {
		model.setTaxId(taxId);
	}

	/**
	 * Sets the type of this commerce account.
	 *
	 * @param type the type of this commerce account
	 */
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this commerce account.
	 *
	 * @param userId the user ID of this commerce account
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce account.
	 *
	 * @param userName the user name of this commerce account
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce account.
	 *
	 * @param userUuid the user uuid of this commerce account
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected CommerceAccountWrapper wrap(CommerceAccount commerceAccount) {
		return new CommerceAccountWrapper(commerceAccount);
	}

}