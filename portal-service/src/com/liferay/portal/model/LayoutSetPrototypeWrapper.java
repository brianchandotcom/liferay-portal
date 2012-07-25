/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link LayoutSetPrototype}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutSetPrototype
 * @generated
 */
public class LayoutSetPrototypeWrapper implements LayoutSetPrototype,
	ModelWrapper<LayoutSetPrototype> {
	public LayoutSetPrototypeWrapper(LayoutSetPrototype layoutSetPrototype) {
		_layoutSetPrototype = layoutSetPrototype;
	}

	public Class<?> getModelClass() {
		return LayoutSetPrototype.class;
	}

	public String getModelClassName() {
		return LayoutSetPrototype.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("layoutSetPrototypeId", getLayoutSetPrototypeId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("settings", getSettings());
		attributes.put("active", getActive());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
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

	/**
	* Returns the primary key of this layout set prototype.
	*
	* @return the primary key of this layout set prototype
	*/
	public long getPrimaryKey() {
		return _layoutSetPrototype.getPrimaryKey();
	}

	/**
	* Sets the primary key of this layout set prototype.
	*
	* @param primaryKey the primary key of this layout set prototype
	*/
	public void setPrimaryKey(long primaryKey) {
		_layoutSetPrototype.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this layout set prototype.
	*
	* @return the uuid of this layout set prototype
	*/
	public java.lang.String getUuid() {
		return _layoutSetPrototype.getUuid();
	}

	/**
	* Sets the uuid of this layout set prototype.
	*
	* @param uuid the uuid of this layout set prototype
	*/
	public void setUuid(java.lang.String uuid) {
		_layoutSetPrototype.setUuid(uuid);
	}

	/**
	* Returns the layout set prototype ID of this layout set prototype.
	*
	* @return the layout set prototype ID of this layout set prototype
	*/
	public long getLayoutSetPrototypeId() {
		return _layoutSetPrototype.getLayoutSetPrototypeId();
	}

	/**
	* Sets the layout set prototype ID of this layout set prototype.
	*
	* @param layoutSetPrototypeId the layout set prototype ID of this layout set prototype
	*/
	public void setLayoutSetPrototypeId(long layoutSetPrototypeId) {
		_layoutSetPrototype.setLayoutSetPrototypeId(layoutSetPrototypeId);
	}

	/**
	* Returns the company ID of this layout set prototype.
	*
	* @return the company ID of this layout set prototype
	*/
	public long getCompanyId() {
		return _layoutSetPrototype.getCompanyId();
	}

	/**
	* Sets the company ID of this layout set prototype.
	*
	* @param companyId the company ID of this layout set prototype
	*/
	public void setCompanyId(long companyId) {
		_layoutSetPrototype.setCompanyId(companyId);
	}

	/**
	* Returns the create date of this layout set prototype.
	*
	* @return the create date of this layout set prototype
	*/
	public java.util.Date getCreateDate() {
		return _layoutSetPrototype.getCreateDate();
	}

	/**
	* Sets the create date of this layout set prototype.
	*
	* @param createDate the create date of this layout set prototype
	*/
	public void setCreateDate(java.util.Date createDate) {
		_layoutSetPrototype.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this layout set prototype.
	*
	* @return the modified date of this layout set prototype
	*/
	public java.util.Date getModifiedDate() {
		return _layoutSetPrototype.getModifiedDate();
	}

	/**
	* Sets the modified date of this layout set prototype.
	*
	* @param modifiedDate the modified date of this layout set prototype
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_layoutSetPrototype.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the name of this layout set prototype.
	*
	* @return the name of this layout set prototype
	*/
	public java.lang.String getName() {
		return _layoutSetPrototype.getName();
	}

	/**
	* Returns the localized name of this layout set prototype in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this layout set prototype
	*/
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
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _layoutSetPrototype.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this layout set prototype in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this layout set prototype
	*/
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
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _layoutSetPrototype.getName(languageId, useDefault);
	}

	public java.lang.String getNameCurrentLanguageId() {
		return _layoutSetPrototype.getNameCurrentLanguageId();
	}

	public java.lang.String getNameCurrentValue() {
		return _layoutSetPrototype.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this layout set prototype.
	*
	* @return the locales and localized names of this layout set prototype
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getNameMap() {
		return _layoutSetPrototype.getNameMap();
	}

	/**
	* Sets the name of this layout set prototype.
	*
	* @param name the name of this layout set prototype
	*/
	public void setName(java.lang.String name) {
		_layoutSetPrototype.setName(name);
	}

	/**
	* Sets the localized name of this layout set prototype in the language.
	*
	* @param name the localized name of this layout set prototype
	* @param locale the locale of the language
	*/
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
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_layoutSetPrototype.setName(name, locale, defaultLocale);
	}

	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_layoutSetPrototype.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this layout set prototype from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this layout set prototype
	*/
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap) {
		_layoutSetPrototype.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this layout set prototype from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this layout set prototype
	* @param defaultLocale the default locale
	*/
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_layoutSetPrototype.setNameMap(nameMap, defaultLocale);
	}

	/**
	* Returns the description of this layout set prototype.
	*
	* @return the description of this layout set prototype
	*/
	public java.lang.String getDescription() {
		return _layoutSetPrototype.getDescription();
	}

	/**
	* Sets the description of this layout set prototype.
	*
	* @param description the description of this layout set prototype
	*/
	public void setDescription(java.lang.String description) {
		_layoutSetPrototype.setDescription(description);
	}

	/**
	* Returns the settings of this layout set prototype.
	*
	* @return the settings of this layout set prototype
	*/
	public java.lang.String getSettings() {
		return _layoutSetPrototype.getSettings();
	}

	/**
	* Sets the settings of this layout set prototype.
	*
	* @param settings the settings of this layout set prototype
	*/
	public void setSettings(java.lang.String settings) {
		_layoutSetPrototype.setSettings(settings);
	}

	/**
	* Returns the active of this layout set prototype.
	*
	* @return the active of this layout set prototype
	*/
	public boolean getActive() {
		return _layoutSetPrototype.getActive();
	}

	/**
	* Returns <code>true</code> if this layout set prototype is active.
	*
	* @return <code>true</code> if this layout set prototype is active; <code>false</code> otherwise
	*/
	public boolean isActive() {
		return _layoutSetPrototype.isActive();
	}

	/**
	* Sets whether this layout set prototype is active.
	*
	* @param active the active of this layout set prototype
	*/
	public void setActive(boolean active) {
		_layoutSetPrototype.setActive(active);
	}

	public boolean isNew() {
		return _layoutSetPrototype.isNew();
	}

	public void setNew(boolean n) {
		_layoutSetPrototype.setNew(n);
	}

	public boolean isCachedModel() {
		return _layoutSetPrototype.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_layoutSetPrototype.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _layoutSetPrototype.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _layoutSetPrototype.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_layoutSetPrototype.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _layoutSetPrototype.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_layoutSetPrototype.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new LayoutSetPrototypeWrapper((LayoutSetPrototype)_layoutSetPrototype.clone());
	}

	public int compareTo(
		com.liferay.portal.model.LayoutSetPrototype layoutSetPrototype) {
		return _layoutSetPrototype.compareTo(layoutSetPrototype);
	}

	@Override
	public int hashCode() {
		return _layoutSetPrototype.hashCode();
	}

	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale) {
		_layoutSetPrototype.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.LayoutSetPrototype> toCacheModel() {
		return _layoutSetPrototype.toCacheModel();
	}

	public com.liferay.portal.model.LayoutSetPrototype toEscapedModel() {
		return new LayoutSetPrototypeWrapper(_layoutSetPrototype.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _layoutSetPrototype.toString();
	}

	public java.lang.String toXmlString() {
		return _layoutSetPrototype.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_layoutSetPrototype.persist();
	}

	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetPrototype.getGroup();
	}

	public com.liferay.portal.model.LayoutSet getLayoutSet()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetPrototype.getLayoutSet();
	}

	public com.liferay.portal.kernel.util.UnicodeProperties getSettingsProperties() {
		return _layoutSetPrototype.getSettingsProperties();
	}

	public java.lang.String getSettingsProperty(java.lang.String key) {
		return _layoutSetPrototype.getSettingsProperty(key);
	}

	public void setSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties settingsProperties) {
		_layoutSetPrototype.setSettingsProperties(settingsProperties);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public LayoutSetPrototype getWrappedLayoutSetPrototype() {
		return _layoutSetPrototype;
	}

	public LayoutSetPrototype getWrappedModel() {
		return _layoutSetPrototype;
	}

	public void resetOriginalValues() {
		_layoutSetPrototype.resetOriginalValues();
	}

	private LayoutSetPrototype _layoutSetPrototype;
}