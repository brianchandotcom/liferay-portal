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

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link CommerceCountry}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCountry
 * @generated
 */
@ProviderType
public class CommerceCountryWrapper implements CommerceCountry,
	ModelWrapper<CommerceCountry> {
	public CommerceCountryWrapper(CommerceCountry commerceCountry) {
		_commerceCountry = commerceCountry;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceCountry.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceCountry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("commerceCountryId", getCommerceCountryId());
		attributes.put("groupId", getGroupId());
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

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
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
	}

	@Override
	public java.lang.Object clone() {
		return new CommerceCountryWrapper((CommerceCountry)_commerceCountry.clone());
	}

	@Override
	public int compareTo(CommerceCountry commerceCountry) {
		return _commerceCountry.compareTo(commerceCountry);
	}

	/**
	* Returns the active of this commerce country.
	*
	* @return the active of this commerce country
	*/
	@Override
	public boolean getActive() {
		return _commerceCountry.getActive();
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _commerceCountry.getAvailableLanguageIds();
	}

	/**
	* Returns the billing allowed of this commerce country.
	*
	* @return the billing allowed of this commerce country
	*/
	@Override
	public boolean getBillingAllowed() {
		return _commerceCountry.getBillingAllowed();
	}

	/**
	* Returns the commerce country ID of this commerce country.
	*
	* @return the commerce country ID of this commerce country
	*/
	@Override
	public long getCommerceCountryId() {
		return _commerceCountry.getCommerceCountryId();
	}

	/**
	* Returns the company ID of this commerce country.
	*
	* @return the company ID of this commerce country
	*/
	@Override
	public long getCompanyId() {
		return _commerceCountry.getCompanyId();
	}

	/**
	* Returns the create date of this commerce country.
	*
	* @return the create date of this commerce country
	*/
	@Override
	public Date getCreateDate() {
		return _commerceCountry.getCreateDate();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _commerceCountry.getDefaultLanguageId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceCountry.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce country.
	*
	* @return the group ID of this commerce country
	*/
	@Override
	public long getGroupId() {
		return _commerceCountry.getGroupId();
	}

	/**
	* Returns the last publish date of this commerce country.
	*
	* @return the last publish date of this commerce country
	*/
	@Override
	public Date getLastPublishDate() {
		return _commerceCountry.getLastPublishDate();
	}

	/**
	* Returns the modified date of this commerce country.
	*
	* @return the modified date of this commerce country
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceCountry.getModifiedDate();
	}

	/**
	* Returns the name of this commerce country.
	*
	* @return the name of this commerce country
	*/
	@Override
	public java.lang.String getName() {
		return _commerceCountry.getName();
	}

	/**
	* Returns the localized name of this commerce country in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this commerce country
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale) {
		return _commerceCountry.getName(locale);
	}

	/**
	* Returns the localized name of this commerce country in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this commerce country. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _commerceCountry.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this commerce country in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this commerce country
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId) {
		return _commerceCountry.getName(languageId);
	}

	/**
	* Returns the localized name of this commerce country in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this commerce country
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _commerceCountry.getName(languageId, useDefault);
	}

	@Override
	public java.lang.String getNameCurrentLanguageId() {
		return _commerceCountry.getNameCurrentLanguageId();
	}

	@Override
	public java.lang.String getNameCurrentValue() {
		return _commerceCountry.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this commerce country.
	*
	* @return the locales and localized names of this commerce country
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getNameMap() {
		return _commerceCountry.getNameMap();
	}

	/**
	* Returns the numeric iso code of this commerce country.
	*
	* @return the numeric iso code of this commerce country
	*/
	@Override
	public int getNumericISOCode() {
		return _commerceCountry.getNumericISOCode();
	}

	/**
	* Returns the primary key of this commerce country.
	*
	* @return the primary key of this commerce country
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceCountry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceCountry.getPrimaryKeyObj();
	}

	/**
	* Returns the priority of this commerce country.
	*
	* @return the priority of this commerce country
	*/
	@Override
	public double getPriority() {
		return _commerceCountry.getPriority();
	}

	/**
	* Returns the shipping allowed of this commerce country.
	*
	* @return the shipping allowed of this commerce country
	*/
	@Override
	public boolean getShippingAllowed() {
		return _commerceCountry.getShippingAllowed();
	}

	/**
	* Returns the subject to vat of this commerce country.
	*
	* @return the subject to vat of this commerce country
	*/
	@Override
	public boolean getSubjectToVAT() {
		return _commerceCountry.getSubjectToVAT();
	}

	/**
	* Returns the three letters iso code of this commerce country.
	*
	* @return the three letters iso code of this commerce country
	*/
	@Override
	public java.lang.String getThreeLettersISOCode() {
		return _commerceCountry.getThreeLettersISOCode();
	}

	/**
	* Returns the two letters iso code of this commerce country.
	*
	* @return the two letters iso code of this commerce country
	*/
	@Override
	public java.lang.String getTwoLettersISOCode() {
		return _commerceCountry.getTwoLettersISOCode();
	}

	/**
	* Returns the user ID of this commerce country.
	*
	* @return the user ID of this commerce country
	*/
	@Override
	public long getUserId() {
		return _commerceCountry.getUserId();
	}

	/**
	* Returns the user name of this commerce country.
	*
	* @return the user name of this commerce country
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceCountry.getUserName();
	}

	/**
	* Returns the user uuid of this commerce country.
	*
	* @return the user uuid of this commerce country
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceCountry.getUserUuid();
	}

	/**
	* Returns the uuid of this commerce country.
	*
	* @return the uuid of this commerce country
	*/
	@Override
	public java.lang.String getUuid() {
		return _commerceCountry.getUuid();
	}

	@Override
	public int hashCode() {
		return _commerceCountry.hashCode();
	}

	/**
	* Returns <code>true</code> if this commerce country is active.
	*
	* @return <code>true</code> if this commerce country is active; <code>false</code> otherwise
	*/
	@Override
	public boolean isActive() {
		return _commerceCountry.isActive();
	}

	/**
	* Returns <code>true</code> if this commerce country is billing allowed.
	*
	* @return <code>true</code> if this commerce country is billing allowed; <code>false</code> otherwise
	*/
	@Override
	public boolean isBillingAllowed() {
		return _commerceCountry.isBillingAllowed();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceCountry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceCountry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceCountry.isNew();
	}

	/**
	* Returns <code>true</code> if this commerce country is shipping allowed.
	*
	* @return <code>true</code> if this commerce country is shipping allowed; <code>false</code> otherwise
	*/
	@Override
	public boolean isShippingAllowed() {
		return _commerceCountry.isShippingAllowed();
	}

	/**
	* Returns <code>true</code> if this commerce country is subject to vat.
	*
	* @return <code>true</code> if this commerce country is subject to vat; <code>false</code> otherwise
	*/
	@Override
	public boolean isSubjectToVAT() {
		return _commerceCountry.isSubjectToVAT();
	}

	@Override
	public void persist() {
		_commerceCountry.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commerceCountry.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commerceCountry.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	* Sets whether this commerce country is active.
	*
	* @param active the active of this commerce country
	*/
	@Override
	public void setActive(boolean active) {
		_commerceCountry.setActive(active);
	}

	/**
	* Sets whether this commerce country is billing allowed.
	*
	* @param billingAllowed the billing allowed of this commerce country
	*/
	@Override
	public void setBillingAllowed(boolean billingAllowed) {
		_commerceCountry.setBillingAllowed(billingAllowed);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceCountry.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce country ID of this commerce country.
	*
	* @param commerceCountryId the commerce country ID of this commerce country
	*/
	@Override
	public void setCommerceCountryId(long commerceCountryId) {
		_commerceCountry.setCommerceCountryId(commerceCountryId);
	}

	/**
	* Sets the company ID of this commerce country.
	*
	* @param companyId the company ID of this commerce country
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceCountry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce country.
	*
	* @param createDate the create date of this commerce country
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceCountry.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceCountry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceCountry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceCountry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce country.
	*
	* @param groupId the group ID of this commerce country
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceCountry.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this commerce country.
	*
	* @param lastPublishDate the last publish date of this commerce country
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_commerceCountry.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this commerce country.
	*
	* @param modifiedDate the modified date of this commerce country
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceCountry.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this commerce country.
	*
	* @param name the name of this commerce country
	*/
	@Override
	public void setName(java.lang.String name) {
		_commerceCountry.setName(name);
	}

	/**
	* Sets the localized name of this commerce country in the language.
	*
	* @param name the localized name of this commerce country
	* @param locale the locale of the language
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale) {
		_commerceCountry.setName(name, locale);
	}

	/**
	* Sets the localized name of this commerce country in the language, and sets the default locale.
	*
	* @param name the localized name of this commerce country
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_commerceCountry.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_commerceCountry.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this commerce country from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this commerce country
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap) {
		_commerceCountry.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this commerce country from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this commerce country
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_commerceCountry.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_commerceCountry.setNew(n);
	}

	/**
	* Sets the numeric iso code of this commerce country.
	*
	* @param numericISOCode the numeric iso code of this commerce country
	*/
	@Override
	public void setNumericISOCode(int numericISOCode) {
		_commerceCountry.setNumericISOCode(numericISOCode);
	}

	/**
	* Sets the primary key of this commerce country.
	*
	* @param primaryKey the primary key of this commerce country
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceCountry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceCountry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this commerce country.
	*
	* @param priority the priority of this commerce country
	*/
	@Override
	public void setPriority(double priority) {
		_commerceCountry.setPriority(priority);
	}

	/**
	* Sets whether this commerce country is shipping allowed.
	*
	* @param shippingAllowed the shipping allowed of this commerce country
	*/
	@Override
	public void setShippingAllowed(boolean shippingAllowed) {
		_commerceCountry.setShippingAllowed(shippingAllowed);
	}

	/**
	* Sets whether this commerce country is subject to vat.
	*
	* @param subjectToVAT the subject to vat of this commerce country
	*/
	@Override
	public void setSubjectToVAT(boolean subjectToVAT) {
		_commerceCountry.setSubjectToVAT(subjectToVAT);
	}

	/**
	* Sets the three letters iso code of this commerce country.
	*
	* @param threeLettersISOCode the three letters iso code of this commerce country
	*/
	@Override
	public void setThreeLettersISOCode(java.lang.String threeLettersISOCode) {
		_commerceCountry.setThreeLettersISOCode(threeLettersISOCode);
	}

	/**
	* Sets the two letters iso code of this commerce country.
	*
	* @param twoLettersISOCode the two letters iso code of this commerce country
	*/
	@Override
	public void setTwoLettersISOCode(java.lang.String twoLettersISOCode) {
		_commerceCountry.setTwoLettersISOCode(twoLettersISOCode);
	}

	/**
	* Sets the user ID of this commerce country.
	*
	* @param userId the user ID of this commerce country
	*/
	@Override
	public void setUserId(long userId) {
		_commerceCountry.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce country.
	*
	* @param userName the user name of this commerce country
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceCountry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce country.
	*
	* @param userUuid the user uuid of this commerce country
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceCountry.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this commerce country.
	*
	* @param uuid the uuid of this commerce country
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_commerceCountry.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceCountry> toCacheModel() {
		return _commerceCountry.toCacheModel();
	}

	@Override
	public CommerceCountry toEscapedModel() {
		return new CommerceCountryWrapper(_commerceCountry.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _commerceCountry.toString();
	}

	@Override
	public CommerceCountry toUnescapedModel() {
		return new CommerceCountryWrapper(_commerceCountry.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceCountry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceCountryWrapper)) {
			return false;
		}

		CommerceCountryWrapper commerceCountryWrapper = (CommerceCountryWrapper)obj;

		if (Objects.equals(_commerceCountry,
					commerceCountryWrapper._commerceCountry)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _commerceCountry.getStagedModelType();
	}

	@Override
	public CommerceCountry getWrappedModel() {
		return _commerceCountry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceCountry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceCountry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceCountry.resetOriginalValues();
	}

	private final CommerceCountry _commerceCountry;
}