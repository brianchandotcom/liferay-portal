/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Company}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Company
 * @generated
 */
public class CompanyWrapper implements Company, ModelWrapper<Company> {
	public CompanyWrapper(Company company) {
		_company = company;
	}

	public Class<?> getModelClass() {
		return Company.class;
	}

	public String getModelClassName() {
		return Company.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

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

	public void setModelAttributes(Map<String, Object> attributes) {
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
	public long getPrimaryKey() {
		return _company.getPrimaryKey();
	}

	/**
	* Sets the primary key of this company.
	*
	* @param primaryKey the primary key of this company
	*/
	public void setPrimaryKey(long primaryKey) {
		_company.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the company ID of this company.
	*
	* @return the company ID of this company
	*/
	public long getCompanyId() {
		return _company.getCompanyId();
	}

	/**
	* Sets the company ID of this company.
	*
	* @param companyId the company ID of this company
	*/
	public void setCompanyId(long companyId) {
		_company.setCompanyId(companyId);
	}

	/**
	* Returns the account ID of this company.
	*
	* @return the account ID of this company
	*/
	public long getAccountId() {
		return _company.getAccountId();
	}

	/**
	* Sets the account ID of this company.
	*
	* @param accountId the account ID of this company
	*/
	public void setAccountId(long accountId) {
		_company.setAccountId(accountId);
	}

	/**
	* Returns the web ID of this company.
	*
	* @return the web ID of this company
	*/
	public java.lang.String getWebId() {
		return _company.getWebId();
	}

	/**
	* Sets the web ID of this company.
	*
	* @param webId the web ID of this company
	*/
	public void setWebId(java.lang.String webId) {
		_company.setWebId(webId);
	}

	/**
	* Returns the key of this company.
	*
	* @return the key of this company
	*/
	public java.lang.String getKey() {
		return _company.getKey();
	}

	/**
	* Sets the key of this company.
	*
	* @param key the key of this company
	*/
	public void setKey(java.lang.String key) {
		_company.setKey(key);
	}

	/**
	* Returns the mx of this company.
	*
	* @return the mx of this company
	*/
	public java.lang.String getMx() {
		return _company.getMx();
	}

	/**
	* Sets the mx of this company.
	*
	* @param mx the mx of this company
	*/
	public void setMx(java.lang.String mx) {
		_company.setMx(mx);
	}

	/**
	* Returns the home u r l of this company.
	*
	* @return the home u r l of this company
	*/
	public java.lang.String getHomeURL() {
		return _company.getHomeURL();
	}

	/**
	* Sets the home u r l of this company.
	*
	* @param homeURL the home u r l of this company
	*/
	public void setHomeURL(java.lang.String homeURL) {
		_company.setHomeURL(homeURL);
	}

	/**
	* Returns the logo ID of this company.
	*
	* @return the logo ID of this company
	*/
	public long getLogoId() {
		return _company.getLogoId();
	}

	/**
	* Sets the logo ID of this company.
	*
	* @param logoId the logo ID of this company
	*/
	public void setLogoId(long logoId) {
		_company.setLogoId(logoId);
	}

	/**
	* Returns the system of this company.
	*
	* @return the system of this company
	*/
	public boolean getSystem() {
		return _company.getSystem();
	}

	/**
	* Returns <code>true</code> if this company is system.
	*
	* @return <code>true</code> if this company is system; <code>false</code> otherwise
	*/
	public boolean isSystem() {
		return _company.isSystem();
	}

	/**
	* Sets whether this company is system.
	*
	* @param system the system of this company
	*/
	public void setSystem(boolean system) {
		_company.setSystem(system);
	}

	/**
	* Returns the max users of this company.
	*
	* @return the max users of this company
	*/
	public int getMaxUsers() {
		return _company.getMaxUsers();
	}

	/**
	* Sets the max users of this company.
	*
	* @param maxUsers the max users of this company
	*/
	public void setMaxUsers(int maxUsers) {
		_company.setMaxUsers(maxUsers);
	}

	/**
	* Returns the active of this company.
	*
	* @return the active of this company
	*/
	public boolean getActive() {
		return _company.getActive();
	}

	/**
	* Returns <code>true</code> if this company is active.
	*
	* @return <code>true</code> if this company is active; <code>false</code> otherwise
	*/
	public boolean isActive() {
		return _company.isActive();
	}

	/**
	* Sets whether this company is active.
	*
	* @param active the active of this company
	*/
	public void setActive(boolean active) {
		_company.setActive(active);
	}

	public boolean isNew() {
		return _company.isNew();
	}

	public void setNew(boolean n) {
		_company.setNew(n);
	}

	public boolean isCachedModel() {
		return _company.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_company.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _company.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _company.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_company.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _company.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_company.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new CompanyWrapper((Company)_company.clone());
	}

	public int compareTo(com.liferay.portal.model.Company company) {
		return _company.compareTo(company);
	}

	@Override
	public int hashCode() {
		return _company.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.Company> toCacheModel() {
		return _company.toCacheModel();
	}

	public com.liferay.portal.model.Company toEscapedModel() {
		return new CompanyWrapper(_company.toEscapedModel());
	}

	public com.liferay.portal.model.Company toUnescapedModel() {
		return new CompanyWrapper(_company.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _company.toString();
	}

	public java.lang.String toXmlString() {
		return _company.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_company.persist();
	}

	public com.liferay.portal.model.Account getAccount()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _company.getAccount();
	}

	public java.lang.String getAdminName() {
		return _company.getAdminName();
	}

	public java.lang.String getAuthType()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _company.getAuthType();
	}

	public com.liferay.portal.model.User getDefaultUser()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _company.getDefaultUser();
	}

	public java.lang.String getDefaultWebId() {
		return _company.getDefaultWebId();
	}

	public java.lang.String getEmailAddress() {
		return _company.getEmailAddress();
	}

	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _company.getGroup();
	}

	public java.security.Key getKeyObj() {
		return _company.getKeyObj();
	}

	public java.util.Locale getLocale()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _company.getLocale();
	}

	public java.lang.String getName()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _company.getName();
	}

	public java.lang.String getPortalURL(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _company.getPortalURL(groupId);
	}

	public java.lang.String getShardName()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _company.getShardName();
	}

	public java.lang.String getShortName()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _company.getShortName();
	}

	public java.util.TimeZone getTimeZone()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _company.getTimeZone();
	}

	public java.lang.String getVirtualHostname() {
		return _company.getVirtualHostname();
	}

	public boolean hasCompanyMx(java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _company.hasCompanyMx(emailAddress);
	}

	public boolean isAutoLogin()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _company.isAutoLogin();
	}

	public boolean isSendPassword()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _company.isSendPassword();
	}

	public boolean isSendPasswordResetLink()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _company.isSendPasswordResetLink();
	}

	public boolean isSiteLogo()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _company.isSiteLogo();
	}

	public boolean isStrangers()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _company.isStrangers();
	}

	public boolean isStrangersVerify()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _company.isStrangersVerify();
	}

	public boolean isStrangersWithMx()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _company.isStrangersWithMx();
	}

	public void setKeyObj(java.security.Key keyObj) {
		_company.setKeyObj(keyObj);
	}

	public void setVirtualHostname(java.lang.String virtualHostname) {
		_company.setVirtualHostname(virtualHostname);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public Company getWrappedCompany() {
		return _company;
	}

	public Company getWrappedModel() {
		return _company;
	}

	public void resetOriginalValues() {
		_company.resetOriginalValues();
	}

	private Company _company;
}