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
 * This class is a wrapper for {@link CPOptionCategory}.
 * </p>
 *
 * @author Marco Leo
 * @see CPOptionCategory
 * @generated
 */
@ProviderType
public class CPOptionCategoryWrapper implements CPOptionCategory,
	ModelWrapper<CPOptionCategory> {
	public CPOptionCategoryWrapper(CPOptionCategory cpOptionCategory) {
		_cpOptionCategory = cpOptionCategory;
	}

	@Override
	public Class<?> getModelClass() {
		return CPOptionCategory.class;
	}

	@Override
	public String getModelClassName() {
		return CPOptionCategory.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("CPOptionCategoryId", getCPOptionCategoryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("title", getTitle());
		attributes.put("description", getDescription());
		attributes.put("priority", getPriority());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long CPOptionCategoryId = (Long)attributes.get("CPOptionCategoryId");

		if (CPOptionCategoryId != null) {
			setCPOptionCategoryId(CPOptionCategoryId);
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

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public CPOptionCategory toEscapedModel() {
		return new CPOptionCategoryWrapper(_cpOptionCategory.toEscapedModel());
	}

	@Override
	public CPOptionCategory toUnescapedModel() {
		return new CPOptionCategoryWrapper(_cpOptionCategory.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _cpOptionCategory.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cpOptionCategory.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _cpOptionCategory.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cpOptionCategory.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CPOptionCategory> toCacheModel() {
		return _cpOptionCategory.toCacheModel();
	}

	@Override
	public int compareTo(CPOptionCategory cpOptionCategory) {
		return _cpOptionCategory.compareTo(cpOptionCategory);
	}

	/**
	* Returns the priority of this cp option category.
	*
	* @return the priority of this cp option category
	*/
	@Override
	public int getPriority() {
		return _cpOptionCategory.getPriority();
	}

	@Override
	public int hashCode() {
		return _cpOptionCategory.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cpOptionCategory.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new CPOptionCategoryWrapper((CPOptionCategory)_cpOptionCategory.clone());
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _cpOptionCategory.getDefaultLanguageId();
	}

	/**
	* Returns the description of this cp option category.
	*
	* @return the description of this cp option category
	*/
	@Override
	public java.lang.String getDescription() {
		return _cpOptionCategory.getDescription();
	}

	/**
	* Returns the localized description of this cp option category in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this cp option category
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId) {
		return _cpOptionCategory.getDescription(languageId);
	}

	/**
	* Returns the localized description of this cp option category in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this cp option category
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _cpOptionCategory.getDescription(languageId, useDefault);
	}

	/**
	* Returns the localized description of this cp option category in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this cp option category
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale) {
		return _cpOptionCategory.getDescription(locale);
	}

	/**
	* Returns the localized description of this cp option category in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this cp option category. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _cpOptionCategory.getDescription(locale, useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _cpOptionCategory.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _cpOptionCategory.getDescriptionCurrentValue();
	}

	/**
	* Returns the name of this cp option category.
	*
	* @return the name of this cp option category
	*/
	@Override
	public java.lang.String getName() {
		return _cpOptionCategory.getName();
	}

	/**
	* Returns the title of this cp option category.
	*
	* @return the title of this cp option category
	*/
	@Override
	public java.lang.String getTitle() {
		return _cpOptionCategory.getTitle();
	}

	/**
	* Returns the localized title of this cp option category in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized title of this cp option category
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId) {
		return _cpOptionCategory.getTitle(languageId);
	}

	/**
	* Returns the localized title of this cp option category in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this cp option category
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _cpOptionCategory.getTitle(languageId, useDefault);
	}

	/**
	* Returns the localized title of this cp option category in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized title of this cp option category
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale) {
		return _cpOptionCategory.getTitle(locale);
	}

	/**
	* Returns the localized title of this cp option category in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this cp option category. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _cpOptionCategory.getTitle(locale, useDefault);
	}

	@Override
	public java.lang.String getTitleCurrentLanguageId() {
		return _cpOptionCategory.getTitleCurrentLanguageId();
	}

	@Override
	public java.lang.String getTitleCurrentValue() {
		return _cpOptionCategory.getTitleCurrentValue();
	}

	/**
	* Returns the user name of this cp option category.
	*
	* @return the user name of this cp option category
	*/
	@Override
	public java.lang.String getUserName() {
		return _cpOptionCategory.getUserName();
	}

	/**
	* Returns the user uuid of this cp option category.
	*
	* @return the user uuid of this cp option category
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _cpOptionCategory.getUserUuid();
	}

	/**
	* Returns the uuid of this cp option category.
	*
	* @return the uuid of this cp option category
	*/
	@Override
	public java.lang.String getUuid() {
		return _cpOptionCategory.getUuid();
	}

	@Override
	public java.lang.String toString() {
		return _cpOptionCategory.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _cpOptionCategory.toXmlString();
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _cpOptionCategory.getAvailableLanguageIds();
	}

	/**
	* Returns the create date of this cp option category.
	*
	* @return the create date of this cp option category
	*/
	@Override
	public Date getCreateDate() {
		return _cpOptionCategory.getCreateDate();
	}

	/**
	* Returns the last publish date of this cp option category.
	*
	* @return the last publish date of this cp option category
	*/
	@Override
	public Date getLastPublishDate() {
		return _cpOptionCategory.getLastPublishDate();
	}

	/**
	* Returns the modified date of this cp option category.
	*
	* @return the modified date of this cp option category
	*/
	@Override
	public Date getModifiedDate() {
		return _cpOptionCategory.getModifiedDate();
	}

	/**
	* Returns a map of the locales and localized descriptions of this cp option category.
	*
	* @return the locales and localized descriptions of this cp option category
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _cpOptionCategory.getDescriptionMap();
	}

	/**
	* Returns a map of the locales and localized titles of this cp option category.
	*
	* @return the locales and localized titles of this cp option category
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _cpOptionCategory.getTitleMap();
	}

	/**
	* Returns the cp option category ID of this cp option category.
	*
	* @return the cp option category ID of this cp option category
	*/
	@Override
	public long getCPOptionCategoryId() {
		return _cpOptionCategory.getCPOptionCategoryId();
	}

	/**
	* Returns the company ID of this cp option category.
	*
	* @return the company ID of this cp option category
	*/
	@Override
	public long getCompanyId() {
		return _cpOptionCategory.getCompanyId();
	}

	/**
	* Returns the group ID of this cp option category.
	*
	* @return the group ID of this cp option category
	*/
	@Override
	public long getGroupId() {
		return _cpOptionCategory.getGroupId();
	}

	/**
	* Returns the primary key of this cp option category.
	*
	* @return the primary key of this cp option category
	*/
	@Override
	public long getPrimaryKey() {
		return _cpOptionCategory.getPrimaryKey();
	}

	/**
	* Returns the user ID of this cp option category.
	*
	* @return the user ID of this cp option category
	*/
	@Override
	public long getUserId() {
		return _cpOptionCategory.getUserId();
	}

	@Override
	public void persist() {
		_cpOptionCategory.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_cpOptionCategory.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_cpOptionCategory.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	* Sets the cp option category ID of this cp option category.
	*
	* @param CPOptionCategoryId the cp option category ID of this cp option category
	*/
	@Override
	public void setCPOptionCategoryId(long CPOptionCategoryId) {
		_cpOptionCategory.setCPOptionCategoryId(CPOptionCategoryId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cpOptionCategory.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this cp option category.
	*
	* @param companyId the company ID of this cp option category
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cpOptionCategory.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this cp option category.
	*
	* @param createDate the create date of this cp option category
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cpOptionCategory.setCreateDate(createDate);
	}

	/**
	* Sets the description of this cp option category.
	*
	* @param description the description of this cp option category
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_cpOptionCategory.setDescription(description);
	}

	/**
	* Sets the localized description of this cp option category in the language.
	*
	* @param description the localized description of this cp option category
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_cpOptionCategory.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this cp option category in the language, and sets the default locale.
	*
	* @param description the localized description of this cp option category
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_cpOptionCategory.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_cpOptionCategory.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this cp option category from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this cp option category
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap) {
		_cpOptionCategory.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this cp option category from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this cp option category
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_cpOptionCategory.setDescriptionMap(descriptionMap, defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cpOptionCategory.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cpOptionCategory.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cpOptionCategory.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this cp option category.
	*
	* @param groupId the group ID of this cp option category
	*/
	@Override
	public void setGroupId(long groupId) {
		_cpOptionCategory.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this cp option category.
	*
	* @param lastPublishDate the last publish date of this cp option category
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_cpOptionCategory.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this cp option category.
	*
	* @param modifiedDate the modified date of this cp option category
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cpOptionCategory.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this cp option category.
	*
	* @param name the name of this cp option category
	*/
	@Override
	public void setName(java.lang.String name) {
		_cpOptionCategory.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_cpOptionCategory.setNew(n);
	}

	/**
	* Sets the primary key of this cp option category.
	*
	* @param primaryKey the primary key of this cp option category
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cpOptionCategory.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cpOptionCategory.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this cp option category.
	*
	* @param priority the priority of this cp option category
	*/
	@Override
	public void setPriority(int priority) {
		_cpOptionCategory.setPriority(priority);
	}

	/**
	* Sets the title of this cp option category.
	*
	* @param title the title of this cp option category
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_cpOptionCategory.setTitle(title);
	}

	/**
	* Sets the localized title of this cp option category in the language.
	*
	* @param title the localized title of this cp option category
	* @param locale the locale of the language
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale) {
		_cpOptionCategory.setTitle(title, locale);
	}

	/**
	* Sets the localized title of this cp option category in the language, and sets the default locale.
	*
	* @param title the localized title of this cp option category
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_cpOptionCategory.setTitle(title, locale, defaultLocale);
	}

	@Override
	public void setTitleCurrentLanguageId(java.lang.String languageId) {
		_cpOptionCategory.setTitleCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized titles of this cp option category from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this cp option category
	*/
	@Override
	public void setTitleMap(Map<java.util.Locale, java.lang.String> titleMap) {
		_cpOptionCategory.setTitleMap(titleMap);
	}

	/**
	* Sets the localized titles of this cp option category from the map of locales and localized titles, and sets the default locale.
	*
	* @param titleMap the locales and localized titles of this cp option category
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitleMap(Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Locale defaultLocale) {
		_cpOptionCategory.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Sets the user ID of this cp option category.
	*
	* @param userId the user ID of this cp option category
	*/
	@Override
	public void setUserId(long userId) {
		_cpOptionCategory.setUserId(userId);
	}

	/**
	* Sets the user name of this cp option category.
	*
	* @param userName the user name of this cp option category
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_cpOptionCategory.setUserName(userName);
	}

	/**
	* Sets the user uuid of this cp option category.
	*
	* @param userUuid the user uuid of this cp option category
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_cpOptionCategory.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this cp option category.
	*
	* @param uuid the uuid of this cp option category
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_cpOptionCategory.setUuid(uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPOptionCategoryWrapper)) {
			return false;
		}

		CPOptionCategoryWrapper cpOptionCategoryWrapper = (CPOptionCategoryWrapper)obj;

		if (Objects.equals(_cpOptionCategory,
					cpOptionCategoryWrapper._cpOptionCategory)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _cpOptionCategory.getStagedModelType();
	}

	@Override
	public CPOptionCategory getWrappedModel() {
		return _cpOptionCategory;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cpOptionCategory.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cpOptionCategory.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cpOptionCategory.resetOriginalValues();
	}

	private final CPOptionCategory _cpOptionCategory;
}