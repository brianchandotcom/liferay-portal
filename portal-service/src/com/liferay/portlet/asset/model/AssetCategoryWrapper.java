/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AssetCategory}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetCategory
 * @generated
 */
public class AssetCategoryWrapper implements AssetCategory,
	ModelWrapper<AssetCategory> {
	public AssetCategoryWrapper(AssetCategory assetCategory) {
		_assetCategory = assetCategory;
	}

	@Override
	public Class<?> getModelClass() {
		return AssetCategory.class;
	}

	@Override
	public String getModelClassName() {
		return AssetCategory.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("categoryId", getCategoryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("parentCategoryId", getParentCategoryId());
		attributes.put("leftCategoryId", getLeftCategoryId());
		attributes.put("rightCategoryId", getRightCategoryId());
		attributes.put("name", getName());
		attributes.put("title", getTitle());
		attributes.put("description", getDescription());
		attributes.put("vocabularyId", getVocabularyId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long categoryId = (Long)attributes.get("categoryId");

		if (categoryId != null) {
			setCategoryId(categoryId);
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

		Long parentCategoryId = (Long)attributes.get("parentCategoryId");

		if (parentCategoryId != null) {
			setParentCategoryId(parentCategoryId);
		}

		Long leftCategoryId = (Long)attributes.get("leftCategoryId");

		if (leftCategoryId != null) {
			setLeftCategoryId(leftCategoryId);
		}

		Long rightCategoryId = (Long)attributes.get("rightCategoryId");

		if (rightCategoryId != null) {
			setRightCategoryId(rightCategoryId);
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

		Long vocabularyId = (Long)attributes.get("vocabularyId");

		if (vocabularyId != null) {
			setVocabularyId(vocabularyId);
		}
	}

	/**
	* Returns the primary key of this asset category.
	*
	* @return the primary key of this asset category
	*/
	@Override
	public long getPrimaryKey() {
		return _assetCategory.getPrimaryKey();
	}

	/**
	* Sets the primary key of this asset category.
	*
	* @param primaryKey the primary key of this asset category
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_assetCategory.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this asset category.
	*
	* @return the uuid of this asset category
	*/
	@Override
	public java.lang.String getUuid() {
		return _assetCategory.getUuid();
	}

	/**
	* Sets the uuid of this asset category.
	*
	* @param uuid the uuid of this asset category
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_assetCategory.setUuid(uuid);
	}

	/**
	* Returns the category ID of this asset category.
	*
	* @return the category ID of this asset category
	*/
	@Override
	public long getCategoryId() {
		return _assetCategory.getCategoryId();
	}

	/**
	* Sets the category ID of this asset category.
	*
	* @param categoryId the category ID of this asset category
	*/
	@Override
	public void setCategoryId(long categoryId) {
		_assetCategory.setCategoryId(categoryId);
	}

	/**
	* Returns the group ID of this asset category.
	*
	* @return the group ID of this asset category
	*/
	@Override
	public long getGroupId() {
		return _assetCategory.getGroupId();
	}

	/**
	* Sets the group ID of this asset category.
	*
	* @param groupId the group ID of this asset category
	*/
	@Override
	public void setGroupId(long groupId) {
		_assetCategory.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this asset category.
	*
	* @return the company ID of this asset category
	*/
	@Override
	public long getCompanyId() {
		return _assetCategory.getCompanyId();
	}

	/**
	* Sets the company ID of this asset category.
	*
	* @param companyId the company ID of this asset category
	*/
	@Override
	public void setCompanyId(long companyId) {
		_assetCategory.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this asset category.
	*
	* @return the user ID of this asset category
	*/
	@Override
	public long getUserId() {
		return _assetCategory.getUserId();
	}

	/**
	* Sets the user ID of this asset category.
	*
	* @param userId the user ID of this asset category
	*/
	@Override
	public void setUserId(long userId) {
		_assetCategory.setUserId(userId);
	}

	/**
	* Returns the user uuid of this asset category.
	*
	* @return the user uuid of this asset category
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategory.getUserUuid();
	}

	/**
	* Sets the user uuid of this asset category.
	*
	* @param userUuid the user uuid of this asset category
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_assetCategory.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this asset category.
	*
	* @return the user name of this asset category
	*/
	@Override
	public java.lang.String getUserName() {
		return _assetCategory.getUserName();
	}

	/**
	* Sets the user name of this asset category.
	*
	* @param userName the user name of this asset category
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_assetCategory.setUserName(userName);
	}

	/**
	* Returns the create date of this asset category.
	*
	* @return the create date of this asset category
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _assetCategory.getCreateDate();
	}

	/**
	* Sets the create date of this asset category.
	*
	* @param createDate the create date of this asset category
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_assetCategory.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this asset category.
	*
	* @return the modified date of this asset category
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _assetCategory.getModifiedDate();
	}

	/**
	* Sets the modified date of this asset category.
	*
	* @param modifiedDate the modified date of this asset category
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_assetCategory.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the parent category ID of this asset category.
	*
	* @return the parent category ID of this asset category
	*/
	@Override
	public long getParentCategoryId() {
		return _assetCategory.getParentCategoryId();
	}

	/**
	* Sets the parent category ID of this asset category.
	*
	* @param parentCategoryId the parent category ID of this asset category
	*/
	@Override
	public void setParentCategoryId(long parentCategoryId) {
		_assetCategory.setParentCategoryId(parentCategoryId);
	}

	/**
	* Returns the left category ID of this asset category.
	*
	* @return the left category ID of this asset category
	*/
	@Override
	public long getLeftCategoryId() {
		return _assetCategory.getLeftCategoryId();
	}

	/**
	* Sets the left category ID of this asset category.
	*
	* @param leftCategoryId the left category ID of this asset category
	*/
	@Override
	public void setLeftCategoryId(long leftCategoryId) {
		_assetCategory.setLeftCategoryId(leftCategoryId);
	}

	/**
	* Returns the right category ID of this asset category.
	*
	* @return the right category ID of this asset category
	*/
	@Override
	public long getRightCategoryId() {
		return _assetCategory.getRightCategoryId();
	}

	/**
	* Sets the right category ID of this asset category.
	*
	* @param rightCategoryId the right category ID of this asset category
	*/
	@Override
	public void setRightCategoryId(long rightCategoryId) {
		_assetCategory.setRightCategoryId(rightCategoryId);
	}

	/**
	* Returns the name of this asset category.
	*
	* @return the name of this asset category
	*/
	@Override
	public java.lang.String getName() {
		return _assetCategory.getName();
	}

	/**
	* Sets the name of this asset category.
	*
	* @param name the name of this asset category
	*/
	@Override
	public void setName(java.lang.String name) {
		_assetCategory.setName(name);
	}

	/**
	* Returns the title of this asset category.
	*
	* @return the title of this asset category
	*/
	@Override
	public java.lang.String getTitle() {
		return _assetCategory.getTitle();
	}

	/**
	* Returns the localized title of this asset category in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized title of this asset category
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale) {
		return _assetCategory.getTitle(locale);
	}

	/**
	* Returns the localized title of this asset category in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this asset category. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _assetCategory.getTitle(locale, useDefault);
	}

	/**
	* Returns the localized title of this asset category in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized title of this asset category
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId) {
		return _assetCategory.getTitle(languageId);
	}

	/**
	* Returns the localized title of this asset category in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this asset category
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _assetCategory.getTitle(languageId, useDefault);
	}

	@Override
	public java.lang.String getTitleCurrentLanguageId() {
		return _assetCategory.getTitleCurrentLanguageId();
	}

	@Override
	public java.lang.String getTitleCurrentValue() {
		return _assetCategory.getTitleCurrentValue();
	}

	/**
	* Returns a map of the locales and localized titles of this asset category.
	*
	* @return the locales and localized titles of this asset category
	*/
	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _assetCategory.getTitleMap();
	}

	/**
	* Sets the title of this asset category.
	*
	* @param title the title of this asset category
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_assetCategory.setTitle(title);
	}

	/**
	* Sets the localized title of this asset category in the language.
	*
	* @param title the localized title of this asset category
	* @param locale the locale of the language
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale) {
		_assetCategory.setTitle(title, locale);
	}

	/**
	* Sets the localized title of this asset category in the language, and sets the default locale.
	*
	* @param title the localized title of this asset category
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_assetCategory.setTitle(title, locale, defaultLocale);
	}

	@Override
	public void setTitleCurrentLanguageId(java.lang.String languageId) {
		_assetCategory.setTitleCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized titles of this asset category from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this asset category
	*/
	@Override
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap) {
		_assetCategory.setTitleMap(titleMap);
	}

	/**
	* Sets the localized titles of this asset category from the map of locales and localized titles, and sets the default locale.
	*
	* @param titleMap the locales and localized titles of this asset category
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Locale defaultLocale) {
		_assetCategory.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Returns the description of this asset category.
	*
	* @return the description of this asset category
	*/
	@Override
	public java.lang.String getDescription() {
		return _assetCategory.getDescription();
	}

	/**
	* Returns the localized description of this asset category in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this asset category
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale) {
		return _assetCategory.getDescription(locale);
	}

	/**
	* Returns the localized description of this asset category in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this asset category. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _assetCategory.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this asset category in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this asset category
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId) {
		return _assetCategory.getDescription(languageId);
	}

	/**
	* Returns the localized description of this asset category in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this asset category
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _assetCategory.getDescription(languageId, useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _assetCategory.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _assetCategory.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this asset category.
	*
	* @return the locales and localized descriptions of this asset category
	*/
	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _assetCategory.getDescriptionMap();
	}

	/**
	* Sets the description of this asset category.
	*
	* @param description the description of this asset category
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_assetCategory.setDescription(description);
	}

	/**
	* Sets the localized description of this asset category in the language.
	*
	* @param description the localized description of this asset category
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_assetCategory.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this asset category in the language, and sets the default locale.
	*
	* @param description the localized description of this asset category
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_assetCategory.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_assetCategory.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this asset category from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this asset category
	*/
	@Override
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_assetCategory.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this asset category from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this asset category
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_assetCategory.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Returns the vocabulary ID of this asset category.
	*
	* @return the vocabulary ID of this asset category
	*/
	@Override
	public long getVocabularyId() {
		return _assetCategory.getVocabularyId();
	}

	/**
	* Sets the vocabulary ID of this asset category.
	*
	* @param vocabularyId the vocabulary ID of this asset category
	*/
	@Override
	public void setVocabularyId(long vocabularyId) {
		_assetCategory.setVocabularyId(vocabularyId);
	}

	@Override
	public boolean isNew() {
		return _assetCategory.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_assetCategory.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _assetCategory.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_assetCategory.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _assetCategory.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _assetCategory.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_assetCategory.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _assetCategory.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_assetCategory.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_assetCategory.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_assetCategory.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_assetCategory.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public java.lang.Object clone() {
		return new AssetCategoryWrapper((AssetCategory)_assetCategory.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.asset.model.AssetCategory assetCategory) {
		return _assetCategory.compareTo(assetCategory);
	}

	@Override
	public int hashCode() {
		return _assetCategory.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.asset.model.AssetCategory> toCacheModel() {
		return _assetCategory.toCacheModel();
	}

	@Override
	public com.liferay.portlet.asset.model.AssetCategory toEscapedModel() {
		return new AssetCategoryWrapper(_assetCategory.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.asset.model.AssetCategory toUnescapedModel() {
		return new AssetCategoryWrapper(_assetCategory.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _assetCategory.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _assetCategory.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetCategory.persist();
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategory.getAncestors();
	}

	@Override
	public boolean isRootCategory() {
		return _assetCategory.isRootCategory();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public AssetCategory getWrappedAssetCategory() {
		return _assetCategory;
	}

	@Override
	public AssetCategory getWrappedModel() {
		return _assetCategory;
	}

	@Override
	public void resetOriginalValues() {
		_assetCategory.resetOriginalValues();
	}

	private AssetCategory _assetCategory;
}