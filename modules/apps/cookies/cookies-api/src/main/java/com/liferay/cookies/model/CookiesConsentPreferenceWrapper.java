/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CookiesConsentPreference}.
 * </p>
 *
 * @author Christopher Kian
 * @see CookiesConsentPreference
 * @generated
 */
public class CookiesConsentPreferenceWrapper
	extends BaseModelWrapper<CookiesConsentPreference>
	implements CookiesConsentPreference,
			   ModelWrapper<CookiesConsentPreference> {

	public CookiesConsentPreferenceWrapper(
		CookiesConsentPreference cookiesConsentPreference) {

		super(cookiesConsentPreference);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"cookiesConsentPreferenceId", getCookiesConsentPreferenceId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("domain", getDomain());
		attributes.put("expirationDate", getExpirationDate());
		attributes.put("name", getName());
		attributes.put("value", getValue());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long cookiesConsentPreferenceId = (Long)attributes.get(
			"cookiesConsentPreferenceId");

		if (cookiesConsentPreferenceId != null) {
			setCookiesConsentPreferenceId(cookiesConsentPreferenceId);
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

		String domain = (String)attributes.get("domain");

		if (domain != null) {
			setDomain(domain);
		}

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String value = (String)attributes.get("value");

		if (value != null) {
			setValue(value);
		}
	}

	@Override
	public CookiesConsentPreference cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this cookies consent preference.
	 *
	 * @return the company ID of this cookies consent preference
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the cookies consent preference ID of this cookies consent preference.
	 *
	 * @return the cookies consent preference ID of this cookies consent preference
	 */
	@Override
	public long getCookiesConsentPreferenceId() {
		return model.getCookiesConsentPreferenceId();
	}

	/**
	 * Returns the domain of this cookies consent preference.
	 *
	 * @return the domain of this cookies consent preference
	 */
	@Override
	public String getDomain() {
		return model.getDomain();
	}

	/**
	 * Returns the expiration date of this cookies consent preference.
	 *
	 * @return the expiration date of this cookies consent preference
	 */
	@Override
	public Date getExpirationDate() {
		return model.getExpirationDate();
	}

	/**
	 * Returns the mvcc version of this cookies consent preference.
	 *
	 * @return the mvcc version of this cookies consent preference
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this cookies consent preference.
	 *
	 * @return the name of this cookies consent preference
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this cookies consent preference.
	 *
	 * @return the primary key of this cookies consent preference
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this cookies consent preference.
	 *
	 * @return the user ID of this cookies consent preference
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this cookies consent preference.
	 *
	 * @return the user name of this cookies consent preference
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this cookies consent preference.
	 *
	 * @return the user uuid of this cookies consent preference
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the value of this cookies consent preference.
	 *
	 * @return the value of this cookies consent preference
	 */
	@Override
	public String getValue() {
		return model.getValue();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this cookies consent preference.
	 *
	 * @param companyId the company ID of this cookies consent preference
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cookies consent preference ID of this cookies consent preference.
	 *
	 * @param cookiesConsentPreferenceId the cookies consent preference ID of this cookies consent preference
	 */
	@Override
	public void setCookiesConsentPreferenceId(long cookiesConsentPreferenceId) {
		model.setCookiesConsentPreferenceId(cookiesConsentPreferenceId);
	}

	/**
	 * Sets the domain of this cookies consent preference.
	 *
	 * @param domain the domain of this cookies consent preference
	 */
	@Override
	public void setDomain(String domain) {
		model.setDomain(domain);
	}

	/**
	 * Sets the expiration date of this cookies consent preference.
	 *
	 * @param expirationDate the expiration date of this cookies consent preference
	 */
	@Override
	public void setExpirationDate(Date expirationDate) {
		model.setExpirationDate(expirationDate);
	}

	/**
	 * Sets the mvcc version of this cookies consent preference.
	 *
	 * @param mvccVersion the mvcc version of this cookies consent preference
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this cookies consent preference.
	 *
	 * @param name the name of this cookies consent preference
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this cookies consent preference.
	 *
	 * @param primaryKey the primary key of this cookies consent preference
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this cookies consent preference.
	 *
	 * @param userId the user ID of this cookies consent preference
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this cookies consent preference.
	 *
	 * @param userName the user name of this cookies consent preference
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this cookies consent preference.
	 *
	 * @param userUuid the user uuid of this cookies consent preference
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the value of this cookies consent preference.
	 *
	 * @param value the value of this cookies consent preference
	 */
	@Override
	public void setValue(String value) {
		model.setValue(value);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected CookiesConsentPreferenceWrapper wrap(
		CookiesConsentPreference cookiesConsentPreference) {

		return new CookiesConsentPreferenceWrapper(cookiesConsentPreference);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1449307652