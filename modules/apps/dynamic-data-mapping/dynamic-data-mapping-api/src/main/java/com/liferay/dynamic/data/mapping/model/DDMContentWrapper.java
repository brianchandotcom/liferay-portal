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

package com.liferay.dynamic.data.mapping.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DDMContent}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMContent
 * @generated
 */
@ProviderType
public class DDMContentWrapper extends BaseModelWrapper<DDMContent>
	implements DDMContent, ModelWrapper<DDMContent> {
	public DDMContentWrapper(DDMContent ddmContent) {
		super(ddmContent);
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	* Returns the company ID of this ddm content.
	*
	* @return the company ID of this ddm content
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the content ID of this ddm content.
	*
	* @return the content ID of this ddm content
	*/
	@Override
	public long getContentId() {
		return model.getContentId();
	}

	/**
	* Returns the create date of this ddm content.
	*
	* @return the create date of this ddm content
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the data of this ddm content.
	*
	* @return the data of this ddm content
	*/
	@Override
	public String getData() {
		return model.getData();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	* Returns the description of this ddm content.
	*
	* @return the description of this ddm content
	*/
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	* Returns the group ID of this ddm content.
	*
	* @return the group ID of this ddm content
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the modified date of this ddm content.
	*
	* @return the modified date of this ddm content
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the name of this ddm content.
	*
	* @return the name of this ddm content
	*/
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	* Returns the localized name of this ddm content in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this ddm content
	*/
	@Override
	public String getName(java.util.Locale locale) {
		return model.getName(locale);
	}

	/**
	* Returns the localized name of this ddm content in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this ddm content. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public String getName(java.util.Locale locale, boolean useDefault) {
		return model.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this ddm content in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this ddm content
	*/
	@Override
	public String getName(String languageId) {
		return model.getName(languageId);
	}

	/**
	* Returns the localized name of this ddm content in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this ddm content
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
	* Returns a map of the locales and localized names of this ddm content.
	*
	* @return the locales and localized names of this ddm content
	*/
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return model.getNameMap();
	}

	/**
	* Returns the primary key of this ddm content.
	*
	* @return the primary key of this ddm content
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the user ID of this ddm content.
	*
	* @return the user ID of this ddm content
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this ddm content.
	*
	* @return the user name of this ddm content
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this ddm content.
	*
	* @return the user uuid of this ddm content
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	* Returns the uuid of this ddm content.
	*
	* @return the uuid of this ddm content
	*/
	@Override
	public String getUuid() {
		return model.getUuid();
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
	* Sets the company ID of this ddm content.
	*
	* @param companyId the company ID of this ddm content
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the content ID of this ddm content.
	*
	* @param contentId the content ID of this ddm content
	*/
	@Override
	public void setContentId(long contentId) {
		model.setContentId(contentId);
	}

	/**
	* Sets the create date of this ddm content.
	*
	* @param createDate the create date of this ddm content
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the data of this ddm content.
	*
	* @param data the data of this ddm content
	*/
	@Override
	public void setData(String data) {
		model.setData(data);
	}

	/**
	* Sets the description of this ddm content.
	*
	* @param description the description of this ddm content
	*/
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	* Sets the group ID of this ddm content.
	*
	* @param groupId the group ID of this ddm content
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this ddm content.
	*
	* @param modifiedDate the modified date of this ddm content
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this ddm content.
	*
	* @param name the name of this ddm content
	*/
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	* Sets the localized name of this ddm content in the language.
	*
	* @param name the localized name of this ddm content
	* @param locale the locale of the language
	*/
	@Override
	public void setName(String name, java.util.Locale locale) {
		model.setName(name, locale);
	}

	/**
	* Sets the localized name of this ddm content in the language, and sets the default locale.
	*
	* @param name the localized name of this ddm content
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		model.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(String languageId) {
		model.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this ddm content from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this ddm content
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		model.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this ddm content from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this ddm content
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap,
		java.util.Locale defaultLocale) {
		model.setNameMap(nameMap, defaultLocale);
	}

	/**
	* Sets the primary key of this ddm content.
	*
	* @param primaryKey the primary key of this ddm content
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the user ID of this ddm content.
	*
	* @param userId the user ID of this ddm content
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this ddm content.
	*
	* @param userName the user name of this ddm content
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this ddm content.
	*
	* @param userUuid the user uuid of this ddm content
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this ddm content.
	*
	* @param uuid the uuid of this ddm content
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
	protected DDMContentWrapper wrap(DDMContent ddmContent) {
		return new DDMContentWrapper(ddmContent);
	}
}