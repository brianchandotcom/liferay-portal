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

package com.liferay.commerce.product.model;

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
 * This class is a wrapper for {@link CPSpecificationOption}.
 * </p>
 *
 * @author Marco Leo
 * @see CPSpecificationOption
 * @generated
 */
@ProviderType
public class CPSpecificationOptionWrapper implements CPSpecificationOption,
	ModelWrapper<CPSpecificationOption> {
	public CPSpecificationOptionWrapper(
		CPSpecificationOption cpSpecificationOption) {
		_cpSpecificationOption = cpSpecificationOption;
	}

	@Override
	public Class<?> getModelClass() {
		return CPSpecificationOption.class;
	}

	@Override
	public String getModelClassName() {
		return CPSpecificationOption.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("CPSpecificationOptionId", getCPSpecificationOptionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CPOptionCategoryId", getCPOptionCategoryId());
		attributes.put("title", getTitle());
		attributes.put("description", getDescription());
		attributes.put("facetable", isFacetable());
		attributes.put("key", getKey());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long CPSpecificationOptionId = (Long)attributes.get(
				"CPSpecificationOptionId");

		if (CPSpecificationOptionId != null) {
			setCPSpecificationOptionId(CPSpecificationOptionId);
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

		Long CPOptionCategoryId = (Long)attributes.get("CPOptionCategoryId");

		if (CPOptionCategoryId != null) {
			setCPOptionCategoryId(CPOptionCategoryId);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Boolean facetable = (Boolean)attributes.get("facetable");

		if (facetable != null) {
			setFacetable(facetable);
		}

		String key = (String)attributes.get("key");

		if (key != null) {
			setKey(key);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public Object clone() {
		return new CPSpecificationOptionWrapper((CPSpecificationOption)_cpSpecificationOption.clone());
	}

	@Override
	public int compareTo(CPSpecificationOption cpSpecificationOption) {
		return _cpSpecificationOption.compareTo(cpSpecificationOption);
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return _cpSpecificationOption.getAvailableLanguageIds();
	}

	/**
	* Returns the company ID of this cp specification option.
	*
	* @return the company ID of this cp specification option
	*/
	@Override
	public long getCompanyId() {
		return _cpSpecificationOption.getCompanyId();
	}

	@Override
	public CPOptionCategory getCPOptionCategory()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpSpecificationOption.getCPOptionCategory();
	}

	/**
	* Returns the cp option category ID of this cp specification option.
	*
	* @return the cp option category ID of this cp specification option
	*/
	@Override
	public long getCPOptionCategoryId() {
		return _cpSpecificationOption.getCPOptionCategoryId();
	}

	/**
	* Returns the cp specification option ID of this cp specification option.
	*
	* @return the cp specification option ID of this cp specification option
	*/
	@Override
	public long getCPSpecificationOptionId() {
		return _cpSpecificationOption.getCPSpecificationOptionId();
	}

	/**
	* Returns the create date of this cp specification option.
	*
	* @return the create date of this cp specification option
	*/
	@Override
	public Date getCreateDate() {
		return _cpSpecificationOption.getCreateDate();
	}

	@Override
	public String getDefaultLanguageId() {
		return _cpSpecificationOption.getDefaultLanguageId();
	}

	/**
	* Returns the description of this cp specification option.
	*
	* @return the description of this cp specification option
	*/
	@Override
	public String getDescription() {
		return _cpSpecificationOption.getDescription();
	}

	/**
	* Returns the localized description of this cp specification option in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this cp specification option
	*/
	@Override
	public String getDescription(java.util.Locale locale) {
		return _cpSpecificationOption.getDescription(locale);
	}

	/**
	* Returns the localized description of this cp specification option in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this cp specification option. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public String getDescription(java.util.Locale locale, boolean useDefault) {
		return _cpSpecificationOption.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this cp specification option in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this cp specification option
	*/
	@Override
	public String getDescription(String languageId) {
		return _cpSpecificationOption.getDescription(languageId);
	}

	/**
	* Returns the localized description of this cp specification option in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this cp specification option
	*/
	@Override
	public String getDescription(String languageId, boolean useDefault) {
		return _cpSpecificationOption.getDescription(languageId, useDefault);
	}

	@Override
	public String getDescriptionCurrentLanguageId() {
		return _cpSpecificationOption.getDescriptionCurrentLanguageId();
	}

	@Override
	public String getDescriptionCurrentValue() {
		return _cpSpecificationOption.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this cp specification option.
	*
	* @return the locales and localized descriptions of this cp specification option
	*/
	@Override
	public Map<java.util.Locale, String> getDescriptionMap() {
		return _cpSpecificationOption.getDescriptionMap();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cpSpecificationOption.getExpandoBridge();
	}

	/**
	* Returns the facetable of this cp specification option.
	*
	* @return the facetable of this cp specification option
	*/
	@Override
	public boolean getFacetable() {
		return _cpSpecificationOption.getFacetable();
	}

	/**
	* Returns the group ID of this cp specification option.
	*
	* @return the group ID of this cp specification option
	*/
	@Override
	public long getGroupId() {
		return _cpSpecificationOption.getGroupId();
	}

	/**
	* Returns the key of this cp specification option.
	*
	* @return the key of this cp specification option
	*/
	@Override
	public String getKey() {
		return _cpSpecificationOption.getKey();
	}

	/**
	* Returns the last publish date of this cp specification option.
	*
	* @return the last publish date of this cp specification option
	*/
	@Override
	public Date getLastPublishDate() {
		return _cpSpecificationOption.getLastPublishDate();
	}

	/**
	* Returns the modified date of this cp specification option.
	*
	* @return the modified date of this cp specification option
	*/
	@Override
	public Date getModifiedDate() {
		return _cpSpecificationOption.getModifiedDate();
	}

	/**
	* Returns the primary key of this cp specification option.
	*
	* @return the primary key of this cp specification option
	*/
	@Override
	public long getPrimaryKey() {
		return _cpSpecificationOption.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cpSpecificationOption.getPrimaryKeyObj();
	}

	/**
	* Returns the title of this cp specification option.
	*
	* @return the title of this cp specification option
	*/
	@Override
	public String getTitle() {
		return _cpSpecificationOption.getTitle();
	}

	/**
	* Returns the localized title of this cp specification option in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized title of this cp specification option
	*/
	@Override
	public String getTitle(java.util.Locale locale) {
		return _cpSpecificationOption.getTitle(locale);
	}

	/**
	* Returns the localized title of this cp specification option in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this cp specification option. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public String getTitle(java.util.Locale locale, boolean useDefault) {
		return _cpSpecificationOption.getTitle(locale, useDefault);
	}

	/**
	* Returns the localized title of this cp specification option in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized title of this cp specification option
	*/
	@Override
	public String getTitle(String languageId) {
		return _cpSpecificationOption.getTitle(languageId);
	}

	/**
	* Returns the localized title of this cp specification option in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this cp specification option
	*/
	@Override
	public String getTitle(String languageId, boolean useDefault) {
		return _cpSpecificationOption.getTitle(languageId, useDefault);
	}

	@Override
	public String getTitleCurrentLanguageId() {
		return _cpSpecificationOption.getTitleCurrentLanguageId();
	}

	@Override
	public String getTitleCurrentValue() {
		return _cpSpecificationOption.getTitleCurrentValue();
	}

	/**
	* Returns a map of the locales and localized titles of this cp specification option.
	*
	* @return the locales and localized titles of this cp specification option
	*/
	@Override
	public Map<java.util.Locale, String> getTitleMap() {
		return _cpSpecificationOption.getTitleMap();
	}

	/**
	* Returns the user ID of this cp specification option.
	*
	* @return the user ID of this cp specification option
	*/
	@Override
	public long getUserId() {
		return _cpSpecificationOption.getUserId();
	}

	/**
	* Returns the user name of this cp specification option.
	*
	* @return the user name of this cp specification option
	*/
	@Override
	public String getUserName() {
		return _cpSpecificationOption.getUserName();
	}

	/**
	* Returns the user uuid of this cp specification option.
	*
	* @return the user uuid of this cp specification option
	*/
	@Override
	public String getUserUuid() {
		return _cpSpecificationOption.getUserUuid();
	}

	/**
	* Returns the uuid of this cp specification option.
	*
	* @return the uuid of this cp specification option
	*/
	@Override
	public String getUuid() {
		return _cpSpecificationOption.getUuid();
	}

	@Override
	public int hashCode() {
		return _cpSpecificationOption.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _cpSpecificationOption.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cpSpecificationOption.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this cp specification option is facetable.
	*
	* @return <code>true</code> if this cp specification option is facetable; <code>false</code> otherwise
	*/
	@Override
	public boolean isFacetable() {
		return _cpSpecificationOption.isFacetable();
	}

	@Override
	public boolean isNew() {
		return _cpSpecificationOption.isNew();
	}

	@Override
	public void persist() {
		_cpSpecificationOption.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_cpSpecificationOption.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_cpSpecificationOption.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cpSpecificationOption.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this cp specification option.
	*
	* @param companyId the company ID of this cp specification option
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cpSpecificationOption.setCompanyId(companyId);
	}

	/**
	* Sets the cp option category ID of this cp specification option.
	*
	* @param CPOptionCategoryId the cp option category ID of this cp specification option
	*/
	@Override
	public void setCPOptionCategoryId(long CPOptionCategoryId) {
		_cpSpecificationOption.setCPOptionCategoryId(CPOptionCategoryId);
	}

	/**
	* Sets the cp specification option ID of this cp specification option.
	*
	* @param CPSpecificationOptionId the cp specification option ID of this cp specification option
	*/
	@Override
	public void setCPSpecificationOptionId(long CPSpecificationOptionId) {
		_cpSpecificationOption.setCPSpecificationOptionId(CPSpecificationOptionId);
	}

	/**
	* Sets the create date of this cp specification option.
	*
	* @param createDate the create date of this cp specification option
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cpSpecificationOption.setCreateDate(createDate);
	}

	/**
	* Sets the description of this cp specification option.
	*
	* @param description the description of this cp specification option
	*/
	@Override
	public void setDescription(String description) {
		_cpSpecificationOption.setDescription(description);
	}

	/**
	* Sets the localized description of this cp specification option in the language.
	*
	* @param description the localized description of this cp specification option
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(String description, java.util.Locale locale) {
		_cpSpecificationOption.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this cp specification option in the language, and sets the default locale.
	*
	* @param description the localized description of this cp specification option
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(String description, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_cpSpecificationOption.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(String languageId) {
		_cpSpecificationOption.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this cp specification option from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this cp specification option
	*/
	@Override
	public void setDescriptionMap(Map<java.util.Locale, String> descriptionMap) {
		_cpSpecificationOption.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this cp specification option from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this cp specification option
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap,
		java.util.Locale defaultLocale) {
		_cpSpecificationOption.setDescriptionMap(descriptionMap, defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cpSpecificationOption.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cpSpecificationOption.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cpSpecificationOption.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets whether this cp specification option is facetable.
	*
	* @param facetable the facetable of this cp specification option
	*/
	@Override
	public void setFacetable(boolean facetable) {
		_cpSpecificationOption.setFacetable(facetable);
	}

	/**
	* Sets the group ID of this cp specification option.
	*
	* @param groupId the group ID of this cp specification option
	*/
	@Override
	public void setGroupId(long groupId) {
		_cpSpecificationOption.setGroupId(groupId);
	}

	/**
	* Sets the key of this cp specification option.
	*
	* @param key the key of this cp specification option
	*/
	@Override
	public void setKey(String key) {
		_cpSpecificationOption.setKey(key);
	}

	/**
	* Sets the last publish date of this cp specification option.
	*
	* @param lastPublishDate the last publish date of this cp specification option
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_cpSpecificationOption.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this cp specification option.
	*
	* @param modifiedDate the modified date of this cp specification option
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cpSpecificationOption.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_cpSpecificationOption.setNew(n);
	}

	/**
	* Sets the primary key of this cp specification option.
	*
	* @param primaryKey the primary key of this cp specification option
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cpSpecificationOption.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cpSpecificationOption.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the title of this cp specification option.
	*
	* @param title the title of this cp specification option
	*/
	@Override
	public void setTitle(String title) {
		_cpSpecificationOption.setTitle(title);
	}

	/**
	* Sets the localized title of this cp specification option in the language.
	*
	* @param title the localized title of this cp specification option
	* @param locale the locale of the language
	*/
	@Override
	public void setTitle(String title, java.util.Locale locale) {
		_cpSpecificationOption.setTitle(title, locale);
	}

	/**
	* Sets the localized title of this cp specification option in the language, and sets the default locale.
	*
	* @param title the localized title of this cp specification option
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitle(String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_cpSpecificationOption.setTitle(title, locale, defaultLocale);
	}

	@Override
	public void setTitleCurrentLanguageId(String languageId) {
		_cpSpecificationOption.setTitleCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized titles of this cp specification option from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this cp specification option
	*/
	@Override
	public void setTitleMap(Map<java.util.Locale, String> titleMap) {
		_cpSpecificationOption.setTitleMap(titleMap);
	}

	/**
	* Sets the localized titles of this cp specification option from the map of locales and localized titles, and sets the default locale.
	*
	* @param titleMap the locales and localized titles of this cp specification option
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitleMap(Map<java.util.Locale, String> titleMap,
		java.util.Locale defaultLocale) {
		_cpSpecificationOption.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Sets the user ID of this cp specification option.
	*
	* @param userId the user ID of this cp specification option
	*/
	@Override
	public void setUserId(long userId) {
		_cpSpecificationOption.setUserId(userId);
	}

	/**
	* Sets the user name of this cp specification option.
	*
	* @param userName the user name of this cp specification option
	*/
	@Override
	public void setUserName(String userName) {
		_cpSpecificationOption.setUserName(userName);
	}

	/**
	* Sets the user uuid of this cp specification option.
	*
	* @param userUuid the user uuid of this cp specification option
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_cpSpecificationOption.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this cp specification option.
	*
	* @param uuid the uuid of this cp specification option
	*/
	@Override
	public void setUuid(String uuid) {
		_cpSpecificationOption.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CPSpecificationOption> toCacheModel() {
		return _cpSpecificationOption.toCacheModel();
	}

	@Override
	public CPSpecificationOption toEscapedModel() {
		return new CPSpecificationOptionWrapper(_cpSpecificationOption.toEscapedModel());
	}

	@Override
	public String toString() {
		return _cpSpecificationOption.toString();
	}

	@Override
	public CPSpecificationOption toUnescapedModel() {
		return new CPSpecificationOptionWrapper(_cpSpecificationOption.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _cpSpecificationOption.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPSpecificationOptionWrapper)) {
			return false;
		}

		CPSpecificationOptionWrapper cpSpecificationOptionWrapper = (CPSpecificationOptionWrapper)obj;

		if (Objects.equals(_cpSpecificationOption,
					cpSpecificationOptionWrapper._cpSpecificationOption)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _cpSpecificationOption.getStagedModelType();
	}

	@Override
	public CPSpecificationOption getWrappedModel() {
		return _cpSpecificationOption;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cpSpecificationOption.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cpSpecificationOption.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cpSpecificationOption.resetOriginalValues();
	}

	private final CPSpecificationOption _cpSpecificationOption;
}