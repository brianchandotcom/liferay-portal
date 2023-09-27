/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
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
public class CompanyWrapper
	extends BaseModelWrapper<Company>
	implements Company, ModelWrapper<Company> {

	public CompanyWrapper(Company company) {
		super(company);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("webId", getWebId());
		attributes.put("mx", getMx());
		attributes.put("homeURL", getHomeURL());
		attributes.put("logoId", getLogoId());
		attributes.put("maxUsers", getMaxUsers());
		attributes.put("active", isActive());
		attributes.put("name", getName());
		attributes.put("legalName", getLegalName());
		attributes.put("legalId", getLegalId());
		attributes.put("legalType", getLegalType());
		attributes.put("sicCode", getSicCode());
		attributes.put("tickerSymbol", getTickerSymbol());
		attributes.put("industry", getIndustry());
		attributes.put("type", getType());
		attributes.put("size", getSize());
		attributes.put("indexNameCurrent", getIndexNameCurrent());
		attributes.put("indexNameNext", getIndexNameNext());

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

		String webId = (String)attributes.get("webId");

		if (webId != null) {
			setWebId(webId);
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

		Integer maxUsers = (Integer)attributes.get("maxUsers");

		if (maxUsers != null) {
			setMaxUsers(maxUsers);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String legalName = (String)attributes.get("legalName");

		if (legalName != null) {
			setLegalName(legalName);
		}

		String legalId = (String)attributes.get("legalId");

		if (legalId != null) {
			setLegalId(legalId);
		}

		String legalType = (String)attributes.get("legalType");

		if (legalType != null) {
			setLegalType(legalType);
		}

		String sicCode = (String)attributes.get("sicCode");

		if (sicCode != null) {
			setSicCode(sicCode);
		}

		String tickerSymbol = (String)attributes.get("tickerSymbol");

		if (tickerSymbol != null) {
			setTickerSymbol(tickerSymbol);
		}

		String industry = (String)attributes.get("industry");

		if (industry != null) {
			setIndustry(industry);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String size = (String)attributes.get("size");

		if (size != null) {
			setSize(size);
		}

		String indexNameCurrent = (String)attributes.get("indexNameCurrent");

		if (indexNameCurrent != null) {
			setIndexNameCurrent(indexNameCurrent);
		}

		String indexNameNext = (String)attributes.get("indexNameNext");

		if (indexNameNext != null) {
			setIndexNameNext(indexNameNext);
		}
	}

	@Override
	public Company cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public int compareTo(Company company) {
		return model.compareTo(company);
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
	public CompanyInfo getCompanyInfo() {
		return model.getCompanyInfo();
	}

	/**
	 * Returns the create date of this company.
	 *
	 * @return the create date of this company
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #getGuestUser}
	 */
	@Deprecated
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

	@Override
	public User getGuestUser()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getGuestUser();
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
	 * Returns the index name current of this company.
	 *
	 * @return the index name current of this company
	 */
	@Override
	public String getIndexNameCurrent() {
		return model.getIndexNameCurrent();
	}

	/**
	 * Returns the index name next of this company.
	 *
	 * @return the index name next of this company
	 */
	@Override
	public String getIndexNameNext() {
		return model.getIndexNameNext();
	}

	/**
	 * Returns the industry of this company.
	 *
	 * @return the industry of this company
	 */
	@Override
	public String getIndustry() {
		return model.getIndustry();
	}

	@Override
	public String getKey() {
		return model.getKey();
	}

	@Override
	public java.security.Key getKeyObj() {
		return model.getKeyObj();
	}

	/**
	 * Returns the legal ID of this company.
	 *
	 * @return the legal ID of this company
	 */
	@Override
	public String getLegalId() {
		return model.getLegalId();
	}

	/**
	 * Returns the legal name of this company.
	 *
	 * @return the legal name of this company
	 */
	@Override
	public String getLegalName() {
		return model.getLegalName();
	}

	/**
	 * Returns the legal type of this company.
	 *
	 * @return the legal type of this company
	 */
	@Override
	public String getLegalType() {
		return model.getLegalType();
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
	 * Returns the modified date of this company.
	 *
	 * @return the modified date of this company
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
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

	/**
	 * Returns the name of this company.
	 *
	 * @return the name of this company
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	@Override
	public String getPortalURL(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getPortalURL(groupId);
	}

	@Override
	public String getPortalURL(long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getPortalURL(groupId, privateLayout);
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
	 * Returns the sic code of this company.
	 *
	 * @return the sic code of this company
	 */
	@Override
	public String getSicCode() {
		return model.getSicCode();
	}

	/**
	 * Returns the size of this company.
	 *
	 * @return the size of this company
	 */
	@Override
	public String getSize() {
		return model.getSize();
	}

	/**
	 * Returns the ticker symbol of this company.
	 *
	 * @return the ticker symbol of this company
	 */
	@Override
	public String getTickerSymbol() {
		return model.getTickerSymbol();
	}

	@Override
	public java.util.TimeZone getTimeZone()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getTimeZone();
	}

	/**
	 * Returns the type of this company.
	 *
	 * @return the type of this company
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this company.
	 *
	 * @return the user ID of this company
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this company.
	 *
	 * @return the user name of this company
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this company.
	 *
	 * @return the user uuid of this company
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
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

	@Override
	public boolean isUpdatePasswordRequired() {
		return model.isUpdatePasswordRequired();
	}

	@Override
	public void persist() {
		model.persist();
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
	 * Sets the create date of this company.
	 *
	 * @param createDate the create date of this company
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
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
	 * Sets the index name current of this company.
	 *
	 * @param indexNameCurrent the index name current of this company
	 */
	@Override
	public void setIndexNameCurrent(String indexNameCurrent) {
		model.setIndexNameCurrent(indexNameCurrent);
	}

	/**
	 * Sets the index name next of this company.
	 *
	 * @param indexNameNext the index name next of this company
	 */
	@Override
	public void setIndexNameNext(String indexNameNext) {
		model.setIndexNameNext(indexNameNext);
	}

	/**
	 * Sets the industry of this company.
	 *
	 * @param industry the industry of this company
	 */
	@Override
	public void setIndustry(String industry) {
		model.setIndustry(industry);
	}

	@Override
	public void setKey(String key) {
		model.setKey(key);
	}

	@Override
	public void setKeyObj(java.security.Key keyObj) {
		model.setKeyObj(keyObj);
	}

	/**
	 * Sets the legal ID of this company.
	 *
	 * @param legalId the legal ID of this company
	 */
	@Override
	public void setLegalId(String legalId) {
		model.setLegalId(legalId);
	}

	/**
	 * Sets the legal name of this company.
	 *
	 * @param legalName the legal name of this company
	 */
	@Override
	public void setLegalName(String legalName) {
		model.setLegalName(legalName);
	}

	/**
	 * Sets the legal type of this company.
	 *
	 * @param legalType the legal type of this company
	 */
	@Override
	public void setLegalType(String legalType) {
		model.setLegalType(legalType);
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
	 * Sets the modified date of this company.
	 *
	 * @param modifiedDate the modified date of this company
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
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
	 * Sets the name of this company.
	 *
	 * @param name the name of this company
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
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
	 * Sets the sic code of this company.
	 *
	 * @param sicCode the sic code of this company
	 */
	@Override
	public void setSicCode(String sicCode) {
		model.setSicCode(sicCode);
	}

	/**
	 * Sets the size of this company.
	 *
	 * @param size the size of this company
	 */
	@Override
	public void setSize(String size) {
		model.setSize(size);
	}

	/**
	 * Sets the ticker symbol of this company.
	 *
	 * @param tickerSymbol the ticker symbol of this company
	 */
	@Override
	public void setTickerSymbol(String tickerSymbol) {
		model.setTickerSymbol(tickerSymbol);
	}

	/**
	 * Sets the type of this company.
	 *
	 * @param type the type of this company
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this company.
	 *
	 * @param userId the user ID of this company
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this company.
	 *
	 * @param userName the user name of this company
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this company.
	 *
	 * @param userUuid the user uuid of this company
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
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
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected CompanyWrapper wrap(Company company) {
		return new CompanyWrapper(company);
	}

}