/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Country}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Country
 * @generated
 */
public class CountryWrapper
	extends BaseModelWrapper<Country>
	implements Country, ModelWrapper<Country> {

	public CountryWrapper(Country country) {
		super(country);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("defaultLanguageId", getDefaultLanguageId());
		attributes.put("countryId", getCountryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("a2", getA2());
		attributes.put("a3", getA3());
		attributes.put("active", isActive());
		attributes.put("billingAllowed", isBillingAllowed());
		attributes.put("groupFilterEnabled", isGroupFilterEnabled());
		attributes.put("idd", getIdd());
		attributes.put("name", getName());
		attributes.put("number", getNumber());
		attributes.put("position", getPosition());
		attributes.put("shippingAllowed", isShippingAllowed());
		attributes.put("subjectToVAT", isSubjectToVAT());
		attributes.put("zipRequired", isZipRequired());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		String defaultLanguageId = (String)attributes.get("defaultLanguageId");

		if (defaultLanguageId != null) {
			setDefaultLanguageId(defaultLanguageId);
		}

		Long countryId = (Long)attributes.get("countryId");

		if (countryId != null) {
			setCountryId(countryId);
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

		String a2 = (String)attributes.get("a2");

		if (a2 != null) {
			setA2(a2);
		}

		String a3 = (String)attributes.get("a3");

		if (a3 != null) {
			setA3(a3);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		Boolean billingAllowed = (Boolean)attributes.get("billingAllowed");

		if (billingAllowed != null) {
			setBillingAllowed(billingAllowed);
		}

		Boolean groupFilterEnabled = (Boolean)attributes.get(
			"groupFilterEnabled");

		if (groupFilterEnabled != null) {
			setGroupFilterEnabled(groupFilterEnabled);
		}

		String idd = (String)attributes.get("idd");

		if (idd != null) {
			setIdd(idd);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String number = (String)attributes.get("number");

		if (number != null) {
			setNumber(number);
		}

		Double position = (Double)attributes.get("position");

		if (position != null) {
			setPosition(position);
		}

		Boolean shippingAllowed = (Boolean)attributes.get("shippingAllowed");

		if (shippingAllowed != null) {
			setShippingAllowed(shippingAllowed);
		}

		Boolean subjectToVAT = (Boolean)attributes.get("subjectToVAT");

		if (subjectToVAT != null) {
			setSubjectToVAT(subjectToVAT);
		}

		Boolean zipRequired = (Boolean)attributes.get("zipRequired");

		if (zipRequired != null) {
			setZipRequired(zipRequired);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public Country cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the a2 of this country.
	 *
	 * @return the a2 of this country
	 */
	@Override
	public String getA2() {
		return model.getA2();
	}

	/**
	 * Returns the a3 of this country.
	 *
	 * @return the a3 of this country
	 */
	@Override
	public String getA3() {
		return model.getA3();
	}

	/**
	 * Returns the active of this country.
	 *
	 * @return the active of this country
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the billing allowed of this country.
	 *
	 * @return the billing allowed of this country
	 */
	@Override
	public boolean getBillingAllowed() {
		return model.getBillingAllowed();
	}

	/**
	 * Returns the company ID of this country.
	 *
	 * @return the company ID of this country
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the country ID of this country.
	 *
	 * @return the country ID of this country
	 */
	@Override
	public long getCountryId() {
		return model.getCountryId();
	}

	/**
	 * Returns the create date of this country.
	 *
	 * @return the create date of this country
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the default language ID of this country.
	 *
	 * @return the default language ID of this country
	 */
	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the group filter enabled of this country.
	 *
	 * @return the group filter enabled of this country
	 */
	@Override
	public boolean getGroupFilterEnabled() {
		return model.getGroupFilterEnabled();
	}

	/**
	 * Returns the idd of this country.
	 *
	 * @return the idd of this country
	 */
	@Override
	public String getIdd() {
		return model.getIdd();
	}

	@Override
	public Map<String, String> getLanguageIdToTitleMap() {
		return model.getLanguageIdToTitleMap();
	}

	/**
	 * Returns the last publish date of this country.
	 *
	 * @return the last publish date of this country
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this country.
	 *
	 * @return the modified date of this country
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this country.
	 *
	 * @return the mvcc version of this country
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this country.
	 *
	 * @return the name of this country
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	@Override
	public String getName(java.util.Locale locale) {
		return model.getName(locale);
	}

	@Override
	public String getNameCurrentLanguageId() {
		return model.getNameCurrentLanguageId();
	}

	@Override
	public String getNameCurrentValue() {
		return model.getNameCurrentValue();
	}

	/**
	 * Returns the number of this country.
	 *
	 * @return the number of this country
	 */
	@Override
	public String getNumber() {
		return model.getNumber();
	}

	/**
	 * Returns the position of this country.
	 *
	 * @return the position of this country
	 */
	@Override
	public double getPosition() {
		return model.getPosition();
	}

	/**
	 * Returns the primary key of this country.
	 *
	 * @return the primary key of this country
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the shipping allowed of this country.
	 *
	 * @return the shipping allowed of this country
	 */
	@Override
	public boolean getShippingAllowed() {
		return model.getShippingAllowed();
	}

	/**
	 * Returns the subject to vat of this country.
	 *
	 * @return the subject to vat of this country
	 */
	@Override
	public boolean getSubjectToVAT() {
		return model.getSubjectToVAT();
	}

	@Override
	public String getTitle() {
		return model.getTitle();
	}

	@Override
	public String getTitle(java.util.Locale locale) {
		return model.getTitle(locale);
	}

	@Override
	public String getTitle(String languageId) {
		return model.getTitle(languageId);
	}

	@Override
	public String getTitle(String languageId, boolean useDefault) {
		return model.getTitle(languageId, useDefault);
	}

	@Override
	public String getTitleMapAsXML() {
		return model.getTitleMapAsXML();
	}

	/**
	 * Returns the user ID of this country.
	 *
	 * @return the user ID of this country
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this country.
	 *
	 * @return the user name of this country
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this country.
	 *
	 * @return the user uuid of this country
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this country.
	 *
	 * @return the uuid of this country
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the zip required of this country.
	 *
	 * @return the zip required of this country
	 */
	@Override
	public boolean getZipRequired() {
		return model.getZipRequired();
	}

	/**
	 * Returns <code>true</code> if this country is active.
	 *
	 * @return <code>true</code> if this country is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	/**
	 * Returns <code>true</code> if this country is billing allowed.
	 *
	 * @return <code>true</code> if this country is billing allowed; <code>false</code> otherwise
	 */
	@Override
	public boolean isBillingAllowed() {
		return model.isBillingAllowed();
	}

	/**
	 * Returns <code>true</code> if this country is group filter enabled.
	 *
	 * @return <code>true</code> if this country is group filter enabled; <code>false</code> otherwise
	 */
	@Override
	public boolean isGroupFilterEnabled() {
		return model.isGroupFilterEnabled();
	}

	/**
	 * Returns <code>true</code> if this country is shipping allowed.
	 *
	 * @return <code>true</code> if this country is shipping allowed; <code>false</code> otherwise
	 */
	@Override
	public boolean isShippingAllowed() {
		return model.isShippingAllowed();
	}

	/**
	 * Returns <code>true</code> if this country is subject to vat.
	 *
	 * @return <code>true</code> if this country is subject to vat; <code>false</code> otherwise
	 */
	@Override
	public boolean isSubjectToVAT() {
		return model.isSubjectToVAT();
	}

	/**
	 * Returns <code>true</code> if this country is zip required.
	 *
	 * @return <code>true</code> if this country is zip required; <code>false</code> otherwise
	 */
	@Override
	public boolean isZipRequired() {
		return model.isZipRequired();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the a2 of this country.
	 *
	 * @param a2 the a2 of this country
	 */
	@Override
	public void setA2(String a2) {
		model.setA2(a2);
	}

	/**
	 * Sets the a3 of this country.
	 *
	 * @param a3 the a3 of this country
	 */
	@Override
	public void setA3(String a3) {
		model.setA3(a3);
	}

	/**
	 * Sets whether this country is active.
	 *
	 * @param active the active of this country
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets whether this country is billing allowed.
	 *
	 * @param billingAllowed the billing allowed of this country
	 */
	@Override
	public void setBillingAllowed(boolean billingAllowed) {
		model.setBillingAllowed(billingAllowed);
	}

	/**
	 * Sets the company ID of this country.
	 *
	 * @param companyId the company ID of this country
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the country ID of this country.
	 *
	 * @param countryId the country ID of this country
	 */
	@Override
	public void setCountryId(long countryId) {
		model.setCountryId(countryId);
	}

	/**
	 * Sets the create date of this country.
	 *
	 * @param createDate the create date of this country
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the default language ID of this country.
	 *
	 * @param defaultLanguageId the default language ID of this country
	 */
	@Override
	public void setDefaultLanguageId(String defaultLanguageId) {
		model.setDefaultLanguageId(defaultLanguageId);
	}

	/**
	 * Sets whether this country is group filter enabled.
	 *
	 * @param groupFilterEnabled the group filter enabled of this country
	 */
	@Override
	public void setGroupFilterEnabled(boolean groupFilterEnabled) {
		model.setGroupFilterEnabled(groupFilterEnabled);
	}

	/**
	 * Sets the idd of this country.
	 *
	 * @param idd the idd of this country
	 */
	@Override
	public void setIdd(String idd) {
		model.setIdd(idd);
	}

	/**
	 * Sets the last publish date of this country.
	 *
	 * @param lastPublishDate the last publish date of this country
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this country.
	 *
	 * @param modifiedDate the modified date of this country
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this country.
	 *
	 * @param mvccVersion the mvcc version of this country
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this country.
	 *
	 * @param name the name of this country
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	@Override
	public void setNameCurrentLanguageId(String languageId) {
		model.setNameCurrentLanguageId(languageId);
	}

	/**
	 * Sets the number of this country.
	 *
	 * @param number the number of this country
	 */
	@Override
	public void setNumber(String number) {
		model.setNumber(number);
	}

	/**
	 * Sets the position of this country.
	 *
	 * @param position the position of this country
	 */
	@Override
	public void setPosition(double position) {
		model.setPosition(position);
	}

	/**
	 * Sets the primary key of this country.
	 *
	 * @param primaryKey the primary key of this country
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets whether this country is shipping allowed.
	 *
	 * @param shippingAllowed the shipping allowed of this country
	 */
	@Override
	public void setShippingAllowed(boolean shippingAllowed) {
		model.setShippingAllowed(shippingAllowed);
	}

	/**
	 * Sets whether this country is subject to vat.
	 *
	 * @param subjectToVAT the subject to vat of this country
	 */
	@Override
	public void setSubjectToVAT(boolean subjectToVAT) {
		model.setSubjectToVAT(subjectToVAT);
	}

	/**
	 * Sets the user ID of this country.
	 *
	 * @param userId the user ID of this country
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this country.
	 *
	 * @param userName the user name of this country
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this country.
	 *
	 * @param userUuid the user uuid of this country
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this country.
	 *
	 * @param uuid the uuid of this country
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets whether this country is zip required.
	 *
	 * @param zipRequired the zip required of this country
	 */
	@Override
	public void setZipRequired(boolean zipRequired) {
		model.setZipRequired(zipRequired);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CountryWrapper wrap(Country country) {
		return new CountryWrapper(country);
	}

}