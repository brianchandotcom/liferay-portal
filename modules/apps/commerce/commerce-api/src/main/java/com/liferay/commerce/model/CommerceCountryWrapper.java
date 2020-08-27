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

package com.liferay.commerce.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceCountry}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCountry
 * @generated
 */
public class CommerceCountryWrapper
	extends BaseModelWrapper<CommerceCountry>
	implements CommerceCountry, ModelWrapper<CommerceCountry> {

	public CommerceCountryWrapper(CommerceCountry commerceCountry) {
		super(commerceCountry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("commerceCountryId", getCommerceCountryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("billingAllowed", isBillingAllowed());
		attributes.put("shippingAllowed", isShippingAllowed());
		attributes.put("twoLettersISOCode", getTwoLettersISOCode());
		attributes.put("threeLettersISOCode", getThreeLettersISOCode());
		attributes.put("numericISOCode", getNumericISOCode());
		attributes.put("subjectToVAT", isSubjectToVAT());
		attributes.put("priority", getPriority());
		attributes.put("active", isActive());
		attributes.put("lastPublishDate", getLastPublishDate());
		attributes.put("channelFilterEnabled", isChannelFilterEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long commerceCountryId = (Long)attributes.get("commerceCountryId");

		if (commerceCountryId != null) {
			setCommerceCountryId(commerceCountryId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Boolean billingAllowed = (Boolean)attributes.get("billingAllowed");

		if (billingAllowed != null) {
			setBillingAllowed(billingAllowed);
		}

		Boolean shippingAllowed = (Boolean)attributes.get("shippingAllowed");

		if (shippingAllowed != null) {
			setShippingAllowed(shippingAllowed);
		}

		String twoLettersISOCode = (String)attributes.get("twoLettersISOCode");

		if (twoLettersISOCode != null) {
			setTwoLettersISOCode(twoLettersISOCode);
		}

		String threeLettersISOCode = (String)attributes.get(
			"threeLettersISOCode");

		if (threeLettersISOCode != null) {
			setThreeLettersISOCode(threeLettersISOCode);
		}

		Integer numericISOCode = (Integer)attributes.get("numericISOCode");

		if (numericISOCode != null) {
			setNumericISOCode(numericISOCode);
		}

		Boolean subjectToVAT = (Boolean)attributes.get("subjectToVAT");

		if (subjectToVAT != null) {
			setSubjectToVAT(subjectToVAT);
		}

		Double priority = (Double)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}

		Boolean channelFilterEnabled = (Boolean)attributes.get(
			"channelFilterEnabled");

		if (channelFilterEnabled != null) {
			setChannelFilterEnabled(channelFilterEnabled);
		}
	}

	/**
	 * Returns the active of this commerce country.
	 *
	 * @return the active of this commerce country
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
	 * Returns the billing allowed of this commerce country.
	 *
	 * @return the billing allowed of this commerce country
	 */
	@Override
	public boolean getBillingAllowed() {
		return model.getBillingAllowed();
	}

	/**
	 * Returns the channel filter enabled of this commerce country.
	 *
	 * @return the channel filter enabled of this commerce country
	 */
	@Override
	public boolean getChannelFilterEnabled() {
		return model.getChannelFilterEnabled();
	}

	/**
	 * Returns the commerce country ID of this commerce country.
	 *
	 * @return the commerce country ID of this commerce country
	 */
	@Override
	public long getCommerceCountryId() {
		return model.getCommerceCountryId();
	}

	@Override
	public java.util.List<CommerceRegion> getCommerceRegions() {
		return model.getCommerceRegions();
	}

	/**
	 * Returns the company ID of this commerce country.
	 *
	 * @return the company ID of this commerce country
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce country.
	 *
	 * @return the create date of this commerce country
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the last publish date of this commerce country.
	 *
	 * @return the last publish date of this commerce country
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this commerce country.
	 *
	 * @return the modified date of this commerce country
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this commerce country.
	 *
	 * @return the name of this commerce country
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the localized name of this commerce country in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this commerce country
	 */
	@Override
	public String getName(java.util.Locale locale) {
		return model.getName(locale);
	}

	/**
	 * Returns the localized name of this commerce country in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this commerce country. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getName(java.util.Locale locale, boolean useDefault) {
		return model.getName(locale, useDefault);
	}

	/**
	 * Returns the localized name of this commerce country in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this commerce country
	 */
	@Override
	public String getName(String languageId) {
		return model.getName(languageId);
	}

	/**
	 * Returns the localized name of this commerce country in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this commerce country
	 */
	@Override
	public String getName(String languageId, boolean useDefault) {
		return model.getName(languageId, useDefault);
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
	 * Returns a map of the locales and localized names of this commerce country.
	 *
	 * @return the locales and localized names of this commerce country
	 */
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return model.getNameMap();
	}

	/**
	 * Returns the numeric iso code of this commerce country.
	 *
	 * @return the numeric iso code of this commerce country
	 */
	@Override
	public int getNumericISOCode() {
		return model.getNumericISOCode();
	}

	/**
	 * Returns the primary key of this commerce country.
	 *
	 * @return the primary key of this commerce country
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this commerce country.
	 *
	 * @return the priority of this commerce country
	 */
	@Override
	public double getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the shipping allowed of this commerce country.
	 *
	 * @return the shipping allowed of this commerce country
	 */
	@Override
	public boolean getShippingAllowed() {
		return model.getShippingAllowed();
	}

	/**
	 * Returns the subject to vat of this commerce country.
	 *
	 * @return the subject to vat of this commerce country
	 */
	@Override
	public boolean getSubjectToVAT() {
		return model.getSubjectToVAT();
	}

	/**
	 * Returns the three letters iso code of this commerce country.
	 *
	 * @return the three letters iso code of this commerce country
	 */
	@Override
	public String getThreeLettersISOCode() {
		return model.getThreeLettersISOCode();
	}

	/**
	 * Returns the two letters iso code of this commerce country.
	 *
	 * @return the two letters iso code of this commerce country
	 */
	@Override
	public String getTwoLettersISOCode() {
		return model.getTwoLettersISOCode();
	}

	/**
	 * Returns the user ID of this commerce country.
	 *
	 * @return the user ID of this commerce country
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce country.
	 *
	 * @return the user name of this commerce country
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce country.
	 *
	 * @return the user uuid of this commerce country
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce country.
	 *
	 * @return the uuid of this commerce country
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this commerce country is active.
	 *
	 * @return <code>true</code> if this commerce country is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	/**
	 * Returns <code>true</code> if this commerce country is billing allowed.
	 *
	 * @return <code>true</code> if this commerce country is billing allowed; <code>false</code> otherwise
	 */
	@Override
	public boolean isBillingAllowed() {
		return model.isBillingAllowed();
	}

	/**
	 * Returns <code>true</code> if this commerce country is channel filter enabled.
	 *
	 * @return <code>true</code> if this commerce country is channel filter enabled; <code>false</code> otherwise
	 */
	@Override
	public boolean isChannelFilterEnabled() {
		return model.isChannelFilterEnabled();
	}

	/**
	 * Returns <code>true</code> if this commerce country is shipping allowed.
	 *
	 * @return <code>true</code> if this commerce country is shipping allowed; <code>false</code> otherwise
	 */
	@Override
	public boolean isShippingAllowed() {
		return model.isShippingAllowed();
	}

	/**
	 * Returns <code>true</code> if this commerce country is subject to vat.
	 *
	 * @return <code>true</code> if this commerce country is subject to vat; <code>false</code> otherwise
	 */
	@Override
	public boolean isSubjectToVAT() {
		return model.isSubjectToVAT();
	}

	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {

		model.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
			java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {

		model.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	 * Sets whether this commerce country is active.
	 *
	 * @param active the active of this commerce country
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets whether this commerce country is billing allowed.
	 *
	 * @param billingAllowed the billing allowed of this commerce country
	 */
	@Override
	public void setBillingAllowed(boolean billingAllowed) {
		model.setBillingAllowed(billingAllowed);
	}

	/**
	 * Sets whether this commerce country is channel filter enabled.
	 *
	 * @param channelFilterEnabled the channel filter enabled of this commerce country
	 */
	@Override
	public void setChannelFilterEnabled(boolean channelFilterEnabled) {
		model.setChannelFilterEnabled(channelFilterEnabled);
	}

	/**
	 * Sets the commerce country ID of this commerce country.
	 *
	 * @param commerceCountryId the commerce country ID of this commerce country
	 */
	@Override
	public void setCommerceCountryId(long commerceCountryId) {
		model.setCommerceCountryId(commerceCountryId);
	}

	/**
	 * Sets the company ID of this commerce country.
	 *
	 * @param companyId the company ID of this commerce country
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce country.
	 *
	 * @param createDate the create date of this commerce country
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the last publish date of this commerce country.
	 *
	 * @param lastPublishDate the last publish date of this commerce country
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this commerce country.
	 *
	 * @param modifiedDate the modified date of this commerce country
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this commerce country.
	 *
	 * @param name the name of this commerce country
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the localized name of this commerce country in the language.
	 *
	 * @param name the localized name of this commerce country
	 * @param locale the locale of the language
	 */
	@Override
	public void setName(String name, java.util.Locale locale) {
		model.setName(name, locale);
	}

	/**
	 * Sets the localized name of this commerce country in the language, and sets the default locale.
	 *
	 * @param name the localized name of this commerce country
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setName(
		String name, java.util.Locale locale, java.util.Locale defaultLocale) {

		model.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(String languageId) {
		model.setNameCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized names of this commerce country from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this commerce country
	 */
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		model.setNameMap(nameMap);
	}

	/**
	 * Sets the localized names of this commerce country from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this commerce country
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setNameMap(
		Map<java.util.Locale, String> nameMap, java.util.Locale defaultLocale) {

		model.setNameMap(nameMap, defaultLocale);
	}

	/**
	 * Sets the numeric iso code of this commerce country.
	 *
	 * @param numericISOCode the numeric iso code of this commerce country
	 */
	@Override
	public void setNumericISOCode(int numericISOCode) {
		model.setNumericISOCode(numericISOCode);
	}

	/**
	 * Sets the primary key of this commerce country.
	 *
	 * @param primaryKey the primary key of this commerce country
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this commerce country.
	 *
	 * @param priority the priority of this commerce country
	 */
	@Override
	public void setPriority(double priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets whether this commerce country is shipping allowed.
	 *
	 * @param shippingAllowed the shipping allowed of this commerce country
	 */
	@Override
	public void setShippingAllowed(boolean shippingAllowed) {
		model.setShippingAllowed(shippingAllowed);
	}

	/**
	 * Sets whether this commerce country is subject to vat.
	 *
	 * @param subjectToVAT the subject to vat of this commerce country
	 */
	@Override
	public void setSubjectToVAT(boolean subjectToVAT) {
		model.setSubjectToVAT(subjectToVAT);
	}

	/**
	 * Sets the three letters iso code of this commerce country.
	 *
	 * @param threeLettersISOCode the three letters iso code of this commerce country
	 */
	@Override
	public void setThreeLettersISOCode(String threeLettersISOCode) {
		model.setThreeLettersISOCode(threeLettersISOCode);
	}

	/**
	 * Sets the two letters iso code of this commerce country.
	 *
	 * @param twoLettersISOCode the two letters iso code of this commerce country
	 */
	@Override
	public void setTwoLettersISOCode(String twoLettersISOCode) {
		model.setTwoLettersISOCode(twoLettersISOCode);
	}

	/**
	 * Sets the user ID of this commerce country.
	 *
	 * @param userId the user ID of this commerce country
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce country.
	 *
	 * @param userName the user name of this commerce country
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce country.
	 *
	 * @param userUuid the user uuid of this commerce country
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce country.
	 *
	 * @param uuid the uuid of this commerce country
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CommerceCountryWrapper wrap(CommerceCountry commerceCountry) {
		return new CommerceCountryWrapper(commerceCountry);
	}

}