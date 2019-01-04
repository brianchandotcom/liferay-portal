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

package com.liferay.portal.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

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
public class CompanyWrapper extends BaseModelWrapper<Company> implements Company,
	ModelWrapper<Company> {
	public CompanyWrapper(Company company) {
		super(company);
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
		attributes.put("system", isSystem());
		attributes.put("maxUsers", getMaxUsers());
		attributes.put("active", isActive());

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

	@Override
	public int compareTo(Company company) {
		return model.compareTo(company);
	}

	@Override
	public Account getAccount()
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getAccount();
	}

	/**
	* Returns the account ID of this company.
	*
	* @return the account ID of this company
	*/
	@Override
	public long getAccountId() {
		return model.getAccountId();
	}

	/**
	* Returns the active of this company.
	*
	* @return the active of this company
	*/
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	@Override
	public String getAdminName() {
		return model.getAdminName();
	}

	@Override
	public String getAuthType() {
		return model.getAuthType();
	}

	/**
	* Returns the company ID of this company.
	*
	* @return the company ID of this company
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	@Override
	public User getDefaultUser()
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getDefaultUser();
	}

	@Override
	public String getDefaultWebId() {
		return model.getDefaultWebId();
	}

	@Override
	public String getEmailAddress() {
		return model.getEmailAddress();
	}

	@Override
	public Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getGroup();
	}

	@Override
	public long getGroupId()
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getGroupId();
	}

	/**
	* Returns the home url of this company.
	*
	* @return the home url of this company
	*/
	@Override
	public String getHomeURL() {
		return model.getHomeURL();
	}

	/**
	* Returns the key of this company.
	*
	* @return the key of this company
	*/
	@Override
	public String getKey() {
		return model.getKey();
	}

	@Override
	public java.security.Key getKeyObj() {
		return model.getKeyObj();
	}

	@Override
	public java.util.Locale getLocale()
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getLocale();
	}

	/**
	* Returns the logo ID of this company.
	*
	* @return the logo ID of this company
	*/
	@Override
	public long getLogoId() {
		return model.getLogoId();
	}

	/**
	* Returns the max users of this company.
	*
	* @return the max users of this company
	*/
	@Override
	public int getMaxUsers() {
		return model.getMaxUsers();
	}

	/**
	* Returns the mvcc version of this company.
	*
	* @return the mvcc version of this company
	*/
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	* Returns the mx of this company.
	*
	* @return the mx of this company
	*/
	@Override
	public String getMx() {
		return model.getMx();
	}

	@Override
	public String getName()
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getName();
	}

	@Override
	public String getPortalURL(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getPortalURL(groupId);
	}

	/**
	* Returns the primary key of this company.
	*
	* @return the primary key of this company
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public String getShortName()
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getShortName();
	}

	/**
	* Returns the system of this company.
	*
	* @return the system of this company
	*/
	@Override
	public boolean getSystem() {
		return model.getSystem();
	}

	@Override
	public java.util.TimeZone getTimeZone()
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getTimeZone();
	}

	@Override
	public String getVirtualHostname() {
		return model.getVirtualHostname();
	}

	/**
	* Returns the web ID of this company.
	*
	* @return the web ID of this company
	*/
	@Override
	public String getWebId() {
		return model.getWebId();
	}

	@Override
	public boolean hasCompanyMx(String emailAddress) {
		return model.hasCompanyMx(emailAddress);
	}

	/**
	* Returns <code>true</code> if this company is active.
	*
	* @return <code>true</code> if this company is active; <code>false</code> otherwise
	*/
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	@Override
	public boolean isAutoLogin() {
		return model.isAutoLogin();
	}

	@Override
	public boolean isSendPassword() {
		return model.isSendPassword();
	}

	@Override
	public boolean isSendPasswordResetLink() {
		return model.isSendPasswordResetLink();
	}

	@Override
	public boolean isSiteLogo() {
		return model.isSiteLogo();
	}

	@Override
	public boolean isStrangers() {
		return model.isStrangers();
	}

	@Override
	public boolean isStrangersVerify() {
		return model.isStrangersVerify();
	}

	@Override
	public boolean isStrangersWithMx() {
		return model.isStrangersWithMx();
	}

	/**
	* Returns <code>true</code> if this company is system.
	*
	* @return <code>true</code> if this company is system; <code>false</code> otherwise
	*/
	@Override
	public boolean isSystem() {
		return model.isSystem();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the account ID of this company.
	*
	* @param accountId the account ID of this company
	*/
	@Override
	public void setAccountId(long accountId) {
		model.setAccountId(accountId);
	}

	/**
	* Sets whether this company is active.
	*
	* @param active the active of this company
	*/
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	* Sets the company ID of this company.
	*
	* @param companyId the company ID of this company
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the home url of this company.
	*
	* @param homeURL the home url of this company
	*/
	@Override
	public void setHomeURL(String homeURL) {
		model.setHomeURL(homeURL);
	}

	/**
	* Sets the key of this company.
	*
	* @param key the key of this company
	*/
	@Override
	public void setKey(String key) {
		model.setKey(key);
	}

	@Override
	public void setKeyObj(java.security.Key keyObj) {
		model.setKeyObj(keyObj);
	}

	/**
	* Sets the logo ID of this company.
	*
	* @param logoId the logo ID of this company
	*/
	@Override
	public void setLogoId(long logoId) {
		model.setLogoId(logoId);
	}

	/**
	* Sets the max users of this company.
	*
	* @param maxUsers the max users of this company
	*/
	@Override
	public void setMaxUsers(int maxUsers) {
		model.setMaxUsers(maxUsers);
	}

	/**
	* Sets the mvcc version of this company.
	*
	* @param mvccVersion the mvcc version of this company
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the mx of this company.
	*
	* @param mx the mx of this company
	*/
	@Override
	public void setMx(String mx) {
		model.setMx(mx);
	}

	/**
	* Sets the primary key of this company.
	*
	* @param primaryKey the primary key of this company
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets whether this company is system.
	*
	* @param system the system of this company
	*/
	@Override
	public void setSystem(boolean system) {
		model.setSystem(system);
	}

	@Override
	public void setVirtualHostname(String virtualHostname) {
		model.setVirtualHostname(virtualHostname);
	}

	/**
	* Sets the web ID of this company.
	*
	* @param webId the web ID of this company
	*/
	@Override
	public void setWebId(String webId) {
		model.setWebId(webId);
	}

	@Override
	protected CompanyWrapper wrap(Company company) {
		return new CompanyWrapper(company);
	}
}