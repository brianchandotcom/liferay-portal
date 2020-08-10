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
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
	implements CommerceAccount, ModelWrapper<CommerceAccount> {

	public CommerceAccountWrapper(CommerceAccount commerceAccount) {
		_commerceAccount = commerceAccount;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceAccount.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceAccount.class.getName();
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

	@Override
	public Object clone() {
		return new CommerceAccountWrapper(
			(CommerceAccount)_commerceAccount.clone());
	}

	@Override
	public int compareTo(CommerceAccount commerceAccount) {
		return _commerceAccount.compareTo(commerceAccount);
	}

	/**
	 * Returns the active of this commerce account.
	 *
	 * @return the active of this commerce account
	 */
	@Override
	public boolean getActive() {
		return _commerceAccount.getActive();
	}

	@Override
	public com.liferay.portal.kernel.model.Group getCommerceAccountGroup()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccount.getCommerceAccountGroup();
	}

	@Override
	public long getCommerceAccountGroupId()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccount.getCommerceAccountGroupId();
	}

	/**
	 * Returns the commerce account ID of this commerce account.
	 *
	 * @return the commerce account ID of this commerce account
	 */
	@Override
	public long getCommerceAccountId() {
		return _commerceAccount.getCommerceAccountId();
	}

	@Override
	public java.util.List<CommerceAccountOrganizationRel>
		getCommerceAccountOrganizationRels() {

		return _commerceAccount.getCommerceAccountOrganizationRels();
	}

	@Override
	public java.util.List<CommerceAccountUserRel> getCommerceAccountUserRels() {
		return _commerceAccount.getCommerceAccountUserRels();
	}

	/**
	 * Returns the company ID of this commerce account.
	 *
	 * @return the company ID of this commerce account
	 */
	@Override
	public long getCompanyId() {
		return _commerceAccount.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce account.
	 *
	 * @return the create date of this commerce account
	 */
	@Override
	public Date getCreateDate() {
		return _commerceAccount.getCreateDate();
	}

	/**
	 * Returns the default billing address ID of this commerce account.
	 *
	 * @return the default billing address ID of this commerce account
	 */
	@Override
	public long getDefaultBillingAddressId() {
		return _commerceAccount.getDefaultBillingAddressId();
	}

	/**
	 * Returns the default shipping address ID of this commerce account.
	 *
	 * @return the default shipping address ID of this commerce account
	 */
	@Override
	public long getDefaultShippingAddressId() {
		return _commerceAccount.getDefaultShippingAddressId();
	}

	/**
	 * Returns the display date of this commerce account.
	 *
	 * @return the display date of this commerce account
	 */
	@Override
	public Date getDisplayDate() {
		return _commerceAccount.getDisplayDate();
	}

	/**
	 * Returns the email of this commerce account.
	 *
	 * @return the email of this commerce account
	 */
	@Override
	public String getEmail() {
		return _commerceAccount.getEmail();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceAccount.getExpandoBridge();
	}

	/**
	 * Returns the expiration date of this commerce account.
	 *
	 * @return the expiration date of this commerce account
	 */
	@Override
	public Date getExpirationDate() {
		return _commerceAccount.getExpirationDate();
	}

	/**
	 * Returns the external reference code of this commerce account.
	 *
	 * @return the external reference code of this commerce account
	 */
	@Override
	public String getExternalReferenceCode() {
		return _commerceAccount.getExternalReferenceCode();
	}

	/**
	 * Returns the last publish date of this commerce account.
	 *
	 * @return the last publish date of this commerce account
	 */
	@Override
	public Date getLastPublishDate() {
		return _commerceAccount.getLastPublishDate();
	}

	/**
	 * Returns the logo ID of this commerce account.
	 *
	 * @return the logo ID of this commerce account
	 */
	@Override
	public long getLogoId() {
		return _commerceAccount.getLogoId();
	}

	/**
	 * Returns the modified date of this commerce account.
	 *
	 * @return the modified date of this commerce account
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceAccount.getModifiedDate();
	}

	/**
	 * Returns the name of this commerce account.
	 *
	 * @return the name of this commerce account
	 */
	@Override
	public String getName() {
		return _commerceAccount.getName();
	}

	@Override
	public CommerceAccount getParentCommerceAccount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccount.getParentCommerceAccount();
	}

	/**
	 * Returns the parent commerce account ID of this commerce account.
	 *
	 * @return the parent commerce account ID of this commerce account
	 */
	@Override
	public long getParentCommerceAccountId() {
		return _commerceAccount.getParentCommerceAccountId();
	}

	/**
	 * Returns the primary key of this commerce account.
	 *
	 * @return the primary key of this commerce account
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceAccount.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceAccount.getPrimaryKeyObj();
	}

	/**
	 * Returns the status of this commerce account.
	 *
	 * @return the status of this commerce account
	 */
	@Override
	public int getStatus() {
		return _commerceAccount.getStatus();
	}

	/**
	 * Returns the status by user ID of this commerce account.
	 *
	 * @return the status by user ID of this commerce account
	 */
	@Override
	public long getStatusByUserId() {
		return _commerceAccount.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this commerce account.
	 *
	 * @return the status by user name of this commerce account
	 */
	@Override
	public String getStatusByUserName() {
		return _commerceAccount.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this commerce account.
	 *
	 * @return the status by user uuid of this commerce account
	 */
	@Override
	public String getStatusByUserUuid() {
		return _commerceAccount.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this commerce account.
	 *
	 * @return the status date of this commerce account
	 */
	@Override
	public Date getStatusDate() {
		return _commerceAccount.getStatusDate();
	}

	/**
	 * Returns the tax ID of this commerce account.
	 *
	 * @return the tax ID of this commerce account
	 */
	@Override
	public String getTaxId() {
		return _commerceAccount.getTaxId();
	}

	/**
	 * Returns the type of this commerce account.
	 *
	 * @return the type of this commerce account
	 */
	@Override
	public int getType() {
		return _commerceAccount.getType();
	}

	/**
	 * Returns the user ID of this commerce account.
	 *
	 * @return the user ID of this commerce account
	 */
	@Override
	public long getUserId() {
		return _commerceAccount.getUserId();
	}

	/**
	 * Returns the user name of this commerce account.
	 *
	 * @return the user name of this commerce account
	 */
	@Override
	public String getUserName() {
		return _commerceAccount.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce account.
	 *
	 * @return the user uuid of this commerce account
	 */
	@Override
	public String getUserUuid() {
		return _commerceAccount.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceAccount.hashCode();
	}

	/**
	 * Returns <code>true</code> if this commerce account is active.
	 *
	 * @return <code>true</code> if this commerce account is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return _commerceAccount.isActive();
	}

	/**
	 * Returns <code>true</code> if this commerce account is approved.
	 *
	 * @return <code>true</code> if this commerce account is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return _commerceAccount.isApproved();
	}

	@Override
	public boolean isBusinessAccount() {
		return _commerceAccount.isBusinessAccount();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceAccount.isCachedModel();
	}

	/**
	 * Returns <code>true</code> if this commerce account is denied.
	 *
	 * @return <code>true</code> if this commerce account is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return _commerceAccount.isDenied();
	}

	/**
	 * Returns <code>true</code> if this commerce account is a draft.
	 *
	 * @return <code>true</code> if this commerce account is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return _commerceAccount.isDraft();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceAccount.isEscapedModel();
	}

	/**
	 * Returns <code>true</code> if this commerce account is expired.
	 *
	 * @return <code>true</code> if this commerce account is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return _commerceAccount.isExpired();
	}

	/**
	 * Returns <code>true</code> if this commerce account is inactive.
	 *
	 * @return <code>true</code> if this commerce account is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return _commerceAccount.isInactive();
	}

	/**
	 * Returns <code>true</code> if this commerce account is incomplete.
	 *
	 * @return <code>true</code> if this commerce account is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return _commerceAccount.isIncomplete();
	}

	@Override
	public boolean isNew() {
		return _commerceAccount.isNew();
	}

	/**
	 * Returns <code>true</code> if this commerce account is pending.
	 *
	 * @return <code>true</code> if this commerce account is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return _commerceAccount.isPending();
	}

	@Override
	public boolean isPersonalAccount() {
		return _commerceAccount.isPersonalAccount();
	}

	@Override
	public boolean isRoot() {
		return _commerceAccount.isRoot();
	}

	/**
	 * Returns <code>true</code> if this commerce account is scheduled.
	 *
	 * @return <code>true</code> if this commerce account is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled() {
		return _commerceAccount.isScheduled();
	}

	@Override
	public void persist() {
		_commerceAccount.persist();
	}

	/**
	 * Sets whether this commerce account is active.
	 *
	 * @param active the active of this commerce account
	 */
	@Override
	public void setActive(boolean active) {
		_commerceAccount.setActive(active);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceAccount.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce account ID of this commerce account.
	 *
	 * @param commerceAccountId the commerce account ID of this commerce account
	 */
	@Override
	public void setCommerceAccountId(long commerceAccountId) {
		_commerceAccount.setCommerceAccountId(commerceAccountId);
	}

	/**
	 * Sets the company ID of this commerce account.
	 *
	 * @param companyId the company ID of this commerce account
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceAccount.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce account.
	 *
	 * @param createDate the create date of this commerce account
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceAccount.setCreateDate(createDate);
	}

	/**
	 * Sets the default billing address ID of this commerce account.
	 *
	 * @param defaultBillingAddressId the default billing address ID of this commerce account
	 */
	@Override
	public void setDefaultBillingAddressId(long defaultBillingAddressId) {
		_commerceAccount.setDefaultBillingAddressId(defaultBillingAddressId);
	}

	/**
	 * Sets the default shipping address ID of this commerce account.
	 *
	 * @param defaultShippingAddressId the default shipping address ID of this commerce account
	 */
	@Override
	public void setDefaultShippingAddressId(long defaultShippingAddressId) {
		_commerceAccount.setDefaultShippingAddressId(defaultShippingAddressId);
	}

	/**
	 * Sets the display date of this commerce account.
	 *
	 * @param displayDate the display date of this commerce account
	 */
	@Override
	public void setDisplayDate(Date displayDate) {
		_commerceAccount.setDisplayDate(displayDate);
	}

	/**
	 * Sets the email of this commerce account.
	 *
	 * @param email the email of this commerce account
	 */
	@Override
	public void setEmail(String email) {
		_commerceAccount.setEmail(email);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceAccount.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceAccount.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceAccount.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the expiration date of this commerce account.
	 *
	 * @param expirationDate the expiration date of this commerce account
	 */
	@Override
	public void setExpirationDate(Date expirationDate) {
		_commerceAccount.setExpirationDate(expirationDate);
	}

	/**
	 * Sets the external reference code of this commerce account.
	 *
	 * @param externalReferenceCode the external reference code of this commerce account
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		_commerceAccount.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the last publish date of this commerce account.
	 *
	 * @param lastPublishDate the last publish date of this commerce account
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_commerceAccount.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the logo ID of this commerce account.
	 *
	 * @param logoId the logo ID of this commerce account
	 */
	@Override
	public void setLogoId(long logoId) {
		_commerceAccount.setLogoId(logoId);
	}

	/**
	 * Sets the modified date of this commerce account.
	 *
	 * @param modifiedDate the modified date of this commerce account
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceAccount.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this commerce account.
	 *
	 * @param name the name of this commerce account
	 */
	@Override
	public void setName(String name) {
		_commerceAccount.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_commerceAccount.setNew(n);
	}

	/**
	 * Sets the parent commerce account ID of this commerce account.
	 *
	 * @param parentCommerceAccountId the parent commerce account ID of this commerce account
	 */
	@Override
	public void setParentCommerceAccountId(long parentCommerceAccountId) {
		_commerceAccount.setParentCommerceAccountId(parentCommerceAccountId);
	}

	/**
	 * Sets the primary key of this commerce account.
	 *
	 * @param primaryKey the primary key of this commerce account
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceAccount.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceAccount.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the status of this commerce account.
	 *
	 * @param status the status of this commerce account
	 */
	@Override
	public void setStatus(int status) {
		_commerceAccount.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this commerce account.
	 *
	 * @param statusByUserId the status by user ID of this commerce account
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_commerceAccount.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this commerce account.
	 *
	 * @param statusByUserName the status by user name of this commerce account
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		_commerceAccount.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this commerce account.
	 *
	 * @param statusByUserUuid the status by user uuid of this commerce account
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		_commerceAccount.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this commerce account.
	 *
	 * @param statusDate the status date of this commerce account
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		_commerceAccount.setStatusDate(statusDate);
	}

	/**
	 * Sets the tax ID of this commerce account.
	 *
	 * @param taxId the tax ID of this commerce account
	 */
	@Override
	public void setTaxId(String taxId) {
		_commerceAccount.setTaxId(taxId);
	}

	/**
	 * Sets the type of this commerce account.
	 *
	 * @param type the type of this commerce account
	 */
	@Override
	public void setType(int type) {
		_commerceAccount.setType(type);
	}

	/**
	 * Sets the user ID of this commerce account.
	 *
	 * @param userId the user ID of this commerce account
	 */
	@Override
	public void setUserId(long userId) {
		_commerceAccount.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce account.
	 *
	 * @param userName the user name of this commerce account
	 */
	@Override
	public void setUserName(String userName) {
		_commerceAccount.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce account.
	 *
	 * @param userUuid the user uuid of this commerce account
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceAccount.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceAccount>
		toCacheModel() {

		return _commerceAccount.toCacheModel();
	}

	@Override
	public CommerceAccount toEscapedModel() {
		return new CommerceAccountWrapper(_commerceAccount.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceAccount.toString();
	}

	@Override
	public CommerceAccount toUnescapedModel() {
		return new CommerceAccountWrapper(_commerceAccount.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceAccount.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceAccountWrapper)) {
			return false;
		}

		CommerceAccountWrapper commerceAccountWrapper =
			(CommerceAccountWrapper)object;

		if (Objects.equals(
				_commerceAccount, commerceAccountWrapper._commerceAccount)) {

			return true;
		}

		return false;
	}

	@Override
	public CommerceAccount getWrappedModel() {
		return _commerceAccount;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceAccount.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceAccount.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceAccount.resetOriginalValues();
	}

	private final CommerceAccount _commerceAccount;

}