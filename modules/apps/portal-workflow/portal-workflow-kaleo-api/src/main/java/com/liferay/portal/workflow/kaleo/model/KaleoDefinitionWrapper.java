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

package com.liferay.portal.workflow.kaleo.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link KaleoDefinition}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoDefinition
 * @generated
 */
@ProviderType
public class KaleoDefinitionWrapper extends BaseModelWrapper<KaleoDefinition>
	implements KaleoDefinition, ModelWrapper<KaleoDefinition> {
	public KaleoDefinitionWrapper(KaleoDefinition kaleoDefinition) {
		super(kaleoDefinition);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("kaleoDefinitionId", getKaleoDefinitionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("title", getTitle());
		attributes.put("description", getDescription());
		attributes.put("content", getContent());
		attributes.put("version", getVersion());
		attributes.put("active", isActive());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long kaleoDefinitionId = (Long)attributes.get("kaleoDefinitionId");

		if (kaleoDefinitionId != null) {
			setKaleoDefinitionId(kaleoDefinitionId);
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

		String content = (String)attributes.get("content");

		if (content != null) {
			setContent(content);
		}

		Integer version = (Integer)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}
	}

	/**
	* Returns the active of this kaleo definition.
	*
	* @return the active of this kaleo definition
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
	* Returns the company ID of this kaleo definition.
	*
	* @return the company ID of this kaleo definition
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the content of this kaleo definition.
	*
	* @return the content of this kaleo definition
	*/
	@Override
	public String getContent() {
		return model.getContent();
	}

	/**
	* Returns the create date of this kaleo definition.
	*
	* @return the create date of this kaleo definition
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
	* Returns the description of this kaleo definition.
	*
	* @return the description of this kaleo definition
	*/
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	* Returns the group ID of this kaleo definition.
	*
	* @return the group ID of this kaleo definition
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the kaleo definition ID of this kaleo definition.
	*
	* @return the kaleo definition ID of this kaleo definition
	*/
	@Override
	public long getKaleoDefinitionId() {
		return model.getKaleoDefinitionId();
	}

	@Override
	public java.util.List<KaleoDefinitionVersion> getKaleoDefinitionVersions()
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getKaleoDefinitionVersions();
	}

	/**
	* Returns the modified date of this kaleo definition.
	*
	* @return the modified date of this kaleo definition
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the mvcc version of this kaleo definition.
	*
	* @return the mvcc version of this kaleo definition
	*/
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	* Returns the name of this kaleo definition.
	*
	* @return the name of this kaleo definition
	*/
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	* Returns the primary key of this kaleo definition.
	*
	* @return the primary key of this kaleo definition
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the title of this kaleo definition.
	*
	* @return the title of this kaleo definition
	*/
	@Override
	public String getTitle() {
		return model.getTitle();
	}

	/**
	* Returns the localized title of this kaleo definition in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized title of this kaleo definition
	*/
	@Override
	public String getTitle(java.util.Locale locale) {
		return model.getTitle(locale);
	}

	/**
	* Returns the localized title of this kaleo definition in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this kaleo definition. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public String getTitle(java.util.Locale locale, boolean useDefault) {
		return model.getTitle(locale, useDefault);
	}

	/**
	* Returns the localized title of this kaleo definition in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized title of this kaleo definition
	*/
	@Override
	public String getTitle(String languageId) {
		return model.getTitle(languageId);
	}

	/**
	* Returns the localized title of this kaleo definition in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this kaleo definition
	*/
	@Override
	public String getTitle(String languageId, boolean useDefault) {
		return model.getTitle(languageId, useDefault);
	}

	@Override
	public String getTitleCurrentLanguageId() {
		return model.getTitleCurrentLanguageId();
	}

	@Override
	public String getTitleCurrentValue() {
		return model.getTitleCurrentValue();
	}

	/**
	* Returns a map of the locales and localized titles of this kaleo definition.
	*
	* @return the locales and localized titles of this kaleo definition
	*/
	@Override
	public Map<java.util.Locale, String> getTitleMap() {
		return model.getTitleMap();
	}

	/**
	* Returns the user ID of this kaleo definition.
	*
	* @return the user ID of this kaleo definition
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this kaleo definition.
	*
	* @return the user name of this kaleo definition
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this kaleo definition.
	*
	* @return the user uuid of this kaleo definition
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	* Returns the version of this kaleo definition.
	*
	* @return the version of this kaleo definition
	*/
	@Override
	public int getVersion() {
		return model.getVersion();
	}

	/**
	* Returns <code>true</code> if this kaleo definition is active.
	*
	* @return <code>true</code> if this kaleo definition is active; <code>false</code> otherwise
	*/
	@Override
	public boolean isActive() {
		return model.isActive();
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
	* Sets whether this kaleo definition is active.
	*
	* @param active the active of this kaleo definition
	*/
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	* Sets the company ID of this kaleo definition.
	*
	* @param companyId the company ID of this kaleo definition
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the content of this kaleo definition.
	*
	* @param content the content of this kaleo definition
	*/
	@Override
	public void setContent(String content) {
		model.setContent(content);
	}

	/**
	* Sets the create date of this kaleo definition.
	*
	* @param createDate the create date of this kaleo definition
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the description of this kaleo definition.
	*
	* @param description the description of this kaleo definition
	*/
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	* Sets the group ID of this kaleo definition.
	*
	* @param groupId the group ID of this kaleo definition
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the kaleo definition ID of this kaleo definition.
	*
	* @param kaleoDefinitionId the kaleo definition ID of this kaleo definition
	*/
	@Override
	public void setKaleoDefinitionId(long kaleoDefinitionId) {
		model.setKaleoDefinitionId(kaleoDefinitionId);
	}

	/**
	* Sets the modified date of this kaleo definition.
	*
	* @param modifiedDate the modified date of this kaleo definition
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the mvcc version of this kaleo definition.
	*
	* @param mvccVersion the mvcc version of this kaleo definition
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the name of this kaleo definition.
	*
	* @param name the name of this kaleo definition
	*/
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	* Sets the primary key of this kaleo definition.
	*
	* @param primaryKey the primary key of this kaleo definition
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the title of this kaleo definition.
	*
	* @param title the title of this kaleo definition
	*/
	@Override
	public void setTitle(String title) {
		model.setTitle(title);
	}

	/**
	* Sets the localized title of this kaleo definition in the language.
	*
	* @param title the localized title of this kaleo definition
	* @param locale the locale of the language
	*/
	@Override
	public void setTitle(String title, java.util.Locale locale) {
		model.setTitle(title, locale);
	}

	/**
	* Sets the localized title of this kaleo definition in the language, and sets the default locale.
	*
	* @param title the localized title of this kaleo definition
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitle(String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		model.setTitle(title, locale, defaultLocale);
	}

	@Override
	public void setTitleCurrentLanguageId(String languageId) {
		model.setTitleCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized titles of this kaleo definition from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this kaleo definition
	*/
	@Override
	public void setTitleMap(Map<java.util.Locale, String> titleMap) {
		model.setTitleMap(titleMap);
	}

	/**
	* Sets the localized titles of this kaleo definition from the map of locales and localized titles, and sets the default locale.
	*
	* @param titleMap the locales and localized titles of this kaleo definition
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitleMap(Map<java.util.Locale, String> titleMap,
		java.util.Locale defaultLocale) {
		model.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Sets the user ID of this kaleo definition.
	*
	* @param userId the user ID of this kaleo definition
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this kaleo definition.
	*
	* @param userName the user name of this kaleo definition
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this kaleo definition.
	*
	* @param userUuid the user uuid of this kaleo definition
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	* Sets the version of this kaleo definition.
	*
	* @param version the version of this kaleo definition
	*/
	@Override
	public void setVersion(int version) {
		model.setVersion(version);
	}

	@Override
	protected KaleoDefinitionWrapper wrap(KaleoDefinition kaleoDefinition) {
		return new KaleoDefinitionWrapper(kaleoDefinition);
	}
}