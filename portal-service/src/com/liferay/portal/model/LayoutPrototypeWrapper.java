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

package com.liferay.portal.model;

import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link LayoutPrototype}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPrototype
 * @generated
 */
public class LayoutPrototypeWrapper implements LayoutPrototype,
	ModelWrapper<LayoutPrototype> {
	public LayoutPrototypeWrapper(LayoutPrototype layoutPrototype) {
		_layoutPrototype = layoutPrototype;
	}

	@Override
	public Class<?> getModelClass() {
		return LayoutPrototype.class;
	}

	@Override
	public String getModelClassName() {
		return LayoutPrototype.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("layoutPrototypeId", getLayoutPrototypeId());
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
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long layoutPrototypeId = (Long)attributes.get("layoutPrototypeId");

		if (layoutPrototypeId != null) {
			setLayoutPrototypeId(layoutPrototypeId);
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

	/**
	* Returns the primary key of this layout prototype.
	*
	* @return the primary key of this layout prototype
	*/
	@Override
	public long getPrimaryKey() {
		return _layoutPrototype.getPrimaryKey();
	}

	/**
	* Sets the primary key of this layout prototype.
	*
	* @param primaryKey the primary key of this layout prototype
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_layoutPrototype.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this layout prototype.
	*
	* @return the uuid of this layout prototype
	*/
	@Override
	public java.lang.String getUuid() {
		return _layoutPrototype.getUuid();
	}

	/**
	* Sets the uuid of this layout prototype.
	*
	* @param uuid the uuid of this layout prototype
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_layoutPrototype.setUuid(uuid);
	}

	/**
	* Returns the layout prototype ID of this layout prototype.
	*
	* @return the layout prototype ID of this layout prototype
	*/
	@Override
	public long getLayoutPrototypeId() {
		return _layoutPrototype.getLayoutPrototypeId();
	}

	/**
	* Sets the layout prototype ID of this layout prototype.
	*
	* @param layoutPrototypeId the layout prototype ID of this layout prototype
	*/
	@Override
	public void setLayoutPrototypeId(long layoutPrototypeId) {
		_layoutPrototype.setLayoutPrototypeId(layoutPrototypeId);
	}

	/**
	* Returns the company ID of this layout prototype.
	*
	* @return the company ID of this layout prototype
	*/
	@Override
	public long getCompanyId() {
		return _layoutPrototype.getCompanyId();
	}

	/**
	* Sets the company ID of this layout prototype.
	*
	* @param companyId the company ID of this layout prototype
	*/
	@Override
	public void setCompanyId(long companyId) {
		_layoutPrototype.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this layout prototype.
	*
	* @return the user ID of this layout prototype
	*/
	@Override
	public long getUserId() {
		return _layoutPrototype.getUserId();
	}

	/**
	* Sets the user ID of this layout prototype.
	*
	* @param userId the user ID of this layout prototype
	*/
	@Override
	public void setUserId(long userId) {
		_layoutPrototype.setUserId(userId);
	}

	/**
	* Returns the user uuid of this layout prototype.
	*
	* @return the user uuid of this layout prototype
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutPrototype.getUserUuid();
	}

	/**
	* Sets the user uuid of this layout prototype.
	*
	* @param userUuid the user uuid of this layout prototype
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_layoutPrototype.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this layout prototype.
	*
	* @return the user name of this layout prototype
	*/
	@Override
	public java.lang.String getUserName() {
		return _layoutPrototype.getUserName();
	}

	/**
	* Sets the user name of this layout prototype.
	*
	* @param userName the user name of this layout prototype
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_layoutPrototype.setUserName(userName);
	}

	/**
	* Returns the create date of this layout prototype.
	*
	* @return the create date of this layout prototype
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _layoutPrototype.getCreateDate();
	}

	/**
	* Sets the create date of this layout prototype.
	*
	* @param createDate the create date of this layout prototype
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_layoutPrototype.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this layout prototype.
	*
	* @return the modified date of this layout prototype
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _layoutPrototype.getModifiedDate();
	}

	/**
	* Sets the modified date of this layout prototype.
	*
	* @param modifiedDate the modified date of this layout prototype
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_layoutPrototype.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the name of this layout prototype.
	*
	* @return the name of this layout prototype
	*/
	@Override
	public java.lang.String getName() {
		return _layoutPrototype.getName();
	}

	/**
	* Returns the localized name of this layout prototype in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this layout prototype
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale) {
		return _layoutPrototype.getName(locale);
	}

	/**
	* Returns the localized name of this layout prototype in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this layout prototype. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _layoutPrototype.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this layout prototype in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this layout prototype
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId) {
		return _layoutPrototype.getName(languageId);
	}

	/**
	* Returns the localized name of this layout prototype in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this layout prototype
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _layoutPrototype.getName(languageId, useDefault);
	}

	@Override
	public java.lang.String getNameCurrentLanguageId() {
		return _layoutPrototype.getNameCurrentLanguageId();
	}

	@Override
	public java.lang.String getNameCurrentValue() {
		return _layoutPrototype.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this layout prototype.
	*
	* @return the locales and localized names of this layout prototype
	*/
	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getNameMap() {
		return _layoutPrototype.getNameMap();
	}

	/**
	* Sets the name of this layout prototype.
	*
	* @param name the name of this layout prototype
	*/
	@Override
	public void setName(java.lang.String name) {
		_layoutPrototype.setName(name);
	}

	/**
	* Sets the localized name of this layout prototype in the language.
	*
	* @param name the localized name of this layout prototype
	* @param locale the locale of the language
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale) {
		_layoutPrototype.setName(name, locale);
	}

	/**
	* Sets the localized name of this layout prototype in the language, and sets the default locale.
	*
	* @param name the localized name of this layout prototype
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_layoutPrototype.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_layoutPrototype.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this layout prototype from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this layout prototype
	*/
	@Override
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap) {
		_layoutPrototype.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this layout prototype from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this layout prototype
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_layoutPrototype.setNameMap(nameMap, defaultLocale);
	}

	/**
	* Returns the description of this layout prototype.
	*
	* @return the description of this layout prototype
	*/
	@Override
	public java.lang.String getDescription() {
		return _layoutPrototype.getDescription();
	}

	/**
	* Sets the description of this layout prototype.
	*
	* @param description the description of this layout prototype
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_layoutPrototype.setDescription(description);
	}

	/**
	* Returns the settings of this layout prototype.
	*
	* @return the settings of this layout prototype
	*/
	@Override
	public java.lang.String getSettings() {
		return _layoutPrototype.getSettings();
	}

	/**
	* Sets the settings of this layout prototype.
	*
	* @param settings the settings of this layout prototype
	*/
	@Override
	public void setSettings(java.lang.String settings) {
		_layoutPrototype.setSettings(settings);
	}

	/**
	* Returns the active of this layout prototype.
	*
	* @return the active of this layout prototype
	*/
	@Override
	public boolean getActive() {
		return _layoutPrototype.getActive();
	}

	/**
	* Returns <code>true</code> if this layout prototype is active.
	*
	* @return <code>true</code> if this layout prototype is active; <code>false</code> otherwise
	*/
	@Override
	public boolean isActive() {
		return _layoutPrototype.isActive();
	}

	/**
	* Sets whether this layout prototype is active.
	*
	* @param active the active of this layout prototype
	*/
	@Override
	public void setActive(boolean active) {
		_layoutPrototype.setActive(active);
	}

	@Override
	public boolean isNew() {
		return _layoutPrototype.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_layoutPrototype.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _layoutPrototype.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_layoutPrototype.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _layoutPrototype.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _layoutPrototype.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_layoutPrototype.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _layoutPrototype.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_layoutPrototype.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_layoutPrototype.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_layoutPrototype.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_layoutPrototype.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public java.lang.Object clone() {
		return new LayoutPrototypeWrapper((LayoutPrototype)_layoutPrototype.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portal.model.LayoutPrototype layoutPrototype) {
		return _layoutPrototype.compareTo(layoutPrototype);
	}

	@Override
	public int hashCode() {
		return _layoutPrototype.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.LayoutPrototype> toCacheModel() {
		return _layoutPrototype.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.LayoutPrototype toEscapedModel() {
		return new LayoutPrototypeWrapper(_layoutPrototype.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.LayoutPrototype toUnescapedModel() {
		return new LayoutPrototypeWrapper(_layoutPrototype.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _layoutPrototype.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _layoutPrototype.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_layoutPrototype.persist();
	}

	@Override
	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutPrototype.getGroup();
	}

	@Override
	public long getGroupId()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutPrototype.getGroupId();
	}

	@Override
	public com.liferay.portal.model.Layout getLayout()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutPrototype.getLayout();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutPrototypeWrapper)) {
			return false;
		}

		LayoutPrototypeWrapper layoutPrototypeWrapper = (LayoutPrototypeWrapper)obj;

		if (Validator.equals(_layoutPrototype,
					layoutPrototypeWrapper._layoutPrototype)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public LayoutPrototype getWrappedLayoutPrototype() {
		return _layoutPrototype;
	}

	@Override
	public LayoutPrototype getWrappedModel() {
		return _layoutPrototype;
	}

	@Override
	public void resetOriginalValues() {
		_layoutPrototype.resetOriginalValues();
	}

	private LayoutPrototype _layoutPrototype;
}