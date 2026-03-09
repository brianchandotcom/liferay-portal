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
 * This class is a wrapper for {@link ConsentPreference}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ConsentPreference
 * @generated
 */
public class ConsentPreferenceWrapper
	extends BaseModelWrapper<ConsentPreference>
	implements ConsentPreference, ModelWrapper<ConsentPreference> {

	public ConsentPreferenceWrapper(ConsentPreference consentPreference) {
		super(consentPreference);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("consentPreferenceId", getConsentPreferenceId());
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

		Long consentPreferenceId = (Long)attributes.get("consentPreferenceId");

		if (consentPreferenceId != null) {
			setConsentPreferenceId(consentPreferenceId);
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
	public ConsentPreference cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this consent preference.
	 *
	 * @return the company ID of this consent preference
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the consent preference ID of this consent preference.
	 *
	 * @return the consent preference ID of this consent preference
	 */
	@Override
	public long getConsentPreferenceId() {
		return model.getConsentPreferenceId();
	}

	/**
	 * Returns the domain of this consent preference.
	 *
	 * @return the domain of this consent preference
	 */
	@Override
	public String getDomain() {
		return model.getDomain();
	}

	/**
	 * Returns the expiration date of this consent preference.
	 *
	 * @return the expiration date of this consent preference
	 */
	@Override
	public Date getExpirationDate() {
		return model.getExpirationDate();
	}

	/**
	 * Returns the mvcc version of this consent preference.
	 *
	 * @return the mvcc version of this consent preference
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this consent preference.
	 *
	 * @return the name of this consent preference
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this consent preference.
	 *
	 * @return the primary key of this consent preference
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this consent preference.
	 *
	 * @return the user ID of this consent preference
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this consent preference.
	 *
	 * @return the user name of this consent preference
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this consent preference.
	 *
	 * @return the user uuid of this consent preference
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the value of this consent preference.
	 *
	 * @return the value of this consent preference
	 */
	@Override
	public String getValue() {
		return model.getValue();
	}

	/**
	 * Sets the company ID of this consent preference.
	 *
	 * @param companyId the company ID of this consent preference
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the consent preference ID of this consent preference.
	 *
	 * @param consentPreferenceId the consent preference ID of this consent preference
	 */
	@Override
	public void setConsentPreferenceId(long consentPreferenceId) {
		model.setConsentPreferenceId(consentPreferenceId);
	}

	/**
	 * Sets the domain of this consent preference.
	 *
	 * @param domain the domain of this consent preference
	 */
	@Override
	public void setDomain(String domain) {
		model.setDomain(domain);
	}

	/**
	 * Sets the expiration date of this consent preference.
	 *
	 * @param expirationDate the expiration date of this consent preference
	 */
	@Override
	public void setExpirationDate(Date expirationDate) {
		model.setExpirationDate(expirationDate);
	}

	/**
	 * Sets the mvcc version of this consent preference.
	 *
	 * @param mvccVersion the mvcc version of this consent preference
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this consent preference.
	 *
	 * @param name the name of this consent preference
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this consent preference.
	 *
	 * @param primaryKey the primary key of this consent preference
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this consent preference.
	 *
	 * @param userId the user ID of this consent preference
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this consent preference.
	 *
	 * @param userName the user name of this consent preference
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this consent preference.
	 *
	 * @param userUuid the user uuid of this consent preference
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the value of this consent preference.
	 *
	 * @param value the value of this consent preference
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
	protected ConsentPreferenceWrapper wrap(
		ConsentPreference consentPreference) {

		return new ConsentPreferenceWrapper(consentPreference);
	}

}