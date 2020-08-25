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

package com.liferay.commerce.pricing.model;

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
 * This class is a wrapper for {@link CommercePricingClass}.
 * </p>
 *
 * @author Riccardo Alberti
 * @see CommercePricingClass
 * @generated
 */
public class CommercePricingClassWrapper
	implements CommercePricingClass, ModelWrapper<CommercePricingClass> {

	public CommercePricingClassWrapper(
		CommercePricingClass commercePricingClass) {

		_commercePricingClass = commercePricingClass;
	}

	@Override
	public Class<?> getModelClass() {
		return CommercePricingClass.class;
	}

	@Override
	public String getModelClassName() {
		return CommercePricingClass.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("commercePricingClassId", getCommercePricingClassId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("title", getTitle());
		attributes.put("description", getDescription());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long commercePricingClassId = (Long)attributes.get(
			"commercePricingClassId");

		if (commercePricingClassId != null) {
			setCommercePricingClassId(commercePricingClassId);
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

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public Object clone() {
		return new CommercePricingClassWrapper(
			(CommercePricingClass)_commercePricingClass.clone());
	}

	@Override
	public int compareTo(CommercePricingClass commercePricingClass) {
		return _commercePricingClass.compareTo(commercePricingClass);
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return _commercePricingClass.getAvailableLanguageIds();
	}

	/**
	 * Returns the commerce pricing class ID of this commerce pricing class.
	 *
	 * @return the commerce pricing class ID of this commerce pricing class
	 */
	@Override
	public long getCommercePricingClassId() {
		return _commercePricingClass.getCommercePricingClassId();
	}

	/**
	 * Returns the company ID of this commerce pricing class.
	 *
	 * @return the company ID of this commerce pricing class
	 */
	@Override
	public long getCompanyId() {
		return _commercePricingClass.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce pricing class.
	 *
	 * @return the create date of this commerce pricing class
	 */
	@Override
	public Date getCreateDate() {
		return _commercePricingClass.getCreateDate();
	}

	@Override
	public String getDefaultLanguageId() {
		return _commercePricingClass.getDefaultLanguageId();
	}

	/**
	 * Returns the description of this commerce pricing class.
	 *
	 * @return the description of this commerce pricing class
	 */
	@Override
	public String getDescription() {
		return _commercePricingClass.getDescription();
	}

	/**
	 * Returns the localized description of this commerce pricing class in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized description of this commerce pricing class
	 */
	@Override
	public String getDescription(java.util.Locale locale) {
		return _commercePricingClass.getDescription(locale);
	}

	/**
	 * Returns the localized description of this commerce pricing class in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this commerce pricing class. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getDescription(java.util.Locale locale, boolean useDefault) {
		return _commercePricingClass.getDescription(locale, useDefault);
	}

	/**
	 * Returns the localized description of this commerce pricing class in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized description of this commerce pricing class
	 */
	@Override
	public String getDescription(String languageId) {
		return _commercePricingClass.getDescription(languageId);
	}

	/**
	 * Returns the localized description of this commerce pricing class in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this commerce pricing class
	 */
	@Override
	public String getDescription(String languageId, boolean useDefault) {
		return _commercePricingClass.getDescription(languageId, useDefault);
	}

	@Override
	public String getDescriptionCurrentLanguageId() {
		return _commercePricingClass.getDescriptionCurrentLanguageId();
	}

	@Override
	public String getDescriptionCurrentValue() {
		return _commercePricingClass.getDescriptionCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized descriptions of this commerce pricing class.
	 *
	 * @return the locales and localized descriptions of this commerce pricing class
	 */
	@Override
	public Map<java.util.Locale, String> getDescriptionMap() {
		return _commercePricingClass.getDescriptionMap();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commercePricingClass.getExpandoBridge();
	}

	/**
	 * Returns the external reference code of this commerce pricing class.
	 *
	 * @return the external reference code of this commerce pricing class
	 */
	@Override
	public String getExternalReferenceCode() {
		return _commercePricingClass.getExternalReferenceCode();
	}

	/**
	 * Returns the last publish date of this commerce pricing class.
	 *
	 * @return the last publish date of this commerce pricing class
	 */
	@Override
	public Date getLastPublishDate() {
		return _commercePricingClass.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this commerce pricing class.
	 *
	 * @return the modified date of this commerce pricing class
	 */
	@Override
	public Date getModifiedDate() {
		return _commercePricingClass.getModifiedDate();
	}

	/**
	 * Returns the primary key of this commerce pricing class.
	 *
	 * @return the primary key of this commerce pricing class
	 */
	@Override
	public long getPrimaryKey() {
		return _commercePricingClass.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commercePricingClass.getPrimaryKeyObj();
	}

	/**
	 * Returns the title of this commerce pricing class.
	 *
	 * @return the title of this commerce pricing class
	 */
	@Override
	public String getTitle() {
		return _commercePricingClass.getTitle();
	}

	/**
	 * Returns the localized title of this commerce pricing class in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized title of this commerce pricing class
	 */
	@Override
	public String getTitle(java.util.Locale locale) {
		return _commercePricingClass.getTitle(locale);
	}

	/**
	 * Returns the localized title of this commerce pricing class in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized title of this commerce pricing class. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getTitle(java.util.Locale locale, boolean useDefault) {
		return _commercePricingClass.getTitle(locale, useDefault);
	}

	/**
	 * Returns the localized title of this commerce pricing class in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized title of this commerce pricing class
	 */
	@Override
	public String getTitle(String languageId) {
		return _commercePricingClass.getTitle(languageId);
	}

	/**
	 * Returns the localized title of this commerce pricing class in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized title of this commerce pricing class
	 */
	@Override
	public String getTitle(String languageId, boolean useDefault) {
		return _commercePricingClass.getTitle(languageId, useDefault);
	}

	@Override
	public String getTitleCurrentLanguageId() {
		return _commercePricingClass.getTitleCurrentLanguageId();
	}

	@Override
	public String getTitleCurrentValue() {
		return _commercePricingClass.getTitleCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized titles of this commerce pricing class.
	 *
	 * @return the locales and localized titles of this commerce pricing class
	 */
	@Override
	public Map<java.util.Locale, String> getTitleMap() {
		return _commercePricingClass.getTitleMap();
	}

	/**
	 * Returns the user ID of this commerce pricing class.
	 *
	 * @return the user ID of this commerce pricing class
	 */
	@Override
	public long getUserId() {
		return _commercePricingClass.getUserId();
	}

	/**
	 * Returns the user name of this commerce pricing class.
	 *
	 * @return the user name of this commerce pricing class
	 */
	@Override
	public String getUserName() {
		return _commercePricingClass.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce pricing class.
	 *
	 * @return the user uuid of this commerce pricing class
	 */
	@Override
	public String getUserUuid() {
		return _commercePricingClass.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce pricing class.
	 *
	 * @return the uuid of this commerce pricing class
	 */
	@Override
	public String getUuid() {
		return _commercePricingClass.getUuid();
	}

	@Override
	public int hashCode() {
		return _commercePricingClass.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commercePricingClass.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commercePricingClass.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commercePricingClass.isNew();
	}

	@Override
	public void persist() {
		_commercePricingClass.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {

		_commercePricingClass.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
			java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {

		_commercePricingClass.prepareLocalizedFieldsForImport(
			defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commercePricingClass.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce pricing class ID of this commerce pricing class.
	 *
	 * @param commercePricingClassId the commerce pricing class ID of this commerce pricing class
	 */
	@Override
	public void setCommercePricingClassId(long commercePricingClassId) {
		_commercePricingClass.setCommercePricingClassId(commercePricingClassId);
	}

	/**
	 * Sets the company ID of this commerce pricing class.
	 *
	 * @param companyId the company ID of this commerce pricing class
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commercePricingClass.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce pricing class.
	 *
	 * @param createDate the create date of this commerce pricing class
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commercePricingClass.setCreateDate(createDate);
	}

	/**
	 * Sets the description of this commerce pricing class.
	 *
	 * @param description the description of this commerce pricing class
	 */
	@Override
	public void setDescription(String description) {
		_commercePricingClass.setDescription(description);
	}

	/**
	 * Sets the localized description of this commerce pricing class in the language.
	 *
	 * @param description the localized description of this commerce pricing class
	 * @param locale the locale of the language
	 */
	@Override
	public void setDescription(String description, java.util.Locale locale) {
		_commercePricingClass.setDescription(description, locale);
	}

	/**
	 * Sets the localized description of this commerce pricing class in the language, and sets the default locale.
	 *
	 * @param description the localized description of this commerce pricing class
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setDescription(
		String description, java.util.Locale locale,
		java.util.Locale defaultLocale) {

		_commercePricingClass.setDescription(
			description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(String languageId) {
		_commercePricingClass.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized descriptions of this commerce pricing class from the map of locales and localized descriptions.
	 *
	 * @param descriptionMap the locales and localized descriptions of this commerce pricing class
	 */
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap) {

		_commercePricingClass.setDescriptionMap(descriptionMap);
	}

	/**
	 * Sets the localized descriptions of this commerce pricing class from the map of locales and localized descriptions, and sets the default locale.
	 *
	 * @param descriptionMap the locales and localized descriptions of this commerce pricing class
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap,
		java.util.Locale defaultLocale) {

		_commercePricingClass.setDescriptionMap(descriptionMap, defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commercePricingClass.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commercePricingClass.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commercePricingClass.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the external reference code of this commerce pricing class.
	 *
	 * @param externalReferenceCode the external reference code of this commerce pricing class
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		_commercePricingClass.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the last publish date of this commerce pricing class.
	 *
	 * @param lastPublishDate the last publish date of this commerce pricing class
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_commercePricingClass.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this commerce pricing class.
	 *
	 * @param modifiedDate the modified date of this commerce pricing class
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commercePricingClass.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commercePricingClass.setNew(n);
	}

	/**
	 * Sets the primary key of this commerce pricing class.
	 *
	 * @param primaryKey the primary key of this commerce pricing class
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commercePricingClass.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commercePricingClass.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the title of this commerce pricing class.
	 *
	 * @param title the title of this commerce pricing class
	 */
	@Override
	public void setTitle(String title) {
		_commercePricingClass.setTitle(title);
	}

	/**
	 * Sets the localized title of this commerce pricing class in the language.
	 *
	 * @param title the localized title of this commerce pricing class
	 * @param locale the locale of the language
	 */
	@Override
	public void setTitle(String title, java.util.Locale locale) {
		_commercePricingClass.setTitle(title, locale);
	}

	/**
	 * Sets the localized title of this commerce pricing class in the language, and sets the default locale.
	 *
	 * @param title the localized title of this commerce pricing class
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setTitle(
		String title, java.util.Locale locale, java.util.Locale defaultLocale) {

		_commercePricingClass.setTitle(title, locale, defaultLocale);
	}

	@Override
	public void setTitleCurrentLanguageId(String languageId) {
		_commercePricingClass.setTitleCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized titles of this commerce pricing class from the map of locales and localized titles.
	 *
	 * @param titleMap the locales and localized titles of this commerce pricing class
	 */
	@Override
	public void setTitleMap(Map<java.util.Locale, String> titleMap) {
		_commercePricingClass.setTitleMap(titleMap);
	}

	/**
	 * Sets the localized titles of this commerce pricing class from the map of locales and localized titles, and sets the default locale.
	 *
	 * @param titleMap the locales and localized titles of this commerce pricing class
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setTitleMap(
		Map<java.util.Locale, String> titleMap,
		java.util.Locale defaultLocale) {

		_commercePricingClass.setTitleMap(titleMap, defaultLocale);
	}

	/**
	 * Sets the user ID of this commerce pricing class.
	 *
	 * @param userId the user ID of this commerce pricing class
	 */
	@Override
	public void setUserId(long userId) {
		_commercePricingClass.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce pricing class.
	 *
	 * @param userName the user name of this commerce pricing class
	 */
	@Override
	public void setUserName(String userName) {
		_commercePricingClass.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce pricing class.
	 *
	 * @param userUuid the user uuid of this commerce pricing class
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commercePricingClass.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce pricing class.
	 *
	 * @param uuid the uuid of this commerce pricing class
	 */
	@Override
	public void setUuid(String uuid) {
		_commercePricingClass.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommercePricingClass>
		toCacheModel() {

		return _commercePricingClass.toCacheModel();
	}

	@Override
	public CommercePricingClass toEscapedModel() {
		return new CommercePricingClassWrapper(
			_commercePricingClass.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commercePricingClass.toString();
	}

	@Override
	public CommercePricingClass toUnescapedModel() {
		return new CommercePricingClassWrapper(
			_commercePricingClass.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commercePricingClass.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommercePricingClassWrapper)) {
			return false;
		}

		CommercePricingClassWrapper commercePricingClassWrapper =
			(CommercePricingClassWrapper)object;

		if (Objects.equals(
				_commercePricingClass,
				commercePricingClassWrapper._commercePricingClass)) {

			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _commercePricingClass.getStagedModelType();
	}

	@Override
	public CommercePricingClass getWrappedModel() {
		return _commercePricingClass;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commercePricingClass.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commercePricingClass.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commercePricingClass.resetOriginalValues();
	}

	private final CommercePricingClass _commercePricingClass;

}