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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Company}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Company
 * @generated
 */
@ProviderType
public class CompanyWrapper implements Company, ModelWrapper<Company> {
	public CompanyWrapper(Company company) {
		_company = company;
	}

	@Override
	public Class<?> getModelClass() {
		return Company.class;
	}

	@Override
	public String getModelClassName() {
		return Company.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("companyId", getCompanyId());
		attributes.put("accountId", getAccountId());
		attributes.put("webId", getWebId());
		attributes.put("key", getKey());
		attributes.put("mx", getMx());
		attributes.put("homeURL", getHomeURL());
		attributes.put("logoId", getLogoId());
		attributes.put("system", getSystem());
		attributes.put("maxUsers", getMaxUsers());
		attributes.put("active", getActive());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long accountId = (Long)attributes.get("accountId");

		if (accountId != null) {
			setAccountId(accountId);
		}

		String webId = (String)attributes.get("webId");

		if (webId != null) {
			setWebId(webId);
		}

		String key = (String)attributes.get("key");

		if (key != null) {
			setKey(key);
		}

		String mx = (String)attributes.get("mx");

		if (mx != null) {
			setMx(mx);
		}

		String homeURL = (String)attributes.get("homeURL");

		if (homeURL != null) {
			setHomeURL(homeURL);
		}

		Long logoId = (Long)attributes.get("logoId");

		if (logoId != null) {
			setLogoId(logoId);
		}

		Boolean system = (Boolean)attributes.get("system");

		if (system != null) {
			setSystem(system);
		}

		Integer maxUsers = (Integer)attributes.get("maxUsers");

		if (maxUsers != null) {
			setMaxUsers(maxUsers);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}
	}

	/**
	* Returns the primary key of this company.
	*
	* @return the primary key of this company
	*/
	@Override
	public long getPrimaryKey() {
		return _company.getPrimaryKey();
	}

	/**
	* Sets the primary key of this company.
	*
	* @param primaryKey the primary key of this company
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_company.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the mvcc version of this company.
	*
	* @return the mvcc version of this company
	*/
	@Override
	public long getMvccVersion() {
		return _company.getMvccVersion();
	}

	/**
	* Sets the mvcc version of this company.
	*
	* @param mvccVersion the mvcc version of this company
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_company.setMvccVersion(mvccVersion);
	}

	/**
	* Returns the company ID of this company.
	*
	* @return the company ID of this company
	*/
	@Override
	public long getCompanyId() {
		return _company.getCompanyId();
	}

	/**
	* Sets the company ID of this company.
	*
	* @param companyId the company ID of this company
	*/
	@Override
	public void setCompanyId(long companyId) {
		_company.setCompanyId(companyId);
	}

	/**
	* Returns the account ID of this company.
	*
	* @return the account ID of this company
	*/
	@Override
	public long getAccountId() {
		return _company.getAccountId();
	}

	/**
	* Sets the account ID of this company.
	*
	* @param accountId the account ID of this company
	*/
	@Override
	public void setAccountId(long accountId) {
		_company.setAccountId(accountId);
	}

	/**
	* Returns the web ID of this company.
	*
	* @return the web ID of this company
	*/
	@Override
	public java.lang.String getWebId() {
		return _company.getWebId();
	}

	/**
	* Sets the web ID of this company.
	*
	* @param webId the web ID of this company
	*/
	@Override
	public void setWebId(java.lang.String webId) {
		_company.setWebId(webId);
	}

	/**
	* Returns the key of this company.
	*
	* @return the key of this company
	*/
	@Override
	public java.lang.String getKey() {
		return _company.getKey();
	}

	/**
	* Sets the key of this company.
	*
	* @param key the key of this company
	*/
	@Override
	public void setKey(java.lang.String key) {
		_company.setKey(key);
	}

	/**
	* Returns the mx of this company.
	*
	* @return the mx of this company
	*/
	@Override
	public java.lang.String getMx() {
		return _company.getMx();
	}

	/**
	* Sets the mx of this company.
	*
	* @param mx the mx of this company
	*/
	@Override
	public void setMx(java.lang.String mx) {
		_company.setMx(mx);
	}

	/**
	* Returns the home u r l of this company.
	*
	* @return the home u r l of this company
	*/
	@Override
	public java.lang.String getHomeURL() {
		return _company.getHomeURL();
	}

	/**
	* Sets the home u r l of this company.
	*
	* @param homeURL the home u r l of this company
	*/
	@Override
	public void setHomeURL(java.lang.String homeURL) {
		_company.setHomeURL(homeURL);
	}

	/**
	* Returns the logo ID of this company.
	*
	* @return the logo ID of this company
	*/
	@Override
	public long getLogoId() {
		return _company.getLogoId();
	}

	/**
	* Sets the logo ID of this company.
	*
	* @param logoId the logo ID of this company
	*/
	@Override
	public void setLogoId(long logoId) {
		_company.setLogoId(logoId);
	}

