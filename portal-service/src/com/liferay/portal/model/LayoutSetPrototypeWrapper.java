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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;

import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link LayoutSetPrototype}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetPrototype
 * @generated
 */
@ProviderType
public class LayoutSetPrototypeWrapper implements LayoutSetPrototype,
	ModelWrapper<LayoutSetPrototype> {
	public LayoutSetPrototypeWrapper(LayoutSetPrototype layoutSetPrototype) {
		_layoutSetPrototype = layoutSetPrototype;
	}

	@Override
	public Class<?> getModelClass() {
		return LayoutSetPrototype.class;
	}

	@Override
	public String getModelClassName() {
		return LayoutSetPrototype.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("layoutSetPrototypeId", getLayoutSetPrototypeId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("settings", getSettings());
		attributes.put("active", getActive());

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

		Long layoutSetPrototypeId = (Long)attributes.get("layoutSetPrototypeId");

		if (layoutSetPrototypeId != null) {
			setLayoutSetPrototypeId(layoutSetPrototypeId);
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

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String settings = (String)attributes.get("settings");

		if (settings != null) {
			setSettings(settings);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new LayoutSetPrototypeWrapper((LayoutSetPrototype)_layoutSetPrototype.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portal.model.LayoutSetPrototype layoutSetPrototype) {
		return _layoutSetPrototype.compareTo(layoutSetPrototype);
	}

	/**
	* Returns the active of this layout set prototype.
	*
	* @return the active of this layout set prototype
	*/
	@Override
	public boolean getActive() {
		return _layoutSetPrototype.getActive();
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _layoutSetPrototype.getAvailableLanguageIds();
	}

	/**
	* Returns the company ID of this layout set prototype.
	*
	* @return the company ID of this layout set prototype
	*/
	@Override
	public long getCompanyId() {
		return _layoutSetPrototype.getCompanyId();
	}

	/**
	* Returns the create date of this layout set prototype.
	*
	* @return the create date of this layout set prototype
	*/
	@Override
	public Date getCreateDate() {
		return _layoutSetPrototype.getCreateDate();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _layoutSetPrototype.getDefaultLanguageId();
	}

	/**
	* Returns the description of this layout set prototype.
	*
	* @return the description of this layout set prototype
	*/
	@Override
	public java.lang.String getDescription() {
		return _layoutSetPrototype.getDescription();
	}

	/**
	* Returns the localized description of this layout set prototype in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this layout set prototype
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId) {
		return _layoutSetPrototype.getDescription(languageId);
	}

	/**
	* Returns the localized description of this layout set prototype in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this layout set prototype
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _layoutSetPrototype.getDescription(languageId, useDefault);
	}

	/**
	* Returns the localized description of this layout set prototype in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this layout set prototype
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale) {
		return _layoutSetPrototype.getDescription(locale);
	}

	/**
	* Returns the localized description of this layout set prototype in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this layout set prototype. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _layoutSetPrototype.getDescription(locale, useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _layoutSetPrototype.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _layoutSetPrototype.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this layout set prototype.
	*
	* @return the locales and localized descriptions of this layout set prototype
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _layoutSetPrototype.getDescriptionMap();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _layoutSetPrototype.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetPrototype.getGroup();
	}

	@Override
	public long getGroupId()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetPrototype.getGroupId();
	}

	@Override
	public com.liferay.portal.model.LayoutSet getLayoutSet()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetPrototype.getLayoutSet();
	}

	/**
	* Returns the layout set prototype ID of this layout set prototype.
	*
	* @return the layout set prototype ID of this layout set prototype
	*/
	@Override
	public long getLayoutSetPrototypeId() {
		return _layoutSetPrototype.getLayoutSetPrototypeId();
	}

	/**
	* Returns the modified date of this layout set prototype.
	*
	* @return the modified date of this layout set prototype
	*/
	@Override
	public Date getModifiedDate() {
		return _layoutSetPrototype.getModifiedDate();
	}

	/**
	* Returns the mvcc version of this layout set prototype.
	*
	* @return the mvcc version of this layout set prototype
	*/
	@Override
	public long getMvccVersion() {
		return _layoutSetPrototype.getMvccVersion();
	}

	/**
	* Returns the name of this layout set prototype.
	*
	* @return the name of this layout set prototype
	*/
	@Override
	public java.lang.String getName() {
		return _layoutSetPrototype.getName();
	}

	/**
	* Returns the localized name of this layout set prototype in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this layout set prototype
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId) {
		return _layoutSetPrototype.getName(languageId);
	}

	/**
	* Returns the localized name of this layout set prototype in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this layout set prototype
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _layoutSetPrototype.getName(languageId, useDefault);
	}

	/**
	* Returns the localized name of this layout set prototype in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this layout set prototype
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale) {
		return _layoutSetPrototype.getName(locale);
	}

	/**
	* Returns the localized name of this layout set prototype in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this layout set prototype. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _layoutSetPrototype.getName(locale, useDefault);
	}

	@Override
	public java.lang.String getNameCurrentLanguageId() {
		return _layoutSetPrototype.getNameCurrentLanguageId();
	}

	@Override
	public java.lang.String getNameCurrentValue() {
		return _layoutSetPrototype.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this layout set prototype.
	*
	* @return the locales and localized names of this layout set prototype
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getNameMap() {
		return _layoutSetPrototype.getNameMap();
	}

	/**
	* Returns the primary key of this layout set prototype.
	*
	* @return the primary key of this layout set prototype
	*/
	@Override
	public long getPrimaryKey() {
		return _layoutSetPrototype.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _layoutSetPrototype.getPrimaryKeyObj();
	}

	/**
	* Returns the settings of this layout set prototype.
	*
	* @return the settings of this layout set prototype
	*/
	@Override
	public java.lang.String getSettings() {
		return _layoutSetPrototype.getSettings();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties getSettingsProperties() {
		return _layoutSetPrototype.getSettingsProperties();
	}

	@Override
	public java.lang.String getSettingsProperty(java.lang.String key) {
		return _layoutSetPrototype.getSettingsProperty(key);
	}

	/**
	* Returns the user ID of this layout set prototype.
	*
	* @return the user ID of this layout set prototype
	*/
	@Override
	public long getUserId() {
		return _layoutSetPrototype.getUserId();
	}

	/**
	* Returns the user name of this layout set prototype.
	*
	* @return the user name of this layout set prototype
	*/
	@Override
	public java.lang.String getUserName() {
		return _layoutSetPrototype.getUserName();
	}

	/**
	* Returns the user uuid of this layout set prototype.
	*
	* @return the user uuid of this layout set prototype
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _layoutSetPrototype.getUserUuid();
	}

	/**
	* Returns the uuid of this layout set prototype.
	*
	* @return the uuid of this layout set prototype
	*/
	@Override
	public java.lang.String getUuid() {
		return _layoutSetPrototype.getUuid();
	}

	@Override
	public boolean hasSetModifiedDate() {
		return _layoutSetPrototype.hasSetModifiedDate();
	}

	@Override
	public int hashCode() {
		return _layoutSetPrototype.hashCode();
	}

	/**
	* Returns <code>true</code> if this layout set prototype is active.
	*
	* @return <code>true</code> if this layout set prototype is active; <code>false</code> otherwise
	*/
	@Override
	public boolean isActive() {
		return _layoutSetPrototype.isActive();
	}

	@Override
	public boolean isCachedModel() {
		return _layoutSetPrototype.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _layoutSetPrototype.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _layoutSetPrototype.isNew();
	}

	@Override
	public void persist() {
		_layoutSetPrototype.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.LocaleException {
		_layoutSetPrototype.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_layoutSetPrototype.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	* Sets whether this layout set prototype is active.
	*
	* @param active the active of this layout set prototype
	*/
	@Override
	public void setActive(boolean active) {
		_layoutSetPrototype.setActive(active);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_layoutSetPrototype.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this layout set prototype.
	*
	* @param companyId the company ID of this layout set prototype
	*/
	@Override
	public void setCompanyId(long companyId) {
		_layoutSetPrototype.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this layout set prototype.
	*
	* @param createDate the create date of this layout set prototype
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_layoutSetPrototype.setCreateDate(createDate);
	}

	/**
	* Sets the description of this layout set prototype.
	*
	* @param description the description of this layout set prototype
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_layoutSetPrototype.setDescription(description);
	}

	/**
	* Sets the localized description of this layout set prototype in the language.
	*
	* @param description the localized description of this layout set prototype
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_layoutSetPrototype.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this layout set prototype in the language, and sets the default locale.
	*
	* @param description the localized description of this layout set prototype
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_layoutSetPrototype.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_layoutSetPrototype.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this layout set prototype from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this layout set prototype
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap) {
		_layoutSetPrototype.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this layout set prototype from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this layout set prototype
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_layoutSetPrototype.setDescriptionMap(descriptionMap, defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_layoutSetPrototype.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_layoutSetPrototype.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_layoutSetPrototype.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the layout set prototype ID of this layout set prototype.
	*
	* @param layoutSetPrototypeId the layout set prototype ID of this layout set prototype
	*/
	@Override
	public void setLayoutSetPrototypeId(long layoutSetPrototypeId) {
		_layoutSetPrototype.setLayoutSetPrototypeId(layoutSetPrototypeId);
	}

	/**
	* Sets the modified date of this layout set prototype.
	*
	* @param modifiedDate the modified date of this layout set prototype
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_layoutSetPrototype.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the mvcc version of this layout set prototype.
	*
	* @param mvccVersion the mvcc version of this layout set prototype
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_layoutSetPrototype.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the name of this layout set prototype.
	*
	* @param name the name of this layout set prototype
	*/
	@Override
	public void setName(java.lang.String name) {
		_layoutSetPrototype.setName(name);
	}

	/**
	* Sets the localized name of this layout set prototype in the language.
	*
	* @param name the localized name of this layout set prototype
	* @param locale the locale of the language
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale) {
		_layoutSetPrototype.setName(name, locale);
	}

	/**
	* Sets the localized name of this layout set prototype in the language, and sets the default locale.
	*
	* @param name the localized name of this layout set prototype
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_layoutSetPrototype.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_layoutSetPrototype.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this layout set prototype from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this layout set prototype
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap) {
		_layoutSetPrototype.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this layout set prototype from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this layout set prototype
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_layoutSetPrototype.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_layoutSetPrototype.setNew(n);
	}

	/**
	* Sets the primary key of this layout set prototype.
	*
	* @param primaryKey the primary key of this layout set prototype
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_layoutSetPrototype.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_layoutSetPrototype.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the settings of this layout set prototype.
	*
	* @param settings the settings of this layout set prototype
	*/
	@Override
	public void setSettings(java.lang.String settings) {
		_layoutSetPrototype.setSettings(settings);
	}

	@Override
	public void setSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties settingsProperties) {
		_layoutSetPrototype.setSettingsProperties(settingsProperties);
	}

	/**
	* Sets the user ID of this layout set prototype.
	*
	* @param userId the user ID of this layout set prototype
	*/
	@Override
	public void setUserId(long userId) {
		_layoutSetPrototype.setUserId(userId);
	}

	/**
	* Sets the user name of this layout set prototype.
	*
	* @param userName the user name of this layout set prototype
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_layoutSetPrototype.setUserName(userName);
	}

	/**
	* Sets the user uuid of this layout set prototype.
	*
	* @param userUuid the user uuid of this layout set prototype
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_layoutSetPrototype.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this layout set prototype.
	*
	* @param uuid the uuid of this layout set prototype
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_layoutSetPrototype.setUuid(uuid);
	}

	@Override
	public CacheModel<com.liferay.portal.model.LayoutSetPrototype> toCacheModel() {
		return _layoutSetPrototype.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.LayoutSetPrototype toEscapedModel() {
		return new LayoutSetPrototypeWrapper(_layoutSetPrototype.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _layoutSetPrototype.toString();
	}

	@Override
	public com.liferay.portal.model.LayoutSetPrototype toUnescapedModel() {
		return new LayoutSetPrototypeWrapper(_layoutSetPrototype.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _layoutSetPrototype.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutSetPrototypeWrapper)) {
			return false;
		}

		LayoutSetPrototypeWrapper layoutSetPrototypeWrapper = (LayoutSetPrototypeWrapper)obj;

		if (Validator.equals(_layoutSetPrototype,
					layoutSetPrototypeWrapper._layoutSetPrototype)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _layoutSetPrototype.getStagedModelType();
	}

	@Override
	public LayoutSetPrototype getWrappedModel() {
		return _layoutSetPrototype;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _layoutSetPrototype.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _layoutSetPrototype.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_layoutSetPrototype.resetOriginalValues();
	}

	private final LayoutSetPrototype _layoutSetPrototype;
}