	/**
	* Returns the system of this company.
	*
	* @return the system of this company
	*/
	@Override
	public boolean getSystem() {
		return _company.getSystem();
	}

	/**
	* Returns <code>true</code> if this company is system.
	*
	* @return <code>true</code> if this company is system; <code>false</code> otherwise
	*/
	@Override
	public boolean isSystem() {
		return _company.isSystem();
	}

	/**
	* Sets whether this company is system.
	*
	* @param system the system of this company
	*/
	@Override
	public void setSystem(boolean system) {
		_company.setSystem(system);
	}

	/**
	* Returns the max users of this company.
	*
	* @return the max users of this company
	*/
	@Override
	public int getMaxUsers() {
		return _company.getMaxUsers();
	}

	/**
	* Sets the max users of this company.
	*
	* @param maxUsers the max users of this company
	*/
	@Override
	public void setMaxUsers(int maxUsers) {
		_company.setMaxUsers(maxUsers);
	}

	/**
	* Returns the active of this company.
	*
	* @return the active of this company
	*/
	@Override
	public boolean getActive() {
		return _company.getActive();
	}

	/**
	* Returns <code>true</code> if this company is active.
	*
	* @return <code>true</code> if this company is active; <code>false</code> otherwise
	*/
	@Override
	public boolean isActive() {
		return _company.isActive();
	}

	/**
	* Sets whether this company is active.
	*
	* @param active the active of this company
	*/
	@Override
	public void setActive(boolean active) {
		_company.setActive(active);
	}

	@Override
	public boolean isNew() {
		return _company.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_company.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _company.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_company.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _company.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _company.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_company.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _company.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_company.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_company.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_company.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new CompanyWrapper((Company)_company.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.Company company) {
		return _company.compareTo(company);
	}

	@Override
	public int hashCode() {
		return _company.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.Company> toCacheModel() {
		return _company.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.Company toEscapedModel() {
		return new CompanyWrapper(_company.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.Company toUnescapedModel() {
		return new CompanyWrapper(_company.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _company.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _company.toXmlString();
	}

	@Override
	public void persist() {
		_company.persist();
	}

	@Override
	public com.liferay.portal.model.Account getAccount()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _company.getAccount();
	}

	@Override
	public java.lang.String getAdminName() {
		return _company.getAdminName();
	}

	@Override
	public java.lang.String getAuthType() {
		return _company.getAuthType();
	}

	@Override
	public com.liferay.portal.model.User getDefaultUser()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _company.getDefaultUser();
	}

	@Override
	public java.lang.String getDefaultWebId() {
		return _company.getDefaultWebId();
	}

	@Override
	public java.lang.String getEmailAddress() {
		return _company.getEmailAddress();
	}

	@Override
	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _company.getGroup();
	}

	@Override
	public long getGroupId()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _company.getGroupId();
	}

	@Override
	public java.security.Key getKeyObj() {
		return _company.getKeyObj();
	}

	@Override
	public java.util.Locale getLocale()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _company.getLocale();
	}

	@Override
	public java.lang.String getName()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _company.getName();
	}

	@Override
	public java.lang.String getPortalURL(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _company.getPortalURL(groupId);
	}

	@Override
	public java.lang.String getShardName()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _company.getShardName();
	}

	@Override
	public java.lang.String getShortName()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _company.getShortName();
	}

	@Override
	public java.util.TimeZone getTimeZone()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _company.getTimeZone();
	}

	@Override
	public java.lang.String getVirtualHostname() {
		return _company.getVirtualHostname();
	}

	@Override
	public boolean hasCompanyMx(java.lang.String emailAddress) {
		return _company.hasCompanyMx(emailAddress);
	}

	@Override
	public boolean isAutoLogin() {
		return _company.isAutoLogin();
	}

	@Override
	public boolean isSendPassword() {
		return _company.isSendPassword();
	}

	@Override
	public boolean isSendPasswordResetLink() {
		return _company.isSendPasswordResetLink();
	}

	@Override
	public boolean isSiteLogo() {
		return _company.isSiteLogo();
	}

	@Override
	public boolean isStrangers() {
		return _company.isStrangers();
	}

	@Override
	public boolean isStrangersVerify() {
		return _company.isStrangersVerify();
	}

	@Override
	public boolean isStrangersWithMx() {
		return _company.isStrangersWithMx();
	}

	@Override
	public void setKeyObj(java.security.Key keyObj) {
		_company.setKeyObj(keyObj);
	}

	@Override
	public void setVirtualHostname(java.lang.String virtualHostname) {
		_company.setVirtualHostname(virtualHostname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CompanyWrapper)) {
			return false;
		}

		CompanyWrapper companyWrapper = (CompanyWrapper)obj;

		if (Validator.equals(_company, companyWrapper._company)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	@Deprecated
	public Company getWrappedCompany() {
		return _company;
	}

	@Override
	public Company getWrappedModel() {
		return _company;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _company.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _company.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_company.resetOriginalValues();
	}

	private Company _company;
}